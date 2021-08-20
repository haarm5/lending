package com.tmb.oneapp.lendingservice.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.tmb.common.exception.model.TMBCommonException;
import com.tmb.common.model.legacy.rsl.common.ob.address.Address;
import com.tmb.common.model.legacy.rsl.common.ob.creditcard.CreditCard;
import com.tmb.common.model.legacy.rsl.common.ob.facility.Facility;
import com.tmb.common.model.legacy.rsl.common.ob.individual.Individual;
import com.tmb.common.model.legacy.rsl.ws.creditcard.response.ResponseCreditcard;
import com.tmb.common.model.legacy.rsl.ws.facility.response.ResponseFacility;
import com.tmb.common.model.legacy.rsl.ws.individual.response.Body;
import com.tmb.common.model.legacy.rsl.ws.individual.response.Header;
import com.tmb.common.model.legacy.rsl.ws.individual.response.ResponseIndividual;
import com.tmb.oneapp.lendingservice.client.*;
import com.tmb.oneapp.lendingservice.model.loanonline.UpdateWorkingDetailRequest;
import org.junit.Assert;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import javax.xml.rpc.ServiceException;
import java.rmi.RemoteException;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;

@RunWith(JUnit4.class)
class WorkingDetailUpdateWorkingDetailServiceTest {

    WorkingDetailUpdateWorkingDetailService workingDetailUpdateWorkingDetailService;

    @Mock
    private LoanSubmissionGetCustomerInfoClient customerInfoClient;

    @Mock
    private LoanSubmissionUpdateCustomerClient loanSubmissionUpdateCustomerClient;

    @Mock
    private LoanSubmissionGetFacilityInfoClient loanSubmissionGetFacilityInfoClient;

    @Mock
    private LoanSubmissionUpdateFacilityInfoClient loanSubmissionUpdateFacilityInfoClient;

    @Mock
    private LoanSubmissionGetCreditcardInfoClient loanSubmissionGetCreditcardInfoClient;

