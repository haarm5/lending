package com.tmb.oneapp.lendingservice.util;

import com.tmb.common.exception.model.TMBCommonException;

@FunctionalInterface
public interface TmbExceptionSupplierFunction<T> {
    T apply(T t) throws TMBCommonException;
}