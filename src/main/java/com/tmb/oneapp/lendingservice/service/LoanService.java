package com.tmb.oneapp.lendingservice.service;

import com.tmb.common.exception.model.TMBCommonException;
import com.tmb.common.logger.TMBLogger;
import com.tmb.common.model.TmbOneServiceResponse;
import com.tmb.common.model.TmbStatus;
import com.tmb.common.model.legacy.rsl.common.ob.creditcard.InstantCreditCard;
import com.tmb.common.model.legacy.rsl.common.ob.dropdown.CommonCodeEntry;
import com.tmb.common.model.legacy.rsl.common.ob.facility.InstantFacility;
import com.tmb.common.model.legacy.rsl.common.ob.individual.Individual;
import com.tmb.common.model.legacy.rsl.ws.application.response.Body;
import com.tmb.common.model.legacy.rsl.ws.application.response.ResponseApplication;
import com.tmb.common.model.legacy.rsl.ws.individual.response.ResponseIndividual;
import com.tmb.common.model.legacy.rsl.ws.instant.eligible.product.response.ResponseInstantLoanGetEligibleProduct;
import com.tmb.common.model.legacy.rsl.ws.tracking.response.Application;
import com.tmb.common.model.legacy.rsl.ws.tracking.response.Header;
import com.tmb.common.model.legacy.rsl.ws.tracking.response.ResponseTracking;
import com.tmb.oneapp.lendingservice.LendingModuleCache;
import com.tmb.oneapp.lendingservice.client.*;
import com.tmb.oneapp.lendingservice.constant.LoanCategory;
import com.tmb.oneapp.lendingservice.constant.ResponseCode;
import com.tmb.oneapp.lendingservice.model.Customer;
import com.tmb.oneapp.lendingservice.model.ServiceError;
import com.tmb.oneapp.lendingservice.model.ServiceResponse;
import com.tmb.oneapp.lendingservice.model.ServiceResponseImp;
import com.tmb.oneapp.lendingservice.model.account.*;
import com.tmb.oneapp.lendingservice.model.config.CommonProductConfig;
import com.tmb.oneapp.lendingservice.model.config.LendingModuleConfig;
import com.tmb.oneapp.lendingservice.model.creditcard.CreditCard;
import com.tmb.oneapp.lendingservice.model.creditcard.CreditCardResponse;
import com.tmb.oneapp.lendingservice.model.loan.*;
import com.tmb.oneapp.lendingservice.util.Fetch;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.xml.rpc.ServiceException;
import java.rmi.RemoteException;
import java.util.*;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Provides services for Flexi loan
 */
@Service
public class LoanService {
    @Autowired
    private LoanStatusTrackingClient loanStatusTrackingClient;
    @Autowired
    private EligibleProductClient eligibleProductClient;
    @Autowired
    private CustomerServiceClient customerServiceClient;
    @Autowired
    private LendingModuleCache lendingModuleCache;
    @Autowired
    private CustomerExpServiceClient customerExpServiceClient;
    @Autowired
    private CommonServiceFeignClient commonServiceClient;
    @Autowired
    private LoanSubmissionGetApplicationInfoClient loanSubmissionGetApplicationInfoClient;
    @Autowired
    private LoanSubmissionGetCustomerInfoClient loanSubmissionGetCustomerInfoClient;


    private Map<String, Object> masterData;

    private LoanObjectMapper loanObjectMapper = new LoanObjectMapper();

    private static final TMBLogger<LoanService> logger = new TMBLogger<>(LoanService.class);

