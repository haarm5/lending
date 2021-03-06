package com.tmb.oneapp.lendingservice.service;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.tmb.common.exception.model.TMBCommonException;
import com.tmb.common.logger.TMBLogger;
import com.tmb.common.model.legacy.rsl.common.ob.address.Address;
import com.tmb.common.model.legacy.rsl.common.ob.creditcard.CreditCard;
import com.tmb.common.model.legacy.rsl.common.ob.facility.Facility;
import com.tmb.common.model.legacy.rsl.common.ob.individual.Individual;
import com.tmb.common.model.legacy.rsl.ws.application.response.Body;
import com.tmb.common.model.legacy.rsl.ws.application.response.ResponseApplication;
import com.tmb.common.model.legacy.rsl.ws.creditcard.response.ResponseCreditcard;
import com.tmb.common.model.legacy.rsl.ws.facility.response.ResponseFacility;
import com.tmb.common.model.legacy.rsl.ws.individual.response.ResponseIndividual;
import com.tmb.oneapp.lendingservice.client.LoanSubmissionGetApplicationInfoClient;
import com.tmb.oneapp.lendingservice.client.LoanSubmissionGetCreditcardInfoClient;
import com.tmb.oneapp.lendingservice.client.LoanSubmissionGetCustomerInfoClient;
import com.tmb.oneapp.lendingservice.client.LoanSubmissionGetFacilityInfoClient;
import com.tmb.oneapp.lendingservice.model.dropdown.Dropdowns;
import com.tmb.oneapp.lendingservice.model.loanonline.EAppResponse;
import com.tmb.oneapp.lendingservice.util.RslServiceUtils;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import javax.xml.rpc.ServiceException;
import java.math.BigDecimal;
import java.rmi.RemoteException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

@Service
@AllArgsConstructor
public class LoanOnlineSubmissionEAppService {

    private static final TMBLogger<LoanOnlineSubmissionEAppService> logger = new TMBLogger<>(LoanOnlineSubmissionEAppService.class);

    private final LoanSubmissionGetApplicationInfoClient loanSubmissionGetApplicationInfoClient;
    private final LoanSubmissionGetCustomerInfoClient loanSubmissionGetCustomerInfoClient;
    private final LoanSubmissionGetFacilityInfoClient loanSubmissionGetFacilityInfoClient;
    private final LoanSubmissionGetCreditcardInfoClient loanSubmissionGetCreditcardInfoClient;
    private final DropdownService dropdownService;

    private static String rmId;
    private static String correlationId;
    private static String productCode;

    public EAppResponse getEApp(long caId, String crmId, String correlation) throws ServiceException, TMBCommonException, JsonProcessingException, RemoteException, ParseException {

        try {
            rmId = crmId;
            correlationId = correlation;
            EAppResponse response = new EAppResponse();

            Body application = getApplicationInfo(caId);
            productCode = application.getProduct();
            Individual customer = getCustomerInfo(caId);
            if (application.getAppType().equals("CC")) {
                CreditCard creditCard = getCreditCard(caId);
                mapDataFromCreditCard(creditCard, response);
                response.setProductType("??????????????????????????????");
                response.setDelivery(mapDelivery(creditCard.getMailPreference()));
            } else {
                Facility facility = getFacility(caId);
                mapDataFromFacility(facility, response);
                response.setProductType("???????????????????????????????????????????????????");
                response.setDelivery(mapDelivery(facility.getMailingPreference()));
            }
            mapDataFromApplication(application, response);
            mapDataFromCustomer(customer, response);
            return response;
        } catch (Exception e) {
            logger.error("get e-app service error", e);
            throw e;
        }
    }

    private EAppResponse mapDataFromApplication(Body application, EAppResponse response) throws ParseException {
        response.setAppNo(application.getAppRefNo());
        response.setProductNameTh(application.getProductDescTH());
        response.setWaiveDoc(application.getNatureOfRequest().equals("04") || application.getNatureOfRequest().equals("12"));
        response.setAcceptBy("Access Pin");
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
        Date date = Objects.nonNull(application.getNcbConsentDate()) ? sdf.parse(application.getNcbConsentDate()) : sdf.parse(application.getApplicationDate());
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        response.setAcceptDate(cal);

        return response;
    }

