package com.tmb.oneapp.lendingservice.client;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.tmb.common.logger.TMBLogger;
import com.tmb.common.model.legacy.rsl.ws.incomemodel.request.RequestIncomeModel;
import com.tmb.common.model.legacy.rsl.ws.incomemodel.response.ResponseIncomeModel;
import com.tmb.common.model.legacy.rsl.ws.loan.submission.LoanSubmissionGetIncomeModelInfoServiceLocator;
import com.tmb.common.model.legacy.rsl.ws.loan.submission.LoanSubmissionGetIncomeModelInfoSoapBindingStub;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.xml.rpc.ServiceException;
import java.rmi.RemoteException;
import java.util.UUID;

@Service
public class LoanSubmissionGetIncomeModelInfoClient {
    @Value("${loan-submission-get-income-model-info.url}")
    private String incomeInfoUrl;

    private final ObjectMapper mapper;
    private static final TMBLogger<LoanSubmissionGetIncomeModelInfoClient> logger = new TMBLogger<>(LoanSubmissionGetIncomeModelInfoClient.class);

    private static final String CHANNEL = "MIB";
    private static final String MODULE = "3";
    LoanSubmissionGetIncomeModelInfoServiceLocator locator = new LoanSubmissionGetIncomeModelInfoServiceLocator();

    public LoanSubmissionGetIncomeModelInfoClient(ObjectMapper mapper) {
        this.mapper = mapper;
        mapper.setVisibility(PropertyAccessor.FIELD, JsonAutoDetect.Visibility.ANY);
    }

    public void setLocator(LoanSubmissionGetIncomeModelInfoServiceLocator locator) {
        this.locator = locator;
    }

    public ResponseIncomeModel getIncomeInfo(String rmNo) throws RemoteException, ServiceException, JsonProcessingException {
        locator.setLoanSubmissionGetIncomeModelInfoEndpointAddress(incomeInfoUrl);

        LoanSubmissionGetIncomeModelInfoSoapBindingStub stub = (LoanSubmissionGetIncomeModelInfoSoapBindingStub) locator
                .getLoanSubmissionGetIncomeModelInfo();

        RequestIncomeModel req = new RequestIncomeModel();


        com.tmb.common.model.legacy.rsl.ws.incomemodel.request.Header header = new com.tmb.common.model.legacy.rsl.ws.incomemodel.request.Header();
        header.setChannel(CHANNEL);
        header.setModule(MODULE);
        header.setRequestID(UUID.randomUUID().toString());
        req.setHeader(header);

        com.tmb.common.model.legacy.rsl.ws.incomemodel.request.Body body = new com.tmb.common.model.legacy.rsl.ws.incomemodel.request.Body();
        body.setRmNo(rmNo);
        req.setBody(body);
        logger.info(" Request from Client to get income info is {} : " + mapper.writeValueAsString(req));
        ResponseIncomeModel res = stub.getIncomeModelInfo(req);
        logger.info(" Response from Client to get income info is {} : " + mapper.writeValueAsString(res));
        return res;
    }

}

