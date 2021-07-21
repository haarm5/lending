package com.tmb.oneapp.lendingservice.model.loanonline;

import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
public class LoanSubmissionResponse {
    private List<LoanCustomerDisburstAccount> accounts;
    private List<InterestRate> interestRateList;
    private List<RangeIncome> rangeIncomeList;

}
