package com.tmb.oneapp.lendingservice.service;

import com.itextpdf.text.DocumentException;
import com.tmb.common.exception.model.TMBCommonException;
import com.tmb.common.model.legacy.rsl.ws.application.response.Body;
import com.tmb.common.model.legacy.rsl.ws.application.response.Header;
import com.tmb.common.model.legacy.rsl.ws.application.response.ResponseApplication;
import com.tmb.oneapp.lendingservice.client.LoanSubmissionGetApplicationInfoClient;
import com.tmb.oneapp.lendingservice.client.SFTPClientImp;
import com.tmb.oneapp.lendingservice.constant.ResponseCode;
import com.tmb.oneapp.lendingservice.constant.RslResponseCode;
import com.tmb.oneapp.lendingservice.model.CriteriaCodeEntry;
import com.tmb.oneapp.lendingservice.model.documnet.*;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.Spy;
import org.springframework.http.HttpStatus;

import javax.xml.rpc.ServiceException;
import java.io.IOException;
import java.math.BigDecimal;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@RunWith(JUnit4.class)
public class UploadDocumentServiceTest {

    @Spy
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
    public void upload_Image_Success() throws TMBCommonException, ServiceException, IOException, ParseException {
        UploadDocumentRequest request = new UploadDocumentRequest();
        request.setCaId("1");
        request.setDocCode("ID01");
        request.setFile("data:image/jpeg;base64,base64");
        request.setFileName("test.png");

        doReturn(mockResponseApplication()).when(loanSubmissionGetApplicationInfoClient).searchApplicationInfoByCaID(anyLong());
        doReturn(true).when(sftpClientImp).storeFile(anyList());

        UploadDocumentResponse response = uploadDocumentService.upload("001100000000000000000018593707", request);
        Assertions.assertNotNull(response);
    }

    @Test
    public void upload_Pdf_Success() throws TMBCommonException, ServiceException, IOException, ParseException {
        UploadDocumentRequest request = new UploadDocumentRequest();
        request.setCaId("1");
        request.setDocCode("ID01");
        request.setFile("data:application/pdf;base64,base64");
        request.setFileName("test.pdf");

        doReturn(mockResponseApplication()).when(loanSubmissionGetApplicationInfoClient).searchApplicationInfoByCaID(anyLong());
        doReturn(true).when(sftpClientImp).storeFile(anyList());


        CriteriaCodeEntry entry = new CriteriaCodeEntry();
        entry.setRefEntryCode("test");
        List<CriteriaCodeEntry> docTypeList = new ArrayList<>();
        docTypeList.add(entry);
        doReturn(docTypeList).when(lendingCriteriaInfoService).getBrmsEcmDocTypeByCode(anyString());

        UploadDocumentResponse response = uploadDocumentService.upload("001100000000000000000018593707", request);
        Assertions.assertNotNull(response);
    }

    @Test
    public void submit_Success() throws TMBCommonException, ServiceException, IOException, ParseException, DocumentException {
        SubmitDocumentRequest request = new SubmitDocumentRequest();
        request.setCaId("1");
        List<String> docCodes = new ArrayList<>();
        docCodes.add("ID01");
        request.setDocCodes(docCodes);

        doReturn(mockResponseApplication()).when(loanSubmissionGetApplicationInfoClient).searchApplicationInfoByCaID(anyLong());

        CriteriaCodeEntry entry = new CriteriaCodeEntry();
        entry.setRefEntryCode("test");
        List<CriteriaCodeEntry> docTypeList = new ArrayList<>();
        docTypeList.add(entry);
        doReturn(docTypeList).when(lendingCriteriaInfoService).getBrmsEcmDocTypeByCode(anyString());

        doNothing().when(uploadDocumentService).mergeImagesToPdf(anyString(), anyString(), anyString());
        doReturn(true).when(sftpClientImp).storeFile(anyList());
        doReturn(true).when(sftpClientImp).removeFile(anyList());

        SubmitDocumentResponse response = uploadDocumentService.submit("001100000000000000000018593707", request);
        Assertions.assertEquals("026PL64000674", response.getAppRefNo());
        Assertions.assertEquals("PL", response.getAppType());
        Assertions.assertEquals("Y", response.getNcbConsentFlag());
    }

