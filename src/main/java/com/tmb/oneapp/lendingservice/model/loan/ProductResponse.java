package com.tmb.oneapp.lendingservice.model.loan;

import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.tmb.oneapp.lendingservice.model.account.DepositAccount;
import lombok.Getter;
import lombok.Setter;

import java.util.List;


@Getter
@Setter
@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
public class ProductResponse {
    private List<DepositAccount> depositAccountLists;
    private LoanStatusTrackingResponse loanStatusTracking;
    private EligibleProductResponse eligibleProducts;

}
