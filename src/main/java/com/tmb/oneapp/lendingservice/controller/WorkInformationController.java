package com.tmb.oneapp.lendingservice.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

import com.tmb.common.logger.LogAround;
import com.tmb.common.logger.TMBLogger;
import com.tmb.common.model.TmbOneServiceResponse;
import com.tmb.common.model.TmbStatus;
import com.tmb.oneapp.lendingservice.constant.LendingServiceConstant;
import com.tmb.oneapp.lendingservice.constant.LoanCategory;
import com.tmb.oneapp.lendingservice.constant.ResponseCode;
import com.tmb.oneapp.lendingservice.model.CriteriaCodeEntry;
import com.tmb.oneapp.lendingservice.service.LendingCriteriaInfoService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

/**
 * Controller working information
 * 
 * @author Admin
 *
 */
@RestController
@Api(tags = "Creteria Working information")
public class WorkInformationController {

	private static final TMBLogger<WorkInformationController> logger = new TMBLogger<>(WorkInformationController.class);

	private LendingCriteriaInfoService lendingCriteriaInfoService;

	public WorkInformationController(LendingCriteriaInfoService lendingCriteriaInfoService) {
		this.lendingCriteriaInfoService = lendingCriteriaInfoService;
	}

	/**
	 * 
	 * @param occcupationCode
	 * @return
	 */
	@ApiOperation(value = "Creteria for Working Status")
	@GetMapping(value = "/criteria/status", produces = MediaType.APPLICATION_JSON_VALUE)
	@LogAround
	public ResponseEntity<TmbOneServiceResponse<List<CriteriaCodeEntry>>> getWorkStatusInfoByOccupationCode(
			@ApiParam(value = "Correlation ID", defaultValue = "32fbd3b2-3f97-4a89-ar39-b4f628fbc8da", required = true) @Valid @RequestHeader(LendingServiceConstant.HEADER_CORRELATION_ID) String correlationId) {
		logger.info("lending-service getWorkStatusInfoByOccupationCode method start Time : {} ",
				System.currentTimeMillis());
		TmbOneServiceResponse<List<CriteriaCodeEntry>> response = new TmbOneServiceResponse();
		try {
			List<CriteriaCodeEntry> criteriaCodeEntrys = lendingCriteriaInfoService
					.getCriteriaByCatalogId(LoanCategory.EMPLOYMENT_STATUS);
			if (logger.isDebugEnabled()) {
				logger.debug("Return " + criteriaCodeEntrys);
			}
			response.setData(criteriaCodeEntrys);
			response.setStatus(new TmbStatus(ResponseCode.SUCCESS.getCode(), ResponseCode.SUCCESS.getMessage(),
					ResponseCode.SUCCESS.getService(), ResponseCode.SUCCESS.getDesc()));
		} catch (Exception e) {
			response.setStatus(new TmbStatus(ResponseCode.FAILED.getCode(), ResponseCode.FAILED.getMessage(),
					ResponseCode.FAILED.getService(), ResponseCode.FAILED.getDesc()));
		}
		return ResponseEntity.ok(response);
	}

	@ApiOperation(value = "Creteria for Occupation info")
	@GetMapping(value = "/criteria/status/{entrycode}", produces = MediaType.APPLICATION_JSON_VALUE)
	@LogAround
	public ResponseEntity<TmbOneServiceResponse<List<CriteriaCodeEntry>>> getOccupationByOccupationCode(
			@ApiParam(value = "Correlation ID", defaultValue = "32fbd3b2-3f97-4a89-ar39-b4f628fbc8da", required = true) @Valid @RequestHeader(LendingServiceConstant.HEADER_CORRELATION_ID) String correlationId,
			@ApiParam(value = "Employment status entry code", defaultValue = "01", required = true) @PathVariable("entrycode") String reference) {
		logger.info("lending-service getOccupationByOccupationCode method start Time : {} ",
				System.currentTimeMillis());
		if (logger.isDebugEnabled()) {
			logger.info("Fetch for occupation code :" + reference);
		}
		TmbOneServiceResponse<List<CriteriaCodeEntry>> response = new TmbOneServiceResponse();
		try {

			List<CriteriaCodeEntry> criteriaCodeEntrys = lendingCriteriaInfoService
					.getOccupationByEmploymentStatus(reference);
			if (logger.isDebugEnabled()) {
				logger.debug("Return " + criteriaCodeEntrys);
			}

			response.setData(criteriaCodeEntrys);
			response.setStatus(new TmbStatus(ResponseCode.SUCCESS.getCode(), ResponseCode.SUCCESS.getMessage(),
					ResponseCode.SUCCESS.getService(), ResponseCode.SUCCESS.getDesc()));
		} catch (Exception e) {
			response.setStatus(new TmbStatus(ResponseCode.FAILED.getCode(), ResponseCode.FAILED.getMessage(),
					ResponseCode.FAILED.getService(), ResponseCode.FAILED.getDesc()));
		}
		return ResponseEntity.ok(response);
	}

