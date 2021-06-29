package com.tmb.oneapp.lendingservice.model.loan;

import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
public class ProductDetailResponse {
    private String loanType;
    private String productCode;
    private String productNameTh;
    private String productNameEn;
    private boolean alreadyHasProduct;
    private boolean flexiOnly;
    private String contentLink;
    private String status;
    private String continueApplyNextStep;
    private ContinueApplyParams continueApplyParams;
}