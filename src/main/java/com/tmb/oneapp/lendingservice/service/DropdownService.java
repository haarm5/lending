package com.tmb.oneapp.lendingservice.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.tmb.common.exception.model.TMBCommonException;
import com.tmb.common.logger.TMBLogger;
import com.tmb.common.model.CustGeneralProfileResponse;
import com.tmb.common.model.LovMaster;
import com.tmb.common.model.TmbOneServiceResponse;
import com.tmb.common.util.TMBUtils;
import com.tmb.oneapp.lendingservice.client.CommonServiceFeignClient;
import com.tmb.oneapp.lendingservice.constant.LoanCategory;
import com.tmb.oneapp.lendingservice.constant.ResponseCode;
import com.tmb.oneapp.lendingservice.model.CriteriaCodeEntry;
import com.tmb.oneapp.lendingservice.model.dropdown.Dropdowns;
import com.tmb.oneapp.lendingservice.model.dropdown.DropdownsLoanSubmissionWorkingDetail;
import com.tmb.oneapp.lendingservice.util.CommonServiceUtils;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class DropdownService {
    private static final TMBLogger<DropdownService> logger = new TMBLogger<>(DropdownService.class);

    private final LoanOnlineSubmissionGetPersonalDetailService loanOnlineSubmissionGetPersonalDetailService;
    private final CommonServiceFeignClient commonServiceFeignClient;
    private final LendingCriteriaInfoService lendingCriteriaInfoService;

    private static final String CHANNEL_MIB = "MIB";
    private static final String ACTIVE_STATUS = "1";

    public DropdownsLoanSubmissionWorkingDetail getDropdownsLoanSubmissionWorkingDetail(String correlationId, String crmId) throws TMBCommonException, JsonProcessingException {
        CustGeneralProfileResponse customerInfo = loanOnlineSubmissionGetPersonalDetailService.getCustomerEC(crmId);
        String employmentStatus = getEmploymentStatus(customerInfo.getOccupationCode());
        DropdownsLoanSubmissionWorkingDetail response = new DropdownsLoanSubmissionWorkingDetail();
        response.setEmploymentStatus(getDropdownEmploymentStatus());
        response.setRmOccupation(getDropdownRmOccupation(employmentStatus));
        response.setBusinessType(getDropdownBusinessType());
        response.setIncomeBank(getDropdownIncomeBank());
        response.setIncomeType(getDropdownIncomeType(employmentStatus));
        response.setSciCountry(getDropdownSciCountry(correlationId, crmId));
        response.setCardDelivery(getDropdownCardDelivery());
        response.setEmailStatementFlag(getDropdownEmailStatementFlag());
        return response;
    }

    public String getEmploymentStatus(String occupationCode) throws TMBCommonException {
        List<CriteriaCodeEntry> occupationCriteria = lendingCriteriaInfoService.getOccupationInfoByCode(occupationCode);
        if (occupationCriteria.isEmpty()) {
            throw new TMBCommonException(ResponseCode.DATA_NOT_FOUND.getCode(), ResponseCode.DATA_NOT_FOUND.getMessage(), ResponseCode.DATA_NOT_FOUND.getService(), HttpStatus.INTERNAL_SERVER_ERROR, null);
        }
        return occupationCriteria.get(0).getExtValue1();
    }

    public List<Dropdowns.EmploymentStatus> getDropdownEmploymentStatus() throws JsonProcessingException {
        List<CriteriaCodeEntry> employmentStatusCriteria = lendingCriteriaInfoService.getEmploymentStatus();
        List<Dropdowns.EmploymentStatus> employmentStatusList = employmentStatusCriteria.stream()
                .map(employmentStatus -> Dropdowns.EmploymentStatus.builder()
                        .code(employmentStatus.getEntryCode())
                        .name(employmentStatus.getEntryName())
                        .name2(employmentStatus.getEntryName2())
                        .refEntryCode(employmentStatus.getRefEntryCode())
                        .build())
                .collect(Collectors.toList());
        logger.info("Dropdown EmploymentStatus: {}", TMBUtils.convertJavaObjectToString(employmentStatusList));
        return employmentStatusList;
    }

    public List<Dropdowns.RmOccupation> getDropdownRmOccupation(String employmentStatus) throws JsonProcessingException {
        List<CriteriaCodeEntry> rmOccupationCriteria = lendingCriteriaInfoService.getOccupationByEmploymentStatus(employmentStatus);
        List<Dropdowns.RmOccupation> rmOccupationList = rmOccupationCriteria.stream()
                .map(rmOccupation -> {
                    try {
                        return Dropdowns.RmOccupation.builder()
                                .code(rmOccupation.getEntryCode())
                                .name(rmOccupation.getEntryName())
                                .name2(rmOccupation.getEntryName2())
                                .refEntryCode(rmOccupation.getRefEntryCode())
                                .groupId(rmOccupation.getGroupId())
                                .extValue2(rmOccupation.getExtValue2())
                                .occupation(getDropdownOccupation(rmOccupation.getExtValue2()))
                                .build();
                    } catch (Exception e) {
                        logger.error("Get dropdown rmOccupation fail: {}", e);
                        return null;
                    }
                })
                .collect(Collectors.toList());

        logger.info("Dropdown RmOccupation: {}", TMBUtils.convertJavaObjectToString(rmOccupationList));
        return rmOccupationList;
    }

    public List<Dropdowns.Occupation> getDropdownOccupation(String rmOccupation) throws JsonProcessingException, TMBCommonException {
        List<CriteriaCodeEntry> proffesionalCriteria = lendingCriteriaInfoService.getOccupationsByRmOccupation(rmOccupation);
        List<Dropdowns.Occupation> occupationList = proffesionalCriteria.stream()
                .map(occupation -> Dropdowns.Occupation.builder()
                        .code(occupation.getEntryCode())
                        .name(occupation.getEntryName())
                        .name2(occupation.getEntryName2())
                        .build())
                .collect(Collectors.toList());
        logger.info("Dropdown occupation: {}", TMBUtils.convertJavaObjectToString(occupationList));
        return occupationList;
    }

    public List<Dropdowns.RmOccupation> getDropdownRmOccupationName(String occupationCode) throws JsonProcessingException {
        List<CriteriaCodeEntry> rmOccupationCriteria = lendingCriteriaInfoService.getOccupationInfoByCode(occupationCode);
        List<Dropdowns.RmOccupation> rmOccupationList = rmOccupationCriteria.stream()
                .map(rmOccupation -> {
                    try {
                        return Dropdowns.RmOccupation.builder()
                                .code(rmOccupation.getEntryCode())
                                .name(rmOccupation.getEntryName())
                                .name2(rmOccupation.getEntryName2())
                                .refEntryCode(rmOccupation.getRefEntryCode())
                                .groupId(rmOccupation.getGroupId())
                                .occupation(getDropdownOccupation(rmOccupation.getExtValue2()))
                                .build();
                    } catch (Exception e) {
                        logger.error("Get dropdown rmOccupation fail: {}", e);
                        return null;
                    }
                })
                .collect(Collectors.toList());

        logger.info("Dropdown RmOccupation: {}", TMBUtils.convertJavaObjectToString(rmOccupationList));
        return rmOccupationList;
    }

    public List<Dropdowns.BusinessType> getDropdownBusinessType() throws JsonProcessingException {
        List<CriteriaCodeEntry> businessTypeCriteria = lendingCriteriaInfoService.getBusinessType();
        List<Dropdowns.BusinessType> businessTypeList = businessTypeCriteria.stream()
                .map(businessType -> {
                    try {
                        return Dropdowns.BusinessType.builder()
                                .code(businessType.getEntryCode())
                                .name(businessType.getEntryName())
                                .name2(businessType.getEntryName2())
                                .businessSubType(getDropdownBusinessSubType(businessType.getEntryCode()))
                                .build();
                    } catch (Exception e) {
                        logger.error("Get dropdown BusinessType fail: {}", e);
                        return null;
                    }
                })
                .collect(Collectors.toList());
        logger.info("Dropdown BusinessType: {}", TMBUtils.convertJavaObjectToString(businessTypeList));
        return businessTypeList;
    }

    public List<Dropdowns.BusinessSubType> getDropdownBusinessSubType(String businessTypeCode) throws JsonProcessingException {
        List<CriteriaCodeEntry> businessSubTypeCriteria = lendingCriteriaInfoService.getCriteriaByCatalogId(LoanCategory.BUSINESS_SUB_TYPE);
        List<Dropdowns.BusinessSubType> businessSubTypeList = businessSubTypeCriteria.stream()
                .filter(businessSubType -> {
                    try {
                        return ACTIVE_STATUS.equals(businessSubType.getActiveStatus())
                                && CHANNEL_MIB.equals(businessSubType.getExtValue1())
                                && CommonServiceUtils.parseStringToList(businessSubType.getRefEntryCode()).contains(businessTypeCode);
                    } catch (Exception e) {
                        logger.error("Get dropdown SubBusinessType fail: {}", e);
                        return false;
                    }
                })
                .map(businessSubType -> Dropdowns.BusinessSubType.builder()
                        .code(businessSubType.getEntryCode())
                        .name(businessSubType.getExtValue2())
                        .name2(businessSubType.getExtValue2())
                        .build())
                .collect(Collectors.toList());
        logger.info("Dropdown BusinessSubType: {}", TMBUtils.convertJavaObjectToString(businessSubTypeList));
        return businessSubTypeList;
    }

    public List<Dropdowns.IncomeBank> getDropdownIncomeBank() throws JsonProcessingException {
        List<CriteriaCodeEntry> dropdownIncomeBank = lendingCriteriaInfoService.getPayrollBank();
        List<Dropdowns.IncomeBank> incomeBankList = dropdownIncomeBank.stream()
                .filter(incomeBank -> !incomeBank.getEntryCode().equals("0")) //exclude other
                .map(incomeBank -> Dropdowns.IncomeBank.builder()
                        .code(incomeBank.getEntryCode())
                        .name(incomeBank.getEntryName())
                        .name2(incomeBank.getEntryName2())
                        .build())
                .collect(Collectors.toList());
        logger.info("Dropdown IncomeBank: {}", TMBUtils.convertJavaObjectToString(incomeBankList));
        return incomeBankList;
    }

    public List<Dropdowns.IncomeType> getDropdownIncomeType(String employmentStatus) throws JsonProcessingException {
        List<CriteriaCodeEntry> incomeTypeCriteria = lendingCriteriaInfoService.getSourceOfIncome(employmentStatus);
        List<Dropdowns.IncomeType> incomeTypeList = incomeTypeCriteria.stream()
                .map(incomeType -> Dropdowns.IncomeType.builder()
                        .code(incomeType.getEntryCode())
                        .name(incomeType.getEntryName())
                        .name2(incomeType.getEntryName2())
                        .extValue2(incomeType.getExtValue2())
                        .build())
                .collect(Collectors.toList());
        logger.info("Dropdown IncomeType: {}", TMBUtils.convertJavaObjectToString(incomeTypeList));
        return incomeTypeList;
    }

    public List<Dropdowns.SciCountry> getDropdownSciCountry(String correlationId, String crmId) throws TMBCommonException, JsonProcessingException {
        List<CriteriaCodeEntry> sciCountryCriteria = lendingCriteriaInfoService.getCountry();
        Map<String, String> countryTh = getCountryTH(correlationId, crmId);

        List<Dropdowns.SciCountry> sciCountryList = new ArrayList<>();
        for (CriteriaCodeEntry sciCountry : sciCountryCriteria) {
                Dropdowns.SciCountry dropdownCountry = Dropdowns.SciCountry.builder()
                        .code(sciCountry.getEntryCode())
                        .name(sciCountry.getEntryName())
                        .name2(countryTh.get(sciCountry.getEntryCode()) != null ? countryTh.get(sciCountry.getEntryCode()) : sciCountry.getEntryName2())
                        .build();
                if ("TH".equals(sciCountry.getEntryCode())) {
                    sciCountryList.add(0, dropdownCountry);
                } else {
                    sciCountryList.add(dropdownCountry);
                }
        }
        logger.info("Dropdown SciCountry: {}", TMBUtils.convertJavaObjectToString(sciCountryList));
        return sciCountryList;
    }

    public List<Dropdowns.MaritalStatus> getDropdownMaritalStatus(String maritalStatusCode) throws JsonProcessingException {
        List<CriteriaCodeEntry> maritalStatusCriteria = lendingCriteriaInfoService.getMaritalStatusByCode(maritalStatusCode);
        List<Dropdowns.MaritalStatus> maritalStatusList = maritalStatusCriteria.stream()
                .map(maritalStatus -> Dropdowns.MaritalStatus.builder()
                        .code(maritalStatus.getEntryCode())
                        .name(maritalStatus.getEntryName())
                        .name2(maritalStatus.getEntryName2())
                        .build())
                .collect(Collectors.toList());
        logger.info("Dropdown Marital Status: {}", TMBUtils.convertJavaObjectToString(maritalStatusList));
        return maritalStatusList;
    }

    public List<Dropdowns.ResidentType> getDropdownResidentType(String residentTypeCode) throws JsonProcessingException {
        List<CriteriaCodeEntry> residentTypeCriteria = lendingCriteriaInfoService.getResidentTypeByCode(residentTypeCode);
        List<Dropdowns.ResidentType> residentTypeList = residentTypeCriteria.stream()
                .map(residentType -> Dropdowns.ResidentType.builder()
                        .code(residentType.getEntryCode())
                        .name(residentType.getEntryName())
                        .name2(residentType.getEntryName2())
                        .build())
                .collect(Collectors.toList());
        logger.info("Dropdown Resident Type: {}", TMBUtils.convertJavaObjectToString(residentTypeList));
        return residentTypeList;
    }

    public List<Dropdowns.EducationLevel> getDropdownEducationLevel(String educationLevel) throws JsonProcessingException {
        List<CriteriaCodeEntry> dropdownEducationLevel = lendingCriteriaInfoService.getEducationLevel(educationLevel);
        List<Dropdowns.EducationLevel> educationLevelList = dropdownEducationLevel.stream()
                .map(education -> Dropdowns.EducationLevel.builder()
                        .code(education.getEntryCode())
                        .name(education.getEntryName())
                        .name2(education.getEntryName2())
                        .build())
                .collect(Collectors.toList());
        logger.info("Dropdown Education Level: {}", TMBUtils.convertJavaObjectToString(educationLevelList));
        return educationLevelList;
    }

    private Map<String, String> getCountryTH(String correlationId, String crmId) throws TMBCommonException {
        TmbOneServiceResponse<List<LovMaster>> countryTH = commonServiceFeignClient.getLovmasterConfig(correlationId, crmId, "COUNTRY", "th_TH");
        if (!ResponseCode.SUCCESS.getCode().equals(countryTH.getStatus().getCode())) {
            String errorMessage = String.format("[%s] %s", countryTH.getStatus().getCode(), countryTH.getStatus().getMessage());
            throw new TMBCommonException(ResponseCode.DATA_NOT_FOUND.getCode(), errorMessage, ResponseCode.DATA_NOT_FOUND.getService(), HttpStatus.INTERNAL_SERVER_ERROR, null);
        }
        return countryTH.getData().stream().collect(Collectors.toMap(LovMaster::getLovCode, LovMaster::getLovDesc));
    }

    public List<String> getDropdownCardDelivery() {
        return List.of("H", "O");
    }

    public List<String> getDropdownEmailStatementFlag() {
        return List.of("Y", "N");
    }

}
