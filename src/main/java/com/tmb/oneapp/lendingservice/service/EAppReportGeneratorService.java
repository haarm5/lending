package com.tmb.oneapp.lendingservice.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.tmb.common.exception.model.TMBCommonException;
import com.tmb.common.logger.TMBLogger;
import com.tmb.common.model.legacy.rsl.ws.application.response.Body;
import com.tmb.common.model.legacy.rsl.ws.application.response.ResponseApplication;
import com.tmb.oneapp.lendingservice.client.LoanSubmissionGetApplicationInfoClient;
import com.tmb.oneapp.lendingservice.client.ProductExpServiceClient;
import com.tmb.oneapp.lendingservice.client.SFTPClientImp;
import com.tmb.oneapp.lendingservice.constant.EAppCardCategory;
import com.tmb.oneapp.lendingservice.constant.LendingServiceConstant;
import com.tmb.oneapp.lendingservice.constant.ResponseCode;
import com.tmb.oneapp.lendingservice.model.SFTPStoreFileInfo;
import com.tmb.oneapp.lendingservice.model.eapp.GenerateEAppReportRequest;
import com.tmb.oneapp.lendingservice.model.eapp.GenerateEAppReportResponse;
import com.tmb.oneapp.lendingservice.model.loanonline.EAppResponse;
import com.tmb.oneapp.lendingservice.model.notification.EAppReportGeneratorWrapper;
import com.tmb.oneapp.lendingservice.util.CommonServiceUtils;
import com.tmb.oneapp.lendingservice.util.Fetch;
import com.tmb.oneapp.lendingservice.util.RslServiceUtils;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperExportManager;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import javax.xml.rpc.ServiceException;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.*;

import static com.tmb.oneapp.lendingservice.constant.LendingServiceConstant.SEPARATOR;

@Service
public class EAppReportGeneratorService {

    private static TMBLogger<EAppReportGeneratorService> logger = new TMBLogger<>(EAppReportGeneratorService.class);

    private final LoanSubmissionGetApplicationInfoClient loanSubmissionGetApplicationInfoClient;
    private final JasperReportFillerService jasperReportFillerService;
    private final ProductExpServiceClient productExpServiceClient;
    private final NotificationService notificationService;
    private final SFTPClientImp sftpClientImp;

    @Value("${sftp.locations.eapp.reports}")
    private String sftpLocations;

    public EAppReportGeneratorService(LoanSubmissionGetApplicationInfoClient loanSubmissionGetApplicationInfoClient,
                                      JasperReportFillerService jasperReportFillerService,
                                      ProductExpServiceClient productExpServiceClient,
                                      NotificationService notificationService,
                                      SFTPClientImp sftpClientImp) {
        this.loanSubmissionGetApplicationInfoClient = loanSubmissionGetApplicationInfoClient;
        this.jasperReportFillerService = jasperReportFillerService;
        this.productExpServiceClient = productExpServiceClient;
        this.notificationService = notificationService;
        this.sftpClientImp = sftpClientImp;
    }

    public GenerateEAppReportResponse generateEAppReport(HttpHeaders headers, GenerateEAppReportRequest request, String correlationId, String crmId) throws TMBCommonException, ServiceException, JsonProcessingException {
        String caId = request.getCaId();
        String productCode = request.getProductCode();
        EAppResponse response = Fetch.fetch(() -> productExpServiceClient.getLoanOnlineSubmissionEApp(crmId, caId));

        Body applicationInfo = getApplicationInfo(Long.parseLong(caId));
        String appRefNo = applicationInfo.getAppRefNo();

        String fileName = "";
        Map<String, Object> parameters = new HashMap<>();
        switch (productCode) {
            case "VJ":
            case "VP":
            case "VM":
            case "VH":
            case "VI":
            case "VB": {
                fileName = EAppCardCategory.C2G_CARD.getTemplate();
                parameters.putAll(buildCreditCardParameters(response));
            }
            case "RC01": {
                fileName = EAppCardCategory.C2G_CARD.getTemplate();
                parameters.putAll(buildFlashCardParameters(response));
            }
            case "C2G": {
                fileName = EAppCardCategory.C2G_CARD.getTemplate();
                parameters.putAll(buildC2GCardParameters(response));
            }
        }

        if (fileName.isBlank()) {
            throw new TMBCommonException(ResponseCode.EAPP_INVALID_PRODUCT_CODE.getCode(),
                    ResponseCode.EAPP_INVALID_PRODUCT_CODE.getMessage(),
                    ResponseCode.EAPP_INVALID_PRODUCT_CODE.getService(),
                    HttpStatus.BAD_REQUEST, null);
        } else {
            buildJasperReport(fileName, parameters);
            storeToSftp(crmId, appRefNo, productCode, fileName);

            //Need to verify sftp path again
            String eAppAttachment = String.format("sftp://%s/users/enotiftp/SIT/MIB/TempAttachments/%s.pdf", sftpClientImp.getRemoteHost(), fileName);
            EAppReportGeneratorWrapper wrapper = buildWrapper(response, appRefNo, eAppAttachment);
            sendNotification(headers, crmId, correlationId, wrapper);
        }

        return new GenerateEAppReportResponse(productCode, fileName);
    }

