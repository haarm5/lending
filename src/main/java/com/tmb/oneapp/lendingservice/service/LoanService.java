package com.tmb.oneapp.lendingservice.service;

import com.tmb.common.model.legacy.rsl.ws.instant.eligible.product.response.ResponseInstantLoanGetEligibleProduct;
import com.tmb.common.model.legacy.rsl.ws.tracking.response.Header;
import com.tmb.common.model.legacy.rsl.ws.tracking.response.ResponseTracking;
import com.tmb.oneapp.lendingservice.client.EligibleProductClient;
import com.tmb.oneapp.lendingservice.client.LoanStatusTrackingClient;
import com.tmb.oneapp.lendingservice.model.ServiceError;
import com.tmb.oneapp.lendingservice.model.ServiceResponse;
import com.tmb.oneapp.lendingservice.model.ServiceResponseImp;
import com.tmb.oneapp.lendingservice.model.loan.*;
import org.springframework.stereotype.Service;

import javax.xml.rpc.ServiceException;
import java.rmi.RemoteException;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

@Service
public class LoanService {

    private final LoanStatusTrackingClient loanStatusTrackingClient;
    private final EligibleProductClient eligibleProductClient;

    private LoanObjectMapper loanObjectMapper = new LoanObjectMapper();

    public LoanService(LoanStatusTrackingClient loanStatusTrackingClient, EligibleProductClient eligibleProductClient) {
        this.loanStatusTrackingClient = loanStatusTrackingClient;
        this.eligibleProductClient = eligibleProductClient;
    }


    private ServiceResponse combineLoanStatusTrackingAndEligibleProducts(ServiceResponse eligibleProductsResponse, ServiceResponse loanStatusTrackingResponse) {
        if (eligibleProductsResponse.getError() != null) return eligibleProductsResponse;
        if (loanStatusTrackingResponse.getError() != null) return loanStatusTrackingResponse;

        ProductResponse productResponse = new ProductResponse();
        ServiceResponseImp serviceResponseImp = new ServiceResponseImp();

        EligibleProductResponse eligibleProductResponse = (EligibleProductResponse) eligibleProductsResponse.getData();
        LoanStatusTrackingResponse loanStatusTrackingResponseModel = (LoanStatusTrackingResponse) loanStatusTrackingResponse.getData();
        productResponse.setLoanStatusTracking(loanStatusTrackingResponseModel);
        productResponse.setEligibleProducts(eligibleProductResponse);
        serviceResponseImp.setData(productResponse);
        return serviceResponseImp;

    }

    public ServiceResponse fetchProducts(ProductRequest request) {
        CompletableFuture<ServiceResponse> f1 = CompletableFuture.supplyAsync(() -> this.fetchEligibleProducts(request));
        CompletableFuture<ServiceResponse> f2 = CompletableFuture.supplyAsync(() -> this.fetchLoanStatusTracking(request));
        CompletableFuture<ServiceResponse> fResult = f1.thenCombine(f2, this::combineLoanStatusTrackingAndEligibleProducts);

        try {
            return fResult.get();
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        } catch (ExecutionException e) {

        }
        ServiceError serviceError = new ServiceError();
        serviceError.setResponseCode("Other");
        ServiceResponseImp serviceResponseImp = new ServiceResponseImp();
        serviceResponseImp.setError(serviceError);
        return serviceResponseImp;

    }

    private ServiceResponse parseLoanStatusTracking(ResponseTracking loanStatusTrackingResponse) {
        String SUCCESS_CODE = "MSG_000";
        ServiceResponseImp serviceResponseImp = new ServiceResponseImp();
        Header header = loanStatusTrackingResponse.getHeader();
        if (!SUCCESS_CODE.equals(header.getResponseCode())) {
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

    public ServiceResponse fetchLoanStatusTracking(ProductRequest request) {

        try {
            ResponseTracking loanStatusTrackingResponse = loanStatusTrackingClient.searchAppStatusByID();
            return parseLoanStatusTracking(loanStatusTrackingResponse);
        } catch (RemoteException | ServiceException e) {
            e.printStackTrace();
        }
        ServiceResponseImp serviceResponseImp = new ServiceResponseImp();
        ServiceError serviceError = new ServiceError();
        serviceError.setResponseCode("500");
        serviceError.setErrorMessage("LoanStatusTracking Service Unavailable");
        serviceResponseImp.setError(serviceError);
        return serviceResponseImp;
    }


    private ServiceResponse parseEligibleProducts(ResponseInstantLoanGetEligibleProduct responseInstantLoanGetEligibleProduct) {
        String SUCCESS_CODE = "MSG_000";
        com.tmb.common.model.legacy.rsl.ws.instant.eligible.product.response.Header header = responseInstantLoanGetEligibleProduct.getHeader();
        ServiceResponseImp serviceResponseImp = new ServiceResponseImp();
        if (!SUCCESS_CODE.equals(header.getResponseCode())) {
            ServiceError serviceError = new ServiceError();
            serviceError.setResponseCode(header.getResponseCode());
            serviceError.setErrorMessage(header.getResponseDescriptionEN());
            serviceResponseImp.setError(serviceError);
            return serviceResponseImp;
        }
        EligibleProductResponse eligibleProductResponse = loanObjectMapper.toOneApp(responseInstantLoanGetEligibleProduct);
        serviceResponseImp.setData(eligibleProductResponse);
        return serviceResponseImp;
    }

    public ServiceResponse fetchEligibleProducts(ProductRequest request) {
        try {
            ResponseInstantLoanGetEligibleProduct responseInstantLoanGetEligibleProduct = eligibleProductClient.getEligibleProduct();
            return parseEligibleProducts(responseInstantLoanGetEligibleProduct);

        } catch (ServiceException | RemoteException e) {
            e.printStackTrace();
        }
        ServiceResponseImp serviceResponseImp = new ServiceResponseImp();
        ServiceError serviceError = new ServiceError();
        serviceError.setResponseCode("500");
        serviceError.setErrorMessage("Eligible Product Service Unavailable");
        serviceResponseImp.setError(serviceError);
        return serviceResponseImp;
    }
}
