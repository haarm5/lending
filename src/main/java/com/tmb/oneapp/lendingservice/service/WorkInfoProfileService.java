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
			selectWorkstatusCodeEntry.setName(defaultEntry.getEntryName());
			selectWorkstatusCodeEntry.setValue(defaultEntry.getEntryCode());

			List<CriteriaCodeEntry> relateCodeEntry = lendingCriteriaInfoService
					.getCriteriaByCatalogId(LoanCategory.EMPLOYMENT_STATUS);
			selectWorkstatusCodeEntry.setRelateCodeEntry(relateCodeEntry);
		}
		response.setWorkstatus(selectWorkstatusCodeEntry);

		SelectCodeEntry selectOccupationCodeEntry = new SelectCodeEntry();
		List<CriteriaCodeEntry> occupationCodeList = lendingCriteriaInfoService.getOccupationInfoByCode(occupationCode);
		if (CollectionUtils.isNotEmpty(occupationCodeList)) {
			CriteriaCodeEntry defaultEntry = occupationCodeList.get(0);
			selectOccupationCodeEntry.setName(defaultEntry.getEntryName());
			selectOccupationCodeEntry.setValue(defaultEntry.getEntryCode());
			List<CriteriaCodeEntry> relateCodeEntry = lendingCriteriaInfoService
					.getOccupationByEmploymentStatus(response.getWorkstatus().getValue());
			selectOccupationCodeEntry.setRelateCodeEntry(relateCodeEntry);
		}
		response.setOccupation(selectOccupationCodeEntry);
		
		
		SelectCodeEntry selectBusTypeCodeEntry = new SelectCodeEntry();
		List<CriteriaCodeEntry> busTypeCodes = lendingCriteriaInfoService.getBusinessTypeCode(businessTypeCode);
		if(CollectionUtils.isNotEmpty(busTypeCodes)) {
			CriteriaCodeEntry defaultEntry = busTypeCodes.get(0);
			selectBusTypeCodeEntry.setName(defaultEntry.getEntryName());
			selectBusTypeCodeEntry.setValue(defaultEntry.getEntryCode());
			List<CriteriaCodeEntry> relateCodeEntry = lendingCriteriaInfoService.getCriteriaByCatalogId(LoanCategory.BUSINESS_TYPE);
			selectBusTypeCodeEntry.setRelateCodeEntry(relateCodeEntry);
		}
		response.setBusinessType(selectBusTypeCodeEntry);

		SelectCodeEntry selectBusSubTypeCodeEntry = new SelectCodeEntry();
		response.setSubBusinessType(selectBusSubTypeCodeEntry);

		return response;
	}

}
