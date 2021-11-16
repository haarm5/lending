package com.tmb.oneapp.lendingservice.service;

import com.tmb.common.logger.TMBLogger;
import com.tmb.common.model.legacy.rsl.ws.application.response.ResponseApplication;
import com.tmb.oneapp.lendingservice.client.SFTPClientImp;
import com.tmb.oneapp.lendingservice.client.SFTPEnotiClientImp;
import com.tmb.oneapp.lendingservice.model.SFTPStoreFileInfo;
import com.tmb.oneapp.lendingservice.model.instantloancreation.LOCRequest;
import com.tmb.oneapp.lendingservice.model.loanonline.CustomerInformationResponse;
import com.tmb.oneapp.lendingservice.util.CommonServiceUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import static com.tmb.oneapp.lendingservice.constant.LendingServiceConstant.SEPARATOR;

@Service
public class LoanOnlineSubmissionGenNCBFileService {
    private static final TMBLogger<LoanOnlineSubmissionGenNCBFileService> logger = new TMBLogger<>(
            LoanOnlineSubmissionGenNCBFileService.class);
    private final ImageGeneratorService imageGeneratorService;
    private final SFTPClientImp sftpClient;
    private final SFTPEnotiClientImp sftpEnotiClient;

    @Value("${sftp.locations.loan.submission.consent-images}")
    private String sftpLocations;

    @Value("${sftp.e-noti.locations.loan.submission.consent-images}")
    private String sftpEnotiLocations;

    private final ExecutorService executor = Executors.newSingleThreadExecutor();

    public LoanOnlineSubmissionGenNCBFileService(ImageGeneratorService imageGeneratorService, SFTPClientImp sftpClient, SFTPEnotiClientImp sftpEnotiClient) {
        this.imageGeneratorService = imageGeneratorService;
        this.sftpClient = sftpClient;
        this.sftpEnotiClient = sftpEnotiClient;
    }

    public void setSftpLocations(String sftpLocations) {
        this.sftpLocations = sftpLocations;
    }

    public void storeNCBfile(ResponseApplication responseApplication, CustomerInformationResponse custInfo) {
        try {
            LOCRequest locRequest = new LOCRequest();
            locRequest.setNCBMobileNo(custInfo.getMobileNo());
            locRequest.setNCBDateofbirth(custInfo.getBirthDate());
            locRequest.setNcbid(custInfo.getCitizenIdOrPassportNo());
            locRequest.setNCBCustName(custInfo.getFullName());
            locRequest.setCrmId(custInfo.getCrmId());
            locRequest.setNCBReferenceID(custInfo.getMemberRef());
            locRequest.setNCBDateTime(custInfo.getNcbConsentDate());
            locRequest.setProductName(custInfo.getProductName());
            locRequest.setAppRefNo(responseApplication.getBody().getAppRefNo());
            locRequest.setCreateDate(responseApplication.getBody().getApplicationDate());
            LOCRequest locRequest2 = new LOCRequest(locRequest);
            executor.execute(() -> constructRequestForLOCCompleteImage(locRequest2));

        } catch (Exception e) {
            logger.error("Exception {} : ", e);
        }
    }

    private void constructRequestForLOCCompleteImage(LOCRequest locRequest) {
        logger.info("constructRequestForLOCCompleteImage Start");
        locRequest.setConsentbyCustomer("Access PIN");

        String mobno = locRequest.getNCBMobileNo();
        locRequest.setNCBMobileNo(CommonServiceUtils.formatPhoneNumber(mobno));

        String customerId = locRequest.getNcbid();
        locRequest.setNcbid(CommonServiceUtils.formatCustomerId(customerId));

        try {
            String jpgFile = imageGeneratorService.generateLOCImage(locRequest);
            String directoryPath = locRequest.getCrmId() + SEPARATOR + locRequest.getAppRefNo();

            String[] locations = sftpLocations.split(",");
            List<SFTPStoreFileInfo> sftpStoreFiles = sftpClient.setSFTPStoreFileInfo(locations, jpgFile, directoryPath);
            sftpClient.removeFile(sftpStoreFiles);
            sftpClient.storeFile(sftpStoreFiles);

            String[] enotiLocations = sftpEnotiLocations.split(",");
            List<SFTPStoreFileInfo> sftpEnotiStoreFiles = sftpEnotiClient.setSFTPStoreFileInfo(enotiLocations, jpgFile, directoryPath);
            sftpEnotiClient.removeFile(sftpEnotiStoreFiles);
            sftpEnotiClient.storeFile(sftpEnotiStoreFiles);

            Files.delete(Paths.get(jpgFile));
        } catch (IOException e) {
            logger.error("constructRequestForLOCCompleteImage got error:{}", e);
        }

        logger.info("constructRequestForLOCCompleteImage END");
    }

}
