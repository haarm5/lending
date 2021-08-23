package com.tmb.oneapp.lendingservice.client;

import java.math.BigDecimal;
import java.rmi.RemoteException;
import java.util.UUID;

import javax.xml.rpc.ServiceException;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.tmb.common.exception.model.TMBCommonException;
import com.tmb.common.logger.TMBLogger;
import com.tmb.common.model.legacy.rsl.ws.loan.submission.LoanSubmissionUpdateNCBConsentFlagServiceLocator;
import com.tmb.common.model.legacy.rsl.ws.loan.submission.LoanSubmissionUpdateNCBConsentFlagSoapBindingStub;
import com.tmb.common.model.legacy.rsl.ws.ncb.consent.flag.update.request.Body;
import com.tmb.common.model.legacy.rsl.ws.ncb.consent.flag.update.request.Header;
import com.tmb.common.model.legacy.rsl.ws.ncb.consent.flag.update.request.RequestUpdateNCBConsentFlag;
import com.tmb.common.model.legacy.rsl.ws.ncb.consent.flag.update.response.ResponseUpdateNCBConsentFlag;
import com.tmb.oneapp.lendingservice.constant.ResponseCode;
import com.tmb.oneapp.lendingservice.model.loanonline.UpdateNCBConsentFlagRequest;

@Service
public class LoanSubmissionUpdateNCBConsentFlagClient {
	@Value("${rsl.loan-submission-update-ncb-consent-flag.url}")
	private String updateNCBConsentFlagUrl;
	private final ObjectMapper mapper;
	private static final TMBLogger<LoanSubmissionUpdateNCBConsentFlagClient> logger = new TMBLogger<>(
			LoanSubmissionUpdateNCBConsentFlagClient.class);

	LoanSubmissionUpdateNCBConsentFlagServiceLocator locator = new LoanSubmissionUpdateNCBConsentFlagServiceLocator();
	private static final String CHANNEL = "MIB";
	private static final String MODULE = "3";

	public LoanSubmissionUpdateNCBConsentFlagClient(ObjectMapper mapper) {
		this.mapper = mapper;
		mapper.setVisibility(PropertyAccessor.FIELD, JsonAutoDetect.Visibility.ANY);

	}

	public void setLocator(LoanSubmissionUpdateNCBConsentFlagServiceLocator locator) {
		this.locator = locator;
	}

	public ResponseUpdateNCBConsentFlag updateNCBConsentFlag(UpdateNCBConsentFlagRequest updateNCBConsentFlagRequest)
			throws ServiceException, JsonProcessingException, TMBCommonException {
		locator.setLoanSubmissionUpdateNCBConsentFlagEndpointAddress(updateNCBConsentFlagUrl);
		LoanSubmissionUpdateNCBConsentFlagSoapBindingStub stub = (LoanSubmissionUpdateNCBConsentFlagSoapBindingStub) locator
				.getLoanSubmissionUpdateNCBConsentFlag();
		logger.info("LoanSubmissionUpdateNCBConsentFlag Url: {}", updateNCBConsentFlagUrl);

		RequestUpdateNCBConsentFlag req = new RequestUpdateNCBConsentFlag();
		Header header = new Header();
		header.setChannel(CHANNEL);
		header.setModule(MODULE);
		header.setRequestID(UUID.randomUUID().toString());
		req.setHeader(header);

		Body body = new Body();
		body.setCaId(new BigDecimal(updateNCBConsentFlagRequest.getCaId()));
		body.setNcbConsentFlag(updateNCBConsentFlagRequest.getNcbConsentFlag());
		body.setNcbModelFlag(updateNCBConsentFlagRequest.getNcbModelFlag());
		req.setBody(body);
		logger.info("LoanSubmissionUpdateCustomer req {} : " + mapper.writeValueAsString(req));

		try {
			ResponseUpdateNCBConsentFlag responseUpdateNCBConsentFlag = stub.updateNCBConsentFlag(req);
			logger.info("LoanSubmissionUpdateNCBConsentFlag Response: {}",
					mapper.writeValueAsString(responseUpdateNCBConsentFlag));
			return responseUpdateNCBConsentFlag;
		} catch (RemoteException e) {
			throw new TMBCommonException(ResponseCode.RSL_CONNECTION_ERROR.getCode(),
					ResponseCode.RSL_CONNECTION_ERROR.getMessage(), ResponseCode.RSL_CONNECTION_ERROR.getService(),
					HttpStatus.INTERNAL_SERVER_ERROR, e);
		}
	}
}
