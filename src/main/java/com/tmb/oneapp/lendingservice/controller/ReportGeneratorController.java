package com.tmb.oneapp.lendingservice.controller;

import com.tmb.common.exception.model.TMBCommonException;
import com.tmb.common.logger.LogAround;
import com.tmb.common.model.TmbOneServiceResponse;
import com.tmb.common.model.TmbStatus;
import com.tmb.common.util.TMBUtils;
import com.tmb.oneapp.lendingservice.constant.LendingServiceConstant;
import com.tmb.oneapp.lendingservice.constant.ResponseCode;
import com.tmb.oneapp.lendingservice.model.eapp.ReportGeneratorRequest;
import com.tmb.oneapp.lendingservice.model.eapp.ReportGeneratorResponse;
import com.tmb.oneapp.lendingservice.service.ReportGeneratorService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RequiredArgsConstructor
@Api(tags = "ReportGeneratorController")
@RequestMapping("/report")
@RestController
public class ReportGeneratorController {

    private final ReportGeneratorService reportGeneratorService;

    @ApiOperation("Generate E-App Reports")
    @PostMapping(value = "/generate")
    @LogAround
    public ResponseEntity<TmbOneServiceResponse<ReportGeneratorResponse>> generateReport(
            @ApiParam(value = LendingServiceConstant.HEADER_CORRELATION_ID, defaultValue = "32fbd3b2-3f97-4a89-ar39-b4f628fbc8da", required = true)
            @Valid @RequestHeader(LendingServiceConstant.HEADER_CORRELATION_ID) String correlationId,
            @ApiParam(value = LendingServiceConstant.HEADER_X_CRMID, defaultValue = "001100000000000000000018593707", required = true)
            @Valid @RequestHeader(LendingServiceConstant.HEADER_X_CRMID) String crmId,
            @ApiParam(value = LendingServiceConstant.HEADER_ACCOUNT_ID, defaultValue = "00050078680019000079", required = true)
            @Valid @RequestHeader(LendingServiceConstant.HEADER_ACCOUNT_ID) String accountId,
            @RequestBody ReportGeneratorRequest request) throws TMBCommonException {
        TmbOneServiceResponse<ReportGeneratorResponse> response = new TmbOneServiceResponse<>();

        try {
            ReportGeneratorResponse reportGeneratorResponse = reportGeneratorService.generateEAppReport(accountId, request, correlationId, crmId);
            response.setData(reportGeneratorResponse);
            response.setStatus(new TmbStatus(ResponseCode.SUCCESS.getCode(),
                    ResponseCode.SUCCESS.getMessage(), ResponseCode.SUCCESS.getService(), ResponseCode.SUCCESS.getDesc()));

            return ResponseEntity.ok()
                    .headers(TMBUtils.getResponseHeaders())
                    .body(response);
        } catch (Exception e) {
            throw new TMBCommonException(ResponseCode.FAILED.getCode(), ResponseCode.FAILED.getMessage(), ResponseCode.FAILED.getService(), HttpStatus.INTERNAL_SERVER_ERROR, e);
        }
    }

}