    /**
     * Models ProductResponse
     *
     * @param eligibleProductsResponse
     * @param loanStatusTrackingResponse
     * @param accountListResponse
     * @return
     */
    private ServiceResponse combineFlexiLoanProductResponse(ServiceResponse eligibleProductsResponse, ServiceResponse loanStatusTrackingResponse, ServiceResponse accountListResponse) {
        if (eligibleProductsResponse.getError() != null) return eligibleProductsResponse;
        if (loanStatusTrackingResponse.getError() != null) return loanStatusTrackingResponse;
        if (accountListResponse.getError() != null) return accountListResponse;

        ProductResponse productResponse = new ProductResponse();
        ServiceResponseImp serviceResponseImp = new ServiceResponseImp();
        List<DepositAccount> accountList = (List<DepositAccount>) accountListResponse.getData();
        EligibleProductResponse eligibleProductResponse = (EligibleProductResponse) eligibleProductsResponse.getData();
        LoanStatusTrackingResponse loanStatusTrackingResponseModel = (LoanStatusTrackingResponse) loanStatusTrackingResponse.getData();
        productResponse.setLoanStatusTracking(loanStatusTrackingResponseModel);
        productResponse.setEligibleProducts(eligibleProductResponse.getEligibleProducts());
        productResponse.setDepositAccountLists(accountList);
        serviceResponseImp.setData(productResponse);
        return serviceResponseImp;

    }

    /**
     * Models master data and cache it.
     */
    private void ensureCacheMasterData() {
        if (masterData != null) return;
        masterData = new HashMap<>();
        List<CommonCodeEntry> localMasterData26 = lendingModuleCache.getListByCategoryCode(LoanCategory.PRODUCT.getCode());
        List<CommonCodeEntry> localMasterData27 = lendingModuleCache.getListByCategoryCode(LoanCategory.SUBPRODUCT.getCode());
        List<CommonCodeEntry> localMasterDataPymtCriteria = lendingModuleCache.getListByCategoryCode(LoanCategory.PYMT_CRITERIA.getCode());
        List<PaymentCriteriaOption> masterDataPymt = localMasterDataPymtCriteria.stream()
                .sorted((o1, o2) -> o2.getEntryID().compareTo(o1.getEntryID())).map(commonCodeEntry -> {
                    PaymentCriteriaOption paymentCriteriaOption = new PaymentCriteriaOption();
                    paymentCriteriaOption.setOptionId(String.valueOf(commonCodeEntry.getEntryID()));
                    paymentCriteriaOption.setOptionNameEn(commonCodeEntry.getEntryName());
                    paymentCriteriaOption.setOptionNameTh(commonCodeEntry.getEntryName2());
                    paymentCriteriaOption.setEntrySource(commonCodeEntry.getEntrySource());
                    return paymentCriteriaOption;
                }).collect(Collectors.toList());

        localMasterData26.addAll(localMasterData27);
        localMasterData26.forEach(commonCodeEntry -> masterData.put(commonCodeEntry.getEntryCode(), commonCodeEntry));
        masterData.put(LoanCategory.PYMT_CRITERIA.getCode(), masterDataPymt);


    }


    /**
     * Gets Account List from calling Customer-exp-service
     *
     * @param crmId
     * @return
     */
    private ServiceResponse getAccountList(String crmId) {
        logger.info("Get Account List by crm id:{}", crmId);
        TmbOneServiceResponse<AccountSaving> accountListResponse = customerExpServiceClient.getAccountList(UUID.randomUUID().toString(), crmId);
        TmbStatus tmbStatus = accountListResponse.getStatus();
        ServiceResponseImp serviceResponseImp = new ServiceResponseImp();
        if (!ResponseCode.SUCCESS.getCode().equals(tmbStatus.getCode())) {
            logger.error("Get Account List got error:{}, {}", tmbStatus.getCode(), tmbStatus.getMessage());
            ServiceError serviceError = new ServiceError();
            serviceError.setErrorMessage("Get Account List error");
            serviceError.setResponseCode(tmbStatus.getCode());
            serviceResponseImp.setError(serviceError);
            return serviceResponseImp;
        }
        AccountSaving accountSaving = accountListResponse.getData();
        List<DepositAccount> filteredAccountList = accountSaving.getDepositAccountLists().stream().filter(depositAccount -> depositAccount.getAllowReceiveLoanFund().equals("1")).collect(Collectors.toList());

        serviceResponseImp.setData(filteredAccountList);
        return serviceResponseImp;
    }

