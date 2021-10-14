package com.tmb.oneapp.lendingservice.service;

import com.tmb.common.exception.model.TMBCommonException;
import com.tmb.common.model.legacy.rsl.common.ob.dropdown.CommonCodeEntry;
import com.tmb.oneapp.lendingservice.LendingModuleCache;
import com.tmb.oneapp.lendingservice.constant.LoanCategory;
import com.tmb.oneapp.lendingservice.model.CriteriaCodeEntry;
import com.tmb.oneapp.lendingservice.util.CommonServiceUtils;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class LendingCriteriaInfoService {

    private LendingModuleCache lendingModuleCache;
    private static final String ACTIVE_STATUS = "1";
    private static final String CHANNEL_MIB = "MIB";

    @Autowired
    public LendingCriteriaInfoService(LendingModuleCache lendingModuleCache) {
        this.lendingModuleCache = lendingModuleCache;
    }

    /**
     * @param code
     * @return
     */
    public List<CriteriaCodeEntry> getWorkStatusByOccupationCode(String code) {
        List<CommonCodeEntry> commonCodeEntries = lendingModuleCache
                .getListByCategoryCode(LoanCategory.RM_OCCUPATION.getCode());
        Set<String> mappingExtValue1 = findOutMappingExt(commonCodeEntries, code);
        List<CommonCodeEntry> employmentEntrys = lendingModuleCache
                .getListByCategoryCode(LoanCategory.EMPLOYMENT_STATUS.getCode());
        return getCriteriaByEntryCode(employmentEntrys, mappingExtValue1);

    }

    /**
     * @param commonCodeEntries
     * @param code
     * @return
     */
    private Set<String> findOutMappingExt(List<CommonCodeEntry> commonCodeEntries, String code) {
        List<CommonCodeEntry> selectValue = new ArrayList();
        for (CommonCodeEntry entry : commonCodeEntries) {
            if (entry.getEntryCode().equals(code)) {
                selectValue.add(entry);
            }
        }
        Set<String> validStringSet = new HashSet();
        if (CollectionUtils.isNotEmpty(selectValue)) {
            for (CommonCodeEntry select : selectValue) {
                validStringSet.add(select.getExtValue1());
            }
        }

        return validStringSet;
    }

    /**
     * Get Criteria By entry code
     *
     * @param commonCodeEntries
     * @param mappingEntryCode
     * @return
     */
    private List<CriteriaCodeEntry> getCriteriaByEntryCode(List<CommonCodeEntry> commonCodeEntries,
                                                           Set<String> mappingEntryCode) {
        final List<CriteriaCodeEntry> responseCriterias = new ArrayList<>();
        for (CommonCodeEntry entry : commonCodeEntries) {
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
     * @param occupationCode
     * @return
     */
    public List<CriteriaCodeEntry> getOccupationInfoByCode(String occupationCode) {
        final List<CriteriaCodeEntry> responseCriterias = new ArrayList<>();
        List<CommonCodeEntry> commonCodeEntries = lendingModuleCache
                .getListByCategoryCode(LoanCategory.RM_OCCUPATION.getCode());
        for (CommonCodeEntry entry : commonCodeEntries) {
            if (StringUtils.isNotEmpty(occupationCode)
                    && ACTIVE_STATUS.equals(entry.getActiveStatus())
                    && occupationCode.equals(entry.getEntryCode())) {
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
        List<CommonCodeEntry> commonCodeEntries = lendingModuleCache
                .getListByCategoryCode(LoanCategory.BUSINESS_TYPE.getCode());
        for (CommonCodeEntry entry : commonCodeEntries) {
            if (StringUtils.isNotEmpty(bustypeEntryCode) && bustypeEntryCode.equals(entry.getEntryCode())) {
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
        List<CommonCodeEntry> commonCodeEntries = lendingModuleCache.getListByCategoryCode(employmentStatus.getCode());
        for (CommonCodeEntry entry : commonCodeEntries) {
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
        List<CommonCodeEntry> commonCodeEntries = lendingModuleCache
                .getListByCategoryCode(LoanCategory.RM_OCCUPATION.getCode());
        for (CommonCodeEntry entry : commonCodeEntries) {
            if (ACTIVE_STATUS.equals(entry.getActiveStatus())
                    && entry.getExtValue1().equals(reference)) {
                responseCriterias.add(setModelResponseInfo(entry));
            }
        }
        return responseCriterias;
    }

    /**
     * @param reference
     * @return
     */
    public List<CriteriaCodeEntry> getSubBusinessType(String reference) {
        final List<CriteriaCodeEntry> responseCriterias = new ArrayList<>();
        List<CommonCodeEntry> commonCodeEntries = lendingModuleCache
                .getListByCategoryCode(LoanCategory.BUSINESS_SUB_TYPE.getCode());
        for (CommonCodeEntry entry : commonCodeEntries) {
            if (CHANNEL_MIB.equals(entry.getExtValue1())
                    && ACTIVE_STATUS.equals(entry.getActiveStatus())
                    && reference.equals(entry.getRefEntryCode())) {
                responseCriterias.add(setModelResponseInfo(entry));
            }
        }
        return responseCriterias;
    }

    /**
     * @param reference
     * @return
     */
    public List<CriteriaCodeEntry> getSourceOfIncome(String reference) {
        final List<CriteriaCodeEntry> responseCriterias = new ArrayList<>();
        List<CommonCodeEntry> commonCodeEntries = lendingModuleCache
                .getListByCategoryCode(LoanCategory.INCOME_TYPE.getCode());
        for (CommonCodeEntry entry : commonCodeEntries) {
            if (CHANNEL_MIB.equals(entry.getExtValue1())
                    && StringUtils.isNotEmpty(reference)
                    && ACTIVE_STATUS.equals(entry.getActiveStatus())
                    && reference.equals(entry.getRefEntryCode())) {
                responseCriterias.add(setModelResponseInfo(entry));
            }
        }
        return responseCriterias;
    }

    /**
     * @param reference
     * @return
     */
    public List<CriteriaCodeEntry> getDefaultforSubBusinessType(String reference) {
        final List<CriteriaCodeEntry> responseCriterias = new ArrayList<>();
        List<CommonCodeEntry> commonCodeEntries = lendingModuleCache
                .getListByCategoryCode(LoanCategory.BUSINESS_SUB_TYPE.getCode());
        for (CommonCodeEntry entry : commonCodeEntries) {
            if (StringUtils.isNotEmpty(reference) && reference.equals(entry.getRefEntryCode())) {
                responseCriterias.add(setModelResponseInfo(entry));
            }
        }
        return responseCriterias;
    }

    /**
     * @param countryOfIncome
     * @return
     */
    public List<CriteriaCodeEntry> getDefaultforCountryType(String countryOfIncome) {
        final List<CriteriaCodeEntry> responseCriterias = new ArrayList<>();
        List<CommonCodeEntry> commonCodeEntries = lendingModuleCache
                .getListByCategoryCode(LoanCategory.SCI_COUNTRY.getCode());
        for (CommonCodeEntry entry : commonCodeEntries) {
            if (StringUtils.isNotEmpty(countryOfIncome) && countryOfIncome.equals(entry.getEntryCode())) {
                responseCriterias.add(setModelResponseInfo(entry));
            }
        }
        return responseCriterias;
    }

    public List<CriteriaCodeEntry> getBrmsEcmDocTypeByCode(String code) {
        final List<CriteriaCodeEntry> responseCriterias = new ArrayList<>();
        List<CommonCodeEntry> commonCodeEntries = lendingModuleCache
                .getListByCategoryCode(LoanCategory.BRMS_ECM_DOC_TYPE.getCode());
        for (CommonCodeEntry entry : commonCodeEntries) {
            if (StringUtils.isNotEmpty(code) && code.equals(entry.getEntryCode())) {
                responseCriterias.add(setModelResponseInfo(entry));
            }
        }
        return responseCriterias;
    }

    public List<CriteriaCodeEntry> getEmploymentStatus() {
        final List<CriteriaCodeEntry> responseCriterias = new ArrayList<>();
        List<CommonCodeEntry> commonCodeEntries = lendingModuleCache.getListByCategoryCode(LoanCategory.EMPLOYMENT_STATUS.getCode());
        for (CommonCodeEntry entry : commonCodeEntries) {
            if (ACTIVE_STATUS.equals(entry.getActiveStatus())) {
                responseCriterias.add(setModelResponseInfo(entry));
            }
        }
        return responseCriterias;
    }

    public List<CriteriaCodeEntry> getOccupationsByRmOccupation(String rmOccupation) throws TMBCommonException {
        final List<CriteriaCodeEntry> responseCriterias = new ArrayList<>();
        List<CommonCodeEntry> commonCodeEntries = lendingModuleCache.getListByCategoryCode(LoanCategory.PROFFESIONAL.getCode());
        for (CommonCodeEntry entry : commonCodeEntries) {
            if (ACTIVE_STATUS.equals(entry.getActiveStatus())
                    && CommonServiceUtils.parseStringToList(entry.getEntrySource()).contains(rmOccupation)) {
                responseCriterias.add(setModelResponseInfo(entry));
            }
        }
        return responseCriterias;
    }

    public List<CriteriaCodeEntry> getCountry() {
        final List<CriteriaCodeEntry> responseCriterias = new ArrayList<>();
        List<CommonCodeEntry> commonCodeEntries = lendingModuleCache
                .getListByCategoryCode(LoanCategory.SCI_COUNTRY.getCode());
        for (CommonCodeEntry entry : commonCodeEntries) {
            if (ACTIVE_STATUS.equals(entry.getActiveStatus())) {
                responseCriterias.add(setModelResponseInfo(entry));
            }
        }
        return responseCriterias;
    }

    public List<CriteriaCodeEntry> getBusinessType() {
        final List<CriteriaCodeEntry> responseCriterias = new ArrayList<>();
        List<CommonCodeEntry> commonCodeEntries = lendingModuleCache.getListByCategoryCode(LoanCategory.BUSINESS_TYPE.getCode());
        for (CommonCodeEntry entry : commonCodeEntries) {
            if (ACTIVE_STATUS.equals(entry.getActiveStatus())) {
                responseCriterias.add(setModelResponseInfo(entry));
            }
        }
        return responseCriterias;
    }

    public List<CriteriaCodeEntry> getPayrollBank() {
        final List<CriteriaCodeEntry> responseCriterias = new ArrayList<>();
        List<CommonCodeEntry> commonCodeEntries = lendingModuleCache.getListByCategoryCode(LoanCategory.PAYROLL_BANK.getCode());
        for (CommonCodeEntry entry : commonCodeEntries) {
            if (ACTIVE_STATUS.equals(entry.getActiveStatus())) {
                responseCriterias.add(setModelResponseInfo(entry));
            }
        }
        return responseCriterias;
    }

    public List<CriteriaCodeEntry> getMaritalStatusByCode(String maritalStatusCode) {
        final List<CriteriaCodeEntry> responseCriterias = new ArrayList<>();
        List<CommonCodeEntry> commonCodeEntries = lendingModuleCache.getListByCategoryCode(LoanCategory.MARITAL_STATUS.getCode());
        for (CommonCodeEntry entry : commonCodeEntries) {
            if (ACTIVE_STATUS.equals(entry.getActiveStatus())
                    && maritalStatusCode.equals(entry.getEntryCode())) {
                responseCriterias.add(setModelResponseInfo(entry));
            }
        }
        return responseCriterias;
    }

    public List<CriteriaCodeEntry> getResidentTypeByCode(String residentTypeCode) {
        final List<CriteriaCodeEntry> responseCriterias = new ArrayList<>();
        List<CommonCodeEntry> commonCodeEntries = lendingModuleCache.getListByCategoryCode(LoanCategory.RESIDENT_TYP.getCode());
        for (CommonCodeEntry entry : commonCodeEntries) {
            if (ACTIVE_STATUS.equals(entry.getActiveStatus())
                    && residentTypeCode.equals(entry.getEntryCode())) {
                responseCriterias.add(setModelResponseInfo(entry));
            }
        }
        return responseCriterias;
    }

    public List<CriteriaCodeEntry> getEducationLevel(String educationLevel) {
        final List<CriteriaCodeEntry> responseCriterias = new ArrayList<>();
        List<CommonCodeEntry> commonCodeEntries = lendingModuleCache.getListByCategoryCode(LoanCategory.EDUCATION_LEVEL.getCode());
        for (CommonCodeEntry entry : commonCodeEntries) {
            if (ACTIVE_STATUS.equals(entry.getActiveStatus())
                    && educationLevel.equals(entry.getEntryCode())) {
                responseCriterias.add(setModelResponseInfo(entry));
            }
        }
        return responseCriterias;
    }

}
