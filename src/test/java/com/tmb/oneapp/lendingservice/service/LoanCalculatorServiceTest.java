package com.tmb.oneapp.lendingservice.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.tmb.common.exception.model.TMBCommonException;
import com.tmb.common.model.TmbOneServiceResponse;
import com.tmb.common.model.TmbStatus;
import com.tmb.common.model.legacy.rsl.common.ob.creditcard.CreditCard;
import com.tmb.common.model.legacy.rsl.common.ob.facility.Facility;
import com.tmb.common.model.legacy.rsl.common.ob.individual.Individual;
import com.tmb.common.model.legacy.rsl.ws.application.response.ResponseApplication;
import com.tmb.common.model.legacy.rsl.ws.creditcard.response.ResponseCreditcard;
import com.tmb.common.model.legacy.rsl.ws.facility.response.Header;
import com.tmb.common.model.legacy.rsl.ws.facility.response.ResponseFacility;
import com.tmb.common.model.legacy.rsl.ws.individual.response.ResponseIndividual;
import com.tmb.oneapp.lendingservice.client.LoanSubmissionGetApplicationInfoClient;
import com.tmb.oneapp.lendingservice.client.LoanSubmissionGetCreditcardInfoClient;
import com.tmb.oneapp.lendingservice.client.LoanSubmissionGetCustomerInfoClient;
import com.tmb.oneapp.lendingservice.client.LoanSubmissionGetFacilityInfoClient;
import com.tmb.oneapp.lendingservice.constant.ResponseCode;
import com.tmb.oneapp.lendingservice.model.flexiloan.LoanCalculatorRequest;
import com.tmb.oneapp.lendingservice.model.flexiloan.LoanCalculatorResponse;
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

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.when;

@RunWith(JUnit4.class)
class LoanCalculatorServiceTest {
    @InjectMocks
    LoanCalculatorService loanCalculatorService;

