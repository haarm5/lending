package com.tmb.oneapp.lendingservice.model.response;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.tmb.oneapp.lendingservice.model.CriteriaCodeEntry;

import lombok.Data;

@Data
public class SelectCodeEntry {

	@JsonProperty("name")
	private String name;
	@JsonProperty("value")
	private String value;
	@JsonProperty("entry")
	private List<CriteriaCodeEntry> relateCodeEntry;

}
