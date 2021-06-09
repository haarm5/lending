package com.tmb.oneapp.lendingservice.client;


import com.tmb.common.logger.TMBLogger;
import org.apache.commons.net.ftp.FTP;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

/**
 * Provides method to store file to ftp server by using Apache ftp client.
 */
@Service
public class FTPClientImp implements FTPClient {

    @Value("${ftp.server.ip}")
    public String serverIP;
    @Value("${ftp.server.port}")
    public int port;
    @Value("${ftp.server.username}")
    public String username;
    @Value("${ftp.server.password}")
    public String password;

    @Value("${ftp.loc.file.primary.location}")
    public String locPrimaryLocation;

    @Value("${ftp.loc.file.secondary.location}")
    public String locSecondaryLocation;

    private static final TMBLogger<FTPClientImp> logger = new TMBLogger<>(FTPClientImp.class);

    private boolean makeDirectory(org.apache.commons.net.ftp.FTPClient ftpClient, String pathname) throws IOException {
        logger.info("target directory: {}", pathname);
        String[] pathElements = pathname.split("/");
        if (pathElements.length == 0) return false;
        for (String singleDir : pathElements) {
            boolean existed = ftpClient.changeWorkingDirectory(singleDir);
            if (!existed) {
                boolean created = ftpClient.makeDirectory(singleDir);
                if (created) {
                    logger.info("sub target directory {} is created", singleDir);
                    ftpClient.changeWorkingDirectory(singleDir);
                } else {
                    return false;
                }
            }
        }
        return true;
    }

    /**
     * Store file to ftp server
     * @param targetDir
     * @param srcFile
     * @return
     * @throws IOException
     */
    @Override
    public boolean storeFile(String targetDir, String srcFile) throws IOException {
        File sourceFile = new File(srcFile);
        if (!sourceFile.exists()) {
            logger.error("src file to upload to ftp does not exists:{}", srcFile);
            return false;
        }
        org.apache.commons.net.ftp.FTPClient ftpClient = new org.apache.commons.net.ftp.FTPClient();
        ftpClient.connect(serverIP, port);
        logger.info("ftp server is connected");
        boolean loggedIn = ftpClient.login(username, password);
        if (!loggedIn) {
            logger.error("cannot log in to ftp server");
            return false;
        }
        logger.info("logged in to ftp server");
        String fileName = sourceFile.getName();
        String[] ftpLocations = new String[]{locPrimaryLocation, locSecondaryLocation};
        ftpClient.enterLocalPassiveMode();
        ftpClient.setFileType(FTP.BINARY_FILE_TYPE);
        try {
            for (String ftpLocation : ftpLocations) {
                boolean enterMainDirectory = ftpClient.changeWorkingDirectory(ftpLocation);
                logger.info("enter main directory {}: {}", ftpLocation, enterMainDirectory);
                makeDirectory(ftpClient, targetDir);
                String fullTarget = ftpLocation + "/" + targetDir;
                boolean enteredFullTarget = ftpClient.changeWorkingDirectory(fullTarget);
                logger.info("enter full target directory {}: {}", fullTarget, enteredFullTarget);
                try (BufferedInputStream buffIn = new BufferedInputStream(new FileInputStream(sourceFile))) {
                    String remote = fullTarget + "/" + fileName;
                    ftpClient.storeFile(remote, buffIn);
                    logger.info("stored file to ftp success: {}", remote);
                }
            }
        } finally {
            ftpClient.logout();
            ftpClient.disconnect();
        }

        return true;
    }
}
