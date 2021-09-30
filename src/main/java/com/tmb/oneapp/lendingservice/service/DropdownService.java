package com.tmb.oneapp.lendingservice.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.tmb.common.exception.model.TMBCommonException;
import com.tmb.common.logger.TMBLogger;
import com.tmb.common.model.CustGeneralProfileResponse;
import com.tmb.common.model.LovMaster;
import com.tmb.common.model.TmbOneServiceResponse;
import com.tmb.common.model.legacy.rsl.common.ob.dropdown.CommonCodeEntry;
import com.tmb.common.model.legacy.rsl.ws.dropdown.response.ResponseDropdown;
import com.tmb.common.util.TMBUtils;
import com.tmb.oneapp.lendingservice.client.CommonServiceFeignClient;
import com.tmb.oneapp.lendingservice.client.LoanSubmissionGetDropdownListClient;
import com.tmb.oneapp.lendingservice.constant.ResponseCode;
import com.tmb.oneapp.lendingservice.model.dropdown.Dropdowns;
import com.tmb.oneapp.lendingservice.model.dropdown.DropdownsLoanSubmissionWorkingDetail;
import com.tmb.oneapp.lendingservice.util.CommonServiceUtils;
import com.tmb.oneapp.lendingservice.util.RslServiceUtils;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import javax.xml.rpc.ServiceException;
import java.util.*;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class DropdownService {
    private static final TMBLogger<DropdownService> logger = new TMBLogger<>(DropdownService.class);

    private final LoanSubmissionGetDropdownListClient loanSubmissionGetDropdownListClient;
    private final LoanOnlineSubmissionGetPersonalDetailService loanOnlineSubmissionGetPersonalDetailService;
    private final CommonServiceFeignClient commonServiceFeignClient;

    private static final String DROPDOWN_EMPLOYMENT_STATUS = "EMPLOYMENT_STATUS";
    private static final String DROPDOWN_RM_OCCUPATION = "RM_OCCUPATION";
    private static final String DROPDOWN_PROFFESIONAL = "PROFFESIONAL";
    private static final String DROPDOWN_BUSINESS_TYPE = "BUSINESS_TYPE";
    private static final String DROPDOWN_BUSINESS_SUB_TYPE = "BUSINESS_SUB_TYPE";
    private static final String DROPDOWN_PAYROLL_BANK = "PAYROLL_BANK";
    private static final String DROPDOWN_INCOME_TYPE = "INCOME_TYPE";
    private static final String DROPDOWN_SCI_COUNTRY = "SCI_COUNTRY";
    private static final String DROPDOWN_MARITAL_STATUS = "MARITAL_STATUS";
    private static final String DROPDOWN_EDUCATION_LEVEL = "EDUCATION_LEVEL";
    private static final String DROPDOWN_RESIDENT_TYP = "RESIDENT_TYP";
    private static final String CHANNEL_MIB = "MIB";
    private static final String ACTIVE_STATUS = "1";

    public DropdownsLoanSubmissionWorkingDetail getDropdownsLoanSubmissionWorkingDetail(String correlationId, String crmId) throws TMBCommonException, ServiceException, JsonProcessingException {
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

    public String getEmploymentStatus(String occupationCode) throws ServiceException, TMBCommonException, JsonProcessingException {
        ResponseDropdown dropdownRmOccupation = getDropdown(DROPDOWN_RM_OCCUPATION);
        CommonCodeEntry rmOccupationList = Arrays.stream(dropdownRmOccupation.getBody().getCommonCodeEntries())
                .filter(dropdown->occupationCode.equals(dropdown.getEntryCode())).findFirst()
                .orElseThrow(()->new TMBCommonException(ResponseCode.DATA_NOT_FOUND.getCode(), ResponseCode.DATA_NOT_FOUND.getMessage(), ResponseCode.DATA_NOT_FOUND.getService(), HttpStatus.INTERNAL_SERVER_ERROR, null));

        return rmOccupationList.getExtValue1();
    }

    public List<Dropdowns.EmploymentStatus> getDropdownEmploymentStatus() throws ServiceException, TMBCommonException, JsonProcessingException {
        ResponseDropdown dropdownEmploymentStatus = getDropdown(DROPDOWN_EMPLOYMENT_STATUS);
        List<Dropdowns.EmploymentStatus> employmentStatusList =  Arrays.stream(dropdownEmploymentStatus.getBody().getCommonCodeEntries())
                .filter(employmentStatus -> ACTIVE_STATUS.equals(employmentStatus.getActiveStatus()))
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

    public List<Dropdowns.RmOccupation> getDropdownRmOccupation(String occupationCode) throws ServiceException, TMBCommonException, JsonProcessingException {
        ResponseDropdown dropdownRmOccupation = getDropdown(DROPDOWN_RM_OCCUPATION);
        List<Dropdowns.RmOccupation> rmOccupationList = Arrays.stream(dropdownRmOccupation.getBody().getCommonCodeEntries())
                .filter(dropdown -> ACTIVE_STATUS.equals(dropdown.getActiveStatus())
                        && occupationCode.equals(dropdown.getExtValue1()))
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

    public List<Dropdowns.Occupation> getDropdownOccupation(String employmentStatus) throws ServiceException, TMBCommonException, JsonProcessingException {
        ResponseDropdown dropdownProffesional = getDropdown(DROPDOWN_PROFFESIONAL);
        List<Dropdowns.Occupation> occupationList = Arrays.stream(dropdownProffesional.getBody().getCommonCodeEntries())
                .filter(occupation -> {
                            try {
                                return ACTIVE_STATUS.equals(occupation.getActiveStatus())
                                        && CommonServiceUtils.parseStringToList(occupation.getEntrySource()).contains(employmentStatus);
                            } catch (Exception e) {
                                logger.error("Get dropdown Occupation fail: {}", e);
                                return false;
                            }
                        }
                )
                .map(occupation -> Dropdowns.Occupation.builder()
                        .code(occupation.getEntryCode())
                        .name(occupation.getEntryName())
                        .name2(occupation.getEntryName2())
                        .build())
                .collect(Collectors.toList());
        logger.info("Dropdown occupation: {}", TMBUtils.convertJavaObjectToString(occupationList));
        return occupationList;
    }

    public List<Dropdowns.RmOccupation> getDropdownRmOccupationName(String occupationCode) throws ServiceException, TMBCommonException, JsonProcessingException {
        ResponseDropdown dropdownRmOccupation = getDropdown(DROPDOWN_RM_OCCUPATION);
        List<Dropdowns.RmOccupation> rmOccupationList = Arrays.stream(dropdownRmOccupation.getBody().getCommonCodeEntries())
                .filter(dropdown -> ACTIVE_STATUS.equals(dropdown.getActiveStatus())
                        && occupationCode.equals(dropdown.getEntryCode()))
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

    public List<Dropdowns.BusinessType> getDropdownBusinessType() throws ServiceException, TMBCommonException, JsonProcessingException {
        ResponseDropdown dropdownBusinessType = getDropdown(DROPDOWN_BUSINESS_TYPE);
        List<Dropdowns.BusinessType> businessTypeList = Arrays.stream(dropdownBusinessType.getBody().getCommonCodeEntries())
                .filter(businessType -> ACTIVE_STATUS.equals(businessType.getActiveStatus()))
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

    public List<Dropdowns.BusinessSubType> getDropdownBusinessSubType(String businessTypeCode) throws ServiceException, TMBCommonException, JsonProcessingException {
        ResponseDropdown dropdownBusinessSubType = getDropdown(DROPDOWN_BUSINESS_SUB_TYPE);
        List<Dropdowns.BusinessSubType> businessSubTypeList = Arrays.stream(dropdownBusinessSubType.getBody().getCommonCodeEntries())
                .filter(businessSubType-> {
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

    public List<Dropdowns.IncomeBank> getDropdownIncomeBank() throws ServiceException, TMBCommonException, JsonProcessingException {
        ResponseDropdown dropdownIncomeBank = getDropdown(DROPDOWN_PAYROLL_BANK);
        List<Dropdowns.IncomeBank> incomeBankList = Arrays.stream(dropdownIncomeBank.getBody().getCommonCodeEntries())
                .filter(incomeBank -> ACTIVE_STATUS.equals(incomeBank.getActiveStatus()))
                .map(incomeBank -> Dropdowns.IncomeBank.builder()
                        .code(incomeBank.getEntryCode())
                        .name(incomeBank.getEntryName())
                        .name2(incomeBank.getEntryName2())
                        .build())
                .collect(Collectors.toList());
        logger.info("Dropdown IncomeBank: {}", TMBUtils.convertJavaObjectToString(incomeBankList));
        return incomeBankList;
    }

    public List<Dropdowns.IncomeType> getDropdownIncomeType(String employmentStatus) throws ServiceException, TMBCommonException, JsonProcessingException {
        ResponseDropdown dropdownIncomeType = getDropdown(DROPDOWN_INCOME_TYPE);
        List<Dropdowns.IncomeType> incomeTypeList = Arrays.stream(dropdownIncomeType.getBody().getCommonCodeEntries())
                .filter(incomeType -> ACTIVE_STATUS.equals(incomeType.getActiveStatus())
                        && CHANNEL_MIB.equals(incomeType.getExtValue1())
                && employmentStatus.equals(incomeType.getRefEntryCode()))
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

    public List<Dropdowns.SciCountry> getDropdownSciCountry(String correlationId, String crmId) throws ServiceException, TMBCommonException, JsonProcessingException {
        ResponseDropdown dropdownSciCountry = getDropdown(DROPDOWN_SCI_COUNTRY);
        Map<String, String> countryTh = getCountryTH(correlationId, crmId);

        List<Dropdowns.SciCountry> sciCountryList = new ArrayList<>();
        for(CommonCodeEntry sciCountry : dropdownSciCountry.getBody().getCommonCodeEntries()) {
            if(ACTIVE_STATUS.equals(sciCountry.getActiveStatus())) {
                Dropdowns.SciCountry dropdownCountry = Dropdowns.SciCountry.builder()
                        .code(sciCountry.getEntryCode())
                        .name(sciCountry.getEntryName())
                        .name2(countryTh.get(sciCountry.getEntryCode())!=null?countryTh.get(sciCountry.getEntryCode()):sciCountry.getEntryName2())
                        .build();
                if("TH".equals(sciCountry.getEntryCode())) {
                    sciCountryList.add(0, dropdownCountry);
                }else {
                    sciCountryList.add(dropdownCountry);
                }
            }
        }
        logger.info("Dropdown SciCountry: {}", TMBUtils.convertJavaObjectToString(sciCountryList));
        return sciCountryList;
    }

    public List<Dropdowns.MaritalStatus> getDropdownMaritalStatus(String maritalStatusCode) throws ServiceException, TMBCommonException, JsonProcessingException {
        ResponseDropdown dropdownMaritalStatus = getDropdown(DROPDOWN_MARITAL_STATUS);
        List<Dropdowns.MaritalStatus> maritalStatusList= Arrays.stream(dropdownMaritalStatus.getBody().getCommonCodeEntries())
                .filter(maritalStatus-> {
                    try {
                        return ACTIVE_STATUS.equals(maritalStatus.getActiveStatus())
                                && CommonServiceUtils.parseStringToList(maritalStatus.getEntryCode()).contains(maritalStatusCode);
                    } catch (Exception e) {
                        logger.error("Get dropdown Marital Status fail: {}", e);
                        return false;
                    }
                })
                .map(maritalStatus -> Dropdowns.MaritalStatus.builder()
                        .code(maritalStatus.getEntryCode())
                        .name(maritalStatus.getEntryName())
                        .name2(maritalStatus.getEntryName2())
                        .build())
                .collect(Collectors.toList());
        logger.info("Dropdown Marital Status: {}", TMBUtils.convertJavaObjectToString(maritalStatusList));
        return maritalStatusList;
    }

    public List<Dropdowns.ResidentType> getDropdownResidentType(String residentTypeCode) throws ServiceException, TMBCommonException, JsonProcessingException {
        ResponseDropdown dropdownResidentType = getDropdown(DROPDOWN_RESIDENT_TYP);
        List<Dropdowns.ResidentType> residentTypeList= Arrays.stream(dropdownResidentType.getBody().getCommonCodeEntries())
                .filter(residentType-> {
                    try {
                        return ACTIVE_STATUS.equals(residentType.getActiveStatus())
                                && CommonServiceUtils.parseStringToList(residentType.getEntryCode()).contains(residentTypeCode);
                    } catch (Exception e) {
                        logger.error("Get dropdown Resident Type fail: {}", e);
                        return false;
                    }
                })
                .map(residentType -> Dropdowns.ResidentType.builder()
                        .code(residentType.getEntryCode())
                        .name(residentType.getEntryName())
                        .name2(residentType.getEntryName2())
                        .build())
                .collect(Collectors.toList());
        logger.info("Dropdown Resident Type: {}", TMBUtils.convertJavaObjectToString(residentTypeList));
        return residentTypeList;
    }

    public List<Dropdowns.EducationLevel> getDropdownEducationLevel(String educationLevel) throws ServiceException, TMBCommonException, JsonProcessingException {
        ResponseDropdown dropdownEducationLevel = getDropdown(DROPDOWN_EDUCATION_LEVEL);
        List<Dropdowns.EducationLevel> educationLevelList= Arrays.stream(dropdownEducationLevel.getBody().getCommonCodeEntries())
                .filter(education-> {
                    try {
                        return ACTIVE_STATUS.equals(education.getActiveStatus())
                                && CommonServiceUtils.parseStringToList(education.getEntryCode()).contains(educationLevel);
                    } catch (Exception e) {
                        logger.error("Get dropdown Education Level fail: {}", e);
                        return false;
                    }
                })
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
        if(!ResponseCode.SUCCESS.getCode().equals(countryTH.getStatus().getCode())){
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


    public ResponseDropdown getDropdown(String categoryCode) throws TMBCommonException, ServiceException, JsonProcessingException {
        ResponseDropdown dropdown = loanSubmissionGetDropdownListClient.getDropDownListByCode(categoryCode);
        RslServiceUtils.checkRslResponse(dropdown.getHeader().getResponseCode(), dropdown.getHeader().getResponseDescriptionEN());
        return dropdown;
    }

}
