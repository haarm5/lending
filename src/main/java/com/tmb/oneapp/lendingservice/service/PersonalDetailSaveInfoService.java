package com.tmb.oneapp.lendingservice.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.tmb.common.exception.model.TMBCommonException;
import com.tmb.common.logger.TMBLogger;
import com.tmb.common.model.CustGeneralProfileResponse;
import com.tmb.common.model.legacy.rsl.common.ob.dropdown.CommonCodeEntry;
import com.tmb.common.model.legacy.rsl.common.ob.individual.Individual;
import com.tmb.common.model.legacy.rsl.ws.dropdown.response.ResponseDropdown;
import com.tmb.common.model.legacy.rsl.ws.individual.update.request.Body;
import com.tmb.common.model.legacy.rsl.ws.individual.update.request.RequestIndividual;
import com.tmb.common.model.legacy.rsl.ws.individual.update.response.ResponseIndividual;
import com.tmb.oneapp.lendingservice.client.LoanSubmissionGetDropdownListClient;
import com.tmb.oneapp.lendingservice.client.LoanSubmissionUpdateCustomerClient;
import com.tmb.oneapp.lendingservice.constant.ResponseCode;
import com.tmb.oneapp.lendingservice.model.personal.Address;
import com.tmb.oneapp.lendingservice.model.personal.DropDown;
import com.tmb.oneapp.lendingservice.model.personal.PersonalDetailResponse;
import com.tmb.oneapp.lendingservice.model.personal.PersonalDetailSaveInfoRequest;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import javax.xml.rpc.ServiceException;
import java.math.BigDecimal;
import java.rmi.RemoteException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

import static com.tmb.oneapp.lendingservice.service.PersonalDetailService.PATTERN_DATE;

@Service
@AllArgsConstructor
public class PersonalDetailSaveInfoService {
    private static final TMBLogger<PersonalDetailSaveInfoService> logger = new TMBLogger<>(PersonalDetailSaveInfoService.class);
    private final LoanSubmissionUpdateCustomerClient updateCustomerClient;
    private final LoanSubmissionGetDropdownListClient dropdownListClient;
    private final PersonalDetailService personalDetailService;
    static final String DROPDOWN_RESIDENT_TYPE = "RESIDENT_TYP";
    static final String DROPDOWN_SALUTATION_TYPE = "SALUTATION";
    static final String IDEN_PRESENT_BANK_03 = "03";
    static final String IDEN_PRESENT_BANK_02 = "02";
    static final String IDEN_PRESENT_BANK_01 = "01";
    static final String EXPIRE_DATE_DEFAULT = "9000-01-01";
    static final String RESIDENCE = "RESIDENCE";
    static final String YES = "Y";
    static final String NO = "N";