    /**
     * Fetches all inforamtion for Flexi loan products
     *
     * @param request
     * @return
     */
    public ServiceResponse fetchProducts(ProductRequest request) {
        ensureCacheMasterData();
        CompletableFuture<ServiceResponse> accountListFuture = CompletableFuture.supplyAsync(() -> this.getAccountList(request.getCrmId()));
        CompletableFuture<ServiceResponse> f1 = CompletableFuture.supplyAsync(() -> this.fetchEligibleProducts(request, masterData));
        CompletableFuture<ServiceResponse> f2 = CompletableFuture.supplyAsync(() -> this.fetchLoanStatusTracking(request));
        CompletableFuture<Void> combinedFuture
                = CompletableFuture.allOf(accountListFuture, f1, f2);
        try {
            combinedFuture.get();
            return this.combineFlexiLoanProductResponse(f1.get(), f2.get(), accountListFuture.get());
        } catch (InterruptedException e) {
            logger.error("fetchProducts got InterruptedException:{}", e);
            Thread.currentThread().interrupt();
        } catch (ExecutionException e) {
            logger.error("fetchProducts got ExecutionException:{}", e);
        }
        ServiceError serviceError = new ServiceError();
        serviceError.setResponseCode("Other");
        ServiceResponseImp serviceResponseImp = new ServiceResponseImp();
        serviceResponseImp.setError(serviceError);
        return serviceResponseImp;

    }

    /**
     * Parses ResponseTracking to LoanStatusTrackingResponse
     *
     * @param loanStatusTrackingResponse
     * @return
     */
    private ServiceResponse parseLoanStatusTracking(ResponseTracking loanStatusTrackingResponse) {
        String successCode = "MSG_000";
        String dataNotFoundCode = "MSG_001";
        ServiceResponseImp serviceResponseImp = new ServiceResponseImp();
        Header header = loanStatusTrackingResponse.getHeader();
        if (dataNotFoundCode.equals(header.getResponseCode())) {
            LoanStatusTrackingResponse dataNotFoundLoanStatusTrackingResponse = new LoanStatusTrackingResponse();
            serviceResponseImp.setData(dataNotFoundLoanStatusTrackingResponse);
            return serviceResponseImp;
        }
        if (!successCode.equals(header.getResponseCode())) {
            logger.error("LoanStatusTracking got error:{}, {}", header.getResponseCode(), header.getResponseDescriptionEN());
            ServiceError serviceError = new ServiceError();
            serviceError.setResponseCode(header.getResponseCode());
            serviceError.setErrorMessage(header.getResponseDescriptionEN());
            serviceResponseImp.setError(serviceError);
            return serviceResponseImp;
        }

        LoanStatusTrackingResponse loanStatusTracking = loanObjectMapper.toOneApp(loanStatusTrackingResponse);
        serviceResponseImp.setData(loanStatusTracking);
        return serviceResponseImp;
    }

    /**
     * Fetches LoanStatusTracking
     *
     * @param request
     * @return
     */
    public ServiceResponse fetchLoanStatusTracking(ProductRequest request) {
        TmbOneServiceResponse<Customer> customerResponse = customerServiceClient.getCustomerDetails(request.getCrmId());
        TmbStatus tmbStatus = customerResponse.getStatus();
        if (!ResponseCode.SUCCESS.getCode().equals(tmbStatus.getCode())) {
            logger.error("call customer service to get citizen id got error:{}, {}", tmbStatus.getCode(), tmbStatus.getMessage());
            ServiceError serviceError = new ServiceError();
            serviceError.setResponseCode(tmbStatus.getCode());
            ServiceResponseImp serviceResponseImp = new ServiceResponseImp();
            serviceResponseImp.setError(serviceError);
            return serviceResponseImp;
        }
        String citizenId = customerResponse.getData().getCitizenId();
        try {
            ResponseTracking loanStatusTrackingResponse = loanStatusTrackingClient.searchAppStatusByID(citizenId);
            return parseLoanStatusTracking(loanStatusTrackingResponse);
        } catch (RemoteException | ServiceException e) {
            logger.error("fetchLoanStatusTracking got exception:{}", e);
        }
        ServiceResponseImp serviceResponseImp = new ServiceResponseImp();
        ServiceError serviceError = new ServiceError();
        serviceError.setResponseCode("500");
        serviceError.setErrorMessage("LoanStatusTracking Service Unavailable");
        serviceResponseImp.setError(serviceError);
        return serviceResponseImp;
    }

