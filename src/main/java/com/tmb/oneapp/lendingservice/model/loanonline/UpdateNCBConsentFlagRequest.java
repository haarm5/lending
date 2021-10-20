package com.tmb.oneapp.lendingservice.model.loanonline;

import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;

@Getter
@Setter
@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
public class UpdateNCBConsentFlagRequest {

    @NotEmpty
    private String caId;
    @NotEmpty
    private String appType;
    private String appRefNo;
    private String productDescTH;
    private String ncbConsentFlag;
    private String ncbModelFlag;
}
