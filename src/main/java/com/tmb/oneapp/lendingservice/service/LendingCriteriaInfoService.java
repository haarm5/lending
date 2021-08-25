package com.tmb.oneapp.lendingservice.service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tmb.common.model.legacy.rsl.common.ob.dropdown.CommonCodeEntry;
import com.tmb.oneapp.lendingservice.LendingModuleCache;
import com.tmb.oneapp.lendingservice.constant.LoanCategory;
import com.tmb.oneapp.lendingservice.model.CriteriaCodeEntry;

@Service
public class LendingCriteriaInfoService {

	private LendingModuleCache lendingModuleCache;

	@Autowired
	public LendingCriteriaInfoService(LendingModuleCache lendingModuleCache) {
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
		Set<String> mappingExtValue1 = findOutMappingExt(commonCodeEntrys, code);
		List<CommonCodeEntry> employmentEntrys = lendingModuleCache
				.getListByCategoryCode(LoanCategory.EMPLOYMENT_STATUS.getCode());
		return getCriteriaByEntryCode(employmentEntrys, mappingExtValue1);

	}

	/**
	 * 
	 * @param commonCodeEntrys
	 * @param code
	 * @return
	 */
	private Set<String> findOutMappingExt(List<CommonCodeEntry> commonCodeEntrys, String code) {
		List<CommonCodeEntry> selctValue = new ArrayList();
		for (CommonCodeEntry entry : commonCodeEntrys) {
			if (entry.getEntryCode().equals(code)) {
				selctValue.add(entry);
			}
		}
		Set<String> validStringSet = new HashSet();
		if (CollectionUtils.isNotEmpty(selctValue)) {
			for (CommonCodeEntry select : selctValue) {
				validStringSet.add(select.getExtValue1());
			}
		}

		return validStringSet;
	}

	/**
	 * Get Criteria By entry code
	 * 
	 * @param commonCodeEntrys
	 * @param mappingEntryCode
	 * @return
	 */
	private List<CriteriaCodeEntry> getCriteriaByEntryCode(List<CommonCodeEntry> commonCodeEntrys,
			Set<String> mappingEntryCode) {
		final List<CriteriaCodeEntry> responseCriterias = new ArrayList<>();
		for (CommonCodeEntry entry : commonCodeEntrys) {
			for (String value : mappingEntryCode) {
				if (value.equals(entry.getEntryCode())) {
					responseCriterias.add(setModelResponseInfo(entry));
				}
			}
		}
		return responseCriterias;
	}

	/**
	 * Get Occupation By code
	 * 
	 * @param occcupationCode
	 * @return
	 */
	public List<CriteriaCodeEntry> getOccupationInfoByCode(String occcupationCode) {
		final List<CriteriaCodeEntry> responseCriterias = new ArrayList<>();
		List<CommonCodeEntry> commonCodeEntrys = lendingModuleCache
				.getListByCategoryCode(LoanCategory.RM_OCCUPATION.getCode());
		for (CommonCodeEntry entry : commonCodeEntrys) {
			if (occcupationCode.equals(entry.getEntryCode())) {
				responseCriterias.add(setModelResponseInfo(entry));
			}
		}
		return responseCriterias;
	}

	/**
	 * Get Business type code
	 * 
	 * @param bustypeEntryCode
	 * @return
	 */
	public List<CriteriaCodeEntry> getBusinessTypeCode(String bustypeEntryCode) {
		final List<CriteriaCodeEntry> responseCriterias = new ArrayList<>();
		List<CommonCodeEntry> commonCodeEntrys = lendingModuleCache
				.getListByCategoryCode(LoanCategory.BUSINESS_TYPE.getCode());
		for (CommonCodeEntry entry : commonCodeEntrys) {
			if (bustypeEntryCode.equals(entry.getEntryCode())) {
				responseCriterias.add(setModelResponseInfo(entry));
			}
		}
		return responseCriterias;
	}

	/**
	 * Set Response model
	 * 
	 * @param entry
	 * @return
	 */
	private CriteriaCodeEntry setModelResponseInfo(CommonCodeEntry entry) {
		CriteriaCodeEntry codeEntry = new CriteriaCodeEntry();
		codeEntry.setActiveStatus(entry.getActiveStatus());
		codeEntry.setCategoryCode(entry.getCategoryCode());
		codeEntry.setEntryCode(entry.getEntryCode());
		codeEntry.setEntryID(entry.getEntryID().toString());
		codeEntry.setEntryName(entry.getEntryName());
		codeEntry.setEntryName2(entry.getEntryName2());
		codeEntry.setEntrySource(entry.getEntrySource());
		codeEntry.setExtValue1(entry.getExtValue1());
		codeEntry.setExtValue2(entry.getExtValue2());
		codeEntry.setGroupId(entry.getGroupId());
		codeEntry.setRefEntryCode(entry.getRefEntryCode());
		return codeEntry;
	}

