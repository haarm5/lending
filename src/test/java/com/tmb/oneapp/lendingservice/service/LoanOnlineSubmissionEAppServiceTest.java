package com.tmb.oneapp.lendingservice.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.tmb.common.exception.model.TMBCommonException;
import com.tmb.common.model.legacy.rsl.common.ob.address.Address;
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
import com.tmb.oneapp.lendingservice.client.LoanSubmissionGetApplicationInfoClient;
import com.tmb.oneapp.lendingservice.client.LoanSubmissionGetCreditcardInfoClient;
import com.tmb.oneapp.lendingservice.client.LoanSubmissionGetCustomerInfoClient;
import com.tmb.oneapp.lendingservice.client.LoanSubmissionGetFacilityInfoClient;
import com.tmb.oneapp.lendingservice.constant.RslResponseCode;
import com.tmb.oneapp.lendingservice.model.loanonline.EAppResponse;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import javax.xml.rpc.ServiceException;
import java.rmi.RemoteException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.GregorianCalendar;

import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doReturn;

@RunWith(JUnit4.class)
class LoanOnlineSubmissionEAppServiceTest {

    @Mock
    private LoanSubmissionGetApplicationInfoClient loanSubmissionGetApplicationInfoClient;
    @Mock
    private LoanSubmissionGetCustomerInfoClient loanSubmissionGetCustomerInfoClient;
    @Mock
    private LoanSubmissionGetFacilityInfoClient loanSubmissionGetFacilityInfoClient;
    @Mock
    private LoanSubmissionGetCreditcardInfoClient loanSubmissionGetCreditcardInfoClient;
    @Mock
    private DropdownService dropdownService;

    @InjectMocks
    LoanOnlineSubmissionEAppService loanOnlineSubmissionEAppService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testGetEAppTypeCC() throws ServiceException, TMBCommonException, JsonProcessingException, RemoteException, ParseException {
        doReturn(application("CC")).when(loanSubmissionGetApplicationInfoClient).searchApplicationInfoByCaID(anyLong());
        doReturn(individual()).when(loanSubmissionGetCustomerInfoClient).searchCustomerInfoByCaID(anyLong());
        doReturn(creditCard("0", "0", "H")).when(loanSubmissionGetCreditcardInfoClient).searchCreditcardInfoByCaID(anyLong());
        doReturn(new ArrayList<>()).when(dropdownService).getDropdownSciCountry(anyString(), anyString());
        doReturn(new ArrayList<>()).when(dropdownService).getDropdownEducationLevel(anyString());
        doReturn(new ArrayList<>()).when(dropdownService).getDropdownMaritalStatus(anyString());
        doReturn(new ArrayList<>()).when(dropdownService).getDropdownResidentType(anyString());
        doReturn(new ArrayList<>()).when(dropdownService).getDropdownRmOccupation(anyString());
        doReturn(new ArrayList<>()).when(dropdownService).getDropdownOccupation(anyString());
        doReturn(new ArrayList<>()).when(dropdownService).getDropdownIncomeBank();

        EAppResponse response = loanOnlineSubmissionEAppService.getEApp(123L, "crm", "correlation");
        Assertions.assertNotNull(response);

        doReturn(creditCard("1", "1", "O")).when(loanSubmissionGetCreditcardInfoClient).searchCreditcardInfoByCaID(anyLong());
        response = loanOnlineSubmissionEAppService.getEApp(123L, "crm", "correlation");
        Assertions.assertNotNull(response);
    }

