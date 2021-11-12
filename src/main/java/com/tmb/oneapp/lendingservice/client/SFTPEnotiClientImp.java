package com.tmb.oneapp.lendingservice.client;


import com.jcraft.jsch.*;
import com.tmb.common.logger.TMBLogger;
import com.tmb.oneapp.lendingservice.model.SFTPStoreFileInfo;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import static com.tmb.oneapp.lendingservice.constant.LendingServiceConstant.SEPARATOR;

/**
 * Provides method to store file to sftp server.
 */
@Service
public class SFTPEnotiClientImp extends SFTPClient implements FTPClient {

    @Value("${sftp.enoti.remote-host}")
    private String enotiRemoteHost;
    @Value("${sftp.enoti.port}")
    private int enotiPort;
    @Value("${sftp.enoti.username}")
    private String enotiUsername;
    @Value("${sftp.enoti.password}")
    private String enotiPassword;

    private static final TMBLogger<SFTPEnotiClientImp> logger = new TMBLogger<>(SFTPEnotiClientImp.class);

    public String getEnotiRemoteHost() {
        return enotiRemoteHost;
    }

    @Override
    public Channel setupJsch() throws JSchException {
        JSch jsch = new JSch();
        Session jschSession = jsch.getSession(enotiUsername, enotiRemoteHost);
        jschSession.setPassword(enotiPassword);
        java.util.Properties config = new java.util.Properties();
        config.put("StrictHostKeyChecking", "no");
        jschSession.setConfig(config);
        jschSession.setTimeout(60000);
        jschSession.connect();
        return jschSession.openChannel("sftp");
    }

    @Override
    public boolean storeFile(List<SFTPStoreFileInfo> storeFileInfoList) {
        String dst;
        try {
            ChannelSftp channelSftp = (ChannelSftp) setupJsch();
            channelSftp.connect();
            for (SFTPStoreFileInfo sftpStoreFileInfo : storeFileInfoList) {
                File sourceFile = new File(sftpStoreFileInfo.getSrcFile());
                if (!sourceFile.exists()) {
                    logger.error("src file to upload to ftp does not exists: {}", sftpStoreFileInfo.getSrcFile());
                    return false;
                }
                String dstDir = sftpStoreFileInfo.getDstDir();
                if (StringUtils.isEmpty(dstDir)) {
                    dst = sftpStoreFileInfo.getRootPath() + SEPARATOR + sourceFile.getName();
                } else {
                    mkdir(channelSftp, sftpStoreFileInfo.getRootPath(), sftpStoreFileInfo.getDstDir());
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


    @Override
    public boolean removeFile(List<SFTPStoreFileInfo> storeFileInfoList) {
        String dst = null;
        try {
            ChannelSftp channelSftp = (ChannelSftp) setupJsch();
            channelSftp.connect();
            for (SFTPStoreFileInfo sftpStoreFileInfo : storeFileInfoList) {
                String dstDir = sftpStoreFileInfo.getDstDir();
                if (dstDir != null) {
                    mkdir(channelSftp, sftpStoreFileInfo.getRootPath(), sftpStoreFileInfo.getDstDir());
                    dst = sftpStoreFileInfo.getRootPath() + SEPARATOR + sftpStoreFileInfo.getDstDir();
                    channelSftp.rmdir(dst);
                }
                logger.info("sftp deleted success:{}", dst);
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
    public boolean purgeFileOlderThanNDays(String dst, long day) {
        try {
            ChannelSftp channelSftp = (ChannelSftp) setupJsch();
            channelSftp.connect();
            List<String> list = new ArrayList<>();
            listDirectory(channelSftp, dst, list);

            for (String entry : list) {
                purgeDataOlderThanNDay(channelSftp, entry, day);
            }
            channelSftp.exit();
            return true;

        } catch (JSchException e) {
            logger.error("error jsch connection:{}", e);
            return false;
        } catch (SftpException e) {
            logger.error("error sftp e-noti exception:{}", e);
            return false;
        } catch (Exception e) {
            logger.error("error other exception:{}", e);
            return false;
        }
    }
}
