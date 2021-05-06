/**
 * Feature.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.integrosys.sml.common.ob.feature;

public class Feature  implements java.io.Serializable {
    private java.lang.String disbAcctName;

    private java.lang.String disbAcctNo;

    private java.lang.String disbBankCode;

    private java.math.BigDecimal id;

    private java.math.BigDecimal requestAmount;

    private java.math.BigDecimal requestPercent;

    private java.lang.Long tenure;

    public Feature() {
    }

    public Feature(
           java.lang.String disbAcctName,
           java.lang.String disbAcctNo,
           java.lang.String disbBankCode,
           java.math.BigDecimal id,
           java.math.BigDecimal requestAmount,
           java.math.BigDecimal requestPercent,
           java.lang.Long tenure) {
           this.disbAcctName = disbAcctName;
           this.disbAcctNo = disbAcctNo;
           this.disbBankCode = disbBankCode;
           this.id = id;
           this.requestAmount = requestAmount;
           this.requestPercent = requestPercent;
           this.tenure = tenure;
    }


    /**
     * Gets the disbAcctName value for this Feature.
     * 
     * @return disbAcctName
     */
    public java.lang.String getDisbAcctName() {
        return disbAcctName;
    }


    /**
     * Sets the disbAcctName value for this Feature.
     * 
     * @param disbAcctName
     */
    public void setDisbAcctName(java.lang.String disbAcctName) {
        this.disbAcctName = disbAcctName;
    }


    /**
     * Gets the disbAcctNo value for this Feature.
     * 
     * @return disbAcctNo
     */
    public java.lang.String getDisbAcctNo() {
        return disbAcctNo;
    }


    /**
     * Sets the disbAcctNo value for this Feature.
     * 
     * @param disbAcctNo
     */
    public void setDisbAcctNo(java.lang.String disbAcctNo) {
        this.disbAcctNo = disbAcctNo;
    }


    /**
     * Gets the disbBankCode value for this Feature.
     * 
     * @return disbBankCode
     */
    public java.lang.String getDisbBankCode() {
        return disbBankCode;
    }


    /**
     * Sets the disbBankCode value for this Feature.
     * 
     * @param disbBankCode
     */
    public void setDisbBankCode(java.lang.String disbBankCode) {
        this.disbBankCode = disbBankCode;
    }


    /**
     * Gets the id value for this Feature.
     * 
     * @return id
     */
    public java.math.BigDecimal getId() {
        return id;
    }


    /**
     * Sets the id value for this Feature.
     * 
     * @param id
     */
    public void setId(java.math.BigDecimal id) {
        this.id = id;
    }


    /**
     * Gets the requestAmount value for this Feature.
     * 
     * @return requestAmount
     */
    public java.math.BigDecimal getRequestAmount() {
        return requestAmount;
    }


    /**
     * Sets the requestAmount value for this Feature.
     * 
     * @param requestAmount
     */
    public void setRequestAmount(java.math.BigDecimal requestAmount) {
        this.requestAmount = requestAmount;
    }


    /**
     * Gets the requestPercent value for this Feature.
     * 
     * @return requestPercent
     */
    public java.math.BigDecimal getRequestPercent() {
        return requestPercent;
    }


    /**
     * Sets the requestPercent value for this Feature.
     * 
     * @param requestPercent
     */
    public void setRequestPercent(java.math.BigDecimal requestPercent) {
        this.requestPercent = requestPercent;
    }


    /**
     * Gets the tenure value for this Feature.
     * 
     * @return tenure
     */
    public java.lang.Long getTenure() {
        return tenure;
    }


    /**
     * Sets the tenure value for this Feature.
     * 
     * @param tenure
     */
    public void setTenure(java.lang.Long tenure) {
        this.tenure = tenure;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof Feature)) return false;
        Feature other = (Feature) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.disbAcctName==null && other.getDisbAcctName()==null) || 
             (this.disbAcctName!=null &&
              this.disbAcctName.equals(other.getDisbAcctName()))) &&
            ((this.disbAcctNo==null && other.getDisbAcctNo()==null) || 
             (this.disbAcctNo!=null &&
              this.disbAcctNo.equals(other.getDisbAcctNo()))) &&
            ((this.disbBankCode==null && other.getDisbBankCode()==null) || 
             (this.disbBankCode!=null &&
              this.disbBankCode.equals(other.getDisbBankCode()))) &&
            ((this.id==null && other.getId()==null) || 
             (this.id!=null &&
              this.id.equals(other.getId()))) &&
            ((this.requestAmount==null && other.getRequestAmount()==null) || 
             (this.requestAmount!=null &&
              this.requestAmount.equals(other.getRequestAmount()))) &&
            ((this.requestPercent==null && other.getRequestPercent()==null) || 
             (this.requestPercent!=null &&
              this.requestPercent.equals(other.getRequestPercent()))) &&
            ((this.tenure==null && other.getTenure()==null) || 
             (this.tenure!=null &&
              this.tenure.equals(other.getTenure())));
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
        if (getDisbAcctName() != null) {
            _hashCode += getDisbAcctName().hashCode();
        }
        if (getDisbAcctNo() != null) {
            _hashCode += getDisbAcctNo().hashCode();
        }
        if (getDisbBankCode() != null) {
            _hashCode += getDisbBankCode().hashCode();
        }
        if (getId() != null) {
            _hashCode += getId().hashCode();
        }
        if (getRequestAmount() != null) {
            _hashCode += getRequestAmount().hashCode();
        }
        if (getRequestPercent() != null) {
            _hashCode += getRequestPercent().hashCode();
        }
        if (getTenure() != null) {
            _hashCode += getTenure().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(Feature.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://feature.ob.common.sml.integrosys.com", "Feature"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("disbAcctName");
        elemField.setXmlName(new javax.xml.namespace.QName("http://feature.ob.common.sml.integrosys.com", "disbAcctName"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("disbAcctNo");
        elemField.setXmlName(new javax.xml.namespace.QName("http://feature.ob.common.sml.integrosys.com", "disbAcctNo"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("disbBankCode");
        elemField.setXmlName(new javax.xml.namespace.QName("http://feature.ob.common.sml.integrosys.com", "disbBankCode"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("id");
        elemField.setXmlName(new javax.xml.namespace.QName("http://feature.ob.common.sml.integrosys.com", "id"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "decimal"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("requestAmount");
        elemField.setXmlName(new javax.xml.namespace.QName("http://feature.ob.common.sml.integrosys.com", "requestAmount"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "decimal"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("requestPercent");
        elemField.setXmlName(new javax.xml.namespace.QName("http://feature.ob.common.sml.integrosys.com", "requestPercent"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "decimal"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("tenure");
        elemField.setXmlName(new javax.xml.namespace.QName("http://feature.ob.common.sml.integrosys.com", "tenure"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "long"));
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
