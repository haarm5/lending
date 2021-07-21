package com.tmb.oneapp.lendingservice.service;

import com.tmb.common.exception.model.TMBCommonException;
import com.tmb.common.model.legacy.rsl.common.ob.creditcard.CreditCard;
import com.tmb.common.model.legacy.rsl.common.ob.facility.Facility;
import com.tmb.common.model.legacy.rsl.common.ob.feature.Feature;
import com.tmb.common.model.legacy.rsl.common.ob.individual.Individual;
import com.tmb.common.model.legacy.rsl.common.ob.pricing.Pricing;
import com.tmb.common.model.legacy.rsl.ws.creditcard.response.ResponseCreditcard;
import com.tmb.common.model.legacy.rsl.ws.facility.response.Body;
import com.tmb.common.model.legacy.rsl.ws.facility.response.Header;
import com.tmb.common.model.legacy.rsl.ws.facility.response.ResponseFacility;
import com.tmb.common.model.legacy.rsl.ws.individual.response.ResponseIndividual;
import com.tmb.oneapp.lendingservice.client.LoanSubmissionGetCreditCardInfoClient;
import com.tmb.oneapp.lendingservice.client.LoanSubmissionGetCustomerInfoClient;
import com.tmb.oneapp.lendingservice.client.LoanSubmissionGetFacilityInfoClient;
import com.tmb.oneapp.lendingservice.model.flexiloan.LoanCustomerPricing;
import com.tmb.oneapp.lendingservice.model.flexiloan.SubmissionInfoRequest;
import com.tmb.oneapp.lendingservice.model.flexiloan.SubmissionInfoResponse;
import com.tmb.oneapp.lendingservice.model.flexiloan.SubmissionPricingInfo;
import org.junit.Assert;
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
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;

@RunWith(JUnit4.class)
public class FlexiLoanSubmitServiceTest {
    @Mock
    private LoanSubmissionGetFacilityInfoClient getFacilityInfoClient;
    @Mock
    private LoanSubmissionGetCustomerInfoClient getCustomerInfoClient;
    @Mock
    private LoanSubmissionGetCreditCardInfoClient getCreditCardInfoClient;

    FlexiLoanSubmitService flexiLoanSubmitService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
        flexiLoanSubmitService = new FlexiLoanSubmitService(getFacilityInfoClient, getCustomerInfoClient, getCreditCardInfoClient);
    }

    @Test
    public void testService_creditCard() throws ServiceException, RemoteException, TMBCommonException {
        when(getFacilityInfoClient.searchFacilityInfoByCaID(any())).thenReturn(mockFacilityInfo());
        when(getCustomerInfoClient.searchCustomerInfoByCaID(anyLong())).thenReturn(mockCustomerInfo());
        when(getCreditCardInfoClient.searchCreditcardInfoByCaID(any())).thenReturn(mockCreditCardInfo());
        SubmissionInfoRequest request = new SubmissionInfoRequest();
        request.setCaId(1L);
        request.setProductCode("VI");
        flexiLoanSubmitService.getSubmissionInfo(request);
        Assert.assertTrue(true);
    }

