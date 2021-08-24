

package com.tmb.oneapp.lendingservice.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.tmb.common.model.legacy.rsl.ws.instant.application.create.response.ResponseInstantLoanCreateApplication;
import com.tmb.common.model.loan.InstantLoanCreationRequest;
import com.tmb.oneapp.lendingservice.client.FTPClient;
import com.tmb.oneapp.lendingservice.client.InstantLoanCreateApplicationClient;
import com.tmb.oneapp.lendingservice.model.ServiceResponse;
import com.tmb.oneapp.lendingservice.model.instantloancreation.InstantLoanCreationResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import javax.xml.rpc.ServiceException;
import java.io.IOException;
import java.rmi.RemoteException;
import java.util.concurrent.TimeUnit;

import static org.awaitility.Awaitility.await;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@RunWith(JUnit4.class)
public class InstantLoanCreateApplicationServiceTest {

    @Mock
    InstantLoanCreateApplicationClient Client;

    @Mock
    FTPClient ftpClient;
    ObjectMapper mapper;
    InstantLoanCreateApplicationService createApplicationService;
    InstantLoanCreationRequest ccRequest;
    InstantLoanCreationRequest flashCardRequest;
    ResponseInstantLoanCreateApplication soapResponse;
    @Mock
    ImageGeneratorService imageGeneratorService;

    private String crmId = "123";

