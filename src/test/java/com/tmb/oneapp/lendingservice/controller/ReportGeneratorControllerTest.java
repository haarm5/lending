package com.tmb.oneapp.lendingservice.controller;

import com.tmb.common.exception.model.TMBCommonException;
import com.tmb.common.model.TmbOneServiceResponse;
import com.tmb.oneapp.lendingservice.model.eapp.ReportGeneratorRequest;
import com.tmb.oneapp.lendingservice.model.eapp.ReportGeneratorResponse;
import com.tmb.oneapp.lendingservice.service.ReportGeneratorService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;

import javax.xml.rpc.ServiceException;

import java.io.IOException;
import java.text.ParseException;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.doThrow;

@RunWith(JUnit4.class)
public class ReportGeneratorControllerTest {

    @InjectMocks
    private ReportGeneratorController controller;

    @Mock
    private ReportGeneratorService service;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void generateReport_Success() throws TMBCommonException, ServiceException, IOException, ParseException {
        ReportGeneratorRequest request = new ReportGeneratorRequest();
        request.setCaId("1");
        request.setProductCode("VJ");

        ReportGeneratorResponse response = new ReportGeneratorResponse();
        doReturn(response).when(service).generateEAppReport(any(), any(), any(), any());

        ResponseEntity<TmbOneServiceResponse<ReportGeneratorResponse>> responseEntity = controller.generateReport("correlationId", "crmId", "accountId", request);
        Assertions.assertTrue(responseEntity.getStatusCode().is2xxSuccessful());
    }

    @Test
    public void generateReport_Failed() throws TMBCommonException, ServiceException, IOException, ParseException {
        ReportGeneratorRequest request = new ReportGeneratorRequest();
        request.setCaId("1");
        request.setProductCode("VJ");

        doThrow(IllegalArgumentException.class).when(service).generateEAppReport(any(), any(), any(), any());
        Assertions.assertThrows(TMBCommonException.class, () -> controller.generateReport("correlationId", "crmId", "accountId", request));
    }

}
