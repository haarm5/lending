package com.tmb.oneapp.lendingservice.model.instantloancreation;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class InstantLoanCreationResponse {

    @JsonIgnore
    private String error;
    @JsonProperty("appRefNo")
    private String appRefNo;
    @JsonProperty("appType")
    private String appType;
    @JsonProperty("caId")
    private Long caId;
    @JsonProperty("ccId")
    private Long ccId;
    @JsonProperty("cifId")
    private Long cifId;
    @JsonProperty("collId")
    private Object collId;
    @JsonProperty("createDate")
    private String createDate;
    @JsonProperty("currentWorkflow")
    private String currentWorkflow;
    @JsonProperty("facId")
    private Object facId;
    @JsonProperty("memberRef")
    private String memberRef;
    @JsonProperty("product")
    private String product;
    @JsonProperty("productDescEN")
    private String productDescEN;
    @JsonProperty("productDescTH")
    private String productDescTH;
    @JsonProperty("regAddressId")
    private Long regAddressId;
    @JsonProperty("resAddressId")
    private Long resAddressId;
    @JsonProperty("subProduct")
    private Object subProduct;
    @JsonProperty("subProductDescEN")
    private Object subProductDescEN;
    @JsonProperty("subProductDescTH")
    private Object subProductDescTH;
    @JsonProperty("workAddressId")
    private Long workAddressId;
    @JsonProperty("channel")
    private String channel;
    @JsonProperty("module")
    private String module;
    @JsonProperty("requestId")
    private String requestId;

    private String authMode = "Access Pin";
}
