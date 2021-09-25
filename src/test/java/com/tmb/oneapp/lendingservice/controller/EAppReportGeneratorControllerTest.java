package com.tmb.oneapp.lendingservice.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.tmb.common.exception.model.TMBCommonException;
import com.tmb.common.model.TmbOneServiceResponse;
import com.tmb.oneapp.lendingservice.model.eapp.GenerateEAppReportRequest;
import com.tmb.oneapp.lendingservice.model.eapp.GenerateEAppReportResponse;
import com.tmb.oneapp.lendingservice.service.EAppReportGeneratorService;
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

import java.rmi.RemoteException;
import java.text.ParseException;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.doThrow;

@RunWith(JUnit4.class)
public class EAppReportGeneratorControllerTest {

    @InjectMocks
    private EAppReportGeneratorController controller;

    @Mock
    private EAppReportGeneratorService service;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void generateReport_Success() throws TMBCommonException, ServiceException, JsonProcessingException, ParseException, RemoteException {
        GenerateEAppReportRequest request = new GenerateEAppReportRequest();
        request.setCaId("1");
        request.setProductCode("VJ");

        GenerateEAppReportResponse response = new GenerateEAppReportResponse();
        doReturn(response).when(service).generateEAppReport(any(), any(), any(), any());

        ResponseEntity<TmbOneServiceResponse<GenerateEAppReportResponse>> responseEntity = controller.generateReport("correlationId", "crmId", new HttpHeaders(), request);
        Assertions.assertTrue(responseEntity.getStatusCode().is2xxSuccessful());
    }

    @Test
    public void generateReport_Failed() throws TMBCommonException, ServiceException, JsonProcessingException, ParseException, RemoteException {
        GenerateEAppReportRequest request = new GenerateEAppReportRequest();
        request.setCaId("1");
        request.setProductCode("VJ");

        doThrow(IllegalArgumentException.class).when(service).generateEAppReport(any(), any(), any(), any());
        Assertions.assertThrows(TMBCommonException.class, () -> controller.generateReport("correlationId", "crmId", new HttpHeaders(), request));
    }

}