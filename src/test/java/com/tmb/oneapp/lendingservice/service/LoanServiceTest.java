package com.tmb.oneapp.lendingservice.service;

import com.tmb.common.model.legacy.rsl.common.ob.creditcard.InstantCreditCard;
import com.tmb.common.model.legacy.rsl.common.ob.facility.InstantFacility;
import com.tmb.common.model.legacy.rsl.ws.instant.eligible.product.response.ResponseInstantLoanGetEligibleProduct;
import com.tmb.common.model.legacy.rsl.ws.tracking.response.*;
import com.tmb.oneapp.lendingservice.client.EligibleProductClient;
import com.tmb.oneapp.lendingservice.client.LoanStatusTrackingClient;
import com.tmb.oneapp.lendingservice.model.ServiceError;
import com.tmb.oneapp.lendingservice.model.ServiceResponse;
import com.tmb.oneapp.lendingservice.model.loan.*;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import javax.xml.rpc.ServiceException;
import java.rmi.RemoteException;
import java.util.List;

import static org.mockito.Mockito.when;


@RunWith(JUnit4.class)
public class LoanServiceTest {

    @Mock
    LoanStatusTrackingClient loanStatusTrackingClient;

    @Mock
    EligibleProductClient eligibleProductClient;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    private  ResponseTracking mockLoanStatusTrackingResponse(){
        Product product = new Product();
        product.setProductCode("VM");


        ResponseTracking mockLoanStatusTrackingResponse = new ResponseTracking();

        Body body = new Body();

        Application application = new Application();
        application.setApplicationDate("2021-04-30T20:25:18.000Z");

        RoadMap roadMap = new RoadMap();
        roadMap.setDefaultDescEn("ttb has received your application.");

        Applicant applicant = new Applicant();
        applicant.setApplicantType("P");
        applicant.setFirstNameEN("FLEXILOAN NA TEETEEBEE");
        applicant.setFirstNameTH("วันแอพสาม");
        Product[] products = new Product[]{product};

        applicant.setProducts(products);

        Applicant[] applicants = new Applicant[]{applicant};

        application.setApplicants(applicants);
        application.setRoadMap(new RoadMap[]{roadMap});

        Application[] applications = new Application[]{application};
        body.setApplication(applications);


        mockLoanStatusTrackingResponse.setBody(body);
        Header header = new Header();
        header.setChannel("MIB");
        header.setModule("");
        header.setRequestID("725a9ec5-5de0-4b95-a51f-9774b559b459");
        header.setResponseCode("MSG_000");
        header.setResponseDescriptionEN("Success");
        header.setResponseDescriptionTH("");
        mockLoanStatusTrackingResponse.setHeader(header);
        return mockLoanStatusTrackingResponse;
    }
    @Test
    void fetchLoanStatusTrackingSuccess() throws ServiceException, RemoteException {


        ResponseTracking mockLoanStatusTrackingResponse = mockLoanStatusTrackingResponse();

        when(loanStatusTrackingClient.searchAppStatusByID()).thenReturn(mockLoanStatusTrackingResponse);
        LoanService loanService = new LoanService(loanStatusTrackingClient,null);
        ProductRequest request = new ProductRequest();

        ServiceResponse actualResponse = loanService.fetchLoanStatusTracking(request);

        Assertions.assertNotNull(actualResponse);

        LoanStatusTrackingResponse loanStatusTrackingResponse =  (LoanStatusTrackingResponse) actualResponse.getData();
        Assertions.assertNotNull(loanStatusTrackingResponse);

        List<OneAppApplication> actualApplications = loanStatusTrackingResponse.getApplications();
        Assertions.assertNotNull(actualApplications);

        OneAppApplication actualApplication = actualApplications.get(0);
        Assertions.assertNotNull(actualApplication);
        Assertions.assertEquals("2021-04-30T20:25:18.000Z", actualApplication.getApplicationDate());

        OneAppApplicant[] actualApplicants = actualApplication.getApplicants();
        Assertions.assertNotNull(actualApplicants);
        OneAppApplicant actualApplicant = actualApplicants[0];

        Assertions.assertEquals("P", actualApplicant.getApplicantType());

        OneAppRoadMap[] actualRoadMaps = actualApplication.getRoadMap();
        Assertions.assertNotNull(actualRoadMaps);

        OneAppRoadMap actualRoadMap = actualRoadMaps[0];
        Assertions.assertEquals("ttb has received your application.", actualRoadMap.getDefaultDescEn());
    }

