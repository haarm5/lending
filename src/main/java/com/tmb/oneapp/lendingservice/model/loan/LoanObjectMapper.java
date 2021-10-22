package com.tmb.oneapp.lendingservice.model.loan;

import com.tmb.common.model.legacy.rsl.common.ob.creditcard.InstantCreditCard;
import com.tmb.common.model.legacy.rsl.common.ob.dropdown.CommonCodeEntry;
import com.tmb.common.model.legacy.rsl.common.ob.facility.InstantFacility;
import com.tmb.common.model.legacy.rsl.ws.instant.eligible.product.response.ResponseInstantLoanGetEligibleProduct;
import com.tmb.common.model.legacy.rsl.ws.tracking.response.*;
import com.tmb.oneapp.lendingservice.constant.LoanCategory;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Provides functionalities to map response from bank's flexi loan services to OneApp
 */
public class LoanObjectMapper {

    private OneAppApplicant[] toOneApp(Applicant[] applicants) {
        if (applicants == null) return new OneAppApplicant[0];
        return Arrays.stream(applicants).map(applicant -> {
            OneAppApplicant oneAppApplicant = new OneAppApplicant();
            oneAppApplicant.setApplicantType(applicant.getApplicantType());
            oneAppApplicant.setFirstNameEN(applicant.getFirstNameEN());
            oneAppApplicant.setFirstNameTH(applicant.getFirstNameTH());
            oneAppApplicant.setSurNameEN(applicant.getSurNameEN());
            oneAppApplicant.setSurNameTH(applicant.getSurNameTH());
            oneAppApplicant.setTitleEN(applicant.getTitleEN());
            oneAppApplicant.setTitleTH(applicant.getTitleTH());
            OneAppProduct[] oneAppProducts = toOneApp(applicant.getProducts());
            oneAppApplicant.setProducts(oneAppProducts);
            return oneAppApplicant;
        }).toArray(OneAppApplicant[]::new);

    }

    private OneAppProduct[] toOneApp(Product[] products) {
        if (products == null) return new OneAppProduct[0];

        return Arrays.stream(products).map(product -> {
            OneAppProduct oneAppProduct = new OneAppProduct();
            oneAppProduct.setApproveCreditLimit(product.getApproveCreditLimit());
            oneAppProduct.setBankAccountNumber(product.getBankAccountNumber());
            oneAppProduct.setCardName(product.getCardName());
            oneAppProduct.setDisburseBankCode(product.getDisburseBankCode());
            oneAppProduct.setDisburseBankFullEN(product.getDisburseBankFullEN());
            oneAppProduct.setDisburseBankFullTH(product.getDisburseBankFullTH());
            oneAppProduct.setDisburseBankShort(product.getDisburseBankShort());
            oneAppProduct.setDsr(product.getDsr());
            oneAppProduct.setDti(product.getDti());
            oneAppProduct.setPaymentMethod(product.getPaymentMethod());
            oneAppProduct.setPaymentMethodDescEN(product.getPaymentMethodDescEN());
            oneAppProduct.setPaymentMethodDescTH(product.getPaymentMethodDescTH());
            OneAppPricing[] oneAppPricings = toOneApp(product.getPricings());
            oneAppProduct.setPricings(oneAppPricings);
            oneAppProduct.setProductCode(product.getProductCode());
            oneAppProduct.setProductDescEN(product.getProductDescEN());
            oneAppProduct.setProductDescTH(product.getProductDescTH());
            oneAppProduct.setSubProductCode(product.getSubProductCode());
            oneAppProduct.setSubProductDescEN(product.getSubProductDescEN());
            oneAppProduct.setSubProductDescTH(product.getSubProductDescTH());
            oneAppProduct.setTenure(product.getTenure());
            return oneAppProduct;
        }).toArray(OneAppProduct[]::new);
    }

    private OneAppPricing[] toOneApp(Pricing[] pricings) {
        if (pricings == null) return new OneAppPricing[0];
        return Arrays.stream(pricings).map(pricing -> {
            OneAppPricing oneAppPricing = new OneAppPricing();
            oneAppPricing.setCalculatedRate(pricing.getCalculatedRate());
            oneAppPricing.setInstallment(pricing.getInstallment());
            oneAppPricing.setInterestRate(pricing.getInterestRate());
            oneAppPricing.setMonthFrom(pricing.getMonthFrom());
            oneAppPricing.setMonthTo(pricing.getMonthTo());
            oneAppPricing.setPercentSign(pricing.getPercentSign());
            oneAppPricing.setRateType(pricing.getRateType());
            oneAppPricing.setRateVaraince(pricing.getRateVaraince());
            oneAppPricing.setTier(pricing.getTier());
            oneAppPricing.setYearFrom(pricing.getYearFrom());
            oneAppPricing.setYearTo(pricing.getYearTo());
            return oneAppPricing;
        }).toArray(OneAppPricing[]::new);
    }

