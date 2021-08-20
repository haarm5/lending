package com.tmb.oneapp.lendingservice.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.tmb.common.exception.model.TMBCommonException;
import com.tmb.common.logger.TMBLogger;
import com.tmb.common.model.legacy.rsl.ws.application.response.Body;
import com.tmb.common.model.legacy.rsl.ws.application.response.ResponseApplication;
import com.tmb.oneapp.lendingservice.client.LoanSubmissionGetApplicationInfoClient;
import com.tmb.oneapp.lendingservice.controller.RslController;
import com.tmb.oneapp.lendingservice.model.documnet.UploadDocumentRequest;
import com.tmb.oneapp.lendingservice.model.documnet.UploadDocumentResponse;
import com.tmb.oneapp.lendingservice.util.RslServiceUtils;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import javax.xml.rpc.ServiceException;
import java.util.ArrayList;
import java.util.List;

@Service
@AllArgsConstructor
public class UploadDocumentService {
    private final LoanSubmissionGetApplicationInfoClient loanSubmissionGetApplicationInfoClient;
    private static final TMBLogger<UploadDocumentService> logger = new TMBLogger<>(UploadDocumentService.class);

    public UploadDocumentResponse upload(UploadDocumentRequest request) throws TMBCommonException, ServiceException, JsonProcessingException {
        UploadDocumentResponse response = new UploadDocumentResponse();
        response.setDocuments(parseDocuments(request));
        Body applicationInfo = getApplicationInfo(request.getCaId());
        response.setAppRefNo(applicationInfo.getAppRefNo());
        response.setAppType(applicationInfo.getAppType());
        response.setProductDescTh(applicationInfo.getProductDescTH());
        return response;
    }

    private List<UploadDocumentResponse.Document> parseDocuments(UploadDocumentRequest request) {
        logger.info("caId: {} ", request.getCaId());
        UploadDocumentResponse.Document docSuccess = UploadDocumentResponse.Document.builder()
                .docCode("ID01")
                .pdfFileName("mock1.pdf")
                .status("success")
                .build();

        UploadDocumentResponse.Document docFail = UploadDocumentResponse.Document.builder()
                .docCode("AD01")
                .pdfFileName("mock2.pdf")
                .status("fail")
                .build();

        List<UploadDocumentResponse.Document> documents = new ArrayList<>();
        documents.add(docSuccess);
        documents.add(docFail);

        return documents;
    }

    private Body getApplicationInfo(long caId) throws ServiceException, TMBCommonException, JsonProcessingException {
        ResponseApplication response = loanSubmissionGetApplicationInfoClient.searchApplicationInfoByCaID(caId);
        RslServiceUtils.checkRslResponse(response.getHeader().getResponseCode(), response.getHeader().getResponseDescriptionEN());
        return response.getBody();
    }

}
