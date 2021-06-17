package com.tmb.oneapp.lendingservice.client;

import com.tmb.oneapp.lendingservice.model.SFTPStoreFileInfo;

import java.io.IOException;
import java.util.List;

/**
 * Provides method to store file to ftp server.
 */
public interface FTPClient {
    /**
     * Store file to ftp server
     * @param destDirs
     * @param srcFile
     * @return
     * @throws IOException
     */
    boolean storeFile(List<SFTPStoreFileInfo> storeFileInfo)throws IOException;
}
