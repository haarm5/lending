package com.tmb.oneapp.lendingservice.client;

import com.tmb.common.model.TmbOneServiceResponse;
import com.tmb.oneapp.lendingservice.constant.LendingServiceConstant;
import com.tmb.oneapp.lendingservice.model.loanonline.EAppResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "product-exp-service", url = "${product-exp-service.url}")
public interface ProductExpServiceClient {
    @GetMapping(value = "${product-exp-service.loan-online-submission-e-app.endpoint}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    TmbOneServiceResponse<EAppResponse> getLoanOnlineSubmissionEApp(@RequestHeader(value = LendingServiceConstant.HEADER_X_CRMID) final String crmId,
                                                                    @RequestParam(value = "caId") String caId);

}
