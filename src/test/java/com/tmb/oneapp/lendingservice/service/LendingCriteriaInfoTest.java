package com.tmb.oneapp.lendingservice.service;

import com.tmb.common.exception.model.TMBCommonException;
import com.tmb.common.model.legacy.rsl.common.ob.dropdown.CommonCodeEntry;
import com.tmb.common.model.legacy.rsl.ws.dropdown.response.Body;
import com.tmb.common.model.legacy.rsl.ws.dropdown.response.Header;
import com.tmb.common.model.legacy.rsl.ws.dropdown.response.ResponseDropdown;
import com.tmb.common.model.legacy.rsl.ws.loan.submission.LoanSubmissionGetDropdownListServiceLocator;
import com.tmb.common.model.legacy.rsl.ws.loan.submission.LoanSubmissionGetDropdownListSoapBindingStub;
import com.tmb.oneapp.lendingservice.LendingModuleCache;
import com.tmb.oneapp.lendingservice.constant.LoanCategory;
import com.tmb.oneapp.lendingservice.model.CriteriaCodeEntry;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import javax.xml.rpc.ServiceException;
import java.math.BigDecimal;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.when;

@RunWith(JUnit4.class)
public class LendingCriteriaInfoTest {

    @InjectMocks
    LendingCriteriaInfoService lendingCriteriaInfoService;
    @Mock
    LoanSubmissionGetDropdownListServiceLocator mockLocator;
    @Mock
    LoanSubmissionGetDropdownListSoapBindingStub mockStub;
    @Mock
    LendingModuleCache lendingModuleCache;
    @Mock
    CodeEntriesService codeEntriesService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    void getCatlogIdByLoanType() throws ServiceException, RemoteException {

        codeEntriesService = new CodeEntriesService();
        lendingCriteriaInfoService = new LendingCriteriaInfoService(lendingModuleCache);
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

        List<CriteriaCodeEntry> criteriaList = new ArrayList();

        when(lendingCriteriaInfoService.getBusinessTypeCode(any())).thenReturn(criteriaList);
        codeEntriesService.setLocator(mockLocator);
        List<CriteriaCodeEntry> actualResponse = lendingCriteriaInfoService
                .getBusinessTypeCode(LoanCategory.BUSINESS_SUB_TYPE.getCode());
        Assertions.assertNotNull(actualResponse);
        Assertions.assertEquals(0, actualResponse.size());

        lendingCriteriaInfoService.getDefaultforCountryType(null);

        Assertions.assertTrue(true);
    }

    @Test
    public void getBusinessTypeCode() {
        lendingCriteriaInfoService = new LendingCriteriaInfoService(lendingModuleCache);
        CommonCodeEntry codeEntry = new CommonCodeEntry();
        codeEntry.setActiveStatus("1");
        codeEntry.setCategoryCode("INCOME_TYPE");
        codeEntry.setEntryCode("1");
        codeEntry.setEntryID(new BigDecimal("69842"));
        codeEntry.setEntryName("ABC");
        codeEntry.setEntryName2("AAAA");
        List<CommonCodeEntry> status = new ArrayList();
        status.add(codeEntry);
        when(lendingModuleCache.getListByCategoryCode(any())).thenReturn(status);
        lendingCriteriaInfoService.getBusinessTypeCode(LoanCategory.EMPLOYMENT_STATUS.getCode());
        Assertions.assertTrue(true);
    }

    @Test
    public void getCriteriaByCatalogId() {
        lendingCriteriaInfoService = new LendingCriteriaInfoService(lendingModuleCache);
        CommonCodeEntry codeEntry = new CommonCodeEntry();
        codeEntry.setActiveStatus("1");
        codeEntry.setCategoryCode("INCOME_TYPE");
        codeEntry.setEntryCode("1");
        codeEntry.setEntryID(new BigDecimal("69842"));
        codeEntry.setEntryName("ABC");
        codeEntry.setEntryName2("AAAA");
        List<CommonCodeEntry> status = new ArrayList();
        status.add(codeEntry);
        when(lendingModuleCache.getListByCategoryCode(any())).thenReturn(status);
        lendingCriteriaInfoService.getCriteriaByCatalogId(LoanCategory.BUSINESS_SUB_TYPE);
        Assertions.assertTrue(true);
    }

