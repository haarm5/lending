package com.tmb.oneapp.lendingservice.controller;


import com.jcraft.jsch.*;
import com.tmb.common.logger.TMBLogger;
import com.tmb.oneapp.lendingservice.client.SFTPClientImp;
import net.schmizz.sshj.SSHClient;
import net.schmizz.sshj.sftp.SFTPClient;
import net.schmizz.sshj.transport.verification.PromiscuousVerifier;
import net.schmizz.sshj.xfer.FileSystemFile;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.File;
import java.io.IOException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@RequestMapping("/sftp")
@RestController
public class SFTPController {
    private static final TMBLogger<SFTPController> logger = new TMBLogger<>(SFTPController.class);

    @Autowired
    private SFTPClientImp sftpClientImp;

    private ExecutorService executor = Executors.newSingleThreadExecutor();

    private SSHClient setupSshj() throws IOException {
        SSHClient client = new SSHClient();
        client.addHostKeyVerifier(new PromiscuousVerifier());
        client.connect(sftpClientImp.remoteHost);
        client.authPassword(sftpClientImp.username, sftpClientImp.password);
        return client;
    }

    private ChannelSftp setupJsch() throws JSchException {
        JSch jsch = new JSch();
        //jsch.setKnownHosts("/Users/john/.ssh/known_hosts");
        Session jschSession = jsch.getSession(sftpClientImp.username, sftpClientImp.remoteHost);
        jschSession.setPassword(sftpClientImp.password);
        java.util.Properties config = new java.util.Properties();
        config.put("StrictHostKeyChecking", "no");
        jschSession.setConfig(config);
        jschSession.connect();
        return (ChannelSftp) jschSession.openChannel("sftp");
    }

    @PostMapping(value = "/jsch")
    public ResponseEntity jsch() throws JSchException, SftpException {
        logger.info("start jsch");
        ChannelSftp channelSftp = setupJsch();
        channelSftp.connect();
        String baseDir = System.getProperty("user.dir");
        String localFile = baseDir + File.separator + "fop" + File.separator + "01_210615152223_abc_00110.JPG";

        channelSftp.cd( sftpClientImp.locPrimaryLocation );
        channelSftp.mkdir(sftpClientImp.locPrimaryLocation +"/jsch");
        channelSftp.cd(sftpClientImp.locPrimaryLocation +"/jsch/");
        channelSftp.mkdir(sftpClientImp.locPrimaryLocation +"/jsch/abc");

        channelSftp.put(localFile,  sftpClientImp.locPrimaryLocation + "/jsch/abc/" + "01_210615152223_abc_00110.JPG");




        channelSftp.exit();
        logger.info("end jsch");
        return ResponseEntity.ok().build();
    }

    @PostMapping(value = "/sshj")
    public ResponseEntity sshj() throws IOException {
        logger.info("start sshj");
        String directoryPath = sftpClientImp.locPrimaryLocation + "/sshj/";
        String baseDir = System.getProperty("user.dir");
        SSHClient sshClient = setupSshj();
        SFTPClient sftpClient = sshClient.newSFTPClient();
        String localFile = baseDir + File.separator + "fop" + File.separator + "01_210615152223_abc_00110.JPG";
        sftpClient.mkdir(directoryPath);
        sftpClient.put(new FileSystemFile(localFile), directoryPath + "01_210615152223_abc_00110.JPG");
        sftpClient.close();
        sshClient.disconnect();
        logger.info("end sshj");
        return ResponseEntity.ok().build();
    }

    @PostMapping(value = "/normal")
    public ResponseEntity normal() throws IOException {
        String directoryPath = "normal/12345";
        String baseDir = System.getProperty("user.dir");
        sftpClientImp.storeFile(directoryPath, baseDir + File.separator + "fop" + File.separator + "01_210615152223_abc_00110.JPG");
        return ResponseEntity.ok().build();
    }

    @PostMapping(value = "/async")
    public ResponseEntity async() {

        executor.execute(() -> {
            try {
                Thread.sleep(5000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            String directoryPath = "async/12346";
            String baseDir = System.getProperty("user.dir");
            try {
                sftpClientImp.storeFile(directoryPath, baseDir + File.separator + "fop" + File.separator + "01_210615152223_abc_00110.JPG");
            } catch (IOException e) {
                e.printStackTrace();
            }
        });

        return ResponseEntity.ok().build();
    }
}
