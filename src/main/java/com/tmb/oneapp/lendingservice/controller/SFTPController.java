package com.tmb.oneapp.lendingservice.controller;

import com.jcraft.jsch.*;
import com.tmb.common.exception.model.TMBCommonException;
import com.tmb.common.logger.LogAround;
import com.tmb.common.logger.TMBLogger;
import com.tmb.common.model.TmbOneServiceResponse;
import com.tmb.common.model.TmbStatus;
import com.tmb.oneapp.lendingservice.client.SFTPClientImp;
import com.tmb.oneapp.lendingservice.constant.LendingServiceConstant;
import com.tmb.oneapp.lendingservice.constant.ResponseCode;
import com.tmb.oneapp.lendingservice.model.SFTPStoreFileInfo;
import com.tmb.oneapp.lendingservice.model.ServiceResponse;
import com.tmb.oneapp.lendingservice.model.loan.ProductRequest;
import com.tmb.oneapp.lendingservice.service.LoanService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.*;
import java.util.ArrayList;
import java.util.Map;
import java.util.concurrent.Executors;

/**
 * Provides loan services
 */
@RequestMapping("/sftp")
@RestController

public class SFTPController {
    private static final TMBLogger<SFTPController> logger = new TMBLogger<>(SFTPController.class);

    @Autowired
    private SFTPClientImp sftpClientImp;

    @Value("${ftp.locations.consent-images}")
    public String locations;



    private Channel setupJsch() throws JSchException {
        JSch jsch = new JSch();
        //jsch.setKnownHosts("/Users/john/.ssh/known_hosts");
        Session jschSession = jsch.getSession(sftpClientImp.username, sftpClientImp.remoteHost);
        jschSession.setPassword(sftpClientImp.password);
        java.util.Properties config = new java.util.Properties();
        config.put("StrictHostKeyChecking", "no");
        jschSession.setConfig(config);
        jschSession.setTimeout(60000);
        jschSession.connect();
        return  jschSession.openChannel("sftp");
    }

    @PostMapping(value = "/poc")
    public ResponseEntity poc() throws  IOException {

        String baseDir = System.getProperty("user.dir");
        String localFile = baseDir + File.separator + "fop" + File.separator + "01_210615152223_abc_00110.JPG";

        String[] locationTokens = locations.split(",");

        ArrayList<SFTPStoreFileInfo> sftpStoreFileInfos = new ArrayList<>();

        SFTPStoreFileInfo c1 = new SFTPStoreFileInfo();
        c1.setDstDir("crm1/ref1");
        c1.setRootPath(locationTokens[0]);
        c1.setSrcFile(localFile);


        SFTPStoreFileInfo c2 = new SFTPStoreFileInfo();
        c2.setDstDir(null);
        c2.setRootPath(locationTokens[1]);
        c2.setSrcFile(localFile);

        SFTPStoreFileInfo c3 = new SFTPStoreFileInfo();
        c3.setDstDir(null);
        c3.setRootPath(locationTokens[2]);
        c3.setSrcFile(localFile);

        sftpStoreFileInfos.add(c1);
        sftpStoreFileInfos.add(c2);
        sftpStoreFileInfos.add(c3);

        sftpClientImp.storeFile(sftpStoreFileInfos);
        return ResponseEntity.ok().build();
    }

    @PostMapping(value = "/jsch")
    public ResponseEntity jsch() throws JSchException, SftpException {
        logger.info("start jsch");
        ChannelSftp channelSftp = (ChannelSftp) setupJsch();
        channelSftp.connect();
        String baseDir = System.getProperty("user.dir");
        String localFile = baseDir + File.separator + "fop" + File.separator + "01_210615152223_abc_00110.JPG";
        try{
            String remoteDir = "/users/mibuser/u01/datafile/mib/mibshare/LetterConsent";
            String current = channelSftp.pwd();
            logger.info("current 1 :{}",current);

            channelSftp.cd(remoteDir);
            channelSftp.mkdir("jsch");
            channelSftp.cd(remoteDir+"/jsch");
            String current2 = channelSftp.pwd();
            logger.info("current 2 :{}",current2);

            channelSftp.mkdir("jsch2");
            channelSftp.cd(remoteDir+"/jsch/jsch2");
            String current3 = channelSftp.pwd();
            logger.info("current 3 :{}",current3);

            channelSftp.put(localFile, remoteDir + "/jsch/jsch2/01_210615152223_abc_00110.JPG");
            channelSftp.exit();
        }catch (Exception e){


        }

        logger.info("end jsch");
        return ResponseEntity.ok().build();
    }



}
