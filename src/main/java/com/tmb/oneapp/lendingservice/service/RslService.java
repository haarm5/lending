package com.tmb.oneapp.lendingservice.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.tmb.common.exception.model.TMBCommonException;
import com.tmb.common.model.legacy.rsl.common.ob.facility.Facility;
import com.tmb.common.model.legacy.rsl.common.ob.individual.Individual;
import com.tmb.common.model.legacy.rsl.ws.application.response.ResponseApplication;
import com.tmb.common.model.legacy.rsl.ws.checklist.response.ResponseChecklist;
import com.tmb.common.model.legacy.rsl.ws.creditcard.response.ResponseCreditcard;
import com.tmb.common.model.legacy.rsl.ws.dropdown.response.ResponseDropdown;
import com.tmb.common.model.legacy.rsl.ws.facility.response.ResponseFacility;
import com.tmb.common.model.legacy.rsl.ws.individual.response.ResponseIndividual;
import com.tmb.common.model.legacy.rsl.ws.instant.calculate.uw.response.ResponseInstantLoanCalUW;
import com.tmb.common.model.legacy.rsl.ws.instant.eligible.customer.response.ResponseInstantLoanGetCustInfo;
import com.tmb.common.model.legacy.rsl.ws.instant.submit.response.ResponseInstantLoanSubmit;
import com.tmb.oneapp.lendingservice.client.*;
import com.tmb.oneapp.lendingservice.model.rsl.*;
import com.tmb.oneapp.lendingservice.util.CommonServiceUtils;
import com.tmb.oneapp.lendingservice.util.RslServiceUtils;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import javax.xml.rpc.ServiceException;
import java.math.BigDecimal;
import java.rmi.RemoteException;

@Service
@AllArgsConstructor
public class RslService {

    private final LoanSubmissionGetApplicationInfoClient loanSubmissionGetApplicationInfoClient;
    private final LoanSubmissionGetCreditcardInfoClient loanSubmissionGetCreditCardInfoClient;
    private final LoanSubmissionGetCustomerInfoClient loanSubmissionGetCustomerInfoClient;
    private final LoanSubmissionGetDropdownListClient loanSubmissionGetDropdownListClient;
    private final LoanSubmissionGetFacilityInfoClient loanSubmissionGetFacilityInfoClient;
    private final LoanSubmissionInstantLoanCalUWClient loanSubmissionInstantLoanCalUWClient;
    private final LoanSubmissionInstantLoanGetCustomerInfoClient loanSubmissionInstantLoanGetCustomerInfoClient;
    private final LoanSubmissionInstantLoanSubmitApplicationClient loanSubmissionInstantLoanSubmitApplicationClient;
    private final LoanSubmissionUpdateFacilityInfoClient loanSubmissionUpdateFacilityInfoClient;
    private final LoanSubmissionUpdateCustomerClient loanSubmissionUpdateCustomerClient;
    private final LoanSubmissionGetChecklistInfoClient loanSubmissionGetChecklistInfoClient;

    public ResponseApplication getLoanSubmissionApplicationInfo(LoanSubmissionGetApplicationInfoRequest request) throws ServiceException, TMBCommonException, JsonProcessingException {
        long caId = CommonServiceUtils.validateCaId(request.getCaId());
        ResponseApplication response = loanSubmissionGetApplicationInfoClient.searchApplicationInfoByCaID(caId);
        RslServiceUtils.checkRslResponse(response.getHeader().getResponseCode(), response.getHeader().getResponseDescriptionEN());
        return response;
    }

    public ResponseCreditcard getLoanSubmissionCreditCardInfo(LoanSubmissionGetCreditcardInfoRequest request) throws TMBCommonException, ServiceException, JsonProcessingException {
        long caId = CommonServiceUtils.validateCaId(request.getCaId());
        ResponseCreditcard response = loanSubmissionGetCreditCardInfoClient.searchCreditcardInfoByCaID(caId);
        RslServiceUtils.checkRslResponse(response.getHeader().getResponseCode(), response.getHeader().getResponseDescriptionEN());
        return response;
    }

    public ResponseIndividual getLoanSubmissionCustomerInfo(LoanSubmissionGetCustomerInfoRequest request) throws ServiceException, TMBCommonException, RemoteException, JsonProcessingException {
        long caId = CommonServiceUtils.validateCaId(request.getCaId());
        ResponseIndividual response = loanSubmissionGetCustomerInfoClient.searchCustomerInfoByCaID(caId);
        RslServiceUtils.checkRslResponse(response.getHeader().getResponseCode(), response.getHeader().getResponseDescriptionEN());
        return response;
    }

