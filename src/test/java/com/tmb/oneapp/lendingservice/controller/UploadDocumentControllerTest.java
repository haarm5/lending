package com.tmb.oneapp.lendingservice.controller;

import com.tmb.common.exception.model.TMBCommonException;
import com.tmb.common.model.TmbOneServiceResponse;
import com.tmb.oneapp.lendingservice.model.documnet.UploadDocumentRequest;
import com.tmb.oneapp.lendingservice.model.documnet.UploadDocumentResponse;
import com.tmb.oneapp.lendingservice.service.UploadDocumentService;
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
import java.io.IOException;
import java.text.ParseException;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doReturn;

@RunWith(JUnit4.class)
public class UploadDocumentControllerTest {

    @InjectMocks
    private UploadDocumentController uploadDocumentController;

    @Mock
    private UploadDocumentService uploadDocumentService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void uploadDocument_Success() throws TMBCommonException, IOException, ServiceException, ParseException {
        UploadDocumentRequest request = new UploadDocumentRequest();
        request.setCaId("1");
        request.setDocCode("ID01");
        request.setFile("base64");

        UploadDocumentResponse response = new UploadDocumentResponse();
        doReturn(response).when(uploadDocumentService).upload(anyString(), any());

        ResponseEntity<TmbOneServiceResponse<UploadDocumentResponse>> responseEntity = uploadDocumentController.uploadDocument("correlationId", "crmId", request);
        Assertions.assertTrue(responseEntity.getStatusCode().is2xxSuccessful());
    }
}