    @Test
    public void submit_sftpStoreDocuments_Fail() throws TMBCommonException, ServiceException, IOException, ParseException, DocumentException {
        SubmitDocumentRequest request = new SubmitDocumentRequest();
        request.setCaId("1");
        List<String> docCodes = new ArrayList<>();
        docCodes.add("ID01");
        request.setDocCodes(docCodes);

        doReturn(mockResponseApplication()).when(loanSubmissionGetApplicationInfoClient).searchApplicationInfoByCaID(anyLong());

        CriteriaCodeEntry entry = new CriteriaCodeEntry();
        entry.setRefEntryCode("test");
        List<CriteriaCodeEntry> docTypeList = new ArrayList<>();
        docTypeList.add(entry);
        doReturn(docTypeList).when(lendingCriteriaInfoService).getBrmsEcmDocTypeByCode(anyString());

        doNothing().when(uploadDocumentService).mergeImagesToPdf(anyString(), anyString(), anyString());

        TMBCommonException exception = assertThrows(TMBCommonException.class, () -> {
            doThrow(new IllegalArgumentException("error")).when(sftpClientImp).storeFile(anyList());

            uploadDocumentService.submit("001100000000000000000018593707", request);
        });

        Assertions.assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, exception.getStatus());
        Assertions.assertEquals(ResponseCode.SFTP_FAILED.getCode(), exception.getErrorCode());
    }

    @Test
    public void submit_sftpRemoveDocuments_Fail() throws TMBCommonException, ServiceException, IOException, ParseException, DocumentException {
        SubmitDocumentRequest request = new SubmitDocumentRequest();
        request.setCaId("1");
        List<String> docCodes = new ArrayList<>();
        docCodes.add("ID01");
        request.setDocCodes(docCodes);

        doReturn(mockResponseApplication()).when(loanSubmissionGetApplicationInfoClient).searchApplicationInfoByCaID(anyLong());

        CriteriaCodeEntry entry = new CriteriaCodeEntry();
        entry.setRefEntryCode("test");
        List<CriteriaCodeEntry> docTypeList = new ArrayList<>();
        docTypeList.add(entry);
        doReturn(docTypeList).when(lendingCriteriaInfoService).getBrmsEcmDocTypeByCode(anyString());

        doNothing().when(uploadDocumentService).mergeImagesToPdf(anyString(), anyString(), anyString());
        doReturn(true).when(sftpClientImp).storeFile(anyList());
        TMBCommonException exception = assertThrows(TMBCommonException.class, () -> {
            doThrow(new IllegalArgumentException("error")).when(sftpClientImp).removeFile(anyList());

            uploadDocumentService.submit("001100000000000000000018593707", request);
        });

        Assertions.assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, exception.getStatus());
        Assertions.assertEquals(ResponseCode.SFTP_FAILED.getCode(), exception.getErrorCode());
    }

    @Test
    public void submit_DocCodeNotFound() throws TMBCommonException, ServiceException, IOException, DocumentException {
        SubmitDocumentRequest request = new SubmitDocumentRequest();
        request.setCaId("1");
        List<String> docCodes = new ArrayList<>();
        docCodes.add("ID01");
        request.setDocCodes(docCodes);

        doReturn(mockResponseApplication()).when(loanSubmissionGetApplicationInfoClient).searchApplicationInfoByCaID(anyLong());
        doNothing().when(uploadDocumentService).mergeImagesToPdf(anyString(), anyString(), anyString());

        TMBCommonException exception = assertThrows(TMBCommonException.class, () -> {
            doReturn(new ArrayList<>()).when(lendingCriteriaInfoService).getBrmsEcmDocTypeByCode(anyString());

            uploadDocumentService.submit("001100000000000000000018593707", request);
        });

        Assertions.assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, exception.getStatus());
        Assertions.assertEquals(ResponseCode.DATA_NOT_FOUND.getCode(), exception.getErrorCode());
        Assertions.assertEquals("Doc code ID01 is not found.", exception.getErrorMessage());
    }

    @Test
    public void delete_Image_Success() throws TMBCommonException, ServiceException, IOException {
        doReturn(mockResponseApplication()).when(loanSubmissionGetApplicationInfoClient).searchApplicationInfoByCaID(anyLong());
        doReturn(true).when(sftpClientImp).removeFile(anyList());

        DeleteDocumentResponse response = uploadDocumentService.delete("001100000000000000000018593707", "1", "ID01", "png", "test");
        Assertions.assertNotNull(response);
    }

    @Test
    public void delete_Pdf_Success() throws TMBCommonException, ServiceException, IOException {
        doReturn(mockResponseApplication()).when(loanSubmissionGetApplicationInfoClient).searchApplicationInfoByCaID(anyLong());
        doReturn(true).when(sftpClientImp).removeFile(anyList());

        DeleteDocumentResponse response = uploadDocumentService.delete("001100000000000000000018593707", "1", "ID01", "pdf", "test");
        Assertions.assertNotNull(response);
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
