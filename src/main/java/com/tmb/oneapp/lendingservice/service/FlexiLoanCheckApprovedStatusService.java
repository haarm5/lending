package com.tmb.oneapp.lendingservice.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.tmb.common.exception.model.TMBCommonException;
import com.tmb.common.logger.TMBLogger;
import com.tmb.common.model.legacy.rsl.common.ob.apprmemo.facility.ApprovalMemoFacility;
import com.tmb.common.model.legacy.rsl.common.ob.creditcard.CreditCard;
import com.tmb.common.model.legacy.rsl.common.ob.facility.Facility;
import com.tmb.common.model.legacy.rsl.common.ob.pricing.Pricing;
import com.tmb.common.model.legacy.rsl.ws.creditcard.response.ResponseCreditcard;
import com.tmb.common.model.legacy.rsl.ws.facility.response.ResponseFacility;
import com.tmb.common.model.legacy.rsl.ws.instant.calculate.uw.request.Body;
import com.tmb.common.model.legacy.rsl.ws.instant.calculate.uw.request.RequestInstantLoanCalUW;
import com.tmb.common.model.legacy.rsl.ws.instant.calculate.uw.response.ResponseInstantLoanCalUW;
import com.tmb.oneapp.lendingservice.client.LoanSubmissionGetCreditcardInfoClient;
import com.tmb.oneapp.lendingservice.client.LoanSubmissionGetFacilityInfoClient;
import com.tmb.oneapp.lendingservice.client.LoanSubmissionInstantLoanCalUWClient;
import com.tmb.oneapp.lendingservice.constant.ResponseCode;
import com.tmb.oneapp.lendingservice.model.flexiloan.InstantLoanCalUWRequest;
import com.tmb.oneapp.lendingservice.model.flexiloan.InstantLoanCalUWResponse;
import com.tmb.oneapp.lendingservice.model.flexiloan.LoanCustomerPricing;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import javax.xml.rpc.ServiceException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Service
@AllArgsConstructor
public class FlexiLoanCheckApprovedStatusService {
    private static final TMBLogger<FlexiLoanCheckApprovedStatusService> logger = new TMBLogger<>(FlexiLoanCheckApprovedStatusService.class);
    private final LoanSubmissionInstantLoanCalUWClient loanCalUWClient;
    private final LoanSubmissionGetFacilityInfoClient getFacilityInfoClient;
    private final LoanSubmissionGetCreditcardInfoClient getCreditCardInfoClient;
    static final String APPROVE = "APPROVE";
    static final String FLASH = "RC01";
    static final String C2G02 = "C2G02";
    static final String C2G01 = "C2G01";
    private static final List<String> CREDIT_CARD_CODE_LIST = List.of("VJ", "VP", "VM", "VH", "VI", "VB");
    static final String YES = "Y";
    static final String MSG_000 = "MSG_000";

    public InstantLoanCalUWResponse checkCalculateUnderwriting(InstantLoanCalUWRequest request) throws TMBCommonException {

        RequestInstantLoanCalUW requestInstantLoanCalUW = new RequestInstantLoanCalUW();
        Body body = new Body();
        body.setCaId(request.getCaId());
        body.setTriggerFlag(request.getTriggerFlag());
        requestInstantLoanCalUW.setBody(body);


        return calculateUnderwriting(requestInstantLoanCalUW, request);
    }

    private InstantLoanCalUWResponse calculateUnderwriting(RequestInstantLoanCalUW request, InstantLoanCalUWRequest instantLoanCalUWRequest) throws TMBCommonException {
        try {

            ResponseInstantLoanCalUW responseInstantLoanCalUW = loanCalUWClient.calculateUnderwriting(request.getBody().getTriggerFlag(), request.getBody().getCaId());
            Facility facilityInfo = new Facility();
            CreditCard creditCardInfo = new CreditCard();

            if (responseInstantLoanCalUW.getHeader().getResponseCode().equals(MSG_000)) {
                if (CREDIT_CARD_CODE_LIST.contains(instantLoanCalUWRequest.getProduct())) {
                    creditCardInfo = getCreditCard(instantLoanCalUWRequest.getCaId().longValue());
                } else {
                    facilityInfo = getFacility(instantLoanCalUWRequest.getCaId().longValue());
                }
                return parseResponse(facilityInfo, creditCardInfo, responseInstantLoanCalUW, instantLoanCalUWRequest);
            } else {
                throw new TMBCommonException(ResponseCode.FAILED.getCode(),
                        ResponseCode.FAILED.getMessage(),
                        ResponseCode.FAILED.getService(), HttpStatus.NOT_FOUND, null);
            }

        } catch (Exception e) {
            throw new TMBCommonException(ResponseCode.FAILED.getCode(),
                    e.getMessage(),
                    ResponseCode.FAILED.getService(), HttpStatus.NOT_FOUND, null);
        }
    }

