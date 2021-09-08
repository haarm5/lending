package com.tmb.oneapp.lendingservice.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.tmb.common.exception.model.TMBCommonException;
import com.tmb.common.logger.TMBLogger;
import com.tmb.common.model.legacy.rsl.common.ob.creditcard.CreditCard;
import com.tmb.common.model.legacy.rsl.common.ob.facility.Facility;
import com.tmb.common.model.legacy.rsl.common.ob.individual.Individual;
import com.tmb.common.model.legacy.rsl.ws.application.response.ResponseApplication;
import com.tmb.common.model.legacy.rsl.ws.creditcard.response.ResponseCreditcard;
import com.tmb.common.model.legacy.rsl.ws.facility.response.ResponseFacility;
import com.tmb.common.model.legacy.rsl.ws.individual.response.ResponseIndividual;
import com.tmb.oneapp.lendingservice.client.LoanSubmissionGetApplicationInfoClient;
import com.tmb.oneapp.lendingservice.client.LoanSubmissionGetCreditcardInfoClient;
import com.tmb.oneapp.lendingservice.client.LoanSubmissionGetCustomerInfoClient;
import com.tmb.oneapp.lendingservice.client.LoanSubmissionGetFacilityInfoClient;
import com.tmb.oneapp.lendingservice.constant.ResponseCode;
import com.tmb.oneapp.lendingservice.model.flexiloan.LoanCalculatorResponse;
import com.tmb.oneapp.lendingservice.model.loanonline.LoanCustomerDisburstAccount;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import javax.xml.rpc.ServiceException;
import java.rmi.RemoteException;
import java.util.Objects;

@Service
@AllArgsConstructor
public class LoanCalculatorService {
    private static final TMBLogger<LoanCalculatorService> logger = new TMBLogger<>(LoanCalculatorService.class);
    private final LoanSubmissionGetFacilityInfoClient getFacilityInfoClient;
    private final LoanSubmissionGetCreditcardInfoClient getCreditCardInfoClient;
    private final LoanSubmissionGetCustomerInfoClient getCustomerInfoClient;
    private final LoanSubmissionGetApplicationInfoClient getApplicationInfoClient;
    private static final String CREDIT_CARD = "CC";
    static final String MSG_000 = "MSG_000";

    public LoanCalculatorResponse getPreloadLoanCalculator(Long caId, String product) throws ServiceException, TMBCommonException, JsonProcessingException, RemoteException {
        LoanCalculatorResponse calculatorResponse = new LoanCalculatorResponse();
        LoanCustomerDisburstAccount paymentAccount = new LoanCustomerDisburstAccount();
        LoanCustomerDisburstAccount receiveAccount = new LoanCustomerDisburstAccount();
        Facility facility = Objects.requireNonNull(getFacility(caId))[0];
        CreditCard creditCard = Objects.requireNonNull(getCreditCard(caId))[0];
        Individual individual = Objects.requireNonNull(getCustomer(caId))[0];
        String natureOfReq = Objects.requireNonNull(getApplicationInfo(caId)).getBody().getNatureOfRequest();

        if (!product.equals(CREDIT_CARD) && facility != null) {
            if (natureOfReq.equals("12")) {
                calculatorResponse.setIsWaiveDoc(true);
            } else if (natureOfReq.equals("11")) {
                calculatorResponse.setIsWaiveDoc(false);
            }

            receiveAccount.setAccountNo(facility.getDisburstAccountNo());
            receiveAccount.setAccountName(facility.getDisburstAccountName());

            paymentAccount.setAccountName(facility.getPaymentAccountName());
            paymentAccount.setAccountNo(facility.getPaymentAccountNo());

            calculatorResponse.setReceiveAccount(receiveAccount);
            calculatorResponse.setPaymentAccount(paymentAccount);
        }

        if (product.equals(CREDIT_CARD) && creditCard != null) {
            if (natureOfReq.equals("04")) {
                calculatorResponse.setIsWaiveDoc(true);
            } else if (natureOfReq.equals("03")) {
                calculatorResponse.setIsWaiveDoc(false);
            }
            receiveAccount.setAccountNo(creditCard.getDebitAccountNo());
            receiveAccount.setAccountName(creditCard.getDebitAccountName());

            calculatorResponse.setReceiveAccount(receiveAccount);
            calculatorResponse.setPaymentAccount(paymentAccount);
        }

        if (individual != null) {
            calculatorResponse.setEmploymentStatus(individual.getEmploymentStatus());
            calculatorResponse.setIncomeBasicSalary(individual.getIncomeBasicSalary());
            calculatorResponse.setIncomeDeclared(individual.getIncomeDeclared());
            calculatorResponse.setIncomeOtherIncome(individual.getIncomeOtherIncome());
        }

        return calculatorResponse;
    }

