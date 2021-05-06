/**
 * Body.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.integrosys.sml.ws.tracking.request;

public class Body  implements java.io.Serializable {
    private java.lang.String citizenID;

    private java.lang.String mobileNo;

    private java.lang.String rmNo;

    public Body() {
    }

    public Body(
           java.lang.String citizenID,
           java.lang.String mobileNo,
           java.lang.String rmNo) {
           this.citizenID = citizenID;
           this.mobileNo = mobileNo;
           this.rmNo = rmNo;
    }


    /**
     * Gets the citizenID value for this Body.
     * 
     * @return citizenID
     */
    public java.lang.String getCitizenID() {
        return citizenID;
    }


    /**
     * Sets the citizenID value for this Body.
     * 
     * @param citizenID
     */
    public void setCitizenID(java.lang.String citizenID) {
        this.citizenID = citizenID;
    }


    /**
     * Gets the mobileNo value for this Body.
     * 
     * @return mobileNo
     */
    public java.lang.String getMobileNo() {
        return mobileNo;
    }


    /**
     * Sets the mobileNo value for this Body.
     * 
     * @param mobileNo
     */
    public void setMobileNo(java.lang.String mobileNo) {
        this.mobileNo = mobileNo;
    }


    /**
     * Gets the rmNo value for this Body.
     * 
     * @return rmNo
     */
    public java.lang.String getRmNo() {
        return rmNo;
    }


    /**
     * Sets the rmNo value for this Body.
     * 
     * @param rmNo
     */
    public void setRmNo(java.lang.String rmNo) {
        this.rmNo = rmNo;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof Body)) return false;
        Body other = (Body) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.citizenID==null && other.getCitizenID()==null) || 
             (this.citizenID!=null &&
              this.citizenID.equals(other.getCitizenID()))) &&
            ((this.mobileNo==null && other.getMobileNo()==null) || 
             (this.mobileNo!=null &&
              this.mobileNo.equals(other.getMobileNo()))) &&
            ((this.rmNo==null && other.getRmNo()==null) || 
             (this.rmNo!=null &&
              this.rmNo.equals(other.getRmNo())));
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
        if (getCitizenID() != null) {
            _hashCode += getCitizenID().hashCode();
        }
        if (getMobileNo() != null) {
            _hashCode += getMobileNo().hashCode();
        }
        if (getRmNo() != null) {
            _hashCode += getRmNo().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(Body.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://request.tracking.ws.sml.integrosys.com", "Body"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("citizenID");
        elemField.setXmlName(new javax.xml.namespace.QName("http://request.tracking.ws.sml.integrosys.com", "citizenID"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("mobileNo");
        elemField.setXmlName(new javax.xml.namespace.QName("http://request.tracking.ws.sml.integrosys.com", "mobileNo"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("rmNo");
        elemField.setXmlName(new javax.xml.namespace.QName("http://request.tracking.ws.sml.integrosys.com", "rmNo"));
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
