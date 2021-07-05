package com.tmb.oneapp.lendingservice.model.account;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.*;

import java.math.BigDecimal;
import java.util.List;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
public class LoanAccount {
    private String loanType;
    private String accountNumber;
    private String accountTypeDescEn;
    private String accountTypeDescTh;
    private String license;
    private String productCode;
    private String productIcon;
    private String productNameEn;
    private String productNameTh;
    private String productNickname;
    private String dueDate;

    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private BigDecimal dueAmount;

    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private BigDecimal paidAmount;

    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private BigDecimal outstandingAmount;

    private String paidPeriod;
    private String remainingPeriod;
    private List<AccountShortcut> shortcuts;
    private String productOrder;
}
