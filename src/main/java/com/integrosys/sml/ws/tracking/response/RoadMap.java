/**
 * RoadMap.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.integrosys.sml.ws.tracking.response;

public class RoadMap  implements java.io.Serializable {
    private java.lang.String defaultDescEn;

    private java.lang.String defaultDescTh;

    private java.lang.String defaultPercentComplete;

    private int groupNumber;

    public RoadMap() {
    }

    public RoadMap(
           java.lang.String defaultDescEn,
           java.lang.String defaultDescTh,
           java.lang.String defaultPercentComplete,
           int groupNumber) {
           this.defaultDescEn = defaultDescEn;
           this.defaultDescTh = defaultDescTh;
           this.defaultPercentComplete = defaultPercentComplete;
           this.groupNumber = groupNumber;
    }


    /**
     * Gets the defaultDescEn value for this RoadMap.
     * 
     * @return defaultDescEn
     */
    public java.lang.String getDefaultDescEn() {
        return defaultDescEn;
    }


    /**
     * Sets the defaultDescEn value for this RoadMap.
     * 
     * @param defaultDescEn
     */
    public void setDefaultDescEn(java.lang.String defaultDescEn) {
        this.defaultDescEn = defaultDescEn;
    }


    /**
     * Gets the defaultDescTh value for this RoadMap.
     * 
     * @return defaultDescTh
     */
    public java.lang.String getDefaultDescTh() {
        return defaultDescTh;
    }


    /**
     * Sets the defaultDescTh value for this RoadMap.
     * 
     * @param defaultDescTh
     */
    public void setDefaultDescTh(java.lang.String defaultDescTh) {
        this.defaultDescTh = defaultDescTh;
    }


    /**
     * Gets the defaultPercentComplete value for this RoadMap.
     * 
     * @return defaultPercentComplete
     */
    public java.lang.String getDefaultPercentComplete() {
        return defaultPercentComplete;
    }


    /**
     * Sets the defaultPercentComplete value for this RoadMap.
     * 
     * @param defaultPercentComplete
     */
    public void setDefaultPercentComplete(java.lang.String defaultPercentComplete) {
        this.defaultPercentComplete = defaultPercentComplete;
    }


    /**
     * Gets the groupNumber value for this RoadMap.
     * 
     * @return groupNumber
     */
    public int getGroupNumber() {
        return groupNumber;
    }


    /**
     * Sets the groupNumber value for this RoadMap.
     * 
     * @param groupNumber
     */
    public void setGroupNumber(int groupNumber) {
        this.groupNumber = groupNumber;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof RoadMap)) return false;
        RoadMap other = (RoadMap) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.defaultDescEn==null && other.getDefaultDescEn()==null) || 
             (this.defaultDescEn!=null &&
              this.defaultDescEn.equals(other.getDefaultDescEn()))) &&
            ((this.defaultDescTh==null && other.getDefaultDescTh()==null) || 
             (this.defaultDescTh!=null &&
              this.defaultDescTh.equals(other.getDefaultDescTh()))) &&
            ((this.defaultPercentComplete==null && other.getDefaultPercentComplete()==null) || 
             (this.defaultPercentComplete!=null &&
              this.defaultPercentComplete.equals(other.getDefaultPercentComplete()))) &&
            this.groupNumber == other.getGroupNumber();
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
        if (getDefaultDescEn() != null) {
            _hashCode += getDefaultDescEn().hashCode();
        }
        if (getDefaultDescTh() != null) {
            _hashCode += getDefaultDescTh().hashCode();
        }
        if (getDefaultPercentComplete() != null) {
            _hashCode += getDefaultPercentComplete().hashCode();
        }
        _hashCode += getGroupNumber();
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(RoadMap.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://response.tracking.ws.sml.integrosys.com", "RoadMap"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("defaultDescEn");
        elemField.setXmlName(new javax.xml.namespace.QName("http://response.tracking.ws.sml.integrosys.com", "defaultDescEn"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("defaultDescTh");
        elemField.setXmlName(new javax.xml.namespace.QName("http://response.tracking.ws.sml.integrosys.com", "defaultDescTh"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("defaultPercentComplete");
        elemField.setXmlName(new javax.xml.namespace.QName("http://response.tracking.ws.sml.integrosys.com", "defaultPercentComplete"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("groupNumber");
        elemField.setXmlName(new javax.xml.namespace.QName("http://response.tracking.ws.sml.integrosys.com", "groupNumber"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "int"));
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
