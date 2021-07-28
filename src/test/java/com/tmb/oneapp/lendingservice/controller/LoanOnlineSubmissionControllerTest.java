package com.tmb.oneapp.lendingservice.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.tmb.common.exception.model.TMBCommonException;
import com.tmb.common.model.TmbOneServiceResponse;
import com.tmb.common.model.legacy.rsl.ws.application.save.response.ResponseApplication;
import com.tmb.oneapp.lendingservice.model.loanonline.IncomeInfo;
import com.tmb.oneapp.lendingservice.model.loanonline.LoanSubmissionCreateApplicationReq;
import com.tmb.oneapp.lendingservice.service.LoanOnlineSubmissionCheckWaiveDocService;
import com.tmb.oneapp.lendingservice.service.LoanSubmissionCreateApplicationService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;
import javax.xml.rpc.ServiceException;
import java.math.BigDecimal;
import java.rmi.RemoteException;
import static org.junit.Assert.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

class LoanOnlineSubmissionControllerTest {

    LoanOnlineSubmissionController loanOnlineSubmissionController;

    @Mock
    LoanOnlineSubmissionCheckWaiveDocService loanOnlineSubmissionCheckWaiveDocService;

    @Mock
    LoanSubmissionCreateApplicationService loanSubmissionCreateApplicationService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
        loanOnlineSubmissionController = new LoanOnlineSubmissionController(loanSubmissionCreateApplicationService, loanOnlineSubmissionCheckWaiveDocService);
    }

    @Test
    public void testGetIncomeInfoByRmIdSuccess() throws ServiceException, RemoteException, TMBCommonException, JsonProcessingException {
        IncomeInfo res = new IncomeInfo();
        res.setIncomeAmount(BigDecimal.valueOf(100));
        when(loanOnlineSubmissionCheckWaiveDocService.getIncomeInfoByRmId(any())).thenReturn(res);
        ResponseEntity<TmbOneServiceResponse<IncomeInfo>> responseEntity = loanOnlineSubmissionController.getIncomeInfo("rmid");
        assertTrue(responseEntity.getStatusCode().is2xxSuccessful());
    }

    @Test
    public void testGetIncomeInfoByRmIdFail() throws ServiceException, RemoteException, TMBCommonException, JsonProcessingException {
        IncomeInfo res = new IncomeInfo();
        res.setIncomeAmount(BigDecimal.valueOf(100));
        when(loanOnlineSubmissionCheckWaiveDocService.getIncomeInfoByRmId(any())).thenThrow(new IllegalArgumentException());
        ResponseEntity<TmbOneServiceResponse<IncomeInfo>> responseEntity = loanOnlineSubmissionController.getIncomeInfo("rmid");
        assertTrue(responseEntity.getStatusCode().isError());
    }

    @Test
    public void testCreateApplicationSuccess() throws ServiceException, RemoteException, TMBCommonException {
        ResponseApplication res = new ResponseApplication();
        when(loanSubmissionCreateApplicationService.createApplication(any(),any())).thenReturn(res);
        ResponseEntity<TmbOneServiceResponse<ResponseApplication>> responseEntity = loanOnlineSubmissionController.createApplication("rmid", new LoanSubmissionCreateApplicationReq());
        assertTrue(responseEntity.getStatusCode().is2xxSuccessful());
    }

    @Test
    public void testCreateApplicationFail() throws ServiceException, RemoteException, TMBCommonException {
        ResponseApplication res = new ResponseApplication();
        when(loanSubmissionCreateApplicationService.createApplication(any(),any())).thenThrow(new IllegalArgumentException());
        ResponseEntity<TmbOneServiceResponse<ResponseApplication>> responseEntity = loanOnlineSubmissionController.createApplication("rmid", new LoanSubmissionCreateApplicationReq());
        assertTrue(responseEntity.getStatusCode().isError());
    }

}
