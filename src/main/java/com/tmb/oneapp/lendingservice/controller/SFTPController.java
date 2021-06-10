package com.tmb.oneapp.lendingservice.controller;

import com.tmb.common.logger.TMBLogger;
import org.apache.commons.vfs2.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;

@RequestMapping("/sftp")
@RestController
public class SFTPController {
    private static final TMBLogger<SFTPController> logger = new TMBLogger<>(SFTPController.class);
    @Value("${ftp.server.ip}")
    public String remoteHost;
    @Value("${ftp.server.port}")
    public int port;
    @Value("${ftp.server.username}")
    public String username;
    @Value("${ftp.server.password}")
    public String password;

    @Value("${ftp.loc.file.primary.location}")
    public String locPrimaryLocation;

    private static URI createConnectionString(String hostName, String username, String password, String remoteFilePath) {
        URI sftpURI = null;
        try {
            String userInfo = username + ":" + password;

            sftpURI = new URI("sftp", userInfo, hostName, -1, remoteFilePath, null, null);
        } catch (URISyntaxException e) {
            logger.error("Got error createConnectionString:{}", e);
        }

        return sftpURI;
    }

    @PostMapping(value = "/upload")
    public ResponseEntity upload() {

        new Thread(new Runnable() {
            @Override
            public void run() {
                String[] files = new String[]{"lending_test.jpg"};
                new File("./images").mkdir();
                for (String file : files) {
                    Resource configResource = new ClassPathResource("fop/" + file);
                    InputStream inputStream = null;
                    try {
                        inputStream = configResource.getInputStream();
                        Files.copy(inputStream, new File("./images/" + file).toPath(), StandardCopyOption.REPLACE_EXISTING);
                    } catch (IOException e) {
                        logger.error("Got error when prepare image file: {}", e);
                    }

                }


                String remoteFilePath = locPrimaryLocation + "/abc/xyz/lending_test.jpg";
                try {
                    FileSystemManager manager = VFS.getManager();
                    FileObject local = manager.resolveFile(
                            System.getProperty("user.dir") + "/images/lending_test.jpg");
                    FileObject remoteFile = manager.resolveFile(createConnectionString(remoteHost, username, password, remoteFilePath));
                    logger.info("ftp server connected");
                    remoteFile.copyFrom(local, Selectors.SELECT_SELF);
                    logger.info("ftp upload done");
                    local.close();
                    remoteFile.close();
                    manager.close();
                } catch (FileSystemException e) {
                    logger.error("Got error when upload file: {}", e);
                }
            }
        }).start();


        return ResponseEntity.ok().build();
    }

}
