package com.tmb.oneapp.lendingservice.constant;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.io.Serializable;

@Getter
@AllArgsConstructor
public enum RslResponseCode implements Serializable {
        SUCCESS("MSG_000", "Success"),
        FAIL("MSG_001", "Fail");

        private final String code;
        private final String message;
}
