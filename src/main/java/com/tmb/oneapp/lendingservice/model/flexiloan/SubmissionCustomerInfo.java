package com.tmb.oneapp.lendingservice.model.flexiloan;

import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
public class SubmissionCustomerInfo {
    private String name;
    private String citizenId;
}
