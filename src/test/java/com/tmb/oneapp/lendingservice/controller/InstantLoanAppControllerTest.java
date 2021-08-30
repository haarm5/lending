package com.tmb.oneapp.lendingservice.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.tmb.common.exception.model.TMBCommonException;
import com.tmb.common.model.TmbOneServiceResponse;
import com.tmb.common.model.loan.CreditCardLoanInfo;
import com.tmb.common.model.loan.InstantLoanCreationRequest;
import com.tmb.oneapp.lendingservice.constant.LendingServiceConstant;
import com.tmb.oneapp.lendingservice.model.ServiceError;
import com.tmb.oneapp.lendingservice.model.ServiceResponseImp;
import com.tmb.oneapp.lendingservice.model.instantloancreation.InstantLoanCreationResponse;
import com.tmb.oneapp.lendingservice.service.InstantLoanCreateApplicationService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@RunWith(JUnit4.class)
public class InstantLoanAppControllerTest {

    ObjectMapper mapper;
    InstantLoanAppController instantLoanCreateApplicationController;
    @Mock
    InstantLoanCreateApplicationService createLoanApplicationService;

    InstantLoanCreationRequest ccRequest;
    InstantLoanCreationRequest flashCardRequest;
    InstantLoanCreationResponse expectedResponse;
    private String crmId = "123";

    Map<String, String> reqHeaders;

