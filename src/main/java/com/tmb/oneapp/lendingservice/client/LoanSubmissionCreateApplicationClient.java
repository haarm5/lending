package com.tmb.oneapp.lendingservice.client;

import com.tmb.common.model.legacy.rsl.common.ob.application.Application;
import com.tmb.common.model.legacy.rsl.ws.application.save.request.Body;
import com.tmb.common.model.legacy.rsl.ws.application.save.response.ResponseApplication;
import com.tmb.common.model.legacy.rsl.ws.application.save.request.Header;
import com.tmb.common.model.legacy.rsl.ws.loan.submission.LoanSubmissionCreateApplicationServiceLocator;
import com.tmb.common.model.legacy.rsl.ws.loan.submission.LoanSubmissionCreateApplicationSoapBindingStub;
import com.tmb.common.model.legacy.rsl.ws.application.save.request.RequestApplication;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.xml.rpc.ServiceException;
import java.rmi.RemoteException;
import java.util.UUID;

@Service
public class LoanSubmissionCreateApplicationClient {
    @Value("${loam-submission-create-application.url}")
    private String createAppUrlInfo;

    LoanSubmissionCreateApplicationServiceLocator locator = new LoanSubmissionCreateApplicationServiceLocator();
    private static final String CHANNEL = "MIB";
    private static final String MODULE = "3";

    public void setLocator(LoanSubmissionCreateApplicationServiceLocator locator) {
        this.locator = locator;
    }

    public ResponseApplication createApplication(Application application) throws RemoteException, ServiceException {
        locator.setLoanSubmissionCreateApplicationEndpointAddress(createAppUrlInfo);
        LoanSubmissionCreateApplicationSoapBindingStub stub =
                (LoanSubmissionCreateApplicationSoapBindingStub) locator.getLoanSubmissionCreateApplication();

        Header header = new Header();
        header.setChannel(CHANNEL);
        header.setModule(MODULE);
        header.setRequestID(UUID.randomUUID().toString());

        Body body = new Body();
        body.setApplication(application);

        var req = new RequestApplication();
        req.setHeader(header);
        req.setBody(body);
        return stub.createApplicationInfo(req);
    }
}
