/**
 * Product.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.integrosys.sml.ws.tracking.response;

public class Product  implements java.io.Serializable {
    private double approveCreditLimit;

    private java.lang.String bankAccountNumber;

    private java.lang.String cardName;

    private java.lang.String disburseBankCode;

    private java.lang.String disburseBankFullEN;

    private java.lang.String disburseBankFullTH;

    private java.lang.String disburseBankShort;

    private double dsr;

    private double dti;

    private java.lang.String paymentMethod;

    private java.lang.String paymentMethodDescEN;

    private java.lang.String paymentMethodDescTH;

    private com.integrosys.sml.ws.tracking.response.Pricing[] pricings;

    private java.lang.String productCode;

    private java.lang.String productDescEN;

    private java.lang.String productDescTH;

    private java.lang.String subProductCode;

    private java.lang.String subProductDescEN;

    private java.lang.String subProductDescTH;

    private double tenure;

    public Product() {
    }

    public Product(
           double approveCreditLimit,
           java.lang.String bankAccountNumber,
           java.lang.String cardName,
           java.lang.String disburseBankCode,
           java.lang.String disburseBankFullEN,
           java.lang.String disburseBankFullTH,
           java.lang.String disburseBankShort,
           double dsr,
           double dti,
           java.lang.String paymentMethod,
           java.lang.String paymentMethodDescEN,
           java.lang.String paymentMethodDescTH,
           com.integrosys.sml.ws.tracking.response.Pricing[] pricings,
           java.lang.String productCode,
           java.lang.String productDescEN,
           java.lang.String productDescTH,
           java.lang.String subProductCode,
           java.lang.String subProductDescEN,
           java.lang.String subProductDescTH,
           double tenure) {
           this.approveCreditLimit = approveCreditLimit;
           this.bankAccountNumber = bankAccountNumber;
           this.cardName = cardName;
           this.disburseBankCode = disburseBankCode;
           this.disburseBankFullEN = disburseBankFullEN;
           this.disburseBankFullTH = disburseBankFullTH;
           this.disburseBankShort = disburseBankShort;
           this.dsr = dsr;
           this.dti = dti;
           this.paymentMethod = paymentMethod;
           this.paymentMethodDescEN = paymentMethodDescEN;
           this.paymentMethodDescTH = paymentMethodDescTH;
           this.pricings = pricings;
           this.productCode = productCode;
           this.productDescEN = productDescEN;
           this.productDescTH = productDescTH;
           this.subProductCode = subProductCode;
           this.subProductDescEN = subProductDescEN;
           this.subProductDescTH = subProductDescTH;
           this.tenure = tenure;
    }


    /**
     * Gets the approveCreditLimit value for this Product.
     * 
     * @return approveCreditLimit
     */
    public double getApproveCreditLimit() {
        return approveCreditLimit;
    }


    /**
     * Sets the approveCreditLimit value for this Product.
     * 
     * @param approveCreditLimit
     */
    public void setApproveCreditLimit(double approveCreditLimit) {
        this.approveCreditLimit = approveCreditLimit;
    }


    /**
     * Gets the bankAccountNumber value for this Product.
     * 
     * @return bankAccountNumber
     */
    public java.lang.String getBankAccountNumber() {
        return bankAccountNumber;
    }


    /**
     * Sets the bankAccountNumber value for this Product.
     * 
     * @param bankAccountNumber
     */
    public void setBankAccountNumber(java.lang.String bankAccountNumber) {
        this.bankAccountNumber = bankAccountNumber;
    }


    /**
     * Gets the cardName value for this Product.
     * 
     * @return cardName
     */
    public java.lang.String getCardName() {
        return cardName;
    }


    /**
     * Sets the cardName value for this Product.
     * 
     * @param cardName
     */
    public void setCardName(java.lang.String cardName) {
        this.cardName = cardName;
    }


    /**
     * Gets the disburseBankCode value for this Product.
     * 
     * @return disburseBankCode
     */
    public java.lang.String getDisburseBankCode() {
        return disburseBankCode;
    }


    /**
     * Sets the disburseBankCode value for this Product.
     * 
     * @param disburseBankCode
     */
    public void setDisburseBankCode(java.lang.String disburseBankCode) {
        this.disburseBankCode = disburseBankCode;
    }


    /**
     * Gets the disburseBankFullEN value for this Product.
     * 
     * @return disburseBankFullEN
     */
    public java.lang.String getDisburseBankFullEN() {
        return disburseBankFullEN;
    }


    /**
     * Sets the disburseBankFullEN value for this Product.
     * 
     * @param disburseBankFullEN
     */
    public void setDisburseBankFullEN(java.lang.String disburseBankFullEN) {
        this.disburseBankFullEN = disburseBankFullEN;
    }


    /**
     * Gets the disburseBankFullTH value for this Product.
     * 
     * @return disburseBankFullTH
     */
    public java.lang.String getDisburseBankFullTH() {
        return disburseBankFullTH;
    }


    /**
     * Sets the disburseBankFullTH value for this Product.
     * 
     * @param disburseBankFullTH
     */
    public void setDisburseBankFullTH(java.lang.String disburseBankFullTH) {
        this.disburseBankFullTH = disburseBankFullTH;
    }


    /**
     * Gets the disburseBankShort value for this Product.
     * 
     * @return disburseBankShort
     */
    public java.lang.String getDisburseBankShort() {
        return disburseBankShort;
    }


    /**
     * Sets the disburseBankShort value for this Product.
     * 
     * @param disburseBankShort
     */
    public void setDisburseBankShort(java.lang.String disburseBankShort) {
        this.disburseBankShort = disburseBankShort;
    }


    /**
     * Gets the dsr value for this Product.
     * 
     * @return dsr
     */
    public double getDsr() {
        return dsr;
    }


    /**
     * Sets the dsr value for this Product.
     * 
     * @param dsr
     */
    public void setDsr(double dsr) {
        this.dsr = dsr;
    }


    /**
     * Gets the dti value for this Product.
     * 
     * @return dti
     */
    public double getDti() {
        return dti;
    }


    /**
     * Sets the dti value for this Product.
     * 
     * @param dti
     */
    public void setDti(double dti) {
        this.dti = dti;
    }


    /**
     * Gets the paymentMethod value for this Product.
     * 
     * @return paymentMethod
     */
    public java.lang.String getPaymentMethod() {
        return paymentMethod;
    }


    /**
     * Sets the paymentMethod value for this Product.
     * 
     * @param paymentMethod
     */
    public void setPaymentMethod(java.lang.String paymentMethod) {
        this.paymentMethod = paymentMethod;
    }


    /**
     * Gets the paymentMethodDescEN value for this Product.
     * 
     * @return paymentMethodDescEN
     */
    public java.lang.String getPaymentMethodDescEN() {
        return paymentMethodDescEN;
    }


    /**
     * Sets the paymentMethodDescEN value for this Product.
     * 
     * @param paymentMethodDescEN
     */
    public void setPaymentMethodDescEN(java.lang.String paymentMethodDescEN) {
        this.paymentMethodDescEN = paymentMethodDescEN;
    }


    /**
     * Gets the paymentMethodDescTH value for this Product.
     * 
     * @return paymentMethodDescTH
     */
    public java.lang.String getPaymentMethodDescTH() {
        return paymentMethodDescTH;
    }


    /**
     * Sets the paymentMethodDescTH value for this Product.
     * 
     * @param paymentMethodDescTH
     */
    public void setPaymentMethodDescTH(java.lang.String paymentMethodDescTH) {
        this.paymentMethodDescTH = paymentMethodDescTH;
    }


    /**
     * Gets the pricings value for this Product.
     * 
     * @return pricings
     */
    public com.integrosys.sml.ws.tracking.response.Pricing[] getPricings() {
        return pricings;
    }


    /**
     * Sets the pricings value for this Product.
     * 
     * @param pricings
     */
    public void setPricings(com.integrosys.sml.ws.tracking.response.Pricing[] pricings) {
        this.pricings = pricings;
    }


    /**
     * Gets the productCode value for this Product.
     * 
     * @return productCode
     */
    public java.lang.String getProductCode() {
        return productCode;
    }


    /**
     * Sets the productCode value for this Product.
     * 
     * @param productCode
     */
    public void setProductCode(java.lang.String productCode) {
        this.productCode = productCode;
    }


    /**
     * Gets the productDescEN value for this Product.
     * 
     * @return productDescEN
     */
    public java.lang.String getProductDescEN() {
        return productDescEN;
    }


    /**
     * Sets the productDescEN value for this Product.
     * 
     * @param productDescEN
     */
    public void setProductDescEN(java.lang.String productDescEN) {
        this.productDescEN = productDescEN;
    }


    /**
     * Gets the productDescTH value for this Product.
     * 
     * @return productDescTH
     */
    public java.lang.String getProductDescTH() {
        return productDescTH;
    }


    /**
     * Sets the productDescTH value for this Product.
     * 
     * @param productDescTH
     */
    public void setProductDescTH(java.lang.String productDescTH) {
        this.productDescTH = productDescTH;
    }


    /**
     * Gets the subProductCode value for this Product.
     * 
     * @return subProductCode
     */
    public java.lang.String getSubProductCode() {
        return subProductCode;
    }


    /**
     * Sets the subProductCode value for this Product.
     * 
     * @param subProductCode
     */
    public void setSubProductCode(java.lang.String subProductCode) {
        this.subProductCode = subProductCode;
    }


    /**
     * Gets the subProductDescEN value for this Product.
     * 
     * @return subProductDescEN
     */
    public java.lang.String getSubProductDescEN() {
        return subProductDescEN;
    }


    /**
     * Sets the subProductDescEN value for this Product.
     * 
     * @param subProductDescEN
     */
    public void setSubProductDescEN(java.lang.String subProductDescEN) {
        this.subProductDescEN = subProductDescEN;
    }


    /**
     * Gets the subProductDescTH value for this Product.
     * 
     * @return subProductDescTH
     */
    public java.lang.String getSubProductDescTH() {
        return subProductDescTH;
    }


    /**
     * Sets the subProductDescTH value for this Product.
     * 
     * @param subProductDescTH
     */
    public void setSubProductDescTH(java.lang.String subProductDescTH) {
        this.subProductDescTH = subProductDescTH;
    }


    /**
     * Gets the tenure value for this Product.
     * 
     * @return tenure
     */
    public double getTenure() {
        return tenure;
    }


    /**
     * Sets the tenure value for this Product.
     * 
     * @param tenure
     */
    public void setTenure(double tenure) {
        this.tenure = tenure;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof Product)) return false;
        Product other = (Product) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            this.approveCreditLimit == other.getApproveCreditLimit() &&
            ((this.bankAccountNumber==null && other.getBankAccountNumber()==null) || 
             (this.bankAccountNumber!=null &&
              this.bankAccountNumber.equals(other.getBankAccountNumber()))) &&
            ((this.cardName==null && other.getCardName()==null) || 
             (this.cardName!=null &&
              this.cardName.equals(other.getCardName()))) &&
            ((this.disburseBankCode==null && other.getDisburseBankCode()==null) || 
             (this.disburseBankCode!=null &&
              this.disburseBankCode.equals(other.getDisburseBankCode()))) &&
            ((this.disburseBankFullEN==null && other.getDisburseBankFullEN()==null) || 
             (this.disburseBankFullEN!=null &&
              this.disburseBankFullEN.equals(other.getDisburseBankFullEN()))) &&
            ((this.disburseBankFullTH==null && other.getDisburseBankFullTH()==null) || 
             (this.disburseBankFullTH!=null &&
              this.disburseBankFullTH.equals(other.getDisburseBankFullTH()))) &&
            ((this.disburseBankShort==null && other.getDisburseBankShort()==null) || 
             (this.disburseBankShort!=null &&
              this.disburseBankShort.equals(other.getDisburseBankShort()))) &&
            this.dsr == other.getDsr() &&
            this.dti == other.getDti() &&
            ((this.paymentMethod==null && other.getPaymentMethod()==null) || 
             (this.paymentMethod!=null &&
              this.paymentMethod.equals(other.getPaymentMethod()))) &&
            ((this.paymentMethodDescEN==null && other.getPaymentMethodDescEN()==null) || 
             (this.paymentMethodDescEN!=null &&
              this.paymentMethodDescEN.equals(other.getPaymentMethodDescEN()))) &&
            ((this.paymentMethodDescTH==null && other.getPaymentMethodDescTH()==null) || 
             (this.paymentMethodDescTH!=null &&
              this.paymentMethodDescTH.equals(other.getPaymentMethodDescTH()))) &&
            ((this.pricings==null && other.getPricings()==null) || 
             (this.pricings!=null &&
              java.util.Arrays.equals(this.pricings, other.getPricings()))) &&
            ((this.productCode==null && other.getProductCode()==null) || 
             (this.productCode!=null &&
              this.productCode.equals(other.getProductCode()))) &&
            ((this.productDescEN==null && other.getProductDescEN()==null) || 
             (this.productDescEN!=null &&
              this.productDescEN.equals(other.getProductDescEN()))) &&
            ((this.productDescTH==null && other.getProductDescTH()==null) || 
             (this.productDescTH!=null &&
              this.productDescTH.equals(other.getProductDescTH()))) &&
            ((this.subProductCode==null && other.getSubProductCode()==null) || 
             (this.subProductCode!=null &&
              this.subProductCode.equals(other.getSubProductCode()))) &&
            ((this.subProductDescEN==null && other.getSubProductDescEN()==null) || 
             (this.subProductDescEN!=null &&
              this.subProductDescEN.equals(other.getSubProductDescEN()))) &&
            ((this.subProductDescTH==null && other.getSubProductDescTH()==null) || 
             (this.subProductDescTH!=null &&
              this.subProductDescTH.equals(other.getSubProductDescTH()))) &&
            this.tenure == other.getTenure();
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
        _hashCode += new Double(getApproveCreditLimit()).hashCode();
        if (getBankAccountNumber() != null) {
            _hashCode += getBankAccountNumber().hashCode();
        }
        if (getCardName() != null) {
            _hashCode += getCardName().hashCode();
        }
        if (getDisburseBankCode() != null) {
            _hashCode += getDisburseBankCode().hashCode();
        }
        if (getDisburseBankFullEN() != null) {
            _hashCode += getDisburseBankFullEN().hashCode();
        }
        if (getDisburseBankFullTH() != null) {
            _hashCode += getDisburseBankFullTH().hashCode();
        }
        if (getDisburseBankShort() != null) {
            _hashCode += getDisburseBankShort().hashCode();
        }
        _hashCode += new Double(getDsr()).hashCode();
        _hashCode += new Double(getDti()).hashCode();
        if (getPaymentMethod() != null) {
            _hashCode += getPaymentMethod().hashCode();
        }
        if (getPaymentMethodDescEN() != null) {
            _hashCode += getPaymentMethodDescEN().hashCode();
        }
        if (getPaymentMethodDescTH() != null) {
            _hashCode += getPaymentMethodDescTH().hashCode();
        }
        if (getPricings() != null) {
            for (int i=0;
                 i<java.lang.reflect.Array.getLength(getPricings());
                 i++) {
                java.lang.Object obj = java.lang.reflect.Array.get(getPricings(), i);
                if (obj != null &&
                    !obj.getClass().isArray()) {
                    _hashCode += obj.hashCode();
                }
            }
        }
        if (getProductCode() != null) {
            _hashCode += getProductCode().hashCode();
        }
        if (getProductDescEN() != null) {
            _hashCode += getProductDescEN().hashCode();
        }
        if (getProductDescTH() != null) {
            _hashCode += getProductDescTH().hashCode();
        }
        if (getSubProductCode() != null) {
            _hashCode += getSubProductCode().hashCode();
        }
        if (getSubProductDescEN() != null) {
            _hashCode += getSubProductDescEN().hashCode();
        }
        if (getSubProductDescTH() != null) {
            _hashCode += getSubProductDescTH().hashCode();
        }
        _hashCode += new Double(getTenure()).hashCode();
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(Product.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://response.tracking.ws.sml.integrosys.com", "Product"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("approveCreditLimit");
        elemField.setXmlName(new javax.xml.namespace.QName("http://response.tracking.ws.sml.integrosys.com", "approveCreditLimit"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "double"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("bankAccountNumber");
        elemField.setXmlName(new javax.xml.namespace.QName("http://response.tracking.ws.sml.integrosys.com", "bankAccountNumber"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("cardName");
        elemField.setXmlName(new javax.xml.namespace.QName("http://response.tracking.ws.sml.integrosys.com", "cardName"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("disburseBankCode");
        elemField.setXmlName(new javax.xml.namespace.QName("http://response.tracking.ws.sml.integrosys.com", "disburseBankCode"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("disburseBankFullEN");
        elemField.setXmlName(new javax.xml.namespace.QName("http://response.tracking.ws.sml.integrosys.com", "disburseBankFullEN"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("disburseBankFullTH");
        elemField.setXmlName(new javax.xml.namespace.QName("http://response.tracking.ws.sml.integrosys.com", "disburseBankFullTH"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("disburseBankShort");
        elemField.setXmlName(new javax.xml.namespace.QName("http://response.tracking.ws.sml.integrosys.com", "disburseBankShort"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("dsr");
        elemField.setXmlName(new javax.xml.namespace.QName("http://response.tracking.ws.sml.integrosys.com", "dsr"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "double"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("dti");
        elemField.setXmlName(new javax.xml.namespace.QName("http://response.tracking.ws.sml.integrosys.com", "dti"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "double"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("paymentMethod");
        elemField.setXmlName(new javax.xml.namespace.QName("http://response.tracking.ws.sml.integrosys.com", "paymentMethod"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("paymentMethodDescEN");
        elemField.setXmlName(new javax.xml.namespace.QName("http://response.tracking.ws.sml.integrosys.com", "paymentMethodDescEN"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("paymentMethodDescTH");
        elemField.setXmlName(new javax.xml.namespace.QName("http://response.tracking.ws.sml.integrosys.com", "paymentMethodDescTH"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("pricings");
        elemField.setXmlName(new javax.xml.namespace.QName("http://response.tracking.ws.sml.integrosys.com", "pricings"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://response.tracking.ws.sml.integrosys.com", "Pricing"));
        elemField.setNillable(true);
        elemField.setItemQName(new javax.xml.namespace.QName("http://tracking.ws.sml.integrosys.com", "item"));
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("productCode");
        elemField.setXmlName(new javax.xml.namespace.QName("http://response.tracking.ws.sml.integrosys.com", "productCode"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("productDescEN");
        elemField.setXmlName(new javax.xml.namespace.QName("http://response.tracking.ws.sml.integrosys.com", "productDescEN"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("productDescTH");
        elemField.setXmlName(new javax.xml.namespace.QName("http://response.tracking.ws.sml.integrosys.com", "productDescTH"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("subProductCode");
        elemField.setXmlName(new javax.xml.namespace.QName("http://response.tracking.ws.sml.integrosys.com", "subProductCode"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("subProductDescEN");
        elemField.setXmlName(new javax.xml.namespace.QName("http://response.tracking.ws.sml.integrosys.com", "subProductDescEN"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("subProductDescTH");
        elemField.setXmlName(new javax.xml.namespace.QName("http://response.tracking.ws.sml.integrosys.com", "subProductDescTH"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("tenure");
        elemField.setXmlName(new javax.xml.namespace.QName("http://response.tracking.ws.sml.integrosys.com", "tenure"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "double"));
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
