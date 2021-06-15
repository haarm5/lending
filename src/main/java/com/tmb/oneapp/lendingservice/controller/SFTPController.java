package com.tmb.oneapp.lendingservice.controller;


import com.tmb.oneapp.lendingservice.client.SFTPClientImp;
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

    @Autowired
    private SFTPClientImp sftpClientImp;

    private ExecutorService executor = Executors.newSingleThreadExecutor();

    @PostMapping(value = "/normal")
    public ResponseEntity normal() throws IOException {
        String directoryPath = "normal/12345";
        String baseDir = System.getProperty("user.dir");
        sftpClientImp.storeFile(directoryPath, baseDir+File.separator+"fop"+File.separator+"01_210615152223_abc_00110.JPG");
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
                sftpClientImp.storeFile(directoryPath, baseDir+File.separator+"fop"+File.separator+"01_210615152223_abc_00110.JPG");
            } catch (IOException e) {
                e.printStackTrace();
            }
        });

        return ResponseEntity.ok().build();
    }
}
