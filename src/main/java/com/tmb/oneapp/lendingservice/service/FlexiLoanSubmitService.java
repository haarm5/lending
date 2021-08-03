package com.tmb.oneapp.lendingservice.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.tmb.common.exception.model.TMBCommonException;
import com.tmb.common.logger.TMBLogger;
import com.tmb.common.model.legacy.rsl.common.ob.creditcard.CreditCard;
import com.tmb.common.model.legacy.rsl.common.ob.facility.Facility;
import com.tmb.common.model.legacy.rsl.common.ob.individual.Individual;
import com.tmb.common.model.legacy.rsl.common.ob.pricing.Pricing;
import com.tmb.common.model.legacy.rsl.ws.creditcard.response.ResponseCreditcard;
import com.tmb.common.model.legacy.rsl.ws.facility.response.ResponseFacility;
import com.tmb.common.model.legacy.rsl.ws.individual.response.ResponseIndividual;
import com.tmb.oneapp.lendingservice.client.LoanSubmissionGetCreditcardInfoClient;
import com.tmb.oneapp.lendingservice.client.LoanSubmissionGetCustomerInfoClient;
import com.tmb.oneapp.lendingservice.client.LoanSubmissionGetFacilityInfoClient;
import com.tmb.oneapp.lendingservice.constant.ResponseCode;
import com.tmb.oneapp.lendingservice.model.flexiloan.*;
import io.netty.util.internal.StringUtil;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import javax.xml.rpc.ServiceException;
import java.math.BigDecimal;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.List;

@Service
@AllArgsConstructor
public class FlexiLoanSubmitService {
    private static final TMBLogger<FlexiLoanSubmitService> logger = new TMBLogger<>(FlexiLoanSubmitService.class);

    private final LoanSubmissionGetFacilityInfoClient getFacilityInfoClient;
    private final LoanSubmissionGetCustomerInfoClient getCustomerInfoClient;
    private final LoanSubmissionGetCreditcardInfoClient getCreditCardInfoClient;

    private static final List<String> CREDIT_CARD_CODE_LIST = List.of("VJ", "VP", "VM", "VH", "VI", "VB");
    private static final  String MSG_000 = "MSG_000";

    public SubmissionInfoResponse getSubmissionInfo(SubmissionInfoRequest request) throws ServiceException, RemoteException, TMBCommonException, JsonProcessingException {

        Facility facilityInfo = getFacility(request.getCaId());
        Individual customerInfo = getCustomer(request.getCaId());
        CreditCard creditCardInfo = getCreditCard(request.getCaId());
        return parseSubmissionInfoResponse(request.getProductCode(), facilityInfo, customerInfo, creditCardInfo);
    }

