/**
 * Account.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.integrosys.sml.common.ob.account;

public class Account  implements java.io.Serializable {
    private java.lang.String accountNo;

    private java.lang.String accountStatus;

    private java.lang.String accountType;

    private java.lang.String bankName;

    private java.lang.String bankNo;

    private java.math.BigDecimal cifId;

    private java.lang.String cifName;

    private java.lang.String cifRelationship;

    private java.lang.String cifRelationshipCode;

    private java.lang.String currency;

    private java.lang.String exposureTypeCode;

    private java.lang.String hostID;

    private java.math.BigDecimal id;

    private java.lang.String identifiedBalType;

    private java.math.BigDecimal outstandingLimit;

    private java.lang.String outstandingLimitCurrency;

    private java.lang.String productCode;

    public Account() {
    }

    public Account(
           java.lang.String accountNo,
           java.lang.String accountStatus,
           java.lang.String accountType,
           java.lang.String bankName,
           java.lang.String bankNo,
           java.math.BigDecimal cifId,
           java.lang.String cifName,
           java.lang.String cifRelationship,
           java.lang.String cifRelationshipCode,
           java.lang.String currency,
           java.lang.String exposureTypeCode,
           java.lang.String hostID,
           java.math.BigDecimal id,
           java.lang.String identifiedBalType,
           java.math.BigDecimal outstandingLimit,
           java.lang.String outstandingLimitCurrency,
           java.lang.String productCode) {
           this.accountNo = accountNo;
           this.accountStatus = accountStatus;
           this.accountType = accountType;
           this.bankName = bankName;
           this.bankNo = bankNo;
           this.cifId = cifId;
           this.cifName = cifName;
           this.cifRelationship = cifRelationship;
           this.cifRelationshipCode = cifRelationshipCode;
           this.currency = currency;
           this.exposureTypeCode = exposureTypeCode;
           this.hostID = hostID;
           this.id = id;
           this.identifiedBalType = identifiedBalType;
           this.outstandingLimit = outstandingLimit;
           this.outstandingLimitCurrency = outstandingLimitCurrency;
           this.productCode = productCode;
    }


    /**
     * Gets the accountNo value for this Account.
     * 
     * @return accountNo
     */
    public java.lang.String getAccountNo() {
        return accountNo;
    }


    /**
     * Sets the accountNo value for this Account.
     * 
     * @param accountNo
     */
    public void setAccountNo(java.lang.String accountNo) {
        this.accountNo = accountNo;
    }


    /**
     * Gets the accountStatus value for this Account.
     * 
     * @return accountStatus
     */
    public java.lang.String getAccountStatus() {
        return accountStatus;
    }


    /**
     * Sets the accountStatus value for this Account.
     * 
     * @param accountStatus
     */
    public void setAccountStatus(java.lang.String accountStatus) {
        this.accountStatus = accountStatus;
    }


    /**
     * Gets the accountType value for this Account.
     * 
     * @return accountType
     */
    public java.lang.String getAccountType() {
        return accountType;
    }


    /**
     * Sets the accountType value for this Account.
     * 
     * @param accountType
     */
    public void setAccountType(java.lang.String accountType) {
        this.accountType = accountType;
    }


    /**
     * Gets the bankName value for this Account.
     * 
     * @return bankName
     */
    public java.lang.String getBankName() {
        return bankName;
    }


    /**
     * Sets the bankName value for this Account.
     * 
     * @param bankName
     */
    public void setBankName(java.lang.String bankName) {
        this.bankName = bankName;
    }


    /**
     * Gets the bankNo value for this Account.
     * 
     * @return bankNo
     */
    public java.lang.String getBankNo() {
        return bankNo;
    }


    /**
     * Sets the bankNo value for this Account.
     * 
     * @param bankNo
     */
    public void setBankNo(java.lang.String bankNo) {
        this.bankNo = bankNo;
    }


    /**
     * Gets the cifId value for this Account.
     * 
     * @return cifId
     */
    public java.math.BigDecimal getCifId() {
        return cifId;
    }


    /**
     * Sets the cifId value for this Account.
     * 
     * @param cifId
     */
    public void setCifId(java.math.BigDecimal cifId) {
        this.cifId = cifId;
    }


    /**
     * Gets the cifName value for this Account.
     * 
     * @return cifName
     */
    public java.lang.String getCifName() {
        return cifName;
    }


    /**
     * Sets the cifName value for this Account.
     * 
     * @param cifName
     */
    public void setCifName(java.lang.String cifName) {
        this.cifName = cifName;
    }


    /**
     * Gets the cifRelationship value for this Account.
     * 
     * @return cifRelationship
     */
    public java.lang.String getCifRelationship() {
        return cifRelationship;
    }


    /**
     * Sets the cifRelationship value for this Account.
     * 
     * @param cifRelationship
     */
    public void setCifRelationship(java.lang.String cifRelationship) {
        this.cifRelationship = cifRelationship;
    }


    /**
     * Gets the cifRelationshipCode value for this Account.
     * 
     * @return cifRelationshipCode
     */
    public java.lang.String getCifRelationshipCode() {
        return cifRelationshipCode;
    }


    /**
     * Sets the cifRelationshipCode value for this Account.
     * 
     * @param cifRelationshipCode
     */
    public void setCifRelationshipCode(java.lang.String cifRelationshipCode) {
        this.cifRelationshipCode = cifRelationshipCode;
    }


    /**
     * Gets the currency value for this Account.
     * 
     * @return currency
     */
    public java.lang.String getCurrency() {
        return currency;
    }


    /**
     * Sets the currency value for this Account.
     * 
     * @param currency
     */
    public void setCurrency(java.lang.String currency) {
        this.currency = currency;
    }


    /**
     * Gets the exposureTypeCode value for this Account.
     * 
     * @return exposureTypeCode
     */
    public java.lang.String getExposureTypeCode() {
        return exposureTypeCode;
    }


    /**
     * Sets the exposureTypeCode value for this Account.
     * 
     * @param exposureTypeCode
     */
    public void setExposureTypeCode(java.lang.String exposureTypeCode) {
        this.exposureTypeCode = exposureTypeCode;
    }


    /**
     * Gets the hostID value for this Account.
     * 
     * @return hostID
     */
    public java.lang.String getHostID() {
        return hostID;
    }


    /**
     * Sets the hostID value for this Account.
     * 
     * @param hostID
     */
    public void setHostID(java.lang.String hostID) {
        this.hostID = hostID;
    }


    /**
     * Gets the id value for this Account.
     * 
     * @return id
     */
    public java.math.BigDecimal getId() {
        return id;
    }


    /**
     * Sets the id value for this Account.
     * 
     * @param id
     */
    public void setId(java.math.BigDecimal id) {
        this.id = id;
    }


    /**
     * Gets the identifiedBalType value for this Account.
     * 
     * @return identifiedBalType
     */
    public java.lang.String getIdentifiedBalType() {
        return identifiedBalType;
    }


    /**
     * Sets the identifiedBalType value for this Account.
     * 
     * @param identifiedBalType
     */
    public void setIdentifiedBalType(java.lang.String identifiedBalType) {
        this.identifiedBalType = identifiedBalType;
    }


    /**
     * Gets the outstandingLimit value for this Account.
     * 
     * @return outstandingLimit
     */
    public java.math.BigDecimal getOutstandingLimit() {
        return outstandingLimit;
    }


    /**
     * Sets the outstandingLimit value for this Account.
     * 
     * @param outstandingLimit
     */
    public void setOutstandingLimit(java.math.BigDecimal outstandingLimit) {
        this.outstandingLimit = outstandingLimit;
    }


    /**
     * Gets the outstandingLimitCurrency value for this Account.
     * 
     * @return outstandingLimitCurrency
     */
    public java.lang.String getOutstandingLimitCurrency() {
        return outstandingLimitCurrency;
    }


    /**
     * Sets the outstandingLimitCurrency value for this Account.
     * 
     * @param outstandingLimitCurrency
     */
    public void setOutstandingLimitCurrency(java.lang.String outstandingLimitCurrency) {
        this.outstandingLimitCurrency = outstandingLimitCurrency;
    }


    /**
     * Gets the productCode value for this Account.
     * 
     * @return productCode
     */
    public java.lang.String getProductCode() {
        return productCode;
    }


    /**
     * Sets the productCode value for this Account.
     * 
     * @param productCode
     */
    public void setProductCode(java.lang.String productCode) {
        this.productCode = productCode;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof Account)) return false;
        Account other = (Account) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.accountNo==null && other.getAccountNo()==null) || 
             (this.accountNo!=null &&
              this.accountNo.equals(other.getAccountNo()))) &&
            ((this.accountStatus==null && other.getAccountStatus()==null) || 
             (this.accountStatus!=null &&
              this.accountStatus.equals(other.getAccountStatus()))) &&
            ((this.accountType==null && other.getAccountType()==null) || 
             (this.accountType!=null &&
              this.accountType.equals(other.getAccountType()))) &&
            ((this.bankName==null && other.getBankName()==null) || 
             (this.bankName!=null &&
              this.bankName.equals(other.getBankName()))) &&
            ((this.bankNo==null && other.getBankNo()==null) || 
             (this.bankNo!=null &&
              this.bankNo.equals(other.getBankNo()))) &&
            ((this.cifId==null && other.getCifId()==null) || 
             (this.cifId!=null &&
              this.cifId.equals(other.getCifId()))) &&
            ((this.cifName==null && other.getCifName()==null) || 
             (this.cifName!=null &&
              this.cifName.equals(other.getCifName()))) &&
            ((this.cifRelationship==null && other.getCifRelationship()==null) || 
             (this.cifRelationship!=null &&
              this.cifRelationship.equals(other.getCifRelationship()))) &&
            ((this.cifRelationshipCode==null && other.getCifRelationshipCode()==null) || 
             (this.cifRelationshipCode!=null &&
              this.cifRelationshipCode.equals(other.getCifRelationshipCode()))) &&
            ((this.currency==null && other.getCurrency()==null) || 
             (this.currency!=null &&
              this.currency.equals(other.getCurrency()))) &&
            ((this.exposureTypeCode==null && other.getExposureTypeCode()==null) || 
             (this.exposureTypeCode!=null &&
              this.exposureTypeCode.equals(other.getExposureTypeCode()))) &&
            ((this.hostID==null && other.getHostID()==null) || 
             (this.hostID!=null &&
              this.hostID.equals(other.getHostID()))) &&
            ((this.id==null && other.getId()==null) || 
             (this.id!=null &&
              this.id.equals(other.getId()))) &&
            ((this.identifiedBalType==null && other.getIdentifiedBalType()==null) || 
             (this.identifiedBalType!=null &&
              this.identifiedBalType.equals(other.getIdentifiedBalType()))) &&
            ((this.outstandingLimit==null && other.getOutstandingLimit()==null) || 
             (this.outstandingLimit!=null &&
              this.outstandingLimit.equals(other.getOutstandingLimit()))) &&
            ((this.outstandingLimitCurrency==null && other.getOutstandingLimitCurrency()==null) || 
             (this.outstandingLimitCurrency!=null &&
              this.outstandingLimitCurrency.equals(other.getOutstandingLimitCurrency()))) &&
            ((this.productCode==null && other.getProductCode()==null) || 
             (this.productCode!=null &&
              this.productCode.equals(other.getProductCode())));
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
        if (getAccountNo() != null) {
            _hashCode += getAccountNo().hashCode();
        }
        if (getAccountStatus() != null) {
            _hashCode += getAccountStatus().hashCode();
        }
        if (getAccountType() != null) {
            _hashCode += getAccountType().hashCode();
        }
        if (getBankName() != null) {
            _hashCode += getBankName().hashCode();
        }
        if (getBankNo() != null) {
            _hashCode += getBankNo().hashCode();
        }
        if (getCifId() != null) {
            _hashCode += getCifId().hashCode();
        }
        if (getCifName() != null) {
            _hashCode += getCifName().hashCode();
        }
        if (getCifRelationship() != null) {
            _hashCode += getCifRelationship().hashCode();
        }
        if (getCifRelationshipCode() != null) {
            _hashCode += getCifRelationshipCode().hashCode();
        }
        if (getCurrency() != null) {
            _hashCode += getCurrency().hashCode();
        }
        if (getExposureTypeCode() != null) {
            _hashCode += getExposureTypeCode().hashCode();
        }
        if (getHostID() != null) {
            _hashCode += getHostID().hashCode();
        }
        if (getId() != null) {
            _hashCode += getId().hashCode();
        }
        if (getIdentifiedBalType() != null) {
            _hashCode += getIdentifiedBalType().hashCode();
        }
        if (getOutstandingLimit() != null) {
            _hashCode += getOutstandingLimit().hashCode();
        }
        if (getOutstandingLimitCurrency() != null) {
            _hashCode += getOutstandingLimitCurrency().hashCode();
        }
        if (getProductCode() != null) {
            _hashCode += getProductCode().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(Account.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://account.ob.common.sml.integrosys.com", "Account"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("accountNo");
        elemField.setXmlName(new javax.xml.namespace.QName("http://account.ob.common.sml.integrosys.com", "accountNo"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("accountStatus");
        elemField.setXmlName(new javax.xml.namespace.QName("http://account.ob.common.sml.integrosys.com", "accountStatus"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("accountType");
        elemField.setXmlName(new javax.xml.namespace.QName("http://account.ob.common.sml.integrosys.com", "accountType"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("bankName");
        elemField.setXmlName(new javax.xml.namespace.QName("http://account.ob.common.sml.integrosys.com", "bankName"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("bankNo");
        elemField.setXmlName(new javax.xml.namespace.QName("http://account.ob.common.sml.integrosys.com", "bankNo"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("cifId");
        elemField.setXmlName(new javax.xml.namespace.QName("http://account.ob.common.sml.integrosys.com", "cifId"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "decimal"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("cifName");
        elemField.setXmlName(new javax.xml.namespace.QName("http://account.ob.common.sml.integrosys.com", "cifName"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("cifRelationship");
        elemField.setXmlName(new javax.xml.namespace.QName("http://account.ob.common.sml.integrosys.com", "cifRelationship"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("cifRelationshipCode");
        elemField.setXmlName(new javax.xml.namespace.QName("http://account.ob.common.sml.integrosys.com", "cifRelationshipCode"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("currency");
        elemField.setXmlName(new javax.xml.namespace.QName("http://account.ob.common.sml.integrosys.com", "currency"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("exposureTypeCode");
        elemField.setXmlName(new javax.xml.namespace.QName("http://account.ob.common.sml.integrosys.com", "exposureTypeCode"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("hostID");
        elemField.setXmlName(new javax.xml.namespace.QName("http://account.ob.common.sml.integrosys.com", "hostID"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("id");
        elemField.setXmlName(new javax.xml.namespace.QName("http://account.ob.common.sml.integrosys.com", "id"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "decimal"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("identifiedBalType");
        elemField.setXmlName(new javax.xml.namespace.QName("http://account.ob.common.sml.integrosys.com", "identifiedBalType"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("outstandingLimit");
        elemField.setXmlName(new javax.xml.namespace.QName("http://account.ob.common.sml.integrosys.com", "outstandingLimit"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "decimal"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("outstandingLimitCurrency");
        elemField.setXmlName(new javax.xml.namespace.QName("http://account.ob.common.sml.integrosys.com", "outstandingLimitCurrency"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("productCode");
        elemField.setXmlName(new javax.xml.namespace.QName("http://account.ob.common.sml.integrosys.com", "productCode"));
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
