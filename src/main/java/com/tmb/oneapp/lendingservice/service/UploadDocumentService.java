package com.tmb.oneapp.lendingservice.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Image;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.pdf.PdfWriter;
import com.tmb.common.exception.model.TMBCommonException;
import com.tmb.common.logger.TMBLogger;
import com.tmb.common.model.legacy.rsl.ws.application.response.Body;
import com.tmb.common.model.legacy.rsl.ws.application.response.ResponseApplication;
import com.tmb.oneapp.lendingservice.client.LoanSubmissionGetApplicationInfoClient;
import com.tmb.oneapp.lendingservice.client.SFTPClientImp;
import com.tmb.oneapp.lendingservice.constant.ResponseCode;
import com.tmb.oneapp.lendingservice.model.CriteriaCodeEntry;
import com.tmb.oneapp.lendingservice.model.SFTPStoreFileInfo;
import com.tmb.oneapp.lendingservice.model.documnet.*;
import com.tmb.oneapp.lendingservice.util.CommonServiceUtils;
import com.tmb.oneapp.lendingservice.util.RslServiceUtils;
import lombok.RequiredArgsConstructor;
import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import javax.xml.rpc.ServiceException;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Base64;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

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

    private final String baseDir = System.getProperty("user.dir");

    public UploadDocumentResponse upload(String crmId, UploadDocumentRequest request) throws TMBCommonException, IOException, ServiceException, ParseException {
        UploadDocumentResponse response = new UploadDocumentResponse();

        String rmId = CommonServiceUtils.getRmId(crmId);

        Body applicationInfo = getApplicationInfo(Long.parseLong(request.getCaId()));
        String appRefNo = applicationInfo.getAppRefNo();
        String dir = String.format("%s/%s/%s", rmId, appRefNo, request.getDocCode());
        String srcDir = String.format("%s/documents/%s", baseDir, dir);
        String fileName = request.getFileName();

        String fileType = request.getFile().split(";")[0];
        if (fileType.equals("data:application/pdf")) {
            String appDate = applicationInfo.getApplicationDate();
            fileName = parsePdfFileName(request.getDocCode(), appRefNo, convertAppDate(appDate));
        } else {
            dir = String.format("%s/TempAttachments", dir);
            srcDir = String.format("%s/TempAttachments", srcDir);
        }

        generateFileFromBase64(srcDir, fileName, request.getFile());
        String srcFile = String.format("%s/%s", srcDir, fileName);
        sftpStoreDocuments(srcFile, dir);

        response.setDocCode(request.getDocCode());
        response.setFileName(request.getFileName());
        return response;
    }

    public SubmitDocumentResponse submit(String crmId, SubmitDocumentRequest request) throws TMBCommonException, IOException, ServiceException, ParseException, DocumentException {
        SubmitDocumentResponse response = new SubmitDocumentResponse();
        String rmId = CommonServiceUtils.getRmId(crmId);

        Body applicationInfo = getApplicationInfo(Long.parseLong(request.getCaId()));
        String appRefNo = applicationInfo.getAppRefNo();
        String appDate = applicationInfo.getApplicationDate();

        for (String docCode : request.getDocCodes()) {
            String dir = String.format("%s/%s/%s", rmId, appRefNo, docCode);
            String srcDir = String.format("%s/documents/%s/TempAttachments", baseDir, dir);
            String outDir = String.format("%s/documents/%s", baseDir, dir);
            String fileName = parsePdfFileName(docCode, appRefNo, convertAppDate(appDate));
            mergeImagesToPdf(srcDir, outDir, fileName);
            removeDirectory(srcDir);
            String srcFile = String.format("%s/%s", srcDir, fileName);
            sftpStoreDocuments(srcFile, dir);
            sftpRemoveDocuments(srcFile);
        }

        String tempDoc = String.format("%s/documents/%s", baseDir, rmId);
        removeDirectory(tempDoc);

        response.setAppRefNo(appRefNo);
        response.setAppType(applicationInfo.getAppType());
        response.setProductDescTh(applicationInfo.getProductDescTH());
        response.setNcbConsentFlag(applicationInfo.getNcbConsentFlag());

        return response;
    }

    public DeleteDocumentResponse delete(String crmId, String caId, String docCode, String fileType, String fileName) throws ServiceException, TMBCommonException, JsonProcessingException {
        DeleteDocumentResponse response = new DeleteDocumentResponse();

        String rmId = CommonServiceUtils.getRmId(crmId);

        Body applicationInfo = getApplicationInfo(Long.parseLong(caId));
        String appRefNo = applicationInfo.getAppRefNo();

        String filePath = String.format("%s/documents/%s/%s/%s/TempAttachments/%s.%s", baseDir, rmId, appRefNo, docCode, fileName, fileType);
        if ("pdf".equals(fileType)) {
            filePath = String.format("%s/documents/%s/%s/%s/%s.%s", baseDir, rmId, appRefNo, docCode, fileName, fileType);
        }

        removeFile(filePath);
        logger.info("Remove file : {}", filePath);
        sftpRemoveDocuments(filePath);

        response.setDocCode(docCode);
        response.setFileName(fileName);
        return response;
    }

    private Body getApplicationInfo(long caId) throws ServiceException, TMBCommonException, JsonProcessingException {
        ResponseApplication response = loanSubmissionGetApplicationInfoClient.searchApplicationInfoByCaID(caId);
        RslServiceUtils.checkRslResponse(response.getHeader().getResponseCode(), response.getHeader().getResponseDescriptionEN());
        return response.getBody();
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

    private void sftpRemoveDocuments(String filePath) throws TMBCommonException {
        try {
            List<SFTPStoreFileInfo> sftpStoreFiles = new ArrayList<>();

            SFTPStoreFileInfo sftpStoreFile = new SFTPStoreFileInfo();
            sftpStoreFile.setRootPath(sftpLocations);
            sftpStoreFile.setDstDir(filePath);
            sftpStoreFiles.add(sftpStoreFile);

            sftpClientImp.removeFile(sftpStoreFiles);
        } catch (Exception e) {
            throw new TMBCommonException(ResponseCode.SFTP_FAILED.getCode(), "SFTP remove file : " + filePath + " fail.", ResponseCode.SFTP_FAILED.getService(), HttpStatus.INTERNAL_SERVER_ERROR, null);
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

    private String convertAppDate(String appDate) throws ParseException {
        Date date = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss").parse(appDate);
        return CommonServiceUtils.getDateAndTimeInYYMMDDHHMMSS(date);
    }

    public void generateFileFromBase64(String dir, String fileName, String base64) throws IOException {
        String base64String = base64.split(",")[1];
        byte[] decoder = Base64.getDecoder().decode(base64String);
        File outputDir = new File(dir);
        mkdirs(outputDir);

        String filePath = outputDir + SEPARATOR + fileName;

        try (FileOutputStream fos = new FileOutputStream(filePath)) {
            fos.write(decoder);
        }
        logger.info("Generate file from base64 successes: {}", filePath);
    }

    public void mergeImagesToPdf(String srcDir, String outDir, String fileName) throws IOException, DocumentException {
        File root = new File(srcDir);
        List<Path> files = listFiles(root.toPath());

        Document document = new Document();
        File outputDir = new File(outDir);
        mkdirs(outputDir);

        String filePath = outDir + SEPARATOR + fileName;
        File out = new File(filePath);
        PdfWriter.getInstance(document, new FileOutputStream(out));
        document.open();
        for (Path f : files) {
            document.newPage();
            Image image = Image.getInstance(f.toAbsolutePath().toString());
            image.setAbsolutePosition(0, 0);
            image.scaleAbsolute(PageSize.A4);
            document.add(image);
        }
        document.close();
        logger.info("Merge images to pdf successes: {}", filePath);

    }

    public void removeFile(String filePath) throws TMBCommonException {
        File outputDir = new File(filePath);
        if (outputDir.exists()) {
            if (outputDir.delete()) {
                throw new TMBCommonException(ResponseCode.GENERAL_ERROR.getCode(), "Remove file fail: " + filePath, ResponseCode.GENERAL_ERROR.getService(), HttpStatus.INTERNAL_SERVER_ERROR, null);
            }
        }
        logger.info("Remove file successes: {}", filePath);
    }

    public void removeDirectory(String dirPath) throws IOException {
        File dir = new File(dirPath);
        FileUtils.deleteDirectory(dir);
        logger.info("Remove directory successes: {}", dirPath);
    }

    public List<Path> listFiles(Path path) throws IOException {
        List<Path> result;
        try (Stream<Path> walk = Files.walk(path)) {
            result = walk.filter(Files::isRegularFile)
                    .collect(Collectors.toList());
        }
        return result;

    }

    public void mkdirs(File outputDir) {
        if (Files.notExists(outputDir.toPath())) {
            outputDir.setReadable(true, false);
            outputDir.setWritable(true, false);
            outputDir.mkdirs();
        }
    }

}
