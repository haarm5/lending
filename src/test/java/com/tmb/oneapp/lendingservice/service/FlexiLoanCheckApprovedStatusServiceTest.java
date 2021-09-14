package com.tmb.oneapp.lendingservice.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.tmb.common.exception.model.TMBCommonException;
import com.tmb.common.model.legacy.rsl.common.ob.apprmemo.creditcard.ApprovalMemoCreditCard;
import com.tmb.common.model.legacy.rsl.common.ob.apprmemo.facility.ApprovalMemoFacility;
import com.tmb.common.model.legacy.rsl.common.ob.facility.Facility;
import com.tmb.common.model.legacy.rsl.common.ob.feature.Feature;
import com.tmb.common.model.legacy.rsl.common.ob.pricing.Pricing;
import com.tmb.common.model.legacy.rsl.ws.facility.response.Header;
import com.tmb.common.model.legacy.rsl.ws.facility.response.ResponseFacility;
import com.tmb.common.model.legacy.rsl.ws.instant.calculate.uw.request.Body;
import com.tmb.common.model.legacy.rsl.ws.instant.calculate.uw.request.RequestInstantLoanCalUW;
import com.tmb.common.model.legacy.rsl.ws.instant.calculate.uw.response.ResponseInstantLoanCalUW;
import com.tmb.oneapp.lendingservice.client.LoanSubmissionGetCreditcardInfoClient;
import com.tmb.oneapp.lendingservice.client.LoanSubmissionGetFacilityInfoClient;
import com.tmb.oneapp.lendingservice.client.LoanSubmissionInstantLoanCalUWClient;
import com.tmb.oneapp.lendingservice.model.flexiloan.InstantLoanCalUWRequest;
import com.tmb.oneapp.lendingservice.model.flexiloan.InstantLoanCalUWResponse;
import com.tmb.oneapp.lendingservice.model.flexiloan.LoanCustomerPricing;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import javax.xml.rpc.ServiceException;
import java.math.BigDecimal;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;

@RunWith(JUnit4.class)
public class FlexiLoanCheckApprovedStatusServiceTest {

    @Mock
    private LoanSubmissionInstantLoanCalUWClient loanCalUWClient;
    @Mock
    private LoanSubmissionGetFacilityInfoClient getFacilityInfoClient;
    @Mock
    private LoanSubmissionGetCreditcardInfoClient getCreditcardInfoClient;