    @BeforeEach
    void setUp() throws JsonProcessingException {
        MockitoAnnotations.initMocks(this);
        mapper = new ObjectMapper();
        createApplicationService = new InstantLoanCreateApplicationService(mapper, Client, imageGeneratorService, ftpClient);
        String requestCC = "{\"loanType\":\"CC\",\"getMore\":\"Y\",\"requestID\":\"aaaa\",\"channel\":\"MIB\",\"module\":\"3\",\"transactionType\":\"INST\",\"natureOfRequest\":\"03\",\"appType\":\"CC\",\"branchCode\":\"026\",\"bookBranchCode\":\"026\",\"saleChannel\":\"05\",\"authenCode\":\"Access Pin\",\"ncbConsentFlag\":\"Y\",\"addresses\":[{\"addrTypCode\":\"H\",\"address\":\"11\\/222\",\"buildingName\":\"\u0e40\u0e21\u0e37\u0e2d\u0e07\u0e19\u0e19\u0e17\u0e1a\u0e38\u0e23\u0e35\",\"streetName\":\"\",\"postalCode\":\"11000\",\"country\":\"TH\",\"tumbol\":\"\",\"road\":\"\u0e07\u0e32\u0e21\u0e27\u0e07\u0e28\u0e4c\u0e27\u0e32\u0e19\",\"moo\":\"\",\"amphur\":\"\u0e0b\u0e2d\u0e22 \u0e08\u0e38\u0e2c\u0e32\",\"province\":\"\",\"floor\":\"\"},{\"addrTypCode\":\"O\",\"address\":\"3000\",\"buildingName\":\"\u0e40\u0e21\u0e37\u0e2d\u0e07\u0e19\u0e19\u0e17\u0e1a\u0e38\u0e23\u0e35\",\"streetName\":\"\",\"postalCode\":\"10900\",\"country\":\"TH\",\"tumbol\":\"\",\"road\":\"\u0e07\u0e32\u0e21\u0e27\u0e07\u0e28\u0e4c\u0e27\u0e32\u0e19\",\"moo\":\"\",\"amphur\":\"\u0e0b\u0e2d\u0e22 \u0e08\u0e38\u0e2c\u0e32\",\"province\":\"\",\"floor\":\"\"}],\"creditCards\":[{\"cardInd\":\"P\",\"productType\":\"VM\",\"cardBrand\":\"1\",\"campaignCode\":\"BB32\",\"requestCreditLimit\":\"1500000\",\"paymentMethod\":\"0\",\"paymentCriteria\":\"2\",\"debitAccountName\":\"\",\"debitAccountNo\":\"\",\"mailPreference\":\"H\",\"cardDelivery\":\"H\"}],\"flashCardOrC2G\":[],\"customerInfo\":{\"cifRelCode\":\"M\",\"idType1\":\"CI\",\"idNo1\":\"1846622310794\",\"hostCifNo\":\"00000018593706\",\"email\":\"40654@TMBBANKCOM\",\"emailStatementFlag\":\"Y\",\"thaiName\":\"\u0e27\u0e31\u0e19\u0e41\u0e2d\u0e1e\u0e2a\u0e32\u0e21\",\"thaiSurName\":\"\u0e40\u0e1f\u0e25\u0e01\u0e0b\u0e35\u0e48\u0e42\u0e25\u0e19 \u0e13 \u0e17\u0e35\u0e17\u0e35\u0e1a\u0e35\",\"birthDate\":\"1986-08-26T00:00:00.000Z\",\"mobileNo\":\"0953626563\",\"ncbConsentFlag\":\"Y\",\"discloseCustInfoFlag\":\"Y\",\"nameLine2\":\"FLEXILOAN NA TEETEEBEE\",\"nameLine1\":\"ONEAPPTHREE\",\"issuedDate\":\"2014-12-18T00:00:00.000Z\",\"nationality\":\"TH\",\"rmOccupation\":\"302\",\"professionalCode\":\"13\",\"businessType\":\"DK00\",\"businessSubType\":\"\",\"incomeType\":\"8\",\"sourceFromCountry\":\"TH\",\"employmentName\":\"TMB BANK PUBLIC COMPANY LIMITED\",\"employmentStatus\":\"01\",\"employmentOccupation\":\"04\",\"employmentTelephoneDirectNo\":\"022991000\",\"employmentTelephoneExtNo\":\"1094\",\"incomeDeclared\":\"\",\"incomeBasicSalary\":\"500000\"}}";
        String requestFlashCard = "{\"loanType\":\"F\",\"getMore\":\"Y\",\"requestID\":\"aaaa\",\"channel\":\"MIB\",\"module\":\"3\",\"transactionType\":\"INST\",\"natureOfRequest\":\"03\",\"appType\":\"PL\",\"branchCode\":\"026\",\"bookBranchCode\":\"026\",\"saleChannel\":\"05\",\"authenCode\":\"Access Pin\",\"ncbConsentFlag\":\"Y\",\"addresses\":[{\"addrTypCode\":\"H\",\"address\":\"11\\/222\",\"buildingName\":\"\u0e40\u0e21\u0e37\u0e2d\u0e07\u0e19\u0e19\u0e17\u0e1a\u0e38\u0e23\u0e35\",\"streetName\":\"\",\"postalCode\":\"11000\",\"country\":\"TH\",\"tumbol\":\"\",\"road\":\"\u0e07\u0e32\u0e21\u0e27\u0e07\u0e28\u0e4c\u0e27\u0e32\u0e19\",\"moo\":\"\",\"amphur\":\"\u0e0b\u0e2d\u0e22 \u0e08\u0e38\u0e2c\u0e32\",\"province\":\"\",\"floor\":\"\"},{\"addrTypCode\":\"O\",\"address\":\"3000\",\"buildingName\":\"\u0e40\u0e21\u0e37\u0e2d\u0e07\u0e19\u0e19\u0e17\u0e1a\u0e38\u0e23\u0e35\",\"streetName\":\"\",\"postalCode\":\"10900\",\"country\":\"TH\",\"tumbol\":\"\",\"road\":\"\u0e07\u0e32\u0e21\u0e27\u0e07\u0e28\u0e4c\u0e27\u0e32\u0e19\",\"moo\":\"\",\"amphur\":\"\u0e0b\u0e2d\u0e22 \u0e08\u0e38\u0e2c\u0e32\",\"province\":\"\",\"floor\":\"\"}],\"creditCards\":[],\"flashCardOrC2G\":[{\"facilityCode\":\"C2G\",\"productCode\":\"C2G01\",\"caCampaignCode\":\"DL02\",\"limitApplied\":\"700000\",\"monthlyInstallment\":\"2000\",\"tenure\":\"36\",\"interestRate\":\"0.28\",\"paymentDueDate\":\"25\",\"firstPaymentDueDate\":\"25\\/05\\/2021\",\"loanWithOtherBank\":\"1\",\"considerLoanWithOtherBank\":\"1\",\"disburstBankName\":\"011\",\"disburstAccountName\":\"FLEXI ACCT 9190\",\"disburstAccountNo\":\"00001112469190\",\"paymentMethod\":\"0\",\"paymentAccountName\":\"FLEXI ACCT 9190\",\"paymentAccountNo\":\"00001112469190\",\"mailPreference\":\"H\",\"cardDelivery\":\"\",\"paymentCriteria\":\"\"}],\"customerInfo\":{\"cifRelCode\":\"M\",\"idType1\":\"CI\",\"idNo1\":\"1846622310794\",\"hostCifNo\":\"00000018593706\",\"email\":\"40654@TMBBANKCOM\",\"emailStatementFlag\":\"Y\",\"thaiName\":\"\u0e27\u0e31\u0e19\u0e41\u0e2d\u0e1e\u0e2a\u0e32\u0e21\",\"thaiSurName\":\"\u0e40\u0e1f\u0e25\u0e01\u0e0b\u0e35\u0e48\u0e42\u0e25\u0e19 \u0e13 \u0e17\u0e35\u0e17\u0e35\u0e1a\u0e35\",\"birthDate\":\"1986-08-26T00:00:00.000Z\",\"mobileNo\":\"0953626563\",\"ncbConsentFlag\":\"Y\",\"discloseCustInfoFlag\":\"Y\",\"nameLine2\":\"FLEXILOAN NA TEETEEBEE\",\"nameLine1\":\"ONEAPPTHREE\",\"issuedDate\":\"2014-12-18T00:00:00.000Z\",\"nationality\":\"TH\",\"rmOccupation\":\"302\",\"professionalCode\":\"13\",\"businessType\":\"DK00\",\"businessSubType\":\"\",\"incomeType\":\"8\",\"sourceFromCountry\":\"TH\",\"employmentName\":\"TMB BANK PUBLIC COMPANY LIMITED\",\"employmentStatus\":\"01\",\"employmentOccupation\":\"04\",\"employmentTelephoneDirectNo\":\"022991000\",\"employmentTelephoneExtNo\":\"1094\",\"incomeDeclared\":\"\",\"incomeBasicSalary\":\"500000\"}}";
        String response = "{\"body\":{\"appRefNo\":\"026CC64000357\",\"appType\":\"CC\",\"caId\":2021051604186543,\"ccId\":2021051601992713,\"cifId\":2021051604267309,\"collId\":null,\"createDate\":\"2021-05-16T11:02:32.000Z\",\"currentWorkflow\":\"INST\",\"facId\":null,\"memberref\":\"MIB026CC64000357\",\"product\":\"VM\",\"productDescEN\":\"ttb so smart credit card\",\"productDescTH\":\"\u0e1a\u0e31\u0e15\u0e23\u0e40\u0e04\u0e23\u0e14\u0e34\u0e15 \u0e17\u0e35\u0e17\u0e35\u0e1a\u0e35 \u0e42\u0e0b \u0e2a\u0e21\u0e32\u0e23\u0e4c\u0e17\",\"regAddressId\":2021051615914652,\"resAddressId\":2021051615914653,\"subProduct\":null,\"subProductDescEN\":null,\"subProductDescTH\":null,\"workAddressId\":2021051615914654},\"header\":{\"channel\":\"MIB\",\"module\":\"3\",\"requestID\":\"aaaa\",\"responseCode\":\"MSG_000\",\"responseDescriptionEN\":\"Success\",\"responseDescriptionTH\":null}}";
        ccRequest = mapper.readValue(requestCC, InstantLoanCreationRequest.class);
        flashCardRequest = mapper.readValue(requestFlashCard, InstantLoanCreationRequest.class);
        soapResponse = mapper.readValue(response, ResponseInstantLoanCreateApplication.class);
    }

