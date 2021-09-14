package com.tmb.oneapp.lendingservice.service;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.tmb.common.exception.model.TMBCommonException;
import com.tmb.common.logger.TMBLogger;
import com.tmb.common.model.legacy.rsl.common.ob.creditcard.CreditCard;
import com.tmb.common.model.legacy.rsl.common.ob.facility.Facility;
import com.tmb.common.model.legacy.rsl.common.ob.individual.Individual;
import com.tmb.common.model.legacy.rsl.ws.application.response.Body;
import com.tmb.common.model.legacy.rsl.ws.application.response.ResponseApplication;
import com.tmb.common.model.legacy.rsl.ws.creditcard.response.ResponseCreditcard;
import com.tmb.common.model.legacy.rsl.ws.facility.response.ResponseFacility;
import com.tmb.common.model.legacy.rsl.ws.individual.response.ResponseIndividual;
import com.tmb.oneapp.lendingservice.client.*;
import com.tmb.oneapp.lendingservice.model.loanonline.LoanSubmissionCreateApplicationReq;
import com.tmb.oneapp.lendingservice.util.RslServiceUtils;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import javax.xml.rpc.ServiceException;
import java.rmi.RemoteException;

@Service
@AllArgsConstructor
public class LoanOnlineSubmissionUpdateApplicationService {
    private static final TMBLogger<LoanOnlineSubmissionUpdateApplicationService> logger = new TMBLogger<>(LoanOnlineSubmissionUpdateApplicationService.class);
    private final LoanSubmissionGetApplicationInfoClient loanSubmissionGetApplicationInfoClient;
    private final LoanSubmissionGetCustomerInfoClient loanSubmissionGetCustomerInfoClient;
    private final LoanSubmissionUpdateCustomerClient loanSubmissionUpdateCustomerClient;
    private final LoanSubmissionGetFacilityInfoClient loanSubmissionGetFacilityInfoClient;
    private final LoanSubmissionUpdateFacilityInfoClient loanSubmissionUpdateFacilityInfoClient;
    private final LoanSubmissionGetCreditcardInfoClient loanSubmissionGetCreditcardInfoClient;
    private final LoanSubmissionUpdateCreditCardClient loanSubmissionUpdateCreditCardClient;

    public Object updateApplication(LoanSubmissionCreateApplicationReq request) throws ServiceException, TMBCommonException, RemoteException, JsonProcessingException {

        try {
            Body applicationInfo = getApplicationInfo(request.getCaId());
            if (applicationInfo.getAppType().equals("CC")) {
                updateCreditCard(mapCreditCard(getCreditCard(request.getCaId()), request));
            } else {
                updateFacility(mapFacility(getFacility(request.getCaId()), request));
            }
            return updateIndividual(mapIndividual(getCustomerInfo(request.getCaId()), request));
        } catch (Exception e) {
            logger.error("update application service error", e);
            throw e;
        }
    }

    private Individual mapIndividual(Individual individual, LoanSubmissionCreateApplicationReq req) {
        individual.setEmploymentStatus(req.getEmploymentStatus());
        individual.setIncomeBasicSalary(req.getIncomeBasicSalary());
        individual.setInTotalIncome(req.getInTotalIncome());
        individual.setIncomeOtherIncome(req.getIncomeOtherIncome());
        individual.setIncomeDeclared(req.getIncomeDeclared());
        individual.setEmploymentFinalTotalIncome(req.getIncomeBasicSalary());
        return individual;
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

    private void updateFacility(Facility facility) throws ServiceException, TMBCommonException, JsonProcessingException {
        try {
            com.tmb.common.model.legacy.rsl.ws.facility.update.response.ResponseFacility response = loanSubmissionUpdateFacilityInfoClient.updateFacilityInfo(facility);
            RslServiceUtils.checkRslResponse(response.getHeader().getResponseCode(), response.getHeader().getResponseDescriptionEN());
        } catch (Exception e) {
            logger.error("update application service => update facility soap error", e);
            throw e;
        }
    }

    private void updateCreditCard(CreditCard creditCard) throws ServiceException, TMBCommonException, JsonProcessingException {
        try {
            com.tmb.common.model.legacy.rsl.ws.creditcard.update.response.ResponseCreditcard response = loanSubmissionUpdateCreditCardClient.updateCreditCard(creditCard);
            RslServiceUtils.checkRslResponse(response.getHeader().getResponseCode(), response.getHeader().getResponseDescriptionEN());
        } catch (Exception e) {
            logger.error("update application service => update credit card soap error", e);
            throw e;
        }
    }

    private com.tmb.common.model.legacy.rsl.ws.individual.update.response.ResponseIndividual updateIndividual(Individual individual) throws ServiceException, TMBCommonException, JsonProcessingException {
        try {
            com.tmb.common.model.legacy.rsl.ws.individual.update.response.ResponseIndividual response = loanSubmissionUpdateCustomerClient.updateCustomerInfo(individual);
            RslServiceUtils.checkRslResponse(response.getHeader().getResponseCode(), response.getHeader().getResponseDescriptionEN());
            return response;
        } catch (Exception e) {
            logger.error("update application service => update customer soap error", e);
            throw e;
        }
    }
}