    @Test
    public void getDefaultforCountryType() {
        lendingCriteriaInfoService = new LendingCriteriaInfoService(lendingModuleCache);
        CommonCodeEntry codeEntry = new CommonCodeEntry();
        codeEntry.setActiveStatus("1");
        codeEntry.setCategoryCode("INCOME_TYPE");
        codeEntry.setEntryCode("1");
        codeEntry.setEntryID(new BigDecimal("69842"));
        codeEntry.setEntryName("ABC");
        codeEntry.setEntryName2("AAAA");
        List<CommonCodeEntry> status = new ArrayList();
        status.add(codeEntry);
        when(lendingModuleCache.getListByCategoryCode(any())).thenReturn(status);
        lendingCriteriaInfoService.getDefaultforCountryType("TH");
        Assertions.assertTrue(true);
    }

    @Test
    public void getDefaultforSubBusinessType() {
        lendingCriteriaInfoService = new LendingCriteriaInfoService(lendingModuleCache);
        CommonCodeEntry codeEntry = new CommonCodeEntry();
        codeEntry.setActiveStatus("1");
        codeEntry.setCategoryCode("INCOME_TYPE");
        codeEntry.setEntryCode("1");
        codeEntry.setEntryID(new BigDecimal("69842"));
        codeEntry.setEntryName("ABC");
        codeEntry.setEntryName2("AAAA");
        List<CommonCodeEntry> status = new ArrayList();
        status.add(codeEntry);
        when(lendingModuleCache.getListByCategoryCode(any())).thenReturn(status);
        lendingCriteriaInfoService.getDefaultforSubBusinessType("A");
        Assertions.assertTrue(true);
    }

    @Test
    public void getOccupationByEmploymentStatus() {
        lendingCriteriaInfoService = new LendingCriteriaInfoService(lendingModuleCache);
        CommonCodeEntry codeEntry = new CommonCodeEntry();
        codeEntry.setActiveStatus("1");
        codeEntry.setCategoryCode("INCOME_TYPE");
        codeEntry.setEntryCode("1");
        codeEntry.setEntryID(new BigDecimal("69842"));
        codeEntry.setEntryName("ABC");
        codeEntry.setEntryName2("AAAA");
        codeEntry.setExtValue1("");
        codeEntry.setExtValue2("");
        List<CommonCodeEntry> status = new ArrayList();
        status.add(codeEntry);
        when(lendingModuleCache.getListByCategoryCode(any())).thenReturn(status);
        lendingCriteriaInfoService.getOccupationByEmploymentStatus("VB");
        Assertions.assertTrue(true);
    }

    @Test
    public void getOccupationInfoByCode() {
        lendingCriteriaInfoService = new LendingCriteriaInfoService(lendingModuleCache);
        CommonCodeEntry codeEntry = new CommonCodeEntry();
        codeEntry.setActiveStatus("1");
        codeEntry.setCategoryCode("INCOME_TYPE");
        codeEntry.setEntryCode("1");
        codeEntry.setEntryID(new BigDecimal("69842"));
        codeEntry.setEntryName("ABC");
        codeEntry.setEntryName2("AAAA");
        List<CommonCodeEntry> status = new ArrayList();
        status.add(codeEntry);
        when(lendingModuleCache.getListByCategoryCode(any())).thenReturn(status);
        lendingCriteriaInfoService.getOccupationInfoByCode(LoanCategory.RM_OCCUPATION.getCode());
        Assertions.assertTrue(true);
    }

    @Test
    public void getSourceOfIncome() {
        lendingCriteriaInfoService = new LendingCriteriaInfoService(lendingModuleCache);
        CommonCodeEntry codeEntry = new CommonCodeEntry();
        codeEntry.setActiveStatus("1");
        codeEntry.setCategoryCode("INCOME_TYPE");
        codeEntry.setEntryCode("1");
        codeEntry.setEntryID(new BigDecimal("69842"));
        codeEntry.setEntryName("ABC");
        codeEntry.setEntryName2("AAAA");
        List<CommonCodeEntry> status = new ArrayList();
        status.add(codeEntry);
        when(lendingModuleCache.getListByCategoryCode(any())).thenReturn(status);
        List<CriteriaCodeEntry> sourceOfIncome = lendingCriteriaInfoService.getSourceOfIncome(LoanCategory.RM_OCCUPATION.getCode());
        Assertions.assertTrue(true);
    }

