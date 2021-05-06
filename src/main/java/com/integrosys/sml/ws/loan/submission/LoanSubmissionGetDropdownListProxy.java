package com.integrosys.sml.ws.loan.submission;

public class LoanSubmissionGetDropdownListProxy implements com.integrosys.sml.ws.loan.submission.LoanSubmissionGetDropdownList {
  private String _endpoint = null;
  private com.integrosys.sml.ws.loan.submission.LoanSubmissionGetDropdownList loanSubmissionGetDropdownList = null;
  
  public LoanSubmissionGetDropdownListProxy() {
    _initLoanSubmissionGetDropdownListProxy();
  }
  
  public LoanSubmissionGetDropdownListProxy(String endpoint) {
    _endpoint = endpoint;
    _initLoanSubmissionGetDropdownListProxy();
  }
  
  private void _initLoanSubmissionGetDropdownListProxy() {
    try {
      loanSubmissionGetDropdownList = (new com.integrosys.sml.ws.loan.submission.LoanSubmissionGetDropdownListServiceLocator()).getLoanSubmissionGetDropdownList();
      if (loanSubmissionGetDropdownList != null) {
        if (_endpoint != null)
          ((javax.xml.rpc.Stub)loanSubmissionGetDropdownList)._setProperty("javax.xml.rpc.service.endpoint.address", _endpoint);
        else
          _endpoint = (String)((javax.xml.rpc.Stub)loanSubmissionGetDropdownList)._getProperty("javax.xml.rpc.service.endpoint.address");
      }
      
    }
    catch (javax.xml.rpc.ServiceException serviceException) {}
  }
  
  public String getEndpoint() {
    return _endpoint;
  }
  
  public void setEndpoint(String endpoint) {
    _endpoint = endpoint;
    if (loanSubmissionGetDropdownList != null)
      ((javax.xml.rpc.Stub)loanSubmissionGetDropdownList)._setProperty("javax.xml.rpc.service.endpoint.address", _endpoint);
    
  }
  
  public com.integrosys.sml.ws.loan.submission.LoanSubmissionGetDropdownList getLoanSubmissionGetDropdownList() {
    if (loanSubmissionGetDropdownList == null)
      _initLoanSubmissionGetDropdownListProxy();
    return loanSubmissionGetDropdownList;
  }
  
  public com.integrosys.sml.ws.dropdown.response.ResponseDropdown getDropDownListByCode(com.integrosys.sml.ws.dropdown.request.RequestDropdown req) throws java.rmi.RemoteException{
    if (loanSubmissionGetDropdownList == null)
      _initLoanSubmissionGetDropdownListProxy();
    return loanSubmissionGetDropdownList.getDropDownListByCode(req);
  }
  
  
}