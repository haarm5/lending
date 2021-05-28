package com.tmb.oneapp.lendingservice.client;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.tmb.common.logger.TMBLogger;
import com.tmb.oneapp.lendingservice.constant.LendingServiceConstant;
import com.tmb.oneapp.lendingservice.model.instantloancreation.LOCRequest;
import com.tmb.oneapp.lendingservice.util.CommonServiceUtils;
import com.tmb.oneapp.lendingservice.util.ImageGeneratorUtil;
import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPReply;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

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

    private final ObjectMapper mapper;

    @Autowired
    ResourceLoader resourceLoader;

    public FTPServerLOCClient(ObjectMapper mapper) {
        this.mapper = mapper;
    }

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
            return result;
        }
        FTPClient ftpClient = getFTPConnection();

        if(ftpClient == null){
            return false;
        }
        try (BufferedInputStream buffIn = new BufferedInputStream(new FileInputStream(sourceFile));){

            ftpClient.setFileType(FTP.BINARY_FILE_TYPE);
            ftpClient.enterLocalPassiveMode();
            boolean  directoryPathChanged =   ftpClient.changeWorkingDirectory(locPrimaryLocation);
            if(directoryPathChanged){
                boolean success = makeDirectories(ftpClient,directoryPath);
                if(success){
                    String destinationFilePath = ftpClient.printWorkingDirectory();
                    logger.info("destinationFilePath {} ",destinationFilePath);
                    destinationFilePath = destinationFilePath + File.separator + fileName;
                    result = ftpClient.storeFile(destinationFilePath, buffIn);
                    logger.info("Copy file to FTP is done!! result:"+result);

                }
            }

        } catch (IOException e) {
            logger.error("IOException in closeFTPConnection {} ", e.toString());
        } finally {
            closeFTPConnection(ftpClient);
        }
        logger.info("uploadFileToFTP Method ends here");
        return result;
    }

    public void generateLOCImageAndUploadToFTP(LOCRequest request) throws JsonProcessingException {

        logger.info("generateLOCImageAndUploadToFTP start");
        String dateStr = CommonServiceUtils.getDateAndTimeInYYYYMMDDHHMMSS();
        logger.info("dateStr {} : "+dateStr);
        dateStr = dateStr.replaceAll("[/: ]", "");
        dateStr = dateStr.substring(2);
        String fileName = "01" + LendingServiceConstant.UNDER_SCORE + dateStr + LendingServiceConstant.UNDER_SCORE + request.getAppRefNo() + LendingServiceConstant.UNDER_SCORE+ "00110";
        String jsonData = mapper.writeValueAsString(request);
        String jpgSourcePath = ImageGeneratorUtil.generateJPGFile(jsonData,"InstantLoanNCBConsentTH",fileName);
        if(jpgSourcePath != null){
            String jpgFileName = fileName + ".JPG";
            String directoryPath = request.getNcbid() + File.separator + request.getAppRefNo();
            uploadFileToFTP(jpgSourcePath,jpgFileName,directoryPath);
        }

        logger.info("generateLOCImageAndUploadToFTP END");
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