    @Test
    public void getSubBusinessType() {
        lendingCriteriaInfoService = new LendingCriteriaInfoService(lendingModuleCache);
        CommonCodeEntry codeEntry = new CommonCodeEntry();
        codeEntry.setActiveStatus("1");
        codeEntry.setCategoryCode("INCOME_TYPE");
        codeEntry.setEntryCode("1");
        codeEntry.setEntryID(new BigDecimal("69842"));
        codeEntry.setEntryName("ABC");
        codeEntry.setEntryName2("AAAA");
        List<CommonCodeEntry> status = new ArrayList();
        status.add(codeEntry);
        when(lendingModuleCache.getListByCategoryCode(any())).thenReturn(status);
        lendingCriteriaInfoService.getSubBusinessType(LoanCategory.RM_OCCUPATION.getCode());
        Assertions.assertTrue(true);
    }

    @Test
    public void getDropdownEmploymentStatus_Success() {
        lendingCriteriaInfoService = new LendingCriteriaInfoService(lendingModuleCache);
        doReturn(mockEmploymentStatus()).when(lendingModuleCache).getListByCategoryCode(any());

        List<CriteriaCodeEntry> response = lendingCriteriaInfoService.getEmploymentStatus();
        Assertions.assertEquals(3, response.size());
    }

    @Test
    public void getWorkStatusByOccupationCode() {
        lendingCriteriaInfoService = new LendingCriteriaInfoService(lendingModuleCache);
        CommonCodeEntry codeEntry = new CommonCodeEntry();
        codeEntry.setActiveStatus("1");
        codeEntry.setCategoryCode("INCOME_TYPE");
        codeEntry.setEntryCode("1");
        codeEntry.setEntryID(new BigDecimal("69842"));
        codeEntry.setEntryName("ABC");
        codeEntry.setEntryName2("AAAA");
        List<CommonCodeEntry> status = new ArrayList();
        status.add(codeEntry);
        when(lendingModuleCache.getListByCategoryCode(any())).thenReturn(status);
        lendingCriteriaInfoService.getWorkStatusByOccupationCode(LoanCategory.RM_OCCUPATION.getCode());
        Assertions.assertTrue(true);
    }

    @Test
    public void getOccupationsByRmOccupation01_Success() throws TMBCommonException {
        lendingCriteriaInfoService = new LendingCriteriaInfoService(lendingModuleCache);
        doReturn(mockOccupation()).when(lendingModuleCache).getListByCategoryCode(any());

        String rmOccupation = "01";
        List<CriteriaCodeEntry> response = lendingCriteriaInfoService.getOccupationsByRmOccupation(rmOccupation);
        Assertions.assertEquals(3, response.size());
    }

    @Test
    public void getOccupationsByRmOccupation02_Success() throws TMBCommonException {
        lendingCriteriaInfoService = new LendingCriteriaInfoService(lendingModuleCache);
        doReturn(mockOccupation()).when(lendingModuleCache).getListByCategoryCode(any());

        String rmOccupation = "02";
        List<CriteriaCodeEntry> response = lendingCriteriaInfoService.getOccupationsByRmOccupation(rmOccupation);
        Assertions.assertEquals(4, response.size());
    }

    @Test
    public void getCountry_Success() {
        lendingCriteriaInfoService = new LendingCriteriaInfoService(lendingModuleCache);
        doReturn(mockGetCountry()).when(lendingModuleCache).getListByCategoryCode(any());

        List<CriteriaCodeEntry> response = lendingCriteriaInfoService.getCountry();
        Assertions.assertEquals(2, response.size());
    }

    @Test
    public void getBusinessType_Success() {
        lendingCriteriaInfoService = new LendingCriteriaInfoService(lendingModuleCache);
        doReturn(mockBusinessType()).when(lendingModuleCache).getListByCategoryCode(any());

        List<CriteriaCodeEntry> response = lendingCriteriaInfoService.getBusinessType();
        Assertions.assertEquals(2, response.size());
    }

    @Test
    public void getPayrollBank_Success() {
        lendingCriteriaInfoService = new LendingCriteriaInfoService(lendingModuleCache);
        doReturn(mockPayrollBank()).when(lendingModuleCache).getListByCategoryCode(any());

        List<CriteriaCodeEntry> response = lendingCriteriaInfoService.getPayrollBank();
        Assertions.assertEquals(2, response.size());
    }