    private Facility[] getFacility(Long caId) throws ServiceException, TMBCommonException, JsonProcessingException {

        try {
            ResponseFacility response = getFacilityInfoClient.searchFacilityInfoByCaID(caId);
            if (response.getHeader().getResponseCode().equals(MSG_000)) {
                return response.getBody().getFacilities() == null ? null : response.getBody().getFacilities();
            } else {
                throw new TMBCommonException(response.getHeader().getResponseCode(),
                        response.getHeader().getResponseDescriptionEN(),
                        ResponseCode.FAILED.getService(), HttpStatus.NOT_FOUND, null);
            }
        } catch (Exception e) {
            logger.error("preload loan calculator => get facility soap error", e);
            throw e;
        }
    }

    private CreditCard[] getCreditCard(Long caId) throws ServiceException, TMBCommonException, JsonProcessingException {
        try {
            ResponseCreditcard response = getCreditCardInfoClient.searchCreditcardInfoByCaID(caId);
            if (response.getHeader().getResponseCode().equals(MSG_000)) {
                return response.getBody().getCreditCards() == null ? null : response.getBody().getCreditCards();
            } else {
                throw new TMBCommonException(response.getHeader().getResponseCode(),
                        response.getHeader().getResponseDescriptionEN(),
                        ResponseCode.FAILED.getService(), HttpStatus.NOT_FOUND, null);
            }
        } catch (Exception e) {
            logger.error("preload loan calculator => get credit card soap error", e);
            throw e;
        }
    }

    private Individual[] getCustomer(Long caId) throws ServiceException, TMBCommonException, JsonProcessingException, RemoteException {
        try {
            ResponseIndividual response = getCustomerInfoClient.searchCustomerInfoByCaID(caId);
            if (response.getHeader().getResponseCode().equals(MSG_000)) {
                return response.getBody().getIndividuals() == null ? null : response.getBody().getIndividuals();
            } else {
                throw new TMBCommonException(response.getHeader().getResponseCode(),
                        response.getHeader().getResponseDescriptionEN(),
                        ResponseCode.FAILED.getService(), HttpStatus.NOT_FOUND, null);
            }
        } catch (Exception e) {
            logger.error("preload loan calculator => get customer card soap error", e);
            throw e;
        }
    }

    private ResponseApplication getApplicationInfo(Long caId) throws ServiceException, TMBCommonException, JsonProcessingException {
        try {
            ResponseApplication response = getApplicationInfoClient.searchApplicationInfoByCaID(caId);
            if (response.getHeader().getResponseCode().equals(MSG_000)) {
                return response.getBody() == null ? null : response;
            } else {
                throw new TMBCommonException(response.getHeader().getResponseCode(),
                        response.getHeader().getResponseDescriptionEN(),
                        ResponseCode.FAILED.getService(), HttpStatus.NOT_FOUND, null);
            }
        } catch (Exception e) {
            logger.error("preload loan calculator => get application soap error", e);
            throw e;
        }
    }
}
