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
import org.springframework.http.ResponseEntity;

import javax.xml.rpc.ServiceException;
import java.util.ArrayList;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
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
        request.setCaId(1L);

        List<UploadDocumentRequest.Document> documents = new ArrayList<>();
        UploadDocumentRequest.Document document = new UploadDocumentRequest.Document();
        documents.add(document);
        request.setDocuments(documents);

        UploadDocumentResponse response = new UploadDocumentResponse();
        doReturn(response).when(uploadDocumentService).upload(any());

        ResponseEntity<TmbOneServiceResponse<UploadDocumentResponse>> responseEntity = uploadDocumentController.uploadDocument("correlationId", "crmId", request);
        Assertions.assertEquals(true, responseEntity.getStatusCode().is2xxSuccessful());
    }
}
