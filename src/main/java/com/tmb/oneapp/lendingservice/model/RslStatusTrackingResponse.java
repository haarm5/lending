package com.tmb.oneapp.lendingservice.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
public class RslStatusTrackingResponse {
    private String appStatus;
    private String productCode;
    private String productTypeTh;
    private String productTypeEn;
    private String referenceNo;
    private String currentNode;
    private List<String> nodeTextTh;
    private List<String> nodeTextEn;
    private String applicationDate;
    private String lastUpdateDate;
    private String isApproved;
    private String isRejected;
}