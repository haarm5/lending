package com.tmb.oneapp.lendingservice.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.tmb.common.exception.model.TMBCommonException;
import com.tmb.common.logger.TMBLogger;
import com.tmb.common.model.legacy.rsl.common.ob.checklist.Checklist;
import com.tmb.common.model.legacy.rsl.ws.application.response.Body;
import com.tmb.common.model.legacy.rsl.ws.checklist.response.ResponseChecklist;
import com.tmb.oneapp.lendingservice.client.LoanSubmissionGetChecklistInfoClient;
import com.tmb.oneapp.lendingservice.constant.ResponseCode;
import com.tmb.oneapp.lendingservice.model.CriteriaCodeEntry;
import com.tmb.oneapp.lendingservice.model.personal.ChecklistResponse;
import com.tmb.oneapp.lendingservice.util.CommonServiceUtils;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import javax.xml.rpc.ServiceException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Service
@AllArgsConstructor
public class LoanOnlineSubmissionGetDocumentListService {
    private static final TMBLogger<LoanOnlineSubmissionGetDocumentListService> logger = new TMBLogger<>(LoanOnlineSubmissionGetDocumentListService.class);
    private final LoanSubmissionGetChecklistInfoClient loanSubmissionGetChecklistInfoClient;
    private final UploadDocumentService uploadDocumentService;
    private final LendingCriteriaInfoService lendingCriteriaInfoService;

    public List<ChecklistResponse> getDocuments(String crmId, Long caId) throws ServiceException, TMBCommonException, IOException {
        ResponseChecklist responseChecklist = checklistDocument(caId, "N");
        clearDocuments(crmId, caId.toString());
        return parseChecklistResponse(responseChecklist.getBody().getCustomerChecklists());
    }

    public List<ChecklistResponse> getMoreDocuments(String crmId, Long caId) throws ServiceException, TMBCommonException, IOException {
        ResponseChecklist responseChecklist = checklistDocument(caId, "Y");
        clearDocuments(crmId, caId.toString());
        return parseChecklistResponse(responseChecklist.getBody().getCustomerChecklists());
    }


    private ResponseChecklist checklistDocument(Long caId, String incompleteDocFlag) throws ServiceException, TMBCommonException, JsonProcessingException {
        try {
            ResponseChecklist response = loanSubmissionGetChecklistInfoClient.getChecklistInfo(caId, incompleteDocFlag);
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

    private List<ChecklistResponse> parseChecklistResponse(Checklist[] checklists) {
        List<ChecklistResponse> checklistResponses = new ArrayList<>();

        for (Checklist document : checklists) {
            List<CriteriaCodeEntry> docTypeList = lendingCriteriaInfoService.getBrmsEcmDocTypeByCode(document.getDocumentCode());
            if (!docTypeList.isEmpty()) {
                for (CriteriaCodeEntry entry : docTypeList) {
                    ChecklistResponse response = new ChecklistResponse();
                    response.setChecklistType(document.getChecklistType());
                    response.setCifRelCode(document.getCifRelCode());
                    response.setDocId(document.getDocId());
                    response.setDocumentCode(document.getDocumentCode());
                    response.setDocDescription(entry.getExtValue1());
                    response.setId(document.getId());
                    response.setStatus(document.getStatus());
                    response.setIsMandatory(document.getIsMandatory());
                    response.setIncompletedDocReasonCd(document.getIncompletedDocReasonCd());
                    response.setIncompletedDocReasonDesc(document.getIncompletedDocReasonDesc());
                    response.setLosCifId(document.getLosCifId());
                    checklistResponses.add(response);
                }
            }

        }
        return checklistResponses;
    }

    private void clearDocuments(String crmId, String caId) throws TMBCommonException, ServiceException, IOException {
        String rmId = CommonServiceUtils.getRmId(crmId);
        Body applicationInfo = uploadDocumentService.getApplicationInfo(caId);
        String baseDir = System.getProperty("user.dir");
        String docDir = String.format("%s/documents/%s/%s", baseDir, rmId, applicationInfo.getAppRefNo());
        String sftpDir = String.format("%s/%s", rmId, applicationInfo.getAppRefNo());
        uploadDocumentService.removeDirectory(docDir);
        uploadDocumentService.sftpClearDocuments(sftpDir);
    }
}
