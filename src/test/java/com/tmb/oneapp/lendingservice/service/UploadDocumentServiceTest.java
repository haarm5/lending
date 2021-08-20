package com.tmb.oneapp.lendingservice.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.tmb.common.exception.model.TMBCommonException;
import com.tmb.common.model.legacy.rsl.ws.application.response.Body;
import com.tmb.common.model.legacy.rsl.ws.application.response.Header;
import com.tmb.common.model.legacy.rsl.ws.application.response.ResponseApplication;
import com.tmb.oneapp.lendingservice.client.LoanSubmissionGetApplicationInfoClient;
import com.tmb.oneapp.lendingservice.constant.RslResponseCode;
import com.tmb.oneapp.lendingservice.model.documnet.UploadDocumentRequest;
import com.tmb.oneapp.lendingservice.model.documnet.UploadDocumentResponse;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.doReturn;

import javax.xml.rpc.ServiceException;
import java.math.BigDecimal;

@RunWith(JUnit4.class)
public class UploadDocumentServiceTest {

    @InjectMocks
    private UploadDocumentService uploadDocumentService;

    @Mock
    private LoanSubmissionGetApplicationInfoClient loanSubmissionGetApplicationInfoClient;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void upload_Success() throws TMBCommonException, ServiceException, JsonProcessingException {
        UploadDocumentRequest request = new UploadDocumentRequest();

        doReturn(mockResponseApplication()).when(loanSubmissionGetApplicationInfoClient).searchApplicationInfoByCaID(anyLong());

        UploadDocumentResponse response = uploadDocumentService.upload(request);
        Assertions.assertEquals("success", response.getDocuments().get(0).getStatus());
    }

    private ResponseApplication mockResponseApplication() {
        ResponseApplication response = new ResponseApplication();
        Header header = new Header();
        header.setResponseCode(RslResponseCode.SUCCESS.getCode());

        Body body = new Body();
        body.setAppRefNo("026PL64000674");
        body.setAppType("PL");
        body.setApplicationDate("2021-08-05T10:37:18.000Z");
        body.setBookBranchCode("026");
        body.setBranchCode("026");
        body.setCaId(BigDecimal.valueOf(2021080504188295L));
        body.setMemberref("MIB026PL64000674");
        body.setNatureOfRequest("11");
        body.setNcbConsentFlag("Y");
        body.setProduct("RC");
        body.setProductDescEN("ttb flash card");
        body.setProductDescTH("บัตรกดเงินสด ทีทีบี แฟลซ");
        body.setSubProduct("RC01");
        body.setSubProductDescEN("ttb flash card");
        body.setProductDescTH("บัตรกดเงินสด ทีทีบี แฟลช");

        response.setHeader(header);
        response.setBody(body);
        return response;
    }
}
