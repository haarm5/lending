package com.tmb.oneapp.lendingservice.config;

import com.tmb.common.logger.TMBLogger;
import org.springframework.context.ResourceLoaderAware;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;

@Component
public class FopComponent implements ResourceLoaderAware {

    private ResourceLoader resourceLoader;
    private static final TMBLogger<FopComponent> logger = new TMBLogger<>(FopComponent.class);

    @PostConstruct
    public void setupFopFiles() throws IOException {
        String[] files = new String[]{"tmblogo.png", "InstantLoanNCBConsentTH.xsl", "DBOzoneX.ttf", "DBOzoneX-Bold.ttf", "DBOzoneX-Italic.ttf", "fop-config.xml"};
        new File("./fop").mkdir();
        for (String file : files) {
            Resource configResource = this.resourceLoader.getResource("classpath:fop/" + file);
            InputStream inputStream = configResource.getInputStream();
            Files.copy(inputStream, new File("./fop/" + file).toPath(), StandardCopyOption.REPLACE_EXISTING);
            logger.info("done preparing fop file:"+file);
        }
        logger.info("finished preparing fop files");
    }

    @Override
    public void setResourceLoader(ResourceLoader resourceLoader) {
        this.resourceLoader = resourceLoader;
    }
}
