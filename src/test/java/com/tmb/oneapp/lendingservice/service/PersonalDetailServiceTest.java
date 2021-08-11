package com.tmb.oneapp.lendingservice.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.tmb.common.exception.model.TMBCommonException;
import com.tmb.common.model.CustGeneralProfileResponse;
import com.tmb.common.model.TmbOneServiceResponse;
import com.tmb.common.model.TmbStatus;
import com.tmb.common.model.legacy.rsl.common.ob.address.Address;
import com.tmb.common.model.legacy.rsl.common.ob.dropdown.CommonCodeEntry;
import com.tmb.common.model.legacy.rsl.common.ob.individual.Individual;
import com.tmb.common.model.legacy.rsl.ws.dropdown.response.Body;
import com.tmb.common.model.legacy.rsl.ws.dropdown.response.Header;
import com.tmb.common.model.legacy.rsl.ws.dropdown.response.ResponseDropdown;
import com.tmb.common.model.legacy.rsl.ws.individual.response.ResponseIndividual;
import com.tmb.oneapp.lendingservice.client.CustomerServiceClient;
import com.tmb.oneapp.lendingservice.client.LoanSubmissionGetCustomerInfoClient;
import com.tmb.oneapp.lendingservice.constant.ResponseCode;
import com.tmb.oneapp.lendingservice.model.personal.PersonalDetailRequest;
import com.tmb.oneapp.lendingservice.model.personal.PersonalDetailResponse;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;

import javax.xml.rpc.ServiceException;
import java.math.BigDecimal;
import java.rmi.RemoteException;
import java.text.ParseException;
import java.util.Calendar;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.when;

@RunWith(JUnit4.class)
public class PersonalDetailServiceTest {

    @Mock
    private CustomerServiceClient customerServiceClient;
    @Mock
    private LoanSubmissionGetCustomerInfoClient customerInfoClient;
    @Mock
    private DropdownService dropdownService;

    @InjectMocks
    PersonalDetailService personalDetailService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testGetPersonalDetailSuccess() throws ServiceException, RemoteException, TMBCommonException, JsonProcessingException, ParseException {
        PersonalDetailRequest request = new PersonalDetailRequest();
        request.setCaId(2021071404188196L);

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
        individual.setAddresses(new Address[]{address});
        customerBody.setIndividuals(new Individual[]{individual});
        mockCustomerInfoResponse.setBody(customerBody);
        mockCustomerInfoResponse.setHeader(customerHeader);

        ResponseDropdown mockResponse = new ResponseDropdown();
        Body body = new Body();
        CommonCodeEntry item1 = new CommonCodeEntry();
        item1.setEntryCode("M");
        item1.setEntryName("Mortgages");
        item1.setEntryName2("อยู่ระหว่างผ่อนชำระ");
        item1.setEntrySource("HOST");
        item1.setEntryID(BigDecimal.ONE);
        CommonCodeEntry[] entries = new CommonCodeEntry[]{item1};
        body.setCommonCodeEntries(entries);
        mockResponse.setBody(body);
        Header header = new Header();
        header.setResponseCode("MSG_000");
        mockResponse.setHeader(header);

        TmbOneServiceResponse oneServiceResponse = new TmbOneServiceResponse();
        TmbStatus tmbStatus = new TmbStatus();
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
        oneServiceResponse.setData(custGeneralProfileResponse);
        oneServiceResponse.setStatus(tmbStatus);


        when(customerInfoClient.searchCustomerInfoByCaID(anyLong())).thenReturn(mockCustomerInfoResponse);
        when(customerServiceClient.getCustomers(any())).thenReturn(ResponseEntity.ok(oneServiceResponse));
        doReturn(new ResponseDropdown()).when(dropdownService).getDropdown(anyString());

        PersonalDetailResponse response = personalDetailService.getPersonalDetail("001100000000000000000018593707",request.getCaId());
        Assertions.assertNotNull(response);

    }


