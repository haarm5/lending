package com.tmb.oneapp.lendingservice.model.rsl;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;

@Getter
@Setter
public class LoanSubmissionInstantLoanCalUWRequest {
    @NotEmpty
    private String caId;
    @NotEmpty
    private String triggerFlag;
}
