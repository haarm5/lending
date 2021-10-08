package com.tmb.oneapp.lendingservice.model.loanonline;

import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.tmb.oneapp.lendingservice.model.personal.Address;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
public class WorkingDetail {
    private String employmentStatus;
    private String employmentYear;
    private String employmentMonth;
    private String rmOccupation;
    private String employmentOccupation;
    private String professional;
    private String contractEmployedFlag;
    private String businessType;
    private String businessSubType;
    private String employmentName;
    private Address address;
    private String tel;
    private String exTel;
    private BigDecimal incomeBasicSalary;
    private BigDecimal incomeOtherIncome;
    private String incomeBank;
    private String incomeBankAccountNumber;
    private BigDecimal incomeDeclared;
    private BigDecimal incometotalLastMthCreditAcct1;
    private BigDecimal incomeSharedHolderPercent;
    private String incomeType;
    private String sciCountry;
    private String cardDelivery;
    private String emailStatementFlag;
}
