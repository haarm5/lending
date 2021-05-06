/**
 * CommonCodeEntry.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.integrosys.sml.common.ob.dropdown;

public class CommonCodeEntry  implements java.io.Serializable {
    private java.lang.String activeStatus;

    private java.lang.String categoryCode;

    private java.lang.String entryCode;

    private java.math.BigDecimal entryID;

    private java.lang.String entryName;

    private java.lang.String entryName2;

    private java.lang.String entrySource;

    private java.lang.String extValue1;

    private java.lang.String extValue2;

    private java.lang.String groupId;

    private java.lang.String refEntryCode;

    public CommonCodeEntry() {
    }

    public CommonCodeEntry(
           java.lang.String activeStatus,
           java.lang.String categoryCode,
           java.lang.String entryCode,
           java.math.BigDecimal entryID,
           java.lang.String entryName,
           java.lang.String entryName2,
           java.lang.String entrySource,
           java.lang.String extValue1,
           java.lang.String extValue2,
           java.lang.String groupId,
           java.lang.String refEntryCode) {
           this.activeStatus = activeStatus;
           this.categoryCode = categoryCode;
           this.entryCode = entryCode;
           this.entryID = entryID;
           this.entryName = entryName;
           this.entryName2 = entryName2;
           this.entrySource = entrySource;
           this.extValue1 = extValue1;
           this.extValue2 = extValue2;
           this.groupId = groupId;
           this.refEntryCode = refEntryCode;
    }


    /**
     * Gets the activeStatus value for this CommonCodeEntry.
     * 
     * @return activeStatus
     */
    public java.lang.String getActiveStatus() {
        return activeStatus;
    }


    /**
     * Sets the activeStatus value for this CommonCodeEntry.
     * 
     * @param activeStatus
     */
    public void setActiveStatus(java.lang.String activeStatus) {
        this.activeStatus = activeStatus;
    }


    /**
     * Gets the categoryCode value for this CommonCodeEntry.
     * 
     * @return categoryCode
     */
    public java.lang.String getCategoryCode() {
        return categoryCode;
    }


    /**
     * Sets the categoryCode value for this CommonCodeEntry.
     * 
     * @param categoryCode
     */
    public void setCategoryCode(java.lang.String categoryCode) {
        this.categoryCode = categoryCode;
    }


    /**
     * Gets the entryCode value for this CommonCodeEntry.
     * 
     * @return entryCode
     */
    public java.lang.String getEntryCode() {
        return entryCode;
    }


    /**
     * Sets the entryCode value for this CommonCodeEntry.
     * 
     * @param entryCode
     */
    public void setEntryCode(java.lang.String entryCode) {
        this.entryCode = entryCode;
    }


    /**
     * Gets the entryID value for this CommonCodeEntry.
     * 
     * @return entryID
     */
    public java.math.BigDecimal getEntryID() {
        return entryID;
    }


    /**
     * Sets the entryID value for this CommonCodeEntry.
     * 
     * @param entryID
     */
    public void setEntryID(java.math.BigDecimal entryID) {
        this.entryID = entryID;
    }


    /**
     * Gets the entryName value for this CommonCodeEntry.
     * 
     * @return entryName
     */
    public java.lang.String getEntryName() {
        return entryName;
    }


    /**
     * Sets the entryName value for this CommonCodeEntry.
     * 
     * @param entryName
     */
    public void setEntryName(java.lang.String entryName) {
        this.entryName = entryName;
    }


    /**
     * Gets the entryName2 value for this CommonCodeEntry.
     * 
     * @return entryName2
     */
    public java.lang.String getEntryName2() {
        return entryName2;
    }


    /**
     * Sets the entryName2 value for this CommonCodeEntry.
     * 
     * @param entryName2
     */
    public void setEntryName2(java.lang.String entryName2) {
        this.entryName2 = entryName2;
    }


    /**
     * Gets the entrySource value for this CommonCodeEntry.
     * 
     * @return entrySource
     */
    public java.lang.String getEntrySource() {
        return entrySource;
    }


    /**
     * Sets the entrySource value for this CommonCodeEntry.
     * 
     * @param entrySource
     */
    public void setEntrySource(java.lang.String entrySource) {
        this.entrySource = entrySource;
    }


    /**
     * Gets the extValue1 value for this CommonCodeEntry.
     * 
     * @return extValue1
     */
    public java.lang.String getExtValue1() {
        return extValue1;
    }


    /**
     * Sets the extValue1 value for this CommonCodeEntry.
     * 
     * @param extValue1
     */
    public void setExtValue1(java.lang.String extValue1) {
        this.extValue1 = extValue1;
    }


    /**
     * Gets the extValue2 value for this CommonCodeEntry.
     * 
     * @return extValue2
     */
    public java.lang.String getExtValue2() {
        return extValue2;
    }


    /**
     * Sets the extValue2 value for this CommonCodeEntry.
     * 
     * @param extValue2
     */
    public void setExtValue2(java.lang.String extValue2) {
        this.extValue2 = extValue2;
    }


    /**
     * Gets the groupId value for this CommonCodeEntry.
     * 
     * @return groupId
     */
    public java.lang.String getGroupId() {
        return groupId;
    }


    /**
     * Sets the groupId value for this CommonCodeEntry.
     * 
     * @param groupId
     */
    public void setGroupId(java.lang.String groupId) {
        this.groupId = groupId;
    }


    /**
     * Gets the refEntryCode value for this CommonCodeEntry.
     * 
     * @return refEntryCode
     */
    public java.lang.String getRefEntryCode() {
        return refEntryCode;
    }


    /**
     * Sets the refEntryCode value for this CommonCodeEntry.
     * 
     * @param refEntryCode
     */
    public void setRefEntryCode(java.lang.String refEntryCode) {
        this.refEntryCode = refEntryCode;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof CommonCodeEntry)) return false;
        CommonCodeEntry other = (CommonCodeEntry) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.activeStatus==null && other.getActiveStatus()==null) || 
             (this.activeStatus!=null &&
              this.activeStatus.equals(other.getActiveStatus()))) &&
            ((this.categoryCode==null && other.getCategoryCode()==null) || 
             (this.categoryCode!=null &&
              this.categoryCode.equals(other.getCategoryCode()))) &&
            ((this.entryCode==null && other.getEntryCode()==null) || 
             (this.entryCode!=null &&
              this.entryCode.equals(other.getEntryCode()))) &&
            ((this.entryID==null && other.getEntryID()==null) || 
             (this.entryID!=null &&
              this.entryID.equals(other.getEntryID()))) &&
            ((this.entryName==null && other.getEntryName()==null) || 
             (this.entryName!=null &&
              this.entryName.equals(other.getEntryName()))) &&
            ((this.entryName2==null && other.getEntryName2()==null) || 
             (this.entryName2!=null &&
              this.entryName2.equals(other.getEntryName2()))) &&
            ((this.entrySource==null && other.getEntrySource()==null) || 
             (this.entrySource!=null &&
              this.entrySource.equals(other.getEntrySource()))) &&
            ((this.extValue1==null && other.getExtValue1()==null) || 
             (this.extValue1!=null &&
              this.extValue1.equals(other.getExtValue1()))) &&
            ((this.extValue2==null && other.getExtValue2()==null) || 
             (this.extValue2!=null &&
              this.extValue2.equals(other.getExtValue2()))) &&
            ((this.groupId==null && other.getGroupId()==null) || 
             (this.groupId!=null &&
              this.groupId.equals(other.getGroupId()))) &&
            ((this.refEntryCode==null && other.getRefEntryCode()==null) || 
             (this.refEntryCode!=null &&
              this.refEntryCode.equals(other.getRefEntryCode())));
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
        if (getActiveStatus() != null) {
            _hashCode += getActiveStatus().hashCode();
        }
        if (getCategoryCode() != null) {
            _hashCode += getCategoryCode().hashCode();
        }
        if (getEntryCode() != null) {
            _hashCode += getEntryCode().hashCode();
        }
        if (getEntryID() != null) {
            _hashCode += getEntryID().hashCode();
        }
        if (getEntryName() != null) {
            _hashCode += getEntryName().hashCode();
        }
        if (getEntryName2() != null) {
            _hashCode += getEntryName2().hashCode();
        }
        if (getEntrySource() != null) {
            _hashCode += getEntrySource().hashCode();
        }
        if (getExtValue1() != null) {
            _hashCode += getExtValue1().hashCode();
        }
        if (getExtValue2() != null) {
            _hashCode += getExtValue2().hashCode();
        }
        if (getGroupId() != null) {
            _hashCode += getGroupId().hashCode();
        }
        if (getRefEntryCode() != null) {
            _hashCode += getRefEntryCode().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(CommonCodeEntry.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://dropdown.ob.common.sml.integrosys.com", "CommonCodeEntry"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("activeStatus");
        elemField.setXmlName(new javax.xml.namespace.QName("http://dropdown.ob.common.sml.integrosys.com", "activeStatus"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("categoryCode");
        elemField.setXmlName(new javax.xml.namespace.QName("http://dropdown.ob.common.sml.integrosys.com", "categoryCode"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("entryCode");
        elemField.setXmlName(new javax.xml.namespace.QName("http://dropdown.ob.common.sml.integrosys.com", "entryCode"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("entryID");
        elemField.setXmlName(new javax.xml.namespace.QName("http://dropdown.ob.common.sml.integrosys.com", "entryID"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "decimal"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("entryName");
        elemField.setXmlName(new javax.xml.namespace.QName("http://dropdown.ob.common.sml.integrosys.com", "entryName"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("entryName2");
        elemField.setXmlName(new javax.xml.namespace.QName("http://dropdown.ob.common.sml.integrosys.com", "entryName2"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("entrySource");
        elemField.setXmlName(new javax.xml.namespace.QName("http://dropdown.ob.common.sml.integrosys.com", "entrySource"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("extValue1");
        elemField.setXmlName(new javax.xml.namespace.QName("http://dropdown.ob.common.sml.integrosys.com", "extValue1"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("extValue2");
        elemField.setXmlName(new javax.xml.namespace.QName("http://dropdown.ob.common.sml.integrosys.com", "extValue2"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("groupId");
        elemField.setXmlName(new javax.xml.namespace.QName("http://dropdown.ob.common.sml.integrosys.com", "groupId"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("refEntryCode");
        elemField.setXmlName(new javax.xml.namespace.QName("http://dropdown.ob.common.sml.integrosys.com", "refEntryCode"));
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