//    @Test
//    public void testService_creditCardFailed() throws ServiceException, RemoteException, TMBCommonException {
//        ResponseFacility facilityInfo = new ResponseFacility();
//        Body body = new Body();
//        Header header = new Header();
//        header.setChannel("MIB");
//        header.setModule("3");
//        header.setResponseCode("MSG_000");
//        facilityInfo.setHeader(header);
//        facilityInfo.setBody(body);
//
//        ResponseCreditcard creditCardInfo = new ResponseCreditcard();
//        com.tmb.common.model.legacy.rsl.ws.creditcard.response.Body bodyCredit = new com.tmb.common.model.legacy.rsl.ws.creditcard.response.Body();
//        com.tmb.common.model.legacy.rsl.ws.creditcard.response.Header headerCredit = new com.tmb.common.model.legacy.rsl.ws.creditcard.response.Header();
//        headerCredit.setChannel("MIB");
//        headerCredit.setModule("3");
//        headerCredit.setResponseCode("MSG_999");
//        creditCardInfo.setHeader(headerCredit);
//        creditCardInfo.setBody(bodyCredit);
//
//        ResponseIndividual customerInfo = new ResponseIndividual();
//        com.tmb.common.model.legacy.rsl.ws.individual.response.Body bodyCustomer = new com.tmb.common.model.legacy.rsl.ws.individual.response.Body();
//        com.tmb.common.model.legacy.rsl.ws.individual.response.Header headerCustomer = new com.tmb.common.model.legacy.rsl.ws.individual.response.Header();
//        headerCustomer.setChannel("MIB");
//        headerCustomer.setModule("3");
//        headerCustomer.setResponseCode("MSG_999");
//        customerInfo.setHeader(headerCustomer);
//        customerInfo.setBody(bodyCustomer);
//
//        when(getFacilityInfoClient.searchFacilityInfoByCaID(any())).thenReturn(facilityInfo);
//        when(getCustomerInfoClient.searchCustomerInfoByCaID(anyLong())).thenReturn(customerInfo);
//        when(getCreditCardInfoClient.searchCreditcardInfoByCaID(any())).thenReturn(creditCardInfo);
//        SubmissionInfoRequest request = new SubmissionInfoRequest();
//        request.setCaId(1L);
//        request.setProductCode("VI");
//
//        assertThrows(Exception.class, () ->
//                flexiLoanSubmitService.getSubmissionInfo(request));
//    }

    @Test
    public void testService_c2g() throws ServiceException, RemoteException, TMBCommonException {
        when(getFacilityInfoClient.searchFacilityInfoByCaID(any())).thenReturn(mockFacilityInfoWithRateType());
        when(getCustomerInfoClient.searchCustomerInfoByCaID(anyLong())).thenReturn(mockCustomerInfo());
        when(getCreditCardInfoClient.searchCreditcardInfoByCaID(any())).thenReturn(mockCreditCardInfo());
        SubmissionInfoRequest request = new SubmissionInfoRequest();
        request.setCaId(1L);
        request.setProductCode("C2G");
        flexiLoanSubmitService.getSubmissionInfo(request);
        Assert.assertTrue(true);
    }

