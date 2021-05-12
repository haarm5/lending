package com.tmb.oneapp.lendingservice.config;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.tmb.common.model.legacy.rsl.ws.instant.eligible.product.request.Body;
import com.tmb.common.model.legacy.rsl.ws.instant.eligible.product.request.Header;
import com.tmb.common.model.legacy.rsl.ws.instant.eligible.product.request.RequestInstantLoanGetEligibleProduct;
import com.tmb.common.model.legacy.rsl.ws.instant.eligible.product.response.ResponseInstantLoanGetEligibleProduct;
import com.tmb.common.model.legacy.rsl.ws.loan.submission.LoanSubmissionInstantLoanGetEligibleProductServiceLocator;
import com.tmb.common.model.legacy.rsl.ws.loan.submission.LoanSubmissionInstantLoanGetEligibleProductSoapBindingStub;



import javax.annotation.PostConstruct;
import javax.xml.rpc.ServiceException;
import java.rmi.RemoteException;

//@Component
public class LoanSubmissionInstantLoanGetEligibleProductTest {


    @PostConstruct
    public void exe() {
        LoanSubmissionInstantLoanGetEligibleProductServiceLocator locator = new LoanSubmissionInstantLoanGetEligibleProductServiceLocator();
        locator.setLoanSubmissionInstantLoanGetEligibleProductEndpointAddress("http://10.209.27.99:9081/LoanSubmissionWS/services/LoanSubmissionInstantLoanGetEligibleProduct");
        try {
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


            ResponseInstantLoanGetEligibleProduct response = stub.getEligibleProduct(req);
            ObjectMapper objectMapper = new ObjectMapper();
            try {
                String responseStr = objectMapper.writeValueAsString(response.getBody());
                System.out.println(responseStr);
            } catch (JsonProcessingException e) {
                e.printStackTrace();
            }

//            InstantCreditCard[] cards = response.getBody().getInstantCreditCard();
//            for(InstantCreditCard c : cards){
//                System.out.println("Card:"+c.getCampaignCode());
//            }
        } catch (ServiceException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (RemoteException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

}
