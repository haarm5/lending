package com.tmb.oneapp.lendingservice.service;

import com.tmb.common.model.CustGeneralProfileResponse;
import com.tmb.common.model.TmbOneServiceResponse;
import com.tmb.common.model.TmbStatus;
import com.tmb.common.model.legacy.rsl.common.ob.dropdown.CommonCodeEntry;
import com.tmb.common.model.legacy.rsl.ws.application.save.response.Body;
import com.tmb.common.model.legacy.rsl.ws.application.save.response.Header;
import com.tmb.common.model.legacy.rsl.ws.application.save.response.ResponseApplication;
import com.tmb.common.model.legacy.rsl.ws.dropdown.response.ResponseDropdown;
import com.tmb.oneapp.lendingservice.client.CommonServiceFeignClient;
import com.tmb.oneapp.lendingservice.client.CustomerServiceClient;
import com.tmb.oneapp.lendingservice.client.LoanSubmissionCreateApplicationClient;
import com.tmb.oneapp.lendingservice.client.LoanSubmissionGetDropdownListClient;
import com.tmb.oneapp.lendingservice.constant.ResponseCode;
import com.tmb.oneapp.lendingservice.model.loanonline.LoanSubmissionCreateApplicationReq;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.when;


@RunWith(JUnit4.class)
class LoanOnlineSubmissionCreateApplicationServiceTest {


    @Mock
    private LoanSubmissionCreateApplicationClient loanSubmissionCreateApplicationClient;
    @Mock
    private CustomerServiceClient customerServiceClient;
    @Mock
    private LoanSubmissionGetDropdownListClient dropdownListClient;
    @Mock
    private CommonServiceFeignClient commonServiceFeignClient;


