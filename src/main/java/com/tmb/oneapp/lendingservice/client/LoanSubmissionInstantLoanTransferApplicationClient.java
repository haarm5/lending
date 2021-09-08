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
import com.tmb.common.model.legacy.rsl.ws.instant.transfer.request.Body;
import com.tmb.common.model.legacy.rsl.ws.instant.transfer.request.Header;
import com.tmb.common.model.legacy.rsl.ws.instant.transfer.request.RequestTransfer;
import com.tmb.common.model.legacy.rsl.ws.instant.transfer.response.ResponseTransfer;
import com.tmb.common.model.legacy.rsl.ws.loan.submission.LoanSubmissionInstantLoanTransferApplicationServiceLocator;
import com.tmb.common.model.legacy.rsl.ws.loan.submission.LoanSubmissionInstantLoanTransferApplicationSoapBindingStub;
import com.tmb.oneapp.lendingservice.constant.ResponseCode;

@Service
public class LoanSubmissionInstantLoanTransferApplicationClient {
	@Value("${rsl.loan-submission-instant-loan-transfer-application.url}")
	private String instantLoanTransferApplicationUrl;
	private final ObjectMapper mapper;
	private static final TMBLogger<LoanSubmissionInstantLoanTransferApplicationClient> logger = new TMBLogger<>(
			LoanSubmissionInstantLoanTransferApplicationClient.class);

	LoanSubmissionInstantLoanTransferApplicationServiceLocator locator = new LoanSubmissionInstantLoanTransferApplicationServiceLocator();
	private static final String CHANNEL = "MIB";
	private static final String MODULE = "3";

	public LoanSubmissionInstantLoanTransferApplicationClient(ObjectMapper mapper) {
		this.mapper = mapper;
		mapper.setVisibility(PropertyAccessor.FIELD, JsonAutoDetect.Visibility.ANY);

	}

	public void setLocator(LoanSubmissionInstantLoanTransferApplicationServiceLocator locator) {
		this.locator = locator;
	}

	public ResponseTransfer transferApplication(String caId)
			throws ServiceException, JsonProcessingException, TMBCommonException {
		locator.setLoanSubmissionInstantLoanTransferApplicationEndpointAddress(instantLoanTransferApplicationUrl);
		LoanSubmissionInstantLoanTransferApplicationSoapBindingStub stub = (LoanSubmissionInstantLoanTransferApplicationSoapBindingStub) locator
				.getLoanSubmissionInstantLoanTransferApplication();
		logger.info("LoanSubmissionInstantLoanTransferApplication Url: {}", instantLoanTransferApplicationUrl);

		RequestTransfer req = new RequestTransfer();
		Header header = new Header();
		header.setChannel(CHANNEL);
		header.setModule(MODULE);
		header.setRequestID(UUID.randomUUID().toString());
		req.setHeader(header);

		Body body = new Body();
		body.setCaId(new BigDecimal(caId));
		req.setBody(body);
		logger.info("LoanSubmissionInstantLoanTransferApplication req {} : " + mapper.writeValueAsString(req));

		try {
			ResponseTransfer responseTransfer = stub.transferApplication(req);
			logger.info("LoanSubmissionInstantLoanTransferApplication Response: {}",
					mapper.writeValueAsString(responseTransfer));
			return responseTransfer;
		} catch (RemoteException e) {
			throw new TMBCommonException(ResponseCode.RSL_CONNECTION_ERROR.getCode(),
					ResponseCode.RSL_CONNECTION_ERROR.getMessage(), ResponseCode.RSL_CONNECTION_ERROR.getService(),
					HttpStatus.INTERNAL_SERVER_ERROR, e);
		}
	}
}
