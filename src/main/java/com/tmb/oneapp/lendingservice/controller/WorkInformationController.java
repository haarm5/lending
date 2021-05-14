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
import com.tmb.oneapp.lendingservice.constant.ResponseCode;
import com.tmb.oneapp.lendingservice.model.CriteriaCodeEntry;
import com.tmb.oneapp.lendingservice.service.CustomerProfileService;
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
	@GetMapping(value = "/criteria/work/{occcupationCode}", produces = MediaType.APPLICATION_JSON_VALUE)
	@LogAround
	public ResponseEntity<TmbOneServiceResponse<List<CriteriaCodeEntry>>> getWorkStatusInfoByOccupationCode(
			@ApiParam(value = "Correlation ID", defaultValue = "32fbd3b2-3f97-4a89-ar39-b4f628fbc8da", required = true) @Valid @RequestHeader(LendingServiceConstant.HEADER_CORRELATION_ID) String correlationId,
			@PathVariable("occcupationCode") String occcupationCode) {
		logger.info("customers-service getWorkStatusInfo method start Time : {} ", System.currentTimeMillis());
		if (logger.isDebugEnabled()) {
			logger.info("Fetch for occupation code :" + occcupationCode);
		}
		TmbOneServiceResponse<List<CriteriaCodeEntry>> response = new TmbOneServiceResponse();
		try {
			List<CriteriaCodeEntry> criteriaCodeEntrys = lendingCriteriaInfoService
					.getWorkStatusByOccupationCode(occcupationCode);
			if (logger.isDebugEnabled()) {
				logger.debug("Return " + criteriaCodeEntrys);
			}
			response.setData(criteriaCodeEntrys);
			response.setStatus(new TmbStatus(ResponseCode.SUCCESS.getCode(), ResponseCode.SUCCESS.getMessage(),
					ResponseCode.SUCCESS.getDesc()));
		} catch (Exception e) {
			response.setStatus(new TmbStatus(ResponseCode.FAILED.getCode(), ResponseCode.FAILED.getMessage(),
					ResponseCode.FAILED.getDesc()));
		}
		return ResponseEntity.ok(response);
	}
	
	

}