    /**
     * Parses ResponseInstantLoanGetEligibleProduct to EligibleProductResponse
     *
     * @param responseInstantLoanGetEligibleProduct
     * @param masterData
     * @return
     */
    private ServiceResponse parseEligibleProducts(ResponseInstantLoanGetEligibleProduct responseInstantLoanGetEligibleProduct, Map<String, Object> masterData) {
        String successCode = "MSG_000";
        String successDesc = "Success";
        com.tmb.common.model.legacy.rsl.ws.instant.eligible.product.response.Header header = responseInstantLoanGetEligibleProduct.getHeader();
        ServiceResponseImp serviceResponseImp = new ServiceResponseImp();
        if (!successCode.equals(header.getResponseCode()) || !successDesc.equalsIgnoreCase(header.getResponseDescriptionEN())) {
            logger.error("EligibleProducts got error:{}, {}", header.getResponseCode(), header.getResponseDescriptionEN());
            ServiceError serviceError = new ServiceError();
            serviceError.setResponseCode(header.getResponseCode());
            serviceError.setErrorMessage(header.getResponseDescriptionEN());
            serviceResponseImp.setError(serviceError);
            return serviceResponseImp;
        }
        EligibleProductResponse eligibleProductResponse = loanObjectMapper.toOneApp(responseInstantLoanGetEligibleProduct, masterData);
        serviceResponseImp.setData(eligibleProductResponse);
        return serviceResponseImp;
    }

    /**
     * Fetches EligibleProducts
     *
     * @param request
     * @param masterData
     * @return
     */
    public ServiceResponse fetchEligibleProducts(ProductRequest request, Map<String, Object> masterData) {
        try {
            ResponseInstantLoanGetEligibleProduct responseInstantLoanGetEligibleProduct = eligibleProductClient.getEligibleProduct(request.getCrmId());
            return parseEligibleProducts(responseInstantLoanGetEligibleProduct, masterData);

        } catch (ServiceException | RemoteException | StringIndexOutOfBoundsException e) {
            logger.error("fetchEligibleProducts got exception:{}", e);
        }
        ServiceResponseImp serviceResponseImp = new ServiceResponseImp();
        ServiceError serviceError = new ServiceError();
        serviceError.setResponseCode("500");
        serviceError.setErrorMessage("Eligible Product Service Unavailable");
        serviceResponseImp.setError(serviceError);
        return serviceResponseImp;
    }

    /**
     * Check if it is incomplete doc status
     * @param appStatus
     * @param incompleteDocStatus
     * @return
     */
    private boolean isIncompleteDocStatus(String appStatus, List<String> incompleteDocStatus) {
        return !incompleteDocStatus.stream().filter(appStatus::equalsIgnoreCase).collect(Collectors.toList()).isEmpty();
    }

    /**
     * Check if product code is credit card and has active status.
     * @param productCode
     * @param crmId
     * @return
     * @throws TMBCommonException
     */
    private boolean containInAccountCreditCardsAndActive(String productCode, String crmId) throws TMBCommonException {
        CreditCardResponse creditCardResponse = Fetch.fetch(() -> customerExpServiceClient.getCreditCards(UUID.randomUUID().toString(), crmId));

        List<CreditCard> alreadyHaveProduct = Stream.concat(creditCardResponse.getCreditCards().stream(), creditCardResponse.getFlashCards().stream())
                .filter(creditCard -> productCode.equalsIgnoreCase(creditCard.getRslProductCode()) && "active".equalsIgnoreCase(creditCard.getAccountStatus()))
                .collect(Collectors.toList());
        return !alreadyHaveProduct.isEmpty();
    }


    private boolean containInCreditCardEligibleProduct(String crmId, String productCode) throws TMBCommonException {
        ResponseInstantLoanGetEligibleProduct eligibleProducts = Fetch.fetch(() -> eligibleProductClient.getEligibleProduct(crmId), LoanServiceResponseParser::parseEligibleProducts);
        List<InstantCreditCard> hasligibleProducts = Arrays.stream(eligibleProducts.getBody().getInstantCreditCard()).filter(instantCreditCard -> instantCreditCard.getProductType().equalsIgnoreCase(productCode)).collect(Collectors.toList());
        return !hasligibleProducts.isEmpty();

    }

