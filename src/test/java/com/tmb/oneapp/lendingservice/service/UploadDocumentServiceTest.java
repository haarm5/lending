package com.tmb.oneapp.lendingservice.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.tmb.common.exception.model.TMBCommonException;
import com.tmb.common.model.legacy.rsl.ws.application.response.Body;
import com.tmb.common.model.legacy.rsl.ws.application.response.Header;
import com.tmb.common.model.legacy.rsl.ws.application.response.ResponseApplication;
import com.tmb.oneapp.lendingservice.client.LoanSubmissionGetApplicationInfoClient;
import com.tmb.oneapp.lendingservice.client.SFTPClientImp;
import com.tmb.oneapp.lendingservice.constant.RslResponseCode;
import com.tmb.oneapp.lendingservice.model.CriteriaCodeEntry;
import com.tmb.oneapp.lendingservice.model.documnet.UploadDocumentResponse;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;

import javax.xml.rpc.ServiceException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.doReturn;
@RunWith(JUnit4.class)
public class UploadDocumentServiceTest {

    @InjectMocks
    private UploadDocumentService uploadDocumentService;

    @Mock
    private LoanSubmissionGetApplicationInfoClient loanSubmissionGetApplicationInfoClient;
    @Mock
    private SFTPClientImp sftpClientImp;
    @Mock
    private LendingCriteriaInfoService lendingCriteriaInfoService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void upload_Success() throws TMBCommonException, ServiceException, JsonProcessingException {
        MockMultipartFile file
                = new MockMultipartFile(
                "file",
                "hello.txt",
                MediaType.TEXT_PLAIN_VALUE,
                "Hello, World!".getBytes()
        );

        doReturn(mockResponseApplication()).when(loanSubmissionGetApplicationInfoClient).searchApplicationInfoByCaID(anyLong());
        doReturn(true).when(sftpClientImp).storeFile(anyList());

        CriteriaCodeEntry entry = new CriteriaCodeEntry();
        entry.setRefEntryCode("test");
        List<CriteriaCodeEntry> docTypeList = new ArrayList<>();
        docTypeList.add(entry);
        doReturn(docTypeList).when(lendingCriteriaInfoService).getBrmsEcmDocTypeByCode(anyString());

        UploadDocumentResponse response = uploadDocumentService.upload("001100000000000000000018593707", file, 1L, "ID01");
        Assertions.assertEquals("success", response.getStatus());
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
