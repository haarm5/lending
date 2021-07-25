package com.tmb.oneapp.lendingservice.controller;

import com.tmb.common.logger.LogAround;
import com.tmb.common.logger.TMBLogger;
import com.tmb.common.model.TmbOneServiceResponse;
import com.tmb.common.model.TmbStatus;
import com.tmb.common.util.TMBUtils;
import com.tmb.oneapp.lendingservice.constant.LendingServiceConstant;
import com.tmb.oneapp.lendingservice.constant.ResponseCode;
import com.tmb.oneapp.lendingservice.model.flexiloan.InstantLoanCalUWRequest;
import com.tmb.oneapp.lendingservice.model.flexiloan.InstantLoanCalUWResponse;
import com.tmb.oneapp.lendingservice.model.flexiloan.SubmissionInfoRequest;
import com.tmb.oneapp.lendingservice.model.flexiloan.SubmissionInfoResponse;
import com.tmb.oneapp.lendingservice.service.FlexiLoanCheckApprovedStatusService;
import com.tmb.oneapp.lendingservice.service.FlexiLoanSubmitService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.time.Instant;

@RequiredArgsConstructor
@Api(tags = "FlexiLoan")
@RequestMapping("/flexiLoan")
@RestController
public class FlexiLoanController {
    private static final TMBLogger<FlexiLoanController> logger = new TMBLogger<>(FlexiLoanController.class);
    private final FlexiLoanCheckApprovedStatusService flexiLoanCheckApprovedStatusService;
    private final FlexiLoanSubmitService flexiLoanSubmitService;
    private static final HttpHeaders responseHeaders = new HttpHeaders();

    @ApiOperation(value = "check approved status")
    @LogAround
    @GetMapping("/approvedStatus")
    public ResponseEntity<TmbOneServiceResponse<InstantLoanCalUWResponse>> approveStatus(@Valid InstantLoanCalUWRequest instantLoanCalUWRequest) {
        responseHeaders.set(LendingServiceConstant.HEADER_TIMESTAMP, String.valueOf(Instant.now().toEpochMilli()));
        TmbOneServiceResponse<InstantLoanCalUWResponse> oneTmbOneServiceResponse = new TmbOneServiceResponse<>();
        try {
            InstantLoanCalUWResponse instantLoanCalUWResponse = flexiLoanCheckApprovedStatusService.checkCalculateUnderwriting(instantLoanCalUWRequest);
            oneTmbOneServiceResponse.setData(instantLoanCalUWResponse);
            oneTmbOneServiceResponse.setStatus(getStatusSuccess());
            setHeader();
            return ResponseEntity.ok().body(oneTmbOneServiceResponse);
        } catch (Exception e) {
            logger.error("error while check under writing: {}", e);
            oneTmbOneServiceResponse.setStatus(getStatusFailed(e.getMessage()));
        }
        return ResponseEntity.ok().headers(TMBUtils.getResponseHeaders()).body(oneTmbOneServiceResponse);


    }


    @LogAround
    @ApiOperation("Flexi loan submission info")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "X-Correlation-ID", defaultValue = "32fbd3b2-3f97-4a89-ae39-b4f628fbc8da", required = true, dataType = "string", paramType = "header", example = "32fbd3b2-3f97-4a89-ae39-b4f628fbc8da")
    })
    @GetMapping(value = "/submissionInfo", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<TmbOneServiceResponse<SubmissionInfoResponse>> getSubmissionInfo(@Valid @RequestHeader(LendingServiceConstant.HEADER_CORRELATION_ID) String correlationId,
                                                                                           @Valid SubmissionInfoRequest request) {

        setHeader();
        logger.info("Get flexi loan submission info for correlation id: {}", correlationId);

        TmbOneServiceResponse<SubmissionInfoResponse> oneTmbOneServiceResponse = new TmbOneServiceResponse<>();

        try {
            SubmissionInfoResponse response = flexiLoanSubmitService.getSubmissionInfo(request);
            oneTmbOneServiceResponse.setData(response);
            oneTmbOneServiceResponse.setStatus(getStatusSuccess());
            return ResponseEntity.ok().body(oneTmbOneServiceResponse);

        } catch (Exception e) {
            logger.error("Error while submission info : {}", e);
            oneTmbOneServiceResponse.setStatus(getStatusFailed(e.getMessage()));
            return ResponseEntity.badRequest().headers(responseHeaders).body(oneTmbOneServiceResponse);
        }

    }

    private TmbStatus getStatusFailed(String error) {
        return new TmbStatus(ResponseCode.FAILED.getCode(), ResponseCode.FAILED.getMessage(),
                ResponseCode.FAILED.getService(),error);
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
