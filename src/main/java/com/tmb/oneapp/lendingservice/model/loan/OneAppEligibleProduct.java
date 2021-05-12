package com.tmb.oneapp.lendingservice.model.loan;

import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
public class OneAppEligibleProduct {
    private String productCategory;
    private String productCode;
    private String productType;
    private String productNameEn;
    private String productNameTh;
    private String interestRate;
    private String paymentCriteriaOptions;
    private String loanReqMax;
    private String loanReqMin;
}
