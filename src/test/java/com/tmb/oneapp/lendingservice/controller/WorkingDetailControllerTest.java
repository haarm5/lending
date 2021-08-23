package com.tmb.oneapp.lendingservice.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.tmb.common.exception.model.TMBCommonException;
import com.tmb.common.model.TmbOneServiceResponse;
import com.tmb.common.model.legacy.rsl.ws.application.save.response.ResponseApplication;
import com.tmb.common.model.legacy.rsl.ws.individual.update.response.ResponseIndividual;
import com.tmb.oneapp.lendingservice.model.loanonline.UpdateWorkingDetailRequest;
import com.tmb.oneapp.lendingservice.service.LoanOnlineSubmissionUpdateWorkingDetailService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;

import javax.xml.rpc.ServiceException;
import java.rmi.RemoteException;

import static org.junit.Assert.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;


@RunWith(JUnit4.class)
class WorkingDetailControllerTest {

    WorkingDetailController workingDetailController;

    @Mock
    private LoanOnlineSubmissionUpdateWorkingDetailService workingDetailUpdateWorkingDetailService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
        workingDetailController = new WorkingDetailController(workingDetailUpdateWorkingDetailService);
    }

    @Test
    public void  testUpdateWorkingDetailSuccess() throws ServiceException, TMBCommonException, RemoteException, JsonProcessingException {
        when(workingDetailUpdateWorkingDetailService.updateWorkDetail(any())).thenReturn(new ResponseIndividual());
        ResponseEntity<TmbOneServiceResponse<ResponseApplication>> responseEntity = workingDetailController.updateWorkingDetail(new UpdateWorkingDetailRequest());
        assertTrue(responseEntity.getStatusCode().is2xxSuccessful());
    }

    @Test
    public void  testUpdateWorkingDetailFail() throws ServiceException, TMBCommonException, RemoteException, JsonProcessingException {
        when(workingDetailUpdateWorkingDetailService.updateWorkDetail(any())).thenThrow(new IllegalArgumentException());
        ResponseEntity<TmbOneServiceResponse<ResponseApplication>> responseEntity = workingDetailController.updateWorkingDetail(new UpdateWorkingDetailRequest());
        assertTrue(responseEntity.getStatusCode().isError());
    }
}