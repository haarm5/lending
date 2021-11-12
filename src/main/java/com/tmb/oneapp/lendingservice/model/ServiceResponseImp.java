package com.tmb.oneapp.lendingservice.model;

import java.util.List;

import com.tmb.common.model.loan.RslServiceError;

public class ServiceResponseImp implements ServiceResponse {

    private String responseCode;
    private RslServiceError error;
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
    public RslServiceError getError() {
        return error;
    }

    public void setError(RslServiceError error) {
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
