package com.tmb.oneapp.lendingservice.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.tmb.common.exception.model.TMBCommonException;
import com.tmb.common.model.TmbOneServiceResponse;
import com.tmb.oneapp.lendingservice.constant.ResponseCode;
import com.tmb.oneapp.lendingservice.model.dropdown.DropdownsLoanSubmissionWorkingDetail;
import com.tmb.oneapp.lendingservice.service.DropdownService;
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

import javax.xml.rpc.ServiceException;
import java.util.Objects;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.doThrow;

@RunWith(JUnit4.class)
class DropdownControllerTest {

    @InjectMocks
    DropdownController dropdownController;

    @Mock
    DropdownService dropdownService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void getDropdownLoanSubmissionWorkingDetailSuccess() throws ServiceException, TMBCommonException, JsonProcessingException {
        DropdownsLoanSubmissionWorkingDetail dropdownWorkingDetailResp = new DropdownsLoanSubmissionWorkingDetail();
        doReturn(dropdownWorkingDetailResp).when(dropdownService).getDropdownsLoanSubmissionWorkingDetail(anyString(), anyString());

        ResponseEntity<TmbOneServiceResponse<DropdownsLoanSubmissionWorkingDetail>> responseEntity = dropdownController.getDropdownLoanSubmissionWorkingDetail("correlationId", "crmId");

        Assertions.assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        Assertions.assertEquals(ResponseCode.SUCCESS.getCode(), Objects.requireNonNull(responseEntity.getBody()).getStatus().getCode());
        Assertions.assertEquals(ResponseCode.SUCCESS.getMessage(), responseEntity.getBody().getStatus().getMessage());
    }

    @Test
    public void getDropdownLoanSubmissionWorkingDetailThrowTMBCommonException() {
        TMBCommonException exception = assertThrows(TMBCommonException.class, () -> {
            doThrow(new TMBCommonException(ResponseCode.RSL_CONNECTION_ERROR.getCode(), ResponseCode.RSL_CONNECTION_ERROR.getMessage(), ResponseCode.RSL_CONNECTION_ERROR.getService(), HttpStatus.INTERNAL_SERVER_ERROR, null))
                    .when(dropdownService).getDropdownsLoanSubmissionWorkingDetail(anyString(), anyString());

            dropdownController.getDropdownLoanSubmissionWorkingDetail("correlationId", "crmId");
        });

        Assertions.assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, exception.getStatus());
        Assertions.assertEquals(ResponseCode.RSL_CONNECTION_ERROR.getCode(), exception.getErrorCode());
        Assertions.assertEquals(ResponseCode.RSL_CONNECTION_ERROR.getMessage(), exception.getErrorMessage());
    }

    @Test
    public void getDropdownLoanSubmissionWorkingDetailThrowException() {
        TMBCommonException exception = assertThrows(TMBCommonException.class, () -> {
            doThrow(new IllegalArgumentException())
                    .when(dropdownService).getDropdownsLoanSubmissionWorkingDetail(anyString(), anyString());

            dropdownController.getDropdownLoanSubmissionWorkingDetail("correlationId", "crmId");
        });

        Assertions.assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, exception.getStatus());
        Assertions.assertEquals(ResponseCode.FAILED.getCode(), exception.getErrorCode());
        Assertions.assertEquals(ResponseCode.FAILED.getMessage(), exception.getErrorMessage());
    }

}
