package com.tmb.oneapp.lendingservice.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ServiceError {
    private String responseCode = "N/A";
    private String apiName;
    private String httpCode;
    private String responseValue = "N/A";

    private String errorMessage;
}
