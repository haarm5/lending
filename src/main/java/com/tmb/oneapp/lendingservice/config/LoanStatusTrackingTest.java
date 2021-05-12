package com.tmb.oneapp.lendingservice.config;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.tmb.common.model.legacy.rsl.ws.tracking.LoanStatusTrackingServiceLocator;
import com.tmb.common.model.legacy.rsl.ws.tracking.LoanStatusTrackingSoapBindingStub;
import com.tmb.common.model.legacy.rsl.ws.tracking.request.Body;
import com.tmb.common.model.legacy.rsl.ws.tracking.request.Header;
import com.tmb.common.model.legacy.rsl.ws.tracking.request.RequestTracking;
import com.tmb.common.model.legacy.rsl.ws.tracking.response.Application;
import com.tmb.common.model.legacy.rsl.ws.tracking.response.ResponseTracking;


import javax.annotation.PostConstruct;
import javax.xml.rpc.ServiceException;
import java.rmi.RemoteException;


//@Component
public class LoanStatusTrackingTest {


    @PostConstruct
    public void exe() {
        LoanStatusTrackingServiceLocator locator = new LoanStatusTrackingServiceLocator();
        locator.setLoanStatusTrackingEndpointAddress("http://10.209.27.99:9081/RSLWS/services/LoanStatusTracking");
        try {
            LoanStatusTrackingSoapBindingStub stub = (LoanStatusTrackingSoapBindingStub) locator.getLoanStatusTracking();

            RequestTracking req = new RequestTracking();

            Header header = new Header();
            req.setHeader(header);

            Body body = new Body();
            body.setCitizenID("1846622310794");
            body.setMobileNo("0000000000");
            req.setBody(body);

            header.setChannel("MIB");
            header.setModule("3");
            header.setRequestID("725a9ec5-5de0-4b95-a51f-9774b559b459");
            req.setHeader(header);
            ResponseTracking response = stub.searchAppStatusByID(req);


            Application[] apps = response.getBody().getApplication();
            ObjectMapper objectMapper = new ObjectMapper();
            try {
                String responseStr = objectMapper.writeValueAsString(response.getBody());
                System.out.println(responseStr);
            } catch (JsonProcessingException e) {
                e.printStackTrace();
            }

//            for (Application a : apps) {
//
//                System.out.println("getAppRefNo:"+a.getAppRefNo());
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
