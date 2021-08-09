package com.tmb.oneapp.lendingservice.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.tmb.common.exception.model.TMBCommonException;
import com.tmb.common.logger.TMBLogger;
import com.tmb.common.model.CustGeneralProfileResponse;
import com.tmb.common.model.legacy.rsl.ws.dropdown.response.ResponseDropdown;
import com.tmb.common.util.TMBUtils;
import com.tmb.oneapp.lendingservice.client.LoanSubmissionGetDropdownListClient;
import com.tmb.oneapp.lendingservice.model.dropdown.Dropdowns;
import com.tmb.oneapp.lendingservice.model.dropdown.DropdownsLoanSubmissionWorkingDetail;
import com.tmb.oneapp.lendingservice.util.CommonServiceUtils;
import com.tmb.oneapp.lendingservice.util.RslServiceUtils;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import javax.xml.rpc.ServiceException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class DropdownService {
    private static final TMBLogger<DropdownService> logger = new TMBLogger<>(DropdownService.class);

    private final LoanSubmissionGetDropdownListClient loanSubmissionGetDropdownListClient;
    private final PersonalDetailService personalDetailService;

    private static final String DROPDOWN_EMPLOYMENT_STATUS = "EMPLOYMENT_STATUS";
    private static final String DROPDOWN_RM_OCCUPATION = "RM_OCCUPATION";
    private static final String DROPDOWN_PROFFESIONAL = "PROFFESIONAL";
    private static final String DROPDOWN_BUSINESS_TYPE = "BUSINESS_TYPE";
    private static final String DROPDOWN_BUSINESS_SUB_TYPE = "BUSINESS_SUB_TYPE";
    private static final String DROPDOWN_PAYROLL_BANK = "PAYROLL_BANK";
    private static final String DROPDOWN_INCOME_TYPE = "INCOME_TYPE";
    private static final String DROPDOWN_SCI_COUNTRY = "SCI_COUNTRY";
    private static final String CHANNEL_MIB = "MIB";
    private static final String ACTIVE_STATUS = "1";

    public DropdownsLoanSubmissionWorkingDetail getDropdownsLoanSubmissionWorkingDetail(String crmId) throws TMBCommonException, ServiceException, JsonProcessingException {
        CustGeneralProfileResponse customerInfo = personalDetailService.getCustomerEC(crmId);
        DropdownsLoanSubmissionWorkingDetail response = new DropdownsLoanSubmissionWorkingDetail();
        response.setEmploymentStatus(getDropdownEmploymentStatus());
        response.setRmOccupation(getDropdownRmOccupation(customerInfo.getOccupationCode()));
        response.setBusinessType(getDropdownBusinessType());
        response.setTotalIncome(getDropdownTotalIncome());
        response.setIncomeBank(getDropdownIncomeBank());
        response.setIncomeType(getDropdownIncomeType(customerInfo.getOccupationCode()));
        response.setSciCountry(getDropdownSciCountry());
        response.setCardDelivery(getDropdownCardDelivery());
        response.setEmailStatementFlag(getDropdownEmailStatementFlag());
        return response;
    }

    public List<Dropdowns.EmploymentStatus> getDropdownEmploymentStatus() throws ServiceException, TMBCommonException, JsonProcessingException {
        ResponseDropdown dropdownEmploymentStatus = getDropdown(DROPDOWN_EMPLOYMENT_STATUS);
        List<Dropdowns.EmploymentStatus> employmentStatusList =  Arrays.stream(dropdownEmploymentStatus.getBody().getCommonCodeEntries())
                .filter(employmentStatus -> ACTIVE_STATUS.equals(employmentStatus.getActiveStatus()))
                .map(employmentStatus -> Dropdowns.EmploymentStatus.builder()
                        .code(employmentStatus.getEntryCode())
                        .name(employmentStatus.getEntryName())
                        .name2(employmentStatus.getEntryName2())
                        .build())
                .collect(Collectors.toList());
        logger.info("Dropdown EmploymentStatus: {}", TMBUtils.convertJavaObjectToString(employmentStatusList));
        return employmentStatusList;
    }

    public List<Dropdowns.RmOccupation> getDropdownRmOccupation(String employmentStatus) throws ServiceException, TMBCommonException, JsonProcessingException {
        ResponseDropdown dropdownRmOccupation = getDropdown(DROPDOWN_RM_OCCUPATION);
        List<Dropdowns.RmOccupation> rmOccupationList = Arrays.stream(dropdownRmOccupation.getBody().getCommonCodeEntries())
                .filter(dropdown -> ACTIVE_STATUS.equals(dropdown.getActiveStatus())
                        && employmentStatus.equals(dropdown.getExtValue1()))
                .map(rmOccupation -> {
                    try {
                        return Dropdowns.RmOccupation.builder()
                                .code(rmOccupation.getEntryCode())
                                .name(rmOccupation.getEntryName())
                                .name2(rmOccupation.getEntryName2())
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
                        .build())
                .collect(Collectors.toList());
        logger.info("Dropdown IncomeType: {}", TMBUtils.convertJavaObjectToString(incomeTypeList));
        return incomeTypeList;
    }

    public List<Dropdowns.SciCountry> getDropdownSciCountry() throws ServiceException, TMBCommonException, JsonProcessingException {
        ResponseDropdown dropdownSciCountry = getDropdown(DROPDOWN_SCI_COUNTRY);
        List<Dropdowns.SciCountry> sciCountryList = Arrays.stream(dropdownSciCountry.getBody().getCommonCodeEntries())
                .filter(sciCountry -> ACTIVE_STATUS.equals(sciCountry.getActiveStatus()))
                .map(sciCountry -> Dropdowns.SciCountry.builder()
                        .code(sciCountry.getEntryCode())
                        .name(sciCountry.getEntryName())
                        .name2(sciCountry.getEntryName2())
                        .build())
                .collect(Collectors.toList());
        logger.info("Dropdown SciCountry: {}", TMBUtils.convertJavaObjectToString(sciCountryList));
        return sciCountryList;
    }

    public List<Dropdowns.TotalIncome> getDropdownTotalIncome() {
        List<Dropdowns.TotalIncome> totalIncomeList = new ArrayList<>();
        Dropdowns.TotalIncome tier1 = Dropdowns.TotalIncome.builder()
                .max(BigDecimal.valueOf(15000))
                .max(BigDecimal.valueOf(29999))
                .build();

        Dropdowns.TotalIncome tier2 = Dropdowns.TotalIncome.builder()
                .max(BigDecimal.valueOf(30000))
                .max(BigDecimal.valueOf(49999))
                .build();

        Dropdowns.TotalIncome tier3 = Dropdowns.TotalIncome.builder()
                .max(BigDecimal.valueOf(50000))
                .max(BigDecimal.valueOf(99999))
                .build();

        Dropdowns.TotalIncome tier4 = Dropdowns.TotalIncome.builder()
                .max(BigDecimal.valueOf(100000))
                .max(BigDecimal.valueOf(199999))
                .build();

        Dropdowns.TotalIncome tier5 = Dropdowns.TotalIncome.builder()
                .max(BigDecimal.valueOf(200000))
                .max(BigDecimal.valueOf(999999999))
                .build();

        totalIncomeList.add(tier1);
        totalIncomeList.add(tier2);
        totalIncomeList.add(tier3);
        totalIncomeList.add(tier4);
        totalIncomeList.add(tier5);
        return totalIncomeList;
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
