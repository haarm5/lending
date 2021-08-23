package com.tmb.oneapp.lendingservice.service;

import javax.validation.Valid;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import com.tmb.common.logger.TMBLogger;
import com.tmb.common.model.legacy.rsl.ws.ncb.consent.flag.update.response.ResponseUpdateNCBConsentFlag;
import com.tmb.oneapp.lendingservice.constant.LendingServiceConstant;
import com.tmb.oneapp.lendingservice.model.loanonline.CustomerInformationResponse;
import com.tmb.oneapp.lendingservice.model.loanonline.UpdateNCBConsentFlagRequest;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class LoanOnlineSubmissionUpdateNCBConsentFlagAndStoreFileService {
	private static final TMBLogger<LoanOnlineSubmissionUpdateNCBConsentFlagAndStoreFileService> logger = new TMBLogger<>(
			LoanOnlineSubmissionUpdateNCBConsentFlagAndStoreFileService.class);

	private final RslService rslService;
	private final LoanOnlineSubmissionGetCustInformationService loanSubmissionGetCustInformationService;
	private final LoanOnlineSubmissionGenNCBFileService loanSubmissionGenNCBFileService;

	public CustomerInformationResponse updateNCBConsentFlagAndStoreFile(@Valid UpdateNCBConsentFlagRequest request) {
		CustomerInformationResponse customerInfoRes = new CustomerInformationResponse();
		try {
			logger.info("Update NCB Consent flag [RSL]");
			ResponseUpdateNCBConsentFlag updateNCBConsentFlagResponse = rslService.updateNCBConsentFlag(request);

			logger.info("Generate and Store NCB File");
			customerInfoRes = loanSubmissionGetCustInformationService.getCustomerInformation(request);
			customerInfoRes.setMemberRef(updateNCBConsentFlagResponse.getBody().getMemberref());
			customerInfoRes.setNcbConsentDate(
					getDateAndTimeForLOC(updateNCBConsentFlagResponse.getBody().getNcbConsentDate()));
			customerInfoRes.setCrmId(request.getCrmId());
			loanSubmissionGenNCBFileService.storeNCBfile(customerInfoRes);

		} catch (Exception e) {
			logger.error("getGetCustomerInformation got ExecutionException:{}", e);
		}
		return customerInfoRes;

	}

	private String getDateAndTimeForLOC(String dateAndTime) {
		if (StringUtils.isBlank(dateAndTime))
			return "";
		logger.info("dateAndTime is  {} :", dateAndTime);
		String[] dateAndTimeArry = dateAndTime.split("T");
		String dateEng = dateAndTimeArry[0];
		String curTime = dateAndTimeArry[1];
		return loanSubmissionGetCustInformationService.concertToThaiDate(dateEng) + LendingServiceConstant.SPACE + curTime.replace(".000Z", "")
				+ LendingServiceConstant.SPACE + "น.";
	}
}
