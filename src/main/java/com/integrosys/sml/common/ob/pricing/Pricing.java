/**
 * Pricing.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.integrosys.sml.common.ob.pricing;

public class Pricing  implements java.io.Serializable {
    private java.math.BigDecimal calculatedRate;

    private java.math.BigDecimal ccId;

    private java.math.BigDecimal facId;

    private java.math.BigDecimal id;

    private java.math.BigDecimal installment;

    private java.math.BigDecimal interestRate;

    private java.math.BigDecimal monthFrom;

    private java.math.BigDecimal monthTo;

    private java.lang.String percentSign;

    private java.lang.String pricingType;

    private java.lang.String rateType;

    private java.math.BigDecimal rateVaraince;

    private java.math.BigDecimal tier;

    private java.math.BigDecimal yearFrom;

    private java.math.BigDecimal yearTo;

    public Pricing() {
    }

    public Pricing(
           java.math.BigDecimal calculatedRate,
           java.math.BigDecimal ccId,
           java.math.BigDecimal facId,
           java.math.BigDecimal id,
           java.math.BigDecimal installment,
           java.math.BigDecimal interestRate,
           java.math.BigDecimal monthFrom,
           java.math.BigDecimal monthTo,
           java.lang.String percentSign,
           java.lang.String pricingType,
           java.lang.String rateType,
           java.math.BigDecimal rateVaraince,
           java.math.BigDecimal tier,
           java.math.BigDecimal yearFrom,
           java.math.BigDecimal yearTo) {
           this.calculatedRate = calculatedRate;
           this.ccId = ccId;
           this.facId = facId;
           this.id = id;
           this.installment = installment;
           this.interestRate = interestRate;
           this.monthFrom = monthFrom;
           this.monthTo = monthTo;
           this.percentSign = percentSign;
           this.pricingType = pricingType;
           this.rateType = rateType;
           this.rateVaraince = rateVaraince;
           this.tier = tier;
           this.yearFrom = yearFrom;
           this.yearTo = yearTo;
    }


    /**
     * Gets the calculatedRate value for this Pricing.
     * 
     * @return calculatedRate
     */
    public java.math.BigDecimal getCalculatedRate() {
        return calculatedRate;
    }


    /**
     * Sets the calculatedRate value for this Pricing.
     * 
     * @param calculatedRate
     */
    public void setCalculatedRate(java.math.BigDecimal calculatedRate) {
        this.calculatedRate = calculatedRate;
    }


    /**
     * Gets the ccId value for this Pricing.
     * 
     * @return ccId
     */
    public java.math.BigDecimal getCcId() {
        return ccId;
    }


    /**
     * Sets the ccId value for this Pricing.
     * 
     * @param ccId
     */
    public void setCcId(java.math.BigDecimal ccId) {
        this.ccId = ccId;
    }


    /**
     * Gets the facId value for this Pricing.
     * 
     * @return facId
     */
    public java.math.BigDecimal getFacId() {
        return facId;
    }


    /**
     * Sets the facId value for this Pricing.
     * 
     * @param facId
     */
    public void setFacId(java.math.BigDecimal facId) {
        this.facId = facId;
    }


    /**
     * Gets the id value for this Pricing.
     * 
     * @return id
     */
    public java.math.BigDecimal getId() {
        return id;
    }


    /**
     * Sets the id value for this Pricing.
     * 
     * @param id
     */
    public void setId(java.math.BigDecimal id) {
        this.id = id;
    }


    /**
     * Gets the installment value for this Pricing.
     * 
     * @return installment
     */
    public java.math.BigDecimal getInstallment() {
        return installment;
    }


    /**
     * Sets the installment value for this Pricing.
     * 
     * @param installment
     */
    public void setInstallment(java.math.BigDecimal installment) {
        this.installment = installment;
    }


    /**
     * Gets the interestRate value for this Pricing.
     * 
     * @return interestRate
     */
    public java.math.BigDecimal getInterestRate() {
        return interestRate;
    }


    /**
     * Sets the interestRate value for this Pricing.
     * 
     * @param interestRate
     */
    public void setInterestRate(java.math.BigDecimal interestRate) {
        this.interestRate = interestRate;
    }


    /**
     * Gets the monthFrom value for this Pricing.
     * 
     * @return monthFrom
     */
    public java.math.BigDecimal getMonthFrom() {
        return monthFrom;
    }


    /**
     * Sets the monthFrom value for this Pricing.
     * 
     * @param monthFrom
     */
    public void setMonthFrom(java.math.BigDecimal monthFrom) {
        this.monthFrom = monthFrom;
    }


    /**
     * Gets the monthTo value for this Pricing.
     * 
     * @return monthTo
     */
    public java.math.BigDecimal getMonthTo() {
        return monthTo;
    }


    /**
     * Sets the monthTo value for this Pricing.
     * 
     * @param monthTo
     */
    public void setMonthTo(java.math.BigDecimal monthTo) {
        this.monthTo = monthTo;
    }


    /**
     * Gets the percentSign value for this Pricing.
     * 
     * @return percentSign
     */
    public java.lang.String getPercentSign() {
        return percentSign;
    }


    /**
     * Sets the percentSign value for this Pricing.
     * 
     * @param percentSign
     */
    public void setPercentSign(java.lang.String percentSign) {
        this.percentSign = percentSign;
    }


    /**
     * Gets the pricingType value for this Pricing.
     * 
     * @return pricingType
     */
    public java.lang.String getPricingType() {
        return pricingType;
    }


    /**
     * Sets the pricingType value for this Pricing.
     * 
     * @param pricingType
     */
    public void setPricingType(java.lang.String pricingType) {
        this.pricingType = pricingType;
    }


    /**
     * Gets the rateType value for this Pricing.
     * 
     * @return rateType
     */
    public java.lang.String getRateType() {
        return rateType;
    }


    /**
     * Sets the rateType value for this Pricing.
     * 
     * @param rateType
     */
    public void setRateType(java.lang.String rateType) {
        this.rateType = rateType;
    }


    /**
     * Gets the rateVaraince value for this Pricing.
     * 
     * @return rateVaraince
     */
    public java.math.BigDecimal getRateVaraince() {
        return rateVaraince;
    }


    /**
     * Sets the rateVaraince value for this Pricing.
     * 
     * @param rateVaraince
     */
    public void setRateVaraince(java.math.BigDecimal rateVaraince) {
        this.rateVaraince = rateVaraince;
    }


    /**
     * Gets the tier value for this Pricing.
     * 
     * @return tier
     */
    public java.math.BigDecimal getTier() {
        return tier;
    }


    /**
     * Sets the tier value for this Pricing.
     * 
     * @param tier
     */
    public void setTier(java.math.BigDecimal tier) {
        this.tier = tier;
    }


    /**
     * Gets the yearFrom value for this Pricing.
     * 
     * @return yearFrom
     */
    public java.math.BigDecimal getYearFrom() {
        return yearFrom;
    }


    /**
     * Sets the yearFrom value for this Pricing.
     * 
     * @param yearFrom
     */
    public void setYearFrom(java.math.BigDecimal yearFrom) {
        this.yearFrom = yearFrom;
    }


    /**
     * Gets the yearTo value for this Pricing.
     * 
     * @return yearTo
     */
    public java.math.BigDecimal getYearTo() {
        return yearTo;
    }


    /**
     * Sets the yearTo value for this Pricing.
     * 
     * @param yearTo
     */
    public void setYearTo(java.math.BigDecimal yearTo) {
        this.yearTo = yearTo;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof Pricing)) return false;
        Pricing other = (Pricing) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.calculatedRate==null && other.getCalculatedRate()==null) || 
             (this.calculatedRate!=null &&
              this.calculatedRate.equals(other.getCalculatedRate()))) &&
            ((this.ccId==null && other.getCcId()==null) || 
             (this.ccId!=null &&
              this.ccId.equals(other.getCcId()))) &&
            ((this.facId==null && other.getFacId()==null) || 
             (this.facId!=null &&
              this.facId.equals(other.getFacId()))) &&
            ((this.id==null && other.getId()==null) || 
             (this.id!=null &&
              this.id.equals(other.getId()))) &&
            ((this.installment==null && other.getInstallment()==null) || 
             (this.installment!=null &&
              this.installment.equals(other.getInstallment()))) &&
            ((this.interestRate==null && other.getInterestRate()==null) || 
             (this.interestRate!=null &&
              this.interestRate.equals(other.getInterestRate()))) &&
            ((this.monthFrom==null && other.getMonthFrom()==null) || 
             (this.monthFrom!=null &&
              this.monthFrom.equals(other.getMonthFrom()))) &&
            ((this.monthTo==null && other.getMonthTo()==null) || 
             (this.monthTo!=null &&
              this.monthTo.equals(other.getMonthTo()))) &&
            ((this.percentSign==null && other.getPercentSign()==null) || 
             (this.percentSign!=null &&
              this.percentSign.equals(other.getPercentSign()))) &&
            ((this.pricingType==null && other.getPricingType()==null) || 
             (this.pricingType!=null &&
              this.pricingType.equals(other.getPricingType()))) &&
            ((this.rateType==null && other.getRateType()==null) || 
             (this.rateType!=null &&
              this.rateType.equals(other.getRateType()))) &&
            ((this.rateVaraince==null && other.getRateVaraince()==null) || 
             (this.rateVaraince!=null &&
              this.rateVaraince.equals(other.getRateVaraince()))) &&
            ((this.tier==null && other.getTier()==null) || 
             (this.tier!=null &&
              this.tier.equals(other.getTier()))) &&
            ((this.yearFrom==null && other.getYearFrom()==null) || 
             (this.yearFrom!=null &&
              this.yearFrom.equals(other.getYearFrom()))) &&
            ((this.yearTo==null && other.getYearTo()==null) || 
             (this.yearTo!=null &&
              this.yearTo.equals(other.getYearTo())));
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
        if (getCalculatedRate() != null) {
            _hashCode += getCalculatedRate().hashCode();
        }
        if (getCcId() != null) {
            _hashCode += getCcId().hashCode();
        }
        if (getFacId() != null) {
            _hashCode += getFacId().hashCode();
        }
        if (getId() != null) {
            _hashCode += getId().hashCode();
        }
        if (getInstallment() != null) {
            _hashCode += getInstallment().hashCode();
        }
        if (getInterestRate() != null) {
            _hashCode += getInterestRate().hashCode();
        }
        if (getMonthFrom() != null) {
            _hashCode += getMonthFrom().hashCode();
        }
        if (getMonthTo() != null) {
            _hashCode += getMonthTo().hashCode();
        }
        if (getPercentSign() != null) {
            _hashCode += getPercentSign().hashCode();
        }
        if (getPricingType() != null) {
            _hashCode += getPricingType().hashCode();
        }
        if (getRateType() != null) {
            _hashCode += getRateType().hashCode();
        }
        if (getRateVaraince() != null) {
            _hashCode += getRateVaraince().hashCode();
        }
        if (getTier() != null) {
            _hashCode += getTier().hashCode();
        }
        if (getYearFrom() != null) {
            _hashCode += getYearFrom().hashCode();
        }
        if (getYearTo() != null) {
            _hashCode += getYearTo().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(Pricing.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://pricing.ob.common.sml.integrosys.com", "Pricing"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("calculatedRate");
        elemField.setXmlName(new javax.xml.namespace.QName("http://pricing.ob.common.sml.integrosys.com", "calculatedRate"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "decimal"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("ccId");
        elemField.setXmlName(new javax.xml.namespace.QName("http://pricing.ob.common.sml.integrosys.com", "ccId"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "decimal"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("facId");
        elemField.setXmlName(new javax.xml.namespace.QName("http://pricing.ob.common.sml.integrosys.com", "facId"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "decimal"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("id");
        elemField.setXmlName(new javax.xml.namespace.QName("http://pricing.ob.common.sml.integrosys.com", "id"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "decimal"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("installment");
        elemField.setXmlName(new javax.xml.namespace.QName("http://pricing.ob.common.sml.integrosys.com", "installment"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "decimal"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("interestRate");
        elemField.setXmlName(new javax.xml.namespace.QName("http://pricing.ob.common.sml.integrosys.com", "interestRate"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "decimal"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("monthFrom");
        elemField.setXmlName(new javax.xml.namespace.QName("http://pricing.ob.common.sml.integrosys.com", "monthFrom"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "decimal"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("monthTo");
        elemField.setXmlName(new javax.xml.namespace.QName("http://pricing.ob.common.sml.integrosys.com", "monthTo"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "decimal"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("percentSign");
        elemField.setXmlName(new javax.xml.namespace.QName("http://pricing.ob.common.sml.integrosys.com", "percentSign"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("pricingType");
        elemField.setXmlName(new javax.xml.namespace.QName("http://pricing.ob.common.sml.integrosys.com", "pricingType"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("rateType");
        elemField.setXmlName(new javax.xml.namespace.QName("http://pricing.ob.common.sml.integrosys.com", "rateType"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("rateVaraince");
        elemField.setXmlName(new javax.xml.namespace.QName("http://pricing.ob.common.sml.integrosys.com", "rateVaraince"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "decimal"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("tier");
        elemField.setXmlName(new javax.xml.namespace.QName("http://pricing.ob.common.sml.integrosys.com", "tier"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "decimal"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("yearFrom");
        elemField.setXmlName(new javax.xml.namespace.QName("http://pricing.ob.common.sml.integrosys.com", "yearFrom"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "decimal"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("yearTo");
        elemField.setXmlName(new javax.xml.namespace.QName("http://pricing.ob.common.sml.integrosys.com", "yearTo"));
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
