package com.tmb.oneapp.lendingservice.model.personal;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Getter
@Setter
public class ChecklistRequest {
    @NotNull
    private Long caId;
    @NotEmpty
    private String incompleteDocFlag;
}