    private InstantLoanCalUWResponse parseResponse(Facility facilityInfo,
                                                   CreditCard creditCard,
                                                   ResponseInstantLoanCalUW loanCalUWResponse,
                                                   InstantLoanCalUWRequest request
    ) {
        InstantLoanCalUWResponse response = new InstantLoanCalUWResponse();
        String underWriting = loanCalUWResponse.getBody().getUnderwritingResult();

        response.setStatus(underWriting);
        response.setProduct(request.getProduct());
        response.setLimitApplied(facilityInfo.getLimitApplied());

        setAmount(response, request.getLoanDay1Set(), request.getProduct(), facilityInfo, loanCalUWResponse);

        if (underWriting.equals(APPROVE)) {
            if (request.getProduct().equals(FLASH) && facilityInfo.getPricings() != null) {
                Pricing[] pricings = facilityInfo.getPricings();
                List<LoanCustomerPricing> pricingList = new ArrayList<>();

                for (Pricing p : pricings) {
                    LoanCustomerPricing pricing = new LoanCustomerPricing();
                    if (p.getPricingType().equals("S")) {
                        pricing.setMonthFrom(p.getMonthFrom());
                        pricing.setMonthTo(p.getMonthTo());
                        pricing.setRateVariance(p.getRateVaraince().multiply(BigDecimal.valueOf(100)));
                        pricing.setYearTo(p.getYearTo());
                        pricing.setYearFrom(p.getYearFrom());
                        pricingList.add(pricing);
                    }
                }
                response.setPricings(pricingList);
            }


            if (loanCalUWResponse.getBody().getApprovalMemoFacilities() != null) {
                ApprovalMemoFacility approvalMemoFacility = loanCalUWResponse.getBody().getApprovalMemoFacilities()[0];

                response.setTenor(approvalMemoFacility.getTenor());
                response.setPayDate(approvalMemoFacility.getPayDate());
                response.setInterestRate(approvalMemoFacility.getInterestRate());
                response.setDisburstAccountNo(approvalMemoFacility.getDisburstAccountNo());

                response.setFirstPaymentDueDate(approvalMemoFacility.getFirstPaymentDueDate());
                response.setLoanContractDate(approvalMemoFacility.getLoanContractDate());
                response.setInstallmentAmount(approvalMemoFacility.getInstallmentAmount());
                response.setRateType(approvalMemoFacility.getRateType());
                response.setRateTypePercent(approvalMemoFacility.getRateTypePercent());
                response.setOutStandingBalance(approvalMemoFacility.getOutstandingBalance());
            }

            if (creditCard != null) {
                response.setDisburstAccountNo(creditCard.getDebitAccountNo());
                response.setRequestCreditLimit(creditCard.getRequestCreditLimit());
                // set credit limit
            }
        }

        return response;
    }


    private InstantLoanCalUWResponse setAmount(InstantLoanCalUWResponse response, String loanDay1Set, String product, Facility facility, ResponseInstantLoanCalUW loanCalUWResponse) {

        logger.info("loanDayOneSet: " , loanDay1Set);
        if (loanCalUWResponse.getBody().getApprovalMemoFacilities() != null) {
            response.setCreditLimit(loanCalUWResponse.getBody().getApprovalMemoFacilities()[0].getCreditLimit());
        }
        if (product.equals(FLASH) && facility != null) {
            response.setRequestAmount(facility.getFeature().getRequestAmount());
        } else if ((product.equals(C2G02) || product.equals(C2G01)) && loanCalUWResponse.getBody().getApprovalMemoFacilities() != null) {
            response.setRequestAmount(loanCalUWResponse.getBody().getApprovalMemoFacilities()[0].getOutstandingBalance());
        }
        return response;
    }

    private Facility getFacility(Long caID) throws ServiceException, TMBCommonException, JsonProcessingException {
        ResponseFacility response = getFacilityInfoClient.searchFacilityInfoByCaID(caID);
        return response.getBody().getFacilities() == null ? null : response.getBody().getFacilities()[0];
    }

    private CreditCard getCreditCard(Long caID) throws ServiceException, TMBCommonException, JsonProcessingException {
        ResponseCreditcard response = getCreditCardInfoClient.searchCreditcardInfoByCaID(caID);
        return response.getBody().getCreditCards() == null ? null : response.getBody().getCreditCards()[0];
    }

}
