package com.tmb.oneapp.lendingservice.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.tmb.common.exception.model.TMBCommonException;
import com.tmb.common.model.legacy.rsl.ws.application.save.response.Body;
import com.tmb.common.model.legacy.rsl.ws.application.save.response.Header;
import com.tmb.common.model.legacy.rsl.ws.application.save.response.ResponseApplication;
import com.tmb.common.model.legacy.rsl.ws.incomemodel.response.ResponseIncomeModel;
import com.tmb.oneapp.lendingservice.client.LoanSubmissionCreateApplicationClient;
import com.tmb.oneapp.lendingservice.client.LoanSubmissionGetIncomeModelInfoClient;
import com.tmb.oneapp.lendingservice.model.loanonline.LoanSubmissionCreateApplicationReq;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import javax.xml.rpc.ServiceException;
import java.rmi.RemoteException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;


@RunWith(JUnit4.class)
class LoanSubmissionCreateApplicationServiceTest {


    @Mock
    private LoanSubmissionCreateApplicationClient loanSubmissionCreateApplicationClient;
    @Mock
    private LoanSubmissionGetIncomeModelInfoClient loanSubmissionGetIncomeModelInfoClient;

    LoanSubmissionCreateApplicationService loanSubmissionCreateApplicationService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
        loanSubmissionCreateApplicationService = new LoanSubmissionCreateApplicationService(loanSubmissionCreateApplicationClient, loanSubmissionGetIncomeModelInfoClient);
    }

    @Test
    public void testCreateApplicationTypeCC() throws ServiceException, RemoteException, TMBCommonException, JsonProcessingException {
        LoanSubmissionCreateApplicationReq req = new LoanSubmissionCreateApplicationReq();
        req.setProductCode("CC");

        Header appHeader = new Header();
        appHeader.setResponseCode("MSG_000");
        Body body = new Body();
        body.setAppType("test");
        ResponseApplication res = new ResponseApplication();
        res.setHeader(appHeader);
        res.setBody(body);
        when(loanSubmissionCreateApplicationClient.createApplication(any())).thenReturn(res);

        com.tmb.common.model.legacy.rsl.ws.incomemodel.response.Header header = new com.tmb.common.model.legacy.rsl.ws.incomemodel.response.Header();
        header.setResponseCode("MSG_000");
        ResponseIncomeModel incomeModel = new ResponseIncomeModel();
        incomeModel.setHeader(header);
        when(loanSubmissionGetIncomeModelInfoClient.getIncomeInfo(any())).thenReturn(incomeModel);

        ResponseApplication result = loanSubmissionCreateApplicationService.createApplication(req, "rmId");
        assertEquals("test", result.getBody().getAppType());
    }

    @Test
    public void testCreateApplicationTypePL() throws ServiceException, RemoteException, TMBCommonException, JsonProcessingException {
        LoanSubmissionCreateApplicationReq req = new LoanSubmissionCreateApplicationReq();
        req.setProductCode("PL");

        Header appHeader = new Header();
        appHeader.setResponseCode("MSG_000");
        Body body = new Body();
        body.setAppType("test");
        ResponseApplication res = new ResponseApplication();
        res.setHeader(appHeader);
        res.setBody(body);
        when(loanSubmissionCreateApplicationClient.createApplication(any())).thenReturn(res);

        com.tmb.common.model.legacy.rsl.ws.incomemodel.response.Header header = new com.tmb.common.model.legacy.rsl.ws.incomemodel.response.Header();
        header.setResponseCode("MSG_000");
        ResponseIncomeModel incomeModel = new ResponseIncomeModel();
        incomeModel.setHeader(header);
        when(loanSubmissionGetIncomeModelInfoClient.getIncomeInfo(any())).thenReturn(incomeModel);

        ResponseApplication result = loanSubmissionCreateApplicationService.createApplication(req, "rmId");
        assertEquals("test", result.getBody().getAppType());
    }

}