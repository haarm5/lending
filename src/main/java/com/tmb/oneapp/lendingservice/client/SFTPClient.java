package com.tmb.oneapp.lendingservice.client;


import com.jcraft.jsch.*;
import com.jcraft.jsch.ChannelSftp.LsEntry;
import com.tmb.common.logger.TMBLogger;
import com.tmb.oneapp.lendingservice.model.SFTPStoreFileInfo;
import org.springframework.stereotype.Service;

import java.io.File;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Vector;

import static com.tmb.oneapp.lendingservice.constant.LendingServiceConstant.SEPARATOR;

/**
 * Provides method to store file to sftp server.
 */
@Service
public class SFTPClient {

    private static final TMBLogger<SFTPClient> logger = new TMBLogger<>(SFTPClient.class);

    public Channel createChannel(String enotiUsername, String enotiRemoteHost, String enotiPassword) throws JSchException {
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

    public boolean isDirExist(ChannelSftp channel, String path) {
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

    @SuppressWarnings("unchecked")
    public void listDirectory(ChannelSftp channelSftp, String path, List<String> list) throws SftpException {
        Vector<LsEntry> files = channelSftp.ls(path);  //NOSONAR
        for (LsEntry entry : files) {
            if (entry.getAttrs().isDir() && !entry.getFilename().equals(".") && !entry.getFilename().equals("..")) {
                list.add(path + "/" + entry.getFilename() + "");
            }
        }
    }

    @SuppressWarnings("unchecked")
    public void purgeDataOlderThanNDay(ChannelSftp channelSftp, String path, long day) throws SftpException {
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
    public static void recursiveRemoveDirectory(ChannelSftp channelSftp, String path) throws SftpException {
        Collection<LsEntry> fileAndDirList = channelSftp.ls(path);
        for (LsEntry item : fileAndDirList) {
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

    public File getSourceFile(SFTPStoreFileInfo sftpStoreFileEnotiInfo) {
        return new File(sftpStoreFileEnotiInfo.getSrcFile());
    }

    public void logSftpError(Exception e) {
        if(e instanceof JSchException) {
            logger.error("error jsch connection:{}", e);
        }else if(e instanceof SftpException) {
            logger.error("error sftp exception:{}", e);
        }else{
            logger.error("error other exception:{}", e);
        }
    }
}
