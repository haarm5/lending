package com.tmb.oneapp.lendingservice.model.flexiloan;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

@Getter
@Setter
public class InstantLoanCalUWRequest {
    @NotNull
    private BigDecimal caId;
    @NotEmpty
    private String triggerFlag;
    @NotEmpty
    private String product;
    private String loanDay1Set;
}
