/**
 * LoanStatusTrackingServiceLocator.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.integrosys.sml.ws.tracking;

public class LoanStatusTrackingServiceLocator extends org.apache.axis.client.Service implements com.integrosys.sml.ws.tracking.LoanStatusTrackingService {

    public LoanStatusTrackingServiceLocator() {
    }


    public LoanStatusTrackingServiceLocator(org.apache.axis.EngineConfiguration config) {
        super(config);
    }

    public LoanStatusTrackingServiceLocator(java.lang.String wsdlLoc, javax.xml.namespace.QName sName) throws javax.xml.rpc.ServiceException {
        super(wsdlLoc, sName);
    }

    // Use to get a proxy class for LoanStatusTracking
    private java.lang.String LoanStatusTracking_address = "http://10.209.27.99:9081/RSLWS/services/LoanStatusTracking";

    public java.lang.String getLoanStatusTrackingAddress() {
        return LoanStatusTracking_address;
    }

    // The WSDD service name defaults to the port name.
    private java.lang.String LoanStatusTrackingWSDDServiceName = "LoanStatusTracking";

    public java.lang.String getLoanStatusTrackingWSDDServiceName() {
        return LoanStatusTrackingWSDDServiceName;
    }

    public void setLoanStatusTrackingWSDDServiceName(java.lang.String name) {
        LoanStatusTrackingWSDDServiceName = name;
    }

    public com.integrosys.sml.ws.tracking.LoanStatusTracking getLoanStatusTracking() throws javax.xml.rpc.ServiceException {
       java.net.URL endpoint;
        try {
            endpoint = new java.net.URL(LoanStatusTracking_address);
        }
        catch (java.net.MalformedURLException e) {
            throw new javax.xml.rpc.ServiceException(e);
        }
        return getLoanStatusTracking(endpoint);
    }

    public com.integrosys.sml.ws.tracking.LoanStatusTracking getLoanStatusTracking(java.net.URL portAddress) throws javax.xml.rpc.ServiceException {
        try {
            com.integrosys.sml.ws.tracking.LoanStatusTrackingSoapBindingStub _stub = new com.integrosys.sml.ws.tracking.LoanStatusTrackingSoapBindingStub(portAddress, this);
            _stub.setPortName(getLoanStatusTrackingWSDDServiceName());
            return _stub;
        }
        catch (org.apache.axis.AxisFault e) {
            return null;
        }
    }

    public void setLoanStatusTrackingEndpointAddress(java.lang.String address) {
        LoanStatusTracking_address = address;
    }

    /**
     * For the given interface, get the stub implementation.
     * If this service has no port for the given interface,
     * then ServiceException is thrown.
     */
    public java.rmi.Remote getPort(Class serviceEndpointInterface) throws javax.xml.rpc.ServiceException {
        try {
            if (com.integrosys.sml.ws.tracking.LoanStatusTracking.class.isAssignableFrom(serviceEndpointInterface)) {
                com.integrosys.sml.ws.tracking.LoanStatusTrackingSoapBindingStub _stub = new com.integrosys.sml.ws.tracking.LoanStatusTrackingSoapBindingStub(new java.net.URL(LoanStatusTracking_address), this);
                _stub.setPortName(getLoanStatusTrackingWSDDServiceName());
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
        if ("LoanStatusTracking".equals(inputPortName)) {
            return getLoanStatusTracking();
        }
        else  {
            java.rmi.Remote _stub = getPort(serviceEndpointInterface);
            ((org.apache.axis.client.Stub) _stub).setPortName(portName);
            return _stub;
        }
    }

    public javax.xml.namespace.QName getServiceName() {
        return new javax.xml.namespace.QName("http://tracking.ws.sml.integrosys.com", "LoanStatusTrackingService");
    }

    private java.util.HashSet ports = null;

    public java.util.Iterator getPorts() {
        if (ports == null) {
            ports = new java.util.HashSet();
            ports.add(new javax.xml.namespace.QName("http://tracking.ws.sml.integrosys.com", "LoanStatusTracking"));
        }
        return ports.iterator();
    }

    /**
    * Set the endpoint address for the specified port name.
    */
    public void setEndpointAddress(java.lang.String portName, java.lang.String address) throws javax.xml.rpc.ServiceException {
        
if ("LoanStatusTracking".equals(portName)) {
            setLoanStatusTrackingEndpointAddress(address);
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
