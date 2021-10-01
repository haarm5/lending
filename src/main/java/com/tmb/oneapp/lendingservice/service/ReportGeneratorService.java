package com.tmb.oneapp.lendingservice.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.tmb.common.exception.model.TMBCommonException;
import com.tmb.common.logger.TMBLogger;
import com.tmb.common.model.RslCode;
import com.tmb.common.model.legacy.rsl.ws.application.response.ResponseApplication;
import com.tmb.oneapp.lendingservice.client.CommonServiceFeignClient;
import com.tmb.oneapp.lendingservice.client.ReportServiceClient;
import com.tmb.oneapp.lendingservice.client.SFTPClientImp;
import com.tmb.oneapp.lendingservice.constant.EAppCardCategory;
import com.tmb.oneapp.lendingservice.constant.ResponseCode;
import com.tmb.oneapp.lendingservice.model.SFTPStoreFileInfo;
import com.tmb.oneapp.lendingservice.model.config.LendingModuleConfig;
import com.tmb.oneapp.lendingservice.model.eapp.ReportGeneratorRequest;
import com.tmb.oneapp.lendingservice.model.eapp.ReportGeneratorResponse;
import com.tmb.oneapp.lendingservice.model.loanonline.EAppResponse;
import com.tmb.oneapp.lendingservice.model.notification.ReportGeneratorNotificationWrapper;
import com.tmb.oneapp.lendingservice.model.report.ReportGenerateClientRequest;
import com.tmb.oneapp.lendingservice.model.report.ReportGenerateClientResponse;
import com.tmb.oneapp.lendingservice.model.rsl.LoanSubmissionGetApplicationInfoRequest;
import com.tmb.oneapp.lendingservice.util.CommonServiceUtils;
import com.tmb.oneapp.lendingservice.util.Fetch;
import com.tmb.oneapp.lendingservice.util.FileConvertorUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import javax.xml.rpc.ServiceException;
import java.io.IOException;
import java.math.BigDecimal;
import java.text.ParseException;
import java.util.*;

import static com.tmb.oneapp.lendingservice.constant.LendingServiceConstant.REPORT_TYPE_PDF;
import static com.tmb.oneapp.lendingservice.constant.LendingServiceConstant.SEPARATOR;

@Service
public class ReportGeneratorService {

    private static TMBLogger<ReportGeneratorService> logger = new TMBLogger<>(ReportGeneratorService.class);

    private final RslService rslService;
    private final CommonServiceFeignClient commonServiceFeignClient;
    private final ReportServiceClient reportServiceClient;
    private final LoanOnlineSubmissionEAppService loanOnlineSubmissionEAppService;
    private final NotificationService notificationService;
    private final SFTPClientImp sftpClientImp;

    @Value("${sftp.locations.loan.root}")
    private String sftpLocationLoanRoot;

    @Value("${sftp.locations.loan.dir}")
    private String sftpLocationLoanDir;

    @Value("${sftp.locations.e-noti.root}")
    private String sftpLocationENotiRoot;

    @Value("${sftp.locations.e-noti.dir}")
    private String sftpLocationENotiDir;

    public ReportGeneratorService(RslService rslService,
                                      CommonServiceFeignClient commonServiceFeignClient,
                                      ReportServiceClient reportServiceClient,
                                      LoanOnlineSubmissionEAppService loanOnlineSubmissionEAppService,
                                      NotificationService notificationService,
                                      SFTPClientImp sftpClientImp) {
        this.rslService = rslService;
        this.commonServiceFeignClient = commonServiceFeignClient;
        this.reportServiceClient = reportServiceClient;
        this.loanOnlineSubmissionEAppService = loanOnlineSubmissionEAppService;
        this.notificationService = notificationService;
        this.sftpClientImp = sftpClientImp;
    }

