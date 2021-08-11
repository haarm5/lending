package com.tmb.oneapp.lendingservice.controller;

import com.tmb.common.logger.LogAround;
import com.tmb.common.logger.TMBLogger;
import com.tmb.common.model.TmbOneServiceResponse;
import com.tmb.common.model.TmbStatus;
import com.tmb.oneapp.lendingservice.constant.LendingServiceConstant;
import com.tmb.oneapp.lendingservice.constant.ResponseCode;
import com.tmb.oneapp.lendingservice.model.personal.ChecklistRequest;
import com.tmb.oneapp.lendingservice.model.personal.ChecklistResponse;
import com.tmb.oneapp.lendingservice.service.ChecklistService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.time.Instant;
import java.util.List;

@RequiredArgsConstructor
@Api(tags = "checklist")
@RequestMapping("/loanSubmission")
@RestController
public class ChecklistController {
    private static final TMBLogger<ChecklistController> logger = new TMBLogger<>(ChecklistController.class);
    private final ChecklistService checklistService;
    private static final HttpHeaders responseHeaders = new HttpHeaders();

    @ApiOperation(value = "get personal detail")
    @LogAround
    @GetMapping("/documents")
    @ApiImplicitParams({
            @ApiImplicitParam(name = LendingServiceConstant.HEADER_X_CRMID, defaultValue = "001100000000000000000018593707", required = true, dataType = "string", paramType = "header") })
    public ResponseEntity<TmbOneServiceResponse<List<ChecklistResponse>>> getDocuments(@Valid @RequestHeader(name = LendingServiceConstant.HEADER_X_CRMID) String crmId,
                                                                                      @Valid ChecklistRequest request) {
        responseHeaders.set(LendingServiceConstant.HEADER_TIMESTAMP, String.valueOf(Instant.now().toEpochMilli()));
        TmbOneServiceResponse<List<ChecklistResponse>> oneTmbOneServiceResponse = new TmbOneServiceResponse<>();
        try {
            List<ChecklistResponse> responseChecklist = checklistService.getDocuments(request.getCaId());
            oneTmbOneServiceResponse.setData(responseChecklist);
            oneTmbOneServiceResponse.setStatus(getStatus(ResponseCode.SUCCESS.getCode(),ResponseCode.SUCCESS.getService(),ResponseCode.SUCCESS.getMessage(),""));
            setHeader();
            return ResponseEntity.ok().body(oneTmbOneServiceResponse);
        } catch (Exception e) {
            logger.error("error while get checklist document upload: {}", e);
            oneTmbOneServiceResponse.setStatus(getStatus(ResponseCode.FAILED.getCode(),ResponseCode.FAILED.getService(),ResponseCode.FAILED.getMessage(),e.getMessage()));
            return ResponseEntity.badRequest().headers(responseHeaders).body(oneTmbOneServiceResponse);
        }

    }

    private TmbStatus getStatus(String responseCode, String responseService, String responseMessage, String error) {
        return new TmbStatus(responseCode, responseMessage,
                responseService,error);
    }

    private void setHeader() {
        responseHeaders.set(LendingServiceConstant.HEADER_TIMESTAMP, String.valueOf(Instant.now().toEpochMilli()));
    }
}
