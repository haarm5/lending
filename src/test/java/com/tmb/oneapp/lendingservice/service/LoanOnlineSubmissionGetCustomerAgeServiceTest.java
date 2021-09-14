package com.tmb.oneapp.lendingservice.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.tmb.common.exception.model.TMBCommonException;
import com.tmb.common.model.CustGeneralProfileResponse;
import com.tmb.common.model.TmbOneServiceResponse;
import com.tmb.common.model.TmbStatus;
import com.tmb.oneapp.lendingservice.client.CustomerServiceClient;
import com.tmb.oneapp.lendingservice.constant.ResponseCode;
import com.tmb.oneapp.lendingservice.model.rsl.LoanSubmissionGetCustomerAgeResponse;
import org.junit.Assert;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;

import javax.xml.rpc.ServiceException;
import java.text.ParseException;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;


@RunWith(JUnit4.class)
class LoanOnlineSubmissionGetCustomerAgeServiceTest {

    @Mock
    private CustomerServiceClient customerServiceClient;

    @InjectMocks
    private LoanOnlineSubmissionGetCustomerAgeService loanOnlineSubmissionGetCustomerAgeService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
    }


    @Test
    public void testGetAgeReturnObject() throws TMBCommonException {
        TmbOneServiceResponse oneServiceResponse = new TmbOneServiceResponse();
        TmbStatus tmbStatus = new TmbStatus();
        tmbStatus.setCode(ResponseCode.SUCCESS.getCode());
        CustGeneralProfileResponse custGeneralProfileResponse = new CustGeneralProfileResponse();
        custGeneralProfileResponse.setIdBirthDate("1960-12-24");
        custGeneralProfileResponse.setIdExpireDate("1960-12-24");
        custGeneralProfileResponse.setIdType("PP");
        oneServiceResponse.setData(custGeneralProfileResponse);
        oneServiceResponse.setStatus(tmbStatus);
        when(customerServiceClient.getCustomers(any())).thenReturn(ResponseEntity.ok(oneServiceResponse));

        LoanSubmissionGetCustomerAgeResponse result = loanOnlineSubmissionGetCustomerAgeService.getAge("123");
        Assert.assertNotNull(result);
    }

    @Test
    public void testGetAgeReturnNull() throws TMBCommonException {
        TmbOneServiceResponse oneServiceResponse = new TmbOneServiceResponse();
        TmbStatus tmbStatus = new TmbStatus();
        tmbStatus.setCode(ResponseCode.SUCCESS.getCode());
        CustGeneralProfileResponse custGeneralProfileResponse = new CustGeneralProfileResponse();
        oneServiceResponse.setData(custGeneralProfileResponse);
        oneServiceResponse.setStatus(tmbStatus);
        when(customerServiceClient.getCustomers(any())).thenReturn(ResponseEntity.ok(oneServiceResponse));

        LoanSubmissionGetCustomerAgeResponse result = loanOnlineSubmissionGetCustomerAgeService.getAge("123");
        Assert.assertNull(result);
    }

    @Test
    public void testGetAgeReturnFail() {
        TmbOneServiceResponse oneServiceResponse = new TmbOneServiceResponse();
        TmbStatus tmbStatus = new TmbStatus();
        tmbStatus.setCode(ResponseCode.FAILED.getCode());
        CustGeneralProfileResponse custGeneralProfileResponse = new CustGeneralProfileResponse();
        oneServiceResponse.setData(custGeneralProfileResponse);
        oneServiceResponse.setStatus(tmbStatus);
        when(customerServiceClient.getCustomers(any())).thenReturn(ResponseEntity.ok(oneServiceResponse));
        assertThrows(Exception.class, () ->
                loanOnlineSubmissionGetCustomerAgeService.getAge(any()));
    }

}