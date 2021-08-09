package com.tmb.oneapp.lendingservice.model.personal;

import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
public class Address {
    private String buildingName;
    private String no;
    private String floor;
    private String moo;
    private String streetName;
    private String postalCode;
    private String province;
    private String country;
    private String tumbol;
    private String road;
    private String amphur;
}
