package com.tmb.oneapp.lendingservice.config;

import java.rmi.RemoteException;

import javax.annotation.PostConstruct;
import javax.xml.rpc.ServiceException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.integrosys.sml.common.ob.dropdown.CommonCodeEntry;
import com.integrosys.sml.ws.dropdown.request.Body;
import com.integrosys.sml.ws.dropdown.request.Header;
import com.integrosys.sml.ws.dropdown.request.RequestDropdown;
import com.integrosys.sml.ws.dropdown.response.ResponseDropdown;
import com.integrosys.sml.ws.loan.submission.LoanSubmissionGetDropdownListService;
import com.integrosys.sml.ws.loan.submission.LoanSubmissionGetDropdownListServiceLocator;
import com.integrosys.sml.ws.loan.submission.LoanSubmissionGetDropdownListSoapBindingStub;
import com.tmb.oneapp.lendingservice.SOAPConnector;

@Component
public class SoapComponentTest {



	@PostConstruct
	public void exe() {
		LoanSubmissionGetDropdownListServiceLocator locator = new LoanSubmissionGetDropdownListServiceLocator();
		locator.setLoanSubmissionGetDropdownListEndpointAddress("http://10.209.27.99:9081/LoanSubmissionWS/services/LoanSubmissionGetDropdownList");
		try {
			LoanSubmissionGetDropdownListSoapBindingStub stub = (LoanSubmissionGetDropdownListSoapBindingStub) locator
					.getLoanSubmissionGetDropdownList();
			RequestDropdown req = new RequestDropdown();
			Body body = new Body();
			body.setCategoryCode("EMPLOYMENT_STATUS");
			req.setBody(body);
			Header header = new Header();
			header.setChannel("MIB");
			header.setModule("3");
			header.setRequestID("725a9ec5-5de0-4b95-a51f-9774b559b459");
			req.setHeader(header);
			ResponseDropdown response = stub.getDropDownListByCode(req);
			response.getBody().getCommonCodeEntries();
			
			CommonCodeEntry[] codeEntrys = response.getBody().getCommonCodeEntries();
			for(CommonCodeEntry a :codeEntrys) {
				
				System.out.println(a.getEntryName());
			}
			
		} catch (ServiceException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
