package com.tmb.oneapp.lendingservice.constant;

import lombok.Getter;

@Getter
public enum EAppCardCategory {

	CREDIT_CARD("LOAN_EAPP_CREDIT_CARD", "credit-card"),
	FLASH_CARD("LOAN_EAPP_FLASH_CARD", "flash-card"),
	C2G_CARD("LOAN_EAPP_C2G_CARD", "c2g-card");

	private final String template;
	private final String type;

	EAppCardCategory(String template, String type) {
		this.template = template;
		this.type = type;
	}
}
