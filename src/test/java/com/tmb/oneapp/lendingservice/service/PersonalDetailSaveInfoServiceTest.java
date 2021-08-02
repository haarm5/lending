package com.tmb.oneapp.lendingservice.service;

import com.tmb.common.model.legacy.rsl.common.ob.address.Address;
import com.tmb.common.model.legacy.rsl.common.ob.individual.Individual;
import com.tmb.common.model.legacy.rsl.ws.individual.response.ResponseIndividual;
import com.tmb.oneapp.lendingservice.client.LoanSubmissionGetCustomerInfoClient;
import com.tmb.oneapp.lendingservice.client.LoanSubmissionUpdateCustomerClient;
import org.junit.jupiter.api.BeforeEach;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import javax.xml.rpc.ServiceException;
import java.math.BigDecimal;
import java.rmi.RemoteException;
import java.util.Calendar;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;

@RunWith(JUnit4.class)
public class PersonalDetailSaveInfoServiceTest {
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

    public void testSaveCustomerSuccess() throws ServiceException, RemoteException {
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


        when(customerInfoClient.searchCustomerInfoByCaID(anyLong())).thenReturn(mockCustomerInfoResponse);

//        when(updateCustomerClient.updateCustomerInfo(any())).thenReturn(new ResponseIndividual());


    }
}