    private EAppResponse mapDataFromCustomer(Individual customer, EAppResponse response) throws TMBCommonException, JsonProcessingException {

        //application
        response.setEmploymentStatus("????????????????????????????????????");
        if (customer.getEmploymentStatus().equals("02")) {
            response.setEmploymentStatus("???????????????????????????????????????");
        }
        response.setEmploymentStatusCode(customer.getEmploymentStatus());
        response.setSalary(customer.getEmploymentFinalTotalIncome());
        response.setOtherIncome(customer.getIncomeOtherIncome());

        //customer information
        mapCustomerInformation(customer, response);

        //working information
        mapWorkingInformation(customer, response);

        response.setEStatement("?????????");
        if (customer.getEmailStatementFlag().equals("N")) {
            response.setEStatement("?????????");
        }
        response.setNcbModelAccept("??????????????????");
        if (customer.getNcbConsentFlag().equals("N")) {
            response.setNcbModelAccept("???????????????????????????");
        }

        return response;
    }

    private EAppResponse mapCustomerInformation(Individual customer, EAppResponse response) throws TMBCommonException, JsonProcessingException {
        response.setIdType("?????????????????????????????????????????????????????????");
        if (customer.getIdType1().equals("PP")) {
            response.setIdType("????????????????????????");
        }
        response.setIdNo(customer.getIdNo1());
        response.setIssueCountry(mapCountryName(customer.getIdIssueCtry1()));
        response.setIssueDate(adjustDate(customer.getIssuedDate()));
        // expire date ?????????????????????
        response.setExpiryDate(customer.getExpiryDate());
        response.setNameTh(customer.getThaiName() + " " + customer.getThaiSurName());
        response.setNameEn(customer.getNameLine2() + " " + customer.getNameLine1());
        response.setBirthDay(adjustDate(customer.getBirthDate()));
        response.setMobileNo(customer.getMobileNo());
        response.setEducationLevel(mapEducationLevel(customer.getEducationLevel()));
        response.setSourceFromCountry(mapCountryName(customer.getSourceFromCountry()));
        response.setNationality(mapCountryName(customer.getNationality()));
        response.setMaritalStatus(mapMaritalStatus(customer.getMaritalStatus()));
        response.setEmail(customer.getEmail());
        response.setContactAddress(mapAddress("H", customer.getAddresses()));
        response.setResidentStatus(mapResidentType(customer.getResidentType()));
        return response;
    }


    private EAppResponse mapWorkingInformation(Individual customer, EAppResponse response) throws TMBCommonException, JsonProcessingException {
        // ?????????????????????????????? map ??????????????????
        response.setRmOccupation(mapRmOccupationName(customer.getEmploymentOccupation(), customer.getRmOccupation()));
        response.setOccupation(mapProfessional(customer.getProfessionalCode(), customer.getEmploymentStatus()));
        response.setContractType("?????????????????????????????????????????????");
        if (customer.getContractEmployedFlag().equals("Y")) {
            response.setContractType("????????????????????????????????????");
        }
        response.setWorkPeriodYear(customer.getEmploymentYear());
        response.setWorkPeriodMonth(customer.getEmploymentMonth());
        response.setWorkName(customer.getEmploymentName());
        response.setWorkAddress(mapAddress("O", customer.getAddresses()));
        response.setWorkTel(customer.getEmploymentTelephoneDirectNo());
        response.setWorkTelEx(customer.getEmploymentTelephoneExtNo());
        response.setIncomeBank(mapIncomeBank(customer.getIncomeBankName()));
        response.setIncomeBankAccountNo(customer.getIncomeBankAccoutNumber());
        response.setCashFlow(customer.getIncometotalLastMthCreditAcct1());
        response.setSharePercent(customer.getIncomeSharedHolderPercent());
        return response;
    }

    private EAppResponse mapDataFromCreditCard(CreditCard creditCard, EAppResponse response) {
        response.setPaymentMethod(mapPaymentMethod(creditCard.getPaymentMethod()));
        response.setPaymentMethodCode(creditCard.getPaymentMethod());
        response.setPaymentAccountName(creditCard.getDebitAccountName());
        response.setPaymentAccountNo(creditCard.getDebitAccountNo());
        response.setPaymentCriteria(mapPaymentMethodCriteria(creditCard.getPaymentCriteria(), "CC"));
        return response;
    }

