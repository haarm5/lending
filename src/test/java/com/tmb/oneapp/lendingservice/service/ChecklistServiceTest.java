package com.tmb.oneapp.lendingservice.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.tmb.common.exception.model.TMBCommonException;
import com.tmb.common.model.TmbOneServiceResponse;
import com.tmb.common.model.TmbStatus;
import com.tmb.common.model.legacy.rsl.common.ob.checklist.Checklist;
import com.tmb.common.model.legacy.rsl.ws.checklist.response.Body;
import com.tmb.common.model.legacy.rsl.ws.checklist.response.Header;
import com.tmb.common.model.legacy.rsl.ws.checklist.response.ResponseChecklist;
import com.tmb.oneapp.lendingservice.client.LoanSubmissionGetChecklistInfoClient;
import com.tmb.oneapp.lendingservice.constant.ResponseCode;
import com.tmb.oneapp.lendingservice.model.personal.ChecklistRequest;
import com.tmb.oneapp.lendingservice.model.personal.ChecklistResponse;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import javax.xml.rpc.ServiceException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;

@RunWith(JUnit4.class)
public class ChecklistServiceTest {
    @Mock
    private LoanSubmissionGetChecklistInfoClient checklistInfoClient;

    ChecklistService checklistService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
        checklistService = new ChecklistService(checklistInfoClient);
    }

    @Test
    public void testGetChecklistSuccess() throws ServiceException, TMBCommonException, JsonProcessingException {
        ChecklistRequest request = new ChecklistRequest();
        request.setCaId(2021081104188603L);
        TmbOneServiceResponse<ResponseChecklist> oneServiceResponse = new TmbOneServiceResponse<ResponseChecklist>();

        oneServiceResponse.setStatus(new TmbStatus(ResponseCode.SUCCESS.getCode(), "success", "lending-service"));

        when(checklistInfoClient.getChecklistInfo(anyLong())).thenReturn(mockChecklistResponseData().getData());
        ChecklistResponse checklistResponse = new ChecklistResponse();
        List<ChecklistResponse> checklistResponses = new ArrayList<>();
        checklistResponse.setChecklistType("CC");
        checklistResponse.setCifRelCode("M");
        checklistResponse.setStatus("ACTIVE");
        checklistResponse.setDocDescription("xx");
        checklistResponse.setDocId(BigDecimal.ONE);
        checklistResponse.setDocumentCode("ID01");
        checklistResponse.setIncompletedDocReasonCd("xx");
        checklistResponse.setIncompletedDocReasonDesc("xx");
        checklistResponse.setId(BigDecimal.ONE);
        checklistResponse.setIsMandatory("Y");
        checklistResponse.setLosCifId(BigDecimal.ONE);
        checklistResponses.add(checklistResponse);

        checklistResponses = checklistService.getDocuments(request.getCaId());
        Assertions.assertNotNull(checklistResponses);

    }

    @Test
    public void testGetChecklistFail() throws ServiceException, TMBCommonException, JsonProcessingException {
        ChecklistRequest request = new ChecklistRequest();
        request.setCaId(2021071404188196L);

        TmbOneServiceResponse<ResponseChecklist> oneServiceResponse = new TmbOneServiceResponse<ResponseChecklist>();

        oneServiceResponse.setStatus(new TmbStatus(ResponseCode.FAILED.getCode(), "failed", "lending-service"));

        when(checklistInfoClient.getChecklistInfo(anyLong())).thenReturn(oneServiceResponse.getData());

        assertThrows(Exception.class, () ->
                checklistService.getDocuments(request.getCaId()));

    }

    private TmbOneServiceResponse<ResponseChecklist> mockChecklistResponseData() {
        TmbOneServiceResponse<ResponseChecklist> oneServiceResponse = new TmbOneServiceResponse<ResponseChecklist>();
        ResponseChecklist responseChecklist = new ResponseChecklist();
        ChecklistResponse checklistResponse = new ChecklistResponse();
        List<ChecklistResponse> checklistResponses = new ArrayList<>();
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

        checklistResponse.setChecklistType("CC");
        checklistResponse.setCifRelCode("M");
        checklistResponse.setStatus("ACTIVE");
        checklistResponse.setDocDescription("xx");
        checklistResponse.setDocId(BigDecimal.ONE);
        checklistResponse.setDocumentCode("ID01");
        checklistResponse.setIncompletedDocReasonCd("xx");
        checklistResponse.setIncompletedDocReasonDesc("xx");
        checklistResponse.setId(BigDecimal.ONE);
        checklistResponse.setIsMandatory("Y");
        checklistResponse.setLosCifId(BigDecimal.ONE);
        checklistResponses.add(checklistResponse);

        body.setCustomerChecklists(checklists);
        responseChecklist.setBody(body);
        oneServiceResponse.setStatus(new TmbStatus(ResponseCode.SUCCESS.getCode(), "success", "lending-service"));
        oneServiceResponse.setData(responseChecklist);
        return oneServiceResponse;
    }

    private TmbOneServiceResponse<ResponseChecklist> mockChecklistResponseFailData() {
        TmbOneServiceResponse<ResponseChecklist> oneServiceResponse = new TmbOneServiceResponse<ResponseChecklist>();
        ResponseChecklist responseChecklist = new ResponseChecklist();
        com.tmb.common.model.legacy.rsl.ws.checklist.response.Body body = new Body();
        Header header = new Header();
        header.setResponseCode("MSG_999");
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
}