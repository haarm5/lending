package com.tmb.oneapp.lendingservice.client;


import com.tmb.common.model.legacy.rsl.ws.tracking.LoanStatusTrackingServiceLocator;
import com.tmb.common.model.legacy.rsl.ws.tracking.LoanStatusTrackingSoapBindingStub;
import com.tmb.common.model.legacy.rsl.ws.tracking.request.Body;
import com.tmb.common.model.legacy.rsl.ws.tracking.request.Header;
import com.tmb.common.model.legacy.rsl.ws.tracking.request.RequestTracking;
import com.tmb.common.model.legacy.rsl.ws.tracking.response.ResponseTracking;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.xml.rpc.ServiceException;
import java.rmi.RemoteException;
import java.util.UUID;

@Service
public class LoanStatusTrackingClient {

    @Value("${loan-status-tracking.url}")
    private String loanStatusTrackingUrl;
    private  LoanStatusTrackingServiceLocator locator = new LoanStatusTrackingServiceLocator();

    private static final String MOBILE_NUMBER  = "0000000000";
    private static final String CHANNEL = "MIB";
    private static final String MODULE = "3";

    public void setLocator(LoanStatusTrackingServiceLocator locator){
        this.locator = locator;
    }
    public ResponseTracking searchAppStatusByID(String citizenId) throws RemoteException, ServiceException {

        locator.setLoanStatusTrackingEndpointAddress(loanStatusTrackingUrl);
        LoanStatusTrackingSoapBindingStub stub = (LoanStatusTrackingSoapBindingStub) locator.getLoanStatusTracking();

        RequestTracking req = new RequestTracking();

        Header header = new Header();
        req.setHeader(header);

        Body body = new Body();
        body.setCitizenID(citizenId);
        body.setMobileNo(MOBILE_NUMBER);
        req.setBody(body);

        header.setChannel(CHANNEL);
        header.setModule(MODULE);
        header.setRequestID(UUID.randomUUID().toString());
        req.setHeader(header);
        return stub.searchAppStatusByID(req);

    }
}
