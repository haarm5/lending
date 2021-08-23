package com.tmb.oneapp.lendingservice.service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.tmb.common.logger.TMBLogger;
import com.tmb.oneapp.lendingservice.client.FTPClient;
import com.tmb.oneapp.lendingservice.model.SFTPStoreFileInfo;
import com.tmb.oneapp.lendingservice.model.instantloancreation.LOCRequest;
import com.tmb.oneapp.lendingservice.model.loanonline.CustomerInformationResponse;
import com.tmb.oneapp.lendingservice.util.CommonServiceUtils;

@Service
public class LoanSubmissionGenNCBFileService {
	private static final TMBLogger<LoanSubmissionGenNCBFileService> logger = new TMBLogger<>(
			LoanSubmissionGenNCBFileService.class);
	private final ImageGeneratorService imageGeneratorService;
	private final FTPClient ftpClient;
	private static final String SEPARATOR = "/";

	@Value("${sftp.locations.loan.submission.consent-images}")
	private String sftpLocations;

	private ExecutorService executor = Executors.newSingleThreadExecutor();

	public LoanSubmissionGenNCBFileService(ImageGeneratorService imageGeneratorService, FTPClient ftpClient) {
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
	public void storeNCBfile(CustomerInformationResponse custInfo) throws JsonProcessingException, ParseException {
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
			locRequest.setAppRefNo(custInfo.getAppRefNo());
			String createDateStr = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'").format(new Date());
			locRequest.setCreateDate(createDateStr);
			LOCRequest locRequest2 = new LOCRequest(locRequest);
			executor.execute(() -> constructRequestForLOCCompleteImage(locRequest2));

		} catch (Exception e) {
			logger.error("Exception {} : ", e);
		}
	}

	private void constructRequestForLOCCompleteImage(LOCRequest locRequest2) {
		logger.info("constructRequestForLOCCompleteImage Start");
		locRequest2.setConsentbyCustomer("Access PIN");

		String mobno = locRequest2.getNCBMobileNo();
		locRequest2.setNCBMobileNo(CommonServiceUtils.formatPhoneNumber(mobno));

		String customerId = locRequest2.getNcbid();
		locRequest2.setNcbid(CommonServiceUtils.formatCustomerId(customerId));

		try {
			String jpgFile = imageGeneratorService.generateLOCImage(locRequest2);
			String directoryPath = locRequest2.getCrmId() + SEPARATOR + locRequest2.getAppRefNo();
			List<SFTPStoreFileInfo> sftpStoreFileInfoList = new ArrayList<>();
			String[] locationTokens = sftpLocations.split(",");
			SFTPStoreFileInfo sftpStoreFileInfo;
			for (int i = 0; i < locationTokens.length; i++) {
				sftpStoreFileInfo = new SFTPStoreFileInfo();
				sftpStoreFileInfo.setSrcFile(jpgFile);
				sftpStoreFileInfo.setRootPath(locationTokens[i]);
				if (i == 0)
					sftpStoreFileInfo.setDstDir(directoryPath);
				sftpStoreFileInfoList.add(sftpStoreFileInfo);
			}
			ftpClient.removeFile(sftpStoreFileInfoList);
			ftpClient.storeFile(sftpStoreFileInfoList);
			Files.delete(Paths.get(jpgFile));
		} catch (IOException e) {
			logger.error("constructRequestForLOCCompleteImage got error:{}", e);
		}

		logger.info("constructRequestForLOCCompleteImage END");
	}

}