    @Test
    public void getMaritalStatusByCode_Success() {
        lendingCriteriaInfoService = new LendingCriteriaInfoService(lendingModuleCache);
        doReturn(mockMaritalStatus()).when(lendingModuleCache).getListByCategoryCode(any());

        List<CriteriaCodeEntry> response = lendingCriteriaInfoService.getMaritalStatusByCode("M");
        Assertions.assertEquals(1, response.size());
    }

    @Test
    public void getResidentTypeByCode_Success() {
        lendingCriteriaInfoService = new LendingCriteriaInfoService(lendingModuleCache);
        doReturn(mockResidentType()).when(lendingModuleCache).getListByCategoryCode(any());

        List<CriteriaCodeEntry> response = lendingCriteriaInfoService.getResidentTypeByCode("0");
        Assertions.assertEquals(1, response.size());
    }

    @Test
    public void getEducationLevel_Success() {
        lendingCriteriaInfoService = new LendingCriteriaInfoService(lendingModuleCache);
        doReturn(mockEducationLevel()).when(lendingModuleCache).getListByCategoryCode(any());

        List<CriteriaCodeEntry> response = lendingCriteriaInfoService.getEducationLevel("04");
        Assertions.assertEquals(1, response.size());
    }

    private List<CommonCodeEntry> mockOccupation() {
        List<CommonCodeEntry> commonCodeEntries = new ArrayList<>();

        CommonCodeEntry rmOccupation01 = new CommonCodeEntry();
        rmOccupation01.setActiveStatus("1");
        rmOccupation01.setCategoryCode("PROFFESIONAL");
        rmOccupation01.setEntryCode("101");
        rmOccupation01.setEntryID(BigDecimal.valueOf(80347));
        rmOccupation01.setEntryName("แพทย์ / ทันตแพทย์ / สัตวแพทย์");
        rmOccupation01.setEntryName2("แพทย์ / ทันตแพทย์ / สัตวแพทย์");
        rmOccupation01.setEntrySource("01,02,03,04,05,06,07,08,09,10,11,12,13,14");
        rmOccupation01.setExtValue1("");
        rmOccupation01.setExtValue2("01");
        rmOccupation01.setGroupId("");
        rmOccupation01.setRefEntryCode("");

        CommonCodeEntry rmOccupation02 = new CommonCodeEntry();
        rmOccupation02.setActiveStatus("1");
        rmOccupation02.setCategoryCode("PROFFESIONAL");
        rmOccupation02.setEntryCode("10");
        rmOccupation02.setEntryID(BigDecimal.valueOf(80356));
        rmOccupation02.setEntryName("อาจารย์ (ปวช ปวส มหาวิทยาลัย)");
        rmOccupation02.setEntryName2("อาจารย์ (ปวช ปวส มหาวิทยาลัย)");
        rmOccupation02.setEntrySource("01,02,03,04,05,06,07,08,09,10,11,12,13,14");
        rmOccupation02.setExtValue1("");
        rmOccupation02.setExtValue2("01");
        rmOccupation02.setGroupId("");
        rmOccupation02.setRefEntryCode("");

        CommonCodeEntry rmOccupation03 = new CommonCodeEntry();
        rmOccupation03.setActiveStatus("1");
        rmOccupation03.setCategoryCode("PROFFESIONAL");
        rmOccupation03.setEntryCode("13");
        rmOccupation03.setEntryID(BigDecimal.valueOf(80359));
        rmOccupation03.setEntryName("อื่นๆ");
        rmOccupation03.setEntryName2("อื่นๆ");
        rmOccupation03.setEntrySource("01,02,03,04,05,06,07,08,09,10,11,12,13,14");
        rmOccupation03.setExtValue1("");
        rmOccupation03.setExtValue2("01");
        rmOccupation03.setGroupId("");
        rmOccupation03.setRefEntryCode("");

        CommonCodeEntry rmOccupation04 = new CommonCodeEntry();
        rmOccupation04.setActiveStatus("1");
        rmOccupation04.setCategoryCode("PROFFESIONAL");
        rmOccupation04.setEntryCode("19");
        rmOccupation04.setEntryID(BigDecimal.valueOf(85438));
        rmOccupation04.setEntryName("พนักงานราชการ");
        rmOccupation04.setEntryName2("พนักงานราชการ");
        rmOccupation04.setEntrySource("02");
        rmOccupation04.setExtValue1("");
        rmOccupation04.setExtValue2("01");
        rmOccupation04.setGroupId("");
        rmOccupation04.setRefEntryCode("");

        CommonCodeEntry rmOccupation05 = new CommonCodeEntry();
        rmOccupation05.setActiveStatus("1");
        rmOccupation05.setCategoryCode("PROFFESIONAL");
        rmOccupation05.setEntryCode("20");
        rmOccupation05.setEntryID(BigDecimal.valueOf(80347));
        rmOccupation05.setEntryName("ลูกจ้างประจำหน่วยงานราชการ");
        rmOccupation05.setEntryName2("ลูกจ้างประจำหน่วยงานราชการ");
        rmOccupation05.setEntrySource("02");
        rmOccupation05.setExtValue1("");
        rmOccupation05.setExtValue2("02");
        rmOccupation05.setGroupId("");
        rmOccupation05.setRefEntryCode("");

        CommonCodeEntry rmOccupationNotActive = new CommonCodeEntry();
        rmOccupation05.setActiveStatus("0");
        rmOccupation05.setCategoryCode("RM_OCCUPATION");
        rmOccupation05.setEntryCode("3");
        rmOccupation05.setEntryID(BigDecimal.valueOf(80349));
        rmOccupation05.setEntryName("Test Not Active");
        rmOccupation05.setEntryName2("ทดสอบ not active");
        rmOccupation05.setEntrySource("01,02,03,04,05,06,07,08,09,10,11,12,13,14");
        rmOccupation05.setExtValue1("");
        rmOccupation05.setExtValue2("02");
        rmOccupation05.setGroupId("");
        rmOccupation04.setRefEntryCode("");

        commonCodeEntries.add(rmOccupation01);
        commonCodeEntries.add(rmOccupation02);
        commonCodeEntries.add(rmOccupation03);
        commonCodeEntries.add(rmOccupation04);
        commonCodeEntries.add(rmOccupation05);
        commonCodeEntries.add(rmOccupationNotActive);

        return commonCodeEntries;
    }

