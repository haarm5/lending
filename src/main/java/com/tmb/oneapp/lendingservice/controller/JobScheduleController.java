package com.tmb.oneapp.lendingservice.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

import com.tmb.common.logger.LogAround;
import com.tmb.common.logger.TMBLogger;
import com.tmb.common.model.TmbOneServiceResponse;
import com.tmb.common.model.TmbStatus;
import com.tmb.common.util.TMBUtils;
import com.tmb.oneapp.lendingservice.constant.LendingServiceConstant;
import com.tmb.oneapp.lendingservice.constant.ResponseCode;
import com.tmb.oneapp.lendingservice.service.PurgeDataService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@RestController
@Api(tags = "Batch Job Api")
public class JobScheduleController {
	private static final TMBLogger<JobScheduleController> logger = new TMBLogger<>(JobScheduleController.class);
	private final PurgeDataService purgeDataService;

	/**
	 * Constructor
	 *
	 * @param purgeDataService
	 */

	@Autowired
	public JobScheduleController(PurgeDataService purgeDataService) {
		this.purgeDataService = purgeDataService;
	}

	/**
	 * Batch Job purging e-consent e-app and upload document
	 *
	 * @param headers
	 * @return purging response
	 */

	@LogAround
	@PostMapping("/purge-data")
	@ApiOperation("Service purging e-consent e-app and upload document : this will be called from scheduler-microservice")
	public ResponseEntity<TmbOneServiceResponse<String>> captureInstallmentPlan(@RequestHeader HttpHeaders headers) {
		logger.info("Purging e-consent e-app and upload document ");
		TmbOneServiceResponse<String> oneResponse = new TmbOneServiceResponse<>();
		boolean isSuccess = purgeDataService.purgeData();
		if (isSuccess) {
			oneResponse.setStatus(new TmbStatus(ResponseCode.SUCCESS.getCode(), ResponseCode.SUCCESS.getMessage(),
					ResponseCode.SUCCESS.getService(), ResponseCode.SUCCESS.getDesc()));
			oneResponse.setData(LendingServiceConstant.SUCCESS_DESC_PURGE_DATA);
		} else {
			oneResponse.setStatus(new TmbStatus(ResponseCode.FAILED.getCode(), ResponseCode.FAILED.getMessage(),
					ResponseCode.FAILED.getService()));
			oneResponse.setData(LendingServiceConstant.FAILED_ESC_PURGE_DATA);
		}
		return ResponseEntity.ok().headers(TMBUtils.getResponseHeaders()).body(oneResponse);
	}

}