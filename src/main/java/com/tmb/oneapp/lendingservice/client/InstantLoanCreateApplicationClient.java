package com.tmb.oneapp.lendingservice.client;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.tmb.common.logger.TMBLogger;
import com.tmb.common.model.legacy.rsl.ws.instant.application.create.request.RequestInstantLoanCreateApplication;
import com.tmb.common.model.legacy.rsl.ws.instant.application.create.response.ResponseInstantLoanCreateApplication;
import com.tmb.common.model.legacy.rsl.ws.loan.submission.LoanSubmissionInstantLoanCreateApplicationServiceLocator;
import com.tmb.common.model.legacy.rsl.ws.loan.submission.LoanSubmissionInstantLoanCreateApplicationSoapBindingStub;
import com.tmb.oneapp.lendingservice.service.InstantLoanCreateApplicationService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.xml.rpc.ServiceException;
import java.rmi.RemoteException;

@Service
public class InstantLoanCreateApplicationClient {
    private static final TMBLogger<InstantLoanCreateApplicationService> logger = new TMBLogger<>(InstantLoanCreateApplicationService.class);
    private  final ObjectMapper mapper;

    @Value("${loan.submission.instant.loan.create.application.url}")
    private String loanSubmissionInstantLoanCreateApplicationURL;
    public InstantLoanCreateApplicationClient(ObjectMapper mapper) {
        this.mapper = mapper;
    }

    public ResponseInstantLoanCreateApplication callLoanSubmissionInstantLoanCreateApplication(RequestInstantLoanCreateApplication request) throws JsonProcessingException, RemoteException, ServiceException {
        LoanSubmissionInstantLoanCreateApplicationServiceLocator locator = new LoanSubmissionInstantLoanCreateApplicationServiceLocator();
        locator.setLoanSubmissionInstantLoanCreateApplicationEndpointAddress(loanSubmissionInstantLoanCreateApplicationURL);
        LoanSubmissionInstantLoanCreateApplicationSoapBindingStub stub = (LoanSubmissionInstantLoanCreateApplicationSoapBindingStub) locator.getLoanSubmissionInstantLoanCreateApplication();
        logger.info("Soap request to  InstantLoanCreateApplication is  {} :" + mapper.writeValueAsString(request));
        ResponseInstantLoanCreateApplication response = stub.createInstantLoanApplication(request);
        logger.info("Soap Response from  InstantLoanCreateApplication is {} : " +mapper.writeValueAsString(response));
        return response;
    }

}
