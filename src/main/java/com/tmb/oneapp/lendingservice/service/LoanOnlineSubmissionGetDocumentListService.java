package com.tmb.oneapp.lendingservice.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.tmb.common.exception.model.TMBCommonException;
import com.tmb.common.logger.TMBLogger;
import com.tmb.common.model.legacy.rsl.common.ob.checklist.Checklist;
import com.tmb.common.model.legacy.rsl.ws.checklist.response.ResponseChecklist;
import com.tmb.oneapp.lendingservice.client.LoanSubmissionGetChecklistInfoClient;
import com.tmb.oneapp.lendingservice.constant.ResponseCode;
import com.tmb.oneapp.lendingservice.model.personal.ChecklistResponse;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import javax.xml.rpc.ServiceException;
import java.util.ArrayList;
import java.util.List;

@Service
@AllArgsConstructor
public class LoanOnlineSubmissionGetDocumentListService {
    private static final TMBLogger<LoanOnlineSubmissionGetDocumentListService> logger = new TMBLogger<>(LoanOnlineSubmissionGetDocumentListService.class);
    private final LoanSubmissionGetChecklistInfoClient loanSubmissionGetChecklistInfoClient;

    public List<ChecklistResponse> getDocuments(Long caId) throws ServiceException, TMBCommonException, JsonProcessingException {
        ResponseChecklist responseChecklist = checklistDocument(caId);
        List<ChecklistResponse> checklistResponses = new ArrayList<>();


        for (Checklist document : responseChecklist.getBody().getCustomerChecklists()) {
            ChecklistResponse response = new ChecklistResponse();
            response.setChecklistType(document.getChecklistType());
            response.setCifRelCode(document.getCifRelCode());
            response.setDocId(document.getDocId());
            response.setDocumentCode(document.getDocumentCode());
            response.setDocDescription(document.getDocDescription());
            response.setId(document.getId());
            response.setStatus(document.getStatus());
            response.setIsMandatory(document.getIsMandatory());
            response.setIncompletedDocReasonCd(document.getIncompletedDocReasonCd());
            response.setIncompletedDocReasonDesc(document.getIncompletedDocReasonDesc());
            response.setLosCifId(document.getLosCifId());
            checklistResponses.add(response);
        }

        return checklistResponses;
    }

    private ResponseChecklist checklistDocument(Long caId) throws ServiceException, TMBCommonException, JsonProcessingException {
        try {
            ResponseChecklist response = loanSubmissionGetChecklistInfoClient.getChecklistInfo(caId);
            logger.error("get checklist soap res", response.getBody().getCustomerChecklists().length);
            if (response != null) {
                return response;
            } else {
                throw new TMBCommonException(ResponseCode.FAILED.getCode(),
                        ResponseCode.FAILED.getDesc(),
                        ResponseCode.FAILED.getService(), HttpStatus.NOT_FOUND, null);
            }
        } catch (Exception e) {
            logger.error("get checklist soap error", e);
            throw e;
        }
    }
}
