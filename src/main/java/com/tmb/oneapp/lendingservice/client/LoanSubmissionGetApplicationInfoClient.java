package com.tmb.oneapp.lendingservice.client;

import com.tmb.common.model.legacy.rsl.ws.application.request.Body;
import com.tmb.common.model.legacy.rsl.ws.application.request.Header;
import com.tmb.common.model.legacy.rsl.ws.application.request.RequestApplication;
import com.tmb.common.model.legacy.rsl.ws.application.response.ResponseApplication;
import com.tmb.common.model.legacy.rsl.ws.loan.submission.LoanSubmissionGetApplicationInfoServiceLocator;
import com.tmb.common.model.legacy.rsl.ws.loan.submission.LoanSubmissionGetApplicationInfoSoapBindingStub;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.xml.rpc.ServiceException;
import java.rmi.RemoteException;
import java.util.UUID;

@Service
public class LoanSubmissionGetApplicationInfoClient {

    @Value("${loan-submission-get-application-info.url}")
    private String loanSubmissionGetApplicationInfoUrl;

    public ResponseApplication searchApplicationInfoByCaID(long caId) throws RemoteException, ServiceException {

        RequestApplication request = new RequestApplication();
        Header header = new Header();
        header.setChannel("MIB");
        header.setModule("3");
        header.setRequestID(UUID.randomUUID().toString());
        request.setHeader(header);
        Body body = new Body();
        body.setCaID(caId);
        request.setBody(body);

        LoanSubmissionGetApplicationInfoServiceLocator locator = new LoanSubmissionGetApplicationInfoServiceLocator();
        locator.setLoanSubmissionGetApplicationInfoEndpointAddress(loanSubmissionGetApplicationInfoUrl);
        LoanSubmissionGetApplicationInfoSoapBindingStub stub = (LoanSubmissionGetApplicationInfoSoapBindingStub) locator.getLoanSubmissionGetApplicationInfo();
        return stub.searchApplicationInfoByCaID(request);
    }

}