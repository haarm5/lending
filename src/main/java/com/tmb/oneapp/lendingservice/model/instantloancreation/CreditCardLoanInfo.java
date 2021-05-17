package com.tmb.oneapp.lendingservice.model.instantloancreation;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;
import org.apache.commons.lang3.StringUtils;

@Getter
@Setter
public class CreditCardLoanInfo {

    @JsonIgnore
    public boolean isValid() {
        return !(StringUtils.isBlank(getCardInd()) ||
                StringUtils.isBlank(getProductType()) ||
                StringUtils.isBlank(getCardBrand()) ||
                StringUtils.isBlank(getCampaignCode()));
    }


    @JsonProperty("cardInd")
    private String cardInd;
    @JsonProperty("productType")
    private String productType;
    @JsonProperty("cardBrand")
    private String cardBrand;
    @JsonProperty("campaignCode")
    private String campaignCode;
    @JsonProperty("requestCreditLimit")
    private String requestCreditLimit;
    @JsonProperty("paymentMethod")
    private String paymentMethod;
    @JsonProperty("paymentCriteria")
    private String paymentCriteria;
    @JsonProperty("debitAccountName")
    private String debitAccountName;
    @JsonProperty("debitAccountNo")
    private String debitAccountNo;
    @JsonProperty("mailPreference")
    private String mailPreference;
    @JsonProperty("cardDelivery")
    private String cardDelivery;
}
