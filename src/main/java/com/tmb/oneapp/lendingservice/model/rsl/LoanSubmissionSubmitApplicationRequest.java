package com.tmb.oneapp.lendingservice.model.rsl;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Getter
@Setter
public class LoanSubmissionSubmitApplicationRequest {
    @NotNull
    private Long caId;
    @NotEmpty
    private String memberRef;
}
