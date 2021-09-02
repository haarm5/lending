package com.tmb.oneapp.lendingservice.controller;

import javax.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.tmb.common.exception.model.TMBCommonException;
import com.tmb.common.logger.LogAround;
import com.tmb.common.logger.TMBLogger;
import com.tmb.common.model.TmbOneServiceResponse;
import com.tmb.common.model.TmbStatus;
import com.tmb.common.model.loan.stagingbar.LoanStagingbar;
import com.tmb.common.util.TMBUtils;
import com.tmb.oneapp.lendingservice.constant.LendingServiceConstant;
import com.tmb.oneapp.lendingservice.constant.ResponseCode;
import com.tmb.oneapp.lendingservice.model.LoanStagingbarRequest;
import com.tmb.oneapp.lendingservice.service.LoanStagingBarService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Api(tags = "LoanStagingBar")
@RequestMapping("/loan")
@RestController
public class LoanStagingBarController {
	private static final TMBLogger<LoanStagingBarController> logger = new TMBLogger<>(LoanStagingBarController.class);
	private final LoanStagingBarService loanStagingBarService;

	@ApiOperation("Loan Staging Bar")
	@PostMapping(value = "/get-staging-bar", produces = MediaType.APPLICATION_JSON_VALUE)
	@LogAround
	public ResponseEntity<TmbOneServiceResponse<LoanStagingbar>> fetchLoanStagingBar(
			@ApiParam(value = LendingServiceConstant.HEADER_CORRELATION_ID, defaultValue = "32fbd3b2-3f97-4a89-ar39-b4f628fbc8da", required = true) @Valid @RequestHeader(LendingServiceConstant.HEADER_CORRELATION_ID) String correlationId,
			@ApiParam(value = LendingServiceConstant.HEADER_X_CRMID, defaultValue = "001100000000000000000018593707", required = true) @Valid @RequestHeader(LendingServiceConstant.HEADER_X_CRMID) String crmId,
			@Valid @RequestBody LoanStagingbarRequest request) throws TMBCommonException {
		TmbOneServiceResponse<LoanStagingbar> response = new TmbOneServiceResponse<>();
		if (request.getLoanType() == null || request.getProductHeaderKey() == null) {
			logger.error("error exception fetch loan staging bar : key value is null");
			throw new TMBCommonException(ResponseCode.FAILED.getCode(), ResponseCode.FAILED.getMessage(),
					ResponseCode.FAILED.getService(), HttpStatus.BAD_REQUEST, null);
		}
		try {
			LoanStagingbar loanStagingbarRes = loanStagingBarService.fetchLoanStagingBar(correlationId, crmId, request);
			response.setData(loanStagingbarRes);
			response.setStatus(new TmbStatus(ResponseCode.SUCCESS.getCode(), ResponseCode.SUCCESS.getMessage(),
					ResponseCode.SUCCESS.getService(), ResponseCode.SUCCESS.getDesc()));
			return ResponseEntity.ok().headers(TMBUtils.getResponseHeaders()).body(response);

		} catch (Exception e) {
			throw new TMBCommonException(ResponseCode.FAILED.getCode(), ResponseCode.FAILED.getMessage(),
					ResponseCode.FAILED.getService(), HttpStatus.INTERNAL_SERVER_ERROR, e);
		}
	}

}