	/**
	 * 
	 * @param occcupationCode
	 * @return
	 */
	@ApiOperation(value = "Creteria for business type info")
	@GetMapping(value = "/criteria/businesstype", produces = MediaType.APPLICATION_JSON_VALUE)
	@LogAround
	public ResponseEntity<TmbOneServiceResponse<List<CriteriaCodeEntry>>> getBusinessTypeInfo(
			@ApiParam(value = "Correlation ID", defaultValue = "32fbd3b2-3f97-4a89-ar39-b4f628fbc8da", required = true) @Valid @RequestHeader(LendingServiceConstant.HEADER_CORRELATION_ID) String correlationId) {
		logger.info("lending-service getBusinessTypeInfo method start Time : {} ", System.currentTimeMillis());

		TmbOneServiceResponse<List<CriteriaCodeEntry>> response = new TmbOneServiceResponse();
		try {
			List<CriteriaCodeEntry> criteriaCodeEntrys = lendingCriteriaInfoService.getCriteriaByCatalogId(LoanCategory.BUSINESS_TYPE);
			response.setData(criteriaCodeEntrys);
			response.setStatus(new TmbStatus(ResponseCode.SUCCESS.getCode(), ResponseCode.SUCCESS.getMessage(),
					ResponseCode.SUCCESS.getService(), ResponseCode.SUCCESS.getDesc()));
		} catch (Exception e) {
			response.setStatus(new TmbStatus(ResponseCode.FAILED.getCode(), ResponseCode.FAILED.getMessage(),
					ResponseCode.FAILED.getService(), ResponseCode.FAILED.getDesc()));
		}
		return ResponseEntity.ok(response);
	}

	/**
	 * 
	 * @param occcupationCode
	 * @return
	 */
	@ApiOperation(value = "Creteria for sub business type info")
	@GetMapping(value = "/criteria/businesstype/{entryCode}", produces = MediaType.APPLICATION_JSON_VALUE)
	@LogAround
	public ResponseEntity<TmbOneServiceResponse<List<CriteriaCodeEntry>>> getSubBusinessTypeInfo(
			@ApiParam(value = "Correlation ID", defaultValue = "32fbd3b2-3f97-4a89-ar39-b4f628fbc8da", required = true) @Valid @RequestHeader(LendingServiceConstant.HEADER_CORRELATION_ID) String correlationId,
			@ApiParam(value = "Business type entry code", defaultValue = "A", required = true) @PathVariable("entryCode") String reference) {
		logger.info("lending-service getSubBusinessTypeInfo method start Time : {} ", System.currentTimeMillis());

		TmbOneServiceResponse<List<CriteriaCodeEntry>> response = new TmbOneServiceResponse();
		try {
			List<CriteriaCodeEntry> criteriaCodeEntrys = lendingCriteriaInfoService.getSubBusinessType(reference);
			response.setData(criteriaCodeEntrys);
			response.setStatus(new TmbStatus(ResponseCode.SUCCESS.getCode(), ResponseCode.SUCCESS.getMessage(),
					ResponseCode.SUCCESS.getService(), ResponseCode.SUCCESS.getDesc()));
		} catch (Exception e) {
			response.setStatus(new TmbStatus(ResponseCode.FAILED.getCode(), ResponseCode.FAILED.getMessage(),
					ResponseCode.FAILED.getService(), ResponseCode.FAILED.getDesc()));
		}
		return ResponseEntity.ok(response);
	}

}