    LoanOnlineSubmissionCreateApplicationService loanOnlineSubmissionCreateApplicationService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
        loanOnlineSubmissionCreateApplicationService = new LoanOnlineSubmissionCreateApplicationService(
                loanSubmissionCreateApplicationClient, customerServiceClient,
                dropdownListClient, commonServiceFeignClient);
    }


    @Test
    public void testCreateApplicationTypeCC() throws Exception {
        LoanSubmissionCreateApplicationReq req = new LoanSubmissionCreateApplicationReq();
        req.setProductCode("VM");
        req.setEmploymentStatus("01");

        Header appHeader = new Header();
        appHeader.setResponseCode("MSG_000");
        Body body = new Body();
        body.setAppType("test");
        ResponseApplication res = new ResponseApplication();
        res.setHeader(appHeader);
        res.setBody(body);
        when(loanSubmissionCreateApplicationClient.createApplication(any())).thenReturn(res);


        TmbOneServiceResponse oneServiceResponse = new TmbOneServiceResponse();
        TmbStatus tmbStatus = new TmbStatus();
        tmbStatus.setCode(ResponseCode.SUCCESS.getCode());
        CustGeneralProfileResponse custGeneralProfileResponse = new CustGeneralProfileResponse();
        custGeneralProfileResponse.setCitizenId("122");
        custGeneralProfileResponse.setCurrentAddrdistrictNameTh("ปทุมวัน");
        custGeneralProfileResponse.setCurrentAddrFloorNo("6");
        custGeneralProfileResponse.setEmailAddress("40654@tmbbank.com");
        custGeneralProfileResponse.setIdBirthDate("11-11-2563");
        custGeneralProfileResponse.setEngFname("ONEAPPFOUR");
        custGeneralProfileResponse.setEngLname("NA TEETEEBEE");
        custGeneralProfileResponse.setThaTname("นาย");
        custGeneralProfileResponse.setThaFname("วันแอพสี่");
        custGeneralProfileResponse.setThaLname("ทีทีบี");
        custGeneralProfileResponse.setNationality("ทีทีบี");
        custGeneralProfileResponse.setIdExpireDate("11-11-2563");
        custGeneralProfileResponse.setPhoneNoFull("0891117777");
        custGeneralProfileResponse.setCurrentAddrVillageOrbuilding("cv");
        custGeneralProfileResponse.setCurrentAddrMoo("1");
        custGeneralProfileResponse.setCurrentAddrProvinceNameTh("dm,");
        custGeneralProfileResponse.setCurrentAddrStreet("ลาดพร้าว");
        custGeneralProfileResponse.setWorkAddrZipcode("10240");
        custGeneralProfileResponse.setWorkAddrSubDistrictNameTh("แขงวังทองหลาง");
        oneServiceResponse.setData(custGeneralProfileResponse);
        oneServiceResponse.setStatus(tmbStatus);
        when(customerServiceClient.getCustomers(any())).thenReturn(ResponseEntity.ok(oneServiceResponse));

        ResponseDropdown responseDropdown = new ResponseDropdown();
        com.tmb.common.model.legacy.rsl.ws.dropdown.response.Body dropdownsBody = new com.tmb.common.model.legacy.rsl.ws.dropdown.response.Body();
        CommonCodeEntry commonCodeEntry = new CommonCodeEntry();
        commonCodeEntry.setEntryName("นาย");
        commonCodeEntry.setEntryCode("01");
        CommonCodeEntry[] commonCodeEntries = {commonCodeEntry};
        dropdownsBody.setCommonCodeEntries(commonCodeEntries);
        responseDropdown.setBody(dropdownsBody);
        doReturn(responseDropdown).when(dropdownListClient).getDropDownListByCode(anyString());

        doReturn(LoanServiceUtils.moduleLendingModuleConfig()).when(commonServiceFeignClient).getCommonConfig(any(), anyString());

        ResponseApplication result = loanOnlineSubmissionCreateApplicationService.createApplication(req, "rmId");
        assertEquals("test", result.getBody().getAppType());
    }

    @Test
    public void testCreateApplicationTypePL() throws Exception {
        LoanSubmissionCreateApplicationReq req = new LoanSubmissionCreateApplicationReq();
        req.setProductCode("C2G");
        req.setEmploymentStatus("01");

        Header appHeader = new Header();
        appHeader.setResponseCode("MSG_000");
        Body body = new Body();
        body.setAppType("test");
        ResponseApplication res = new ResponseApplication();
        res.setHeader(appHeader);
        res.setBody(body);
        when(loanSubmissionCreateApplicationClient.createApplication(any())).thenReturn(res);


        TmbOneServiceResponse oneServiceResponse = new TmbOneServiceResponse();
        TmbStatus tmbStatus = new TmbStatus();
        tmbStatus.setCode(ResponseCode.SUCCESS.getCode());
        CustGeneralProfileResponse custGeneralProfileResponse = new CustGeneralProfileResponse();
        custGeneralProfileResponse.setCitizenId("122");
        custGeneralProfileResponse.setCurrentAddrdistrictNameTh("ปทุมวัน");
        custGeneralProfileResponse.setCurrentAddrFloorNo("6");
        custGeneralProfileResponse.setEmailAddress("40654@tmbbank.com");
        custGeneralProfileResponse.setIdBirthDate("11-11-2563");
        custGeneralProfileResponse.setEngFname("ONEAPPFOUR");
        custGeneralProfileResponse.setEngLname("NA TEETEEBEE");
        custGeneralProfileResponse.setThaTname("นาย");
        custGeneralProfileResponse.setThaFname("วันแอพสี่");
        custGeneralProfileResponse.setThaLname("ทีทีบี");
        custGeneralProfileResponse.setNationality("ทีทีบี");
        custGeneralProfileResponse.setIdExpireDate("11-11-2563");
        custGeneralProfileResponse.setPhoneNoFull("0891117777");
        custGeneralProfileResponse.setCurrentAddrVillageOrbuilding("cv");
        custGeneralProfileResponse.setCurrentAddrMoo("1");
        custGeneralProfileResponse.setCurrentAddrProvinceNameTh("dm,");
        custGeneralProfileResponse.setCurrentAddrStreet("ลาดพร้าว");
        custGeneralProfileResponse.setWorkAddrZipcode("10240");
        custGeneralProfileResponse.setWorkAddrSubDistrictNameTh("แขงวังทองหลาง");
        oneServiceResponse.setData(custGeneralProfileResponse);
        oneServiceResponse.setStatus(tmbStatus);
        when(customerServiceClient.getCustomers(any())).thenReturn(ResponseEntity.ok(oneServiceResponse));

        ResponseDropdown responseDropdown = new ResponseDropdown();
        com.tmb.common.model.legacy.rsl.ws.dropdown.response.Body dropdownsBody = new com.tmb.common.model.legacy.rsl.ws.dropdown.response.Body();
        CommonCodeEntry commonCodeEntry = new CommonCodeEntry();
        commonCodeEntry.setEntryName("นาย");
        commonCodeEntry.setEntryCode("01");
        CommonCodeEntry[] commonCodeEntries = {commonCodeEntry};
        dropdownsBody.setCommonCodeEntries(commonCodeEntries);
        responseDropdown.setBody(dropdownsBody);
        doReturn(responseDropdown).when(dropdownListClient).getDropDownListByCode(anyString());

        doReturn(LoanServiceUtils.moduleLendingModuleConfig()).when(commonServiceFeignClient).getCommonConfig(any(), anyString());

        ResponseApplication result = loanOnlineSubmissionCreateApplicationService.createApplication(req, "rmId");
        assertEquals("test", result.getBody().getAppType());
    }
}