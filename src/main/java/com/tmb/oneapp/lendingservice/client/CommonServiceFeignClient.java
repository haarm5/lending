package com.tmb.oneapp.lendingservice.client;

import com.tmb.common.model.TmbOneServiceResponse;
import com.tmb.oneapp.lendingservice.constant.LendingServiceConstant;
import com.tmb.oneapp.lendingservice.model.ProductConfig;
import com.tmb.oneapp.lendingservice.model.config.LendingModuleConfig;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;

import java.util.List;

@FeignClient(name = "${feign.common.service.name}", url = "${feign.common.service.url}")
public interface CommonServiceFeignClient {

    @GetMapping(value = "/apis/common/fetch/product-config")
    ResponseEntity<TmbOneServiceResponse<List<ProductConfig>>> getProductConfig(
            @RequestHeader(value = LendingServiceConstant.HEADER_CORRELATION_ID) String correlationID);

    @GetMapping(value = "/apis/common/internal/common/config")
    TmbOneServiceResponse<LendingModuleConfig> getCommonConfig(
            @RequestHeader(value = LendingServiceConstant.HEADER_CORRELATION_ID) String correlationID, @RequestHeader(value = "search") String search);
}