    /**
     * Maps ResponseTracking to LoanStatusTrackingResponse
     *
     * @param responseTracking
     * @return
     */

    public LoanStatusTrackingResponse toOneApp(ResponseTracking responseTracking) {

        LoanStatusTrackingResponse loanStatusTracking = new LoanStatusTrackingResponse();
        List<OneAppApplication> oneAppApplications = new ArrayList<>();
        loanStatusTracking.setApplications(oneAppApplications);
        Application[] applications = responseTracking.getBody().getApplication();

        OneAppApplication oneAppApplication;
        for (Application application : applications) {
            oneAppApplication = new OneAppApplication();
            oneAppApplication.setApplicationDate(application.getApplicationDate());
            oneAppApplication.setAppRefNo(application.getAppRefNo());
            oneAppApplication.setAppStatus(application.getAppStatus());
            oneAppApplication.setAppStatusDesc(application.getAppStatusDesc());
            oneAppApplication.setAppType(application.getAppType());
            oneAppApplication.setInstantAppliedStepFlag(application.getInstantAppliedStepFlag());
            oneAppApplication.setInstantFlag(application.getInstantFlag());
            oneAppApplication.setIsApproved(application.getIsApproved());
            oneAppApplication.setIsIncompleteDocUpdated(application.getIsIncompleteDocUpdated());
            oneAppApplication.setIsRejected(application.getIsRejected());
            oneAppApplication.setIsSubmitted(application.getIsSubmitted());
            oneAppApplication.setLastUpdateDate(application.getLastUpdateDate());
            oneAppApplication.setNatureOfRequest(application.getNatureOfRequest());
            oneAppApplication.setNatureOfRequestDescEN(application.getNatureOfRequestDescEN());
            oneAppApplication.setNatureOfRequestDescTH(application.getNatureOfRequestDescTH());
            oneAppApplication.setApplicants(toOneApp(application.getApplicants()));
            oneAppApplication.setRoadMap(toOneApp(application.getRoadMap()));
            oneAppApplications.add(oneAppApplication);
        }


        return loanStatusTracking;
    }

    private OneAppRoadMap[] toOneApp(RoadMap[] roadMaps) {
        if (roadMaps == null) return new OneAppRoadMap[0];
        return Arrays.stream(roadMaps).map(roadMap -> {
            OneAppRoadMap oneAppRoadMap = new OneAppRoadMap();
            oneAppRoadMap.setDefaultDescEn(roadMap.getDefaultDescEn());
            oneAppRoadMap.setDefaultDescTh(roadMap.getDefaultDescTh());
            oneAppRoadMap.setGroupNumber(roadMap.getGroupNumber());
            oneAppRoadMap.setDefaultPercentComplete(roadMap.getDefaultPercentComplete());
            return oneAppRoadMap;
        }).toArray(OneAppRoadMap[]::new);
    }

    /**
     * instantCreditCards uses master26 key map is product_type->entry_code
     * instantFacility uses master27 key map is facility_code->entry_code
     *
     * @param responseInstantLoanGetEligibleProduct
     * @param masterData
     * @return
     */
    public EligibleProductResponse toOneApp(ResponseInstantLoanGetEligibleProduct responseInstantLoanGetEligibleProduct, Map<String, Object> masterData) {
        EligibleProductResponse eligibleProductResponse = new EligibleProductResponse();
        InstantFacility[] instantFacilities = responseInstantLoanGetEligibleProduct.getBody().getInstantFacility();
        InstantCreditCard[] instantCreditCards = responseInstantLoanGetEligibleProduct.getBody().getInstantCreditCard();
        OneAppEligibleProduct[] product1 = toOneApp(instantFacilities, masterData);
        OneAppEligibleProduct[] product2 = toOneApp(instantCreditCards, masterData);
        eligibleProductResponse.setEligibleProducts(Stream.concat(Arrays.stream(product1), Arrays.stream(product2))
                .toArray(OneAppEligibleProduct[]::new));

        return eligibleProductResponse;
    }

