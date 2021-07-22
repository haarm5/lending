package com.tmb.oneapp.lendingservice.controller;

import com.tmb.common.logger.LogAround;
import com.tmb.common.logger.TMBLogger;
import com.tmb.common.model.TmbOneServiceResponse;
import com.tmb.common.model.TmbStatus;
import com.tmb.oneapp.lendingservice.constant.LendingServiceConstant;
import com.tmb.oneapp.lendingservice.constant.ResponseCode;
import com.tmb.oneapp.lendingservice.model.flexiloan.InstantLoanCalUWRequest;
import com.tmb.oneapp.lendingservice.model.flexiloan.InstantLoanCalUWResponse;
import com.tmb.oneapp.lendingservice.model.personal.PersonalDetailRequest;
import com.tmb.oneapp.lendingservice.model.personal.PersonalDetailResponse;
import com.tmb.oneapp.lendingservice.service.FlexiLoanSubmitService;
import com.tmb.oneapp.lendingservice.service.PersonalDetailService;
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
@Api(tags = "loanSubmission")
@RequestMapping("/loanSubmission")
@RestController
public class PersonalDetailController {
    private static final TMBLogger<PersonalDetailController> logger = new TMBLogger<>(PersonalDetailController.class);
    private final PersonalDetailService personalDetailService;
    private static final HttpHeaders responseHeaders = new HttpHeaders();

    @ApiOperation(value = "get personal detail")
    @LogAround
    @GetMapping("/personalDetail")
    public ResponseEntity<TmbOneServiceResponse<PersonalDetailResponse>> getPersonalDetail(@Valid PersonalDetailRequest request) {
        responseHeaders.set(LendingServiceConstant.HEADER_TIMESTAMP, String.valueOf(Instant.now().toEpochMilli()));
        TmbOneServiceResponse<PersonalDetailResponse> oneTmbOneServiceResponse = new TmbOneServiceResponse<>();
        try {
            PersonalDetailResponse personalDetailResponse = personalDetailService.getPersonalDetail(request.getCaId());
            oneTmbOneServiceResponse.setData(personalDetailResponse);
            oneTmbOneServiceResponse.setStatus(getStatusSuccess());
            setHeader();
            return ResponseEntity.ok().body(oneTmbOneServiceResponse);
        } catch (Exception e) {
            logger.error("error while get personal detail: {}", e);
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
