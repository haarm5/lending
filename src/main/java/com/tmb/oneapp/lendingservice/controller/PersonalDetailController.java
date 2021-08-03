package com.tmb.oneapp.lendingservice.controller;

import com.tmb.common.logger.LogAround;
import com.tmb.common.logger.TMBLogger;
import com.tmb.common.model.TmbOneServiceResponse;
import com.tmb.common.model.TmbStatus;
import com.tmb.oneapp.lendingservice.constant.LendingServiceConstant;
import com.tmb.oneapp.lendingservice.constant.ResponseCode;
import com.tmb.oneapp.lendingservice.model.personal.PersonalDetailRequest;
import com.tmb.oneapp.lendingservice.model.personal.PersonalDetailResponse;
import com.tmb.oneapp.lendingservice.model.personal.PersonalDetailSaveInfoRequest;
import com.tmb.oneapp.lendingservice.service.PersonalDetailSaveInfoService;
import com.tmb.oneapp.lendingservice.service.PersonalDetailService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.time.Instant;

@RequiredArgsConstructor
@Api(tags = "loanSubmission")
@RequestMapping("/loanSubmission")
@RestController
public class PersonalDetailController {
    private static final TMBLogger<PersonalDetailController> logger = new TMBLogger<>(PersonalDetailController.class);
    private final PersonalDetailService personalDetailService;
    private final PersonalDetailSaveInfoService updatePersonalDetail;
    private static final HttpHeaders responseHeaders = new HttpHeaders();

    @ApiOperation(value = "get personal detail")
    @LogAround
    @GetMapping("/personalDetail")
    @ApiImplicitParams({
            @ApiImplicitParam(name = LendingServiceConstant.HEADER_X_CRMID, defaultValue = "001100000000000000000018593707", required = true, dataType = "string", paramType = "header") })
    public ResponseEntity<TmbOneServiceResponse<PersonalDetailResponse>> getPersonalDetail(@Valid @RequestHeader(name = LendingServiceConstant.HEADER_X_CRMID) String crmId,
                                                                                           @Valid PersonalDetailRequest request) {
        responseHeaders.set(LendingServiceConstant.HEADER_TIMESTAMP, String.valueOf(Instant.now().toEpochMilli()));
        TmbOneServiceResponse<PersonalDetailResponse> oneTmbOneServiceResponse = new TmbOneServiceResponse<>();
        try {
            PersonalDetailResponse personalDetailResponse = personalDetailService.getPersonalDetail(crmId,request.getCaId());
            oneTmbOneServiceResponse.setData(personalDetailResponse);
            oneTmbOneServiceResponse.setStatus(getStatus(ResponseCode.SUCCESS.getCode(),ResponseCode.SUCCESS.getService(),ResponseCode.SUCCESS.getMessage(),""));
            setHeader();
            return ResponseEntity.ok().body(oneTmbOneServiceResponse);
        } catch (Exception e) {
            logger.error("error while get personal detail: {}", e);
            oneTmbOneServiceResponse.setStatus(getStatus(ResponseCode.FAILED.getCode(),ResponseCode.FAILED.getService(),ResponseCode.FAILED.getMessage(),e.getMessage()));
            return ResponseEntity.badRequest().headers(responseHeaders).body(oneTmbOneServiceResponse);
        }

    }

    @ApiOperation(value = "update personal detail info")
    @LogAround
    @PostMapping("/savePersonalDetail")
    public ResponseEntity<TmbOneServiceResponse<com.tmb.common.model.legacy.rsl.ws.individual.update.response.ResponseIndividual>> updatePersonalDetail(@Valid PersonalDetailRequest request,
                                                                                          @Valid @RequestBody PersonalDetailSaveInfoRequest personalDetailReg) {
        responseHeaders.set(LendingServiceConstant.HEADER_TIMESTAMP, String.valueOf(Instant.now().toEpochMilli()));
        TmbOneServiceResponse<com.tmb.common.model.legacy.rsl.ws.individual.update.response.ResponseIndividual> oneTmbOneServiceResponse = new TmbOneServiceResponse<>();

        try {
            com.tmb.common.model.legacy.rsl.ws.individual.update.response.ResponseIndividual responseIndividual = updatePersonalDetail.updateCustomerInfo(request.getCaId(),personalDetailReg);
            oneTmbOneServiceResponse.setData(responseIndividual);
            oneTmbOneServiceResponse.setStatus(getStatus(ResponseCode.SUCCESS.getCode(),ResponseCode.SUCCESS.getService(),ResponseCode.SUCCESS.getMessage(),""));
            setHeader();
            return ResponseEntity.ok().body(oneTmbOneServiceResponse);
        } catch (Exception e) {
            logger.error("error while update personal detail info: {}", e);
            oneTmbOneServiceResponse.setStatus(getStatus(ResponseCode.FAILED.getCode(),ResponseCode.FAILED.getService(),ResponseCode.FAILED.getMessage(),e.getMessage()));
            return ResponseEntity.badRequest().headers(responseHeaders).body(oneTmbOneServiceResponse);
        }

    }

    private TmbStatus getStatus(String responseCode,String responseService,String responseMessage, String error) {
        return new TmbStatus(responseCode, responseMessage,
                responseService,error);
    }

    private void setHeader() {
        responseHeaders.set(LendingServiceConstant.HEADER_TIMESTAMP, String.valueOf(Instant.now().toEpochMilli()));
    }
}