    /**
     * instantCreditCards uses master26 key map is product_type->entry_code
     *
     * @param instantCreditCards
     * @param masterData
     * @return
     */
    private OneAppEligibleProduct[] toOneApp(InstantCreditCard[] instantCreditCards, Map<String, Object> masterData) {
        if (instantCreditCards == null) return new OneAppEligibleProduct[0];
        List<PaymentCriteriaOption> masterDataPymt = (List<PaymentCriteriaOption>) masterData.get(LoanCategory.PYMT_CRITERIA.getCode());
        List<PaymentCriteriaOption> ccMasterDataPymt = masterDataPymt.stream().filter(paymentCriteriaOption -> paymentCriteriaOption.getEntrySource().contains("CC")).collect(Collectors.toList());

        return Arrays.stream(instantCreditCards).map(instantCreditCard -> {
            CommonCodeEntry masterDataItem = (CommonCodeEntry) masterData.get(instantCreditCard.getProductType());
            OneAppEligibleProduct oneAppEligibleProduct = new OneAppEligibleProduct();
            oneAppEligibleProduct.setProductNameEn(masterDataItem.getEntryName());
            oneAppEligibleProduct.setProductNameTh(masterDataItem.getEntryName2());
            oneAppEligibleProduct.setProductCategory("credit_card");
            oneAppEligibleProduct.setCampaignCode(instantCreditCard.getCampaignCode());
            oneAppEligibleProduct.setProductType(instantCreditCard.getProductType());
            oneAppEligibleProduct.setLoanReqMax(String.valueOf(instantCreditCard.getLoanReqMax()));
            oneAppEligibleProduct.setLoanReqMin(String.valueOf(instantCreditCard.getLoanReqMin()));
            oneAppEligibleProduct.setPaymentCriteriaOptions(ccMasterDataPymt);
            oneAppEligibleProduct.setSourceOfData(instantCreditCard.getSourceOfData());

            return oneAppEligibleProduct;
        }).toArray(OneAppEligibleProduct[]::new);


    }

    /**
     * instantFacility uses master27 key map is facility_code->entry_code
     *
     * @param instantFacilities
     * @param masterData
     * @return
     */
    private OneAppEligibleProduct[] toOneApp(InstantFacility[] instantFacilities, Map<String, Object> masterData) {
        if (instantFacilities == null) return new OneAppEligibleProduct[0];
        List<PaymentCriteriaOption> masterDataPymt = (List<PaymentCriteriaOption>) masterData.get(LoanCategory.PYMT_CRITERIA.getCode());
        List<PaymentCriteriaOption> rcMasterDataPymt = masterDataPymt.stream().filter(paymentCriteriaOption -> paymentCriteriaOption.getEntrySource().contains("RC")).collect(Collectors.toList());
        return Arrays.stream(instantFacilities).map(instantFacility -> {
            CommonCodeEntry masterDataItem = (CommonCodeEntry) masterData.get(instantFacility.getFacilityCode());
            OneAppEligibleProduct oneAppEligibleProduct = new OneAppEligibleProduct();
            oneAppEligibleProduct.setProductNameEn(masterDataItem.getEntryName());
            oneAppEligibleProduct.setProductNameTh(masterDataItem.getEntryName2());
            oneAppEligibleProduct.setProductCode(instantFacility.getProductCode());
            oneAppEligibleProduct.setProductCategory("loan");
            oneAppEligibleProduct.setCampaignCode(instantFacility.getCaCampaignCode());
            oneAppEligibleProduct.setFacilityCode(instantFacility.getFacilityCode());
            oneAppEligibleProduct.setInterestRate(String.valueOf(instantFacility.getInterestRate()));
            oneAppEligibleProduct.setLoanReqMax(String.valueOf(instantFacility.getLoanReqMax()));
            oneAppEligibleProduct.setLoanReqMin(String.valueOf(instantFacility.getLoanReqMin()));
            oneAppEligibleProduct.setOsLimit(String.valueOf(instantFacility.getOsLimit()));
            oneAppEligibleProduct.setSourceOfData(instantFacility.getSourceOfData());
            oneAppEligibleProduct.setPaymentCriteriaOptions(rcMasterDataPymt);
            return oneAppEligibleProduct;
        }).toArray(OneAppEligibleProduct[]::new);
    }
}
