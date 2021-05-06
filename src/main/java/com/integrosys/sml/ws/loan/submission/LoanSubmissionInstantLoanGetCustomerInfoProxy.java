package com.integrosys.sml.ws.loan.submission;

public class LoanSubmissionInstantLoanGetCustomerInfoProxy implements com.integrosys.sml.ws.loan.submission.LoanSubmissionInstantLoanGetCustomerInfo {
  private String _endpoint = null;
  private com.integrosys.sml.ws.loan.submission.LoanSubmissionInstantLoanGetCustomerInfo loanSubmissionInstantLoanGetCustomerInfo = null;
  
  public LoanSubmissionInstantLoanGetCustomerInfoProxy() {
    _initLoanSubmissionInstantLoanGetCustomerInfoProxy();
  }
  
  public LoanSubmissionInstantLoanGetCustomerInfoProxy(String endpoint) {
    _endpoint = endpoint;
    _initLoanSubmissionInstantLoanGetCustomerInfoProxy();
  }
  
  private void _initLoanSubmissionInstantLoanGetCustomerInfoProxy() {
    try {
      loanSubmissionInstantLoanGetCustomerInfo = (new com.integrosys.sml.ws.loan.submission.LoanSubmissionInstantLoanGetCustomerInfoServiceLocator()).getLoanSubmissionInstantLoanGetCustomerInfo();
      if (loanSubmissionInstantLoanGetCustomerInfo != null) {
        if (_endpoint != null)
          ((javax.xml.rpc.Stub)loanSubmissionInstantLoanGetCustomerInfo)._setProperty("javax.xml.rpc.service.endpoint.address", _endpoint);
        else
          _endpoint = (String)((javax.xml.rpc.Stub)loanSubmissionInstantLoanGetCustomerInfo)._getProperty("javax.xml.rpc.service.endpoint.address");
      }
      
    }
    catch (javax.xml.rpc.ServiceException serviceException) {}
  }
  
  public String getEndpoint() {
    return _endpoint;
  }
  
  public void setEndpoint(String endpoint) {
    _endpoint = endpoint;
    if (loanSubmissionInstantLoanGetCustomerInfo != null)
      ((javax.xml.rpc.Stub)loanSubmissionInstantLoanGetCustomerInfo)._setProperty("javax.xml.rpc.service.endpoint.address", _endpoint);
    
  }
  
  public com.integrosys.sml.ws.loan.submission.LoanSubmissionInstantLoanGetCustomerInfo getLoanSubmissionInstantLoanGetCustomerInfo() {
    if (loanSubmissionInstantLoanGetCustomerInfo == null)
      _initLoanSubmissionInstantLoanGetCustomerInfoProxy();
    return loanSubmissionInstantLoanGetCustomerInfo;
  }
  
  public com.integrosys.sml.ws.instant.eligible.customer.response.ResponseInstantLoanGetCustInfo getInstantCustomerInfo(com.integrosys.sml.ws.instant.eligible.customer.request.RequestInstantLoanGetCustInfo req) throws java.rmi.RemoteException{
    if (loanSubmissionInstantLoanGetCustomerInfo == null)
      _initLoanSubmissionInstantLoanGetCustomerInfoProxy();
    return loanSubmissionInstantLoanGetCustomerInfo.getInstantCustomerInfo(req);
  }
  
  
}