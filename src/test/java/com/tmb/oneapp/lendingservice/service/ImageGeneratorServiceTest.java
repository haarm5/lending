package com.tmb.oneapp.lendingservice.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.tmb.oneapp.lendingservice.model.instantloancreation.LOCRequest;
import org.apache.fop.apps.FopFactory;
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

@RunWith(JUnit4.class)
public class ImageGeneratorServiceTest {

    private ImageGeneratorService imageGeneratorService;

    @BeforeEach
    void setUp() throws IOException, SAXException {
        MockitoAnnotations.initMocks(this);
        Resource resource = new ClassPathResource("/fop/fop-config.xml");
        InputStream input = resource.getInputStream();
        FopFactory fopFactory = FopFactory.newInstance(resource.getURI(), input);
        imageGeneratorService = new ImageGeneratorService(new ObjectMapper(), fopFactory, TransformerFactory.newInstance());
    }

    @Test
    void genImage() {
        LOCRequest request = new LOCRequest();
        request.setCrmId("123");
        request.setAppRefNo("abc");
        String imagePath = imageGeneratorService.generateLOCImage(request);
        Assertions.assertNotNull(imagePath);
        new File(imagePath).delete();
    }


}
