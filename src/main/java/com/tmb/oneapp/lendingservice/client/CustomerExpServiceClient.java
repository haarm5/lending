package com.tmb.oneapp.lendingservice.client;

import com.tmb.common.model.TmbOneServiceResponse;
import com.tmb.oneapp.lendingservice.model.account.AccountSaving;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;

@FeignClient(name = "customer-exp-service", url = "${customers-exp-service.url}")
public interface CustomerExpServiceClient {
    @GetMapping(value = "${customers-exp-service.account-list.endpoint}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    TmbOneServiceResponse<AccountSaving> getAccountList(@RequestHeader(value = "X-Correlation-ID", required = true) String correlationId,@RequestHeader(value = "X-CRMID", required = true) final String crmID);
}
