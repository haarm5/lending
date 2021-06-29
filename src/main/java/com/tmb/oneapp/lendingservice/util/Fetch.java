package com.tmb.oneapp.lendingservice.util;

import com.tmb.common.exception.model.TMBCommonException;
import com.tmb.common.model.TmbOneServiceResponse;
import com.tmb.common.model.TmbStatus;
import com.tmb.oneapp.lendingservice.constant.ResponseCode;
import org.springframework.http.HttpStatus;

import javax.xml.rpc.ServiceException;
import java.rmi.RemoteException;
import java.util.function.Supplier;

public class Fetch {
    public static <T> T fetch(Supplier<TmbOneServiceResponse> supplier) throws TMBCommonException {
        TmbOneServiceResponse oneAppResponse = supplier.get();
        TmbStatus tmbStatus = oneAppResponse.getStatus();
        if (!ResponseCode.SUCCESS.getCode().equals(tmbStatus.getCode())) {
            throw new TMBCommonException(tmbStatus.getCode(),tmbStatus.getMessage(), tmbStatus.getService(), HttpStatus.BAD_REQUEST, null);
        }
        return (T) oneAppResponse.getData();
    }
    public static <T, R> R fetch(Supplier<T> supplier, ThrowTmbExceptionFunction<T, R> parser) throws TMBCommonException {
        T response = supplier.get();
        R result = parser.apply(response);
        return result;
    }

    public static <T, R> R fetch(ThrowSoapServiceExceptionFunction<T> supplier, ThrowTmbExceptionFunction<T, R> parser) throws TMBCommonException {
        T response = null;
        try {
            response = supplier.get();
        } catch (RemoteException | ServiceException  e) {
            throw new TMBCommonException("network error");
        }
        R result = parser.apply(response);
        return result;
    }
    public static <T> T fetch(ThrowSoapServiceExceptionFunction<T> supplier) throws TMBCommonException {
        T response = null;
        try {
            response = supplier.get();
        } catch (RemoteException | ServiceException  e) {
            throw new TMBCommonException("network error");
        }

        return response;
    }
}
