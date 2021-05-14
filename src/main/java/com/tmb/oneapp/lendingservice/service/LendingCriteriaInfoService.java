package com.tmb.oneapp.lendingservice.service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.tmb.common.model.legacy.rsl.common.ob.dropdown.CommonCodeEntry;
import com.tmb.oneapp.lendingservice.LendingModuleCache;
import com.tmb.oneapp.lendingservice.constant.LoanCategory;
import com.tmb.oneapp.lendingservice.model.CriteriaCodeEntry;

@Service
public class LendingCriteriaInfoService {

	private LendingModuleCache lendingModuleCache;

	private LendingCriteriaInfoService(LendingModuleCache lendingModuleCache) {
		this.lendingModuleCache = lendingModuleCache;
	}

	/**
	 * 
	 * @param code
	 * @return
	 */
	public List<CriteriaCodeEntry> getWorkStatusByOccupationCode(String code) {
		List<CommonCodeEntry> commonCodeEntrys = lendingModuleCache
				.getListByCategoryCode(LoanCategory.RM_OCCUPATION.getCode());
		List<String> mappingExtValue1 = commonCodeEntrys.stream().map(e -> e.getExtValue1())
				.collect(Collectors.toList());
		return getWorkStatusInformation(commonCodeEntrys, mappingExtValue1);

	}

	/**
	 * 
	 * @param commonCodeEntrys
	 * @param occupationCode
	 * @return
	 */
	private List<CriteriaCodeEntry> getWorkStatusInformation(List<CommonCodeEntry> commonCodeEntrys,
			List<String> mappingExtValue1) {
		final List<CriteriaCodeEntry> responseCriterias = new ArrayList<CriteriaCodeEntry>();
		List<CommonCodeEntry> employmentEntrys = lendingModuleCache
				.getListByCategoryCode(LoanCategory.EMPLOYMENT_STATUS.getCode());
		for (CommonCodeEntry entry : commonCodeEntrys) {
			for (String value : mappingExtValue1) {
				if (value.equals(entry.getEntryCode())) {
					commonCodeEntrys.add(entry);
				}
			}
		}
		return responseCriterias;
	}

}
