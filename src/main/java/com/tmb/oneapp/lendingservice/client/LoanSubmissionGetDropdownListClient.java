package com.tmb.oneapp.lendingservice.client;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.tmb.common.exception.model.TMBCommonException;
import com.tmb.common.logger.TMBLogger;
import com.tmb.common.model.legacy.rsl.ws.dropdown.request.RequestDropdown;
import com.tmb.common.model.legacy.rsl.ws.dropdown.response.ResponseDropdown;
import com.tmb.common.model.legacy.rsl.ws.loan.submission.LoanSubmissionGetDropdownListServiceLocator;
import com.tmb.common.model.legacy.rsl.ws.loan.submission.LoanSubmissionGetDropdownListSoapBindingStub;
import com.tmb.oneapp.lendingservice.constant.ResponseCode;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import javax.xml.rpc.ServiceException;
import java.rmi.RemoteException;
import java.util.UUID;

@Service
public class LoanSubmissionGetDropdownListClient {

	private static final TMBLogger<LoanSubmissionGetDropdownListClient> logger = new TMBLogger<>(LoanSubmissionGetDropdownListClient.class);
	private final ObjectMapper mapper;

	@Value("${rsl.loan-submission-get-dropdown-list.url}")
	private String url;

	LoanSubmissionGetDropdownListServiceLocator locator = new LoanSubmissionGetDropdownListServiceLocator();

	private static final String CHANNEL = "MIB";
	private static final String MODULE = "3";

	public LoanSubmissionGetDropdownListClient(ObjectMapper mapper) {
		this.mapper = mapper;
	}

	public void setLocator(LoanSubmissionGetDropdownListServiceLocator locator) {
		this.locator = locator;
	}

	public ResponseDropdown getDropDownListByCode(String categoryCode) throws ServiceException, TMBCommonException, JsonProcessingException {
		locator.setLoanSubmissionGetDropdownListEndpointAddress(url);
		LoanSubmissionGetDropdownListSoapBindingStub stub = (LoanSubmissionGetDropdownListSoapBindingStub) locator
				.getLoanSubmissionGetDropdownList();
		logger.info("LoanSubmissionGetDropdownList Url: {}", url);

		RequestDropdown request = new RequestDropdown();
		request.setHeader(getHeader());
		request.setBody(getBody(categoryCode));
		logger.info("LoanSubmissionGetDropdownList Request: {}", mapper.writeValueAsString(request));

		try {
			ResponseDropdown response = stub.getDropDownListByCode(request);
			logger.info("LoanSubmissionGetDropdownList Response: {}", mapper.writeValueAsString(response));
			return response;
		} catch (RemoteException e) {
			throw new TMBCommonException(ResponseCode.RSL_CONNECTION_ERROR.getCode(), ResponseCode.RSL_CONNECTION_ERROR.getMessage(), ResponseCode.RSL_CONNECTION_ERROR.getService(), HttpStatus.INTERNAL_SERVER_ERROR, e);
		}
	}

	private com.tmb.common.model.legacy.rsl.ws.dropdown.request.Header getHeader() {
		com.tmb.common.model.legacy.rsl.ws.dropdown.request.Header header = new com.tmb.common.model.legacy.rsl.ws.dropdown.request.Header();
		header.setChannel(CHANNEL);
		header.setModule(MODULE);
		header.setRequestID(UUID.randomUUID().toString());
		return header;
	}

	private com.tmb.common.model.legacy.rsl.ws.dropdown.request.Body getBody(String categoryCode) {
		com.tmb.common.model.legacy.rsl.ws.dropdown.request.Body body = new com.tmb.common.model.legacy.rsl.ws.dropdown.request.Body();
		body.setCategoryCode(categoryCode);
		return body;
	}

}
