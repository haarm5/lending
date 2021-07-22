package com.tmb.oneapp.lendingservice.model.personal;

import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
public class PersonalDetailResponse {
    private String thaiSalutationCode;
    private String engName;
    private String engSurName;
    private String thaiName;
    private String  thaiSurName;
    private String email;
    private Date birthDate;
    private String idIssueCtry1;
    private Date expiryDate;
    private String nationality;
    private Address address;
    private String mobileNo;
    private String residentFlag;
}
