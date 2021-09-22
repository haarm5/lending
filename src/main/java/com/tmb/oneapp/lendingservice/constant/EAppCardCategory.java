package com.tmb.oneapp.lendingservice.constant;

import lombok.Getter;

@Getter
public enum EAppCardCategory {

	CREDIT_CARD("credit-card-report", "credit-card", "credit"),
	FLASH_CARD("flash-card-report", "flash-card", "flash"),
	C2G_CARD("c2g-report", "c2g-card", "c2g");

	private final String template;
	private final String type;
	private final String path;

	EAppCardCategory(String template, String type, String path) {
		this.template = template;
		this.type = type;
		this.path = path;
	}
}
