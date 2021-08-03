package com.tmb.oneapp.lendingservice.client;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.tmb.common.exception.model.TMBCommonException;
import com.tmb.common.logger.TMBLogger;
import com.tmb.common.model.legacy.rsl.ws.instant.calculate.uw.request.RequestInstantLoanCalUW;
import com.tmb.common.model.legacy.rsl.ws.instant.calculate.uw.response.ResponseInstantLoanCalUW;
import com.tmb.common.model.legacy.rsl.ws.loan.submission.LoanSubmissionInstantLoanCalUWServiceLocator;
import com.tmb.common.model.legacy.rsl.ws.loan.submission.LoanSubmissionInstantLoanCalUWSoapBindingStub;
import com.tmb.oneapp.lendingservice.constant.ResponseCode;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import javax.xml.rpc.ServiceException;
import java.math.BigDecimal;
import java.rmi.RemoteException;
import java.util.UUID;

@Service
public class LoanSubmissionInstantLoanCalUWClient {

    private static final TMBLogger<LoanSubmissionInstantLoanCalUWClient> logger = new TMBLogger<>(LoanSubmissionInstantLoanCalUWClient.class);
    private final ObjectMapper mapper;

    @Value("${rsl.loan-submission-instant-loan-cal-uw.url}")
    private String url;

    LoanSubmissionInstantLoanCalUWServiceLocator locator = new LoanSubmissionInstantLoanCalUWServiceLocator();

    private static final String CHANNEL = "MIB";
    private static final String MODULE = "3";

    public LoanSubmissionInstantLoanCalUWClient(ObjectMapper mapper) {
        this.mapper = mapper;
    }

    public ResponseInstantLoanCalUW calculateUnderwriting(String triggerFlag, BigDecimal caId) throws ServiceException, JsonProcessingException, TMBCommonException {
        locator.setLoanSubmissionInstantLoanCalUWEndpointAddress(url);
        LoanSubmissionInstantLoanCalUWSoapBindingStub stub = (LoanSubmissionInstantLoanCalUWSoapBindingStub) locator.getLoanSubmissionInstantLoanCalUW();
        logger.info("LoanSubmissionInstantLoanCalUW Url: {}", url);

        RequestInstantLoanCalUW request = new RequestInstantLoanCalUW();
        request.setHeader(setHeader());
        request.setBody(setBody(triggerFlag, caId));
        logger.info("LoanSubmissionInstantLoanCalUW Request: {}", mapper.writeValueAsString(request));

        try {
            ResponseInstantLoanCalUW response = stub.calculateUnderwriting(request);
            logger.info("LoanSubmissionInstantLoanCalUW Response: {}", mapper.writeValueAsString(response));
            return response;
        } catch (RemoteException e) {
            throw new TMBCommonException(ResponseCode.RSL_CONNECTION_ERROR.getCode(), ResponseCode.RSL_CONNECTION_ERROR.getMessage(), ResponseCode.RSL_CONNECTION_ERROR.getService(), HttpStatus.INTERNAL_SERVER_ERROR, e);
        }
    }

    private com.tmb.common.model.legacy.rsl.ws.instant.calculate.uw.request.Header setHeader() {
        com.tmb.common.model.legacy.rsl.ws.instant.calculate.uw.request.Header header = new com.tmb.common.model.legacy.rsl.ws.instant.calculate.uw.request.Header();
        header.setChannel(CHANNEL);
        header.setModule(MODULE);
        header.setRequestID(UUID.randomUUID().toString());
        return header;
    }

    private com.tmb.common.model.legacy.rsl.ws.instant.calculate.uw.request.Body setBody(String triggerFlag, BigDecimal caId) {
        com.tmb.common.model.legacy.rsl.ws.instant.calculate.uw.request.Body body = new com.tmb.common.model.legacy.rsl.ws.instant.calculate.uw.request.Body();
        body.setTriggerFlag(triggerFlag);
        body.setCaId(caId);
        return body;
    }
}
