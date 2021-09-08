package com.tmb.oneapp.lendingservice.model.flexiloan;

import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.tmb.oneapp.lendingservice.model.loanonline.LoanCustomerDisburstAccount;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

@Getter
@Setter
@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
public class LoanCalculatorRequest {
    @NotNull
    private Long caId;
    @NotEmpty
    private String product;

}