    @Mock
    private LoanSubmissionGetFacilityInfoClient getFacilityInfoClient;
    @Mock
    private LoanSubmissionGetCreditcardInfoClient getCreditCardInfoClient;
    @Mock
    private LoanSubmissionGetCustomerInfoClient getCustomerInfoClient;
    @Mock
    private LoanSubmissionGetApplicationInfoClient getApplicationInfoClient;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
        loanCalculatorService = new LoanCalculatorService(getFacilityInfoClient,getCreditCardInfoClient,getCustomerInfoClient,getApplicationInfoClient);
    }

    @Test
    public void testGetPreloadLoanCal_RC_Success() throws ServiceException, RemoteException, TMBCommonException, JsonProcessingException, ParseException {
        LoanCalculatorRequest request = new LoanCalculatorRequest();
        request.setCaId(2021071404188196L);
        request.setProduct("RC");

        doReturn(mockFacilityData().getData()).when(getFacilityInfoClient).searchFacilityInfoByCaID(anyLong());
        doReturn(mockCreditCardData().getData()).when(getCreditCardInfoClient).searchCreditcardInfoByCaID(anyLong());
        doReturn(mockCustomerData().getData()).when(getCustomerInfoClient).searchCustomerInfoByCaID(anyLong());
        doReturn(mockApplicationData().getData()).when(getApplicationInfoClient).searchApplicationInfoByCaID(anyLong());
        LoanCalculatorResponse response = loanCalculatorService.getPreloadLoanCalculator(request.getCaId(),request.getProduct());
        Assertions.assertNotNull(response);

    }

    @Test
    public void testGetPreloadLoanCal_CC_Success() throws ServiceException, RemoteException, TMBCommonException, JsonProcessingException, ParseException {
        LoanCalculatorRequest request = new LoanCalculatorRequest();
        request.setCaId(2021071404188196L);
        request.setProduct("CC");

        doReturn(mockFacilityData().getData()).when(getFacilityInfoClient).searchFacilityInfoByCaID(anyLong());
        doReturn(mockCreditCardData().getData()).when(getCreditCardInfoClient).searchCreditcardInfoByCaID(anyLong());
        doReturn(mockCustomerData().getData()).when(getCustomerInfoClient).searchCustomerInfoByCaID(anyLong());
        doReturn(mockApplicationData().getData()).when(getApplicationInfoClient).searchApplicationInfoByCaID(anyLong());
        LoanCalculatorResponse response = loanCalculatorService.getPreloadLoanCalculator(request.getCaId(),request.getProduct());
        Assertions.assertNotNull(response);

    }

    @Test
    public void testGetPreloadLoanCalFail() throws ServiceException, TMBCommonException, JsonProcessingException, RemoteException {
        LoanCalculatorRequest request = new LoanCalculatorRequest();
        request.setCaId(2021071404188196L);
        request.setProduct("CC");

        TmbOneServiceResponse<ResponseFacility> oneServiceResponse = new TmbOneServiceResponse<ResponseFacility>();
        oneServiceResponse.setStatus(new TmbStatus(ResponseCode.FAILED.getCode(), "failed", "lending-service"));
        TmbOneServiceResponse<ResponseCreditcard> oneServiceResponse1 = new TmbOneServiceResponse<ResponseCreditcard>();
        oneServiceResponse1.setStatus(new TmbStatus(ResponseCode.FAILED.getCode(), "failed", "lending-service"));
        TmbOneServiceResponse<ResponseIndividual> oneServiceResponse2 = new TmbOneServiceResponse<ResponseIndividual>();
        oneServiceResponse2.setStatus(new TmbStatus(ResponseCode.FAILED.getCode(), "failed", "lending-service"));
        TmbOneServiceResponse<ResponseApplication> oneServiceResponse3 = new TmbOneServiceResponse<ResponseApplication>();
        oneServiceResponse3.setStatus(new TmbStatus(ResponseCode.FAILED.getCode(), "failed", "lending-service"));

        when(getFacilityInfoClient.searchFacilityInfoByCaID(anyLong())).thenReturn(oneServiceResponse.getData());
        when(getCreditCardInfoClient.searchCreditcardInfoByCaID(anyLong())).thenReturn(oneServiceResponse1.getData());
        when(getCustomerInfoClient.searchCustomerInfoByCaID(anyLong())).thenReturn(oneServiceResponse2.getData());
        when(getApplicationInfoClient.searchApplicationInfoByCaID(anyLong())).thenReturn(oneServiceResponse3.getData());

        assertThrows(Exception.class, () ->
                loanCalculatorService.getPreloadLoanCalculator(request.getCaId(),request.getProduct()));

    }

    private TmbOneServiceResponse<ResponseFacility> mockFacilityData() {
        TmbOneServiceResponse<ResponseFacility> oneServiceResponse = new TmbOneServiceResponse<ResponseFacility>();
        ResponseFacility responseFacility = new ResponseFacility();
        Facility[] facilities = new Facility[1];
        Facility facility = new Facility();
        facility.setDisburstBankName("xxx");
        facility.setDisburstAccountNo("123");
        facility.setPaymentAccountName("xxx");
        facility.setPaymentAccountNo("xxx");
        facilities[0] = facility;
        com.tmb.common.model.legacy.rsl.ws.facility.response.Body body = new com.tmb.common.model.legacy.rsl.ws.facility.response.Body();
        com.tmb.common.model.legacy.rsl.ws.facility.response.Header header = new Header();
        body.setFacilities(facilities);
        header.setResponseCode("MSG_000");
        responseFacility.setBody(body);
        responseFacility.setHeader(header);
        oneServiceResponse.setStatus(new TmbStatus(ResponseCode.SUCCESS.getCode(), "success", "lending-service"));
        oneServiceResponse.setData(responseFacility);
        return oneServiceResponse;
    }


    private TmbOneServiceResponse<ResponseCreditcard> mockCreditCardData() {
        TmbOneServiceResponse<ResponseCreditcard> oneServiceResponse = new TmbOneServiceResponse<ResponseCreditcard>();
        ResponseCreditcard responseCreditcard = new ResponseCreditcard();
        CreditCard[] creditCards = new CreditCard[1];
        CreditCard creditCard = new CreditCard();
        creditCard.setDebitAccountName("xxx");
        creditCard.setDebitAccountNo("123");
        creditCards[0] = creditCard;
        com.tmb.common.model.legacy.rsl.ws.creditcard.response.Body body = new com.tmb.common.model.legacy.rsl.ws.creditcard.response.Body();
        com.tmb.common.model.legacy.rsl.ws.creditcard.response.Header header = new com.tmb.common.model.legacy.rsl.ws.creditcard.response.Header();
        body.setCreditCards(creditCards);
        header.setResponseCode("MSG_000");
        responseCreditcard.setBody(body);
        responseCreditcard.setHeader(header);
        oneServiceResponse.setStatus(new TmbStatus(ResponseCode.SUCCESS.getCode(), "success", "lending-service"));
        oneServiceResponse.setData(responseCreditcard);
        return oneServiceResponse;
    }


    private TmbOneServiceResponse<ResponseIndividual> mockCustomerData() {
        TmbOneServiceResponse<ResponseIndividual> oneServiceResponse = new TmbOneServiceResponse<ResponseIndividual>();
        ResponseIndividual responseIndividual = new ResponseIndividual();
        Individual[] individuals = new Individual[1];
        Individual individual = new Individual();
        individuals[0] = individual;
        com.tmb.common.model.legacy.rsl.ws.individual.response.Body body = new com.tmb.common.model.legacy.rsl.ws.individual.response.Body();
        com.tmb.common.model.legacy.rsl.ws.individual.response.Header header = new com.tmb.common.model.legacy.rsl.ws.individual.response.Header();

        body.setIndividuals(individuals);
        header.setResponseCode("MSG_000");
        responseIndividual.setBody(body);
        responseIndividual.setHeader(header);
        oneServiceResponse.setStatus(new TmbStatus(ResponseCode.SUCCESS.getCode(), "success", "lending-service"));
        oneServiceResponse.setData(responseIndividual);
        return oneServiceResponse;
    }

    private TmbOneServiceResponse<ResponseApplication> mockApplicationData() {
        TmbOneServiceResponse<ResponseApplication> oneServiceResponse = new TmbOneServiceResponse<ResponseApplication>();
        ResponseApplication responseApplication = new ResponseApplication();
        com.tmb.common.model.legacy.rsl.ws.application.response.Body body = new com.tmb.common.model.legacy.rsl.ws.application.response.Body();
        com.tmb.common.model.legacy.rsl.ws.application.response.Header header = new com.tmb.common.model.legacy.rsl.ws.application.response.Header();

        body.setNatureOfRequest("04");
        header.setResponseCode("MSG_000");
        responseApplication.setBody(body);
        responseApplication.setHeader(header);
        oneServiceResponse.setStatus(new TmbStatus(ResponseCode.SUCCESS.getCode(), "success", "lending-service"));
        oneServiceResponse.setData(responseApplication);
        return oneServiceResponse;
    }


}