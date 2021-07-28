package com.tmb.oneapp.lendingservice.controller;

import com.tmb.common.logger.LogAround;
import com.tmb.common.logger.TMBLogger;
import com.tmb.common.model.TmbOneServiceResponse;
import com.tmb.common.model.TmbStatus;
import com.tmb.common.model.legacy.rsl.ws.application.save.response.ResponseApplication;
import com.tmb.oneapp.lendingservice.constant.LendingServiceConstant;
import com.tmb.oneapp.lendingservice.constant.ResponseCode;
import com.tmb.oneapp.lendingservice.model.loanonline.IncomeInfo;
import com.tmb.oneapp.lendingservice.model.loanonline.LoanSubmissionCreateApplicationReq;
import com.tmb.oneapp.lendingservice.service.LoanOnlineSubmissionCheckWaiveDocService;
import com.tmb.oneapp.lendingservice.service.LoanSubmissionCreateApplicationService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.time.Instant;

@RequiredArgsConstructor
@Api(tags = "LoanOnlineSubmission")
@RequestMapping("/loanOnlineSubmission")
@RestController
public class LoanOnlineSubmissionController {
    private static final TMBLogger<LoanOnlineSubmissionController> logger = new TMBLogger<>(LoanOnlineSubmissionController.class);
    private final LoanSubmissionCreateApplicationService loanSubmissionCreateApplicationService;
    private final LoanOnlineSubmissionCheckWaiveDocService loanOnlineSubmissionCheckWaiveDocService;
    private static final HttpHeaders responseHeaders = new HttpHeaders();


    @GetMapping("/getIncomeInfo")
    @LogAround
    @ApiOperation(value = "get income info")
    public ResponseEntity<TmbOneServiceResponse<IncomeInfo>> getIncomeInfo(@RequestHeader(name = LendingServiceConstant.HEADER_X_CRMID, required = true) String crmId) {
        TmbOneServiceResponse<IncomeInfo> oneTmbOneServiceResponse = new TmbOneServiceResponse<>();
        try {
            oneTmbOneServiceResponse.setData(loanOnlineSubmissionCheckWaiveDocService.getIncomeInfoByRmId(crmId));
            oneTmbOneServiceResponse.setStatus(getStatus(ResponseCode.SUCCESS.getCode(), ResponseCode.SUCCESS.getService(), ResponseCode.SUCCESS.getMessage(), ""));
            setHeader();
            return ResponseEntity.ok().headers(responseHeaders).body(oneTmbOneServiceResponse);
        } catch (Exception e) {
            logger.error("error while check waive doc:", e);
            oneTmbOneServiceResponse.setStatus(getStatus(ResponseCode.FAILED.getCode(), ResponseCode.FAILED.getService(), ResponseCode.FAILED.getMessage(), e.getMessage()));
            return ResponseEntity.badRequest().headers(responseHeaders).body(oneTmbOneServiceResponse);
        }
    }

    @ApiOperation(value = "create application")
    @LogAround
    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<TmbOneServiceResponse<ResponseApplication>> createApplication(@Valid @RequestHeader(name = LendingServiceConstant.HEADER_X_CRMID) String crmId,
                                                                                        @Valid @RequestBody LoanSubmissionCreateApplicationReq request) {

        TmbOneServiceResponse<ResponseApplication> oneTmbOneServiceResponse = new TmbOneServiceResponse<>();
        try {
            oneTmbOneServiceResponse.setData(loanSubmissionCreateApplicationService.createApplication(request, crmId));
            oneTmbOneServiceResponse.setStatus(getStatus(ResponseCode.SUCCESS.getCode(), ResponseCode.SUCCESS.getService(), ResponseCode.SUCCESS.getMessage(), ""));
            setHeader();
            return ResponseEntity.ok().headers(responseHeaders).body(oneTmbOneServiceResponse);
        } catch (Exception e) {
            logger.error("error while create application: {}", e);
            oneTmbOneServiceResponse.setStatus(getStatus(ResponseCode.FAILED.getCode(), ResponseCode.FAILED.getService(), ResponseCode.FAILED.getMessage(), e.getMessage()));
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
