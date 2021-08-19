package com.tmb.oneapp.lendingservice.service;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import com.tmb.common.logger.TMBLogger;
import com.tmb.common.model.legacy.rsl.common.ob.individual.Individual;
import com.tmb.common.model.legacy.rsl.ws.application.response.ResponseApplication;
import com.tmb.common.model.legacy.rsl.ws.individual.response.ResponseIndividual;
import com.tmb.oneapp.lendingservice.constant.LendingServiceConstant;
import com.tmb.oneapp.lendingservice.model.loanonline.CustomerInfoApplicationInfo;
import com.tmb.oneapp.lendingservice.model.rsl.LoanSubmissionGetApplicationInfoRequest;
import com.tmb.oneapp.lendingservice.model.rsl.LoanSubmissionGetCustomerInfoRequest;
import com.tmb.oneapp.lendingservice.util.CommonServiceUtils;

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
		customerInfoApplicationInfo.setBirthDate(getThaiDate(individual.getBirthDate().getTime().toString()));
		customerInfoApplicationInfo.setMobileNo(individual.getMobileNo());
		customerInfoApplicationInfo.setProductName(applicationInfoResponse.getBody().getProductDescTH());
		customerInfoApplicationInfo.setMemberRef(applicationInfoResponse.getBody().getMemberref());
		customerInfoApplicationInfo.setCustContactTime(individual.getCustContactTime());
		customerInfoApplicationInfo.setChannel("TTB APP");
		customerInfoApplicationInfo.setModule("Access PIN");
		customerInfoApplicationInfo.setCreateDate(applicationInfoResponse.getBody().getApplicationDate());
		customerInfoApplicationInfo.setAppRefNo(applicationInfoResponse.getBody().getAppRefNo());
		
		return customerInfoApplicationInfo;
	}

	private String getThaiDate(String dateEng) {
		if (StringUtils.isBlank(dateEng))
			return "";
		String dob = dateEng;
		dob = dob.substring(0, 10);
		
		String[] dateArray = dob.split("-");
		String thaiYear = CommonServiceUtils.getThaiYear(dateArray[0]);
		String thaiMonth = CommonServiceUtils.getThaiMonth(dateArray[1]);
		StringBuilder thaiDate = new StringBuilder();
		thaiDate.append(dateArray[2]);
		thaiDate.append(LendingServiceConstant.SPACE);
		thaiDate.append(thaiMonth);
		thaiDate.append(LendingServiceConstant.SPACE);
		thaiDate.append(thaiYear);
		return thaiDate.toString();
	}


}
