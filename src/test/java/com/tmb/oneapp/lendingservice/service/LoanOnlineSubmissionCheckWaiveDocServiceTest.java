package com.tmb.oneapp.lendingservice.service;

import com.tmb.common.model.CustGeneralProfileResponse;
import com.tmb.common.model.TmbOneServiceResponse;
import com.tmb.common.model.legacy.rsl.common.ob.dropdown.CommonCodeEntry;
import com.tmb.common.model.legacy.rsl.ws.dropdown.response.ResponseDropdown;
import com.tmb.common.model.legacy.rsl.ws.incomemodel.response.Body;
import com.tmb.common.model.legacy.rsl.ws.incomemodel.response.ResponseIncomeModel;
import com.tmb.oneapp.lendingservice.client.CustomerServiceClient;
import com.tmb.oneapp.lendingservice.client.LoanSubmissionGetDropdownListClient;
import com.tmb.oneapp.lendingservice.client.LoanSubmissionGetIncomeModelInfoClient;
import com.tmb.oneapp.lendingservice.model.loanonline.IncomeInfo;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;

import javax.xml.rpc.ServiceException;
import java.math.BigDecimal;
import java.rmi.RemoteException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@RunWith(JUnit4.class)
class LoanOnlineSubmissionCheckWaiveDocServiceTest {

    @Mock
    private LoanSubmissionGetIncomeModelInfoClient loanSubmissionGetIncomeModelInfoClient;
    @Mock
    private CustomerServiceClient customerServiceClient;
    @Mock
    private LoanSubmissionGetDropdownListClient getDropdownListClient;

    LoanOnlineSubmissionCheckWaiveDocService loanOnlineSubmissionCheckWaiveDocService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
        loanOnlineSubmissionCheckWaiveDocService = new LoanOnlineSubmissionCheckWaiveDocService(loanSubmissionGetIncomeModelInfoClient, customerServiceClient, getDropdownListClient);
    }

    @Test
    public void testGetIncomeInfoByRmIdNotReturnStatus() throws ServiceException, RemoteException {
        ResponseIncomeModel clientRes = new ResponseIncomeModel();
        Body body = new Body();
        body.setIncomeModelAmt(BigDecimal.valueOf(100));
        clientRes.setBody(body);
        when(loanSubmissionGetIncomeModelInfoClient.getIncomeInfo(any())).thenReturn(clientRes);
        IncomeInfo result = loanOnlineSubmissionCheckWaiveDocService.getIncomeInfoByRmId("rmId");
        assertEquals(BigDecimal.valueOf(100), result.getIncomeAmount());
    }

    @Test
    public void testGetIncomeInfoByRmIdReturnWithStatusSalary() throws ServiceException, RemoteException {
        ResponseIncomeModel clientRes = new ResponseIncomeModel();
        Body body = new Body();
        body.setIncomeModelAmt(BigDecimal.valueOf(100));
        clientRes.setBody(body);
        when(loanSubmissionGetIncomeModelInfoClient.getIncomeInfo(any())).thenReturn(clientRes);

        TmbOneServiceResponse<CustGeneralProfileResponse> customerModuleResponse = new TmbOneServiceResponse<>();
        CustGeneralProfileResponse profile = new CustGeneralProfileResponse();
        profile.setOccupationCode("306");
        customerModuleResponse.setData(profile);
        when(customerServiceClient.getCustomers(any())).thenReturn(ResponseEntity.ok(customerModuleResponse));

        ResponseDropdown dropdown = new ResponseDropdown();
        CommonCodeEntry[] entrycodes = new CommonCodeEntry[1];
        entrycodes[0] = new CommonCodeEntry();
        entrycodes[0].setEntryCode("306");
        entrycodes[0].setExtValue1("01");
        com.tmb.common.model.legacy.rsl.ws.dropdown.response.Body dropdownBody = new com.tmb.common.model.legacy.rsl.ws.dropdown.response.Body();
        dropdownBody.setCommonCodeEntries(entrycodes);
        dropdown.setBody(dropdownBody);
        when(getDropdownListClient.getDropdownList(any())).thenReturn(dropdown);

        IncomeInfo result = loanOnlineSubmissionCheckWaiveDocService.getIncomeInfoByRmId("rmId");
        assertEquals(BigDecimal.valueOf(100), result.getIncomeAmount());
        assertEquals("salary", result.getStatusWorking());
    }

    @Test
    public void testGetIncomeInfoByRmIdReturnWithStatusSelfEmployed() throws ServiceException, RemoteException {
        ResponseIncomeModel clientRes = new ResponseIncomeModel();
        Body body = new Body();
        body.setIncomeModelAmt(BigDecimal.valueOf(100));
        clientRes.setBody(body);
        when(loanSubmissionGetIncomeModelInfoClient.getIncomeInfo(any())).thenReturn(clientRes);

        TmbOneServiceResponse<CustGeneralProfileResponse> customerModuleResponse = new TmbOneServiceResponse<>();
        CustGeneralProfileResponse profile = new CustGeneralProfileResponse();
        profile.setOccupationCode("306");
        customerModuleResponse.setData(profile);
        when(customerServiceClient.getCustomers(any())).thenReturn(ResponseEntity.ok(customerModuleResponse));

        ResponseDropdown dropdown = new ResponseDropdown();
        CommonCodeEntry[] entryCodes = new CommonCodeEntry[1];
        entryCodes[0] = new CommonCodeEntry();
        entryCodes[0].setEntryCode("306");
        entryCodes[0].setExtValue1("02");
        com.tmb.common.model.legacy.rsl.ws.dropdown.response.Body dropdownBody = new com.tmb.common.model.legacy.rsl.ws.dropdown.response.Body();
        dropdownBody.setCommonCodeEntries(entryCodes);
        dropdown.setBody(dropdownBody);
        when(getDropdownListClient.getDropdownList(any())).thenReturn(dropdown);

        IncomeInfo result = loanOnlineSubmissionCheckWaiveDocService.getIncomeInfoByRmId("rmId");
        assertEquals(BigDecimal.valueOf(100), result.getIncomeAmount());
        assertEquals("self_employed", result.getStatusWorking());
    }
}