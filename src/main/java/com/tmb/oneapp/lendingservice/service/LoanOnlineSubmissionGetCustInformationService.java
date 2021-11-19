package com.tmb.oneapp.lendingservice.service;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Objects;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import com.tmb.common.logger.TMBLogger;
import com.tmb.common.model.legacy.rsl.common.ob.individual.Individual;
import com.tmb.common.model.legacy.rsl.ws.individual.response.ResponseIndividual;
import com.tmb.oneapp.lendingservice.constant.LendingServiceConstant;
import com.tmb.oneapp.lendingservice.model.loanonline.CustomerInformationResponse;
import com.tmb.oneapp.lendingservice.model.loanonline.UpdateNCBConsentFlagRequest;
import com.tmb.oneapp.lendingservice.model.rsl.LoanSubmissionGetCustomerInfoRequest;
import com.tmb.oneapp.lendingservice.util.CommonServiceUtils;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class LoanOnlineSubmissionGetCustInformationService {
	private static final TMBLogger<LoanOnlineSubmissionGetCustInformationService> logger = new TMBLogger<>(
			LoanOnlineSubmissionGetCustInformationService.class);

	private final RslService rslService;

	public CustomerInformationResponse getCustomerInformation(UpdateNCBConsentFlagRequest request) {
		CustomerInformationResponse customerInfoRes = new CustomerInformationResponse();
		try {
			LoanSubmissionGetCustomerInfoRequest requestCust = new LoanSubmissionGetCustomerInfoRequest();
			requestCust.setCaId(request.getCaId());

			logger.info("Get customer info from [RSL]");
			ResponseIndividual individualResponse = rslService.getLoanSubmissionCustomerInfo(requestCust);

			if (Objects.nonNull(individualResponse)) {
				customerInfoRes = parseCustomerInformation(individualResponse, request);
			}
		} catch (Exception e) {
			logger.error("getGetCustomerInformation got ExecutionException:{}", e);
		}
		return customerInfoRes;
	}

	private CustomerInformationResponse parseCustomerInformation(ResponseIndividual individualResponse,
																 UpdateNCBConsentFlagRequest updateNCBConsentFlagRequest) {
		CustomerInformationResponse customerInfoRes = new CustomerInformationResponse();
		Individual individual = individualResponse.getBody().getIndividuals()[0];

		customerInfoRes.setFullName(individual.getThaiName() + " " + individual.getThaiSurName());
		customerInfoRes.setCitizenIdOrPassportNo(individual.getIdNo1());
		individual.getBirthDate().set(Calendar.HOUR, 0);
		individual.getBirthDate().set(Calendar.MINUTE, 0);
		String birthDateStr = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'")
				.format(individual.getBirthDate().getTime());
		customerInfoRes.setBirthDate(concertToThaiDate(birthDateStr));
		customerInfoRes.setMobileNo(individual.getMobileNo());
		customerInfoRes.setProductName(updateNCBConsentFlagRequest.getProductDescTH() + " (22)");
		if ("PL".equalsIgnoreCase(updateNCBConsentFlagRequest.getAppType())) {
			customerInfoRes.setProductName(updateNCBConsentFlagRequest.getProductDescTH() + " (05)");
		}
		customerInfoRes.setChannel("TTB APP");
		customerInfoRes.setModule("Access PIN");
		customerInfoRes.setAppRefNo(updateNCBConsentFlagRequest.getAppRefNo());

		return customerInfoRes;
	}

	public String concertToThaiDate(String dateEng) {
		if (StringUtils.isBlank(dateEng))
			return "";
		String dob = dateEng;
		dob = dob.substring(0, 10);
		String[] dates = dob.split("-");
		String year = CommonServiceUtils.getThaiYear(dates[0]);
		String month = CommonServiceUtils.getThaiMonth(dates[1]);
		StringBuilder dateResult = new StringBuilder();
		dateResult.append(dates[2]);
		dateResult.append(LendingServiceConstant.SPACE);
		dateResult.append(month);
		dateResult.append(LendingServiceConstant.SPACE);
		dateResult.append(year);
		return dateResult.toString();
	}

}