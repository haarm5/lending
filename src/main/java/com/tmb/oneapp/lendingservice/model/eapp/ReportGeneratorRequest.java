package com.tmb.oneapp.lendingservice.model.eapp;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;

@Getter
@Setter
public class ReportGeneratorRequest {

    @NotEmpty
    private String caId;
    @NotEmpty
    private String productCode;

}
