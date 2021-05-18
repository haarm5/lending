package com.tmb.oneapp.lendingservice.service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;


import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.tmb.common.logger.TMBLogger;
import com.tmb.common.model.legacy.rsl.common.ob.dropdown.CommonCodeEntry;
import com.tmb.common.model.legacy.rsl.ws.dropdown.request.Body;
import com.tmb.common.model.legacy.rsl.ws.dropdown.request.Header;
import com.tmb.common.model.legacy.rsl.ws.dropdown.request.RequestDropdown;
import com.tmb.common.model.legacy.rsl.ws.dropdown.response.ResponseDropdown;
import com.tmb.common.model.legacy.rsl.ws.loan.submission.LoanSubmissionGetDropdownListServiceLocator;
import com.tmb.common.model.legacy.rsl.ws.loan.submission.LoanSubmissionGetDropdownListSoapBindingStub;

/**
 * Provides service to get master data from LoanSubmissionGetDropdownListService
 */
@Service
public class CodeEntriesService {

	private static final TMBLogger<CodeEntriesService> logger = new TMBLogger<>(CodeEntriesService.class);
	private LoanSubmissionGetDropdownListServiceLocator locator = new LoanSubmissionGetDropdownListServiceLocator();
	@Value("${rsl.service.name.loansubmmission.url}")
	private String loanSubmissionGetDropdownListUrl;

	public void setLocator(LoanSubmissionGetDropdownListServiceLocator locator) {
		this.locator = locator;
	}

	public List<CommonCodeEntry> loadEntry(String code, String channel, String module, String requestId) {
		locator.setLoanSubmissionGetDropdownListEndpointAddress(loanSubmissionGetDropdownListUrl);
		List<CommonCodeEntry> commonCodeEntrys = new ArrayList<>();
		try {
			LoanSubmissionGetDropdownListSoapBindingStub stub = (LoanSubmissionGetDropdownListSoapBindingStub) locator
					.getLoanSubmissionGetDropdownList();
			RequestDropdown req = new RequestDropdown();
			Body body = new Body();
			body.setCategoryCode(code);
			req.setBody(body);
			Header header = new Header();
			header.setChannel(channel);
			header.setModule(module);
			header.setRequestID(requestId);
			req.setHeader(header);
			ResponseDropdown response = stub.getDropDownListByCode(req);
			com.tmb.common.model.legacy.rsl.ws.dropdown.response.Header headerResponse = response.getHeader();
			if (!"MSG_000".equals(headerResponse.getResponseCode())) {
				logger.error("code: {},Fetching Master Data got error:{} {}", code, headerResponse.getResponseCode(),
						headerResponse.getResponseDescriptionEN());
				return commonCodeEntrys;
			}
			CommonCodeEntry[] codeEntrys = response.getBody().getCommonCodeEntries();
			if (Objects.nonNull(codeEntrys)) {
				commonCodeEntrys = new ArrayList<>(Arrays.asList(codeEntrys));
			}

		} catch (Exception e) {
			logger.error(e.toString(), e);
		}
		return commonCodeEntrys;
	}

}
