package com.tmb.oneapp.lendingservice.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.tmb.common.exception.model.TMBCommonException;
import com.tmb.common.model.legacy.rsl.common.ob.creditcard.CreditCard;
import com.tmb.common.model.legacy.rsl.common.ob.doc.application.DocApplication;
import com.tmb.common.model.legacy.rsl.common.ob.facility.Facility;
import com.tmb.common.model.legacy.rsl.common.ob.individual.Individual;
import com.tmb.common.model.legacy.rsl.ws.application.response.ResponseApplication;
import com.tmb.common.model.legacy.rsl.ws.checklist.response.ResponseChecklist;
import com.tmb.common.model.legacy.rsl.ws.creditcard.response.ResponseCreditcard;
import com.tmb.common.model.legacy.rsl.ws.doc.application.response.ResponseDocApplication;
import com.tmb.common.model.legacy.rsl.ws.dropdown.response.ResponseDropdown;
import com.tmb.common.model.legacy.rsl.ws.facility.response.ResponseFacility;
import com.tmb.common.model.legacy.rsl.ws.individual.response.ResponseIndividual;
import com.tmb.common.model.legacy.rsl.ws.individual.update.response.Body;
import com.tmb.common.model.legacy.rsl.ws.individual.update.response.Header;
import com.tmb.common.model.legacy.rsl.ws.instant.calculate.uw.response.ResponseInstantLoanCalUW;
import com.tmb.common.model.legacy.rsl.ws.instant.eligible.customer.response.ResponseInstantLoanGetCustInfo;
import com.tmb.common.model.legacy.rsl.ws.instant.submit.response.ResponseInstantLoanSubmit;
import com.tmb.oneapp.lendingservice.client.*;
import com.tmb.oneapp.lendingservice.constant.ResponseCode;
import com.tmb.oneapp.lendingservice.constant.RslResponseCode;
import com.tmb.oneapp.lendingservice.model.loanonline.UpdateNCBConsentFlagRequest;
import com.tmb.oneapp.lendingservice.model.rsl.*;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;

import javax.xml.rpc.ServiceException;
import java.rmi.RemoteException;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.doThrow;

@RunWith(JUnit4.class)
public class RslServiceTest {

    @InjectMocks
    RslService rslService;

