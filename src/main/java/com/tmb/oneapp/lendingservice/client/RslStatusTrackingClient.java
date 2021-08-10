package com.tmb.oneapp.lendingservice.client;

import com.tmb.common.logger.TMBLogger;
import com.tmb.oneapp.lendingservice.constant.LendingServiceConstant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

/**
 * RslStatusTrackingClient class will get RSL Status for Customer
 */
@Service
public class RslStatusTrackingClient {
    private static final TMBLogger<RslStatusTrackingClient> logger = new TMBLogger<>(RslStatusTrackingClient.class);

    @Value("${rsl.status.tracking.url}")
    private String rslStatusTrackingUrl;

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
            RestTemplate restTemplate = new RestTemplate();
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
            headers.add(LendingServiceConstant.HEADER_SOAP_ACTION, "");

            HttpEntity<String> request = new HttpEntity<>(xml, headers);

            return restTemplate.exchange(rslStatusTrackingUrl, HttpMethod.POST, request, String.class);
        } catch (Exception e) {
            logger.error("getRslStatusTracking method Error(Data Not Found) : {} ", e);
            return null;
        }
    }
}