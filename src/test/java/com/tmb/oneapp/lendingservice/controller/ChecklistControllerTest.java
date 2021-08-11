package com.tmb.oneapp.lendingservice.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.tmb.common.exception.model.TMBCommonException;
import com.tmb.common.model.TmbOneServiceResponse;
import com.tmb.common.model.TmbStatus;
import com.tmb.common.model.legacy.rsl.common.ob.checklist.Checklist;
import com.tmb.common.model.legacy.rsl.ws.checklist.response.Body;
import com.tmb.oneapp.lendingservice.constant.ResponseCode;
import com.tmb.oneapp.lendingservice.model.personal.ChecklistRequest;
import com.tmb.oneapp.lendingservice.model.personal.ChecklistResponse;
import com.tmb.oneapp.lendingservice.service.ChecklistService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import javax.xml.rpc.ServiceException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@RunWith(JUnit4.class)
public class ChecklistControllerTest {
    ChecklistController checklistController;

    @Mock
    ChecklistService checklistService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
        checklistController = new ChecklistController(checklistService);
    }

    @Test
    public void testGetPersonalDetailSuccess() throws ServiceException, TMBCommonException, JsonProcessingException {
        ChecklistRequest request = new ChecklistRequest();
        request.setCaId(2021071404188196L);
        String crmid = "001100000000000000000018593707";
        when(checklistService.getDocuments(any())).thenReturn(mockChecklistResponseData().getData());
        ResponseEntity<TmbOneServiceResponse<List<ChecklistResponse>>> result = checklistController.getDocuments(crmid, request);
        assertEquals(HttpStatus.OK.value(), result.getStatusCode().value());
    }

    @Test
    public void testGetPersonalDetailFail() throws ServiceException, TMBCommonException, JsonProcessingException {
        ChecklistRequest request = new ChecklistRequest();
        request.setCaId(2021071404188196L);
        String crmid = "001100000000000000000018593707";
        when(checklistService.getDocuments(any())).thenThrow(new NullPointerException());
        ResponseEntity<TmbOneServiceResponse<List<ChecklistResponse>>> result = checklistController.getDocuments(crmid, request);
        assertTrue(result.getStatusCode().isError());
    }

    private TmbOneServiceResponse<List<ChecklistResponse>> mockChecklistResponseData() {
        TmbOneServiceResponse<List<ChecklistResponse>> oneServiceResponse = new TmbOneServiceResponse<List<ChecklistResponse>>();
        Body body = new Body();
        Checklist checklist = new Checklist();
        List<ChecklistResponse> checklists = new ArrayList<>();
        ChecklistResponse checklistResponse = new ChecklistResponse();
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
        checklists.add(checklistResponse);

        Checklist[] checklists1 = new Checklist[1];
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
        checklists1[0] = checklist;

        body.setCustomerChecklists(checklists1);

        oneServiceResponse.setStatus(new TmbStatus(ResponseCode.SUCCESS.getCode(), "success", "lending-service"));
        oneServiceResponse.setData(oneServiceResponse.getData());
        return oneServiceResponse;
    }
}