    private SubmissionInfoResponse parseSubmissionInfoResponse(String productCode,
                                                               Facility facilityInfo,
                                                               Individual customerInfo,
                                                               CreditCard creditCardInfo) {
        SubmissionInfoResponse response = new SubmissionInfoResponse();

        SubmissionCustomerInfo customer = new SubmissionCustomerInfo();
        customer.setName(customerInfo == null ? null : String.format("%s %s", customerInfo.getThaiName(), customerInfo.getThaiSurName()));
        customer.setCitizenId(customerInfo == null ? null : customerInfo.getIdNo1());

        SubmissionPaymentInfo payment = new SubmissionPaymentInfo();
        SubmissionReceivingInfo receiving = new SubmissionReceivingInfo();
        SubmissionPricingInfo pricingInfo = new SubmissionPricingInfo();
        List<LoanCustomerPricing> pricingList = new ArrayList<>();
        if (facilityInfo != null) {
            LoanCustomerPricing customerPricing = new LoanCustomerPricing();
            for (Pricing p : facilityInfo.getPricings()) {
                if (p.getPricingType().equals("S")) {
                    customerPricing.setMonthFrom(p.getMonthFrom());
                    customerPricing.setMonthTo(p.getMonthTo());
                    customerPricing.setRateVariance(p.getRateVaraince().multiply(BigDecimal.valueOf(100)));
                    customerPricing.setRate(parseRate(p));
                    customerPricing.setYearTo(p.getYearTo());
                    customerPricing.setYearFrom(p.getYearFrom());
                    pricingList.add(customerPricing);
                }
            }
            pricingInfo.setPricing(pricingList);
            payment.setFeatureType(facilityInfo.getFeatureType());
            payment.setPaymentMethod(setPaymentMethod(productCode, facilityInfo, creditCardInfo));
            payment.setOtherBank(facilityInfo.getLoanWithOtherBank());
            payment.setOtherBankInProgress(facilityInfo.getConsiderLoanWithOtherBank());

            receiving.setOsLimit(facilityInfo.getOsLimit());
            receiving.setHostAcfNo(facilityInfo.getHostAcfNo());
            receiving.setDisburseAccount(String.format("TMB%s", facilityInfo.getFeature().getDisbAcctNo()));

            response.setTenure(facilityInfo.getTenure());
        }

        payment.setEStatement(customerInfo == null ? null : customerInfo.getEmail());

        response.setCustomerInfo(customer);
        response.setPricingInfo(pricingInfo);
        response.setReceivingInfo(receiving);
        response.setSubmissionInfo(payment);

        return response;
    }

    private Facility getFacility(Long caID) throws ServiceException, TMBCommonException, JsonProcessingException {
        try {
            ResponseFacility response = getFacilityInfoClient.searchFacilityInfoByCaID(caID);
            if (response.getHeader().getResponseCode().equals(MSG_000)) {
                return response.getBody().getFacilities() == null ? null : response.getBody().getFacilities()[0];
            } else {
                throw new TMBCommonException(response.getHeader().getResponseCode(),
                        response.getHeader().getResponseDescriptionEN(),
                        ResponseCode.FAILED.getService(), HttpStatus.NOT_FOUND, null);
            }
        }catch (Exception e) {
            logger.error("get facility soap error",e);
            throw e;
        }
    }

    private Individual getCustomer(Long caID) throws ServiceException, RemoteException, TMBCommonException, JsonProcessingException {
        try {
            ResponseIndividual response = getCustomerInfoClient.searchCustomerInfoByCaID(caID);
            if (response.getHeader().getResponseCode().equals(MSG_000)) {
                return response.getBody().getIndividuals() == null ? null : response.getBody().getIndividuals()[0];
            } else {
                throw new TMBCommonException(response.getHeader().getResponseCode(),
                        response.getHeader().getResponseDescriptionEN(),
                        ResponseCode.FAILED.getService(), HttpStatus.NOT_FOUND, null);
            }
        }catch (Exception e) {
            logger.error("get customer soap error",e);
            throw e;
        }
    }

    private CreditCard getCreditCard(Long caID) throws ServiceException, TMBCommonException, JsonProcessingException {
        ResponseCreditcard response = getCreditCardInfoClient.searchCreditcardInfoByCaID(caID);
        return response.getBody().getCreditCards() == null ? null : response.getBody().getCreditCards()[0];
    }

    private String parseRate(Pricing pricing) {
        if (StringUtil.isNullOrEmpty(pricing.getRateType())) {
            return String.format("%.2f", pricing.getRateVaraince().multiply(BigDecimal.valueOf(100)));
        } else {
            return String.format("%s %s %.2f", pricing.getRateType(), pricing.getPercentSign(), pricing.getRateVaraince().multiply(BigDecimal.valueOf(100)));
        }

    }

    private String setPaymentMethod(String productCode, Facility facilityInfo, CreditCard creditCardInfo) {
        if(CREDIT_CARD_CODE_LIST.contains(productCode)) {
            return creditCardInfo == null ? null : creditCardInfo.getPaymentMethod();
        }
        return facilityInfo == null ? null : facilityInfo.getPaymentMethod();
    }

}
