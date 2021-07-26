package com.tmb.oneapp.lendingservice.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.tmb.common.exception.model.TMBCommonException;

import javax.xml.rpc.ServiceException;
import java.rmi.RemoteException;

@FunctionalInterface
public interface ThrowSoapServiceExceptionFunction<T> {
    T get() throws RemoteException, ServiceException, JsonProcessingException, TMBCommonException;
}
