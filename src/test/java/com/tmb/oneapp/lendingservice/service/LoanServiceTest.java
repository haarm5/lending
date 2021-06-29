package com.tmb.oneapp.lendingservice.service;

import com.tmb.common.exception.model.TMBCommonException;
import com.tmb.common.model.TmbOneServiceResponse;
import com.tmb.common.model.TmbStatus;
import com.tmb.common.model.legacy.rsl.common.ob.creditcard.InstantCreditCard;
import com.tmb.common.model.legacy.rsl.common.ob.dropdown.CommonCodeEntry;
import com.tmb.common.model.legacy.rsl.common.ob.facility.InstantFacility;
import com.tmb.common.model.legacy.rsl.ws.instant.eligible.product.response.ResponseInstantLoanGetEligibleProduct;
import com.tmb.common.model.legacy.rsl.ws.tracking.response.*;
import com.tmb.oneapp.lendingservice.LendingModuleCache;
import com.tmb.oneapp.lendingservice.client.*;
import com.tmb.oneapp.lendingservice.constant.LoanCategory;
import com.tmb.oneapp.lendingservice.constant.ResponseCode;
import com.tmb.oneapp.lendingservice.model.Customer;
import com.tmb.oneapp.lendingservice.model.ServiceError;
import com.tmb.oneapp.lendingservice.model.ServiceResponse;
import com.tmb.oneapp.lendingservice.model.account.AccountSaving;
import com.tmb.oneapp.lendingservice.model.config.LendingModuleConfig;
import com.tmb.oneapp.lendingservice.model.creditcard.CreditCard;
import com.tmb.oneapp.lendingservice.model.creditcard.CreditCardResponse;
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
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;


@RunWith(JUnit4.class)
public class LoanServiceTest {

    @Mock
    LoanStatusTrackingClient loanStatusTrackingClient;

    @Mock
    EligibleProductClient eligibleProductClient;

    @Mock
    CustomerServiceClient customerServiceClient;

    @Mock
    LendingModuleCache lendingModuleCache;

    @Mock
    CustomerExpServiceClient customerExpServiceClient;

    @Mock
    CommonServiceFeignClient commonServiceFeignClient;

