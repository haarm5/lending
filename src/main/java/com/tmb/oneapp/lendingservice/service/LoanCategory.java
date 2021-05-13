package com.tmb.oneapp.lendingservice.service;

public enum LoanCategory {

	PRODUCT("26", "Product"), 
	SUBPRODUCT("27", "Sub product"), 
	RM_OCCUPATION("RM_OCCUPATION", "RM Occupation"),
	EMPLOYMENT_STATUS("EMPLOYMENT_STATUS", "Employment"), 
	BUSINESS_TYPE("BUSINESS_TYPE", "Business Type"),
	BUSNIESS_SUB_TYPE("BUSNIESS_SUB_TYPE", "Business Sub Type"), 
	INCOME_TYPE("INCOME_TYPE", "Income Type"),
	SCI_COUNTRY("SCI_COUNTRY", "Source of country"), 
	FEATURE_ENABLE("FEATURE_ENABLE", "Feature enable"),
	TENURE("TENURE", "Tenor (refEntryCode=RSL)");

	private String code;
	private String name;

	private LoanCategory(String _code, String _name) {
		this.code = _code;
		this.name = _name;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

}