    public ResponseDropdown getLoanSubmissionDropdownList(LoanSubmissionGetDropdownListRequest request) throws ServiceException, JsonProcessingException, TMBCommonException {
        ResponseDropdown response = loanSubmissionGetDropdownListClient.getDropDownListByCode(request.getCategoryCode());
        RslServiceUtils.checkRslResponse(response.getHeader().getResponseCode(), response.getHeader().getResponseDescriptionEN());
        return response;
    }

    public ResponseFacility getLoanSubmissionFacilityInfo(LoanSubmissionGetFacilityInfoRequest request) throws ServiceException, TMBCommonException, JsonProcessingException {
        long caId = CommonServiceUtils.validateCaId(request.getCaId());
        ResponseFacility response = loanSubmissionGetFacilityInfoClient.searchFacilityInfoByCaID(caId);
        RslServiceUtils.checkRslResponse(response.getHeader().getResponseCode(), response.getHeader().getResponseDescriptionEN());
        return response;
    }

    public ResponseInstantLoanCalUW getLoanSubmissionInstantLoanCalUW(LoanSubmissionInstantLoanCalUWRequest request) throws ServiceException, TMBCommonException, JsonProcessingException {
        BigDecimal caId = BigDecimal.valueOf(CommonServiceUtils.validateCaId(request.getCaId()));
        ResponseInstantLoanCalUW response = loanSubmissionInstantLoanCalUWClient.calculateUnderwriting(request.getTriggerFlag(), caId);
        RslServiceUtils.checkRslResponse(response.getHeader().getResponseCode(), response.getHeader().getResponseDescriptionEN());
        return response;
    }

    public ResponseInstantLoanGetCustInfo getSubmissionInstantLoanCustomerInfo(String crmId) throws ServiceException, JsonProcessingException, TMBCommonException {
        String rmNo = CommonServiceUtils.getRmId(crmId);
        ResponseInstantLoanGetCustInfo response = loanSubmissionInstantLoanGetCustomerInfoClient.getInstantCustomerInfo(rmNo);
        RslServiceUtils.checkRslResponse(response.getHeader().getResponseCode(), response.getHeader().getResponseDescriptionEN());
        return response;
    }

    public ResponseInstantLoanSubmit submitInstantLoanApplication(LoanSubmissionInstantLoanSubmitApplicationRequest request) throws TMBCommonException, ServiceException, JsonProcessingException {
        BigDecimal caId = BigDecimal.valueOf(CommonServiceUtils.validateCaId(request.getCaId()));
        ResponseInstantLoanSubmit response = loanSubmissionInstantLoanSubmitApplicationClient.submitInstantLoanApplication(caId, request.getSubmittedFlag());
        RslServiceUtils.checkRslResponse(response.getHeader().getResponseCode(), response.getHeader().getResponseDescriptionEN());
        return response;
    }

    public com.tmb.common.model.legacy.rsl.ws.facility.update.response.ResponseFacility updateFacilityInfo(Facility request) throws ServiceException, TMBCommonException, JsonProcessingException {
        com.tmb.common.model.legacy.rsl.ws.facility.update.response.ResponseFacility response = loanSubmissionUpdateFacilityInfoClient.updateFacilityInfo(request);
        RslServiceUtils.checkRslResponse(response.getHeader().getResponseCode(), response.getHeader().getResponseDescriptionEN());
        return response;
    }

    public com.tmb.common.model.legacy.rsl.ws.individual.update.response.ResponseIndividual updateCustomerInfo(Individual individual) throws ServiceException, TMBCommonException, JsonProcessingException {
        com.tmb.common.model.legacy.rsl.ws.individual.update.response.ResponseIndividual response = loanSubmissionUpdateCustomerClient.updateCustomerInfo(individual);
        RslServiceUtils.checkRslResponse(response.getHeader().getResponseCode(), response.getHeader().getResponseDescriptionEN());
        return response;
    }

    public ResponseChecklist getDocumentList(Long caId) throws ServiceException, JsonProcessingException, TMBCommonException {
        ResponseChecklist response = loanSubmissionGetChecklistInfoClient.getChecklistInfo(caId);
        RslServiceUtils.checkRslResponse(response.getHeader().getResponseCode(), response.getHeader().getResponseDescriptionEN());
        return response;
    }


}
