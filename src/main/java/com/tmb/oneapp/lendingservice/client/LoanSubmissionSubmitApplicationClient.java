package com.tmb.oneapp.lendingservice.client;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.tmb.common.exception.model.TMBCommonException;
import com.tmb.common.logger.TMBLogger;
import com.tmb.common.model.legacy.rsl.ws.loan.submission.LoanSubmissionSubmitApplicationServiceLocator;
import com.tmb.common.model.legacy.rsl.ws.loan.submission.LoanSubmissionSubmitApplicationSoapBindingStub;
import com.tmb.common.model.legacy.rsl.ws.submit.request.RequestSubmit;
import com.tmb.common.model.legacy.rsl.ws.submit.response.ResponseSubmit;
import com.tmb.oneapp.lendingservice.constant.ResponseCode;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import javax.xml.rpc.ServiceException;
import java.rmi.RemoteException;
import java.util.UUID;

@Service
public class LoanSubmissionSubmitApplicationClient {

    @Value("${rsl.loan-submission-submit-app.url}")
    private String url;

    private static final TMBLogger<LoanSubmissionSubmitApplicationClient> logger = new TMBLogger<>(LoanSubmissionSubmitApplicationClient.class);
    private final ObjectMapper mapper;

    LoanSubmissionSubmitApplicationServiceLocator locator = new LoanSubmissionSubmitApplicationServiceLocator();

    private static final String CHANNEL = "MIB";
    private static final String MODULE = "3";

    public LoanSubmissionSubmitApplicationClient(ObjectMapper mapper) {
        this.mapper = mapper;
        mapper.setVisibility(PropertyAccessor.FIELD, JsonAutoDetect.Visibility.ANY);
    }

    public void setLocator(LoanSubmissionSubmitApplicationServiceLocator locator) {
        this.locator = locator;
    }


    public ResponseSubmit submitApplication(Long caId, String memberref)
            throws ServiceException, TMBCommonException, JsonProcessingException {
        locator.setLoanSubmissionSubmitApplicationEndpointAddress(url);
        LoanSubmissionSubmitApplicationSoapBindingStub stub = (LoanSubmissionSubmitApplicationSoapBindingStub) locator
                .getLoanSubmissionSubmitApplication();
        logger.info("LoanSubmissionSubmitApplication Url: {}", url);

        RequestSubmit request = new RequestSubmit();
        request.setHeader(setHeader());
        request.setBody(setBody(caId, memberref));
        logger.info("LoanSubmissionSubmitApplication Request: {}", mapper.writeValueAsString(request));

        try {
            ResponseSubmit response = stub.submitApplication(request);
            logger.info("LoanSubmissionInstantLoanSubmitApplication Response: {}", mapper.writeValueAsString(response));
            return response;
        } catch (RemoteException e) {
            throw new TMBCommonException(ResponseCode.RSL_CONNECTION_ERROR.getCode(), ResponseCode.RSL_CONNECTION_ERROR.getMessage(), ResponseCode.RSL_CONNECTION_ERROR.getService(), HttpStatus.INTERNAL_SERVER_ERROR, e);
        }
    }

    private com.tmb.common.model.legacy.rsl.ws.submit.request.Header setHeader() {
        com.tmb.common.model.legacy.rsl.ws.submit.request.Header header = new com.tmb.common.model.legacy.rsl.ws.submit.request.Header();
        header.setChannel(CHANNEL);
        header.setModule(MODULE);
        header.setRequestID(UUID.randomUUID().toString());
        return header;
    }

    private com.tmb.common.model.legacy.rsl.ws.submit.request.Body setBody(Long caId, String memberref) {
        com.tmb.common.model.legacy.rsl.ws.submit.request.Body body = new com.tmb.common.model.legacy.rsl.ws.submit.request.Body();
        body.setCaID(caId);
        body.setMemberref(memberref);
        return body;
    }

}
