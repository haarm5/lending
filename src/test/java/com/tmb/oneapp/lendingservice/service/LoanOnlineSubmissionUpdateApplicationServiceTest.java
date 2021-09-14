package com.tmb.oneapp.lendingservice.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.tmb.common.exception.model.TMBCommonException;
import com.tmb.common.model.legacy.rsl.common.ob.creditcard.CreditCard;
import com.tmb.common.model.legacy.rsl.common.ob.facility.Facility;
import com.tmb.common.model.legacy.rsl.common.ob.feature.Feature;
import com.tmb.common.model.legacy.rsl.common.ob.individual.Individual;
import com.tmb.common.model.legacy.rsl.common.ob.pricing.Pricing;
import com.tmb.common.model.legacy.rsl.ws.application.response.Body;
import com.tmb.common.model.legacy.rsl.ws.application.response.Header;
import com.tmb.common.model.legacy.rsl.ws.application.response.ResponseApplication;
import com.tmb.common.model.legacy.rsl.ws.creditcard.response.ResponseCreditcard;
import com.tmb.common.model.legacy.rsl.ws.facility.response.ResponseFacility;
import com.tmb.common.model.legacy.rsl.ws.individual.response.ResponseIndividual;
import com.tmb.oneapp.lendingservice.client.*;
import com.tmb.oneapp.lendingservice.constant.RslResponseCode;
import com.tmb.oneapp.lendingservice.model.loanonline.LoanSubmissionCreateApplicationReq;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import javax.xml.rpc.ServiceException;
import java.rmi.RemoteException;
import java.util.GregorianCalendar;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;


@RunWith(JUnit4.class)
class LoanOnlineSubmissionUpdateApplicationServiceTest {

    @Mock
    private LoanSubmissionGetApplicationInfoClient loanSubmissionGetApplicationInfoClient;
    @Mock
    private LoanSubmissionGetCustomerInfoClient loanSubmissionGetCustomerInfoClient;
    @Mock
    private LoanSubmissionGetFacilityInfoClient loanSubmissionGetFacilityInfoClient;
    @Mock
    private LoanSubmissionGetCreditcardInfoClient loanSubmissionGetCreditcardInfoClient;

    @Mock
    private LoanSubmissionUpdateCustomerClient loanSubmissionUpdateCustomerClient;
    @Mock
    private LoanSubmissionUpdateFacilityInfoClient loanSubmissionUpdateFacilityInfoClient;
    @Mock
    private LoanSubmissionUpdateCreditCardClient loanSubmissionUpdateCreditCardClient;

    @InjectMocks
    LoanOnlineSubmissionUpdateApplicationService loanOnlineSubmissionUpdateApplicationService;


    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testUpdateApplicationTypeCC() throws ServiceException, TMBCommonException, JsonProcessingException, RemoteException {
        doReturn(getApplicationResponse("CC")).when(loanSubmissionGetApplicationInfoClient).searchApplicationInfoByCaID(anyLong());
        doReturn(getIndividualResponse()).when(loanSubmissionGetCustomerInfoClient).searchCustomerInfoByCaID(anyLong());
        doReturn(getCreditCardResponse()).when(loanSubmissionGetCreditcardInfoClient).searchCreditcardInfoByCaID(anyLong());
        doReturn(updateIndividualResponse()).when(loanSubmissionUpdateCustomerClient).updateCustomerInfo(any());
        doReturn(updateCreditCardResponse()).when(loanSubmissionUpdateCreditCardClient).updateCreditCard(any());

        var req = new LoanSubmissionCreateApplicationReq();
        req.setCaId(12l);
        loanOnlineSubmissionUpdateApplicationService.updateApplication(req);

        verify(loanSubmissionGetApplicationInfoClient, times(1)).searchApplicationInfoByCaID(anyLong());
        verify(loanSubmissionGetCustomerInfoClient, times(1)).searchCustomerInfoByCaID(anyLong());
        verify(loanSubmissionGetCreditcardInfoClient, times(1)).searchCreditcardInfoByCaID(anyLong());
        verify(loanSubmissionUpdateCreditCardClient, times(1)).updateCreditCard(any());
        verify(loanSubmissionUpdateCustomerClient, times(1)).updateCustomerInfo(any());
    }

    @Test
    public void testUpdateApplicationTypePL() throws ServiceException, TMBCommonException, JsonProcessingException, RemoteException {
        doReturn(getApplicationResponse("PL")).when(loanSubmissionGetApplicationInfoClient).searchApplicationInfoByCaID(anyLong());
        doReturn(getIndividualResponse()).when(loanSubmissionGetCustomerInfoClient).searchCustomerInfoByCaID(anyLong());
        doReturn(getFacilityResponse()).when(loanSubmissionGetFacilityInfoClient).searchFacilityInfoByCaID(anyLong());
        doReturn(updateIndividualResponse()).when(loanSubmissionUpdateCustomerClient).updateCustomerInfo(any());
        doReturn(updateFacilityResponse()).when(loanSubmissionUpdateFacilityInfoClient).updateFacilityInfo(any());

        var req = new LoanSubmissionCreateApplicationReq();
        req.setCaId(12l);
        loanOnlineSubmissionUpdateApplicationService.updateApplication(req);

        verify(loanSubmissionGetApplicationInfoClient, times(1)).searchApplicationInfoByCaID(anyLong());
        verify(loanSubmissionGetCustomerInfoClient, times(1)).searchCustomerInfoByCaID(anyLong());
        verify(loanSubmissionGetFacilityInfoClient, times(1)).searchFacilityInfoByCaID(anyLong());
        verify(loanSubmissionUpdateFacilityInfoClient, times(1)).updateFacilityInfo(any());
        verify(loanSubmissionUpdateCustomerClient, times(1)).updateCustomerInfo(any());
    }