    /**
     * Handles credit card product detail
     * @param loanType
     * @param crmId
     * @param productCode
     * @param lendingModuleConfig
     * @return
     * @throws TMBCommonException
     */
    private ProductDetailResponse handleCreditCard(LoanType loanType, String crmId, String productCode, LendingModuleConfig lendingModuleConfig) throws TMBCommonException {
        ProductDetailResponse productDetailResponse = new ProductDetailResponse();
        productDetailResponse.setLoanType(loanType);


        if (containInAccountCreditCardsAndActive(productCode, crmId)) {
            productDetailResponse.setAlreadyHasProduct(true);
            return productDetailResponse;
        }

        if (containInCreditCardEligibleProduct(crmId, productCode)) {
            List<String> hasFlexiOnlyProducts = lendingModuleConfig.getFlexiOnly().stream().filter(s -> s.equalsIgnoreCase(productCode)).collect(Collectors.toList());
            if (!hasFlexiOnlyProducts.isEmpty()) {
                // Hide button
                productDetailResponse.setFlexiOnly(true);
                return productDetailResponse;
            }
            return handleFlexiLoanFlow(crmId, productCode, loanType);
        }

        return handleLoanSubmissionFlow(crmId, productCode, loanType, lendingModuleConfig);

    }

    /**
     * Checks if response from /account/loan has "Benefit Loan" as desc which means personal loan product.
     * @param loanSummary
     * @return
     */
    private boolean haveInAccounts(LoanSummary loanSummary) {
        List<LoanAccount> loanAccounts = loanSummary.getLoanAccounts().stream()
                .filter(loanAccount -> "Benefit Loan".equalsIgnoreCase(loanAccount.getAccountTypeDescEn()))
                .collect(Collectors.toList());
        return !loanAccounts.isEmpty();
    }

    /**
     * Handles personal loan product detail
     * @param loanType
     * @param crmId
     * @param productCode
     * @param lendingModuleConfig
     * @return
     * @throws TMBCommonException
     */
    private ProductDetailResponse handlePersonaLoan(LoanType loanType, String crmId, String productCode, LendingModuleConfig lendingModuleConfig) throws TMBCommonException {
        String lowerCaseProductCode = productCode.toLowerCase();

        List<String> foundPersonalLoanCodes = Arrays.asList("c2g", "rc").stream().filter(lowerCaseProductCode::contains).collect(Collectors.toList());
        if (foundPersonalLoanCodes.isEmpty()) {
            logger.error("product code is invalid:{}",productCode);
            throw new TMBCommonException(ResponseCode.FAILED.getCode(), ResponseCode.FAILED.getMessage(), ResponseCode.FAILED.getService(), HttpStatus.BAD_REQUEST, null);
        }

        ProductDetailResponse productDetailResponse = new ProductDetailResponse();


        if (lowerCaseProductCode.contains("c2g")) {

            AccountSaving accountSaving = Fetch.fetch(() -> customerExpServiceClient.getAccountList(UUID.randomUUID().toString(), crmId));
            Map<String, LoanAccount> loanAccountHashMap = new HashMap<>();
            accountSaving.getLoanAccounts().forEach(loanAccount -> loanAccountHashMap.put(loanAccount.getAccountNumber(), loanAccount));
            LoanAccountSummaryRequest loanAccountSummaryRequest = new LoanAccountSummaryRequest();
            loanAccountSummaryRequest.setLoanAccounts(toLoanAccounts(accountSaving.getLoanAccounts()));
            loanAccountSummaryRequest.setHpAccounts(new ArrayList<>());
            LoanSummary loanSummary = Fetch.fetch(() -> customerExpServiceClient.getAccountLoan(UUID.randomUUID().toString(), crmId, loanAccountSummaryRequest));

            if (haveInAccounts(loanSummary)) {

                ResponseInstantLoanGetEligibleProduct eligibleProducts = Fetch.fetch(() -> eligibleProductClient.getEligibleProduct(crmId), LoanServiceResponseParser::parseEligibleProducts);
                List<InstantFacility> foundProducts = Arrays.stream(eligibleProducts.getBody().getInstantFacility()).filter(instantFacility -> instantFacility.getProductCode().equalsIgnoreCase("C2G02") || instantFacility.getProductCode().equalsIgnoreCase(productCode)).collect(Collectors.toList());
                if (foundProducts.isEmpty()) {
                    productDetailResponse.setAlreadyHasProduct(true);
                    return productDetailResponse;
                }
                return handleFlexiLoanFlow(crmId, productCode, loanType);
            }
            return handleLoanSubmissionFlow(crmId, productCode, loanType, lendingModuleConfig);

        }

        if (containInAccountCreditCardsAndActive(productCode, crmId)) {
            productDetailResponse.setAlreadyHasProduct(true);
            return productDetailResponse;
        }
        ResponseInstantLoanGetEligibleProduct eligibleProducts = Fetch.fetch(() -> eligibleProductClient.getEligibleProduct(crmId), LoanServiceResponseParser::parseEligibleProducts);
        List<InstantFacility> foundProducts = Arrays.stream(eligibleProducts.getBody().getInstantFacility()).filter(instantFacility -> instantFacility.getProductCode().equalsIgnoreCase("C2G02") || instantFacility.getProductCode().equalsIgnoreCase(productCode)).collect(Collectors.toList());
        if (!foundProducts.isEmpty()) {
            return handleFlexiLoanFlow(crmId, productCode, loanType);
        }
        return handleLoanSubmissionFlow(crmId, productCode, loanType, lendingModuleConfig);


    }

