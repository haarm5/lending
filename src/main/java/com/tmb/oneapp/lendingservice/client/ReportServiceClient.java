package com.tmb.oneapp.lendingservice.client;

import com.tmb.common.model.TmbOneServiceResponse;
import com.tmb.oneapp.lendingservice.constant.LendingServiceConstant;
import com.tmb.oneapp.lendingservice.model.report.ReportGenerateClientRequest;
import com.tmb.oneapp.lendingservice.model.report.ReportGenerateClientResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;

@FeignClient(name = "${feign.report.service.name}", url = "${feign.report.service.url}")
public interface ReportServiceClient {

    @GetMapping(value = "${feign.report.service.generate.endpoint}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    TmbOneServiceResponse<ReportGenerateClientResponse> generateReport(@RequestHeader(value = LendingServiceConstant.HEADER_X_CRMID) final String crmId,
                                                                       @RequestBody ReportGenerateClientRequest request);

}
