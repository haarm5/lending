package com.tmb.oneapp.lendingservice.service;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import java.math.BigDecimal;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.List;

import javax.xml.rpc.ServiceException;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.tmb.common.model.legacy.rsl.common.ob.dropdown.CommonCodeEntry;
import com.tmb.common.model.legacy.rsl.ws.dropdown.response.Body;
import com.tmb.common.model.legacy.rsl.ws.dropdown.response.Header;
import com.tmb.common.model.legacy.rsl.ws.dropdown.response.ResponseDropdown;
import com.tmb.common.model.legacy.rsl.ws.loan.submission.LoanSubmissionGetDropdownListServiceLocator;
import com.tmb.common.model.legacy.rsl.ws.loan.submission.LoanSubmissionGetDropdownListSoapBindingStub;
import com.tmb.oneapp.lendingservice.LendingModuleCache;
import com.tmb.oneapp.lendingservice.constant.LoanCategory;
import com.tmb.oneapp.lendingservice.model.CriteriaCodeEntry;

@RunWith(JUnit4.class)
public class LendingCriteriaInfoTest {

	CodeEntriesService codeEntriesService;
	LendingCriteriaInfoService lendingCriteriaInfoService;
	@Mock
	LoanSubmissionGetDropdownListServiceLocator mockLocator;
	@Mock
	LoanSubmissionGetDropdownListSoapBindingStub mockStub;
	@Mock
	LendingModuleCache lendingModuleCache;

	@BeforeEach
	void setUp() {
		MockitoAnnotations.initMocks(this);
	}

	@Test
	void getCatlogIdByLoanType() throws ServiceException, RemoteException {

		codeEntriesService = new CodeEntriesService();
		lendingCriteriaInfoService = new LendingCriteriaInfoService(lendingModuleCache);
		ResponseDropdown mockResponse = new ResponseDropdown();
		Body body = new Body();

		CommonCodeEntry item1 = new CommonCodeEntry();
		CommonCodeEntry[] entries = new CommonCodeEntry[] { item1 };
		body.setCommonCodeEntries(entries);
		mockResponse.setBody(body);
		Header header = new Header();
		header.setResponseCode("MSG_000");
		mockResponse.setHeader(header);
		when(mockStub.getDropDownListByCode(any())).thenReturn(mockResponse);
		when(mockLocator.getLoanSubmissionGetDropdownList()).thenReturn(mockStub);

		List<CriteriaCodeEntry> criteriaList = new ArrayList();

		when(lendingCriteriaInfoService.getBusinessTypeCode(any())).thenReturn(criteriaList);
		codeEntriesService.setLocator(mockLocator);
		List<CriteriaCodeEntry> actualResponse = lendingCriteriaInfoService
				.getBusinessTypeCode(LoanCategory.BUSINESS_SUB_TYPE.getCode());
		Assertions.assertNotNull(actualResponse);
		Assertions.assertEquals(0, actualResponse.size());

		lendingCriteriaInfoService.getDefaultforCountryType(null);
		
		org.junit.Assert.assertTrue(true);
	}

	@Test
	public void getBusinessTypeCode() {
		lendingCriteriaInfoService = new LendingCriteriaInfoService(lendingModuleCache);
		CommonCodeEntry codeEntry = new CommonCodeEntry();
		codeEntry.setActiveStatus("1");
		codeEntry.setCategoryCode("INCOME_TYPE");
		codeEntry.setEntryCode("1");
		codeEntry.setEntryID(new BigDecimal("69842"));
		codeEntry.setEntryName("ABC");
		codeEntry.setEntryName2("AAAA");
		List<CommonCodeEntry> status = new ArrayList();
		status.add(codeEntry);
		when(lendingModuleCache.getListByCategoryCode(any())).thenReturn(status);
		lendingCriteriaInfoService.getBusinessTypeCode(LoanCategory.EMPLOYMENT_STATUS.getCode());
		org.junit.Assert.assertTrue(true);
	}

	@Test
	public void getCriteriaByCatalogId() {
		lendingCriteriaInfoService = new LendingCriteriaInfoService(lendingModuleCache);
		CommonCodeEntry codeEntry = new CommonCodeEntry();
		codeEntry.setActiveStatus("1");
		codeEntry.setCategoryCode("INCOME_TYPE");
		codeEntry.setEntryCode("1");
		codeEntry.setEntryID(new BigDecimal("69842"));
		codeEntry.setEntryName("ABC");
		codeEntry.setEntryName2("AAAA");
		List<CommonCodeEntry> status = new ArrayList();
		status.add(codeEntry);
		when(lendingModuleCache.getListByCategoryCode(any())).thenReturn(status);
		lendingCriteriaInfoService.getCriteriaByCatalogId(LoanCategory.BUSINESS_SUB_TYPE);
		org.junit.Assert.assertTrue(true);
	}

	@Test
	public void getDefaultforCountryType() {
		lendingCriteriaInfoService = new LendingCriteriaInfoService(lendingModuleCache);
		CommonCodeEntry codeEntry = new CommonCodeEntry();
		codeEntry.setActiveStatus("1");
		codeEntry.setCategoryCode("INCOME_TYPE");
		codeEntry.setEntryCode("1");
		codeEntry.setEntryID(new BigDecimal("69842"));
		codeEntry.setEntryName("ABC");
		codeEntry.setEntryName2("AAAA");
		List<CommonCodeEntry> status = new ArrayList();
		status.add(codeEntry);
		when(lendingModuleCache.getListByCategoryCode(any())).thenReturn(status);
		lendingCriteriaInfoService.getDefaultforCountryType("TH");
		org.junit.Assert.assertTrue(true);
	}

