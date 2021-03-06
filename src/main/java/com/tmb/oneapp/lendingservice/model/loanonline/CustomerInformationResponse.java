package com.tmb.oneapp.lendingservice.model.loanonline;

import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
public class CustomerInformationResponse {
    
	private String crmId;
	private String fullName;
    private String citizenIdOrPassportNo;
    private String birthDate;
    private String mobileNo;
    private String productName;
    private String memberRef;
    private String ncbConsentDate;
    private String channel;
    private String module;
    private String createDate;
    private String appRefNo;
    
}
