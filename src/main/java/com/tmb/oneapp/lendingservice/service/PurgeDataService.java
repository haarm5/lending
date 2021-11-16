package com.tmb.oneapp.lendingservice.service;

import com.tmb.common.logger.TMBLogger;
import com.tmb.oneapp.lendingservice.client.SFTPClientImp;
import com.tmb.oneapp.lendingservice.client.SFTPEnotiClientImp;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

/**
 * Provides lending information services.
 */

@Service
@Setter
public class PurgeDataService {

    private static final TMBLogger<PurgeDataService> logger = new TMBLogger<>(PurgeDataService.class);
    private final SFTPClientImp sftpClient;
    private final SFTPEnotiClientImp sftpEnotiClient;
    @Value("${sftp.purge.data.after.day}")
    private String purgeAfterDay;
    @Value("${sftp.locations.loan.submission.consent-images}")
    private String pathLOC;
    @Value("${sftp.locations.loan.documents}")
    private String pathDocuments;

    public PurgeDataService(SFTPClientImp sftpClient, SFTPEnotiClientImp sftpEnotiClient) {
        this.sftpClient = sftpClient;
        this.sftpEnotiClient = sftpEnotiClient;
    }

    public boolean purgeData() {
        logger.info("Starting... purge data :{} and :{} ", pathLOC, purgeAfterDay);
        return sftpClient.purgeFileOlderThanNDays(pathLOC, Long.parseLong(purgeAfterDay))
                && sftpClient.purgeFileOlderThanNDays(pathDocuments, Long.parseLong(purgeAfterDay))
                && sftpEnotiClient.purgeFileOlderThanNDays(pathLOC, Long.parseLong(purgeAfterDay))
                && sftpEnotiClient.purgeFileOlderThanNDays(pathDocuments, Long.parseLong(purgeAfterDay));
    }

}
