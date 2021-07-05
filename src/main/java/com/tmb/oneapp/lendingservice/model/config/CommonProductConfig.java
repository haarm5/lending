package com.tmb.oneapp.lendingservice.model.config;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
@JsonIgnoreProperties(ignoreUnknown = true)
public class CommonProductConfig {
    private String rslCode;
    private String contentLink;
    private String productNameEn;
    private String productNameTh;
    private String productDescEn;
    private String productDescTh;
    private String iconId;

}
