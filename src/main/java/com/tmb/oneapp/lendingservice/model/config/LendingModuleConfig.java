package com.tmb.oneapp.lendingservice.model.config;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.tmb.common.model.RslCode;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
@JsonIgnoreProperties(ignoreUnknown = true)
public class LendingModuleConfig {
    private List<String> flexiOnly;
    private List<CommonProductConfig> applyCreditCards;
    private List<CommonProductConfig> applyPersonalLoans;
    private List<String> incompleteDocStatus;
    private List<RslCode> defaultRslCode;
}