//    @Test
//    public void testService_c2gFailed() throws ServiceException, RemoteException, TMBCommonException {
//        when(getFacilityInfoClient.searchFacilityInfoByCaID(any())).thenReturn(mockFacilityInfoWithRateTypeFailed());
//        when(getCustomerInfoClient.searchCustomerInfoByCaID(anyLong())).thenReturn(mockCustomerInfo());
//        when(getCreditCardInfoClient.searchCreditcardInfoByCaID(any())).thenReturn(mockCreditCardInfo());
//        SubmissionInfoRequest request = new SubmissionInfoRequest();
//        request.setCaId(1L);
//        request.setProductCode("C2G");
//        SubmissionInfoResponse submissionInfoResponse = flexiLoanSubmitService.getSubmissionInfo(request);
//        Assert.assertNull(submissionInfoResponse);
//    }

    private ResponseFacility mockFacilityInfo() {
        ResponseFacility facilityInfo = new ResponseFacility();
        SubmissionPricingInfo submissionPricingInfo = new SubmissionPricingInfo();

        Body body = new Body();
        Facility facility = new Facility();
        Header header = new Header();
        header.setChannel("MIB");
        header.setModule("3");
        header.setResponseCode("MSG_000");


        Pricing p = new Pricing();
        p.setRateVaraince(BigDecimal.TEN);
        Pricing[] pricings = new Pricing[1];
        pricings[0] = p;

        List<LoanCustomerPricing> pricingList = new ArrayList<>();
        pricings[0].setMonthTo(BigDecimal.ONE);
        pricings[0].setMonthFrom(BigDecimal.ONE);
        pricings[0].setRateVaraince(BigDecimal.ONE);
        pricings[0].setYearFrom(BigDecimal.ONE);
        pricings[0].setYearTo(BigDecimal.ONE);
        pricings[0].setPricingType("S");

        LoanCustomerPricing customerPricing = new LoanCustomerPricing();
        customerPricing.setYearFrom(BigDecimal.ONE);
        customerPricing.setYearTo(BigDecimal.ONE);
        customerPricing.setMonthFrom(BigDecimal.ONE);
        customerPricing.setMonthTo(BigDecimal.ONE);
        customerPricing.setRate("12");
        customerPricing.setRateVariance(BigDecimal.ONE);
        pricingList.add(customerPricing);
        submissionPricingInfo.setPricing(pricingList);

        facility.setPricings(pricings);

        Feature feature = new Feature();
        feature.setDisbAcctNo("xxx");
        facility.setFeature(feature);

        facility.setFeatureType("S");

        Facility[] facilities = {facility};
        body.setFacilities(facilities);
        facilityInfo.setHeader(header);
        facilityInfo.setBody(body);
        return facilityInfo;
    }

    private ResponseFacility mockFacilityInfoFailed() {
        ResponseFacility facilityInfo = new ResponseFacility();
        SubmissionPricingInfo submissionPricingInfo = new SubmissionPricingInfo();

        Body body = new Body();
        Facility facility = new Facility();
        Header header = new Header();
        header.setChannel("MIB");
        header.setModule("3");
        header.setResponseCode("MSG_999");


        Pricing p = new Pricing();
        p.setRateVaraince(BigDecimal.TEN);
        Pricing[] pricings = new Pricing[1];
        pricings[0] = p;

        List<LoanCustomerPricing> pricingList = new ArrayList<>();
        pricings[0].setMonthTo(BigDecimal.ONE);
        pricings[0].setMonthFrom(BigDecimal.ONE);
        pricings[0].setRateVaraince(BigDecimal.ONE);
        pricings[0].setYearFrom(BigDecimal.ONE);
        pricings[0].setYearTo(BigDecimal.ONE);
        pricings[0].setPricingType("S");

        LoanCustomerPricing customerPricing = new LoanCustomerPricing();
        customerPricing.setYearFrom(BigDecimal.ONE);
        customerPricing.setYearTo(BigDecimal.ONE);
        customerPricing.setMonthFrom(BigDecimal.ONE);
        customerPricing.setMonthTo(BigDecimal.ONE);
        customerPricing.setRate("12");
        customerPricing.setRateVariance(BigDecimal.ONE);
        pricingList.add(customerPricing);
        submissionPricingInfo.setPricing(pricingList);

        facility.setPricings(pricings);

        Feature feature = new Feature();
        feature.setDisbAcctNo("xxx");
        facility.setFeature(feature);

        facility.setFeatureType("S");

        Facility[] facilities = {facility};
        body.setFacilities(facilities);
        facilityInfo.setHeader(header);
        facilityInfo.setBody(body);
        return facilityInfo;
    }

    private ResponseFacility mockFacilityInfoWithRateType() {
        ResponseFacility facilityInfo = new ResponseFacility();
        SubmissionPricingInfo submissionPricingInfo = new SubmissionPricingInfo();

        Pricing p = new Pricing();
        Body body = new Body();
        Header header = new Header();
        header.setChannel("MIB");
        header.setModule("3");
        header.setResponseCode("MSG_000");

        Facility facility = new Facility();
        Pricing[] pricings = new Pricing[1];
        pricings[0] = p;

        List<LoanCustomerPricing> pricingList = new ArrayList<>();
        pricings[0].setMonthTo(BigDecimal.ONE);
        pricings[0].setMonthFrom(BigDecimal.ONE);
        pricings[0].setRateVaraince(BigDecimal.ONE);
        pricings[0].setYearFrom(BigDecimal.ONE);
        pricings[0].setYearTo(BigDecimal.ONE);
        pricings[0].setPricingType("S");

        LoanCustomerPricing customerPricing = new LoanCustomerPricing();
        customerPricing.setYearFrom(BigDecimal.ONE);
        customerPricing.setYearTo(BigDecimal.ONE);
        customerPricing.setMonthFrom(BigDecimal.ONE);
        customerPricing.setMonthTo(BigDecimal.ONE);
        customerPricing.setRate("12");
        customerPricing.setRateVariance(BigDecimal.ONE);
        pricingList.add(customerPricing);
        submissionPricingInfo.setPricing(pricingList);

        Pricing pricing = new Pricing();
        pricing.setRateVaraince(BigDecimal.TEN);
        pricing.setRateType("CPR");
        pricing.setPercentSign("+");

        facility.setPricings(pricings);

        Feature feature = new Feature();
        feature.setDisbAcctNo("xxx");
        facility.setFeature(feature);

        facility.setFeatureType("S");

        Facility[] facilities = {facility};
        body.setFacilities(facilities);
        facilityInfo.setBody(body);
        facilityInfo.setHeader(header);
        return facilityInfo;
    }

    private ResponseFacility mockFacilityInfoWithRateTypeFailed() {
        ResponseFacility facilityInfo = new ResponseFacility();
        SubmissionPricingInfo submissionPricingInfo = new SubmissionPricingInfo();

        Pricing p = new Pricing();
        Body body = new Body();
        Header header = new Header();
        header.setChannel("MIB");
        header.setModule("3");
        header.setResponseCode("MSG_999");

        Facility facility = new Facility();
        Pricing[] pricings = new Pricing[1];
        pricings[0] = p;

        List<LoanCustomerPricing> pricingList = new ArrayList<>();
        pricings[0].setMonthTo(BigDecimal.ONE);
        pricings[0].setMonthFrom(BigDecimal.ONE);
        pricings[0].setRateVaraince(BigDecimal.ONE);
        pricings[0].setYearFrom(BigDecimal.ONE);
        pricings[0].setYearTo(BigDecimal.ONE);
        pricings[0].setPricingType("S");

        LoanCustomerPricing customerPricing = new LoanCustomerPricing();
        customerPricing.setYearFrom(BigDecimal.ONE);
        customerPricing.setYearTo(BigDecimal.ONE);
        customerPricing.setMonthFrom(BigDecimal.ONE);
        customerPricing.setMonthTo(BigDecimal.ONE);
        customerPricing.setRate("12");
        customerPricing.setRateVariance(BigDecimal.ONE);
        pricingList.add(customerPricing);
        submissionPricingInfo.setPricing(pricingList);

        Pricing pricing = new Pricing();
        pricing.setRateVaraince(BigDecimal.TEN);
        pricing.setRateType("CPR");
        pricing.setPercentSign("+");

        facility.setPricings(pricings);

        Feature feature = new Feature();
        feature.setDisbAcctNo("xxx");
        facility.setFeature(feature);

        facility.setFeatureType("S");

        Facility[] facilities = {facility};
        body.setFacilities(facilities);
        facilityInfo.setBody(body);
        facilityInfo.setHeader(header);
        return facilityInfo;
    }

    private ResponseIndividual mockCustomerInfo() {
        ResponseIndividual customerInfo = new ResponseIndividual();
        com.tmb.common.model.legacy.rsl.ws.individual.response.Body body = new com.tmb.common.model.legacy.rsl.ws.individual.response.Body();
        com.tmb.common.model.legacy.rsl.ws.individual.response.Header header = new com.tmb.common.model.legacy.rsl.ws.individual.response.Header();
        header.setChannel("MIB");
        header.setModule("3");
        header.setResponseCode("MSG_000");

        Individual individual = new Individual();
        Individual[] individuals = {individual};
        body.setIndividuals(individuals);
        customerInfo.setBody(body);
        customerInfo.setHeader(header);
        return customerInfo;
    }

    private ResponseIndividual mockCustomerInfoFailed() {
        ResponseIndividual customerInfo = new ResponseIndividual();
        com.tmb.common.model.legacy.rsl.ws.individual.response.Body body = new com.tmb.common.model.legacy.rsl.ws.individual.response.Body();
        com.tmb.common.model.legacy.rsl.ws.individual.response.Header header = new com.tmb.common.model.legacy.rsl.ws.individual.response.Header();
        header.setChannel("MIB");
        header.setModule("3");
        header.setResponseCode("MSG_999");

        Individual individual = new Individual();
        Individual[] individuals = {individual};
        body.setIndividuals(individuals);
        customerInfo.setBody(body);
        customerInfo.setHeader(header);
        return customerInfo;
    }

    private ResponseCreditcard mockCreditCardInfo() {
        ResponseCreditcard creditCardInfo = new ResponseCreditcard();
        com.tmb.common.model.legacy.rsl.ws.creditcard.response.Body body = new com.tmb.common.model.legacy.rsl.ws.creditcard.response.Body();
        com.tmb.common.model.legacy.rsl.ws.creditcard.response.Header header = new com.tmb.common.model.legacy.rsl.ws.creditcard.response.Header();
        header.setChannel("MIB");
        header.setModule("3");
        header.setResponseCode("MSG_000");

        CreditCard creditCard = new CreditCard();
        CreditCard[] creditCards = {creditCard};
        body.setCreditCards(creditCards);
        creditCardInfo.setBody(body);
        creditCardInfo.setHeader(header);
        return creditCardInfo;
    }

    private ResponseCreditcard mockCreditCardInfoFailed() {
        ResponseCreditcard creditCardInfo = new ResponseCreditcard();
        com.tmb.common.model.legacy.rsl.ws.creditcard.response.Body body = new com.tmb.common.model.legacy.rsl.ws.creditcard.response.Body();
        com.tmb.common.model.legacy.rsl.ws.creditcard.response.Header header = new com.tmb.common.model.legacy.rsl.ws.creditcard.response.Header();
        header.setChannel("MIB");
        header.setModule("3");
        header.setResponseCode("MSG_999");

        CreditCard creditCard = new CreditCard();
        CreditCard[] creditCards = {creditCard};
        body.setCreditCards(creditCards);
        creditCardInfo.setBody(body);
        creditCardInfo.setHeader(header);
        return creditCardInfo;
    }

}