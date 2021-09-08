package com.tmb.oneapp.lendingservice.controller;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.doThrow;

import java.math.BigDecimal;
import java.rmi.RemoteException;
import java.util.Objects;

import javax.xml.rpc.ServiceException;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.tmb.common.exception.model.TMBCommonException;
import com.tmb.common.model.TmbOneServiceResponse;
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
import com.tmb.common.model.legacy.rsl.ws.instant.transfer.request.Body;
import com.tmb.oneapp.lendingservice.constant.ResponseCode;
import com.tmb.oneapp.lendingservice.model.rsl.LoanSubmissionGetApplicationInfoRequest;
import com.tmb.oneapp.lendingservice.model.rsl.LoanSubmissionGetChecklistInfoRequest;
import com.tmb.oneapp.lendingservice.model.rsl.LoanSubmissionGetCreditcardInfoRequest;
import com.tmb.oneapp.lendingservice.model.rsl.LoanSubmissionGetCustomerInfoRequest;
import com.tmb.oneapp.lendingservice.model.rsl.LoanSubmissionGetDropdownListRequest;
import com.tmb.oneapp.lendingservice.model.rsl.LoanSubmissionGetFacilityInfoRequest;
import com.tmb.oneapp.lendingservice.model.rsl.LoanSubmissionInstantLoanCalUWRequest;
import com.tmb.oneapp.lendingservice.model.rsl.LoanSubmissionInstantLoanSubmitApplicationRequest;
import com.tmb.oneapp.lendingservice.service.RslService;

@RunWith(JUnit4.class)
public class RslControllerTest {

    @InjectMocks
    RslController rslController;

