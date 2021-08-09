package com.tmb.oneapp.lendingservice.client;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.tmb.common.exception.model.TMBCommonException;
import com.tmb.common.logger.TMBLogger;
import com.tmb.common.model.legacy.rsl.ws.individual.request.RequestIndividual;
import com.tmb.common.model.legacy.rsl.ws.individual.response.ResponseIndividual;
import com.tmb.common.model.legacy.rsl.ws.loan.submission.LoanSubmissionGetCustomerInfoServiceLocator;
import com.tmb.common.model.legacy.rsl.ws.loan.submission.LoanSubmissionGetCustomerInfoSoapBindingStub;
import com.tmb.oneapp.lendingservice.constant.ResponseCode;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import javax.xml.rpc.ServiceException;
import java.rmi.RemoteException;
import java.util.UUID;

@Service
public class LoanSubmissionGetCustomerInfoClient {

    private static final TMBLogger<LoanSubmissionGetCustomerInfoClient> logger = new TMBLogger<>(LoanSubmissionGetCustomerInfoClient.class);
    private final ObjectMapper mapper;

    @Value("${rsl.loan-submission-get-customer-info.url}")
    private String url;

    LoanSubmissionGetCustomerInfoServiceLocator locator = new LoanSubmissionGetCustomerInfoServiceLocator();

    private static final String CHANNEL = "MIB";
    private static final String MODULE = "3";

    public LoanSubmissionGetCustomerInfoClient(ObjectMapper mapper) {
        this.mapper = mapper;
        mapper.setVisibility(PropertyAccessor.FIELD, JsonAutoDetect.Visibility.ANY);
    }

    public void setLocator(LoanSubmissionGetCustomerInfoServiceLocator locator) {
        this.locator = locator;
    }

    public ResponseIndividual searchCustomerInfoByCaID(long caId) throws RemoteException, ServiceException, JsonProcessingException, TMBCommonException {
        locator.setLoanSubmissionGetCustomerInfoEndpointAddress(url);
        LoanSubmissionGetCustomerInfoSoapBindingStub stub = (LoanSubmissionGetCustomerInfoSoapBindingStub) locator.
                getLoanSubmissionGetCustomerInfo();
        logger.info("LoanSubmissionGetCustomerInfo Url: {}", url);

        RequestIndividual request = new RequestIndividual();
        request.setHeader(setHeader());
        request.setBody(setBody(caId));
        logger.info("LoanSubmissionGetCustomerInfo Request: {}", mapper.writeValueAsString(request));

        try {
            ResponseIndividual response = stub.searchCustomerInfoByCaID(request);
            logger.info("LoanSubmissionGetCustomerInfo Response: {}", mapper.writeValueAsString(response));
            return response;
        } catch (RemoteException e) {
            throw new TMBCommonException(ResponseCode.RSL_CONNECTION_ERROR.getCode(), ResponseCode.RSL_CONNECTION_ERROR.getMessage(), ResponseCode.RSL_CONNECTION_ERROR.getService(), HttpStatus.INTERNAL_SERVER_ERROR, e);
        }
    }

    private com.tmb.common.model.legacy.rsl.ws.individual.request.Header setHeader() {
        com.tmb.common.model.legacy.rsl.ws.individual.request.Header header = new com.tmb.common.model.legacy.rsl.ws.individual.request.Header();
        header.setChannel(CHANNEL);
        header.setModule(MODULE);
        header.setRequestID(UUID.randomUUID().toString());
        return header;
    }

    private com.tmb.common.model.legacy.rsl.ws.individual.request.Body setBody(long caId) {
        com.tmb.common.model.legacy.rsl.ws.individual.request.Body body = new com.tmb.common.model.legacy.rsl.ws.individual.request.Body();
        body.setCaID(caId);
        return body;
    }

}