    @BeforeEach
    void setUp() throws JsonProcessingException {
        MockitoAnnotations.initMocks(this);
        mapper = new ObjectMapper();
        instantLoanCreateApplicationController = new InstantLoanAppController(createLoanApplicationService);
        String requestCC = "{\"loanType\":\"CC\",\"requestID\":\"aaaa\",\"channel\":\"MIB\",\"module\":\"3\",\"transactionType\":\"INST\",\"natureOfRequest\":\"03\",\"appType\":\"CC\",\"branchCode\":\"026\",\"bookBranchCode\":\"026\",\"saleChannel\":\"05\",\"authenCode\":\"Access Pin\",\"ncbConsentFlag\":\"Y\",\"addresses\":[{\"addrTypCode\":\"H\",\"address\":\"11\\/222\",\"buildingName\":\"\u0e40\u0e21\u0e37\u0e2d\u0e07\u0e19\u0e19\u0e17\u0e1a\u0e38\u0e23\u0e35\",\"streetName\":\"\",\"postalCode\":\"11000\",\"country\":\"TH\",\"tumbol\":\"\",\"road\":\"\u0e07\u0e32\u0e21\u0e27\u0e07\u0e28\u0e4c\u0e27\u0e32\u0e19\",\"moo\":\"\",\"amphur\":\"\u0e0b\u0e2d\u0e22 \u0e08\u0e38\u0e2c\u0e32\",\"province\":\"\",\"floor\":\"\"},{\"addrTypCode\":\"O\",\"address\":\"3000\",\"buildingName\":\"\u0e40\u0e21\u0e37\u0e2d\u0e07\u0e19\u0e19\u0e17\u0e1a\u0e38\u0e23\u0e35\",\"streetName\":\"\",\"postalCode\":\"10900\",\"country\":\"TH\",\"tumbol\":\"\",\"road\":\"\u0e07\u0e32\u0e21\u0e27\u0e07\u0e28\u0e4c\u0e27\u0e32\u0e19\",\"moo\":\"\",\"amphur\":\"\u0e0b\u0e2d\u0e22 \u0e08\u0e38\u0e2c\u0e32\",\"province\":\"\",\"floor\":\"\"}],\"creditCards\":[{\"cardInd\":\"P\",\"productType\":\"VM\",\"cardBrand\":\"1\",\"campaignCode\":\"BB32\",\"requestCreditLimit\":\"1500000\",\"paymentMethod\":\"0\",\"paymentCriteria\":\"2\",\"debitAccountName\":\"\",\"debitAccountNo\":\"\",\"mailPreference\":\"H\",\"cardDelivery\":\"H\"}],\"flashCardOrC2G\":[],\"customerInfo\":{\"cifRelCode\":\"M\",\"idType1\":\"CI\",\"idNo1\":\"1846622310794\",\"hostCifNo\":\"00000018593706\",\"email\":\"40654@TMBBANKCOM\",\"emailStatementFlag\":\"Y\",\"thaiName\":\"\u0e27\u0e31\u0e19\u0e41\u0e2d\u0e1e\u0e2a\u0e32\u0e21\",\"thaiSurName\":\"\u0e40\u0e1f\u0e25\u0e01\u0e0b\u0e35\u0e48\u0e42\u0e25\u0e19 \u0e13 \u0e17\u0e35\u0e17\u0e35\u0e1a\u0e35\",\"birthDate\":\"1986-08-26T00:00:00.000Z\",\"mobileNo\":\"0953626563\",\"ncbConsentFlag\":\"Y\",\"discloseCustInfoFlag\":\"Y\",\"nameLine2\":\"FLEXILOAN NA TEETEEBEE\",\"nameLine1\":\"ONEAPPTHREE\",\"issuedDate\":\"2014-12-18T00:00:00.000Z\",\"nationality\":\"TH\",\"rmOccupation\":\"302\",\"professionalCode\":\"13\",\"businessType\":\"DK00\",\"businessSubType\":\"\",\"incomeType\":\"8\",\"sourceFromCountry\":\"TH\",\"employmentName\":\"TMB BANK PUBLIC COMPANY LIMITED\",\"employmentStatus\":\"01\",\"employmentOccupation\":\"04\",\"employmentTelephoneDirectNo\":\"022991000\",\"employmentTelephoneExtNo\":\"1094\",\"incomeDeclared\":\"\",\"incomeBasicSalary\":\"500000\"}}";
        String requestFlashCard = "{\"loanType\":\"F\",\"requestID\":\"aaaa\",\"channel\":\"MIB\",\"module\":\"3\",\"transactionType\":\"INST\",\"natureOfRequest\":\"03\",\"appType\":\"PL\",\"branchCode\":\"026\",\"bookBranchCode\":\"026\",\"saleChannel\":\"05\",\"authenCode\":\"Access Pin\",\"ncbConsentFlag\":\"Y\",\"addresses\":[{\"addrTypCode\":\"H\",\"address\":\"11\\/222\",\"buildingName\":\"\u0e40\u0e21\u0e37\u0e2d\u0e07\u0e19\u0e19\u0e17\u0e1a\u0e38\u0e23\u0e35\",\"streetName\":\"\",\"postalCode\":\"11000\",\"country\":\"TH\",\"tumbol\":\"\",\"road\":\"\u0e07\u0e32\u0e21\u0e27\u0e07\u0e28\u0e4c\u0e27\u0e32\u0e19\",\"moo\":\"\",\"amphur\":\"\u0e0b\u0e2d\u0e22 \u0e08\u0e38\u0e2c\u0e32\",\"province\":\"\",\"floor\":\"\"},{\"addrTypCode\":\"O\",\"address\":\"3000\",\"buildingName\":\"\u0e40\u0e21\u0e37\u0e2d\u0e07\u0e19\u0e19\u0e17\u0e1a\u0e38\u0e23\u0e35\",\"streetName\":\"\",\"postalCode\":\"10900\",\"country\":\"TH\",\"tumbol\":\"\",\"road\":\"\u0e07\u0e32\u0e21\u0e27\u0e07\u0e28\u0e4c\u0e27\u0e32\u0e19\",\"moo\":\"\",\"amphur\":\"\u0e0b\u0e2d\u0e22 \u0e08\u0e38\u0e2c\u0e32\",\"province\":\"\",\"floor\":\"\"}],\"creditCards\":[],\"flashCardOrC2G\":[{\"facilityCode\":\"C2G\",\"productCode\":\"C2G01\",\"caCampaignCode\":\"DL02\",\"limitApplied\":\"700000\",\"monthlyInstallment\":\"2000\",\"tenure\":\"36\",\"interestRate\":\"0.28\",\"paymentDueDate\":\"25\",\"firstPaymentDueDate\":\"25\\/05\\/2021\",\"loanWithOtherBank\":\"1\",\"considerLoanWithOtherBank\":\"1\",\"disburstBankName\":\"011\",\"disburstAccountName\":\"FLEXI ACCT 9190\",\"disburstAccountNo\":\"00001112469190\",\"paymentMethod\":\"0\",\"paymentAccountName\":\"FLEXI ACCT 9190\",\"paymentAccountNo\":\"00001112469190\",\"mailPreference\":\"H\",\"cardDelivery\":\"\",\"paymentCriteria\":\"\"}],\"customerInfo\":{\"cifRelCode\":\"M\",\"idType1\":\"CI\",\"idNo1\":\"1846622310794\",\"hostCifNo\":\"00000018593706\",\"email\":\"40654@TMBBANKCOM\",\"emailStatementFlag\":\"Y\",\"thaiName\":\"\u0e27\u0e31\u0e19\u0e41\u0e2d\u0e1e\u0e2a\u0e32\u0e21\",\"thaiSurName\":\"\u0e40\u0e1f\u0e25\u0e01\u0e0b\u0e35\u0e48\u0e42\u0e25\u0e19 \u0e13 \u0e17\u0e35\u0e17\u0e35\u0e1a\u0e35\",\"birthDate\":\"1986-08-26T00:00:00.000Z\",\"mobileNo\":\"0953626563\",\"ncbConsentFlag\":\"Y\",\"discloseCustInfoFlag\":\"Y\",\"nameLine2\":\"FLEXILOAN NA TEETEEBEE\",\"nameLine1\":\"ONEAPPTHREE\",\"issuedDate\":\"2014-12-18T00:00:00.000Z\",\"nationality\":\"TH\",\"rmOccupation\":\"302\",\"professionalCode\":\"13\",\"businessType\":\"DK00\",\"businessSubType\":\"\",\"incomeType\":\"8\",\"sourceFromCountry\":\"TH\",\"employmentName\":\"TMB BANK PUBLIC COMPANY LIMITED\",\"employmentStatus\":\"01\",\"employmentOccupation\":\"04\",\"employmentTelephoneDirectNo\":\"022991000\",\"employmentTelephoneExtNo\":\"1094\",\"incomeDeclared\":\"\",\"incomeBasicSalary\":\"500000\"}}";
        String response = "{\"createDate\":\"2021-05-16T12:24:57.000Z\",\"currentWorkflow\":\"INST\",\"memberRef\":\"MIB026CC64000359\",\"channel\":\"MIB\",\"module\":\"3\",\"requestId\":\"aaaa\"}";
        ccRequest = mapper.readValue(requestCC, InstantLoanCreationRequest.class);
        flashCardRequest = mapper.readValue(requestFlashCard, InstantLoanCreationRequest.class);
        expectedResponse = mapper.readValue(response, InstantLoanCreationResponse.class);
        reqHeaders = new HashMap<>();
        reqHeaders.put(LendingServiceConstant.HEADER_X_CRMID, crmId);


    }