    @Mock
    RslService rslService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
    }


    //Loan Submission Get Application Info
    @Test
    public void loanSubmissionGetApplicationInfo_Success() throws ServiceException, TMBCommonException, JsonProcessingException, RemoteException {

        ResponseApplication response = new ResponseApplication();
        doReturn(response).when(rslService).getLoanSubmissionApplicationInfo(any());

        String correlationId = "correlationId";
        String crmId = "001100000000000000000018593707";
        LoanSubmissionGetApplicationInfoRequest request = new LoanSubmissionGetApplicationInfoRequest();
        request.setCaId("2021071404188194");

        ResponseEntity<TmbOneServiceResponse<ResponseApplication>> responseEntity = rslController.loanSubmissionGetApplicationInfo(correlationId, crmId, request);

        Assertions.assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        Assertions.assertEquals(ResponseCode.SUCCESS.getCode(), Objects.requireNonNull(responseEntity.getBody()).getStatus().getCode());
        Assertions.assertEquals(ResponseCode.SUCCESS.getMessage(), responseEntity.getBody().getStatus().getMessage());
    }

    @Test
    public void loanSubmissionGetApplicationInfo_Fail() {
        String correlationId = "correlationId";
        String crmId = "001100000000000000000018593707";
        LoanSubmissionGetApplicationInfoRequest request = new LoanSubmissionGetApplicationInfoRequest();
        request.setCaId("2021071404188194");

        TMBCommonException exception = assertThrows(TMBCommonException.class, () -> {
            doThrow(new ServiceException("error")).when(rslService).getLoanSubmissionApplicationInfo(any());
            rslController.loanSubmissionGetApplicationInfo(correlationId, crmId, request);
        });

        Assertions.assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, exception.getStatus());
        Assertions.assertEquals(ResponseCode.FAILED.getCode(), exception.getErrorCode());
        Assertions.assertEquals(ResponseCode.FAILED.getMessage(), exception.getErrorMessage());

    }

    @Test
    public void loanSubmissionGetApplicationInfo_TMBCommonExceptionFail() {
        String correlationId = "correlationId";
        String crmId = "001100000000000000000018593707";
        LoanSubmissionGetApplicationInfoRequest request = new LoanSubmissionGetApplicationInfoRequest();
        request.setCaId("2021071404188194");

        TMBCommonException exception = assertThrows(TMBCommonException.class, () -> {
            doThrow(new TMBCommonException(ResponseCode.RSL_CONNECTION_ERROR.getCode(), ResponseCode.RSL_CONNECTION_ERROR.getMessage(), ResponseCode.RSL_CONNECTION_ERROR.getService(), HttpStatus.INTERNAL_SERVER_ERROR, null))
                    .when(rslService).getLoanSubmissionApplicationInfo(any());
            rslController.loanSubmissionGetApplicationInfo(correlationId, crmId, request);
        });

        Assertions.assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, exception.getStatus());
        Assertions.assertEquals(ResponseCode.RSL_CONNECTION_ERROR.getCode(), exception.getErrorCode());
        Assertions.assertEquals(ResponseCode.RSL_CONNECTION_ERROR.getMessage(), exception.getErrorMessage());

    }


    //Loan Submission Get Creditcard Info
    @Test
    public void loanSubmissionGetCreditcardInfo_Success() throws ServiceException, TMBCommonException, RemoteException, JsonProcessingException {

        ResponseCreditcard response = new ResponseCreditcard();
        doReturn(response).when(rslService).getLoanSubmissionCreditCardInfo(any());

        String correlationId = "correlationId";
        String crmId = "001100000000000000000018593707";
        LoanSubmissionGetCreditcardInfoRequest request = new LoanSubmissionGetCreditcardInfoRequest();
        request.setCaId("2021071404188194");

        ResponseEntity<TmbOneServiceResponse<ResponseCreditcard>> responseEntity = rslController.loanSubmissionGetCreditcardInfo(correlationId, crmId, request);

        Assertions.assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        Assertions.assertEquals(ResponseCode.SUCCESS.getCode(), Objects.requireNonNull(responseEntity.getBody()).getStatus().getCode());
        Assertions.assertEquals(ResponseCode.SUCCESS.getMessage(), responseEntity.getBody().getStatus().getMessage());
    }

    @Test
    public void loanSubmissionGetCreditcardInfo_Fail() {
        String correlationId = "correlationId";
        String crmId = "001100000000000000000018593707";
        LoanSubmissionGetCreditcardInfoRequest request = new LoanSubmissionGetCreditcardInfoRequest();
        request.setCaId("2021071404188194");

        TMBCommonException exception = assertThrows(TMBCommonException.class, () -> {
            doThrow(new ServiceException("error")).when(rslService).getLoanSubmissionCreditCardInfo(any());
            rslController.loanSubmissionGetCreditcardInfo(correlationId, crmId, request);
        });

        Assertions.assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, exception.getStatus());
        Assertions.assertEquals(ResponseCode.FAILED.getCode(), exception.getErrorCode());
        Assertions.assertEquals(ResponseCode.FAILED.getMessage(), exception.getErrorMessage());

    }

    @Test
    public void loanSubmissionGetCreditcardInfo_TMBCommonExceptionFail() {
        String correlationId = "correlationId";
        String crmId = "001100000000000000000018593707";
        LoanSubmissionGetCreditcardInfoRequest request = new LoanSubmissionGetCreditcardInfoRequest();
        request.setCaId("2021071404188194");

        TMBCommonException exception = assertThrows(TMBCommonException.class, () -> {
            doThrow(new TMBCommonException(ResponseCode.RSL_CONNECTION_ERROR.getCode(), ResponseCode.RSL_CONNECTION_ERROR.getMessage(), ResponseCode.RSL_CONNECTION_ERROR.getService(), HttpStatus.INTERNAL_SERVER_ERROR, null))
                    .when(rslService).getLoanSubmissionCreditCardInfo(any());
            rslController.loanSubmissionGetCreditcardInfo(correlationId, crmId, request);
        });

        Assertions.assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, exception.getStatus());
        Assertions.assertEquals(ResponseCode.RSL_CONNECTION_ERROR.getCode(), exception.getErrorCode());
        Assertions.assertEquals(ResponseCode.RSL_CONNECTION_ERROR.getMessage(), exception.getErrorMessage());
    }


    //Loan Submission Get Customer Info
    @Test
    public void loanSubmissionGetCustomerInfo_Success() throws ServiceException, TMBCommonException, JsonProcessingException, RemoteException {

        ResponseIndividual response = new ResponseIndividual();
        doReturn(response).when(rslService).getLoanSubmissionCustomerInfo(any());

        String correlationId = "correlationId";
        String crmId = "001100000000000000000018593707";
        LoanSubmissionGetCustomerInfoRequest request = new LoanSubmissionGetCustomerInfoRequest();
        request.setCaId("2021071404188194");

        ResponseEntity<TmbOneServiceResponse<ResponseIndividual>> responseEntity = rslController.loanSubmissionGetCustomerInfo(correlationId, crmId, request);

        Assertions.assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        Assertions.assertEquals(ResponseCode.SUCCESS.getCode(), Objects.requireNonNull(responseEntity.getBody()).getStatus().getCode());
        Assertions.assertEquals(ResponseCode.SUCCESS.getMessage(), responseEntity.getBody().getStatus().getMessage());
    }

    @Test
    public void loanSubmissionGetCustomerInfo_Fail() {
        String correlationId = "correlationId";
        String crmId = "001100000000000000000018593707";
        LoanSubmissionGetCustomerInfoRequest request = new LoanSubmissionGetCustomerInfoRequest();
        request.setCaId("2021071404188194");

        TMBCommonException exception = assertThrows(TMBCommonException.class, () -> {
            doThrow(new ServiceException("error")).when(rslService).getLoanSubmissionCustomerInfo(any());
            rslController.loanSubmissionGetCustomerInfo(correlationId, crmId, request);
        });

        Assertions.assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, exception.getStatus());
        Assertions.assertEquals(ResponseCode.FAILED.getCode(), exception.getErrorCode());
        Assertions.assertEquals(ResponseCode.FAILED.getMessage(), exception.getErrorMessage());

    }

    @Test
    public void loanSubmissionGetCustomerInfo_TMBCommonExceptionFail() {
        String correlationId = "correlationId";
        String crmId = "001100000000000000000018593707";
        LoanSubmissionGetCustomerInfoRequest request = new LoanSubmissionGetCustomerInfoRequest();
        request.setCaId("2021071404188194");

        TMBCommonException exception = assertThrows(TMBCommonException.class, () -> {
            doThrow(new TMBCommonException(ResponseCode.RSL_CONNECTION_ERROR.getCode(), ResponseCode.RSL_CONNECTION_ERROR.getMessage(), ResponseCode.RSL_CONNECTION_ERROR.getService(), HttpStatus.INTERNAL_SERVER_ERROR, null))
                    .when(rslService).getLoanSubmissionCustomerInfo(any());
            rslController.loanSubmissionGetCustomerInfo(correlationId, crmId, request);
        });

        Assertions.assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, exception.getStatus());
        Assertions.assertEquals(ResponseCode.RSL_CONNECTION_ERROR.getCode(), exception.getErrorCode());
        Assertions.assertEquals(ResponseCode.RSL_CONNECTION_ERROR.getMessage(), exception.getErrorMessage());
    }


    //Loan Submission Get Dropdown List
    @Test
    public void loanSubmissionGetDropdownList_Success() throws ServiceException, TMBCommonException, JsonProcessingException {

        ResponseDropdown response = new ResponseDropdown();
        doReturn(response).when(rslService).getLoanSubmissionDropdownList(any());

        String correlationId = "correlationId";
        String crmId = "001100000000000000000018593707";
        LoanSubmissionGetDropdownListRequest request = new LoanSubmissionGetDropdownListRequest();
        request.setCategoryCode("categoryCode");

        ResponseEntity<TmbOneServiceResponse<ResponseDropdown>> responseEntity = rslController.loanSubmissionGetDropdownList(correlationId, crmId, request);

        Assertions.assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        Assertions.assertEquals(ResponseCode.SUCCESS.getCode(), Objects.requireNonNull(responseEntity.getBody()).getStatus().getCode());
        Assertions.assertEquals(ResponseCode.SUCCESS.getMessage(), responseEntity.getBody().getStatus().getMessage());
    }

    @Test
    public void loanSubmissionGetDropdownList_Fail() {
        String correlationId = "correlationId";
        String crmId = "001100000000000000000018593707";
        LoanSubmissionGetDropdownListRequest request = new LoanSubmissionGetDropdownListRequest();
        request.setCategoryCode("categoryCode");

        TMBCommonException exception = assertThrows(TMBCommonException.class, () -> {
            doThrow(new ServiceException("error")).when(rslService).getLoanSubmissionDropdownList(any());
            rslController.loanSubmissionGetDropdownList(correlationId, crmId, request);
        });

        Assertions.assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, exception.getStatus());
        Assertions.assertEquals(ResponseCode.FAILED.getCode(), exception.getErrorCode());
        Assertions.assertEquals(ResponseCode.FAILED.getMessage(), exception.getErrorMessage());

    }

    @Test
    public void loanSubmissionGetDropdownList_TMBCommonExceptionFail() {
        String correlationId = "correlationId";
        String crmId = "001100000000000000000018593707";
        LoanSubmissionGetDropdownListRequest request = new LoanSubmissionGetDropdownListRequest();
        request.setCategoryCode("categoryCode");

        TMBCommonException exception = assertThrows(TMBCommonException.class, () -> {
            doThrow(new TMBCommonException(ResponseCode.RSL_CONNECTION_ERROR.getCode(), ResponseCode.RSL_CONNECTION_ERROR.getMessage(), ResponseCode.RSL_CONNECTION_ERROR.getService(), HttpStatus.INTERNAL_SERVER_ERROR, null))
                    .when(rslService).getLoanSubmissionDropdownList(any());
            rslController.loanSubmissionGetDropdownList(correlationId, crmId, request);
        });

        Assertions.assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, exception.getStatus());
        Assertions.assertEquals(ResponseCode.RSL_CONNECTION_ERROR.getCode(), exception.getErrorCode());
        Assertions.assertEquals(ResponseCode.RSL_CONNECTION_ERROR.getMessage(), exception.getErrorMessage());

    }

    //Loan Submission Get Facility Info
    @Test
    public void loanSubmissionGetFacilityInfo_Success() throws ServiceException, TMBCommonException, JsonProcessingException {

        ResponseFacility response = new ResponseFacility();
        doReturn(response).when(rslService).getLoanSubmissionFacilityInfo(any());

        String correlationId = "correlationId";
        String crmId = "001100000000000000000018593707";
        LoanSubmissionGetFacilityInfoRequest request = new LoanSubmissionGetFacilityInfoRequest();
        request.setCaId("2021071404188194");

        ResponseEntity<TmbOneServiceResponse<ResponseFacility>> responseEntity = rslController.loanSubmissionGetFacilityInfo(correlationId, crmId, request);

        Assertions.assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        Assertions.assertEquals(ResponseCode.SUCCESS.getCode(), Objects.requireNonNull(responseEntity.getBody()).getStatus().getCode());
        Assertions.assertEquals(ResponseCode.SUCCESS.getMessage(), responseEntity.getBody().getStatus().getMessage());
    }

    @Test
    public void loanSubmissionGetFacilityInfo_Fail() {
        String correlationId = "correlationId";
        String crmId = "001100000000000000000018593707";
        LoanSubmissionGetFacilityInfoRequest request = new LoanSubmissionGetFacilityInfoRequest();
        request.setCaId("2021071404188194");

        TMBCommonException exception = assertThrows(TMBCommonException.class, () -> {
            doThrow(new ServiceException("error")).when(rslService).getLoanSubmissionFacilityInfo(any());
            rslController.loanSubmissionGetFacilityInfo(correlationId, crmId, request);
        });

        Assertions.assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, exception.getStatus());
        Assertions.assertEquals(ResponseCode.FAILED.getCode(), exception.getErrorCode());
        Assertions.assertEquals(ResponseCode.FAILED.getMessage(), exception.getErrorMessage());

    }

    @Test
    public void loanSubmissionGetFacilityInfo_TMBCommonExceptionFail() {
        String correlationId = "correlationId";
        String crmId = "001100000000000000000018593707";
        LoanSubmissionGetFacilityInfoRequest request = new LoanSubmissionGetFacilityInfoRequest();
        request.setCaId("2021071404188194");

        TMBCommonException exception = assertThrows(TMBCommonException.class, () -> {
            doThrow(new TMBCommonException(ResponseCode.RSL_CONNECTION_ERROR.getCode(), ResponseCode.RSL_CONNECTION_ERROR.getMessage(), ResponseCode.RSL_CONNECTION_ERROR.getService(), HttpStatus.INTERNAL_SERVER_ERROR, null))
                    .when(rslService).getLoanSubmissionFacilityInfo(any());
            rslController.loanSubmissionGetFacilityInfo(correlationId, crmId, request);
        });

        Assertions.assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, exception.getStatus());
        Assertions.assertEquals(ResponseCode.RSL_CONNECTION_ERROR.getCode(), exception.getErrorCode());
        Assertions.assertEquals(ResponseCode.RSL_CONNECTION_ERROR.getMessage(), exception.getErrorMessage());

    }

    //Loan Submission Instant Loan CalUW
    @Test
    public void loanSubmissionInstantLoanCalUW_Success() throws ServiceException, TMBCommonException, JsonProcessingException {

        ResponseInstantLoanCalUW response = new ResponseInstantLoanCalUW();
        doReturn(response).when(rslService).getLoanSubmissionInstantLoanCalUW(any());

        String correlationId = "correlationId";
        String crmId = "001100000000000000000018593707";
        LoanSubmissionInstantLoanCalUWRequest request = new LoanSubmissionInstantLoanCalUWRequest();
        request.setCaId("2021071404188194");

        ResponseEntity<TmbOneServiceResponse<ResponseInstantLoanCalUW>> responseEntity = rslController.loanSubmissionInstantLoanCalUW(correlationId, crmId, request);

        Assertions.assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        Assertions.assertEquals(ResponseCode.SUCCESS.getCode(), Objects.requireNonNull(responseEntity.getBody()).getStatus().getCode());
        Assertions.assertEquals(ResponseCode.SUCCESS.getMessage(), responseEntity.getBody().getStatus().getMessage());
    }

    @Test
    public void loanSubmissionInstantLoanCalUW_Fail() {
        String correlationId = "correlationId";
        String crmId = "001100000000000000000018593707";
        LoanSubmissionInstantLoanCalUWRequest request = new LoanSubmissionInstantLoanCalUWRequest();
        request.setCaId("2021071404188194");

        TMBCommonException exception = assertThrows(TMBCommonException.class, () -> {
            doThrow(new ServiceException("error")).when(rslService).getLoanSubmissionInstantLoanCalUW(any());
            rslController.loanSubmissionInstantLoanCalUW(correlationId, crmId, request);
        });

        Assertions.assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, exception.getStatus());
        Assertions.assertEquals(ResponseCode.FAILED.getCode(), exception.getErrorCode());
        Assertions.assertEquals(ResponseCode.FAILED.getMessage(), exception.getErrorMessage());

    }

    @Test
    public void loanSubmissionInstantLoanCalUW_TMBCommonExceptionFail() {
        String correlationId = "correlationId";
        String crmId = "001100000000000000000018593707";
        LoanSubmissionInstantLoanCalUWRequest request = new LoanSubmissionInstantLoanCalUWRequest();
        request.setCaId("2021071404188194");

        TMBCommonException exception = assertThrows(TMBCommonException.class, () -> {
            doThrow(new TMBCommonException(ResponseCode.RSL_CONNECTION_ERROR.getCode(), ResponseCode.RSL_CONNECTION_ERROR.getMessage(), ResponseCode.RSL_CONNECTION_ERROR.getService(), HttpStatus.INTERNAL_SERVER_ERROR, null))
                    .when(rslService).getLoanSubmissionInstantLoanCalUW(any());
            rslController.loanSubmissionInstantLoanCalUW(correlationId, crmId, request);
        });

        Assertions.assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, exception.getStatus());
        Assertions.assertEquals(ResponseCode.RSL_CONNECTION_ERROR.getCode(), exception.getErrorCode());
        Assertions.assertEquals(ResponseCode.RSL_CONNECTION_ERROR.getMessage(), exception.getErrorMessage());

    }


    //Loan Submission Instant Loan Get Customer Info
    @Test
    public void loanSubmissionInstantLoanGetCustomerInfo_Success() throws ServiceException, TMBCommonException, JsonProcessingException {

        ResponseInstantLoanGetCustInfo response = new ResponseInstantLoanGetCustInfo();
        doReturn(response).when(rslService).getSubmissionInstantLoanCustomerInfo(anyString());

        String correlationId = "correlationId";
        String crmId = "001100000000000000000018593707";
        ResponseEntity<TmbOneServiceResponse<ResponseInstantLoanGetCustInfo>> responseEntity = rslController.loanSubmissionInstantLoanGetCustomerInfo(correlationId, crmId);

        Assertions.assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        Assertions.assertEquals(ResponseCode.SUCCESS.getCode(), Objects.requireNonNull(responseEntity.getBody()).getStatus().getCode());
        Assertions.assertEquals(ResponseCode.SUCCESS.getMessage(), responseEntity.getBody().getStatus().getMessage());
    }

    @Test
    public void loanSubmissionInstantLoanGetCustomerInfo_Fail() {
        String correlationId = "correlationId";
        String crmId = "001100000000000000000018593707";

        TMBCommonException exception = assertThrows(TMBCommonException.class, () -> {
            doThrow(new ServiceException("error")).when(rslService).getSubmissionInstantLoanCustomerInfo(anyString());
            rslController.loanSubmissionInstantLoanGetCustomerInfo(correlationId, crmId);
        });

        Assertions.assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, exception.getStatus());
        Assertions.assertEquals(ResponseCode.FAILED.getCode(), exception.getErrorCode());
        Assertions.assertEquals(ResponseCode.FAILED.getMessage(), exception.getErrorMessage());

    }

    @Test
    public void loanSubmissionInstantLoanGetCustomerInfo_TMBCommonExceptionFail() {
        String correlationId = "correlationId";
        String crmId = "001100000000000000000018593707";

        TMBCommonException exception = assertThrows(TMBCommonException.class, () -> {
            doThrow(new TMBCommonException(ResponseCode.RSL_CONNECTION_ERROR.getCode(), ResponseCode.RSL_CONNECTION_ERROR.getMessage(), ResponseCode.RSL_CONNECTION_ERROR.getService(), HttpStatus.INTERNAL_SERVER_ERROR, null))
                    .when(rslService).getSubmissionInstantLoanCustomerInfo(anyString());
            rslController.loanSubmissionInstantLoanGetCustomerInfo(correlationId, crmId);
        });

        Assertions.assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, exception.getStatus());
        Assertions.assertEquals(ResponseCode.RSL_CONNECTION_ERROR.getCode(), exception.getErrorCode());
        Assertions.assertEquals(ResponseCode.RSL_CONNECTION_ERROR.getMessage(), exception.getErrorMessage());

    }

    //Loan Submission Instant Loan Submit Application
    @Test
    public void loanSubmissionInstantLoanSubmitApplication_Success() throws ServiceException, TMBCommonException, JsonProcessingException {

        ResponseInstantLoanSubmit response = new ResponseInstantLoanSubmit();
        doReturn(response).when(rslService).submitInstantLoanApplication(any());

        String correlationId = "correlationId";
        String crmId = "001100000000000000000018593707";
        LoanSubmissionInstantLoanSubmitApplicationRequest request = new LoanSubmissionInstantLoanSubmitApplicationRequest();
        request.setSubmittedFlag("Y");
        request.setCaId("1");

        ResponseEntity<TmbOneServiceResponse<ResponseInstantLoanSubmit>> responseEntity = rslController.loanSubmissionInstantLoanSubmitApplication(correlationId, crmId, request);

        Assertions.assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        Assertions.assertEquals(ResponseCode.SUCCESS.getCode(), Objects.requireNonNull(responseEntity.getBody()).getStatus().getCode());
        Assertions.assertEquals(ResponseCode.SUCCESS.getMessage(), responseEntity.getBody().getStatus().getMessage());
    }

    @Test
    public void loanSubmissionInstantLoanSubmitApplication_Fail() {
        String correlationId = "correlationId";
        String crmId = "001100000000000000000018593707";
        LoanSubmissionInstantLoanSubmitApplicationRequest request = new LoanSubmissionInstantLoanSubmitApplicationRequest();
        request.setSubmittedFlag("Y");
        request.setCaId("1");

        TMBCommonException exception = assertThrows(TMBCommonException.class, () -> {
            doThrow(new ServiceException("error")).when(rslService).submitInstantLoanApplication(any());
            rslController.loanSubmissionInstantLoanSubmitApplication(correlationId, crmId, request);
        });

        Assertions.assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, exception.getStatus());
        Assertions.assertEquals(ResponseCode.FAILED.getCode(), exception.getErrorCode());
        Assertions.assertEquals(ResponseCode.FAILED.getMessage(), exception.getErrorMessage());

    }

    @Test
    public void loanSubmissionInstantLoanSubmitApplication_TMBCommonExceptionFail() {
        String correlationId = "correlationId";
        String crmId = "001100000000000000000018593707";
        LoanSubmissionInstantLoanSubmitApplicationRequest request = new LoanSubmissionInstantLoanSubmitApplicationRequest();
        request.setSubmittedFlag("Y");
        request.setCaId("1");

        TMBCommonException exception = assertThrows(TMBCommonException.class, () -> {
            doThrow(new TMBCommonException(ResponseCode.RSL_CONNECTION_ERROR.getCode(), ResponseCode.RSL_CONNECTION_ERROR.getMessage(), ResponseCode.RSL_CONNECTION_ERROR.getService(), HttpStatus.INTERNAL_SERVER_ERROR, null))
                    .when(rslService).submitInstantLoanApplication(any());
            rslController.loanSubmissionInstantLoanSubmitApplication(correlationId, crmId, request);
        });

        Assertions.assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, exception.getStatus());
        Assertions.assertEquals(ResponseCode.RSL_CONNECTION_ERROR.getCode(), exception.getErrorCode());
        Assertions.assertEquals(ResponseCode.RSL_CONNECTION_ERROR.getMessage(), exception.getErrorMessage());

    }

    //Loan Submission Update Facility
    @Test
    public void loanSubmissionUpdateFacility_Success() throws ServiceException, TMBCommonException, JsonProcessingException {

        com.tmb.common.model.legacy.rsl.ws.facility.update.response.ResponseFacility response = new com.tmb.common.model.legacy.rsl.ws.facility.update.response.ResponseFacility();
        doReturn(response).when(rslService).updateFacilityInfo(any());

        String correlationId = "correlationId";
        String crmId = "001100000000000000000018593707";
        Facility request = new Facility();

        ResponseEntity<TmbOneServiceResponse<com.tmb.common.model.legacy.rsl.ws.facility.update.response.ResponseFacility>> responseEntity = rslController.loanSubmissionUpdateFacility(correlationId, crmId, request);

        Assertions.assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        Assertions.assertEquals(ResponseCode.SUCCESS.getCode(), Objects.requireNonNull(responseEntity.getBody()).getStatus().getCode());
        Assertions.assertEquals(ResponseCode.SUCCESS.getMessage(), responseEntity.getBody().getStatus().getMessage());
    }

    @Test
    public void loanSubmissionUpdateFacility_Fail() {
        String correlationId = "correlationId";
        String crmId = "001100000000000000000018593707";
        Facility request = new Facility();

        TMBCommonException exception = assertThrows(TMBCommonException.class, () -> {
            doThrow(new ServiceException("error")).when(rslService).updateFacilityInfo(any());
            rslController.loanSubmissionUpdateFacility(correlationId, crmId, request);
        });

        Assertions.assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, exception.getStatus());
        Assertions.assertEquals(ResponseCode.FAILED.getCode(), exception.getErrorCode());
        Assertions.assertEquals(ResponseCode.FAILED.getMessage(), exception.getErrorMessage());

    }

    @Test
    public void loanSubmissionUpdateFacility_TMBCommonExceptionFail() {
        String correlationId = "correlationId";
        String crmId = "001100000000000000000018593707";
        Facility request = new Facility();

        TMBCommonException exception = assertThrows(TMBCommonException.class, () -> {
            doThrow(new TMBCommonException(ResponseCode.RSL_CONNECTION_ERROR.getCode(), ResponseCode.RSL_CONNECTION_ERROR.getMessage(), ResponseCode.RSL_CONNECTION_ERROR.getService(), HttpStatus.INTERNAL_SERVER_ERROR, null))
                    .when(rslService).updateFacilityInfo(any());

            rslController.loanSubmissionUpdateFacility(correlationId, crmId, request);
        });

        Assertions.assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, exception.getStatus());
        Assertions.assertEquals(ResponseCode.RSL_CONNECTION_ERROR.getCode(), exception.getErrorCode());
        Assertions.assertEquals(ResponseCode.RSL_CONNECTION_ERROR.getMessage(), exception.getErrorMessage());
    }

    //Loan Submission Update Customer
    @Test
    public void loanSubmissionUpdateCustomer_Success() throws ServiceException, TMBCommonException, JsonProcessingException, RemoteException {

        com.tmb.common.model.legacy.rsl.ws.individual.update.response.ResponseIndividual response = new com.tmb.common.model.legacy.rsl.ws.individual.update.response.ResponseIndividual();
        doReturn(response).when(rslService).updateCustomerInfo(any());

        String correlationId = "correlationId";
        String crmId = "001100000000000000000018593707";
        Individual request = new Individual();

        ResponseEntity<TmbOneServiceResponse<com.tmb.common.model.legacy.rsl.ws.individual.update.response.ResponseIndividual>> responseEntity = rslController.loanSubmissionUpdateCustomerInfo(correlationId, crmId, request);

        Assertions.assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        Assertions.assertEquals(ResponseCode.SUCCESS.getCode(), Objects.requireNonNull(responseEntity.getBody()).getStatus().getCode());
        Assertions.assertEquals(ResponseCode.SUCCESS.getMessage(), responseEntity.getBody().getStatus().getMessage());
    }

    @Test
    public void loanSubmissionUpdateCustomer_Fail() {
        String correlationId = "correlationId";
        String crmId = "001100000000000000000018593707";
        Individual request = new Individual();

        TMBCommonException exception = assertThrows(TMBCommonException.class, () -> {
            doThrow(new ServiceException("error")).when(rslService).updateCustomerInfo(any());
            rslController.loanSubmissionUpdateCustomerInfo(correlationId, crmId, request);
        });

        Assertions.assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, exception.getStatus());
        Assertions.assertEquals(ResponseCode.FAILED.getCode(), exception.getErrorCode());
        Assertions.assertEquals(ResponseCode.FAILED.getMessage(), exception.getErrorMessage());

    }

    @Test
    public void loanSubmissionUpdateCustomer_TMBCommonExceptionFail() {
        String correlationId = "correlationId";
        String crmId = "001100000000000000000018593707";
        Individual request = new Individual();

        TMBCommonException exception = assertThrows(TMBCommonException.class, () -> {
            doThrow(new TMBCommonException(ResponseCode.RSL_CONNECTION_ERROR.getCode(), ResponseCode.RSL_CONNECTION_ERROR.getMessage(), ResponseCode.RSL_CONNECTION_ERROR.getService(), HttpStatus.INTERNAL_SERVER_ERROR, null))
                    .when(rslService).updateCustomerInfo(any());

            rslController.loanSubmissionUpdateCustomerInfo(correlationId, crmId, request);
        });

        Assertions.assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, exception.getStatus());
        Assertions.assertEquals(ResponseCode.RSL_CONNECTION_ERROR.getCode(), exception.getErrorCode());
        Assertions.assertEquals(ResponseCode.RSL_CONNECTION_ERROR.getMessage(), exception.getErrorMessage());
    }

    //Loan Submission Get Checklist Info
    @Test
    public void loanSubmissionGetChecklistInfo_Success() throws ServiceException, TMBCommonException, RemoteException, JsonProcessingException {

        ResponseChecklist response = new ResponseChecklist();
        doReturn(response).when(rslService).getDocumentList(any());

        String correlationId = "correlationId";
        String crmId = "001100000000000000000018593707";
        LoanSubmissionGetChecklistInfoRequest request = new LoanSubmissionGetChecklistInfoRequest();
        request.setCaId(2021071404188194L);

        ResponseEntity<TmbOneServiceResponse<ResponseChecklist>> responseEntity = rslController.getDocumentList(correlationId, crmId, request);

        Assertions.assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        Assertions.assertEquals(ResponseCode.SUCCESS.getCode(), Objects.requireNonNull(responseEntity.getBody()).getStatus().getCode());
        Assertions.assertEquals(ResponseCode.SUCCESS.getMessage(), responseEntity.getBody().getStatus().getMessage());
    }

    @Test
    public void loanSubmissionGetChecklistInfo_Fail() {
        String correlationId = "correlationId";
        String crmId = "001100000000000000000018593707";
        LoanSubmissionGetChecklistInfoRequest request = new LoanSubmissionGetChecklistInfoRequest();
        request.setCaId(2021071404188194L);

        TMBCommonException exception = assertThrows(TMBCommonException.class, () -> {
            doThrow(new ServiceException("error")).when(rslService).getDocumentList(any());
            rslController.getDocumentList(correlationId, crmId, request);
        });

        Assertions.assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, exception.getStatus());
        Assertions.assertEquals(ResponseCode.FAILED.getCode(), exception.getErrorCode());
        Assertions.assertEquals(ResponseCode.FAILED.getMessage(), exception.getErrorMessage());

    }
    
  //Loan Submission Instant Loan Transfer Application  
    @Test
    public void transferApplication_Success() throws ServiceException, TMBCommonException, RemoteException, JsonProcessingException {

		com.tmb.common.model.legacy.rsl.ws.instant.transfer.response.ResponseTransfer response = new com.tmb.common.model.legacy.rsl.ws.instant.transfer.response.ResponseTransfer();
		doReturn(response).when(rslService).transferApplication(any());

        String correlationId = "correlationId";
        String crmId = "001100000000000000000018593707";
        com.tmb.common.model.legacy.rsl.ws.instant.transfer.request.Body request = new Body();
        request.setCaId(new BigDecimal("2021080504188297"));

        ResponseEntity<TmbOneServiceResponse<com.tmb.common.model.legacy.rsl.ws.instant.transfer.response.ResponseTransfer>> responseEntity = rslController.transferApplication(correlationId, crmId, request);

        Assertions.assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        Assertions.assertEquals(ResponseCode.SUCCESS.getCode(), Objects.requireNonNull(responseEntity.getBody()).getStatus().getCode());
        Assertions.assertEquals(ResponseCode.SUCCESS.getMessage(), responseEntity.getBody().getStatus().getMessage());
    }

    @Test
    public void transferApplication_Fail() {
        String correlationId = "correlationId";
        String crmId = "001100000000000000000018593707";
        com.tmb.common.model.legacy.rsl.ws.instant.transfer.request.Body request = new Body();
        request.setCaId(new BigDecimal("2021080504188297"));

        TMBCommonException exception = assertThrows(TMBCommonException.class, () -> {
            doThrow(new ServiceException("error")).when(rslService).transferApplication(any());
            rslController.transferApplication(correlationId, crmId, request);
        });

        Assertions.assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, exception.getStatus());
        Assertions.assertEquals(ResponseCode.FAILED.getCode(), exception.getErrorCode());
        Assertions.assertEquals(ResponseCode.FAILED.getMessage(), exception.getErrorMessage());

    }

}
