package com.tmb.oneapp.lendingservice.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.tmb.common.exception.model.TMBCommonException;
import com.tmb.common.model.TmbOneServiceResponse;
import com.tmb.common.model.TmbStatus;
import com.tmb.common.model.legacy.rsl.ws.application.response.Body;
import com.tmb.common.model.legacy.rsl.ws.application.response.Header;
import com.tmb.common.model.legacy.rsl.ws.application.response.ResponseApplication;
import com.tmb.oneapp.lendingservice.client.CommonServiceFeignClient;
import com.tmb.oneapp.lendingservice.client.LoanSubmissionGetApplicationInfoClient;
import com.tmb.oneapp.lendingservice.client.ProductExpServiceClient;
import com.tmb.oneapp.lendingservice.client.SFTPClientImp;
import com.tmb.oneapp.lendingservice.constant.ResponseCode;
import com.tmb.oneapp.lendingservice.constant.RslResponseCode;
import com.tmb.oneapp.lendingservice.model.eapp.GenerateEAppReportRequest;
import com.tmb.oneapp.lendingservice.model.eapp.GenerateEAppReportResponse;
import com.tmb.oneapp.lendingservice.model.loanonline.EAppResponse;
import net.sf.jasperreports.engine.JRException;
import org.junit.Assert;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpHeaders;

import javax.xml.rpc.ServiceException;
import java.math.BigDecimal;
import java.util.Calendar;
import java.util.UUID;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@RunWith(JUnit4.class)
public class EAppReportGeneratorServiceTest {

    @InjectMocks
    private EAppReportGeneratorService eAppReportGeneratorService;

    @Mock
    private LoanSubmissionGetApplicationInfoClient loanSubmissionGetApplicationInfoClient;
    @Mock
    private CommonServiceFeignClient commonServiceFeignClient;
    @Mock
    private JasperReportService jasperReportService;
    @Mock
    private ProductExpServiceClient productExpServiceClient;
    @Mock
    private NotificationService notificationService;
    @Mock
    private SFTPClientImp sftpClientImp;

    @BeforeEach
    void setUp() throws ServiceException, TMBCommonException, JsonProcessingException, JRException {
        MockitoAnnotations.initMocks(this);
        eAppReportGeneratorService = new EAppReportGeneratorService(loanSubmissionGetApplicationInfoClient,
                commonServiceFeignClient, jasperReportService, productExpServiceClient, notificationService, sftpClientImp);
        mockSuccess();
    }

    private void mockSuccess() throws ServiceException, TMBCommonException, JsonProcessingException, JRException {
        doReturn(mockResponseLoanOnlineSubmissionEApp()).when(productExpServiceClient).getLoanOnlineSubmissionEApp(any(), any());
        doReturn(mockApplicationInfoByCaID()).when(loanSubmissionGetApplicationInfoClient).searchApplicationInfoByCaID(anyLong());
        doReturn(LoanServiceUtils.moduleLendingModuleConfig()).when(commonServiceFeignClient).getCommonConfig(any(), anyString());
        doNothing().when(jasperReportService).setReportFileName(anyString());
        doNothing().when(jasperReportService).compileReport();
        doNothing().when(jasperReportService).setParameters(any());
        doNothing().when(jasperReportService).fillReport();
        doNothing().when(jasperReportService).exportToPdf(any(), any());
        doReturn(true).when(sftpClientImp).storeFile(anyList());
        doNothing().when(notificationService).sendNotifyEAppReportGenerator(anyString(), anyString(), anyString(), any());
    }

    @Test
    public void generateEAppReport_CreditCard_Success() throws TMBCommonException, ServiceException, JsonProcessingException {
        GenerateEAppReportRequest request = new GenerateEAppReportRequest();
        request.setCaId("1");
        request.setProductCode("VJ");

        GenerateEAppReportResponse response = eAppReportGeneratorService.generateEAppReport(new HttpHeaders(),
                request, "correlationId", "crmId");
        Assert.assertNotNull(response);
    }

    @Test
    public void generateEAppReport_FlashCard_Success() throws TMBCommonException, ServiceException, JsonProcessingException {
        GenerateEAppReportRequest request = new GenerateEAppReportRequest();
        request.setCaId("1");
        request.setProductCode("RC01");
        request.setIsLoanDayOne(true);

        GenerateEAppReportResponse response = eAppReportGeneratorService.generateEAppReport(new HttpHeaders(),
                request, "correlationId", "crmId");
        Assert.assertNotNull(response);
    }