    private List<CommonCodeEntry> mockEmploymentStatus() {
        List<CommonCodeEntry> commonCodeEntries = new ArrayList<>();

        CommonCodeEntry empStatus01 = new CommonCodeEntry();
        empStatus01.setActiveStatus("1");
        empStatus01.setCategoryCode("EMPLOYMENT_STATUS");
        empStatus01.setEntryCode("101");
        empStatus01.setEntryID(BigDecimal.valueOf(73079));
        empStatus01.setEntryName("Salary-Cash");
        empStatus01.setEntryName2("รับรายได้ด้วยเงินสด");
        empStatus01.setExtValue1("");
        empStatus01.setExtValue2("");
        empStatus01.setGroupId("");
        empStatus01.setRefEntryCode("MO, CC, PL, SM, SP");

        CommonCodeEntry empStatus02 = new CommonCodeEntry();
        empStatus02.setActiveStatus("1");
        empStatus02.setCategoryCode("EMPLOYMENT_STATUS");
        empStatus02.setEntryCode("02");
        empStatus02.setEntryID(BigDecimal.valueOf(73080));
        empStatus02.setEntryName("Self Employed");
        empStatus02.setEntryName2("เจ้าของกิจการ");
        empStatus02.setExtValue1("");
        empStatus02.setExtValue2("");
        empStatus02.setGroupId("");
        empStatus02.setRefEntryCode("MO, CC, PL, SM, SP, SB");

        CommonCodeEntry empStatus03 = new CommonCodeEntry();
        empStatus03.setActiveStatus("1");
        empStatus03.setCategoryCode("EMPLOYMENT_STATUS");
        empStatus03.setEntryCode("03");
        empStatus03.setEntryID(BigDecimal.valueOf(73081));
        empStatus03.setEntryName("Not Working / Other Unemployed / Others");
        empStatus03.setEntryName2("ผู้ไม่มีรายได้/ อื่นๆ");
        empStatus03.setExtValue1("");
        empStatus03.setExtValue2("");
        empStatus03.setGroupId("");
        empStatus03.setRefEntryCode("MO, CC, PL, SM, SP");

        CommonCodeEntry empStatusNotActive = new CommonCodeEntry();
        empStatusNotActive.setActiveStatus("0");
        empStatusNotActive.setCategoryCode("EMPLOYMENT_STATUS");
        empStatusNotActive.setEntryCode("04");
        empStatusNotActive.setEntryID(BigDecimal.valueOf(73081));
        empStatusNotActive.setEntryName("Test Dropdown Not Active");
        empStatusNotActive.setEntryName2("ทดสอบ dropdown not active");
        empStatusNotActive.setExtValue1("");
        empStatusNotActive.setExtValue2("");
        empStatusNotActive.setGroupId("");
        empStatusNotActive.setRefEntryCode("MO, CC, PL, SM, SP");

        commonCodeEntries.add(empStatus01);
        commonCodeEntries.add(empStatus02);
        commonCodeEntries.add(empStatus03);
        commonCodeEntries.add(empStatusNotActive);

        return commonCodeEntries;
    }

