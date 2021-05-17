package com.tmb.oneapp.lendingservice.service;

import com.tmb.common.model.legacy.rsl.common.ob.dropdown.CommonCodeEntry;
import com.tmb.common.model.legacy.rsl.ws.dropdown.response.Body;
import com.tmb.common.model.legacy.rsl.ws.dropdown.response.Header;
import com.tmb.common.model.legacy.rsl.ws.dropdown.response.ResponseDropdown;
import com.tmb.common.model.legacy.rsl.ws.loan.submission.LoanSubmissionGetDropdownListServiceLocator;
import com.tmb.common.model.legacy.rsl.ws.loan.submission.LoanSubmissionGetDropdownListSoapBindingStub;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import javax.xml.rpc.ServiceException;
import java.rmi.RemoteException;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@RunWith(JUnit4.class)
public class CodeEntriesServiceTest {

    CodeEntriesService codeEntriesService;
    @Mock
    LoanSubmissionGetDropdownListServiceLocator mockLocator;

    @Mock
    LoanSubmissionGetDropdownListSoapBindingStub mockStub;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    void loadEntrySuccess() throws ServiceException, RemoteException {
        codeEntriesService = new CodeEntriesService();
        ResponseDropdown mockResponse = new ResponseDropdown();
        Body body = new Body();
        CommonCodeEntry item1 = new CommonCodeEntry();
        CommonCodeEntry[] entries = new CommonCodeEntry[]{item1};
        body.setCommonCodeEntries(entries);
        mockResponse.setBody(body);
        Header header = new Header();
        header.setResponseCode("MSG_000");
        mockResponse.setHeader(header);
        when(mockStub.getDropDownListByCode(any())).thenReturn(mockResponse);
        when(mockLocator.getLoanSubmissionGetDropdownList()).thenReturn(mockStub);
        codeEntriesService.setLocator(mockLocator);
        List<CommonCodeEntry> actualResponse = codeEntriesService.loadEntry("", "", "", "");
        Assertions.assertNotNull(actualResponse);
        Assertions.assertEquals(1, actualResponse.size());
    }

    @Test
    void loadEntryHandleError() throws ServiceException, RemoteException {
        codeEntriesService = new CodeEntriesService();
        ResponseDropdown mockResponse = new ResponseDropdown();
        Header header = new Header();
        header.setResponseCode("MSG_001");
        mockResponse.setHeader(header);
        when(mockStub.getDropDownListByCode(any())).thenReturn(mockResponse);
        when(mockLocator.getLoanSubmissionGetDropdownList()).thenReturn(mockStub);
        codeEntriesService.setLocator(mockLocator);
        List<CommonCodeEntry> actualResponse = codeEntriesService.loadEntry("", "", "", "");
        Assertions.assertNotNull(actualResponse);
        Assertions.assertEquals(0, actualResponse.size());
    }
}