    /**
     * Returns application status from loan status tracking based on rsl product code.
     * @param crmId
     * @param productCode
     * @return
     * @throws TMBCommonException
     */
    private List<Application> findApplication(String crmId, String productCode) throws TMBCommonException {
        TmbOneServiceResponse<Customer> customerResponse = customerServiceClient.getCustomerDetails(crmId);
        String citizenId = customerResponse.getData().getCitizenId();
        ResponseTracking loanStatusTrackingResponse = Fetch.fetch(() -> loanStatusTrackingClient.searchAppStatusByID(citizenId), LoanServiceResponseParser::parseLoanStatusTracking);
        Application[] applications = loanStatusTrackingResponse.getBody().getApplication();
        return Arrays.stream(applications)
                .filter(application -> !Arrays.stream(application.getApplicants())
                        .filter(applicant -> !Arrays.stream(applicant.getProducts())
                                .filter(product -> product.getProductCode().equalsIgnoreCase(productCode))
                                .collect(Collectors.toList()).isEmpty())
                        .collect(Collectors.toList()).isEmpty()).collect(Collectors.toList());
    }

    /**
     * Handles loan submission flow
     * @param crmId
     * @param productCode
     * @param loanType
     * @param lendingModuleConfig
     * @return
     * @throws TMBCommonException
     */
    private ProductDetailResponse handleLoanSubmissionFlow(String crmId, String productCode, LoanType
            loanType, LendingModuleConfig lendingModuleConfig) throws TMBCommonException {
        ProductDetailResponse productDetailResponse = new ProductDetailResponse();
        productDetailResponse.setLoanType(loanType);

        List<Application> foundApplication = findApplication(crmId, productCode);
        if (foundApplication.isEmpty()) {
            productDetailResponse.setStatus(ProductStatus.APPLY_WITH_PRODUCT_NAME);
            return productDetailResponse;
        }
        Application firstApp = foundApplication.get(0);
        if (firstApp.getInstantFlag().equalsIgnoreCase("Y")) {
            productDetailResponse.setStatus(ProductStatus.CHECK_STATUS);
            return productDetailResponse;
        }
        if (firstApp.getAppStatus().equalsIgnoreCase("DRAFT") && firstApp.getIsSubmitted().equalsIgnoreCase("N")) {
            long caId = firstApp.getCaId();
            productDetailResponse.setContinueApplyNextStep(getLoanSubmissionFlowContinueApplyNextScreen(caId));
            productDetailResponse.setContinueApplyParams(getContinueApplyParams(firstApp));
            productDetailResponse.setStatus(ProductStatus.CONTINUE_APPLY);
            return productDetailResponse;
        }
        if (isIncompleteDocStatus(firstApp.getAppStatus(), lendingModuleConfig.getIncompleteDocStatus())) {
            productDetailResponse.setStatus(ProductStatus.SEE_DOC_LIST);
            return productDetailResponse;
        }
        productDetailResponse.setStatus(ProductStatus.CHECK_STATUS);
        return productDetailResponse;
    }

