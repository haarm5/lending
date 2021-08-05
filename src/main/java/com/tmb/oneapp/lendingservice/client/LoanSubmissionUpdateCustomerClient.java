package com.tmb.oneapp.lendingservice.client;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.tmb.common.exception.model.TMBCommonException;
import com.tmb.common.logger.TMBLogger;
import com.tmb.common.model.legacy.rsl.common.ob.individual.Individual;
import com.tmb.common.model.legacy.rsl.ws.individual.update.request.RequestIndividual;
import com.tmb.common.model.legacy.rsl.ws.individual.update.response.ResponseIndividual;
import com.tmb.common.model.legacy.rsl.ws.loan.submission.LoanSubmissionUpdateCustomerServiceLocator;
import com.tmb.common.model.legacy.rsl.ws.loan.submission.LoanSubmissionUpdateCustomerSoapBindingStub;
import com.tmb.oneapp.lendingservice.constant.ResponseCode;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import javax.xml.rpc.ServiceException;
import java.rmi.RemoteException;
import java.util.UUID;

@Service
public class LoanSubmissionUpdateCustomerClient {
    @Value("${loan-submission-update-customer-info.url}")
    private String updateCustomerInfo;
    private final ObjectMapper mapper;
    private static final TMBLogger<LoanSubmissionUpdateCustomerClient> logger = new TMBLogger<>(LoanSubmissionUpdateCustomerClient.class);

    LoanSubmissionUpdateCustomerServiceLocator locator = new LoanSubmissionUpdateCustomerServiceLocator();
    private static final String CHANNEL = "MIB";
    private static final String MODULE = "3";

    public LoanSubmissionUpdateCustomerClient(ObjectMapper mapper) {
        this.mapper = mapper;
    }

    public void setLocator(LoanSubmissionUpdateCustomerServiceLocator locator) {
        this.locator = locator;
    }

    public ResponseIndividual updateCustomerInfo(Individual individual) throws ServiceException, JsonProcessingException, TMBCommonException {
        locator.setLoanSubmissionUpdateCustomerEndpointAddress(updateCustomerInfo);
        LoanSubmissionUpdateCustomerSoapBindingStub stub = (LoanSubmissionUpdateCustomerSoapBindingStub) locator.getLoanSubmissionUpdateCustomer();
        logger.info("LoanSubmissionUpdateCustomer Url: {}", updateCustomerInfo);

        RequestIndividual req = new RequestIndividual();
        com.tmb.common.model.legacy.rsl.ws.individual.update.request.Header header = new com.tmb.common.model.legacy.rsl.ws.individual.update.request.Header();
        header.setChannel(CHANNEL);
        header.setModule(MODULE);
        header.setRequestID(UUID.randomUUID().toString());
        req.setHeader(header);

        com.tmb.common.model.legacy.rsl.ws.individual.update.request.Body body = new com.tmb.common.model.legacy.rsl.ws.individual.update.request.Body();
        body.setIndividual(individual);
        req.setBody(body);
        logger.info("Request from Client to updateCustomer is {} : " + mapper.writeValueAsString(req));

        try {
            ResponseIndividual responseIndividual = stub.updateCustomerInfo(req);
            logger.info("LoanSubmissionUpdateCustomer Response: {}", mapper.writeValueAsString(responseIndividual));
            return responseIndividual;
        }catch (RemoteException e) {
            throw new TMBCommonException(ResponseCode.RSL_CONNECTION_ERROR.getCode(), ResponseCode.RSL_CONNECTION_ERROR.getMessage(), ResponseCode.RSL_CONNECTION_ERROR.getService(), HttpStatus.INTERNAL_SERVER_ERROR, e);
        }
    }
}
