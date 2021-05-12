package com.tmb.oneapp.lendingservice.model.loan;

import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
public class OneAppPricing {
    private double calculatedRate;

    private double installment;

    private double interestRate;

    private double monthFrom;

    private double monthTo;

    private String percentSign;

    private String rateType;

    private double rateVaraince;

    private double tier;

    private double yearFrom;

    private double yearTo;
}
