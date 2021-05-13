package com.tmb.oneapp.lendingservice.controller;

import org.springframework.web.bind.annotation.RestController;

import com.tmb.common.logger.TMBLogger;
import com.tmb.oneapp.lendingservice.service.CustomerProfileService;

import io.swagger.annotations.Api;

/**
 * Controller working information
 * 
 * @author Admin
 *
 */
@RestController
@Api(tags = "Working information")
public class WorkInformationController {

	private static final TMBLogger<WorkInformationController> logger = new TMBLogger<>(WorkInformationController.class);

	private CustomerProfileService customerProfileService;

	public WorkInformationController(CustomerProfileService customerProfileService) {
		this.customerProfileService = customerProfileService;
	}
	
	

}
