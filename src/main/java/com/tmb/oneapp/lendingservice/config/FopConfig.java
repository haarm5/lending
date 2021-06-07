package com.tmb.oneapp.lendingservice.config;

import org.apache.fop.apps.FopFactory;
import org.apache.fop.apps.FopFactoryBuilder;
import org.apache.fop.configuration.ConfigurationException;
import org.apache.fop.configuration.DefaultConfigurationBuilder;
import org.springframework.context.ResourceLoaderAware;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.xml.sax.SAXException;

import javax.xml.transform.TransformerFactory;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;


@Configuration
public class FopConfig implements ResourceLoaderAware {

    private ResourceLoader resourceLoader;
    @Bean
    public FopFactory getFopFactory() throws IOException, ConfigurationException, SAXException {

        String[] files = new String[]{"DBOzoneX.ttf", "DBOzoneX-Bold.ttf", "DBOzoneX-Italic.ttf", "fop-config.xml"};
        File f1 = new File("./fop2");
        f1.mkdir();

        for (String file : files) {
            Resource configResource = this.resourceLoader.getResource("classpath:fop/" + file);

            InputStream inputStream = configResource.getInputStream();

            Files.copy(inputStream, new File("./fop2/" + file).toPath(), StandardCopyOption.REPLACE_EXISTING);
        }

        File fopConfigFile = new File("./fop2/fop-config.xml");
        File baseFolder = new File(fopConfigFile.getParent());

        DefaultConfigurationBuilder cfgBuilder = new DefaultConfigurationBuilder();

        org.apache.fop.configuration.Configuration cfg = cfgBuilder.buildFromFile(fopConfigFile);


        FopFactoryBuilder builder = new FopFactoryBuilder(baseFolder.toURI()).setConfiguration(cfg);
        return builder.build();
    }

    @Bean
    public TransformerFactory getTransformerFactory() {
        return TransformerFactory.newInstance();
    }

    @Override
    public void setResourceLoader(ResourceLoader rl) {
        this.resourceLoader = rl;
    }
}
