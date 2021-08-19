package com.tmb.oneapp.lendingservice.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.tmb.common.exception.model.TMBCommonException;
import com.tmb.common.model.CustGeneralProfileResponse;
import com.tmb.common.model.LovMaster;
import com.tmb.common.model.TmbOneServiceResponse;
import com.tmb.common.model.TmbStatus;
import com.tmb.common.model.legacy.rsl.common.ob.dropdown.CommonCodeEntry;
import com.tmb.common.model.legacy.rsl.ws.dropdown.response.Body;
import com.tmb.common.model.legacy.rsl.ws.dropdown.response.Header;
import com.tmb.common.model.legacy.rsl.ws.dropdown.response.ResponseDropdown;
import com.tmb.oneapp.lendingservice.client.CommonServiceFeignClient;
import com.tmb.oneapp.lendingservice.client.LoanSubmissionGetDropdownListClient;
import com.tmb.oneapp.lendingservice.constant.ResponseCode;
import com.tmb.oneapp.lendingservice.constant.RslResponseCode;
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
import java.math.BigDecimal;
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
    private LoanSubmissionGetDropdownListClient loanSubmissionGetDropdownListClient;
    @Mock
    private CommonServiceFeignClient commonServiceFeignClient;
    @Mock
    private PersonalDetailService personalDetailService;

    private static final String DROPDOWN_EMPLOYMENT_STATUS = "EMPLOYMENT_STATUS";
    private static final String DROPDOWN_RM_OCCUPATION = "RM_OCCUPATION";
    private static final String DROPDOWN_PROFFESIONAL = "PROFFESIONAL";
    private static final String DROPDOWN_BUSINESS_TYPE = "BUSINESS_TYPE";
    private static final String DROPDOWN_BUSINESS_SUB_TYPE = "BUSINESS_SUB_TYPE";
    private static final String DROPDOWN_PAYROLL_BANK = "PAYROLL_BANK";
    private static final String DROPDOWN_INCOME_TYPE = "INCOME_TYPE";
    private static final String DROPDOWN_SCI_COUNTRY = "SCI_COUNTRY";

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void getDropdownEmploymentStatus_Success() throws ServiceException, TMBCommonException, JsonProcessingException {
        doReturn(mockDropdownEmploymentStatus()).when(loanSubmissionGetDropdownListClient).getDropDownListByCode(anyString());

        List<Dropdowns.EmploymentStatus> response = dropdownService.getDropdownEmploymentStatus();
        Assertions.assertEquals(3, response.size());
    }

    @Test
    public void getDropdownOccupation01_Success() throws ServiceException, TMBCommonException, JsonProcessingException {
        doReturn(mockDropdownOccupation()).when(loanSubmissionGetDropdownListClient).getDropDownListByCode(anyString());

        String employmentStatus = "01";
        List<Dropdowns.Occupation> response = dropdownService.getDropdownOccupation(employmentStatus);
        Assertions.assertEquals(3, response.size());
    }

    @Test
    public void getDropdownOccupation02_Success() throws ServiceException, TMBCommonException, JsonProcessingException {
        doReturn(mockDropdownOccupation()).when(loanSubmissionGetDropdownListClient).getDropDownListByCode(anyString());

        String employmentStatus = "02";
        List<Dropdowns.Occupation> response = dropdownService.getDropdownOccupation(employmentStatus);
        Assertions.assertEquals(4, response.size());
    }

    @Test
    public void getDropdownRmOccupation01_Success() throws ServiceException, TMBCommonException, JsonProcessingException {
        doReturn(mockDropdownRmOccupation()).when(loanSubmissionGetDropdownListClient).getDropDownListByCode(anyString());

        String employmentStatus = "01";
        List<Dropdowns.RmOccupation> response = dropdownService.getDropdownRmOccupation(employmentStatus);
        Assertions.assertEquals(2, response.size());
    }

    @Test
    public void getDropdownRmOccupation02_Success() throws ServiceException, TMBCommonException, JsonProcessingException {
        doReturn(mockDropdownRmOccupation()).when(loanSubmissionGetDropdownListClient).getDropDownListByCode(anyString());

        String employmentStatus = "02";
        List<Dropdowns.RmOccupation> response = dropdownService.getDropdownRmOccupation(employmentStatus);
        Assertions.assertEquals(1, response.size());
    }

    @Test
    public void getDropdownBusinessSubTypeA_Success() throws ServiceException, TMBCommonException, JsonProcessingException {
        doReturn(mockDropdownBusinessSubType()).when(loanSubmissionGetDropdownListClient).getDropDownListByCode(anyString());

        String businessTypeCode = "A";
        List<Dropdowns.BusinessSubType> response = dropdownService.getDropdownBusinessSubType(businessTypeCode);
        Assertions.assertEquals(2, response.size());
    }

    @Test
    public void getDropdownBusinessSubTypeB_Success() throws ServiceException, TMBCommonException, JsonProcessingException {
        doReturn(mockDropdownBusinessSubType()).when(loanSubmissionGetDropdownListClient).getDropDownListByCode(anyString());

        String businessTypeCode = "B";
        List<Dropdowns.BusinessSubType> response = dropdownService.getDropdownBusinessSubType(businessTypeCode);
        Assertions.assertEquals(1, response.size());
    }

    @Test
    public void getDropdownBusinessType_Success() throws ServiceException, TMBCommonException, JsonProcessingException {
        doReturn(mockDropdownBusinessType()).when(loanSubmissionGetDropdownListClient).getDropDownListByCode(anyString());

        List<Dropdowns.BusinessType> response = dropdownService.getDropdownBusinessType();
        Assertions.assertEquals(3, response.size());
    }

    @Test
    public void getDropdownIncomeBank_Success() throws ServiceException, TMBCommonException, JsonProcessingException {
        doReturn(mockDropdownIncomeBank()).when(loanSubmissionGetDropdownListClient).getDropDownListByCode(anyString());

        List<Dropdowns.IncomeBank> response = dropdownService.getDropdownIncomeBank();
        Assertions.assertEquals(3, response.size());
    }

    @Test
    public void getDropdownIncomeType01_Success() throws ServiceException, TMBCommonException, JsonProcessingException {
        doReturn(mockDropdownIncomeType()).when(loanSubmissionGetDropdownListClient).getDropDownListByCode(anyString());

        String employmentStatus = "01";
        List<Dropdowns.IncomeType> response = dropdownService.getDropdownIncomeType(employmentStatus);
        Assertions.assertEquals(1, response.size());
    }

    @Test
    public void getDropdownIncomeType02_Success() throws ServiceException, TMBCommonException, JsonProcessingException {
        doReturn(mockDropdownIncomeType()).when(loanSubmissionGetDropdownListClient).getDropDownListByCode(anyString());

        String employmentStatus = "02";
        List<Dropdowns.IncomeType> response = dropdownService.getDropdownIncomeType(employmentStatus);
        Assertions.assertEquals(1, response.size());
    }

    @Test
    public void getDropdownSciCountry_Success() throws ServiceException, TMBCommonException, JsonProcessingException {
        doReturn(mockDropdownSciCountry()).when(loanSubmissionGetDropdownListClient).getDropDownListByCode(anyString());
        doReturn(mockLovmasterConfigCountry()).when(commonServiceFeignClient).getLovmasterConfig(anyString(), anyString(), anyString(), anyString());

        List<Dropdowns.SciCountry> response = dropdownService.getDropdownSciCountry("correlationId", "crmId");
        Assertions.assertEquals(3, response.size());
        Assertions.assertEquals("TH", response.get(0).getCode());
    }

    @Test
    public void getDropdownsLoanSubmissionWorkingDetail_Success() throws ServiceException, TMBCommonException, JsonProcessingException {
        String employmentStatus = "01";
        CustGeneralProfileResponse customerInfo = new CustGeneralProfileResponse();
        customerInfo.setOccupationCode(employmentStatus);
        doReturn(customerInfo).when(personalDetailService).getCustomerEC(anyString());
        doReturn(mockDropdownEmploymentStatus()).when(loanSubmissionGetDropdownListClient).getDropDownListByCode(anyString());
        doReturn(mockDropdownOccupation()).when(loanSubmissionGetDropdownListClient).getDropDownListByCode(anyString());
        doReturn(mockLovmasterConfigCountry()).when(commonServiceFeignClient).getLovmasterConfig(anyString(), anyString(), anyString(), anyString());

        DropdownsLoanSubmissionWorkingDetail response = dropdownService.getDropdownsLoanSubmissionWorkingDetail("correlationId", "crmId");
        Assertions.assertEquals(2, response.getCardDelivery().size());
        Assertions.assertEquals(2, response.getEmailStatementFlag().size());
    }

    @Test
    public void getDropdownsLoanSubmissionWorkingDetail_RslConnectionError() {
        TMBCommonException exception = assertThrows(TMBCommonException.class, () -> {
            doThrow(new TMBCommonException(ResponseCode.RSL_CONNECTION_ERROR.getCode(), ResponseCode.RSL_CONNECTION_ERROR.getMessage(), ResponseCode.RSL_CONNECTION_ERROR.getService(), HttpStatus.INTERNAL_SERVER_ERROR, null))
                    .when(loanSubmissionGetDropdownListClient).getDropDownListByCode(anyString());

            loanSubmissionGetDropdownListClient.getDropDownListByCode(DROPDOWN_EMPLOYMENT_STATUS);
        });

        Assertions.assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, exception.getStatus());
        Assertions.assertEquals(ResponseCode.RSL_CONNECTION_ERROR.getCode(), exception.getErrorCode());
        Assertions.assertEquals(ResponseCode.RSL_CONNECTION_ERROR.getMessage(), exception.getErrorMessage());
    }

    @Test
    public void getDropdownsLoanSubmissionWorkingDetail_RslFail() throws TMBCommonException {
        String employmentStatus = "01";
        CustGeneralProfileResponse customerInfo = new CustGeneralProfileResponse();
        customerInfo.setOccupationCode(employmentStatus);
        doReturn(customerInfo).when(personalDetailService).getCustomerEC(anyString());

        TMBCommonException exception = assertThrows(TMBCommonException.class, () -> {
            doReturn(mockGetDropdownFail()).when(loanSubmissionGetDropdownListClient).getDropDownListByCode(anyString());
            dropdownService.getDropdownsLoanSubmissionWorkingDetail("correlationId", "crmId");
        });

        Assertions.assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, exception.getStatus());
        Assertions.assertEquals(ResponseCode.RSL_FAILED.getCode(), exception.getErrorCode());
        Assertions.assertEquals("[MSG_001] Fail", exception.getErrorMessage());
    }


    private ResponseDropdown mockDropdownEmploymentStatus() {
        ResponseDropdown dropdownEmploymentStatus = new ResponseDropdown();

        CommonCodeEntry empStatus01 = new CommonCodeEntry();
        empStatus01.setActiveStatus("1");
        empStatus01.setCategoryCode(DROPDOWN_EMPLOYMENT_STATUS);
        empStatus01.setEntryCode("01");
        empStatus01.setEntryID(BigDecimal.valueOf(73079));
        empStatus01.setEntryName("Salary-Cash");
        empStatus01.setEntryName2("รับรายได้ด้วยเงินสด");
        empStatus01.setExtValue1("");
        empStatus01.setExtValue2("");
        empStatus01.setGroupId("");
        empStatus01.setRefEntryCode("MO, CC, PL, SM, SP");

        CommonCodeEntry empStatus02 = new CommonCodeEntry();
        empStatus02.setActiveStatus("1");
        empStatus02.setCategoryCode(DROPDOWN_EMPLOYMENT_STATUS);
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
        empStatus03.setCategoryCode(DROPDOWN_EMPLOYMENT_STATUS);
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
        empStatusNotActive.setCategoryCode(DROPDOWN_EMPLOYMENT_STATUS);
        empStatusNotActive.setEntryCode("04");
        empStatusNotActive.setEntryID(BigDecimal.valueOf(73081));
        empStatusNotActive.setEntryName("Test Dropdown Not Active");
        empStatusNotActive.setEntryName2("ทดสอบ dropdown not active");
        empStatusNotActive.setExtValue1("");
        empStatusNotActive.setExtValue2("");
        empStatusNotActive.setGroupId("");
        empStatusNotActive.setRefEntryCode("MO, CC, PL, SM, SP");

        CommonCodeEntry[] commonCodeEntries = {empStatus01, empStatus02, empStatus03, empStatusNotActive};

        Header header = new Header();
        header.setResponseCode(RslResponseCode.SUCCESS.getCode());
        header.setResponseDescriptionEN(RslResponseCode.SUCCESS.getMessage());

        Body body = new Body();
        body.setCommonCodeEntries(commonCodeEntries);

        dropdownEmploymentStatus.setHeader(header);
        dropdownEmploymentStatus.setBody(body);
        return dropdownEmploymentStatus;
    }

    private ResponseDropdown mockDropdownOccupation() {
        ResponseDropdown dropdownRmOccupation = new ResponseDropdown();

        CommonCodeEntry rmOccupation01 = new CommonCodeEntry();
        rmOccupation01.setActiveStatus("1");
        rmOccupation01.setCategoryCode(DROPDOWN_PROFFESIONAL);
        rmOccupation01.setEntryCode("1");
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
        rmOccupation02.setCategoryCode(DROPDOWN_PROFFESIONAL);
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
        rmOccupation03.setCategoryCode(DROPDOWN_PROFFESIONAL);
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
        rmOccupation04.setCategoryCode(DROPDOWN_PROFFESIONAL);
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
        rmOccupation05.setCategoryCode(DROPDOWN_PROFFESIONAL);
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
        rmOccupation05.setCategoryCode(DROPDOWN_RM_OCCUPATION);
        rmOccupation05.setEntryCode("3");
        rmOccupation05.setEntryID(BigDecimal.valueOf(80349));
        rmOccupation05.setEntryName("Test Not Active");
        rmOccupation05.setEntryName2("ทดสอบ not active");
        rmOccupation05.setEntrySource("01,02,03,04,05,06,07,08,09,10,11,12,13,14");
        rmOccupation05.setExtValue1("");
        rmOccupation05.setExtValue2("02");
        rmOccupation05.setGroupId("");
        rmOccupation04.setRefEntryCode("");

        CommonCodeEntry[] commonCodeEntries = {rmOccupation01, rmOccupation02, rmOccupation03, rmOccupation04, rmOccupation05, rmOccupationNotActive};

        Header header = new Header();
        header.setResponseCode(RslResponseCode.SUCCESS.getCode());
        header.setResponseDescriptionEN(RslResponseCode.SUCCESS.getMessage());

        Body body = new Body();
        body.setCommonCodeEntries(commonCodeEntries);

        dropdownRmOccupation.setHeader(header);
        dropdownRmOccupation.setBody(body);
        return dropdownRmOccupation;
    }

    private ResponseDropdown mockDropdownRmOccupation() {
        ResponseDropdown dropdownRmOccupation = new ResponseDropdown();

        CommonCodeEntry rmOccupation01 = new CommonCodeEntry();
        rmOccupation01.setActiveStatus("1");
        rmOccupation01.setCategoryCode(DROPDOWN_RM_OCCUPATION);
        rmOccupation01.setEntryCode("101");
        rmOccupation01.setEntryID(BigDecimal.valueOf(72954));
        rmOccupation01.setEntryName("ข้าราชการการเมือง");
        rmOccupation01.setEntryName2("ข้าราชการการเมือง");
        rmOccupation01.setEntrySource("HOST");
        rmOccupation01.setExtValue1("01");
        rmOccupation01.setExtValue2("01");
        rmOccupation01.setGroupId("4");
        rmOccupation01.setRefEntryCode("01");

        CommonCodeEntry rmOccupation02 = new CommonCodeEntry();
        rmOccupation02.setActiveStatus("1");
        rmOccupation02.setCategoryCode(DROPDOWN_RM_OCCUPATION);
        rmOccupation02.setEntryCode("109");
        rmOccupation02.setEntryID(BigDecimal.valueOf(72962));
        rmOccupation02.setEntryName("ข้าราชการบำนาญ");
        rmOccupation02.setEntryName2("ข้าราชการบำนาญ");
        rmOccupation02.setEntrySource("HOST");
        rmOccupation02.setExtValue1("03");
        rmOccupation02.setExtValue2("01");
        rmOccupation02.setGroupId("4");
        rmOccupation02.setRefEntryCode("12");

        CommonCodeEntry rmOccupation03 = new CommonCodeEntry();
        rmOccupation03.setActiveStatus("1");
        rmOccupation03.setCategoryCode(DROPDOWN_RM_OCCUPATION);
        rmOccupation03.setEntryCode("110");
        rmOccupation03.setEntryID(BigDecimal.valueOf(72963));
        rmOccupation03.setEntryName("ลูกจ้างหน่วยงานราชการ");
        rmOccupation03.setEntryName2("ลูกจ้างหน่วยงานราชการ");
        rmOccupation03.setEntrySource("HOST");
        rmOccupation03.setExtValue1("01");
        rmOccupation03.setExtValue2("01");
        rmOccupation03.setGroupId("4");
        rmOccupation03.setRefEntryCode("02");

        CommonCodeEntry rmOccupation04 = new CommonCodeEntry();
        rmOccupation04.setActiveStatus("1");
        rmOccupation04.setCategoryCode(DROPDOWN_RM_OCCUPATION);
        rmOccupation04.setEntryCode("217");
        rmOccupation04.setEntryID(BigDecimal.valueOf(72964));
        rmOccupation04.setEntryName("นักเรียน/นักศึกษา");
        rmOccupation04.setEntryName2("นักเรียน/นักศึกษา");
        rmOccupation04.setEntrySource("HOST");
        rmOccupation04.setExtValue1("03");
        rmOccupation04.setExtValue2("13");
        rmOccupation04.setGroupId("5");
        rmOccupation04.setRefEntryCode("13");

        CommonCodeEntry rmOccupation05 = new CommonCodeEntry();
        rmOccupation05.setActiveStatus("1");
        rmOccupation05.setCategoryCode(DROPDOWN_RM_OCCUPATION);
        rmOccupation05.setEntryCode("404");
        rmOccupation05.setEntryID(BigDecimal.valueOf(72984));
        rmOccupation05.setEntryName("เจ้าของ/หุ้นส่วนกิจการเกษตรฯ และอุตสาหกรรม");
        rmOccupation05.setEntryName2("เจ้าของ/หุ้นส่วนกิจการเกษตรฯ และอุตสาหกรรม");
        rmOccupation05.setEntrySource("HOST");
        rmOccupation05.setExtValue1("02");
        rmOccupation05.setExtValue2("08");
        rmOccupation05.setGroupId("5");
        rmOccupation05.setRefEntryCode("08, 09, 10, 11");

        CommonCodeEntry rmOccupationNotActive = new CommonCodeEntry();
        rmOccupationNotActive.setActiveStatus("0");
        rmOccupationNotActive.setCategoryCode(DROPDOWN_RM_OCCUPATION);
        rmOccupationNotActive.setEntryCode("404");
        rmOccupationNotActive.setEntryID(BigDecimal.valueOf(72985));
        rmOccupationNotActive.setEntryName("Test Not Active");
        rmOccupationNotActive.setEntryName2("ทดสอบ not active");
        rmOccupationNotActive.setEntrySource("HOST");
        rmOccupationNotActive.setExtValue1("02");
        rmOccupationNotActive.setExtValue2("08");
        rmOccupationNotActive.setGroupId("6");
        rmOccupationNotActive.setRefEntryCode("08");

        CommonCodeEntry[] commonCodeEntries = {rmOccupation01, rmOccupation02, rmOccupation03, rmOccupation04, rmOccupation05, rmOccupationNotActive};

        Header header = new Header();
        header.setResponseCode(RslResponseCode.SUCCESS.getCode());
        header.setResponseDescriptionEN(RslResponseCode.SUCCESS.getMessage());

        Body body = new Body();
        body.setCommonCodeEntries(commonCodeEntries);

        dropdownRmOccupation.setHeader(header);
        dropdownRmOccupation.setBody(body);
        return dropdownRmOccupation;
    }

    private ResponseDropdown mockDropdownBusinessSubType() {
        ResponseDropdown dropdownBusinessSubType = new ResponseDropdown();

        CommonCodeEntry businessSubType01 = new CommonCodeEntry();
        businessSubType01.setActiveStatus("1");
        businessSubType01.setCategoryCode(DROPDOWN_BUSINESS_SUB_TYPE);
        businessSubType01.setEntryCode("AA010000");
        businessSubType01.setEntryID(BigDecimal.valueOf(72391));
        businessSubType01.setEntryName("AA010000-การปลูกข้าวเจ้า");
        businessSubType01.setEntryName2("AA010000-การปลูกข้าวเจ้า");
        businessSubType01.setEntrySource("HOST");
        businessSubType01.setExtValue1("MIB");
        businessSubType01.setExtValue2("การเกษตร การเลี้ยงสัตว์ปีก ประมงและเลี้ยงสัตว์น้ำ และป่าไม้");
        businessSubType01.setGroupId("");
        businessSubType01.setRefEntryCode("A");

        CommonCodeEntry businessSubType02 = new CommonCodeEntry();
        businessSubType02.setActiveStatus("1");
        businessSubType02.setCategoryCode(DROPDOWN_BUSINESS_SUB_TYPE);
        businessSubType02.setEntryCode("AA010101");
        businessSubType02.setEntryID(BigDecimal.valueOf(76651));
        businessSubType02.setEntryName("AA010101-การปลูกข้าวเหนียว");
        businessSubType02.setEntryName2("AA010101-การปลูกข้าวเหนียว");
        businessSubType02.setEntrySource("HOST");
        businessSubType02.setExtValue1("");
        businessSubType02.setExtValue2("");
        businessSubType02.setGroupId("");
        businessSubType02.setRefEntryCode("A");

        CommonCodeEntry businessSubType03 = new CommonCodeEntry();
        businessSubType03.setActiveStatus("1");
        businessSubType03.setCategoryCode(DROPDOWN_BUSINESS_SUB_TYPE);
        businessSubType03.setEntryCode("AB060000");
        businessSubType03.setEntryID(BigDecimal.valueOf(76711));
        businessSubType03.setEntryName("AB060000-กิจกรรมที่สนับสนุนการแพร่พันธุ์สัตว์");
        businessSubType03.setEntryName2("AB060000-กิจกรรมที่สนับสนุนการแพร่พันธุ์สัตว์");
        businessSubType03.setEntrySource("HOST");
        businessSubType03.setExtValue1("MIB");
        businessSubType03.setExtValue2("กิจกรรมที่สนับสนุนการแพร่พันธุ์สัตว์ หรือ สนับสนุนการเลี้ยงสัตว์อื่นๆ");
        businessSubType03.setGroupId("");
        businessSubType03.setRefEntryCode("A");

        CommonCodeEntry businessSubType04 = new CommonCodeEntry();
        businessSubType04.setActiveStatus("1");
        businessSubType04.setCategoryCode(DROPDOWN_BUSINESS_SUB_TYPE);
        businessSubType04.setEntryCode("BA000000");
        businessSubType04.setEntryID(BigDecimal.valueOf(72417));
        businessSubType04.setEntryName("BA000000-การทำเหมืองสินแร่ดีบุก");
        businessSubType04.setEntryName2("BA000000-การทำเหมืองสินแร่ดีบุก");
        businessSubType04.setEntrySource("HOST");
        businessSubType04.setExtValue1("MIB");
        businessSubType04.setExtValue2("การทำเหมือง การขุดเจาะปิโตรเลียม/ก๊าซธรรมชาติ/กรวดทราย/พีต");
        businessSubType04.setGroupId("");
        businessSubType04.setRefEntryCode("B");

        CommonCodeEntry businessSubType05 = new CommonCodeEntry();
        businessSubType05.setActiveStatus("1");
        businessSubType05.setCategoryCode(DROPDOWN_BUSINESS_SUB_TYPE);
        businessSubType05.setEntryCode("JQ000100");
        businessSubType05.setEntryID(BigDecimal.valueOf(72868));
        businessSubType05.setEntryName("JQ000100-การให้คำปรึกษาทางด้านฮาร์ดแวร์");
        businessSubType05.setEntryName2("JQ000100-การให้คำปรึกษาทางด้านฮาร์ดแวร์");
        businessSubType05.setEntrySource("HOST");
        businessSubType05.setExtValue1("MIB");
        businessSubType05.setExtValue2("การบริการเทคโนโลยีสารสนเทศและคอมพิวเตอร์ ฐานข้อมูล ระบบจัดการ ฮาร์แสวร์/ซอฟแวร์ การวิจัยและพัฒนาเชิงทดลอง การบำบัดและการกำจัดของเสีย");
        businessSubType05.setGroupId("");
        businessSubType05.setRefEntryCode("J");

        CommonCodeEntry businessSubTypeNotActive = new CommonCodeEntry();
        businessSubTypeNotActive.setActiveStatus("0");
        businessSubTypeNotActive.setCategoryCode(DROPDOWN_BUSINESS_SUB_TYPE);
        businessSubTypeNotActive.setEntryCode("BB090000");
        businessSubTypeNotActive.setEntryID(BigDecimal.valueOf(72425));
        businessSubTypeNotActive.setEntryName("BB090000-กิจกรรมที่สนับสนุนการขุดเจาะปิโตรเลียมและก๊าซธรรมชาติ");
        businessSubTypeNotActive.setEntryName2("BB090000-กิจกรรมที่สนับสนุนการขุดเจาะปิโตรเลียมและก๊าซธรรมชาติ");
        businessSubTypeNotActive.setEntrySource("HOST");
        businessSubTypeNotActive.setExtValue1("MIB");
        businessSubTypeNotActive.setExtValue2("กิจกรรมที่สนับสนุนการขุดเจาะปิโตรเลียมและก๊าซธรรมชาติ");
        businessSubTypeNotActive.setGroupId("");
        businessSubTypeNotActive.setRefEntryCode("B");

        CommonCodeEntry[] commonCodeEntries = {businessSubType01, businessSubType02, businessSubType03, businessSubType04, businessSubType05, businessSubTypeNotActive};

        Header header = new Header();
        header.setResponseCode(RslResponseCode.SUCCESS.getCode());
        header.setResponseDescriptionEN(RslResponseCode.SUCCESS.getMessage());

        Body body = new Body();
        body.setCommonCodeEntries(commonCodeEntries);

        dropdownBusinessSubType.setHeader(header);
        dropdownBusinessSubType.setBody(body);
        return dropdownBusinessSubType;
    }

    private ResponseDropdown mockDropdownBusinessType() {
        ResponseDropdown dropdownBusinessType = new ResponseDropdown();

        CommonCodeEntry businessType01 = new CommonCodeEntry();
        businessType01.setActiveStatus("1");
        businessType01.setCategoryCode(DROPDOWN_BUSINESS_TYPE);
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
        businessType02.setCategoryCode(DROPDOWN_BUSINESS_TYPE);
        businessType02.setEntryCode("B");
        businessType02.setEntryID(BigDecimal.valueOf(67539));
        businessType02.setEntryName("B -  การทำเหมืองแร่  เหมืองหิน");
        businessType02.setEntryName2("B -  การทำเหมืองแร่  เหมืองหิน");
        businessType02.setEntrySource("HOST");
        businessType02.setExtValue1("BA000000");
        businessType02.setExtValue2("");
        businessType02.setGroupId("");
        businessType02.setRefEntryCode("");

        CommonCodeEntry businessType03 = new CommonCodeEntry();
        businessType03.setActiveStatus("1");
        businessType03.setCategoryCode(DROPDOWN_BUSINESS_TYPE);
        businessType03.setEntryCode("J");
        businessType03.setEntryID(BigDecimal.valueOf(67545));
        businessType03.setEntryName("J - การบริการ");
        businessType03.setEntryName2("J - การบริการ");
        businessType03.setEntrySource("HOST");
        businessType03.setExtValue1("JA010200");
        businessType03.setExtValue2("");
        businessType03.setGroupId("");
        businessType03.setRefEntryCode("");

        CommonCodeEntry businessTypeNotActive = new CommonCodeEntry();
        businessTypeNotActive.setActiveStatus("0");
        businessTypeNotActive.setCategoryCode(DROPDOWN_BUSINESS_TYPE);
        businessTypeNotActive.setEntryCode("I");
        businessTypeNotActive.setEntryID(BigDecimal.valueOf(67544));
        businessTypeNotActive.setEntryName("I - การสาธารณูปโภค และการขนส่ง");
        businessTypeNotActive.setEntryName2("I - การสาธารณูปโภค และการขนส่ง");
        businessTypeNotActive.setEntrySource("HOST");
        businessTypeNotActive.setExtValue1("IE000101");
        businessTypeNotActive.setExtValue2("");
        businessTypeNotActive.setGroupId("");
        businessTypeNotActive.setRefEntryCode("");

        CommonCodeEntry[] commonCodeEntries = {businessType01, businessType02, businessType03, businessTypeNotActive};

        Header header = new Header();
        header.setResponseCode(RslResponseCode.SUCCESS.getCode());
        header.setResponseDescriptionEN(RslResponseCode.SUCCESS.getMessage());

        Body body = new Body();
        body.setCommonCodeEntries(commonCodeEntries);

        dropdownBusinessType.setHeader(header);
        dropdownBusinessType.setBody(body);
        return dropdownBusinessType;
    }

    private ResponseDropdown mockDropdownIncomeBank() {
        ResponseDropdown dropdownIncomeBank = new ResponseDropdown();

        CommonCodeEntry incomeBank01 = new CommonCodeEntry();
        incomeBank01.setActiveStatus("1");
        incomeBank01.setCategoryCode(DROPDOWN_PAYROLL_BANK);
        incomeBank01.setEntryCode("0");
        incomeBank01.setEntryID(BigDecimal.valueOf(103578));
        incomeBank01.setEntryName("Other");
        incomeBank01.setEntryName2("อื่นๆ");
        incomeBank01.setEntrySource("HOST");
        incomeBank01.setExtValue1("");
        incomeBank01.setExtValue2("");
        incomeBank01.setGroupId("");
        incomeBank01.setRefEntryCode("B18");

        CommonCodeEntry incomeBank02 = new CommonCodeEntry();
        incomeBank02.setActiveStatus("1");
        incomeBank02.setCategoryCode(DROPDOWN_PAYROLL_BANK);
        incomeBank02.setEntryCode("002");
        incomeBank02.setEntryID(BigDecimal.valueOf(103556));
        incomeBank02.setEntryName("Bangkok Bank Public Company Limited (BBL)");
        incomeBank02.setEntryName2("ธนาคาร กรุงเทพ จำกัด (มหาชน)");
        incomeBank02.setEntrySource("HOST");
        incomeBank02.setExtValue1("");
        incomeBank02.setExtValue2("");
        incomeBank02.setGroupId("");
        incomeBank02.setRefEntryCode("A02");

        CommonCodeEntry incomeBank03 = new CommonCodeEntry();
        incomeBank03.setActiveStatus("1");
        incomeBank03.setCategoryCode(DROPDOWN_PAYROLL_BANK);
        incomeBank03.setEntryCode("014");
        incomeBank03.setEntryID(BigDecimal.valueOf(103560));
        incomeBank03.setEntryName("Siam Commercial Bank Public Company Limited (SCB)");
        incomeBank03.setEntryName2("J - การบริการ");
        incomeBank03.setEntrySource("HOST");
        incomeBank03.setExtValue1("JA010200");
        incomeBank03.setExtValue2("");
        incomeBank03.setGroupId("");
        incomeBank03.setRefEntryCode("");

        CommonCodeEntry businessTypeNotActive = new CommonCodeEntry();
        businessTypeNotActive.setActiveStatus("0");
        businessTypeNotActive.setCategoryCode(DROPDOWN_PAYROLL_BANK);
        businessTypeNotActive.setEntryCode("070");
        businessTypeNotActive.setEntryID(BigDecimal.valueOf(103572));
        businessTypeNotActive.setEntryName("ACL Bank Public Company Limited (ACL)");
        businessTypeNotActive.setEntryName2("ธนาคาร ไทยพาณิชย์ จำกัด (มหาชน)");
        businessTypeNotActive.setEntrySource("HOST");
        businessTypeNotActive.setExtValue1("");
        businessTypeNotActive.setExtValue2("");
        businessTypeNotActive.setGroupId("");
        businessTypeNotActive.setRefEntryCode("A06");

        CommonCodeEntry[] commonCodeEntries = {incomeBank01, incomeBank02, incomeBank03, businessTypeNotActive};

        Header header = new Header();
        header.setResponseCode(RslResponseCode.SUCCESS.getCode());
        header.setResponseDescriptionEN(RslResponseCode.SUCCESS.getMessage());

        Body body = new Body();
        body.setCommonCodeEntries(commonCodeEntries);

        dropdownIncomeBank.setHeader(header);
        dropdownIncomeBank.setBody(body);
        return dropdownIncomeBank;
    }

    private ResponseDropdown mockDropdownIncomeType() {
        ResponseDropdown dropdownIncomeBank = new ResponseDropdown();

        CommonCodeEntry incomeType01 = new CommonCodeEntry();
        incomeType01.setActiveStatus("1");
        incomeType01.setCategoryCode(DROPDOWN_INCOME_TYPE);
        incomeType01.setEntryCode("1");
        incomeType01.setEntryID(BigDecimal.valueOf(69842));
        incomeType01.setEntryName("Salary-Cash");
        incomeType01.setEntryName2("รับรายได้ด้วยเงินสด");
        incomeType01.setEntrySource("a");
        incomeType01.setExtValue1("");
        incomeType01.setExtValue2("เงินเดือน");
        incomeType01.setGroupId("");
        incomeType01.setRefEntryCode("01");

        CommonCodeEntry incomeType02 = new CommonCodeEntry();
        incomeType02.setActiveStatus("1");
        incomeType02.setCategoryCode(DROPDOWN_INCOME_TYPE);
        incomeType02.setEntryCode("2");
        incomeType02.setEntryID(BigDecimal.valueOf(69843));
        incomeType02.setEntryName("Salary-Pay roll");
        incomeType02.setEntryName2("รับรายได้ผ่านบัญชี");
        incomeType02.setEntrySource("01");
        incomeType02.setExtValue1("MIB");
        incomeType02.setExtValue2("เงินเดือน");
        incomeType02.setGroupId("");
        incomeType02.setRefEntryCode("01");

        CommonCodeEntry incomeType03 = new CommonCodeEntry();
        incomeType03.setActiveStatus("1");
        incomeType03.setCategoryCode(DROPDOWN_INCOME_TYPE);
        incomeType03.setEntryCode("4");
        incomeType03.setEntryID(BigDecimal.valueOf(69845));
        incomeType03.setEntryName("Self Employed- jusistic");
        incomeType03.setEntryName2("รายได้จากการประกอบกิจการ-จดทะเบียนนิติบุคคล");
        incomeType03.setEntrySource("02");
        incomeType03.setExtValue1("MIB");
        incomeType03.setExtValue2("รายได้จากธุรกิจ/ธุรกิจส่วนตัว");
        incomeType03.setGroupId("");
        incomeType03.setRefEntryCode("02");

        CommonCodeEntry incomeType04 = new CommonCodeEntry();
        incomeType04.setActiveStatus("1");
        incomeType04.setCategoryCode(DROPDOWN_INCOME_TYPE);
        incomeType04.setEntryCode("6");
        incomeType04.setEntryID(BigDecimal.valueOf(69847));
        incomeType04.setEntryName("No Income");
        incomeType04.setEntryName2("ไม่มีรายได้");
        incomeType04.setEntrySource("03");
        incomeType04.setExtValue1("");
        incomeType04.setExtValue2("");
        incomeType04.setGroupId("");
        incomeType04.setRefEntryCode("03");

        CommonCodeEntry incomeType05 = new CommonCodeEntry();
        incomeType05.setActiveStatus("1");
        incomeType05.setCategoryCode(DROPDOWN_INCOME_TYPE);
        incomeType05.setEntryCode("7");
        incomeType05.setEntryID(BigDecimal.valueOf(103985));
        incomeType05.setEntryName("โปรแกรมยกเว้นเอกสารรายได้");
        incomeType05.setEntryName2("โปรแกรมยกเว้นเอกสารรายได้");
        incomeType05.setEntrySource("01");
        incomeType05.setExtValue1("");
        incomeType05.setExtValue2("เงินเดือน");
        incomeType05.setGroupId("");
        incomeType05.setRefEntryCode("01");

        CommonCodeEntry businessTypeNotActive = new CommonCodeEntry();
        businessTypeNotActive.setActiveStatus("0");
        businessTypeNotActive.setCategoryCode(DROPDOWN_INCOME_TYPE);
        businessTypeNotActive.setEntryCode("8");
        businessTypeNotActive.setEntryID(BigDecimal.valueOf(103986));
        businessTypeNotActive.setEntryName("Test Not Active");
        businessTypeNotActive.setEntryName2("ทดสอบ not active");
        businessTypeNotActive.setEntrySource("01");
        businessTypeNotActive.setExtValue1("");
        businessTypeNotActive.setExtValue2("");
        businessTypeNotActive.setGroupId("");
        businessTypeNotActive.setRefEntryCode("01");

        CommonCodeEntry[] commonCodeEntries = {incomeType01, incomeType02, incomeType03, incomeType04, incomeType05, businessTypeNotActive};

        Header header = new Header();
        header.setResponseCode(RslResponseCode.SUCCESS.getCode());
        header.setResponseDescriptionEN(RslResponseCode.SUCCESS.getMessage());

        Body body = new Body();
        body.setCommonCodeEntries(commonCodeEntries);

        dropdownIncomeBank.setHeader(header);
        dropdownIncomeBank.setBody(body);
        return dropdownIncomeBank;
    }

    private ResponseDropdown mockDropdownSciCountry() {
        ResponseDropdown dropdownSciCountry = new ResponseDropdown();

        CommonCodeEntry sciCountry01 = new CommonCodeEntry();
        sciCountry01.setActiveStatus("1");
        sciCountry01.setCategoryCode(DROPDOWN_SCI_COUNTRY);
        sciCountry01.setEntryCode("TH");
        sciCountry01.setEntryID(BigDecimal.valueOf(66831));
        sciCountry01.setEntryName("Thailand");
        sciCountry01.setEntryName2("Thailand");
        sciCountry01.setEntrySource("LOS");
        sciCountry01.setExtValue1("");
        sciCountry01.setExtValue2("");
        sciCountry01.setGroupId("");
        sciCountry01.setRefEntryCode("");

        CommonCodeEntry sciCountry02 = new CommonCodeEntry();
        sciCountry02.setActiveStatus("1");
        sciCountry02.setCategoryCode(DROPDOWN_SCI_COUNTRY);
        sciCountry02.setEntryCode("AD");
        sciCountry02.setEntryID(BigDecimal.valueOf(66624));
        sciCountry02.setEntryName("Andorra");
        sciCountry02.setEntryName2("Andorra");
        sciCountry02.setEntrySource("LOS");
        sciCountry02.setExtValue1("");
        sciCountry02.setExtValue2("");
        sciCountry02.setGroupId("");
        sciCountry02.setRefEntryCode("");

        CommonCodeEntry sciCountry03 = new CommonCodeEntry();
        sciCountry03.setActiveStatus("1");
        sciCountry03.setCategoryCode(DROPDOWN_SCI_COUNTRY);
        sciCountry03.setEntryCode("SG");
        sciCountry03.setEntryID(BigDecimal.valueOf(35416));
        sciCountry03.setEntryName("Singapore");
        sciCountry03.setEntryName2("Singapore");
        sciCountry03.setEntrySource("");
        sciCountry03.setExtValue1("");
        sciCountry03.setExtValue2("");
        sciCountry03.setGroupId("");
        sciCountry03.setRefEntryCode("");

        CommonCodeEntry[] commonCodeEntries = {sciCountry02, sciCountry01, sciCountry03};

        Header header = new Header();
        header.setResponseCode(RslResponseCode.SUCCESS.getCode());
        header.setResponseDescriptionEN(RslResponseCode.SUCCESS.getMessage());

        Body body = new Body();
        body.setCommonCodeEntries(commonCodeEntries);

        dropdownSciCountry.setHeader(header);
        dropdownSciCountry.setBody(body);
        return dropdownSciCountry;
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

    private ResponseDropdown mockGetDropdownFail() {
        ResponseDropdown dropdownEmploymentStatus = new ResponseDropdown();

        Header header = new Header();
        header.setResponseCode(RslResponseCode.FAIL.getCode());
        header.setResponseDescriptionEN(RslResponseCode.FAIL.getMessage());

        dropdownEmploymentStatus.setHeader(header);
        return dropdownEmploymentStatus;
    }


}