	/**
	 * Get criteria by catalog id
	 * 
	 * @param employmentStatus
	 * @return
	 */
	public List<CriteriaCodeEntry> getCriteriaByCatalogId(LoanCategory employmentStatus) {
		final List<CriteriaCodeEntry> responseCriterias = new ArrayList<>();
		List<CommonCodeEntry> commonCodeEntrys = lendingModuleCache.getListByCategoryCode(employmentStatus.getCode());
		for (CommonCodeEntry entry : commonCodeEntrys) {
			responseCriterias.add(setModelResponseInfo(entry));
		}
		return responseCriterias;
	}

	/**
	 * Get occupation by employment status by entry code
	 * 
	 * @param reference
	 * @return
	 */
	public List<CriteriaCodeEntry> getOccupationByEmploymentStatus(String reference) {
		final List<CriteriaCodeEntry> responseCriterias = new ArrayList<>();
		List<CommonCodeEntry> commonCodeEntrys = lendingModuleCache
				.getListByCategoryCode(LoanCategory.RM_OCCUPATION.getCode());
		for (CommonCodeEntry entry : commonCodeEntrys) {
			if (entry.getExtValue1().equals(reference)) {
				responseCriterias.add(setModelResponseInfo(entry));
			}
		}
		return responseCriterias;
	}

	/**
	 * 
	 * @param reference
	 * @return
	 */
	public List<CriteriaCodeEntry> getSubBusinessType(String reference) {
		final List<CriteriaCodeEntry> responseCriterias = new ArrayList<>();
		List<CommonCodeEntry> commonCodeEntrys = lendingModuleCache
				.getListByCategoryCode(LoanCategory.BUSINESS_SUB_TYPE.getCode());
		for (CommonCodeEntry entry : commonCodeEntrys) {
			if ("MIB".equals(entry.getExtValue1()) && reference.equals(entry.getRefEntryCode())) {
				responseCriterias.add(setModelResponseInfo(entry));
			}
		}
		return responseCriterias;
	}

	/**
	 * 
	 * @param reference
	 * @return
	 */
	public List<CriteriaCodeEntry> getSourceOfIncome(String reference) {
		final List<CriteriaCodeEntry> responseCriterias = new ArrayList<>();
		List<CommonCodeEntry> commonCodeEntrys = lendingModuleCache
				.getListByCategoryCode(LoanCategory.INCOME_TYPE.getCode());
		for (CommonCodeEntry entry : commonCodeEntrys) {
			if ("MIB".equals(entry.getExtValue1()) && reference.equals(entry.getRefEntryCode())) {
				responseCriterias.add(setModelResponseInfo(entry));
			}
		}
		return responseCriterias;
	}

	/**
	 * 
	 * @param businessTypeCode
	 * @return
	 */
	public List<CriteriaCodeEntry> getDefaultforSubBusinessType(String reference) {
		final List<CriteriaCodeEntry> responseCriterias = new ArrayList<>();
		List<CommonCodeEntry> commonCodeEntrys = lendingModuleCache
				.getListByCategoryCode(LoanCategory.BUSINESS_SUB_TYPE.getCode());
		for (CommonCodeEntry entry : commonCodeEntrys) {
			if (reference.equals(entry.getRefEntryCode())) {
				responseCriterias.add(setModelResponseInfo(entry));
			}
		}
		return responseCriterias;
	}

	/**
	 * 
	 * @param countryOfIncome
	 * @return
	 */
	public List<CriteriaCodeEntry> getDefaultforCountryType(String countryOfIncome) {
		final List<CriteriaCodeEntry> responseCriterias = new ArrayList<>();
		List<CommonCodeEntry> commonCodeEntrys = lendingModuleCache
				.getListByCategoryCode(LoanCategory.SCI_COUNTRY.getCode());
		for (CommonCodeEntry entry : commonCodeEntrys) {
			if (countryOfIncome.equals(entry.getEntryCode())) {
				responseCriterias.add(setModelResponseInfo(entry));
			}
		}
		return responseCriterias;
	}

	public List<CriteriaCodeEntry> getBrmsEcmDocTypeByCode(String code) {
		final List<CriteriaCodeEntry> responseCriterias = new ArrayList<>();
		List<CommonCodeEntry> commonCodeEntrys = lendingModuleCache
				.getListByCategoryCode(LoanCategory.BRMS_ECM_DOC_TYPE.getCode());
		for (CommonCodeEntry entry : commonCodeEntrys) {
			if (code.equals(entry.getEntryCode())) {
				responseCriterias.add(setModelResponseInfo(entry));
			}
		}
		return responseCriterias;
	}

}