    @Mock
    private LoanSubmissionUpdateCreditCardClient loanSubmissionUpdateCreditCardClient;


    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
        workingDetailUpdateWorkingDetailService = new WorkingDetailUpdateWorkingDetailService(
                customerInfoClient, loanSubmissionUpdateCustomerClient,
                loanSubmissionGetFacilityInfoClient, loanSubmissionUpdateFacilityInfoClient,
                loanSubmissionGetCreditcardInfoClient, loanSubmissionUpdateCreditCardClient);
    }

    @Test
    public void testUpdateWorkDetailSuccessCaseNonCC() throws ServiceException, TMBCommonException, RemoteException, JsonProcessingException {

        com.tmb.common.model.legacy.rsl.ws.facility.response.Header facilityResHeader = new com.tmb.common.model.legacy.rsl.ws.facility.response.Header();
        facilityResHeader.setResponseCode("MSG_000");
        Facility[] facilities = new Facility[1];
        facilities[0] = new Facility();
        com.tmb.common.model.legacy.rsl.ws.facility.response.Body facilityResBody = new com.tmb.common.model.legacy.rsl.ws.facility.response.Body();
        facilityResBody.setFacilities(facilities);
        ResponseFacility responseFacility = new ResponseFacility();
        responseFacility.setHeader(facilityResHeader);
        responseFacility.setBody(facilityResBody);
        when(loanSubmissionGetFacilityInfoClient.searchFacilityInfoByCaID(anyLong())).thenReturn(responseFacility);

        com.tmb.common.model.legacy.rsl.ws.facility.update.response.Header updateFacilityResHeader = new com.tmb.common.model.legacy.rsl.ws.facility.update.response.Header();
        updateFacilityResHeader.setResponseCode("MSG_000");
        com.tmb.common.model.legacy.rsl.ws.facility.update.response.ResponseFacility updateFacilityRes = new com.tmb.common.model.legacy.rsl.ws.facility.update.response.ResponseFacility();
        updateFacilityRes.setHeader(updateFacilityResHeader);
        when(loanSubmissionUpdateFacilityInfoClient.updateFacilityInfo(any())).thenReturn(updateFacilityRes);

        Header header = new Header();
        header.setResponseCode("MSG_000");
        Address[] addresses = new Address[1];
        addresses[0] = new Address();
        addresses[0].setAddrTypCode("O");
        Individual[] individuals = new Individual[1];
        individuals[0] = new Individual();
        individuals[0].setAddresses(addresses);
        Body body = new Body();
        body.setIndividuals(individuals);
        ResponseIndividual individual = new ResponseIndividual();
        individual.setHeader(header);
        individual.setBody(body);
        when(customerInfoClient.searchCustomerInfoByCaID(anyLong())).thenReturn(individual);


        var res = new com.tmb.common.model.legacy.rsl.ws.individual.update.response.ResponseIndividual();
        com.tmb.common.model.legacy.rsl.ws.individual.update.response.Header header2 = new com.tmb.common.model.legacy.rsl.ws.individual.update.response.Header();
        header2.setResponseCode("MSG_000");
        res.setHeader(header2);
        when(loanSubmissionUpdateCustomerClient.updateCustomerInfo(any())).thenReturn(res);

        var address = new com.tmb.oneapp.lendingservice.model.personal.Address();
        var req = new UpdateWorkingDetailRequest();
        req.setAddress(address);
        req.setCaId(12L);
        req.setProductCode("C2G");
        com.tmb.common.model.legacy.rsl.ws.individual.update.response.ResponseIndividual result = workingDetailUpdateWorkingDetailService.updateWorkDetail(req);
        Assert.assertNotNull(result);
    }

    @Test
    public void testUpdateWorkDetailSuccessCaseCC() throws ServiceException, TMBCommonException, RemoteException, JsonProcessingException {


        com.tmb.common.model.legacy.rsl.ws.creditcard.response.Header creditCardHeader = new com.tmb.common.model.legacy.rsl.ws.creditcard.response.Header();
        creditCardHeader.setResponseCode("MSG_000");
        CreditCard[] creditCards = new CreditCard[1];
        creditCards[0] = new CreditCard();
        com.tmb.common.model.legacy.rsl.ws.creditcard.response.Body creditCardBody = new com.tmb.common.model.legacy.rsl.ws.creditcard.response.Body();
        creditCardBody.setCreditCards(creditCards);
        ResponseCreditcard responseCreditcard = new ResponseCreditcard();
        responseCreditcard.setHeader(creditCardHeader);
        responseCreditcard.setBody(creditCardBody);
        when(loanSubmissionGetCreditcardInfoClient.searchCreditcardInfoByCaID(anyLong())).thenReturn(responseCreditcard);


        com.tmb.common.model.legacy.rsl.ws.creditcard.update.response.Header updateCreditCardResponseHeader = new com.tmb.common.model.legacy.rsl.ws.creditcard.update.response.Header();
        updateCreditCardResponseHeader.setResponseCode("MSG_000");
        com.tmb.common.model.legacy.rsl.ws.creditcard.update.response.ResponseCreditcard updateCreditCardResponse = new com.tmb.common.model.legacy.rsl.ws.creditcard.update.response.ResponseCreditcard();
        updateCreditCardResponse.setHeader(updateCreditCardResponseHeader);
        when(loanSubmissionUpdateCreditCardClient.updateCreditCard(any())).thenReturn(updateCreditCardResponse);


        Header header = new Header();
        header.setResponseCode("MSG_000");
        Address[] addresses = new Address[1];
        addresses[0] = new Address();
        addresses[0].setAddrTypCode("O");
        Individual[] individuals = new Individual[1];
        individuals[0] = new Individual();
        individuals[0].setAddresses(addresses);
        Body body = new Body();
        body.setIndividuals(individuals);
        ResponseIndividual individual = new ResponseIndividual();
        individual.setHeader(header);
        individual.setBody(body);
        when(customerInfoClient.searchCustomerInfoByCaID(anyLong())).thenReturn(individual);


        var res = new com.tmb.common.model.legacy.rsl.ws.individual.update.response.ResponseIndividual();
        com.tmb.common.model.legacy.rsl.ws.individual.update.response.Header header2 = new com.tmb.common.model.legacy.rsl.ws.individual.update.response.Header();
        header2.setResponseCode("MSG_000");
        res.setHeader(header2);
        when(loanSubmissionUpdateCustomerClient.updateCustomerInfo(any())).thenReturn(res);

        var address = new com.tmb.oneapp.lendingservice.model.personal.Address();
        var req = new UpdateWorkingDetailRequest();
        req.setAddress(address);
        req.setProductCode("VG");
        req.setCaId(12L);
        com.tmb.common.model.legacy.rsl.ws.individual.update.response.ResponseIndividual result = workingDetailUpdateWorkingDetailService.updateWorkDetail(req);
        Assert.assertNotNull(result);
    }
}