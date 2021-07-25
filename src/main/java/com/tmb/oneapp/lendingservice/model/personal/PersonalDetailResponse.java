package com.tmb.oneapp.lendingservice.model.personal;

import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;
import java.util.List;

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
    private String birthDate;
    private String idIssueCtry1;
    private String expiryDate;
    private String nationality;
    private Address address;
    private String mobileNo;
    private List<Resident> residentFlag;
}
