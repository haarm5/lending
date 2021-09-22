package com.tmb.oneapp.lendingservice.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.itextpdf.text.DocumentException;
import com.tmb.common.exception.model.TMBCommonException;
import com.tmb.common.model.legacy.rsl.ws.application.response.Body;
import com.tmb.common.model.legacy.rsl.ws.application.response.Header;
import com.tmb.common.model.legacy.rsl.ws.application.response.ResponseApplication;
import com.tmb.common.model.legacy.rsl.ws.doc.application.response.ResponseDocApplication;
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
import java.nio.file.Path;
import java.nio.file.Paths;
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
    private RslService rslService;
    @Mock
    private SFTPClientImp sftpClientImp;
    @Mock
    private LendingCriteriaInfoService lendingCriteriaInfoService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void upload_Image_Success() throws TMBCommonException, ServiceException, IOException {
        UploadDocumentRequest request = new UploadDocumentRequest();
        request.setCaId("1");
        request.setDocCode("ID01");
        request.setFile("data:image/jpeg;base64,base64");
        request.setFileName("test.png");

        doReturn(mockResponseApplication()).when(rslService).getLoanSubmissionApplicationInfo(any());
        doReturn(true).when(sftpClientImp).storeFile(anyList());

        UploadDocumentResponse response = uploadDocumentService.upload("001100000000000000000018593707", request);
        Assertions.assertNotNull(response);
    }

    @Test
    public void upload_Pdf_Success() throws TMBCommonException, ServiceException, IOException {
        UploadDocumentRequest request = new UploadDocumentRequest();
        request.setCaId("1");
        request.setDocCode("ID01");
        request.setFile("data:application/pdf;base64,base64");
        request.setFileName("test.pdf");

        doReturn(mockResponseApplication()).when(rslService).getLoanSubmissionApplicationInfo(any());
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
    public void submit_Success() throws TMBCommonException, ServiceException, IOException, DocumentException {
        SubmitDocumentRequest request = new SubmitDocumentRequest();
        request.setCaId("1");
        List<String> docCodes = new ArrayList<>();
        docCodes.add("ID01");
        request.setDocCodes(docCodes);

        doReturn(mockResponseApplication()).when(rslService).getLoanSubmissionApplicationInfo(any());

        CriteriaCodeEntry entry = new CriteriaCodeEntry();
        entry.setRefEntryCode("test");
        List<CriteriaCodeEntry> docTypeList = new ArrayList<>();
        docTypeList.add(entry);
        doReturn(docTypeList).when(lendingCriteriaInfoService).getBrmsEcmDocTypeByCode(anyString());

        doNothing().when(uploadDocumentService).mergeImagesToPdf(anyString(), anyString(), anyString());
        doReturn(new ArrayList<>()).when(uploadDocumentService).listFiles(any());
        doReturn(true).when(sftpClientImp).storeFile(anyList());

        SubmitDocumentResponse response = uploadDocumentService.submit("001100000000000000000018593707", request);
        Assertions.assertEquals("026PL64000674", response.getAppRefNo());
        Assertions.assertEquals("PL", response.getAppType());
        Assertions.assertEquals("Y", response.getNcbConsentFlag());
    }

    @Test
    public void submit_sftpStoreDocuments_Fail() throws TMBCommonException, ServiceException, IOException, DocumentException {
        SubmitDocumentRequest request = new SubmitDocumentRequest();
        request.setCaId("1");
        List<String> docCodes = new ArrayList<>();
        docCodes.add("ID01");
        request.setDocCodes(docCodes);

        doReturn(mockResponseApplication()).when(rslService).getLoanSubmissionApplicationInfo(any());

        CriteriaCodeEntry entry = new CriteriaCodeEntry();
        entry.setRefEntryCode("test");
        List<CriteriaCodeEntry> docTypeList = new ArrayList<>();
        docTypeList.add(entry);
        doReturn(docTypeList).when(lendingCriteriaInfoService).getBrmsEcmDocTypeByCode(anyString());

        doNothing().when(uploadDocumentService).mergeImagesToPdf(anyString(), anyString(), anyString());
        List<Path> filePaths = new ArrayList<>();
        filePaths.add(Paths.get("path"));
        doReturn(filePaths).when(uploadDocumentService).listFiles(any());

        TMBCommonException exception = assertThrows(TMBCommonException.class, () -> {
            doThrow(new IllegalArgumentException("error")).when(sftpClientImp).storeFile(anyList());
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

        doReturn(mockResponseApplication()).when(rslService).getLoanSubmissionApplicationInfo(any());
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
    public void submitMore_Success() throws TMBCommonException, ServiceException, IOException, DocumentException {
        SubmitDocumentRequest request = new SubmitDocumentRequest();
        request.setCaId("1");
        List<String> docCodes = new ArrayList<>();
        docCodes.add("ID01");
        request.setDocCodes(docCodes);

        doReturn(mockResponseApplication()).when(rslService).getLoanSubmissionApplicationInfo(any());

        CriteriaCodeEntry entry = new CriteriaCodeEntry();
        entry.setRefEntryCode("test");
        List<CriteriaCodeEntry> docTypeList = new ArrayList<>();
        docTypeList.add(entry);
        doReturn(docTypeList).when(lendingCriteriaInfoService).getBrmsEcmDocTypeByCode(anyString());

        doNothing().when(uploadDocumentService).mergeImagesToPdf(anyString(), anyString(), anyString());
        doReturn(new ArrayList<>()).when(uploadDocumentService).listFiles(any());
        doReturn(true).when(sftpClientImp).storeFile(anyList());

        mockResponseDocApplicationSuccess();

        SubmitDocumentResponse response = uploadDocumentService.submitMore("001100000000000000000018593707", request);
        Assertions.assertEquals("026PL64000674", response.getAppRefNo());
        Assertions.assertEquals("PL", response.getAppType());
        Assertions.assertEquals("Y", response.getNcbConsentFlag());
    }

    @Test
    public void submitMore_sftpStoreDocuments_Fail() throws TMBCommonException, ServiceException, IOException, DocumentException {
        SubmitDocumentRequest request = new SubmitDocumentRequest();
        request.setCaId("1");
        List<String> docCodes = new ArrayList<>();
        docCodes.add("ID01");
        request.setDocCodes(docCodes);

        doReturn(mockResponseApplication()).when(rslService).getLoanSubmissionApplicationInfo(any());

        CriteriaCodeEntry entry = new CriteriaCodeEntry();
        entry.setRefEntryCode("test");
        List<CriteriaCodeEntry> docTypeList = new ArrayList<>();
        docTypeList.add(entry);
        doReturn(docTypeList).when(lendingCriteriaInfoService).getBrmsEcmDocTypeByCode(anyString());

        doNothing().when(uploadDocumentService).mergeImagesToPdf(anyString(), anyString(), anyString());
        List<Path> filePaths = new ArrayList<>();
        filePaths.add(Paths.get("path"));
        doReturn(filePaths).when(uploadDocumentService).listFiles(any());

        TMBCommonException exception = assertThrows(TMBCommonException.class, () -> {
            doThrow(new IllegalArgumentException("error")).when(sftpClientImp).storeFile(anyList());
            uploadDocumentService.submitMore("001100000000000000000018593707", request);
        });

        Assertions.assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, exception.getStatus());
        Assertions.assertEquals(ResponseCode.SFTP_FAILED.getCode(), exception.getErrorCode());
    }

    @Test
    public void submitMore_DocCodeNotFound() throws TMBCommonException, ServiceException, IOException, DocumentException {
        SubmitDocumentRequest request = new SubmitDocumentRequest();
        request.setCaId("1");
        List<String> docCodes = new ArrayList<>();
        docCodes.add("ID01");
        request.setDocCodes(docCodes);

        doReturn(mockResponseApplication()).when(rslService).getLoanSubmissionApplicationInfo(any());
        doNothing().when(uploadDocumentService).mergeImagesToPdf(anyString(), anyString(), anyString());

        TMBCommonException exception = assertThrows(TMBCommonException.class, () -> {
            doReturn(new ArrayList<>()).when(lendingCriteriaInfoService).getBrmsEcmDocTypeByCode(anyString());

            uploadDocumentService.submitMore("001100000000000000000018593707", request);
        });

        Assertions.assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, exception.getStatus());
        Assertions.assertEquals(ResponseCode.DATA_NOT_FOUND.getCode(), exception.getErrorCode());
        Assertions.assertEquals("Doc code ID01 is not found.", exception.getErrorMessage());
    }

    @Test
    public void delete_Image_Success() throws TMBCommonException, ServiceException, IOException {
        doReturn(mockResponseApplication()).when(rslService).getLoanSubmissionApplicationInfo(any());
        doNothing().when(uploadDocumentService).removeFile(anyString());
        doReturn(true).when(sftpClientImp).removeFile(anyList());

        DeleteDocumentResponse response = uploadDocumentService.delete("001100000000000000000018593707", "1", "ID01", "png", "test");
        Assertions.assertNotNull(response);
    }

    @Test
    public void delete_Pdf_Success() throws TMBCommonException, ServiceException, IOException {
        doReturn(mockResponseApplication()).when(rslService).getLoanSubmissionApplicationInfo(any());
        doNothing().when(uploadDocumentService).removeFile(anyString());
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

    private void mockResponseDocApplicationSuccess() throws TMBCommonException, ServiceException, JsonProcessingException {
        ResponseDocApplication response = new ResponseDocApplication();

        com.tmb.common.model.legacy.rsl.ws.doc.application.response.Header header = new com.tmb.common.model.legacy.rsl.ws.doc.application.response.Header();
        header.setResponseCode(RslResponseCode.SUCCESS.getCode());
        header.setResponseDescriptionEN(RslResponseCode.SUCCESS.getMessage());
        response.setHeader(header);

        com.tmb.common.model.legacy.rsl.ws.doc.application.response.Body body = new com.tmb.common.model.legacy.rsl.ws.doc.application.response.Body();
        response.setBody(body);

        doReturn(response).when(rslService).updateIncompleteDocApplication(any());
    }
}
