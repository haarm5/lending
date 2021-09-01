package com.tmb.oneapp.lendingservice.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.tmb.common.exception.model.TMBCommonException;
import com.tmb.common.logger.TMBLogger;
import com.tmb.common.model.legacy.rsl.ws.application.response.Body;
import com.tmb.common.model.legacy.rsl.ws.application.response.ResponseApplication;
import com.tmb.oneapp.lendingservice.client.LoanSubmissionGetApplicationInfoClient;
import com.tmb.oneapp.lendingservice.client.SFTPClientImp;
import com.tmb.oneapp.lendingservice.constant.ResponseCode;
import com.tmb.oneapp.lendingservice.model.CriteriaCodeEntry;
import com.tmb.oneapp.lendingservice.model.SFTPStoreFileInfo;
import com.tmb.oneapp.lendingservice.model.documnet.UploadDocumentResponse;
import com.tmb.oneapp.lendingservice.util.CommonServiceUtils;
import com.tmb.oneapp.lendingservice.util.RslServiceUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.xml.rpc.ServiceException;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static com.tmb.oneapp.lendingservice.constant.LendingServiceConstant.SEPARATOR;

@Service
@RequiredArgsConstructor
public class UploadDocumentService {
    private static final TMBLogger<UploadDocumentService> logger = new TMBLogger<>(UploadDocumentService.class);

    private final LoanSubmissionGetApplicationInfoClient loanSubmissionGetApplicationInfoClient;
    private final SFTPClientImp sftpClientImp;
    private final LendingCriteriaInfoService lendingCriteriaInfoService;

    @Value("${sftp.locations.loan.documents}")
    private String sftpLocations;

    public UploadDocumentResponse upload(String crmId, MultipartFile file, long caId, String docCode) throws TMBCommonException, ServiceException, JsonProcessingException {
        UploadDocumentResponse response = new UploadDocumentResponse();
        Body applicationInfo = getApplicationInfo(caId);
        String rmId = CommonServiceUtils.getRmId(crmId);
        String appRefNo = applicationInfo.getAppRefNo();
        String appDate = applicationInfo.getApplicationDate();
        response.setAppRefNo(applicationInfo.getAppRefNo());
        response.setAppType(applicationInfo.getAppType());
        response.setProductDescTh(applicationInfo.getProductDescTH());

        try {
            String fileName = parsePdfFileName(docCode, appRefNo, convertAppDate(appDate));
            response.setPdfFileName(fileName);

            String srcFile = storeFile(fileName, file);
            sftpStoreDocuments(rmId, appRefNo, srcFile);
            response.setStatus("success");

        } catch (Exception e) {
            logger.error("Sftp document code [{}] fail: {}", docCode, e.getCause().getMessage());
            response.setStatus("fail");
        }

        return response;
    }

    private Body getApplicationInfo(long caId) throws ServiceException, TMBCommonException, JsonProcessingException {
        ResponseApplication response = loanSubmissionGetApplicationInfoClient.searchApplicationInfoByCaID(caId);
        RslServiceUtils.checkRslResponse(response.getHeader().getResponseCode(), response.getHeader().getResponseDescriptionEN());
        return response.getBody();
    }

    private void sftpStoreDocuments(String rmId, String appRefNo, String srcFile) throws TMBCommonException {
        try {
            List<SFTPStoreFileInfo> sftpStoreFiles = new ArrayList<>();
            String dir = String.format("%s/%s", rmId, appRefNo);

            SFTPStoreFileInfo sftpStoreFile = new SFTPStoreFileInfo();
            sftpStoreFile.setRootPath(sftpLocations);
            sftpStoreFile.setDstDir(dir);
            sftpStoreFile.setSrcFile(srcFile);
            sftpStoreFiles.add(sftpStoreFile);

            sftpClientImp.storeFile(sftpStoreFiles);
            Files.delete(Paths.get(srcFile));
        } catch (Exception e) {
            throw new TMBCommonException(ResponseCode.SFTP_FAILED.getCode(), "SFTP file : " + srcFile + " fail.", ResponseCode.SFTP_FAILED.getService(), HttpStatus.INTERNAL_SERVER_ERROR, null);
        }
    }

    private String parsePdfFileName(String docCode, String appRefNo, String appDate) throws TMBCommonException {
        List<CriteriaCodeEntry> docTypeList = lendingCriteriaInfoService.getBrmsEcmDocTypeByCode(docCode);
        if (docTypeList.isEmpty()) {
            throw new TMBCommonException(ResponseCode.DATA_NOT_FOUND.getCode(), "Doc code " + docCode + " is not found.", ResponseCode.DATA_NOT_FOUND.getService(), HttpStatus.INTERNAL_SERVER_ERROR, null);
        }
        String docName = docTypeList.get(0).getRefEntryCode();
        String fileName = String.format("01_%s_%s_%s.pdf", appDate, appRefNo, docName);
        logger.info("Document name : {}", fileName);
        return fileName;
    }

    private String storeFile(String fileName, MultipartFile file) throws IOException {
        String baseDir = System.getProperty("user.dir");
        File outputDir = new File(baseDir + File.separator + "documents");
        outputDir.mkdir();
        String filePath = outputDir + SEPARATOR + fileName;

        try (InputStream inputStream = file.getInputStream()) {
            Files.copy(inputStream, Path.of(filePath), StandardCopyOption.REPLACE_EXISTING);
        }

        return filePath;
    }

    private String convertAppDate(String appDate) throws ParseException {
        Date date = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss").parse(appDate);
        return CommonServiceUtils.getDateAndTimeInYYMMDDHHMMSS(date);
    }

}
