package com.tmb.oneapp.lendingservice.client;


import com.tmb.common.logger.TMBLogger;
import org.apache.commons.vfs2.FileObject;
import org.apache.commons.vfs2.FileSystemException;
import org.apache.commons.vfs2.Selectors;
import org.apache.commons.vfs2.impl.StandardFileSystemManager;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

/**
 * Provides method to store file to ftp server by using Apache ftp client.
 */
@Service
public class SFTPClientImp implements FTPClient {

    @Value("${ftp.server.ip}")
    public String remoteHost;
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

    private static final TMBLogger<SFTPClientImp> logger = new TMBLogger<>(SFTPClientImp.class);

    private static final String SEPARATOR = "/";



    private static URI createConnectionString(String hostName, String username, String password, String remoteFilePath) {
        URI sftpURI = null;
        try {
            String userInfo = username + ":" + password;

            sftpURI = new URI("sftp", userInfo, hostName, -1, remoteFilePath, null, null);
        } catch (URISyntaxException e) {
            logger.error("Got error createConnectionString:{}", e);
        }

        return sftpURI;
    }

    /**
     * Store file to ftp server
     *
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
        String[] locations = new String[]{locPrimaryLocation, locSecondaryLocation};
        String fileName = sourceFile.getName();
        logger.info("src file to upload:{}", srcFile);
        try (StandardFileSystemManager manager = new StandardFileSystemManager()) {
            manager.init();
            FileObject local = manager.resolveFile(
                    srcFile);
            logger.info("src file resolved:{}", srcFile);

            for (String location : locations) {
                String remoteFilePath = location + SEPARATOR + targetDir + SEPARATOR + fileName;
                try {
                    FileObject remoteFile = manager.resolveFile(createConnectionString(remoteHost, username, password, remoteFilePath));
                    logger.info("ftp server connected");
                    remoteFile.copyFrom(local, Selectors.SELECT_SELF);
                    logger.info("ftp upload done:{}", remoteFilePath);
                    remoteFile.close();

                } catch (FileSystemException e) {
                    logger.error("Got error when upload file: {}", e);
                    return false;
                }
            }
            local.close();
        }
        return true;
    }
}
