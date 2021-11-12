package com.tmb.oneapp.lendingservice.client;


import com.jcraft.jsch.*;
import com.jcraft.jsch.ChannelSftp.LsEntry;
import com.tmb.common.logger.TMBLogger;
import com.tmb.oneapp.lendingservice.model.SFTPStoreFileInfo;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.File;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Vector;   //NOSONAR

import static com.tmb.oneapp.lendingservice.constant.LendingServiceConstant.SEPARATOR;

/**
 * Provides method to store file to sftp server.
 */
@Service
public class SFTPClientImp implements FTPClient {

    @Value("${sftp.remote-host}")
    private String remoteHost;
    @Value("${sftp.port}")
    private int port;
    @Value("${sftp.username}")
    private String username;
    @Value("${sftp.password}")
    private String password;

    private static final TMBLogger<SFTPClientImp> logger = new TMBLogger<>(SFTPClientImp.class);

    public List<SFTPStoreFileInfo> setSFTPStoreFileInfo(String[] locations, String jpgFile, String directoryPath) {
        List<SFTPStoreFileInfo> sftpStoreFiles = new ArrayList<>();

        SFTPStoreFileInfo sftpStoreFile;
        for (int i = 0; i < locations.length; i++) {
            sftpStoreFile = new SFTPStoreFileInfo();
            sftpStoreFile.setSrcFile(jpgFile);
            sftpStoreFile.setRootPath(locations[i]);
            if (i == 0) {
                sftpStoreFile.setDstDir(directoryPath);
            }
            sftpStoreFiles.add(sftpStoreFile);
        }
        return sftpStoreFiles;
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


    private boolean mkdir(ChannelSftp channel, String rootPath, String destDirs) {
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

    @Override
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

    @Override
    public boolean storeFile(List<SFTPStoreFileInfo> storeFileInfoList) {
        ChannelSftp channelSftp;
        String dst;
        try {
            channelSftp = (ChannelSftp) setupJsch();
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
        ChannelSftp channelSftp;
        String dst = null;
        try {
            channelSftp = (ChannelSftp) setupJsch();
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

    public String getRemoteHost() {
        return remoteHost;
    }

    @Override
    public boolean purgeFileOlderThanNDays(String dst, long day) {
        ChannelSftp channelSftp;
        try {
            channelSftp = (ChannelSftp) setupJsch();
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
            logger.error("error sftp exception:{}", e);
            return false;
        } catch (Exception e) {
            logger.error("error other exception:{}", e);
            return false;
        }
    }

    public void removeDirectory(SFTPStoreFileInfo storeFileInfoList) {
        ChannelSftp channelSftp;
        try {
            String dst = storeFileInfoList.getRootPath() + SEPARATOR + storeFileInfoList.getDstDir();
            channelSftp = (ChannelSftp) setupJsch();
            channelSftp.connect();
            recursiveRemoveDirectory(channelSftp, dst);
            channelSftp.exit();
        }catch (Exception e) {
            logger.error("sftp remove directory error exception:{}", e.getMessage());
        }
    }

    @SuppressWarnings("unchecked")
    private void listDirectory(ChannelSftp channelSftp, String path, List<String> list) throws SftpException {
        Vector<LsEntry> files = channelSftp.ls(path);  //NOSONAR
        for (LsEntry entry : files) {
            if (entry.getAttrs().isDir() && !entry.getFilename().equals(".") && !entry.getFilename().equals("..")) {
                list.add(path + "/" + entry.getFilename() + "");
            }
        }
    }

    @SuppressWarnings("unchecked")
    private void purgeDataOlderThanNDay(ChannelSftp channelSftp, String path, long day) throws SftpException {
        Vector<LsEntry> files = channelSftp.ls(path);  //NOSONAR
        long cutOff = System.currentTimeMillis() - (day * 24 * 60 * 60 * 1000);
        long modifyDate;
        for (LsEntry entry : files) {
            if (entry.getAttrs().isDir() && !entry.getFilename().equals(".") && !entry.getFilename().equals("..")) {
                modifyDate = entry.getAttrs().getMTime() * 1000L;
                if (modifyDate < cutOff) {
                    channelSftp.rmdir(path + "/" + entry.getFilename());
                    logger.info("Purge data older than {} Day Success >>> Last modify time:{} >>> Path:{}", day,
                            entry.getAttrs().getMtimeString(), path + "/" + entry.getFilename());
                }
            }
        }
    }

    @SuppressWarnings("unchecked")
    private static void recursiveRemoveDirectory(ChannelSftp channelSftp, String path) throws SftpException {
        Collection<ChannelSftp.LsEntry> fileAndDirList = channelSftp.ls(path);
        for (ChannelSftp.LsEntry item : fileAndDirList) {
            if (!item.getAttrs().isDir()) {
                channelSftp.rm(path + "/" + item.getFilename());
            } else if (!(".".equals(item.getFilename()) || "..".equals(item.getFilename()))) {
                try {
                    channelSftp.rmdir(path + "/" + item.getFilename());
                } catch (Exception e) {
                    recursiveRemoveDirectory(channelSftp, path + "/" + item.getFilename());
                }
            }
        }
        channelSftp.rmdir(path);
    }
}