    public ReportGeneratorResponse generateEAppReport(String accountId, ReportGeneratorRequest request, String correlationId, String crmId) throws TMBCommonException, ServiceException, IOException, ParseException {
        long caId = Long.parseLong(request.getCaId());
        String productCode = request.getProductCode();
        EAppResponse eAppResponse = loanOnlineSubmissionEAppService.getEApp(caId, crmId, correlationId);
        //For testing purpose
        eAppResponse.setEmail("jirat.cho@odds.team");

        String template;
        Map<String, Object> parameters = new HashMap<>();
        switch (productCode) {
            case "VJ":
            case "VP":
            case "VM":
            case "VH":
            case "VI":
            case "VB":
                template = prepareCreditCardParameters(parameters, eAppResponse);
                break;
            case "RC01":
                template = prepareFlashCardParameters(parameters, eAppResponse);
                break;
            case "C2G":
                template = prepareC2GCardParameters(parameters, eAppResponse);
                break;
            default:
                template = "";
        }

        LoanSubmissionGetApplicationInfoRequest rslRequest = new LoanSubmissionGetApplicationInfoRequest();
        rslRequest.setCaId(request.getCaId());
        ResponseApplication applicationInfo = rslService.getLoanSubmissionApplicationInfo(rslRequest);
        String appRefNo = applicationInfo.getBody().getAppRefNo();

        if (template.isBlank()) {
            throw new TMBCommonException(ResponseCode.EAPP_INVALID_PRODUCT_CODE.getCode(),
                    ResponseCode.EAPP_INVALID_PRODUCT_CODE.getMessage(),
                    ResponseCode.EAPP_INVALID_PRODUCT_CODE.getService(),
                    HttpStatus.BAD_REQUEST, null);
        } else {
            ReportGenerateClientResponse reportClientResponse = generateReportByReportService(parameters, template, correlationId);
            String baseDir = System.getProperty("user.dir");
            String srcDir = String.format("%s/documents/loan/%s/%s", baseDir, crmId, appRefNo);
            String fileName = generateFilename(applicationInfo);

            String filePath = FileConvertorUtils.generateFileFromBase64(srcDir, fileName, reportClientResponse.getBase64());

            stores(crmId, appRefNo, filePath);

            ReportGeneratorNotificationWrapper notificationWrapper = prepareNotificationWrapper(eAppResponse, productCode, applicationInfo, correlationId, fileName);
            sendNotification(accountId, crmId, correlationId, notificationWrapper);

            return new ReportGeneratorResponse(productCode, fileName);
        }
    }

    private ReportGenerateClientResponse generateReportByReportService(Map<String, Object> parameters, String template, String correlationId) throws TMBCommonException {
        ReportGenerateClientRequest reportReq = new ReportGenerateClientRequest();
        reportReq.setTemplateId(template);
        reportReq.setType(REPORT_TYPE_PDF);

        JsonNode jsonNodeMap = new ObjectMapper().valueToTree(parameters);
        reportReq.setData(jsonNodeMap);
        return Fetch.fetch(() -> reportServiceClient.generateReport(correlationId, reportReq));
    }

    private ReportGeneratorNotificationWrapper prepareNotificationWrapper(EAppResponse response, String productCode, ResponseApplication applicationInfo, String correlationId, String fileName) throws TMBCommonException {
        String appRefNo = applicationInfo.getBody().getAppRefNo();
        List<String> attachments = prepareAttachments(applicationInfo, correlationId, fileName, productCode);
        return buildNotificationWrapper(response, appRefNo, attachments);
    }

    private ReportGeneratorNotificationWrapper buildNotificationWrapper(EAppResponse response, String appRefNo, List<String> attachments) {
        ReportGeneratorNotificationWrapper wrapper = new ReportGeneratorNotificationWrapper();
        wrapper.setCustomerNameTh(beautifyString(response.getNameTh()));
        wrapper.setCustomerNameEn(beautifyString(response.getNameEn()));
        wrapper.setProductNameEn(null);
        wrapper.setProductNameTh(beautifyString(response.getProductNameTh()));
        wrapper.setAppRefNo(appRefNo);
        wrapper.setEmail(response.getEmail());

        wrapper.setAttachments(attachments);

        return wrapper;
    }

