package com.tmb.oneapp.lendingservice.model.instantloancreation;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.List;

@Getter
@Setter
public class InstantLoanCreationRequest {

    @NotEmpty
    @JsonProperty("loanType")
    private String loanType;
    @NotEmpty
    @JsonProperty("requestID")
    private String requestID;
    @NotEmpty
    @JsonProperty("channel")
    private String channel;
    @JsonProperty("module")
    private String module;
    @NotEmpty
    @JsonProperty("transactionType")
    private String transactionType;
    @NotEmpty
    @JsonProperty("natureOfRequest")
    private String natureOfRequest;
    @NotEmpty
    @JsonProperty("appType")
    private String appType;
    @NotEmpty
    @JsonProperty("branchCode")
    private String branchCode;
    @NotEmpty
    @JsonProperty("bookBranchCode")
    private String bookBranchCode;
    @NotEmpty
    @JsonProperty("saleChannel")
    private String saleChannel;
    @NotEmpty
    @JsonProperty("authenCode")
    private String authenCode;
    @NotEmpty
    @JsonProperty("ncbConsentFlag")
    private String ncbConsentFlag;

    @NotEmpty
    @JsonProperty("addresses")
    private List<AddressInfo> addresses = null;

    @JsonProperty("creditCards")
    private List<CreditCardLoanInfo> creditCards = null;

    @JsonProperty("flashCardOrC2G")
    private List<FlashCardOrC2GLoanInfo> flashCardOrC2G = null;

    @NotNull
    @JsonProperty("customerInfo")
    private CustomerInfo customerInfo;
}
