package com.tmb.oneapp.lendingservice.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.tmb.common.exception.model.TMBCommonException;
import com.tmb.common.model.legacy.rsl.common.ob.facility.Facility;
import com.tmb.common.model.legacy.rsl.ws.application.response.ResponseApplication;
import com.tmb.common.model.legacy.rsl.ws.creditcard.response.ResponseCreditcard;
import com.tmb.common.model.legacy.rsl.ws.dropdown.response.ResponseDropdown;
import com.tmb.common.model.legacy.rsl.ws.facility.response.ResponseFacility;
import com.tmb.common.model.legacy.rsl.ws.individual.response.ResponseIndividual;
import com.tmb.common.model.legacy.rsl.ws.instant.calculate.uw.response.ResponseInstantLoanCalUW;
import com.tmb.common.model.legacy.rsl.ws.instant.eligible.customer.response.ResponseInstantLoanGetCustInfo;
import com.tmb.common.model.legacy.rsl.ws.instant.submit.response.ResponseInstantLoanSubmit;
import com.tmb.oneapp.lendingservice.client.*;
import com.tmb.oneapp.lendingservice.constant.ResponseCode;
import com.tmb.oneapp.lendingservice.constant.RslResponseCodeEnum;
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

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
    }


    //Loan Submission Get Application Info
    @Test
    public void getLoanSubmissionApplicationInfo_Success() throws ServiceException, TMBCommonException, RemoteException {
        mockGetLoanSubmissionApplicationInfoSuccess();
        LoanSubmissionGetApplicationInfoRequest request = new LoanSubmissionGetApplicationInfoRequest();
        request.setCaId("1");
        ResponseApplication response = rslService.getLoanSubmissionApplicationInfo(request);
        Assertions.assertEquals(RslResponseCodeEnum.SUCCESS.getCode(), response.getHeader().getResponseCode());
    }

    @Test
    public void getLoanSubmissionApplicationInfo_InvalidData() {
        TMBCommonException exception = assertThrows(TMBCommonException.class, () -> {
            doThrow(new TMBCommonException(ResponseCode.RSL_CONNECTION_ERROR.getCode(), ResponseCode.RSL_CONNECTION_ERROR.getMessage(), ResponseCode.RSL_CONNECTION_ERROR.getService(), HttpStatus.INTERNAL_SERVER_ERROR, null))
                    .when(loanSubmissionGetApplicationInfoClient).searchApplicationInfoByCaID(anyLong());

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
        Assertions.assertEquals(String.format("[%s] %s", RslResponseCodeEnum.FAIL.getCode(), ResponseCode.RSL_FAILED.getMessage()), exception.getErrorMessage());
    }


    //Loan Submission Get Creditcard Info
    @Test
    public void getLoanSubmissionCreditCardInfo_Success() throws ServiceException, TMBCommonException, JsonProcessingException {
        mockGetLoanSubmissionCreditCardInfoSuccess();
        LoanSubmissionGetCreditcardInfoRequest request = new LoanSubmissionGetCreditcardInfoRequest();
        request.setCaId("1");
        ResponseCreditcard response = rslService.getLoanSubmissionCreditCardInfo(request);
        Assertions.assertEquals(RslResponseCodeEnum.SUCCESS.getCode(), response.getHeader().getResponseCode());
    }

    @Test
    public void getLoanSubmissionCreditCardInfo_InvalidData() {
        TMBCommonException exception = assertThrows(TMBCommonException.class, () -> {
            doThrow(new TMBCommonException(ResponseCode.RSL_CONNECTION_ERROR.getCode(), ResponseCode.RSL_CONNECTION_ERROR.getMessage(), ResponseCode.RSL_CONNECTION_ERROR.getService(), HttpStatus.INTERNAL_SERVER_ERROR, null))
                    .when(loanSubmissionGetCreditcardInfoClient).searchCreditcardInfoByCaID(anyLong());

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
        Assertions.assertEquals(String.format("[%s] %s", RslResponseCodeEnum.FAIL.getCode(), ResponseCode.RSL_FAILED.getMessage()), exception.getErrorMessage());
    }


    //Loan Submission Get Customer Info
    @Test
    public void getLoanSubmissionCustomerInfo_Success() throws ServiceException, TMBCommonException, JsonProcessingException, RemoteException {
        mockGetLoanSubmissionCustomerInfoSuccess();
        LoanSubmissionGetCustomerInfoRequest request = new LoanSubmissionGetCustomerInfoRequest();
        request.setCaId("1");
        ResponseIndividual response = rslService.getLoanSubmissionCustomerInfo(request);
        Assertions.assertEquals(RslResponseCodeEnum.SUCCESS.getCode(), response.getHeader().getResponseCode());
    }

    @Test
    public void getLoanSubmissionCustomerInfo_InvalidData() {
        TMBCommonException exception = assertThrows(TMBCommonException.class, () -> {
            doThrow(new TMBCommonException(ResponseCode.RSL_CONNECTION_ERROR.getCode(), ResponseCode.RSL_CONNECTION_ERROR.getMessage(), ResponseCode.RSL_CONNECTION_ERROR.getService(), HttpStatus.INTERNAL_SERVER_ERROR, null))
                    .when(loanSubmissionGetCustomerInfoClient).searchCustomerInfoByCaID(anyLong());

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
        Assertions.assertEquals(String.format("[%s] %s", RslResponseCodeEnum.FAIL.getCode(), ResponseCode.RSL_FAILED.getMessage()), exception.getErrorMessage());
    }


    //Loan Submission Get Dropdown List
    @Test
    public void getLoanSubmissionDropdownList_Success() throws ServiceException, TMBCommonException, JsonProcessingException {
        mockGetLoanSubmissionDropdownListSuccess();
        LoanSubmissionGetDropdownListRequest request = new LoanSubmissionGetDropdownListRequest();
        request.setCategoryCode("categoryCode");
        ResponseDropdown response = rslService.getLoanSubmissionDropdownList(request);
        Assertions.assertEquals(RslResponseCodeEnum.SUCCESS.getCode(), response.getHeader().getResponseCode());
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
        Assertions.assertEquals(String.format("[%s] %s", RslResponseCodeEnum.FAIL.getCode(), ResponseCode.RSL_FAILED.getMessage()), exception.getErrorMessage());
    }


    //Loan Submission Get Facility Info
    @Test
    public void getLoanSubmissionFacilityInfo_Success() throws ServiceException, TMBCommonException, JsonProcessingException {
        mockGetLoanSubmissionFacilityInfoSuccess();
        LoanSubmissionGetFacilityInfoRequest request = new LoanSubmissionGetFacilityInfoRequest();
        request.setCaId("1");
        ResponseFacility response = rslService.getLoanSubmissionFacilityInfo(request);
        Assertions.assertEquals(RslResponseCodeEnum.SUCCESS.getCode(), response.getHeader().getResponseCode());
    }

    @Test
    public void getLoanSubmissionFacilityInfo_InvalidData() {
        TMBCommonException exception = assertThrows(TMBCommonException.class, () -> {
            doThrow(new TMBCommonException(ResponseCode.RSL_CONNECTION_ERROR.getCode(), ResponseCode.RSL_CONNECTION_ERROR.getMessage(), ResponseCode.RSL_CONNECTION_ERROR.getService(), HttpStatus.INTERNAL_SERVER_ERROR, null))
                    .when(loanSubmissionGetFacilityInfoClient).searchFacilityInfoByCaID(anyLong());

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
        Assertions.assertEquals(String.format("[%s] %s", RslResponseCodeEnum.FAIL.getCode(), ResponseCode.RSL_FAILED.getMessage()), exception.getErrorMessage());
    }


    //Loan Submission Instant Loan CalUW
    @Test
    public void getLoanSubmissionInstantLoanCalUW_Success() throws ServiceException, TMBCommonException, JsonProcessingException {
        mockGetLoanSubmissionInstantLoanCalUWSuccess();
        LoanSubmissionInstantLoanCalUWRequest request = new LoanSubmissionInstantLoanCalUWRequest();
        request.setTriggerFlag("Y");
        request.setCaId("1");
        ResponseInstantLoanCalUW response = rslService.getLoanSubmissionInstantLoanCalUW(request);
        Assertions.assertEquals(RslResponseCodeEnum.SUCCESS.getCode(), response.getHeader().getResponseCode());
    }

    @Test
    public void getLoanSubmissionInstantLoanCalUW_InvalidData() {
        TMBCommonException exception = assertThrows(TMBCommonException.class, () -> {
            doThrow(new TMBCommonException(ResponseCode.RSL_CONNECTION_ERROR.getCode(), ResponseCode.RSL_CONNECTION_ERROR.getMessage(), ResponseCode.RSL_CONNECTION_ERROR.getService(), HttpStatus.INTERNAL_SERVER_ERROR, null))
                    .when(loanSubmissionInstantLoanCalUWClient).calculateUnderwriting(any(), any());

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
        Assertions.assertEquals(String.format("[%s] %s", RslResponseCodeEnum.FAIL.getCode(), ResponseCode.RSL_FAILED.getMessage()), exception.getErrorMessage());
    }


    //Loan Submission Instant Loan Get Customer Info
    @Test
    public void getSubmissionInstantLoanCustomerInfo_Success() throws ServiceException, TMBCommonException, JsonProcessingException {
        mockLoanInstantGetCustomerInfoSuccess();
        String rmId = "00000018593707";
        ResponseInstantLoanGetCustInfo response = rslService.getSubmissionInstantLoanCustomerInfo(rmId);
        Assertions.assertEquals(RslResponseCodeEnum.SUCCESS.getCode(), response.getHeader().getResponseCode());
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
        Assertions.assertEquals(String.format("[%s] %s", RslResponseCodeEnum.FAIL.getCode(), ResponseCode.RSL_FAILED.getMessage()), exception.getErrorMessage());
    }


    //Loan Submission Instant Loan Submit Application
    @Test
    public void submitInstantLoanApplication_Success() throws ServiceException, TMBCommonException, JsonProcessingException {
        mockSubmitInstantLoanApplicationSuccess();
        LoanSubmissionInstantLoanSubmitApplicationRequest request = new LoanSubmissionInstantLoanSubmitApplicationRequest();
        request.setSubmittedFlag("Y");
        request.setCaId("1");
        ResponseInstantLoanSubmit response = rslService.submitInstantLoanApplication(request);
        Assertions.assertEquals(RslResponseCodeEnum.SUCCESS.getCode(), response.getHeader().getResponseCode());
    }

    @Test
    public void submitInstantLoanApplication_InvalidData() {
        TMBCommonException exception = assertThrows(TMBCommonException.class, () -> {
            doThrow(new TMBCommonException(ResponseCode.RSL_CONNECTION_ERROR.getCode(), ResponseCode.RSL_CONNECTION_ERROR.getMessage(), ResponseCode.RSL_CONNECTION_ERROR.getService(), HttpStatus.INTERNAL_SERVER_ERROR, null))
                    .when(loanSubmissionInstantLoanSubmitApplicationClient).submitInstantLoanApplication(any(), any());

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
        Assertions.assertEquals(String.format("[%s] %s", RslResponseCodeEnum.FAIL.getCode(), ResponseCode.RSL_FAILED.getMessage()), exception.getErrorMessage());
    }


    //Loan Submission Update Facility
    @Test
    public void updateFacilityInfo_Success() throws ServiceException, TMBCommonException, JsonProcessingException {
        mockUpdateFacilityInfoSuccess();
        Facility request = new Facility();
        com.tmb.common.model.legacy.rsl.ws.facility.update.response.ResponseFacility response = rslService.updateFacilityInfo(request);
        Assertions.assertEquals(RslResponseCodeEnum.SUCCESS.getCode(), response.getHeader().getResponseCode());
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
        Assertions.assertEquals(String.format("[%s] %s", RslResponseCodeEnum.FAIL.getCode(), ResponseCode.RSL_FAILED.getMessage()), exception.getErrorMessage());
    }


    //Mock Data
    private void mockGetLoanSubmissionApplicationInfoSuccess() throws ServiceException, RemoteException {
        ResponseApplication response = new ResponseApplication();

        com.tmb.common.model.legacy.rsl.ws.application.response.Header header = new com.tmb.common.model.legacy.rsl.ws.application.response.Header();
        header.setResponseCode(RslResponseCodeEnum.SUCCESS.getCode());
        response.setHeader(header);

        com.tmb.common.model.legacy.rsl.ws.application.response.Body body = new com.tmb.common.model.legacy.rsl.ws.application.response.Body();
        response.setBody(body);

        doReturn(response).when(loanSubmissionGetApplicationInfoClient).searchApplicationInfoByCaID(anyLong());
    }

    //Mock Data
    private void mockGetLoanSubmissionApplicationInfoFail() throws ServiceException, RemoteException {
        ResponseApplication response = new ResponseApplication();

        com.tmb.common.model.legacy.rsl.ws.application.response.Header header = new com.tmb.common.model.legacy.rsl.ws.application.response.Header();
        header.setResponseCode(RslResponseCodeEnum.FAIL.getCode());
        header.setResponseDescriptionEN("rsl failed");
        response.setHeader(header);

        com.tmb.common.model.legacy.rsl.ws.application.response.Body body = new com.tmb.common.model.legacy.rsl.ws.application.response.Body();
        response.setBody(body);

        doReturn(response).when(loanSubmissionGetApplicationInfoClient).searchApplicationInfoByCaID(anyLong());
    }

    private void mockGetLoanSubmissionCreditCardInfoSuccess() throws ServiceException, TMBCommonException, JsonProcessingException {
        ResponseCreditcard response = new ResponseCreditcard();

        com.tmb.common.model.legacy.rsl.ws.creditcard.response.Header header = new com.tmb.common.model.legacy.rsl.ws.creditcard.response.Header();
        header.setResponseCode(RslResponseCodeEnum.SUCCESS.getCode());
        response.setHeader(header);

        com.tmb.common.model.legacy.rsl.ws.creditcard.response.Body body = new com.tmb.common.model.legacy.rsl.ws.creditcard.response.Body();
        response.setBody(body);

        doReturn(response).when(loanSubmissionGetCreditcardInfoClient).searchCreditcardInfoByCaID(anyLong());
    }

    private void mockGetLoanSubmissionCreditCardInfoFail() throws ServiceException, TMBCommonException, JsonProcessingException {
        ResponseCreditcard response = new ResponseCreditcard();

        com.tmb.common.model.legacy.rsl.ws.creditcard.response.Header header = new com.tmb.common.model.legacy.rsl.ws.creditcard.response.Header();
        header.setResponseCode(RslResponseCodeEnum.FAIL.getCode());
        header.setResponseDescriptionEN("rsl failed");
        response.setHeader(header);

        com.tmb.common.model.legacy.rsl.ws.creditcard.response.Body body = new com.tmb.common.model.legacy.rsl.ws.creditcard.response.Body();
        response.setBody(body);

        doReturn(response).when(loanSubmissionGetCreditcardInfoClient).searchCreditcardInfoByCaID(anyLong());
    }

    private void mockGetLoanSubmissionCustomerInfoSuccess() throws ServiceException, RemoteException {
        ResponseIndividual response = new ResponseIndividual();

        com.tmb.common.model.legacy.rsl.ws.individual.response.Header header = new com.tmb.common.model.legacy.rsl.ws.individual.response.Header();
        header.setResponseCode(RslResponseCodeEnum.SUCCESS.getCode());
        response.setHeader(header);

        com.tmb.common.model.legacy.rsl.ws.individual.response.Body body = new com.tmb.common.model.legacy.rsl.ws.individual.response.Body();
        response.setBody(body);

        doReturn(response).when(loanSubmissionGetCustomerInfoClient).searchCustomerInfoByCaID(anyLong());
    }

    private void mockGetLoanSubmissionCustomerInfoFail() throws ServiceException, RemoteException {
        ResponseIndividual response = new ResponseIndividual();

        com.tmb.common.model.legacy.rsl.ws.individual.response.Header header = new com.tmb.common.model.legacy.rsl.ws.individual.response.Header();
        header.setResponseCode(RslResponseCodeEnum.FAIL.getCode());
        header.setResponseDescriptionEN("rsl failed");
        response.setHeader(header);

        com.tmb.common.model.legacy.rsl.ws.individual.response.Body body = new com.tmb.common.model.legacy.rsl.ws.individual.response.Body();
        response.setBody(body);

        doReturn(response).when(loanSubmissionGetCustomerInfoClient).searchCustomerInfoByCaID(anyLong());
    }

    private void mockGetLoanSubmissionDropdownListSuccess() throws ServiceException, TMBCommonException, JsonProcessingException {
        ResponseDropdown response = new ResponseDropdown();

        com.tmb.common.model.legacy.rsl.ws.dropdown.response.Header header = new com.tmb.common.model.legacy.rsl.ws.dropdown.response.Header();
        header.setResponseCode(RslResponseCodeEnum.SUCCESS.getCode());
        response.setHeader(header);

        com.tmb.common.model.legacy.rsl.ws.dropdown.response.Body body = new com.tmb.common.model.legacy.rsl.ws.dropdown.response.Body();
        response.setBody(body);

        doReturn(response).when(loanSubmissionGetDropdownListClient).getDropDownListByCode(anyString());
    }

    private void mockGetLoanSubmissionDropdownListFail() throws ServiceException, TMBCommonException, JsonProcessingException {
        ResponseDropdown response = new ResponseDropdown();

        com.tmb.common.model.legacy.rsl.ws.dropdown.response.Header header = new com.tmb.common.model.legacy.rsl.ws.dropdown.response.Header();
        header.setResponseCode(RslResponseCodeEnum.FAIL.getCode());
        header.setResponseDescriptionEN("rsl failed");
        response.setHeader(header);

        com.tmb.common.model.legacy.rsl.ws.dropdown.response.Body body = new com.tmb.common.model.legacy.rsl.ws.dropdown.response.Body();
        response.setBody(body);

        doReturn(response).when(loanSubmissionGetDropdownListClient).getDropDownListByCode(anyString());
    }

    private void mockGetLoanSubmissionFacilityInfoSuccess() throws ServiceException, TMBCommonException, JsonProcessingException {
        ResponseFacility response = new ResponseFacility();

        com.tmb.common.model.legacy.rsl.ws.facility.response.Header header = new com.tmb.common.model.legacy.rsl.ws.facility.response.Header();
        header.setResponseCode(RslResponseCodeEnum.SUCCESS.getCode());
        response.setHeader(header);

        com.tmb.common.model.legacy.rsl.ws.facility.response.Body body = new com.tmb.common.model.legacy.rsl.ws.facility.response.Body();
        response.setBody(body);

        doReturn(response).when(loanSubmissionGetFacilityInfoClient).searchFacilityInfoByCaID(anyLong());
    }

    private void mockGetLoanSubmissionFacilityInfoFail() throws ServiceException, TMBCommonException, JsonProcessingException {
        ResponseFacility response = new ResponseFacility();

        com.tmb.common.model.legacy.rsl.ws.facility.response.Header header = new com.tmb.common.model.legacy.rsl.ws.facility.response.Header();
        header.setResponseCode(RslResponseCodeEnum.FAIL.getCode());
        header.setResponseDescriptionEN("rsl failed");
        response.setHeader(header);

        com.tmb.common.model.legacy.rsl.ws.facility.response.Body body = new com.tmb.common.model.legacy.rsl.ws.facility.response.Body();
        response.setBody(body);

        doReturn(response).when(loanSubmissionGetFacilityInfoClient).searchFacilityInfoByCaID(anyLong());
    }

    private void mockGetLoanSubmissionInstantLoanCalUWSuccess() throws ServiceException, TMBCommonException, JsonProcessingException {
        ResponseInstantLoanCalUW response = new ResponseInstantLoanCalUW();

        com.tmb.common.model.legacy.rsl.ws.instant.calculate.uw.response.Header header = new com.tmb.common.model.legacy.rsl.ws.instant.calculate.uw.response.Header();
        header.setResponseCode(RslResponseCodeEnum.SUCCESS.getCode());
        response.setHeader(header);

        com.tmb.common.model.legacy.rsl.ws.instant.calculate.uw.response.Body body = new com.tmb.common.model.legacy.rsl.ws.instant.calculate.uw.response.Body();
        response.setBody(body);

        doReturn(response).when(loanSubmissionInstantLoanCalUWClient).calculateUnderwriting(any(), any());
    }

    private void mockGetLoanSubmissionInstantLoanCalUWFail() throws ServiceException, TMBCommonException, JsonProcessingException {
        ResponseInstantLoanCalUW response = new ResponseInstantLoanCalUW();

        com.tmb.common.model.legacy.rsl.ws.instant.calculate.uw.response.Header header = new com.tmb.common.model.legacy.rsl.ws.instant.calculate.uw.response.Header();
        header.setResponseCode(RslResponseCodeEnum.FAIL.getCode());
        header.setResponseDescriptionEN("rsl failed");
        response.setHeader(header);

        com.tmb.common.model.legacy.rsl.ws.instant.calculate.uw.response.Body body = new com.tmb.common.model.legacy.rsl.ws.instant.calculate.uw.response.Body();
        response.setBody(body);

        doReturn(response).when(loanSubmissionInstantLoanCalUWClient).calculateUnderwriting(any(), any());
    }

    private void mockLoanInstantGetCustomerInfoSuccess() throws ServiceException, TMBCommonException, JsonProcessingException {
        ResponseInstantLoanGetCustInfo response = new ResponseInstantLoanGetCustInfo();

        com.tmb.common.model.legacy.rsl.ws.instant.eligible.customer.response.Header header = new com.tmb.common.model.legacy.rsl.ws.instant.eligible.customer.response.Header();
        header.setResponseCode(RslResponseCodeEnum.SUCCESS.getCode());
        response.setHeader(header);

        com.tmb.common.model.legacy.rsl.ws.instant.eligible.customer.response.Body body = new com.tmb.common.model.legacy.rsl.ws.instant.eligible.customer.response.Body();
        response.setBody(body);

        doReturn(response).when(loanSubmissionInstantLoanGetCustomerInfoClient).getInstantCustomerInfo(anyString());
    }

    private void mockLoanInstantGetCustomerInfoFail() throws ServiceException, TMBCommonException, JsonProcessingException {
        ResponseInstantLoanGetCustInfo response = new ResponseInstantLoanGetCustInfo();

        com.tmb.common.model.legacy.rsl.ws.instant.eligible.customer.response.Header header = new com.tmb.common.model.legacy.rsl.ws.instant.eligible.customer.response.Header();
        header.setResponseCode(RslResponseCodeEnum.FAIL.getCode());
        header.setResponseDescriptionEN("rsl failed");
        response.setHeader(header);

        com.tmb.common.model.legacy.rsl.ws.instant.eligible.customer.response.Body body = new com.tmb.common.model.legacy.rsl.ws.instant.eligible.customer.response.Body();
        response.setBody(body);

        doReturn(response).when(loanSubmissionInstantLoanGetCustomerInfoClient).getInstantCustomerInfo(anyString());
    }

    private void mockSubmitInstantLoanApplicationSuccess() throws ServiceException, TMBCommonException, JsonProcessingException {
        ResponseInstantLoanSubmit response = new ResponseInstantLoanSubmit();

        com.tmb.common.model.legacy.rsl.ws.instant.submit.response.Header header = new com.tmb.common.model.legacy.rsl.ws.instant.submit.response.Header();
        header.setResponseCode(RslResponseCodeEnum.SUCCESS.getCode());
        response.setHeader(header);

        com.tmb.common.model.legacy.rsl.ws.instant.submit.response.Body body = new com.tmb.common.model.legacy.rsl.ws.instant.submit.response.Body();
        response.setBody(body);

        doReturn(response).when(loanSubmissionInstantLoanSubmitApplicationClient).submitInstantLoanApplication(any(), any());
    }

    private void mockSubmitInstantLoanApplicationFail() throws ServiceException, TMBCommonException, JsonProcessingException {
        ResponseInstantLoanSubmit response = new ResponseInstantLoanSubmit();

        com.tmb.common.model.legacy.rsl.ws.instant.submit.response.Header header = new com.tmb.common.model.legacy.rsl.ws.instant.submit.response.Header();
        header.setResponseCode(RslResponseCodeEnum.FAIL.getCode());
        header.setResponseDescriptionEN("rsl failed");
        response.setHeader(header);

        com.tmb.common.model.legacy.rsl.ws.instant.submit.response.Body body = new com.tmb.common.model.legacy.rsl.ws.instant.submit.response.Body();
        response.setBody(body);

        doReturn(response).when(loanSubmissionInstantLoanSubmitApplicationClient).submitInstantLoanApplication(any(), any());
    }

    private void mockUpdateFacilityInfoSuccess() throws ServiceException, TMBCommonException, JsonProcessingException {
        com.tmb.common.model.legacy.rsl.ws.facility.update.response.ResponseFacility response = new com.tmb.common.model.legacy.rsl.ws.facility.update.response.ResponseFacility();

        com.tmb.common.model.legacy.rsl.ws.facility.update.response.Header header = new com.tmb.common.model.legacy.rsl.ws.facility.update.response.Header();
        header.setResponseCode(RslResponseCodeEnum.SUCCESS.getCode());
        response.setHeader(header);

        com.tmb.common.model.legacy.rsl.ws.facility.update.response.Body body = new com.tmb.common.model.legacy.rsl.ws.facility.update.response.Body();
        response.setBody(body);

        doReturn(response).when(loanSubmissionUpdateFacilityInfoClient).updateFacilityInfo(any());
    }

    private void mockUpdateFacilityInfoFail() throws ServiceException, TMBCommonException, JsonProcessingException {
        com.tmb.common.model.legacy.rsl.ws.facility.update.response.ResponseFacility response = new com.tmb.common.model.legacy.rsl.ws.facility.update.response.ResponseFacility();

        com.tmb.common.model.legacy.rsl.ws.facility.update.response.Header header = new com.tmb.common.model.legacy.rsl.ws.facility.update.response.Header();
        header.setResponseCode(RslResponseCodeEnum.FAIL.getCode());
        header.setResponseDescriptionEN("rsl failed");
        response.setHeader(header);

        com.tmb.common.model.legacy.rsl.ws.facility.update.response.Body body = new com.tmb.common.model.legacy.rsl.ws.facility.update.response.Body();
        response.setBody(body);

        doReturn(response).when(loanSubmissionUpdateFacilityInfoClient).updateFacilityInfo(any());
    }
}
