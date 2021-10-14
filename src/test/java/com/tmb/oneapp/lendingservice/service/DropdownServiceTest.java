package com.tmb.oneapp.lendingservice.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.tmb.common.exception.model.TMBCommonException;
import com.tmb.common.model.CustGeneralProfileResponse;
import com.tmb.common.model.LovMaster;
import com.tmb.common.model.TmbOneServiceResponse;
import com.tmb.common.model.TmbStatus;
import com.tmb.oneapp.lendingservice.client.CommonServiceFeignClient;
import com.tmb.oneapp.lendingservice.constant.ResponseCode;
import com.tmb.oneapp.lendingservice.model.CriteriaCodeEntry;
import com.tmb.oneapp.lendingservice.model.dropdown.Dropdowns;
import com.tmb.oneapp.lendingservice.model.dropdown.DropdownsLoanSubmissionWorkingDetail;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;

import javax.xml.rpc.ServiceException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@RunWith(JUnit4.class)
public class DropdownServiceTest {

    @InjectMocks
    private DropdownService dropdownService;

    @Mock
    private LendingCriteriaInfoService lendingCriteriaInfoService;
    @Mock
    private CommonServiceFeignClient commonServiceFeignClient;
    @Mock
    private LoanOnlineSubmissionGetPersonalDetailService loanOnlineSubmissionGetPersonalDetailService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void getEmploymentStatus_Success() throws JsonProcessingException, ServiceException, TMBCommonException {
        doReturn(mockDropdownRmOccupation()).when(lendingCriteriaInfoService).getOccupationInfoByCode(anyString());

        String response = dropdownService.getEmploymentStatus("101");
        Assertions.assertEquals("01", response);
    }

