package com.tmb.oneapp.lendingservice.client;

import com.tmb.common.model.legacy.rsl.ws.tracking.LoanStatusTrackingServiceLocator;
import com.tmb.common.model.legacy.rsl.ws.tracking.LoanStatusTrackingSoapBindingStub;
import com.tmb.common.model.legacy.rsl.ws.tracking.response.Application;
import com.tmb.common.model.legacy.rsl.ws.tracking.response.Body;
import com.tmb.common.model.legacy.rsl.ws.tracking.response.ResponseTracking;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import javax.xml.rpc.ServiceException;
import java.rmi.RemoteException;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@RunWith(JUnit4.class)
public class LoanStatusTrackingClientTest {

    private LoanStatusTrackingClient loanStatusTrackingClient;

    @Mock
    LoanStatusTrackingServiceLocator locator;
    @Mock
    LoanStatusTrackingSoapBindingStub stub;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    void searchAppStatusByIDSuccess() throws RemoteException, ServiceException {
        ResponseTracking mockResponse = new ResponseTracking();
        Body body = new Body();
        Application app1 = new Application();
        Application[] apps = new Application[]{app1};
        body.setApplication(apps);
        mockResponse.setBody(body);
        when(stub.searchAppStatusByID(any())).thenReturn(mockResponse);
        when(locator.getLoanStatusTracking()).thenReturn(stub);
        loanStatusTrackingClient = new LoanStatusTrackingClient();
        loanStatusTrackingClient.setLocator(locator);
        ResponseTracking actualResponse = loanStatusTrackingClient.searchAppStatusByID("");
        Assertions.assertNotNull(actualResponse);
        Assertions.assertEquals(1, actualResponse.getBody().getApplication().length);
    }
}
