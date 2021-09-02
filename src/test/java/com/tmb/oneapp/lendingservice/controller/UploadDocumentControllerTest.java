package com.tmb.oneapp.lendingservice.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
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
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.mock.web.MockMultipartFile;

import javax.xml.rpc.ServiceException;

import static org.mockito.ArgumentMatchers.*;
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
    public void uploadDocument_Success() throws TMBCommonException, ServiceException, JsonProcessingException {
        UploadDocumentRequest request = new UploadDocumentRequest();
        request.setDocCode("ID01");
        request.setCaId("1");
        MockMultipartFile file
                = new MockMultipartFile(
                "file",
                "hello.txt",
                MediaType.TEXT_PLAIN_VALUE,
                "Hello, World!".getBytes()
        );
        request.setFile(file);

        UploadDocumentResponse response = new UploadDocumentResponse();
        doReturn(response).when(uploadDocumentService).upload(anyString(), any(), anyLong(), any());

        ResponseEntity<TmbOneServiceResponse<UploadDocumentResponse>> responseEntity = uploadDocumentController.uploadDocument("correlationId", "crmId", request);
        Assertions.assertTrue(responseEntity.getStatusCode().is2xxSuccessful());
    }
}
