package com.tmb.oneapp.lendingservice.model.flexiloan;

import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
public class SubmissionPricingInfo {
    private List<LoanCustomerPricing> pricing;
}
