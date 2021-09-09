package com.tmb.oneapp.lendingservice.model.documnet;

import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
public class SubmitDocumentResponse {

    private String appRefNo;
    private String appType;
    private String productDescTh;
    private String ncbConsentFlag;

}
