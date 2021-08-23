package com.tmb.oneapp.lendingservice.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.tmb.common.exception.model.TMBCommonException;
import com.tmb.common.model.TmbOneServiceResponse;
import com.tmb.common.model.TmbStatus;
import com.tmb.common.model.legacy.rsl.ws.application.save.response.ResponseApplication;
import com.tmb.common.model.legacy.rsl.ws.individual.update.response.ResponseIndividual;
import com.tmb.oneapp.lendingservice.constant.ResponseCode;
import com.tmb.oneapp.lendingservice.model.loanonline.*;
import com.tmb.oneapp.lendingservice.model.personal.*;
import com.tmb.oneapp.lendingservice.service.*;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import javax.xml.rpc.ServiceException;
import java.math.BigDecimal;
import java.rmi.RemoteException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Objects;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class LoanOnlineSubmissionControllerTest {

    @InjectMocks
    LoanOnlineSubmissionController loanOnlineSubmissionController;

    @Mock
    LoanOnlineSubmissionCheckWaiveDocService loanOnlineSubmissionCheckWaiveDocService;

    @Mock
    LoanOnlineSubmissionCreateApplicationService loanOnlineSubmissionCreateApplicationService;

    @Mock
    LoanOnlineSubmissionGetWorkingDetailService loanOnlineSubmissionGetWorkingDetailService;
    
    @Mock
    LoanSubmissionGetCustInfoAppInfoService loanSubmissionGetCustInfoAppInfoService;

    @Mock
    LoanOnlineSubmissionUpdateWorkingDetailService loanOnlineSubmissionUpdateWorkingDetailService;

    @Mock
    LoanOnlineSubmissionGetPersonalDetailService loanOnlineSubmissionGetPersonalDetailService;

    @Mock
    LoanOnlineSubmissionUpdatePersonalDetailInfoService loanOnlineSubmissionUpdatePersonalDetailInfoService;


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
        when(loanOnlineSubmissionCreateApplicationService.createApplication(any(),any())).thenReturn(res);
        ResponseEntity<TmbOneServiceResponse<ResponseApplication>> responseEntity = loanOnlineSubmissionController.createApplication("rmid", new LoanSubmissionCreateApplicationReq());
        Assertions.assertTrue(responseEntity.getStatusCode().is2xxSuccessful());
    }

    @Test
    public void testCreateApplicationFail() throws Exception {
        when(loanOnlineSubmissionCreateApplicationService.createApplication(any(),any())).thenThrow(new IllegalArgumentException());
        ResponseEntity<TmbOneServiceResponse<ResponseApplication>> responseEntity = loanOnlineSubmissionController.createApplication("rmid", new LoanSubmissionCreateApplicationReq());
        Assertions.assertTrue(responseEntity.getStatusCode().isError());
    }

    @Test
    public void loanSubmissionGetWorkingDetailSuccess() throws Exception {
        WorkingDetail res = new WorkingDetail();
        when(loanOnlineSubmissionGetWorkingDetailService.getWorkingDetail(any(),any())).thenReturn(res);
        ResponseEntity<TmbOneServiceResponse<WorkingDetail>> responseEntity = loanOnlineSubmissionController.loanSubmissionGetWorkingDetail("correlationId", "crmId", 1L);

        Assertions.assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        Assertions.assertEquals(ResponseCode.SUCCESS.getCode(), Objects.requireNonNull(responseEntity.getBody()).getStatus().getCode());
        Assertions.assertEquals(ResponseCode.SUCCESS.getMessage(), responseEntity.getBody().getStatus().getMessage());
    }

    @Test
    public void loanSubmissionGetWorkingDetailThrowTMBCommonException() {
        TMBCommonException exception = assertThrows(TMBCommonException.class, () -> {
            doThrow(new TMBCommonException(ResponseCode.RSL_CONNECTION_ERROR.getCode(), ResponseCode.RSL_CONNECTION_ERROR.getMessage(), ResponseCode.RSL_CONNECTION_ERROR.getService(), HttpStatus.INTERNAL_SERVER_ERROR, null))
                    .when(loanOnlineSubmissionGetWorkingDetailService).getWorkingDetail(any(),any());

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
                    .when(loanOnlineSubmissionGetWorkingDetailService).getWorkingDetail(any(),any());

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
    public void  testUpdateWorkingDetailSuccess() throws ServiceException, TMBCommonException, RemoteException, JsonProcessingException {
        when(loanOnlineSubmissionUpdateWorkingDetailService.updateWorkDetail(any())).thenReturn(new ResponseIndividual());
        ResponseEntity<TmbOneServiceResponse<ResponseApplication>> responseEntity = loanOnlineSubmissionController.updateWorkingDetail(new UpdateWorkingDetailRequest());
        assertTrue(responseEntity.getStatusCode().is2xxSuccessful());
    }

    @Test
    public void  testUpdateWorkingDetailFail() throws ServiceException, TMBCommonException, RemoteException, JsonProcessingException {
        when(loanOnlineSubmissionUpdateWorkingDetailService.updateWorkDetail(any())).thenThrow(new IllegalArgumentException());
        ResponseEntity<TmbOneServiceResponse<ResponseApplication>> responseEntity = loanOnlineSubmissionController.updateWorkingDetail(new UpdateWorkingDetailRequest());
        assertTrue(responseEntity.getStatusCode().isError());
    }



    @Test
    public void testGetPersonalDetailSuccess() throws ServiceException, RemoteException, TMBCommonException, JsonProcessingException, ParseException {
        PersonalDetailRequest request = new PersonalDetailRequest();
        request.setCaId(2021071404188196L);
        String crmid = "001100000000000000000018593707";
        when(loanOnlineSubmissionGetPersonalDetailService.getPersonalDetail(any(), any())).thenReturn(mockPersonalDetailResponseData().getData());
        ResponseEntity<TmbOneServiceResponse<PersonalDetailResponse>> result = loanOnlineSubmissionController.getPersonalDetail(crmid, request);
        assertEquals(HttpStatus.OK.value(), result.getStatusCode().value());
    }

    @Test
    public void testGetPersonalDetailFail() throws ServiceException, RemoteException, TMBCommonException, JsonProcessingException, ParseException {
        PersonalDetailRequest request = new PersonalDetailRequest();
        request.setCaId(2021071404188196L);
        String crmid = "001100000000000000000018593707";
        when(loanOnlineSubmissionGetPersonalDetailService.getPersonalDetail(any(), any())).thenThrow(new NullPointerException());
        ResponseEntity<TmbOneServiceResponse<PersonalDetailResponse>> result = loanOnlineSubmissionController.getPersonalDetail(crmid, request);
        assertTrue(result.getStatusCode().isError());
    }

    @Test
    public void testUpdatePersonalDetailSuccess() throws Exception {
        PersonalDetailRequest request = new PersonalDetailRequest();
        request.setCaId(2021071404188196L);

        PersonalDetailSaveInfoRequest personalDetailSaveInfoRequest = new PersonalDetailSaveInfoRequest();
        Address address = new Address();
        DropDown resident = new DropDown();
        address.setRoomNo("111");
        address.setCountry("TH");
        address.setFloor("6");
        address.setNo("11");
        address.setBuildingName("xx");
        address.setProvince("xx");
        address.setMoo("1");
        address.setPostalCode("122222");
        address.setStreetName("xx");
        address.setRoad("xx");
        address.setTumbol("xx");
        address.setAmphur("xx");

        resident.setEntrySource("111");
        resident.setEntryId(BigDecimal.ONE);
        resident.setEntryCode("xx");
        resident.setEntryNameTh("xx");
        resident.setEntryNameEng("xx");

        personalDetailSaveInfoRequest.setThaiSalutationCode("xx");
        personalDetailSaveInfoRequest.setEngName("xx");
        personalDetailSaveInfoRequest.setEngSurname("xx");
        personalDetailSaveInfoRequest.setThaiName("xx");
        personalDetailSaveInfoRequest.setThaiSurname("xx");
        personalDetailSaveInfoRequest.setEmail("xx");
        personalDetailSaveInfoRequest.setBirthDate(Calendar.getInstance());
        personalDetailSaveInfoRequest.setIdIssueCtry1("xx");
        personalDetailSaveInfoRequest.setExpiryDate(Calendar.getInstance());
        personalDetailSaveInfoRequest.setNationality("xx");
        personalDetailSaveInfoRequest.setAddress(address);
        personalDetailSaveInfoRequest.setMobileNo("xx");
        personalDetailSaveInfoRequest.setResidentFlag(resident.getEntryCode());

        when(loanOnlineSubmissionUpdatePersonalDetailInfoService.updateCustomerInfo(any(),any())).thenReturn(mockPersonalDetailResponseData().getData());
        ResponseEntity<TmbOneServiceResponse<PersonalDetailResponse>> result = loanOnlineSubmissionController.updatePersonalDetail("001100000000000000000018593707",personalDetailSaveInfoRequest);
        assertEquals(HttpStatus.OK.value(), result.getStatusCode().value());
    }


    public TmbOneServiceResponse<PersonalDetailResponse> mockPersonalDetailResponseData() {
        TmbOneServiceResponse<PersonalDetailResponse> oneServiceResponse = new TmbOneServiceResponse<PersonalDetailResponse>();

        PersonalDetailResponse response = new PersonalDetailResponse();
        Address address = new Address();
        List<DropDown> residentList = new ArrayList<>();
        DropDown resident = new DropDown();

        List<DropDown> dropDownList = new ArrayList<>();
        DropDown dropDown = new DropDown();

        address.setAmphur("แขงวังทองหลาง");
        address.setCountry("TH");
        address.setBuildingName("มบ.ปรีชา 3");
        address.setFloor("6");
        address.setMoo("2");
        address.setNo("11");
        address.setPostalCode("10400");
        address.setProvince("dm,");
        address.setRoad("ลาดพร้าว");
        address.setTumbol("ปทุมวัน");
        address.setStreetName("ลาดพร้าว");

        resident.setEntryCode("H");
        resident.setEntryId(BigDecimal.valueOf(65239));
        resident.setEntryNameEng("Mortgages");
        resident.setEntryNameTh("อยู่ระหว่างผ่อนชำระ");
        resident.setEntrySource("HOST");
        residentList.add(resident);

        dropDown.setEntryCode("H");
        dropDown.setEntryId(BigDecimal.valueOf(65239));
        dropDown.setEntryNameEng("Mortgages");
        dropDown.setEntryNameTh("อยู่ระหว่างผ่อนชำระ");
        dropDown.setEntrySource("HOST");
        dropDownList.add(dropDown);


        response.setBirthDate(Calendar.getInstance());
        response.setEmail("kk@gmail.com");
        response.setEngName("Test");
        response.setEngSurname("Ja");
        response.setExpiryDate(Calendar.getInstance());
        response.setIdIssueCtry1("dd");
        response.setMobileNo("0987654321");
        response.setNationality("TH");
        response.setThaiName("ทีทีบี");
        response.setThaiSurname("แบงค์");
        response.setThaiSalutationCode(dropDownList);
        response.setAddress(address);
        response.setResidentFlag(residentList);

        oneServiceResponse.setStatus(new TmbStatus(ResponseCode.SUCCESS.getCode(), "success", "lending-service"));
        oneServiceResponse.setData(response);

        return oneServiceResponse;

    }

}
