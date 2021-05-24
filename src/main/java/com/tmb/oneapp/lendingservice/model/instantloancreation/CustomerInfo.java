package com.tmb.oneapp.lendingservice.model.instantloancreation;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;
import org.apache.commons.lang3.StringUtils;


@Getter
@Setter
public class CustomerInfo {

    @JsonIgnore
    public boolean isValid() {
        return !(
                StringUtils.isEmpty(getIdType1()) ||
                StringUtils.isEmpty(getIdNo1()) ||
                StringUtils.isEmpty(getHostCifNo()) ||
                StringUtils.isEmpty(getNationality()) ||
                StringUtils.isEmpty(getEmploymentStatus()) ||
                StringUtils.isEmpty(getIncomeType()));
    }


    @JsonProperty("cifRelCode")
    private String cifRelCode;

    @JsonProperty("idType1")
    private String idType1;

    @JsonProperty("idNo1")
    private String idNo1;

    @JsonProperty("hostCifNo")
    private String hostCifNo;
    @JsonProperty("email")
    private String email;
    @JsonProperty("emailStatementFlag")
    private String emailStatementFlag;
    @JsonProperty("thaiName")
    private String thaiName;
    @JsonProperty("thaiSurName")
    private String thaiSurName;
    @JsonProperty("birthDate")
    private String birthDate;
    @JsonProperty("mobileNo")
    private String mobileNo;
    @JsonProperty("ncbConsentFlag")
    private String ncbConsentFlag;
    @JsonProperty("discloseCustInfoFlag")
    private String discloseCustInfoFlag;
    @JsonProperty("nameLine2")
    private String nameLine2;
    @JsonProperty("nameLine1")
    private String nameLine1;
    @JsonProperty("issuedDate")
    private String issuedDate;

    @JsonProperty("nationality")
    private String nationality;
    @JsonProperty("rmOccupation")
    private String rmOccupation;
    @JsonProperty("professionalCode")
    private String professionalCode;
    @JsonProperty("businessType")
    private String businessType;
    @JsonProperty("businessSubType")
    private String businessSubType;

    @JsonProperty("incomeType")
    private String incomeType;
    @JsonProperty("sourceFromCountry")
    private String sourceFromCountry;
    @JsonProperty("employmentName")
    private String employmentName;

    @JsonProperty("employmentStatus")
    private String employmentStatus;
    @JsonProperty("employmentOccupation")
    private String employmentOccupation;
    @JsonProperty("employmentTelephoneDirectNo")
    private String employmentTelephoneDirectNo;
    @JsonProperty("employmentTelephoneExtNo")
    private String employmentTelephoneExtNo;
    @JsonProperty("incomeDeclared")
    private String incomeDeclared;
    @JsonProperty("incomeBasicSalary")
    private String incomeBasicSalary;

    @JsonProperty("maxIncomeRange")
    private String maxIncomeRange;

}
