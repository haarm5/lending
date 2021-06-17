package com.tmb.oneapp.lendingservice.client;


import com.jcraft.jsch.*;
import com.tmb.common.logger.TMBLogger;
import com.tmb.oneapp.lendingservice.model.SFTPStoreFileInfo;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;

/**
 * Provides method to store file to ftp server by using Apache ftp client.
 */
@Service
public class SFTPClientImp implements FTPClient {

    @Value("${ftp.remote-host}")
    public String remoteHost;
    @Value("${ftp.port}")
    public int port;
    @Value("${ftp.username}")
    public String username;
    @Value("${ftp.password}")
    public String password;

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

    private boolean isDirExist(ChannelSftp channel, String path) {
        try {
            channel.cd(path);
            return true;
        } catch (SftpException e) {
            logger.info("sftp directory: {} does not exist", path);
        }
        return false;
    }


    public boolean mkdir(ChannelSftp channel, String rootPath, String destDirs) {
        String currentPath = rootPath;
        try {
            String[] dirs = destDirs.split(SEPARATOR);
            String targetDir;
            for (String dir : dirs) {
                targetDir = currentPath + SEPARATOR + dir;
                if (!isDirExist(channel, targetDir)) {
                    channel.cd(currentPath);
                    channel.mkdir(dir);
                    logger.info("sftp directory: {} created", targetDir);
                }
                currentPath = targetDir;
            }
            return true;

        } catch (SftpException e) {
            logger.error("sftp exception:{}", e);
        }
        return false;
    }

    public Channel setupJsch() throws JSchException {
        JSch jsch = new JSch();
        Session jschSession = jsch.getSession(username, remoteHost);
        jschSession.setPassword(password);
        java.util.Properties config = new java.util.Properties();
        config.put("StrictHostKeyChecking", "no");
        jschSession.setConfig(config);
        jschSession.setTimeout(60000);
        jschSession.connect();
        return jschSession.openChannel("sftp");
    }

    /**
     * Store file to ftp server
     *
     * @param destDirs
     * @param srcFile
     * @return
     * @throws IOException
     */

    public boolean storeFile(String srcFile, String[] rootPaths,String[] destDirs) throws IOException {

        File sourceFile = new File(srcFile);
        if (!sourceFile.exists()) {
            logger.error("src file to upload to ftp does not exists: {}", srcFile);
            return false;
        }
        ChannelSftp channelSftp = null;
        String dst;
        try {
            channelSftp = (ChannelSftp) setupJsch();
            channelSftp.connect();
            for(String rootPath:rootPaths) {
                for (String desDir : destDirs) {
                    mkdir(channelSftp, rootPath, desDir);
                    dst = rootPath + SEPARATOR + desDir + SEPARATOR + sourceFile.getName();
                    channelSftp.put(srcFile, dst);
                    logger.info("sftp stored success:{}", dst);

                }
            }
            channelSftp.exit();
            return true;
        } catch (JSchException e) {
            logger.error("error sftp connection:{}", e);
        } catch (SftpException e) {
            logger.error("error sftp operation:{}", e);
        }
        return false;
    }

    @Override
    public boolean storeFile(List<SFTPStoreFileInfo> storeFileInfoList) throws IOException {
        ChannelSftp channelSftp = null;
        String dst;
        try {
            channelSftp = (ChannelSftp) setupJsch();
            channelSftp.connect();
            for(SFTPStoreFileInfo sftpStoreFileInfo : storeFileInfoList ){
                File sourceFile = new File(sftpStoreFileInfo.getSrcFile());
                if (!sourceFile.exists()) {
                    logger.error("src file to upload to ftp does not exists: {}", sftpStoreFileInfo.getSrcFile());
                    return false;
                }
                String dstDir = sftpStoreFileInfo.getDstDir();
                if(dstDir==null){
                    dst = sftpStoreFileInfo.getRootPath() + SEPARATOR + sourceFile.getName();
                }else{
                    mkdir(channelSftp,sftpStoreFileInfo.getRootPath(),sftpStoreFileInfo.getDstDir());
                    dst = sftpStoreFileInfo.getRootPath() + SEPARATOR + sftpStoreFileInfo.getDstDir() + SEPARATOR + sourceFile.getName();
                }

                channelSftp.put(sftpStoreFileInfo.getSrcFile(), dst);
                logger.info("sftp stored success:{}", dst);
            }
            channelSftp.exit();
            return true;
        } catch (JSchException e) {
            logger.error("error sftp connection:{}", e);
        } catch (SftpException e) {
            logger.error("error sftp operation:{}", e);
        }
        return false;
    }
}
