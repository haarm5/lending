package com.tmb.oneapp.lendingservice.model.personal;

import lombok.Getter;
import lombok.Setter;

import java.util.Calendar;

@Getter
@Setter
public class PersonalDetailModel {
    private String engName;
    private String engSurName;
    private String thaiName;
    private String  thaiSurName;
    private String email;
    private Calendar birthDate;
    private String idIssueCtry1;
    private Calendar expiryDate;
    private String nationality;
    private Address address;
    private String mobileNo;
}
