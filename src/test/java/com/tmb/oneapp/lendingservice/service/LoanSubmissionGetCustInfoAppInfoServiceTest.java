package com.tmb.oneapp.lendingservice.service;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import java.util.Calendar;

import org.junit.Assert;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.tmb.common.model.legacy.rsl.common.ob.individual.Individual;
import com.tmb.common.model.legacy.rsl.ws.application.response.ResponseApplication;
import com.tmb.common.model.legacy.rsl.ws.individual.response.Body;
import com.tmb.common.model.legacy.rsl.ws.individual.response.ResponseIndividual;

@RunWith(JUnit4.class)
public class LoanSubmissionGetCustInfoAppInfoServiceTest {

	@Mock
	private RslService rslService;
	@Mock
	LoanSubmissionGetCustInfoAppInfoService loanSubmissionGetCustInfoAppInfoService;

	@BeforeEach
	void setUp() {
		MockitoAnnotations.initMocks(this);
		loanSubmissionGetCustInfoAppInfoService = new LoanSubmissionGetCustInfoAppInfoService(rslService);
	}

	@Test
	void testGetCustomerInfoAndApplicationInfo() throws Exception {

		ResponseIndividual individualResponse = new ResponseIndividual();
		Body body = new Body();
		Individual individual = new Individual();
		individual.setThaiName("");
		individual.setThaiSurName("");
		individual.setIdNo1("");
		Calendar date = Calendar.getInstance();
		individual.setBirthDate(date);
		individual.setMobileNo("");
		individual.setCustContactTime("");
		Individual[] individuals = {individual};
		body.setIndividuals(individuals);
		individualResponse.setBody(body);
		ResponseApplication applicationInfoResponse = new ResponseApplication();
		com.tmb.common.model.legacy.rsl.ws.application.response.Body bodyApp = new com.tmb.common.model.legacy.rsl.ws.application.response.Body();
		bodyApp.setAppType("");
		bodyApp.setMemberref("");
		applicationInfoResponse.setBody(bodyApp);

		when(rslService.getLoanSubmissionCustomerInfo(any())).thenReturn(individualResponse);
		when(rslService.getLoanSubmissionApplicationInfo(any())).thenReturn(applicationInfoResponse);

		Assert.assertNotNull(loanSubmissionGetCustInfoAppInfoService.getCustomerInfoAndApplicationInfo("caId"));
	}

}
