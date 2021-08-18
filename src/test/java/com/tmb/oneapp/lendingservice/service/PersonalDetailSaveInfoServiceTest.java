package com.tmb.oneapp.lendingservice.service;

import com.tmb.common.model.legacy.rsl.common.ob.address.Address;
import com.tmb.common.model.legacy.rsl.common.ob.individual.Individual;
import com.tmb.common.model.legacy.rsl.ws.individual.response.ResponseIndividual;
import com.tmb.common.model.legacy.rsl.ws.individual.update.response.Header;
import com.tmb.oneapp.lendingservice.client.LoanSubmissionGetCustomerInfoClient;
import com.tmb.oneapp.lendingservice.client.LoanSubmissionUpdateCustomerClient;
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

import java.math.BigDecimal;
import java.util.Calendar;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;

@RunWith(JUnit4.class)
public class
PersonalDetailSaveInfoServiceTest {
    @Mock
    private LoanSubmissionUpdateCustomerClient updateCustomerClient;
    @Mock
    private LoanSubmissionGetCustomerInfoClient customerInfoClient;

    PersonalDetailSaveInfoService personalDetailSaveInfoService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
        personalDetailSaveInfoService = new PersonalDetailSaveInfoService(updateCustomerClient, customerInfoClient);
    }

    @Test
    public void testSaveCustomerSuccess() throws Exception {
        ResponseIndividual mockCustomerInfoResponse = new ResponseIndividual();
        com.tmb.common.model.legacy.rsl.ws.individual.response.Body customerBody = new com.tmb.common.model.legacy.rsl.ws.individual.response.Body();
        com.tmb.common.model.legacy.rsl.ws.individual.response.Header customerHeader = new com.tmb.common.model.legacy.rsl.ws.individual.response.Header();
        Address address = new Address();
        Address[] addresses = new Address[1];

        customerHeader.setResponseCode("MSG_000");
        customerHeader.setChannel("MIB");
        customerHeader.setModule("3");
        customerHeader.setResponseDescriptionEN("Success");
        Individual individual = new Individual();
        individual.setEmail("kk@kk.com");
        individual.setPersonalInfoSavedFlag("Y");
        individual.setBirthDate(Calendar.getInstance());
        individual.setNameLine1("ttn");
        individual.setNameLine2("ttb");
        individual.setThaiName("ทีทีบี");
        individual.setSpThaiSurName("แบงค์");
        individual.setNationality("TH");
        individual.setExpiryDate(Calendar.getInstance());
        individual.setThaiSalutationCode("111");
        address.setAddress("xx");
        address.setAddrTypCode("R");
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
        addresses[0] = address;
        individual.setAddresses(addresses);
        customerBody.setIndividuals(new Individual[]{individual});
        mockCustomerInfoResponse.setBody(customerBody);
        mockCustomerInfoResponse.setHeader(customerHeader);

        com.tmb.common.model.legacy.rsl.ws.individual.update.response.ResponseIndividual responseIndividual = new com.tmb.common.model.legacy.rsl.ws.individual.update.response.ResponseIndividual();
        com.tmb.common.model.legacy.rsl.ws.individual.update.request.Body body = new com.tmb.common.model.legacy.rsl.ws.individual.update.request.Body();
        com.tmb.common.model.legacy.rsl.ws.individual.update.request.RequestIndividual requestIndividual = new com.tmb.common.model.legacy.rsl.ws.individual.update.request.RequestIndividual();
        body.setIndividual(individual);
        requestIndividual.setBody(body);


        Header header = new Header();
        header.setResponseCode("MSG_000");
        responseIndividual.setHeader(header);

        when(customerInfoClient.searchCustomerInfoByCaID(anyLong())).thenReturn(mockCustomerInfoResponse);
        when(updateCustomerClient.updateCustomerInfo(any())).thenReturn(responseIndividual);
        PersonalDetailSaveInfoRequest request = new PersonalDetailSaveInfoRequest();
        request.setMobileNo("0626027648");
        request.setNationality("TH");
        request.setExpiryDate(Calendar.getInstance());
        request.setIdIssueCtry1("xx");
        request.setThaiSurname("xx");
        request.setThaiName("xxx");
        request.setThaiSalutationCode("xxx");
        request.setEngName("xxx");
        request.setEngSurName("xxxx");
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

        response = personalDetailSaveInfoService.updateCustomerInfo(request);
        Assert.assertNotNull(response);


    }

    @Test
    public void testSaveCustomerFail() throws Exception {
        ResponseIndividual mockCustomerInfoResponse = new ResponseIndividual();
        com.tmb.common.model.legacy.rsl.ws.individual.response.Body customerBody = new com.tmb.common.model.legacy.rsl.ws.individual.response.Body();
        com.tmb.common.model.legacy.rsl.ws.individual.response.Header customerHeader = new com.tmb.common.model.legacy.rsl.ws.individual.response.Header();

        Address address = new Address();
        customerHeader.setResponseCode("MSG_000");
        customerHeader.setChannel("MIB");
        customerHeader.setModule("3");
        customerHeader.setResponseDescriptionEN("Success");
        Individual individual = new Individual();
        individual.setEmail("kk@kk.com");
        individual.setPersonalInfoSavedFlag("Y");
        individual.setBirthDate(Calendar.getInstance());
        individual.setNameLine1("ttn");
        individual.setNameLine2("ttb");
        individual.setThaiName("ทีทีบี");
        individual.setSpThaiSurName("แบงค์");
        individual.setNationality("TH");
        individual.setExpiryDate(Calendar.getInstance());
        individual.setThaiSalutationCode("111");
        address.setAddress("xx");
        address.setAddrTypCode("R");
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
        Address[] addresses = new Address[1];
        addresses[0] = address;
        individual.setAddresses(addresses);
        customerBody.setIndividuals(new Individual[]{individual});
        mockCustomerInfoResponse.setBody(customerBody);
        mockCustomerInfoResponse.setHeader(customerHeader);

        com.tmb.common.model.legacy.rsl.ws.individual.update.response.ResponseIndividual responseIndividual = new com.tmb.common.model.legacy.rsl.ws.individual.update.response.ResponseIndividual();
        com.tmb.common.model.legacy.rsl.ws.individual.update.request.Body body = new com.tmb.common.model.legacy.rsl.ws.individual.update.request.Body();
        com.tmb.common.model.legacy.rsl.ws.individual.update.request.RequestIndividual requestIndividual = new com.tmb.common.model.legacy.rsl.ws.individual.update.request.RequestIndividual();
        body.setIndividual(individual);
        requestIndividual.setBody(body);


        Header header = new Header();
        header.setResponseCode("MSG_999");
        responseIndividual.setHeader(header);

        when(customerInfoClient.searchCustomerInfoByCaID(anyLong())).thenReturn(mockCustomerInfoResponse);
        when(updateCustomerClient.updateCustomerInfo(any())).thenReturn(responseIndividual);
        PersonalDetailSaveInfoRequest request = new PersonalDetailSaveInfoRequest();
        request.setMobileNo("0626027648");
        request.setNationality("TH");
        request.setExpiryDate(Calendar.getInstance());
        request.setThaiSurname("xx");
        request.setThaiName("xxx");
        request.setThaiSalutationCode("xxx");
        request.setEngName("xxx");
        request.setEngSurName("xxxx");
        request.setEmail("xxx@gmail.com");
        request.setBirthDate(Calendar.getInstance());
        request.setNationality("TH");
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

        personalDetailSaveInfoService.updateCustomerInfo(request);
        Assert.assertTrue(responseIndividual.getHeader().getResponseCode().equals("MSG_999"));


    }


}