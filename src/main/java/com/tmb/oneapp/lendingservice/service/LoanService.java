package com.tmb.oneapp.lendingservice.service;

import com.tmb.common.exception.model.TMBCommonException;
import com.tmb.common.logger.TMBLogger;
import com.tmb.common.model.TmbOneServiceResponse;
import com.tmb.common.model.TmbStatus;
import com.tmb.common.model.legacy.rsl.common.ob.creditcard.InstantCreditCard;
import com.tmb.common.model.legacy.rsl.common.ob.dropdown.CommonCodeEntry;
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
import com.tmb.oneapp.lendingservice.model.account.AccountSaving;
import com.tmb.oneapp.lendingservice.model.account.DepositAccount;
import com.tmb.oneapp.lendingservice.model.config.LendingModuleConfig;
import com.tmb.oneapp.lendingservice.model.creditcard.CreditCard;
import com.tmb.oneapp.lendingservice.model.creditcard.CreditCardResponse;
import com.tmb.oneapp.lendingservice.model.loan.*;
import com.tmb.oneapp.lendingservice.util.Fetch;
import org.springframework.stereotype.Service;

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
    private final LoanStatusTrackingClient loanStatusTrackingClient;
    private final EligibleProductClient eligibleProductClient;
    private final CustomerServiceClient customerServiceClient;
    private final LendingModuleCache lendingModuleCache;
    private final CustomerExpServiceClient customerExpServiceClient;
    private final CommonServiceFeignClient commonServiceClient;
    private Map<String, Object> masterData;

    private LoanObjectMapper loanObjectMapper = new LoanObjectMapper();

    private static final TMBLogger<LoanService> logger = new TMBLogger<>(LoanService.class);


    public LoanService(LoanStatusTrackingClient loanStatusTrackingClient, EligibleProductClient eligibleProductClient, CustomerServiceClient customerServiceClient, LendingModuleCache lendingModuleCache, CustomerExpServiceClient customerExpServiceClient, CommonServiceFeignClient commonServiceClient) {
        this.loanStatusTrackingClient = loanStatusTrackingClient;
        this.eligibleProductClient = eligibleProductClient;
        this.customerServiceClient = customerServiceClient;
        this.lendingModuleCache = lendingModuleCache;
        this.customerExpServiceClient = customerExpServiceClient;
        this.commonServiceClient = commonServiceClient;
    }

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
        com.tmb.common.model.legacy.rsl.ws.instant.eligible.product.response.Header header = responseInstantLoanGetEligibleProduct.getHeader();
        ServiceResponseImp serviceResponseImp = new ServiceResponseImp();
        if (!successCode.equals(header.getResponseCode())) {
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

    private boolean isIncompleteDocStatus(String appStatus, List<String> incompleteDocStatus) {
        return !incompleteDocStatus.stream().filter(appStatus::equalsIgnoreCase).collect(Collectors.toList()).isEmpty();
    }


    private ServiceResponse handleCreditCard(String loanType, String crmId, String productCode, CommonCodeEntry productDetail) throws TMBCommonException {

        LendingModuleConfig lendingModuleConfig = Fetch.fetch(() -> commonServiceClient.getCommonConfig(UUID.randomUUID().toString(), "lending_module"));

        //List<CommonProductConfig> allProductConfig = Stream.concat(lendingModuleConfig.getApplyCreditCards().stream(), lendingModuleConfig.getApplyPersonalLoans().stream()).collect(Collectors.toList());


        CreditCardResponse creditCardResponse = Fetch.fetch(() -> customerExpServiceClient.getCreditCards(UUID.randomUUID().toString(), crmId));

        ServiceResponseImp serviceResponseImp = new ServiceResponseImp();

        ProductDetailResponse productDetailResponse = new ProductDetailResponse();
        serviceResponseImp.setData(productDetailResponse);
        productDetailResponse.setLoanType(loanType);
        productDetailResponse.setProductCode(productCode);
        productDetailResponse.setProductNameEn(productDetail.getEntryName());
        productDetailResponse.setProductNameTh(productDetail.getEntryName2());


        List<CreditCard> alreadyHaveProduct = Stream.concat(creditCardResponse.getCreditCards().stream(), creditCardResponse.getFlashCards().stream())
                .filter(creditCard -> productCode.equalsIgnoreCase(creditCard.getRslProductCode()) && "active".equalsIgnoreCase(creditCard.getAccountStatus()))
                .collect(Collectors.toList());

        if (!alreadyHaveProduct.isEmpty()) {
            productDetailResponse.setAlreadyHasProduct(true);
            return serviceResponseImp;
        }

        //step No.4

        ResponseInstantLoanGetEligibleProduct eligibleProducts = Fetch.fetch(() -> eligibleProductClient.getEligibleProduct(crmId));


        List<InstantCreditCard> hasligibleProducts = Arrays.stream(eligibleProducts.getBody().getInstantCreditCard()).filter(instantCreditCard -> instantCreditCard.getProductType().equalsIgnoreCase(productCode)).collect(Collectors.toList());
        if (!hasligibleProducts.isEmpty()) {
            List<String> hasFlexiOnlyProducts = lendingModuleConfig.getFlexiOnly().stream().filter(s -> s.equalsIgnoreCase(productCode)).collect(Collectors.toList());
            if (!hasFlexiOnlyProducts.isEmpty()) {
                // Hide button
                productDetailResponse.setFlexiOnly(true);
                return serviceResponseImp;
            }
        }

        TmbOneServiceResponse<Customer> customerResponse = customerServiceClient.getCustomerDetails(crmId);
        String citizenId = customerResponse.getData().getCitizenId();
        ResponseTracking loanStatusTrackingResponse = Fetch.fetch(() -> loanStatusTrackingClient.searchAppStatusByID(citizenId));
        Application[] applications = loanStatusTrackingResponse.getBody().getApplication();
        List<Application> foundApplication = Arrays.stream(applications)
                .filter(application -> !Arrays.stream(application.getApplicants())
                        .filter(applicant -> !Arrays.stream(applicant.getProducts())
                                .filter(product -> product.getProductCode().equalsIgnoreCase(productCode))
                                .collect(Collectors.toList()).isEmpty())
                        .collect(Collectors.toList()).isEmpty()).collect(Collectors.toList());

        if (foundApplication.isEmpty()) {
            productDetailResponse.setStatus("apply_with_product_name");
            return serviceResponseImp;
        }


        Application firstApp = foundApplication.get(0);
        if (firstApp.getInstantFlag().equalsIgnoreCase("Y")) {
            if (firstApp.getIsSubmitted().equalsIgnoreCase("N")) {
                productDetailResponse.setStatus("continue_apply");
            } else {
                productDetailResponse.setStatus("check_status");
            }
            return serviceResponseImp;
        }
        if (firstApp.getAppStatus().equalsIgnoreCase("DRAFT") && firstApp.getIsSubmitted().equalsIgnoreCase("N")) {
            productDetailResponse.setStatus("continue_apply");
        } else if (isIncompleteDocStatus(firstApp.getAppStatus(), lendingModuleConfig.getIncompleteDocStatus())) {
            productDetailResponse.setStatus("see_doc_list");
        } else {
            productDetailResponse.setStatus("check_status");
        }
        return serviceResponseImp;


    }


    public ServiceResponse fetchProductDetail(String crmId, ProductDetailRequest request) throws TMBCommonException {
        String productCode = request.getProductCode();
        List<CommonCodeEntry> master26 = lendingModuleCache.getListByCategoryCode(LoanCategory.PRODUCT.getCode());
        List<CommonCodeEntry> master27 = lendingModuleCache.getListByCategoryCode(LoanCategory.SUBPRODUCT.getCode());
        String loanType = "credit_card";
        List<CommonCodeEntry> creditCardProducts = master26.stream().filter(commonCodeEntry -> productCode.equalsIgnoreCase(commonCodeEntry.getEntryCode())).collect(Collectors.toList());
        List<CommonCodeEntry> personalLoanProducts = master27.stream().filter(commonCodeEntry -> productCode.equalsIgnoreCase(commonCodeEntry.getEntryCode())).collect(Collectors.toList());
        CommonCodeEntry productDetail;
        if (!personalLoanProducts.isEmpty()) {
            loanType = "personal_loan";
            productDetail = personalLoanProducts.get(0);
        } else {
            productDetail = creditCardProducts.get(0);
        }

        if (loanType.equals("credit_card")) {
            return handleCreditCard(loanType, crmId, productCode, productDetail);
        }
        return null;


    }
}
