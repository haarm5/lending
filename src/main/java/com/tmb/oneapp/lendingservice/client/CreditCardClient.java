package com.tmb.oneapp.lendingservice.client;

import com.tmb.oneapp.lendingservice.constant.LendingServiceConstant;
import com.tmb.oneapp.lendingservice.model.creditcard.FetchCardResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@FeignClient(name = "${feign.creditcard.service.name}", url = "${feign.creditcard.service.url}")
public interface CreditCardClient {

    @GetMapping(value = "${feign.creditcard.service.detail.endpoint}")
    ResponseEntity<FetchCardResponse> getCreditCardDetails(
            @RequestHeader(LendingServiceConstant.HEADER_CORRELATION_ID) String correlationId,
            @PathVariable(value = "ACCOUNT_ID") String accountId);
}