package com.tmb.oneapp.lendingservice.model.creditcard;

import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
public class CreditCardResponse {
    private List<CreditCard> creditCards;
    private List<CreditCard> flashCards;
}