    @Test
    void fetchLoanStatusTrackingShouldHandleDataNotFound() throws ServiceException, RemoteException {
        ResponseTracking mockLoanStatusTrackingResponse = new ResponseTracking();
        Header header = new Header();
        header.setChannel("MIB");
        header.setModule("");
        header.setRequestID("725a9ec5-5de0-4b95-a51f-9774b559b459");
        header.setResponseCode("MSG_001");
        header.setResponseDescriptionEN("Data Not Found");
        header.setResponseDescriptionTH("");
        mockLoanStatusTrackingResponse.setHeader(header);
        when(loanStatusTrackingClient.searchAppStatusByID()).thenReturn(mockLoanStatusTrackingResponse);
        LoanService loanService = new LoanService(loanStatusTrackingClient,null);
        ServiceResponse actualResponse = loanService.fetchLoanStatusTracking(new ProductRequest());
        ServiceError error = actualResponse.getError();
        Assertions.assertNotNull(error);
        Assertions.assertEquals(header.getResponseCode(), error.getResponseCode());
        Assertions.assertEquals(header.getResponseDescriptionEN(), error.getErrorMessage());
    }

    private ResponseInstantLoanGetEligibleProduct mockResponseInstantLoanGetEligibleProduct(){
        ResponseInstantLoanGetEligibleProduct mockResponse = new ResponseInstantLoanGetEligibleProduct();
        com.tmb.common.model.legacy.rsl.ws.instant.eligible.product.response.Body body = new com.tmb.common.model.legacy.rsl.ws.instant.eligible.product.response.Body();
        InstantCreditCard[] instantCreditCards = new InstantCreditCard[0];
        body.setInstantCreditCard(instantCreditCards);
        InstantFacility[] instantFacilities = new InstantFacility[0];
        body.setInstantFacility(instantFacilities);
        mockResponse.setBody(body);

        com.tmb.common.model.legacy.rsl.ws.instant.eligible.product.response.Header header = new com.tmb.common.model.legacy.rsl.ws.instant.eligible.product.response.Header();
        header.setResponseCode("MSG_000");
        mockResponse.setHeader(header);
        return mockResponse;
    }

    @Test
    void fetchEligibleProductsSuccess() throws ServiceException, RemoteException {
        ResponseInstantLoanGetEligibleProduct mockResponse =mockResponseInstantLoanGetEligibleProduct();

        when(eligibleProductClient.getEligibleProduct()).thenReturn(mockResponse);
        LoanService loanService = new LoanService(null,eligibleProductClient);
        ServiceResponse actualResponse = loanService.fetchEligibleProducts(new ProductRequest());
        Assertions.assertNotNull(actualResponse);

    }

    @Test
    void fetchEligibleProductsShouldHandleDataNotFound() throws ServiceException, RemoteException {
        ResponseInstantLoanGetEligibleProduct mockResponse = new ResponseInstantLoanGetEligibleProduct();

        com.tmb.common.model.legacy.rsl.ws.instant.eligible.product.response.Header header = new com.tmb.common.model.legacy.rsl.ws.instant.eligible.product.response.Header();
        header.setResponseCode("MSG_001");
        mockResponse.setHeader(header);

        when(eligibleProductClient.getEligibleProduct()).thenReturn(mockResponse);
        LoanService loanService = new LoanService(null,eligibleProductClient);
        ServiceResponse actualResponse = loanService.fetchEligibleProducts(new ProductRequest());
        Assertions.assertNotNull(actualResponse);
        ServiceError serviceError = actualResponse.getError();
        Assertions.assertNotNull(serviceError);
        Assertions.assertEquals(header.getResponseCode(), serviceError.getResponseCode());

    }

    @Test
    void fetchProductsSuccess() throws ServiceException, RemoteException {
        ResponseTracking mockLoanStatusTrackingResponse = mockLoanStatusTrackingResponse();
        when(loanStatusTrackingClient.searchAppStatusByID()).thenReturn(mockLoanStatusTrackingResponse);
        ResponseInstantLoanGetEligibleProduct mockResponse =mockResponseInstantLoanGetEligibleProduct();
        when(eligibleProductClient.getEligibleProduct()).thenReturn(mockResponse);
        LoanService loanService = new LoanService(loanStatusTrackingClient,eligibleProductClient);
        ServiceResponse actualResponse = loanService.fetchProducts(new ProductRequest());
        Assertions.assertNotNull(actualResponse);
        ProductResponse productResponse = (ProductResponse) actualResponse.getData();
        Assertions.assertNotNull(productResponse);

        LoanStatusTrackingResponse loanStatusTracking = productResponse.getLoanStatusTracking();
        Assertions.assertNotNull(loanStatusTracking);

        List<OneAppApplication> applications = loanStatusTracking.getApplications();
        OneAppApplication application = applications.get(0);

        Assertions.assertEquals(mockLoanStatusTrackingResponse.getBody().getApplication()[0].getApplicationDate(),application.getApplicationDate());

    }
}
