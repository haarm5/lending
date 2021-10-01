package com.tmb.oneapp.lendingservice.client;


import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.tmb.common.exception.model.TMBCommonException;
import com.tmb.common.logger.TMBLogger;
import com.tmb.common.model.legacy.rsl.common.ob.creditcard.CreditCard;
import com.tmb.common.model.legacy.rsl.ws.creditcard.update.request.Body;
import com.tmb.common.model.legacy.rsl.ws.creditcard.update.request.Header;
import com.tmb.common.model.legacy.rsl.ws.creditcard.update.request.RequestCreditcard;
import com.tmb.common.model.legacy.rsl.ws.creditcard.update.response.ResponseCreditcard;
import com.tmb.common.model.legacy.rsl.ws.loan.submission.LoanSubmissionUpdateCreditcardServiceLocator;
import com.tmb.common.model.legacy.rsl.ws.loan.submission.LoanSubmissionUpdateCreditcardSoapBindingStub;
import com.tmb.oneapp.lendingservice.constant.ResponseCode;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import javax.xml.rpc.ServiceException;
import java.rmi.RemoteException;
import java.util.UUID;

@Service
public class LoanSubmissionUpdateCreditCardClient {

    @Value("${rsl.loan-submission-update-credit-card-info.url}")
    private String updateCreditCard;
    private final ObjectMapper mapper;
    private static final TMBLogger<LoanSubmissionUpdateCreditCardClient> logger = new TMBLogger<>(LoanSubmissionUpdateCreditCardClient.class);


    LoanSubmissionUpdateCreditcardServiceLocator locator = new LoanSubmissionUpdateCreditcardServiceLocator();
    private static final String CHANNEL = "MIB";
    private static final String MODULE = "3";


    public LoanSubmissionUpdateCreditCardClient(ObjectMapper mapper) {
        this.mapper = mapper;
        mapper.setVisibility(PropertyAccessor.FIELD, JsonAutoDetect.Visibility.ANY);
    }

    public void setLocator(LoanSubmissionUpdateCreditcardServiceLocator locator) {
        this.locator = locator;
    }


    public ResponseCreditcard updateCreditCard(CreditCard creditCard) throws ServiceException, JsonProcessingException, TMBCommonException {
        locator.setLoanSubmissionUpdateCreditcardEndpointAddress(updateCreditCard);
        LoanSubmissionUpdateCreditcardSoapBindingStub stub = (LoanSubmissionUpdateCreditcardSoapBindingStub) locator.getLoanSubmissionUpdateCreditcard();
        logger.info("LoanSubmissionUpdateCreditCard Url: {}", updateCreditCard);

        Header header = new Header();
        header.setChannel(CHANNEL);
        header.setModule(MODULE);
        header.setRequestID(UUID.randomUUID().toString());

        Body body = new Body();
        body.setCreditCard(creditCard);

        RequestCreditcard req = new RequestCreditcard();
        req.setHeader(header);
        req.setBody(body);

        logger.info("LoanSubmissionUpdateCreditCard req {} : " + mapper.writeValueAsString(req));

        try {
            ResponseCreditcard responseCreditcard = stub.updateCreditcardInfo(req);
            logger.info("LoanSubmissionUpdateCreditCard Response: {}", mapper.writeValueAsString(responseCreditcard));
            return responseCreditcard;
        } catch (RemoteException e) {
            throw new TMBCommonException(ResponseCode.RSL_CONNECTION_ERROR.getCode(), ResponseCode.RSL_CONNECTION_ERROR.getMessage(), ResponseCode.RSL_CONNECTION_ERROR.getService(), HttpStatus.INTERNAL_SERVER_ERROR, e);
        }

    }
}
