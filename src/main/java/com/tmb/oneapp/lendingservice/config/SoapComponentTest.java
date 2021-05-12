package com.tmb.oneapp.lendingservice.config;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import com.tmb.common.model.legacy.rsl.ws.dropdown.request.Body;
import com.tmb.common.model.legacy.rsl.ws.dropdown.request.Header;
import com.tmb.common.model.legacy.rsl.ws.dropdown.request.RequestDropdown;
import com.tmb.common.model.legacy.rsl.ws.dropdown.response.ResponseDropdown;
import com.tmb.common.model.legacy.rsl.ws.loan.submission.LoanSubmissionGetDropdownListServiceLocator;
import com.tmb.common.model.legacy.rsl.ws.loan.submission.LoanSubmissionGetDropdownListSoapBindingStub;
import com.tmb.common.model.legacy.rsl.ws.loan.submission.LoanSubmissionInstantLoanCreateApplicationServiceLocator;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.xml.rpc.ServiceException;
import java.rmi.RemoteException;

@Component
public class SoapComponentTest {


    @PostConstruct
    public void exe() {
        LoanSubmissionInstantLoanCreateApplicationServiceLocator l2 = new LoanSubmissionInstantLoanCreateApplicationServiceLocator();
        l2.setLoanSubmissionInstantLoanCreateApplicationEndpointAddress("http://10.209.27.99:9081/LoanSubmissionWS/services/LoanSubmissionGetDropdownList");


        LoanSubmissionGetDropdownListServiceLocator locator = new LoanSubmissionGetDropdownListServiceLocator();
        locator.setLoanSubmissionGetDropdownListEndpointAddress("http://10.209.27.99:9081/LoanSubmissionWS/services/LoanSubmissionGetDropdownList");
        try {
            LoanSubmissionGetDropdownListSoapBindingStub stub = (LoanSubmissionGetDropdownListSoapBindingStub) locator
                    .getLoanSubmissionGetDropdownList();
            RequestDropdown req = new RequestDropdown();
            Body body = new Body();
            body.setCategoryCode("PYMT_CRITERIA");
            req.setBody(body);
            Header header = new Header();
            header.setChannel("MIB");
            header.setModule("3");
            header.setRequestID("725a9ec5-5de0-4b95-a51f-9774b559b459");
            req.setHeader(header);
            ResponseDropdown response = stub.getDropDownListByCode(req);
            response.getBody().getCommonCodeEntries();
            ObjectMapper objectMapper = new ObjectMapper();
            try {
                String responseStr = objectMapper.writeValueAsString(response.getBody());
                System.out.println(responseStr);
            } catch (JsonProcessingException e) {
                e.printStackTrace();
            }
//            CommonCodeEntry[] codeEntrys = response.getBody().getCommonCodeEntries();
//            for (CommonCodeEntry a : codeEntrys) {
//
//                System.out.println(a.getEntryName());
//            }

        } catch (ServiceException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (RemoteException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

}
