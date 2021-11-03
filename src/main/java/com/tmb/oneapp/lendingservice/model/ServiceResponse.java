package com.tmb.oneapp.lendingservice.model;

import java.util.List;

import com.tmb.common.model.loan.RslServiceError;

public interface ServiceResponse {
    String getResponseCode();

    List<String> getResponseMessage();

    RslServiceError getError();

    Object getData();
}
