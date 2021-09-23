package com.tmb.oneapp.lendingservice.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.tmb.common.exception.model.TMBCommonException;
import com.tmb.common.model.TmbOneServiceResponse;
import com.tmb.common.model.TmbStatus;
import com.tmb.common.model.legacy.rsl.common.ob.checklist.Checklist;
import com.tmb.common.model.legacy.rsl.ws.application.response.ResponseApplication;
import com.tmb.common.model.legacy.rsl.ws.checklist.response.Body;
import com.tmb.common.model.legacy.rsl.ws.checklist.response.Header;
import com.tmb.common.model.legacy.rsl.ws.checklist.response.ResponseChecklist;
import com.tmb.oneapp.lendingservice.client.LoanSubmissionGetChecklistInfoClient;
import com.tmb.oneapp.lendingservice.constant.ResponseCode;
import com.tmb.oneapp.lendingservice.constant.RslResponseCode;
import com.tmb.oneapp.lendingservice.model.personal.ChecklistRequest;
import com.tmb.oneapp.lendingservice.model.personal.ChecklistResponse;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import javax.xml.rpc.ServiceException;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@RunWith(JUnit4.class)
public class LoanOnlineSubmissionGetDocumentListServiceTest {
    @Mock
    private LoanSubmissionGetChecklistInfoClient checklistInfoClient;
    @Mock
    private UploadDocumentService uploadDocumentService;

    @InjectMocks
    LoanOnlineSubmissionGetDocumentListService loanOnlineSubmissionGetDocumentListService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testGetChecklistSuccess() throws ServiceException, TMBCommonException, IOException {
        ChecklistRequest request = new ChecklistRequest();
        request.setCaId(2021081104188603L);
        request.setIncompleteDocFlag("N");
        TmbOneServiceResponse<ResponseChecklist> oneServiceResponse = new TmbOneServiceResponse<>();

        oneServiceResponse.setStatus(new TmbStatus(ResponseCode.SUCCESS.getCode(), "success", "lending-service"));

        doReturn(mockChecklistResponseData().getData()).when(checklistInfoClient).getChecklistInfo(anyLong(), anyString());
        doReturn(mockResponseApplication().getBody()).when(uploadDocumentService).getApplicationInfo(anyString());
        doNothing().when(uploadDocumentService).removeDirectory(anyString());

        List<ChecklistResponse> checklistResponses = loanOnlineSubmissionGetDocumentListService.getDocuments("001100000000000000000018593707", request.getCaId());
        Assertions.assertNotNull(checklistResponses);

    }

    @Test
    public void testGetChecklistFail() throws ServiceException, TMBCommonException, JsonProcessingException {
        ChecklistRequest request = new ChecklistRequest();
        request.setCaId(2021071404188196L);
        request.setIncompleteDocFlag("N");

        TmbOneServiceResponse<ResponseChecklist> oneServiceResponse = new TmbOneServiceResponse<>();

        oneServiceResponse.setStatus(new TmbStatus(ResponseCode.FAILED.getCode(), "failed", "lending-service"));

        when(checklistInfoClient.getChecklistInfo(anyLong(), anyString())).thenReturn(oneServiceResponse.getData());

        assertThrows(Exception.class, () ->
                loanOnlineSubmissionGetDocumentListService.getDocuments("crmId", request.getCaId()));

    }

    @Test
    public void testGetMoreChecklistSuccess() throws ServiceException, TMBCommonException, IOException {
        ChecklistRequest request = new ChecklistRequest();
        request.setCaId(2021081104188603L);
        request.setIncompleteDocFlag("Y");
        TmbOneServiceResponse<ResponseChecklist> oneServiceResponse = new TmbOneServiceResponse<>();

        oneServiceResponse.setStatus(new TmbStatus(ResponseCode.SUCCESS.getCode(), "success", "lending-service"));

        when(checklistInfoClient.getChecklistInfo(anyLong(), anyString())).thenReturn(mockChecklistResponseData().getData());
        doReturn(mockResponseApplication().getBody()).when(uploadDocumentService).getApplicationInfo(anyString());
        doNothing().when(uploadDocumentService).removeDirectory(anyString());

        List<ChecklistResponse> checklistResponses = loanOnlineSubmissionGetDocumentListService.getDocuments("001100000000000000000018593707", request.getCaId());
        Assertions.assertNotNull(checklistResponses);

    }

    @Test
    public void testGetMoreChecklistFail() throws ServiceException, TMBCommonException, JsonProcessingException {
        ChecklistRequest request = new ChecklistRequest();
        request.setCaId(2021071404188196L);
        request.setIncompleteDocFlag("Y");

        TmbOneServiceResponse<ResponseChecklist> oneServiceResponse = new TmbOneServiceResponse<>();

        oneServiceResponse.setStatus(new TmbStatus(ResponseCode.FAILED.getCode(), "failed", "lending-service"));

        when(checklistInfoClient.getChecklistInfo(anyLong(), anyString())).thenReturn(oneServiceResponse.getData());

        assertThrows(Exception.class, () ->
                loanOnlineSubmissionGetDocumentListService.getDocuments("crmId", request.getCaId()));

    }

    private TmbOneServiceResponse<ResponseChecklist> mockChecklistResponseData() {
        TmbOneServiceResponse<ResponseChecklist> oneServiceResponse = new TmbOneServiceResponse<>();
        ResponseChecklist responseChecklist = new ResponseChecklist();
        com.tmb.common.model.legacy.rsl.ws.checklist.response.Body body = new Body();

        Header header = new Header();
        header.setResponseCode("MSG_000");
        Checklist checklist = new Checklist();
        Checklist[] checklists = new Checklist[1];
        checklist.setChecklistType("CC");
        checklist.setCifRelCode("M");
        checklist.setStatus("ACTIVE");
        checklist.setDocDescription("xx");
        checklist.setDocId(BigDecimal.ONE);
        checklist.setDocumentCode("ID01");
        checklist.setIncompletedDocReasonCd("xx");
        checklist.setIncompletedDocReasonDesc("xx");
        checklist.setId(BigDecimal.ONE);
        checklist.setIsMandatory("Y");
        checklist.setLosCifId(BigDecimal.ONE);
        checklists[0] = checklist;

        body.setCustomerChecklists(checklists);
        responseChecklist.setBody(body);
        oneServiceResponse.setStatus(new TmbStatus(ResponseCode.SUCCESS.getCode(), "success", "lending-service"));
        oneServiceResponse.setData(responseChecklist);
        return oneServiceResponse;
    }

    private ResponseApplication mockResponseApplication() {
        ResponseApplication response = new ResponseApplication();
        com.tmb.common.model.legacy.rsl.ws.application.response.Header header = new com.tmb.common.model.legacy.rsl.ws.application.response.Header();
        header.setResponseCode(RslResponseCode.SUCCESS.getCode());

        com.tmb.common.model.legacy.rsl.ws.application.response.Body body = new com.tmb.common.model.legacy.rsl.ws.application.response.Body();
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
