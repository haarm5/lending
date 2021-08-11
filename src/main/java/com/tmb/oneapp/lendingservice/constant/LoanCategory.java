package com.tmb.oneapp.lendingservice.constant;

import lombok.Getter;

@Getter
public enum LoanCategory {

	PRODUCT("26", "Product"), 
	SUBPRODUCT("27", "Sub product"), 
	RM_OCCUPATION("RM_OCCUPATION", "RM Occupation"),
	PROFFESIONAL("PROFFESIONAL", "Proffesional"),
	EMPLOYMENT_STATUS("EMPLOYMENT_STATUS", "Employment"), 
	BUSINESS_TYPE("BUSINESS_TYPE", "Business Type"),
	BUSINESS_SUB_TYPE("BUSINESS_SUB_TYPE", "Business Sub Type"), 
	INCOME_TYPE("INCOME_TYPE", "Income Type"),
	SCI_COUNTRY("SCI_COUNTRY", "Source of country"), 
	FEATURE_ENABLE("FEATURE_ENABLE", "Feature enable"),
	TENURE("TENURE", "Tenor (refEntryCode=RSL)"),
	PYMT_CRITERIA("PYMT_CRITERIA", "Payment Criteria"),
	PAYROLL_BANK("PAYROLL_BANK", "Payroll Bank");

	private String code;
	private String name;

	LoanCategory(String code, String name) {
		this.code = code;
		this.name = name;
	}
}
