package com.tmb.oneapp.lendingservice.model.account;

import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.*;

import java.util.List;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
public class LoanSummary {
    private List<LoanAccount> loanAccounts;
    private String noneServiceHourStart;
    private String noneServiceHourEnd;
    private Boolean isHpServiceAvailable;
}
