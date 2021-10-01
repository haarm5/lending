package com.tmb.oneapp.lendingservice.controller;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;

import com.tmb.common.model.TmbOneServiceResponse;
import com.tmb.oneapp.lendingservice.constant.LendingServiceConstant;
import com.tmb.oneapp.lendingservice.service.PurgeDataService;

@RunWith(JUnit4.class)
public class JobScheduleControllerTest {

	@Mock
	PurgeDataService purgeDataService;

	@BeforeEach
	void setUp() {
		MockitoAnnotations.initMocks(this);
	}

	@Test
	void purgeDataSuccess() {
		when(purgeDataService.purgeData()).thenReturn(true);
		JobScheduleController jobScheduleController = new JobScheduleController(purgeDataService);
		ResponseEntity<TmbOneServiceResponse<String>> actualResponse = jobScheduleController
				.purgeData(new HttpHeaders());
		Assertions.assertNotNull(actualResponse);
		Assertions.assertEquals(LendingServiceConstant.SUCCESS_DESC_PURGE_DATA, actualResponse.getBody().getData());
		verify(purgeDataService, times(1)).purgeData();
	}

	@Test
	void purgeDataFailed() {
		when(purgeDataService.purgeData()).thenReturn(false);
		JobScheduleController jobScheduleController = new JobScheduleController(purgeDataService);
		ResponseEntity<TmbOneServiceResponse<String>> actualResponse = jobScheduleController
				.purgeData(new HttpHeaders());
		Assertions.assertNotNull(actualResponse);
		Assertions.assertEquals(LendingServiceConstant.FAILED_DESC_PURGE_DATA, actualResponse.getBody().getData());
		verify(purgeDataService, times(1)).purgeData();
	}

}