    /**
     * Handles flexi loan flow
     * @param crmId
     * @param productCode
     * @param loanType
     * @return
     * @throws TMBCommonException
     */
    private ProductDetailResponse handleFlexiLoanFlow(String crmId, String productCode, LoanType loanType) throws
            TMBCommonException {
        ProductDetailResponse productDetailResponse = new ProductDetailResponse();
        productDetailResponse.setLoanType(loanType);
        List<Application> foundApplication = findApplication(crmId, productCode);
        if (foundApplication.isEmpty()) {
            productDetailResponse.setStatus(ProductStatus.APPLY_WITH_PRODUCT_NAME);
            return productDetailResponse;
        }
        Application firstApp = foundApplication.get(0);
        if (firstApp.getInstantFlag().equalsIgnoreCase("Y")) {
            if (firstApp.getIsSubmitted().equalsIgnoreCase("N")) {
                productDetailResponse.setContinueApplyNextStep(getFlexiFlowContinueApplyNextScreen(firstApp));
                productDetailResponse.setContinueApplyParams(getContinueApplyParams(firstApp));
                productDetailResponse.setStatus(ProductStatus.CONTINUE_APPLY);
                return productDetailResponse;
            }
            productDetailResponse.setStatus(ProductStatus.CHECK_STATUS);
            return productDetailResponse;
        }
        productDetailResponse.setStatus(ProductStatus.APPLY_WITH_PRODUCT_NAME);
        return productDetailResponse;

    }

    private ContinueApplyParams getContinueApplyParams(Application application) {
        ContinueApplyParams continueApplyParams = new ContinueApplyParams();
        continueApplyParams.setAppRefNo(application.getAppRefNo());
        continueApplyParams.setCaId(application.getCaId());
        return continueApplyParams;
    }

    /**
     * Creates LoanAccount to request /account/loan service
     * @param loanAccounts
     * @return
     */
    private List<LoanAccount> toLoanAccounts(List<LoanAccount> loanAccounts) {
        return loanAccounts.stream().map(depositAccount -> {
            LoanAccount loanAccount = new LoanAccount();
            loanAccount.setProductCode(depositAccount.getProductCode());
            loanAccount.setAccountNumber(depositAccount.getAccountNumber());
            return loanAccount;
        }).collect(Collectors.toList());
    }

    /**
     * Return credit card or personal loan product detail based on rsl product code
     * @param crmId
     * @param request
     * @return
     * @throws TMBCommonException
     */
    public ProductDetailResponse fetchProductOrientation(String crmId, ProductDetailRequest request) throws
            TMBCommonException {
        String productCode = request.getProductCode();
        List<CommonCodeEntry> master26 = lendingModuleCache.getListByCategoryCode(LoanCategory.PRODUCT.getCode());
        LoanType loanType = LoanType.PERSONAL_LOAN;
        List<CommonCodeEntry> creditCardProducts = master26.stream().filter(commonCodeEntry -> productCode.equalsIgnoreCase(commonCodeEntry.getEntryCode())).collect(Collectors.toList());
        if (!creditCardProducts.isEmpty()) {
            loanType = LoanType.CREDIT_CARD;
        }
        List<LendingModuleConfig> lendingModuleConfigs = Fetch.fetch(() -> commonServiceClient.getCommonConfig(UUID.randomUUID().toString(), "lending_module"));
        LendingModuleConfig lendingModuleConfig = lendingModuleConfigs.get(0);
        List<CommonProductConfig> allProductConfig = Stream.concat(lendingModuleConfig.getApplyCreditCards().stream(), lendingModuleConfig.getApplyPersonalLoans().stream()).collect(Collectors.toList());
        ProductDetailResponse productDetailResponse;
        if (loanType.equals(LoanType.CREDIT_CARD)) {
            productDetailResponse = handleCreditCard(loanType, crmId, productCode, lendingModuleConfig);
        } else {
            productDetailResponse = handlePersonaLoan(loanType, crmId, productCode, lendingModuleConfig);
        }
        List<CommonProductConfig> selectedProductConfigs = allProductConfig.stream().filter(commonProductConfig -> productCode.equalsIgnoreCase(commonProductConfig.getRslCode())).collect(Collectors.toList());
        if (!selectedProductConfigs.isEmpty()) {
            CommonProductConfig productConfig = selectedProductConfigs.get(0);
            productDetailResponse.setProductNameEn(productConfig.getProductNameTh());
            productDetailResponse.setProductNameTh(productConfig.getProductNameEn());
            productDetailResponse.setContentLink(productConfig.getContentLink());
        }
        productDetailResponse.setProductCode(productCode);
        productDetailResponse.setLoanType(loanType);

        return productDetailResponse;
    }

