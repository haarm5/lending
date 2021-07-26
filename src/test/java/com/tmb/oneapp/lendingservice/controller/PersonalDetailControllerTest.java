package com.tmb.oneapp.lendingservice.controller;

import com.tmb.common.exception.model.TMBCommonException;
import com.tmb.oneapp.lendingservice.model.personal.PersonalDetailRequest;
import com.tmb.oneapp.lendingservice.model.personal.PersonalDetailResponse;
import com.tmb.oneapp.lendingservice.service.PersonalDetailService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import javax.xml.rpc.ServiceException;
import java.rmi.RemoteException;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@RunWith(JUnit4.class)
public class PersonalDetailControllerTest {

    PersonalDetailController personalDetailController;

    @Mock
    PersonalDetailService personalDetailService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
        personalDetailController = new PersonalDetailController(personalDetailService);
    }

    @Test
    public void testGetPersonalDetailSuccess() throws ServiceException, RemoteException, TMBCommonException {
        PersonalDetailRequest request = new PersonalDetailRequest();
        request.setCaId(2021071404188196L);
        String crmid = "001100000000000000000018593707";
        when(personalDetailService.getPersonalDetail(any(), any())).thenReturn(any());
        PersonalDetailResponse  result = personalDetailService.getPersonalDetail(crmid,request.getCaId());
        assertNotNull(result);
    }

    @Test
    public void testGetPersonalDetailFail() throws ServiceException, RemoteException, TMBCommonException {
        PersonalDetailRequest request = new PersonalDetailRequest();
        request.setCaId(202107140L);
        String crmid = "001100000000000000000018593707";
        when(personalDetailService.getPersonalDetail(any(), any())).thenReturn(any());
        PersonalDetailResponse  result = personalDetailService.getPersonalDetail(crmid,request.getCaId());
        assertNull(result);
    }
}