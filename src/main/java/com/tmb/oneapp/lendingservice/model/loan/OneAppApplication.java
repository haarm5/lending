package com.tmb.oneapp.lendingservice.model.loan;

import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.Getter;
import lombok.Setter;

import java.util.Calendar;

@Getter
@Setter
@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
public class OneAppApplication {

    private String appRefNo;

    private String appStatus;

    private String appStatusDesc;

    private String appType;

    private OneAppApplicant[] applicants;

    private String applicationDate;

    private String instantAppliedStepFlag;

    private String instantFlag;

    private String isApproved;

    private String isIncompleteDocUpdated;

    private String isRejected;

    private String isSubmitted;

    private Calendar lastUpdateDate;

    private String natureOfRequest;

    private String natureOfRequestDescEN;

    private String natureOfRequestDescTH;

    private OneAppRoadMap[] roadMap;

}
