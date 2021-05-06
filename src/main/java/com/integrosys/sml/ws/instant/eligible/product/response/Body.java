/**
 * Body.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.integrosys.sml.ws.instant.eligible.product.response;

public class Body  implements java.io.Serializable {
    private java.lang.String hostCifNo;

    private com.integrosys.sml.common.ob.creditcard.InstantCreditCard[] instantCreditCard;

    private com.integrosys.sml.common.ob.facility.InstantFacility[] instantFacility;

    public Body() {
    }

    public Body(
           java.lang.String hostCifNo,
           com.integrosys.sml.common.ob.creditcard.InstantCreditCard[] instantCreditCard,
           com.integrosys.sml.common.ob.facility.InstantFacility[] instantFacility) {
           this.hostCifNo = hostCifNo;
           this.instantCreditCard = instantCreditCard;
           this.instantFacility = instantFacility;
    }


    /**
     * Gets the hostCifNo value for this Body.
     * 
     * @return hostCifNo
     */
    public java.lang.String getHostCifNo() {
        return hostCifNo;
    }


    /**
     * Sets the hostCifNo value for this Body.
     * 
     * @param hostCifNo
     */
    public void setHostCifNo(java.lang.String hostCifNo) {
        this.hostCifNo = hostCifNo;
    }


    /**
     * Gets the instantCreditCard value for this Body.
     * 
     * @return instantCreditCard
     */
    public com.integrosys.sml.common.ob.creditcard.InstantCreditCard[] getInstantCreditCard() {
        return instantCreditCard;
    }


    /**
     * Sets the instantCreditCard value for this Body.
     * 
     * @param instantCreditCard
     */
    public void setInstantCreditCard(com.integrosys.sml.common.ob.creditcard.InstantCreditCard[] instantCreditCard) {
        this.instantCreditCard = instantCreditCard;
    }


    /**
     * Gets the instantFacility value for this Body.
     * 
     * @return instantFacility
     */
    public com.integrosys.sml.common.ob.facility.InstantFacility[] getInstantFacility() {
        return instantFacility;
    }


    /**
     * Sets the instantFacility value for this Body.
     * 
     * @param instantFacility
     */
    public void setInstantFacility(com.integrosys.sml.common.ob.facility.InstantFacility[] instantFacility) {
        this.instantFacility = instantFacility;
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
            ((this.hostCifNo==null && other.getHostCifNo()==null) || 
             (this.hostCifNo!=null &&
              this.hostCifNo.equals(other.getHostCifNo()))) &&
            ((this.instantCreditCard==null && other.getInstantCreditCard()==null) || 
             (this.instantCreditCard!=null &&
              java.util.Arrays.equals(this.instantCreditCard, other.getInstantCreditCard()))) &&
            ((this.instantFacility==null && other.getInstantFacility()==null) || 
             (this.instantFacility!=null &&
              java.util.Arrays.equals(this.instantFacility, other.getInstantFacility())));
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
        if (getHostCifNo() != null) {
            _hashCode += getHostCifNo().hashCode();
        }
        if (getInstantCreditCard() != null) {
            for (int i=0;
                 i<java.lang.reflect.Array.getLength(getInstantCreditCard());
                 i++) {
                java.lang.Object obj = java.lang.reflect.Array.get(getInstantCreditCard(), i);
                if (obj != null &&
                    !obj.getClass().isArray()) {
                    _hashCode += obj.hashCode();
                }
            }
        }
        if (getInstantFacility() != null) {
            for (int i=0;
                 i<java.lang.reflect.Array.getLength(getInstantFacility());
                 i++) {
                java.lang.Object obj = java.lang.reflect.Array.get(getInstantFacility(), i);
                if (obj != null &&
                    !obj.getClass().isArray()) {
                    _hashCode += obj.hashCode();
                }
            }
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(Body.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://response.product.eligible.instant.ws.sml.integrosys.com", "Body"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("hostCifNo");
        elemField.setXmlName(new javax.xml.namespace.QName("http://response.product.eligible.instant.ws.sml.integrosys.com", "hostCifNo"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("instantCreditCard");
        elemField.setXmlName(new javax.xml.namespace.QName("http://response.product.eligible.instant.ws.sml.integrosys.com", "instantCreditCard"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://creditcard.ob.common.sml.integrosys.com", "InstantCreditCard"));
        elemField.setNillable(true);
        elemField.setItemQName(new javax.xml.namespace.QName("http://submission.loan.ws.sml.integrosys.com", "item"));
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("instantFacility");
        elemField.setXmlName(new javax.xml.namespace.QName("http://response.product.eligible.instant.ws.sml.integrosys.com", "instantFacility"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://facility.ob.common.sml.integrosys.com", "InstantFacility"));
        elemField.setNillable(true);
        elemField.setItemQName(new javax.xml.namespace.QName("http://submission.loan.ws.sml.integrosys.com", "item"));
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
