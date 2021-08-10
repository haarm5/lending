package com.tmb.oneapp.lendingservice.model.rsl;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;

@Getter
@Setter
public class LoanSubmissionGetChecklistInfoRequest {
    @NotEmpty
    private Long caId;
}
