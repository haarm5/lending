package com.tmb.oneapp.lendingservice.service;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.junit.Assert;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.tmb.common.model.legacy.rsl.ws.ncb.consent.flag.update.response.ResponseUpdateNCBConsentFlag;
import com.tmb.oneapp.lendingservice.model.loanonline.CustomerInformationResponse;
import com.tmb.oneapp.lendingservice.model.loanonline.UpdateNCBConsentFlagRequest;

@RunWith(JUnit4.class)
public class LoanOnlineSubmissionUpdateNCBConsentFlagAndStoreFileServiceTest {

	@Mock
	private RslService rslService;
	@Mock
	LoanOnlineSubmissionGetCustInformationService loanSubmissionGetCustInfoAppInfoService;
	@Mock
	LoanOnlineSubmissionGenNCBFileService loanSubmissionGenNCBFileService;

	LoanOnlineSubmissionUpdateNCBConsentFlagAndStoreFileService loanSubmissionUpdateNCBConsentFlagAndStoreFileService;

	@BeforeEach
	void setUp() {
		MockitoAnnotations.initMocks(this);
		loanSubmissionUpdateNCBConsentFlagAndStoreFileService = new LoanOnlineSubmissionUpdateNCBConsentFlagAndStoreFileService(
				rslService, loanSubmissionGetCustInfoAppInfoService, loanSubmissionGenNCBFileService);

	}

	@Test
	void testUpdateNCBConsentFlagAndStoreFile() throws Exception {
		UpdateNCBConsentFlagRequest request = new UpdateNCBConsentFlagRequest();
		ResponseUpdateNCBConsentFlag updateNCBConsentFlagResponse = new ResponseUpdateNCBConsentFlag();
		com.tmb.common.model.legacy.rsl.ws.ncb.consent.flag.update.response.Body body = new com.tmb.common.model.legacy.rsl.ws.ncb.consent.flag.update.response.Body();
		body.setMemberref("");
		body.setNcbConsentDate(new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'").format(new Date()));
		updateNCBConsentFlagResponse.setBody(body);
		when(rslService.updateNCBConsentFlag(any())).thenReturn(updateNCBConsentFlagResponse);
		
		CustomerInformationResponse customerInfoRes = new CustomerInformationResponse();
		when(loanSubmissionGetCustInfoAppInfoService.getCustomerInformation(any())).thenReturn(customerInfoRes);
		
		Assert.assertNotNull(loanSubmissionUpdateNCBConsentFlagAndStoreFileService.updateNCBConsentFlagAndStoreFile("crmId",request));
	}

}