    private List<String> prepareAttachments(ResponseApplication application, String correlationId, String fileName, String productCode) throws TMBCommonException {
        List<String> notificationAttachments = new ArrayList<>();
        String letterOfConsent = getLetterOfConsentFilePath(application);
        notificationAttachments.add(letterOfConsent);

        RslCode rslConfig = getRslConfig(correlationId).stream().filter(rslCode -> rslCode.getRslCode().contains(productCode)).findFirst().orElse(null);
        if (rslConfig != null) {
            String saleSheetAttachments = getSaleSheetFilePath(rslConfig);
            String termAndConditionAttachments = getTermAndConditionFilePath(rslConfig);

            notificationAttachments.add(saleSheetAttachments);
            notificationAttachments.add(termAndConditionAttachments);
        }

        String eAppAttachment = String.format("sftp://%s%s%s/%s", sftpClientImp.getRemoteHost(),
                sftpLocationENotiRoot, sftpLocationENotiDir, fileName);
        logger.info("eApp: {}", eAppAttachment);
        notificationAttachments.add(eAppAttachment);
        return notificationAttachments;
    }

    private void sendNotification(String accountId, String crmId, String correlationId, ReportGeneratorNotificationWrapper wrapper) {
        try {
            notificationService.sendNotifyEAppReportGenerator(crmId, accountId, correlationId, wrapper);
        } catch (Exception e) {
            logger.error("sendNotifyEAppReportGenerator error: {}", e);
            throw e;
        }
    }

    private void stores(String crmId, String appRefNo, String srcFile) throws TMBCommonException {
        storeFileOnSFTP(sftpLocationLoanRoot, sftpLocationLoanDir + SEPARATOR + "ApplyLoan" +SEPARATOR + crmId + SEPARATOR + appRefNo, srcFile);
        storeFileOnSFTP(sftpLocationLoanRoot, sftpLocationLoanDir, srcFile);
        storeFileOnSFTP(sftpLocationENotiRoot, sftpLocationENotiDir, srcFile);
    }

    private String prepareCreditCardParameters(Map<String, Object> parameters, EAppResponse eAppResponse) {
        buildCommonParameters(parameters, eAppResponse);
        parameters.put("payment_criteria", eAppResponse.getPaymentCriteria());

        return EAppCardCategory.CREDIT_CARD.getTemplate();
    }

    private String prepareFlashCardParameters(Map<String, Object> parameters, EAppResponse eAppResponse) {
        buildCommonParameters(parameters, eAppResponse);
        buildBankInfoParameters(parameters, eAppResponse);
        parameters.put("payment_plan", beautifyString(eAppResponse.getPaymentPlan()));
        parameters.put("payment_criteria", beautifyString(eAppResponse.getPaymentCriteria()));
        parameters.put("is_loan_day_one", StringUtils.isBlank(eAppResponse.getDisburstAccountNo()) ? "N" : "Y");

        return EAppCardCategory.FLASH_CARD.getTemplate();
    }

    private String prepareC2GCardParameters(Map<String, Object> parameters, EAppResponse eAppResponse) {
        buildCommonParameters(parameters, eAppResponse);
        buildBankInfoParameters(parameters, eAppResponse);
        parameters.put("monthly_installment", beautifyBigDecimal(eAppResponse.getMonthlyInstallment()));
        parameters.put("interest", String.format("%s%%", eAppResponse.getInterest()));

        return EAppCardCategory.C2G_CARD.getTemplate();
    }

    private void buildBankInfoParameters(Map<String, Object> parameters, EAppResponse eAppResponse) {
        parameters.put("request_amount", beautifyBigDecimal(eAppResponse.getRequestAmount()));
        parameters.put("tenure", formatBigDecimalToString(eAppResponse.getTenure()));
        parameters.put("loan_with_other_bank", beautifyString(eAppResponse.getLoanWithOtherBank()));
        parameters.put("consider_loan_with_other_bank", beautifyString(eAppResponse.getConsiderLoanWithOtherBank()));
    }

