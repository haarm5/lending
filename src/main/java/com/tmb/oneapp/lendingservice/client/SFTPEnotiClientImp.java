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

    public Channel setupEnotiJsch() throws JSchException {
        return createChannel(enotiUsername, enotiRemoteHost, enotiPassword);
    }

    @Override
    public boolean storeFile(List<SFTPStoreFileInfo> storeFileInfoList) {
        String dst;
        try {
            ChannelSftp channelSftpEnoti = connectChannelSftp();
            for (SFTPStoreFileInfo sftpStoreFileEnotiInfo : storeFileInfoList) {
                File sourceFile = getSourceFile(sftpStoreFileEnotiInfo);
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

        } catch (Exception e) {
            logSftpError(e);
            return false;
        }
    }


    @Override
    public boolean removeFile(List<SFTPStoreFileInfo> storeFileInfoList) {
        String dst = null;
        try {
            ChannelSftp channelSftpEnoti = connectChannelSftp();
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
        } catch (Exception e) {
            logSftpError(e);
            return false;
        }
    }

    @Override
    public boolean purgeFileOlderThanNDays(String dst, long day) {
        try {
            ChannelSftp channelSftpEnoti = connectChannelSftp();
            List<String> list = new ArrayList<>();
            listDirectory(channelSftpEnoti, dst, list);

            for (String entry : list) {
                purgeDataOlderThanNDay(channelSftpEnoti, entry, day);
            }
            channelSftpEnoti.exit();
            return true;

        } catch (Exception e) {
            logSftpError(e);
            return false;
        }
    }

    private ChannelSftp connectChannelSftp() throws JSchException {
        ChannelSftp channelSftpEnoti = (ChannelSftp) setupEnotiJsch();
        channelSftpEnoti.connect();
        return channelSftpEnoti;
    }
}
