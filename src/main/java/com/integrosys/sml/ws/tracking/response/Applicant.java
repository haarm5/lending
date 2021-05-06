/**
 * Applicant.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.integrosys.sml.ws.tracking.response;

public class Applicant  implements java.io.Serializable {
    private java.lang.String applicantType;

    private java.lang.String firstNameEN;

    private java.lang.String firstNameTH;

    private com.integrosys.sml.ws.tracking.response.Product[] products;

    private int seq;

    private java.lang.String surNameEN;

    private java.lang.String surNameTH;

    private java.lang.String titleEN;

    private java.lang.String titleTH;

    public Applicant() {
    }

    public Applicant(
           java.lang.String applicantType,
           java.lang.String firstNameEN,
           java.lang.String firstNameTH,
           com.integrosys.sml.ws.tracking.response.Product[] products,
           int seq,
           java.lang.String surNameEN,
           java.lang.String surNameTH,
           java.lang.String titleEN,
           java.lang.String titleTH) {
           this.applicantType = applicantType;
           this.firstNameEN = firstNameEN;
           this.firstNameTH = firstNameTH;
           this.products = products;
           this.seq = seq;
           this.surNameEN = surNameEN;
           this.surNameTH = surNameTH;
           this.titleEN = titleEN;
           this.titleTH = titleTH;
    }


    /**
     * Gets the applicantType value for this Applicant.
     * 
     * @return applicantType
     */
    public java.lang.String getApplicantType() {
        return applicantType;
    }


    /**
     * Sets the applicantType value for this Applicant.
     * 
     * @param applicantType
     */
    public void setApplicantType(java.lang.String applicantType) {
        this.applicantType = applicantType;
    }


    /**
     * Gets the firstNameEN value for this Applicant.
     * 
     * @return firstNameEN
     */
    public java.lang.String getFirstNameEN() {
        return firstNameEN;
    }


    /**
     * Sets the firstNameEN value for this Applicant.
     * 
     * @param firstNameEN
     */
    public void setFirstNameEN(java.lang.String firstNameEN) {
        this.firstNameEN = firstNameEN;
    }


    /**
     * Gets the firstNameTH value for this Applicant.
     * 
     * @return firstNameTH
     */
    public java.lang.String getFirstNameTH() {
        return firstNameTH;
    }


    /**
     * Sets the firstNameTH value for this Applicant.
     * 
     * @param firstNameTH
     */
    public void setFirstNameTH(java.lang.String firstNameTH) {
        this.firstNameTH = firstNameTH;
    }


    /**
     * Gets the products value for this Applicant.
     * 
     * @return products
     */
    public com.integrosys.sml.ws.tracking.response.Product[] getProducts() {
        return products;
    }


    /**
     * Sets the products value for this Applicant.
     * 
     * @param products
     */
    public void setProducts(com.integrosys.sml.ws.tracking.response.Product[] products) {
        this.products = products;
    }


    /**
     * Gets the seq value for this Applicant.
     * 
     * @return seq
     */
    public int getSeq() {
        return seq;
    }


    /**
     * Sets the seq value for this Applicant.
     * 
     * @param seq
     */
    public void setSeq(int seq) {
        this.seq = seq;
    }


    /**
     * Gets the surNameEN value for this Applicant.
     * 
     * @return surNameEN
     */
    public java.lang.String getSurNameEN() {
        return surNameEN;
    }


    /**
     * Sets the surNameEN value for this Applicant.
     * 
     * @param surNameEN
     */
    public void setSurNameEN(java.lang.String surNameEN) {
        this.surNameEN = surNameEN;
    }


    /**
     * Gets the surNameTH value for this Applicant.
     * 
     * @return surNameTH
     */
    public java.lang.String getSurNameTH() {
        return surNameTH;
    }


    /**
     * Sets the surNameTH value for this Applicant.
     * 
     * @param surNameTH
     */
    public void setSurNameTH(java.lang.String surNameTH) {
        this.surNameTH = surNameTH;
    }


    /**
     * Gets the titleEN value for this Applicant.
     * 
     * @return titleEN
     */
    public java.lang.String getTitleEN() {
        return titleEN;
    }


    /**
     * Sets the titleEN value for this Applicant.
     * 
     * @param titleEN
     */
    public void setTitleEN(java.lang.String titleEN) {
        this.titleEN = titleEN;
    }


    /**
     * Gets the titleTH value for this Applicant.
     * 
     * @return titleTH
     */
    public java.lang.String getTitleTH() {
        return titleTH;
    }


    /**
     * Sets the titleTH value for this Applicant.
     * 
     * @param titleTH
     */
    public void setTitleTH(java.lang.String titleTH) {
        this.titleTH = titleTH;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof Applicant)) return false;
        Applicant other = (Applicant) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.applicantType==null && other.getApplicantType()==null) || 
             (this.applicantType!=null &&
              this.applicantType.equals(other.getApplicantType()))) &&
            ((this.firstNameEN==null && other.getFirstNameEN()==null) || 
             (this.firstNameEN!=null &&
              this.firstNameEN.equals(other.getFirstNameEN()))) &&
            ((this.firstNameTH==null && other.getFirstNameTH()==null) || 
             (this.firstNameTH!=null &&
              this.firstNameTH.equals(other.getFirstNameTH()))) &&
            ((this.products==null && other.getProducts()==null) || 
             (this.products!=null &&
              java.util.Arrays.equals(this.products, other.getProducts()))) &&
            this.seq == other.getSeq() &&
            ((this.surNameEN==null && other.getSurNameEN()==null) || 
             (this.surNameEN!=null &&
              this.surNameEN.equals(other.getSurNameEN()))) &&
            ((this.surNameTH==null && other.getSurNameTH()==null) || 
             (this.surNameTH!=null &&
              this.surNameTH.equals(other.getSurNameTH()))) &&
            ((this.titleEN==null && other.getTitleEN()==null) || 
             (this.titleEN!=null &&
              this.titleEN.equals(other.getTitleEN()))) &&
            ((this.titleTH==null && other.getTitleTH()==null) || 
             (this.titleTH!=null &&
              this.titleTH.equals(other.getTitleTH())));
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
        if (getApplicantType() != null) {
            _hashCode += getApplicantType().hashCode();
        }
        if (getFirstNameEN() != null) {
            _hashCode += getFirstNameEN().hashCode();
        }
        if (getFirstNameTH() != null) {
            _hashCode += getFirstNameTH().hashCode();
        }
        if (getProducts() != null) {
            for (int i=0;
                 i<java.lang.reflect.Array.getLength(getProducts());
                 i++) {
                java.lang.Object obj = java.lang.reflect.Array.get(getProducts(), i);
                if (obj != null &&
                    !obj.getClass().isArray()) {
                    _hashCode += obj.hashCode();
                }
            }
        }
        _hashCode += getSeq();
        if (getSurNameEN() != null) {
            _hashCode += getSurNameEN().hashCode();
        }
        if (getSurNameTH() != null) {
            _hashCode += getSurNameTH().hashCode();
        }
        if (getTitleEN() != null) {
            _hashCode += getTitleEN().hashCode();
        }
        if (getTitleTH() != null) {
            _hashCode += getTitleTH().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(Applicant.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://response.tracking.ws.sml.integrosys.com", "Applicant"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("applicantType");
        elemField.setXmlName(new javax.xml.namespace.QName("http://response.tracking.ws.sml.integrosys.com", "applicantType"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("firstNameEN");
        elemField.setXmlName(new javax.xml.namespace.QName("http://response.tracking.ws.sml.integrosys.com", "firstNameEN"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("firstNameTH");
        elemField.setXmlName(new javax.xml.namespace.QName("http://response.tracking.ws.sml.integrosys.com", "firstNameTH"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("products");
        elemField.setXmlName(new javax.xml.namespace.QName("http://response.tracking.ws.sml.integrosys.com", "products"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://response.tracking.ws.sml.integrosys.com", "Product"));
        elemField.setNillable(true);
        elemField.setItemQName(new javax.xml.namespace.QName("http://tracking.ws.sml.integrosys.com", "item"));
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("seq");
        elemField.setXmlName(new javax.xml.namespace.QName("http://response.tracking.ws.sml.integrosys.com", "seq"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "int"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("surNameEN");
        elemField.setXmlName(new javax.xml.namespace.QName("http://response.tracking.ws.sml.integrosys.com", "surNameEN"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("surNameTH");
        elemField.setXmlName(new javax.xml.namespace.QName("http://response.tracking.ws.sml.integrosys.com", "surNameTH"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("titleEN");
        elemField.setXmlName(new javax.xml.namespace.QName("http://response.tracking.ws.sml.integrosys.com", "titleEN"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("titleTH");
        elemField.setXmlName(new javax.xml.namespace.QName("http://response.tracking.ws.sml.integrosys.com", "titleTH"));
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
