package com.integrosys.sml.ws.tracking;

public class LoanStatusTrackingProxy implements com.integrosys.sml.ws.tracking.LoanStatusTracking {
  private String _endpoint = null;
  private com.integrosys.sml.ws.tracking.LoanStatusTracking loanStatusTracking = null;
  
  public LoanStatusTrackingProxy() {
    _initLoanStatusTrackingProxy();
  }
  
  public LoanStatusTrackingProxy(String endpoint) {
    _endpoint = endpoint;
    _initLoanStatusTrackingProxy();
  }
  
  private void _initLoanStatusTrackingProxy() {
    try {
      loanStatusTracking = (new com.integrosys.sml.ws.tracking.LoanStatusTrackingServiceLocator()).getLoanStatusTracking();
      if (loanStatusTracking != null) {
        if (_endpoint != null)
          ((javax.xml.rpc.Stub)loanStatusTracking)._setProperty("javax.xml.rpc.service.endpoint.address", _endpoint);
        else
          _endpoint = (String)((javax.xml.rpc.Stub)loanStatusTracking)._getProperty("javax.xml.rpc.service.endpoint.address");
      }
      
    }
    catch (javax.xml.rpc.ServiceException serviceException) {}
  }
  
  public String getEndpoint() {
    return _endpoint;
  }
  
  public void setEndpoint(String endpoint) {
    _endpoint = endpoint;
    if (loanStatusTracking != null)
      ((javax.xml.rpc.Stub)loanStatusTracking)._setProperty("javax.xml.rpc.service.endpoint.address", _endpoint);
    
  }
  
  public com.integrosys.sml.ws.tracking.LoanStatusTracking getLoanStatusTracking() {
    if (loanStatusTracking == null)
      _initLoanStatusTrackingProxy();
    return loanStatusTracking;
  }
  
  public com.integrosys.sml.ws.tracking.response.ResponseTracking searchAppStatusByID(com.integrosys.sml.ws.tracking.request.RequestTracking req) throws java.rmi.RemoteException{
    if (loanStatusTracking == null)
      _initLoanStatusTrackingProxy();
    return loanStatusTracking.searchAppStatusByID(req);
  }
  
  
}