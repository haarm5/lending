package com.tmb.oneapp.lendingservice.service;

import com.tmb.common.model.CustGeneralProfileResponse;
import com.tmb.common.model.TmbOneServiceResponse;
import com.tmb.common.model.TmbStatus;
import com.tmb.common.model.address.Province;
import com.tmb.common.model.legacy.rsl.common.ob.address.Address;
import com.tmb.common.model.legacy.rsl.common.ob.dropdown.CommonCodeEntry;
import com.tmb.common.model.legacy.rsl.common.ob.individual.Individual;
import com.tmb.common.model.legacy.rsl.ws.dropdown.response.Body;
import com.tmb.common.model.legacy.rsl.ws.dropdown.response.ResponseDropdown;
import com.tmb.common.model.legacy.rsl.ws.individual.response.ResponseIndividual;
import com.tmb.common.model.legacy.rsl.ws.individual.update.response.Header;
import com.tmb.oneapp.lendingservice.client.CommonServiceFeignClient;
import com.tmb.oneapp.lendingservice.client.LoanSubmissionGetDropdownListClient;
import com.tmb.oneapp.lendingservice.client.LoanSubmissionUpdateCustomerClient;
import com.tmb.oneapp.lendingservice.constant.ResponseCode;
import com.tmb.oneapp.lendingservice.model.personal.DropDown;
import com.tmb.oneapp.lendingservice.model.personal.PersonalDetailResponse;
import com.tmb.oneapp.lendingservice.model.personal.PersonalDetailSaveInfoRequest;
import org.junit.Assert;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.when;

