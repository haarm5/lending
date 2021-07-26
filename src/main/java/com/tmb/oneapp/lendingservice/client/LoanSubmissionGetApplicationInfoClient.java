package com.tmb.oneapp.lendingservice.client;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.tmb.common.exception.model.TMBCommonException;
import com.tmb.common.logger.TMBLogger;
import com.tmb.common.model.legacy.rsl.ws.application.request.Body;
import com.tmb.common.model.legacy.rsl.ws.application.request.Header;
import com.tmb.common.model.legacy.rsl.ws.application.request.RequestApplication;
import com.tmb.common.model.legacy.rsl.ws.application.response.ResponseApplication;
import com.tmb.common.model.legacy.rsl.ws.loan.submission.LoanSubmissionGetApplicationInfoServiceLocator;
import com.tmb.common.model.legacy.rsl.ws.loan.submission.LoanSubmissionGetApplicationInfoSoapBindingStub;
import com.tmb.oneapp.lendingservice.constant.ResponseCode;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import javax.xml.rpc.ServiceException;
import java.rmi.RemoteException;
import java.util.UUID;

@Service
public class LoanSubmissionGetApplicationInfoClient {

    private static final TMBLogger<LoanSubmissionGetApplicationInfoClient> logger = new TMBLogger<>(LoanSubmissionGetApplicationInfoClient.class);
    private final ObjectMapper mapper;

    @Value("${rsl.loan-submission-get-application-info.url}")
    private String url;

    LoanSubmissionGetApplicationInfoServiceLocator locator = new LoanSubmissionGetApplicationInfoServiceLocator();

    private static final String CHANNEL = "MIB";
    private static final String MODULE = "3";

    public LoanSubmissionGetApplicationInfoClient(ObjectMapper mapper) {
        this.mapper = mapper;
    }

    public void setLocator(LoanSubmissionGetApplicationInfoServiceLocator locator) {
        this.locator = locator;
    }

    public ResponseApplication searchApplicationInfoByCaID(long caId) throws ServiceException, JsonProcessingException, TMBCommonException {
        locator.setLoanSubmissionGetApplicationInfoEndpointAddress(url);
        LoanSubmissionGetApplicationInfoSoapBindingStub stub = (LoanSubmissionGetApplicationInfoSoapBindingStub) locator
                .getLoanSubmissionGetApplicationInfo();
        logger.info("LoanSubmissionGetApplicationInfo Url: {}", url);

        RequestApplication request = new RequestApplication();
        request.setHeader(setHeader());
        request.setBody(setBody(caId));
        logger.info("LoanSubmissionGetApplicationInfo Request: {}", mapper.writeValueAsString(request));

        try {
            ResponseApplication response = stub.searchApplicationInfoByCaID(request);
            logger.info("LoanSubmissionGetApplicationInfo Response: {}", mapper.writeValueAsString(response));
            return response;
        } catch (RemoteException e) {
            throw new TMBCommonException(ResponseCode.RSL_CONNECTION_ERROR.getCode(), ResponseCode.RSL_CONNECTION_ERROR.getMessage(), ResponseCode.RSL_CONNECTION_ERROR.getService(), HttpStatus.INTERNAL_SERVER_ERROR, e);
        }
    }

    private Header setHeader() {
        Header header = new Header();
        header.setChannel(CHANNEL);
        header.setModule(MODULE);
        header.setRequestID(UUID.randomUUID().toString());
        return header;
    }

    private Body setBody(long caId) {
        Body body = new Body();
        body.setCaID(caId);
        return body;
    }

}
