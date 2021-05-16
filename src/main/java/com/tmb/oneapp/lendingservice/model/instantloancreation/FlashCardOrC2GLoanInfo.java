package com.tmb.oneapp.lendingservice.model.instantloancreation;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;
import org.apache.commons.lang3.StringUtils;

@Getter
@Setter
public class FlashCardOrC2GLoanInfo {

    @JsonIgnore
    public boolean isValid() {
        return !(StringUtils.isEmpty(getFacilityCode()) ||
                StringUtils.isEmpty(getProductCode()) ||
                StringUtils.isEmpty(getCaCampaignCode()) ||
                StringUtils.isEmpty(getLimitApplied()));
    }

    @JsonProperty("facilityCode")
    private String facilityCode;
    @JsonProperty("productCode")
    private String productCode;
    @JsonProperty("caCampaignCode")
    private String caCampaignCode;
    @JsonProperty("limitApplied")
    private String limitApplied;
    @JsonProperty("monthlyInstallment")
    private String monthlyInstallment;
    @JsonProperty("tenure")
    private String tenure;
    @JsonProperty("interestRate")
    private String interestRate;
    @JsonProperty("paymentDueDate")
    private String paymentDueDate;
    @JsonProperty("firstPaymentDueDate")
    private String firstPaymentDueDate;
    @JsonProperty("loanWithOtherBank")
    private String loanWithOtherBank;
    @JsonProperty("considerLoanWithOtherBank")
    private String considerLoanWithOtherBank;
    @JsonProperty("disburstBankName")
    private String disburstBankName;
    @JsonProperty("disburstAccountName")
    private String disburstAccountName;
    @JsonProperty("disburstAccountNo")
    private String disburstAccountNo;
    @JsonProperty("paymentMethod")
    private String paymentMethod;
    @JsonProperty("paymentAccountName")
    private String paymentAccountName;
    @JsonProperty("paymentAccountNo")
    private String paymentAccountNo;
    @JsonProperty("mailPreference")
    private String mailPreference;
    @JsonProperty("cardDelivery")
    private String cardDelivery;
    @JsonProperty("paymentCriteria")
    private String paymentCriteria;
}
