package com.tmb.oneapp.lendingservice.model;

import java.util.List;

public interface ServiceResponse {
    String getResponseCode();

    List<String> getResponseMessage();

    ServiceError getError();

    Object getData();
}