    private EAppResponse mapDataFromFacility(Facility facility, EAppResponse response) {
        response.setLimitApplied(facility.getLimitApplied());
        response.setMonthlyInstallment(facility.getMonthlyInstallment());
        response.setInterest(facility.getPricings() != null ? facility.getPricings()[0].getInterestRate() : null);
        response.setDisburstAccountNo(facility.getDisburstAccountNo());
        response.setPaymentMethod(mapPaymentMethod(facility.getPaymentMethod()));
        response.setPaymentMethodCode(facility.getPaymentMethod());
        response.setPaymentAccountName(facility.getPaymentAccountName());
        response.setPaymentAccountNo(facility.getPaymentAccountNo());
        response.setPaymentCriteria(mapPaymentMethodCriteria(facility.getPayMethodCriteria(), "PL"));
        response.setLoanWithOtherBank(mapLoanWithOtherBank(facility.getLoanWithOtherBank()));
        response.setConsiderLoanWithOtherBank(mapLoanWithOtherBank(facility.getConsiderLoanWithOtherBank()));
        if (Objects.nonNull(facility.getFeature())) {
            response.setRequestAmount(facility.getFeature().getRequestAmount());
        }
        response.setPaymentPlan(mapPaymentPlan(facility.getFeatureType()));
        response.setTenure(mapTenure(facility));

        return response;
    }

    private Calendar adjustDate(Calendar date) {
        if (Objects.nonNull(date)) {
            date.set(Calendar.HOUR, 0);
            date.set(Calendar.MINUTE, 0);
        }
        return date;
    }

    private BigDecimal mapTenure(Facility facility) {
        if (productCode.contains("RC") && Objects.nonNull(facility.getFeature()) && Objects.nonNull(facility.getFeature().getTenure())) {
            return BigDecimal.valueOf(facility.getFeature().getTenure());
        }
        return facility.getTenure();
    }

    private String mapPaymentMethod(String code) {
        if (code.equals("1")) {
            return "??????????????????????????????????????????";
        }
        return "??????????????????????????????";
    }

    private String mapPaymentMethodCriteria(String code, String type) {
        if (code.equals("0") && type.equals("CC")) {
            return "????????????????????????????????????????????? 5%";
        } else if (code.equals("0") && type.equals("PL")) {
            return "????????????????????????????????????????????? 3%";
        }
        return "???????????????????????????????????????????????????";
    }

    private String mapLoanWithOtherBank(String code) {
        if (code.equals("2")) {
            return "?????? 1 - 2 ????????????";
        } else if (code.equals("3")) {
            return "3 ??????????????????????????????";
        }
        return "???????????????";
    }

    private String mapPaymentPlan(String code) {
        if (Objects.nonNull(code)) {
            if (code.contains("c")) {
                return "Cash Chill Chill (??????????????????????????????????????????????????????????????? ???????????????)";
            }
            return "Cash Transfer (????????????????????????????????????????????????????????????)";
        }
        return "";
    }

    private String mapDelivery(String type) {
        if (type.equals("H")) {
            return "?????????????????????????????????????????????";
        }
        return "?????????????????????????????????????????????";
    }


    private String mapAddress(String type, Address[] addresses) {

        Optional<Address> filter = Arrays.stream(addresses).filter(x -> x.getAddrTypCode().equals(type)).findFirst();
        if (filter.isPresent()) {
            Address address = filter.get();
            String result = address.getAddress();
            if (!address.getBuildingName().isEmpty()) {
                String[] arrOfStr = address.getBuildingName().split("????????????");
                if (arrOfStr.length > 1) {
                    result = result + " " + arrOfStr[0];
                    result = prepareAddress(result, "???.", address.getMoo());
                    result = prepareAddress(result, "????????????", address.getFloor());
                    result = result + " " + "????????????" + " " + arrOfStr[1];
                } else {
                    result = result + " " + address.getBuildingName();
                    result = prepareAddress(result, "???.", address.getMoo());
                    result = prepareAddress(result, "????????????", address.getFloor());
                }
            } else {
                result = prepareAddress(result, "???.", address.getMoo());
            }
            result = prepareAddress(result, "???.", address.getStreetName());
            result = prepareAddress(result, "???.", address.getRoad());

            result = result + " " + address.getTumbol() +
                    " " + address.getAmphur() + " " + address.getProvince() +
                    " " + address.getPostalCode();
            return result;
        }
        return null;
    }

    private String prepareAddress(String result, String name, String value) {
        if (!value.isEmpty()) {
            result = result + " " + name + " " + value;
        }
        return result;
    }