    public PersonalDetailResponse updateCustomerInfo(String crmId, PersonalDetailSaveInfoRequest request) throws ServiceException, TMBCommonException, JsonProcessingException, RemoteException, ParseException {
        RequestIndividual responseIndividual = new RequestIndividual();
        //rsl
        Individual individual = personalDetailService.getCustomer(request.getCaId());
        // ec
        CustGeneralProfileResponse ecResponse = personalDetailService.getCustomerEC(crmId);

        Body body = new Body();

        individual.setAddresses(prepareAddress(individual, request.getAddress()).getAddresses());
        individual.setPersonalInfoSavedFlag(YES);
        individual.setNationality(request.getNationality());
        individual.setMobileNo(request.getMobileNo());
        individual.setThaiName(request.getThaiName());
        individual.setThaiSalutationCode(request.getThaiSalutationCode());
        individual.setThaiSurName(request.getThaiSurname());
        individual.setNameLine1(request.getEngSurName());
        individual.setNameLine2(request.getEngName());
        individual.setEmail(request.getEmail());
        individual.setIdIssueCtry1(request.getIdIssueCtry1());
        individual.setResidentFlag(request.getResidentFlag());
        individual.setExpiryDate(request.getExpiryDate());
        individual.setBirthDate(request.getBirthDate());
        individual.setAccounts(individual.getAccounts());
        individual.setWorkingAddrCopyFrom(RESIDENCE);

        String educationLevel = prepareData(individual.getEducationLevel(), ecResponse.getEducationCode()).toString();
        String maritalStatus = prepareData(individual.getMaritalStatus(), ecResponse.getMaritalStatus()).toString();
        String gender = prepareData(individual.getGender(), ecResponse.getGender()).toString();
        String cusType = prepareData(individual.getCustomerType(), ecResponse.getCustomerType()).toString();
        String sourceFromCountry = prepareData(individual.getSourceFromCountry(), ecResponse.getCountryOfIncome()).toString();
        BigDecimal customerLevel = BigDecimal.valueOf(Long.parseLong(prepareData(individual.getCustomerLevel(), ecResponse.getCustomerLevel()).toString()));

        int customerType = Integer.parseInt(cusType); // customerType
        String idenPresentToBank;
        String lifeTimeFlag;

        if (customerType == 920) {
            idenPresentToBank = IDEN_PRESENT_BANK_03;
        } else if (customerType <= 109 && customerType >= 101) {
            idenPresentToBank = IDEN_PRESENT_BANK_02;
        } else {
            idenPresentToBank = IDEN_PRESENT_BANK_01;
        }

        Calendar cal = Calendar.getInstance();
        DateFormat sdf = new SimpleDateFormat(PATTERN_DATE);
        if (sdf.format(cal.getTime()).equals(EXPIRE_DATE_DEFAULT)) {
            lifeTimeFlag = YES;
        } else {
            lifeTimeFlag = NO;
        }

        individual.setIdenPresentToBank(idenPresentToBank);
        individual.setLifeTimeFlag(lifeTimeFlag);
        individual.setCompanyType("4");

        Calendar year = individual.getBirthDate() == null ? personalDetailService.convertStringToCalender(ecResponse.getIdBirthDate()) : individual.getBirthDate();
        Calendar currentYear = Calendar.getInstance();

        int year1 = year.get(Calendar.YEAR);
        int year2 = currentYear.get(Calendar.YEAR);
        int month1 = year.get(Calendar.MONTH);
        int month2 = currentYear.get(Calendar.MONTH);
        int ageYear = year2 - year1;
        int ageMonth = month2 - month1;

        individual.setIssuedDate(individual.getBirthDate() == null ? personalDetailService.convertStringToCalender(ecResponse.getIdReleasedDate()) : individual.getIssuedDate()); // issuedDate
        individual.setAge(BigDecimal.valueOf(ageYear));
        individual.setAgeMonth(BigDecimal.valueOf(ageMonth));
        individual.setSourceFromCountry(sourceFromCountry); //country_of_income
        individual.setEducationLevel(educationLevel); // education_code
        individual.setMaritalStatus(maritalStatus); // marital_status
        individual.setGender(gender); // gender
        individual.setCustomerType(cusType); //customer_type
        individual.setCustomerLevel(customerLevel); // customer_level

        body.setIndividual(individual);
        responseIndividual.setBody(body);
        return saveCustomer(request.getCaId(), responseIndividual.getBody().getIndividual(), request);
    }

    private PersonalDetailResponse saveCustomer(Long caId, Individual individual, PersonalDetailSaveInfoRequest request) throws ServiceException, TMBCommonException, JsonProcessingException, RemoteException {
        try {
            ResponseIndividual response = updateCustomerClient.updateCustomerInfo(individual);
            PersonalDetailResponse detailResponse = new PersonalDetailResponse();
            Address address = new Address();

            if (response != null) {
                Individual responseIndividual = personalDetailService.getCustomer(caId);

                Optional<com.tmb.common.model.legacy.rsl.common.ob.address.Address> responseAddress = Arrays.stream(responseIndividual.getAddresses()).filter(x -> x.getAddrTypCode().equals("H")).findFirst();
                if (responseAddress.isPresent()) {
                    com.tmb.common.model.legacy.rsl.common.ob.address.Address personalAddress = responseAddress.get();
                    address.setId(personalAddress.getId());
                    address.setNo(personalAddress.getAddress());
                    address.setAmphur(personalAddress.getAmphur());
                    address.setTumbol(personalAddress.getTumbol());
                    address.setProvince(personalAddress.getProvince());
                    address.setRoad(personalAddress.getRoad());
                    address.setStreetName(personalAddress.getStreetName());
                    address.setPostalCode(personalAddress.getPostalCode());
                    address.setMoo(personalAddress.getMoo());
                    address.setFloor(personalAddress.getFloor());
                    address.setCountry(personalAddress.getCountry());
                    address.setBuildingName(personalAddress.getBuildingName());
                }

                detailResponse.setAddress(address);
                detailResponse.setCitizenId(responseIndividual.getIdNo1());
                detailResponse.setThaiName(responseIndividual.getThaiName());
                detailResponse.setThaiSurname(responseIndividual.getThaiSurName());
                detailResponse.setNationality(responseIndividual.getNationality());
                detailResponse.setMobileNo(responseIndividual.getMobileNo());
                detailResponse.setEmail(responseIndividual.getEmail());
                detailResponse.setBirthDate(responseIndividual.getBirthDate());
                detailResponse.setEngName(responseIndividual.getNameLine2());
                detailResponse.setEngSurname(responseIndividual.getNameLine1());
                detailResponse.setExpiryDate(responseIndividual.getExpiryDate());
                detailResponse.setIdIssueCtry1(responseIndividual.getIdIssueCtry1());
                detailResponse.setPrefix(responseIndividual.getThaiSalutationCode());
                if (request.getThaiSalutationCode() != null) {
                    detailResponse.setThaiSalutationCode(prepareDropDown(DROPDOWN_SALUTATION_TYPE, request.getThaiSalutationCode()));
                }

                detailResponse.setResidentFlag(prepareDropDown(DROPDOWN_RESIDENT_TYPE, request.getResidentFlag()));
                return detailResponse;
            } else {
                throw new TMBCommonException(ResponseCode.FAILED.getCode(),
                        ResponseCode.FAILED.getDesc(),
                        ResponseCode.FAILED.getService(), HttpStatus.NOT_FOUND, null);
            }

        } catch (Exception e) {
            logger.error("update customer soap error", e);
            throw e;
        }
    }

