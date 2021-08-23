package com.tmb.oneapp.lendingservice.service;

import java.text.SimpleDateFormat;

import org.springframework.stereotype.Service;

import com.tmb.common.logger.TMBLogger;
import com.tmb.common.model.legacy.rsl.common.ob.individual.Individual;
import com.tmb.common.model.legacy.rsl.ws.individual.response.ResponseIndividual;
import com.tmb.oneapp.lendingservice.model.loanonline.CustomerInformationResponse;
import com.tmb.oneapp.lendingservice.model.loanonline.UpdateNCBConsentFlagRequest;
import com.tmb.oneapp.lendingservice.model.rsl.LoanSubmissionGetCustomerInfoRequest;
import com.tmb.oneapp.lendingservice.util.CommonServiceUtils;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class LoanSubmissionGetCustInfoAppInfoService {
	private static final TMBLogger<LoanSubmissionGetCustInfoAppInfoService> logger = new TMBLogger<>(
			LoanSubmissionGetCustInfoAppInfoService.class);

	private final RslService rslService;

	public CustomerInformationResponse getCustomerInformation(UpdateNCBConsentFlagRequest request) {
		CustomerInformationResponse customerInfoRes = new CustomerInformationResponse();
		try {
			LoanSubmissionGetCustomerInfoRequest requestCust = new LoanSubmissionGetCustomerInfoRequest();
			requestCust.setCaId(request.getCaId());

			logger.info("Get customer info from [RSL]");
			ResponseIndividual individualResponse = rslService.getLoanSubmissionCustomerInfo(requestCust);

			if (individualResponse != null) {
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
		String birthDateStr = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'")
				.format(individual.getBirthDate().getTime());
		customerInfoRes.setBirthDate(CommonServiceUtils.getThaiDate(birthDateStr));
		customerInfoRes.setMobileNo(individual.getMobileNo());
		if ("PL".equalsIgnoreCase(updateNCBConsentFlagRequest.getAppType())) {
			customerInfoRes.setProductName(updateNCBConsentFlagRequest.getProductDescTH() + " (05)");
		} else {
			customerInfoRes.setProductName(updateNCBConsentFlagRequest.getProductDescTH() + " (22)");
		}
		customerInfoRes.setChannel("TTB APP");
		customerInfoRes.setModule("Access PIN");
		customerInfoRes.setAppRefNo(updateNCBConsentFlagRequest.getAppRefNo());

		return customerInfoRes;
	}

}
