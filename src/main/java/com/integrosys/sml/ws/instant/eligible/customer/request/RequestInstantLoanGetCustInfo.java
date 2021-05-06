/**
 * RequestInstantLoanGetCustInfo.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.integrosys.sml.ws.instant.eligible.customer.request;

public class RequestInstantLoanGetCustInfo  implements java.io.Serializable {
    private com.integrosys.sml.ws.instant.eligible.customer.request.Body body;

    private com.integrosys.sml.ws.instant.eligible.customer.request.Header header;

    public RequestInstantLoanGetCustInfo() {
    }

    public RequestInstantLoanGetCustInfo(
           com.integrosys.sml.ws.instant.eligible.customer.request.Body body,
           com.integrosys.sml.ws.instant.eligible.customer.request.Header header) {
           this.body = body;
           this.header = header;
    }


    /**
     * Gets the body value for this RequestInstantLoanGetCustInfo.
     * 
     * @return body
     */
    public com.integrosys.sml.ws.instant.eligible.customer.request.Body getBody() {
        return body;
    }


    /**
     * Sets the body value for this RequestInstantLoanGetCustInfo.
     * 
     * @param body
     */
    public void setBody(com.integrosys.sml.ws.instant.eligible.customer.request.Body body) {
        this.body = body;
    }


    /**
     * Gets the header value for this RequestInstantLoanGetCustInfo.
     * 
     * @return header
     */
    public com.integrosys.sml.ws.instant.eligible.customer.request.Header getHeader() {
        return header;
    }


    /**
     * Sets the header value for this RequestInstantLoanGetCustInfo.
     * 
     * @param header
     */
    public void setHeader(com.integrosys.sml.ws.instant.eligible.customer.request.Header header) {
        this.header = header;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof RequestInstantLoanGetCustInfo)) return false;
        RequestInstantLoanGetCustInfo other = (RequestInstantLoanGetCustInfo) obj;
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
        new org.apache.axis.description.TypeDesc(RequestInstantLoanGetCustInfo.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://request.customer.eligible.instant.ws.sml.integrosys.com", "RequestInstantLoanGetCustInfo"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("body");
        elemField.setXmlName(new javax.xml.namespace.QName("http://request.customer.eligible.instant.ws.sml.integrosys.com", "body"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://request.customer.eligible.instant.ws.sml.integrosys.com", "Body"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("header");
        elemField.setXmlName(new javax.xml.namespace.QName("http://request.customer.eligible.instant.ws.sml.integrosys.com", "header"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://request.customer.eligible.instant.ws.sml.integrosys.com", "Header"));
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
