package com.tmb.oneapp.lendingservice.controller;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;

import java.math.BigDecimal;
import java.rmi.RemoteException;
import java.util.Objects;

import javax.xml.rpc.ServiceException;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.tmb.common.exception.model.TMBCommonException;
import com.tmb.common.model.TmbOneServiceResponse;
import com.tmb.common.model.legacy.rsl.ws.application.save.response.ResponseApplication;
import com.tmb.oneapp.lendingservice.constant.ResponseCode;
import com.tmb.oneapp.lendingservice.model.loanonline.CustomerInformationResponse;
import com.tmb.oneapp.lendingservice.model.loanonline.IncomeInfo;
import com.tmb.oneapp.lendingservice.model.loanonline.LoanSubmissionCreateApplicationReq;
import com.tmb.oneapp.lendingservice.model.loanonline.UpdateNCBConsentFlagRequest;
import com.tmb.oneapp.lendingservice.model.loanonline.WorkingDetail;
import com.tmb.oneapp.lendingservice.service.LoanOnlineSubmissionCheckWaiveDocService;
import com.tmb.oneapp.lendingservice.service.LoanSubmissionCreateApplicationService;
import com.tmb.oneapp.lendingservice.service.LoanSubmissionGetCustInfoAppInfoService;
import com.tmb.oneapp.lendingservice.service.LoanSubmissionGetWorkingDetailService;
import com.tmb.oneapp.lendingservice.service.LoanSubmissionUpdateNCBConsentFlagAndStoreFileService;

class LoanOnlineSubmissionControllerTest {

    @InjectMocks
    LoanOnlineSubmissionController loanOnlineSubmissionController;

    @Mock
    LoanOnlineSubmissionCheckWaiveDocService loanOnlineSubmissionCheckWaiveDocService;

    @Mock
    LoanSubmissionCreateApplicationService loanSubmissionCreateApplicationService;

    @Mock
    LoanSubmissionGetWorkingDetailService loanSubmissionGetWorkingDetailService;
    
    @Mock
    LoanSubmissionGetCustInfoAppInfoService loanSubmissionGetCustInfoAppInfoService;
    
    @Mock
    LoanSubmissionUpdateNCBConsentFlagAndStoreFileService updateNCBConsentFlagAndStoreFileService;;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testGetIncomeInfoByRmIdSuccess() throws ServiceException, RemoteException, TMBCommonException, JsonProcessingException {
        IncomeInfo res = new IncomeInfo();
        res.setIncomeAmount(BigDecimal.valueOf(100));
        when(loanOnlineSubmissionCheckWaiveDocService.getIncomeInfoByRmId(any())).thenReturn(res);
        ResponseEntity<TmbOneServiceResponse<IncomeInfo>> responseEntity = loanOnlineSubmissionController.getIncomeInfo("rmid");
        Assertions.assertTrue(responseEntity.getStatusCode().is2xxSuccessful());
    }

    @Test
    public void testGetIncomeInfoByRmIdFail() throws ServiceException, RemoteException, TMBCommonException, JsonProcessingException {
        IncomeInfo res = new IncomeInfo();
        res.setIncomeAmount(BigDecimal.valueOf(100));
        when(loanOnlineSubmissionCheckWaiveDocService.getIncomeInfoByRmId(any())).thenThrow(new IllegalArgumentException());
        ResponseEntity<TmbOneServiceResponse<IncomeInfo>> responseEntity = loanOnlineSubmissionController.getIncomeInfo("rmid");
        Assertions.assertTrue(responseEntity.getStatusCode().isError());
    }

    @Test
    public void testCreateApplicationSuccess() throws Exception {
        ResponseApplication res = new ResponseApplication();
        when(loanSubmissionCreateApplicationService.createApplication(any(),any())).thenReturn(res);
        ResponseEntity<TmbOneServiceResponse<ResponseApplication>> responseEntity = loanOnlineSubmissionController.createApplication("rmid", new LoanSubmissionCreateApplicationReq());
        Assertions.assertTrue(responseEntity.getStatusCode().is2xxSuccessful());
    }

    @Test
    public void testCreateApplicationFail() throws Exception {
        when(loanSubmissionCreateApplicationService.createApplication(any(),any())).thenThrow(new IllegalArgumentException());
        ResponseEntity<TmbOneServiceResponse<ResponseApplication>> responseEntity = loanOnlineSubmissionController.createApplication("rmid", new LoanSubmissionCreateApplicationReq());
        Assertions.assertTrue(responseEntity.getStatusCode().isError());
    }

    @Test
    public void loanSubmissionGetWorkingDetailSuccess() throws Exception {
        WorkingDetail res = new WorkingDetail();
        when(loanSubmissionGetWorkingDetailService.getWorkingDetail(any(),any())).thenReturn(res);
        ResponseEntity<TmbOneServiceResponse<WorkingDetail>> responseEntity = loanOnlineSubmissionController.loanSubmissionGetWorkingDetail("correlationId", "crmId", 1L);

        Assertions.assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        Assertions.assertEquals(ResponseCode.SUCCESS.getCode(), Objects.requireNonNull(responseEntity.getBody()).getStatus().getCode());
        Assertions.assertEquals(ResponseCode.SUCCESS.getMessage(), responseEntity.getBody().getStatus().getMessage());
    }

