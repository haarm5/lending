package com.tmb.oneapp.lendingservice.client;


import com.tmb.common.model.legacy.rsl.ws.tracking.LoanStatusTrackingServiceLocator;
import com.tmb.common.model.legacy.rsl.ws.tracking.LoanStatusTrackingSoapBindingStub;
import com.tmb.common.model.legacy.rsl.ws.tracking.request.Body;
import com.tmb.common.model.legacy.rsl.ws.tracking.request.Header;
import com.tmb.common.model.legacy.rsl.ws.tracking.request.RequestTracking;
import com.tmb.common.model.legacy.rsl.ws.tracking.response.ResponseTracking;
import org.springframework.stereotype.Service;

import javax.xml.rpc.ServiceException;
import java.rmi.RemoteException;

@Service
public class LoanStatusTrackingClient {

    public ResponseTracking searchAppStatusByID() throws RemoteException, ServiceException {
        LoanStatusTrackingServiceLocator locator = new LoanStatusTrackingServiceLocator();
        locator.setLoanStatusTrackingEndpointAddress("http://10.209.27.99:9081/RSLWS/services/LoanStatusTracking");

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
        return stub.searchAppStatusByID(req);

    }
}
