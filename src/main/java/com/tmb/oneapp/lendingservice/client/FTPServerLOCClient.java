package com.tmb.oneapp.lendingservice.client;

import com.tmb.common.logger.TMBLogger;
import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPReply;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Service;

import java.io.*;

@Service
public class FTPServerLOCClient {
    private static final TMBLogger<FTPServerLOCClient> logger = new TMBLogger<>(FTPServerLOCClient.class);

    @Value("${ftp.server.ip}")
    public String serverIP;
    @Value("${ftp.server.port}")
    public int port;
    @Value("${ftp.server.username}")
    public String username;
    @Value("${ftp.server.password}")
    public String password;

    @Value("${ftp.loc.file.primary.location}")
    public String locPrimaryLocation;

    @Value("${ftp.loc.file.secondary.location}")
    public String locSecondaryLocation;

    @Autowired
    ResourceLoader resourceLoader;

    public  FTPClient getFTPConnection(){
        FTPClient ftpClient = new FTPClient();
        try{

            ftpClient.connect(serverIP, port);
            int replyCode = ftpClient.getReplyCode();
            if (!FTPReply.isPositiveCompletion(replyCode)) {
                logger.info("Operation failed. Server reply code: " + replyCode);
                return null;
            }
            boolean success = ftpClient.login(username, password);

            if (success) {
                logger.info("Connection to FTP Server Success");
                return ftpClient;
            } else {
                logger.info("Connection to FTP Server Failed");
            }

        }catch (Exception e){
            logger.error("Exception while connecting to FTP server", e.toString());
        }
        return null;
    }

    private static void closeFTPConnection(FTPClient ftpClient) {
        try {
            if (ftpClient.isConnected()) {
                ftpClient.logout();
                ftpClient.disconnect();
            }
            logger.info("closed FTP Connection!");
        } catch (IOException e) {
            logger.error("IOException in closeFTPConnection {} ", e.toString());
        }
    }

    public  boolean uploadFileToFTP(String sourceFilePath, String fileName, String directoryPath) {
        logger.info("uploadFileToFTP Method Begins here");
        boolean result = false;


        logger.info("uploadFileToFTP - sourceFilePath:"+sourceFilePath);
        logger.info("uploadFileToFTP - fileName:"+fileName);
        File sourceFile = new File(sourceFilePath);
        if(!sourceFile.exists()) {
            logger.info("In uploadFileToFTP - source file does not exist at given sourceFilePath:"+sourceFilePath);
            return false;
        }
        FTPClient ftpClient = getFTPConnection();

        if(ftpClient == null){
            return false;
        }

        int i =1;
        String ftpLocation = locPrimaryLocation;
        try{
            while( i <= 2){
                try (BufferedInputStream buffIn = new BufferedInputStream(new FileInputStream(sourceFile))){
                    ftpClient.enterLocalPassiveMode();
                    ftpClient.setFileType(FTP.BINARY_FILE_TYPE);

                    boolean  directoryPathChanged =   ftpClient.changeWorkingDirectory(ftpLocation);
                    if(directoryPathChanged){
                        boolean success = makeDirectories(ftpClient,directoryPath);
                        if(success){
                            StringBuilder destinationFilePath = new StringBuilder();
                            destinationFilePath.append(ftpClient.printWorkingDirectory());
                            destinationFilePath.append(File.separator);
                            destinationFilePath.append(fileName);
                            logger.info("destinationFilePath {} ",destinationFilePath);
                            result = ftpClient.storeFile(destinationFilePath.toString(), buffIn);
                            logger.info("Copy file to FTP is done!! result:"+result);

                        }
                    }
                } catch (FileNotFoundException e) {
                    logger.error("FileNotFoundException in uploadFileToFTP {} ", e.toString());
                } catch (IOException e) {
                    logger.error("IOException in uploadFileToFTP {} ", e.toString());
                }
                i++;
                ftpLocation = locSecondaryLocation;
            }

        }finally {
            closeFTPConnection(ftpClient);
        }
        logger.info("uploadFileToFTP Method ends here");
        return result;
    }


    public static boolean makeDirectories(FTPClient ftpClient, String dirPath)
            throws IOException {
        logger.info("In makeDirectories dirPath {} :",dirPath);
        String[] pathElements = dirPath.split("/");
        if (pathElements != null && pathElements.length > 0) {
            for (String singleDir : pathElements) {
                logger.info("singleDir is {} : ",singleDir);
                boolean existed = ftpClient.changeWorkingDirectory(singleDir);
                if (!existed) {
                    boolean created = ftpClient.makeDirectory(singleDir);
                    if (created) {
                        logger.info("directory created for : " + singleDir);
                        ftpClient.changeWorkingDirectory(singleDir);
                    } else {
                        logger.info("failed to create directory for : " + singleDir);
                        return false;
                    }
                }
            }
        }
        return true;
    }


}