    @Mock
    LoanSubmissionGetApplicationInfoClient loanSubmissionGetApplicationInfoClient;
    @Mock
    LoanSubmissionGetCreditcardInfoClient loanSubmissionGetCreditcardInfoClient;
    @Mock
    LoanSubmissionGetCustomerInfoClient loanSubmissionGetCustomerInfoClient;
    @Mock
    LoanSubmissionGetDropdownListClient loanSubmissionGetDropdownListClient;
    @Mock
    LoanSubmissionGetFacilityInfoClient loanSubmissionGetFacilityInfoClient;
    @Mock
    LoanSubmissionInstantLoanCalUWClient loanSubmissionInstantLoanCalUWClient;
    @Mock
    LoanSubmissionInstantLoanGetCustomerInfoClient loanSubmissionInstantLoanGetCustomerInfoClient;
    @Mock
    LoanSubmissionInstantLoanSubmitApplicationClient loanSubmissionInstantLoanSubmitApplicationClient;
    @Mock
    LoanSubmissionUpdateFacilityInfoClient loanSubmissionUpdateFacilityInfoClient;
    @Mock
    LoanSubmissionUpdateCustomerClient loanSubmissionUpdateCustomerClient;
    @Mock
    LoanSubmissionGetChecklistInfoClient loanSubmissionGetChecklistInfoClient;
    @Mock
    LoanSubmissionUpdateNCBConsentFlagClient loanSubmissionUpdateNCBConsentFlagClient;
    @Mock
    LoanSubmissionInstantLoanTransferApplicationClient loanSubmissionInstantLoanTransferApplicationClient;
    @Mock
    LoanSubmissionUpdateCreditCardClient loanSubmissionUpdateCreditCardClient;
    @Mock
    LoanSubmissionUpdateIncompleteDocApplicationClient loanSubmissionUpdateIncompleteDocApplicationClient;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
    }


    //Loan Submission Get Application Info
    @Test
    public void getLoanSubmissionApplicationInfo_Success() throws ServiceException, TMBCommonException, JsonProcessingException {
        mockGetLoanSubmissionApplicationInfoSuccess();
        LoanSubmissionGetApplicationInfoRequest request = new LoanSubmissionGetApplicationInfoRequest();
        request.setCaId("1");
        ResponseApplication response = rslService.getLoanSubmissionApplicationInfo(request);
        Assertions.assertEquals(RslResponseCode.SUCCESS.getCode(), response.getHeader().getResponseCode());
    }

    @Test
    public void getLoanSubmissionApplicationInfo_InvalidData() {
        TMBCommonException exception = assertThrows(TMBCommonException.class, () -> {
            LoanSubmissionGetApplicationInfoRequest request = new LoanSubmissionGetApplicationInfoRequest();
            request.setCaId("a"); //invalid caId
            rslService.getLoanSubmissionApplicationInfo(request);
        });

        Assertions.assertEquals(HttpStatus.BAD_REQUEST, exception.getStatus());
        Assertions.assertEquals(ResponseCode.INVALID_DATA.getCode(), exception.getErrorCode());
        Assertions.assertEquals("invalid caId", exception.getErrorMessage());
    }

    @Test
    public void getLoanSubmissionApplicationInfo_RslConnectionError() {
        TMBCommonException exception = assertThrows(TMBCommonException.class, () -> {
            doThrow(new TMBCommonException(ResponseCode.RSL_CONNECTION_ERROR.getCode(), ResponseCode.RSL_CONNECTION_ERROR.getMessage(), ResponseCode.RSL_CONNECTION_ERROR.getService(), HttpStatus.INTERNAL_SERVER_ERROR, null))
                    .when(loanSubmissionGetApplicationInfoClient).searchApplicationInfoByCaID(anyLong());

            LoanSubmissionGetApplicationInfoRequest request = new LoanSubmissionGetApplicationInfoRequest();
            request.setCaId("1");
            rslService.getLoanSubmissionApplicationInfo(request);
        });

        Assertions.assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, exception.getStatus());
        Assertions.assertEquals(ResponseCode.RSL_CONNECTION_ERROR.getCode(), exception.getErrorCode());
        Assertions.assertEquals(ResponseCode.RSL_CONNECTION_ERROR.getMessage(), exception.getErrorMessage());
    }

    @Test
    public void getLoanSubmissionApplicationInfo_RslFail() {
        TMBCommonException exception = assertThrows(TMBCommonException.class, () -> {
            mockGetLoanSubmissionApplicationInfoFail();
            LoanSubmissionGetApplicationInfoRequest request = new LoanSubmissionGetApplicationInfoRequest();
            request.setCaId("1");
            rslService.getLoanSubmissionApplicationInfo(request);
        });

        Assertions.assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, exception.getStatus());
        Assertions.assertEquals(ResponseCode.RSL_FAILED.getCode(), exception.getErrorCode());
        Assertions.assertEquals(String.format("[%s] %s", RslResponseCode.FAIL.getCode(), ResponseCode.RSL_FAILED.getMessage()), exception.getErrorMessage());
    }


    //Loan Submission Get Creditcard Info
    @Test
    public void getLoanSubmissionCreditCardInfo_Success() throws ServiceException, TMBCommonException, JsonProcessingException {
        mockGetLoanSubmissionCreditCardInfoSuccess();
        LoanSubmissionGetCreditcardInfoRequest request = new LoanSubmissionGetCreditcardInfoRequest();
        request.setCaId("1");
        ResponseCreditcard response = rslService.getLoanSubmissionCreditCardInfo(request);
        Assertions.assertEquals(RslResponseCode.SUCCESS.getCode(), response.getHeader().getResponseCode());
    }

    @Test
    public void getLoanSubmissionCreditCardInfo_InvalidData() {
        TMBCommonException exception = assertThrows(TMBCommonException.class, () -> {
            LoanSubmissionGetCreditcardInfoRequest request = new LoanSubmissionGetCreditcardInfoRequest();
            request.setCaId("a"); //invalid caId
            rslService.getLoanSubmissionCreditCardInfo(request);
        });

        Assertions.assertEquals(HttpStatus.BAD_REQUEST, exception.getStatus());
        Assertions.assertEquals(ResponseCode.INVALID_DATA.getCode(), exception.getErrorCode());
        Assertions.assertEquals("invalid caId", exception.getErrorMessage());
    }

    @Test
    public void getLoanSubmissionCreditCardInfo_RslConnectionError() {
        TMBCommonException exception = assertThrows(TMBCommonException.class, () -> {
            doThrow(new TMBCommonException(ResponseCode.RSL_CONNECTION_ERROR.getCode(), ResponseCode.RSL_CONNECTION_ERROR.getMessage(), ResponseCode.RSL_CONNECTION_ERROR.getService(), HttpStatus.INTERNAL_SERVER_ERROR, null))
                    .when(loanSubmissionGetCreditcardInfoClient).searchCreditcardInfoByCaID(anyLong());

            LoanSubmissionGetCreditcardInfoRequest request = new LoanSubmissionGetCreditcardInfoRequest();
            request.setCaId("1");
            rslService.getLoanSubmissionCreditCardInfo(request);
        });

        Assertions.assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, exception.getStatus());
        Assertions.assertEquals(ResponseCode.RSL_CONNECTION_ERROR.getCode(), exception.getErrorCode());
        Assertions.assertEquals(ResponseCode.RSL_CONNECTION_ERROR.getMessage(), exception.getErrorMessage());
    }

    @Test
    public void getLoanSubmissionCreditCardInfo_RslFail() {
        TMBCommonException exception = assertThrows(TMBCommonException.class, () -> {
            mockGetLoanSubmissionCreditCardInfoFail();
            LoanSubmissionGetCreditcardInfoRequest request = new LoanSubmissionGetCreditcardInfoRequest();
            request.setCaId("1");
            rslService.getLoanSubmissionCreditCardInfo(request);
        });

        Assertions.assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, exception.getStatus());
        Assertions.assertEquals(ResponseCode.RSL_FAILED.getCode(), exception.getErrorCode());
        Assertions.assertEquals(String.format("[%s] %s", RslResponseCode.FAIL.getCode(), ResponseCode.RSL_FAILED.getMessage()), exception.getErrorMessage());
    }


    //Loan Submission Get Customer Info
    @Test
    public void getLoanSubmissionCustomerInfo_Success() throws ServiceException, TMBCommonException, JsonProcessingException, RemoteException {
        mockGetLoanSubmissionCustomerInfoSuccess();
        LoanSubmissionGetCustomerInfoRequest request = new LoanSubmissionGetCustomerInfoRequest();
        request.setCaId("1");
        ResponseIndividual response = rslService.getLoanSubmissionCustomerInfo(request);
        Assertions.assertEquals(RslResponseCode.SUCCESS.getCode(), response.getHeader().getResponseCode());
    }

    @Test
    public void getLoanSubmissionCustomerInfo_InvalidData() {
        TMBCommonException exception = assertThrows(TMBCommonException.class, () -> {
            LoanSubmissionGetCustomerInfoRequest request = new LoanSubmissionGetCustomerInfoRequest();
            request.setCaId("a"); //invalid caId
            rslService.getLoanSubmissionCustomerInfo(request);
        });

        Assertions.assertEquals(HttpStatus.BAD_REQUEST, exception.getStatus());
        Assertions.assertEquals(ResponseCode.INVALID_DATA.getCode(), exception.getErrorCode());
        Assertions.assertEquals("invalid caId", exception.getErrorMessage());
    }

    @Test
    public void getLoanSubmissionCustomerInfo_RslConnectionError() {
        TMBCommonException exception = assertThrows(TMBCommonException.class, () -> {
            doThrow(new TMBCommonException(ResponseCode.RSL_CONNECTION_ERROR.getCode(), ResponseCode.RSL_CONNECTION_ERROR.getMessage(), ResponseCode.RSL_CONNECTION_ERROR.getService(), HttpStatus.INTERNAL_SERVER_ERROR, null))
                    .when(loanSubmissionGetCustomerInfoClient).searchCustomerInfoByCaID(anyLong());

            String crmId = "a";
            rslService.getSubmissionInstantLoanCustomerInfo(crmId);
        });

        Assertions.assertEquals(HttpStatus.BAD_REQUEST, exception.getStatus());
        Assertions.assertEquals(ResponseCode.INVALID_DATA.getCode(), exception.getErrorCode());
        Assertions.assertEquals("invalid crmId", exception.getErrorMessage());
    }

    @Test
    public void getLoanSubmissionCustomerInfo_RslFail() {
        TMBCommonException exception = assertThrows(TMBCommonException.class, () -> {
            mockGetLoanSubmissionCustomerInfoFail();
            LoanSubmissionGetCustomerInfoRequest request = new LoanSubmissionGetCustomerInfoRequest();
            request.setCaId("1");
            rslService.getLoanSubmissionCustomerInfo(request);
        });

        Assertions.assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, exception.getStatus());
        Assertions.assertEquals(ResponseCode.RSL_FAILED.getCode(), exception.getErrorCode());
        Assertions.assertEquals(String.format("[%s] %s", RslResponseCode.FAIL.getCode(), ResponseCode.RSL_FAILED.getMessage()), exception.getErrorMessage());
    }


    //Loan Submission Get Dropdown List
    @Test
    public void getLoanSubmissionDropdownList_Success() throws ServiceException, TMBCommonException, JsonProcessingException {
        mockGetLoanSubmissionDropdownListSuccess();
        LoanSubmissionGetDropdownListRequest request = new LoanSubmissionGetDropdownListRequest();
        request.setCategoryCode("categoryCode");
        ResponseDropdown response = rslService.getLoanSubmissionDropdownList(request);
        Assertions.assertEquals(RslResponseCode.SUCCESS.getCode(), response.getHeader().getResponseCode());
    }

    @Test
    public void getLoanSubmissionDropdownList_RslConnectionError() {
        TMBCommonException exception = assertThrows(TMBCommonException.class, () -> {
            doThrow(new TMBCommonException(ResponseCode.RSL_CONNECTION_ERROR.getCode(), ResponseCode.RSL_CONNECTION_ERROR.getMessage(), ResponseCode.RSL_CONNECTION_ERROR.getService(), HttpStatus.INTERNAL_SERVER_ERROR, null))
                    .when(loanSubmissionGetDropdownListClient).getDropDownListByCode(anyString());

            LoanSubmissionGetDropdownListRequest request = new LoanSubmissionGetDropdownListRequest();
            request.setCategoryCode("categoryCode");
            rslService.getLoanSubmissionDropdownList(request);
        });

        Assertions.assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, exception.getStatus());
        Assertions.assertEquals(ResponseCode.RSL_CONNECTION_ERROR.getCode(), exception.getErrorCode());
        Assertions.assertEquals(ResponseCode.RSL_CONNECTION_ERROR.getMessage(), exception.getErrorMessage());
    }

    @Test
    public void getLoanSubmissionDropdownList_RslFail() {
        TMBCommonException exception = assertThrows(TMBCommonException.class, () -> {
            mockGetLoanSubmissionDropdownListFail();
            LoanSubmissionGetDropdownListRequest request = new LoanSubmissionGetDropdownListRequest();
            request.setCategoryCode("1");
            rslService.getLoanSubmissionDropdownList(request);
        });

        Assertions.assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, exception.getStatus());
        Assertions.assertEquals(ResponseCode.RSL_FAILED.getCode(), exception.getErrorCode());
        Assertions.assertEquals(String.format("[%s] %s", RslResponseCode.FAIL.getCode(), ResponseCode.RSL_FAILED.getMessage()), exception.getErrorMessage());
    }


    //Loan Submission Get Facility Info
    @Test
    public void getLoanSubmissionFacilityInfo_Success() throws ServiceException, TMBCommonException, JsonProcessingException {
        mockGetLoanSubmissionFacilityInfoSuccess();
        LoanSubmissionGetFacilityInfoRequest request = new LoanSubmissionGetFacilityInfoRequest();
        request.setCaId("1");
        ResponseFacility response = rslService.getLoanSubmissionFacilityInfo(request);
        Assertions.assertEquals(RslResponseCode.SUCCESS.getCode(), response.getHeader().getResponseCode());
    }

    @Test
    public void getLoanSubmissionFacilityInfo_InvalidData() {
        TMBCommonException exception = assertThrows(TMBCommonException.class, () -> {
            LoanSubmissionGetFacilityInfoRequest request = new LoanSubmissionGetFacilityInfoRequest();
            request.setCaId("a"); //invalid caId
            rslService.getLoanSubmissionFacilityInfo(request);
        });

        Assertions.assertEquals(HttpStatus.BAD_REQUEST, exception.getStatus());
        Assertions.assertEquals(ResponseCode.INVALID_DATA.getCode(), exception.getErrorCode());
        Assertions.assertEquals("invalid caId", exception.getErrorMessage());
    }

    @Test
    public void getLoanSubmissionFacilityInfo_RslConnectionError() {
        TMBCommonException exception = assertThrows(TMBCommonException.class, () -> {
            doThrow(new TMBCommonException(ResponseCode.RSL_CONNECTION_ERROR.getCode(), ResponseCode.RSL_CONNECTION_ERROR.getMessage(), ResponseCode.RSL_CONNECTION_ERROR.getService(), HttpStatus.INTERNAL_SERVER_ERROR, null))
                    .when(loanSubmissionGetFacilityInfoClient).searchFacilityInfoByCaID(anyLong());

            LoanSubmissionGetFacilityInfoRequest request = new LoanSubmissionGetFacilityInfoRequest();
            request.setCaId("1");
            rslService.getLoanSubmissionFacilityInfo(request);
        });

        Assertions.assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, exception.getStatus());
        Assertions.assertEquals(ResponseCode.RSL_CONNECTION_ERROR.getCode(), exception.getErrorCode());
        Assertions.assertEquals(ResponseCode.RSL_CONNECTION_ERROR.getMessage(), exception.getErrorMessage());
    }

    @Test
    public void getLoanSubmissionFacilityInfo_RslFail() {
        TMBCommonException exception = assertThrows(TMBCommonException.class, () -> {
            mockGetLoanSubmissionFacilityInfoFail();
            LoanSubmissionGetFacilityInfoRequest request = new LoanSubmissionGetFacilityInfoRequest();
            request.setCaId("1");
            rslService.getLoanSubmissionFacilityInfo(request);
        });

        Assertions.assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, exception.getStatus());
        Assertions.assertEquals(ResponseCode.RSL_FAILED.getCode(), exception.getErrorCode());
        Assertions.assertEquals(String.format("[%s] %s", RslResponseCode.FAIL.getCode(), ResponseCode.RSL_FAILED.getMessage()), exception.getErrorMessage());
    }


    //Loan Submission Instant Loan CalUW
    @Test
    public void getLoanSubmissionInstantLoanCalUW_Success() throws ServiceException, TMBCommonException, JsonProcessingException {
        mockGetLoanSubmissionInstantLoanCalUWSuccess();
        LoanSubmissionInstantLoanCalUWRequest request = new LoanSubmissionInstantLoanCalUWRequest();
        request.setTriggerFlag("Y");
        request.setCaId("1");
        ResponseInstantLoanCalUW response = rslService.getLoanSubmissionInstantLoanCalUW(request);
        Assertions.assertEquals(RslResponseCode.SUCCESS.getCode(), response.getHeader().getResponseCode());
    }

    @Test
    public void getLoanSubmissionInstantLoanCalUW_InvalidData() {
        TMBCommonException exception = assertThrows(TMBCommonException.class, () -> {
            LoanSubmissionInstantLoanCalUWRequest request = new LoanSubmissionInstantLoanCalUWRequest();
            request.setCaId("a"); //invalid caId
            rslService.getLoanSubmissionInstantLoanCalUW(request);
        });

        Assertions.assertEquals(HttpStatus.BAD_REQUEST, exception.getStatus());
        Assertions.assertEquals(ResponseCode.INVALID_DATA.getCode(), exception.getErrorCode());
        Assertions.assertEquals("invalid caId", exception.getErrorMessage());
    }

    @Test
    public void getLoanSubmissionInstantLoanCalUW_RslConnectionError() {
        TMBCommonException exception = assertThrows(TMBCommonException.class, () -> {
            doThrow(new TMBCommonException(ResponseCode.RSL_CONNECTION_ERROR.getCode(), ResponseCode.RSL_CONNECTION_ERROR.getMessage(), ResponseCode.RSL_CONNECTION_ERROR.getService(), HttpStatus.INTERNAL_SERVER_ERROR, null))
                    .when(loanSubmissionInstantLoanCalUWClient).calculateUnderwriting(any(), any());

            LoanSubmissionInstantLoanCalUWRequest request = new LoanSubmissionInstantLoanCalUWRequest();
            request.setTriggerFlag("Y");
            request.setCaId("1");
            rslService.getLoanSubmissionInstantLoanCalUW(request);
        });

        Assertions.assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, exception.getStatus());
        Assertions.assertEquals(ResponseCode.RSL_CONNECTION_ERROR.getCode(), exception.getErrorCode());
        Assertions.assertEquals(ResponseCode.RSL_CONNECTION_ERROR.getMessage(), exception.getErrorMessage());
    }

    @Test
    public void getLoanSubmissionInstantLoanCalUW_RslFail() {
        TMBCommonException exception = assertThrows(TMBCommonException.class, () -> {
            mockGetLoanSubmissionInstantLoanCalUWFail();
            LoanSubmissionInstantLoanCalUWRequest request = new LoanSubmissionInstantLoanCalUWRequest();
            request.setTriggerFlag("Y");
            request.setCaId("1");
            rslService.getLoanSubmissionInstantLoanCalUW(request);
        });

        Assertions.assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, exception.getStatus());
        Assertions.assertEquals(ResponseCode.RSL_FAILED.getCode(), exception.getErrorCode());
        Assertions.assertEquals(String.format("[%s] %s", RslResponseCode.FAIL.getCode(), ResponseCode.RSL_FAILED.getMessage()), exception.getErrorMessage());
    }


    //Loan Submission Instant Loan Get Customer Info
    @Test
    public void getSubmissionInstantLoanCustomerInfo_Success() throws ServiceException, TMBCommonException, JsonProcessingException {
        mockLoanInstantGetCustomerInfoSuccess();
        String rmId = "00000018593707";
        ResponseInstantLoanGetCustInfo response = rslService.getSubmissionInstantLoanCustomerInfo(rmId);
        Assertions.assertEquals(RslResponseCode.SUCCESS.getCode(), response.getHeader().getResponseCode());
    }

    @Test
    public void getSubmissionInstantLoanCustomerInfo_RslConnectionError() {
        TMBCommonException exception = assertThrows(TMBCommonException.class, () -> {
            doThrow(new TMBCommonException(ResponseCode.RSL_CONNECTION_ERROR.getCode(), ResponseCode.RSL_CONNECTION_ERROR.getMessage(), ResponseCode.RSL_CONNECTION_ERROR.getService(), HttpStatus.INTERNAL_SERVER_ERROR, null))
                    .when(loanSubmissionInstantLoanGetCustomerInfoClient).getInstantCustomerInfo(anyString());

            String rmId = "00000018593707";
            rslService.getSubmissionInstantLoanCustomerInfo(rmId);
        });

        Assertions.assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, exception.getStatus());
        Assertions.assertEquals(ResponseCode.RSL_CONNECTION_ERROR.getCode(), exception.getErrorCode());
        Assertions.assertEquals(ResponseCode.RSL_CONNECTION_ERROR.getMessage(), exception.getErrorMessage());
    }

    @Test
    public void getSubmissionInstantLoanCustomerInfo_RslFail() {
        TMBCommonException exception = assertThrows(TMBCommonException.class, () -> {
            mockLoanInstantGetCustomerInfoFail();
            String rmId = "00000018593707";
            rslService.getSubmissionInstantLoanCustomerInfo(rmId);
        });

        Assertions.assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, exception.getStatus());
        Assertions.assertEquals(ResponseCode.RSL_FAILED.getCode(), exception.getErrorCode());
        Assertions.assertEquals(String.format("[%s] %s", RslResponseCode.FAIL.getCode(), ResponseCode.RSL_FAILED.getMessage()), exception.getErrorMessage());
    }


    //Loan Submission Instant Loan Submit Application
    @Test
    public void submitInstantLoanApplication_Success() throws ServiceException, TMBCommonException, JsonProcessingException {
        mockSubmitInstantLoanApplicationSuccess();
        LoanSubmissionInstantLoanSubmitApplicationRequest request = new LoanSubmissionInstantLoanSubmitApplicationRequest();
        request.setSubmittedFlag("Y");
        request.setCaId("1");
        ResponseInstantLoanSubmit response = rslService.submitInstantLoanApplication(request);
        Assertions.assertEquals(RslResponseCode.SUCCESS.getCode(), response.getHeader().getResponseCode());
    }

    @Test
    public void submitInstantLoanApplication_InvalidData() {
        TMBCommonException exception = assertThrows(TMBCommonException.class, () -> {
            LoanSubmissionInstantLoanSubmitApplicationRequest request = new LoanSubmissionInstantLoanSubmitApplicationRequest();
            request.setSubmittedFlag("Y");
            request.setCaId("a"); //invalid caId
            rslService.submitInstantLoanApplication(request);
        });

        Assertions.assertEquals(HttpStatus.BAD_REQUEST, exception.getStatus());
        Assertions.assertEquals(ResponseCode.INVALID_DATA.getCode(), exception.getErrorCode());
        Assertions.assertEquals("invalid caId", exception.getErrorMessage());
    }

    @Test
    public void submitInstantLoanApplication_RslConnectionError() {
        TMBCommonException exception = assertThrows(TMBCommonException.class, () -> {
            doThrow(new TMBCommonException(ResponseCode.RSL_CONNECTION_ERROR.getCode(), ResponseCode.RSL_CONNECTION_ERROR.getMessage(), ResponseCode.RSL_CONNECTION_ERROR.getService(), HttpStatus.INTERNAL_SERVER_ERROR, null))
                    .when(loanSubmissionInstantLoanSubmitApplicationClient).submitInstantLoanApplication(any(), any());

            LoanSubmissionInstantLoanSubmitApplicationRequest request = new LoanSubmissionInstantLoanSubmitApplicationRequest();
            request.setSubmittedFlag("Y");
            request.setCaId("1");
            rslService.submitInstantLoanApplication(request);
        });

        Assertions.assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, exception.getStatus());
        Assertions.assertEquals(ResponseCode.RSL_CONNECTION_ERROR.getCode(), exception.getErrorCode());
        Assertions.assertEquals(ResponseCode.RSL_CONNECTION_ERROR.getMessage(), exception.getErrorMessage());
    }

    @Test
    public void submitInstantLoanApplication_RslFail() {
        TMBCommonException exception = assertThrows(TMBCommonException.class, () -> {
            mockSubmitInstantLoanApplicationFail();
            LoanSubmissionInstantLoanSubmitApplicationRequest request = new LoanSubmissionInstantLoanSubmitApplicationRequest();
            request.setSubmittedFlag("Y");
            request.setCaId("1");
            rslService.submitInstantLoanApplication(request);
        });

        Assertions.assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, exception.getStatus());
        Assertions.assertEquals(ResponseCode.RSL_FAILED.getCode(), exception.getErrorCode());
        Assertions.assertEquals(String.format("[%s] %s", RslResponseCode.FAIL.getCode(), ResponseCode.RSL_FAILED.getMessage()), exception.getErrorMessage());
    }


    //Loan Submission Update Facility
    @Test
    public void updateFacilityInfo_Success() throws ServiceException, TMBCommonException, JsonProcessingException {
        mockUpdateFacilityInfoSuccess();
        Facility request = new Facility();
        com.tmb.common.model.legacy.rsl.ws.facility.update.response.ResponseFacility response = rslService.updateFacilityInfo(request);
        Assertions.assertEquals(RslResponseCode.SUCCESS.getCode(), response.getHeader().getResponseCode());
    }

    @Test
    public void updateFacilityInfo_RslConnectionError() {
        TMBCommonException exception = assertThrows(TMBCommonException.class, () -> {
            doThrow(new TMBCommonException(ResponseCode.RSL_CONNECTION_ERROR.getCode(), ResponseCode.RSL_CONNECTION_ERROR.getMessage(), ResponseCode.RSL_CONNECTION_ERROR.getService(), HttpStatus.INTERNAL_SERVER_ERROR, null))
                    .when(loanSubmissionUpdateFacilityInfoClient).updateFacilityInfo(any());

            Facility request = new Facility();
            rslService.updateFacilityInfo(request);
        });

        Assertions.assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, exception.getStatus());
        Assertions.assertEquals(ResponseCode.RSL_CONNECTION_ERROR.getCode(), exception.getErrorCode());
        Assertions.assertEquals(ResponseCode.RSL_CONNECTION_ERROR.getMessage(), exception.getErrorMessage());
    }

    @Test
    public void updateFacilityInfo_RslFail() {
        TMBCommonException exception = assertThrows(TMBCommonException.class, () -> {
            mockUpdateFacilityInfoFail();
            Facility request = new Facility();
            rslService.updateFacilityInfo(request);
        });

        Assertions.assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, exception.getStatus());
        Assertions.assertEquals(ResponseCode.RSL_FAILED.getCode(), exception.getErrorCode());
        Assertions.assertEquals(String.format("[%s] %s", RslResponseCode.FAIL.getCode(), ResponseCode.RSL_FAILED.getMessage()), exception.getErrorMessage());
    }

    //Loan Submission Update Customer
    @Test
    public void updateCustomerInfo_Success() throws ServiceException, TMBCommonException, JsonProcessingException {
        mockUpdateCustomerInfoSuccess();
        Individual request = new Individual();
        com.tmb.common.model.legacy.rsl.ws.individual.update.response.ResponseIndividual response = rslService.updateCustomerInfo(request);
        Assertions.assertEquals(RslResponseCode.SUCCESS.getCode(), response.getHeader().getResponseCode());
    }

    @Test
    public void updateCustomerInfo_RslConnectionError() {
        TMBCommonException exception = assertThrows(TMBCommonException.class, () -> {
            doThrow(new TMBCommonException(ResponseCode.RSL_CONNECTION_ERROR.getCode(), ResponseCode.RSL_CONNECTION_ERROR.getMessage(), ResponseCode.RSL_CONNECTION_ERROR.getService(), HttpStatus.INTERNAL_SERVER_ERROR, null))
                    .when(loanSubmissionUpdateCustomerClient).updateCustomerInfo(any());

            Individual request = new Individual();
            rslService.updateCustomerInfo(request);
        });

        Assertions.assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, exception.getStatus());
        Assertions.assertEquals(ResponseCode.RSL_CONNECTION_ERROR.getCode(), exception.getErrorCode());
        Assertions.assertEquals(ResponseCode.RSL_CONNECTION_ERROR.getMessage(), exception.getErrorMessage());
    }

    @Test
    public void updateCustomerInfo_RslFail() {
        TMBCommonException exception = assertThrows(TMBCommonException.class, () -> {
            mockUpdateCustomerInfoFail();
            Individual request = new Individual();
            rslService.updateCustomerInfo(request);
        });

        Assertions.assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, exception.getStatus());
        Assertions.assertEquals(ResponseCode.RSL_FAILED.getCode(), exception.getErrorCode());
        Assertions.assertEquals(String.format("[%s] %s", RslResponseCode.FAIL.getCode(), ResponseCode.RSL_FAILED.getMessage()), exception.getErrorMessage());
    }

    //Loan Submission Get Checklist Info
    @Test
    public void getLoanSubmissionChecklistInfo_Success() throws ServiceException, TMBCommonException, JsonProcessingException {
        mockGetLoanSubmissionChecklistInfoSuccess();
        LoanSubmissionGetChecklistInfoRequest request = new LoanSubmissionGetChecklistInfoRequest();
        request.setCaId(1L);
        request.setIncompleteDocFlag("N");
        ResponseChecklist response = rslService.getDocumentList(request.getCaId(), request.getIncompleteDocFlag());
        Assertions.assertEquals(RslResponseCode.SUCCESS.getCode(), response.getHeader().getResponseCode());
    }


    @Test
    public void getLoanSubmissionChecklistInfo_RslConnectionError() {
        TMBCommonException exception = assertThrows(TMBCommonException.class, () -> {
            doThrow(new TMBCommonException(ResponseCode.RSL_CONNECTION_ERROR.getCode(), ResponseCode.RSL_CONNECTION_ERROR.getMessage(), ResponseCode.RSL_CONNECTION_ERROR.getService(), HttpStatus.INTERNAL_SERVER_ERROR, null))
                    .when(loanSubmissionGetChecklistInfoClient).getChecklistInfo(anyLong(), anyString());

            LoanSubmissionGetChecklistInfoRequest request = new LoanSubmissionGetChecklistInfoRequest();
            request.setCaId(1L);
            request.setIncompleteDocFlag("N");
            rslService.getDocumentList(request.getCaId(), request.getIncompleteDocFlag());
        });

        Assertions.assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, exception.getStatus());
        Assertions.assertEquals(ResponseCode.RSL_CONNECTION_ERROR.getCode(), exception.getErrorCode());
        Assertions.assertEquals(ResponseCode.RSL_CONNECTION_ERROR.getMessage(), exception.getErrorMessage());
    }

    @Test
    public void getLoanSubmissionChecklistInfo_RslFail() {
        TMBCommonException exception = assertThrows(TMBCommonException.class, () -> {
            mockGetLoanSubmissionChecklistInfoFail();
            LoanSubmissionGetChecklistInfoRequest request = new LoanSubmissionGetChecklistInfoRequest();
            request.setCaId(1L);
            request.setIncompleteDocFlag("N");
            rslService.getDocumentList(request.getCaId(), request.getIncompleteDocFlag());
        });

        Assertions.assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, exception.getStatus());
        Assertions.assertEquals(ResponseCode.RSL_FAILED.getCode(), exception.getErrorCode());
        Assertions.assertEquals(String.format("[%s] %s", RslResponseCode.FAIL.getCode(), ResponseCode.RSL_FAILED.getMessage()), exception.getErrorMessage());
    }

    //Loan Submission Update NCB Consent Flag
    @Test
    public void updateNCBConsentFlag_Success() throws ServiceException, TMBCommonException, JsonProcessingException {
        mockUpdateNCBConsentFlagSuccess();
        UpdateNCBConsentFlagRequest request = new UpdateNCBConsentFlagRequest();
        com.tmb.common.model.legacy.rsl.ws.ncb.consent.flag.update.response.ResponseUpdateNCBConsentFlag response = rslService.updateNCBConsentFlag(request);
        Assertions.assertEquals(RslResponseCode.SUCCESS.getCode(), response.getHeader().getResponseCode());
    }

    @Test
    public void updateNCBConsentFlag_RslConnectionError() {
        TMBCommonException exception = assertThrows(TMBCommonException.class, () -> {
            doThrow(new TMBCommonException(ResponseCode.RSL_CONNECTION_ERROR.getCode(), ResponseCode.RSL_CONNECTION_ERROR.getMessage(), ResponseCode.RSL_CONNECTION_ERROR.getService(), HttpStatus.INTERNAL_SERVER_ERROR, null))
                    .when(loanSubmissionUpdateNCBConsentFlagClient).updateNCBConsentFlag(any());

            UpdateNCBConsentFlagRequest request = new UpdateNCBConsentFlagRequest();
            rslService.updateNCBConsentFlag(request);
        });

        Assertions.assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, exception.getStatus());
        Assertions.assertEquals(ResponseCode.RSL_CONNECTION_ERROR.getCode(), exception.getErrorCode());
        Assertions.assertEquals(ResponseCode.RSL_CONNECTION_ERROR.getMessage(), exception.getErrorMessage());
    }

    @Test
    public void updateNCBConsentFlag_RslFail() {
        TMBCommonException exception = assertThrows(TMBCommonException.class, () -> {
            mockUpdateNCBConsentFlagFail();
            UpdateNCBConsentFlagRequest request = new UpdateNCBConsentFlagRequest();
            rslService.updateNCBConsentFlag(request);
        });

        Assertions.assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, exception.getStatus());
        Assertions.assertEquals(ResponseCode.RSL_FAILED.getCode(), exception.getErrorCode());
        Assertions.assertEquals(String.format("[%s] %s", RslResponseCode.FAIL.getCode(), ResponseCode.RSL_FAILED.getMessage()), exception.getErrorMessage());
    }

    //Loan Submission Instant Loan Transfer Application
    @Test
    public void transferApplication_Success() throws ServiceException, TMBCommonException, JsonProcessingException {
        mockTransferApplicationSuccess();
        com.tmb.common.model.legacy.rsl.ws.instant.transfer.response.ResponseTransfer response = rslService.transferApplication("2021080504188297");
        Assertions.assertEquals(RslResponseCode.SUCCESS.getCode(), response.getHeader().getResponseCode());
    }

    @Test
    public void transferApplication_RslConnectionError() {
        TMBCommonException exception = assertThrows(TMBCommonException.class, () -> {
            doThrow(new TMBCommonException(ResponseCode.RSL_CONNECTION_ERROR.getCode(), ResponseCode.RSL_CONNECTION_ERROR.getMessage(), ResponseCode.RSL_CONNECTION_ERROR.getService(), HttpStatus.INTERNAL_SERVER_ERROR, null))
                    .when(loanSubmissionInstantLoanTransferApplicationClient).transferApplication(any());
            rslService.transferApplication("2021080504188297");
        });

        Assertions.assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, exception.getStatus());
        Assertions.assertEquals(ResponseCode.RSL_CONNECTION_ERROR.getCode(), exception.getErrorCode());
        Assertions.assertEquals(ResponseCode.RSL_CONNECTION_ERROR.getMessage(), exception.getErrorMessage());
    }

    @Test
    public void transferApplication_RslFail() {
        TMBCommonException exception = assertThrows(TMBCommonException.class, () -> {
            mockTransferApplicationFail();
            rslService.transferApplication("2021080504188297");
        });

        Assertions.assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, exception.getStatus());
        Assertions.assertEquals(ResponseCode.RSL_FAILED.getCode(), exception.getErrorCode());
        Assertions.assertEquals(String.format("[%s] %s", RslResponseCode.FAIL.getCode(), ResponseCode.RSL_FAILED.getMessage()), exception.getErrorMessage());
    }

    //Mock Data
    private void mockGetLoanSubmissionApplicationInfoSuccess() throws ServiceException, JsonProcessingException, TMBCommonException {
        ResponseApplication response = new ResponseApplication();

        com.tmb.common.model.legacy.rsl.ws.application.response.Header header = new com.tmb.common.model.legacy.rsl.ws.application.response.Header();
        header.setResponseCode(RslResponseCode.SUCCESS.getCode());
        response.setHeader(header);

        com.tmb.common.model.legacy.rsl.ws.application.response.Body body = new com.tmb.common.model.legacy.rsl.ws.application.response.Body();
        response.setBody(body);

        doReturn(response).when(loanSubmissionGetApplicationInfoClient).searchApplicationInfoByCaID(anyLong());
    }

    //Mock Data
    private void mockGetLoanSubmissionApplicationInfoFail() throws ServiceException, JsonProcessingException, TMBCommonException {
        ResponseApplication response = new ResponseApplication();

        com.tmb.common.model.legacy.rsl.ws.application.response.Header header = new com.tmb.common.model.legacy.rsl.ws.application.response.Header();
        header.setResponseCode(RslResponseCode.FAIL.getCode());
        header.setResponseDescriptionEN("rsl failed");
        response.setHeader(header);

        com.tmb.common.model.legacy.rsl.ws.application.response.Body body = new com.tmb.common.model.legacy.rsl.ws.application.response.Body();
        response.setBody(body);

        doReturn(response).when(loanSubmissionGetApplicationInfoClient).searchApplicationInfoByCaID(anyLong());
    }

    private void mockGetLoanSubmissionCreditCardInfoSuccess() throws ServiceException, TMBCommonException, JsonProcessingException {
        ResponseCreditcard response = new ResponseCreditcard();

        com.tmb.common.model.legacy.rsl.ws.creditcard.response.Header header = new com.tmb.common.model.legacy.rsl.ws.creditcard.response.Header();
        header.setResponseCode(RslResponseCode.SUCCESS.getCode());
        response.setHeader(header);

        com.tmb.common.model.legacy.rsl.ws.creditcard.response.Body body = new com.tmb.common.model.legacy.rsl.ws.creditcard.response.Body();
        response.setBody(body);

        doReturn(response).when(loanSubmissionGetCreditcardInfoClient).searchCreditcardInfoByCaID(anyLong());
    }

    private void mockGetLoanSubmissionCreditCardInfoFail() throws ServiceException, TMBCommonException, JsonProcessingException {
        ResponseCreditcard response = new ResponseCreditcard();

        com.tmb.common.model.legacy.rsl.ws.creditcard.response.Header header = new com.tmb.common.model.legacy.rsl.ws.creditcard.response.Header();
        header.setResponseCode(RslResponseCode.FAIL.getCode());
        header.setResponseDescriptionEN("rsl failed");
        response.setHeader(header);

        com.tmb.common.model.legacy.rsl.ws.creditcard.response.Body body = new com.tmb.common.model.legacy.rsl.ws.creditcard.response.Body();
        response.setBody(body);

        doReturn(response).when(loanSubmissionGetCreditcardInfoClient).searchCreditcardInfoByCaID(anyLong());
    }

    private void mockGetLoanSubmissionCustomerInfoSuccess() throws ServiceException, RemoteException, TMBCommonException, JsonProcessingException {
        ResponseIndividual response = new ResponseIndividual();

        com.tmb.common.model.legacy.rsl.ws.individual.response.Header header = new com.tmb.common.model.legacy.rsl.ws.individual.response.Header();
        header.setResponseCode(RslResponseCode.SUCCESS.getCode());
        response.setHeader(header);

        com.tmb.common.model.legacy.rsl.ws.individual.response.Body body = new com.tmb.common.model.legacy.rsl.ws.individual.response.Body();
        response.setBody(body);

        doReturn(response).when(loanSubmissionGetCustomerInfoClient).searchCustomerInfoByCaID(anyLong());
    }

    private void mockGetLoanSubmissionCustomerInfoFail() throws ServiceException, RemoteException, TMBCommonException, JsonProcessingException {
        ResponseIndividual response = new ResponseIndividual();

        com.tmb.common.model.legacy.rsl.ws.individual.response.Header header = new com.tmb.common.model.legacy.rsl.ws.individual.response.Header();
        header.setResponseCode(RslResponseCode.FAIL.getCode());
        header.setResponseDescriptionEN("rsl failed");
        response.setHeader(header);

        com.tmb.common.model.legacy.rsl.ws.individual.response.Body body = new com.tmb.common.model.legacy.rsl.ws.individual.response.Body();
        response.setBody(body);

        doReturn(response).when(loanSubmissionGetCustomerInfoClient).searchCustomerInfoByCaID(anyLong());
    }

    private void mockGetLoanSubmissionDropdownListSuccess() throws ServiceException, TMBCommonException, JsonProcessingException {
        ResponseDropdown response = new ResponseDropdown();

        com.tmb.common.model.legacy.rsl.ws.dropdown.response.Header header = new com.tmb.common.model.legacy.rsl.ws.dropdown.response.Header();
        header.setResponseCode(RslResponseCode.SUCCESS.getCode());
        response.setHeader(header);

        com.tmb.common.model.legacy.rsl.ws.dropdown.response.Body body = new com.tmb.common.model.legacy.rsl.ws.dropdown.response.Body();
        response.setBody(body);

        doReturn(response).when(loanSubmissionGetDropdownListClient).getDropDownListByCode(anyString());
    }

    private void mockGetLoanSubmissionDropdownListFail() throws ServiceException, TMBCommonException, JsonProcessingException {
        ResponseDropdown response = new ResponseDropdown();

        com.tmb.common.model.legacy.rsl.ws.dropdown.response.Header header = new com.tmb.common.model.legacy.rsl.ws.dropdown.response.Header();
        header.setResponseCode(RslResponseCode.FAIL.getCode());
        header.setResponseDescriptionEN("rsl failed");
        response.setHeader(header);

        com.tmb.common.model.legacy.rsl.ws.dropdown.response.Body body = new com.tmb.common.model.legacy.rsl.ws.dropdown.response.Body();
        response.setBody(body);

        doReturn(response).when(loanSubmissionGetDropdownListClient).getDropDownListByCode(anyString());
    }

    private void mockGetLoanSubmissionFacilityInfoSuccess() throws ServiceException, TMBCommonException, JsonProcessingException {
        ResponseFacility response = new ResponseFacility();

        com.tmb.common.model.legacy.rsl.ws.facility.response.Header header = new com.tmb.common.model.legacy.rsl.ws.facility.response.Header();
        header.setResponseCode(RslResponseCode.SUCCESS.getCode());
        response.setHeader(header);

        com.tmb.common.model.legacy.rsl.ws.facility.response.Body body = new com.tmb.common.model.legacy.rsl.ws.facility.response.Body();
        response.setBody(body);

        doReturn(response).when(loanSubmissionGetFacilityInfoClient).searchFacilityInfoByCaID(anyLong());
    }

    private void mockGetLoanSubmissionFacilityInfoFail() throws ServiceException, TMBCommonException, JsonProcessingException {
        ResponseFacility response = new ResponseFacility();

        com.tmb.common.model.legacy.rsl.ws.facility.response.Header header = new com.tmb.common.model.legacy.rsl.ws.facility.response.Header();
        header.setResponseCode(RslResponseCode.FAIL.getCode());
        header.setResponseDescriptionEN("rsl failed");
        response.setHeader(header);

        com.tmb.common.model.legacy.rsl.ws.facility.response.Body body = new com.tmb.common.model.legacy.rsl.ws.facility.response.Body();
        response.setBody(body);

        doReturn(response).when(loanSubmissionGetFacilityInfoClient).searchFacilityInfoByCaID(anyLong());
    }

    private void mockGetLoanSubmissionInstantLoanCalUWSuccess() throws ServiceException, TMBCommonException, JsonProcessingException {
        ResponseInstantLoanCalUW response = new ResponseInstantLoanCalUW();

        com.tmb.common.model.legacy.rsl.ws.instant.calculate.uw.response.Header header = new com.tmb.common.model.legacy.rsl.ws.instant.calculate.uw.response.Header();
        header.setResponseCode(RslResponseCode.SUCCESS.getCode());
        response.setHeader(header);

        com.tmb.common.model.legacy.rsl.ws.instant.calculate.uw.response.Body body = new com.tmb.common.model.legacy.rsl.ws.instant.calculate.uw.response.Body();
        response.setBody(body);

        doReturn(response).when(loanSubmissionInstantLoanCalUWClient).calculateUnderwriting(any(), any());
    }

    private void mockGetLoanSubmissionInstantLoanCalUWFail() throws ServiceException, TMBCommonException, JsonProcessingException {
        ResponseInstantLoanCalUW response = new ResponseInstantLoanCalUW();

        com.tmb.common.model.legacy.rsl.ws.instant.calculate.uw.response.Header header = new com.tmb.common.model.legacy.rsl.ws.instant.calculate.uw.response.Header();
        header.setResponseCode(RslResponseCode.FAIL.getCode());
        header.setResponseDescriptionEN("rsl failed");
        response.setHeader(header);

        com.tmb.common.model.legacy.rsl.ws.instant.calculate.uw.response.Body body = new com.tmb.common.model.legacy.rsl.ws.instant.calculate.uw.response.Body();
        response.setBody(body);

        doReturn(response).when(loanSubmissionInstantLoanCalUWClient).calculateUnderwriting(any(), any());
    }

    private void mockLoanInstantGetCustomerInfoSuccess() throws ServiceException, TMBCommonException, JsonProcessingException {
        ResponseInstantLoanGetCustInfo response = new ResponseInstantLoanGetCustInfo();

        com.tmb.common.model.legacy.rsl.ws.instant.eligible.customer.response.Header header = new com.tmb.common.model.legacy.rsl.ws.instant.eligible.customer.response.Header();
        header.setResponseCode(RslResponseCode.SUCCESS.getCode());
        response.setHeader(header);

        com.tmb.common.model.legacy.rsl.ws.instant.eligible.customer.response.Body body = new com.tmb.common.model.legacy.rsl.ws.instant.eligible.customer.response.Body();
        response.setBody(body);

        doReturn(response).when(loanSubmissionInstantLoanGetCustomerInfoClient).getInstantCustomerInfo(anyString());
    }

    private void mockLoanInstantGetCustomerInfoFail() throws ServiceException, TMBCommonException, JsonProcessingException {
        ResponseInstantLoanGetCustInfo response = new ResponseInstantLoanGetCustInfo();

        com.tmb.common.model.legacy.rsl.ws.instant.eligible.customer.response.Header header = new com.tmb.common.model.legacy.rsl.ws.instant.eligible.customer.response.Header();
        header.setResponseCode(RslResponseCode.FAIL.getCode());
        header.setResponseDescriptionEN("rsl failed");
        response.setHeader(header);

        com.tmb.common.model.legacy.rsl.ws.instant.eligible.customer.response.Body body = new com.tmb.common.model.legacy.rsl.ws.instant.eligible.customer.response.Body();
        response.setBody(body);

        doReturn(response).when(loanSubmissionInstantLoanGetCustomerInfoClient).getInstantCustomerInfo(anyString());
    }

    private void mockSubmitInstantLoanApplicationSuccess() throws ServiceException, TMBCommonException, JsonProcessingException {
        ResponseInstantLoanSubmit response = new ResponseInstantLoanSubmit();

        com.tmb.common.model.legacy.rsl.ws.instant.submit.response.Header header = new com.tmb.common.model.legacy.rsl.ws.instant.submit.response.Header();
        header.setResponseCode(RslResponseCode.SUCCESS.getCode());
        response.setHeader(header);

        com.tmb.common.model.legacy.rsl.ws.instant.submit.response.Body body = new com.tmb.common.model.legacy.rsl.ws.instant.submit.response.Body();
        response.setBody(body);

        doReturn(response).when(loanSubmissionInstantLoanSubmitApplicationClient).submitInstantLoanApplication(any(), any());
    }

    private void mockSubmitInstantLoanApplicationFail() throws ServiceException, TMBCommonException, JsonProcessingException {
        ResponseInstantLoanSubmit response = new ResponseInstantLoanSubmit();

        com.tmb.common.model.legacy.rsl.ws.instant.submit.response.Header header = new com.tmb.common.model.legacy.rsl.ws.instant.submit.response.Header();
        header.setResponseCode(RslResponseCode.FAIL.getCode());
        header.setResponseDescriptionEN("rsl failed");
        response.setHeader(header);

        com.tmb.common.model.legacy.rsl.ws.instant.submit.response.Body body = new com.tmb.common.model.legacy.rsl.ws.instant.submit.response.Body();
        response.setBody(body);

        doReturn(response).when(loanSubmissionInstantLoanSubmitApplicationClient).submitInstantLoanApplication(any(), any());
    }

    private void mockUpdateFacilityInfoSuccess() throws ServiceException, TMBCommonException, JsonProcessingException {
        com.tmb.common.model.legacy.rsl.ws.facility.update.response.ResponseFacility response = new com.tmb.common.model.legacy.rsl.ws.facility.update.response.ResponseFacility();

        com.tmb.common.model.legacy.rsl.ws.facility.update.response.Header header = new com.tmb.common.model.legacy.rsl.ws.facility.update.response.Header();
        header.setResponseCode(RslResponseCode.SUCCESS.getCode());
        response.setHeader(header);

        com.tmb.common.model.legacy.rsl.ws.facility.update.response.Body body = new com.tmb.common.model.legacy.rsl.ws.facility.update.response.Body();
        response.setBody(body);

        doReturn(response).when(loanSubmissionUpdateFacilityInfoClient).updateFacilityInfo(any());
    }

    private void mockUpdateFacilityInfoFail() throws ServiceException, TMBCommonException, JsonProcessingException {
        com.tmb.common.model.legacy.rsl.ws.facility.update.response.ResponseFacility response = new com.tmb.common.model.legacy.rsl.ws.facility.update.response.ResponseFacility();

        com.tmb.common.model.legacy.rsl.ws.facility.update.response.Header header = new com.tmb.common.model.legacy.rsl.ws.facility.update.response.Header();
        header.setResponseCode(RslResponseCode.FAIL.getCode());
        header.setResponseDescriptionEN("rsl failed");
        response.setHeader(header);

        com.tmb.common.model.legacy.rsl.ws.facility.update.response.Body body = new com.tmb.common.model.legacy.rsl.ws.facility.update.response.Body();
        response.setBody(body);

        doReturn(response).when(loanSubmissionUpdateFacilityInfoClient).updateFacilityInfo(any());
    }

    private void mockUpdateCustomerInfoSuccess() throws ServiceException, TMBCommonException, JsonProcessingException {
        com.tmb.common.model.legacy.rsl.ws.individual.update.response.ResponseIndividual response = new com.tmb.common.model.legacy.rsl.ws.individual.update.response.ResponseIndividual();

        Header header = new Header();
        header.setResponseCode(RslResponseCode.SUCCESS.getCode());
        response.setHeader(header);

        Body body = new Body();
        response.setBody(body);

        doReturn(response).when(loanSubmissionUpdateCustomerClient).updateCustomerInfo(any());
    }

    private void mockUpdateCustomerInfoFail() throws ServiceException, TMBCommonException, JsonProcessingException {
        com.tmb.common.model.legacy.rsl.ws.individual.update.response.ResponseIndividual response = new com.tmb.common.model.legacy.rsl.ws.individual.update.response.ResponseIndividual();

        Header header = new Header();
        header.setResponseCode(RslResponseCode.FAIL.getCode());
        header.setResponseDescriptionEN("rsl failed");
        response.setHeader(header);

        Body body = new Body();
        response.setBody(body);

        doReturn(response).when(loanSubmissionUpdateCustomerClient).updateCustomerInfo(any());
    }

    private void mockGetLoanSubmissionChecklistInfoSuccess() throws ServiceException, TMBCommonException, JsonProcessingException {
        ResponseChecklist response = new ResponseChecklist();

        com.tmb.common.model.legacy.rsl.ws.checklist.response.Header header = new com.tmb.common.model.legacy.rsl.ws.checklist.response.Header();
        header.setResponseCode(RslResponseCode.SUCCESS.getCode());
        response.setHeader(header);

        com.tmb.common.model.legacy.rsl.ws.checklist.response.Body body = new com.tmb.common.model.legacy.rsl.ws.checklist.response.Body();
        response.setBody(body);

        doReturn(response).when(loanSubmissionGetChecklistInfoClient).getChecklistInfo(anyLong(), anyString());
    }

    private void mockGetLoanSubmissionChecklistInfoFail() throws ServiceException, TMBCommonException, JsonProcessingException {
        ResponseChecklist response = new ResponseChecklist();

        com.tmb.common.model.legacy.rsl.ws.checklist.response.Header header = new com.tmb.common.model.legacy.rsl.ws.checklist.response.Header();
        header.setResponseCode(RslResponseCode.FAIL.getCode());
        header.setResponseDescriptionEN("rsl failed");
        response.setHeader(header);

        com.tmb.common.model.legacy.rsl.ws.checklist.response.Body body = new com.tmb.common.model.legacy.rsl.ws.checklist.response.Body();
        response.setBody(body);

        doReturn(response).when(loanSubmissionGetChecklistInfoClient).getChecklistInfo(anyLong(), anyString());
    }

    private void mockUpdateNCBConsentFlagSuccess() throws ServiceException, TMBCommonException, JsonProcessingException {
        com.tmb.common.model.legacy.rsl.ws.ncb.consent.flag.update.response.ResponseUpdateNCBConsentFlag response = new com.tmb.common.model.legacy.rsl.ws.ncb.consent.flag.update.response.ResponseUpdateNCBConsentFlag();

        com.tmb.common.model.legacy.rsl.ws.ncb.consent.flag.update.response.Header header = new com.tmb.common.model.legacy.rsl.ws.ncb.consent.flag.update.response.Header();
        header.setResponseCode(RslResponseCode.SUCCESS.getCode());
        response.setHeader(header);

        com.tmb.common.model.legacy.rsl.ws.ncb.consent.flag.update.response.Body body = new com.tmb.common.model.legacy.rsl.ws.ncb.consent.flag.update.response.Body();
        response.setBody(body);

        doReturn(response).when(loanSubmissionUpdateNCBConsentFlagClient).updateNCBConsentFlag(any());
    }

    private void mockUpdateNCBConsentFlagFail() throws ServiceException, TMBCommonException, JsonProcessingException {
        com.tmb.common.model.legacy.rsl.ws.ncb.consent.flag.update.response.ResponseUpdateNCBConsentFlag response = new com.tmb.common.model.legacy.rsl.ws.ncb.consent.flag.update.response.ResponseUpdateNCBConsentFlag();

        com.tmb.common.model.legacy.rsl.ws.ncb.consent.flag.update.response.Header header = new com.tmb.common.model.legacy.rsl.ws.ncb.consent.flag.update.response.Header();
        header.setResponseCode(RslResponseCode.FAIL.getCode());
        header.setResponseDescriptionEN("rsl failed");
        response.setHeader(header);

        com.tmb.common.model.legacy.rsl.ws.ncb.consent.flag.update.response.Body body = new com.tmb.common.model.legacy.rsl.ws.ncb.consent.flag.update.response.Body();
        response.setBody(body);

        doReturn(response).when(loanSubmissionUpdateNCBConsentFlagClient).updateNCBConsentFlag(any());
    }

    private void mockTransferApplicationSuccess() throws ServiceException, TMBCommonException, JsonProcessingException {
        com.tmb.common.model.legacy.rsl.ws.instant.transfer.response.ResponseTransfer response = new com.tmb.common.model.legacy.rsl.ws.instant.transfer.response.ResponseTransfer();

        com.tmb.common.model.legacy.rsl.ws.instant.transfer.response.Header header = new com.tmb.common.model.legacy.rsl.ws.instant.transfer.response.Header();
        header.setResponseCode(RslResponseCode.SUCCESS.getCode());
        response.setHeader(header);

        com.tmb.common.model.legacy.rsl.ws.instant.transfer.response.Body body = new com.tmb.common.model.legacy.rsl.ws.instant.transfer.response.Body();
        response.setBody(body);

        doReturn(response).when(loanSubmissionInstantLoanTransferApplicationClient).transferApplication(any());
    }

    private void mockTransferApplicationFail() throws ServiceException, TMBCommonException, JsonProcessingException {
        com.tmb.common.model.legacy.rsl.ws.instant.transfer.response.ResponseTransfer response = new com.tmb.common.model.legacy.rsl.ws.instant.transfer.response.ResponseTransfer();

        com.tmb.common.model.legacy.rsl.ws.instant.transfer.response.Header header = new com.tmb.common.model.legacy.rsl.ws.instant.transfer.response.Header();
        header.setResponseCode(RslResponseCode.FAIL.getCode());
        header.setResponseDescriptionEN("rsl failed");
        response.setHeader(header);

        com.tmb.common.model.legacy.rsl.ws.instant.transfer.response.Body body = new com.tmb.common.model.legacy.rsl.ws.instant.transfer.response.Body();
        response.setBody(body);

        doReturn(response).when(loanSubmissionInstantLoanTransferApplicationClient).transferApplication(any());
    }

    //Loan Submission Update Credit Card
    @Test
    public void updateUpdateCreditCardInfo_Success() throws ServiceException, TMBCommonException, JsonProcessingException {
        mockUpdateCreditCardInfoSuccess();
        CreditCard request = new CreditCard();
        com.tmb.common.model.legacy.rsl.ws.creditcard.update.response.ResponseCreditcard response = rslService.updateCreditCardInfo(request);
        Assertions.assertEquals(RslResponseCode.SUCCESS.getCode(), response.getHeader().getResponseCode());
    }

    @Test
    public void updateCreditCardInfo_RslConnectionError() {
        TMBCommonException exception = assertThrows(TMBCommonException.class, () -> {
            doThrow(new TMBCommonException(ResponseCode.RSL_CONNECTION_ERROR.getCode(), ResponseCode.RSL_CONNECTION_ERROR.getMessage(), ResponseCode.RSL_CONNECTION_ERROR.getService(), HttpStatus.INTERNAL_SERVER_ERROR, null))
                    .when(loanSubmissionUpdateCreditCardClient).updateCreditCard(any());

            CreditCard request = new CreditCard();
            rslService.updateCreditCardInfo(request);
        });

        Assertions.assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, exception.getStatus());
        Assertions.assertEquals(ResponseCode.RSL_CONNECTION_ERROR.getCode(), exception.getErrorCode());
        Assertions.assertEquals(ResponseCode.RSL_CONNECTION_ERROR.getMessage(), exception.getErrorMessage());
    }

    @Test
    public void updateCreditCardInfo_RslFail() {
        TMBCommonException exception = assertThrows(TMBCommonException.class, () -> {
            mockUpdateCreditCardInfoFail();
            CreditCard request = new CreditCard();
            rslService.updateCreditCardInfo(request);
        });

        Assertions.assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, exception.getStatus());
        Assertions.assertEquals(ResponseCode.RSL_FAILED.getCode(), exception.getErrorCode());
        Assertions.assertEquals(String.format("[%s] %s", RslResponseCode.FAIL.getCode(), ResponseCode.RSL_FAILED.getMessage()), exception.getErrorMessage());
    }


    private void mockUpdateCreditCardInfoSuccess() throws ServiceException, TMBCommonException, JsonProcessingException {
        com.tmb.common.model.legacy.rsl.ws.creditcard.update.response.ResponseCreditcard response = new com.tmb.common.model.legacy.rsl.ws.creditcard.update.response.ResponseCreditcard();

        com.tmb.common.model.legacy.rsl.ws.creditcard.update.response.Header header = new com.tmb.common.model.legacy.rsl.ws.creditcard.update.response.Header();
        header.setResponseCode(RslResponseCode.SUCCESS.getCode());
        response.setHeader(header);

        com.tmb.common.model.legacy.rsl.ws.creditcard.update.response.Body body = new com.tmb.common.model.legacy.rsl.ws.creditcard.update.response.Body();
        response.setBody(body);

        doReturn(response).when(loanSubmissionUpdateCreditCardClient).updateCreditCard(any());
    }

    private void mockUpdateCreditCardInfoFail() throws ServiceException, TMBCommonException, JsonProcessingException {
        com.tmb.common.model.legacy.rsl.ws.creditcard.update.response.ResponseCreditcard response = new com.tmb.common.model.legacy.rsl.ws.creditcard.update.response.ResponseCreditcard();

        com.tmb.common.model.legacy.rsl.ws.creditcard.update.response.Header header = new com.tmb.common.model.legacy.rsl.ws.creditcard.update.response.Header();
        header.setResponseCode(RslResponseCode.FAIL.getCode());
        header.setResponseDescriptionEN("rsl failed");
        response.setHeader(header);

        com.tmb.common.model.legacy.rsl.ws.creditcard.update.response.Body body = new com.tmb.common.model.legacy.rsl.ws.creditcard.update.response.Body();
        response.setBody(body);

        doReturn(response).when(loanSubmissionUpdateCreditCardClient).updateCreditCard(any());
    }

    private void mockUpdateIncompleteDocApplicationSuccess() throws ServiceException, TMBCommonException, JsonProcessingException {
        ResponseDocApplication response = new ResponseDocApplication();

        com.tmb.common.model.legacy.rsl.ws.doc.application.response.Header header = new com.tmb.common.model.legacy.rsl.ws.doc.application.response.Header();
        header.setResponseCode(RslResponseCode.SUCCESS.getCode());
        header.setResponseDescriptionEN(RslResponseCode.SUCCESS.getMessage());
        response.setHeader(header);

        com.tmb.common.model.legacy.rsl.ws.doc.application.response.Body body = new com.tmb.common.model.legacy.rsl.ws.doc.application.response.Body();
        response.setBody(body);

        doReturn(response).when(loanSubmissionUpdateIncompleteDocApplicationClient).updateIncompleteDocApplication(any());
    }

    private void mockUpdateIncompleteDocApplicationFail() throws ServiceException, TMBCommonException, JsonProcessingException {
        ResponseDocApplication response = new ResponseDocApplication();

        com.tmb.common.model.legacy.rsl.ws.doc.application.response.Header header = new com.tmb.common.model.legacy.rsl.ws.doc.application.response.Header();
        header.setResponseCode(RslResponseCode.FAIL.getCode());
        header.setResponseDescriptionEN("rsl failed");
        response.setHeader(header);

        com.tmb.common.model.legacy.rsl.ws.doc.application.response.Body body = new com.tmb.common.model.legacy.rsl.ws.doc.application.response.Body();
        response.setBody(body);

        doReturn(response).when(loanSubmissionUpdateIncompleteDocApplicationClient).updateIncompleteDocApplication(any());
    }

    //Loan Submission Update Incomplete Doc Application
    @Test
    public void updateIncompleteDocApplication_Success() throws TMBCommonException, ServiceException, JsonProcessingException {
        mockUpdateIncompleteDocApplicationSuccess();
        DocApplication request = new DocApplication();
        request.setCaId(1L);
        request.setUpdateFlag("Y");

        ResponseDocApplication response = rslService.updateIncompleteDocApplication(request);
        Assertions.assertEquals(RslResponseCode.SUCCESS.getCode(), response.getHeader().getResponseCode());
    }

    @Test
    public void updateIncompleteDocApplication_RslConnectionError() {
        TMBCommonException exception = assertThrows(TMBCommonException.class, () -> {
            doThrow(new TMBCommonException(ResponseCode.RSL_CONNECTION_ERROR.getCode(), ResponseCode.RSL_CONNECTION_ERROR.getMessage(), ResponseCode.RSL_CONNECTION_ERROR.getService(), HttpStatus.INTERNAL_SERVER_ERROR, null))
                    .when(loanSubmissionUpdateIncompleteDocApplicationClient).updateIncompleteDocApplication(any());

            DocApplication request = new DocApplication();
            request.setCaId(1L);
            request.setUpdateFlag("Y");
            rslService.updateIncompleteDocApplication(request);
        });

        Assertions.assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, exception.getStatus());
        Assertions.assertEquals(ResponseCode.RSL_CONNECTION_ERROR.getCode(), exception.getErrorCode());
        Assertions.assertEquals(ResponseCode.RSL_CONNECTION_ERROR.getMessage(), exception.getErrorMessage());
    }

    @Test
    public void updateIncompleteDocApplication_RslFail() {
        TMBCommonException exception = assertThrows(TMBCommonException.class, () -> {
            mockUpdateIncompleteDocApplicationFail();
            DocApplication request = new DocApplication();
            request.setCaId(1L);
            request.setUpdateFlag("Y");
            rslService.updateIncompleteDocApplication(request);
        });

        Assertions.assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, exception.getStatus());
        Assertions.assertEquals(ResponseCode.RSL_FAILED.getCode(), exception.getErrorCode());
        Assertions.assertEquals(String.format("[%s] %s", RslResponseCode.FAIL.getCode(), ResponseCode.RSL_FAILED.getMessage()), exception.getErrorMessage());
    }


}
