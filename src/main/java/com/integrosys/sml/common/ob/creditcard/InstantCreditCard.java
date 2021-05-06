/**
 * InstantCreditCard.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.integrosys.sml.common.ob.creditcard;

public class InstantCreditCard  implements java.io.Serializable {
    private java.math.BigDecimal caId;

    private java.lang.String campaignCode;

    private java.util.Calendar campaignExpireDate;

    private java.util.Calendar campaignStartDate;

    private java.lang.String cardBrand;

    private java.lang.String cardDeliveryAddress;

    private java.lang.String cardInd;

    private java.math.BigDecimal cifId;

    private java.lang.String creditcardSavedFlag;

    private java.lang.String custSegment;

    private java.lang.String existCardName;

    private java.lang.String existCardNumber;

    private java.lang.String existCardStatus;

    private java.math.BigDecimal existCreditLimit;

    private java.math.BigDecimal existCreditLine;

    private java.lang.String existProductType;

    private com.integrosys.sml.common.ob.feature.Feature feature;

    private java.lang.String featureType;

    private java.math.BigDecimal id;

    private java.math.BigDecimal loanReqMax;

    private java.math.BigDecimal loanReqMin;

    private java.lang.String mailPreference;

    private java.lang.String modelName;

    private java.math.BigDecimal modelScore;

    private java.math.BigDecimal newCreditLimit;

    private java.lang.String paymentMethod;

    private com.integrosys.sml.common.ob.pricing.Pricing[] pricings;

    private java.lang.String productCode;

    private java.lang.String productType;

    private java.lang.String projectCode;

    private java.math.BigDecimal requestCreditLimit;

    private java.lang.String saleChannelCode;

    private java.lang.String saleChannelFlag;

    private java.lang.String sourceOfData;

    public InstantCreditCard() {
    }

    public InstantCreditCard(
           java.math.BigDecimal caId,
           java.lang.String campaignCode,
           java.util.Calendar campaignExpireDate,
           java.util.Calendar campaignStartDate,
           java.lang.String cardBrand,
           java.lang.String cardDeliveryAddress,
           java.lang.String cardInd,
           java.math.BigDecimal cifId,
           java.lang.String creditcardSavedFlag,
           java.lang.String custSegment,
           java.lang.String existCardName,
           java.lang.String existCardNumber,
           java.lang.String existCardStatus,
           java.math.BigDecimal existCreditLimit,
           java.math.BigDecimal existCreditLine,
           java.lang.String existProductType,
           com.integrosys.sml.common.ob.feature.Feature feature,
           java.lang.String featureType,
           java.math.BigDecimal id,
           java.math.BigDecimal loanReqMax,
           java.math.BigDecimal loanReqMin,
           java.lang.String mailPreference,
           java.lang.String modelName,
           java.math.BigDecimal modelScore,
           java.math.BigDecimal newCreditLimit,
           java.lang.String paymentMethod,
           com.integrosys.sml.common.ob.pricing.Pricing[] pricings,
           java.lang.String productCode,
           java.lang.String productType,
           java.lang.String projectCode,
           java.math.BigDecimal requestCreditLimit,
           java.lang.String saleChannelCode,
           java.lang.String saleChannelFlag,
           java.lang.String sourceOfData) {
           this.caId = caId;
           this.campaignCode = campaignCode;
           this.campaignExpireDate = campaignExpireDate;
           this.campaignStartDate = campaignStartDate;
           this.cardBrand = cardBrand;
           this.cardDeliveryAddress = cardDeliveryAddress;
           this.cardInd = cardInd;
           this.cifId = cifId;
           this.creditcardSavedFlag = creditcardSavedFlag;
           this.custSegment = custSegment;
           this.existCardName = existCardName;
           this.existCardNumber = existCardNumber;
           this.existCardStatus = existCardStatus;
           this.existCreditLimit = existCreditLimit;
           this.existCreditLine = existCreditLine;
           this.existProductType = existProductType;
           this.feature = feature;
           this.featureType = featureType;
           this.id = id;
           this.loanReqMax = loanReqMax;
           this.loanReqMin = loanReqMin;
           this.mailPreference = mailPreference;
           this.modelName = modelName;
           this.modelScore = modelScore;
           this.newCreditLimit = newCreditLimit;
           this.paymentMethod = paymentMethod;
           this.pricings = pricings;
           this.productCode = productCode;
           this.productType = productType;
           this.projectCode = projectCode;
           this.requestCreditLimit = requestCreditLimit;
           this.saleChannelCode = saleChannelCode;
           this.saleChannelFlag = saleChannelFlag;
           this.sourceOfData = sourceOfData;
    }


    /**
     * Gets the caId value for this InstantCreditCard.
     * 
     * @return caId
     */
    public java.math.BigDecimal getCaId() {
        return caId;
    }


    /**
     * Sets the caId value for this InstantCreditCard.
     * 
     * @param caId
     */
    public void setCaId(java.math.BigDecimal caId) {
        this.caId = caId;
    }


    /**
     * Gets the campaignCode value for this InstantCreditCard.
     * 
     * @return campaignCode
     */
    public java.lang.String getCampaignCode() {
        return campaignCode;
    }


    /**
     * Sets the campaignCode value for this InstantCreditCard.
     * 
     * @param campaignCode
     */
    public void setCampaignCode(java.lang.String campaignCode) {
        this.campaignCode = campaignCode;
    }


    /**
     * Gets the campaignExpireDate value for this InstantCreditCard.
     * 
     * @return campaignExpireDate
     */
    public java.util.Calendar getCampaignExpireDate() {
        return campaignExpireDate;
    }


    /**
     * Sets the campaignExpireDate value for this InstantCreditCard.
     * 
     * @param campaignExpireDate
     */
    public void setCampaignExpireDate(java.util.Calendar campaignExpireDate) {
        this.campaignExpireDate = campaignExpireDate;
    }


    /**
     * Gets the campaignStartDate value for this InstantCreditCard.
     * 
     * @return campaignStartDate
     */
    public java.util.Calendar getCampaignStartDate() {
        return campaignStartDate;
    }


    /**
     * Sets the campaignStartDate value for this InstantCreditCard.
     * 
     * @param campaignStartDate
     */
    public void setCampaignStartDate(java.util.Calendar campaignStartDate) {
        this.campaignStartDate = campaignStartDate;
    }


    /**
     * Gets the cardBrand value for this InstantCreditCard.
     * 
     * @return cardBrand
     */
    public java.lang.String getCardBrand() {
        return cardBrand;
    }


    /**
     * Sets the cardBrand value for this InstantCreditCard.
     * 
     * @param cardBrand
     */
    public void setCardBrand(java.lang.String cardBrand) {
        this.cardBrand = cardBrand;
    }


    /**
     * Gets the cardDeliveryAddress value for this InstantCreditCard.
     * 
     * @return cardDeliveryAddress
     */
    public java.lang.String getCardDeliveryAddress() {
        return cardDeliveryAddress;
    }


    /**
     * Sets the cardDeliveryAddress value for this InstantCreditCard.
     * 
     * @param cardDeliveryAddress
     */
    public void setCardDeliveryAddress(java.lang.String cardDeliveryAddress) {
        this.cardDeliveryAddress = cardDeliveryAddress;
    }


    /**
     * Gets the cardInd value for this InstantCreditCard.
     * 
     * @return cardInd
     */
    public java.lang.String getCardInd() {
        return cardInd;
    }


    /**
     * Sets the cardInd value for this InstantCreditCard.
     * 
     * @param cardInd
     */
    public void setCardInd(java.lang.String cardInd) {
        this.cardInd = cardInd;
    }


    /**
     * Gets the cifId value for this InstantCreditCard.
     * 
     * @return cifId
     */
    public java.math.BigDecimal getCifId() {
        return cifId;
    }


    /**
     * Sets the cifId value for this InstantCreditCard.
     * 
     * @param cifId
     */
    public void setCifId(java.math.BigDecimal cifId) {
        this.cifId = cifId;
    }


    /**
     * Gets the creditcardSavedFlag value for this InstantCreditCard.
     * 
     * @return creditcardSavedFlag
     */
    public java.lang.String getCreditcardSavedFlag() {
        return creditcardSavedFlag;
    }


    /**
     * Sets the creditcardSavedFlag value for this InstantCreditCard.
     * 
     * @param creditcardSavedFlag
     */
    public void setCreditcardSavedFlag(java.lang.String creditcardSavedFlag) {
        this.creditcardSavedFlag = creditcardSavedFlag;
    }


    /**
     * Gets the custSegment value for this InstantCreditCard.
     * 
     * @return custSegment
     */
    public java.lang.String getCustSegment() {
        return custSegment;
    }


    /**
     * Sets the custSegment value for this InstantCreditCard.
     * 
     * @param custSegment
     */
    public void setCustSegment(java.lang.String custSegment) {
        this.custSegment = custSegment;
    }


    /**
     * Gets the existCardName value for this InstantCreditCard.
     * 
     * @return existCardName
     */
    public java.lang.String getExistCardName() {
        return existCardName;
    }


    /**
     * Sets the existCardName value for this InstantCreditCard.
     * 
     * @param existCardName
     */
    public void setExistCardName(java.lang.String existCardName) {
        this.existCardName = existCardName;
    }


    /**
     * Gets the existCardNumber value for this InstantCreditCard.
     * 
     * @return existCardNumber
     */
    public java.lang.String getExistCardNumber() {
        return existCardNumber;
    }


    /**
     * Sets the existCardNumber value for this InstantCreditCard.
     * 
     * @param existCardNumber
     */
    public void setExistCardNumber(java.lang.String existCardNumber) {
        this.existCardNumber = existCardNumber;
    }


    /**
     * Gets the existCardStatus value for this InstantCreditCard.
     * 
     * @return existCardStatus
     */
    public java.lang.String getExistCardStatus() {
        return existCardStatus;
    }


    /**
     * Sets the existCardStatus value for this InstantCreditCard.
     * 
     * @param existCardStatus
     */
    public void setExistCardStatus(java.lang.String existCardStatus) {
        this.existCardStatus = existCardStatus;
    }


    /**
     * Gets the existCreditLimit value for this InstantCreditCard.
     * 
     * @return existCreditLimit
     */
    public java.math.BigDecimal getExistCreditLimit() {
        return existCreditLimit;
    }


    /**
     * Sets the existCreditLimit value for this InstantCreditCard.
     * 
     * @param existCreditLimit
     */
    public void setExistCreditLimit(java.math.BigDecimal existCreditLimit) {
        this.existCreditLimit = existCreditLimit;
    }


    /**
     * Gets the existCreditLine value for this InstantCreditCard.
     * 
     * @return existCreditLine
     */
    public java.math.BigDecimal getExistCreditLine() {
        return existCreditLine;
    }


    /**
     * Sets the existCreditLine value for this InstantCreditCard.
     * 
     * @param existCreditLine
     */
    public void setExistCreditLine(java.math.BigDecimal existCreditLine) {
        this.existCreditLine = existCreditLine;
    }


    /**
     * Gets the existProductType value for this InstantCreditCard.
     * 
     * @return existProductType
     */
    public java.lang.String getExistProductType() {
        return existProductType;
    }


    /**
     * Sets the existProductType value for this InstantCreditCard.
     * 
     * @param existProductType
     */
    public void setExistProductType(java.lang.String existProductType) {
        this.existProductType = existProductType;
    }


    /**
     * Gets the feature value for this InstantCreditCard.
     * 
     * @return feature
     */
    public com.integrosys.sml.common.ob.feature.Feature getFeature() {
        return feature;
    }


    /**
     * Sets the feature value for this InstantCreditCard.
     * 
     * @param feature
     */
    public void setFeature(com.integrosys.sml.common.ob.feature.Feature feature) {
        this.feature = feature;
    }


    /**
     * Gets the featureType value for this InstantCreditCard.
     * 
     * @return featureType
     */
    public java.lang.String getFeatureType() {
        return featureType;
    }


    /**
     * Sets the featureType value for this InstantCreditCard.
     * 
     * @param featureType
     */
    public void setFeatureType(java.lang.String featureType) {
        this.featureType = featureType;
    }


    /**
     * Gets the id value for this InstantCreditCard.
     * 
     * @return id
     */
    public java.math.BigDecimal getId() {
        return id;
    }


    /**
     * Sets the id value for this InstantCreditCard.
     * 
     * @param id
     */
    public void setId(java.math.BigDecimal id) {
        this.id = id;
    }


    /**
     * Gets the loanReqMax value for this InstantCreditCard.
     * 
     * @return loanReqMax
     */
    public java.math.BigDecimal getLoanReqMax() {
        return loanReqMax;
    }


    /**
     * Sets the loanReqMax value for this InstantCreditCard.
     * 
     * @param loanReqMax
     */
    public void setLoanReqMax(java.math.BigDecimal loanReqMax) {
        this.loanReqMax = loanReqMax;
    }


    /**
     * Gets the loanReqMin value for this InstantCreditCard.
     * 
     * @return loanReqMin
     */
    public java.math.BigDecimal getLoanReqMin() {
        return loanReqMin;
    }


    /**
     * Sets the loanReqMin value for this InstantCreditCard.
     * 
     * @param loanReqMin
     */
    public void setLoanReqMin(java.math.BigDecimal loanReqMin) {
        this.loanReqMin = loanReqMin;
    }


    /**
     * Gets the mailPreference value for this InstantCreditCard.
     * 
     * @return mailPreference
     */
    public java.lang.String getMailPreference() {
        return mailPreference;
    }


    /**
     * Sets the mailPreference value for this InstantCreditCard.
     * 
     * @param mailPreference
     */
    public void setMailPreference(java.lang.String mailPreference) {
        this.mailPreference = mailPreference;
    }


    /**
     * Gets the modelName value for this InstantCreditCard.
     * 
     * @return modelName
     */
    public java.lang.String getModelName() {
        return modelName;
    }


    /**
     * Sets the modelName value for this InstantCreditCard.
     * 
     * @param modelName
     */
    public void setModelName(java.lang.String modelName) {
        this.modelName = modelName;
    }


    /**
     * Gets the modelScore value for this InstantCreditCard.
     * 
     * @return modelScore
     */
    public java.math.BigDecimal getModelScore() {
        return modelScore;
    }


    /**
     * Sets the modelScore value for this InstantCreditCard.
     * 
     * @param modelScore
     */
    public void setModelScore(java.math.BigDecimal modelScore) {
        this.modelScore = modelScore;
    }


    /**
     * Gets the newCreditLimit value for this InstantCreditCard.
     * 
     * @return newCreditLimit
     */
    public java.math.BigDecimal getNewCreditLimit() {
        return newCreditLimit;
    }


    /**
     * Sets the newCreditLimit value for this InstantCreditCard.
     * 
     * @param newCreditLimit
     */
    public void setNewCreditLimit(java.math.BigDecimal newCreditLimit) {
        this.newCreditLimit = newCreditLimit;
    }


    /**
     * Gets the paymentMethod value for this InstantCreditCard.
     * 
     * @return paymentMethod
     */
    public java.lang.String getPaymentMethod() {
        return paymentMethod;
    }


    /**
     * Sets the paymentMethod value for this InstantCreditCard.
     * 
     * @param paymentMethod
     */
    public void setPaymentMethod(java.lang.String paymentMethod) {
        this.paymentMethod = paymentMethod;
    }


    /**
     * Gets the pricings value for this InstantCreditCard.
     * 
     * @return pricings
     */
    public com.integrosys.sml.common.ob.pricing.Pricing[] getPricings() {
        return pricings;
    }


    /**
     * Sets the pricings value for this InstantCreditCard.
     * 
     * @param pricings
     */
    public void setPricings(com.integrosys.sml.common.ob.pricing.Pricing[] pricings) {
        this.pricings = pricings;
    }


    /**
     * Gets the productCode value for this InstantCreditCard.
     * 
     * @return productCode
     */
    public java.lang.String getProductCode() {
        return productCode;
    }


    /**
     * Sets the productCode value for this InstantCreditCard.
     * 
     * @param productCode
     */
    public void setProductCode(java.lang.String productCode) {
        this.productCode = productCode;
    }


    /**
     * Gets the productType value for this InstantCreditCard.
     * 
     * @return productType
     */
    public java.lang.String getProductType() {
        return productType;
    }


    /**
     * Sets the productType value for this InstantCreditCard.
     * 
     * @param productType
     */
    public void setProductType(java.lang.String productType) {
        this.productType = productType;
    }


    /**
     * Gets the projectCode value for this InstantCreditCard.
     * 
     * @return projectCode
     */
    public java.lang.String getProjectCode() {
        return projectCode;
    }


    /**
     * Sets the projectCode value for this InstantCreditCard.
     * 
     * @param projectCode
     */
    public void setProjectCode(java.lang.String projectCode) {
        this.projectCode = projectCode;
    }


    /**
     * Gets the requestCreditLimit value for this InstantCreditCard.
     * 
     * @return requestCreditLimit
     */
    public java.math.BigDecimal getRequestCreditLimit() {
        return requestCreditLimit;
    }


    /**
     * Sets the requestCreditLimit value for this InstantCreditCard.
     * 
     * @param requestCreditLimit
     */
    public void setRequestCreditLimit(java.math.BigDecimal requestCreditLimit) {
        this.requestCreditLimit = requestCreditLimit;
    }


    /**
     * Gets the saleChannelCode value for this InstantCreditCard.
     * 
     * @return saleChannelCode
     */
    public java.lang.String getSaleChannelCode() {
        return saleChannelCode;
    }


    /**
     * Sets the saleChannelCode value for this InstantCreditCard.
     * 
     * @param saleChannelCode
     */
    public void setSaleChannelCode(java.lang.String saleChannelCode) {
        this.saleChannelCode = saleChannelCode;
    }


    /**
     * Gets the saleChannelFlag value for this InstantCreditCard.
     * 
     * @return saleChannelFlag
     */
    public java.lang.String getSaleChannelFlag() {
        return saleChannelFlag;
    }


    /**
     * Sets the saleChannelFlag value for this InstantCreditCard.
     * 
     * @param saleChannelFlag
     */
    public void setSaleChannelFlag(java.lang.String saleChannelFlag) {
        this.saleChannelFlag = saleChannelFlag;
    }


    /**
     * Gets the sourceOfData value for this InstantCreditCard.
     * 
     * @return sourceOfData
     */
    public java.lang.String getSourceOfData() {
        return sourceOfData;
    }


    /**
     * Sets the sourceOfData value for this InstantCreditCard.
     * 
     * @param sourceOfData
     */
    public void setSourceOfData(java.lang.String sourceOfData) {
        this.sourceOfData = sourceOfData;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof InstantCreditCard)) return false;
        InstantCreditCard other = (InstantCreditCard) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.caId==null && other.getCaId()==null) || 
             (this.caId!=null &&
              this.caId.equals(other.getCaId()))) &&
            ((this.campaignCode==null && other.getCampaignCode()==null) || 
             (this.campaignCode!=null &&
              this.campaignCode.equals(other.getCampaignCode()))) &&
            ((this.campaignExpireDate==null && other.getCampaignExpireDate()==null) || 
             (this.campaignExpireDate!=null &&
              this.campaignExpireDate.equals(other.getCampaignExpireDate()))) &&
            ((this.campaignStartDate==null && other.getCampaignStartDate()==null) || 
             (this.campaignStartDate!=null &&
              this.campaignStartDate.equals(other.getCampaignStartDate()))) &&
            ((this.cardBrand==null && other.getCardBrand()==null) || 
             (this.cardBrand!=null &&
              this.cardBrand.equals(other.getCardBrand()))) &&
            ((this.cardDeliveryAddress==null && other.getCardDeliveryAddress()==null) || 
             (this.cardDeliveryAddress!=null &&
              this.cardDeliveryAddress.equals(other.getCardDeliveryAddress()))) &&
            ((this.cardInd==null && other.getCardInd()==null) || 
             (this.cardInd!=null &&
              this.cardInd.equals(other.getCardInd()))) &&
            ((this.cifId==null && other.getCifId()==null) || 
             (this.cifId!=null &&
              this.cifId.equals(other.getCifId()))) &&
            ((this.creditcardSavedFlag==null && other.getCreditcardSavedFlag()==null) || 
             (this.creditcardSavedFlag!=null &&
              this.creditcardSavedFlag.equals(other.getCreditcardSavedFlag()))) &&
            ((this.custSegment==null && other.getCustSegment()==null) || 
             (this.custSegment!=null &&
              this.custSegment.equals(other.getCustSegment()))) &&
            ((this.existCardName==null && other.getExistCardName()==null) || 
             (this.existCardName!=null &&
              this.existCardName.equals(other.getExistCardName()))) &&
            ((this.existCardNumber==null && other.getExistCardNumber()==null) || 
             (this.existCardNumber!=null &&
              this.existCardNumber.equals(other.getExistCardNumber()))) &&
            ((this.existCardStatus==null && other.getExistCardStatus()==null) || 
             (this.existCardStatus!=null &&
              this.existCardStatus.equals(other.getExistCardStatus()))) &&
            ((this.existCreditLimit==null && other.getExistCreditLimit()==null) || 
             (this.existCreditLimit!=null &&
              this.existCreditLimit.equals(other.getExistCreditLimit()))) &&
            ((this.existCreditLine==null && other.getExistCreditLine()==null) || 
             (this.existCreditLine!=null &&
              this.existCreditLine.equals(other.getExistCreditLine()))) &&
            ((this.existProductType==null && other.getExistProductType()==null) || 
             (this.existProductType!=null &&
              this.existProductType.equals(other.getExistProductType()))) &&
            ((this.feature==null && other.getFeature()==null) || 
             (this.feature!=null &&
              this.feature.equals(other.getFeature()))) &&
            ((this.featureType==null && other.getFeatureType()==null) || 
             (this.featureType!=null &&
              this.featureType.equals(other.getFeatureType()))) &&
            ((this.id==null && other.getId()==null) || 
             (this.id!=null &&
              this.id.equals(other.getId()))) &&
            ((this.loanReqMax==null && other.getLoanReqMax()==null) || 
             (this.loanReqMax!=null &&
              this.loanReqMax.equals(other.getLoanReqMax()))) &&
            ((this.loanReqMin==null && other.getLoanReqMin()==null) || 
             (this.loanReqMin!=null &&
              this.loanReqMin.equals(other.getLoanReqMin()))) &&
            ((this.mailPreference==null && other.getMailPreference()==null) || 
             (this.mailPreference!=null &&
              this.mailPreference.equals(other.getMailPreference()))) &&
            ((this.modelName==null && other.getModelName()==null) || 
             (this.modelName!=null &&
              this.modelName.equals(other.getModelName()))) &&
            ((this.modelScore==null && other.getModelScore()==null) || 
             (this.modelScore!=null &&
              this.modelScore.equals(other.getModelScore()))) &&
            ((this.newCreditLimit==null && other.getNewCreditLimit()==null) || 
             (this.newCreditLimit!=null &&
              this.newCreditLimit.equals(other.getNewCreditLimit()))) &&
            ((this.paymentMethod==null && other.getPaymentMethod()==null) || 
             (this.paymentMethod!=null &&
              this.paymentMethod.equals(other.getPaymentMethod()))) &&
            ((this.pricings==null && other.getPricings()==null) || 
             (this.pricings!=null &&
              java.util.Arrays.equals(this.pricings, other.getPricings()))) &&
            ((this.productCode==null && other.getProductCode()==null) || 
             (this.productCode!=null &&
              this.productCode.equals(other.getProductCode()))) &&
            ((this.productType==null && other.getProductType()==null) || 
             (this.productType!=null &&
              this.productType.equals(other.getProductType()))) &&
            ((this.projectCode==null && other.getProjectCode()==null) || 
             (this.projectCode!=null &&
              this.projectCode.equals(other.getProjectCode()))) &&
            ((this.requestCreditLimit==null && other.getRequestCreditLimit()==null) || 
             (this.requestCreditLimit!=null &&
              this.requestCreditLimit.equals(other.getRequestCreditLimit()))) &&
            ((this.saleChannelCode==null && other.getSaleChannelCode()==null) || 
             (this.saleChannelCode!=null &&
              this.saleChannelCode.equals(other.getSaleChannelCode()))) &&
            ((this.saleChannelFlag==null && other.getSaleChannelFlag()==null) || 
             (this.saleChannelFlag!=null &&
              this.saleChannelFlag.equals(other.getSaleChannelFlag()))) &&
            ((this.sourceOfData==null && other.getSourceOfData()==null) || 
             (this.sourceOfData!=null &&
              this.sourceOfData.equals(other.getSourceOfData())));
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
        if (getCaId() != null) {
            _hashCode += getCaId().hashCode();
        }
        if (getCampaignCode() != null) {
            _hashCode += getCampaignCode().hashCode();
        }
        if (getCampaignExpireDate() != null) {
            _hashCode += getCampaignExpireDate().hashCode();
        }
        if (getCampaignStartDate() != null) {
            _hashCode += getCampaignStartDate().hashCode();
        }
        if (getCardBrand() != null) {
            _hashCode += getCardBrand().hashCode();
        }
        if (getCardDeliveryAddress() != null) {
            _hashCode += getCardDeliveryAddress().hashCode();
        }
        if (getCardInd() != null) {
            _hashCode += getCardInd().hashCode();
        }
        if (getCifId() != null) {
            _hashCode += getCifId().hashCode();
        }
        if (getCreditcardSavedFlag() != null) {
            _hashCode += getCreditcardSavedFlag().hashCode();
        }
        if (getCustSegment() != null) {
            _hashCode += getCustSegment().hashCode();
        }
        if (getExistCardName() != null) {
            _hashCode += getExistCardName().hashCode();
        }
        if (getExistCardNumber() != null) {
            _hashCode += getExistCardNumber().hashCode();
        }
        if (getExistCardStatus() != null) {
            _hashCode += getExistCardStatus().hashCode();
        }
        if (getExistCreditLimit() != null) {
            _hashCode += getExistCreditLimit().hashCode();
        }
        if (getExistCreditLine() != null) {
            _hashCode += getExistCreditLine().hashCode();
        }
        if (getExistProductType() != null) {
            _hashCode += getExistProductType().hashCode();
        }
        if (getFeature() != null) {
            _hashCode += getFeature().hashCode();
        }
        if (getFeatureType() != null) {
            _hashCode += getFeatureType().hashCode();
        }
        if (getId() != null) {
            _hashCode += getId().hashCode();
        }
        if (getLoanReqMax() != null) {
            _hashCode += getLoanReqMax().hashCode();
        }
        if (getLoanReqMin() != null) {
            _hashCode += getLoanReqMin().hashCode();
        }
        if (getMailPreference() != null) {
            _hashCode += getMailPreference().hashCode();
        }
        if (getModelName() != null) {
            _hashCode += getModelName().hashCode();
        }
        if (getModelScore() != null) {
            _hashCode += getModelScore().hashCode();
        }
        if (getNewCreditLimit() != null) {
            _hashCode += getNewCreditLimit().hashCode();
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
        if (getProductType() != null) {
            _hashCode += getProductType().hashCode();
        }
        if (getProjectCode() != null) {
            _hashCode += getProjectCode().hashCode();
        }
        if (getRequestCreditLimit() != null) {
            _hashCode += getRequestCreditLimit().hashCode();
        }
        if (getSaleChannelCode() != null) {
            _hashCode += getSaleChannelCode().hashCode();
        }
        if (getSaleChannelFlag() != null) {
            _hashCode += getSaleChannelFlag().hashCode();
        }
        if (getSourceOfData() != null) {
            _hashCode += getSourceOfData().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(InstantCreditCard.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://creditcard.ob.common.sml.integrosys.com", "InstantCreditCard"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("caId");
        elemField.setXmlName(new javax.xml.namespace.QName("http://creditcard.ob.common.sml.integrosys.com", "caId"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "decimal"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("campaignCode");
        elemField.setXmlName(new javax.xml.namespace.QName("http://creditcard.ob.common.sml.integrosys.com", "campaignCode"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("campaignExpireDate");
        elemField.setXmlName(new javax.xml.namespace.QName("http://creditcard.ob.common.sml.integrosys.com", "campaignExpireDate"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "dateTime"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("campaignStartDate");
        elemField.setXmlName(new javax.xml.namespace.QName("http://creditcard.ob.common.sml.integrosys.com", "campaignStartDate"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "dateTime"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("cardBrand");
        elemField.setXmlName(new javax.xml.namespace.QName("http://creditcard.ob.common.sml.integrosys.com", "cardBrand"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("cardDeliveryAddress");
        elemField.setXmlName(new javax.xml.namespace.QName("http://creditcard.ob.common.sml.integrosys.com", "cardDeliveryAddress"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("cardInd");
        elemField.setXmlName(new javax.xml.namespace.QName("http://creditcard.ob.common.sml.integrosys.com", "cardInd"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("cifId");
        elemField.setXmlName(new javax.xml.namespace.QName("http://creditcard.ob.common.sml.integrosys.com", "cifId"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "decimal"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("creditcardSavedFlag");
        elemField.setXmlName(new javax.xml.namespace.QName("http://creditcard.ob.common.sml.integrosys.com", "creditcardSavedFlag"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("custSegment");
        elemField.setXmlName(new javax.xml.namespace.QName("http://creditcard.ob.common.sml.integrosys.com", "custSegment"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("existCardName");
        elemField.setXmlName(new javax.xml.namespace.QName("http://creditcard.ob.common.sml.integrosys.com", "existCardName"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("existCardNumber");
        elemField.setXmlName(new javax.xml.namespace.QName("http://creditcard.ob.common.sml.integrosys.com", "existCardNumber"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("existCardStatus");
        elemField.setXmlName(new javax.xml.namespace.QName("http://creditcard.ob.common.sml.integrosys.com", "existCardStatus"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("existCreditLimit");
        elemField.setXmlName(new javax.xml.namespace.QName("http://creditcard.ob.common.sml.integrosys.com", "existCreditLimit"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "decimal"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("existCreditLine");
        elemField.setXmlName(new javax.xml.namespace.QName("http://creditcard.ob.common.sml.integrosys.com", "existCreditLine"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "decimal"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("existProductType");
        elemField.setXmlName(new javax.xml.namespace.QName("http://creditcard.ob.common.sml.integrosys.com", "existProductType"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("feature");
        elemField.setXmlName(new javax.xml.namespace.QName("http://creditcard.ob.common.sml.integrosys.com", "feature"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://feature.ob.common.sml.integrosys.com", "Feature"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("featureType");
        elemField.setXmlName(new javax.xml.namespace.QName("http://creditcard.ob.common.sml.integrosys.com", "featureType"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("id");
        elemField.setXmlName(new javax.xml.namespace.QName("http://creditcard.ob.common.sml.integrosys.com", "id"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "decimal"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("loanReqMax");
        elemField.setXmlName(new javax.xml.namespace.QName("http://creditcard.ob.common.sml.integrosys.com", "loanReqMax"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "decimal"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("loanReqMin");
        elemField.setXmlName(new javax.xml.namespace.QName("http://creditcard.ob.common.sml.integrosys.com", "loanReqMin"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "decimal"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("mailPreference");
        elemField.setXmlName(new javax.xml.namespace.QName("http://creditcard.ob.common.sml.integrosys.com", "mailPreference"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("modelName");
        elemField.setXmlName(new javax.xml.namespace.QName("http://creditcard.ob.common.sml.integrosys.com", "modelName"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("modelScore");
        elemField.setXmlName(new javax.xml.namespace.QName("http://creditcard.ob.common.sml.integrosys.com", "modelScore"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "decimal"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("newCreditLimit");
        elemField.setXmlName(new javax.xml.namespace.QName("http://creditcard.ob.common.sml.integrosys.com", "newCreditLimit"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "decimal"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("paymentMethod");
        elemField.setXmlName(new javax.xml.namespace.QName("http://creditcard.ob.common.sml.integrosys.com", "paymentMethod"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("pricings");
        elemField.setXmlName(new javax.xml.namespace.QName("http://creditcard.ob.common.sml.integrosys.com", "pricings"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://pricing.ob.common.sml.integrosys.com", "Pricing"));
        elemField.setNillable(true);
        elemField.setItemQName(new javax.xml.namespace.QName("http://submission.loan.ws.sml.integrosys.com", "item"));
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("productCode");
        elemField.setXmlName(new javax.xml.namespace.QName("http://creditcard.ob.common.sml.integrosys.com", "productCode"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("productType");
        elemField.setXmlName(new javax.xml.namespace.QName("http://creditcard.ob.common.sml.integrosys.com", "productType"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("projectCode");
        elemField.setXmlName(new javax.xml.namespace.QName("http://creditcard.ob.common.sml.integrosys.com", "projectCode"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("requestCreditLimit");
        elemField.setXmlName(new javax.xml.namespace.QName("http://creditcard.ob.common.sml.integrosys.com", "requestCreditLimit"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "decimal"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("saleChannelCode");
        elemField.setXmlName(new javax.xml.namespace.QName("http://creditcard.ob.common.sml.integrosys.com", "saleChannelCode"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("saleChannelFlag");
        elemField.setXmlName(new javax.xml.namespace.QName("http://creditcard.ob.common.sml.integrosys.com", "saleChannelFlag"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("sourceOfData");
        elemField.setXmlName(new javax.xml.namespace.QName("http://creditcard.ob.common.sml.integrosys.com", "sourceOfData"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
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
