package com.tmb.oneapp.lendingservice.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.tmb.oneapp.lendingservice.client.FTPServerLOCClient;
import com.tmb.oneapp.lendingservice.model.instantloancreation.LOCRequest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@RunWith(JUnit4.class)
public class ImageGeneratorServiceTest {

    @Mock
    FTPServerLOCClient ftpServerLOCClient;
    ObjectMapper mapper;
    ImageGeneratorService imageGeneratorService;

    @BeforeEach
    void setUp(){
        MockitoAnnotations.initMocks(this);
        mapper = new ObjectMapper();
        imageGeneratorService = new ImageGeneratorService(mapper,ftpServerLOCClient);
    }

    private LOCRequest constructRequestForLOCCompleteImage() {

        LOCRequest request = new LOCRequest();
        request.setCrmId("0998797398780938");
        request.setNcbid("123456");
        request.setNCBMobileNo("0787349887");
        request.setAppRefNo("123456");
        request.setConsentbyCustomer("Y");
        request.setNCBCustName("Roopa");
        request.setNCBDateofbirth("12/12/1990");
        return request;

    }

}
