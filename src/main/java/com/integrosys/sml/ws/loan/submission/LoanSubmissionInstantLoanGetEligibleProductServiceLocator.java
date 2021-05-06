/**
 * LoanSubmissionInstantLoanGetEligibleProductServiceLocator.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.integrosys.sml.ws.loan.submission;

public class LoanSubmissionInstantLoanGetEligibleProductServiceLocator extends org.apache.axis.client.Service implements com.integrosys.sml.ws.loan.submission.LoanSubmissionInstantLoanGetEligibleProductService {

    public LoanSubmissionInstantLoanGetEligibleProductServiceLocator() {
    }


    public LoanSubmissionInstantLoanGetEligibleProductServiceLocator(org.apache.axis.EngineConfiguration config) {
        super(config);
    }

    public LoanSubmissionInstantLoanGetEligibleProductServiceLocator(java.lang.String wsdlLoc, javax.xml.namespace.QName sName) throws javax.xml.rpc.ServiceException {
        super(wsdlLoc, sName);
    }

    // Use to get a proxy class for LoanSubmissionInstantLoanGetEligibleProduct
    private java.lang.String LoanSubmissionInstantLoanGetEligibleProduct_address = "http://10.209.27.99:9081/LoanSubmissionWS/services/LoanSubmissionInstantLoanGetEligibleProduct";

    public java.lang.String getLoanSubmissionInstantLoanGetEligibleProductAddress() {
        return LoanSubmissionInstantLoanGetEligibleProduct_address;
    }

    // The WSDD service name defaults to the port name.
    private java.lang.String LoanSubmissionInstantLoanGetEligibleProductWSDDServiceName = "LoanSubmissionInstantLoanGetEligibleProduct";

    public java.lang.String getLoanSubmissionInstantLoanGetEligibleProductWSDDServiceName() {
        return LoanSubmissionInstantLoanGetEligibleProductWSDDServiceName;
    }

    public void setLoanSubmissionInstantLoanGetEligibleProductWSDDServiceName(java.lang.String name) {
        LoanSubmissionInstantLoanGetEligibleProductWSDDServiceName = name;
    }

    public com.integrosys.sml.ws.loan.submission.LoanSubmissionInstantLoanGetEligibleProduct getLoanSubmissionInstantLoanGetEligibleProduct() throws javax.xml.rpc.ServiceException {
       java.net.URL endpoint;
        try {
            endpoint = new java.net.URL(LoanSubmissionInstantLoanGetEligibleProduct_address);
        }
        catch (java.net.MalformedURLException e) {
            throw new javax.xml.rpc.ServiceException(e);
        }
        return getLoanSubmissionInstantLoanGetEligibleProduct(endpoint);
    }

    public com.integrosys.sml.ws.loan.submission.LoanSubmissionInstantLoanGetEligibleProduct getLoanSubmissionInstantLoanGetEligibleProduct(java.net.URL portAddress) throws javax.xml.rpc.ServiceException {
        try {
            com.integrosys.sml.ws.loan.submission.LoanSubmissionInstantLoanGetEligibleProductSoapBindingStub _stub = new com.integrosys.sml.ws.loan.submission.LoanSubmissionInstantLoanGetEligibleProductSoapBindingStub(portAddress, this);
            _stub.setPortName(getLoanSubmissionInstantLoanGetEligibleProductWSDDServiceName());
            return _stub;
        }
        catch (org.apache.axis.AxisFault e) {
            return null;
        }
    }

    public void setLoanSubmissionInstantLoanGetEligibleProductEndpointAddress(java.lang.String address) {
        LoanSubmissionInstantLoanGetEligibleProduct_address = address;
    }

    /**
     * For the given interface, get the stub implementation.
     * If this service has no port for the given interface,
     * then ServiceException is thrown.
     */
    public java.rmi.Remote getPort(Class serviceEndpointInterface) throws javax.xml.rpc.ServiceException {
        try {
            if (com.integrosys.sml.ws.loan.submission.LoanSubmissionInstantLoanGetEligibleProduct.class.isAssignableFrom(serviceEndpointInterface)) {
                com.integrosys.sml.ws.loan.submission.LoanSubmissionInstantLoanGetEligibleProductSoapBindingStub _stub = new com.integrosys.sml.ws.loan.submission.LoanSubmissionInstantLoanGetEligibleProductSoapBindingStub(new java.net.URL(LoanSubmissionInstantLoanGetEligibleProduct_address), this);
                _stub.setPortName(getLoanSubmissionInstantLoanGetEligibleProductWSDDServiceName());
                return _stub;
            }
        }
        catch (java.lang.Throwable t) {
            throw new javax.xml.rpc.ServiceException(t);
        }
        throw new javax.xml.rpc.ServiceException("There is no stub implementation for the interface:  " + (serviceEndpointInterface == null ? "null" : serviceEndpointInterface.getName()));
    }

    /**
     * For the given interface, get the stub implementation.
     * If this service has no port for the given interface,
     * then ServiceException is thrown.
     */
    public java.rmi.Remote getPort(javax.xml.namespace.QName portName, Class serviceEndpointInterface) throws javax.xml.rpc.ServiceException {
        if (portName == null) {
            return getPort(serviceEndpointInterface);
        }
        java.lang.String inputPortName = portName.getLocalPart();
        if ("LoanSubmissionInstantLoanGetEligibleProduct".equals(inputPortName)) {
            return getLoanSubmissionInstantLoanGetEligibleProduct();
        }
        else  {
            java.rmi.Remote _stub = getPort(serviceEndpointInterface);
            ((org.apache.axis.client.Stub) _stub).setPortName(portName);
            return _stub;
        }
    }

    public javax.xml.namespace.QName getServiceName() {
        return new javax.xml.namespace.QName("http://submission.loan.ws.sml.integrosys.com", "LoanSubmissionInstantLoanGetEligibleProductService");
    }

    private java.util.HashSet ports = null;

    public java.util.Iterator getPorts() {
        if (ports == null) {
            ports = new java.util.HashSet();
            ports.add(new javax.xml.namespace.QName("http://submission.loan.ws.sml.integrosys.com", "LoanSubmissionInstantLoanGetEligibleProduct"));
        }
        return ports.iterator();
    }

    /**
    * Set the endpoint address for the specified port name.
    */
    public void setEndpointAddress(java.lang.String portName, java.lang.String address) throws javax.xml.rpc.ServiceException {
        
if ("LoanSubmissionInstantLoanGetEligibleProduct".equals(portName)) {
            setLoanSubmissionInstantLoanGetEligibleProductEndpointAddress(address);
        }
        else 
{ // Unknown Port Name
            throw new javax.xml.rpc.ServiceException(" Cannot set Endpoint Address for Unknown Port" + portName);
        }
    }

    /**
    * Set the endpoint address for the specified port name.
    */
    public void setEndpointAddress(javax.xml.namespace.QName portName, java.lang.String address) throws javax.xml.rpc.ServiceException {
        setEndpointAddress(portName.getLocalPart(), address);
    }

}
