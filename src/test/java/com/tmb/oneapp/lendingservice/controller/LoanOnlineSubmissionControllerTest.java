package com.tmb.oneapp.lendingservice.controller;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;

import java.math.BigDecimal;
import java.rmi.RemoteException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
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
import com.tmb.common.model.TmbStatus;
import com.tmb.common.model.legacy.rsl.common.ob.checklist.Checklist;
import com.tmb.common.model.legacy.rsl.ws.application.save.response.ResponseApplication;
import com.tmb.common.model.legacy.rsl.ws.checklist.response.Body;
import com.tmb.common.model.legacy.rsl.ws.individual.update.response.ResponseIndividual;
import com.tmb.oneapp.lendingservice.constant.ResponseCode;
import com.tmb.oneapp.lendingservice.model.loanonline.CustomerInformationResponse;
import com.tmb.oneapp.lendingservice.model.loanonline.IncomeInfo;
import com.tmb.oneapp.lendingservice.model.loanonline.LoanSubmissionCreateApplicationReq;
import com.tmb.oneapp.lendingservice.model.loanonline.UpdateNCBConsentFlagRequest;
import com.tmb.oneapp.lendingservice.model.loanonline.UpdateWorkingDetailRequest;
import com.tmb.oneapp.lendingservice.model.loanonline.WorkingDetail;
import com.tmb.oneapp.lendingservice.model.personal.Address;
import com.tmb.oneapp.lendingservice.model.personal.ChecklistRequest;
import com.tmb.oneapp.lendingservice.model.personal.ChecklistResponse;
import com.tmb.oneapp.lendingservice.model.personal.DropDown;
import com.tmb.oneapp.lendingservice.model.personal.PersonalDetailRequest;
import com.tmb.oneapp.lendingservice.model.personal.PersonalDetailResponse;
import com.tmb.oneapp.lendingservice.model.personal.PersonalDetailSaveInfoRequest;
import com.tmb.oneapp.lendingservice.service.LoanOnlineSubmissionCheckWaiveDocService;
import com.tmb.oneapp.lendingservice.service.LoanOnlineSubmissionCreateApplicationService;
import com.tmb.oneapp.lendingservice.service.LoanOnlineSubmissionGetDocumentListService;
import com.tmb.oneapp.lendingservice.service.LoanOnlineSubmissionGetPersonalDetailService;
import com.tmb.oneapp.lendingservice.service.LoanOnlineSubmissionGetWorkingDetailService;
import com.tmb.oneapp.lendingservice.service.LoanOnlineSubmissionUpdatePersonalDetailInfoService;
import com.tmb.oneapp.lendingservice.service.LoanOnlineSubmissionUpdateWorkingDetailService;
import com.tmb.oneapp.lendingservice.service.LoanOnlineSubmissionGetCustInformationService;
import com.tmb.oneapp.lendingservice.service.LoanOnlineSubmissionUpdateNCBConsentFlagAndStoreFileService;

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
    LoanOnlineSubmissionGetCustInformationService loanSubmissionGetCustInfoAppInfoService;
    
    @Mock
    LoanOnlineSubmissionUpdateNCBConsentFlagAndStoreFileService updateNCBConsentFlagAndStoreFileService;;

    @Mock
    LoanOnlineSubmissionUpdateWorkingDetailService loanOnlineSubmissionUpdateWorkingDetailService;

    @Mock
    LoanOnlineSubmissionGetPersonalDetailService loanOnlineSubmissionGetPersonalDetailService;

    @Mock
    LoanOnlineSubmissionUpdatePersonalDetailInfoService loanOnlineSubmissionUpdatePersonalDetailInfoService;

    @Mock
    LoanOnlineSubmissionGetDocumentListService loanOnlineSubmissionGetDocumentListService;


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

        address.setAmphur("à¹�à¸‚à¸‡à¸§à¸±à¸‡à¸—à¸­à¸‡à¸«à¸¥à¸²à¸‡");
        address.setCountry("TH");
        address.setBuildingName("à¸¡à¸š.à¸›à¸£à¸µà¸Šà¸² 3");
        address.setFloor("6");
        address.setMoo("2");
        address.setNo("11");
        address.setPostalCode("10400");
        address.setProvince("dm,");
        address.setRoad("à¸¥à¸²à¸”à¸žà¸£à¹‰à¸²à¸§");
        address.setTumbol("à¸›à¸—à¸¸à¸¡à¸§à¸±à¸™");
        address.setStreetName("à¸¥à¸²à¸”à¸žà¸£à¹‰à¸²à¸§");

        resident.setEntryCode("H");
        resident.setEntryId(BigDecimal.valueOf(65239));
        resident.setEntryNameEng("Mortgages");
        resident.setEntryNameTh("à¸­à¸¢à¸¹à¹ˆà¸£à¸°à¸«à¸§à¹ˆà¸²à¸‡à¸œà¹ˆà¸­à¸™à¸Šà¸³à¸£à¸°");
        resident.setEntrySource("HOST");
        residentList.add(resident);

        dropDown.setEntryCode("H");
        dropDown.setEntryId(BigDecimal.valueOf(65239));
        dropDown.setEntryNameEng("Mortgages");
        dropDown.setEntryNameTh("à¸­à¸¢à¸¹à¹ˆà¸£à¸°à¸«à¸§à¹ˆà¸²à¸‡à¸œà¹ˆà¸­à¸™à¸Šà¸³à¸£à¸°");
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
        response.setThaiName("à¸—à¸µà¸—à¸µà¸šà¸µ");
        response.setThaiSurname("à¹�à¸šà¸‡à¸„à¹Œ");
        response.setThaiSalutationCode(dropDownList);
        response.setAddress(address);
        response.setResidentFlag(residentList);

        oneServiceResponse.setStatus(new TmbStatus(ResponseCode.SUCCESS.getCode(), "success", "lending-service"));
        oneServiceResponse.setData(response);

        return oneServiceResponse;
    }

    @Test
    public void testGetDocumentsSuccess() throws ServiceException, TMBCommonException, JsonProcessingException {
        ChecklistRequest request = new ChecklistRequest();
        request.setCaId(2021071404188196L);
        String crmid = "001100000000000000000018593707";
        when(loanOnlineSubmissionGetDocumentListService.getDocuments(any())).thenReturn(mockChecklistResponseData().getData());
        ResponseEntity<TmbOneServiceResponse<List<ChecklistResponse>>> result = loanOnlineSubmissionController.getDocuments(crmid, request);
        assertEquals(HttpStatus.OK.value(), result.getStatusCode().value());
    }

    @Test
    public void testGetDocumentsFail() throws ServiceException, TMBCommonException, JsonProcessingException {
        ChecklistRequest request = new ChecklistRequest();
        request.setCaId(2021071404188196L);
        String crmid = "001100000000000000000018593707";
        when(loanOnlineSubmissionGetDocumentListService.getDocuments(any())).thenThrow(new NullPointerException());
        ResponseEntity<TmbOneServiceResponse<List<ChecklistResponse>>> result = loanOnlineSubmissionController.getDocuments(crmid, request);
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
