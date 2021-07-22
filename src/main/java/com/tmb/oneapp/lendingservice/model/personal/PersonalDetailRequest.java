package com.tmb.oneapp.lendingservice.model.personal;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

@Getter
@Setter
public class PersonalDetailRequest {
    @NotNull
    private Long caId;
}
