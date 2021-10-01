package com.tmb.oneapp.lendingservice.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.tmb.common.logger.TMBLogger;
import com.tmb.oneapp.lendingservice.client.FTPClient;

import lombok.Setter;

/**
 * Provides lending information services.
 */

@Service
@Setter
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
		logger.info("Starting... purge data :{} and :{} ", pathLOC, purgeAfterDay);
		return ftpClient.purgeFileOlderThanNDays(pathLOC, Long.parseLong(purgeAfterDay))
				&& ftpClient.purgeFileOlderThanNDays(pathDocuments, Long.parseLong(purgeAfterDay));
	}

}
