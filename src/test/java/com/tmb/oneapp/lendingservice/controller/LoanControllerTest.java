package com.tmb.oneapp.lendingservice.controller;

import com.tmb.common.exception.model.TMBCommonException;
import com.tmb.common.model.TmbOneServiceResponse;
import com.tmb.oneapp.lendingservice.constant.LendingServiceConstant;
import com.tmb.oneapp.lendingservice.model.ServiceError;
import com.tmb.oneapp.lendingservice.model.ServiceResponseImp;
import com.tmb.oneapp.lendingservice.model.loan.ProductDetailRequest;
import com.tmb.oneapp.lendingservice.model.loan.ProductDetailResponse;
import com.tmb.oneapp.lendingservice.model.loan.ProductRequest;
import com.tmb.oneapp.lendingservice.model.loan.ProductResponse;
import com.tmb.oneapp.lendingservice.service.LoanService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;

import javax.validation.Valid;
import java.util.HashMap;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@RunWith(JUnit4.class)
public class LoanControllerTest {

    @Mock
    LoanService loanService;


    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    void productsSuccess() throws TMBCommonException {
        ServiceResponseImp mockResponse = new ServiceResponseImp();
        ProductResponse productResponse = new ProductResponse();
        mockResponse.setData(productResponse);

        when(loanService.fetchProducts(any())).thenReturn(mockResponse);
        LoanController loanController = new LoanController(loanService);
        HashMap<String, String> reqHeader = new HashMap<>();
        reqHeader.put(LendingServiceConstant.HEADER_X_CRMID, "123");
        ResponseEntity<TmbOneServiceResponse<Object>> actualResponse = loanController.fetchProducts(reqHeader);
        ProductResponse actualProductResponse = (ProductResponse) actualResponse.getBody().getData();
        Assertions.assertNotNull(actualProductResponse);
        verify(loanService, times(1)).fetchProducts(any());

    }


    @Test
    void productDetailSuccess() throws TMBCommonException {
        ServiceResponseImp mockResponse = new ServiceResponseImp();
        mockResponse.setData(new ProductDetailResponse());
        when(loanService.fetchProductDetail(any(), any())).thenReturn(mockResponse);
        LoanController loanController = new LoanController(loanService);
        HashMap<String, String> reqHeader = new HashMap<>();
        reqHeader.put(LendingServiceConstant.HEADER_X_CRMID, "123");
        @Valid ProductDetailRequest req = new ProductDetailRequest();
        req.setProductCode("c2g01");
        ResponseEntity<TmbOneServiceResponse<Object>> actualResponse = loanController.fetchProductDetail(reqHeader, req);
        ProductDetailResponse actualProductResponse = (ProductDetailResponse) actualResponse.getBody().getData();
        Assertions.assertNotNull(actualProductResponse);
        verify(loanService, times(1)).fetchProductDetail("123", req);
    }

    @Test
    void productsHasErrorShouldThrowException() {
        ServiceResponseImp mockResponse = new ServiceResponseImp();
        ServiceError error = new ServiceError();
        mockResponse.setError(error);
        when(loanService.fetchProducts(any())).thenReturn(mockResponse);
        LoanController loanController = new LoanController(loanService);
        ProductRequest request = new ProductRequest();
        request.setCrmId("12345");
        try {
            loanController.fetchProducts(new HashMap<>());
            Assertions.fail("Should have TMBCommonException");
        } catch (TMBCommonException e) {
        }
    }
}
