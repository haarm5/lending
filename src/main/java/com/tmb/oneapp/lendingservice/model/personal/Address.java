package com.tmb.oneapp.lendingservice.model.personal;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class Address {
    private String addrTypCode;
    private String no;
    private String buildingName;
    private String streetName;
    private String postalCode;
    private String country;
    private String tumbol;
    private String road;
    private String moo;
    private String amphur;
    private String province;
    private String floor;
    private String roomNo;
    private BigDecimal cifId;
    private BigDecimal id;
}
