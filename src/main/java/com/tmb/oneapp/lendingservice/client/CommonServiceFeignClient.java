package com.tmb.oneapp.lendingservice.client;

import com.tmb.common.model.LovMaster;
import com.tmb.common.model.TmbOneServiceResponse;
import com.tmb.common.model.loan.stagingbar.LoanStagingbar;
import com.tmb.oneapp.lendingservice.constant.LendingServiceConstant;
import com.tmb.oneapp.lendingservice.model.LoanStagingbarRequest;
import com.tmb.oneapp.lendingservice.model.ProductConfig;
import com.tmb.oneapp.lendingservice.model.RslMessage;
import com.tmb.oneapp.lendingservice.model.config.LendingModuleConfig;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@FeignClient(name = "${feign.common.service.name}", url = "${feign.common.service.url}")
public interface CommonServiceFeignClient {

    @GetMapping(value = "/apis/common/fetch/product-config")
    ResponseEntity<TmbOneServiceResponse<List<ProductConfig>>> getProductConfig(
            @RequestHeader(value = LendingServiceConstant.HEADER_CORRELATION_ID) String correlationID);

    @GetMapping(value = "/apis/common/internal/common/config")
    TmbOneServiceResponse<List<LendingModuleConfig>> getCommonConfig(
            @RequestHeader(value = LendingServiceConstant.HEADER_CORRELATION_ID) String correlationID, @RequestParam(value = "search") String search);

    @GetMapping(value = "/apis/common/rsl-message")
    ResponseEntity<TmbOneServiceResponse<RslMessage>> getRslMessage(
            @RequestHeader(value = "app-status") String appStatus, @RequestHeader(value = "loan-type") String loanType);

    @GetMapping(value = "/apis/common/internal/lovmaster")
    TmbOneServiceResponse<List<LovMaster>> getLovmasterConfig(
            @RequestHeader(value = LendingServiceConstant.HEADER_CORRELATION_ID) String correlationId,
            @RequestHeader(value = LendingServiceConstant.HEADER_X_CRMID) String crmId,
            @RequestParam(value = "type") String searchType,
            @RequestParam(value = "lang") String defaultLang);
    
}
