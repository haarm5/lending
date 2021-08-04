package com.tmb.oneapp.lendingservice.service;

import com.tmb.common.exception.model.TMBCommonException;
import com.tmb.common.model.legacy.rsl.common.ob.apprmemo.facility.ApprovalMemoFacility;
import com.tmb.common.model.legacy.rsl.common.ob.pricing.Pricing;
import com.tmb.common.model.legacy.rsl.ws.facility.response.ResponseFacility;
import com.tmb.common.model.legacy.rsl.ws.instant.calculate.uw.request.Body;
import com.tmb.common.model.legacy.rsl.ws.instant.calculate.uw.request.RequestInstantLoanCalUW;
import com.tmb.common.model.legacy.rsl.ws.instant.calculate.uw.response.ResponseInstantLoanCalUW;
import com.tmb.oneapp.lendingservice.client.LoanSubmissionGetFacilityInfoClient;
import com.tmb.oneapp.lendingservice.client.LoanSubmissionInstantLoanCalUWClient;
import com.tmb.oneapp.lendingservice.constant.ResponseCode;
import com.tmb.oneapp.lendingservice.model.flexiloan.InstantLoanCalUWRequest;
import com.tmb.oneapp.lendingservice.model.flexiloan.InstantLoanCalUWResponse;
import com.tmb.oneapp.lendingservice.model.flexiloan.LoanCustomerPricing;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Service
@AllArgsConstructor
public class FlexiLoanCheckApprovedStatusService {
    private final LoanSubmissionInstantLoanCalUWClient loanCalUWClient;
    private final LoanSubmissionGetFacilityInfoClient getFacilityInfoClient;
    static final String APPROVE = "APPROVE";
    static final String FLASH = "RC01";
    static final String C2G02 = "C2G02";
    static final  String MSG_000 = "MSG_000";

    public InstantLoanCalUWResponse checkCalculateUnderwriting(InstantLoanCalUWRequest request) throws TMBCommonException {

        RequestInstantLoanCalUW requestInstantLoanCalUW = new RequestInstantLoanCalUW();
        Body body = new Body();
        body.setCaId(request.getCaId());
        body.setTriggerFlag(request.getTriggerFlag());
        requestInstantLoanCalUW.setBody(body);


        return calculateUnderwriting(requestInstantLoanCalUW, request.getProduct());
    }

    private InstantLoanCalUWResponse calculateUnderwriting(RequestInstantLoanCalUW request, String productCode) throws TMBCommonException {
        try {

            ResponseInstantLoanCalUW responseInstantLoanCalUW = loanCalUWClient.calculateUnderwriting(request.getBody().getTriggerFlag(), request.getBody().getCaId());
            ResponseFacility facilityInfo = new ResponseFacility();

            if (responseInstantLoanCalUW.getHeader().getResponseCode().equals(MSG_000)) {
                if (productCode.equals(FLASH)) {
                    facilityInfo = getFacilityInfoClient.searchFacilityInfoByCaID(request.getBody().getCaId().longValue());
                }
                return parseResponse(facilityInfo, responseInstantLoanCalUW, productCode);
            }else {
                throw new TMBCommonException(ResponseCode.FAILED.getCode(),
                        ResponseCode.FAILED.getMessage(),
                        ResponseCode.FAILED.getService(), HttpStatus.NOT_FOUND, null);
            }

        }catch (Exception e) {
            throw new TMBCommonException(ResponseCode.FAILED.getCode(),
                    e.getMessage(),
                    ResponseCode.FAILED.getService(), HttpStatus.NOT_FOUND, null);
        }
    }
    private InstantLoanCalUWResponse parseResponse(ResponseFacility facilityInfo,
                                                   ResponseInstantLoanCalUW loanCalUWResponse,
                                                   String productCode
    ) {
        InstantLoanCalUWResponse response = new InstantLoanCalUWResponse();
        String underWriting = loanCalUWResponse.getBody().getUnderwritingResult();

        response.setStatus(underWriting);
        response.setProduct(productCode);

        if (underWriting.equals(APPROVE)) {
            if (productCode.equals(FLASH) && facilityInfo.getBody().getFacilities() != null) {
                response.setRequestAmount(facilityInfo.getBody().getFacilities()[0].getFeature().getRequestAmount());
                Pricing[] pricings = facilityInfo.getBody().getFacilities()[0].getPricings();
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


            }else if (productCode.equals(C2G02) && loanCalUWResponse.getBody().getApprovalMemoFacilities() != null){
                response.setRequestAmount(loanCalUWResponse.getBody().getApprovalMemoFacilities()[0].getOutstandingBalance());
            }


            if(loanCalUWResponse.getBody().getApprovalMemoFacilities()!=null){
                ApprovalMemoFacility approvalMemoFacility = loanCalUWResponse.getBody().getApprovalMemoFacilities()[0];

                response.setTenor(approvalMemoFacility.getTenor());
                response.setPayDate(approvalMemoFacility.getPayDate());
                response.setInterestRate(approvalMemoFacility.getInterestRate());
                response.setDisburstAccountNo(approvalMemoFacility.getDisburstAccountNo());
                response.setCreditLimit(approvalMemoFacility.getCreditLimit());

                response.setFirstPaymentDueDate(approvalMemoFacility.getFirstPaymentDueDate());
                response.setLoanContractDate(approvalMemoFacility.getLoanContractDate());
                response.setInstallmentAmount(approvalMemoFacility.getInstallmentAmount());
                response.setRateType(approvalMemoFacility.getRateType());
                response.setRateTypePercent(approvalMemoFacility.getRateTypePercent());
            }
        }

        return response;
    }
}