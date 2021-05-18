package com.tmb.oneapp.lendingservice.model.response;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

@Data
public class WorkInfoEntryResp {

	@JsonProperty("workstatus")
	private SelectCodeEntry workstatus;
	@JsonProperty("occupation")
	private SelectCodeEntry occupation;
	@JsonProperty("businessType")
	private SelectCodeEntry businessType;
	@JsonProperty("subBusinessType")
	private SelectCodeEntry subBusinessType;
	@JsonProperty("sourceIncomes")
	private SelectCodeEntry sourceIncomes;
	@JsonProperty("countryIncomes")
	private SelectCodeEntry countryIncomes;

}
