package com.tmb.oneapp.lendingservice.util;

import com.tmb.common.exception.model.TMBCommonException;
import com.tmb.oneapp.lendingservice.constant.ResponseCode;
import com.tmb.oneapp.lendingservice.constant.RslResponseCode;
import org.springframework.http.HttpStatus;

public class RslServiceUtils {

    private RslServiceUtils() {

    }

    public static void checkRslResponse(String responseCode, String responseMessage) throws TMBCommonException {
        if(!RslResponseCode.SUCCESS.getCode().equals(responseCode)) {
            String message = String.format("[%s] %s", responseCode, responseMessage);
            throw new TMBCommonException(ResponseCode.RSL_FAILED.getCode(), message, ResponseCode.RSL_CONNECTION_ERROR.getService(), HttpStatus.INTERNAL_SERVER_ERROR, null);
        }
    }
}