    private EAppReportGeneratorWrapper buildWrapper(EAppResponse response, String appRefNo, String attachment) {
        EAppReportGeneratorWrapper wrapper = new EAppReportGeneratorWrapper();
        wrapper.setCustomerNameTh(response.getNameTh());
        wrapper.setCustomerNameEn(response.getNameEn());
        wrapper.setProductNameEn(null);
        wrapper.setProductNameTh(response.getProductNameTh());
        wrapper.setAppRefNo(appRefNo);
        wrapper.setEmail(response.getEmail());

        wrapper.setAttachments(List.of(attachment));

        return wrapper;
    }

    private void sendNotification(HttpHeaders headers, String crmId, String correlationId, EAppReportGeneratorWrapper wrapper) {
        try {
            notificationService.sendNotifyEAppReportGenerator(crmId, headers.getFirst(LendingServiceConstant.HEADER_ACCOUNT_ID),
                    correlationId, wrapper);
        } catch (Exception e) {
            logger.error("sendNotifyEAppReportGenerator error: {}", e);
            throw e;
        }
    }

    private void buildJasperReport(String reportName, Map<String, Object> parameters) {
        jasperReportFillerService.setReportFileName(String.format("%s.jasper", reportName));
        jasperReportFillerService.compileReport();
        jasperReportFillerService.setParameters(parameters);
        jasperReportFillerService.fillReport();
    }

    private void storeToSftp(String crmId, String appRefNo, String productCode, String fileName) throws TMBCommonException {
        ByteArrayOutputStream os = new ByteArrayOutputStream();
        try {
            JasperExportManager.exportReportToPdfStream(jasperReportFillerService.getJasperPrint(), os);
        } catch (JRException e) {
            throw new TMBCommonException(ResponseCode.JASPER_REPORT_ERROR.getCode(),
                    ResponseCode.JASPER_REPORT_ERROR.getMessage(),
                    ResponseCode.JASPER_REPORT_ERROR.getService(),
                    HttpStatus.INTERNAL_SERVER_ERROR, e);
        }

        try {
            String srcFile = generateFileFromOutputStream(fileName, os);
            String dir = String.format("%s/%s/TempAttachments/%s/", crmId, appRefNo, productCode);
            sftpStoreDocuments(srcFile, dir);
        }  catch (IOException e) {
            throw new TMBCommonException(ResponseCode.JASPER_IO_ERROR.getCode(),
                    ResponseCode.JASPER_IO_ERROR.getMessage(),
                    ResponseCode.JASPER_IO_ERROR.getService(),
                    HttpStatus.INTERNAL_SERVER_ERROR, e);
        }
    }
    private Map<String, Object> buildCreditCardParameters(EAppResponse eAppResponse) {
        Map<String, Object> parameters = buildCommonParameters(eAppResponse, "Y");
        parameters.put("payment_criteria", eAppResponse.getPaymentCriteria());

        return parameters;
    }

    private Map<String, Object> buildFlashCardParameters(EAppResponse eAppResponse) {
        Map<String, Object> parameters = buildCommonParameters(eAppResponse, "Y");
        parameters.put("request_amount", CommonServiceUtils.format2DigitDecimalPoint(eAppResponse.getRequestAmount()));
        parameters.put("tenure", eAppResponse.getTenure());
        parameters.put("payment_plan", eAppResponse.getPaymentPlan());
        parameters.put("payment_criteria", eAppResponse.getPaymentCriteria());
        parameters.put("loan_with_other_bank", eAppResponse.getLoanWithOtherBank());
        parameters.put("consider_loan_with_other_bank", eAppResponse.getConsiderLoanWithOtherBank());
        parameters.put("is_loan_day_one", "Y"); //Need flag for checking

        return parameters;
    }

