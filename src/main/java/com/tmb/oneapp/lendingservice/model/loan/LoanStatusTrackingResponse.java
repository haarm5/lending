package com.tmb.oneapp.lendingservice.model.loan;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
public class LoanStatusTrackingResponse {

    private List<OneAppApplication> applications;


}
