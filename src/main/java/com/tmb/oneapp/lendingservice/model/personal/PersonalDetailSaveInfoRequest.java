package com.tmb.oneapp.lendingservice.model.personal;

import lombok.Getter;
import lombok.Setter;

import java.util.Calendar;

@Getter
@Setter
public class PersonalDetailSaveInfoRequest {
    private Long caId;
    private String thaiSalutationCode;
    private String engName;
    private String engSurName;
    private Calendar birthDate;
    private String idIssueCtry1;
    private Calendar expiryDate;
    private String nationality;
    private Address address;
    private String mobileNo;
    private String residentFlag;
    private String thaiName;
    private String thaiSurname;
    private String email;
}
