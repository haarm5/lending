package com.tmb.oneapp.lendingservice.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.tmb.oneapp.lendingservice.client.CommonServiceFeignClient;
import com.tmb.oneapp.lendingservice.model.CriteriaCodeEntry;
import com.tmb.oneapp.lendingservice.service.LendingCriteriaInfoService;
import com.tmb.oneapp.lendingservice.service.WorkInfoProfileService;

@RunWith(JUnit4.class)
public class WorkInformationControllerTest {

	@Mock
	private LendingCriteriaInfoService lendingCriteriaInfoService;
	@Mock
	private WorkInfoProfileService workInfoProfileService;
	
	private WorkInformationController controller;
	private CommonServiceFeignClient commonServiceClient;

	@BeforeEach
	void setUp() {
		MockitoAnnotations.initMocks(this);
	}

	@Test
	public void testController() {
		workInfoProfileService = new WorkInfoProfileService(lendingCriteriaInfoService,commonServiceClient);
		List<CriteriaCodeEntry> mockResult = new ArrayList();
		CriteriaCodeEntry entryA = new CriteriaCodeEntry();
		mockResult.add(entryA);
		when(lendingCriteriaInfoService.getBusinessTypeCode(any())).thenReturn(mockResult);
		when(lendingCriteriaInfoService.getCriteriaByCatalogId(any())).thenReturn(mockResult);
		when(lendingCriteriaInfoService.getDefaultforCountryType(any())).thenReturn(mockResult);
		when(lendingCriteriaInfoService.getDefaultforSubBusinessType(any())).thenReturn(mockResult);
		when(lendingCriteriaInfoService.getOccupationByEmploymentStatus(any())).thenReturn(mockResult);
		when(lendingCriteriaInfoService.getOccupationInfoByCode(any())).thenReturn(mockResult);
		when(lendingCriteriaInfoService.getSourceOfIncome(any())).thenReturn(mockResult);
		when(lendingCriteriaInfoService.getSubBusinessType(any())).thenReturn(mockResult);
		when(lendingCriteriaInfoService.getWorkStatusByOccupationCode(any())).thenReturn(mockResult);
		controller = new WorkInformationController(lendingCriteriaInfoService, workInfoProfileService);
		controller.getBusinessTypeInfo("");
		controller.getCountryList("");
		controller.getOccupationByOccupationCode("", "");
		controller.getSourceOfIncomeInfo("", "");
		controller.getSubBusinessTypeInfo("", "");
		controller.getWorkInformationWithProfile("", "", "");
		controller.getWorkStatusInfoByOccupationCode("");
		Assertions.assertTrue(true);
	}

}
