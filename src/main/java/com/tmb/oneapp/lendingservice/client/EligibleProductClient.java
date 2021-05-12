package com.tmb.oneapp.lendingservice.client;

import com.tmb.common.model.legacy.rsl.ws.instant.eligible.product.request.Body;
import com.tmb.common.model.legacy.rsl.ws.instant.eligible.product.request.Header;
import com.tmb.common.model.legacy.rsl.ws.instant.eligible.product.request.RequestInstantLoanGetEligibleProduct;
import com.tmb.common.model.legacy.rsl.ws.instant.eligible.product.response.ResponseInstantLoanGetEligibleProduct;
import com.tmb.common.model.legacy.rsl.ws.loan.submission.LoanSubmissionInstantLoanGetEligibleProductServiceLocator;
import com.tmb.common.model.legacy.rsl.ws.loan.submission.LoanSubmissionInstantLoanGetEligibleProductSoapBindingStub;
import org.springframework.stereotype.Service;

import javax.xml.rpc.ServiceException;
import java.rmi.RemoteException;

@Service
public class EligibleProductClient {

    public ResponseInstantLoanGetEligibleProduct getEligibleProduct() throws RemoteException, ServiceException {
        LoanSubmissionInstantLoanGetEligibleProductServiceLocator locator = new LoanSubmissionInstantLoanGetEligibleProductServiceLocator();
        locator.setLoanSubmissionInstantLoanGetEligibleProductEndpointAddress("http://10.209.27.99:9081/LoanSubmissionWS/services/LoanSubmissionInstantLoanGetEligibleProduct");
        LoanSubmissionInstantLoanGetEligibleProductSoapBindingStub stub = (LoanSubmissionInstantLoanGetEligibleProductSoapBindingStub) locator.getLoanSubmissionInstantLoanGetEligibleProduct();
        RequestInstantLoanGetEligibleProduct req = new RequestInstantLoanGetEligibleProduct();


        Header header = new Header();
        header.setChannel("MIB");
        header.setModule("");
        header.setRequestID("725a9ec5-5de0-4b95-a51f-9774b559b459");

        req.setHeader(header);
        Body body = new Body();
        body.setRmNo("00000018593707");
        req.setBody(body);
        return stub.getEligibleProduct(req);
    }

}
