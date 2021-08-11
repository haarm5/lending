package com.tmb.oneapp.lendingservice.client;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.tmb.common.exception.model.TMBCommonException;
import com.tmb.common.logger.TMBLogger;
import com.tmb.common.model.legacy.rsl.ws.checklist.request.RequestChecklist;
import com.tmb.common.model.legacy.rsl.ws.checklist.response.ResponseChecklist;
import com.tmb.common.model.legacy.rsl.ws.loan.submission.LoanSubmissionGetChecklistInfoServiceLocator;
import com.tmb.common.model.legacy.rsl.ws.loan.submission.LoanSubmissionGetChecklistInfoSoapBindingStub;
import com.tmb.oneapp.lendingservice.constant.ResponseCode;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import javax.xml.rpc.ServiceException;
import java.rmi.RemoteException;
import java.util.UUID;

@Service
public class LoanSubmissionGetChecklistInfoClient {
    private static final TMBLogger<LoanSubmissionGetChecklistInfoClient> logger = new TMBLogger<>(LoanSubmissionGetChecklistInfoClient.class);
    private final ObjectMapper mapper;

    @Value("${rsl.loan-submission-loan-get-checklist-info.url}")
    private String url;

    LoanSubmissionGetChecklistInfoServiceLocator locator = new LoanSubmissionGetChecklistInfoServiceLocator();
    private static final String CHANNEL = "MIB";
    private static final String MODULE = "3";
    private static final String CHECKLIST_TYPE = "CC";
    private static final String INCOMPLETE_DOC_FLAG = "N";

    public LoanSubmissionGetChecklistInfoClient(ObjectMapper mapper) {
        this.mapper = mapper;
        mapper.setVisibility(PropertyAccessor.FIELD, JsonAutoDetect.Visibility.ANY);
    }

    public void setLocator(LoanSubmissionGetChecklistInfoServiceLocator locator) {
        this.locator = locator;
    }

    public ResponseChecklist getChecklistInfo(Long caId) throws TMBCommonException, ServiceException, JsonProcessingException {

        locator.setLoanSubmissionGetChecklistInfoEndpointAddress(url);

        LoanSubmissionGetChecklistInfoSoapBindingStub stub = (LoanSubmissionGetChecklistInfoSoapBindingStub) locator.getLoanSubmissionGetChecklistInfo();

        logger.info("LoanSubmissionGetChecklistInfoSoapBindingStub Url: {}", url);


        RequestChecklist request = new RequestChecklist();
        com.tmb.common.model.legacy.rsl.ws.checklist.request.Body body = new com.tmb.common.model.legacy.rsl.ws.checklist.request.Body();
        body.setCaID(caId);
        body.setChecklistType(CHECKLIST_TYPE);
        body.setIncompleteDocFlag(INCOMPLETE_DOC_FLAG);
        request.setBody(body);

        com.tmb.common.model.legacy.rsl.ws.checklist.request.Header header = new com.tmb.common.model.legacy.rsl.ws.checklist.request.Header();
        header.setChannel(CHANNEL);
        header.setModule(MODULE);
        header.setRequestID(UUID.randomUUID().toString());
        request.setHeader(header);
        logger.info("LoanSubmissionGetChecklistInfoSoapBindingStub Request: {}", mapper.writeValueAsString(request));

        try {
            ResponseChecklist response = stub.searchChecklistInfoByCaID(request);
            logger.info("LoanSubmissionGetChecklistInfoSoapBindingStub Response: {}", mapper.writeValueAsString(response));
            return response;
        } catch (RemoteException e) {
            throw new TMBCommonException(ResponseCode.RSL_CONNECTION_ERROR.getCode(), ResponseCode.RSL_CONNECTION_ERROR.getMessage(), ResponseCode.RSL_CONNECTION_ERROR.getService(), HttpStatus.INTERNAL_SERVER_ERROR, e);
        }
    }

}
