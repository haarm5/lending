package com.tmb.oneapp.lendingservice.client;

import java.io.IOException;

/**
 * Provides method to store file to ftp server.
 */
public interface FTPClient {
    /**
     * Store file to ftp server
     * @param target
     * @param srcFile
     * @return
     * @throws IOException
     */
    boolean storeFile(String target, String srcFile) throws IOException;
}
