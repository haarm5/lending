package com.tmb.oneapp.lendingservice.client;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.tmb.common.exception.model.TMBCommonException;
import com.tmb.common.logger.TMBLogger;
import com.tmb.common.model.legacy.rsl.ws.instant.submit.request.RequestInstantLoanSubmit;
import com.tmb.common.model.legacy.rsl.ws.instant.submit.response.ResponseInstantLoanSubmit;
import com.tmb.common.model.legacy.rsl.ws.loan.submission.LoanSubmissionInstantLoanSubmitApplicationServiceLocator;
import com.tmb.common.model.legacy.rsl.ws.loan.submission.LoanSubmissionInstantLoanSubmitApplicationSoapBindingStub;
import com.tmb.oneapp.lendingservice.constant.ResponseCode;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import javax.xml.rpc.ServiceException;
import java.math.BigDecimal;
import java.rmi.RemoteException;
import java.util.UUID;

@Service
public class LoanSubmissionInstantLoanSubmitApplicationClient {

	@Value("${rsl.loan-submission-submit-application.url}")
	private String url;

	private static final TMBLogger<LoanSubmissionInstantLoanSubmitApplicationClient> logger = new TMBLogger<>(LoanSubmissionInstantLoanSubmitApplicationClient.class);
	private final ObjectMapper mapper;

	LoanSubmissionInstantLoanSubmitApplicationServiceLocator locator = new LoanSubmissionInstantLoanSubmitApplicationServiceLocator();

	private static final String CHANNEL = "MIB";
	private static final String MODULE = "3";

	public LoanSubmissionInstantLoanSubmitApplicationClient(ObjectMapper mapper) {
		this.mapper = mapper;
	}

	public void setLocator(LoanSubmissionInstantLoanSubmitApplicationServiceLocator locator) {
		this.locator = locator;
	}

	public ResponseInstantLoanSubmit submitInstantLoanApplication(BigDecimal caId, String submitFlag)
			throws ServiceException, TMBCommonException, JsonProcessingException {
		locator.setLoanSubmissionInstantLoanSubmitApplicationEndpointAddress(url);
		LoanSubmissionInstantLoanSubmitApplicationSoapBindingStub stub = (LoanSubmissionInstantLoanSubmitApplicationSoapBindingStub) locator
				.getLoanSubmissionInstantLoanSubmitApplication();
		logger.info("LoanSubmissionInstantLoanSubmitApplication Url: {}", url);

		RequestInstantLoanSubmit request = new RequestInstantLoanSubmit();
		request.setHeader(setHeader());
		request.setBody(setBody(caId, submitFlag));
		logger.info("LoanSubmissionInstantLoanSubmitApplication Request: {}", mapper.writeValueAsString(request));

		try {
			ResponseInstantLoanSubmit response = stub.submitInstantLoanApplication(request);
			logger.info("LoanSubmissionInstantLoanSubmitApplication Response: {}", mapper.writeValueAsString(response));
			return response;
		} catch (RemoteException e) {
			throw new TMBCommonException(ResponseCode.RSL_CONNECTION_ERROR.getCode(), ResponseCode.RSL_CONNECTION_ERROR.getMessage(), ResponseCode.RSL_CONNECTION_ERROR.getService(), HttpStatus.INTERNAL_SERVER_ERROR, e);
		}
	}

	private com.tmb.common.model.legacy.rsl.ws.instant.submit.request.Header setHeader() {
		com.tmb.common.model.legacy.rsl.ws.instant.submit.request.Header header = new com.tmb.common.model.legacy.rsl.ws.instant.submit.request.Header();
		header.setChannel(CHANNEL);
		header.setModule(MODULE);
		header.setRequestID(UUID.randomUUID().toString());
		return header;
	}

	private com.tmb.common.model.legacy.rsl.ws.instant.submit.request.Body setBody(BigDecimal caId, String submitFlag) {
		com.tmb.common.model.legacy.rsl.ws.instant.submit.request.Body body = new com.tmb.common.model.legacy.rsl.ws.instant.submit.request.Body();
		body.setCaId(caId);
		body.setSubmittedFlag(submitFlag);
		return body;
	}
}
