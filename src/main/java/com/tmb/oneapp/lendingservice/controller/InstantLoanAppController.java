package com.tmb.oneapp.lendingservice.controller;

import com.tmb.common.exception.model.TMBCommonException;
import com.tmb.common.logger.LogAround;
import com.tmb.common.logger.TMBLogger;
import com.tmb.common.model.TmbOneServiceResponse;
import com.tmb.common.model.TmbStatus;
import com.tmb.common.model.loan.InstantLoanCreationRequest;
import com.tmb.oneapp.lendingservice.constant.LendingServiceConstant;
import com.tmb.oneapp.lendingservice.constant.ResponseCode;
import com.tmb.oneapp.lendingservice.model.ServiceResponse;
import com.tmb.oneapp.lendingservice.service.InstantLoanCreateApplicationService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

import static com.tmb.oneapp.lendingservice.constant.LendingServiceConstant.*;

import java.time.Instant;
import java.util.Map;

/**
 * Controller to call Instant Loan Creation
 */
@Api(tags = "Loan Submission- Instant Loan Create Application")
@RestController
public class InstantLoanAppController {
	private static final TMBLogger<InstantLoanAppController> logger = new TMBLogger<>(
			InstantLoanAppController.class);
	private final InstantLoanCreateApplicationService instantLoanCreateApplicationService;

	/**
	 * Constructor
	 *
	 * @param instantLoanCreateApplicationService
	 */
	public InstantLoanAppController(
			InstantLoanCreateApplicationService instantLoanCreateApplicationService) {
		this.instantLoanCreateApplicationService = instantLoanCreateApplicationService;
	}

	/**
	 * method to call InstantLoanCreateApplication service for Credit cards, Flasg
	 * crds, C2G
	 *
	 * @param request InstantLoanCreationRequest
	 * @return TmbOneServiceResponse<InstantLoanCreationResponse>
	 */
	@ApiOperation(value = "Create instant Loan Application")
	@LogAround
	@PostMapping("/create-instant-loan-application")
	@ApiImplicitParams({
			@ApiImplicitParam(name = HEADER_CORRELATION_ID, defaultValue = "32fbd3b2-3f97-4a89-ae39-b4f628fbc8da", required = true, paramType = "header"),
			@ApiImplicitParam(name = HEADER_X_CRMID, defaultValue = "001100000000000000000018593707", required = true, dataType = "string", paramType = "header") })
	public ResponseEntity<TmbOneServiceResponse<Object>> createInstantLoanApplication(
			@ApiParam(hidden = true) @RequestHeader Map<String, String> reqHeaders,
			@Valid @RequestBody InstantLoanCreationRequest request) throws TMBCommonException {
		logger.info("Calling createInstantLoanApplication "+request.toString());
		HttpHeaders responseHeaders = new HttpHeaders();
		responseHeaders.set(LendingServiceConstant.HEADER_TIMESTAMP, String.valueOf(Instant.now().toEpochMilli()));
		TmbOneServiceResponse<Object> oneServiceResponse = new TmbOneServiceResponse<>();

		if (!instantLoanCreateApplicationService.isPassMandatoryInfomationRequest(request)) {
			oneServiceResponse.setData(null);
			oneServiceResponse
					.setStatus(new TmbStatus(ResponseCode.BAD_REQUEST.getCode(), ResponseCode.BAD_REQUEST.getMessage(),
							ResponseCode.BAD_REQUEST.getService(), ResponseCode.BAD_REQUEST.getDesc()));
			return ResponseEntity.badRequest().headers(responseHeaders).body(oneServiceResponse);

		}
		String crmId = reqHeaders.get(LendingServiceConstant.HEADER_X_CRMID);
		if (crmId == null) {
			logger.error("no x-crm-id for createInstantLoanApplication");
			throw new TMBCommonException("0001", "failed", ResponseCode.BAD_REQUEST.getService(),
					HttpStatus.BAD_REQUEST, null);
		}
		ServiceResponse response = instantLoanCreateApplicationService.createInstantLoanApplication(crmId, request);

		if (response.getError() != null) {
			oneServiceResponse.setData(null);
			oneServiceResponse.setStatus(new TmbStatus(ResponseCode.FAILED.getCode(), ResponseCode.FAILED.getMessage(),
					ResponseCode.FAILED.getService(), ResponseCode.FAILED.getDesc()));
			return ResponseEntity.badRequest().headers(responseHeaders).body(oneServiceResponse);

		}
		oneServiceResponse.setData(response.getData());
		oneServiceResponse.setStatus(new TmbStatus(ResponseCode.SUCCESS.getCode(), ResponseCode.SUCCESS.getMessage(),
				ResponseCode.SUCCESS.getService(), ResponseCode.SUCCESS.getDesc()));
		return ResponseEntity.ok().headers(responseHeaders).body(oneServiceResponse);

	}

}
