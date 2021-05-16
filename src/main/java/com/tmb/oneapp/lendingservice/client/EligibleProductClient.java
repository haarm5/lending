package com.tmb.oneapp.lendingservice.client;

import com.tmb.common.logger.TMBLogger;
import com.tmb.common.model.legacy.rsl.ws.instant.eligible.product.request.Body;
import com.tmb.common.model.legacy.rsl.ws.instant.eligible.product.request.Header;
import com.tmb.common.model.legacy.rsl.ws.instant.eligible.product.request.RequestInstantLoanGetEligibleProduct;
import com.tmb.common.model.legacy.rsl.ws.instant.eligible.product.response.ResponseInstantLoanGetEligibleProduct;
import com.tmb.common.model.legacy.rsl.ws.loan.submission.LoanSubmissionInstantLoanGetEligibleProductServiceLocator;
import com.tmb.common.model.legacy.rsl.ws.loan.submission.LoanSubmissionInstantLoanGetEligibleProductSoapBindingStub;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.xml.rpc.ServiceException;
import java.rmi.RemoteException;
import java.util.UUID;

@Service
public class EligibleProductClient {

    private static final TMBLogger<EligibleProductClient> logger = new TMBLogger<>(EligibleProductClient.class);


    @Value("${loan-submission-instant-loan-get-eligible-product.url}")
    private String loanSubmissionInstantLoanGetEligibleProductUrl;

    private LoanSubmissionInstantLoanGetEligibleProductServiceLocator locator = new LoanSubmissionInstantLoanGetEligibleProductServiceLocator();

    public void setLocator(LoanSubmissionInstantLoanGetEligibleProductServiceLocator locator) {
        this.locator = locator;
    }

    public ResponseInstantLoanGetEligibleProduct getEligibleProduct(String crmId) throws RemoteException, ServiceException {

        locator.setLoanSubmissionInstantLoanGetEligibleProductEndpointAddress(loanSubmissionInstantLoanGetEligibleProductUrl);
        LoanSubmissionInstantLoanGetEligibleProductSoapBindingStub stub = (LoanSubmissionInstantLoanGetEligibleProductSoapBindingStub) locator.getLoanSubmissionInstantLoanGetEligibleProduct();
        RequestInstantLoanGetEligibleProduct req = new RequestInstantLoanGetEligibleProduct();

        Header header = new Header();
        header.setChannel("MIB");
        header.setModule("");
        header.setRequestID(UUID.randomUUID().toString());

        req.setHeader(header);
        Body body = new Body();
        try {
            String crmId14Digit = new StringBuilder(new StringBuilder(crmId).reverse().substring(0, 14)).reverse().toString();
            body.setRmNo(crmId14Digit);
            req.setBody(body);
            return stub.getEligibleProduct(req);
        } catch (StringIndexOutOfBoundsException e) {
            logger.error("getEligibleProduct got wrong format crmId:{}", crmId);
            throw e;
        }
    }

}
