/**
 * Address.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.integrosys.sml.common.ob.address;

public class Address  implements java.io.Serializable {
    private java.lang.String addrTypCode;

    private java.lang.String address;

    private java.lang.String amphur;

    private java.lang.String buildingName;

    private java.math.BigDecimal cifId;

    private java.lang.String country;

    private java.lang.String floor;

    private java.math.BigDecimal id;

    private java.lang.String moo;

    private java.lang.String postalCode;

    private java.lang.String province;

    private java.lang.String road;

    private java.lang.String streetName;

    private java.lang.String tumbol;

    public Address() {
    }

    public Address(
           java.lang.String addrTypCode,
           java.lang.String address,
           java.lang.String amphur,
           java.lang.String buildingName,
           java.math.BigDecimal cifId,
           java.lang.String country,
           java.lang.String floor,
           java.math.BigDecimal id,
           java.lang.String moo,
           java.lang.String postalCode,
           java.lang.String province,
           java.lang.String road,
           java.lang.String streetName,
           java.lang.String tumbol) {
           this.addrTypCode = addrTypCode;
           this.address = address;
           this.amphur = amphur;
           this.buildingName = buildingName;
           this.cifId = cifId;
           this.country = country;
           this.floor = floor;
           this.id = id;
           this.moo = moo;
           this.postalCode = postalCode;
           this.province = province;
           this.road = road;
           this.streetName = streetName;
           this.tumbol = tumbol;
    }


    /**
     * Gets the addrTypCode value for this Address.
     * 
     * @return addrTypCode
     */
    public java.lang.String getAddrTypCode() {
        return addrTypCode;
    }


    /**
     * Sets the addrTypCode value for this Address.
     * 
     * @param addrTypCode
     */
    public void setAddrTypCode(java.lang.String addrTypCode) {
        this.addrTypCode = addrTypCode;
    }


    /**
     * Gets the address value for this Address.
     * 
     * @return address
     */
    public java.lang.String getAddress() {
        return address;
    }


    /**
     * Sets the address value for this Address.
     * 
     * @param address
     */
    public void setAddress(java.lang.String address) {
        this.address = address;
    }


    /**
     * Gets the amphur value for this Address.
     * 
     * @return amphur
     */
    public java.lang.String getAmphur() {
        return amphur;
    }


    /**
     * Sets the amphur value for this Address.
     * 
     * @param amphur
     */
    public void setAmphur(java.lang.String amphur) {
        this.amphur = amphur;
    }


    /**
     * Gets the buildingName value for this Address.
     * 
     * @return buildingName
     */
    public java.lang.String getBuildingName() {
        return buildingName;
    }


    /**
     * Sets the buildingName value for this Address.
     * 
     * @param buildingName
     */
    public void setBuildingName(java.lang.String buildingName) {
        this.buildingName = buildingName;
    }


    /**
     * Gets the cifId value for this Address.
     * 
     * @return cifId
     */
    public java.math.BigDecimal getCifId() {
        return cifId;
    }


    /**
     * Sets the cifId value for this Address.
     * 
     * @param cifId
     */
    public void setCifId(java.math.BigDecimal cifId) {
        this.cifId = cifId;
    }


    /**
     * Gets the country value for this Address.
     * 
     * @return country
     */
    public java.lang.String getCountry() {
        return country;
    }


    /**
     * Sets the country value for this Address.
     * 
     * @param country
     */
    public void setCountry(java.lang.String country) {
        this.country = country;
    }


    /**
     * Gets the floor value for this Address.
     * 
     * @return floor
     */
    public java.lang.String getFloor() {
        return floor;
    }


    /**
     * Sets the floor value for this Address.
     * 
     * @param floor
     */
    public void setFloor(java.lang.String floor) {
        this.floor = floor;
    }


    /**
     * Gets the id value for this Address.
     * 
     * @return id
     */
    public java.math.BigDecimal getId() {
        return id;
    }


    /**
     * Sets the id value for this Address.
     * 
     * @param id
     */
    public void setId(java.math.BigDecimal id) {
        this.id = id;
    }


    /**
     * Gets the moo value for this Address.
     * 
     * @return moo
     */
    public java.lang.String getMoo() {
        return moo;
    }


    /**
     * Sets the moo value for this Address.
     * 
     * @param moo
     */
    public void setMoo(java.lang.String moo) {
        this.moo = moo;
    }


    /**
     * Gets the postalCode value for this Address.
     * 
     * @return postalCode
     */
    public java.lang.String getPostalCode() {
        return postalCode;
    }


    /**
     * Sets the postalCode value for this Address.
     * 
     * @param postalCode
     */
    public void setPostalCode(java.lang.String postalCode) {
        this.postalCode = postalCode;
    }


    /**
     * Gets the province value for this Address.
     * 
     * @return province
     */
    public java.lang.String getProvince() {
        return province;
    }


    /**
     * Sets the province value for this Address.
     * 
     * @param province
     */
    public void setProvince(java.lang.String province) {
        this.province = province;
    }


    /**
     * Gets the road value for this Address.
     * 
     * @return road
     */
    public java.lang.String getRoad() {
        return road;
    }


    /**
     * Sets the road value for this Address.
     * 
     * @param road
     */
    public void setRoad(java.lang.String road) {
        this.road = road;
    }


    /**
     * Gets the streetName value for this Address.
     * 
     * @return streetName
     */
    public java.lang.String getStreetName() {
        return streetName;
    }


    /**
     * Sets the streetName value for this Address.
     * 
     * @param streetName
     */
    public void setStreetName(java.lang.String streetName) {
        this.streetName = streetName;
    }


    /**
     * Gets the tumbol value for this Address.
     * 
     * @return tumbol
     */
    public java.lang.String getTumbol() {
        return tumbol;
    }


    /**
     * Sets the tumbol value for this Address.
     * 
     * @param tumbol
     */
    public void setTumbol(java.lang.String tumbol) {
        this.tumbol = tumbol;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof Address)) return false;
        Address other = (Address) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.addrTypCode==null && other.getAddrTypCode()==null) || 
             (this.addrTypCode!=null &&
              this.addrTypCode.equals(other.getAddrTypCode()))) &&
            ((this.address==null && other.getAddress()==null) || 
             (this.address!=null &&
              this.address.equals(other.getAddress()))) &&
            ((this.amphur==null && other.getAmphur()==null) || 
             (this.amphur!=null &&
              this.amphur.equals(other.getAmphur()))) &&
            ((this.buildingName==null && other.getBuildingName()==null) || 
             (this.buildingName!=null &&
              this.buildingName.equals(other.getBuildingName()))) &&
            ((this.cifId==null && other.getCifId()==null) || 
             (this.cifId!=null &&
              this.cifId.equals(other.getCifId()))) &&
            ((this.country==null && other.getCountry()==null) || 
             (this.country!=null &&
              this.country.equals(other.getCountry()))) &&
            ((this.floor==null && other.getFloor()==null) || 
             (this.floor!=null &&
              this.floor.equals(other.getFloor()))) &&
            ((this.id==null && other.getId()==null) || 
             (this.id!=null &&
              this.id.equals(other.getId()))) &&
            ((this.moo==null && other.getMoo()==null) || 
             (this.moo!=null &&
              this.moo.equals(other.getMoo()))) &&
            ((this.postalCode==null && other.getPostalCode()==null) || 
             (this.postalCode!=null &&
              this.postalCode.equals(other.getPostalCode()))) &&
            ((this.province==null && other.getProvince()==null) || 
             (this.province!=null &&
              this.province.equals(other.getProvince()))) &&
            ((this.road==null && other.getRoad()==null) || 
             (this.road!=null &&
              this.road.equals(other.getRoad()))) &&
            ((this.streetName==null && other.getStreetName()==null) || 
             (this.streetName!=null &&
              this.streetName.equals(other.getStreetName()))) &&
            ((this.tumbol==null && other.getTumbol()==null) || 
             (this.tumbol!=null &&
              this.tumbol.equals(other.getTumbol())));
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
        if (getAddrTypCode() != null) {
            _hashCode += getAddrTypCode().hashCode();
        }
        if (getAddress() != null) {
            _hashCode += getAddress().hashCode();
        }
        if (getAmphur() != null) {
            _hashCode += getAmphur().hashCode();
        }
        if (getBuildingName() != null) {
            _hashCode += getBuildingName().hashCode();
        }
        if (getCifId() != null) {
            _hashCode += getCifId().hashCode();
        }
        if (getCountry() != null) {
            _hashCode += getCountry().hashCode();
        }
        if (getFloor() != null) {
            _hashCode += getFloor().hashCode();
        }
        if (getId() != null) {
            _hashCode += getId().hashCode();
        }
        if (getMoo() != null) {
            _hashCode += getMoo().hashCode();
        }
        if (getPostalCode() != null) {
            _hashCode += getPostalCode().hashCode();
        }
        if (getProvince() != null) {
            _hashCode += getProvince().hashCode();
        }
        if (getRoad() != null) {
            _hashCode += getRoad().hashCode();
        }
        if (getStreetName() != null) {
            _hashCode += getStreetName().hashCode();
        }
        if (getTumbol() != null) {
            _hashCode += getTumbol().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(Address.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://address.ob.common.sml.integrosys.com", "Address"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("addrTypCode");
        elemField.setXmlName(new javax.xml.namespace.QName("http://address.ob.common.sml.integrosys.com", "addrTypCode"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("address");
        elemField.setXmlName(new javax.xml.namespace.QName("http://address.ob.common.sml.integrosys.com", "address"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("amphur");
        elemField.setXmlName(new javax.xml.namespace.QName("http://address.ob.common.sml.integrosys.com", "amphur"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("buildingName");
        elemField.setXmlName(new javax.xml.namespace.QName("http://address.ob.common.sml.integrosys.com", "buildingName"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("cifId");
        elemField.setXmlName(new javax.xml.namespace.QName("http://address.ob.common.sml.integrosys.com", "cifId"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "decimal"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("country");
        elemField.setXmlName(new javax.xml.namespace.QName("http://address.ob.common.sml.integrosys.com", "country"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("floor");
        elemField.setXmlName(new javax.xml.namespace.QName("http://address.ob.common.sml.integrosys.com", "floor"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("id");
        elemField.setXmlName(new javax.xml.namespace.QName("http://address.ob.common.sml.integrosys.com", "id"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "decimal"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("moo");
        elemField.setXmlName(new javax.xml.namespace.QName("http://address.ob.common.sml.integrosys.com", "moo"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("postalCode");
        elemField.setXmlName(new javax.xml.namespace.QName("http://address.ob.common.sml.integrosys.com", "postalCode"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("province");
        elemField.setXmlName(new javax.xml.namespace.QName("http://address.ob.common.sml.integrosys.com", "province"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("road");
        elemField.setXmlName(new javax.xml.namespace.QName("http://address.ob.common.sml.integrosys.com", "road"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("streetName");
        elemField.setXmlName(new javax.xml.namespace.QName("http://address.ob.common.sml.integrosys.com", "streetName"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("tumbol");
        elemField.setXmlName(new javax.xml.namespace.QName("http://address.ob.common.sml.integrosys.com", "tumbol"));
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
