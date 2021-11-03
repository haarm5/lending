package com.tmb.oneapp.lendingservice.controller;

import com.tmb.common.exception.model.TMBCommonException;
import com.tmb.common.model.TmbOneServiceResponse;
import com.tmb.common.model.loan.RslServiceError;
import com.tmb.oneapp.lendingservice.model.ServiceResponseImp;
import com.tmb.oneapp.lendingservice.model.info.MasterDataRequest;
import com.tmb.oneapp.lendingservice.model.info.MasterDataResponse;
import com.tmb.oneapp.lendingservice.model.loan.ProductResponse;
import com.tmb.oneapp.lendingservice.service.InfoService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@RunWith(JUnit4.class)
public class LendingInfoControllerTest {

    @Mock
    InfoService infoService;


    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    void fetchMasterDataSuccess() throws TMBCommonException {
        ServiceResponseImp mockResponse = new ServiceResponseImp();
        MasterDataResponse productResponse = new MasterDataResponse();
        mockResponse.setData(productResponse);

        when(infoService.fetchMasterData(any())).thenReturn(mockResponse);
        LendingInfoController loanController = new LendingInfoController(infoService);
        MasterDataRequest request = new MasterDataRequest();
        request.setCategoryCodes(new ArrayList<>());
        ResponseEntity<TmbOneServiceResponse<Object>> actualResponse = loanController.fetchMaster("123", request);
        MasterDataResponse actualMasterDataResponse = (MasterDataResponse) actualResponse.getBody().getData();
        Assertions.assertNotNull(actualMasterDataResponse);
        verify(infoService, times(1)).fetchMasterData(any());

    }

    @Test
    void fetchMasterDataHasErrorShouldThrowException() {
        ServiceResponseImp mockResponse = new ServiceResponseImp();
        RslServiceError error = new RslServiceError();
        mockResponse.setError(error);
        when(infoService.fetchMasterData(any())).thenReturn(mockResponse);
        LendingInfoController loanController = new LendingInfoController(infoService);
        MasterDataRequest request = new MasterDataRequest();
        request.setCategoryCodes(new ArrayList<>());
        try {
            loanController.fetchMaster("123",request);
            Assertions.fail("Should have TMBCommonException");
        } catch (TMBCommonException e) {
        }
    }
}
