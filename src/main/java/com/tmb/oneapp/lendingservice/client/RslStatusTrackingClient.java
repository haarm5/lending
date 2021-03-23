package com.tmb.oneapp.lendingservice.client;

import com.tmb.common.logger.TMBLogger;
import com.tmb.oneapp.lendingservice.constant.LendingServiceConstant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

/**
 * RslStatusTrackingClient class will get RSL Status for Customer
 */
@Service
public class RslStatusTrackingClient {
    private static final TMBLogger<RslStatusTrackingClient> logger = new TMBLogger<>(RslStatusTrackingClient.class);

    /**
     * Constructor
     */
    @Autowired
    public RslStatusTrackingClient() {
        //Empty Constructor
    }

    /**
     * This method for get rsl status tracking for RSL API(TMB)
     *
     * @param xml String
     *
     * @return String
     */
    public ResponseEntity<String> getRslStatusTracking(String xml) {
        try {
            String url = "http://10.209.23.234:9081/RSLWS/services/LoanStatusTracking";    //NOSONAR lightweight logging

            RestTemplate restTemplate = new RestTemplate();
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
            headers.add(LendingServiceConstant.HEADER_SOAP_ACTION, "");

            HttpEntity<String> request = new HttpEntity<>(xml, headers);

            return restTemplate.exchange(url, HttpMethod.POST, request, String.class);
        } catch (Exception e) {
            logger.error("getRslStatusTracking method Error(Data Not Found) : {} ", e);
            return null;
        }
    }
}