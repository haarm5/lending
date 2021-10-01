package com.tmb.oneapp.lendingservice.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.tmb.common.exception.model.TMBCommonException;
import com.tmb.common.model.TmbOneServiceResponse;
import com.tmb.common.model.TmbStatus;
import com.tmb.common.model.legacy.rsl.ws.application.response.Body;
import com.tmb.common.model.legacy.rsl.ws.application.response.Header;
import com.tmb.common.model.legacy.rsl.ws.application.response.ResponseApplication;
import com.tmb.oneapp.lendingservice.client.CommonServiceFeignClient;
import com.tmb.oneapp.lendingservice.client.ReportServiceClient;
import com.tmb.oneapp.lendingservice.client.SFTPClientImp;
import com.tmb.oneapp.lendingservice.constant.ResponseCode;
import com.tmb.oneapp.lendingservice.constant.RslResponseCode;
import com.tmb.oneapp.lendingservice.model.eapp.ReportGeneratorRequest;
import com.tmb.oneapp.lendingservice.model.eapp.ReportGeneratorResponse;
import com.tmb.oneapp.lendingservice.model.loanonline.EAppResponse;
import com.tmb.oneapp.lendingservice.model.report.ReportGenerateClientResponse;
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
import java.io.IOException;
import java.math.BigDecimal;
import java.rmi.RemoteException;
import java.text.ParseException;
import java.util.Calendar;
import java.util.UUID;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@RunWith(JUnit4.class)
public class ReportGeneratorServiceTest {

    @InjectMocks
    private ReportGeneratorService reportGeneratorService;

    @Mock
    private RslService rslService;
    @Mock
    private CommonServiceFeignClient commonServiceFeignClient;
    @Mock
    private ReportServiceClient reportServiceClient;
    @Mock
    private LoanOnlineSubmissionEAppService loanOnlineSubmissionEAppService;
    @Mock
    private NotificationService notificationService;
    @Mock
    private SFTPClientImp sftpClientImp;

    @BeforeEach
    void setUp() throws ServiceException, TMBCommonException, JsonProcessingException, JRException, ParseException, RemoteException {
        MockitoAnnotations.initMocks(this);
        reportGeneratorService = new ReportGeneratorService(rslService,
                commonServiceFeignClient, reportServiceClient, loanOnlineSubmissionEAppService, notificationService, sftpClientImp);
        mockSuccess();
    }

    private void mockSuccess() throws ServiceException, TMBCommonException, JsonProcessingException, ParseException, RemoteException {
        doReturn(mockResponseLoanOnlineSubmissionEApp()).when(loanOnlineSubmissionEAppService).getEApp(anyLong(), any(),any());
        doReturn(mockApplicationInfoByCaID()).when(rslService).getLoanSubmissionApplicationInfo(any());
        doReturn(LoanServiceUtils.moduleLendingModuleConfig()).when(commonServiceFeignClient).getCommonConfig(any(), anyString());
        doReturn(mockReportServiceResponse()).when(reportServiceClient).generateReport(anyString(), any());
        doReturn(true).when(sftpClientImp).storeFile(anyList());
        doNothing().when(notificationService).sendNotifyEAppReportGenerator(anyString(), anyString(), anyString(), any());
    }

    @Test
    public void generateEAppReport_CreditCard_Success() throws TMBCommonException, ServiceException, ParseException, IOException {
        ReportGeneratorRequest request = new ReportGeneratorRequest();
        request.setCaId("1");
        request.setProductCode("VJ");

        ReportGeneratorResponse response = reportGeneratorService.generateEAppReport("account-id",
                request, "correlationId", "crmId");
        Assert.assertNotNull(response);
    }

    @Test
    public void generateEAppReport_FlashCard_Success() throws TMBCommonException, ServiceException, IOException, ParseException {
        ReportGeneratorRequest request = new ReportGeneratorRequest();
        request.setCaId("1");
        request.setProductCode("RC01");

        ReportGeneratorResponse response = reportGeneratorService.generateEAppReport("account-id",
                request, "correlationId", "crmId");
        Assert.assertNotNull(response);
    }

    @Test
    public void generateEAppReport_C2GCard_Success() throws TMBCommonException, ServiceException, IOException, ParseException {
        ReportGeneratorRequest request = new ReportGeneratorRequest();
        request.setCaId("1");
        request.setProductCode("C2G");

        ReportGeneratorResponse response = reportGeneratorService.generateEAppReport("account-id",
                request, "correlationId", "crmId");
        Assert.assertNotNull(response);
    }

    @Test
    public void generateEAppReport_invalidProductCode_Failed() {
        ReportGeneratorRequest request = new ReportGeneratorRequest();
        request.setCaId("1");
        request.setProductCode("XXX");

        Assertions.assertThrows(TMBCommonException.class, () -> reportGeneratorService.generateEAppReport("account-id",
                request, "correlationId", "crmId"));
    }

    private EAppResponse mockResponseLoanOnlineSubmissionEApp() {
        Calendar c = Calendar.getInstance();
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
        return eAppResponse;
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

    private TmbOneServiceResponse<ReportGenerateClientResponse> mockReportServiceResponse() {
        TmbOneServiceResponse<ReportGenerateClientResponse> serviceResponse = new TmbOneServiceResponse<>();
        serviceResponse.setStatus(new TmbStatus(ResponseCode.SUCCESS.getCode(), ResponseCode.SUCCESS.getMessage(), "report-service"));

        ReportGenerateClientResponse response = new ReportGenerateClientResponse();
        response.setType("PDF");
        response.setCode("0000");
        response.setBase64("base64file");

        serviceResponse.setData(response);
        return serviceResponse;
    }
}