    private String checkForDirectDebit(String paymentMethod) {
        return "ตัดบัญชีธนาคาร".equalsIgnoreCase(paymentMethod) ? "Y" : "N";
    }

    private void buildCommonParameters(Map<String, Object> parameters, EAppResponse eAppResponse) {
        //Loan Detail Section
        parameters.put("app_no", eAppResponse.getAppNo());
        parameters.put("product_name", eAppResponse.getProductNameTh());
        parameters.put("product_type", eAppResponse.getProductType());
        parameters.put("employment_status", eAppResponse.getEmploymentStatus());
        parameters.put("salary", beautifyBigDecimal(eAppResponse.getSalary()));
        parameters.put("other_income", beautifyBigDecimal(eAppResponse.getOtherIncome()));
        parameters.put("apox_income", beautifyBigDecimal(eAppResponse.getSalary().add(eAppResponse.getOtherIncome())));

        //Loan Payment Detail Section
        parameters.put("payment_method", eAppResponse.getPaymentMethod());
        parameters.put("payment_account_name", eAppResponse.getPaymentAccountName());
        parameters.put("payment_account_no", eAppResponse.getPaymentAccountNo());
        parameters.put("is_direct_debit", checkForDirectDebit(eAppResponse.getPaymentMethod()));

        //Personal Detail Section
        parameters.put("id_type", eAppResponse.getIdType());
        parameters.put("id_no", CommonServiceUtils.formatCustomerId(eAppResponse.getIdNo()));
        parameters.put("issue_country", eAppResponse.getIssueCountry());
        parameters.put("issue_date", convertToThaiDate(eAppResponse.getIssueDate()));
        parameters.put("expiry_date", convertToThaiDate(eAppResponse.getExpiryDate()));
        parameters.put("name_th", eAppResponse.getNameTh());
        parameters.put("name_en", eAppResponse.getNameEn());
        parameters.put("birth_day", convertToThaiDate(eAppResponse.getBirthDay()));
        parameters.put("mobile_no", CommonServiceUtils.formatPhoneNumber(
                CommonServiceUtils.maskPhoneNumber(eAppResponse.getMobileNo())));
        parameters.put("education_level", eAppResponse.getEducationLevel());
        parameters.put("source_from_country", eAppResponse.getSourceFromCountry());
        parameters.put("nationality", eAppResponse.getNationality());
        parameters.put("marital_status", eAppResponse.getMaritalStatus());
        parameters.put("place_of_birth", eAppResponse.getPlaceOfBirth());
        parameters.put("email", eAppResponse.getEmail());
        parameters.put("contact_address", eAppResponse.getContactAddress());
        parameters.put("resident_status", eAppResponse.getResidentStatus());

        //Job Detail Section
        parameters.put("rm_occupation", eAppResponse.getRmOccupation());
        parameters.put("occupation", eAppResponse.getOccupation());
        parameters.put("contract_type", eAppResponse.getContractType());
        parameters.put("work_period", CommonServiceUtils.formatWorkPeriod(eAppResponse.getWorkPeriodYear(),
                eAppResponse.getWorkPeriodMonth()));
        parameters.put("work_name", eAppResponse.getWorkName());
        parameters.put("work_address", eAppResponse.getWorkAddress());
        parameters.put("work_tel", CommonServiceUtils.formatPhoneNumber(eAppResponse.getWorkTel()));
        parameters.put("work_tel_ex", eAppResponse.getWorkTelEx());
        parameters.put("income_bank", eAppResponse.getIncomeBank());
        parameters.put("income_bank_account_no", CommonServiceUtils.formatBankAccountNo(eAppResponse.getIncomeBankAccountNo()));
        parameters.put("cash_flow", beautifyBigDecimal(eAppResponse.getCashFlow()));
        parameters.put("share_percent", eAppResponse.getSharePercent());
        parameters.put("company_source_of_income", eAppResponse.getSourceFromCountry());
        parameters.put("estatement", eAppResponse.getEStatement());
        parameters.put("delivery", eAppResponse.getDelivery());
        parameters.put("ncb_model_accept", eAppResponse.getNcbModelAccept());

        //Consent Section
        parameters.put("accept_by", eAppResponse.getAcceptBy());
        parameters.put("consent_date", convertToThaiDate(eAppResponse.getAcceptDate()));
        parameters.put("consent_time", CommonServiceUtils.getTimeInHHMMSS(eAppResponse.getAcceptDate().getTime()));
    }
    
