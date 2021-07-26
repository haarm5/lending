package com.tmb.oneapp.lendingservice.client;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.tmb.common.exception.model.TMBCommonException;
import com.tmb.common.logger.TMBLogger;
import com.tmb.common.model.legacy.rsl.ws.instant.eligible.customer.request.Body;
import com.tmb.common.model.legacy.rsl.ws.instant.eligible.customer.request.Header;
import com.tmb.common.model.legacy.rsl.ws.instant.eligible.customer.request.RequestInstantLoanGetCustInfo;
import com.tmb.common.model.legacy.rsl.ws.instant.eligible.customer.response.ResponseInstantLoanGetCustInfo;
import com.tmb.common.model.legacy.rsl.ws.loan.submission.LoanSubmissionInstantLoanGetCustomerInfoServiceLocator;
import com.tmb.common.model.legacy.rsl.ws.loan.submission.LoanSubmissionInstantLoanGetCustomerInfoSoapBindingStub;
import com.tmb.oneapp.lendingservice.constant.ResponseCode;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import javax.xml.rpc.ServiceException;
import java.rmi.RemoteException;
import java.util.UUID;

@Service
public class LoanSubmissionInstantLoanGetCustomerInfoClient {

    private static final TMBLogger<LoanSubmissionInstantLoanGetCustomerInfoClient> logger = new TMBLogger<>(LoanSubmissionInstantLoanGetCustomerInfoClient.class);
    private final ObjectMapper mapper;

    @Value("${rsl.loan-submission-instance-loan-get-customer-info.url}")
    private String url;

    LoanSubmissionInstantLoanGetCustomerInfoServiceLocator locator = new LoanSubmissionInstantLoanGetCustomerInfoServiceLocator();

    private static final String CHANNEL = "MIB";
    private static final String MODULE = "3";

    public LoanSubmissionInstantLoanGetCustomerInfoClient(ObjectMapper mapper) {
        this.mapper = mapper;
    }

    public void setLocator(LoanSubmissionInstantLoanGetCustomerInfoServiceLocator locator) {
        this.locator = locator;
    }

    public ResponseInstantLoanGetCustInfo getInstantCustomerInfo(String rmNo)
            throws TMBCommonException, ServiceException, JsonProcessingException {

        locator.setLoanSubmissionInstantLoanGetCustomerInfoEndpointAddress(url);
        LoanSubmissionInstantLoanGetCustomerInfoSoapBindingStub stub = (LoanSubmissionInstantLoanGetCustomerInfoSoapBindingStub) locator
                .getLoanSubmissionInstantLoanGetCustomerInfo();
        logger.info("LoanSubmissionGetCustomerInfo Url: {}", url);

        RequestInstantLoanGetCustInfo request = new RequestInstantLoanGetCustInfo();
        request.setBody(setBody(rmNo));
        request.setHeader(setHeader());
        logger.info("LoanSubmissionGetCustomerInfo Request: {}", mapper.writeValueAsString(request));

        try {
            ResponseInstantLoanGetCustInfo response = stub.getInstantCustomerInfo(request);
            logger.info("LoanSubmissionGetCustomerInfo Response: {}", mapper.writeValueAsString(response));
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

    private Body setBody(String rmNo) {
        Body body = new Body();
        body.setRmNo(rmNo);
        return body;
    }

}
