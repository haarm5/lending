package com.tmb.oneapp.lendingservice.service;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.tmb.common.exception.model.TMBCommonException;
import com.tmb.common.logger.TMBLogger;
import com.tmb.common.model.CustGeneralProfileResponse;
import com.tmb.common.model.legacy.rsl.common.ob.creditcard.CreditCard;
import com.tmb.common.model.legacy.rsl.common.ob.facility.Facility;
import com.tmb.common.model.legacy.rsl.common.ob.individual.Individual;
import com.tmb.common.model.legacy.rsl.ws.application.response.Body;
import com.tmb.common.model.legacy.rsl.ws.application.response.ResponseApplication;
import com.tmb.common.model.legacy.rsl.ws.creditcard.response.ResponseCreditcard;
import com.tmb.common.model.legacy.rsl.ws.facility.response.ResponseFacility;
import com.tmb.common.model.legacy.rsl.ws.individual.response.ResponseIndividual;
import com.tmb.oneapp.lendingservice.model.loanonline.LoanSubmissionCreateApplicationReq;
import com.tmb.oneapp.lendingservice.model.rsl.LoanSubmissionGetApplicationInfoRequest;
import com.tmb.oneapp.lendingservice.model.rsl.LoanSubmissionGetCreditcardInfoRequest;
import com.tmb.oneapp.lendingservice.model.rsl.LoanSubmissionGetCustomerInfoRequest;
import com.tmb.oneapp.lendingservice.model.rsl.LoanSubmissionGetFacilityInfoRequest;
import com.tmb.oneapp.lendingservice.util.RslServiceUtils;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import javax.xml.rpc.ServiceException;
import java.math.BigDecimal;
import java.rmi.RemoteException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Objects;

@Service
@AllArgsConstructor
public class LoanOnlineSubmissionUpdateApplicationService {
    private static final TMBLogger<LoanOnlineSubmissionUpdateApplicationService> logger = new TMBLogger<>(LoanOnlineSubmissionUpdateApplicationService.class);

    private final RslService rslService;
    private final LoanOnlineSubmissionGetPersonalDetailService loanOnlineSubmissionGetPersonalDetailService;

    static String CRM_ID;

    public Object updateApplication(LoanSubmissionCreateApplicationReq request, String crmId) throws ServiceException, TMBCommonException, RemoteException, JsonProcessingException, ParseException {

        try {
            CRM_ID = crmId;
            var result = updateIndividual(mapIndividual(getCustomerInfo(request.getCaId()), request));
            Body applicationInfo = getApplicationInfo(request.getCaId());
            if (applicationInfo.getAppType().equals("CC")) {
                updateCreditCard(mapCreditCard(getCreditCard(request.getCaId()), request));
            } else {
                updateFacility(mapFacility(getFacility(request.getCaId()), request));
            }
            return result;
        } catch (Exception e) {
            logger.error("update application service error", e);
            throw e;
        }
    }

    private Individual mapIndividual(Individual individual, LoanSubmissionCreateApplicationReq req) throws TMBCommonException, ParseException {
        mapHiddenField(individual);
        individual.setEmploymentStatus(req.getEmploymentStatus());
        individual.setIncomeBasicSalary(req.getIncomeBasicSalary());
        individual.setInTotalIncome(req.getInTotalIncome());
        individual.setIncomeOtherIncome(req.getIncomeOtherIncome());
        individual.setIncomeDeclared(req.getIncomeDeclared());
        individual.setEmploymentFinalTotalIncome(req.getIncomeBasicSalary());
        return individual;
    }

    private Individual mapHiddenField(Individual individual) throws TMBCommonException, ParseException {
        CustGeneralProfileResponse ecResponse = loanOnlineSubmissionGetPersonalDetailService.getCustomerEC(CRM_ID);

        individual.setIdenPresentToBank(getPresentToBank(prepareField(individual.getCustomerType(), ecResponse.getCustomerType())));
        individual.setCustomerLevel(getCustomerLevel(prepareField(individual.getCustomerLevel(),ecResponse.getCustomerLevel())));
        List<BigDecimal> age = getAge(individual, ecResponse);
        individual.setAge(age.get(0));
        individual.setAgeMonth(age.get(1));
        individual.setNationality(ecResponse.getNationality());
        individual.setCompanyType("4");
        individual.setEmploymentYear("0");
        individual.setEmploymentMonth("0");
        individual.setIncomeType("2");
        individual.setIncomeBankName("011");

        return individual;
    }

    private String getPresentToBank(String code) {
        if (Objects.nonNull(code) && !code.isEmpty()) {
            if (Integer.parseInt(code) == 920) {
                return "03";
            } else if (Integer.parseInt(code) <= 109 && Integer.parseInt(code) >= 101) {
                return "02";
            }
        }
        return "01";
    }

    private BigDecimal getCustomerLevel(String code) {
        if (code.length() < 2) {
            return BigDecimal.valueOf(Integer.parseInt(String.valueOf(code.charAt(0))));
        }
        return BigDecimal.valueOf(Integer.parseInt(String.valueOf(code.charAt(1))));
    }


