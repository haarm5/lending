package com.tmb.oneapp.lendingservice.service;

import java.util.List;

import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tmb.oneapp.lendingservice.constant.LoanCategory;
import com.tmb.oneapp.lendingservice.model.CriteriaCodeEntry;
import com.tmb.oneapp.lendingservice.model.response.SelectCodeEntry;
import com.tmb.oneapp.lendingservice.model.response.WorkInfoEntryResp;

@Service
public class WorkInfoProfileService {

	private LendingCriteriaInfoService lendingCriteriaInfoService;

	@Autowired
	public WorkInfoProfileService(LendingCriteriaInfoService lendingCriteriaInfoService) {
		this.lendingCriteriaInfoService = lendingCriteriaInfoService;
	}

	public WorkInfoEntryResp createWorkInformationModel(String occupationCode, String businessTypeCode,
			String countryOfIncome) {
		WorkInfoEntryResp response = new WorkInfoEntryResp();

		SelectCodeEntry selectWorkstatusCodeEntry = new SelectCodeEntry();
		List<CriteriaCodeEntry> workStatusByOccupationList = lendingCriteriaInfoService
				.getWorkStatusByOccupationCode(occupationCode);
		if (CollectionUtils.isNotEmpty(workStatusByOccupationList)) {
			CriteriaCodeEntry defaultEntry = workStatusByOccupationList.get(0);
			selectWorkstatusCodeEntry.setName(defaultEntry.getEntryName2());
			selectWorkstatusCodeEntry.setValue(defaultEntry.getEntryCode());
		}
		List<CriteriaCodeEntry> relateWorkStatusCodeEntry = lendingCriteriaInfoService
				.getCriteriaByCatalogId(LoanCategory.EMPLOYMENT_STATUS);
		selectWorkstatusCodeEntry.setRelateCodeEntry(relateWorkStatusCodeEntry);
		response.setWorkstatus(selectWorkstatusCodeEntry);

		SelectCodeEntry selectOccupationCodeEntry = new SelectCodeEntry();
		List<CriteriaCodeEntry> occupationCodeList = lendingCriteriaInfoService.getOccupationInfoByCode(occupationCode);
		if (CollectionUtils.isNotEmpty(occupationCodeList)) {
			CriteriaCodeEntry defaultEntry = occupationCodeList.get(0);
			selectOccupationCodeEntry.setName(defaultEntry.getEntryName());
			selectOccupationCodeEntry.setValue(defaultEntry.getEntryCode());

		}
		List<CriteriaCodeEntry> relateOccupationCodeEntry = lendingCriteriaInfoService
				.getOccupationByEmploymentStatus(response.getWorkstatus().getValue());
		selectOccupationCodeEntry.setRelateCodeEntry(relateOccupationCodeEntry);
		response.setOccupation(selectOccupationCodeEntry);

		SelectCodeEntry selectBusTypeCodeEntry = new SelectCodeEntry();
		List<CriteriaCodeEntry> busTypeCodes = lendingCriteriaInfoService.getBusinessTypeCode(businessTypeCode);
		if (CollectionUtils.isNotEmpty(busTypeCodes)) {
			CriteriaCodeEntry defaultEntry = busTypeCodes.get(0);
			selectBusTypeCodeEntry.setName(defaultEntry.getEntryName());
			selectBusTypeCodeEntry.setValue(defaultEntry.getEntryCode());
		}
		List<CriteriaCodeEntry> relateBusTypeCodeEntry = lendingCriteriaInfoService
				.getCriteriaByCatalogId(LoanCategory.BUSINESS_TYPE);
		selectBusTypeCodeEntry.setRelateCodeEntry(relateBusTypeCodeEntry);
		response.setBusinessType(selectBusTypeCodeEntry);

		SelectCodeEntry selectBusSubTypeCodeEntry = new SelectCodeEntry();
		List<CriteriaCodeEntry> busSubTypes = lendingCriteriaInfoService.getDefaultforSubBusinessType(businessTypeCode);
		if (CollectionUtils.isNotEmpty(busSubTypes)) {
			CriteriaCodeEntry defaultEntry = busSubTypes.get(0);
			selectBusSubTypeCodeEntry.setName(defaultEntry.getExtValue2());
			selectBusSubTypeCodeEntry.setValue(defaultEntry.getEntryCode());
		}
		List<CriteriaCodeEntry> relateBusSubCodeEntry = lendingCriteriaInfoService
				.getSubBusinessType(selectBusTypeCodeEntry.getValue());
		selectBusSubTypeCodeEntry.setRelateCodeEntry(relateBusSubCodeEntry);
		response.setSubBusinessType(selectBusSubTypeCodeEntry);

		SelectCodeEntry sourceOfIncomeEntry = new SelectCodeEntry();
		
		List<CriteriaCodeEntry> relateSourceOfIncomeCodeEntry = lendingCriteriaInfoService
				.getSourceOfIncome(response.getWorkstatus().getValue());
		sourceOfIncomeEntry.setRelateCodeEntry(relateSourceOfIncomeCodeEntry);
		if (CollectionUtils.isNotEmpty(relateSourceOfIncomeCodeEntry)) {
			CriteriaCodeEntry defaultEntry = relateSourceOfIncomeCodeEntry.get(0);
			sourceOfIncomeEntry.setName(defaultEntry.getExtValue2());
			sourceOfIncomeEntry.setValue(defaultEntry.getEntryCode());
		}
		response.setSourceIncomes(sourceOfIncomeEntry);

		SelectCodeEntry sourceCountryEntry = new SelectCodeEntry();
		List<CriteriaCodeEntry> sourceOfCountry = lendingCriteriaInfoService.getDefaultforCountryType(countryOfIncome);
		if (CollectionUtils.isNotEmpty(sourceOfCountry)) {
			CriteriaCodeEntry defaultEntry = sourceOfCountry.get(0);
			sourceCountryEntry.setName(defaultEntry.getEntryName());
			sourceCountryEntry.setValue(defaultEntry.getEntryCode());
		}
		List<CriteriaCodeEntry> countryList = lendingCriteriaInfoService
				.getCriteriaByCatalogId(LoanCategory.SCI_COUNTRY);
		sourceCountryEntry.setRelateCodeEntry(countryList);
		response.setCountryIncomes(sourceCountryEntry);

		return response;
	}

}
