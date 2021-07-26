package com.tmb.oneapp.lendingservice.client;


import com.tmb.common.model.legacy.rsl.ws.individual.request.Body;
import com.tmb.common.model.legacy.rsl.ws.individual.request.Header;
import com.tmb.common.model.legacy.rsl.ws.individual.request.RequestIndividual;
import com.tmb.common.model.legacy.rsl.ws.individual.response.ResponseIndividual;
import com.tmb.common.model.legacy.rsl.ws.loan.submission.LoanSubmissionGetCustomerInfoServiceLocator;
import com.tmb.common.model.legacy.rsl.ws.loan.submission.LoanSubmissionGetCustomerInfoSoapBindingStub;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.xml.rpc.ServiceException;
import java.rmi.RemoteException;
import java.util.UUID;

@Service
public class LoanSubmissionGetCustomerInfoClient {

    @Value("${loan-submission-get-customer-info.url}")
    private String loanSubmissionGetCustomerInfoServiceLocatorUrl;

    public ResponseIndividual searchCustomerInfoByCaID(long caId) throws RemoteException, ServiceException {

        RequestIndividual request = new RequestIndividual();

        Header header = new Header();
        header.setChannel("MIB");
        header.setModule("3");
        header.setRequestID(UUID.randomUUID().toString());

        request.setHeader(header);
        Body body = new Body();
        body.setCaID(caId);
        request.setBody(body);

        LoanSubmissionGetCustomerInfoServiceLocator locator = new LoanSubmissionGetCustomerInfoServiceLocator();
        locator.setLoanSubmissionGetCustomerInfoEndpointAddress(loanSubmissionGetCustomerInfoServiceLocatorUrl);
        LoanSubmissionGetCustomerInfoSoapBindingStub stub = (LoanSubmissionGetCustomerInfoSoapBindingStub) locator.getLoanSubmissionGetCustomerInfo();

        return stub.searchCustomerInfoByCaID(request);
    }

}
