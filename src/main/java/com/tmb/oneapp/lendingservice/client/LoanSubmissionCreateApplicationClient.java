package com.tmb.oneapp.lendingservice.client;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.tmb.common.logger.TMBLogger;
import com.fasterxml.jackson.core.JsonProcessingException;
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
    @Value("${rsl.loan-submission-create-application.url}")
    private String createAppUrlInfo;
    private final ObjectMapper mapper;
    private static final TMBLogger<LoanSubmissionCreateApplicationClient> logger = new TMBLogger<>(LoanSubmissionCreateApplicationClient.class);


    LoanSubmissionCreateApplicationServiceLocator locator = new LoanSubmissionCreateApplicationServiceLocator();
    private static final String CHANNEL = "MIB";
    private static final String MODULE = "3";

    public LoanSubmissionCreateApplicationClient(ObjectMapper mapper) {
        this.mapper = mapper;
        mapper.setVisibility(PropertyAccessor.FIELD, JsonAutoDetect.Visibility.ANY);
    }

    public void setLocator(LoanSubmissionCreateApplicationServiceLocator locator) {
        this.locator = locator;
    }

    public ResponseApplication createApplication(Application application) throws RemoteException, ServiceException, JsonProcessingException {
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
        logger.info(" Request from Client to createApplication is {} : " + mapper.writeValueAsString(req));
        ResponseApplication res = stub.createApplicationInfo(req);
        logger.info(" Response from Client to createApplication is {} : " + mapper.writeValueAsString(res));
        return res;
    }
}
