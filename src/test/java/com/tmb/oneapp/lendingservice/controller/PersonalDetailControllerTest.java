package com.tmb.oneapp.lendingservice.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.tmb.common.exception.model.TMBCommonException;
import com.tmb.common.model.TmbOneServiceResponse;
import com.tmb.common.model.TmbStatus;
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
import static org.mockito.ArgumentMatchers.anyLong;
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
    public void testGetPersonalDetailSuccess() throws ServiceException, RemoteException, TMBCommonException, JsonProcessingException, ParseException {
        PersonalDetailRequest request = new PersonalDetailRequest();
        request.setCaId(2021071404188196L);
        String crmid = "001100000000000000000018593707";
        when(personalDetailService.getPersonalDetail(any(), any())).thenReturn(mockPersonalDetailResponseData().getData());
        ResponseEntity<TmbOneServiceResponse<PersonalDetailResponse>> result = personalDetailController.getPersonalDetail(crmid, request);
        assertEquals(HttpStatus.OK.value(), result.getStatusCode().value());
    }

    @Test
    public void testGetPersonalDetailFail() throws ServiceException, RemoteException, TMBCommonException, JsonProcessingException, ParseException {
        PersonalDetailRequest request = new PersonalDetailRequest();
        request.setCaId(2021071404188196L);
        String crmid = "001100000000000000000018593707";
        when(personalDetailService.getPersonalDetail(any(), any())).thenThrow(new NullPointerException());
        ResponseEntity<TmbOneServiceResponse<PersonalDetailResponse>> result = personalDetailController.getPersonalDetail(crmid, request);
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
        personalDetailSaveInfoRequest.setEngSurName("xx");
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

        when(personalDetailSaveInfoService.updateCustomerInfo(any(),any())).thenReturn(mockPersonalDetailResponseData().getData());
        ResponseEntity<TmbOneServiceResponse<PersonalDetailResponse>> result = personalDetailController.updatePersonalDetail("001100000000000000000018593707",personalDetailSaveInfoRequest);
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