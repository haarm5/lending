package com.tmb.oneapp.lendingservice.client;

import com.tmb.common.model.legacy.rsl.common.ob.individual.Individual;
import com.tmb.common.model.legacy.rsl.ws.individual.update.request.Body;
import com.tmb.common.model.legacy.rsl.ws.individual.update.request.Header;
import com.tmb.common.model.legacy.rsl.ws.individual.update.request.RequestIndividual;
import com.tmb.common.model.legacy.rsl.ws.individual.update.response.ResponseIndividual;
import com.tmb.common.model.legacy.rsl.ws.loan.submission.LoanSubmissionUpdateCustomerServiceLocator;
import com.tmb.common.model.legacy.rsl.ws.loan.submission.LoanSubmissionUpdateCustomerSoapBindingStub;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.xml.rpc.ServiceException;
import java.rmi.RemoteException;
import java.util.UUID;

@Service
public class LoanSubmissionUpdateCustomerClient {
    @Value("${loan-submission-update-customer-info.url}")
    private String updateCustomerInfo;

    LoanSubmissionUpdateCustomerServiceLocator locator = new LoanSubmissionUpdateCustomerServiceLocator();

    private static final String CHANNEL = "MIB";
    private static final String MODULE = "3";

    private void setLocator(LoanSubmissionUpdateCustomerServiceLocator locator) {
        this.locator = locator;
    }

    public ResponseIndividual updateCustomerInfo(Individual individual) throws RemoteException, ServiceException {
        locator.setLoanSubmissionUpdateCustomerEndpointAddress(updateCustomerInfo);

        LoanSubmissionUpdateCustomerSoapBindingStub stub = (LoanSubmissionUpdateCustomerSoapBindingStub) locator.getLoanSubmissionUpdateCustomer();

        RequestIndividual req = new RequestIndividual();

        Header header = new Header();
        header.setChannel(CHANNEL);
        header.setModule(MODULE);
        header.setRequestID(UUID.randomUUID().toString());
        req.setHeader(header);

        Body body = new Body();
        body.setIndividual(individual);

        return stub.updateCustomerInfo(req);


    }
}