    private List<CommonCodeEntry> mockGetCountry() {
        List<CommonCodeEntry> commonCodeEntries = new ArrayList<>();

        CommonCodeEntry sciCountry01 = new CommonCodeEntry();
        sciCountry01.setActiveStatus("1");
        sciCountry01.setCategoryCode("SCI_COUNTRY");
        sciCountry01.setEntryCode("AD");
        sciCountry01.setEntryID(BigDecimal.valueOf(66624));
        sciCountry01.setEntryName("Andorra");
        sciCountry01.setEntryName2("Andorra");
        sciCountry01.setEntrySource("LOS");
        sciCountry01.setExtValue1("");
        sciCountry01.setExtValue2("");
        sciCountry01.setGroupId("");
        sciCountry01.setRefEntryCode("");

        CommonCodeEntry sciCountry02 = new CommonCodeEntry();
        sciCountry02.setActiveStatus("1");
        sciCountry02.setCategoryCode("SCI_COUNTRY");
        sciCountry02.setEntryCode("AE");
        sciCountry02.setEntryID(BigDecimal.valueOf(66625));
        sciCountry02.setEntryName("United Arab Emirates");
        sciCountry02.setEntryName2("United Arab Emirates");
        sciCountry02.setEntrySource("LOS");
        sciCountry02.setExtValue1("");
        sciCountry02.setExtValue2("");
        sciCountry02.setGroupId("");
        sciCountry02.setRefEntryCode("");

        CommonCodeEntry sciCountryNotActive = new CommonCodeEntry();
        sciCountryNotActive.setActiveStatus("0");
        sciCountryNotActive.setCategoryCode("SCI_COUNTRY");
        sciCountryNotActive.setEntryCode("A");
        sciCountryNotActive.setEntryID(BigDecimal.valueOf(66626));
        sciCountryNotActive.setEntryName("Afghanistan");
        sciCountryNotActive.setEntryName2("Afghanistan");
        sciCountryNotActive.setEntrySource("LOS");
        sciCountryNotActive.setExtValue1("");
        sciCountryNotActive.setExtValue2("");
        sciCountryNotActive.setGroupId("");
        sciCountryNotActive.setRefEntryCode("");

        commonCodeEntries.add(sciCountry01);
        commonCodeEntries.add(sciCountry02);
        commonCodeEntries.add(sciCountryNotActive);

        return commonCodeEntries;
    }

    private List<CommonCodeEntry> mockBusinessType() {
        List<CommonCodeEntry> commonCodeEntries = new ArrayList<>();

        CommonCodeEntry businessType01 = new CommonCodeEntry();
        businessType01.setActiveStatus("1");
        businessType01.setCategoryCode("BUSINESS_TYPE");
        businessType01.setEntryCode("A");
        businessType01.setEntryID(BigDecimal.valueOf(67538));
        businessType01.setEntryName("A - การเกษตร  การเลี้ยงสัตว์  การประมง  การป่าไม้");
        businessType01.setEntryName2("A - การเกษตร  การเลี้ยงสัตว์  การประมง  การป่าไม้");
        businessType01.setEntrySource("HOST");
        businessType01.setExtValue1("AB060000");
        businessType01.setExtValue2("");
        businessType01.setGroupId("");
        businessType01.setRefEntryCode("");

        CommonCodeEntry businessType02 = new CommonCodeEntry();
        businessType02.setActiveStatus("1");
        businessType02.setCategoryCode("BUSINESS_TYPE");
        businessType02.setEntryCode("B");
        businessType02.setEntryID(BigDecimal.valueOf(67539));
        businessType02.setEntryName("B -  การทำเหมืองแร่  เหมืองหิน");
        businessType02.setEntryName2("B -  การทำเหมืองแร่  เหมืองหิน");
        businessType02.setEntrySource("HOST");
        businessType02.setExtValue1("BA000000");
        businessType02.setExtValue2("");
        businessType02.setGroupId("");
        businessType02.setRefEntryCode("");

        CommonCodeEntry businessTypeNotActive = new CommonCodeEntry();
        businessTypeNotActive.setActiveStatus("0");
        businessTypeNotActive.setCategoryCode("BUSINESS_TYPE");
        businessTypeNotActive.setEntryCode("C");
        businessTypeNotActive.setEntryID(BigDecimal.valueOf(67540));
        businessTypeNotActive.setEntryName("C - การผลิต");
        businessTypeNotActive.setEntryName2("C - การผลิต");
        businessTypeNotActive.setEntrySource("HOST");
        businessTypeNotActive.setExtValue1("CM070103");
        businessTypeNotActive.setExtValue2("");
        businessTypeNotActive.setGroupId("");
        businessTypeNotActive.setRefEntryCode("");

        commonCodeEntries.add(businessType01);
        commonCodeEntries.add(businessType02);
        commonCodeEntries.add(businessTypeNotActive);

        return commonCodeEntries;
    }