    //response position0 = year ,position1 = month
    private List<BigDecimal> getAge(Individual individual, CustGeneralProfileResponse ecResponse) throws ParseException {
        Calendar year = individual.getBirthDate() == null ? loanOnlineSubmissionGetPersonalDetailService.convertStringToCalender(ecResponse.getIdBirthDate()) : individual.getBirthDate();
        int ageYear = Calendar.getInstance().get(Calendar.YEAR) - year.get(Calendar.YEAR);
        int ageMonth = Calendar.getInstance().get(Calendar.MONTH) - year.get(Calendar.MONTH);
        List<BigDecimal> age = new ArrayList<>();
        age.add(BigDecimal.valueOf(ageYear));
        age.add(BigDecimal.valueOf(ageMonth));
        return age;
    }



    private String prepareField(Object fromIndividual, Object fromEC) {
        if (Objects.nonNull(fromIndividual) && !fromIndividual.toString().isEmpty()) {
            return fromIndividual.toString();
        }
        return fromEC.toString();
    }

    private Facility mapFacility(Facility facility, LoanSubmissionCreateApplicationReq req) {
        facility.setCaCampaignCode(req.getCampaignCode());
        facility.setLimitApplied(req.getLimitApplied());
        facility.setTenure(req.getTenure());
        facility.setDisburstBankName(req.getDisburstBankName());
        facility.setDisburstAccountNo(req.getDisburstAccountNo());
        facility.setDisburstAccountName(req.getDisburstAccountName());
        facility.setPaymentMethod(req.getPaymentMethod());
        facility.setPaymentAccountNo(req.getPaymentAccountNo());
        facility.setPaymentAccountName(req.getPaymentAccountName());
        facility.setPayMethodCriteria(req.getPayMethodCriteria());
        facility.setLoanWithOtherBank(req.getLoanWithOtherBank());
        facility.setConsiderLoanWithOtherBank(req.getConsiderLoanWithOtherBank());
        return facility;
    }

    private CreditCard mapCreditCard(CreditCard creditCard, LoanSubmissionCreateApplicationReq req) {
        creditCard.setCardInd(req.getCardInd());
        creditCard.setProductType(req.getProductType());
        creditCard.setCardBrand(req.getCardBrand());
        creditCard.setCampaignCode(req.getCampaignCode());
        creditCard.setPaymentMethod(req.getPaymentMethod());
        creditCard.setDebitAccountNo(req.getDebitAccountNo());
        creditCard.setDebitAccountName(req.getDebitAccountName());
        creditCard.setPaymentCriteria(req.getPaymentCriteria());
        return creditCard;
    }


    private Body getApplicationInfo(long caId) throws ServiceException, TMBCommonException, JsonProcessingException {
        LoanSubmissionGetApplicationInfoRequest req = new LoanSubmissionGetApplicationInfoRequest();
        req.setCaId(String.valueOf(caId));
        ResponseApplication response = rslService.getLoanSubmissionApplicationInfo(req);
        return response.getBody();
    }

    private Individual getCustomerInfo(long caId) throws ServiceException, TMBCommonException, RemoteException, JsonProcessingException {
        LoanSubmissionGetCustomerInfoRequest req = new LoanSubmissionGetCustomerInfoRequest();
        req.setCaId(String.valueOf(caId));
        ResponseIndividual response = rslService.getLoanSubmissionCustomerInfo(req);
        return response.getBody().getIndividuals()[0];
    }

    private Facility getFacility(long caId) throws ServiceException, TMBCommonException, JsonProcessingException {
        LoanSubmissionGetFacilityInfoRequest req = new LoanSubmissionGetFacilityInfoRequest();
        req.setCaId(String.valueOf(caId));
        ResponseFacility response = rslService.getLoanSubmissionFacilityInfo(req);
        return response.getBody().getFacilities()[0];
    }

    private CreditCard getCreditCard(long caId) throws ServiceException, TMBCommonException, JsonProcessingException {
        LoanSubmissionGetCreditcardInfoRequest req = new LoanSubmissionGetCreditcardInfoRequest();
        req.setCaId(String.valueOf(caId));
        ResponseCreditcard response = rslService.getLoanSubmissionCreditCardInfo(req);
        RslServiceUtils.checkRslResponse(response.getHeader().getResponseCode(), response.getHeader().getResponseDescriptionEN());
        return response.getBody().getCreditCards()[0];
    }

    private void updateFacility(Facility facility) throws ServiceException, TMBCommonException, JsonProcessingException {
        try {
            rslService.updateFacilityInfo(facility);
        } catch (Exception e) {
            logger.error("update application service => update facility soap error", e);
            throw e;
        }
    }

    private void updateCreditCard(CreditCard creditCard) throws ServiceException, TMBCommonException, JsonProcessingException {
        try {
            rslService.updateCreditCardInfo(creditCard);
        } catch (Exception e) {
            logger.error("update application service => update credit card soap error", e);
            throw e;
        }
    }

    private com.tmb.common.model.legacy.rsl.ws.individual.update.response.ResponseIndividual updateIndividual(Individual individual) throws ServiceException, TMBCommonException, JsonProcessingException {
        try {
            return rslService.updateCustomerInfo(individual);
        } catch (Exception e) {
            logger.error("update application service => update customer soap error", e);
            throw e;
        }
    }
}
