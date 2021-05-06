/**
 * InstantFacility.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.integrosys.sml.common.ob.facility;

public class InstantFacility  implements java.io.Serializable {
    private java.lang.String accountName;

    private java.math.BigDecimal amountFinance;

    private java.lang.String caCampaignCode;

    private java.math.BigDecimal caId;

    private java.util.Calendar campaignExpireDate;

    private java.util.Calendar campaignStartDate;

    private java.lang.String cardDelivery;

    private java.lang.String considerLoanWithOtherBank;

    private java.util.Calendar contractDate;

    private java.math.BigDecimal creditLimitFromMof;

    private java.lang.String customerSegment;

    private java.lang.String disburstAccountName;

    private java.lang.String disburstAccountNo;

    private java.lang.String disburstBankName;

    private java.math.BigDecimal existLimit;

    private java.lang.String existLoan;

    private java.math.BigDecimal existMaxCreditLimit;

    private java.lang.String existingAccountNo;

    private java.math.BigDecimal existingCreditLimit;

    private java.math.BigDecimal existingOsBalance;

    private java.lang.String facilityCode;

    private java.lang.String facilityPurchaseCode;

    private java.lang.String facilityPurchaseDesc;

    private java.lang.String facilitySavedFlag;

    private java.lang.String facilityStatus;

    private com.integrosys.sml.common.ob.feature.Feature feature;

    private java.lang.String featureType;

    private java.lang.String financialInstitution;

    private java.lang.String financialInstitutionName;

    private java.lang.String firstPaymentDueDate;

    private java.lang.String hostAaNo;

    private java.lang.String hostProductCode;

    private java.lang.String hostProjectCode;

    private java.math.BigDecimal id;

    private java.math.BigDecimal interestRate;

    private java.math.BigDecimal limitApplied;

    private java.math.BigDecimal loanReqMax;

    private java.math.BigDecimal loanReqMin;

    private java.lang.String loanWithOtherBank;

    private java.lang.String mailingPreference;

    private java.lang.String modelName;

    private java.math.BigDecimal modelScore;

    private java.lang.String mofDocumentCode;

    private java.lang.String mofLoanID;

    private java.math.BigDecimal monthlyInstall;

    private java.math.BigDecimal monthlyInstallment;

    private java.math.BigDecimal mrtaAmount;

    private java.lang.String mrtaFlag;

    private java.lang.String mrtaIncludeInLoanAmountFlag;

    private java.math.BigDecimal mrtaSumInsured;

    private java.math.BigDecimal mrtaYrsCoverage;

    private java.math.BigDecimal osLimit;

    private java.math.BigDecimal outStandingBalance;

    private java.lang.String payMethodCriteria;

    private java.lang.String paymentAccountName;

    private java.lang.String paymentAccountNo;

    private java.lang.String paymentDueDate;

    private java.lang.String paymentMethod;

    private com.integrosys.sml.common.ob.pricing.Pricing[] pricings;

    private java.lang.String productCode;

    private java.lang.String rePayMent;

    private java.util.Calendar rfContractDate;

    private java.lang.String saleChannelCode;

    private java.lang.String saleChannelFlag;

    private java.lang.String sameGroup;

    private java.lang.String sourceOfData;

    private java.lang.String suffix;

    private java.math.BigDecimal tenure;

    private java.math.BigDecimal totalCreditLimit;

    private java.math.BigDecimal totalRepaymentAmount;

    public InstantFacility() {
    }

    public InstantFacility(
           java.lang.String accountName,
           java.math.BigDecimal amountFinance,
           java.lang.String caCampaignCode,
           java.math.BigDecimal caId,
           java.util.Calendar campaignExpireDate,
           java.util.Calendar campaignStartDate,
           java.lang.String cardDelivery,
           java.lang.String considerLoanWithOtherBank,
           java.util.Calendar contractDate,
           java.math.BigDecimal creditLimitFromMof,
           java.lang.String customerSegment,
           java.lang.String disburstAccountName,
           java.lang.String disburstAccountNo,
           java.lang.String disburstBankName,
           java.math.BigDecimal existLimit,
           java.lang.String existLoan,
           java.math.BigDecimal existMaxCreditLimit,
           java.lang.String existingAccountNo,
           java.math.BigDecimal existingCreditLimit,
           java.math.BigDecimal existingOsBalance,
           java.lang.String facilityCode,
           java.lang.String facilityPurchaseCode,
           java.lang.String facilityPurchaseDesc,
           java.lang.String facilitySavedFlag,
           java.lang.String facilityStatus,
           com.integrosys.sml.common.ob.feature.Feature feature,
           java.lang.String featureType,
           java.lang.String financialInstitution,
           java.lang.String financialInstitutionName,
           java.lang.String firstPaymentDueDate,
           java.lang.String hostAaNo,
           java.lang.String hostProductCode,
           java.lang.String hostProjectCode,
           java.math.BigDecimal id,
           java.math.BigDecimal interestRate,
           java.math.BigDecimal limitApplied,
           java.math.BigDecimal loanReqMax,
           java.math.BigDecimal loanReqMin,
           java.lang.String loanWithOtherBank,
           java.lang.String mailingPreference,
           java.lang.String modelName,
           java.math.BigDecimal modelScore,
           java.lang.String mofDocumentCode,
           java.lang.String mofLoanID,
           java.math.BigDecimal monthlyInstall,
           java.math.BigDecimal monthlyInstallment,
           java.math.BigDecimal mrtaAmount,
           java.lang.String mrtaFlag,
           java.lang.String mrtaIncludeInLoanAmountFlag,
           java.math.BigDecimal mrtaSumInsured,
           java.math.BigDecimal mrtaYrsCoverage,
           java.math.BigDecimal osLimit,
           java.math.BigDecimal outStandingBalance,
           java.lang.String payMethodCriteria,
           java.lang.String paymentAccountName,
           java.lang.String paymentAccountNo,
           java.lang.String paymentDueDate,
           java.lang.String paymentMethod,
           com.integrosys.sml.common.ob.pricing.Pricing[] pricings,
           java.lang.String productCode,
           java.lang.String rePayMent,
           java.util.Calendar rfContractDate,
           java.lang.String saleChannelCode,
           java.lang.String saleChannelFlag,
           java.lang.String sameGroup,
           java.lang.String sourceOfData,
           java.lang.String suffix,
           java.math.BigDecimal tenure,
           java.math.BigDecimal totalCreditLimit,
           java.math.BigDecimal totalRepaymentAmount) {
           this.accountName = accountName;
           this.amountFinance = amountFinance;
           this.caCampaignCode = caCampaignCode;
           this.caId = caId;
           this.campaignExpireDate = campaignExpireDate;
           this.campaignStartDate = campaignStartDate;
           this.cardDelivery = cardDelivery;
           this.considerLoanWithOtherBank = considerLoanWithOtherBank;
           this.contractDate = contractDate;
           this.creditLimitFromMof = creditLimitFromMof;
           this.customerSegment = customerSegment;
           this.disburstAccountName = disburstAccountName;
           this.disburstAccountNo = disburstAccountNo;
           this.disburstBankName = disburstBankName;
           this.existLimit = existLimit;
           this.existLoan = existLoan;
           this.existMaxCreditLimit = existMaxCreditLimit;
           this.existingAccountNo = existingAccountNo;
           this.existingCreditLimit = existingCreditLimit;
           this.existingOsBalance = existingOsBalance;
           this.facilityCode = facilityCode;
           this.facilityPurchaseCode = facilityPurchaseCode;
           this.facilityPurchaseDesc = facilityPurchaseDesc;
           this.facilitySavedFlag = facilitySavedFlag;
           this.facilityStatus = facilityStatus;
           this.feature = feature;
           this.featureType = featureType;
           this.financialInstitution = financialInstitution;
           this.financialInstitutionName = financialInstitutionName;
           this.firstPaymentDueDate = firstPaymentDueDate;
           this.hostAaNo = hostAaNo;
           this.hostProductCode = hostProductCode;
           this.hostProjectCode = hostProjectCode;
           this.id = id;
           this.interestRate = interestRate;
           this.limitApplied = limitApplied;
           this.loanReqMax = loanReqMax;
           this.loanReqMin = loanReqMin;
           this.loanWithOtherBank = loanWithOtherBank;
           this.mailingPreference = mailingPreference;
           this.modelName = modelName;
           this.modelScore = modelScore;
           this.mofDocumentCode = mofDocumentCode;
           this.mofLoanID = mofLoanID;
           this.monthlyInstall = monthlyInstall;
           this.monthlyInstallment = monthlyInstallment;
           this.mrtaAmount = mrtaAmount;
           this.mrtaFlag = mrtaFlag;
           this.mrtaIncludeInLoanAmountFlag = mrtaIncludeInLoanAmountFlag;
           this.mrtaSumInsured = mrtaSumInsured;
           this.mrtaYrsCoverage = mrtaYrsCoverage;
           this.osLimit = osLimit;
           this.outStandingBalance = outStandingBalance;
           this.payMethodCriteria = payMethodCriteria;
           this.paymentAccountName = paymentAccountName;
           this.paymentAccountNo = paymentAccountNo;
           this.paymentDueDate = paymentDueDate;
           this.paymentMethod = paymentMethod;
           this.pricings = pricings;
           this.productCode = productCode;
           this.rePayMent = rePayMent;
           this.rfContractDate = rfContractDate;
           this.saleChannelCode = saleChannelCode;
           this.saleChannelFlag = saleChannelFlag;
           this.sameGroup = sameGroup;
           this.sourceOfData = sourceOfData;
           this.suffix = suffix;
           this.tenure = tenure;
           this.totalCreditLimit = totalCreditLimit;
           this.totalRepaymentAmount = totalRepaymentAmount;
    }


    /**
     * Gets the accountName value for this InstantFacility.
     * 
     * @return accountName
     */
    public java.lang.String getAccountName() {
        return accountName;
    }


    /**
     * Sets the accountName value for this InstantFacility.
     * 
     * @param accountName
     */
    public void setAccountName(java.lang.String accountName) {
        this.accountName = accountName;
    }


    /**
     * Gets the amountFinance value for this InstantFacility.
     * 
     * @return amountFinance
     */
    public java.math.BigDecimal getAmountFinance() {
        return amountFinance;
    }


    /**
     * Sets the amountFinance value for this InstantFacility.
     * 
     * @param amountFinance
     */
    public void setAmountFinance(java.math.BigDecimal amountFinance) {
        this.amountFinance = amountFinance;
    }


    /**
     * Gets the caCampaignCode value for this InstantFacility.
     * 
     * @return caCampaignCode
     */
    public java.lang.String getCaCampaignCode() {
        return caCampaignCode;
    }


    /**
     * Sets the caCampaignCode value for this InstantFacility.
     * 
     * @param caCampaignCode
     */
    public void setCaCampaignCode(java.lang.String caCampaignCode) {
        this.caCampaignCode = caCampaignCode;
    }


    /**
     * Gets the caId value for this InstantFacility.
     * 
     * @return caId
     */
    public java.math.BigDecimal getCaId() {
        return caId;
    }


    /**
     * Sets the caId value for this InstantFacility.
     * 
     * @param caId
     */
    public void setCaId(java.math.BigDecimal caId) {
        this.caId = caId;
    }


    /**
     * Gets the campaignExpireDate value for this InstantFacility.
     * 
     * @return campaignExpireDate
     */
    public java.util.Calendar getCampaignExpireDate() {
        return campaignExpireDate;
    }


    /**
     * Sets the campaignExpireDate value for this InstantFacility.
     * 
     * @param campaignExpireDate
     */
    public void setCampaignExpireDate(java.util.Calendar campaignExpireDate) {
        this.campaignExpireDate = campaignExpireDate;
    }


    /**
     * Gets the campaignStartDate value for this InstantFacility.
     * 
     * @return campaignStartDate
     */
    public java.util.Calendar getCampaignStartDate() {
        return campaignStartDate;
    }


    /**
     * Sets the campaignStartDate value for this InstantFacility.
     * 
     * @param campaignStartDate
     */
    public void setCampaignStartDate(java.util.Calendar campaignStartDate) {
        this.campaignStartDate = campaignStartDate;
    }


    /**
     * Gets the cardDelivery value for this InstantFacility.
     * 
     * @return cardDelivery
     */
    public java.lang.String getCardDelivery() {
        return cardDelivery;
    }


    /**
     * Sets the cardDelivery value for this InstantFacility.
     * 
     * @param cardDelivery
     */
    public void setCardDelivery(java.lang.String cardDelivery) {
        this.cardDelivery = cardDelivery;
    }


    /**
     * Gets the considerLoanWithOtherBank value for this InstantFacility.
     * 
     * @return considerLoanWithOtherBank
     */
    public java.lang.String getConsiderLoanWithOtherBank() {
        return considerLoanWithOtherBank;
    }


    /**
     * Sets the considerLoanWithOtherBank value for this InstantFacility.
     * 
     * @param considerLoanWithOtherBank
     */
    public void setConsiderLoanWithOtherBank(java.lang.String considerLoanWithOtherBank) {
        this.considerLoanWithOtherBank = considerLoanWithOtherBank;
    }


    /**
     * Gets the contractDate value for this InstantFacility.
     * 
     * @return contractDate
     */
    public java.util.Calendar getContractDate() {
        return contractDate;
    }


    /**
     * Sets the contractDate value for this InstantFacility.
     * 
     * @param contractDate
     */
    public void setContractDate(java.util.Calendar contractDate) {
        this.contractDate = contractDate;
    }


    /**
     * Gets the creditLimitFromMof value for this InstantFacility.
     * 
     * @return creditLimitFromMof
     */
    public java.math.BigDecimal getCreditLimitFromMof() {
        return creditLimitFromMof;
    }


    /**
     * Sets the creditLimitFromMof value for this InstantFacility.
     * 
     * @param creditLimitFromMof
     */
    public void setCreditLimitFromMof(java.math.BigDecimal creditLimitFromMof) {
        this.creditLimitFromMof = creditLimitFromMof;
    }


    /**
     * Gets the customerSegment value for this InstantFacility.
     * 
     * @return customerSegment
     */
    public java.lang.String getCustomerSegment() {
        return customerSegment;
    }


    /**
     * Sets the customerSegment value for this InstantFacility.
     * 
     * @param customerSegment
     */
    public void setCustomerSegment(java.lang.String customerSegment) {
        this.customerSegment = customerSegment;
    }


    /**
     * Gets the disburstAccountName value for this InstantFacility.
     * 
     * @return disburstAccountName
     */
    public java.lang.String getDisburstAccountName() {
        return disburstAccountName;
    }


    /**
     * Sets the disburstAccountName value for this InstantFacility.
     * 
     * @param disburstAccountName
     */
    public void setDisburstAccountName(java.lang.String disburstAccountName) {
        this.disburstAccountName = disburstAccountName;
    }


    /**
     * Gets the disburstAccountNo value for this InstantFacility.
     * 
     * @return disburstAccountNo
     */
    public java.lang.String getDisburstAccountNo() {
        return disburstAccountNo;
    }


    /**
     * Sets the disburstAccountNo value for this InstantFacility.
     * 
     * @param disburstAccountNo
     */
    public void setDisburstAccountNo(java.lang.String disburstAccountNo) {
        this.disburstAccountNo = disburstAccountNo;
    }


    /**
     * Gets the disburstBankName value for this InstantFacility.
     * 
     * @return disburstBankName
     */
    public java.lang.String getDisburstBankName() {
        return disburstBankName;
    }


    /**
     * Sets the disburstBankName value for this InstantFacility.
     * 
     * @param disburstBankName
     */
    public void setDisburstBankName(java.lang.String disburstBankName) {
        this.disburstBankName = disburstBankName;
    }


    /**
     * Gets the existLimit value for this InstantFacility.
     * 
     * @return existLimit
     */
    public java.math.BigDecimal getExistLimit() {
        return existLimit;
    }


    /**
     * Sets the existLimit value for this InstantFacility.
     * 
     * @param existLimit
     */
    public void setExistLimit(java.math.BigDecimal existLimit) {
        this.existLimit = existLimit;
    }


    /**
     * Gets the existLoan value for this InstantFacility.
     * 
     * @return existLoan
     */
    public java.lang.String getExistLoan() {
        return existLoan;
    }


    /**
     * Sets the existLoan value for this InstantFacility.
     * 
     * @param existLoan
     */
    public void setExistLoan(java.lang.String existLoan) {
        this.existLoan = existLoan;
    }


    /**
     * Gets the existMaxCreditLimit value for this InstantFacility.
     * 
     * @return existMaxCreditLimit
     */
    public java.math.BigDecimal getExistMaxCreditLimit() {
        return existMaxCreditLimit;
    }


    /**
     * Sets the existMaxCreditLimit value for this InstantFacility.
     * 
     * @param existMaxCreditLimit
     */
    public void setExistMaxCreditLimit(java.math.BigDecimal existMaxCreditLimit) {
        this.existMaxCreditLimit = existMaxCreditLimit;
    }


    /**
     * Gets the existingAccountNo value for this InstantFacility.
     * 
     * @return existingAccountNo
     */
    public java.lang.String getExistingAccountNo() {
        return existingAccountNo;
    }


    /**
     * Sets the existingAccountNo value for this InstantFacility.
     * 
     * @param existingAccountNo
     */
    public void setExistingAccountNo(java.lang.String existingAccountNo) {
        this.existingAccountNo = existingAccountNo;
    }


    /**
     * Gets the existingCreditLimit value for this InstantFacility.
     * 
     * @return existingCreditLimit
     */
    public java.math.BigDecimal getExistingCreditLimit() {
        return existingCreditLimit;
    }


    /**
     * Sets the existingCreditLimit value for this InstantFacility.
     * 
     * @param existingCreditLimit
     */
    public void setExistingCreditLimit(java.math.BigDecimal existingCreditLimit) {
        this.existingCreditLimit = existingCreditLimit;
    }


    /**
     * Gets the existingOsBalance value for this InstantFacility.
     * 
     * @return existingOsBalance
     */
    public java.math.BigDecimal getExistingOsBalance() {
        return existingOsBalance;
    }


    /**
     * Sets the existingOsBalance value for this InstantFacility.
     * 
     * @param existingOsBalance
     */
    public void setExistingOsBalance(java.math.BigDecimal existingOsBalance) {
        this.existingOsBalance = existingOsBalance;
    }


    /**
     * Gets the facilityCode value for this InstantFacility.
     * 
     * @return facilityCode
     */
    public java.lang.String getFacilityCode() {
        return facilityCode;
    }


    /**
     * Sets the facilityCode value for this InstantFacility.
     * 
     * @param facilityCode
     */
    public void setFacilityCode(java.lang.String facilityCode) {
        this.facilityCode = facilityCode;
    }


    /**
     * Gets the facilityPurchaseCode value for this InstantFacility.
     * 
     * @return facilityPurchaseCode
     */
    public java.lang.String getFacilityPurchaseCode() {
        return facilityPurchaseCode;
    }


    /**
     * Sets the facilityPurchaseCode value for this InstantFacility.
     * 
     * @param facilityPurchaseCode
     */
    public void setFacilityPurchaseCode(java.lang.String facilityPurchaseCode) {
        this.facilityPurchaseCode = facilityPurchaseCode;
    }


    /**
     * Gets the facilityPurchaseDesc value for this InstantFacility.
     * 
     * @return facilityPurchaseDesc
     */
    public java.lang.String getFacilityPurchaseDesc() {
        return facilityPurchaseDesc;
    }


    /**
     * Sets the facilityPurchaseDesc value for this InstantFacility.
     * 
     * @param facilityPurchaseDesc
     */
    public void setFacilityPurchaseDesc(java.lang.String facilityPurchaseDesc) {
        this.facilityPurchaseDesc = facilityPurchaseDesc;
    }


    /**
     * Gets the facilitySavedFlag value for this InstantFacility.
     * 
     * @return facilitySavedFlag
     */
    public java.lang.String getFacilitySavedFlag() {
        return facilitySavedFlag;
    }


    /**
     * Sets the facilitySavedFlag value for this InstantFacility.
     * 
     * @param facilitySavedFlag
     */
    public void setFacilitySavedFlag(java.lang.String facilitySavedFlag) {
        this.facilitySavedFlag = facilitySavedFlag;
    }


    /**
     * Gets the facilityStatus value for this InstantFacility.
     * 
     * @return facilityStatus
     */
    public java.lang.String getFacilityStatus() {
        return facilityStatus;
    }


    /**
     * Sets the facilityStatus value for this InstantFacility.
     * 
     * @param facilityStatus
     */
    public void setFacilityStatus(java.lang.String facilityStatus) {
        this.facilityStatus = facilityStatus;
    }


    /**
     * Gets the feature value for this InstantFacility.
     * 
     * @return feature
     */
    public com.integrosys.sml.common.ob.feature.Feature getFeature() {
        return feature;
    }


    /**
     * Sets the feature value for this InstantFacility.
     * 
     * @param feature
     */
    public void setFeature(com.integrosys.sml.common.ob.feature.Feature feature) {
        this.feature = feature;
    }


    /**
     * Gets the featureType value for this InstantFacility.
     * 
     * @return featureType
     */
    public java.lang.String getFeatureType() {
        return featureType;
    }


    /**
     * Sets the featureType value for this InstantFacility.
     * 
     * @param featureType
     */
    public void setFeatureType(java.lang.String featureType) {
        this.featureType = featureType;
    }


    /**
     * Gets the financialInstitution value for this InstantFacility.
     * 
     * @return financialInstitution
     */
    public java.lang.String getFinancialInstitution() {
        return financialInstitution;
    }


    /**
     * Sets the financialInstitution value for this InstantFacility.
     * 
     * @param financialInstitution
     */
    public void setFinancialInstitution(java.lang.String financialInstitution) {
        this.financialInstitution = financialInstitution;
    }


    /**
     * Gets the financialInstitutionName value for this InstantFacility.
     * 
     * @return financialInstitutionName
     */
    public java.lang.String getFinancialInstitutionName() {
        return financialInstitutionName;
    }


    /**
     * Sets the financialInstitutionName value for this InstantFacility.
     * 
     * @param financialInstitutionName
     */
    public void setFinancialInstitutionName(java.lang.String financialInstitutionName) {
        this.financialInstitutionName = financialInstitutionName;
    }


    /**
     * Gets the firstPaymentDueDate value for this InstantFacility.
     * 
     * @return firstPaymentDueDate
     */
    public java.lang.String getFirstPaymentDueDate() {
        return firstPaymentDueDate;
    }


    /**
     * Sets the firstPaymentDueDate value for this InstantFacility.
     * 
     * @param firstPaymentDueDate
     */
    public void setFirstPaymentDueDate(java.lang.String firstPaymentDueDate) {
        this.firstPaymentDueDate = firstPaymentDueDate;
    }


    /**
     * Gets the hostAaNo value for this InstantFacility.
     * 
     * @return hostAaNo
     */
    public java.lang.String getHostAaNo() {
        return hostAaNo;
    }


    /**
     * Sets the hostAaNo value for this InstantFacility.
     * 
     * @param hostAaNo
     */
    public void setHostAaNo(java.lang.String hostAaNo) {
        this.hostAaNo = hostAaNo;
    }


    /**
     * Gets the hostProductCode value for this InstantFacility.
     * 
     * @return hostProductCode
     */
    public java.lang.String getHostProductCode() {
        return hostProductCode;
    }


    /**
     * Sets the hostProductCode value for this InstantFacility.
     * 
     * @param hostProductCode
     */
    public void setHostProductCode(java.lang.String hostProductCode) {
        this.hostProductCode = hostProductCode;
    }


    /**
     * Gets the hostProjectCode value for this InstantFacility.
     * 
     * @return hostProjectCode
     */
    public java.lang.String getHostProjectCode() {
        return hostProjectCode;
    }


    /**
     * Sets the hostProjectCode value for this InstantFacility.
     * 
     * @param hostProjectCode
     */
    public void setHostProjectCode(java.lang.String hostProjectCode) {
        this.hostProjectCode = hostProjectCode;
    }


    /**
     * Gets the id value for this InstantFacility.
     * 
     * @return id
     */
    public java.math.BigDecimal getId() {
        return id;
    }


    /**
     * Sets the id value for this InstantFacility.
     * 
     * @param id
     */
    public void setId(java.math.BigDecimal id) {
        this.id = id;
    }


    /**
     * Gets the interestRate value for this InstantFacility.
     * 
     * @return interestRate
     */
    public java.math.BigDecimal getInterestRate() {
        return interestRate;
    }


    /**
     * Sets the interestRate value for this InstantFacility.
     * 
     * @param interestRate
     */
    public void setInterestRate(java.math.BigDecimal interestRate) {
        this.interestRate = interestRate;
    }


    /**
     * Gets the limitApplied value for this InstantFacility.
     * 
     * @return limitApplied
     */
    public java.math.BigDecimal getLimitApplied() {
        return limitApplied;
    }


    /**
     * Sets the limitApplied value for this InstantFacility.
     * 
     * @param limitApplied
     */
    public void setLimitApplied(java.math.BigDecimal limitApplied) {
        this.limitApplied = limitApplied;
    }


    /**
     * Gets the loanReqMax value for this InstantFacility.
     * 
     * @return loanReqMax
     */
    public java.math.BigDecimal getLoanReqMax() {
        return loanReqMax;
    }


    /**
     * Sets the loanReqMax value for this InstantFacility.
     * 
     * @param loanReqMax
     */
    public void setLoanReqMax(java.math.BigDecimal loanReqMax) {
        this.loanReqMax = loanReqMax;
    }


    /**
     * Gets the loanReqMin value for this InstantFacility.
     * 
     * @return loanReqMin
     */
    public java.math.BigDecimal getLoanReqMin() {
        return loanReqMin;
    }


    /**
     * Sets the loanReqMin value for this InstantFacility.
     * 
     * @param loanReqMin
     */
    public void setLoanReqMin(java.math.BigDecimal loanReqMin) {
        this.loanReqMin = loanReqMin;
    }


    /**
     * Gets the loanWithOtherBank value for this InstantFacility.
     * 
     * @return loanWithOtherBank
     */
    public java.lang.String getLoanWithOtherBank() {
        return loanWithOtherBank;
    }


    /**
     * Sets the loanWithOtherBank value for this InstantFacility.
     * 
     * @param loanWithOtherBank
     */
    public void setLoanWithOtherBank(java.lang.String loanWithOtherBank) {
        this.loanWithOtherBank = loanWithOtherBank;
    }


    /**
     * Gets the mailingPreference value for this InstantFacility.
     * 
     * @return mailingPreference
     */
    public java.lang.String getMailingPreference() {
        return mailingPreference;
    }


    /**
     * Sets the mailingPreference value for this InstantFacility.
     * 
     * @param mailingPreference
     */
    public void setMailingPreference(java.lang.String mailingPreference) {
        this.mailingPreference = mailingPreference;
    }


    /**
     * Gets the modelName value for this InstantFacility.
     * 
     * @return modelName
     */
    public java.lang.String getModelName() {
        return modelName;
    }


    /**
     * Sets the modelName value for this InstantFacility.
     * 
     * @param modelName
     */
    public void setModelName(java.lang.String modelName) {
        this.modelName = modelName;
    }


    /**
     * Gets the modelScore value for this InstantFacility.
     * 
     * @return modelScore
     */
    public java.math.BigDecimal getModelScore() {
        return modelScore;
    }


    /**
     * Sets the modelScore value for this InstantFacility.
     * 
     * @param modelScore
     */
    public void setModelScore(java.math.BigDecimal modelScore) {
        this.modelScore = modelScore;
    }


    /**
     * Gets the mofDocumentCode value for this InstantFacility.
     * 
     * @return mofDocumentCode
     */
    public java.lang.String getMofDocumentCode() {
        return mofDocumentCode;
    }


    /**
     * Sets the mofDocumentCode value for this InstantFacility.
     * 
     * @param mofDocumentCode
     */
    public void setMofDocumentCode(java.lang.String mofDocumentCode) {
        this.mofDocumentCode = mofDocumentCode;
    }


    /**
     * Gets the mofLoanID value for this InstantFacility.
     * 
     * @return mofLoanID
     */
    public java.lang.String getMofLoanID() {
        return mofLoanID;
    }


    /**
     * Sets the mofLoanID value for this InstantFacility.
     * 
     * @param mofLoanID
     */
    public void setMofLoanID(java.lang.String mofLoanID) {
        this.mofLoanID = mofLoanID;
    }


    /**
     * Gets the monthlyInstall value for this InstantFacility.
     * 
     * @return monthlyInstall
     */
    public java.math.BigDecimal getMonthlyInstall() {
        return monthlyInstall;
    }


    /**
     * Sets the monthlyInstall value for this InstantFacility.
     * 
     * @param monthlyInstall
     */
    public void setMonthlyInstall(java.math.BigDecimal monthlyInstall) {
        this.monthlyInstall = monthlyInstall;
    }


    /**
     * Gets the monthlyInstallment value for this InstantFacility.
     * 
     * @return monthlyInstallment
     */
    public java.math.BigDecimal getMonthlyInstallment() {
        return monthlyInstallment;
    }


    /**
     * Sets the monthlyInstallment value for this InstantFacility.
     * 
     * @param monthlyInstallment
     */
    public void setMonthlyInstallment(java.math.BigDecimal monthlyInstallment) {
        this.monthlyInstallment = monthlyInstallment;
    }


    /**
     * Gets the mrtaAmount value for this InstantFacility.
     * 
     * @return mrtaAmount
     */
    public java.math.BigDecimal getMrtaAmount() {
        return mrtaAmount;
    }


    /**
     * Sets the mrtaAmount value for this InstantFacility.
     * 
     * @param mrtaAmount
     */
    public void setMrtaAmount(java.math.BigDecimal mrtaAmount) {
        this.mrtaAmount = mrtaAmount;
    }


    /**
     * Gets the mrtaFlag value for this InstantFacility.
     * 
     * @return mrtaFlag
     */
    public java.lang.String getMrtaFlag() {
        return mrtaFlag;
    }


    /**
     * Sets the mrtaFlag value for this InstantFacility.
     * 
     * @param mrtaFlag
     */
    public void setMrtaFlag(java.lang.String mrtaFlag) {
        this.mrtaFlag = mrtaFlag;
    }


    /**
     * Gets the mrtaIncludeInLoanAmountFlag value for this InstantFacility.
     * 
     * @return mrtaIncludeInLoanAmountFlag
     */
    public java.lang.String getMrtaIncludeInLoanAmountFlag() {
        return mrtaIncludeInLoanAmountFlag;
    }


    /**
     * Sets the mrtaIncludeInLoanAmountFlag value for this InstantFacility.
     * 
     * @param mrtaIncludeInLoanAmountFlag
     */
    public void setMrtaIncludeInLoanAmountFlag(java.lang.String mrtaIncludeInLoanAmountFlag) {
        this.mrtaIncludeInLoanAmountFlag = mrtaIncludeInLoanAmountFlag;
    }


    /**
     * Gets the mrtaSumInsured value for this InstantFacility.
     * 
     * @return mrtaSumInsured
     */
    public java.math.BigDecimal getMrtaSumInsured() {
        return mrtaSumInsured;
    }


    /**
     * Sets the mrtaSumInsured value for this InstantFacility.
     * 
     * @param mrtaSumInsured
     */
    public void setMrtaSumInsured(java.math.BigDecimal mrtaSumInsured) {
        this.mrtaSumInsured = mrtaSumInsured;
    }


    /**
     * Gets the mrtaYrsCoverage value for this InstantFacility.
     * 
     * @return mrtaYrsCoverage
     */
    public java.math.BigDecimal getMrtaYrsCoverage() {
        return mrtaYrsCoverage;
    }


    /**
     * Sets the mrtaYrsCoverage value for this InstantFacility.
     * 
     * @param mrtaYrsCoverage
     */
    public void setMrtaYrsCoverage(java.math.BigDecimal mrtaYrsCoverage) {
        this.mrtaYrsCoverage = mrtaYrsCoverage;
    }


    /**
     * Gets the osLimit value for this InstantFacility.
     * 
     * @return osLimit
     */
    public java.math.BigDecimal getOsLimit() {
        return osLimit;
    }


    /**
     * Sets the osLimit value for this InstantFacility.
     * 
     * @param osLimit
     */
    public void setOsLimit(java.math.BigDecimal osLimit) {
        this.osLimit = osLimit;
    }


    /**
     * Gets the outStandingBalance value for this InstantFacility.
     * 
     * @return outStandingBalance
     */
    public java.math.BigDecimal getOutStandingBalance() {
        return outStandingBalance;
    }


    /**
     * Sets the outStandingBalance value for this InstantFacility.
     * 
     * @param outStandingBalance
     */
    public void setOutStandingBalance(java.math.BigDecimal outStandingBalance) {
        this.outStandingBalance = outStandingBalance;
    }


    /**
     * Gets the payMethodCriteria value for this InstantFacility.
     * 
     * @return payMethodCriteria
     */
    public java.lang.String getPayMethodCriteria() {
        return payMethodCriteria;
    }


    /**
     * Sets the payMethodCriteria value for this InstantFacility.
     * 
     * @param payMethodCriteria
     */
    public void setPayMethodCriteria(java.lang.String payMethodCriteria) {
        this.payMethodCriteria = payMethodCriteria;
    }


    /**
     * Gets the paymentAccountName value for this InstantFacility.
     * 
     * @return paymentAccountName
     */
    public java.lang.String getPaymentAccountName() {
        return paymentAccountName;
    }


    /**
     * Sets the paymentAccountName value for this InstantFacility.
     * 
     * @param paymentAccountName
     */
    public void setPaymentAccountName(java.lang.String paymentAccountName) {
        this.paymentAccountName = paymentAccountName;
    }


    /**
     * Gets the paymentAccountNo value for this InstantFacility.
     * 
     * @return paymentAccountNo
     */
    public java.lang.String getPaymentAccountNo() {
        return paymentAccountNo;
    }


    /**
     * Sets the paymentAccountNo value for this InstantFacility.
     * 
     * @param paymentAccountNo
     */
    public void setPaymentAccountNo(java.lang.String paymentAccountNo) {
        this.paymentAccountNo = paymentAccountNo;
    }


    /**
     * Gets the paymentDueDate value for this InstantFacility.
     * 
     * @return paymentDueDate
     */
    public java.lang.String getPaymentDueDate() {
        return paymentDueDate;
    }


    /**
     * Sets the paymentDueDate value for this InstantFacility.
     * 
     * @param paymentDueDate
     */
    public void setPaymentDueDate(java.lang.String paymentDueDate) {
        this.paymentDueDate = paymentDueDate;
    }


    /**
     * Gets the paymentMethod value for this InstantFacility.
     * 
     * @return paymentMethod
     */
    public java.lang.String getPaymentMethod() {
        return paymentMethod;
    }


    /**
     * Sets the paymentMethod value for this InstantFacility.
     * 
     * @param paymentMethod
     */
    public void setPaymentMethod(java.lang.String paymentMethod) {
        this.paymentMethod = paymentMethod;
    }


    /**
     * Gets the pricings value for this InstantFacility.
     * 
     * @return pricings
     */
    public com.integrosys.sml.common.ob.pricing.Pricing[] getPricings() {
        return pricings;
    }


    /**
     * Sets the pricings value for this InstantFacility.
     * 
     * @param pricings
     */
    public void setPricings(com.integrosys.sml.common.ob.pricing.Pricing[] pricings) {
        this.pricings = pricings;
    }


    /**
     * Gets the productCode value for this InstantFacility.
     * 
     * @return productCode
     */
    public java.lang.String getProductCode() {
        return productCode;
    }


    /**
     * Sets the productCode value for this InstantFacility.
     * 
     * @param productCode
     */
    public void setProductCode(java.lang.String productCode) {
        this.productCode = productCode;
    }


    /**
     * Gets the rePayMent value for this InstantFacility.
     * 
     * @return rePayMent
     */
    public java.lang.String getRePayMent() {
        return rePayMent;
    }


    /**
     * Sets the rePayMent value for this InstantFacility.
     * 
     * @param rePayMent
     */
    public void setRePayMent(java.lang.String rePayMent) {
        this.rePayMent = rePayMent;
    }


    /**
     * Gets the rfContractDate value for this InstantFacility.
     * 
     * @return rfContractDate
     */
    public java.util.Calendar getRfContractDate() {
        return rfContractDate;
    }


    /**
     * Sets the rfContractDate value for this InstantFacility.
     * 
     * @param rfContractDate
     */
    public void setRfContractDate(java.util.Calendar rfContractDate) {
        this.rfContractDate = rfContractDate;
    }


    /**
     * Gets the saleChannelCode value for this InstantFacility.
     * 
     * @return saleChannelCode
     */
    public java.lang.String getSaleChannelCode() {
        return saleChannelCode;
    }


    /**
     * Sets the saleChannelCode value for this InstantFacility.
     * 
     * @param saleChannelCode
     */
    public void setSaleChannelCode(java.lang.String saleChannelCode) {
        this.saleChannelCode = saleChannelCode;
    }


    /**
     * Gets the saleChannelFlag value for this InstantFacility.
     * 
     * @return saleChannelFlag
     */
    public java.lang.String getSaleChannelFlag() {
        return saleChannelFlag;
    }


    /**
     * Sets the saleChannelFlag value for this InstantFacility.
     * 
     * @param saleChannelFlag
     */
    public void setSaleChannelFlag(java.lang.String saleChannelFlag) {
        this.saleChannelFlag = saleChannelFlag;
    }


    /**
     * Gets the sameGroup value for this InstantFacility.
     * 
     * @return sameGroup
     */
    public java.lang.String getSameGroup() {
        return sameGroup;
    }


    /**
     * Sets the sameGroup value for this InstantFacility.
     * 
     * @param sameGroup
     */
    public void setSameGroup(java.lang.String sameGroup) {
        this.sameGroup = sameGroup;
    }


    /**
     * Gets the sourceOfData value for this InstantFacility.
     * 
     * @return sourceOfData
     */
    public java.lang.String getSourceOfData() {
        return sourceOfData;
    }


    /**
     * Sets the sourceOfData value for this InstantFacility.
     * 
     * @param sourceOfData
     */
    public void setSourceOfData(java.lang.String sourceOfData) {
        this.sourceOfData = sourceOfData;
    }


    /**
     * Gets the suffix value for this InstantFacility.
     * 
     * @return suffix
     */
    public java.lang.String getSuffix() {
        return suffix;
    }


    /**
     * Sets the suffix value for this InstantFacility.
     * 
     * @param suffix
     */
    public void setSuffix(java.lang.String suffix) {
        this.suffix = suffix;
    }


    /**
     * Gets the tenure value for this InstantFacility.
     * 
     * @return tenure
     */
    public java.math.BigDecimal getTenure() {
        return tenure;
    }


    /**
     * Sets the tenure value for this InstantFacility.
     * 
     * @param tenure
     */
    public void setTenure(java.math.BigDecimal tenure) {
        this.tenure = tenure;
    }


    /**
     * Gets the totalCreditLimit value for this InstantFacility.
     * 
     * @return totalCreditLimit
     */
    public java.math.BigDecimal getTotalCreditLimit() {
        return totalCreditLimit;
    }


    /**
     * Sets the totalCreditLimit value for this InstantFacility.
     * 
     * @param totalCreditLimit
     */
    public void setTotalCreditLimit(java.math.BigDecimal totalCreditLimit) {
        this.totalCreditLimit = totalCreditLimit;
    }


    /**
     * Gets the totalRepaymentAmount value for this InstantFacility.
     * 
     * @return totalRepaymentAmount
     */
    public java.math.BigDecimal getTotalRepaymentAmount() {
        return totalRepaymentAmount;
    }


    /**
     * Sets the totalRepaymentAmount value for this InstantFacility.
     * 
     * @param totalRepaymentAmount
     */
    public void setTotalRepaymentAmount(java.math.BigDecimal totalRepaymentAmount) {
        this.totalRepaymentAmount = totalRepaymentAmount;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof InstantFacility)) return false;
        InstantFacility other = (InstantFacility) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.accountName==null && other.getAccountName()==null) || 
             (this.accountName!=null &&
              this.accountName.equals(other.getAccountName()))) &&
            ((this.amountFinance==null && other.getAmountFinance()==null) || 
             (this.amountFinance!=null &&
              this.amountFinance.equals(other.getAmountFinance()))) &&
            ((this.caCampaignCode==null && other.getCaCampaignCode()==null) || 
             (this.caCampaignCode!=null &&
              this.caCampaignCode.equals(other.getCaCampaignCode()))) &&
            ((this.caId==null && other.getCaId()==null) || 
             (this.caId!=null &&
              this.caId.equals(other.getCaId()))) &&
            ((this.campaignExpireDate==null && other.getCampaignExpireDate()==null) || 
             (this.campaignExpireDate!=null &&
              this.campaignExpireDate.equals(other.getCampaignExpireDate()))) &&
            ((this.campaignStartDate==null && other.getCampaignStartDate()==null) || 
             (this.campaignStartDate!=null &&
              this.campaignStartDate.equals(other.getCampaignStartDate()))) &&
            ((this.cardDelivery==null && other.getCardDelivery()==null) || 
             (this.cardDelivery!=null &&
              this.cardDelivery.equals(other.getCardDelivery()))) &&
            ((this.considerLoanWithOtherBank==null && other.getConsiderLoanWithOtherBank()==null) || 
             (this.considerLoanWithOtherBank!=null &&
              this.considerLoanWithOtherBank.equals(other.getConsiderLoanWithOtherBank()))) &&
            ((this.contractDate==null && other.getContractDate()==null) || 
             (this.contractDate!=null &&
              this.contractDate.equals(other.getContractDate()))) &&
            ((this.creditLimitFromMof==null && other.getCreditLimitFromMof()==null) || 
             (this.creditLimitFromMof!=null &&
              this.creditLimitFromMof.equals(other.getCreditLimitFromMof()))) &&
            ((this.customerSegment==null && other.getCustomerSegment()==null) || 
             (this.customerSegment!=null &&
              this.customerSegment.equals(other.getCustomerSegment()))) &&
            ((this.disburstAccountName==null && other.getDisburstAccountName()==null) || 
             (this.disburstAccountName!=null &&
              this.disburstAccountName.equals(other.getDisburstAccountName()))) &&
            ((this.disburstAccountNo==null && other.getDisburstAccountNo()==null) || 
             (this.disburstAccountNo!=null &&
              this.disburstAccountNo.equals(other.getDisburstAccountNo()))) &&
            ((this.disburstBankName==null && other.getDisburstBankName()==null) || 
             (this.disburstBankName!=null &&
              this.disburstBankName.equals(other.getDisburstBankName()))) &&
            ((this.existLimit==null && other.getExistLimit()==null) || 
             (this.existLimit!=null &&
              this.existLimit.equals(other.getExistLimit()))) &&
            ((this.existLoan==null && other.getExistLoan()==null) || 
             (this.existLoan!=null &&
              this.existLoan.equals(other.getExistLoan()))) &&
            ((this.existMaxCreditLimit==null && other.getExistMaxCreditLimit()==null) || 
             (this.existMaxCreditLimit!=null &&
              this.existMaxCreditLimit.equals(other.getExistMaxCreditLimit()))) &&
            ((this.existingAccountNo==null && other.getExistingAccountNo()==null) || 
             (this.existingAccountNo!=null &&
              this.existingAccountNo.equals(other.getExistingAccountNo()))) &&
            ((this.existingCreditLimit==null && other.getExistingCreditLimit()==null) || 
             (this.existingCreditLimit!=null &&
              this.existingCreditLimit.equals(other.getExistingCreditLimit()))) &&
            ((this.existingOsBalance==null && other.getExistingOsBalance()==null) || 
             (this.existingOsBalance!=null &&
              this.existingOsBalance.equals(other.getExistingOsBalance()))) &&
            ((this.facilityCode==null && other.getFacilityCode()==null) || 
             (this.facilityCode!=null &&
              this.facilityCode.equals(other.getFacilityCode()))) &&
            ((this.facilityPurchaseCode==null && other.getFacilityPurchaseCode()==null) || 
             (this.facilityPurchaseCode!=null &&
              this.facilityPurchaseCode.equals(other.getFacilityPurchaseCode()))) &&
            ((this.facilityPurchaseDesc==null && other.getFacilityPurchaseDesc()==null) || 
             (this.facilityPurchaseDesc!=null &&
              this.facilityPurchaseDesc.equals(other.getFacilityPurchaseDesc()))) &&
            ((this.facilitySavedFlag==null && other.getFacilitySavedFlag()==null) || 
             (this.facilitySavedFlag!=null &&
              this.facilitySavedFlag.equals(other.getFacilitySavedFlag()))) &&
            ((this.facilityStatus==null && other.getFacilityStatus()==null) || 
             (this.facilityStatus!=null &&
              this.facilityStatus.equals(other.getFacilityStatus()))) &&
            ((this.feature==null && other.getFeature()==null) || 
             (this.feature!=null &&
              this.feature.equals(other.getFeature()))) &&
            ((this.featureType==null && other.getFeatureType()==null) || 
             (this.featureType!=null &&
              this.featureType.equals(other.getFeatureType()))) &&
            ((this.financialInstitution==null && other.getFinancialInstitution()==null) || 
             (this.financialInstitution!=null &&
              this.financialInstitution.equals(other.getFinancialInstitution()))) &&
            ((this.financialInstitutionName==null && other.getFinancialInstitutionName()==null) || 
             (this.financialInstitutionName!=null &&
              this.financialInstitutionName.equals(other.getFinancialInstitutionName()))) &&
            ((this.firstPaymentDueDate==null && other.getFirstPaymentDueDate()==null) || 
             (this.firstPaymentDueDate!=null &&
              this.firstPaymentDueDate.equals(other.getFirstPaymentDueDate()))) &&
            ((this.hostAaNo==null && other.getHostAaNo()==null) || 
             (this.hostAaNo!=null &&
              this.hostAaNo.equals(other.getHostAaNo()))) &&
            ((this.hostProductCode==null && other.getHostProductCode()==null) || 
             (this.hostProductCode!=null &&
              this.hostProductCode.equals(other.getHostProductCode()))) &&
            ((this.hostProjectCode==null && other.getHostProjectCode()==null) || 
             (this.hostProjectCode!=null &&
              this.hostProjectCode.equals(other.getHostProjectCode()))) &&
            ((this.id==null && other.getId()==null) || 
             (this.id!=null &&
              this.id.equals(other.getId()))) &&
            ((this.interestRate==null && other.getInterestRate()==null) || 
             (this.interestRate!=null &&
              this.interestRate.equals(other.getInterestRate()))) &&
            ((this.limitApplied==null && other.getLimitApplied()==null) || 
             (this.limitApplied!=null &&
              this.limitApplied.equals(other.getLimitApplied()))) &&
            ((this.loanReqMax==null && other.getLoanReqMax()==null) || 
             (this.loanReqMax!=null &&
              this.loanReqMax.equals(other.getLoanReqMax()))) &&
            ((this.loanReqMin==null && other.getLoanReqMin()==null) || 
             (this.loanReqMin!=null &&
              this.loanReqMin.equals(other.getLoanReqMin()))) &&
            ((this.loanWithOtherBank==null && other.getLoanWithOtherBank()==null) || 
             (this.loanWithOtherBank!=null &&
              this.loanWithOtherBank.equals(other.getLoanWithOtherBank()))) &&
            ((this.mailingPreference==null && other.getMailingPreference()==null) || 
             (this.mailingPreference!=null &&
              this.mailingPreference.equals(other.getMailingPreference()))) &&
            ((this.modelName==null && other.getModelName()==null) || 
             (this.modelName!=null &&
              this.modelName.equals(other.getModelName()))) &&
            ((this.modelScore==null && other.getModelScore()==null) || 
             (this.modelScore!=null &&
              this.modelScore.equals(other.getModelScore()))) &&
            ((this.mofDocumentCode==null && other.getMofDocumentCode()==null) || 
             (this.mofDocumentCode!=null &&
              this.mofDocumentCode.equals(other.getMofDocumentCode()))) &&
            ((this.mofLoanID==null && other.getMofLoanID()==null) || 
             (this.mofLoanID!=null &&
              this.mofLoanID.equals(other.getMofLoanID()))) &&
            ((this.monthlyInstall==null && other.getMonthlyInstall()==null) || 
             (this.monthlyInstall!=null &&
              this.monthlyInstall.equals(other.getMonthlyInstall()))) &&
            ((this.monthlyInstallment==null && other.getMonthlyInstallment()==null) || 
             (this.monthlyInstallment!=null &&
              this.monthlyInstallment.equals(other.getMonthlyInstallment()))) &&
            ((this.mrtaAmount==null && other.getMrtaAmount()==null) || 
             (this.mrtaAmount!=null &&
              this.mrtaAmount.equals(other.getMrtaAmount()))) &&
            ((this.mrtaFlag==null && other.getMrtaFlag()==null) || 
             (this.mrtaFlag!=null &&
              this.mrtaFlag.equals(other.getMrtaFlag()))) &&
            ((this.mrtaIncludeInLoanAmountFlag==null && other.getMrtaIncludeInLoanAmountFlag()==null) || 
             (this.mrtaIncludeInLoanAmountFlag!=null &&
              this.mrtaIncludeInLoanAmountFlag.equals(other.getMrtaIncludeInLoanAmountFlag()))) &&
            ((this.mrtaSumInsured==null && other.getMrtaSumInsured()==null) || 
             (this.mrtaSumInsured!=null &&
              this.mrtaSumInsured.equals(other.getMrtaSumInsured()))) &&
            ((this.mrtaYrsCoverage==null && other.getMrtaYrsCoverage()==null) || 
             (this.mrtaYrsCoverage!=null &&
              this.mrtaYrsCoverage.equals(other.getMrtaYrsCoverage()))) &&
            ((this.osLimit==null && other.getOsLimit()==null) || 
             (this.osLimit!=null &&
              this.osLimit.equals(other.getOsLimit()))) &&
            ((this.outStandingBalance==null && other.getOutStandingBalance()==null) || 
             (this.outStandingBalance!=null &&
              this.outStandingBalance.equals(other.getOutStandingBalance()))) &&
            ((this.payMethodCriteria==null && other.getPayMethodCriteria()==null) || 
             (this.payMethodCriteria!=null &&
              this.payMethodCriteria.equals(other.getPayMethodCriteria()))) &&
            ((this.paymentAccountName==null && other.getPaymentAccountName()==null) || 
             (this.paymentAccountName!=null &&
              this.paymentAccountName.equals(other.getPaymentAccountName()))) &&
            ((this.paymentAccountNo==null && other.getPaymentAccountNo()==null) || 
             (this.paymentAccountNo!=null &&
              this.paymentAccountNo.equals(other.getPaymentAccountNo()))) &&
            ((this.paymentDueDate==null && other.getPaymentDueDate()==null) || 
             (this.paymentDueDate!=null &&
              this.paymentDueDate.equals(other.getPaymentDueDate()))) &&
            ((this.paymentMethod==null && other.getPaymentMethod()==null) || 
             (this.paymentMethod!=null &&
              this.paymentMethod.equals(other.getPaymentMethod()))) &&
            ((this.pricings==null && other.getPricings()==null) || 
             (this.pricings!=null &&
              java.util.Arrays.equals(this.pricings, other.getPricings()))) &&
            ((this.productCode==null && other.getProductCode()==null) || 
             (this.productCode!=null &&
              this.productCode.equals(other.getProductCode()))) &&
            ((this.rePayMent==null && other.getRePayMent()==null) || 
             (this.rePayMent!=null &&
              this.rePayMent.equals(other.getRePayMent()))) &&
            ((this.rfContractDate==null && other.getRfContractDate()==null) || 
             (this.rfContractDate!=null &&
              this.rfContractDate.equals(other.getRfContractDate()))) &&
            ((this.saleChannelCode==null && other.getSaleChannelCode()==null) || 
             (this.saleChannelCode!=null &&
              this.saleChannelCode.equals(other.getSaleChannelCode()))) &&
            ((this.saleChannelFlag==null && other.getSaleChannelFlag()==null) || 
             (this.saleChannelFlag!=null &&
              this.saleChannelFlag.equals(other.getSaleChannelFlag()))) &&
            ((this.sameGroup==null && other.getSameGroup()==null) || 
             (this.sameGroup!=null &&
              this.sameGroup.equals(other.getSameGroup()))) &&
            ((this.sourceOfData==null && other.getSourceOfData()==null) || 
             (this.sourceOfData!=null &&
              this.sourceOfData.equals(other.getSourceOfData()))) &&
            ((this.suffix==null && other.getSuffix()==null) || 
             (this.suffix!=null &&
              this.suffix.equals(other.getSuffix()))) &&
            ((this.tenure==null && other.getTenure()==null) || 
             (this.tenure!=null &&
              this.tenure.equals(other.getTenure()))) &&
            ((this.totalCreditLimit==null && other.getTotalCreditLimit()==null) || 
             (this.totalCreditLimit!=null &&
              this.totalCreditLimit.equals(other.getTotalCreditLimit()))) &&
            ((this.totalRepaymentAmount==null && other.getTotalRepaymentAmount()==null) || 
             (this.totalRepaymentAmount!=null &&
              this.totalRepaymentAmount.equals(other.getTotalRepaymentAmount())));
        __equalsCalc = null;
        return _equals;
    }

    private boolean __hashCodeCalc = false;
    public synchronized int hashCode() {
        if (__hashCodeCalc) {
            return 0;
        }
        __hashCodeCalc = true;
        int _hashCode = 1;
        if (getAccountName() != null) {
            _hashCode += getAccountName().hashCode();
        }
        if (getAmountFinance() != null) {
            _hashCode += getAmountFinance().hashCode();
        }
        if (getCaCampaignCode() != null) {
            _hashCode += getCaCampaignCode().hashCode();
        }
        if (getCaId() != null) {
            _hashCode += getCaId().hashCode();
        }
        if (getCampaignExpireDate() != null) {
            _hashCode += getCampaignExpireDate().hashCode();
        }
        if (getCampaignStartDate() != null) {
            _hashCode += getCampaignStartDate().hashCode();
        }
        if (getCardDelivery() != null) {
            _hashCode += getCardDelivery().hashCode();
        }
        if (getConsiderLoanWithOtherBank() != null) {
            _hashCode += getConsiderLoanWithOtherBank().hashCode();
        }
        if (getContractDate() != null) {
            _hashCode += getContractDate().hashCode();
        }
        if (getCreditLimitFromMof() != null) {
            _hashCode += getCreditLimitFromMof().hashCode();
        }
        if (getCustomerSegment() != null) {
            _hashCode += getCustomerSegment().hashCode();
        }
        if (getDisburstAccountName() != null) {
            _hashCode += getDisburstAccountName().hashCode();
        }
        if (getDisburstAccountNo() != null) {
            _hashCode += getDisburstAccountNo().hashCode();
        }
        if (getDisburstBankName() != null) {
            _hashCode += getDisburstBankName().hashCode();
        }
        if (getExistLimit() != null) {
            _hashCode += getExistLimit().hashCode();
        }
        if (getExistLoan() != null) {
            _hashCode += getExistLoan().hashCode();
        }
        if (getExistMaxCreditLimit() != null) {
            _hashCode += getExistMaxCreditLimit().hashCode();
        }
        if (getExistingAccountNo() != null) {
            _hashCode += getExistingAccountNo().hashCode();
        }
        if (getExistingCreditLimit() != null) {
            _hashCode += getExistingCreditLimit().hashCode();
        }
        if (getExistingOsBalance() != null) {
            _hashCode += getExistingOsBalance().hashCode();
        }
        if (getFacilityCode() != null) {
            _hashCode += getFacilityCode().hashCode();
        }
        if (getFacilityPurchaseCode() != null) {
            _hashCode += getFacilityPurchaseCode().hashCode();
        }
        if (getFacilityPurchaseDesc() != null) {
            _hashCode += getFacilityPurchaseDesc().hashCode();
        }
        if (getFacilitySavedFlag() != null) {
            _hashCode += getFacilitySavedFlag().hashCode();
        }
        if (getFacilityStatus() != null) {
            _hashCode += getFacilityStatus().hashCode();
        }
        if (getFeature() != null) {
            _hashCode += getFeature().hashCode();
        }
        if (getFeatureType() != null) {
            _hashCode += getFeatureType().hashCode();
        }
        if (getFinancialInstitution() != null) {
            _hashCode += getFinancialInstitution().hashCode();
        }
        if (getFinancialInstitutionName() != null) {
            _hashCode += getFinancialInstitutionName().hashCode();
        }
        if (getFirstPaymentDueDate() != null) {
            _hashCode += getFirstPaymentDueDate().hashCode();
        }
        if (getHostAaNo() != null) {
            _hashCode += getHostAaNo().hashCode();
        }
        if (getHostProductCode() != null) {
            _hashCode += getHostProductCode().hashCode();
        }
        if (getHostProjectCode() != null) {
            _hashCode += getHostProjectCode().hashCode();
        }
        if (getId() != null) {
            _hashCode += getId().hashCode();
        }
        if (getInterestRate() != null) {
            _hashCode += getInterestRate().hashCode();
        }
        if (getLimitApplied() != null) {
            _hashCode += getLimitApplied().hashCode();
        }
        if (getLoanReqMax() != null) {
            _hashCode += getLoanReqMax().hashCode();
        }
        if (getLoanReqMin() != null) {
            _hashCode += getLoanReqMin().hashCode();
        }
        if (getLoanWithOtherBank() != null) {
            _hashCode += getLoanWithOtherBank().hashCode();
        }
        if (getMailingPreference() != null) {
            _hashCode += getMailingPreference().hashCode();
        }
        if (getModelName() != null) {
            _hashCode += getModelName().hashCode();
        }
        if (getModelScore() != null) {
            _hashCode += getModelScore().hashCode();
        }
        if (getMofDocumentCode() != null) {
            _hashCode += getMofDocumentCode().hashCode();
        }
        if (getMofLoanID() != null) {
            _hashCode += getMofLoanID().hashCode();
        }
        if (getMonthlyInstall() != null) {
            _hashCode += getMonthlyInstall().hashCode();
        }
        if (getMonthlyInstallment() != null) {
            _hashCode += getMonthlyInstallment().hashCode();
        }
        if (getMrtaAmount() != null) {
            _hashCode += getMrtaAmount().hashCode();
        }
        if (getMrtaFlag() != null) {
            _hashCode += getMrtaFlag().hashCode();
        }
        if (getMrtaIncludeInLoanAmountFlag() != null) {
            _hashCode += getMrtaIncludeInLoanAmountFlag().hashCode();
        }
        if (getMrtaSumInsured() != null) {
            _hashCode += getMrtaSumInsured().hashCode();
        }
        if (getMrtaYrsCoverage() != null) {
            _hashCode += getMrtaYrsCoverage().hashCode();
        }
        if (getOsLimit() != null) {
            _hashCode += getOsLimit().hashCode();
        }
        if (getOutStandingBalance() != null) {
            _hashCode += getOutStandingBalance().hashCode();
        }
        if (getPayMethodCriteria() != null) {
            _hashCode += getPayMethodCriteria().hashCode();
        }
        if (getPaymentAccountName() != null) {
            _hashCode += getPaymentAccountName().hashCode();
        }
        if (getPaymentAccountNo() != null) {
            _hashCode += getPaymentAccountNo().hashCode();
        }
        if (getPaymentDueDate() != null) {
            _hashCode += getPaymentDueDate().hashCode();
        }
        if (getPaymentMethod() != null) {
            _hashCode += getPaymentMethod().hashCode();
        }
        if (getPricings() != null) {
            for (int i=0;
                 i<java.lang.reflect.Array.getLength(getPricings());
                 i++) {
                java.lang.Object obj = java.lang.reflect.Array.get(getPricings(), i);
                if (obj != null &&
                    !obj.getClass().isArray()) {
                    _hashCode += obj.hashCode();
                }
            }
        }
        if (getProductCode() != null) {
            _hashCode += getProductCode().hashCode();
        }
        if (getRePayMent() != null) {
            _hashCode += getRePayMent().hashCode();
        }
        if (getRfContractDate() != null) {
            _hashCode += getRfContractDate().hashCode();
        }
        if (getSaleChannelCode() != null) {
            _hashCode += getSaleChannelCode().hashCode();
        }
        if (getSaleChannelFlag() != null) {
            _hashCode += getSaleChannelFlag().hashCode();
        }
        if (getSameGroup() != null) {
            _hashCode += getSameGroup().hashCode();
        }
        if (getSourceOfData() != null) {
            _hashCode += getSourceOfData().hashCode();
        }
        if (getSuffix() != null) {
            _hashCode += getSuffix().hashCode();
        }
        if (getTenure() != null) {
            _hashCode += getTenure().hashCode();
        }
        if (getTotalCreditLimit() != null) {
            _hashCode += getTotalCreditLimit().hashCode();
        }
        if (getTotalRepaymentAmount() != null) {
            _hashCode += getTotalRepaymentAmount().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(InstantFacility.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://facility.ob.common.sml.integrosys.com", "InstantFacility"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("accountName");
        elemField.setXmlName(new javax.xml.namespace.QName("http://facility.ob.common.sml.integrosys.com", "accountName"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("amountFinance");
        elemField.setXmlName(new javax.xml.namespace.QName("http://facility.ob.common.sml.integrosys.com", "amountFinance"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "decimal"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("caCampaignCode");
        elemField.setXmlName(new javax.xml.namespace.QName("http://facility.ob.common.sml.integrosys.com", "caCampaignCode"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("caId");
        elemField.setXmlName(new javax.xml.namespace.QName("http://facility.ob.common.sml.integrosys.com", "caId"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "decimal"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("campaignExpireDate");
        elemField.setXmlName(new javax.xml.namespace.QName("http://facility.ob.common.sml.integrosys.com", "campaignExpireDate"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "dateTime"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("campaignStartDate");
        elemField.setXmlName(new javax.xml.namespace.QName("http://facility.ob.common.sml.integrosys.com", "campaignStartDate"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "dateTime"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("cardDelivery");
        elemField.setXmlName(new javax.xml.namespace.QName("http://facility.ob.common.sml.integrosys.com", "cardDelivery"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("considerLoanWithOtherBank");
        elemField.setXmlName(new javax.xml.namespace.QName("http://facility.ob.common.sml.integrosys.com", "considerLoanWithOtherBank"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("contractDate");
        elemField.setXmlName(new javax.xml.namespace.QName("http://facility.ob.common.sml.integrosys.com", "contractDate"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "dateTime"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("creditLimitFromMof");
        elemField.setXmlName(new javax.xml.namespace.QName("http://facility.ob.common.sml.integrosys.com", "creditLimitFromMof"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "decimal"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("customerSegment");
        elemField.setXmlName(new javax.xml.namespace.QName("http://facility.ob.common.sml.integrosys.com", "customerSegment"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("disburstAccountName");
        elemField.setXmlName(new javax.xml.namespace.QName("http://facility.ob.common.sml.integrosys.com", "disburstAccountName"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("disburstAccountNo");
        elemField.setXmlName(new javax.xml.namespace.QName("http://facility.ob.common.sml.integrosys.com", "disburstAccountNo"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("disburstBankName");
        elemField.setXmlName(new javax.xml.namespace.QName("http://facility.ob.common.sml.integrosys.com", "disburstBankName"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("existLimit");
        elemField.setXmlName(new javax.xml.namespace.QName("http://facility.ob.common.sml.integrosys.com", "existLimit"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "decimal"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("existLoan");
        elemField.setXmlName(new javax.xml.namespace.QName("http://facility.ob.common.sml.integrosys.com", "existLoan"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("existMaxCreditLimit");
        elemField.setXmlName(new javax.xml.namespace.QName("http://facility.ob.common.sml.integrosys.com", "existMaxCreditLimit"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "decimal"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("existingAccountNo");
        elemField.setXmlName(new javax.xml.namespace.QName("http://facility.ob.common.sml.integrosys.com", "existingAccountNo"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("existingCreditLimit");
        elemField.setXmlName(new javax.xml.namespace.QName("http://facility.ob.common.sml.integrosys.com", "existingCreditLimit"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "decimal"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("existingOsBalance");
        elemField.setXmlName(new javax.xml.namespace.QName("http://facility.ob.common.sml.integrosys.com", "existingOsBalance"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "decimal"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("facilityCode");
        elemField.setXmlName(new javax.xml.namespace.QName("http://facility.ob.common.sml.integrosys.com", "facilityCode"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("facilityPurchaseCode");
        elemField.setXmlName(new javax.xml.namespace.QName("http://facility.ob.common.sml.integrosys.com", "facilityPurchaseCode"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("facilityPurchaseDesc");
        elemField.setXmlName(new javax.xml.namespace.QName("http://facility.ob.common.sml.integrosys.com", "facilityPurchaseDesc"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("facilitySavedFlag");
        elemField.setXmlName(new javax.xml.namespace.QName("http://facility.ob.common.sml.integrosys.com", "facilitySavedFlag"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("facilityStatus");
        elemField.setXmlName(new javax.xml.namespace.QName("http://facility.ob.common.sml.integrosys.com", "facilityStatus"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("feature");
        elemField.setXmlName(new javax.xml.namespace.QName("http://facility.ob.common.sml.integrosys.com", "feature"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://feature.ob.common.sml.integrosys.com", "Feature"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("featureType");
        elemField.setXmlName(new javax.xml.namespace.QName("http://facility.ob.common.sml.integrosys.com", "featureType"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("financialInstitution");
        elemField.setXmlName(new javax.xml.namespace.QName("http://facility.ob.common.sml.integrosys.com", "financialInstitution"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("financialInstitutionName");
        elemField.setXmlName(new javax.xml.namespace.QName("http://facility.ob.common.sml.integrosys.com", "financialInstitutionName"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("firstPaymentDueDate");
        elemField.setXmlName(new javax.xml.namespace.QName("http://facility.ob.common.sml.integrosys.com", "firstPaymentDueDate"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("hostAaNo");
        elemField.setXmlName(new javax.xml.namespace.QName("http://facility.ob.common.sml.integrosys.com", "hostAaNo"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("hostProductCode");
        elemField.setXmlName(new javax.xml.namespace.QName("http://facility.ob.common.sml.integrosys.com", "hostProductCode"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("hostProjectCode");
        elemField.setXmlName(new javax.xml.namespace.QName("http://facility.ob.common.sml.integrosys.com", "hostProjectCode"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("id");
        elemField.setXmlName(new javax.xml.namespace.QName("http://facility.ob.common.sml.integrosys.com", "id"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "decimal"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("interestRate");
        elemField.setXmlName(new javax.xml.namespace.QName("http://facility.ob.common.sml.integrosys.com", "interestRate"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "decimal"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("limitApplied");
        elemField.setXmlName(new javax.xml.namespace.QName("http://facility.ob.common.sml.integrosys.com", "limitApplied"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "decimal"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("loanReqMax");
        elemField.setXmlName(new javax.xml.namespace.QName("http://facility.ob.common.sml.integrosys.com", "loanReqMax"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "decimal"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("loanReqMin");
        elemField.setXmlName(new javax.xml.namespace.QName("http://facility.ob.common.sml.integrosys.com", "loanReqMin"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "decimal"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("loanWithOtherBank");
        elemField.setXmlName(new javax.xml.namespace.QName("http://facility.ob.common.sml.integrosys.com", "loanWithOtherBank"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("mailingPreference");
        elemField.setXmlName(new javax.xml.namespace.QName("http://facility.ob.common.sml.integrosys.com", "mailingPreference"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("modelName");
        elemField.setXmlName(new javax.xml.namespace.QName("http://facility.ob.common.sml.integrosys.com", "modelName"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("modelScore");
        elemField.setXmlName(new javax.xml.namespace.QName("http://facility.ob.common.sml.integrosys.com", "modelScore"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "decimal"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("mofDocumentCode");
        elemField.setXmlName(new javax.xml.namespace.QName("http://facility.ob.common.sml.integrosys.com", "mofDocumentCode"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("mofLoanID");
        elemField.setXmlName(new javax.xml.namespace.QName("http://facility.ob.common.sml.integrosys.com", "mofLoanID"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("monthlyInstall");
        elemField.setXmlName(new javax.xml.namespace.QName("http://facility.ob.common.sml.integrosys.com", "monthlyInstall"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "decimal"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("monthlyInstallment");
        elemField.setXmlName(new javax.xml.namespace.QName("http://facility.ob.common.sml.integrosys.com", "monthlyInstallment"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "decimal"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("mrtaAmount");
        elemField.setXmlName(new javax.xml.namespace.QName("http://facility.ob.common.sml.integrosys.com", "mrtaAmount"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "decimal"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("mrtaFlag");
        elemField.setXmlName(new javax.xml.namespace.QName("http://facility.ob.common.sml.integrosys.com", "mrtaFlag"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("mrtaIncludeInLoanAmountFlag");
        elemField.setXmlName(new javax.xml.namespace.QName("http://facility.ob.common.sml.integrosys.com", "mrtaIncludeInLoanAmountFlag"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("mrtaSumInsured");
        elemField.setXmlName(new javax.xml.namespace.QName("http://facility.ob.common.sml.integrosys.com", "mrtaSumInsured"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "decimal"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("mrtaYrsCoverage");
        elemField.setXmlName(new javax.xml.namespace.QName("http://facility.ob.common.sml.integrosys.com", "mrtaYrsCoverage"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "decimal"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("osLimit");
        elemField.setXmlName(new javax.xml.namespace.QName("http://facility.ob.common.sml.integrosys.com", "osLimit"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "decimal"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("outStandingBalance");
        elemField.setXmlName(new javax.xml.namespace.QName("http://facility.ob.common.sml.integrosys.com", "outStandingBalance"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "decimal"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("payMethodCriteria");
        elemField.setXmlName(new javax.xml.namespace.QName("http://facility.ob.common.sml.integrosys.com", "payMethodCriteria"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("paymentAccountName");
        elemField.setXmlName(new javax.xml.namespace.QName("http://facility.ob.common.sml.integrosys.com", "paymentAccountName"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("paymentAccountNo");
        elemField.setXmlName(new javax.xml.namespace.QName("http://facility.ob.common.sml.integrosys.com", "paymentAccountNo"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("paymentDueDate");
        elemField.setXmlName(new javax.xml.namespace.QName("http://facility.ob.common.sml.integrosys.com", "paymentDueDate"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("paymentMethod");
        elemField.setXmlName(new javax.xml.namespace.QName("http://facility.ob.common.sml.integrosys.com", "paymentMethod"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("pricings");
        elemField.setXmlName(new javax.xml.namespace.QName("http://facility.ob.common.sml.integrosys.com", "pricings"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://pricing.ob.common.sml.integrosys.com", "Pricing"));
        elemField.setNillable(true);
        elemField.setItemQName(new javax.xml.namespace.QName("http://submission.loan.ws.sml.integrosys.com", "item"));
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("productCode");
        elemField.setXmlName(new javax.xml.namespace.QName("http://facility.ob.common.sml.integrosys.com", "productCode"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("rePayMent");
        elemField.setXmlName(new javax.xml.namespace.QName("http://facility.ob.common.sml.integrosys.com", "rePayMent"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("rfContractDate");
        elemField.setXmlName(new javax.xml.namespace.QName("http://facility.ob.common.sml.integrosys.com", "rfContractDate"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "dateTime"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("saleChannelCode");
        elemField.setXmlName(new javax.xml.namespace.QName("http://facility.ob.common.sml.integrosys.com", "saleChannelCode"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("saleChannelFlag");
        elemField.setXmlName(new javax.xml.namespace.QName("http://facility.ob.common.sml.integrosys.com", "saleChannelFlag"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("sameGroup");
        elemField.setXmlName(new javax.xml.namespace.QName("http://facility.ob.common.sml.integrosys.com", "sameGroup"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("sourceOfData");
        elemField.setXmlName(new javax.xml.namespace.QName("http://facility.ob.common.sml.integrosys.com", "sourceOfData"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("suffix");
        elemField.setXmlName(new javax.xml.namespace.QName("http://facility.ob.common.sml.integrosys.com", "suffix"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("tenure");
        elemField.setXmlName(new javax.xml.namespace.QName("http://facility.ob.common.sml.integrosys.com", "tenure"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "decimal"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("totalCreditLimit");
        elemField.setXmlName(new javax.xml.namespace.QName("http://facility.ob.common.sml.integrosys.com", "totalCreditLimit"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "decimal"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("totalRepaymentAmount");
        elemField.setXmlName(new javax.xml.namespace.QName("http://facility.ob.common.sml.integrosys.com", "totalRepaymentAmount"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "decimal"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
    }

    /**
     * Return type metadata object
     */
    public static org.apache.axis.description.TypeDesc getTypeDesc() {
        return typeDesc;
    }

    /**
     * Get Custom Serializer
     */
    public static org.apache.axis.encoding.Serializer getSerializer(
           java.lang.String mechType, 
           java.lang.Class _javaType,  
           javax.xml.namespace.QName _xmlType) {
        return 
          new  org.apache.axis.encoding.ser.BeanSerializer(
            _javaType, _xmlType, typeDesc);
    }

    /**
     * Get Custom Deserializer
     */
    public static org.apache.axis.encoding.Deserializer getDeserializer(
           java.lang.String mechType, 
           java.lang.Class _javaType,  
           javax.xml.namespace.QName _xmlType) {
        return 
          new  org.apache.axis.encoding.ser.BeanDeserializer(
            _javaType, _xmlType, typeDesc);
    }

}