    LoanService loanService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
        loanService = new LoanService(loanStatusTrackingClient, eligibleProductClient, customerServiceClient, lendingModuleCache, customerExpServiceClient, commonServiceFeignClient);
    }

    private ResponseTracking mockLoanStatusTrackingResponse() {
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
        when(loanStatusTrackingClient.searchAppStatusByID(any())).thenReturn(mockLoanStatusTrackingResponse);

        TmbOneServiceResponse<Customer> mockCustomerResponse = mockCustomerResponse();
        when(customerServiceClient.getCustomerDetails(any())).thenReturn(mockCustomerResponse);
        ProductRequest request = new ProductRequest();

        ServiceResponse actualResponse = loanService.fetchLoanStatusTracking(request);

        Assertions.assertNotNull(actualResponse);

        LoanStatusTrackingResponse loanStatusTrackingResponse = (LoanStatusTrackingResponse) actualResponse.getData();
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

    private TmbOneServiceResponse<Customer> mockCustomerResponse() {
        TmbOneServiceResponse<Customer> mockCustomerResponse = new TmbOneServiceResponse<>();
        TmbStatus tmbStatus = new TmbStatus();
        tmbStatus.setCode(ResponseCode.SUCCESS.getCode());
        Customer customer = new Customer();
        customer.setCitizenId("1846622310794");
        mockCustomerResponse.setData(customer);
        mockCustomerResponse.setStatus(tmbStatus);
        return mockCustomerResponse;
    }

    @Test
    void fetchLoanStatusTrackingShouldHandleDataNotFound() throws ServiceException, RemoteException {

        TmbOneServiceResponse<Customer> mockCustomerResponse = mockCustomerResponse();
        when(customerServiceClient.getCustomerDetails(any())).thenReturn(mockCustomerResponse);

        ResponseTracking mockLoanStatusTrackingResponse = new ResponseTracking();
        Header header = new Header();
        header.setChannel("MIB");
        header.setModule("");
        header.setRequestID("725a9ec5-5de0-4b95-a51f-9774b559b459");
        header.setResponseCode("MSG_001");
        header.setResponseDescriptionEN("Data Not Found");
        header.setResponseDescriptionTH("");
        mockLoanStatusTrackingResponse.setHeader(header);
        when(loanStatusTrackingClient.searchAppStatusByID(any())).thenReturn(mockLoanStatusTrackingResponse);
        ServiceResponse actualResponse = loanService.fetchLoanStatusTracking(new ProductRequest());
        ServiceError error = actualResponse.getError();
        Assertions.assertNull(error);
        LoanStatusTrackingResponse loanStatusTrackingResponse = (LoanStatusTrackingResponse) actualResponse.getData();
        Assertions.assertNotNull(loanStatusTrackingResponse);
        Assertions.assertNull(loanStatusTrackingResponse.getApplications());
    }

    private ResponseInstantLoanGetEligibleProduct mockResponseInstantLoanGetEligibleProduct() {
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
        ResponseInstantLoanGetEligibleProduct mockResponse = mockResponseInstantLoanGetEligibleProduct();

        when(eligibleProductClient.getEligibleProduct(any())).thenReturn(mockResponse);
        HashMap<String, Object> masterData = new HashMap<>();
        masterData.put(LoanCategory.PYMT_CRITERIA.getCode(), new ArrayList<PaymentCriteriaOption>());
        ServiceResponse actualResponse = loanService.fetchEligibleProducts(new ProductRequest(), masterData);
        Assertions.assertNotNull(actualResponse);

    }

    @Test
    void fetchEligibleProductsShouldHandleDataNotFound() throws ServiceException, RemoteException {
        ResponseInstantLoanGetEligibleProduct mockResponse = new ResponseInstantLoanGetEligibleProduct();

        com.tmb.common.model.legacy.rsl.ws.instant.eligible.product.response.Header header = new com.tmb.common.model.legacy.rsl.ws.instant.eligible.product.response.Header();
        header.setResponseCode("MSG_001");
        mockResponse.setHeader(header);

        when(eligibleProductClient.getEligibleProduct(any())).thenReturn(mockResponse);
        HashMap<String, Object> masterData = new HashMap<>();
        ServiceResponse actualResponse = loanService.fetchEligibleProducts(new ProductRequest(), masterData);
        Assertions.assertNotNull(actualResponse);
        ServiceError serviceError = actualResponse.getError();
        Assertions.assertNotNull(serviceError);
        Assertions.assertEquals(header.getResponseCode(), serviceError.getResponseCode());

    }

    @Test
    void fetchProductsSuccess() throws ServiceException, RemoteException {

        List<CommonCodeEntry> mock26 = new ArrayList<>();
        when(lendingModuleCache.getListByCategoryCode(LoanCategory.PRODUCT.getCode())).thenReturn(mock26);
        when(lendingModuleCache.getListByCategoryCode(LoanCategory.SUBPRODUCT.getCode())).thenReturn(mock26);
        when(lendingModuleCache.getListByCategoryCode(LoanCategory.PYMT_CRITERIA.getCode())).thenReturn(mock26);
        TmbOneServiceResponse<AccountSaving> mockAccountListResponse = new TmbOneServiceResponse<>();
        TmbStatus tmbStatus = new TmbStatus();
        tmbStatus.setCode(ResponseCode.SUCCESS.getCode());
        mockAccountListResponse.setStatus(tmbStatus);
        AccountSaving mockAccountList = new AccountSaving();
        mockAccountList.setDepositAccountLists(new ArrayList<>());
        mockAccountListResponse.setData(mockAccountList);
        when(customerExpServiceClient.getAccountList(any(), any())).thenReturn(mockAccountListResponse);

        TmbOneServiceResponse<Customer> mockCustomerResponse = mockCustomerResponse();
        when(customerServiceClient.getCustomerDetails(any())).thenReturn(mockCustomerResponse);
        ResponseTracking mockLoanStatusTrackingResponse = mockLoanStatusTrackingResponse();
        when(loanStatusTrackingClient.searchAppStatusByID(any())).thenReturn(mockLoanStatusTrackingResponse);
        ResponseInstantLoanGetEligibleProduct mockResponse = mockResponseInstantLoanGetEligibleProduct();
        when(eligibleProductClient.getEligibleProduct(any())).thenReturn(mockResponse);

        ServiceResponse actualResponse = loanService.fetchProducts(new ProductRequest());
        Assertions.assertNotNull(actualResponse);
        ProductResponse productResponse = (ProductResponse) actualResponse.getData();
        Assertions.assertNotNull(productResponse);

        LoanStatusTrackingResponse loanStatusTracking = productResponse.getLoanStatusTracking();
        Assertions.assertNotNull(loanStatusTracking);

        List<OneAppApplication> applications = loanStatusTracking.getApplications();
        OneAppApplication application = applications.get(0);

        Assertions.assertEquals(mockLoanStatusTrackingResponse.getBody().getApplication()[0].getApplicationDate(), application.getApplicationDate());

    }

    @Test
    void fetchProductDetailCreditCardContainInAccountCreditCardAndStatusActiveShouldReturnAlreadyHasProduct() throws ServiceException, RemoteException, TMBCommonException {
        LendingModuleConfig mockLendingModuleConfig = new LendingModuleConfig();
        mockLendingModuleConfig.setFlexiOnly(new ArrayList<>(Arrays.asList("vi")));
        mockLendingModuleConfig.setApplyCreditCards(new ArrayList<>());
        mockLendingModuleConfig.setApplyPersonalLoans(new ArrayList<>());
        TmbOneServiceResponse<LendingModuleConfig> body = new TmbOneServiceResponse<>();
        body.setData(mockLendingModuleConfig);
        TmbStatus tmbStatus = new TmbStatus();
        tmbStatus.setCode(ResponseCode.SUCCESS.getCode());
        body.setStatus(tmbStatus);
        when(commonServiceFeignClient.getCommonConfig(any(), any())).thenReturn(body);

        List<CommonCodeEntry> master26 = new ArrayList<>();
        when(lendingModuleCache.getListByCategoryCode(LoanCategory.PRODUCT.getCode())).thenReturn(master26);
        List<CommonCodeEntry> master27 = new ArrayList<>();
        CommonCodeEntry vi = new CommonCodeEntry();
        vi.setEntryCode("vi");
        vi.setEntryName("TMB VISA Classic");
        vi.setEntryName2("ทีทีบี");
        master26.add(vi);
        when(lendingModuleCache.getListByCategoryCode(LoanCategory.SUBPRODUCT.getCode())).thenReturn(master27);


        String crmId = "123";
        String correlationId = "abc";
        TmbOneServiceResponse<CreditCardResponse> mockCreditCardResponse = new TmbOneServiceResponse<>();

        tmbStatus.setCode(ResponseCode.SUCCESS.getCode());
        mockCreditCardResponse.setStatus(tmbStatus);
        CreditCardResponse mockCreditCardData = new CreditCardResponse();
        CreditCard c1 = new CreditCard();
        c1.setRslProductCode("vi");
        c1.setAccountStatus("active");
        mockCreditCardData.setCreditCards(new ArrayList<>(Arrays.asList(c1)));
        mockCreditCardData.setFlashCards(new ArrayList<>());
        mockCreditCardResponse.setData(mockCreditCardData);
        when(customerExpServiceClient.getCreditCards(any(), any())).thenReturn(mockCreditCardResponse);


        ProductDetailRequest request = new ProductDetailRequest();
        request.setProductCode("vi");
        ServiceResponse actualResponse = loanService.fetchProductDetail(crmId, request);
        ProductDetailResponse productDetailResponse = (ProductDetailResponse) actualResponse.getData();
        Assertions.assertTrue(productDetailResponse.isAlreadyHasProduct());
        Assertions.assertEquals("credit_card", productDetailResponse.getLoanType());
        Assertions.assertEquals(vi.getEntryName(), productDetailResponse.getProductNameEn());


    }

    @Test
    void fetchProductDetailCreditCardFlexiOnlyShouldBeTrue() throws ServiceException, RemoteException, TMBCommonException {


        List<CommonCodeEntry> master26 = new ArrayList<>();
        when(lendingModuleCache.getListByCategoryCode(LoanCategory.PRODUCT.getCode())).thenReturn(master26);
        List<CommonCodeEntry> master27 = new ArrayList<>();
        CommonCodeEntry vi = new CommonCodeEntry();
        vi.setEntryCode("vi");
        vi.setEntryName("TMB VISA Classic");
        vi.setEntryName2("ทีทีบี");
        master26.add(vi);
        when(lendingModuleCache.getListByCategoryCode(LoanCategory.SUBPRODUCT.getCode())).thenReturn(master27);


        String crmId = "123";
        String correlationId = "abc";
        TmbOneServiceResponse<CreditCardResponse> mockCreditCardResponse = new TmbOneServiceResponse<>();
        TmbStatus tmbStatus = new TmbStatus();
        tmbStatus.setCode(ResponseCode.SUCCESS.getCode());
        mockCreditCardResponse.setStatus(tmbStatus);
        CreditCardResponse mockCreditCardData = new CreditCardResponse();
        CreditCard c1 = new CreditCard();
        c1.setRslProductCode("vj");
        c1.setAccountStatus("active");
        mockCreditCardData.setCreditCards(new ArrayList<>(Arrays.asList(c1)));
        mockCreditCardData.setFlashCards(new ArrayList<>());
        mockCreditCardResponse.setData(mockCreditCardData);
        when(customerExpServiceClient.getCreditCards(any(), any())).thenReturn(mockCreditCardResponse);
        ResponseInstantLoanGetEligibleProduct mockEligibleProductResponse = new ResponseInstantLoanGetEligibleProduct();

        com.tmb.common.model.legacy.rsl.ws.instant.eligible.product.response.Body mockBody = new com.tmb.common.model.legacy.rsl.ws.instant.eligible.product.response.Body();
        InstantCreditCard ic1 = new InstantCreditCard();
        ic1.setProductType("vi");
        mockBody.setInstantCreditCard(new InstantCreditCard[]{ic1});
        mockEligibleProductResponse.setBody(mockBody);

        when(eligibleProductClient.getEligibleProduct(any())).thenReturn(mockEligibleProductResponse);


        LendingModuleConfig mockLendingModuleConfig = new LendingModuleConfig();
        mockLendingModuleConfig.setFlexiOnly(new ArrayList<>(Arrays.asList("vi")));
        mockLendingModuleConfig.setApplyCreditCards(new ArrayList<>());
        mockLendingModuleConfig.setApplyPersonalLoans(new ArrayList<>());
        TmbOneServiceResponse<LendingModuleConfig> body = new TmbOneServiceResponse<>();
        body.setStatus(tmbStatus);
        body.setData(mockLendingModuleConfig);

        when(commonServiceFeignClient.getCommonConfig(any(), any())).thenReturn(body);

        ProductDetailRequest request = new ProductDetailRequest();
        request.setProductCode("vi");
        ServiceResponse actualResponse = loanService.fetchProductDetail(crmId, request);
        ProductDetailResponse productDetailResponse = (ProductDetailResponse) actualResponse.getData();
        Assertions.assertFalse(productDetailResponse.isAlreadyHasProduct());
        Assertions.assertTrue(productDetailResponse.isFlexiOnly());
        Assertions.assertEquals("credit_card", productDetailResponse.getLoanType());
        Assertions.assertEquals(vi.getEntryName(), productDetailResponse.getProductNameEn());


    }

    @Test
    void fetchProductDetailCreditCardFlexiLoanFlowInstantFlagIsYAndIsSubmittedIsNShouldReturnContinueApply() throws ServiceException, RemoteException, TMBCommonException {


        List<CommonCodeEntry> master26 = new ArrayList<>();
        when(lendingModuleCache.getListByCategoryCode(LoanCategory.PRODUCT.getCode())).thenReturn(master26);
        List<CommonCodeEntry> master27 = new ArrayList<>();
        CommonCodeEntry vi = new CommonCodeEntry();
        vi.setEntryCode("vi");
        vi.setEntryName("TMB VISA Classic");
        vi.setEntryName2("ทีทีบี");
        master26.add(vi);
        when(lendingModuleCache.getListByCategoryCode(LoanCategory.SUBPRODUCT.getCode())).thenReturn(master27);


        String crmId = "123";
        String correlationId = "abc";
        TmbOneServiceResponse<CreditCardResponse> mockCreditCardResponse = new TmbOneServiceResponse<>();
        TmbStatus tmbStatus = new TmbStatus();
        tmbStatus.setCode(ResponseCode.SUCCESS.getCode());
        mockCreditCardResponse.setStatus(tmbStatus);
        CreditCardResponse mockCreditCardData = new CreditCardResponse();
        CreditCard c1 = new CreditCard();
        c1.setRslProductCode("vj");
        c1.setAccountStatus("active");
        mockCreditCardData.setCreditCards(new ArrayList<>(Arrays.asList(c1)));
        mockCreditCardData.setFlashCards(new ArrayList<>());
        mockCreditCardResponse.setData(mockCreditCardData);
        when(customerExpServiceClient.getCreditCards(any(), any())).thenReturn(mockCreditCardResponse);
        ResponseInstantLoanGetEligibleProduct mockEligibleProductResponse = new ResponseInstantLoanGetEligibleProduct();

        com.tmb.common.model.legacy.rsl.ws.instant.eligible.product.response.Body mockBody = new com.tmb.common.model.legacy.rsl.ws.instant.eligible.product.response.Body();
        InstantCreditCard ic1 = new InstantCreditCard();
        ic1.setProductType("vi");
        mockBody.setInstantCreditCard(new InstantCreditCard[]{ic1});
        mockEligibleProductResponse.setBody(mockBody);

        when(eligibleProductClient.getEligibleProduct(any())).thenReturn(mockEligibleProductResponse);


        LendingModuleConfig mockLendingModuleConfig = new LendingModuleConfig();
        mockLendingModuleConfig.setFlexiOnly(new ArrayList<>());
        mockLendingModuleConfig.setApplyCreditCards(new ArrayList<>());
        mockLendingModuleConfig.setApplyPersonalLoans(new ArrayList<>());
        TmbOneServiceResponse<LendingModuleConfig> body = new TmbOneServiceResponse<>();
        body.setData(mockLendingModuleConfig);
        body.setStatus(tmbStatus);
        when(commonServiceFeignClient.getCommonConfig(any(), any())).thenReturn(body);

        TmbOneServiceResponse<Customer> mockCustomerResponse = new TmbOneServiceResponse<>();
        Customer customer = new Customer();
        customer.setCitizenId("abcxyz");
        mockCustomerResponse.setData(customer);
        when(customerServiceClient.getCustomerDetails(any())).thenReturn(mockCustomerResponse);

        ResponseTracking mockResponseTracking = new ResponseTracking();
        Body mockResponseTrackingBody = new Body();
        Application application1 = new Application();
        Applicant applicant1 = new Applicant();
        Product product1 = new Product();
        product1.setProductCode("vi");
        applicant1.setProducts(new Product[]{product1});
        application1.setApplicants(new Applicant[]{applicant1});
        application1.setInstantFlag("Y");
        application1.setIsSubmitted("N");
        mockResponseTrackingBody.setApplication(new Application[]{application1});
        mockResponseTracking.setBody(mockResponseTrackingBody);

        when(loanStatusTrackingClient.searchAppStatusByID(any())).thenReturn(mockResponseTracking);

        ProductDetailRequest request = new ProductDetailRequest();
        request.setProductCode("vi");
        ServiceResponse actualResponse = loanService.fetchProductDetail(crmId, request);
        ProductDetailResponse productDetailResponse = (ProductDetailResponse) actualResponse.getData();
        Assertions.assertFalse(productDetailResponse.isAlreadyHasProduct());
        Assertions.assertFalse(productDetailResponse.isFlexiOnly());
        Assertions.assertEquals("credit_card", productDetailResponse.getLoanType());
        Assertions.assertEquals("continue_apply", productDetailResponse.getStatus());
        Assertions.assertEquals(vi.getEntryName(), productDetailResponse.getProductNameEn());


    }

    @Test
    void fetchProductDetailCreditCardFlexiLoanFlowInstantFlagIsYAndIsSubmittedIsYShouldReturnCheckStatus() throws ServiceException, RemoteException, TMBCommonException {


        List<CommonCodeEntry> master26 = new ArrayList<>();
        when(lendingModuleCache.getListByCategoryCode(LoanCategory.PRODUCT.getCode())).thenReturn(master26);
        List<CommonCodeEntry> master27 = new ArrayList<>();
        CommonCodeEntry vi = new CommonCodeEntry();
        vi.setEntryCode("vi");
        vi.setEntryName("TMB VISA Classic");
        vi.setEntryName2("ทีทีบี");
        master26.add(vi);
        when(lendingModuleCache.getListByCategoryCode(LoanCategory.SUBPRODUCT.getCode())).thenReturn(master27);


        String crmId = "123";
        String correlationId = "abc";
        TmbOneServiceResponse<CreditCardResponse> mockCreditCardResponse = new TmbOneServiceResponse<>();
        TmbStatus tmbStatus = new TmbStatus();
        tmbStatus.setCode(ResponseCode.SUCCESS.getCode());
        mockCreditCardResponse.setStatus(tmbStatus);
        CreditCardResponse mockCreditCardData = new CreditCardResponse();
        CreditCard c1 = new CreditCard();
        c1.setRslProductCode("vj");
        c1.setAccountStatus("active");
        mockCreditCardData.setCreditCards(new ArrayList<>(Arrays.asList(c1)));
        mockCreditCardData.setFlashCards(new ArrayList<>());
        mockCreditCardResponse.setData(mockCreditCardData);
        when(customerExpServiceClient.getCreditCards(any(), any())).thenReturn(mockCreditCardResponse);
        ResponseInstantLoanGetEligibleProduct mockEligibleProductResponse = new ResponseInstantLoanGetEligibleProduct();

        com.tmb.common.model.legacy.rsl.ws.instant.eligible.product.response.Body mockBody = new com.tmb.common.model.legacy.rsl.ws.instant.eligible.product.response.Body();
        InstantCreditCard ic1 = new InstantCreditCard();
        ic1.setProductType("vi");
        mockBody.setInstantCreditCard(new InstantCreditCard[]{ic1});
        mockEligibleProductResponse.setBody(mockBody);

        when(eligibleProductClient.getEligibleProduct(any())).thenReturn(mockEligibleProductResponse);


        LendingModuleConfig mockLendingModuleConfig = new LendingModuleConfig();
        mockLendingModuleConfig.setFlexiOnly(new ArrayList<>());
        mockLendingModuleConfig.setApplyCreditCards(new ArrayList<>());
        mockLendingModuleConfig.setApplyPersonalLoans(new ArrayList<>());
        TmbOneServiceResponse<LendingModuleConfig> body = new TmbOneServiceResponse<>();
        body.setData(mockLendingModuleConfig);
        body.setStatus(tmbStatus);
        when(commonServiceFeignClient.getCommonConfig(any(), any())).thenReturn(body);

        TmbOneServiceResponse<Customer> mockCustomerResponse = new TmbOneServiceResponse<>();
        Customer customer = new Customer();
        customer.setCitizenId("abcxyz");
        mockCustomerResponse.setData(customer);
        when(customerServiceClient.getCustomerDetails(any())).thenReturn(mockCustomerResponse);

        ResponseTracking mockResponseTracking = new ResponseTracking();
        Body mockResponseTrackingBody = new Body();
        Application application1 = new Application();
        Applicant applicant1 = new Applicant();
        Product product1 = new Product();
        product1.setProductCode("vi");
        applicant1.setProducts(new Product[]{product1});
        application1.setApplicants(new Applicant[]{applicant1});
        application1.setInstantFlag("Y");
        application1.setIsSubmitted("Y");
        mockResponseTrackingBody.setApplication(new Application[]{application1});
        mockResponseTracking.setBody(mockResponseTrackingBody);

        when(loanStatusTrackingClient.searchAppStatusByID(any())).thenReturn(mockResponseTracking);

        ProductDetailRequest request = new ProductDetailRequest();
        request.setProductCode("vi");
        ServiceResponse actualResponse = loanService.fetchProductDetail(crmId, request);
        ProductDetailResponse productDetailResponse = (ProductDetailResponse) actualResponse.getData();
        Assertions.assertFalse(productDetailResponse.isAlreadyHasProduct());
        Assertions.assertFalse(productDetailResponse.isFlexiOnly());
        Assertions.assertEquals("credit_card", productDetailResponse.getLoanType());
        Assertions.assertEquals("check_status", productDetailResponse.getStatus());
        Assertions.assertEquals(vi.getEntryName(), productDetailResponse.getProductNameEn());


    }

    @Test
    void fetchProductDetailCreditCardLoanSubmissionFlowInstantFlagIsNotYAndIsSubmittedIsNShouldReturnSeeDocList() throws ServiceException, RemoteException, TMBCommonException {


        List<CommonCodeEntry> master26 = new ArrayList<>();
        when(lendingModuleCache.getListByCategoryCode(LoanCategory.PRODUCT.getCode())).thenReturn(master26);
        List<CommonCodeEntry> master27 = new ArrayList<>();
        CommonCodeEntry vi = new CommonCodeEntry();
        vi.setEntryCode("vi");
        vi.setEntryName("TMB VISA Classic");
        vi.setEntryName2("ทีทีบี");
        master26.add(vi);
        when(lendingModuleCache.getListByCategoryCode(LoanCategory.SUBPRODUCT.getCode())).thenReturn(master27);


        String crmId = "123";
        String correlationId = "abc";
        TmbOneServiceResponse<CreditCardResponse> mockCreditCardResponse = new TmbOneServiceResponse<>();
        TmbStatus tmbStatus = new TmbStatus();
        tmbStatus.setCode(ResponseCode.SUCCESS.getCode());
        mockCreditCardResponse.setStatus(tmbStatus);
        CreditCardResponse mockCreditCardData = new CreditCardResponse();
        CreditCard c1 = new CreditCard();
        c1.setRslProductCode("vj");
        c1.setAccountStatus("active");
        mockCreditCardData.setCreditCards(new ArrayList<>(Arrays.asList(c1)));
        mockCreditCardData.setFlashCards(new ArrayList<>());
        mockCreditCardResponse.setData(mockCreditCardData);
        when(customerExpServiceClient.getCreditCards(any(), any())).thenReturn(mockCreditCardResponse);
        ResponseInstantLoanGetEligibleProduct mockEligibleProductResponse = new ResponseInstantLoanGetEligibleProduct();

        com.tmb.common.model.legacy.rsl.ws.instant.eligible.product.response.Body mockBody = new com.tmb.common.model.legacy.rsl.ws.instant.eligible.product.response.Body();
        InstantCreditCard ic1 = new InstantCreditCard();
        ic1.setProductType("vi");
        mockBody.setInstantCreditCard(new InstantCreditCard[]{ic1});
        mockEligibleProductResponse.setBody(mockBody);

        when(eligibleProductClient.getEligibleProduct(any())).thenReturn(mockEligibleProductResponse);


        LendingModuleConfig mockLendingModuleConfig = new LendingModuleConfig();
        mockLendingModuleConfig.setIncompleteDocStatus(new ArrayList<>(Arrays.asList("IDOFD")));
        mockLendingModuleConfig.setFlexiOnly(new ArrayList<>());
        mockLendingModuleConfig.setApplyCreditCards(new ArrayList<>());
        mockLendingModuleConfig.setApplyPersonalLoans(new ArrayList<>());
        TmbOneServiceResponse<LendingModuleConfig> body = new TmbOneServiceResponse<>();
        body.setData(mockLendingModuleConfig);
        body.setStatus(tmbStatus);
        when(commonServiceFeignClient.getCommonConfig(any(), any())).thenReturn(body);

        TmbOneServiceResponse<Customer> mockCustomerResponse = new TmbOneServiceResponse<>();
        Customer customer = new Customer();
        customer.setCitizenId("abcxyz");
        mockCustomerResponse.setData(customer);
        when(customerServiceClient.getCustomerDetails(any())).thenReturn(mockCustomerResponse);

        ResponseTracking mockResponseTracking = new ResponseTracking();
        Body mockResponseTrackingBody = new Body();
        Application application1 = new Application();
        Applicant applicant1 = new Applicant();
        Product product1 = new Product();
        product1.setProductCode("vi");
        applicant1.setProducts(new Product[]{product1});
        application1.setApplicants(new Applicant[]{applicant1});
        application1.setInstantFlag("N");
        application1.setIsSubmitted("N");
        application1.setAppStatus("IDOFD");
        mockResponseTrackingBody.setApplication(new Application[]{application1});
        mockResponseTracking.setBody(mockResponseTrackingBody);

        when(loanStatusTrackingClient.searchAppStatusByID(any())).thenReturn(mockResponseTracking);

        ProductDetailRequest request = new ProductDetailRequest();
        request.setProductCode("vi");
        ServiceResponse actualResponse = loanService.fetchProductDetail(crmId, request);
        ProductDetailResponse productDetailResponse = (ProductDetailResponse) actualResponse.getData();
        Assertions.assertFalse(productDetailResponse.isAlreadyHasProduct());
        Assertions.assertFalse(productDetailResponse.isFlexiOnly());
        Assertions.assertEquals("credit_card", productDetailResponse.getLoanType());
        Assertions.assertEquals("see_doc_list", productDetailResponse.getStatus());
        Assertions.assertEquals(vi.getEntryName(), productDetailResponse.getProductNameEn());


    }

    @Test
    void fetchProductDetailCreditCardLoanSubmissionFlowInstantFlagIsNotYAndIsSubmittedIsNAppStatusIsOtherShouldReturnCheckStatus() throws ServiceException, RemoteException, TMBCommonException {


        List<CommonCodeEntry> master26 = new ArrayList<>();
        when(lendingModuleCache.getListByCategoryCode(LoanCategory.PRODUCT.getCode())).thenReturn(master26);
        List<CommonCodeEntry> master27 = new ArrayList<>();
        CommonCodeEntry vi = new CommonCodeEntry();
        vi.setEntryCode("vi");
        vi.setEntryName("TMB VISA Classic");
        vi.setEntryName2("ทีทีบี");
        master26.add(vi);
        when(lendingModuleCache.getListByCategoryCode(LoanCategory.SUBPRODUCT.getCode())).thenReturn(master27);


        String crmId = "123";
        String correlationId = "abc";
        TmbOneServiceResponse<CreditCardResponse> mockCreditCardResponse = new TmbOneServiceResponse<>();
        TmbStatus tmbStatus = new TmbStatus();
        tmbStatus.setCode(ResponseCode.SUCCESS.getCode());
        mockCreditCardResponse.setStatus(tmbStatus);
        CreditCardResponse mockCreditCardData = new CreditCardResponse();
        CreditCard c1 = new CreditCard();
        c1.setRslProductCode("vj");
        c1.setAccountStatus("active");
        mockCreditCardData.setCreditCards(new ArrayList<>(Arrays.asList(c1)));
        mockCreditCardData.setFlashCards(new ArrayList<>());
        mockCreditCardResponse.setData(mockCreditCardData);
        when(customerExpServiceClient.getCreditCards(any(), any())).thenReturn(mockCreditCardResponse);
        ResponseInstantLoanGetEligibleProduct mockEligibleProductResponse = new ResponseInstantLoanGetEligibleProduct();

        com.tmb.common.model.legacy.rsl.ws.instant.eligible.product.response.Body mockBody = new com.tmb.common.model.legacy.rsl.ws.instant.eligible.product.response.Body();
        InstantCreditCard ic1 = new InstantCreditCard();
        ic1.setProductType("vi");
        mockBody.setInstantCreditCard(new InstantCreditCard[]{ic1});
        mockEligibleProductResponse.setBody(mockBody);

        when(eligibleProductClient.getEligibleProduct(any())).thenReturn(mockEligibleProductResponse);


        LendingModuleConfig mockLendingModuleConfig = new LendingModuleConfig();
        mockLendingModuleConfig.setIncompleteDocStatus(new ArrayList<>(Arrays.asList("IDOFD")));
        mockLendingModuleConfig.setFlexiOnly(new ArrayList<>());
        mockLendingModuleConfig.setApplyCreditCards(new ArrayList<>());
        mockLendingModuleConfig.setApplyPersonalLoans(new ArrayList<>());
        TmbOneServiceResponse<LendingModuleConfig> body = new TmbOneServiceResponse<>();
        body.setData(mockLendingModuleConfig);
        body.setStatus(tmbStatus);
        when(commonServiceFeignClient.getCommonConfig(any(), any())).thenReturn(body);

        TmbOneServiceResponse<Customer> mockCustomerResponse = new TmbOneServiceResponse<>();
        Customer customer = new Customer();
        customer.setCitizenId("abcxyz");
        mockCustomerResponse.setData(customer);
        when(customerServiceClient.getCustomerDetails(any())).thenReturn(mockCustomerResponse);

        ResponseTracking mockResponseTracking = new ResponseTracking();
        Body mockResponseTrackingBody = new Body();
        Application application1 = new Application();
        Applicant applicant1 = new Applicant();
        Product product1 = new Product();
        product1.setProductCode("vi");
        applicant1.setProducts(new Product[]{product1});
        application1.setApplicants(new Applicant[]{applicant1});
        application1.setInstantFlag("N");
        application1.setIsSubmitted("N");
        application1.setAppStatus("Other");
        mockResponseTrackingBody.setApplication(new Application[]{application1});
        mockResponseTracking.setBody(mockResponseTrackingBody);

        when(loanStatusTrackingClient.searchAppStatusByID(any())).thenReturn(mockResponseTracking);

        ProductDetailRequest request = new ProductDetailRequest();
        request.setProductCode("vi");
        ServiceResponse actualResponse = loanService.fetchProductDetail(crmId, request);
        ProductDetailResponse productDetailResponse = (ProductDetailResponse) actualResponse.getData();
        Assertions.assertFalse(productDetailResponse.isAlreadyHasProduct());
        Assertions.assertFalse(productDetailResponse.isFlexiOnly());
        Assertions.assertEquals("credit_card", productDetailResponse.getLoanType());
        Assertions.assertEquals("check_status", productDetailResponse.getStatus());
        Assertions.assertEquals(vi.getEntryName(), productDetailResponse.getProductNameEn());


    }

    @Test
    void fetchProductDetailCreditCardLoanSubmissionFlowInstantFlagIsNotYAndIsSubmittedIsNAppStatusIsOtherShouldReturnContinueApplyWithProductName() throws ServiceException, RemoteException, TMBCommonException {


        List<CommonCodeEntry> master26 = new ArrayList<>();
        when(lendingModuleCache.getListByCategoryCode(LoanCategory.PRODUCT.getCode())).thenReturn(master26);
        List<CommonCodeEntry> master27 = new ArrayList<>();
        CommonCodeEntry vi = new CommonCodeEntry();
        vi.setEntryCode("vi");
        vi.setEntryName("TMB VISA Classic");
        vi.setEntryName2("ทีทีบี");
        master26.add(vi);
        when(lendingModuleCache.getListByCategoryCode(LoanCategory.SUBPRODUCT.getCode())).thenReturn(master27);


        String crmId = "123";
        String correlationId = "abc";
        TmbOneServiceResponse<CreditCardResponse> mockCreditCardResponse = new TmbOneServiceResponse<>();
        TmbStatus tmbStatus = new TmbStatus();
        tmbStatus.setCode(ResponseCode.SUCCESS.getCode());
        mockCreditCardResponse.setStatus(tmbStatus);
        CreditCardResponse mockCreditCardData = new CreditCardResponse();
        CreditCard c1 = new CreditCard();
        c1.setRslProductCode("vj");
        c1.setAccountStatus("active");
        mockCreditCardData.setCreditCards(new ArrayList<>(Arrays.asList(c1)));
        mockCreditCardData.setFlashCards(new ArrayList<>());
        mockCreditCardResponse.setData(mockCreditCardData);
        when(customerExpServiceClient.getCreditCards(any(), any())).thenReturn(mockCreditCardResponse);
        ResponseInstantLoanGetEligibleProduct mockEligibleProductResponse = new ResponseInstantLoanGetEligibleProduct();

        com.tmb.common.model.legacy.rsl.ws.instant.eligible.product.response.Body mockBody = new com.tmb.common.model.legacy.rsl.ws.instant.eligible.product.response.Body();
        InstantCreditCard ic1 = new InstantCreditCard();
        ic1.setProductType("vj");
        mockBody.setInstantCreditCard(new InstantCreditCard[]{ic1});
        mockEligibleProductResponse.setBody(mockBody);

        when(eligibleProductClient.getEligibleProduct(any())).thenReturn(mockEligibleProductResponse);


        LendingModuleConfig mockLendingModuleConfig = new LendingModuleConfig();
        mockLendingModuleConfig.setIncompleteDocStatus(new ArrayList<>(Arrays.asList("IDOFD")));
        mockLendingModuleConfig.setFlexiOnly(new ArrayList<>());
        mockLendingModuleConfig.setApplyCreditCards(new ArrayList<>());
        mockLendingModuleConfig.setApplyPersonalLoans(new ArrayList<>());
        TmbOneServiceResponse<LendingModuleConfig> body = new TmbOneServiceResponse<>();
        body.setData(mockLendingModuleConfig);
        body.setStatus(tmbStatus);

        when(commonServiceFeignClient.getCommonConfig(any(), any())).thenReturn(body);

        TmbOneServiceResponse<Customer> mockCustomerResponse = new TmbOneServiceResponse<>();
        Customer customer = new Customer();
        customer.setCitizenId("abcxyz");
        mockCustomerResponse.setData(customer);
        when(customerServiceClient.getCustomerDetails(any())).thenReturn(mockCustomerResponse);

        ResponseTracking mockResponseTracking = new ResponseTracking();
        Body mockResponseTrackingBody = new Body();
        Application application1 = new Application();
        Applicant applicant1 = new Applicant();
        Product product1 = new Product();
        product1.setProductCode("vj" +
                "");
        applicant1.setProducts(new Product[]{product1});
        application1.setApplicants(new Applicant[]{applicant1});
        application1.setInstantFlag("N");
        application1.setIsSubmitted("N");
        application1.setAppStatus("Other");
        mockResponseTrackingBody.setApplication(new Application[]{application1});
        mockResponseTracking.setBody(mockResponseTrackingBody);

        when(loanStatusTrackingClient.searchAppStatusByID(any())).thenReturn(mockResponseTracking);

        ProductDetailRequest request = new ProductDetailRequest();
        request.setProductCode("vi");
        ServiceResponse actualResponse = loanService.fetchProductDetail(crmId, request);
        ProductDetailResponse productDetailResponse = (ProductDetailResponse) actualResponse.getData();
        Assertions.assertFalse(productDetailResponse.isAlreadyHasProduct());
        Assertions.assertFalse(productDetailResponse.isFlexiOnly());
        Assertions.assertEquals("credit_card", productDetailResponse.getLoanType());
        Assertions.assertEquals("apply_with_product_name", productDetailResponse.getStatus());
        Assertions.assertEquals(vi.getEntryName(), productDetailResponse.getProductNameEn());


    }
//    @Test
//    void fetchProductDetailPersonalLoanContainInAccountCreditCardAndStatusActiveShouldReturnAlreadyHasProduct() throws ServiceException, RemoteException {
//
//
//        List<CommonCodeEntry> master26 = new ArrayList<>();
//        when(lendingModuleCache.getListByCategoryCode(LoanCategory.PRODUCT.getCode())).thenReturn(master26);
//        List<CommonCodeEntry> master27 = new ArrayList<>();
//        CommonCodeEntry c2g01 = new CommonCodeEntry();
//        c2g01.setEntryCode("c2g01");
//        c2g01.setEntryName("ttb cash2go personal loan");
//        c2g01.setEntryName2("ทีทีบี");
//        master27.add(c2g01);
//        when(lendingModuleCache.getListByCategoryCode(LoanCategory.SUBPRODUCT.getCode())).thenReturn(master27);
//
//
//        String crmId = "123";
//        String correlationId = "abc";
//        TmbOneServiceResponse<CreditCardResponse> mockCreditCardResponse = new TmbOneServiceResponse<>();
//        TmbStatus tmbStatus = new TmbStatus();
//        tmbStatus.setCode(ResponseCode.SUCCESS.getCode());
//        mockCreditCardResponse.setStatus(tmbStatus);
//        CreditCardResponse mockCreditCardData = new CreditCardResponse();
//        CreditCard c1 = new CreditCard();
//        c1.setRslProductCode("c2g01");
//        c1.setAccountStatus("active");
//        mockCreditCardData.setCreditCards(new ArrayList<>(Arrays.asList(c1)));
//        mockCreditCardData.setFlashCards(new ArrayList<>());
//        mockCreditCardResponse.setData(mockCreditCardData);
//        when(customerExpServiceClient.getCreditCards(any(),any())).thenReturn(mockCreditCardResponse);
//
//
//        ProductDetailRequest request = new ProductDetailRequest();
//        request.setProductCode("c2g01");
//        ServiceResponse actualResponse = loanService.fetchProductDetail(crmId, request);
//        ProductDetailResponse productDetailResponse = (ProductDetailResponse) actualResponse.getData();
//        Assertions.assertTrue(productDetailResponse.isAlreadyHasProduct());
//        Assertions.assertEquals("personal_loan",productDetailResponse.getLoanType());
//        Assertions.assertEquals(c2g01.getEntryName(),productDetailResponse.getProductNameEn());
//
//
//    }
}
