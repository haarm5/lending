package com.tmb.oneapp.lendingservice.client;

import com.jcraft.jsch.Channel;
import com.jcraft.jsch.JSchException;
import com.tmb.oneapp.lendingservice.model.SFTPStoreFileInfo;

import java.io.IOException;
import java.util.List;

/**
 * Provides method to store file to ftp server.
 */
public interface FTPClient {

    Channel setupJsch() throws JSchException;
    /**
     * Store file to ftp server
     *
     * @param storeFileInfo
     * @return
     * @throws IOException
     */
    boolean storeFile(List<SFTPStoreFileInfo> storeFileInfo);
    
    /**
     * Remove file to ftp server
     *
     * @param storeFileInfo
     * @return
     * @throws IOException
     */
    boolean removeFile(List<SFTPStoreFileInfo> storeFileInfoList);
    
    /**
     * Delete Files Older Than N Days
     *
     * @param dst
     * @param day
     * @return
     */
	boolean purgeFileOlderThanNDays(String dst, long day);
}
