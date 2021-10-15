package com.tmb.oneapp.lendingservice.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Image;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.pdf.PdfWriter;
import com.tmb.common.exception.model.TMBCommonException;
import com.tmb.common.logger.TMBLogger;
import com.tmb.common.model.legacy.rsl.common.ob.doc.application.DocApplication;
import com.tmb.common.model.legacy.rsl.ws.application.response.Body;
import com.tmb.common.model.legacy.rsl.ws.application.response.ResponseApplication;
import com.tmb.oneapp.lendingservice.client.SFTPClientImp;
import com.tmb.oneapp.lendingservice.constant.ResponseCode;
import com.tmb.oneapp.lendingservice.model.CriteriaCodeEntry;
import com.tmb.oneapp.lendingservice.model.SFTPStoreFileInfo;
import com.tmb.oneapp.lendingservice.model.documnet.*;
import com.tmb.oneapp.lendingservice.model.rsl.LoanSubmissionGetApplicationInfoRequest;
import com.tmb.oneapp.lendingservice.util.CommonServiceUtils;
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
import java.nio.file.Paths;
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

    private final RslService rslService;
    private final SFTPClientImp sftpClientImp;
    private final LendingCriteriaInfoService lendingCriteriaInfoService;

    @Value("${sftp.locations.loan.documents}")
    private String sftpLocations;

    private final String baseDir = System.getProperty("user.dir");

    public UploadDocumentResponse upload(String crmId, UploadDocumentRequest request) throws TMBCommonException, IOException, ServiceException {
        UploadDocumentResponse response = new UploadDocumentResponse();

        String rmId = CommonServiceUtils.getRmId(crmId);

        Body applicationInfo = getApplicationInfo(request.getCaId());
        String appRefNo = applicationInfo.getAppRefNo();
        String srcDir = String.format("%s/documents/%s/%s/%s", baseDir, rmId, appRefNo, request.getDocCode());
        String fileName = request.getFileName();

        String fileType = request.getFile().split(";")[0];
        if (fileType.equals("data:application/pdf")) {
            fileName = parsePdfFileName(request.getDocCode(), appRefNo, CommonServiceUtils.getDateAndTimeInYYMMDDHHMMSS(new Date()));
        } else {
            srcDir = String.format("%s/TempAttachments", srcDir);
        }

        generateFileFromBase64(srcDir, fileName, request.getFile());

        response.setDocCode(request.getDocCode());
        response.setFileName(fileName);
        return response;
    }

    public SubmitDocumentResponse submit(String crmId, SubmitDocumentRequest request) throws TMBCommonException, IOException, ServiceException, DocumentException {
        SubmitDocumentResponse response = new SubmitDocumentResponse();
        String rmId = CommonServiceUtils.getRmId(crmId);

        Body applicationInfo = getApplicationInfo(request.getCaId());
        String appRefNo = applicationInfo.getAppRefNo();

        for (String docCode : request.getDocCodes()) {
            String sftpDir = String.format("%s/%s", rmId, appRefNo);
            String srcDir = String.format("%s/documents/%s/%s/%s/TempAttachments", baseDir, rmId, appRefNo, docCode);
            String outDir = String.format("%s/documents/%s/%s/%s", baseDir, rmId, appRefNo, docCode);
            String fileName = parsePdfFileName(docCode, appRefNo, CommonServiceUtils.getDateAndTimeInYYMMDDHHMMSS(new Date()));
            mergeImagesToPdf(srcDir, outDir, fileName);
            for(Path path: listFiles(Paths.get(outDir))) {
                sftpStoreDocuments(path.toFile().getAbsolutePath(), sftpDir);
            }
        }

        String tempDoc = String.format("%s/documents/%s/%s", baseDir, rmId, appRefNo);
        removeDirectory(tempDoc);

        updateDocApplication(Long.parseLong(request.getCaId()));

        response.setAppRefNo(appRefNo);
        response.setAppType(applicationInfo.getAppType());
        response.setProductDescTh(applicationInfo.getProductDescTH());
        response.setNcbConsentFlag(applicationInfo.getNcbConsentFlag());

        return response;
    }

    public SubmitDocumentResponse submitMore(String crmId, SubmitDocumentRequest request) throws TMBCommonException, IOException, ServiceException, DocumentException {
        SubmitDocumentResponse response = submit(crmId, request);
        updateDocApplication(Long.parseLong(request.getCaId()));
        return response;
    }

    public DeleteDocumentResponse delete(String crmId, String caId, String docCode, String fileType, String fileName) throws ServiceException, TMBCommonException, IOException {
        DeleteDocumentResponse response = new DeleteDocumentResponse();

        String rmId = CommonServiceUtils.getRmId(crmId);

        Body applicationInfo = getApplicationInfo(caId);
        String appRefNo = applicationInfo.getAppRefNo();

        String filePath = String.format("%s/%s/%s/TempAttachments/%s.%s", rmId, appRefNo, docCode, fileName, fileType);
        if ("pdf".equals(fileType)) {
            filePath = String.format("%s/%s/%s/%s.%s", rmId, appRefNo, docCode, fileName, fileType);
        }

        String srcPath = String.format("%s/documents/%s", baseDir, filePath);
        removeFile(srcPath);

        response.setDocCode(docCode);
        response.setFileName(fileName);
        return response;
    }

    public Body getApplicationInfo(String caId) throws ServiceException, TMBCommonException, JsonProcessingException {
        LoanSubmissionGetApplicationInfoRequest request = new LoanSubmissionGetApplicationInfoRequest();
        request.setCaId(caId);
        ResponseApplication response = rslService.getLoanSubmissionApplicationInfo(request);
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
            logger.info("Sftp store file successes: {}", dir);
        } catch (Exception e) {
            throw new TMBCommonException(ResponseCode.SFTP_FAILED.getCode(), "SFTP file : " + srcFile + " fail.", ResponseCode.SFTP_FAILED.getService(), HttpStatus.INTERNAL_SERVER_ERROR, null);
        }
    }

    public void sftpClearDocuments(String dir) throws TMBCommonException {
        try {
            sftpClientImp.removeFolder(dir);
            logger.info("Sftp remove file successes: {}", dir);
        } catch (Exception e) {
            throw new TMBCommonException(ResponseCode.SFTP_FAILED.getCode(), "SFTP file : " + dir + " fail.", ResponseCode.SFTP_FAILED.getService(), HttpStatus.INTERNAL_SERVER_ERROR, null);
        }
    }

    private String parsePdfFileName(String docCode, String appRefNo, String date) throws TMBCommonException {
        List<CriteriaCodeEntry> docTypeList = lendingCriteriaInfoService.getBrmsEcmDocTypeByCode(docCode);
        if (docTypeList.isEmpty()) {
            throw new TMBCommonException(ResponseCode.DATA_NOT_FOUND.getCode(), "Doc code " + docCode + " is not found.", ResponseCode.DATA_NOT_FOUND.getService(), HttpStatus.INTERNAL_SERVER_ERROR, null);
        }
        String docName = docTypeList.get(0).getRefEntryCode();
        String fileName = String.format("01_%s_%s_%s.pdf", date, appRefNo, docName);
        logger.info("Document name : {}", fileName);
        return fileName;
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
        if (root.exists()) {
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
        removeDirectory(srcDir);
    }

    public void removeFile(String filePath) throws IOException {
        Files.delete(Paths.get(filePath));
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

    public void mkdirs(File outputDir) throws IOException {
        if (Files.notExists(outputDir.toPath())) {
            FileUtils.forceMkdir(outputDir);
        }
    }

    private void updateDocApplication(long caId) throws TMBCommonException, ServiceException, JsonProcessingException {
        DocApplication request = new DocApplication();
        request.setCaId(caId);
        request.setUpdateFlag("Y");
        rslService.updateIncompleteDocApplication(request);
    }

}
