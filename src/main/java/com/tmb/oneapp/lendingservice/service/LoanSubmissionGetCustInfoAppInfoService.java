package com.tmb.oneapp.lendingservice.service;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import org.springframework.stereotype.Service;

import com.tmb.common.logger.TMBLogger;
import com.tmb.common.model.legacy.rsl.common.ob.individual.Individual;
import com.tmb.common.model.legacy.rsl.ws.application.response.ResponseApplication;
import com.tmb.common.model.legacy.rsl.ws.individual.response.ResponseIndividual;
import com.tmb.oneapp.lendingservice.model.loanonline.CustomerInfoApplicationInfo;
import com.tmb.oneapp.lendingservice.model.rsl.LoanSubmissionGetApplicationInfoRequest;
import com.tmb.oneapp.lendingservice.model.rsl.LoanSubmissionGetCustomerInfoRequest;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class LoanSubmissionGetCustInfoAppInfoService {
	private static final TMBLogger<LoanSubmissionGetCustInfoAppInfoService> logger = new TMBLogger<>(
			LoanSubmissionGetCustInfoAppInfoService.class);

	private final RslService rslService;

	public CustomerInfoApplicationInfo getCustomerInfoAndApplicationInfo(String caId) {
		CustomerInfoApplicationInfo customerInfoApplicationInfo = new CustomerInfoApplicationInfo();
		try {
			LoanSubmissionGetCustomerInfoRequest requestCust = new LoanSubmissionGetCustomerInfoRequest();
			LoanSubmissionGetApplicationInfoRequest requestApp = new LoanSubmissionGetApplicationInfoRequest();
			requestCust.setCaId(caId);
			requestApp.setCaId(caId);

			logger.info("Get customer info from [RSL]");
			ResponseIndividual individualResponse = rslService.getLoanSubmissionCustomerInfo(requestCust);
			logger.info("Get application info from [RSL]");
			ResponseApplication applicationInfoResponse = rslService.getLoanSubmissionApplicationInfo(requestApp);

			if (individualResponse != null && applicationInfoResponse != null) {
				customerInfoApplicationInfo = parseCustomerInfoApplicationInfo(individualResponse,
						applicationInfoResponse);
			}

		} catch (Exception e) {
			logger.error("getGetCustomerInfoAndApplicationInfo got ExecutionException:{}", e);
		}
		return customerInfoApplicationInfo;
	}

	private CustomerInfoApplicationInfo parseCustomerInfoApplicationInfo(ResponseIndividual individualResponse,
			ResponseApplication applicationInfoResponse) {
		CustomerInfoApplicationInfo customerInfoApplicationInfo = new CustomerInfoApplicationInfo();
		Individual individual = individualResponse.getBody().getIndividuals()[0];
		
		customerInfoApplicationInfo.setThaiName(individual.getThaiName());
		customerInfoApplicationInfo.setThaiSurName(individual.getThaiSurName());
		customerInfoApplicationInfo.setCitizenIdOrPassportNo(individual.getIdNo1());
		customerInfoApplicationInfo.setBirthDate(parseDateThaiFormat(individual.getBirthDate().getTime()));
		customerInfoApplicationInfo.setMobileNo(individual.getMobileNo());
		customerInfoApplicationInfo.setAppType(applicationInfoResponse.getBody().getAppType());
		customerInfoApplicationInfo.setMemberRef(applicationInfoResponse.getBody().getMemberref());
		customerInfoApplicationInfo.setCustContactTime(individual.getCustContactTime());
		customerInfoApplicationInfo.setChannel("TTB APP");
		customerInfoApplicationInfo.setModule("Access PIN");
		
		return customerInfoApplicationInfo;
	}

	private String parseDateThaiFormat(Date date) {
		if (date != null) {
			SimpleDateFormat formatter = new SimpleDateFormat("dd MMM yyyy", new Locale("th", "TH"));
			return formatter.format(date);
		}
		return "-";
	}

}