    @Test
    public void loanSubmissionGetWorkingDetailThrowTMBCommonException() {
        TMBCommonException exception = assertThrows(TMBCommonException.class, () -> {
            doThrow(new TMBCommonException(ResponseCode.RSL_CONNECTION_ERROR.getCode(), ResponseCode.RSL_CONNECTION_ERROR.getMessage(), ResponseCode.RSL_CONNECTION_ERROR.getService(), HttpStatus.INTERNAL_SERVER_ERROR, null))
                    .when(loanSubmissionGetWorkingDetailService).getWorkingDetail(any(),any());

            loanOnlineSubmissionController.loanSubmissionGetWorkingDetail("correlationId", "crmId", 1L);
        });

        Assertions.assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, exception.getStatus());
        Assertions.assertEquals(ResponseCode.RSL_CONNECTION_ERROR.getCode(), exception.getErrorCode());
        Assertions.assertEquals(ResponseCode.RSL_CONNECTION_ERROR.getMessage(), exception.getErrorMessage());
    }

    @Test
    public void loanSubmissionGetWorkingDetailThrowException() {
        TMBCommonException exception = assertThrows(TMBCommonException.class, () -> {
            doThrow(new IllegalArgumentException())
                    .when(loanSubmissionGetWorkingDetailService).getWorkingDetail(any(),any());

            loanOnlineSubmissionController.loanSubmissionGetWorkingDetail("correlationId", "crmId", 1L);
        });

        Assertions.assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, exception.getStatus());
        Assertions.assertEquals(ResponseCode.FAILED.getCode(), exception.getErrorCode());
        Assertions.assertEquals(ResponseCode.FAILED.getMessage(), exception.getErrorMessage());
    }
    
	@Test
	public void loanSubmissionGetCustomerInfoAndApplicationInfoSuccess() throws Exception {
		CustomerInformationResponse res = new CustomerInformationResponse();
		when(loanSubmissionGetCustInfoAppInfoService.getCustomerInformation(any())).thenReturn(res);
		ResponseEntity<TmbOneServiceResponse<CustomerInformationResponse>> responseEntity = loanOnlineSubmissionController
				.loanSubmissionGetCustomerInformation("correlationId", "crmId", new UpdateNCBConsentFlagRequest());

		Assertions.assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
		Assertions.assertEquals(ResponseCode.SUCCESS.getCode(),
				Objects.requireNonNull(responseEntity.getBody()).getStatus().getCode());
		Assertions.assertEquals(ResponseCode.SUCCESS.getMessage(), responseEntity.getBody().getStatus().getMessage());
	}

	@Test
	public void loanSubmissionGetCustomerInfoAndApplicationInfoThrowException() {
		TMBCommonException exception = assertThrows(TMBCommonException.class, () -> {
			doThrow(new IllegalArgumentException()).when(loanSubmissionGetCustInfoAppInfoService)
					.getCustomerInformation(any());

			loanOnlineSubmissionController.loanSubmissionGetCustomerInformation("correlationId", "crmId",
					new UpdateNCBConsentFlagRequest());
		});

		Assertions.assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, exception.getStatus());
		Assertions.assertEquals(ResponseCode.FAILED.getCode(), exception.getErrorCode());
		Assertions.assertEquals(ResponseCode.FAILED.getMessage(), exception.getErrorMessage());
	}

	@Test
	public void oanSubmissionUpdateNCBConsentFlagAndStoreFileSuccess() throws Exception {
		CustomerInformationResponse res = new CustomerInformationResponse();
		when(updateNCBConsentFlagAndStoreFileService.updateNCBConsentFlagAndStoreFile(any())).thenReturn(res);
		ResponseEntity<TmbOneServiceResponse<CustomerInformationResponse>> responseEntity = loanOnlineSubmissionController
				.loanSubmissionUpdateNCBConsentFlagAndStoreFile("correlationId", "crmId", new UpdateNCBConsentFlagRequest());

		Assertions.assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
		Assertions.assertEquals(ResponseCode.SUCCESS.getCode(),
				Objects.requireNonNull(responseEntity.getBody()).getStatus().getCode());
		Assertions.assertEquals(ResponseCode.SUCCESS.getMessage(), responseEntity.getBody().getStatus().getMessage());
	}

	@Test
	public void loanSubmissionUpdateNCBConsentFlagAndStoreFileThrowException() {
		TMBCommonException exception = assertThrows(TMBCommonException.class, () -> {
			doThrow(new IllegalArgumentException()).when(updateNCBConsentFlagAndStoreFileService)
					.updateNCBConsentFlagAndStoreFile(any());

			loanOnlineSubmissionController.loanSubmissionUpdateNCBConsentFlagAndStoreFile("correlationId", "crmId",
					new UpdateNCBConsentFlagRequest());
		});

		Assertions.assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, exception.getStatus());
		Assertions.assertEquals(ResponseCode.FAILED.getCode(), exception.getErrorCode());
		Assertions.assertEquals(ResponseCode.FAILED.getMessage(), exception.getErrorMessage());
	}
}
