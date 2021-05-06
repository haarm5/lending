/**
 * Body.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.integrosys.sml.ws.dropdown.response;

public class Body  implements java.io.Serializable {
    private com.integrosys.sml.common.ob.dropdown.CommonCodeEntry[] commonCodeEntries;

    public Body() {
    }

    public Body(
           com.integrosys.sml.common.ob.dropdown.CommonCodeEntry[] commonCodeEntries) {
           this.commonCodeEntries = commonCodeEntries;
    }


    /**
     * Gets the commonCodeEntries value for this Body.
     * 
     * @return commonCodeEntries
     */
    public com.integrosys.sml.common.ob.dropdown.CommonCodeEntry[] getCommonCodeEntries() {
        return commonCodeEntries;
    }


    /**
     * Sets the commonCodeEntries value for this Body.
     * 
     * @param commonCodeEntries
     */
    public void setCommonCodeEntries(com.integrosys.sml.common.ob.dropdown.CommonCodeEntry[] commonCodeEntries) {
        this.commonCodeEntries = commonCodeEntries;
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
            ((this.commonCodeEntries==null && other.getCommonCodeEntries()==null) || 
             (this.commonCodeEntries!=null &&
              java.util.Arrays.equals(this.commonCodeEntries, other.getCommonCodeEntries())));
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
        if (getCommonCodeEntries() != null) {
            for (int i=0;
                 i<java.lang.reflect.Array.getLength(getCommonCodeEntries());
                 i++) {
                java.lang.Object obj = java.lang.reflect.Array.get(getCommonCodeEntries(), i);
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
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://response.dropdown.ws.sml.integrosys.com", "Body"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("commonCodeEntries");
        elemField.setXmlName(new javax.xml.namespace.QName("http://response.dropdown.ws.sml.integrosys.com", "commonCodeEntries"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://dropdown.ob.common.sml.integrosys.com", "CommonCodeEntry"));
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
