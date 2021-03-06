package com.tmb.oneapp.lendingservice.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.tmb.common.logger.LogAround;
import com.tmb.common.logger.TMBLogger;
import com.tmb.common.model.TmbOneServiceResponse;
import com.tmb.common.model.TmbStatus;
import com.tmb.oneapp.lendingservice.constant.LendingServiceConstant;
import com.tmb.oneapp.lendingservice.constant.LoanCategory;
import com.tmb.oneapp.lendingservice.constant.ResponseCode;
import com.tmb.oneapp.lendingservice.model.CriteriaCodeEntry;
import com.tmb.oneapp.lendingservice.model.response.WorkInfoEntryResp;
import com.tmb.oneapp.lendingservice.service.LendingCriteriaInfoService;
import com.tmb.oneapp.lendingservice.service.WorkInfoProfileService;
import static com.tmb.oneapp.lendingservice.constant.LendingServiceConstant.HEADER_X_CRMID;
import static com.tmb.oneapp.lendingservice.constant.LendingServiceConstant.HEADER_CORRELATION_ID;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

/**
 * Controller working information
 * 
 * @author Admin
 *
 */
@RestController
@Api(tags = "Criteria Working information")
public class WorkInformationController {

	private static final TMBLogger<WorkInformationController> logger = new TMBLogger<>(WorkInformationController.class);

	private LendingCriteriaInfoService lendingCriteriaInfoService;

	private WorkInfoProfileService workInfoProfileService;

	@Autowired
	public WorkInformationController(LendingCriteriaInfoService lendingCriteriaInfoService,
			WorkInfoProfileService workInfoProfileService) {
		this.lendingCriteriaInfoService = lendingCriteriaInfoService;
		this.workInfoProfileService = workInfoProfileService;
	}

	/**
	 * 
	 * @param occcupationCode
	 * @return
	 */
	@LogAround
	@GetMapping(value = "/criteria/status", produces = MediaType.APPLICATION_JSON_VALUE)
	@ApiOperation(value = "Criteria for Working Status information")
	@ApiImplicitParams({
			@ApiImplicitParam(name = HEADER_CORRELATION_ID, defaultValue = "32fbd3b2-3f97-4a89-ae39-b4f628fbc8da", required = true, paramType = "header"),
			@ApiImplicitParam(name = HEADER_X_CRMID, defaultValue = "001100000000000000000018593707", required = true, dataType = "string", paramType = "header") })
	public ResponseEntity<TmbOneServiceResponse<List<CriteriaCodeEntry>>> getWorkStatusInfoByOccupationCode(
			@RequestHeader(LendingServiceConstant.HEADER_CORRELATION_ID) String correlationId) {
		logger.info("lending-service getWorkStatusInfoByOccupationCode method start Time : {} ",
				System.currentTimeMillis());
		TmbOneServiceResponse<List<CriteriaCodeEntry>> response = new TmbOneServiceResponse();
		try {
			List<CriteriaCodeEntry> criteriaCodeEntrys = lendingCriteriaInfoService
					.getCriteriaByCatalogId(LoanCategory.EMPLOYMENT_STATUS);
			setResponseSuccess(response, criteriaCodeEntrys);
		} catch (Exception e) {
			logger.error(e.toString(), e);
			response.setStatus(new TmbStatus(ResponseCode.FAILED.getCode(), ResponseCode.FAILED.getMessage(),
					ResponseCode.FAILED.getService(), ResponseCode.FAILED.getDesc()));
		}
		return ResponseEntity.ok(response);
	}

	/**
	 * Model Wrapper
	 * 
	 * @param response
	 * @param criteriaCodeEntrys
	 */
	private void setResponseSuccess(TmbOneServiceResponse<List<CriteriaCodeEntry>> response,
			List<CriteriaCodeEntry> criteriaCodeEntrys) {
		if (logger.isDebugEnabled()) {
			logger.debug("Return " + criteriaCodeEntrys);
		}
		response.setData(criteriaCodeEntrys);
		response.setStatus(new TmbStatus(ResponseCode.SUCCESS.getCode(), ResponseCode.SUCCESS.getMessage(),
				ResponseCode.SUCCESS.getService(), ResponseCode.SUCCESS.getDesc()));
	}