    private void storeFileOnSFTP(String rootPath, String dir, String srcFile) throws TMBCommonException {
        try {
            List<SFTPStoreFileInfo> sftpStoreFiles = new ArrayList<>();

            SFTPStoreFileInfo sftpStoreFile = new SFTPStoreFileInfo();
            sftpStoreFile.setRootPath(rootPath);
            sftpStoreFile.setDstDir(dir);
            sftpStoreFile.setSrcFile(srcFile);
            sftpStoreFiles.add(sftpStoreFile);

            sftpClientImp.storeFile(sftpStoreFiles);
        } catch (Exception e) {
            throw new TMBCommonException(ResponseCode.SFTP_FAILED.getCode(), "SFTP file : " + srcFile + " fail.", ResponseCode.SFTP_FAILED.getService(), HttpStatus.INTERNAL_SERVER_ERROR, null);
        }
    }

    private String generateFilename(ResponseApplication application) {
        String appRefNo = application.getBody().getAppRefNo();
        String dateStr = application.getBody().getApplicationDate();
        dateStr = dateStr.replaceAll("[-:T ]", "");
        dateStr = dateStr.substring(2, 14);
        String docType = "00111";
        return String.format("01_%s_%s_%s.pdf", dateStr, appRefNo, docType);
    }

    private String getLetterOfConsentFilePath(ResponseApplication application) {
        String appRefNo = application.getBody().getAppRefNo();
        String dateStr = application.getBody().getApplicationDate();
        dateStr = dateStr.replaceAll("[-:T ]", "");
        dateStr = dateStr.substring(2, 14);
        String docType = "00110";
        String letterOfConsentFilePath = String.format("sftp://%s%s%s/01_%s_%s_%s.JPG", sftpClientImp.getRemoteHost(),
                sftpLocationENotiRoot, sftpLocationENotiDir, dateStr, appRefNo, docType);
        logger.info("letterOfConsentFilePath: {}", letterOfConsentFilePath);
        return letterOfConsentFilePath;
    }

    private String getSaleSheetFilePath(RslCode rslConfig) {
        String saleSheetFile = rslConfig.getSalesheetName();
        String saleSheetFilePath = String.format("sftp://%s%s/%s", sftpClientImp.getRemoteHost(), sftpLocationENotiRoot, saleSheetFile);
        logger.info("saleSheetFilePath: {}", saleSheetFilePath);
        return saleSheetFilePath;
    }

    private String getTermAndConditionFilePath(RslCode rslConfig) {
        String tncFile = rslConfig.getTncName();
        String tncFilePath = String.format("sftp://%s%s/%s", sftpClientImp.getRemoteHost(), sftpLocationENotiRoot, tncFile);
        logger.info("tncFilePath: {}", tncFilePath);
        return tncFilePath;
    }

    private List<RslCode> getRslConfig(String correlationId) throws TMBCommonException {
        List<LendingModuleConfig> config = Fetch
                .fetch(() -> commonServiceFeignClient.getCommonConfig(correlationId, "lending_module"));
        return config.get(0).getDefaultRslCode();
    }

    private String convertToThaiDate(Calendar calendar) {
        Date date = calendar.getTime();
        String dateEng = CommonServiceUtils.getDateInYYYYMMDD(date);
        return CommonServiceUtils.getThaiDate(dateEng);
    }

    private String formatBigDecimalToString(BigDecimal value) {
        return (value != null) ? value.toString() : "-";
    }

    private String beautifyString(String value) {
        return CommonServiceUtils.formatStringForReport(value);
    }

    private String beautifyBigDecimal(BigDecimal value) {
        return CommonServiceUtils.format2DigitDecimalPoint(value);
    }
}
