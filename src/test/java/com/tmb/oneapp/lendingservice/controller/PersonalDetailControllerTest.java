package com.tmb.oneapp.lendingservice.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.tmb.common.exception.model.TMBCommonException;
import com.tmb.common.model.TmbOneServiceResponse;
import com.tmb.common.model.TmbStatus;
import com.tmb.common.model.legacy.rsl.common.ob.individual.Individual;
import com.tmb.common.model.legacy.rsl.ws.individual.update.response.Body;
import com.tmb.common.model.legacy.rsl.ws.individual.update.response.Header;
import com.tmb.common.model.legacy.rsl.ws.individual.update.response.ResponseIndividual;
import com.tmb.oneapp.lendingservice.constant.ResponseCode;
import com.tmb.oneapp.lendingservice.model.personal.*;
import com.tmb.oneapp.lendingservice.service.PersonalDetailSaveInfoService;
import com.tmb.oneapp.lendingservice.service.PersonalDetailService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
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

import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@RunWith(JUnit4.class)
public class PersonalDetailControllerTest {

    PersonalDetailController personalDetailController;

    @Mock
    PersonalDetailService personalDetailService;

    @Mock
    PersonalDetailSaveInfoService personalDetailSaveInfoService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
        personalDetailController = new PersonalDetailController(personalDetailService,personalDetailSaveInfoService);
    }

    @Test
    public void testGetPersonalDetailSuccess() throws ServiceException, RemoteException, TMBCommonException, JsonProcessingException {
        PersonalDetailRequest request = new PersonalDetailRequest();
        request.setCaId(2021071404188196L);
        String crmid = "001100000000000000000018593707";
        when(personalDetailService.getPersonalDetail(any(), any())).thenReturn(mockPersonalDetailResponseData().getData());
        ResponseEntity<TmbOneServiceResponse<PersonalDetailResponse>> result = personalDetailController.getPersonalDetail(crmid, request);
        assertEquals(HttpStatus.OK.value(), result.getStatusCode().value());
    }

    @Test
    public void testGetPersonalDetailFail() throws ServiceException, RemoteException, TMBCommonException, JsonProcessingException {
        PersonalDetailRequest request = new PersonalDetailRequest();
        request.setCaId(2021071404188196L);
        String crmid = "001100000000000000000018593707";
        when(personalDetailService.getPersonalDetail(any(), any())).thenThrow(new NullPointerException());
        ResponseEntity<TmbOneServiceResponse<PersonalDetailResponse>> result = personalDetailController.getPersonalDetail(crmid, request);
        assertTrue(result.getStatusCode().isError());
    }

    @Test
    public void testUpdatePersonalDetailSuccess() throws ServiceException, RemoteException, TMBCommonException, ParseException, JsonProcessingException {
        PersonalDetailRequest request = new PersonalDetailRequest();
        request.setCaId(2021071404188196L);

        PersonalDetailSaveInfoRequest personalDetailSaveInfoRequest = new PersonalDetailSaveInfoRequest();
        Address address = new Address();
        Resident resident = new Resident();
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
        personalDetailSaveInfoRequest.setEngSurName("xx");
        personalDetailSaveInfoRequest.setThaiName("xx");
        personalDetailSaveInfoRequest.setThaiSurName("xx");
        personalDetailSaveInfoRequest.setEmail("xx");
        personalDetailSaveInfoRequest.setBirthDate(Calendar.getInstance());
        personalDetailSaveInfoRequest.setIdIssueCtry1("xx");
        personalDetailSaveInfoRequest.setExpiryDate(Calendar.getInstance());
        personalDetailSaveInfoRequest.setNationality("xx");
        personalDetailSaveInfoRequest.setAddress(address);
        personalDetailSaveInfoRequest.setMobileNo("xx");
        personalDetailSaveInfoRequest.setResidentFlag(resident);

        when(personalDetailSaveInfoService.updateCustomerInfo(any(), any())).thenReturn(mockResponseIndividual().getData());
        ResponseEntity<TmbOneServiceResponse<ResponseIndividual>> result = personalDetailController.updatePersonalDetail(request,personalDetailSaveInfoRequest);
        assertEquals(HttpStatus.OK.value(), result.getStatusCode().value());
    }

    @Test
    public void testUpdatePersonalDetailFail() throws ServiceException, RemoteException, TMBCommonException, ParseException, JsonProcessingException {
        PersonalDetailRequest request = new PersonalDetailRequest();
        request.setCaId(2021071404188196L);
        PersonalDetailSaveInfoRequest personalDetailSaveInfoRequest = new PersonalDetailSaveInfoRequest();
        Address address = new Address();
        Resident resident = new Resident();
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
        personalDetailSaveInfoRequest.setEngSurName("xx");
        personalDetailSaveInfoRequest.setThaiName("xx");
        personalDetailSaveInfoRequest.setThaiSurName("xx");
        personalDetailSaveInfoRequest.setEmail("xx");
        personalDetailSaveInfoRequest.setBirthDate(Calendar.getInstance());
        personalDetailSaveInfoRequest.setIdIssueCtry1("xx");
        personalDetailSaveInfoRequest.setExpiryDate(Calendar.getInstance());
        personalDetailSaveInfoRequest.setNationality("xx");
        personalDetailSaveInfoRequest.setAddress(address);
        personalDetailSaveInfoRequest.setMobileNo("xx");
        personalDetailSaveInfoRequest.setResidentFlag(resident);
        when(personalDetailSaveInfoService.updateCustomerInfo(any(), any())).thenThrow(new NullPointerException());
        ResponseEntity<TmbOneServiceResponse<ResponseIndividual>> result = personalDetailController.updatePersonalDetail(request,personalDetailSaveInfoRequest);

        assertTrue(result.getStatusCode().isError());
    }

    private TmbOneServiceResponse<PersonalDetailResponse> mockPersonalDetailResponseData() {
        TmbOneServiceResponse<PersonalDetailResponse> oneServiceResponse = new TmbOneServiceResponse<PersonalDetailResponse>();

        PersonalDetailResponse response = new PersonalDetailResponse();
        Address address = new Address();
        List<Resident> residentList = new ArrayList<>();
        Resident resident = new Resident();
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


        response.setBirthDate("11/10/33");
        response.setEmail("kk@gmail.com");
        response.setEngName("Test");
        response.setEngSurName("Ja");
        response.setExpiryDate("11/11/63");
        response.setIdIssueCtry1("dd");
        response.setMobileNo("0987654321");
        response.setNationality("TH");
        response.setThaiName("ทีทีบี");
        response.setThaiSurName("แบงค์");
        response.setThaiSalutationCode("1800272993728");
        response.setAddress(address);
        response.setResidentFlag(residentList);

        oneServiceResponse.setStatus(new TmbStatus(ResponseCode.SUCCESS.getCode(), "success", "lending-service"));
        oneServiceResponse.setData(response);

        return oneServiceResponse;

    }

    private TmbOneServiceResponse<ResponseIndividual> mockResponseIndividual() {
        TmbOneServiceResponse<ResponseIndividual> oneServiceResponse = new TmbOneServiceResponse<ResponseIndividual>();
        Body body = new Body();
        Header header = new Header();

        ResponseIndividual response = new ResponseIndividual();

        response.setBody(body);
        response.setHeader(header);

        oneServiceResponse.setStatus(new TmbStatus(ResponseCode.FAILED.getCode(), "success", "lending-service"));
        oneServiceResponse.setData(response);

        return oneServiceResponse;

    }
}