	@LogAround
	@GetMapping(value = "/criteria/status/{entrycode}", produces = MediaType.APPLICATION_JSON_VALUE)
	@ApiOperation(value = "Criteria for Occupation information")
	@ApiImplicitParams({
			@ApiImplicitParam(name = HEADER_CORRELATION_ID, defaultValue = "32fbd3b2-3f97-4a89-ae39-b4f628fbc8da", required = true, paramType = "header"),
			@ApiImplicitParam(name = HEADER_X_CRMID, defaultValue = "001100000000000000000018593707", required = true, dataType = "string", paramType = "header") })
	public ResponseEntity<TmbOneServiceResponse<List<CriteriaCodeEntry>>> getOccupationByOccupationCode(
			@RequestHeader(LendingServiceConstant.HEADER_CORRELATION_ID) String correlationId,
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
			setResponseSuccess(response, criteriaCodeEntrys);
		} catch (Exception e) {
			logger.error(e.toString(), e);
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
	@LogAround
	@GetMapping(value = "/criteria/businesstype", produces = MediaType.APPLICATION_JSON_VALUE)
	@ApiOperation(value = "Criteria for business type information")
	@ApiImplicitParams({
			@ApiImplicitParam(name = HEADER_CORRELATION_ID, defaultValue = "32fbd3b2-3f97-4a89-ae39-b4f628fbc8da", required = true, paramType = "header"),
			@ApiImplicitParam(name = HEADER_X_CRMID, defaultValue = "001100000000000000000018593707", required = true, dataType = "string", paramType = "header") })
	public ResponseEntity<TmbOneServiceResponse<List<CriteriaCodeEntry>>> getBusinessTypeInfo(
			@RequestHeader(LendingServiceConstant.HEADER_CORRELATION_ID) String correlationId) {
		logger.info("lending-service getBusinessTypeInfo method start Time : {} ", System.currentTimeMillis());

		TmbOneServiceResponse<List<CriteriaCodeEntry>> response = new TmbOneServiceResponse();
		try {
			List<CriteriaCodeEntry> criteriaCodeEntrys = lendingCriteriaInfoService
					.getCriteriaByCatalogId(LoanCategory.BUSINESS_TYPE);
			setResponseSuccess(response, criteriaCodeEntrys);
		} catch (Exception e) {
			logger.error(e.toString(), e);
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
	@LogAround
	@GetMapping(value = "/criteria/businesstype/{entryCode}", produces = MediaType.APPLICATION_JSON_VALUE)
	@ApiOperation(value = "Criteria for sub business type information")
	@ApiImplicitParams({
			@ApiImplicitParam(name = HEADER_CORRELATION_ID, defaultValue = "32fbd3b2-3f97-4a89-ae39-b4f628fbc8da", required = true, paramType = "header"),
			@ApiImplicitParam(name = HEADER_X_CRMID, defaultValue = "001100000000000000000018593707", required = true, dataType = "string", paramType = "header") })
	public ResponseEntity<TmbOneServiceResponse<List<CriteriaCodeEntry>>> getSubBusinessTypeInfo(
			@RequestHeader(LendingServiceConstant.HEADER_CORRELATION_ID) String correlationId,
			@ApiParam(value = "Business type entry code", defaultValue = "A", required = true) @PathVariable("entryCode") String reference) {
		logger.info("lending-service getSubBusinessTypeInfo method start Time : {} ", System.currentTimeMillis());

		TmbOneServiceResponse<List<CriteriaCodeEntry>> response = new TmbOneServiceResponse();
		try {
			List<CriteriaCodeEntry> criteriaCodeEntrys = lendingCriteriaInfoService.getSubBusinessType(reference);
			setResponseSuccess(response, criteriaCodeEntrys);
		} catch (Exception e) {
			logger.error(e.toString(), e);
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
	@LogAround
	@GetMapping(value = "/criteria/income/{entryCode}", produces = MediaType.APPLICATION_JSON_VALUE)
	@ApiOperation(value = "Criteria for type of income information")
	@ApiImplicitParams({
			@ApiImplicitParam(name = HEADER_CORRELATION_ID, defaultValue = "32fbd3b2-3f97-4a89-ae39-b4f628fbc8da", required = true, paramType = "header"),
			@ApiImplicitParam(name = HEADER_X_CRMID, defaultValue = "001100000000000000000018593707", required = true, dataType = "string", paramType = "header") })
	public ResponseEntity<TmbOneServiceResponse<List<CriteriaCodeEntry>>> getSourceOfIncomeInfo(
			@RequestHeader(LendingServiceConstant.HEADER_CORRELATION_ID) String correlationId,
			@ApiParam(value = "Business type entry code", defaultValue = "01", required = true) @PathVariable("entryCode") String reference) {
		logger.info("lending-service getSourceOfIncomeInfo method start Time : {} ", System.currentTimeMillis());

		TmbOneServiceResponse<List<CriteriaCodeEntry>> response = new TmbOneServiceResponse();
		try {
			List<CriteriaCodeEntry> criteriaCodeEntrys = lendingCriteriaInfoService.getSourceOfIncome(reference);
			setResponseSuccess(response, criteriaCodeEntrys);
		} catch (Exception e) {
			logger.error(e.toString(), e);
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
	@LogAround
	@GetMapping(value = "/criteria/country", produces = MediaType.APPLICATION_JSON_VALUE)
	@ApiOperation(value = "Criteria for country information")
	@ApiImplicitParams({
			@ApiImplicitParam(name = HEADER_CORRELATION_ID, defaultValue = "32fbd3b2-3f97-4a89-ae39-b4f628fbc8da", required = true, paramType = "header"),
			@ApiImplicitParam(name = HEADER_X_CRMID, defaultValue = "001100000000000000000018593707", required = true, dataType = "string", paramType = "header") })
	public ResponseEntity<TmbOneServiceResponse<List<CriteriaCodeEntry>>> getCountryList(
			@RequestHeader(LendingServiceConstant.HEADER_CORRELATION_ID) String correlationId) {
		logger.info("lending-service getCountryList method start Time : {} ", System.currentTimeMillis());

		TmbOneServiceResponse<List<CriteriaCodeEntry>> response = new TmbOneServiceResponse();
		try {
			List<CriteriaCodeEntry> criteriaCodeEntrys = lendingCriteriaInfoService
					.getCriteriaByCatalogId(LoanCategory.SCI_COUNTRY);
			setResponseSuccess(response, criteriaCodeEntrys);
		} catch (Exception e) {
			logger.error(e.toString(), e);
			response.setStatus(new TmbStatus(ResponseCode.FAILED.getCode(), ResponseCode.FAILED.getMessage(),
					ResponseCode.FAILED.getService(), ResponseCode.FAILED.getDesc()));
		}
		return ResponseEntity.ok(response);
	}

	@LogAround
	@GetMapping(value = "/fetch-working-info", produces = MediaType.APPLICATION_JSON_VALUE)
	@ApiOperation(value = "Get work information with profile")
	@ApiImplicitParams({
			@ApiImplicitParam(name = HEADER_CORRELATION_ID, defaultValue = "32fbd3b2-3f97-4a89-ae39-b4f628fbc8da", required = true, paramType = "header"),
			@ApiImplicitParam(name = HEADER_X_CRMID, defaultValue = "001100000000000000000018593707", required = true, dataType = "string", paramType = "header") })
	public ResponseEntity<TmbOneServiceResponse<WorkInfoEntryResp>> getWorkInformationWithProfile(
			@RequestParam(value = "occupationcode") String occupationCode,
			@RequestParam(value = "businesstypecode") String businessTypeCode,
			@RequestParam(value = "countryofincome") String countryOfIncome) {
		logger.info("lending-service getWorkInformationWithProfile method start Time : {} ",
				System.currentTimeMillis());
		TmbOneServiceResponse<WorkInfoEntryResp> response = new TmbOneServiceResponse();
		try {
			WorkInfoEntryResp workInfoEntry = workInfoProfileService.createWorkInformationModel(occupationCode,
					businessTypeCode, countryOfIncome);
			response.setData(workInfoEntry);
			response.setStatus(new TmbStatus(ResponseCode.SUCCESS.getCode(), ResponseCode.SUCCESS.getMessage(),
					ResponseCode.SUCCESS.getService(), ResponseCode.SUCCESS.getDesc()));
		} catch (Exception e) {
			logger.error(e.toString(), e);
			response.setStatus(new TmbStatus(ResponseCode.FAILED.getCode(), ResponseCode.FAILED.getMessage(),
					ResponseCode.FAILED.getService(), ResponseCode.FAILED.getDesc()));
		}
		return ResponseEntity.ok(response);
	}

}
