package com.tmb.oneapp.lendingservice.model.report;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ReportGenerateClientResponse {

    @JsonProperty("base64")
    public String base64;

    @JsonProperty("type")
    public String type;

    @JsonProperty("code")
    public String code;

}
