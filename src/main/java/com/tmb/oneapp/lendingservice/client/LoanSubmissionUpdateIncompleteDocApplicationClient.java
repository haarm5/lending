package com.tmb.oneapp.lendingservice.client;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.tmb.common.exception.model.TMBCommonException;
import com.tmb.common.logger.TMBLogger;
import com.tmb.common.model.legacy.rsl.common.ob.doc.application.DocApplication;
import com.tmb.common.model.legacy.rsl.ws.doc.application.request.RequestDocApplication;
import com.tmb.common.model.legacy.rsl.ws.doc.application.response.ResponseDocApplication;
import com.tmb.common.model.legacy.rsl.ws.loan.submission.LoanSubmissionUpdateIncompleteDocApplicationServiceLocator;
import com.tmb.common.model.legacy.rsl.ws.loan.submission.LoanSubmissionUpdateIncompleteDocApplicationSoapBindingStub;
import com.tmb.oneapp.lendingservice.constant.ResponseCode;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import javax.xml.rpc.ServiceException;
import java.rmi.RemoteException;
import java.util.UUID;

@Service
public class LoanSubmissionUpdateIncompleteDocApplicationClient {
    private static final TMBLogger<LoanSubmissionUpdateIncompleteDocApplicationClient> logger = new TMBLogger<>(LoanSubmissionUpdateIncompleteDocApplicationClient.class);
    private final ObjectMapper mapper;

    @Value("${rsl.loan-submission-update-incomplete-doc-application.url}")
    private String url;

    LoanSubmissionUpdateIncompleteDocApplicationServiceLocator locator = new LoanSubmissionUpdateIncompleteDocApplicationServiceLocator();

    private static final String CHANNEL = "MIB";
    private static final String MODULE = "3";

    public LoanSubmissionUpdateIncompleteDocApplicationClient(ObjectMapper mapper) {
        this.mapper = mapper;
        mapper.setVisibility(PropertyAccessor.FIELD, JsonAutoDetect.Visibility.ANY);
    }

    public void setLocator(LoanSubmissionUpdateIncompleteDocApplicationServiceLocator locator) {
        this.locator = locator;
    }


    public ResponseDocApplication updateIncompleteDocApplication(DocApplication docApplication) throws ServiceException, JsonProcessingException, TMBCommonException {
        locator.setLoanSubmissionUpdateIncompleteDocApplicationEndpointAddress(url);
        LoanSubmissionUpdateIncompleteDocApplicationSoapBindingStub stub = (LoanSubmissionUpdateIncompleteDocApplicationSoapBindingStub) locator.getLoanSubmissionUpdateIncompleteDocApplication();
        logger.info("LoanSubmissionUpdateIncompleteDocApplication Url: {}", url);

        RequestDocApplication request = new RequestDocApplication();
        request.setHeader(setHeader());
        request.setBody(setBody(docApplication));
        logger.info("LoanSubmissionUpdateIncompleteDocApplication Request: {}", mapper.writeValueAsString(request));

        try {
            ResponseDocApplication response = stub.updateIncompleteDocApplication(request);
            logger.info("LoanSubmissionUpdateIncompleteDocApplication Response: {}", mapper.writeValueAsString(response));
            return response;
        } catch (RemoteException e) {
            throw new TMBCommonException(ResponseCode.RSL_CONNECTION_ERROR.getCode(), ResponseCode.RSL_CONNECTION_ERROR.getMessage(), ResponseCode.RSL_CONNECTION_ERROR.getService(), HttpStatus.INTERNAL_SERVER_ERROR, e);
        }
    }

    private com.tmb.common.model.legacy.rsl.ws.doc.application.request.Header setHeader() {
        com.tmb.common.model.legacy.rsl.ws.doc.application.request.Header header = new com.tmb.common.model.legacy.rsl.ws.doc.application.request.Header();
        header.setChannel(CHANNEL);
        header.setModule(MODULE);
        header.setRequestID(UUID.randomUUID().toString());
        return header;
    }

    private com.tmb.common.model.legacy.rsl.ws.doc.application.request.Body setBody(DocApplication docApplication) {
        com.tmb.common.model.legacy.rsl.ws.doc.application.request.Body body = new com.tmb.common.model.legacy.rsl.ws.doc.application.request.Body();
        body.setDocApplication(docApplication);
        return body;
    }
}
