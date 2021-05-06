/**
 * ResponseInstantLoanGetCustInfo.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.integrosys.sml.ws.instant.eligible.customer.response;

public class ResponseInstantLoanGetCustInfo  implements java.io.Serializable {
    private com.integrosys.sml.ws.instant.eligible.customer.response.Body body;

    private com.integrosys.sml.ws.instant.eligible.customer.response.Header header;

    public ResponseInstantLoanGetCustInfo() {
    }

    public ResponseInstantLoanGetCustInfo(
           com.integrosys.sml.ws.instant.eligible.customer.response.Body body,
           com.integrosys.sml.ws.instant.eligible.customer.response.Header header) {
           this.body = body;
           this.header = header;
    }


    /**
     * Gets the body value for this ResponseInstantLoanGetCustInfo.
     * 
     * @return body
     */
    public com.integrosys.sml.ws.instant.eligible.customer.response.Body getBody() {
        return body;
    }


    /**
     * Sets the body value for this ResponseInstantLoanGetCustInfo.
     * 
     * @param body
     */
    public void setBody(com.integrosys.sml.ws.instant.eligible.customer.response.Body body) {
        this.body = body;
    }


    /**
     * Gets the header value for this ResponseInstantLoanGetCustInfo.
     * 
     * @return header
     */
    public com.integrosys.sml.ws.instant.eligible.customer.response.Header getHeader() {
        return header;
    }


    /**
     * Sets the header value for this ResponseInstantLoanGetCustInfo.
     * 
     * @param header
     */
    public void setHeader(com.integrosys.sml.ws.instant.eligible.customer.response.Header header) {
        this.header = header;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof ResponseInstantLoanGetCustInfo)) return false;
        ResponseInstantLoanGetCustInfo other = (ResponseInstantLoanGetCustInfo) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.body==null && other.getBody()==null) || 
             (this.body!=null &&
              this.body.equals(other.getBody()))) &&
            ((this.header==null && other.getHeader()==null) || 
             (this.header!=null &&
              this.header.equals(other.getHeader())));
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
        if (getBody() != null) {
            _hashCode += getBody().hashCode();
        }
        if (getHeader() != null) {
            _hashCode += getHeader().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(ResponseInstantLoanGetCustInfo.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://response.customer.eligible.instant.ws.sml.integrosys.com", "ResponseInstantLoanGetCustInfo"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("body");
        elemField.setXmlName(new javax.xml.namespace.QName("http://response.customer.eligible.instant.ws.sml.integrosys.com", "body"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://response.customer.eligible.instant.ws.sml.integrosys.com", "Body"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("header");
        elemField.setXmlName(new javax.xml.namespace.QName("http://response.customer.eligible.instant.ws.sml.integrosys.com", "header"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://response.customer.eligible.instant.ws.sml.integrosys.com", "Header"));
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