    private List<CommonCodeEntry> mockPayrollBank() {
        List<CommonCodeEntry> commonCodeEntries = new ArrayList<>();

        CommonCodeEntry payrollBank01 = new CommonCodeEntry();
        payrollBank01.setActiveStatus("1");
        payrollBank01.setCategoryCode("PAYROLL_BANK");
        payrollBank01.setEntryCode("0");
        payrollBank01.setEntryID(BigDecimal.valueOf(67538));
        payrollBank01.setEntryName("Other");
        payrollBank01.setEntryName2("อื่นๆ");
        payrollBank01.setEntrySource("HOST");
        payrollBank01.setExtValue1("");
        payrollBank01.setExtValue2("");
        payrollBank01.setGroupId("");
        payrollBank01.setRefEntryCode("B18");

        CommonCodeEntry payrollBank02 = new CommonCodeEntry();
        payrollBank02.setActiveStatus("1");
        payrollBank02.setCategoryCode("PAYROLL_BANK");
        payrollBank02.setEntryCode("002");
        payrollBank02.setEntryID(BigDecimal.valueOf(103556));
        payrollBank02.setEntryName("Bangkok Bank Public Company Limited (BBL)");
        payrollBank02.setEntryName2("ธนาคาร กรุงเทพ จำกัด (มหาชน)");
        payrollBank02.setEntrySource("HOST");
        payrollBank02.setExtValue1("");
        payrollBank02.setExtValue2("");
        payrollBank02.setGroupId("");
        payrollBank02.setRefEntryCode("A02");

        CommonCodeEntry payrollBankNotActive = new CommonCodeEntry();
        payrollBankNotActive.setActiveStatus("0");
        payrollBankNotActive.setCategoryCode("PAYROLL_BANK");
        payrollBankNotActive.setEntryCode("004");
        payrollBankNotActive.setEntryID(BigDecimal.valueOf(103559));
        payrollBankNotActive.setEntryName("Kasikornbank Public Company Limited (KBANK)");
        payrollBankNotActive.setEntryName2("ธนาคาร กสิกรไทย จำกัด (มหาชน)");
        payrollBankNotActive.setEntrySource("HOST");
        payrollBankNotActive.setExtValue1("");
        payrollBankNotActive.setExtValue2("");
        payrollBankNotActive.setGroupId("");
        payrollBankNotActive.setRefEntryCode("A05");

        commonCodeEntries.add(payrollBank01);
        commonCodeEntries.add(payrollBank02);
        commonCodeEntries.add(payrollBankNotActive);

        return commonCodeEntries;
    }

