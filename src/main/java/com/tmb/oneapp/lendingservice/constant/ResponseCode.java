package com.tmb.oneapp.lendingservice.constant;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.io.Serializable;

/**
 * enum class for response code to maintain response status
 *
 *
 */
@Getter
@AllArgsConstructor
public enum ResponseCode implements Serializable {

	SUCCESS("0000", "success", Constants.SERVICE_NAME, "success"),
	FAILED("0001", "failed", Constants.SERVICE_NAME, "failed"),
	INVALID_DATA("0002", LendingServiceConstant.INVALID_DATA, Constants.SERVICE_NAME, LendingServiceConstant.INVALID_DATA),
	BAD_REQUEST("400", "BAD REQUEST", Constants.SERVICE_NAME, "BAD REQUEST"),
	DATA_NOT_FOUND("0009", LendingServiceConstant.DATA_NOT_FOUND, Constants.SERVICE_NAME,
			LendingServiceConstant.DATA_NOT_FOUND),
	CUSTOMER_NOT_FOUND("1000007", "customer not found", Constants.SERVICE_NAME, "customer not found"),
	DATABASE_CONNECTION_ERROR("0006", "database connection errror", Constants.SERVICE_NAME,
			"database connection errror"),
	UBO_DATA_NOT_FOUND("4002", LendingServiceConstant.DATA_NOT_FOUND, Constants.SERVICE_NAME,
			LendingServiceConstant.DATA_NOT_FOUND),
	GENERAL_ERROR("0001", "general error", Constants.SERVICE_NAME, "unknown error"),

	//SFTP
	SFTP_FAILED("0010", "sftp failed", Constants.SERVICE_NAME, "sftp failed"),

	//RSL
	RSL_CONNECTION_ERROR("RSL0001", "rsl connection error", Constants.SERVICE_NAME, "rsl connection error"),
	RSL_FAILED("RSL0002", "rsl failed", Constants.RSL_SERVICE_NAME, "rsl failed");

	private final String code;
	private final String message;
	private final String service;
	private final String desc;

	private static class Constants {
		public static final String SERVICE_NAME = "lending-service";
		public static final String RSL_SERVICE_NAME = "rsl-service";
	}
}
