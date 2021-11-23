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
public class SFTPClientImp extends SFTPClient implements FTPClient {

    @Value("${sftp.remote-host}")
    private String remoteHost;
    @Value("${sftp.port}")
    private int port;
    @Value("${sftp.username}")
    private String username;
    @Value("${sftp.password}")
    private String password;

    private static final TMBLogger<SFTPClientImp> logger = new TMBLogger<>(SFTPClientImp.class);

    public Channel setupJsch() throws JSchException {
        return createChannel(username, remoteHost, password);
    }

    @Override
    public boolean storeFile(List<SFTPStoreFileInfo> storeFileInfoList) {
        String dst;
        try {
            ChannelSftp channelSftp = connectChannelSftp();
            for (SFTPStoreFileInfo sftpStoreFileInfo : storeFileInfoList) {
                File sourceFile = getSourceFile(sftpStoreFileInfo);
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
        } catch (Exception e) {
            logSftpError(e);
            return false;
        }
    }


    @Override
    public boolean removeFile(List<SFTPStoreFileInfo> storeFileInfoList) {
        String dst = null;
        try {
            ChannelSftp channelSftp = connectChannelSftp();
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
        } catch (Exception e) {
            logSftpError(e);
            return false;
        }
    }

    public String getRemoteHost() {
        return remoteHost;
    }

    @Override
    public boolean purgeFileOlderThanNDays(String dst, long day) {
        try {
            ChannelSftp channelSftp = connectChannelSftp();
            List<String> list = new ArrayList<>();
            listDirectory(channelSftp, dst, list);

            for (String entry : list) {
                purgeDataOlderThanNDay(channelSftp, entry, day);
            }
            channelSftp.exit();
            return true;

        } catch (Exception e) {
            logSftpError(e);
            return false;
        }
    }

    public void removeDirectory(SFTPStoreFileInfo storeFileInfoList) {
        try {
            String dst = storeFileInfoList.getRootPath() + SEPARATOR + storeFileInfoList.getDstDir();
            ChannelSftp channelSftp = connectChannelSftp();
            recursiveRemoveDirectory(channelSftp, dst);
            channelSftp.exit();
        } catch (Exception e) {
            logger.error("sftp remove directory error exception:{}", e.getMessage());
        }
    }

    private ChannelSftp connectChannelSftp() throws JSchException {
        ChannelSftp channelSftpEnoti = (ChannelSftp) setupJsch();
        channelSftpEnoti.connect();
        return channelSftpEnoti;
    }
}
