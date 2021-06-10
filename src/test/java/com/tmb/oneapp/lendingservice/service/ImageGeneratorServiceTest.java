package com.tmb.oneapp.lendingservice.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.tmb.oneapp.lendingservice.model.instantloancreation.LOCRequest;
import org.apache.fop.apps.FopFactory;
import org.apache.fop.apps.FopFactoryBuilder;
import org.apache.fop.configuration.ConfigurationException;
import org.apache.fop.configuration.DefaultConfigurationBuilder;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import org.mockito.MockitoAnnotations;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.xml.sax.SAXException;

import javax.xml.transform.TransformerFactory;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;

@RunWith(JUnit4.class)
public class ImageGeneratorServiceTest {

    private ImageGeneratorService imageGeneratorService;

    public FopFactory getFopFactory() throws IOException, ConfigurationException, SAXException {
        String[] files = new String[]{"tmblogo.png", "InstantLoanNCBConsentTH.xsl", "DBOzoneX.ttf", "DBOzoneX-Bold.ttf", "DBOzoneX-Italic.ttf", "fop-config.xml"};
        new File("./fop").mkdir();
        for (String file : files) {

            Resource configResource = new ClassPathResource("/fop/" + file);
            InputStream inputStream = configResource.getInputStream();
            Files.copy(inputStream, new File("./fop/" + file).toPath(), StandardCopyOption.REPLACE_EXISTING);
        }

        File fopConfigFile = new File("./fop/fop-config.xml");
        File baseFolder = new File(fopConfigFile.getParent());
        DefaultConfigurationBuilder cfgBuilder = new DefaultConfigurationBuilder();
        org.apache.fop.configuration.Configuration cfg = cfgBuilder.buildFromFile(fopConfigFile);
        FopFactoryBuilder builder = new FopFactoryBuilder(baseFolder.toURI()).setConfiguration(cfg);
        return builder.build();
    }

    @BeforeEach
    void setUp() throws IOException, SAXException, ConfigurationException {
        MockitoAnnotations.initMocks(this);

        FopFactory fopFactory = getFopFactory();
        imageGeneratorService = new ImageGeneratorService(new ObjectMapper(), fopFactory, TransformerFactory.newInstance());
    }

    @Test
    void genImage() {
        LOCRequest request = new LOCRequest();
        request.setCrmId("123");
        request.setAppRefNo("abc");
        request.setConsentbyCustomer("Test");
        request.setNCBCustName("Test");
        request.setNcbid("abc123");
        request.setNCBMobileNo("092xxxxxxx");
        request.setProductName("C2Go");
        request.setNCBDateofbirth("20-may-1980");
        request.setNCBReferenceID("01AR022");
        String imagePath = imageGeneratorService.generateLOCImage(request);
        Assertions.assertNotNull(imagePath);
    }


}