    private ResponseApplication getApplicationResponse(String type) {

        Header header = new Header();
        header.setResponseCode(RslResponseCode.SUCCESS.getCode());

        Body body = new Body();
        body.setAppType(type);
        body.setApplicationDate("2000-10-10");

        ResponseApplication application = new ResponseApplication();
        application.setHeader(header);
        application.setBody(body);

        return application;
    }

    private ResponseIndividual getIndividualResponse() {

        com.tmb.common.model.legacy.rsl.ws.individual.response.Header header = new com.tmb.common.model.legacy.rsl.ws.individual.response.Header();
        header.setResponseCode(RslResponseCode.SUCCESS.getCode());

        Individual[] individuals = new Individual[1];
        individuals[0] = new Individual();
        individuals[0].setEmploymentStatus("02");
        individuals[0].setIdType1("PP");
        individuals[0].setIssuedDate(new GregorianCalendar());
        individuals[0].setBirthDate(new GregorianCalendar());
        individuals[0].setExpiryDate(new GregorianCalendar());
        individuals[0].setContractEmployedFlag("Y");
        individuals[0].setEmailStatementFlag("N");
        individuals[0].setNcbConsentFlag("N");

        com.tmb.common.model.legacy.rsl.ws.individual.response.Body body = new com.tmb.common.model.legacy.rsl.ws.individual.response.Body();
        body.setIndividuals(individuals);

        ResponseIndividual individual = new ResponseIndividual();
        individual.setHeader(header);
        individual.setBody(body);
        return individual;
    }

    private ResponseCreditcard getCreditCardResponse() {
        com.tmb.common.model.legacy.rsl.ws.creditcard.response.Header header = new com.tmb.common.model.legacy.rsl.ws.creditcard.response.Header();
        header.setResponseCode(RslResponseCode.SUCCESS.getCode());

        CreditCard[] creditCards = new CreditCard[1];
        creditCards[0] = new CreditCard();
        creditCards[0].setPaymentMethod("01");
        creditCards[0].setPaymentCriteria("01");
        creditCards[0].setMailPreference("01");
        com.tmb.common.model.legacy.rsl.ws.creditcard.response.Body body = new com.tmb.common.model.legacy.rsl.ws.creditcard.response.Body();
        body.setCreditCards(creditCards);

        ResponseCreditcard creditCard = new ResponseCreditcard();
        creditCard.setHeader(header);
        creditCard.setBody(body);
        return creditCard;
    }

    private ResponseFacility getFacilityResponse() {

        com.tmb.common.model.legacy.rsl.ws.facility.response.Header header = new com.tmb.common.model.legacy.rsl.ws.facility.response.Header();
        header.setResponseCode(RslResponseCode.SUCCESS.getCode());

        Pricing[] pricings = new Pricing[1];
        pricings[0] = new Pricing();

        Feature feature = new Feature();

        Facility[] facilities = new Facility[1];
        facilities[0] = new Facility();
        facilities[0].setPricings(pricings);
        facilities[0].setFeature(feature);
        facilities[0].setFeatureType("01");
        facilities[0].setPaymentMethod("01");
        facilities[0].setPayMethodCriteria("01");
        facilities[0].setLoanWithOtherBank("01");
        facilities[0].setConsiderLoanWithOtherBank("01");
        facilities[0].setMailingPreference("H");

        com.tmb.common.model.legacy.rsl.ws.facility.response.Body body = new com.tmb.common.model.legacy.rsl.ws.facility.response.Body();
        body.setFacilities(facilities);

        ResponseFacility facility = new ResponseFacility();
        facility.setHeader(header);
        facility.setBody(body);
        return facility;
    }

    private com.tmb.common.model.legacy.rsl.ws.facility.update.response.ResponseFacility updateFacilityResponse() {
        com.tmb.common.model.legacy.rsl.ws.facility.update.response.Header header = new com.tmb.common.model.legacy.rsl.ws.facility.update.response.Header();
        header.setResponseCode("MSG_000");
        com.tmb.common.model.legacy.rsl.ws.facility.update.response.ResponseFacility updateFacilityResponse = new com.tmb.common.model.legacy.rsl.ws.facility.update.response.ResponseFacility();
        updateFacilityResponse.setHeader(header);
        return updateFacilityResponse;
    }

    private com.tmb.common.model.legacy.rsl.ws.creditcard.update.response.ResponseCreditcard updateCreditCardResponse() {
        com.tmb.common.model.legacy.rsl.ws.creditcard.update.response.Header header = new com.tmb.common.model.legacy.rsl.ws.creditcard.update.response.Header();
        header.setResponseCode("MSG_000");
        com.tmb.common.model.legacy.rsl.ws.creditcard.update.response.ResponseCreditcard updateCreditCardResponse = new com.tmb.common.model.legacy.rsl.ws.creditcard.update.response.ResponseCreditcard();
        updateCreditCardResponse.setHeader(header);
        return updateCreditCardResponse;
    }

    private com.tmb.common.model.legacy.rsl.ws.individual.update.response.ResponseIndividual updateIndividualResponse() {
        com.tmb.common.model.legacy.rsl.ws.individual.update.response.ResponseIndividual updateIndividualResponse = new com.tmb.common.model.legacy.rsl.ws.individual.update.response.ResponseIndividual();
        com.tmb.common.model.legacy.rsl.ws.individual.update.response.Header header = new com.tmb.common.model.legacy.rsl.ws.individual.update.response.Header();
        header.setResponseCode("MSG_000");
        updateIndividualResponse.setHeader(header);
        return updateIndividualResponse;
    }
}