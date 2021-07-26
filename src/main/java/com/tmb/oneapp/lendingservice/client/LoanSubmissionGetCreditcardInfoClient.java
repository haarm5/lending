package com.tmb.oneapp.lendingservice.client;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.tmb.common.exception.model.TMBCommonException;
import com.tmb.common.logger.TMBLogger;
import com.tmb.common.model.legacy.rsl.ws.creditcard.request.Body;
import com.tmb.common.model.legacy.rsl.ws.creditcard.request.Header;
import com.tmb.common.model.legacy.rsl.ws.creditcard.request.RequestCreditcard;
import com.tmb.common.model.legacy.rsl.ws.creditcard.response.ResponseCreditcard;
import com.tmb.common.model.legacy.rsl.ws.loan.submission.LoanSubmissionGetCreditcardInfoServiceLocator;
import com.tmb.common.model.legacy.rsl.ws.loan.submission.LoanSubmissionGetCreditcardInfoSoapBindingStub;
import com.tmb.oneapp.lendingservice.constant.ResponseCode;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import javax.xml.rpc.ServiceException;
import java.rmi.RemoteException;
import java.util.UUID;

@Service
public class LoanSubmissionGetCreditcardInfoClient {

    private static final TMBLogger<LoanSubmissionGetCreditcardInfoClient> logger = new TMBLogger<>(LoanSubmissionGetCreditcardInfoClient.class);
    private final ObjectMapper mapper;

    @Value("${rsl.loan-submission-get-creditcard-info.url}")
    private String url;

    LoanSubmissionGetCreditcardInfoServiceLocator locator = new LoanSubmissionGetCreditcardInfoServiceLocator();

    private static final String CHANNEL = "MIB";
    private static final String MODULE = "3";

    public LoanSubmissionGetCreditcardInfoClient(ObjectMapper mapper) {
        this.mapper = mapper;
    }

    public void setLocator(LoanSubmissionGetCreditcardInfoServiceLocator locator) {
        this.locator = locator;
    }

    public ResponseCreditcard searchCreditcardInfoByCaID(long caId) throws ServiceException, TMBCommonException, JsonProcessingException {
        locator.setLoanSubmissionGetCreditcardInfoEndpointAddress(url);
        LoanSubmissionGetCreditcardInfoSoapBindingStub stub = (LoanSubmissionGetCreditcardInfoSoapBindingStub) locator
                .getLoanSubmissionGetCreditcardInfo();
        logger.info("LoanSubmissionGetCreditcardInfo Url: {}", url);

        RequestCreditcard request = new RequestCreditcard();
        request.setHeader(setHeader());
        request.setBody(setBody(caId));
        logger.info("LoanSubmissionGetCreditcardInfo Request: {}", mapper.writeValueAsString(request));

        try {
            ResponseCreditcard response = stub.searchCreditcardInfoByCaID(request);
            logger.info("LoanSubmissionGetCreditcardInfo Response: {}", mapper.writeValueAsString(response));
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