    @Test
    public void testGetEAppTypePL() throws ServiceException, TMBCommonException, JsonProcessingException, RemoteException, ParseException {
        doReturn(application("PL")).when(loanSubmissionGetApplicationInfoClient).searchApplicationInfoByCaID(anyLong());
        doReturn(individual()).when(loanSubmissionGetCustomerInfoClient).searchCustomerInfoByCaID(anyLong());
        doReturn(facility("0", "0", "1", "1", "H", "c")).when(loanSubmissionGetFacilityInfoClient).searchFacilityInfoByCaID(anyLong());
        doReturn(new ArrayList<>()).when(dropdownService).getDropdownSciCountry(anyString(), anyString());
        doReturn(new ArrayList<>()).when(dropdownService).getDropdownEducationLevel(anyString());
        doReturn(new ArrayList<>()).when(dropdownService).getDropdownMaritalStatus(anyString());
        doReturn(new ArrayList<>()).when(dropdownService).getDropdownResidentType(anyString());
        doReturn(new ArrayList<>()).when(dropdownService).getDropdownRmOccupation(anyString());
        doReturn(new ArrayList<>()).when(dropdownService).getDropdownOccupation(anyString());
        doReturn(new ArrayList<>()).when(dropdownService).getDropdownIncomeBank();

        EAppResponse response = loanOnlineSubmissionEAppService.getEApp(123L, "crm", "correlation");
        Assertions.assertNotNull(response);

        doReturn(facility("1", "1", "2", "3", "O", "s")).when(loanSubmissionGetFacilityInfoClient).searchFacilityInfoByCaID(anyLong());
        response = loanOnlineSubmissionEAppService.getEApp(123L, "crm", "correlation");
        Assertions.assertNotNull(response);


    }


    private ResponseApplication application(String type) {

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

    private ResponseIndividual individual() {

        com.tmb.common.model.legacy.rsl.ws.individual.response.Header header = new com.tmb.common.model.legacy.rsl.ws.individual.response.Header();
        header.setResponseCode(RslResponseCode.SUCCESS.getCode());

        Address[] addresses = new Address[2];
        addresses[0] = new Address();
        addresses[0].setAddrTypCode("H");
        addresses[1] = new Address();
        addresses[1].setAddrTypCode("O");

        Individual[] individuals = new Individual[1];
        individuals[0] = new Individual();
        individuals[0].setEmploymentStatus("02");
        individuals[0].setIdType1("PP");
        individuals[0].setIssuedDate(new GregorianCalendar());
        individuals[0].setBirthDate(new GregorianCalendar());
        individuals[0].setExpiryDate(new GregorianCalendar());
        individuals[0].setAddresses(addresses);
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

    private ResponseCreditcard creditCard(String paymentCriteria, String paymentMethod, String mailPreference) {
        com.tmb.common.model.legacy.rsl.ws.creditcard.response.Header header = new com.tmb.common.model.legacy.rsl.ws.creditcard.response.Header();
        header.setResponseCode(RslResponseCode.SUCCESS.getCode());

        CreditCard[] creditCards = new CreditCard[1];
        creditCards[0] = new CreditCard();
        creditCards[0].setPaymentMethod(paymentMethod);
        creditCards[0].setPaymentCriteria(paymentCriteria);
        creditCards[0].setMailPreference(mailPreference);
        com.tmb.common.model.legacy.rsl.ws.creditcard.response.Body body = new com.tmb.common.model.legacy.rsl.ws.creditcard.response.Body();
        body.setCreditCards(creditCards);

        ResponseCreditcard creditCard = new ResponseCreditcard();
        creditCard.setHeader(header);
        creditCard.setBody(body);
        return creditCard;
    }

    private ResponseFacility facility(String paymentMethod,
                                      String paymentCriteria,
                                      String loanWithOtherBank,
                                      String considerLoanWithOtherBank,
                                      String mailingPreference,
                                      String featureType) {

        com.tmb.common.model.legacy.rsl.ws.facility.response.Header header = new com.tmb.common.model.legacy.rsl.ws.facility.response.Header();
        header.setResponseCode(RslResponseCode.SUCCESS.getCode());

        Pricing[] pricings = new Pricing[1];
        pricings[0] = new Pricing();

        Feature feature = new Feature();

        Facility[] facilities = new Facility[1];
        facilities[0] = new Facility();
        facilities[0].setPricings(pricings);
        facilities[0].setFeature(feature);
        facilities[0].setFeatureType(featureType);
        facilities[0].setPaymentMethod(paymentMethod);
        facilities[0].setPayMethodCriteria(paymentCriteria);
        facilities[0].setLoanWithOtherBank(loanWithOtherBank);
        facilities[0].setConsiderLoanWithOtherBank(considerLoanWithOtherBank);
        facilities[0].setMailingPreference(mailingPreference);

        com.tmb.common.model.legacy.rsl.ws.facility.response.Body body = new com.tmb.common.model.legacy.rsl.ws.facility.response.Body();
        body.setFacilities(facilities);

        ResponseFacility facility = new ResponseFacility();
        facility.setHeader(header);
        facility.setBody(body);
        return facility;
    }

}