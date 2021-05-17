package com.tmb.oneapp.lendingservice.model.loan;

import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
public class OneAppProduct {
    private double approveCreditLimit;

    private String bankAccountNumber;

    private String cardName;

    private String disburseBankCode;

    private String disburseBankFullEN;

    private String disburseBankFullTH;

    private String disburseBankShort;

    private double dsr;

    private double dti;

    private String paymentMethod;

    private String paymentMethodDescEN;

    private String paymentMethodDescTH;

    private OneAppPricing[] pricings;

    private String productCode;

    private String productDescEN;

    private String productDescTH;

    private String subProductCode;

    private String subProductDescEN;

    private String subProductDescTH;

    private double tenure;
}
