package com.tmb.oneapp.lendingservice.model.loan;

import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
public class PaymentCriteriaOption {
    private String optionId;
    private String optionNameTh;
    private String optionNameEn;
}
