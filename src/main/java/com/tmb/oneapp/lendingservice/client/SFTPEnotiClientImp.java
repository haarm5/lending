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
        JSch jschEnoti = new JSch();
        Session jschSessionEnoti = jschEnoti.getSession(enotiUsername, enotiRemoteHost);
        jschSessionEnoti.setPassword(enotiPassword);
        java.util.Properties configEnoti = new java.util.Properties();
        configEnoti.put("StrictHostKeyChecking", "no");
        jschSessionEnoti.setConfig(configEnoti);
        jschSessionEnoti.setTimeout(60000);
        jschSessionEnoti.connect();
        return jschSessionEnoti.openChannel("sftp");
    }

    @Override
    public boolean storeFile(List<SFTPStoreFileInfo> storeFileInfoList) {
        String dst;
        try {
            ChannelSftp channelSftpEnoti = (ChannelSftp) setupJsch();
            channelSftpEnoti.connect();
            for (SFTPStoreFileInfo sftpStoreFileEnotiInfo : storeFileInfoList) {
                File sourceFile = new File(sftpStoreFileEnotiInfo.getSrcFile());
                if (!sourceFile.exists()) {
                    logger.error("src file to upload to ftp does not exists: {}", sftpStoreFileEnotiInfo.getSrcFile());
                    return false;
                }
                String dstDir = sftpStoreFileEnotiInfo.getDstDir();
                if (StringUtils.isEmpty(dstDir)) {
                    dst = sftpStoreFileEnotiInfo.getRootPath() + SEPARATOR + sourceFile.getName();
                } else {
                    mkdir(channelSftpEnoti, sftpStoreFileEnotiInfo.getRootPath(), sftpStoreFileEnotiInfo.getDstDir());
                    dst = sftpStoreFileEnotiInfo.getRootPath() + SEPARATOR + sftpStoreFileEnotiInfo.getDstDir() + SEPARATOR + sourceFile.getName();
                }
                channelSftpEnoti.put(sftpStoreFileEnotiInfo.getSrcFile(), dst);
                logger.info("sftp e-noti stored success:{}", dst);
            }
            channelSftpEnoti.exit();
            return true;
        } catch (JSchException e) {
            logger.error("error sftp e-noti connection:{}", e);
        } catch (SftpException e) {
            logger.error("error sftp e-noti operation:{}", e);
        }
        return false;
    }


    @Override
    public boolean removeFile(List<SFTPStoreFileInfo> storeFileInfoList) {
        String dst = null;
        try {
            ChannelSftp channelSftpEnoti = (ChannelSftp) setupJsch();
            channelSftpEnoti.connect();
            for (SFTPStoreFileInfo sftpStoreFileEnotiInfo : storeFileInfoList) {
                String dstDir = sftpStoreFileEnotiInfo.getDstDir();
                if (dstDir != null) {
                    mkdir(channelSftpEnoti, sftpStoreFileEnotiInfo.getRootPath(), sftpStoreFileEnotiInfo.getDstDir());
                    dst = sftpStoreFileEnotiInfo.getRootPath() + SEPARATOR + sftpStoreFileEnotiInfo.getDstDir();
                    channelSftpEnoti.rmdir(dst);
                }
                logger.info("sftp e-noti deleted success:{}", dst);
            }
            channelSftpEnoti.exit();
            return true;
        } catch (JSchException e) {
            logger.error("error sftp e-noti connection:{}", e);
        } catch (SftpException e) {
            logger.error("error sftp e-noti operation:{}", e);
        }
        return false;
    }

    @Override
    public boolean purgeFileOlderThanNDays(String dst, long day) {
        try {
            ChannelSftp channelSftpEnoti = (ChannelSftp) setupJsch();
            channelSftpEnoti.connect();
            List<String> list = new ArrayList<>();
            listDirectory(channelSftpEnoti, dst, list);

            for (String entry : list) {
                purgeDataOlderThanNDay(channelSftpEnoti, entry, day);
            }
            channelSftpEnoti.exit();
            return true;

        } catch (JSchException e) {
            logger.error("error jsch e-noti connection:{}", e);
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
