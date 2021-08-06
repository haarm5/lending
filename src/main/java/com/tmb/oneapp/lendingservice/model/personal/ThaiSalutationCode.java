package com.tmb.oneapp.lendingservice.model.personal;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class ThaiSalutationCode {
    private String entryCode;
    private BigDecimal entryId;
    private String entryNameEng;
    private String entryNameTh;
    private String entrySource;
}
