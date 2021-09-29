package com.tmb.oneapp.lendingservice.model.report;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.JsonNode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class ReportGenerateClientRequest {

    @JsonProperty("template_id")
    public String templateId;

    @JsonProperty("type")
    public String type;

    @JsonProperty("data")
    JsonNode data;

}
