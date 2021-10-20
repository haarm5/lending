package com.tmb.oneapp.lendingservice.service;

import com.tmb.common.logger.TMBLogger;
import com.tmb.common.model.legacy.rsl.ws.application.response.ResponseApplication;
import com.tmb.oneapp.lendingservice.client.FTPClient;
import com.tmb.oneapp.lendingservice.model.SFTPStoreFileInfo;
import com.tmb.oneapp.lendingservice.model.instantloancreation.LOCRequest;
import com.tmb.oneapp.lendingservice.model.loanonline.CustomerInformationResponse;
import com.tmb.oneapp.lendingservice.util.CommonServiceUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Service
public class LoanOnlineSubmissionGenNCBFileService {
	private static final TMBLogger<LoanOnlineSubmissionGenNCBFileService> logger = new TMBLogger<>(
			LoanOnlineSubmissionGenNCBFileService.class);
	private final ImageGeneratorService imageGeneratorService;
	private final FTPClient ftpClient;
	private static final String SEPARATOR = "/";

	@Value("${sftp.locations.loan.submission.consent-images}")
	private String sftpLocations;

	private ExecutorService executor = Executors.newSingleThreadExecutor();

	public LoanOnlineSubmissionGenNCBFileService(ImageGeneratorService imageGeneratorService, FTPClient ftpClient) {
		this.imageGeneratorService = imageGeneratorService;
		this.ftpClient = ftpClient;
	}

	public void setSftpLocations(String sftpLocations) {
		this.sftpLocations = sftpLocations;
	}

	/**
	 * method to generate NCB file and store to sFTP
	 * 
	 * @param crmID
	 * @param caID
	 */
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
		List<SFTPStoreFileInfo> sftpStoreFiles = new ArrayList<>();
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
			SFTPStoreFileInfo sftpStoreFile;
			for (int i = 0; i < locations.length; i++) {
				sftpStoreFile = new SFTPStoreFileInfo();
				sftpStoreFile.setSrcFile(jpgFile);
				sftpStoreFile.setRootPath(locations[i]);
				if (i == 0) {
					sftpStoreFile.setDstDir(directoryPath);
				}
				sftpStoreFiles.add(sftpStoreFile);
			}
			ftpClient.removeFile(sftpStoreFiles);
			ftpClient.storeFile(sftpStoreFiles);
			Files.delete(Paths.get(jpgFile));
		} catch (IOException e) {
			logger.error("constructRequestForLOCCompleteImage got error:{}", e);
		}

		logger.info("constructRequestForLOCCompleteImage END");
	}

}
