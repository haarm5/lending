package com.tmb.oneapp.lendingservice.controller;

import com.tmb.common.model.TmbOneServiceResponse;
import com.tmb.oneapp.lendingservice.model.flexiloan.InstantLoanCalUWRequest;
import com.tmb.oneapp.lendingservice.model.flexiloan.InstantLoanCalUWResponse;
import com.tmb.oneapp.lendingservice.service.FlexiLoanCheckApprovedStatusService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import javax.xml.rpc.ServiceException;
import java.math.BigDecimal;
import java.rmi.RemoteException;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@RunWith(JUnit4.class)
public class FlexiLoanControllerTest {

    FlexiLoanController flexiLoanController;

    @Mock
    FlexiLoanCheckApprovedStatusService flexiLoanCheckApprovedStatusService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
        flexiLoanController = new FlexiLoanController(flexiLoanCheckApprovedStatusService);
    }

    @Test
    public void testCheckCalUWSuccess() throws ServiceException, RemoteException {
        InstantLoanCalUWRequest request = new InstantLoanCalUWRequest();
        request.setCaId(BigDecimal.valueOf(2021052704186761L));
        request.setTriggerFlag("Y");
        request.setProduct("RC01");

        when(flexiLoanCheckApprovedStatusService.checkCalculateUnderwriting(request)).thenReturn(any());

        ResponseEntity<TmbOneServiceResponse<InstantLoanCalUWResponse>> result = flexiLoanController.approveStatus(request);
        assertEquals(HttpStatus.OK.value(), result.getStatusCode().value());

    }

    @Test
    public void testCheckCalUWSFail() throws ServiceException, RemoteException {

        InstantLoanCalUWRequest request = new InstantLoanCalUWRequest();
        request.setCaId(BigDecimal.valueOf(2021052704186775L));
        request.setTriggerFlag("Y");
        request.setProduct("RC01");

        when(flexiLoanCheckApprovedStatusService.checkCalculateUnderwriting(request)).thenThrow(new NullPointerException());

        ResponseEntity<TmbOneServiceResponse<InstantLoanCalUWResponse>> result = flexiLoanController.approveStatus(request);
        assertTrue(result.getStatusCode().isError());
    }
}