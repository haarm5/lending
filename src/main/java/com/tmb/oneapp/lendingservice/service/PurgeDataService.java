package com.tmb.oneapp.lendingservice.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.tmb.common.logger.TMBLogger;
import com.tmb.oneapp.lendingservice.client.FTPClient;

/**
 * Provides lending information services.
 */
@Service
public class PurgeDataService {

	private static final TMBLogger<PurgeDataService> logger = new TMBLogger<>(PurgeDataService.class);
	private final FTPClient ftpClient;
	@Value("${sftp.purge.data.after.day}")
	private String purgeAfterDay;
	@Value("${sftp.locations.loan.submission.consent-images}")
	private String pathLOC;
	@Value("${sftp.locations.loan.documents}")
	private String pathDocuments;

	public PurgeDataService(FTPClient ftpClient) {
		this.ftpClient = ftpClient;
	}

	public boolean purgeData() {
		try {
			logger.info("Starting... purge data :{}", pathLOC);
			ftpClient.purgeFileOlderThanNDays(pathLOC, Long.parseLong(purgeAfterDay));

			logger.info("Starting... purge data :{}", pathDocuments);
			ftpClient.purgeFileOlderThanNDays(pathDocuments, Long.parseLong(purgeAfterDay));

			logger.info("Purge data success.");
			return true;
		} catch (Exception e) {
			logger.error("Purge data failed.");
			logger.error("Exception {} : ", e);
			return false;
		}
	}

}
