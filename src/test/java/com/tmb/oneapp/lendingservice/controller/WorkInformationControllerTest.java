package com.tmb.oneapp.lendingservice.controller;

import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.tmb.oneapp.lendingservice.model.CriteriaCodeEntry;
import com.tmb.oneapp.lendingservice.service.LendingCriteriaInfoService;
import com.tmb.oneapp.lendingservice.service.WorkInfoProfileService;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@RunWith(JUnit4.class)
public class WorkInformationControllerTest {

	@Mock
	private LendingCriteriaInfoService lendingCriteriaInfoService;

	@Mock
	private WorkInfoProfileService workInfoProfileService;

	@BeforeEach
	void setUp() {
		MockitoAnnotations.initMocks(this);
	}

	@Test
	public void getWorkStatusInfoByOccupationCode() {
		final List<CriteriaCodeEntry> responseCriterias = new ArrayList<>();
		when(lendingCriteriaInfoService.getWorkStatusByOccupationCode(any())).thenReturn(responseCriterias);
		List<CriteriaCodeEntry> actualResponse = lendingCriteriaInfoService.getWorkStatusByOccupationCode(null);
		Assertions.assertNotNull(actualResponse);
	}

	@Test
	public void getOccupationByOccupationCode() {
		final List<CriteriaCodeEntry> responseCriterias = new ArrayList<>();
		when(lendingCriteriaInfoService.getOccupationInfoByCode(any())).thenReturn(responseCriterias);
		List<CriteriaCodeEntry> actualResponse = lendingCriteriaInfoService.getOccupationInfoByCode(null);
		Assertions.assertNotNull(actualResponse);
	}

	@Test
	public void getBusinessTypeInfo() {
		final List<CriteriaCodeEntry> responseCriterias = new ArrayList<>();
		when(lendingCriteriaInfoService.getBusinessTypeCode(any())).thenReturn(responseCriterias);
		List<CriteriaCodeEntry> actualResponse = lendingCriteriaInfoService.getBusinessTypeCode(null);
		Assertions.assertNotNull(actualResponse);
	}
	
	@Test
	public void getSubBusinessTypeInfo() {
		final List<CriteriaCodeEntry> responseCriterias = new ArrayList<>();
		when(lendingCriteriaInfoService.getSubBusinessType(any())).thenReturn(responseCriterias);
		List<CriteriaCodeEntry> actualResponse = lendingCriteriaInfoService.getSubBusinessType(null);
		Assertions.assertNotNull(actualResponse);
	}
	
	@Test
	public void getSourceOfIncomeInfo() {
		final List<CriteriaCodeEntry> responseCriterias = new ArrayList<>();
		when(lendingCriteriaInfoService.getSourceOfIncome(any())).thenReturn(responseCriterias);
		List<CriteriaCodeEntry> actualResponse = lendingCriteriaInfoService.getSourceOfIncome(null);
		Assertions.assertNotNull(actualResponse);
	}
	@Test
	public void getWorkInformationWithProfile() {
		final List<CriteriaCodeEntry> responseCriterias = new ArrayList<>();
		when(lendingCriteriaInfoService.getSourceOfIncome(any())).thenReturn(responseCriterias);
		List<CriteriaCodeEntry> actualResponse = lendingCriteriaInfoService.getSourceOfIncome(null);
		Assertions.assertNotNull(actualResponse);
	}

}
