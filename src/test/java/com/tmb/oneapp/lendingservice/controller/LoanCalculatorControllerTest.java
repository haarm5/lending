package com.tmb.oneapp.lendingservice.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.tmb.common.exception.model.TMBCommonException;
import com.tmb.common.model.TmbOneServiceResponse;
import com.tmb.oneapp.lendingservice.model.flexiloan.LoanCalculatorRequest;
import com.tmb.oneapp.lendingservice.model.flexiloan.LoanCalculatorResponse;
import com.tmb.oneapp.lendingservice.service.LoanCalculatorService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;

import javax.xml.rpc.ServiceException;
import java.rmi.RemoteException;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;


@RunWith(JUnit4.class)
class LoanCalculatorControllerTest {
    @InjectMocks
    LoanCalculatorController loanCalculatorController;

    @Mock
    LoanCalculatorService loanCalculatorService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testGetPreloadLoanCalSuccess() throws ServiceException, RemoteException, TMBCommonException, JsonProcessingException {
        LoanCalculatorResponse response = new LoanCalculatorResponse();
        LoanCalculatorRequest request = new LoanCalculatorRequest();
        request.setCaId(2021071404188196L);
        request.setProduct("RC");
        when(loanCalculatorService.getPreloadLoanCalculator(anyLong(),any())).thenReturn(response);
        ResponseEntity<TmbOneServiceResponse<LoanCalculatorResponse>> responseEntity = loanCalculatorController.getPreloadLoanCalculator(request);
        Assertions.assertTrue(responseEntity.getStatusCode().is2xxSuccessful());
    }

    @Test
    public void testGetPreloadLoanCalFail() throws ServiceException, RemoteException, TMBCommonException, JsonProcessingException {
        LoanCalculatorRequest request = new LoanCalculatorRequest();
        request.setCaId(2021071404188196L);
        request.setProduct("RC");
        when(loanCalculatorService.getPreloadLoanCalculator(anyLong(),any())).thenThrow(new IllegalArgumentException());
        ResponseEntity<TmbOneServiceResponse<LoanCalculatorResponse>> responseEntity = loanCalculatorController.getPreloadLoanCalculator(request);
        Assertions.assertTrue(responseEntity.getStatusCode().isError());
    }
}