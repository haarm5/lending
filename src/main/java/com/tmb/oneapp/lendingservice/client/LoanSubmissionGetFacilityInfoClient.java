package com.tmb.oneapp.lendingservice.client;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.tmb.common.exception.model.TMBCommonException;
import com.tmb.common.logger.TMBLogger;
import com.tmb.common.model.legacy.rsl.ws.facility.request.Body;
import com.tmb.common.model.legacy.rsl.ws.facility.request.Header;
import com.tmb.common.model.legacy.rsl.ws.facility.request.RequestFacility;
import com.tmb.common.model.legacy.rsl.ws.facility.response.ResponseFacility;
import com.tmb.common.model.legacy.rsl.ws.loan.submission.LoanSubmissionGetFacilityInfoServiceLocator;
import com.tmb.common.model.legacy.rsl.ws.loan.submission.LoanSubmissionGetFacilityInfoSoapBindingStub;
import com.tmb.oneapp.lendingservice.constant.ResponseCode;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import javax.xml.rpc.ServiceException;
import java.rmi.RemoteException;
import java.util.UUID;

@Service
public class LoanSubmissionGetFacilityInfoClient {

	private static final TMBLogger<LoanSubmissionGetFacilityInfoClient> logger = new TMBLogger<>(LoanSubmissionGetFacilityInfoClient.class);
	private final ObjectMapper mapper;

	@Value("${rsl.loan-submission-get-facility-info.url}")
	private String url;

	LoanSubmissionGetFacilityInfoServiceLocator locator = new LoanSubmissionGetFacilityInfoServiceLocator();

	private static final String CHANNEL = "MIB";
	private static final String MODULE = "3";

	public LoanSubmissionGetFacilityInfoClient(ObjectMapper mapper) {
		this.mapper = mapper;
	}

	public void setLocator(LoanSubmissionGetFacilityInfoServiceLocator locator) {
		this.locator = locator;
	}

	public ResponseFacility searchFacilityInfoByCaID(long caId) throws ServiceException, JsonProcessingException, TMBCommonException {
		locator.setLoanSubmissionGetFacilityInfoEndpointAddress(url);
		LoanSubmissionGetFacilityInfoSoapBindingStub stub = (LoanSubmissionGetFacilityInfoSoapBindingStub) locator
				.getLoanSubmissionGetFacilityInfo();
		logger.info("LoanSubmissionGetFacilityInfo Url: {}", url);

		RequestFacility request = new RequestFacility();
		request.setHeader(setHeader());
		request.setBody(setBody(caId));
		logger.info("LoanSubmissionGetFacilityInfo Request: {}", mapper.writeValueAsString(request));

		try {
			ResponseFacility response = stub.searchFacilityInfoByCaID(request);
			logger.info("LoanSubmissionGetFacilityInfo Response: {}", mapper.writeValueAsString(response));
			return response;
		} catch (RemoteException e) {
			throw new TMBCommonException(ResponseCode.RSL_CONNECTION_ERROR.getCode(), ResponseCode.RSL_CONNECTION_ERROR.getMessage(), ResponseCode.RSL_CONNECTION_ERROR.getService(), HttpStatus.INTERNAL_SERVER_ERROR, e);
		}
	}

	private Header setHeader() {
		Header header = new Header();
		header.setChannel(CHANNEL);
		header.setModule(MODULE);
		header.setRequestID(UUID.randomUUID().toString());
		return header;
	}

	private Body setBody(long caId) {
		Body body = new Body();
		body.setCaID(caId);
		return body;
	}
}
