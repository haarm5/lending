package com.tmb.oneapp.lendingservice.util;

import com.tmb.common.logger.TMBLogger;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.util.Base64;

import static com.tmb.oneapp.lendingservice.constant.LendingServiceConstant.SEPARATOR;

public class FileConvertorUtils {
    private static final TMBLogger<FileConvertorUtils> logger = new TMBLogger<>(FileConvertorUtils.class);

    private FileConvertorUtils() { }

    public static String generateFileFromBase64(String dir, String fileName, String base64) throws IOException {
        byte[] decoder = Base64.getDecoder().decode(base64);
        File outputDir = new File(dir);
        //Make directory
        if (Files.notExists(outputDir.toPath())) {
            org.apache.commons.io.FileUtils.forceMkdir(outputDir);
        }

        String filePath = outputDir + SEPARATOR + fileName;

        try (FileOutputStream fos = new FileOutputStream(filePath)) {
            fos.write(decoder);
        }
        logger.info("Generate file from base64 successes: {}", filePath);

        return filePath;
    }
}
