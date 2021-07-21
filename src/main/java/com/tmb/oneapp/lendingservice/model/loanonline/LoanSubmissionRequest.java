package com.tmb.oneapp.lendingservice.model.loanonline;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;

@Getter
@Setter
public class LoanSubmissionRequest {
    @NotNull
    private Long caId;
}
