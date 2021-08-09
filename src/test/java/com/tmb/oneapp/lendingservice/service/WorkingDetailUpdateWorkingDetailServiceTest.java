package com.tmb.oneapp.lendingservice.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.tmb.common.exception.model.TMBCommonException;
import com.tmb.common.model.legacy.rsl.common.ob.individual.Individual;
import com.tmb.common.model.legacy.rsl.ws.individual.response.Body;
import com.tmb.common.model.legacy.rsl.ws.individual.response.Header;
import com.tmb.common.model.legacy.rsl.ws.individual.response.ResponseIndividual;
import com.tmb.oneapp.lendingservice.client.LoanSubmissionGetCustomerInfoClient;
import com.tmb.oneapp.lendingservice.client.LoanSubmissionUpdateCustomerClient;
import com.tmb.common.model.legacy.rsl.common.ob.address.Address;
import com.tmb.oneapp.lendingservice.model.loanonline.UpdateWorkingDetailRequest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;

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


    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
        workingDetailUpdateWorkingDetailService = new WorkingDetailUpdateWorkingDetailService(customerInfoClient, loanSubmissionUpdateCustomerClient);
    }

    @Test
    public void testUpdateWorkDetailSuccess() throws ServiceException, TMBCommonException, RemoteException, JsonProcessingException {

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


       var res =  new com.tmb.common.model.legacy.rsl.ws.individual.update.response.ResponseIndividual();
        com.tmb.common.model.legacy.rsl.ws.individual.update.response.Header header2 = new com.tmb.common.model.legacy.rsl.ws.individual.update.response.Header();
        header2.setResponseCode("MSG_000");
        res.setHeader(header2);
        when(loanSubmissionUpdateCustomerClient.updateCustomerInfo(any())).thenReturn(res);

        var address = new com.tmb.oneapp.lendingservice.model.personal.Address();
        var req = new UpdateWorkingDetailRequest();
        req.setAddress(address);
        req.setCaId(12L);
        com.tmb.common.model.legacy.rsl.ws.individual.update.response.ResponseIndividual result = workingDetailUpdateWorkingDetailService.updateWorkDetail(req);
    }

}