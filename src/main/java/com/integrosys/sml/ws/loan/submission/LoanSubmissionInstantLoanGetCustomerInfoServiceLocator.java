/**
 * LoanSubmissionInstantLoanGetCustomerInfoServiceLocator.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.integrosys.sml.ws.loan.submission;

public class LoanSubmissionInstantLoanGetCustomerInfoServiceLocator extends org.apache.axis.client.Service implements com.integrosys.sml.ws.loan.submission.LoanSubmissionInstantLoanGetCustomerInfoService {

    public LoanSubmissionInstantLoanGetCustomerInfoServiceLocator() {
    }


    public LoanSubmissionInstantLoanGetCustomerInfoServiceLocator(org.apache.axis.EngineConfiguration config) {
        super(config);
    }

    public LoanSubmissionInstantLoanGetCustomerInfoServiceLocator(java.lang.String wsdlLoc, javax.xml.namespace.QName sName) throws javax.xml.rpc.ServiceException {
        super(wsdlLoc, sName);
    }

    // Use to get a proxy class for LoanSubmissionInstantLoanGetCustomerInfo
    private java.lang.String LoanSubmissionInstantLoanGetCustomerInfo_address = "http://10.209.27.99:9081/LoanSubmissionWS/services/LoanSubmissionInstantLoanGetCustomerInfo";

    public java.lang.String getLoanSubmissionInstantLoanGetCustomerInfoAddress() {
        return LoanSubmissionInstantLoanGetCustomerInfo_address;
    }

    // The WSDD service name defaults to the port name.
    private java.lang.String LoanSubmissionInstantLoanGetCustomerInfoWSDDServiceName = "LoanSubmissionInstantLoanGetCustomerInfo";

    public java.lang.String getLoanSubmissionInstantLoanGetCustomerInfoWSDDServiceName() {
        return LoanSubmissionInstantLoanGetCustomerInfoWSDDServiceName;
    }

    public void setLoanSubmissionInstantLoanGetCustomerInfoWSDDServiceName(java.lang.String name) {
        LoanSubmissionInstantLoanGetCustomerInfoWSDDServiceName = name;
    }

    public com.integrosys.sml.ws.loan.submission.LoanSubmissionInstantLoanGetCustomerInfo getLoanSubmissionInstantLoanGetCustomerInfo() throws javax.xml.rpc.ServiceException {
       java.net.URL endpoint;
        try {
            endpoint = new java.net.URL(LoanSubmissionInstantLoanGetCustomerInfo_address);
        }
        catch (java.net.MalformedURLException e) {
            throw new javax.xml.rpc.ServiceException(e);
        }
        return getLoanSubmissionInstantLoanGetCustomerInfo(endpoint);
    }

    public com.integrosys.sml.ws.loan.submission.LoanSubmissionInstantLoanGetCustomerInfo getLoanSubmissionInstantLoanGetCustomerInfo(java.net.URL portAddress) throws javax.xml.rpc.ServiceException {
        try {
            com.integrosys.sml.ws.loan.submission.LoanSubmissionInstantLoanGetCustomerInfoSoapBindingStub _stub = new com.integrosys.sml.ws.loan.submission.LoanSubmissionInstantLoanGetCustomerInfoSoapBindingStub(portAddress, this);
            _stub.setPortName(getLoanSubmissionInstantLoanGetCustomerInfoWSDDServiceName());
            return _stub;
        }
        catch (org.apache.axis.AxisFault e) {
            return null;
        }
    }

    public void setLoanSubmissionInstantLoanGetCustomerInfoEndpointAddress(java.lang.String address) {
        LoanSubmissionInstantLoanGetCustomerInfo_address = address;
    }

    /**
     * For the given interface, get the stub implementation.
     * If this service has no port for the given interface,
     * then ServiceException is thrown.
     */
    public java.rmi.Remote getPort(Class serviceEndpointInterface) throws javax.xml.rpc.ServiceException {
        try {
            if (com.integrosys.sml.ws.loan.submission.LoanSubmissionInstantLoanGetCustomerInfo.class.isAssignableFrom(serviceEndpointInterface)) {
                com.integrosys.sml.ws.loan.submission.LoanSubmissionInstantLoanGetCustomerInfoSoapBindingStub _stub = new com.integrosys.sml.ws.loan.submission.LoanSubmissionInstantLoanGetCustomerInfoSoapBindingStub(new java.net.URL(LoanSubmissionInstantLoanGetCustomerInfo_address), this);
                _stub.setPortName(getLoanSubmissionInstantLoanGetCustomerInfoWSDDServiceName());
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
        if ("LoanSubmissionInstantLoanGetCustomerInfo".equals(inputPortName)) {
            return getLoanSubmissionInstantLoanGetCustomerInfo();
        }
        else  {
            java.rmi.Remote _stub = getPort(serviceEndpointInterface);
            ((org.apache.axis.client.Stub) _stub).setPortName(portName);
            return _stub;
        }
    }

    public javax.xml.namespace.QName getServiceName() {
        return new javax.xml.namespace.QName("http://submission.loan.ws.sml.integrosys.com", "LoanSubmissionInstantLoanGetCustomerInfoService");
    }

    private java.util.HashSet ports = null;

    public java.util.Iterator getPorts() {
        if (ports == null) {
            ports = new java.util.HashSet();
            ports.add(new javax.xml.namespace.QName("http://submission.loan.ws.sml.integrosys.com", "LoanSubmissionInstantLoanGetCustomerInfo"));
        }
        return ports.iterator();
    }

    /**
    * Set the endpoint address for the specified port name.
    */
    public void setEndpointAddress(java.lang.String portName, java.lang.String address) throws javax.xml.rpc.ServiceException {
        
if ("LoanSubmissionInstantLoanGetCustomerInfo".equals(portName)) {
            setLoanSubmissionInstantLoanGetCustomerInfoEndpointAddress(address);
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