    private List<CommonCodeEntry> mockMaritalStatus() {
        List<CommonCodeEntry> commonCodeEntries = new ArrayList<>();

        CommonCodeEntry maritalStatus = new CommonCodeEntry();
        maritalStatus.setActiveStatus("1");
        maritalStatus.setCategoryCode("MARITAL_STATUS");
        maritalStatus.setEntryCode("M");
        maritalStatus.setEntryID(BigDecimal.valueOf(65301));
        maritalStatus.setEntryName("Married with Registration");
        maritalStatus.setEntryName2("สมรสจดทะเบียน");
        maritalStatus.setEntrySource("LOS");
        maritalStatus.setExtValue1("");
        maritalStatus.setExtValue2("");
        maritalStatus.setGroupId("");
        maritalStatus.setRefEntryCode("");

        CommonCodeEntry maritalStatusNotActive = new CommonCodeEntry();
        maritalStatusNotActive.setActiveStatus("0");
        maritalStatusNotActive.setCategoryCode("MARITAL_STATUS");
        maritalStatusNotActive.setEntryCode("M");
        maritalStatusNotActive.setEntryID(BigDecimal.valueOf(65302));
        maritalStatusNotActive.setEntryName("Married without Registration");
        maritalStatusNotActive.setEntryName2("สมรสไม่จดทะเบียน");
        maritalStatusNotActive.setEntrySource("LOS");
        maritalStatusNotActive.setExtValue1("");
        maritalStatusNotActive.setExtValue2("");
        maritalStatusNotActive.setGroupId("");
        maritalStatusNotActive.setRefEntryCode("");

        commonCodeEntries.add(maritalStatus);
        commonCodeEntries.add(maritalStatusNotActive);

        return commonCodeEntries;
    }

    private List<CommonCodeEntry> mockResidentType() {
        List<CommonCodeEntry> commonCodeEntries = new ArrayList<>();

        CommonCodeEntry residentTypeNotActive = new CommonCodeEntry();
        residentTypeNotActive.setActiveStatus("0");
        residentTypeNotActive.setCategoryCode("RESIDENT_TYP");
        residentTypeNotActive.setEntryCode("0");
        residentTypeNotActive.setEntryID(BigDecimal.valueOf(65239));
        residentTypeNotActive.setEntryName("Owns a house on land belonging to others");
        residentTypeNotActive.setEntryName2("บ้านปลูกบนที่ดินคนอื่น");
        residentTypeNotActive.setEntrySource("HOST");
        residentTypeNotActive.setExtValue1("");
        residentTypeNotActive.setExtValue2("");
        residentTypeNotActive.setGroupId("");
        residentTypeNotActive.setRefEntryCode("");

        CommonCodeEntry residentType = new CommonCodeEntry();
        residentType.setActiveStatus("1");
        residentType.setCategoryCode("RESIDENT_TYP");
        residentType.setEntryCode("0");
        residentType.setEntryID(BigDecimal.valueOf(65234));
        residentType.setEntryName("Own Home");
        residentType.setEntryName2("เป็นเจ้าของที่อยู่");
        residentType.setEntrySource("HOST");
        residentType.setExtValue1("");
        residentType.setExtValue2("");
        residentType.setGroupId("");
        residentType.setRefEntryCode("");

        commonCodeEntries.add(residentType);
        commonCodeEntries.add(residentTypeNotActive);

        return commonCodeEntries;
    }

    private List<CommonCodeEntry> mockEducationLevel() {
        List<CommonCodeEntry> commonCodeEntries = new ArrayList<>();

        CommonCodeEntry educationLevelNotActive = new CommonCodeEntry();
        educationLevelNotActive.setActiveStatus("0");
        educationLevelNotActive.setCategoryCode("EDUCATION_LEVEL");
        educationLevelNotActive.setEntryCode("04");
        educationLevelNotActive.setEntryID(BigDecimal.valueOf(65248));
        educationLevelNotActive.setEntryName("01 - Primary School");
        educationLevelNotActive.setEntryName2("ประถมศึกษา");
        educationLevelNotActive.setEntrySource("LOS");
        educationLevelNotActive.setExtValue1("");
        educationLevelNotActive.setExtValue2("");
        educationLevelNotActive.setGroupId("");
        educationLevelNotActive.setRefEntryCode("");

        CommonCodeEntry educationLevel = new CommonCodeEntry();
        educationLevel.setActiveStatus("1");
        educationLevel.setCategoryCode("EDUCATION_LEVEL");
        educationLevel.setEntryCode("04");
        educationLevel.setEntryID(BigDecimal.valueOf(65243));
        educationLevel.setEntryName("04 - Degree");
        educationLevel.setEntryName2("ปริญญาตรี");
        educationLevel.setEntrySource("LOS");
        educationLevel.setExtValue1("");
        educationLevel.setExtValue2("");
        educationLevel.setGroupId("");
        educationLevel.setRefEntryCode("");

        commonCodeEntries.add(educationLevel);
        commonCodeEntries.add(educationLevelNotActive);

        return commonCodeEntries;
    }
}