    @Test
    void createInstantLoanApplicationForCC() throws TMBCommonException {

        ServiceResponseImp serviceResponseImp = new ServiceResponseImp();
        serviceResponseImp.setData(expectedResponse);
        when(createLoanApplicationService.createInstantLoanApplication(crmId, ccRequest)).thenReturn(serviceResponseImp);

        ResponseEntity<TmbOneServiceResponse<Object>> actualResult = instantLoanCreateApplicationController.createInstantLoanApplication(reqHeaders, ccRequest);
        assertEquals(HttpStatus.BAD_REQUEST, actualResult.getStatusCode());
        InstantLoanCreationResponse data = (InstantLoanCreationResponse) actualResult.getBody().getData();

        serviceResponseImp.setError(new ServiceError());
        actualResult = instantLoanCreateApplicationController.createInstantLoanApplication(reqHeaders, ccRequest);
        assertEquals(HttpStatus.BAD_REQUEST, actualResult.getStatusCode());
        assertEquals(null, actualResult.getBody().getData());

    }

    @Test
    void createInstantLoanApplicationForFlashCard() throws TMBCommonException {
        ServiceResponseImp serviceResponseImp = new ServiceResponseImp();
        serviceResponseImp.setData(expectedResponse);
        when(createLoanApplicationService.createInstantLoanApplication(any(), any())).thenReturn(serviceResponseImp);

        ResponseEntity<TmbOneServiceResponse<Object>> actualResult = instantLoanCreateApplicationController.createInstantLoanApplication(reqHeaders, flashCardRequest);
        InstantLoanCreationResponse data = (InstantLoanCreationResponse) actualResult.getBody().getData();
        assertEquals(HttpStatus.BAD_REQUEST, actualResult.getStatusCode());


    }

    @Test
    void createInstantLoanApplicationForInvalidRequest() throws TMBCommonException {
        List<CreditCardLoanInfo> ccInfoList = new ArrayList<>();
        CreditCardLoanInfo ccInfo = new CreditCardLoanInfo();
        ccInfo.setCardInd("");
        ccInfoList.add(ccInfo);
        ccRequest.setCreditCards(ccInfoList);
        
        ServiceResponseImp serviceResponseImp = new ServiceResponseImp();
        serviceResponseImp.setData(expectedResponse);
        when(createLoanApplicationService.createInstantLoanApplication(any(), any())).thenReturn(serviceResponseImp);
        
        ResponseEntity<TmbOneServiceResponse<Object>> actualResult = instantLoanCreateApplicationController.createInstantLoanApplication(reqHeaders, ccRequest);
        assertEquals(HttpStatus.BAD_REQUEST, actualResult.getStatusCode());

    }
}