    @Test
    public void generateEAppReport_C2GCard_Success() throws TMBCommonException, ServiceException, JsonProcessingException {
        GenerateEAppReportRequest request = new GenerateEAppReportRequest();
        request.setCaId("1");
        request.setProductCode("C2G");

        GenerateEAppReportResponse response = eAppReportGeneratorService.generateEAppReport(new HttpHeaders(),
                request, "correlationId", "crmId");
        Assert.assertNotNull(response);
    }

    @Test
    public void generateEAppReport_invalidProductCode_Failed() {
        GenerateEAppReportRequest request = new GenerateEAppReportRequest();
        request.setCaId("1");
        request.setProductCode("XXX");

        Assertions.assertThrows(TMBCommonException.class, () -> eAppReportGeneratorService.generateEAppReport(new HttpHeaders(),
                request, "correlationId", "crmId"));
    }

    private TmbOneServiceResponse<EAppResponse> mockResponseLoanOnlineSubmissionEApp() {
        Calendar c = Calendar.getInstance();
        TmbOneServiceResponse<EAppResponse> response = new TmbOneServiceResponse<>();
        EAppResponse eAppResponse = new EAppResponse();
        eAppResponse.setProductNameTh("สินเชื่อบุคคล ทีทีบี แคชทูโก");
        eAppResponse.setProductType("สินเชื่อส่วนบุคคล");
        eAppResponse.setAppNo("026PL64001220");
        eAppResponse.setEmploymentStatus("พนักงานบริษัท");
        eAppResponse.setSalary(BigDecimal.valueOf(24000));
        eAppResponse.setOtherIncome(BigDecimal.valueOf(10000));
        eAppResponse.setRequestAmount(BigDecimal.valueOf(12000));
        eAppResponse.setMonthlyInstallment(BigDecimal.valueOf(2500));
        eAppResponse.setCashFlow(BigDecimal.valueOf(1000000));
        eAppResponse.setPaymentAccountNo("1234567890");
        eAppResponse.setMobileNo("0866666666");
        eAppResponse.setWorkPeriodYear("1");
        eAppResponse.setWorkPeriodYear("1");
        eAppResponse.setWorkTel("022222222");
        eAppResponse.setIncomeBankAccountNo("9876543210");
        eAppResponse.setAcceptDate(c);
        eAppResponse.setIssueDate(c);
        eAppResponse.setExpiryDate(c);
        eAppResponse.setBirthDay(c);
        eAppResponse.setIdNo("1234567890123");
        response.setStatus(new TmbStatus(ResponseCode.SUCCESS.getCode(), ResponseCode.SUCCESS.getMessage(),
                ResponseCode.SUCCESS.getService(), ResponseCode.SUCCESS.getDesc()));
        response.setData(eAppResponse);
        return response;
    }

    private ResponseApplication mockApplicationInfoByCaID() {
        ResponseApplication response = new ResponseApplication();
        Header header = new Header();
        header.setResponseCode(RslResponseCode.SUCCESS.getCode());
        header.setResponseDescriptionEN("success");
        header.setChannel("MIB");
        header.setModule("3");
        header.setRequestID(UUID.randomUUID().toString());
        Body body = new Body();
        body.setAppRefNo("026PL64000674");
        body.setAppType("PL");
        body.setApplicationDate("2021-08-05T10:37:18.000Z");
        body.setBookBranchCode("026");
        body.setBranchCode("026");
        body.setCaId(BigDecimal.valueOf(2021080504188295L));
        body.setMemberref("MIB026PL64000674");
        body.setNatureOfRequest("11");
        body.setNcbConsentFlag("Y");
        body.setProduct("RC");
        body.setProductDescEN("ttb flash card");
        body.setProductDescTH("บัตรกดเงินสด ทีทีบี แฟลซ");
        body.setSubProduct("RC01");
        body.setSubProductDescEN("ttb flash card");
        body.setProductDescTH("บัตรกดเงินสด ทีทีบี แฟลช");

        response.setHeader(header);
        response.setBody(body);
        return response;
    }
}