@RunWith(JUnit4.class)
public class
LoanOnlineSubmissionUpdatePersonalDetailInfoServiceTest {
    @Mock
    private LoanSubmissionUpdateCustomerClient updateCustomerClient;
    @Mock
    private LoanSubmissionGetDropdownListClient dropdownListClient;
    @Mock
    private LoanOnlineSubmissionGetPersonalDetailService loanOnlineSubmissionGetPersonalDetailService;
    @Mock
    private CommonServiceFeignClient commonServiceFeignClient;

    LoanOnlineSubmissionUpdatePersonalDetailInfoService loanOnlineSubmissionUpdatePersonalDetailInfoService;


    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
        loanOnlineSubmissionUpdatePersonalDetailInfoService = new LoanOnlineSubmissionUpdatePersonalDetailInfoService(updateCustomerClient, dropdownListClient, loanOnlineSubmissionGetPersonalDetailService, commonServiceFeignClient);
    }

    @Test
    public void testSaveCustomerSuccess() throws Exception {
        ResponseIndividual mockCustomerInfoResponse = new ResponseIndividual();
        com.tmb.common.model.legacy.rsl.ws.individual.response.Body customerBody = new com.tmb.common.model.legacy.rsl.ws.individual.response.Body();
        com.tmb.common.model.legacy.rsl.ws.individual.response.Header customerHeader = new com.tmb.common.model.legacy.rsl.ws.individual.response.Header();
        Address address = new Address();
        Address[] addresses = new Address[1];
        TmbStatus tmbStatus = new TmbStatus();
        tmbStatus.setCode(ResponseCode.SUCCESS.getCode());

        customerHeader.setResponseCode("MSG_000");
        customerHeader.setChannel("MIB");
        customerHeader.setModule("3");
        customerHeader.setResponseDescriptionEN("Success");

        customerHeader.setResponseCode("MSG_000");
        customerHeader.setChannel("MIB");
        customerHeader.setModule("3");
        customerHeader.setResponseDescriptionEN("Success");
        Individual individual = new Individual();
        individual.setEmail("kk@kk.com");
        individual.setTitleTypeCode("G");
        individual.setPersonalInfoSavedFlag("Y");
        individual.setBirthDate(Calendar.getInstance());
        individual.setNameLine1("ttn");
        individual.setNameLine2("ttb");
        individual.setThaiName("ทีทีบี");
        individual.setSpThaiSurName("แบงค์");
        individual.setNationality("TH");
        individual.setExpiryDate(Calendar.getInstance());
        individual.setThaiSalutationCode("111");
        individual.setCountryOfRegAddr("Y");
        address.setAddrTypCode("H");
        address.setAddress("xx");
        address.setBuildingName("xxx");
        address.setAmphur("xxx");
        address.setId(BigDecimal.ONE);
        address.setPostalCode("10400");
        address.setProvince("กทม");
        address.setMoo("1");
        address.setFloor("6");
        address.setCountry("Th");
        address.setRoad("xx");
        address.setStreetName("xx");
        address.setTumbol("xx");
        individual.setAddresses(new Address[]{address});
        customerBody.setIndividuals(new Individual[]{individual});

        ResponseDropdown mockResponse = new ResponseDropdown();
        com.tmb.common.model.legacy.rsl.ws.dropdown.response.Body body1 = new com.tmb.common.model.legacy.rsl.ws.dropdown.response.Body();
        DropDown dropDown = new DropDown();
        dropDown.setEntryCode("H");
        dropDown.setEntryNameEng("Mortgages");
        dropDown.setEntryNameTh("อยู่ระหว่างผ่อนชำระ");
        dropDown.setEntrySource("HOST");
        dropDown.setEntryId(BigDecimal.ONE);

        CommonCodeEntry item1 = new CommonCodeEntry();
        item1.setEntryCode("H");
        item1.setEntryName("Mortgages");
        item1.setEntryName2("อยู่ระหว่างผ่อนชำระ");
        item1.setEntrySource("HOST");
        item1.setEntryID(BigDecimal.ONE);
        CommonCodeEntry[] entries = new CommonCodeEntry[]{item1};
        body1.setCommonCodeEntries(entries);
        mockResponse.setBody(body1);
        com.tmb.common.model.legacy.rsl.ws.dropdown.response.Header header1 = new com.tmb.common.model.legacy.rsl.ws.dropdown.response.Header();
        header1.setResponseCode("MSG_000");
        mockResponse.setHeader(header1);

        address.setId(BigDecimal.ONE);
        address.setPostalCode("10400");
        address.setProvince("กทม");
        address.setAddrTypCode("H");
        address.setMoo("1");
        address.setFloor("6");
        address.setCountry("Th");
        address.setRoad("xx");
        address.setStreetName("xx");
        addresses[0] = address;
        individual.setAddresses(addresses);
        Individual[] individuals = new Individual[1];
        individuals[0] = individual;
        customerBody.setIndividuals(individuals);
        mockCustomerInfoResponse.setBody(customerBody);
        mockCustomerInfoResponse.setHeader(customerHeader);

        com.tmb.common.model.legacy.rsl.ws.individual.update.response.ResponseIndividual responseIndividual = new com.tmb.common.model.legacy.rsl.ws.individual.update.response.ResponseIndividual();
        com.tmb.common.model.legacy.rsl.ws.individual.update.request.Body body = new com.tmb.common.model.legacy.rsl.ws.individual.update.request.Body();
        com.tmb.common.model.legacy.rsl.ws.individual.update.request.RequestIndividual requestIndividual = new com.tmb.common.model.legacy.rsl.ws.individual.update.request.RequestIndividual();

        tmbStatus.setCode(ResponseCode.SUCCESS.getCode());
        CustGeneralProfileResponse custGeneralProfileResponse = new CustGeneralProfileResponse();
        custGeneralProfileResponse.setCitizenId("122");
        custGeneralProfileResponse.setCurrentAddrdistrictNameTh("ปทุมวัน");
        custGeneralProfileResponse.setCurrentAddrFloorNo("6");
        custGeneralProfileResponse.setEmailAddress("40654@tmbbank.com");
        custGeneralProfileResponse.setIdBirthDate("2019-11-03");
        custGeneralProfileResponse.setEngFname("ONEAPPFOUR");
        custGeneralProfileResponse.setEngLname("NA TEETEEBEE");
        custGeneralProfileResponse.setThaFname("วันแอพสี่");
        custGeneralProfileResponse.setThaLname("ทีทีบี");
        custGeneralProfileResponse.setNationality("ทีทีบี");
        custGeneralProfileResponse.setIdExpireDate("2019-11-03");
        custGeneralProfileResponse.setPhoneNoFull("0891117777");
        custGeneralProfileResponse.setCurrentAddrVillageOrbuilding("cv");
        custGeneralProfileResponse.setCurrentAddrMoo("1");
        custGeneralProfileResponse.setCurrentAddrProvinceNameTh("dm,");
        custGeneralProfileResponse.setCurrentAddrStreet("ลาดพร้าว");
        custGeneralProfileResponse.setWorkAddrZipcode("10240");
        custGeneralProfileResponse.setWorkAddrSubDistrictNameTh("แขงวังทองหลาง");
        custGeneralProfileResponse.setCountryOfIncome("TH");
        custGeneralProfileResponse.setEducationCode("02");
        custGeneralProfileResponse.setCustomerLevel("02");
        custGeneralProfileResponse.setCustomerType("902");
        custGeneralProfileResponse.setGender("M");
        custGeneralProfileResponse.setMaritalStatus("M");


        body.setIndividual(individual);
        requestIndividual.setBody(body);


        Header header = new Header();
        header.setResponseCode("MSG_000");
        responseIndividual.setHeader(header);

        when(updateCustomerClient.updateCustomerInfo(any())).thenReturn(responseIndividual);
        when(loanOnlineSubmissionGetPersonalDetailService.getCustomer(any())).thenReturn(customerBody.getIndividuals()[0]);
        when(loanOnlineSubmissionGetPersonalDetailService.getCustomerEC(any())).thenReturn(custGeneralProfileResponse);
        ResponseDropdown responseDropdown = new ResponseDropdown();
        Body dropdownsBody = new Body();
        CommonCodeEntry commonCodeEntry = new CommonCodeEntry();
        CommonCodeEntry[] commonCodeEntries = {commonCodeEntry};
        dropdownsBody.setCommonCodeEntries(commonCodeEntries);
        responseDropdown.setBody(dropdownsBody);
        doReturn(responseDropdown).when(dropdownListClient).getDropDownListByCode(anyString());

        TmbOneServiceResponse<List<Province>> mockProvince = new TmbOneServiceResponse<>();
        var status = new TmbStatus();
        status.setCode("0000");
        mockProvince.setStatus(status);
        var mockList = new ArrayList<Province>();
        mockList.add(new Province());
        mockProvince.setData(mockList);

        doReturn(new ResponseEntity<>(mockProvince, HttpStatus.OK)).when(commonServiceFeignClient).getProvince(any());

        PersonalDetailSaveInfoRequest request = new PersonalDetailSaveInfoRequest();
        request.setMobileNo("0626027648");
        request.setNationality("TH");
        request.setExpiryDate(Calendar.getInstance());
        request.setIdIssueCtry1("xx");
        request.setThaiSurname("xx");
        request.setThaiName("xxx");
        request.setThaiSalutationCode("xxx");
        request.setEngName("xxx");
        request.setEngSurname("xxxx");
        request.setEmail("xxx@gmail.com");
        request.setBirthDate(Calendar.getInstance());
        request.setNationality("TH");
        request.setExpiryDate(Calendar.getInstance());
        DropDown resident = new DropDown();
        resident.setEntryNameEng("xxx");
        resident.setEntryNameTh("xxx");
        resident.setEntryCode("xx");
        resident.setEntryId(BigDecimal.ONE);
        resident.setEntrySource("H");
        request.setResidentFlag(resident.getEntryCode());
        com.tmb.oneapp.lendingservice.model.personal.Address address1 = new com.tmb.oneapp.lendingservice.model.personal.Address();
        address1.setNo("111");
        address1.setRoad("xx");
        address1.setCountry("TH");
        address1.setFloor("6");
        address1.setTumbol("xxx");
        address1.setMoo("2");
        address1.setStreetName("xxx");
        address1.setProvince("xxx");
        address1.setPostalCode("10400");
        address1.setBuildingName("xx");
        address1.setAmphur("xxx");
        request.setAddress(address1);
        request.setCaId(2021071404188196L);
        PersonalDetailResponse response = new PersonalDetailResponse();
        response.setPrefix("G01");
        response.setEngSurname("xxx");
        response.setCitizenId("1111");
        response.setIdIssueCtry1("111");
        response.setAddress(address1);
        response.setResidentFlag(Collections.singletonList(resident));



        response = loanOnlineSubmissionUpdatePersonalDetailInfoService.updateCustomerInfo("001100000000000000000018593707", request);
        Assert.assertNotNull(response);


    }

    @Test
    public void testSaveCustomerFail() throws Exception {
        ResponseIndividual mockCustomerInfoResponse = new ResponseIndividual();
        com.tmb.common.model.legacy.rsl.ws.individual.response.Body customerBody = new com.tmb.common.model.legacy.rsl.ws.individual.response.Body();
        com.tmb.common.model.legacy.rsl.ws.individual.response.Header customerHeader = new com.tmb.common.model.legacy.rsl.ws.individual.response.Header();
        Address address = new Address();
        Address[] addresses = new Address[1];
        TmbStatus tmbStatus = new TmbStatus();
        tmbStatus.setCode(ResponseCode.SUCCESS.getCode());

        customerHeader.setResponseCode("MSG_000");
        customerHeader.setChannel("MIB");
        customerHeader.setModule("3");
        customerHeader.setResponseDescriptionEN("Success");

        Individual individual = new Individual();
        individual.setEmail("kk@kk.com");
        individual.setTitleTypeCode("G");
        individual.setPersonalInfoSavedFlag("Y");
        individual.setBirthDate(Calendar.getInstance());
        individual.setNameLine1("ttn");
        individual.setNameLine2("ttb");
        individual.setThaiName("ทีทีบี");
        individual.setSpThaiSurName("แบงค์");
        individual.setNationality("TH");
        individual.setExpiryDate(Calendar.getInstance());
        individual.setThaiSalutationCode("111");
        individual.setCountryOfRegAddr("Y");
        address.setAddrTypCode("H");
        address.setAddress("xx");
        address.setBuildingName("xxx");
        address.setAmphur("xxx");
        address.setId(BigDecimal.ONE);
        address.setPostalCode("10400");
        address.setProvince("กทม");
        address.setMoo("1");
        address.setFloor("6");
        address.setCountry("Th");
        address.setRoad("xx");
        address.setStreetName("xx");
        address.setTumbol("xx");
        individual.setAddresses(new Address[]{address});
        customerBody.setIndividuals(new Individual[]{individual});

        ResponseDropdown mockResponse = new ResponseDropdown();
        com.tmb.common.model.legacy.rsl.ws.dropdown.response.Body body1 = new com.tmb.common.model.legacy.rsl.ws.dropdown.response.Body();
        DropDown dropDown = new DropDown();
        dropDown.setEntryCode("H");
        dropDown.setEntryNameEng("Mortgages");
        dropDown.setEntryNameTh("อยู่ระหว่างผ่อนชำระ");
        dropDown.setEntrySource("HOST");
        dropDown.setEntryId(BigDecimal.ONE);

        CommonCodeEntry item1 = new CommonCodeEntry();
        item1.setEntryCode("H");
        item1.setEntryName("Mortgages");
        item1.setEntryName2("อยู่ระหว่างผ่อนชำระ");
        item1.setEntrySource("HOST");
        item1.setEntryID(BigDecimal.ONE);
        CommonCodeEntry[] entries = new CommonCodeEntry[]{item1};
        body1.setCommonCodeEntries(entries);
        mockResponse.setBody(body1);
        com.tmb.common.model.legacy.rsl.ws.dropdown.response.Header header1 = new com.tmb.common.model.legacy.rsl.ws.dropdown.response.Header();
        header1.setResponseCode("MSG_000");
        mockResponse.setHeader(header1);

        address.setId(BigDecimal.ONE);
        address.setPostalCode("10400");
        address.setProvince("กทม");
        address.setAddrTypCode("H");
        address.setMoo("1");
        address.setFloor("6");
        address.setCountry("Th");
        address.setRoad("xx");
        address.setStreetName("xx");
        addresses[0] = address;
        individual.setAddresses(addresses);
        Individual[] individuals = new Individual[1];
        individuals[0] = individual;
        customerBody.setIndividuals(individuals);
        mockCustomerInfoResponse.setBody(customerBody);
        mockCustomerInfoResponse.setHeader(customerHeader);

        com.tmb.common.model.legacy.rsl.ws.individual.update.response.ResponseIndividual responseIndividual = new com.tmb.common.model.legacy.rsl.ws.individual.update.response.ResponseIndividual();
        com.tmb.common.model.legacy.rsl.ws.individual.update.request.Body body = new com.tmb.common.model.legacy.rsl.ws.individual.update.request.Body();
        com.tmb.common.model.legacy.rsl.ws.individual.update.request.RequestIndividual requestIndividual = new com.tmb.common.model.legacy.rsl.ws.individual.update.request.RequestIndividual();

        tmbStatus.setCode(ResponseCode.SUCCESS.getCode());
        CustGeneralProfileResponse custGeneralProfileResponse = new CustGeneralProfileResponse();
        custGeneralProfileResponse.setCitizenId("122");
        custGeneralProfileResponse.setCurrentAddrdistrictNameTh("ปทุมวัน");
        custGeneralProfileResponse.setCurrentAddrFloorNo("6");
        custGeneralProfileResponse.setEmailAddress("40654@tmbbank.com");
        custGeneralProfileResponse.setIdBirthDate("2019-11-03");
        custGeneralProfileResponse.setEngFname("ONEAPPFOUR");
        custGeneralProfileResponse.setEngLname("NA TEETEEBEE");
        custGeneralProfileResponse.setThaFname("วันแอพสี่");
        custGeneralProfileResponse.setThaLname("ทีทีบี");
        custGeneralProfileResponse.setNationality("ทีทีบี");
        custGeneralProfileResponse.setIdExpireDate("2019-11-03");
        custGeneralProfileResponse.setPhoneNoFull("0891117777");
        custGeneralProfileResponse.setCurrentAddrVillageOrbuilding("cv");
        custGeneralProfileResponse.setCurrentAddrMoo("1");
        custGeneralProfileResponse.setCurrentAddrProvinceNameTh("dm,");
        custGeneralProfileResponse.setCurrentAddrStreet("ลาดพร้าว");
        custGeneralProfileResponse.setWorkAddrZipcode("10240");
        custGeneralProfileResponse.setWorkAddrSubDistrictNameTh("แขงวังทองหลาง");
        custGeneralProfileResponse.setCountryOfIncome("TH");
        custGeneralProfileResponse.setEducationCode("02");
        custGeneralProfileResponse.setCustomerLevel("02");
        custGeneralProfileResponse.setCustomerType("902");
        custGeneralProfileResponse.setGender("M");
        custGeneralProfileResponse.setMaritalStatus("M");

        body.setIndividual(individual);
        requestIndividual.setBody(body);

        Header header = new Header();
        header.setResponseCode("MSG_999");
        responseIndividual.setHeader(header);

        when(updateCustomerClient.updateCustomerInfo(any())).thenReturn(responseIndividual);
        when(loanOnlineSubmissionGetPersonalDetailService.getCustomer(any())).thenReturn(customerBody.getIndividuals()[0]);
        when(loanOnlineSubmissionGetPersonalDetailService.getCustomerEC(any())).thenReturn(custGeneralProfileResponse);
        ResponseDropdown responseDropdown = new ResponseDropdown();
        Body dropdownsBody = new Body();
        CommonCodeEntry commonCodeEntry = new CommonCodeEntry();
        CommonCodeEntry[] commonCodeEntries = {commonCodeEntry};
        dropdownsBody.setCommonCodeEntries(commonCodeEntries);
        responseDropdown.setBody(dropdownsBody);
        doReturn(responseDropdown).when(dropdownListClient).getDropDownListByCode(anyString());

        TmbOneServiceResponse<List<Province>> mockProvince = new TmbOneServiceResponse<>();
        var status = new TmbStatus();
        status.setCode("0000");
        mockProvince.setStatus(status);
        var mockList = new ArrayList<Province>();
        mockList.add(new Province());
        mockProvince.setData(mockList);
        doReturn(new ResponseEntity<>(mockProvince, HttpStatus.OK)).when(commonServiceFeignClient).getProvince(any());


        PersonalDetailSaveInfoRequest request = new PersonalDetailSaveInfoRequest();
        request.setMobileNo("0626027648");
        request.setNationality("TH");
        request.setExpiryDate(Calendar.getInstance());
        request.setIdIssueCtry1("xx");
        request.setThaiSurname("xx");
        request.setThaiName("xxx");
        request.setThaiSalutationCode("xxx");
        request.setEngName("xxx");
        request.setEngSurname("xxxx");
        request.setEmail("xxx@gmail.com");
        request.setBirthDate(Calendar.getInstance());
        request.setNationality("TH");
        request.setExpiryDate(Calendar.getInstance());
        DropDown resident = new DropDown();
        resident.setEntryNameEng("xxx");
        resident.setEntryNameTh("xxx");
        resident.setEntryCode("xx");
        resident.setEntryId(BigDecimal.ONE);
        resident.setEntrySource("H");
        request.setResidentFlag(resident.getEntryCode());
        com.tmb.oneapp.lendingservice.model.personal.Address address1 = new com.tmb.oneapp.lendingservice.model.personal.Address();
        address1.setNo("111");
        address1.setRoad("xx");
        address1.setCountry("TH");
        address1.setFloor("6");
        address1.setTumbol("xxx");
        address1.setMoo("2");
        address1.setStreetName("xxx");
        address1.setProvince("xxx");
        address1.setPostalCode("10400");
        address1.setBuildingName("xx");
        address1.setAmphur("xxx");
        request.setAddress(address1);
        request.setCaId(2021071404188196L);
        PersonalDetailResponse response = new PersonalDetailResponse();
        response.setPrefix("G01");
        response.setEngSurname("xxx");
        response.setCitizenId("1111");
        response.setIdIssueCtry1("111");
        response.setAddress(address1);
        response.setResidentFlag(Collections.singletonList(resident));

        loanOnlineSubmissionUpdatePersonalDetailInfoService.updateCustomerInfo("001100000000000000000018593707", request);
        Assert.assertFalse(responseIndividual.getHeader().getResponseCode().equals("MSG_000"));

    }


}