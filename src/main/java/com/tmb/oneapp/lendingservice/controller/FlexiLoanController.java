package com.tmb.oneapp.lendingservice.controller;

import com.tmb.common.logger.LogAround;
import com.tmb.common.logger.TMBLogger;
import com.tmb.common.model.TmbOneServiceResponse;
import com.tmb.common.model.TmbStatus;
import com.tmb.oneapp.lendingservice.constant.LendingServiceConstant;
import com.tmb.oneapp.lendingservice.constant.ResponseCode;
import com.tmb.oneapp.lendingservice.model.flexiloan.InstantLoanCalUWRequest;
import com.tmb.oneapp.lendingservice.model.flexiloan.InstantLoanCalUWResponse;
import com.tmb.oneapp.lendingservice.service.FlexiLoanCheckApprovedStatusService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.time.Instant;

@RequiredArgsConstructor
@Api(tags = "FlexiLoan")
@RequestMapping("/flexiLoan")
@RestController
public class FlexiLoanController {
    private static final TMBLogger<FlexiLoanController> logger = new TMBLogger<>(FlexiLoanController.class);
    private final FlexiLoanCheckApprovedStatusService flexiLoanCheckApprovedStatusService;
    private static final HttpHeaders responseHeaders = new HttpHeaders();

    @ApiOperation(value = "check approved status")
    @LogAround
    @PostMapping("/approvedStatus")
    public ResponseEntity<TmbOneServiceResponse<InstantLoanCalUWResponse>> approveStatus(@Valid InstantLoanCalUWRequest instantLoanCalUWRequest) {
        HttpHeaders responseHeaders = new HttpHeaders();
        responseHeaders.set(LendingServiceConstant.HEADER_TIMESTAMP, String.valueOf(Instant.now().toEpochMilli()));
        TmbOneServiceResponse<Object> oneServiceResponse = new TmbOneServiceResponse<>();

        TmbOneServiceResponse<InstantLoanCalUWResponse> oneTmbOneServiceResponse = new TmbOneServiceResponse<>();
        try {
            InstantLoanCalUWResponse instantLoanCalUWResponse = flexiLoanCheckApprovedStatusService.checkCalculateUnderwriting(instantLoanCalUWRequest);
            oneTmbOneServiceResponse.setData(instantLoanCalUWResponse);
            oneTmbOneServiceResponse.setStatus(getStatusSuccess());
            setHeader();
            return ResponseEntity.ok().body(oneTmbOneServiceResponse);
        } catch (Exception e) {
            logger.error("error while check under writing: {}", e);
            oneTmbOneServiceResponse.setStatus(getStatusFailed());
            return ResponseEntity.badRequest().headers(responseHeaders).body(oneTmbOneServiceResponse);
        }

    }
    private TmbStatus getStatusFailed() {
        return new TmbStatus(ResponseCode.FAILED.getCode(), ResponseCode.FAILED.getMessage(),
                ResponseCode.FAILED.getService());
    }


    private TmbStatus getStatusSuccess() {
        return new TmbStatus(ResponseCode.SUCCESS.getCode(),
                ResponseCode.SUCCESS.getMessage(),
                ResponseCode.SUCCESS.getService(), ResponseCode.SUCCESS.getMessage());
    }

    private void setHeader() {
        responseHeaders.set(LendingServiceConstant.HEADER_TIMESTAMP, String.valueOf(Instant.now().toEpochMilli()));
    }

}