    FlexiLoanCheckApprovedStatusService flexiLoanCheckApprovedStatusService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
        flexiLoanCheckApprovedStatusService = new FlexiLoanCheckApprovedStatusService(loanCalUWClient, getFacilityInfoClient, getCreditcardInfoClient);
    }

    @Test
    public void testCheckCalculateUnderwritingApprove() throws ServiceException, RemoteException, TMBCommonException, JsonProcessingException {
        RequestInstantLoanCalUW request = new RequestInstantLoanCalUW();

        Body body = new Body();
        body.setTriggerFlag("Y");
        body.setCaId(BigDecimal.valueOf(2021090204189030L));
        request.setBody(body);

        when(loanCalUWClient.calculateUnderwriting(any(), any())).thenReturn(mockCalUW());

        InstantLoanCalUWRequest calUWReq = new InstantLoanCalUWRequest();
        calUWReq.setProduct("RC01");
        calUWReq.setTriggerFlag("Y");
        calUWReq.setLoanDay1Set("Y");
        calUWReq.setCaId(BigDecimal.valueOf(2021090204189030L));

        when(getFacilityInfoClient.searchFacilityInfoByCaID(anyLong())).thenReturn(mockFacility());

        InstantLoanCalUWResponse actualResult = flexiLoanCheckApprovedStatusService.checkCalculateUnderwriting(calUWReq);

        Assertions.assertNotNull(actualResult);

    }

    @Test
    public void testCheckCalculateUnderwritingApproveC2G() throws ServiceException, TMBCommonException, JsonProcessingException {
        RequestInstantLoanCalUW request = new RequestInstantLoanCalUW();

        Body body = new Body();
        body.setTriggerFlag("Y");
        body.setCaId(BigDecimal.valueOf(2021090204189030L));
        request.setBody(body);

        when(loanCalUWClient.calculateUnderwriting(any(), any())).thenReturn(mockCalUW());

        InstantLoanCalUWRequest calUWReq = new InstantLoanCalUWRequest();
        calUWReq.setProduct("C2G02");
        calUWReq.setTriggerFlag("Y");
        calUWReq.setLoanDay1Set("Y");
        calUWReq.setCaId(BigDecimal.valueOf(2021090204189030L));

        when(getFacilityInfoClient.searchFacilityInfoByCaID(anyLong())).thenReturn(mockFacility());

        InstantLoanCalUWResponse actualResult = flexiLoanCheckApprovedStatusService.checkCalculateUnderwriting(calUWReq);

        Assertions.assertNotNull(actualResult);

    }

    private ResponseInstantLoanCalUW mockCalUW() {
        ResponseInstantLoanCalUW response = new ResponseInstantLoanCalUW();
        ApprovalMemoCreditCard[] approvalMemoCreditCardList = new ApprovalMemoCreditCard[10];
        ApprovalMemoCreditCard approvalMemoCreditCard = new ApprovalMemoCreditCard();
        approvalMemoCreditCard.setCardType("T");
        approvalMemoCreditCard.setCreditLimit(BigDecimal.ONE);
        approvalMemoCreditCard.setUnderwritingResult("APPROVE");
        approvalMemoCreditCard.setCcId(BigDecimal.TEN);
        approvalMemoCreditCard.setCycleCutDate("20/05/2021");
        approvalMemoCreditCard.setDebitAccountName("ttb");
        approvalMemoCreditCard.setDebitAccountNo("111");
        approvalMemoCreditCard.setFirstPaymentDueDate("21/05/21");
        approvalMemoCreditCard.setId(BigDecimal.ONE);
        approvalMemoCreditCard.setPaymentMethod("f");
        approvalMemoCreditCard.setPayDate("22/05/21");
        approvalMemoCreditCardList[0] = approvalMemoCreditCard;

        ApprovalMemoFacility[] approvalMemoFacilities = new ApprovalMemoFacility[5];
        ApprovalMemoFacility approvalMemoFacility = new ApprovalMemoFacility();
        approvalMemoFacility.setTenor(BigDecimal.TEN);
        approvalMemoFacility.setCreditLimit(BigDecimal.ONE);
        approvalMemoFacility.setCycleCutDate("11/11/11");
        approvalMemoFacility.setId(BigDecimal.ONE);
        approvalMemoFacility.setUnderwritingResult("APPROVE");
        approvalMemoFacility.setDisburstAccountName("ttb");
        approvalMemoFacility.setDisburstAccountNo("11");
        approvalMemoFacility.setFacId(BigDecimal.TEN);
        approvalMemoFacility.setTenor(BigDecimal.TEN);
        approvalMemoFacility.setPayDate("25");
        approvalMemoFacility.setFirstPaymentDueDate("11/11/11");
        approvalMemoFacility.setLoanContractDate(Calendar.getInstance());
        approvalMemoFacility.setInstallmentAmount(BigDecimal.TEN);
        approvalMemoFacility.setInterestRate(BigDecimal.TEN);
        approvalMemoFacility.setOutstandingBalance(BigDecimal.TEN);
        approvalMemoFacility.setRateTypePercent(BigDecimal.TEN);
        approvalMemoFacility.setRateType("Y");
        approvalMemoFacility.setDisburstAccountName("ttb");
        approvalMemoFacility.setDisburstAccountNo("11");
        approvalMemoFacility.setOutstandingBalance(BigDecimal.TEN);
        approvalMemoFacilities[0] = approvalMemoFacility;

        com.tmb.common.model.legacy.rsl.ws.instant.calculate.uw.response.Body responseBody = new com.tmb.common.model.legacy.rsl.ws.instant.calculate.uw.response.Body();
        com.tmb.common.model.legacy.rsl.ws.instant.calculate.uw.response.Header header = new com.tmb.common.model.legacy.rsl.ws.instant.calculate.uw.response.Header();
        header.setResponseCode("MSG_000");
        responseBody.setUnderwritingResult("APPROVE");
        responseBody.setApprovalMemoCreditCards(approvalMemoCreditCardList);
        responseBody.setApprovalMemoFacilities(approvalMemoFacilities);
        response.setBody(responseBody);
        response.setHeader(header);
        return response;
    }

    private ResponseFacility mockFacility() {
        ResponseFacility responseFacility = new ResponseFacility();
        Facility f = new Facility();
        Feature feature = new Feature();
        com.tmb.common.model.legacy.rsl.ws.facility.response.Body body = new com.tmb.common.model.legacy.rsl.ws.facility.response.Body();
        Header header = new Header();
        f.setCardDelivery("H");
        f.setCaId(BigDecimal.TEN);
        f.setFeatureType("S");
        f.setCaCampaignCode("U");
        f.setAmountFinance(BigDecimal.TEN);
        f.setDisburstBankName("ttb");
        f.setDisburstAccountName("ttb");
        f.setDisburstAccountNo("111");
        f.setOutStandingBalance(BigDecimal.TEN);
        f.setConsiderLoanWithOtherBank("bkk");
        f.setCreditLimitFromMof(BigDecimal.TEN);
        f.setExistingAccountNo("111");
        f.setExistingCreditLimit(BigDecimal.TEN);
        f.setExistLoan("aaa");
        f.setPricings(mockPricing());
        feature.setRequestAmount(BigDecimal.TEN);
        f.setFeature(feature);

        Facility[] facilitys = new Facility[1];
        facilitys[0] = f;
        body.setFacilities(facilitys);
        header.setResponseCode("MSG_000");
        responseFacility.setBody(body);
        responseFacility.setHeader(header);
        return responseFacility;
    }

    private Pricing[] mockPricing() {
        InstantLoanCalUWResponse instantLoanCalUWResponse = new InstantLoanCalUWResponse();
        Pricing[] pricings = new Pricing[1];
        Pricing p = new Pricing();
        p.setMonthFrom(BigDecimal.ONE);
        p.setMonthTo(BigDecimal.ONE);
        p.setYearFrom(BigDecimal.ONE);
        p.setYearTo(BigDecimal.ONE);
        p.setPercentSign("S");
        p.setPricingType("S");
        p.setRateType("S");
        p.setRateVaraince(BigDecimal.ONE);
        pricings[0] = p;

        List<LoanCustomerPricing> pricingList = new ArrayList<>();
        pricings[0].setMonthTo(BigDecimal.ONE);
        pricings[0].setMonthFrom(BigDecimal.ONE);
        pricings[0].setYearFrom(BigDecimal.ONE);
        pricings[0].setYearTo(BigDecimal.ONE);
        pricings[0].setRateVaraince(BigDecimal.ONE);

        LoanCustomerPricing pricing = new LoanCustomerPricing();
        pricing.setMonthTo(BigDecimal.ONE);
        pricing.setMonthFrom(BigDecimal.ONE);
        pricing.setRateVariance(BigDecimal.ONE);
        pricing.setYearFrom(BigDecimal.ONE);
        pricing.setYearTo(BigDecimal.ONE);
        pricing.setRateVariance(BigDecimal.ONE);
        pricing.setRate("1");
        pricingList.add(pricing);
        instantLoanCalUWResponse.setRequestAmount(BigDecimal.TEN);
        instantLoanCalUWResponse.setPricings(pricingList);
        return pricings;
    }
}
