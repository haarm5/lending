package com.tmb.oneapp.lendingservice.controller;

import com.tmb.common.logger.LogAround;
import com.tmb.common.logger.TMBLogger;
import com.tmb.common.model.TmbOneServiceResponse;
import com.tmb.common.model.TmbStatus;
import com.tmb.oneapp.lendingservice.constant.LendingServiceConstant;
import com.tmb.oneapp.lendingservice.constant.ResponseCode;
import com.tmb.oneapp.lendingservice.model.flexiloan.LoanCalculatorRequest;
import com.tmb.oneapp.lendingservice.model.flexiloan.LoanCalculatorResponse;
import com.tmb.oneapp.lendingservice.service.LoanCalculatorService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.time.Instant;

@RequiredArgsConstructor
@RequestMapping("/loan")
@RestController
@Api(tags = "Loan Controller")
public class LoanCalculatorController {
    private static final TMBLogger<LoanCalculatorController> logger = new TMBLogger<>(LoanCalculatorController.class);
    private final LoanCalculatorService loanCalculatorService;
    private static final HttpHeaders responseHeaders = new HttpHeaders();


    @ApiOperation(value = "get preload loan calculator")
    @LogAround
    @GetMapping("/preloadLoanCalculator")
    public ResponseEntity<TmbOneServiceResponse<LoanCalculatorResponse>> getPreloadLoanCalculator(@Valid LoanCalculatorRequest request) {
        responseHeaders.set(LendingServiceConstant.HEADER_TIMESTAMP, String.valueOf(Instant.now().toEpochMilli()));
        TmbOneServiceResponse<LoanCalculatorResponse> oneTmbOneServiceResponse = new TmbOneServiceResponse<>();
        try {
            LoanCalculatorResponse loanCalculatorResponse = loanCalculatorService.getPreloadLoanCalculator(request.getCaId(),request.getProduct());
            oneTmbOneServiceResponse.setData(loanCalculatorResponse);
            oneTmbOneServiceResponse.setStatus(getStatus(ResponseCode.SUCCESS.getCode(),ResponseCode.SUCCESS.getService(),ResponseCode.SUCCESS.getMessage(),""));
            setHeader();
            return ResponseEntity.ok().body(oneTmbOneServiceResponse);
        } catch (Exception e) {
            logger.error("error while get personal detail: {}", e);
            oneTmbOneServiceResponse.setStatus(getStatus(ResponseCode.FAILED.getCode(),ResponseCode.FAILED.getService(),ResponseCode.FAILED.getMessage(),e.getMessage()));
            return ResponseEntity.badRequest().headers(responseHeaders).body(oneTmbOneServiceResponse);
        }

    }

    private TmbStatus getStatus(String responseCode, String responseService, String responseMessage, String error) {
        return new TmbStatus(responseCode, responseMessage,
                responseService, error);
    }

    private void setHeader() {
        responseHeaders.set(LendingServiceConstant.HEADER_TIMESTAMP, String.valueOf(Instant.now().toEpochMilli()));
    }

}
