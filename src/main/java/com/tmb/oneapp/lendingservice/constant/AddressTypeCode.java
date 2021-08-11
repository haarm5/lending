package com.tmb.oneapp.lendingservice.constant;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.io.Serializable;

@Getter
@AllArgsConstructor
public enum AddressTypeCode implements Serializable {
        REGISTER("R", "Register"),
        RESIDENT("H", "Resident"),
        WORKING("O", "Working");

        private final String code;
        private final String type;
}