    private Map<String, Object> buildC2GCardParameters(EAppResponse eAppResponse) {
        Map<String, Object> parameters = buildCommonParameters(eAppResponse, "Y");
        parameters.put("request_amount", CommonServiceUtils.format2DigitDecimalPoint(eAppResponse.getRequestAmount()));
        parameters.put("monthly_installment", CommonServiceUtils.format2DigitDecimalPoint(eAppResponse.getMonthlyInstallment()));
        parameters.put("tenure", eAppResponse.getTenure());
        parameters.put("interest", String.format("%s%%", eAppResponse.getInterest()));
        parameters.put("loan_with_other_bank", eAppResponse.getLoanWithOtherBank());
        parameters.put("consider_loan_with_other_bank", eAppResponse.getConsiderLoanWithOtherBank());

        return parameters;
    }

    private Map<String, Object> buildCommonParameters(EAppResponse eAppResponse, String isDirectDebit) {
        Map<String, Object> parameters = new HashMap<>();
        //Loan Detail Section
        parameters.put("app_no", eAppResponse.getAppNo());
        parameters.put("product_name", eAppResponse.getProductNameTh());
        parameters.put("product_type", eAppResponse.getProductType());
        parameters.put("employment_status", eAppResponse.getEmploymentStatus());
        parameters.put("salary", CommonServiceUtils.format2DigitDecimalPoint(eAppResponse.getSalary()));
        parameters.put("other_income", CommonServiceUtils.format2DigitDecimalPoint(eAppResponse.getOtherIncome()));
        parameters.put("apox_income", CommonServiceUtils.format2DigitDecimalPoint(eAppResponse.getSalary().add(eAppResponse.getOtherIncome())));
        //Loan Payment Detail Section
        parameters.put("payment_method", eAppResponse.getPaymentMethod());
        parameters.put("payment_account_name", eAppResponse.getPaymentAccountName());
        parameters.put("payment_account_no", CommonServiceUtils.formatBankAccountNo(eAppResponse.getPaymentAccountNo()));
        parameters.put("is_direct_debit", isDirectDebit);
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
        parameters.put("cash_flow", CommonServiceUtils.format2DigitDecimalPoint(eAppResponse.getCashFlow()));
        parameters.put("share_percent", eAppResponse.getSharePercent());
        parameters.put("company_source_of_income", "ประเทศไทย"); //Need field
        parameters.put("estatement", eAppResponse.getEStatement());
        parameters.put("delivery", eAppResponse.getDelivery());
        parameters.put("ncb_model_accept", eAppResponse.getNcbModelAccept());
        //Consent Section
        parameters.put("accept_by", eAppResponse.getAcceptBy());
        parameters.put("consent_date", convertToThaiDate(eAppResponse.getAcceptDate()));
        parameters.put("consent_time", CommonServiceUtils.getTimeInHHMMSS(eAppResponse.getAcceptDate().getTime()));
        return parameters;
    }

    private Body getApplicationInfo(long caId) throws ServiceException, TMBCommonException, JsonProcessingException {
        ResponseApplication response = loanSubmissionGetApplicationInfoClient.searchApplicationInfoByCaID(caId);
        RslServiceUtils.checkRslResponse(response.getHeader().getResponseCode(), response.getHeader().getResponseDescriptionEN());
        return response.getBody();
    }

    private String convertToThaiDate(Calendar calendar) {
        Date date = calendar.getTime();
        String dateEng = CommonServiceUtils.getDateInYYYYMMDD(date);
        return CommonServiceUtils.getThaiDate(dateEng);
    }

    private void sftpStoreDocuments(String srcFile, String dir) throws TMBCommonException {
        try {
            List<SFTPStoreFileInfo> sftpStoreFiles = new ArrayList<>();

            SFTPStoreFileInfo sftpStoreFile = new SFTPStoreFileInfo();
            sftpStoreFile.setRootPath(sftpLocations);
            sftpStoreFile.setDstDir(dir);
            sftpStoreFile.setSrcFile(srcFile);
            sftpStoreFiles.add(sftpStoreFile);

            sftpClientImp.storeFile(sftpStoreFiles);
        } catch (Exception e) {
            throw new TMBCommonException(ResponseCode.SFTP_FAILED.getCode(), "SFTP file : " + srcFile + " fail.", ResponseCode.SFTP_FAILED.getService(), HttpStatus.INTERNAL_SERVER_ERROR, null);
        }
    }

    private String generateFileFromOutputStream(String fileName, ByteArrayOutputStream outputStream) throws IOException {
        String baseDir = System.getProperty("user.dir");
        File outputDir = new File(baseDir + File.separator + "epp");
        outputDir.mkdir();
        String filePath = outputDir + SEPARATOR + fileName;

        try (FileOutputStream fos = new FileOutputStream(filePath)) {
            outputStream.writeTo(fos);
        }

        return filePath;
    }

}
