package com.tmb.oneapp.lendingservice.model.loan;

import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
public class OneAppApplicant {

    private String applicantType;

    private String firstNameEN;

    private String firstNameTH;

    private OneAppProduct[] products;

    private String surNameEN;

    private String surNameTH;

    private String titleEN;

    private String titleTH;
}