    private String mapCountryName(String code) throws TMBCommonException, JsonProcessingException {
        List<Dropdowns.SciCountry> countries = dropdownService.getDropdownSciCountry(correlationId, rmId);
        Optional<Dropdowns.SciCountry> filter = countries.stream().filter(x -> x.getCode().equals(code)).findFirst();
        return filter.map(Dropdowns.SciCountry::getName2).orElse(null);
    }

    private String mapRmOccupationName(String occupation, String rmOccupation) throws JsonProcessingException {
        logger.info("mapRmOccupationName: " + "occupation:: " + occupation + "rmOccupation:: " + rmOccupation);
        List<Dropdowns.RmOccupation> rmOccupations = dropdownService.getDropdownRmOccupationName(rmOccupation);
        return rmOccupations.isEmpty() ? null : rmOccupations.get(0).getName();
    }

    private String mapProfessional(String code, String employmentStatus) throws TMBCommonException, JsonProcessingException {
        List<Dropdowns.Occupation> professionals = dropdownService.getDropdownOccupation(employmentStatus);
        Optional<Dropdowns.Occupation> filter = professionals.stream().filter(x -> x.getCode().equals(code)).findFirst();
        return filter.map(Dropdowns.Occupation::getName).orElse(null);
    }

    private String mapMaritalStatus(String code) throws JsonProcessingException {
        List<Dropdowns.MaritalStatus> statusList = dropdownService.getDropdownMaritalStatus(code);
        Optional<Dropdowns.MaritalStatus> filter = statusList.stream().filter(x -> x.getCode().equals(code)).findFirst();
        return filter.map(Dropdowns.MaritalStatus::getName2).orElse(null);
    }

    private String mapResidentType(String code) throws JsonProcessingException {
        List<Dropdowns.ResidentType> residentTypes = dropdownService.getDropdownResidentType(code);
        Optional<Dropdowns.ResidentType> filter = residentTypes.stream().filter(x -> x.getCode().equals(code)).findFirst();
        return filter.map(Dropdowns.ResidentType::getName2).orElse(null);
    }

    private String mapIncomeBank(String code) throws JsonProcessingException {
        List<Dropdowns.IncomeBank> banks = dropdownService.getDropdownIncomeBank();
        Optional<Dropdowns.IncomeBank> filter = banks.stream().filter(x -> x.getCode().equals(code)).findFirst();
        return filter.map(Dropdowns.IncomeBank::getName2).orElse(null);
    }

    private String mapEducationLevel(String code) throws JsonProcessingException {
        List<Dropdowns.EducationLevel> banks = dropdownService.getDropdownEducationLevel(code);
        Optional<Dropdowns.EducationLevel> filter = banks.stream().filter(x -> x.getCode().equals(code)).findFirst();
        return filter.map(Dropdowns.EducationLevel::getName2).orElse(null);
    }


    //get data from rsl

    private Body getApplicationInfo(long caId) throws ServiceException, TMBCommonException, JsonProcessingException {
        ResponseApplication response = loanSubmissionGetApplicationInfoClient.searchApplicationInfoByCaID(caId);
        RslServiceUtils.checkRslResponse(response.getHeader().getResponseCode(), response.getHeader().getResponseDescriptionEN());
        return response.getBody();
    }

    private Individual getCustomerInfo(long caId) throws ServiceException, TMBCommonException, RemoteException, JsonProcessingException {
        ResponseIndividual response = loanSubmissionGetCustomerInfoClient.searchCustomerInfoByCaID(caId);
        RslServiceUtils.checkRslResponse(response.getHeader().getResponseCode(), response.getHeader().getResponseDescriptionEN());
        return response.getBody().getIndividuals()[0];
    }

    private Facility getFacility(long caId) throws ServiceException, TMBCommonException, JsonProcessingException {
        ResponseFacility response = loanSubmissionGetFacilityInfoClient.searchFacilityInfoByCaID(caId);
        RslServiceUtils.checkRslResponse(response.getHeader().getResponseCode(), response.getHeader().getResponseDescriptionEN());
        return response.getBody().getFacilities()[0];
    }

    private CreditCard getCreditCard(long caId) throws ServiceException, TMBCommonException, JsonProcessingException {
        ResponseCreditcard response = loanSubmissionGetCreditcardInfoClient.searchCreditcardInfoByCaID(caId);
        RslServiceUtils.checkRslResponse(response.getHeader().getResponseCode(), response.getHeader().getResponseDescriptionEN());
        return response.getBody().getCreditCards()[0];
    }

}
