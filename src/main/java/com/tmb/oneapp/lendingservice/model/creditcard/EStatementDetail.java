package com.tmb.oneapp.lendingservice.model.creditcard;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.tmb.oneapp.lendingservice.model.estatement.StatementFlag;
import lombok.*;

@Data
@Getter
@Setter
@ToString
@EqualsAndHashCode
@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
public class EStatementDetail {
    @JsonProperty("email_address")
    private String emailAddress;
    @JsonProperty("email_verify_flag")
    private String emailVerifyFlag;
    private StatementFlag statementFlag;
}
