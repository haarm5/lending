package com.tmb.oneapp.lendingservice.model;

import java.util.List;

public class ServiceResponseImp implements ServiceResponse {

    private String responseCode;
    private ServiceError error;
    private Object data;
    private List<String> responseMessageList;

    @Override
    public String getResponseCode() {
        return responseCode;
    }

    public void setResponseCode(String responseCode) {
        this.responseCode = responseCode;
    }

    @Override
    public List<String> getResponseMessage() {
        return responseMessageList;
    }

    public void setResponseMessage(List<String> responseMessageList) {
        this.responseMessageList = responseMessageList;
    }

    @Override
    public ServiceError getError() {
        return error;
    }

    public void setError(ServiceError error) {
        this.error = error;
    }

    @Override
    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }
}