    @Test
    public void getEmploymentStatus_NotFound() {

        TMBCommonException exception = assertThrows(TMBCommonException.class, () -> {
            doReturn(new ArrayList<>()).when(lendingCriteriaInfoService).getOccupationInfoByCode(anyString());

            dropdownService.getEmploymentStatus("00");
        });

        Assertions.assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, exception.getStatus());
        Assertions.assertEquals(ResponseCode.DATA_NOT_FOUND.getCode(), exception.getErrorCode());
        Assertions.assertEquals(ResponseCode.DATA_NOT_FOUND.getMessage(), exception.getErrorMessage());
    }

    @Test
    public void getDropdownEmploymentStatus_Success() throws JsonProcessingException {
        doReturn(mockDropdownEmploymentStatus()).when(lendingCriteriaInfoService).getEmploymentStatus();

        List<Dropdowns.EmploymentStatus> response = dropdownService.getDropdownEmploymentStatus();
        Assertions.assertEquals(3, response.size());
    }

    @Test
    public void getDropdownOccupation01_Success() throws TMBCommonException, JsonProcessingException {
        doReturn(mockDropdownOccupation()).when(lendingCriteriaInfoService).getOccupationsByRmOccupation(anyString());

        String employmentStatus = "01";
        List<Dropdowns.Occupation> response = dropdownService.getDropdownOccupation(employmentStatus);
        Assertions.assertEquals(3, response.size());
    }

    @Test
    public void getDropdownRmOccupation_Success() throws JsonProcessingException {
        doReturn(mockDropdownRmOccupation()).when(lendingCriteriaInfoService).getOccupationByEmploymentStatus(anyString());

        String employmentStatus = "01";
        List<Dropdowns.RmOccupation> response = dropdownService.getDropdownRmOccupation(employmentStatus);
        Assertions.assertEquals(2, response.size());
    }

    @Test
    public void getDropdownRmOccupationName_Success() throws JsonProcessingException {
        doReturn(mockDropdownRmOccupationName()).when(lendingCriteriaInfoService).getOccupationInfoByCode(anyString());

        String occupationCode = "101";
        List<Dropdowns.RmOccupation> response = dropdownService.getDropdownRmOccupationName(occupationCode);
        Assertions.assertEquals(1, response.size());
    }


    @Test
    public void getDropdownBusinessSubTypeA_Success() throws JsonProcessingException {
        doReturn(mockDropdownBusinessSubType()).when(lendingCriteriaInfoService).getCriteriaByCatalogId(any());

        String businessTypeCode = "A";
        List<Dropdowns.BusinessSubType> response = dropdownService.getDropdownBusinessSubType(businessTypeCode);
        Assertions.assertEquals(2, response.size());
    }

    @Test
    public void getDropdownBusinessSubTypeB_Success() throws JsonProcessingException {
        doReturn(mockDropdownBusinessSubType()).when(lendingCriteriaInfoService).getCriteriaByCatalogId(any());

        String businessTypeCode = "B";
        List<Dropdowns.BusinessSubType> response = dropdownService.getDropdownBusinessSubType(businessTypeCode);
        Assertions.assertEquals(1, response.size());
    }

    @Test
    public void getDropdownBusinessType_Success() throws JsonProcessingException {
        doReturn(mockDropdownBusinessType()).when(lendingCriteriaInfoService).getBusinessType();

        List<Dropdowns.BusinessType> response = dropdownService.getDropdownBusinessType();
        Assertions.assertEquals(2, response.size());
    }

    @Test
    public void getDropdownIncomeBank_Success() throws JsonProcessingException {
        doReturn(mockDropdownIncomeBank()).when(lendingCriteriaInfoService).getPayrollBank();

        List<Dropdowns.IncomeBank> response = dropdownService.getDropdownIncomeBank();
        Assertions.assertEquals(2, response.size());
    }

    @Test
    public void getDropdownIncomeType_Success() throws JsonProcessingException {
        doReturn(mockDropdownIncomeType()).when(lendingCriteriaInfoService).getSourceOfIncome(anyString());

        String employmentStatus = "01";
        List<Dropdowns.IncomeType> response = dropdownService.getDropdownIncomeType(employmentStatus);
        Assertions.assertEquals(2, response.size());
    }

    @Test
    public void getDropdownSciCountry_Success() throws TMBCommonException, JsonProcessingException {
        doReturn(mockDropdownSciCountry()).when(lendingCriteriaInfoService).getCountry();
        doReturn(mockLovmasterConfigCountry()).when(commonServiceFeignClient).getLovmasterConfig(anyString(), anyString(), anyString(), anyString());

        List<Dropdowns.SciCountry> response = dropdownService.getDropdownSciCountry("correlationId", "crmId");
        Assertions.assertEquals(2, response.size());
        Assertions.assertEquals("TH", response.get(0).getCode());
    }

    @Test
    public void getDropdownResident_Success() throws JsonProcessingException {
        doReturn(mockDropdownResidentType()).when(lendingCriteriaInfoService).getResidentTypeByCode(anyString());

        String residentTypeCode = "R";
        List<Dropdowns.ResidentType> response = dropdownService.getDropdownResidentType(residentTypeCode);
        Assertions.assertEquals(1, response.size());
    }

    @Test
    public void getDropdownEducation_Success() throws JsonProcessingException {
        doReturn(mockDropdownEducation()).when(lendingCriteriaInfoService).getEducationLevel(anyString());

        String educationLevel = "02";
        List<Dropdowns.EducationLevel> response = dropdownService.getDropdownEducationLevel(educationLevel);
        Assertions.assertEquals(1, response.size());
    }

    @Test
    public void getDropdownMarital_Success() throws JsonProcessingException {
        doReturn(mockDropdownMaritalStatus()).when(lendingCriteriaInfoService).getMaritalStatusByCode(anyString());

        String maritalCode = "U";
        List<Dropdowns.MaritalStatus> response = dropdownService.getDropdownMaritalStatus(maritalCode);
        Assertions.assertEquals(1, response.size());
    }

    @Test
    public void getDropdownsLoanSubmissionWorkingDetail_Success() throws ServiceException, TMBCommonException, JsonProcessingException {
        CustGeneralProfileResponse customerInfo = new CustGeneralProfileResponse();
        customerInfo.setOccupationCode("101");
        doReturn(customerInfo).when(loanOnlineSubmissionGetPersonalDetailService).getCustomerEC(anyString());
        doReturn(mockDropdownRmOccupation()).when(lendingCriteriaInfoService).getOccupationInfoByCode(anyString());
        doReturn(mockDropdownEmploymentStatus()).when(lendingCriteriaInfoService).getEmploymentStatus();
        doReturn(mockDropdownRmOccupation()).when(lendingCriteriaInfoService).getOccupationByEmploymentStatus(anyString());
        doReturn(mockDropdownBusinessType()).when(lendingCriteriaInfoService).getBusinessType();
        doReturn(mockDropdownIncomeBank()).when(lendingCriteriaInfoService).getPayrollBank();
        doReturn(mockDropdownIncomeType()).when(lendingCriteriaInfoService).getSourceOfIncome(anyString());
        doReturn(mockDropdownSciCountry()).when(lendingCriteriaInfoService).getCountry();
        doReturn(mockLovmasterConfigCountry()).when(commonServiceFeignClient).getLovmasterConfig(anyString(), anyString(), anyString(), anyString());

        DropdownsLoanSubmissionWorkingDetail response = dropdownService.getDropdownsLoanSubmissionWorkingDetail("correlationId", "crmId");
        Assertions.assertEquals(2, response.getCardDelivery().size());
        Assertions.assertEquals(2, response.getEmailStatementFlag().size());
    }

    private List<CriteriaCodeEntry> mockDropdownEmploymentStatus() {
        List<CriteriaCodeEntry> criteriaCodeEntries = new ArrayList<>();

        CriteriaCodeEntry empStatus01 = new CriteriaCodeEntry();
        empStatus01.setActiveStatus("1");
        empStatus01.setCategoryCode("EMPLOYMENT_STATUS");
        empStatus01.setEntryCode("101");
        empStatus01.setEntryID("73079");
        empStatus01.setEntryName("Salary-Cash");
        empStatus01.setEntryName2("รับรายได้ด้วยเงินสด");
        empStatus01.setExtValue1("");
        empStatus01.setExtValue2("");
        empStatus01.setGroupId("");
        empStatus01.setRefEntryCode("MO, CC, PL, SM, SP");

        CriteriaCodeEntry empStatus02 = new CriteriaCodeEntry();
        empStatus02.setActiveStatus("1");
        empStatus02.setCategoryCode("EMPLOYMENT_STATUS");
        empStatus02.setEntryCode("02");
        empStatus02.setEntryID("73080");
        empStatus02.setEntryName("Self Employed");
        empStatus02.setEntryName2("เจ้าของกิจการ");
        empStatus02.setExtValue1("");
        empStatus02.setExtValue2("");
        empStatus02.setGroupId("");
        empStatus02.setRefEntryCode("MO, CC, PL, SM, SP, SB");

        CriteriaCodeEntry empStatus03 = new CriteriaCodeEntry();
        empStatus03.setActiveStatus("1");
        empStatus03.setCategoryCode("EMPLOYMENT_STATUS");
        empStatus03.setEntryCode("03");
        empStatus03.setEntryID("73081");
        empStatus03.setEntryName("Not Working / Other Unemployed / Others");
        empStatus03.setEntryName2("ผู้ไม่มีรายได้/ อื่นๆ");
        empStatus03.setExtValue1("");
        empStatus03.setExtValue2("");
        empStatus03.setGroupId("");
        empStatus03.setRefEntryCode("MO, CC, PL, SM, SP");

        criteriaCodeEntries.add(empStatus01);
        criteriaCodeEntries.add(empStatus02);
        criteriaCodeEntries.add(empStatus03);

        return criteriaCodeEntries;
    }

    private List<CriteriaCodeEntry> mockDropdownOccupation() {
        List<CriteriaCodeEntry> criteriaCodeEntries = new ArrayList<>();

        CriteriaCodeEntry rmOccupation01 = new CriteriaCodeEntry();
        rmOccupation01.setActiveStatus("1");
        rmOccupation01.setCategoryCode("PROFFESIONAL");
        rmOccupation01.setEntryCode("101");
        rmOccupation01.setEntryID("80347");
        rmOccupation01.setEntryName("แพทย์ / ทันตแพทย์ / สัตวแพทย์");
        rmOccupation01.setEntryName2("แพทย์ / ทันตแพทย์ / สัตวแพทย์");
        rmOccupation01.setEntrySource("01,02,03,04,05,06,07,08,09,10,11,12,13,14");
        rmOccupation01.setExtValue1("");
        rmOccupation01.setExtValue2("01");
        rmOccupation01.setGroupId("");
        rmOccupation01.setRefEntryCode("");

        CriteriaCodeEntry rmOccupation02 = new CriteriaCodeEntry();
        rmOccupation02.setActiveStatus("1");
        rmOccupation02.setCategoryCode("PROFFESIONAL");
        rmOccupation02.setEntryCode("10");
        rmOccupation02.setEntryID("80356");
        rmOccupation02.setEntryName("อาจารย์ (ปวช ปวส มหาวิทยาลัย)");
        rmOccupation02.setEntryName2("อาจารย์ (ปวช ปวส มหาวิทยาลัย)");
        rmOccupation02.setEntrySource("01,02,03,04,05,06,07,08,09,10,11,12,13,14");
        rmOccupation02.setExtValue1("");
        rmOccupation02.setExtValue2("01");
        rmOccupation02.setGroupId("");
        rmOccupation02.setRefEntryCode("");

        CriteriaCodeEntry rmOccupation03 = new CriteriaCodeEntry();
        rmOccupation03.setActiveStatus("1");
        rmOccupation03.setCategoryCode("PROFFESIONAL");
        rmOccupation03.setEntryCode("13");
        rmOccupation03.setEntryID("80359");
        rmOccupation03.setEntryName("อื่นๆ");
        rmOccupation03.setEntryName2("อื่นๆ");
        rmOccupation03.setEntrySource("01,02,03,04,05,06,07,08,09,10,11,12,13,14");
        rmOccupation03.setExtValue1("");
        rmOccupation03.setExtValue2("01");
        rmOccupation03.setGroupId("");
        rmOccupation03.setRefEntryCode("");

        criteriaCodeEntries.add(rmOccupation01);
        criteriaCodeEntries.add(rmOccupation02);
        criteriaCodeEntries.add(rmOccupation03);

        return criteriaCodeEntries;
    }

    private List<CriteriaCodeEntry> mockDropdownRmOccupation() {
        List<CriteriaCodeEntry> criteriaCodeEntries = new ArrayList<>();

        CriteriaCodeEntry rmOccupation01 = new CriteriaCodeEntry();
        rmOccupation01.setActiveStatus("1");
        rmOccupation01.setCategoryCode("RM_OCCUPATION");
        rmOccupation01.setEntryCode("101");
        rmOccupation01.setEntryID("72954");
        rmOccupation01.setEntryName("ข้าราชการการเมือง");
        rmOccupation01.setEntryName2("ข้าราชการการเมือง");
        rmOccupation01.setEntrySource("HOST");
        rmOccupation01.setExtValue1("01");
        rmOccupation01.setExtValue2("01");
        rmOccupation01.setGroupId("4");
        rmOccupation01.setRefEntryCode("01");

        CriteriaCodeEntry rmOccupation02 = new CriteriaCodeEntry();
        rmOccupation02.setActiveStatus("1");
        rmOccupation02.setCategoryCode("RM_OCCUPATION");
        rmOccupation02.setEntryCode("110");
        rmOccupation02.setEntryID("72963");
        rmOccupation02.setEntryName("ลูกจ้างหน่วยงานราชการ");
        rmOccupation02.setEntryName2("ลูกจ้างหน่วยงานราชการ");
        rmOccupation02.setEntrySource("HOST");
        rmOccupation02.setExtValue1("01");
        rmOccupation02.setExtValue2("01");
        rmOccupation02.setGroupId("4");
        rmOccupation02.setRefEntryCode("02");

        criteriaCodeEntries.add(rmOccupation01);
        criteriaCodeEntries.add(rmOccupation02);

        return criteriaCodeEntries;
    }

    private List<CriteriaCodeEntry> mockDropdownRmOccupationName() {
        List<CriteriaCodeEntry> criteriaCodeEntries = new ArrayList<>();

        CriteriaCodeEntry rmOccupation01 = new CriteriaCodeEntry();
        rmOccupation01.setActiveStatus("1");
        rmOccupation01.setCategoryCode("RM_OCCUPATION");
        rmOccupation01.setEntryCode("101");
        rmOccupation01.setEntryID("72954");
        rmOccupation01.setEntryName("ข้าราชการการเมือง");
        rmOccupation01.setEntryName2("ข้าราชการการเมือง");
        rmOccupation01.setEntrySource("HOST");
        rmOccupation01.setExtValue1("01");
        rmOccupation01.setExtValue2("01");
        rmOccupation01.setGroupId("4");
        rmOccupation01.setRefEntryCode("01");

        criteriaCodeEntries.add(rmOccupation01);

        return criteriaCodeEntries;
    }

    private List<CriteriaCodeEntry> mockDropdownBusinessSubType() {
        List<CriteriaCodeEntry> criteriaCodeEntries = new ArrayList<>();

        CriteriaCodeEntry businessSubType01 = new CriteriaCodeEntry();
        businessSubType01.setActiveStatus("1");
        businessSubType01.setCategoryCode("BUSINESS_SUB_TYPE");
        businessSubType01.setEntryCode("AA010000");
        businessSubType01.setEntryID("72391");
        businessSubType01.setEntryName("AA010000-การปลูกข้าวเจ้า");
        businessSubType01.setEntryName2("AA010000-การปลูกข้าวเจ้า");
        businessSubType01.setEntrySource("HOST");
        businessSubType01.setExtValue1("MIB");
        businessSubType01.setExtValue2("การเกษตร การเลี้ยงสัตว์ปีก ประมงและเลี้ยงสัตว์น้ำ และป่าไม้");
        businessSubType01.setGroupId("");
        businessSubType01.setRefEntryCode("A");

        CriteriaCodeEntry businessSubType02 = new CriteriaCodeEntry();
        businessSubType02.setActiveStatus("1");
        businessSubType02.setCategoryCode("BUSINESS_SUB_TYPE");
        businessSubType02.setEntryCode("AA010101");
        businessSubType02.setEntryID("76651");
        businessSubType02.setEntryName("AA010101-การปลูกข้าวเหนียว");
        businessSubType02.setEntryName2("AA010101-การปลูกข้าวเหนียว");
        businessSubType02.setEntrySource("HOST");
        businessSubType02.setExtValue1("");
        businessSubType02.setExtValue2("");
        businessSubType02.setGroupId("");
        businessSubType02.setRefEntryCode("A");

        CriteriaCodeEntry businessSubType03 = new CriteriaCodeEntry();
        businessSubType03.setActiveStatus("1");
        businessSubType03.setCategoryCode("BUSINESS_SUB_TYPE");
        businessSubType03.setEntryCode("AB060000");
        businessSubType03.setEntryID("76711");
        businessSubType03.setEntryName("AB060000-กิจกรรมที่สนับสนุนการแพร่พันธุ์สัตว์");
        businessSubType03.setEntryName2("AB060000-กิจกรรมที่สนับสนุนการแพร่พันธุ์สัตว์");
        businessSubType03.setEntrySource("HOST");
        businessSubType03.setExtValue1("MIB");
        businessSubType03.setExtValue2("กิจกรรมที่สนับสนุนการแพร่พันธุ์สัตว์ หรือ สนับสนุนการเลี้ยงสัตว์อื่นๆ");
        businessSubType03.setGroupId("");
        businessSubType03.setRefEntryCode("A");

        CriteriaCodeEntry businessSubType04 = new CriteriaCodeEntry();
        businessSubType04.setActiveStatus("1");
        businessSubType04.setCategoryCode("BUSINESS_SUB_TYPE");
        businessSubType04.setEntryCode("BA000000");
        businessSubType04.setEntryID("72417");
        businessSubType04.setEntryName("BA000000-การทำเหมืองสินแร่ดีบุก");
        businessSubType04.setEntryName2("BA000000-การทำเหมืองสินแร่ดีบุก");
        businessSubType04.setEntrySource("HOST");
        businessSubType04.setExtValue1("MIB");
        businessSubType04.setExtValue2("การทำเหมือง การขุดเจาะปิโตรเลียม/ก๊าซธรรมชาติ/กรวดทราย/พีต");
        businessSubType04.setGroupId("");
        businessSubType04.setRefEntryCode("B");

        CriteriaCodeEntry businessSubType05 = new CriteriaCodeEntry();
        businessSubType05.setActiveStatus("1");
        businessSubType05.setCategoryCode("BUSINESS_SUB_TYPE");
        businessSubType05.setEntryCode("JQ000100");
        businessSubType05.setEntryID("72868");
        businessSubType05.setEntryName("JQ000100-การให้คำปรึกษาทางด้านฮาร์ดแวร์");
        businessSubType05.setEntryName2("JQ000100-การให้คำปรึกษาทางด้านฮาร์ดแวร์");
        businessSubType05.setEntrySource("HOST");
        businessSubType05.setExtValue1("MIB");
        businessSubType05.setExtValue2("การบริการเทคโนโลยีสารสนเทศและคอมพิวเตอร์ ฐานข้อมูล ระบบจัดการ ฮาร์แสวร์/ซอฟแวร์ การวิจัยและพัฒนาเชิงทดลอง การบำบัดและการกำจัดของเสีย");
        businessSubType05.setGroupId("");
        businessSubType05.setRefEntryCode("J");

        CriteriaCodeEntry businessSubTypeNotActive = new CriteriaCodeEntry();
        businessSubTypeNotActive.setActiveStatus("0");
        businessSubTypeNotActive.setCategoryCode("BUSINESS_SUB_TYPE");
        businessSubTypeNotActive.setEntryCode("BB090000");
        businessSubTypeNotActive.setEntryID("72425");
        businessSubTypeNotActive.setEntryName("BB090000-กิจกรรมที่สนับสนุนการขุดเจาะปิโตรเลียมและก๊าซธรรมชาติ");
        businessSubTypeNotActive.setEntryName2("BB090000-กิจกรรมที่สนับสนุนการขุดเจาะปิโตรเลียมและก๊าซธรรมชาติ");
        businessSubTypeNotActive.setEntrySource("HOST");
        businessSubTypeNotActive.setExtValue1("MIB");
        businessSubTypeNotActive.setExtValue2("กิจกรรมที่สนับสนุนการขุดเจาะปิโตรเลียมและก๊าซธรรมชาติ");
        businessSubTypeNotActive.setGroupId("");
        businessSubTypeNotActive.setRefEntryCode("B");

        criteriaCodeEntries.add(businessSubType01);
        criteriaCodeEntries.add(businessSubType02);
        criteriaCodeEntries.add(businessSubType03);
        criteriaCodeEntries.add(businessSubType04);
        criteriaCodeEntries.add(businessSubType05);
        criteriaCodeEntries.add(businessSubTypeNotActive);

        return criteriaCodeEntries;
    }

    private List<CriteriaCodeEntry> mockDropdownBusinessType() {
        List<CriteriaCodeEntry> criteriaCodeEntries = new ArrayList<>();

        CriteriaCodeEntry businessType01 = new CriteriaCodeEntry();
        businessType01.setActiveStatus("1");
        businessType01.setCategoryCode("BUSINESS_TYPE");
        businessType01.setEntryCode("A");
        businessType01.setEntryID("67538");
        businessType01.setEntryName("A - การเกษตร  การเลี้ยงสัตว์  การประมง  การป่าไม้");
        businessType01.setEntryName2("A - การเกษตร  การเลี้ยงสัตว์  การประมง  การป่าไม้");
        businessType01.setEntrySource("HOST");
        businessType01.setExtValue1("AB060000");
        businessType01.setExtValue2("");
        businessType01.setGroupId("");
        businessType01.setRefEntryCode("");

        CriteriaCodeEntry businessType02 = new CriteriaCodeEntry();
        businessType02.setActiveStatus("1");
        businessType02.setCategoryCode("BUSINESS_TYPE");
        businessType02.setEntryCode("B");
        businessType02.setEntryID("67539");
        businessType02.setEntryName("B -  การทำเหมืองแร่  เหมืองหิน");
        businessType02.setEntryName2("B -  การทำเหมืองแร่  เหมืองหิน");
        businessType02.setEntrySource("HOST");
        businessType02.setExtValue1("BA000000");
        businessType02.setExtValue2("");
        businessType02.setGroupId("");
        businessType02.setRefEntryCode("");

        criteriaCodeEntries.add(businessType01);
        criteriaCodeEntries.add(businessType02);

        return criteriaCodeEntries;
    }

    private List<CriteriaCodeEntry> mockDropdownIncomeBank() {
        List<CriteriaCodeEntry> criteriaCodeEntries = new ArrayList<>();

        CriteriaCodeEntry incomeBank01 = new CriteriaCodeEntry();
        incomeBank01.setActiveStatus("1");
        incomeBank01.setCategoryCode("PAYROLL_BANK");
        incomeBank01.setEntryCode("0");
        incomeBank01.setEntryID("103578");
        incomeBank01.setEntryName("Other");
        incomeBank01.setEntryName2("อื่นๆ");
        incomeBank01.setEntrySource("HOST");
        incomeBank01.setExtValue1("");
        incomeBank01.setExtValue2("");
        incomeBank01.setGroupId("");
        incomeBank01.setRefEntryCode("B18");

        CriteriaCodeEntry incomeBank02 = new CriteriaCodeEntry();
        incomeBank02.setActiveStatus("1");
        incomeBank02.setCategoryCode("PAYROLL_BANK");
        incomeBank02.setEntryCode("002");
        incomeBank02.setEntryID("103556");
        incomeBank02.setEntryName("Bangkok Bank Public Company Limited (BBL)");
        incomeBank02.setEntryName2("ธนาคาร กรุงเทพ จำกัด (มหาชน)");
        incomeBank02.setEntrySource("HOST");
        incomeBank02.setExtValue1("");
        incomeBank02.setExtValue2("");
        incomeBank02.setGroupId("");
        incomeBank02.setRefEntryCode("A02");

        criteriaCodeEntries.add(incomeBank01);
        criteriaCodeEntries.add(incomeBank02);

        return criteriaCodeEntries;
    }

    private List<CriteriaCodeEntry> mockDropdownIncomeType() {
        List<CriteriaCodeEntry> criteriaCodeEntries = new ArrayList<>();

        CriteriaCodeEntry incomeType01 = new CriteriaCodeEntry();
        incomeType01.setActiveStatus("1");
        incomeType01.setCategoryCode("INCOME_TYPE");
        incomeType01.setEntryCode("1");
        incomeType01.setEntryID("69842");
        incomeType01.setEntryName("Salary-Cash");
        incomeType01.setEntryName2("รับรายได้ด้วยเงินสด");
        incomeType01.setEntrySource("a");
        incomeType01.setExtValue1("");
        incomeType01.setExtValue2("เงินเดือน");
        incomeType01.setGroupId("");
        incomeType01.setRefEntryCode("01");

        CriteriaCodeEntry incomeType02 = new CriteriaCodeEntry();
        incomeType02.setActiveStatus("1");
        incomeType02.setCategoryCode("INCOME_TYPE");
        incomeType02.setEntryCode("2");
        incomeType02.setEntryID("69843");
        incomeType02.setEntryName("Salary-Pay roll");
        incomeType02.setEntryName2("รับรายได้ผ่านบัญชี");
        incomeType02.setEntrySource("01");
        incomeType02.setExtValue1("MIB");
        incomeType02.setExtValue2("เงินเดือน");
        incomeType02.setGroupId("");
        incomeType02.setRefEntryCode("01");

        criteriaCodeEntries.add(incomeType01);
        criteriaCodeEntries.add(incomeType02);

        return criteriaCodeEntries;
    }

    private List<CriteriaCodeEntry> mockDropdownSciCountry() {
        List<CriteriaCodeEntry> criteriaCodeEntries = new ArrayList<>();

        CriteriaCodeEntry sciCountry01 = new CriteriaCodeEntry();
        sciCountry01.setActiveStatus("1");
        sciCountry01.setCategoryCode("SCI_COUNTRY");
        sciCountry01.setEntryCode("TH");
        sciCountry01.setEntryID("66831");
        sciCountry01.setEntryName("Thailand");
        sciCountry01.setEntryName2("Thailand");
        sciCountry01.setEntrySource("LOS");
        sciCountry01.setExtValue1("");
        sciCountry01.setExtValue2("");
        sciCountry01.setGroupId("");
        sciCountry01.setRefEntryCode("");

        CriteriaCodeEntry sciCountry02 = new CriteriaCodeEntry();
        sciCountry02.setActiveStatus("1");
        sciCountry02.setCategoryCode("SCI_COUNTRY");
        sciCountry02.setEntryCode("AD");
        sciCountry02.setEntryID("66624");
        sciCountry02.setEntryName("Andorra");
        sciCountry02.setEntryName2("Andorra");
        sciCountry02.setEntrySource("LOS");
        sciCountry02.setExtValue1("");
        sciCountry02.setExtValue2("");
        sciCountry02.setGroupId("");
        sciCountry02.setRefEntryCode("");

        criteriaCodeEntries.add(sciCountry01);
        criteriaCodeEntries.add(sciCountry02);

        return criteriaCodeEntries;
    }

    private TmbOneServiceResponse mockLovmasterConfigCountry() {
        List<LovMaster> lovMasters = new ArrayList<>();
        LovMaster lovMasterTH = new LovMaster();
        lovMasterTH.setLovType("COUNTRY");
        lovMasterTH.setLovCode("TH");
        lovMasterTH.setLovLang("th_TH");
        lovMasterTH.setLovDesc("ไทย");
        lovMasterTH.setCreatedBy("TMB");
        lovMasterTH.setLovStatus("01");

        LovMaster lovMasterSG = new LovMaster();
        lovMasterSG.setLovType("COUNTRY");
        lovMasterSG.setLovCode("SG");
        lovMasterSG.setLovLang("th_TH");
        lovMasterSG.setLovDesc("สิงค์โปร์");
        lovMasterSG.setCreatedBy("TMB");
        lovMasterSG.setLovStatus("01");

        lovMasters.add(lovMasterTH);
        lovMasters.add(lovMasterSG);

        TmbOneServiceResponse response = new TmbOneServiceResponse();
        TmbStatus status = new TmbStatus();
        status.setCode(ResponseCode.SUCCESS.getCode());
        response.setStatus(status);
        response.setData(lovMasters);

        return response;
    }

    private List<CriteriaCodeEntry> mockDropdownResidentType() {
        List<CriteriaCodeEntry> criteriaCodeEntries = new ArrayList<>();

        CriteriaCodeEntry residentType = new CriteriaCodeEntry();
        residentType.setActiveStatus("1");
        residentType.setCategoryCode("RESIDENT_TYP");
        residentType.setEntryCode("R");
        residentType.setEntryID("65235");
        residentType.setEntryName("Rental");
        residentType.setEntryName2("เช่า");
        residentType.setExtValue1("MIB");

        criteriaCodeEntries.add(residentType);

        return criteriaCodeEntries;
    }

    private List<CriteriaCodeEntry> mockDropdownMaritalStatus() {
        List<CriteriaCodeEntry> criteriaCodeEntries = new ArrayList<>();

        CriteriaCodeEntry maritalStatus = new CriteriaCodeEntry();
        maritalStatus.setActiveStatus("1");
        maritalStatus.setCategoryCode("MARITAL_STATUS");
        maritalStatus.setEntryCode("U");
        maritalStatus.setEntryID("65239");
        maritalStatus.setEntryName("Single");
        maritalStatus.setEntryName2("โสด");
        maritalStatus.setExtValue1("MIB");

        criteriaCodeEntries.add(maritalStatus);

        return criteriaCodeEntries;
    }

    private List<CriteriaCodeEntry> mockDropdownEducation() {
        List<CriteriaCodeEntry> criteriaCodeEntries = new ArrayList<>();

        CriteriaCodeEntry education = new CriteriaCodeEntry();
        education.setActiveStatus("1");
        education.setCategoryCode("MARITAL_STATUS");
        education.setEntryCode("02");
        education.setEntryID("65239");
        education.setEntryName("xx");
        education.setEntryName2("xx");
        education.setExtValue1("MIB");

        criteriaCodeEntries.add(education);

        return criteriaCodeEntries;
    }

}
