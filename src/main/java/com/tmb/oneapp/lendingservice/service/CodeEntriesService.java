package com.tmb.oneapp.lendingservice.service;

import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import javax.xml.rpc.ServiceException;

import org.springframework.stereotype.Service;

import com.tmb.common.logger.TMBLogger;
import com.tmb.common.model.legacy.rsl.common.ob.dropdown.CommonCodeEntry;
import com.tmb.common.model.legacy.rsl.ws.dropdown.request.Body;
import com.tmb.common.model.legacy.rsl.ws.dropdown.request.Header;
import com.tmb.common.model.legacy.rsl.ws.dropdown.request.RequestDropdown;
import com.tmb.common.model.legacy.rsl.ws.dropdown.response.ResponseDropdown;
import com.tmb.common.model.legacy.rsl.ws.loan.submission.LoanSubmissionGetDropdownListServiceLocator;
import com.tmb.common.model.legacy.rsl.ws.loan.submission.LoanSubmissionGetDropdownListSoapBindingStub;

@Service
public class CodeEntriesService {
	
	private static final TMBLogger<CustomerProfileService> logger = new TMBLogger<>(CustomerProfileService.class);

	private static LoanSubmissionGetDropdownListServiceLocator locator = new LoanSubmissionGetDropdownListServiceLocator();

	public List<CommonCodeEntry> loadEntry(String code, String channel, String module, String requestId) {
		locator.setLoanSubmissionGetDropdownListEndpointAddress(
				"http://10.209.27.99:9081/LoanSubmissionWS/services/LoanSubmissionGetDropdownList");
		List<CommonCodeEntry> commonCodeEntrys = new ArrayList<CommonCodeEntry>();
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
			response.getBody().getCommonCodeEntries();

			CommonCodeEntry[] codeEntrys = response.getBody().getCommonCodeEntries();
			if (Objects.nonNull(codeEntrys)) {
				for (CommonCodeEntry entry : codeEntrys) {
					commonCodeEntrys.add(entry);
				}
			}
		} catch (ServiceException e) {
			logger.error(e.toString(), e);
		} catch (RemoteException e) {
			logger.error(e.toString(), e);
		}
		return commonCodeEntrys;
	}

}
