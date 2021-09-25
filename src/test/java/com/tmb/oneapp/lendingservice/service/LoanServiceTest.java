package com.tmb.oneapp.lendingservice.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.tmb.common.exception.model.TMBCommonException;
import com.tmb.common.model.TmbOneServiceResponse;
import com.tmb.common.model.TmbStatus;
import com.tmb.common.model.legacy.rsl.common.ob.creditcard.InstantCreditCard;
import com.tmb.common.model.legacy.rsl.common.ob.dropdown.CommonCodeEntry;
import com.tmb.common.model.legacy.rsl.common.ob.facility.InstantFacility;
import com.tmb.common.model.legacy.rsl.common.ob.individual.Individual;
import com.tmb.common.model.legacy.rsl.ws.application.response.ResponseApplication;
import com.tmb.common.model.legacy.rsl.ws.individual.response.ResponseIndividual;
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
import com.tmb.oneapp.lendingservice.model.account.LoanAccount;
import com.tmb.oneapp.lendingservice.model.config.CommonProductConfig;
import com.tmb.oneapp.lendingservice.model.creditcard.CreditCard;
import com.tmb.oneapp.lendingservice.model.loan.*;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;

import javax.xml.rpc.ServiceException;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
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

    @Mock
    LoanSubmissionGetApplicationInfoClient loanSubmissionGetApplicationInfoClient;

    @Mock
    LoanSubmissionGetCustomerInfoClient loanSubmissionGetCustomerInfoClient;

    @InjectMocks
    LoanService loanService = new LoanService();

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
        List<CommonCodeEntry> master26 = new ArrayList<>();
        when(lendingModuleCache.getListByCategoryCode(LoanCategory.PRODUCT.getCode())).thenReturn(master26);
        List<CommonCodeEntry> master27 = new ArrayList<>();
        CommonCodeEntry vi = new CommonCodeEntry();
        vi.setEntryCode("vi");
        vi.setEntryName("TMB VISA Classic");
        vi.setEntryName2("ทีทีบี");
        master26.add(vi);

        CommonCodeEntry vj = new CommonCodeEntry();
        vj.setEntryCode("vj");
        vj.setEntryName("TMB VISA Classic");
        vj.setEntryName2("ทีทีบี");
        master26.add(vj);

        CommonCodeEntry c2g01 = new CommonCodeEntry();
        c2g01.setEntryCode("c2g01");
        c2g01.setEntryName("cash 2 go");
        c2g01.setEntryName2("ทีทีบี cash 2 go");
        master27.add(c2g01);

        when(lendingModuleCache.getListByCategoryCode(LoanCategory.SUBPRODUCT.getCode())).thenReturn(master27);
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
        Product[] products = new Product[] { product };
        applicant.setProducts(products);
        Applicant[] applicants = new Applicant[] { applicant };
        application.setApplicants(applicants);
        application.setRoadMap(new RoadMap[] { roadMap });

        Application[] applications = new Application[] { application };
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
        header.setResponseDescriptionEN("Success");
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

        Assertions.assertEquals(mockLoanStatusTrackingResponse.getBody().getApplication()[0].getApplicationDate(),
                application.getApplicationDate());

    }

    void verifyProductDetailInfo(String productCode, ProductDetailResponse productDetailResponse) {
        CommonProductConfig productConfig = LoanServiceUtils.getProductConfig(productCode);
        Assertions.assertEquals(productConfig.getProductNameEn(), productDetailResponse.getProductNameEn());
        Assertions.assertEquals(productConfig.getProductNameTh(), productDetailResponse.getProductNameTh());
        Assertions.assertEquals(productConfig.getContentLink(), productDetailResponse.getContentLink());
    }

    @Test
    void fetchProductOrientation_CreditCard_ContainInAccountCreditCard_And_StatusActive_ShouldReturn_AlreadyHasProduct()
            throws TMBCommonException {
        String requestProductCode = "vi";
        String crmId = "123";

        when(commonServiceFeignClient.getCommonConfig(any(), any()))
                .thenReturn(LoanServiceUtils.moduleLendingModuleConfig());

        CreditCard c1 = new CreditCard();
        c1.setRslProductCode("vi");
        c1.setAccountStatus("active");
        when(customerExpServiceClient.getCreditCards(any(), any()))
                .thenReturn(LoanServiceUtils.mockOneAppCreditCardResponse(c1));

        ProductDetailRequest request = new ProductDetailRequest();
        request.setProductCode(requestProductCode);
        ProductDetailResponse productDetailResponse = loanService.fetchProductOrientation(crmId, request);
        Assertions.assertTrue(productDetailResponse.isAlreadyHasProduct());
        Assertions.assertEquals(LoanType.CREDIT_CARD, productDetailResponse.getLoanType());
        verifyProductDetailInfo(requestProductCode, productDetailResponse);
    }

    @Test
    void fetchProductOrientation_CreditCard_ContainFlexiOnly_FlexiOnlyShouldBeTrue()
            throws TMBCommonException, ServiceException, RemoteException {
        String requestProductCode = "vi";
        String crmId = "123";

        when(commonServiceFeignClient.getCommonConfig(any(), any()))
                .thenReturn(LoanServiceUtils.moduleLendingModuleConfig());

        CreditCard c1 = new CreditCard();
        c1.setRslProductCode("vb");
        c1.setAccountStatus("active");
        when(customerExpServiceClient.getCreditCards(any(), any()))
                .thenReturn(LoanServiceUtils.mockOneAppCreditCardResponse(c1));

        InstantCreditCard ic1 = new InstantCreditCard();
        ic1.setProductType("vi");
        when(eligibleProductClient.getEligibleProduct(any()))
                .thenReturn(LoanServiceUtils.mockEligibleProductInstantCreditCard(ic1));

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
        applicant1.setProducts(new Product[] { product1 });
        application1.setApplicants(new Applicant[] { applicant1 });
        application1.setInstantFlag("Y");
        application1.setIsSubmitted("N");
        mockResponseTrackingBody.setApplication(new Application[] { application1 });
        mockResponseTracking.setBody(mockResponseTrackingBody);
        Header header = new Header();
        header.setResponseCode("MSG_000");
        header.setResponseDescriptionEN("Success");

        mockResponseTracking.setHeader(header);

        when(loanStatusTrackingClient.searchAppStatusByID(any())).thenReturn(mockResponseTracking);
        
        ProductDetailRequest request = new ProductDetailRequest();
        request.setProductCode(requestProductCode);
        ProductDetailResponse productDetailResponse = loanService.fetchProductOrientation(crmId, request);
        Assertions.assertFalse(productDetailResponse.isAlreadyHasProduct());
        Assertions.assertTrue(productDetailResponse.isFlexiOnly());
        Assertions.assertEquals(LoanType.CREDIT_CARD, productDetailResponse.getLoanType());
        verifyProductDetailInfo(requestProductCode, productDetailResponse);

    }

    @Test
    void fetchProductOrientation_CreditCard_FlexiLoanFlow_InstantFlag_Is_Y_And_IsSubmitted_Is_N_ShouldReturn_ContinueApply()
            throws ServiceException, RemoteException, TMBCommonException {
        when(commonServiceFeignClient.getCommonConfig(any(), any()))
                .thenReturn(LoanServiceUtils.moduleLendingModuleConfig());

        String requestProductCode = "vj";
        String crmId = "123";
        CreditCard c1 = new CreditCard();
        c1.setRslProductCode("vi");
        c1.setAccountStatus("active");
        when(customerExpServiceClient.getCreditCards(any(), any()))
                .thenReturn(LoanServiceUtils.mockOneAppCreditCardResponse(c1));

        InstantCreditCard ic1 = new InstantCreditCard();
        ic1.setProductType("vj");
        when(eligibleProductClient.getEligibleProduct(any()))
                .thenReturn(LoanServiceUtils.mockEligibleProductInstantCreditCard(ic1));

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
        product1.setProductCode("vj");
        applicant1.setProducts(new Product[] { product1 });
        application1.setApplicants(new Applicant[] { applicant1 });
        application1.setInstantFlag("Y");
        application1.setIsSubmitted("N");
        mockResponseTrackingBody.setApplication(new Application[] { application1 });
        mockResponseTracking.setBody(mockResponseTrackingBody);
        Header header = new Header();
        header.setResponseCode("MSG_000");
        header.setResponseDescriptionEN("Success");

        mockResponseTracking.setHeader(header);

        when(loanStatusTrackingClient.searchAppStatusByID(any())).thenReturn(mockResponseTracking);

        ProductDetailRequest request = new ProductDetailRequest();
        request.setProductCode(requestProductCode);
        ProductDetailResponse productDetailResponse = loanService.fetchProductOrientation(crmId, request);
        Assertions.assertFalse(productDetailResponse.isAlreadyHasProduct());
        Assertions.assertFalse(productDetailResponse.isFlexiOnly());
        Assertions.assertEquals(LoanType.CREDIT_CARD, productDetailResponse.getLoanType());
        Assertions.assertEquals(ProductStatus.CONTINUE_APPLY, productDetailResponse.getStatus());
        verifyProductDetailInfo(requestProductCode, productDetailResponse);
    }

    @Test
    void fetchProductOrientation_CreditCard_FlexiLoanFlow_InstantFlag_Is_Y_And_IsSubmitted_Is_Y_ShouldReturn_CheckStatus()
            throws ServiceException, RemoteException, TMBCommonException {
        when(commonServiceFeignClient.getCommonConfig(any(), any()))
                .thenReturn(LoanServiceUtils.moduleLendingModuleConfig());

        String requestProductCode = "vj";
        String crmId = "123";
        CreditCard c1 = new CreditCard();
        c1.setRslProductCode("vi");
        c1.setAccountStatus("active");
        when(customerExpServiceClient.getCreditCards(any(), any()))
                .thenReturn(LoanServiceUtils.mockOneAppCreditCardResponse(c1));

        InstantCreditCard ic1 = new InstantCreditCard();
        ic1.setProductType("vj");
        when(eligibleProductClient.getEligibleProduct(any()))
                .thenReturn(LoanServiceUtils.mockEligibleProductInstantCreditCard(ic1));

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
        product1.setProductCode("vj");
        applicant1.setProducts(new Product[] { product1 });
        application1.setApplicants(new Applicant[] { applicant1 });
        application1.setInstantFlag("Y");
        application1.setIsSubmitted("Y");
        mockResponseTrackingBody.setApplication(new Application[] { application1 });
        mockResponseTracking.setBody(mockResponseTrackingBody);
        Header header = new Header();
        header.setResponseCode("MSG_000");
        header.setResponseDescriptionEN("Success");
        mockResponseTracking.setHeader(header);
        when(loanStatusTrackingClient.searchAppStatusByID(any())).thenReturn(mockResponseTracking);

        ProductDetailRequest request = new ProductDetailRequest();
        request.setProductCode(requestProductCode);
        ProductDetailResponse productDetailResponse = loanService.fetchProductOrientation(crmId, request);
        Assertions.assertFalse(productDetailResponse.isAlreadyHasProduct());
        Assertions.assertFalse(productDetailResponse.isFlexiOnly());
        Assertions.assertEquals(LoanType.CREDIT_CARD, productDetailResponse.getLoanType());
        Assertions.assertEquals(ProductStatus.CHECK_STATUS, productDetailResponse.getStatus());
        verifyProductDetailInfo(requestProductCode, productDetailResponse);

    }

    @Test
    void fetchProductOrientation_CreditCard_LoanSubmissionFlow_InstantFlag_Is_N_And_IsSubmitted_Is_N_ShouldReturn_ApplyWithProductName()
            throws ServiceException, RemoteException, TMBCommonException {
        when(commonServiceFeignClient.getCommonConfig(any(), any()))
                .thenReturn(LoanServiceUtils.moduleLendingModuleConfig());

        String requestProductCode = "vj";
        String crmId = "123";
        CreditCard c1 = new CreditCard();
        c1.setRslProductCode("vi");
        c1.setAccountStatus("active");
        when(customerExpServiceClient.getCreditCards(any(), any()))
                .thenReturn(LoanServiceUtils.mockOneAppCreditCardResponse(c1));

        InstantCreditCard ic1 = new InstantCreditCard();
        ic1.setProductType("vj");
        when(eligibleProductClient.getEligibleProduct(any()))
                .thenReturn(LoanServiceUtils.mockEligibleProductInstantCreditCard(ic1));

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
        product1.setProductCode("vj");
        applicant1.setProducts(new Product[] { product1 });
        application1.setApplicants(new Applicant[] { applicant1 });
        application1.setInstantFlag("N");
        application1.setIsSubmitted("N");
        mockResponseTrackingBody.setApplication(new Application[] { application1 });
        Header header2 = new Header();
        header2.setResponseCode("MSG_000");
        header2.setResponseDescriptionEN("Success");

        mockResponseTracking.setHeader(header2);
        mockResponseTracking.setBody(mockResponseTrackingBody);

        when(loanStatusTrackingClient.searchAppStatusByID(any())).thenReturn(mockResponseTracking);

        ProductDetailRequest request = new ProductDetailRequest();
        request.setProductCode(requestProductCode);
        ProductDetailResponse productDetailResponse = loanService.fetchProductOrientation(crmId, request);
        Assertions.assertFalse(productDetailResponse.isAlreadyHasProduct());
        Assertions.assertFalse(productDetailResponse.isFlexiOnly());
        Assertions.assertEquals(LoanType.CREDIT_CARD, productDetailResponse.getLoanType());
        Assertions.assertEquals(ProductStatus.APPLY_WITH_PRODUCT_NAME, productDetailResponse.getStatus());
        verifyProductDetailInfo(requestProductCode, productDetailResponse);

    }

    @Test
    void fetchProductOrientation_CreditCard_LoanSubmissionFlow_InstantFlag_Is_N_And_IsSubmitted_Is_N_AppStatus_Is_Other_ShouldReturn_ContinueApplyWithProductName()
            throws ServiceException, RemoteException, TMBCommonException {
        when(commonServiceFeignClient.getCommonConfig(any(), any()))
                .thenReturn(LoanServiceUtils.moduleLendingModuleConfig());

        String requestProductCode = "vj";
        String crmId = "123";
        CreditCard c1 = new CreditCard();
        c1.setRslProductCode("vi");
        c1.setAccountStatus("active");
        when(customerExpServiceClient.getCreditCards(any(), any()))
                .thenReturn(LoanServiceUtils.mockOneAppCreditCardResponse(c1));

        InstantCreditCard ic1 = new InstantCreditCard();
        ic1.setProductType("vi");
        when(eligibleProductClient.getEligibleProduct(any()))
                .thenReturn(LoanServiceUtils.mockEligibleProductInstantCreditCard(ic1));

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
        applicant1.setProducts(new Product[] { product1 });
        application1.setApplicants(new Applicant[] { applicant1 });
        application1.setInstantFlag("N");
        application1.setIsSubmitted("N");
        mockResponseTrackingBody.setApplication(new Application[] { application1 });
        mockResponseTracking.setBody(mockResponseTrackingBody);
        Header header = new Header();
        header.setResponseCode("MSG_000");
        header.setResponseDescriptionEN("Success");
        mockResponseTracking.setHeader(header);

        when(loanStatusTrackingClient.searchAppStatusByID(any())).thenReturn(mockResponseTracking);

        ProductDetailRequest request = new ProductDetailRequest();
        request.setProductCode(requestProductCode);
        ProductDetailResponse productDetailResponse = loanService.fetchProductOrientation(crmId, request);
        Assertions.assertFalse(productDetailResponse.isAlreadyHasProduct());
        Assertions.assertFalse(productDetailResponse.isFlexiOnly());
        Assertions.assertEquals(LoanType.CREDIT_CARD, productDetailResponse.getLoanType());
        Assertions.assertEquals(ProductStatus.APPLY_WITH_PRODUCT_NAME, productDetailResponse.getStatus());
        verifyProductDetailInfo(requestProductCode, productDetailResponse);

    }

    @Test
    void fetchProductOrientation_CreditCard_LoanSubmissionFlow_InstantFlagIs_N_And_IsSubmitted_Is_N_AppStatus_Is_Draft_ShouldReturn_ContinueApply()
            throws ServiceException, RemoteException, TMBCommonException, JsonProcessingException {

        when(commonServiceFeignClient.getCommonConfig(any(), any()))
                .thenReturn(LoanServiceUtils.moduleLendingModuleConfig());

        String requestProductCode = "vj";
        String crmId = "123";
        CreditCard c1 = new CreditCard();
        c1.setRslProductCode("vi");
        c1.setAccountStatus("active");
        when(customerExpServiceClient.getCreditCards(any(), any()))
                .thenReturn(LoanServiceUtils.mockOneAppCreditCardResponse(c1));

        InstantCreditCard ic1 = new InstantCreditCard();
        ic1.setProductType("vi");
        when(eligibleProductClient.getEligibleProduct(any()))
                .thenReturn(LoanServiceUtils.mockEligibleProductInstantCreditCard(ic1));

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
        product1.setProductCode("vj");
        applicant1.setProducts(new Product[] { product1 });
        application1.setApplicants(new Applicant[] { applicant1 });
        application1.setInstantFlag("N");
        application1.setIsSubmitted("N");
        application1.setAppStatus("Draft");
        application1.setCaId(123);
        mockResponseTrackingBody.setApplication(new Application[] { application1 });
        mockResponseTracking.setBody(mockResponseTrackingBody);
        Header header = new Header();
        header.setResponseCode("MSG_000");
        header.setResponseDescriptionEN("Success");

        mockResponseTracking.setHeader(header);
        when(loanStatusTrackingClient.searchAppStatusByID(any())).thenReturn(mockResponseTracking);

        ResponseApplication mockAppInfoResponse = new ResponseApplication();
        com.tmb.common.model.legacy.rsl.ws.application.response.Header header2 = new com.tmb.common.model.legacy.rsl.ws.application.response.Header();
        header2.setResponseCode("MSG_000");
        header2.setResponseDescriptionEN("Success");

        mockAppInfoResponse.setHeader(header2);
        com.tmb.common.model.legacy.rsl.ws.application.response.Body body = new com.tmb.common.model.legacy.rsl.ws.application.response.Body();
        body.setNcbConsentFlag("");
        mockAppInfoResponse.setBody(body);
        when(loanSubmissionGetApplicationInfoClient.searchApplicationInfoByCaID(123)).thenReturn(mockAppInfoResponse);

        ResponseIndividual mockCustomerInfoResponse = new ResponseIndividual();
        com.tmb.common.model.legacy.rsl.ws.individual.response.Body customerBody = new com.tmb.common.model.legacy.rsl.ws.individual.response.Body();
        Individual individual = new Individual();
        customerBody.setIndividuals(new Individual[] { individual });
        mockCustomerInfoResponse.setBody(customerBody);

        com.tmb.common.model.legacy.rsl.ws.individual.response.Header header3 = new com.tmb.common.model.legacy.rsl.ws.individual.response.Header();
        header3.setResponseCode("MSG_000");
        header3.setResponseDescriptionEN("Success");

        mockCustomerInfoResponse.setHeader(header3);

        when(loanSubmissionGetCustomerInfoClient.searchCustomerInfoByCaID(123)).thenReturn(mockCustomerInfoResponse);

        ProductDetailRequest request = new ProductDetailRequest();
        request.setProductCode(requestProductCode);
        ProductDetailResponse productDetailResponse = loanService.fetchProductOrientation(crmId, request);

        Assertions.assertFalse(productDetailResponse.isAlreadyHasProduct());
        Assertions.assertFalse(productDetailResponse.isFlexiOnly());
        Assertions.assertEquals(LoanType.CREDIT_CARD, productDetailResponse.getLoanType());
        Assertions.assertEquals(ProductStatus.CONTINUE_APPLY, productDetailResponse.getStatus());
        verifyProductDetailInfo(requestProductCode, productDetailResponse);

    }

    @Test
    void fetchProductOrientation_CreditCard_LoanSubmissionFlow_InstantFlagIs_N_And_IsSubmitted_Is_N_AppStatus_Is_Other_ShouldReturn_CheckStatus()
            throws ServiceException, RemoteException, TMBCommonException {
        when(commonServiceFeignClient.getCommonConfig(any(), any()))
                .thenReturn(LoanServiceUtils.moduleLendingModuleConfig());

        String requestProductCode = "vj";
        String crmId = "123";
        CreditCard c1 = new CreditCard();
        c1.setRslProductCode("vi");
        c1.setAccountStatus("active");
        when(customerExpServiceClient.getCreditCards(any(), any()))
                .thenReturn(LoanServiceUtils.mockOneAppCreditCardResponse(c1));

        InstantCreditCard ic1 = new InstantCreditCard();
        ic1.setProductType("vi");
        when(eligibleProductClient.getEligibleProduct(any()))
                .thenReturn(LoanServiceUtils.mockEligibleProductInstantCreditCard(ic1));

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
        product1.setProductCode("vj");
        applicant1.setProducts(new Product[] { product1 });
        application1.setApplicants(new Applicant[] { applicant1 });
        application1.setInstantFlag("N");
        application1.setIsSubmitted("N");
        application1.setAppStatus("Other");
        mockResponseTrackingBody.setApplication(new Application[] { application1 });
        Header header2 = new Header();
        header2.setResponseCode("MSG_000");
        header2.setResponseDescriptionEN("Success");

        mockResponseTracking.setHeader(header2);
        mockResponseTracking.setBody(mockResponseTrackingBody);

        when(loanStatusTrackingClient.searchAppStatusByID(any())).thenReturn(mockResponseTracking);

        ProductDetailRequest request = new ProductDetailRequest();
        request.setProductCode(requestProductCode);
        ProductDetailResponse productDetailResponse = loanService.fetchProductOrientation(crmId, request);
        Assertions.assertFalse(productDetailResponse.isAlreadyHasProduct());
        Assertions.assertFalse(productDetailResponse.isFlexiOnly());
        Assertions.assertEquals(LoanType.CREDIT_CARD, productDetailResponse.getLoanType());
        Assertions.assertEquals(ProductStatus.CHECK_STATUS, productDetailResponse.getStatus());
        verifyProductDetailInfo(requestProductCode, productDetailResponse);

    }

    @Test
    void fetchProductOrientation_CreditCard_LoanSubmissionFlow_InstantFlagIs_N_And_IsSubmitted_Is_N_AppStatus_Is_InComplete_ShouldReturn_SeeDocList()
            throws ServiceException, RemoteException, TMBCommonException {
        when(commonServiceFeignClient.getCommonConfig(any(), any()))
                .thenReturn(LoanServiceUtils.moduleLendingModuleConfig());

        String requestProductCode = "vj";
        String crmId = "123";
        CreditCard c1 = new CreditCard();
        c1.setRslProductCode("vi");
        c1.setAccountStatus("active");
        when(customerExpServiceClient.getCreditCards(any(), any()))
                .thenReturn(LoanServiceUtils.mockOneAppCreditCardResponse(c1));

        InstantCreditCard ic1 = new InstantCreditCard();
        ic1.setProductType("vi");
        when(eligibleProductClient.getEligibleProduct(any()))
                .thenReturn(LoanServiceUtils.mockEligibleProductInstantCreditCard(ic1));

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
        product1.setProductCode("vj");
        applicant1.setProducts(new Product[] { product1 });
        application1.setApplicants(new Applicant[] { applicant1 });
        application1.setInstantFlag("N");
        application1.setIsSubmitted("N");
        application1.setAppStatus("IDOFD");
        mockResponseTrackingBody.setApplication(new Application[] { application1 });
        mockResponseTracking.setBody(mockResponseTrackingBody);
        Header header = new Header();
        header.setResponseCode("MSG_000");
        header.setResponseDescriptionEN("Success");
        mockResponseTracking.setHeader(header);
        when(loanStatusTrackingClient.searchAppStatusByID(any())).thenReturn(mockResponseTracking);

        ProductDetailRequest request = new ProductDetailRequest();
        request.setProductCode(requestProductCode);
        ProductDetailResponse productDetailResponse = loanService.fetchProductOrientation(crmId, request);
        Assertions.assertFalse(productDetailResponse.isAlreadyHasProduct());
        Assertions.assertFalse(productDetailResponse.isFlexiOnly());
        Assertions.assertEquals(LoanType.CREDIT_CARD, productDetailResponse.getLoanType());
        Assertions.assertEquals(ProductStatus.SEE_DOC_LIST, productDetailResponse.getStatus());
        verifyProductDetailInfo(requestProductCode, productDetailResponse);

    }

    @Test
    void fetchProductOrientation_PersonalLoan_ProductCode_Is_C2G_ContainInAccountSavingLoan_StatusActive_AND_NO_C2G02_IN_EligibleProduct_ShouldReturn_AlreadyHasProduct()
            throws TMBCommonException, ServiceException, RemoteException {
        when(commonServiceFeignClient.getCommonConfig(any(), any()))
                .thenReturn(LoanServiceUtils.moduleLendingModuleConfig());

        String crmId = "123";
        String requestProductCode = "c2g";
        LoanAccount loanAccountSaving = new LoanAccount();
        loanAccountSaving.setAccountNumber("123");
        loanAccountSaving.setProductCode("0225");

        when(customerExpServiceClient.getAccountList(any(), any()))
                .thenReturn(LoanServiceUtils.mockLoanAccountAccountListResponse(loanAccountSaving));

        LoanAccount loanAccount = new LoanAccount();
        loanAccount.setAccountTypeDescEn("Benefit Loan");
        loanAccount.setAccountNumber("123");
        loanAccount.setProductCode("0225");
        when(customerExpServiceClient.getAccountLoan(any(), any(), any()))
                .thenReturn(LoanServiceUtils.mockAccountLoanResponse(loanAccount));

        when(eligibleProductClient.getEligibleProduct(any()))
                .thenReturn(LoanServiceUtils.mockEligibleProductInstantFacility());

        ProductDetailRequest request = new ProductDetailRequest();
        request.setProductCode(requestProductCode);
        ProductDetailResponse productDetailResponse = loanService.fetchProductOrientation(crmId, request);
        Assertions.assertEquals(LoanType.PERSONAL_LOAN, productDetailResponse.getLoanType());
        Assertions.assertTrue(productDetailResponse.isAlreadyHasProduct());

    }

    @Test
    void fetchProductOrientation_PersonalLoan_ProductCode_Is_C2G_ContainInAccountSavingLoan_StatusActive_AND_C2G02_IN_EligibleProduct_StatusTracking_InstantFlag_Is_Y_IsSubmitted_Is_N_ShouldReturn_ContinueApply()
            throws TMBCommonException, ServiceException, RemoteException {
        when(commonServiceFeignClient.getCommonConfig(any(), any()))
                .thenReturn(LoanServiceUtils.moduleLendingModuleConfig());

        String crmId = "123";
        String requestProductCode = "c2g";
        LoanAccount loanAccountSaving = new LoanAccount();
        loanAccountSaving.setAccountNumber("123");
        loanAccountSaving.setProductCode("0225");

        when(customerExpServiceClient.getAccountList(any(), any()))
                .thenReturn(LoanServiceUtils.mockLoanAccountAccountListResponse(loanAccountSaving));

        LoanAccount loanAccount = new LoanAccount();
        loanAccount.setAccountTypeDescEn("Benefit Loan");
        loanAccount.setAccountNumber("123");
        loanAccount.setProductCode("0225");
        when(customerExpServiceClient.getAccountLoan(any(), any(), any()))
                .thenReturn(LoanServiceUtils.mockAccountLoanResponse(loanAccount));

        InstantFacility f = new InstantFacility();
        f.setProductCode("c2g02");
        f.setFacilityCode("c2g");
        when(eligibleProductClient.getEligibleProduct(any()))
                .thenReturn(LoanServiceUtils.mockEligibleProductInstantFacility(f));

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
        product1.setProductCode("c2g");
        applicant1.setProducts(new Product[] { product1 });
        application1.setApplicants(new Applicant[] { applicant1 });
        application1.setInstantFlag("Y");
        application1.setIsSubmitted("N");
        application1.setCaId(123);
        mockResponseTrackingBody.setApplication(new Application[] { application1 });
        mockResponseTracking.setBody(mockResponseTrackingBody);
        Header header2 = new Header();
        header2.setResponseCode("MSG_000");
        header2.setResponseDescriptionEN("Success");
        mockResponseTracking.setHeader(header2);
        when(loanStatusTrackingClient.searchAppStatusByID(any())).thenReturn(mockResponseTracking);

        ProductDetailRequest request = new ProductDetailRequest();
        request.setProductCode(requestProductCode);
        ProductDetailResponse productDetailResponse = loanService.fetchProductOrientation(crmId, request);
        Assertions.assertEquals(LoanType.PERSONAL_LOAN, productDetailResponse.getLoanType());
        Assertions.assertFalse(productDetailResponse.isAlreadyHasProduct());
        Assertions.assertEquals(ProductStatus.CONTINUE_APPLY, productDetailResponse.getStatus());

    }

    @Test
    void fetchProductOrientation_PersonalLoan_ProductCode_Is_C2G_ContainInAccountSavingLoan_StatusActive_AND_C2G02_IN_EligibleProduct_StatusTracking_InstantFlag_Is_Y_IsSubmitted_Is_Y_ShouldReturn_CheckStatus()
            throws TMBCommonException, ServiceException, RemoteException {
        when(commonServiceFeignClient.getCommonConfig(any(), any()))
                .thenReturn(LoanServiceUtils.moduleLendingModuleConfig());

        String crmId = "123";
        String requestProductCode = "c2g";
        LoanAccount loanAccountSaving = new LoanAccount();
        loanAccountSaving.setAccountNumber("123");
        loanAccountSaving.setProductCode("0225");

        when(customerExpServiceClient.getAccountList(any(), any()))
                .thenReturn(LoanServiceUtils.mockLoanAccountAccountListResponse(loanAccountSaving));

        LoanAccount loanAccount = new LoanAccount();
        loanAccount.setAccountTypeDescEn("Benefit Loan");
        loanAccount.setAccountNumber("123");
        loanAccount.setProductCode("0225");
        when(customerExpServiceClient.getAccountLoan(any(), any(), any()))
                .thenReturn(LoanServiceUtils.mockAccountLoanResponse(loanAccount));

        InstantFacility f = new InstantFacility();
        f.setProductCode("c2g02");
        f.setFacilityCode("c2g");
        when(eligibleProductClient.getEligibleProduct(any()))
                .thenReturn(LoanServiceUtils.mockEligibleProductInstantFacility(f));

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
        product1.setProductCode("c2g");
        applicant1.setProducts(new Product[] { product1 });
        application1.setApplicants(new Applicant[] { applicant1 });
        application1.setInstantFlag("Y");
        application1.setIsSubmitted("Y");
        mockResponseTrackingBody.setApplication(new Application[] { application1 });
        mockResponseTracking.setBody(mockResponseTrackingBody);
        Header header2 = new Header();
        header2.setResponseCode("MSG_000");
        header2.setResponseDescriptionEN("Success");
        mockResponseTracking.setHeader(header2);
        when(loanStatusTrackingClient.searchAppStatusByID(any())).thenReturn(mockResponseTracking);

        ProductDetailRequest request = new ProductDetailRequest();
        request.setProductCode(requestProductCode);
        ProductDetailResponse productDetailResponse = loanService.fetchProductOrientation(crmId, request);
        Assertions.assertEquals(LoanType.PERSONAL_LOAN, productDetailResponse.getLoanType());
        Assertions.assertFalse(productDetailResponse.isAlreadyHasProduct());
        Assertions.assertEquals(ProductStatus.CHECK_STATUS, productDetailResponse.getStatus());

    }

    @Test
    void fetchProductOrientation_PersonalLoan_ProductCode_Is_C2G_ContainInAccountSavingLoan_StatusActive_AND_C2G02_IN_EligibleProduct_StatusTracking_InstantFlag_Is_Y_IsSubmitted_Is_Y_ShouldReturn_ApplyWithProductName()
            throws TMBCommonException, ServiceException, RemoteException {
        when(commonServiceFeignClient.getCommonConfig(any(), any()))
                .thenReturn(LoanServiceUtils.moduleLendingModuleConfig());

        String crmId = "123";
        String requestProductCode = "c2g";
        LoanAccount loanAccountSaving = new LoanAccount();
        loanAccountSaving.setAccountNumber("123");
        loanAccountSaving.setProductCode("0225");

        when(customerExpServiceClient.getAccountList(any(), any()))
                .thenReturn(LoanServiceUtils.mockLoanAccountAccountListResponse(loanAccountSaving));

        LoanAccount loanAccount = new LoanAccount();
        loanAccount.setAccountTypeDescEn("Benefit Loan");
        loanAccount.setAccountNumber("123");
        loanAccount.setProductCode("0225");
        when(customerExpServiceClient.getAccountLoan(any(), any(), any()))
                .thenReturn(LoanServiceUtils.mockAccountLoanResponse(loanAccount));

        InstantFacility f = new InstantFacility();
        f.setProductCode("c2g02");
        f.setFacilityCode("c2g");
        when(eligibleProductClient.getEligibleProduct(any()))
                .thenReturn(LoanServiceUtils.mockEligibleProductInstantFacility(f));

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
        product1.setProductCode("rc");
        applicant1.setProducts(new Product[] { product1 });
        application1.setApplicants(new Applicant[] { applicant1 });
        application1.setInstantFlag("Y");
        application1.setIsSubmitted("Y");
        mockResponseTrackingBody.setApplication(new Application[] { application1 });
        mockResponseTracking.setBody(mockResponseTrackingBody);
        Header header2 = new Header();
        header2.setResponseCode("MSG_000");
        header2.setResponseDescriptionEN("Success");
        mockResponseTracking.setHeader(header2);
        when(loanStatusTrackingClient.searchAppStatusByID(any())).thenReturn(mockResponseTracking);

        ProductDetailRequest request = new ProductDetailRequest();
        request.setProductCode(requestProductCode);
        ProductDetailResponse productDetailResponse = loanService.fetchProductOrientation(crmId, request);
        Assertions.assertEquals(LoanType.PERSONAL_LOAN, productDetailResponse.getLoanType());
        Assertions.assertFalse(productDetailResponse.isAlreadyHasProduct());
        Assertions.assertEquals(ProductStatus.APPLY_WITH_PRODUCT_NAME, productDetailResponse.getStatus());

    }

    @Test
    void fetchProductOrientation_PersonalLoan_ProductCode_Is_C2G_NotContainInAccountSavingLoan_ContainInEligibleProduct_No_StatusTracking_ShouldReturn_ApplyWithProductName()
            throws TMBCommonException, ServiceException, RemoteException {
        when(commonServiceFeignClient.getCommonConfig(any(), any()))
                .thenReturn(LoanServiceUtils.moduleLendingModuleConfig());

        String crmId = "123";
        String requestProductCode = "c2g01";
        LoanAccount loanAccountSaving = new LoanAccount();
        loanAccountSaving.setAccountNumber("123");
        loanAccountSaving.setProductCode("0225");

        when(customerExpServiceClient.getAccountList(any(), any()))
                .thenReturn(LoanServiceUtils.mockLoanAccountAccountListResponse(loanAccountSaving));

        LoanAccount loanAccount = new LoanAccount();
        loanAccount.setAccountTypeDescEn("Personal Loan");
        loanAccount.setAccountNumber("123");
        loanAccount.setProductCode("0225");
        when(customerExpServiceClient.getAccountLoan(any(), any(), any()))
                .thenReturn(LoanServiceUtils.mockAccountLoanResponse(loanAccount));

        InstantFacility f = new InstantFacility();
        f.setProductCode("c2g01");
        f.setFacilityCode("c2g");
        when(eligibleProductClient.getEligibleProduct(any()))
                .thenReturn(LoanServiceUtils.mockEligibleProductInstantFacility(f));

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
        product1.setProductCode("rc01");
        applicant1.setProducts(new Product[] { product1 });
        application1.setApplicants(new Applicant[] { applicant1 });
        application1.setInstantFlag("Y");
        application1.setIsSubmitted("Y");
        mockResponseTrackingBody.setApplication(new Application[] { application1 });
        mockResponseTracking.setBody(mockResponseTrackingBody);
        Header header = new Header();
        header.setResponseCode("MSG_000");
        header.setResponseDescriptionEN("Success");

        mockResponseTracking.setHeader(header);

        when(loanStatusTrackingClient.searchAppStatusByID(any())).thenReturn(mockResponseTracking);

        ProductDetailRequest request = new ProductDetailRequest();
        request.setProductCode(requestProductCode);
        ProductDetailResponse productDetailResponse = loanService.fetchProductOrientation(crmId, request);
        Assertions.assertEquals(LoanType.PERSONAL_LOAN, productDetailResponse.getLoanType());
        Assertions.assertFalse(productDetailResponse.isAlreadyHasProduct());
        Assertions.assertEquals(ProductStatus.APPLY_WITH_PRODUCT_NAME, productDetailResponse.getStatus());

    }

    @Test
    void fetchProductOrientation_PersonalLoan_ProductCode_Is_C2G_NotContainInAccountSavingLoan_NotContainInEligibleProduct_No_StatusTracking_ShouldReturn_ApplyWithProductName()
            throws TMBCommonException, ServiceException, RemoteException {
        when(commonServiceFeignClient.getCommonConfig(any(), any()))
                .thenReturn(LoanServiceUtils.moduleLendingModuleConfig());

        String crmId = "123";
        String requestProductCode = "c2g02";
        LoanAccount loanAccountSaving = new LoanAccount();
        loanAccountSaving.setAccountNumber("123");
        loanAccountSaving.setProductCode("0225");

        when(customerExpServiceClient.getAccountList(any(), any()))
                .thenReturn(LoanServiceUtils.mockLoanAccountAccountListResponse(loanAccountSaving));

        LoanAccount loanAccount = new LoanAccount();
        loanAccount.setAccountTypeDescEn("Personal Loan");
        loanAccount.setAccountNumber("123");
        loanAccount.setProductCode("0225");
        when(customerExpServiceClient.getAccountLoan(any(), any(), any()))
                .thenReturn(LoanServiceUtils.mockAccountLoanResponse(loanAccount));

        InstantFacility f = new InstantFacility();
        f.setProductCode("c2g02");
        when(eligibleProductClient.getEligibleProduct(any()))
                .thenReturn(LoanServiceUtils.mockEligibleProductInstantFacility(f));

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
        product1.setProductCode("rc01");
        applicant1.setProducts(new Product[] { product1 });
        application1.setApplicants(new Applicant[] { applicant1 });
        application1.setInstantFlag("Y");
        application1.setIsSubmitted("Y");
        mockResponseTrackingBody.setApplication(new Application[] { application1 });
        mockResponseTracking.setBody(mockResponseTrackingBody);
        Header header = new Header();
        header.setResponseCode("MSG_000");
        header.setResponseDescriptionEN("Success");
        mockResponseTracking.setHeader(header);
        when(loanStatusTrackingClient.searchAppStatusByID(any())).thenReturn(mockResponseTracking);

        ProductDetailRequest request = new ProductDetailRequest();
        request.setProductCode(requestProductCode);
        ProductDetailResponse productDetailResponse = loanService.fetchProductOrientation(crmId, request);
        Assertions.assertEquals(LoanType.PERSONAL_LOAN, productDetailResponse.getLoanType());
        Assertions.assertFalse(productDetailResponse.isAlreadyHasProduct());
        Assertions.assertEquals(ProductStatus.APPLY_WITH_PRODUCT_NAME, productDetailResponse.getStatus());

    }

    @Test
    void fetchProductOrientation_PersonalLoan_ProductCode_Is_RC_ContainInAccountCreditCard_ShouldReturn_AlreadyHasProduct()
            throws TMBCommonException, ServiceException, RemoteException  {
        when(commonServiceFeignClient.getCommonConfig(any(), any()))
                .thenReturn(LoanServiceUtils.moduleLendingModuleConfig());

        String crmId = "123";
        String requestProductCode = "rc01";
        CreditCard c1 = new CreditCard();
        c1.setRslProductCode("rc01");
        c1.setAccountStatus("active");
        when(customerExpServiceClient.getCreditCards(any(), any()))
                .thenReturn(LoanServiceUtils.mockOneAppCreditCardResponse(c1));
        InstantFacility f = new InstantFacility();
        f.setProductCode("rc01");
        when(eligibleProductClient.getEligibleProduct(any()))
                .thenReturn(LoanServiceUtils.mockEligibleProductInstantFacility(f));
        ProductDetailRequest request = new ProductDetailRequest();
        request.setProductCode(requestProductCode);
        ProductDetailResponse productDetailResponse = loanService.fetchProductOrientation(crmId, request);
        Assertions.assertEquals(LoanType.PERSONAL_LOAN, productDetailResponse.getLoanType());
        Assertions.assertTrue(productDetailResponse.isAlreadyHasProduct());

    }

    @Test
    void fetchProductOrientation_PersonalLoan_ProductCode_Is_RC_NotContainInAccountCreditCard_ContainEligibleProduct_NoStatusTracking_ShouldReturn_ApplyWithProductName()
            throws TMBCommonException, ServiceException, RemoteException {
        when(commonServiceFeignClient.getCommonConfig(any(), any()))
                .thenReturn(LoanServiceUtils.moduleLendingModuleConfig());

        String crmId = "123";
        String requestProductCode = "rc01";
        CreditCard c1 = new CreditCard();
        c1.setRslProductCode("rc01");
        c1.setAccountStatus("inactive");
        when(customerExpServiceClient.getCreditCards(any(), any()))
                .thenReturn(LoanServiceUtils.mockOneAppCreditCardResponse(c1));

        InstantFacility f = new InstantFacility();
        f.setProductCode("rc01");
        f.setFacilityCode("rc");
        when(eligibleProductClient.getEligibleProduct(any()))
                .thenReturn(LoanServiceUtils.mockEligibleProductInstantFacility(f));

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
        product1.setProductCode("cg201");
        applicant1.setProducts(new Product[] { product1 });
        application1.setApplicants(new Applicant[] { applicant1 });
        application1.setInstantFlag("Y");
        application1.setIsSubmitted("Y");
        mockResponseTrackingBody.setApplication(new Application[] { application1 });
        mockResponseTracking.setBody(mockResponseTrackingBody);
        Header header2 = new Header();
        header2.setResponseCode("MSG_000");
        header2.setResponseDescriptionEN("Success");
        mockResponseTracking.setHeader(header2);
        when(loanStatusTrackingClient.searchAppStatusByID(any())).thenReturn(mockResponseTracking);

        ProductDetailRequest request = new ProductDetailRequest();
        request.setProductCode(requestProductCode);
        ProductDetailResponse productDetailResponse = loanService.fetchProductOrientation(crmId, request);
        Assertions.assertEquals(LoanType.PERSONAL_LOAN, productDetailResponse.getLoanType());
        Assertions.assertFalse(productDetailResponse.isAlreadyHasProduct());

        Assertions.assertEquals(ProductStatus.APPLY_WITH_PRODUCT_NAME, productDetailResponse.getStatus());

    }

    @Test
    void fetchProductOrientation_PersonalLoan_ProductCode_Is_RC_NotContainInAccountCreditCard_NotContainEligibleProduct_NoStatusTracking_ShouldReturn_ApplyWithProductName()
            throws TMBCommonException, ServiceException, RemoteException {
        when(commonServiceFeignClient.getCommonConfig(any(), any()))
                .thenReturn(LoanServiceUtils.moduleLendingModuleConfig());

        String crmId = "123";
        String requestProductCode = "rc01";
        CreditCard c1 = new CreditCard();
        c1.setRslProductCode("rc01");
        c1.setAccountStatus("inactive");
        when(customerExpServiceClient.getCreditCards(any(), any()))
                .thenReturn(LoanServiceUtils.mockOneAppCreditCardResponse(c1));

        InstantFacility f = new InstantFacility();
        f.setProductCode("cg201");
        f.setFacilityCode("c2g");
        when(eligibleProductClient.getEligibleProduct(any()))
                .thenReturn(LoanServiceUtils.mockEligibleProductInstantFacility(f));

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
        product1.setProductCode("cg201");
        applicant1.setProducts(new Product[] { product1 });
        application1.setApplicants(new Applicant[] { applicant1 });
        application1.setInstantFlag("Y");
        application1.setIsSubmitted("Y");
        mockResponseTrackingBody.setApplication(new Application[] { application1 });
        mockResponseTracking.setBody(mockResponseTrackingBody);
        Header header = new Header();
        header.setResponseCode("MSG_000");
        header.setResponseDescriptionEN("Success");
        mockResponseTracking.setHeader(header);
        when(loanStatusTrackingClient.searchAppStatusByID(any())).thenReturn(mockResponseTracking);

        ProductDetailRequest request = new ProductDetailRequest();
        request.setProductCode(requestProductCode);
        ProductDetailResponse productDetailResponse = loanService.fetchProductOrientation(crmId, request);
        Assertions.assertEquals(LoanType.PERSONAL_LOAN, productDetailResponse.getLoanType());
        Assertions.assertFalse(productDetailResponse.isAlreadyHasProduct());

        Assertions.assertEquals(ProductStatus.APPLY_WITH_PRODUCT_NAME, productDetailResponse.getStatus());

    }

    @Test
    void fetchProductOrientation_CreditCard_FlexiLoanFlow__ContinueApply_InstantAppliedStepFlag_Is_1_NextScreen_ShouldBe_CashTransferDay1()
            throws ServiceException, RemoteException, TMBCommonException {
        when(commonServiceFeignClient.getCommonConfig(any(), any()))
                .thenReturn(LoanServiceUtils.moduleLendingModuleConfig());

        String requestProductCode = "vj";
        String crmId = "123";
        CreditCard c1 = new CreditCard();
        c1.setRslProductCode("vi");
        c1.setAccountStatus("active");
        when(customerExpServiceClient.getCreditCards(any(), any()))
                .thenReturn(LoanServiceUtils.mockOneAppCreditCardResponse(c1));

        InstantCreditCard ic1 = new InstantCreditCard();
        ic1.setProductType("vj");
        when(eligibleProductClient.getEligibleProduct(any()))
                .thenReturn(LoanServiceUtils.mockEligibleProductInstantCreditCard(ic1));

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
        product1.setProductCode("vj");
        applicant1.setProducts(new Product[] { product1 });
        application1.setApplicants(new Applicant[] { applicant1 });
        application1.setInstantFlag("Y");
        application1.setIsSubmitted("N");
        application1.setInstantAppliedStepFlag("1");
        mockResponseTrackingBody.setApplication(new Application[] { application1 });
        mockResponseTracking.setBody(mockResponseTrackingBody);
        Header header2 = new Header();
        header2.setResponseCode("MSG_000");
        header2.setResponseDescriptionEN("Success");
        mockResponseTracking.setHeader(header2);
        when(loanStatusTrackingClient.searchAppStatusByID(any())).thenReturn(mockResponseTracking);

        ProductDetailRequest request = new ProductDetailRequest();
        request.setProductCode(requestProductCode);
        ProductDetailResponse productDetailResponse = loanService.fetchProductOrientation(crmId, request);
        Assertions.assertFalse(productDetailResponse.isAlreadyHasProduct());
        Assertions.assertFalse(productDetailResponse.isFlexiOnly());
        Assertions.assertEquals(LoanType.CREDIT_CARD, productDetailResponse.getLoanType());
        Assertions.assertEquals(ProductStatus.CONTINUE_APPLY, productDetailResponse.getStatus());
        Assertions.assertEquals(ContinueApplyNextScreen.CASH_TRANSFER_DAY1,
                productDetailResponse.getContinueApplyNextStep());
        verifyProductDetailInfo(requestProductCode, productDetailResponse);
    }

    @Test
    void fetchProductOrientation_CreditCard_FlexiLoanFlow__ContinueApply_InstantAppliedStepFlag_Is_3_NextScreen_ShouldBe_FinalApprove()
            throws ServiceException, RemoteException, TMBCommonException {
        when(commonServiceFeignClient.getCommonConfig(any(), any()))
                .thenReturn(LoanServiceUtils.moduleLendingModuleConfig());

        String requestProductCode = "vj";
        String crmId = "123";
        CreditCard c1 = new CreditCard();
        c1.setRslProductCode("vi");
        c1.setAccountStatus("active");
        when(customerExpServiceClient.getCreditCards(any(), any()))
                .thenReturn(LoanServiceUtils.mockOneAppCreditCardResponse(c1));

        InstantCreditCard ic1 = new InstantCreditCard();
        ic1.setProductType("vj");
        when(eligibleProductClient.getEligibleProduct(any()))
                .thenReturn(LoanServiceUtils.mockEligibleProductInstantCreditCard(ic1));

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
        product1.setProductCode("vj");
        applicant1.setProducts(new Product[] { product1 });
        application1.setApplicants(new Applicant[] { applicant1 });
        application1.setInstantFlag("Y");
        application1.setIsSubmitted("N");
        application1.setInstantAppliedStepFlag("3");
        mockResponseTrackingBody.setApplication(new Application[] { application1 });
        mockResponseTracking.setBody(mockResponseTrackingBody);
        Header header2 = new Header();
        header2.setResponseCode("MSG_000");
        header2.setResponseDescriptionEN("Success");
        mockResponseTracking.setHeader(header2);
        when(loanStatusTrackingClient.searchAppStatusByID(any())).thenReturn(mockResponseTracking);

        ProductDetailRequest request = new ProductDetailRequest();
        request.setProductCode(requestProductCode);
        ProductDetailResponse productDetailResponse = loanService.fetchProductOrientation(crmId, request);
        Assertions.assertFalse(productDetailResponse.isAlreadyHasProduct());
        Assertions.assertFalse(productDetailResponse.isFlexiOnly());
        Assertions.assertEquals(LoanType.CREDIT_CARD, productDetailResponse.getLoanType());
        Assertions.assertEquals(ProductStatus.CONTINUE_APPLY, productDetailResponse.getStatus());
        Assertions.assertEquals(ContinueApplyNextScreen.FINAL_APPROVE_LOAN_CONFIRMATION,
                productDetailResponse.getContinueApplyNextStep());
        verifyProductDetailInfo(requestProductCode, productDetailResponse);
    }

    @Test
    void fetchProductOrientation_CreditCard_LoanSubmissionFlow_ContinueApply_ncbConsentFlag_Is_Blank_ShouldReturn_Upload_doc()
            throws ServiceException, RemoteException, TMBCommonException, JsonProcessingException {

        when(commonServiceFeignClient.getCommonConfig(any(), any()))
                .thenReturn(LoanServiceUtils.moduleLendingModuleConfig());

        String requestProductCode = "vj";
        String crmId = "123";
        CreditCard c1 = new CreditCard();
        c1.setRslProductCode("vi");
        c1.setAccountStatus("active");
        when(customerExpServiceClient.getCreditCards(any(), any()))
                .thenReturn(LoanServiceUtils.mockOneAppCreditCardResponse(c1));

        InstantCreditCard ic1 = new InstantCreditCard();
        ic1.setProductType("vi");
        when(eligibleProductClient.getEligibleProduct(any()))
                .thenReturn(LoanServiceUtils.mockEligibleProductInstantCreditCard(ic1));

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
        product1.setProductCode("vj");
        applicant1.setProducts(new Product[] { product1 });
        application1.setApplicants(new Applicant[] { applicant1 });
        application1.setInstantFlag("N");
        application1.setIsSubmitted("N");
        application1.setAppStatus("Draft");
        application1.setCaId(123);
        mockResponseTrackingBody.setApplication(new Application[] { application1 });
        mockResponseTracking.setBody(mockResponseTrackingBody);
        Header header2 = new Header();
        header2.setResponseCode("MSG_000");
        header2.setResponseDescriptionEN("Success");
        mockResponseTracking.setHeader(header2);
        when(loanStatusTrackingClient.searchAppStatusByID(any())).thenReturn(mockResponseTracking);

        ResponseApplication mockAppInfoResponse = new ResponseApplication();
        com.tmb.common.model.legacy.rsl.ws.application.response.Header header = new com.tmb.common.model.legacy.rsl.ws.application.response.Header();
        header.setResponseCode("MSG_000");
        header.setResponseDescriptionEN("Success");
        mockAppInfoResponse.setHeader(header);
        com.tmb.common.model.legacy.rsl.ws.application.response.Body body = new com.tmb.common.model.legacy.rsl.ws.application.response.Body();
        body.setNcbConsentFlag("");
        mockAppInfoResponse.setBody(body);
        when(loanSubmissionGetApplicationInfoClient.searchApplicationInfoByCaID(123)).thenReturn(mockAppInfoResponse);

        ResponseIndividual mockCustomerInfoResponse = new ResponseIndividual();
        com.tmb.common.model.legacy.rsl.ws.individual.response.Body customerBody = new com.tmb.common.model.legacy.rsl.ws.individual.response.Body();
        Individual individual = new Individual();
        customerBody.setIndividuals(new Individual[] { individual });
        mockCustomerInfoResponse.setBody(customerBody);
        com.tmb.common.model.legacy.rsl.ws.individual.response.Header header3 = new com.tmb.common.model.legacy.rsl.ws.individual.response.Header();
        header3.setResponseCode("MSG_000");
        header3.setResponseDescriptionEN("Success");
        mockCustomerInfoResponse.setHeader(header3);

        when(loanSubmissionGetCustomerInfoClient.searchCustomerInfoByCaID(123)).thenReturn(mockCustomerInfoResponse);

        ProductDetailRequest request = new ProductDetailRequest();
        request.setProductCode(requestProductCode);
        ProductDetailResponse productDetailResponse = loanService.fetchProductOrientation(crmId, request);

        Assertions.assertFalse(productDetailResponse.isAlreadyHasProduct());
        Assertions.assertFalse(productDetailResponse.isFlexiOnly());
        Assertions.assertEquals(LoanType.CREDIT_CARD, productDetailResponse.getLoanType());
        Assertions.assertEquals(ProductStatus.CONTINUE_APPLY, productDetailResponse.getStatus());
        Assertions.assertEquals(ContinueApplyNextScreen.UPLOAD_DOC, productDetailResponse.getContinueApplyNextStep());
        verifyProductDetailInfo(requestProductCode, productDetailResponse);

    }

    @Test
    void fetchProductOrientation_CreditCard_LoanSubmissionFlow_ContinueApply_ncbConsentFlag_Is_Blank_PersonalInfoSavedFlag_Is_N_ShouldReturn_Personal()
            throws ServiceException, RemoteException, TMBCommonException, JsonProcessingException {

        when(commonServiceFeignClient.getCommonConfig(any(), any()))
                .thenReturn(LoanServiceUtils.moduleLendingModuleConfig());

        String requestProductCode = "vj";
        String crmId = "123";
        CreditCard c1 = new CreditCard();
        c1.setRslProductCode("vi");
        c1.setAccountStatus("active");
        when(customerExpServiceClient.getCreditCards(any(), any()))
                .thenReturn(LoanServiceUtils.mockOneAppCreditCardResponse(c1));

        InstantCreditCard ic1 = new InstantCreditCard();
        ic1.setProductType("vi");
        when(eligibleProductClient.getEligibleProduct(any()))
                .thenReturn(LoanServiceUtils.mockEligibleProductInstantCreditCard(ic1));

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
        product1.setProductCode("vj");
        applicant1.setProducts(new Product[] { product1 });
        application1.setApplicants(new Applicant[] { applicant1 });
        application1.setInstantFlag("N");
        application1.setIsSubmitted("N");
        application1.setAppStatus("Draft");
        application1.setCaId(123);
        mockResponseTrackingBody.setApplication(new Application[] { application1 });
        mockResponseTracking.setBody(mockResponseTrackingBody);
        Header header4 = new Header();
        header4.setResponseCode("MSG_000");
        header4.setResponseDescriptionEN("Success");
        mockResponseTracking.setHeader(header4);
        when(loanStatusTrackingClient.searchAppStatusByID(any())).thenReturn(mockResponseTracking);

        ResponseApplication mockAppInfoResponse = new ResponseApplication();
        com.tmb.common.model.legacy.rsl.ws.application.response.Header header = new com.tmb.common.model.legacy.rsl.ws.application.response.Header();
        header.setResponseCode("MSG_000");
        header.setResponseDescriptionEN("Success");
        mockAppInfoResponse.setHeader(header);
        com.tmb.common.model.legacy.rsl.ws.application.response.Body body = new com.tmb.common.model.legacy.rsl.ws.application.response.Body();
        body.setNcbConsentFlag("");
        mockAppInfoResponse.setBody(body);
        when(loanSubmissionGetApplicationInfoClient.searchApplicationInfoByCaID(123)).thenReturn(mockAppInfoResponse);

        ResponseIndividual mockCustomerInfoResponse = new ResponseIndividual();
        com.tmb.common.model.legacy.rsl.ws.individual.response.Body customerBody = new com.tmb.common.model.legacy.rsl.ws.individual.response.Body();
        Individual individual = new Individual();
        individual.setPersonalInfoSavedFlag("N");
        customerBody.setIndividuals(new Individual[] { individual });
        mockCustomerInfoResponse.setBody(customerBody);
        com.tmb.common.model.legacy.rsl.ws.individual.response.Header header2 = new com.tmb.common.model.legacy.rsl.ws.individual.response.Header();
        header2.setResponseCode("MSG_000");
        header2.setResponseDescriptionEN("Success");
        mockCustomerInfoResponse.setHeader(header2);
        when(loanSubmissionGetCustomerInfoClient.searchCustomerInfoByCaID(123)).thenReturn(mockCustomerInfoResponse);

        ProductDetailRequest request = new ProductDetailRequest();
        request.setProductCode(requestProductCode);
        ProductDetailResponse productDetailResponse = loanService.fetchProductOrientation(crmId, request);

        Assertions.assertFalse(productDetailResponse.isAlreadyHasProduct());
        Assertions.assertFalse(productDetailResponse.isFlexiOnly());
        Assertions.assertEquals(LoanType.CREDIT_CARD, productDetailResponse.getLoanType());
        Assertions.assertEquals(ProductStatus.CONTINUE_APPLY, productDetailResponse.getStatus());
        Assertions.assertEquals(ContinueApplyNextScreen.PERSONAL, productDetailResponse.getContinueApplyNextStep());
        verifyProductDetailInfo(requestProductCode, productDetailResponse);

    }

    @Test
    void fetchProductOrientation_CreditCard_LoanSubmissionFlow_ContinueApply_ncbConsentFlag_Is_Blank_EmploymentInfoSavedFlag_Is_N_ShouldReturn_Working()
            throws ServiceException, RemoteException, TMBCommonException, JsonProcessingException {

        when(commonServiceFeignClient.getCommonConfig(any(), any()))
                .thenReturn(LoanServiceUtils.moduleLendingModuleConfig());

        String requestProductCode = "vj";
        String crmId = "123";
        CreditCard c1 = new CreditCard();
        c1.setRslProductCode("vi");
        c1.setAccountStatus("active");
        when(customerExpServiceClient.getCreditCards(any(), any()))
                .thenReturn(LoanServiceUtils.mockOneAppCreditCardResponse(c1));

        InstantCreditCard ic1 = new InstantCreditCard();
        ic1.setProductType("vi");
        when(eligibleProductClient.getEligibleProduct(any()))
                .thenReturn(LoanServiceUtils.mockEligibleProductInstantCreditCard(ic1));

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
        product1.setProductCode("vj");
        applicant1.setProducts(new Product[] { product1 });
        application1.setApplicants(new Applicant[] { applicant1 });
        application1.setInstantFlag("N");
        application1.setIsSubmitted("N");
        application1.setAppStatus("Draft");
        application1.setCaId(123);
        mockResponseTrackingBody.setApplication(new Application[] { application1 });
        mockResponseTracking.setBody(mockResponseTrackingBody);
        Header header = new Header();
        header.setResponseCode("MSG_000");
        header.setResponseDescriptionEN("Success");
        mockResponseTracking.setHeader(header);
        when(loanStatusTrackingClient.searchAppStatusByID(any())).thenReturn(mockResponseTracking);

        ResponseApplication mockAppInfoResponse = new ResponseApplication();
        com.tmb.common.model.legacy.rsl.ws.application.response.Header header2 = new com.tmb.common.model.legacy.rsl.ws.application.response.Header();
        header2.setResponseCode("MSG_000");
        header2.setResponseDescriptionEN("Success");
        mockAppInfoResponse.setHeader(header2);
        com.tmb.common.model.legacy.rsl.ws.application.response.Body body = new com.tmb.common.model.legacy.rsl.ws.application.response.Body();
        body.setNcbConsentFlag("");
        mockAppInfoResponse.setBody(body);

        when(loanSubmissionGetApplicationInfoClient.searchApplicationInfoByCaID(123)).thenReturn(mockAppInfoResponse);

        ResponseIndividual mockCustomerInfoResponse = new ResponseIndividual();
        com.tmb.common.model.legacy.rsl.ws.individual.response.Body customerBody = new com.tmb.common.model.legacy.rsl.ws.individual.response.Body();
        Individual individual = new Individual();
        individual.setPersonalInfoSavedFlag("Y");
        individual.setEmploymentInfoSavedFlag("N");
        customerBody.setIndividuals(new Individual[] { individual });
        mockCustomerInfoResponse.setBody(customerBody);
        com.tmb.common.model.legacy.rsl.ws.individual.response.Header header3 = new com.tmb.common.model.legacy.rsl.ws.individual.response.Header();
        header3.setResponseCode("MSG_000");
        header3.setResponseDescriptionEN("Success");
        mockCustomerInfoResponse.setHeader(header3);
        when(loanSubmissionGetCustomerInfoClient.searchCustomerInfoByCaID(123)).thenReturn(mockCustomerInfoResponse);

        ProductDetailRequest request = new ProductDetailRequest();
        request.setProductCode(requestProductCode);
        ProductDetailResponse productDetailResponse = loanService.fetchProductOrientation(crmId, request);

        Assertions.assertFalse(productDetailResponse.isAlreadyHasProduct());
        Assertions.assertFalse(productDetailResponse.isFlexiOnly());
        Assertions.assertEquals(LoanType.CREDIT_CARD, productDetailResponse.getLoanType());
        Assertions.assertEquals(ProductStatus.CONTINUE_APPLY, productDetailResponse.getStatus());
        Assertions.assertEquals(ContinueApplyNextScreen.WORKING, productDetailResponse.getContinueApplyNextStep());
        verifyProductDetailInfo(requestProductCode, productDetailResponse);

    }

    @Test
    void fetchProductOrientation_CreditCard_LoanSubmissionFlow_ContinueApply_ncbConsentFlag_Is_Blank_IncomeInfoSavedFlag_Is_N_ShouldReturn_InCome()
            throws ServiceException, RemoteException, TMBCommonException, JsonProcessingException {
        when(commonServiceFeignClient.getCommonConfig(any(), any()))
                .thenReturn(LoanServiceUtils.moduleLendingModuleConfig());

        String requestProductCode = "vj";
        String crmId = "123";
        CreditCard c1 = new CreditCard();
        c1.setRslProductCode("vi");
        c1.setAccountStatus("active");
        when(customerExpServiceClient.getCreditCards(any(), any()))
                .thenReturn(LoanServiceUtils.mockOneAppCreditCardResponse(c1));

        InstantCreditCard ic1 = new InstantCreditCard();
        ic1.setProductType("vi");
        when(eligibleProductClient.getEligibleProduct(any()))
                .thenReturn(LoanServiceUtils.mockEligibleProductInstantCreditCard(ic1));

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
        product1.setProductCode("vj");
        applicant1.setProducts(new Product[] { product1 });
        application1.setApplicants(new Applicant[] { applicant1 });
        application1.setInstantFlag("N");
        application1.setIsSubmitted("N");
        application1.setAppStatus("Draft");
        application1.setCaId(123);
        mockResponseTrackingBody.setApplication(new Application[] { application1 });
        mockResponseTracking.setBody(mockResponseTrackingBody);
        Header header = new Header();
        header.setResponseCode("MSG_000");
        header.setResponseDescriptionEN("Success");
        mockResponseTracking.setHeader(header);
        when(loanStatusTrackingClient.searchAppStatusByID(any())).thenReturn(mockResponseTracking);

        ResponseApplication mockAppInfoResponse = new ResponseApplication();
        com.tmb.common.model.legacy.rsl.ws.application.response.Header header3 = new com.tmb.common.model.legacy.rsl.ws.application.response.Header();
        header3.setResponseCode("MSG_000");
        header3.setResponseDescriptionEN("Success");
        mockAppInfoResponse.setHeader(header3);
        com.tmb.common.model.legacy.rsl.ws.application.response.Body body = new com.tmb.common.model.legacy.rsl.ws.application.response.Body();
        body.setNcbConsentFlag("");
        mockAppInfoResponse.setBody(body);
        when(loanSubmissionGetApplicationInfoClient.searchApplicationInfoByCaID(123)).thenReturn(mockAppInfoResponse);

        ResponseIndividual mockCustomerInfoResponse = new ResponseIndividual();
        com.tmb.common.model.legacy.rsl.ws.individual.response.Body customerBody = new com.tmb.common.model.legacy.rsl.ws.individual.response.Body();
        Individual individual = new Individual();
        individual.setPersonalInfoSavedFlag("Y");
        individual.setEmploymentInfoSavedFlag("Y");
        individual.setIncomeInfoSavedFlag("N");
        customerBody.setIndividuals(new Individual[] { individual });
        mockCustomerInfoResponse.setBody(customerBody);
        com.tmb.common.model.legacy.rsl.ws.individual.response.Header header2 = new com.tmb.common.model.legacy.rsl.ws.individual.response.Header();
        header2.setResponseCode("MSG_000");
        header2.setResponseDescriptionEN("Success");
        mockCustomerInfoResponse.setHeader(header2);
        when(loanSubmissionGetCustomerInfoClient.searchCustomerInfoByCaID(123)).thenReturn(mockCustomerInfoResponse);

        ProductDetailRequest request = new ProductDetailRequest();
        request.setProductCode(requestProductCode);
        ProductDetailResponse productDetailResponse = loanService.fetchProductOrientation(crmId, request);

        Assertions.assertFalse(productDetailResponse.isAlreadyHasProduct());
        Assertions.assertFalse(productDetailResponse.isFlexiOnly());
        Assertions.assertEquals(LoanType.CREDIT_CARD, productDetailResponse.getLoanType());
        Assertions.assertEquals(ProductStatus.CONTINUE_APPLY, productDetailResponse.getStatus());
        Assertions.assertEquals(ContinueApplyNextScreen.INCOME, productDetailResponse.getContinueApplyNextStep());
        verifyProductDetailInfo(requestProductCode, productDetailResponse);

    }

    @Test
    void fetchProductOrientation_CreditCard_LoanSubmissionFlow_ContinueApply_ncbConsentFlag_Is_Blank_AllInfoSavedFlag_Is_Y_ShouldReturn_Upload()
            throws ServiceException, RemoteException, TMBCommonException, JsonProcessingException {
        when(commonServiceFeignClient.getCommonConfig(any(), any()))
                .thenReturn(LoanServiceUtils.moduleLendingModuleConfig());

        String requestProductCode = "vj";
        String crmId = "123";
        CreditCard c1 = new CreditCard();
        c1.setRslProductCode("vi");
        c1.setAccountStatus("active");
        when(customerExpServiceClient.getCreditCards(any(), any()))
                .thenReturn(LoanServiceUtils.mockOneAppCreditCardResponse(c1));

        InstantCreditCard ic1 = new InstantCreditCard();
        ic1.setProductType("vi");
        when(eligibleProductClient.getEligibleProduct(any()))
                .thenReturn(LoanServiceUtils.mockEligibleProductInstantCreditCard(ic1));

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
        product1.setProductCode("vj");
        applicant1.setProducts(new Product[] { product1 });
        application1.setApplicants(new Applicant[] { applicant1 });
        application1.setInstantFlag("N");
        application1.setIsSubmitted("N");
        application1.setAppStatus("Draft");
        application1.setCaId(123);
        mockResponseTrackingBody.setApplication(new Application[] { application1 });
        mockResponseTracking.setBody(mockResponseTrackingBody);
        Header header3 = new Header();
        header3.setResponseCode("MSG_000");
        header3.setResponseDescriptionEN("Success");
        mockResponseTracking.setHeader(header3);
        when(loanStatusTrackingClient.searchAppStatusByID(any())).thenReturn(mockResponseTracking);

        ResponseApplication mockAppInfoResponse = new ResponseApplication();
        com.tmb.common.model.legacy.rsl.ws.application.response.Header header = new com.tmb.common.model.legacy.rsl.ws.application.response.Header();
        header.setResponseCode("MSG_000");
        header.setResponseDescriptionEN("Success");

        mockAppInfoResponse.setHeader(header);
        com.tmb.common.model.legacy.rsl.ws.application.response.Body body = new com.tmb.common.model.legacy.rsl.ws.application.response.Body();
        body.setNcbConsentFlag("");
        mockAppInfoResponse.setBody(body);
        when(loanSubmissionGetApplicationInfoClient.searchApplicationInfoByCaID(123)).thenReturn(mockAppInfoResponse);

        ResponseIndividual mockCustomerInfoResponse = new ResponseIndividual();
        com.tmb.common.model.legacy.rsl.ws.individual.response.Body customerBody = new com.tmb.common.model.legacy.rsl.ws.individual.response.Body();
        Individual individual = new Individual();
        individual.setPersonalInfoSavedFlag("Y");
        individual.setEmploymentInfoSavedFlag("Y");
        individual.setIncomeInfoSavedFlag("Y");
        customerBody.setIndividuals(new Individual[] { individual });
        mockCustomerInfoResponse.setBody(customerBody);
        com.tmb.common.model.legacy.rsl.ws.individual.response.Header header2 = new com.tmb.common.model.legacy.rsl.ws.individual.response.Header();
        header2.setResponseCode("MSG_000");
        header2.setResponseDescriptionEN("Success");
        mockCustomerInfoResponse.setHeader(header2);
        when(loanSubmissionGetCustomerInfoClient.searchCustomerInfoByCaID(123)).thenReturn(mockCustomerInfoResponse);

        ProductDetailRequest request = new ProductDetailRequest();
        request.setProductCode(requestProductCode);
        ProductDetailResponse productDetailResponse = loanService.fetchProductOrientation(crmId, request);

        Assertions.assertFalse(productDetailResponse.isAlreadyHasProduct());
        Assertions.assertFalse(productDetailResponse.isFlexiOnly());
        Assertions.assertEquals(LoanType.CREDIT_CARD, productDetailResponse.getLoanType());
        Assertions.assertEquals(ProductStatus.CONTINUE_APPLY, productDetailResponse.getStatus());
        Assertions.assertEquals(ContinueApplyNextScreen.UPLOAD_DOC, productDetailResponse.getContinueApplyNextStep());
        verifyProductDetailInfo(requestProductCode, productDetailResponse);

    }

    @Test
    void fetchProductOrientation_ShouldIgnoreErrorFromOneAppService() throws ServiceException, RemoteException, TMBCommonException, JsonProcessingException {
        when(commonServiceFeignClient.getCommonConfig(any(), any()))
                .thenReturn(LoanServiceUtils.moduleLendingModuleConfig());

        String requestProductCode = "vj";
        String crmId = "123";

        when(customerExpServiceClient.getCreditCards(any(), any()))
                .thenReturn(LoanServiceUtils.mockOneAppCreditCardErrorResponse());

        InstantCreditCard ic1 = new InstantCreditCard();
        ic1.setProductType("vi");
        when(eligibleProductClient.getEligibleProduct(any()))
                .thenReturn(LoanServiceUtils.mockEligibleProductInstantCreditCard(ic1));

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
        product1.setProductCode("vj");
        applicant1.setProducts(new Product[] { product1 });
        application1.setApplicants(new Applicant[] { applicant1 });
        application1.setInstantFlag("N");
        application1.setIsSubmitted("N");
        application1.setAppStatus("Draft");
        application1.setCaId(123);
        mockResponseTrackingBody.setApplication(new Application[] { application1 });
        mockResponseTracking.setBody(mockResponseTrackingBody);
        Header header3 = new Header();
        header3.setResponseCode("MSG_001");
        mockResponseTracking.setHeader(header3);
        when(loanStatusTrackingClient.searchAppStatusByID(any())).thenReturn(mockResponseTracking);

        ResponseApplication mockAppInfoResponse = new ResponseApplication();
        com.tmb.common.model.legacy.rsl.ws.application.response.Header header = new com.tmb.common.model.legacy.rsl.ws.application.response.Header();
        header.setResponseCode("MSG_000");
        header.setResponseDescriptionEN("Success");
        mockAppInfoResponse.setHeader(header);
        com.tmb.common.model.legacy.rsl.ws.application.response.Body body = new com.tmb.common.model.legacy.rsl.ws.application.response.Body();
        body.setNcbConsentFlag("Y");
        mockAppInfoResponse.setBody(body);
        when(loanSubmissionGetApplicationInfoClient.searchApplicationInfoByCaID(123)).thenReturn(mockAppInfoResponse);

        ResponseIndividual mockCustomerInfoResponse = new ResponseIndividual();
        com.tmb.common.model.legacy.rsl.ws.individual.response.Body customerBody = new com.tmb.common.model.legacy.rsl.ws.individual.response.Body();
        Individual individual = new Individual();
        individual.setPersonalInfoSavedFlag("Y");
        individual.setEmploymentInfoSavedFlag("Y");
        individual.setIncomeInfoSavedFlag("Y");
        customerBody.setIndividuals(new Individual[] { individual });
        mockCustomerInfoResponse.setBody(customerBody);
        com.tmb.common.model.legacy.rsl.ws.individual.response.Header header2 = new com.tmb.common.model.legacy.rsl.ws.individual.response.Header();
        header2.setResponseCode("MSG_000");
        header2.setResponseDescriptionEN("Success");
        mockCustomerInfoResponse.setHeader(header2);
        when(loanSubmissionGetCustomerInfoClient.searchCustomerInfoByCaID(123)).thenReturn(mockCustomerInfoResponse);

        ProductDetailRequest request = new ProductDetailRequest();
        request.setProductCode(requestProductCode);
        ProductDetailResponse productDetailResponse = loanService.fetchProductOrientation(crmId, request);

        Assertions.assertFalse(productDetailResponse.isAlreadyHasProduct());
        Assertions.assertFalse(productDetailResponse.isFlexiOnly());
        Assertions.assertEquals(LoanType.CREDIT_CARD, productDetailResponse.getLoanType());
        Assertions.assertEquals(ProductStatus.CONTINUE_APPLY, productDetailResponse.getStatus());
    }
}
