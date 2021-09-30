package com.tmb.oneapp.lendingservice.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.tmb.common.exception.model.TMBCommonException;
import com.tmb.common.model.CustGeneralProfileResponse;
import com.tmb.common.model.TmbOneServiceResponse;
import com.tmb.common.model.TmbStatus;
import com.tmb.common.model.address.Province;
import com.tmb.common.model.legacy.rsl.common.ob.individual.Individual;
import com.tmb.oneapp.lendingservice.client.CommonServiceFeignClient;
import com.tmb.oneapp.lendingservice.constant.AddressTypeCode;
import com.tmb.oneapp.lendingservice.model.dropdown.Dropdowns;
import com.tmb.oneapp.lendingservice.model.loanonline.WorkingDetail;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import javax.xml.rpc.ServiceException;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.List;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.doReturn;

@RunWith(JUnit4.class)
public class LoanOnlineSubmissionGetWorkingDetailServiceTest {
    @InjectMocks
    private LoanOnlineSubmissionGetWorkingDetailService getWorkingDetailService;

    @Mock
    private LoanOnlineSubmissionGetPersonalDetailService loanOnlineSubmissionGetPersonalDetailService;
    @Mock
    private DropdownService dropdownService;
    @Mock
    private CommonServiceFeignClient commonServiceFeignClient;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void getWorkingDetail_PersonalInfoNotSaved_Success() throws TMBCommonException, ServiceException, RemoteException, JsonProcessingException {

        doReturn(mockIndividual()).when(loanOnlineSubmissionGetPersonalDetailService).getCustomer(anyLong());
        doReturn(mockCustGeneralProfileResponse()).when(loanOnlineSubmissionGetPersonalDetailService).getCustomerEC(anyString());
        doReturn(false).when(loanOnlineSubmissionGetPersonalDetailService).personalInfoSaved(any());
        doReturn("01").when(dropdownService).getEmploymentStatus(anyString());
        doReturn(new ResponseEntity<>(mockProvinces(), HttpStatus.OK)).when(commonServiceFeignClient).getProvince(any());

        List<Dropdowns.IncomeType> dropdownIncomeType = new ArrayList<>();
        Dropdowns.IncomeType incomeType = Dropdowns.IncomeType.builder().code("1").build();
        dropdownIncomeType.add(incomeType);
        doReturn(dropdownIncomeType).when(dropdownService).getDropdownIncomeType(anyString());

        WorkingDetail response = getWorkingDetailService.getWorkingDetail("crmId", 1L);
        Assertions.assertNotNull(response);
    }

    @Test
    public void getWorkingDetail_PersonalInfoSaved_Success() throws TMBCommonException, ServiceException, RemoteException, JsonProcessingException {
        doReturn(mockIndividual()).when(loanOnlineSubmissionGetPersonalDetailService).getCustomer(anyLong());
        doReturn(true).when(loanOnlineSubmissionGetPersonalDetailService).personalInfoSaved(any());
        doReturn(new ResponseEntity<>(mockProvinces(), HttpStatus.OK)).when(commonServiceFeignClient).getProvince(any());

        WorkingDetail response = getWorkingDetailService.getWorkingDetail("crmId", 1L);
        Assertions.assertNotNull(response);
    }

    private TmbOneServiceResponse<List<Province>> mockProvinces() {
        TmbOneServiceResponse<List<Province>> mockProvince = new TmbOneServiceResponse<>();
        var status = new TmbStatus();
        status.setCode("0000");
        mockProvince.setStatus(status);
        var mockList = new ArrayList<Province>();
        mockList.add(new Province());
        mockProvince.setData(mockList);
        return mockProvince;
    }

    private CustGeneralProfileResponse mockCustGeneralProfileResponse() {
        CustGeneralProfileResponse customerInfo = new CustGeneralProfileResponse();
        customerInfo.setOccupationCode("01");
        customerInfo.setBusinessTypeCode("123456789");
        return customerInfo;
    }

    private Individual mockIndividual() {
        Individual individual = new Individual();
        com.tmb.common.model.legacy.rsl.common.ob.address.Address address = new com.tmb.common.model.legacy.rsl.common.ob.address.Address();
        address.setAddrTypCode(AddressTypeCode.WORKING.getCode());
        com.tmb.common.model.legacy.rsl.common.ob.address.Address[] addresses = {address};
        individual.setAddresses(addresses);
        return individual;
    }


}
