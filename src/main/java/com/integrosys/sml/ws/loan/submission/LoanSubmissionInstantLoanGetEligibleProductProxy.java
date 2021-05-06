package com.integrosys.sml.ws.loan.submission;

public class LoanSubmissionInstantLoanGetEligibleProductProxy implements com.integrosys.sml.ws.loan.submission.LoanSubmissionInstantLoanGetEligibleProduct {
  private String _endpoint = null;
  private com.integrosys.sml.ws.loan.submission.LoanSubmissionInstantLoanGetEligibleProduct loanSubmissionInstantLoanGetEligibleProduct = null;
  
  public LoanSubmissionInstantLoanGetEligibleProductProxy() {
    _initLoanSubmissionInstantLoanGetEligibleProductProxy();
  }
  
  public LoanSubmissionInstantLoanGetEligibleProductProxy(String endpoint) {
    _endpoint = endpoint;
    _initLoanSubmissionInstantLoanGetEligibleProductProxy();
  }
  
  private void _initLoanSubmissionInstantLoanGetEligibleProductProxy() {
    try {
      loanSubmissionInstantLoanGetEligibleProduct = (new com.integrosys.sml.ws.loan.submission.LoanSubmissionInstantLoanGetEligibleProductServiceLocator()).getLoanSubmissionInstantLoanGetEligibleProduct();
      if (loanSubmissionInstantLoanGetEligibleProduct != null) {
        if (_endpoint != null)
          ((javax.xml.rpc.Stub)loanSubmissionInstantLoanGetEligibleProduct)._setProperty("javax.xml.rpc.service.endpoint.address", _endpoint);
        else
          _endpoint = (String)((javax.xml.rpc.Stub)loanSubmissionInstantLoanGetEligibleProduct)._getProperty("javax.xml.rpc.service.endpoint.address");
      }
      
    }
    catch (javax.xml.rpc.ServiceException serviceException) {}
  }
  
  public String getEndpoint() {
    return _endpoint;
  }
  
  public void setEndpoint(String endpoint) {
    _endpoint = endpoint;
    if (loanSubmissionInstantLoanGetEligibleProduct != null)
      ((javax.xml.rpc.Stub)loanSubmissionInstantLoanGetEligibleProduct)._setProperty("javax.xml.rpc.service.endpoint.address", _endpoint);
    
  }
  
  public com.integrosys.sml.ws.loan.submission.LoanSubmissionInstantLoanGetEligibleProduct getLoanSubmissionInstantLoanGetEligibleProduct() {
    if (loanSubmissionInstantLoanGetEligibleProduct == null)
      _initLoanSubmissionInstantLoanGetEligibleProductProxy();
    return loanSubmissionInstantLoanGetEligibleProduct;
  }
  
  public com.integrosys.sml.ws.instant.eligible.product.response.ResponseInstantLoanGetEligibleProduct getEligibleProduct(com.integrosys.sml.ws.instant.eligible.product.request.RequestInstantLoanGetEligibleProduct req) throws java.rmi.RemoteException{
    if (loanSubmissionInstantLoanGetEligibleProduct == null)
      _initLoanSubmissionInstantLoanGetEligibleProductProxy();
    return loanSubmissionInstantLoanGetEligibleProduct.getEligibleProduct(req);
  }
  
  
}