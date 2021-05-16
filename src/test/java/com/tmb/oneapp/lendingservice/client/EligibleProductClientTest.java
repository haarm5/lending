package com.tmb.oneapp.lendingservice.client;

import com.tmb.common.model.legacy.rsl.ws.instant.eligible.product.response.Body;
import com.tmb.common.model.legacy.rsl.ws.instant.eligible.product.response.ResponseInstantLoanGetEligibleProduct;
import com.tmb.common.model.legacy.rsl.ws.loan.submission.LoanSubmissionInstantLoanGetEligibleProductServiceLocator;
import com.tmb.common.model.legacy.rsl.ws.loan.submission.LoanSubmissionInstantLoanGetEligibleProductSoapBindingStub;
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
public class EligibleProductClientTest {


    private EligibleProductClient eligibleProductClient;
    @Mock
    LoanSubmissionInstantLoanGetEligibleProductServiceLocator locator;
    @Mock
    LoanSubmissionInstantLoanGetEligibleProductSoapBindingStub stub;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    void getEligibleProductSuccess() throws ServiceException, RemoteException {

        ResponseInstantLoanGetEligibleProduct mockResponse = new ResponseInstantLoanGetEligibleProduct();
        Body body = new Body();
        body.setHostCifNo("123");
        mockResponse.setBody(body);
        when(stub.getEligibleProduct(any())).thenReturn(mockResponse);
        when(locator.getLoanSubmissionInstantLoanGetEligibleProduct()).thenReturn(stub);
        eligibleProductClient = new EligibleProductClient();
        eligibleProductClient.setLocator(locator);
        ResponseInstantLoanGetEligibleProduct actualResponse = eligibleProductClient.getEligibleProduct("001100000000000000000018593701");
        Assertions.assertNotNull(actualResponse);
        Assertions.assertEquals(body.getHostCifNo(), actualResponse.getBody().getHostCifNo());
    }

    @Test
    void getEligibleProductWrongCrmIdShouldGetException() throws ServiceException, RemoteException {

        ResponseInstantLoanGetEligibleProduct mockResponse = new ResponseInstantLoanGetEligibleProduct();
        Body body = new Body();
        body.setHostCifNo("123");
        mockResponse.setBody(body);
        when(stub.getEligibleProduct(any())).thenReturn(mockResponse);
        when(locator.getLoanSubmissionInstantLoanGetEligibleProduct()).thenReturn(stub);
        eligibleProductClient = new EligibleProductClient();
        eligibleProductClient.setLocator(locator);
        try {
            eligibleProductClient.getEligibleProduct("123");
            Assertions.fail("Should receive StringIndexOutOfBoundsException");
        } catch (StringIndexOutOfBoundsException e) {

        }
    }


}
