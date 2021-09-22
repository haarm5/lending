package com.tmb.oneapp.lendingservice.model.creditcard;

import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.tmb.common.model.creditcard.CreditCardDetail;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
public class FetchCardResponse {
    private SilverlakeStatus status;
    private CreditCardDetail creditCard;
    private ProductCodeData  productCodeData;
    private EStatementDetail eStatementDetail;
}