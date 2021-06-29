package com.tmb.oneapp.lendingservice.util;

import javax.xml.rpc.ServiceException;
import java.rmi.RemoteException;

@FunctionalInterface
public interface ThrowSoapServiceExceptionFunction<T> {
    T get() throws RemoteException, ServiceException;
}