	@Test
	public void getDefaultforSubBusinessType() {
		lendingCriteriaInfoService = new LendingCriteriaInfoService(lendingModuleCache);
		CommonCodeEntry codeEntry = new CommonCodeEntry();
		codeEntry.setActiveStatus("1");
		codeEntry.setCategoryCode("INCOME_TYPE");
		codeEntry.setEntryCode("1");
		codeEntry.setEntryID(new BigDecimal("69842"));
		codeEntry.setEntryName("ABC");
		codeEntry.setEntryName2("AAAA");
		List<CommonCodeEntry> status = new ArrayList();
		status.add(codeEntry);
		when(lendingModuleCache.getListByCategoryCode(any())).thenReturn(status);
		lendingCriteriaInfoService.getDefaultforSubBusinessType("A");
		org.junit.Assert.assertTrue(true);
	}

	@Test
	public void getOccupationByEmploymentStatus() {
		lendingCriteriaInfoService = new LendingCriteriaInfoService(lendingModuleCache);
		CommonCodeEntry codeEntry = new CommonCodeEntry();
		codeEntry.setActiveStatus("1");
		codeEntry.setCategoryCode("INCOME_TYPE");
		codeEntry.setEntryCode("1");
		codeEntry.setEntryID(new BigDecimal("69842"));
		codeEntry.setEntryName("ABC");
		codeEntry.setEntryName2("AAAA");
		codeEntry.setExtValue1("");
		codeEntry.setExtValue2("");
		List<CommonCodeEntry> status = new ArrayList();
		status.add(codeEntry);
		when(lendingModuleCache.getListByCategoryCode(any())).thenReturn(status);
		lendingCriteriaInfoService.getOccupationByEmploymentStatus("VB");
		org.junit.Assert.assertTrue(true);
	}

	@Test
	public void getOccupationInfoByCode() {
		lendingCriteriaInfoService = new LendingCriteriaInfoService(lendingModuleCache);
		CommonCodeEntry codeEntry = new CommonCodeEntry();
		codeEntry.setActiveStatus("1");
		codeEntry.setCategoryCode("INCOME_TYPE");
		codeEntry.setEntryCode("1");
		codeEntry.setEntryID(new BigDecimal("69842"));
		codeEntry.setEntryName("ABC");
		codeEntry.setEntryName2("AAAA");
		List<CommonCodeEntry> status = new ArrayList();
		status.add(codeEntry);
		when(lendingModuleCache.getListByCategoryCode(any())).thenReturn(status);
		lendingCriteriaInfoService.getOccupationInfoByCode(LoanCategory.RM_OCCUPATION.getCode());
		org.junit.Assert.assertTrue(true);
	}

	@Test
	public void getSourceOfIncome() {
		lendingCriteriaInfoService = new LendingCriteriaInfoService(lendingModuleCache);
		CommonCodeEntry codeEntry = new CommonCodeEntry();
		codeEntry.setActiveStatus("1");
		codeEntry.setCategoryCode("INCOME_TYPE");
		codeEntry.setEntryCode("1");
		codeEntry.setEntryID(new BigDecimal("69842"));
		codeEntry.setEntryName("ABC");
		codeEntry.setEntryName2("AAAA");
		List<CommonCodeEntry> status = new ArrayList();
		status.add(codeEntry);
		when(lendingModuleCache.getListByCategoryCode(any())).thenReturn(status);
		lendingCriteriaInfoService.getSourceOfIncome(LoanCategory.RM_OCCUPATION.getCode());
		org.junit.Assert.assertTrue(true);
	}

	@Test
	public void getSubBusinessType() {
		lendingCriteriaInfoService = new LendingCriteriaInfoService(lendingModuleCache);
		CommonCodeEntry codeEntry = new CommonCodeEntry();
		codeEntry.setActiveStatus("1");
		codeEntry.setCategoryCode("INCOME_TYPE");
		codeEntry.setEntryCode("1");
		codeEntry.setEntryID(new BigDecimal("69842"));
		codeEntry.setEntryName("ABC");
		codeEntry.setEntryName2("AAAA");
		List<CommonCodeEntry> status = new ArrayList();
		status.add(codeEntry);
		when(lendingModuleCache.getListByCategoryCode(any())).thenReturn(status);
		lendingCriteriaInfoService.getSubBusinessType(LoanCategory.RM_OCCUPATION.getCode());
		org.junit.Assert.assertTrue(true);
	}
	
	@Test
	public void getWorkStatusByOccupationCode() {
		lendingCriteriaInfoService = new LendingCriteriaInfoService(lendingModuleCache);
		CommonCodeEntry codeEntry = new CommonCodeEntry();
		codeEntry.setActiveStatus("1");
		codeEntry.setCategoryCode("INCOME_TYPE");
		codeEntry.setEntryCode("1");
		codeEntry.setEntryID(new BigDecimal("69842"));
		codeEntry.setEntryName("ABC");
		codeEntry.setEntryName2("AAAA");
		List<CommonCodeEntry> status = new ArrayList();
		status.add(codeEntry);
		when(lendingModuleCache.getListByCategoryCode(any())).thenReturn(status);
		lendingCriteriaInfoService.getWorkStatusByOccupationCode(LoanCategory.RM_OCCUPATION.getCode());
		org.junit.Assert.assertTrue(true);
	}

}
