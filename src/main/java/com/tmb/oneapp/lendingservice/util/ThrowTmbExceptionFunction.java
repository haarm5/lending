package com.tmb.oneapp.lendingservice.util;

import com.tmb.common.exception.model.TMBCommonException;

@FunctionalInterface
public interface ThrowTmbExceptionFunction<T, R> {
    R apply(T t) throws TMBCommonException;
}