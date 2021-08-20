package com.tmb.oneapp.lendingservice.controller;

import com.tmb.common.exception.model.TMBCommonException;
import com.tmb.common.logger.LogAround;
import com.tmb.common.logger.TMBLogger;
import com.tmb.common.model.TmbOneServiceResponse;
import com.tmb.common.model.TmbStatus;
import com.tmb.common.model.legacy.rsl.ws.application.save.response.ResponseApplication;
import com.tmb.common.util.TMBUtils;
import com.tmb.oneapp.lendingservice.constant.LendingServiceConstant;
import com.tmb.oneapp.lendingservice.constant.ResponseCode;
import com.tmb.oneapp.lendingservice.model.loanonline.*;
import com.tmb.oneapp.lendingservice.service.LoanOnlineSubmissionCheckWaiveDocService;
import com.tmb.oneapp.lendingservice.service.LoanSubmissionCreateApplicationService;
import com.tmb.oneapp.lendingservice.service.LoanSubmissionGenNCBFileService;
import com.tmb.oneapp.lendingservice.service.LoanSubmissionGetCustInfoAppInfoService;
import com.tmb.oneapp.lendingservice.service.LoanSubmissionGetWorkingDetailService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
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
    private final LoanSubmissionGetWorkingDetailService loanSubmissionGetWorkingDetailService;
    private final LoanSubmissionGetCustInfoAppInfoService loanSubmissionGetCustInfoAppInfoService;
    private final LoanSubmissionGenNCBFileService loanSubmissionGenNCBFileService;
    private static final HttpHeaders responseHeaders = new HttpHeaders();

    @GetMapping("/getIncomeInfo")
    @LogAround
    @ApiOperation(value = "get income info")
    public ResponseEntity<TmbOneServiceResponse<IncomeInfo>> getIncomeInfo(@RequestHeader(name = LendingServiceConstant.HEADER_X_CRMID) String crmId) {
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

    @ApiOperation("Loan Submission Get Working Detail")
    @GetMapping(value = "/getWorkingDetail", produces = MediaType.APPLICATION_JSON_VALUE)
    @LogAround
    public ResponseEntity<TmbOneServiceResponse<WorkingDetail>> loanSubmissionGetWorkingDetail(
            @ApiParam(value = LendingServiceConstant.HEADER_CORRELATION_ID, defaultValue = "32fbd3b2-3f97-4a89-ar39-b4f628fbc8da", required = true)
            @Valid @RequestHeader(LendingServiceConstant.HEADER_CORRELATION_ID) String correlationId,
            @ApiParam(value = LendingServiceConstant.HEADER_X_CRMID, defaultValue = "001100000000000000000018593707", required = true)
            @Valid @RequestHeader(LendingServiceConstant.HEADER_X_CRMID) String crmId,
            @RequestParam(value = "caId") Long caId
            ) throws TMBCommonException {
        TmbOneServiceResponse<WorkingDetail> response = new TmbOneServiceResponse<>();

        try {
            WorkingDetail workingDetail = loanSubmissionGetWorkingDetailService.getWorkingDetail(crmId, caId);
            response.setData(workingDetail);
            response.setStatus(new TmbStatus(ResponseCode.SUCCESS.getCode(),
                    ResponseCode.SUCCESS.getMessage(), ResponseCode.SUCCESS.getService(), ResponseCode.SUCCESS.getDesc()));

            return ResponseEntity.ok()
                    .headers(TMBUtils.getResponseHeaders())
                    .body(response);

        } catch (TMBCommonException e) {
            throw e;
        } catch (Exception e) {
            throw new TMBCommonException(ResponseCode.FAILED.getCode(), ResponseCode.FAILED.getMessage(), ResponseCode.FAILED.getService(), HttpStatus.INTERNAL_SERVER_ERROR, e);
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

	@ApiOperation("Loan Submission Get Customer Info and Application Info")
	@GetMapping(value = "/get-customer-info-application-info", produces = MediaType.APPLICATION_JSON_VALUE)
	@LogAround
	public ResponseEntity<TmbOneServiceResponse<CustomerInfoApplicationInfo>> loanSubmissionGetCustomerInfoAndApplicationInfo(
			@ApiParam(value = LendingServiceConstant.HEADER_CORRELATION_ID, defaultValue = "32fbd3b2-3f97-4a89-ar39-b4f628fbc8da", required = true)
            @Valid @RequestHeader(LendingServiceConstant.HEADER_CORRELATION_ID) String correlationId,
            @ApiParam(value = LendingServiceConstant.HEADER_X_CRMID, defaultValue = "001100000000000000000018593707", required = true)
            @Valid @RequestHeader(LendingServiceConstant.HEADER_X_CRMID) String crmId,
			@RequestParam(value = "caId") String caId) throws TMBCommonException {
		TmbOneServiceResponse<CustomerInfoApplicationInfo> response = new TmbOneServiceResponse<>();

		try {
			CustomerInfoApplicationInfo customerInfoApplicationInfo = loanSubmissionGetCustInfoAppInfoService
					.getCustomerInfoAndApplicationInfo(caId);
			response.setData(customerInfoApplicationInfo);
			response.setStatus(new TmbStatus(ResponseCode.SUCCESS.getCode(), ResponseCode.SUCCESS.getMessage(),
					ResponseCode.SUCCESS.getService(), ResponseCode.SUCCESS.getDesc()));

			return ResponseEntity.ok().headers(TMBUtils.getResponseHeaders()).body(response);

		} catch (Exception e) {
			throw new TMBCommonException(ResponseCode.FAILED.getCode(), ResponseCode.FAILED.getMessage(),
					ResponseCode.FAILED.getService(), HttpStatus.INTERNAL_SERVER_ERROR, e);
		}
	}
	
	@ApiOperation("Loan submission generate NCB file and store to sFTP")
	@PostMapping(value = "/update-ncb-consent-flag", produces = MediaType.APPLICATION_JSON_VALUE)
	@LogAround
	public ResponseEntity<TmbOneServiceResponse<String>> loanSubmissionGenerateNCBFile(
			@ApiParam(value = LendingServiceConstant.HEADER_CORRELATION_ID, defaultValue = "32fbd3b2-3f97-4a89-ar39-b4f628fbc8da", required = true) @Valid @RequestHeader(LendingServiceConstant.HEADER_CORRELATION_ID) String correlationId,
			@ApiParam(value = LendingServiceConstant.HEADER_X_CRMID, defaultValue = "001100000000000000000018593707", required = true) @Valid @RequestHeader(LendingServiceConstant.HEADER_X_CRMID) String crmId,
			@RequestParam(value = "caId") String caId) throws TMBCommonException {
		TmbOneServiceResponse<String> response = new TmbOneServiceResponse<>();

		try {
			loanSubmissionGenNCBFileService.storeNCBfile(crmId, caId);
			response.setStatus(new TmbStatus(ResponseCode.SUCCESS.getCode(), ResponseCode.SUCCESS.getMessage(),
					ResponseCode.SUCCESS.getService(), ResponseCode.SUCCESS.getDesc()));

			return ResponseEntity.ok().headers(TMBUtils.getResponseHeaders()).body(response);

		} catch (Exception e) {
			throw new TMBCommonException(ResponseCode.FAILED.getCode(), ResponseCode.FAILED.getMessage(),
					ResponseCode.FAILED.getService(), HttpStatus.INTERNAL_SERVER_ERROR, e);
		}
	}
    
}