    @Test
    public void testGetPersonalDetailPersonalFlagNotYSuccess() throws ServiceException, RemoteException, TMBCommonException, JsonProcessingException, ParseException {
        PersonalDetailRequest request = new PersonalDetailRequest();
        request.setCaId(2021071404188196L);

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
        individual.setPersonalInfoSavedFlag("N");
        individual.setBirthDate(Calendar.getInstance());
        individual.setNameLine1("ttn");
        individual.setNameLine2("ttb");
        individual.setThaiName("ทีทีบี");
        individual.setSpThaiSurName("แบงค์");
        individual.setNationality("TH");
        individual.setExpiryDate(Calendar.getInstance());
        individual.setThaiSalutationCode("111");
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
        individual.setAddresses(new Address[]{address});
        customerBody.setIndividuals(new Individual[]{individual});
        mockCustomerInfoResponse.setBody(customerBody);
        mockCustomerInfoResponse.setHeader(customerHeader);

        ResponseDropdown mockResponse = new ResponseDropdown();
        Body body = new Body();
        CommonCodeEntry item1 = new CommonCodeEntry();
        item1.setEntryCode("M");
        item1.setEntryName("Mortgages");
        item1.setEntryName2("อยู่ระหว่างผ่อนชำระ");
        item1.setEntrySource("HOST");
        item1.setEntryID(BigDecimal.ONE);
        CommonCodeEntry[] entries = new CommonCodeEntry[]{item1};
        body.setCommonCodeEntries(entries);
        mockResponse.setBody(body);
        Header header = new Header();
        header.setResponseCode("MSG_000");
        mockResponse.setHeader(header);

        TmbOneServiceResponse oneServiceResponse = new TmbOneServiceResponse();
        TmbStatus tmbStatus = new TmbStatus();
        tmbStatus.setCode(ResponseCode.SUCCESS.getCode());
        CustGeneralProfileResponse custGeneralProfileResponse = new CustGeneralProfileResponse();
        custGeneralProfileResponse.setCitizenId("122");
        custGeneralProfileResponse.setCurrentAddrdistrictNameTh("ปทุมวัน");
        custGeneralProfileResponse.setCurrentAddrFloorNo("6");
        custGeneralProfileResponse.setEmailAddress("40654@tmbbank.com");
        custGeneralProfileResponse.setIdBirthDate("2019-11-33");
        custGeneralProfileResponse.setEngFname("ONEAPPFOUR");
        custGeneralProfileResponse.setEngLname("NA TEETEEBEE");
        custGeneralProfileResponse.setThaFname("วันแอพสี่");
        custGeneralProfileResponse.setThaLname("ทีทีบี");
        custGeneralProfileResponse.setNationality("ทีทีบี");
        custGeneralProfileResponse.setIdExpireDate("2019-11-33");
        custGeneralProfileResponse.setPhoneNoFull("0891117777");
        custGeneralProfileResponse.setCurrentAddrVillageOrbuilding("cv");
        custGeneralProfileResponse.setCurrentAddrMoo("1");
        custGeneralProfileResponse.setCurrentAddrProvinceNameTh("dm,");
        custGeneralProfileResponse.setCurrentAddrStreet("ลาดพร้าว");
        custGeneralProfileResponse.setWorkAddrZipcode("10240");
        custGeneralProfileResponse.setWorkAddrSubDistrictNameTh("แขงวังทองหลาง");
        oneServiceResponse.setData(custGeneralProfileResponse);
        oneServiceResponse.setStatus(tmbStatus);


        when(customerInfoClient.searchCustomerInfoByCaID(anyLong())).thenReturn(mockCustomerInfoResponse);
        when(customerServiceClient.getCustomers(any())).thenReturn(ResponseEntity.ok(oneServiceResponse));
        doReturn(new ResponseDropdown()).when(dropdownService).getDropdown(anyString());

        PersonalDetailResponse response = personalDetailService.getPersonalDetail("001100000000000000000018593707",request.getCaId());
        Assertions.assertNotNull(response);

    }

}
