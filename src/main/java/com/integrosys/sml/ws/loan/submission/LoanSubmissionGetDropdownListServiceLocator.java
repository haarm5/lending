/**
 * LoanSubmissionGetDropdownListServiceLocator.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.integrosys.sml.ws.loan.submission;

public class LoanSubmissionGetDropdownListServiceLocator extends org.apache.axis.client.Service implements com.integrosys.sml.ws.loan.submission.LoanSubmissionGetDropdownListService {

    public LoanSubmissionGetDropdownListServiceLocator() {
    }


    public LoanSubmissionGetDropdownListServiceLocator(org.apache.axis.EngineConfiguration config) {
        super(config);
    }

    public LoanSubmissionGetDropdownListServiceLocator(java.lang.String wsdlLoc, javax.xml.namespace.QName sName) throws javax.xml.rpc.ServiceException {
        super(wsdlLoc, sName);
    }

    // Use to get a proxy class for LoanSubmissionGetDropdownList
    private java.lang.String LoanSubmissionGetDropdownList_address = "http://10.209.27.99:9081/LoanSubmissionWS/services/LoanSubmissionGetDropdownList";

    public java.lang.String getLoanSubmissionGetDropdownListAddress() {
        return LoanSubmissionGetDropdownList_address;
    }

    // The WSDD service name defaults to the port name.
    private java.lang.String LoanSubmissionGetDropdownListWSDDServiceName = "LoanSubmissionGetDropdownList";

    public java.lang.String getLoanSubmissionGetDropdownListWSDDServiceName() {
        return LoanSubmissionGetDropdownListWSDDServiceName;
    }

    public void setLoanSubmissionGetDropdownListWSDDServiceName(java.lang.String name) {
        LoanSubmissionGetDropdownListWSDDServiceName = name;
    }

    public com.integrosys.sml.ws.loan.submission.LoanSubmissionGetDropdownList getLoanSubmissionGetDropdownList() throws javax.xml.rpc.ServiceException {
       java.net.URL endpoint;
        try {
            endpoint = new java.net.URL(LoanSubmissionGetDropdownList_address);
        }
        catch (java.net.MalformedURLException e) {
            throw new javax.xml.rpc.ServiceException(e);
        }
        return getLoanSubmissionGetDropdownList(endpoint);
    }

    public com.integrosys.sml.ws.loan.submission.LoanSubmissionGetDropdownList getLoanSubmissionGetDropdownList(java.net.URL portAddress) throws javax.xml.rpc.ServiceException {
        try {
            com.integrosys.sml.ws.loan.submission.LoanSubmissionGetDropdownListSoapBindingStub _stub = new com.integrosys.sml.ws.loan.submission.LoanSubmissionGetDropdownListSoapBindingStub(portAddress, this);
            _stub.setPortName(getLoanSubmissionGetDropdownListWSDDServiceName());
            return _stub;
        }
        catch (org.apache.axis.AxisFault e) {
            return null;
        }
    }

    public void setLoanSubmissionGetDropdownListEndpointAddress(java.lang.String address) {
        LoanSubmissionGetDropdownList_address = address;
    }

    /**
     * For the given interface, get the stub implementation.
     * If this service has no port for the given interface,
     * then ServiceException is thrown.
     */
    public java.rmi.Remote getPort(Class serviceEndpointInterface) throws javax.xml.rpc.ServiceException {
        try {
            if (com.integrosys.sml.ws.loan.submission.LoanSubmissionGetDropdownList.class.isAssignableFrom(serviceEndpointInterface)) {
                com.integrosys.sml.ws.loan.submission.LoanSubmissionGetDropdownListSoapBindingStub _stub = new com.integrosys.sml.ws.loan.submission.LoanSubmissionGetDropdownListSoapBindingStub(new java.net.URL(LoanSubmissionGetDropdownList_address), this);
                _stub.setPortName(getLoanSubmissionGetDropdownListWSDDServiceName());
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
        if ("LoanSubmissionGetDropdownList".equals(inputPortName)) {
            return getLoanSubmissionGetDropdownList();
        }
        else  {
            java.rmi.Remote _stub = getPort(serviceEndpointInterface);
            ((org.apache.axis.client.Stub) _stub).setPortName(portName);
            return _stub;
        }
    }

    public javax.xml.namespace.QName getServiceName() {
        return new javax.xml.namespace.QName("http://submission.loan.ws.sml.integrosys.com", "LoanSubmissionGetDropdownListService");
    }

    private java.util.HashSet ports = null;

    public java.util.Iterator getPorts() {
        if (ports == null) {
            ports = new java.util.HashSet();
            ports.add(new javax.xml.namespace.QName("http://submission.loan.ws.sml.integrosys.com", "LoanSubmissionGetDropdownList"));
        }
        return ports.iterator();
    }

    /**
    * Set the endpoint address for the specified port name.
    */
    public void setEndpointAddress(java.lang.String portName, java.lang.String address) throws javax.xml.rpc.ServiceException {
        
if ("LoanSubmissionGetDropdownList".equals(portName)) {
            setLoanSubmissionGetDropdownListEndpointAddress(address);
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