    private Individual prepareAddress(Individual individual, Address address) {
        com.tmb.common.model.legacy.rsl.common.ob.address.Address[] individualAddresses = individual.getAddresses();
        if (Objects.nonNull(individualAddresses)) {
            Optional<com.tmb.common.model.legacy.rsl.common.ob.address.Address> oldAddress = Arrays.stream(individualAddresses).filter(x -> x.getAddrTypCode().equals("H")).findFirst();

            var newAddress = new com.tmb.common.model.legacy.rsl.common.ob.address.Address();
            newAddress.setCifId(individual.getCifId());
            newAddress.setAddrTypCode("H");
            newAddress.setAddress(address.getNo());
            newAddress.setBuildingName(address.getBuildingName() + " " + address.getRoomNo());
            newAddress.setFloor(address.getFloor());
            newAddress.setStreetName(address.getStreetName());
            newAddress.setRoad(address.getRoad());
            newAddress.setMoo(address.getMoo());
            newAddress.setTumbol(address.getTumbol());
            newAddress.setAmphur(address.getAmphur());
            newAddress.setProvince(address.getProvince());
            newAddress.setPostalCode(address.getPostalCode());
            newAddress.setCountry(address.getCountry());
            if (individual.getCountryOfRegAddr().equals(YES)) {
                individual.setCountryOfRegAddr(newAddress.getCountry());
            }

            if (oldAddress.isPresent()) {
                com.tmb.common.model.legacy.rsl.common.ob.address.Address workingAddress = oldAddress.get();
                newAddress.setId(workingAddress.getId());
                for (int i = 0; i < individual.getAddresses().length; i++) {
                    if (individual.getAddresses()[i].getAddrTypCode().equals("H")) {
                        individual.getAddresses()[i] = newAddress;
                        break;
                    }
                }
            }
        }
        return individual;
    }

    private CommonCodeEntry[] getDropdownList(String categoryCode) throws ServiceException, TMBCommonException, JsonProcessingException {
        ResponseDropdown getDropdownListResp = dropdownListClient.getDropDownListByCode(categoryCode);
        return getDropdownListResp.getBody().getCommonCodeEntries();
    }

    private List<DropDown> getDropDown(String type) throws ServiceException, TMBCommonException, JsonProcessingException {
        List<DropDown> dropDowns = new ArrayList<>();
        CommonCodeEntry[] entries = getDropdownList(type);
        for (CommonCodeEntry e : entries) {
            DropDown dropDown = new DropDown();
            dropDown.setEntryId(e.getEntryID());
            dropDown.setEntryCode(e.getEntryCode());
            dropDown.setEntryNameEng(e.getEntryName());
            dropDown.setEntryNameTh(e.getEntryName2());
            dropDown.setEntrySource(e.getEntrySource());
            dropDowns.add(dropDown);
        }
        return dropDowns;
    }

    private List<DropDown> prepareDropDown(String type, String flag) throws ServiceException, TMBCommonException, JsonProcessingException {
        List<DropDown> commonCodeEntry = getDropDown(type);
        List<DropDown> filterList = new ArrayList<>();
        commonCodeEntry.forEach(e -> {
            if (flag.equals(e.getEntryCode())) {
                filterList.add(e);
            }
        });

        return filterList;
    }

    public static Object prepareData(Object individual, Object custGeneralProfileResponse) {
        if (individual != null) {
            return individual;
        }
        return custGeneralProfileResponse;
    }


}
