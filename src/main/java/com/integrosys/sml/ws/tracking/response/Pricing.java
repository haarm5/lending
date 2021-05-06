/**
 * Pricing.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.integrosys.sml.ws.tracking.response;

public class Pricing  implements java.io.Serializable {
    private double calculatedRate;

    private double installment;

    private double interestRate;

    private double monthFrom;

    private double monthTo;

    private java.lang.String percentSign;

    private java.lang.String rateType;

    private double rateVaraince;

    private double tier;

    private double yearFrom;

    private double yearTo;

    public Pricing() {
    }

    public Pricing(
           double calculatedRate,
           double installment,
           double interestRate,
           double monthFrom,
           double monthTo,
           java.lang.String percentSign,
           java.lang.String rateType,
           double rateVaraince,
           double tier,
           double yearFrom,
           double yearTo) {
           this.calculatedRate = calculatedRate;
           this.installment = installment;
           this.interestRate = interestRate;
           this.monthFrom = monthFrom;
           this.monthTo = monthTo;
           this.percentSign = percentSign;
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
    public double getCalculatedRate() {
        return calculatedRate;
    }


    /**
     * Sets the calculatedRate value for this Pricing.
     * 
     * @param calculatedRate
     */
    public void setCalculatedRate(double calculatedRate) {
        this.calculatedRate = calculatedRate;
    }


    /**
     * Gets the installment value for this Pricing.
     * 
     * @return installment
     */
    public double getInstallment() {
        return installment;
    }


    /**
     * Sets the installment value for this Pricing.
     * 
     * @param installment
     */
    public void setInstallment(double installment) {
        this.installment = installment;
    }


    /**
     * Gets the interestRate value for this Pricing.
     * 
     * @return interestRate
     */
    public double getInterestRate() {
        return interestRate;
    }


    /**
     * Sets the interestRate value for this Pricing.
     * 
     * @param interestRate
     */
    public void setInterestRate(double interestRate) {
        this.interestRate = interestRate;
    }


    /**
     * Gets the monthFrom value for this Pricing.
     * 
     * @return monthFrom
     */
    public double getMonthFrom() {
        return monthFrom;
    }


    /**
     * Sets the monthFrom value for this Pricing.
     * 
     * @param monthFrom
     */
    public void setMonthFrom(double monthFrom) {
        this.monthFrom = monthFrom;
    }


    /**
     * Gets the monthTo value for this Pricing.
     * 
     * @return monthTo
     */
    public double getMonthTo() {
        return monthTo;
    }


    /**
     * Sets the monthTo value for this Pricing.
     * 
     * @param monthTo
     */
    public void setMonthTo(double monthTo) {
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
    public double getRateVaraince() {
        return rateVaraince;
    }


    /**
     * Sets the rateVaraince value for this Pricing.
     * 
     * @param rateVaraince
     */
    public void setRateVaraince(double rateVaraince) {
        this.rateVaraince = rateVaraince;
    }


    /**
     * Gets the tier value for this Pricing.
     * 
     * @return tier
     */
    public double getTier() {
        return tier;
    }


    /**
     * Sets the tier value for this Pricing.
     * 
     * @param tier
     */
    public void setTier(double tier) {
        this.tier = tier;
    }


    /**
     * Gets the yearFrom value for this Pricing.
     * 
     * @return yearFrom
     */
    public double getYearFrom() {
        return yearFrom;
    }


    /**
     * Sets the yearFrom value for this Pricing.
     * 
     * @param yearFrom
     */
    public void setYearFrom(double yearFrom) {
        this.yearFrom = yearFrom;
    }


    /**
     * Gets the yearTo value for this Pricing.
     * 
     * @return yearTo
     */
    public double getYearTo() {
        return yearTo;
    }


    /**
     * Sets the yearTo value for this Pricing.
     * 
     * @param yearTo
     */
    public void setYearTo(double yearTo) {
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
            this.calculatedRate == other.getCalculatedRate() &&
            this.installment == other.getInstallment() &&
            this.interestRate == other.getInterestRate() &&
            this.monthFrom == other.getMonthFrom() &&
            this.monthTo == other.getMonthTo() &&
            ((this.percentSign==null && other.getPercentSign()==null) || 
             (this.percentSign!=null &&
              this.percentSign.equals(other.getPercentSign()))) &&
            ((this.rateType==null && other.getRateType()==null) || 
             (this.rateType!=null &&
              this.rateType.equals(other.getRateType()))) &&
            this.rateVaraince == other.getRateVaraince() &&
            this.tier == other.getTier() &&
            this.yearFrom == other.getYearFrom() &&
            this.yearTo == other.getYearTo();
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
        _hashCode += new Double(getCalculatedRate()).hashCode();
        _hashCode += new Double(getInstallment()).hashCode();
        _hashCode += new Double(getInterestRate()).hashCode();
        _hashCode += new Double(getMonthFrom()).hashCode();
        _hashCode += new Double(getMonthTo()).hashCode();
        if (getPercentSign() != null) {
            _hashCode += getPercentSign().hashCode();
        }
        if (getRateType() != null) {
            _hashCode += getRateType().hashCode();
        }
        _hashCode += new Double(getRateVaraince()).hashCode();
        _hashCode += new Double(getTier()).hashCode();
        _hashCode += new Double(getYearFrom()).hashCode();
        _hashCode += new Double(getYearTo()).hashCode();
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(Pricing.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://response.tracking.ws.sml.integrosys.com", "Pricing"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("calculatedRate");
        elemField.setXmlName(new javax.xml.namespace.QName("http://response.tracking.ws.sml.integrosys.com", "calculatedRate"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "double"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("installment");
        elemField.setXmlName(new javax.xml.namespace.QName("http://response.tracking.ws.sml.integrosys.com", "installment"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "double"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("interestRate");
        elemField.setXmlName(new javax.xml.namespace.QName("http://response.tracking.ws.sml.integrosys.com", "interestRate"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "double"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("monthFrom");
        elemField.setXmlName(new javax.xml.namespace.QName("http://response.tracking.ws.sml.integrosys.com", "monthFrom"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "double"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("monthTo");
        elemField.setXmlName(new javax.xml.namespace.QName("http://response.tracking.ws.sml.integrosys.com", "monthTo"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "double"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("percentSign");
        elemField.setXmlName(new javax.xml.namespace.QName("http://response.tracking.ws.sml.integrosys.com", "percentSign"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("rateType");
        elemField.setXmlName(new javax.xml.namespace.QName("http://response.tracking.ws.sml.integrosys.com", "rateType"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("rateVaraince");
        elemField.setXmlName(new javax.xml.namespace.QName("http://response.tracking.ws.sml.integrosys.com", "rateVaraince"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "double"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("tier");
        elemField.setXmlName(new javax.xml.namespace.QName("http://response.tracking.ws.sml.integrosys.com", "tier"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "double"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("yearFrom");
        elemField.setXmlName(new javax.xml.namespace.QName("http://response.tracking.ws.sml.integrosys.com", "yearFrom"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "double"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("yearTo");
        elemField.setXmlName(new javax.xml.namespace.QName("http://response.tracking.ws.sml.integrosys.com", "yearTo"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "double"));
        elemField.setNillable(false);
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
