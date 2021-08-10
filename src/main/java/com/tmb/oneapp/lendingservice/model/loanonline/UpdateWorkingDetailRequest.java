package com.tmb.oneapp.lendingservice.model.loanonline;

import com.tmb.oneapp.lendingservice.model.personal.Address;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class UpdateWorkingDetailRequest {
    private String productCode;
    private Long caId;
    private String employmentStatus;
    private String occupation;
    private String employmentOccupation;
    private String professionalCode;
    private String rmOccupation;
    private String employmentBizNature;
    private String contractEmployedFlag;
    private String businessType;
    private String businessSubType;
    private String employmentName;
    private String employmentYear;
    private String employmentMonth;
    private Address address;
    private String tel;
    private String exTel;
    private BigDecimal incomeBasicSalary;
    private BigDecimal inTotalIncome;
    private String incomeBank;
    private String incomeBankAccountNumber;
    private BigDecimal incomeSharedHolderPercent;
    private BigDecimal incomeDeclared;
    private BigDecimal incomeTotalLastMthCreditAcct1;
    private String incomeType;
    private String mailingPreference;
    private String emailStatementFlag;
}

