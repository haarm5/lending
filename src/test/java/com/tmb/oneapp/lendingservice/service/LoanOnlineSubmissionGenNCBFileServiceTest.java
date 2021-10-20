package com.tmb.oneapp.lendingservice.service;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import com.tmb.common.model.legacy.rsl.ws.application.response.Body;
import com.tmb.common.model.legacy.rsl.ws.application.response.ResponseApplication;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.tmb.oneapp.lendingservice.client.FTPClient;
import com.tmb.oneapp.lendingservice.model.loanonline.CustomerInformationResponse;

@RunWith(JUnit4.class)
public class LoanOnlineSubmissionGenNCBFileServiceTest {

	@Mock
	private ImageGeneratorService imageGeneratorService;
	@Mock
	private FTPClient ftpClient;
	
	@Mock
	LoanOnlineSubmissionGenNCBFileService loanOnlineSubmissionGenNCBFileService;

	@BeforeEach
	void setUp() {
		MockitoAnnotations.initMocks(this);
		loanOnlineSubmissionGenNCBFileService = new LoanOnlineSubmissionGenNCBFileService(
				imageGeneratorService, ftpClient);
		loanOnlineSubmissionGenNCBFileService.setSftpLocations("/u01/datafile/mib/mibshare/LetterConsent");
	}

	@Test
	void testStoreNCBfile() throws Exception {
		ResponseApplication responseApplication = new ResponseApplication();
		Body body = new Body();
		body.setAppRefNo("026PL64001583");
		body.setApplicationDate("026PL64001583");
		responseApplication.setBody(body);
		CustomerInformationResponse custInfo = new CustomerInformationResponse();
		String jpgFile = "";
		custInfo.setMobileNo("0888888888");
		custInfo.setBirthDate("2020-20-01");
		custInfo.setCitizenIdOrPassportNo("1234567891111");
		custInfo.setFullName("Test Test");
		custInfo.setCrmId("5564213546412");
		custInfo.setMemberRef("2154531");
		custInfo.setNcbConsentDate("2020-20-01");
		custInfo.setProductName("Product Name");
		custInfo.setAppRefNo("2645556");
		when(imageGeneratorService.generateLOCImage(any())).thenReturn(jpgFile);
		loanOnlineSubmissionGenNCBFileService.storeNCBfile(responseApplication,custInfo);
		Assertions.assertTrue(true);
	}
}
