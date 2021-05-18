package com.tmb.oneapp.lendingservice.service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.tmb.oneapp.lendingservice.model.CriteriaCodeEntry;
import com.tmb.oneapp.lendingservice.model.response.WorkInfoEntryResp;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

@RunWith(JUnit4.class)
public class WorkInfoProfileServiceTest {

	private WorkInfoProfileService workInfoProfileService;

	@Mock
	private LendingCriteriaInfoService lendingCriteriaInfoService;

	@BeforeEach
	void setUp() {
		MockitoAnnotations.initMocks(this);
	}


	@Test
	public void testCode() {
		workInfoProfileService = new WorkInfoProfileService(lendingCriteriaInfoService);
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

		WorkInfoEntryResp workInfo = workInfoProfileService.createWorkInformationModel("304", "A", "TH");
		Assertions.assertNotNull(workInfo);
	}

}