    /**
     * Returns next screen for Submission Flow
     * @param caId
     * @return
     * @throws TMBCommonException
     */
    public ContinueApplyNextScreen getLoanSubmissionFlowContinueApplyNextScreen(long caId) throws TMBCommonException {
        CompletableFuture<ResponseApplication> appFuture = Fetch.fetchFuture(() -> loanSubmissionGetApplicationInfoClient.searchApplicationInfoByCaID(caId), LoanServiceResponseParser::parseApplicationInfo);
        CompletableFuture<ResponseIndividual> customerFuture = Fetch.fetchFuture(() -> loanSubmissionGetCustomerInfoClient.searchCustomerInfoByCaID(caId), LoanServiceResponseParser::parseCustomerInfo);
        List<Object> results = Fetch.allOf(appFuture, customerFuture);

        if (!results.stream().filter(Objects::isNull).collect(Collectors.toList()).isEmpty()) {
            throw new TMBCommonException(ResponseCode.FAILED.getCode(), ResponseCode.FAILED.getMessage(), ResponseCode.FAILED.getService(), HttpStatus.BAD_REQUEST, null);
        }

        ResponseApplication responseApplication = (ResponseApplication) results.get(0);
        ResponseIndividual responseIndividual = (ResponseIndividual) results.get(1);
        Body appBody = responseApplication.getBody();
        com.tmb.common.model.legacy.rsl.ws.individual.response.Body customerBody = responseIndividual.getBody();
        String ncbConsentFlag = appBody.getNcbConsentFlag();

        if (StringUtils.isEmpty(ncbConsentFlag)) {
            return ContinueApplyNextScreen.CONFIRMATION;
        }
        if (customerBody.getIndividuals().length == 0) {
            return ContinueApplyNextScreen.PERSONAL;
        }
        Individual individual = customerBody.getIndividuals()[0];
        String personalInfoSavedFlag = individual.getPersonalInfoSavedFlag();
        String incomeInfoSavedFlag = individual.getIncomeInfoSavedFlag();
        String employmentInfoSavedFlag = individual.getEmploymentInfoSavedFlag();

        if ("N".equalsIgnoreCase(personalInfoSavedFlag)) {
            return ContinueApplyNextScreen.PERSONAL;
        }
        if ("N".equalsIgnoreCase(employmentInfoSavedFlag)) {
            return ContinueApplyNextScreen.WORKING;
        }
        if ("N".equalsIgnoreCase(incomeInfoSavedFlag)) {
            return ContinueApplyNextScreen.INCOME;
        }
        return ContinueApplyNextScreen.UPLOAD_DOC;

    }

    /**
     * Returns next screen for flexi flow
     * @param firstApp
     * @return
     */
    public ContinueApplyNextScreen getFlexiFlowContinueApplyNextScreen(Application firstApp) {

        String stepFlag = firstApp.getInstantAppliedStepFlag();
        if (stepFlag == null) {
            return ContinueApplyNextScreen.UNKNOWN;
        }
        if (!Arrays.asList("1", "2").stream().filter(s -> s.contains(stepFlag)).collect(Collectors.toList()).isEmpty()) {
            return ContinueApplyNextScreen.CASH_TRANSFER_DAY1;
        }
        if ("3".equalsIgnoreCase(stepFlag)) {
            return ContinueApplyNextScreen.FINAL_APPROVE_LOAN_CONFIRMATION;
        }
        if ("4".equalsIgnoreCase(stepFlag)) {
            return ContinueApplyNextScreen.RESULT;
        }
        return ContinueApplyNextScreen.UNKNOWN;

    }
}