    @Test
    void InstantLoanCreationForRemoteException() throws RemoteException, ServiceException, JsonProcessingException {

        when(Client.callLoanSubmissionInstantLoanCreateApplication(any())).thenThrow(RemoteException.class);
        ServiceResponse actualResponse = createApplicationService.createInstantLoanApplication(crmId, ccRequest);
        assertNotNull(actualResponse.getError());


    }

    @Test
    void InstantLoanCreationForServiceException() throws RemoteException, ServiceException, JsonProcessingException {

        when(Client.callLoanSubmissionInstantLoanCreateApplication(any())).thenThrow(ServiceException.class);
        ServiceResponse actualResponse = createApplicationService.createInstantLoanApplication(crmId, ccRequest);
        assertNotNull(actualResponse.getError());


    }


    @Test
    void InstantLoanCreationForJsonProcessingException() throws RemoteException, ServiceException, JsonProcessingException {

        when(Client.callLoanSubmissionInstantLoanCreateApplication(any())).thenThrow(JsonProcessingException.class);
        ServiceResponse actualResponse = createApplicationService.createInstantLoanApplication(crmId, ccRequest);
        assertNotNull(actualResponse.getError());


    }

    @Test
    void InstantLoanCreationForCC() throws RemoteException, ServiceException, JsonProcessingException {

        when(Client.callLoanSubmissionInstantLoanCreateApplication(any())).thenReturn(soapResponse);
        ServiceResponse actualResponse = createApplicationService.createInstantLoanApplication(crmId, ccRequest);
        InstantLoanCreationResponse data = (InstantLoanCreationResponse) actualResponse.getData();
        assertEquals(soapResponse.getBody().getMemberref(), data.getMemberRef());


    }

    @Test
    void InstantLoanCreationForFlashCard() throws RemoteException, ServiceException, JsonProcessingException {

        when(Client.callLoanSubmissionInstantLoanCreateApplication(any())).thenReturn(soapResponse);
        ServiceResponse actualResponse = createApplicationService.createInstantLoanApplication(crmId, flashCardRequest);
        InstantLoanCreationResponse data = (InstantLoanCreationResponse) actualResponse.getData();
        assertEquals(soapResponse.getBody().getMemberref(), data.getMemberRef());


    }

    @Test
    void InstantLoanCreationForC2G() throws RemoteException, ServiceException, JsonProcessingException {

        flashCardRequest.setLoanType("C2G");
        when(Client.callLoanSubmissionInstantLoanCreateApplication(any())).thenReturn(soapResponse);
        ServiceResponse actualResponse = createApplicationService.createInstantLoanApplication(crmId, flashCardRequest);
        InstantLoanCreationResponse data = (InstantLoanCreationResponse) actualResponse.getData();
        assertEquals(soapResponse.getBody().getMemberref(), data.getMemberRef());
    }

}
