package com.tmb.oneapp.lendingservice.config;

import org.apache.fop.apps.FopFactory;
import org.apache.fop.apps.FopFactoryBuilder;
import org.apache.fop.configuration.ConfigurationException;
import org.apache.fop.configuration.DefaultConfigurationBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.xml.sax.SAXException;

import javax.xml.transform.TransformerFactory;
import java.io.File;
import java.io.IOException;


@Configuration
public class FopConfig {

    @Bean
    public FopFactory getFopFactory() throws IOException, ConfigurationException, SAXException {

        File fopConfigFile = new File("./fop/fop-config.xml");
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
}
