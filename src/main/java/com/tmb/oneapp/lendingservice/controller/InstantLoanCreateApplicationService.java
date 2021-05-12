package com.tmb.oneapp.lendingservice.controller;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.tmb.common.model.legacy.rsl.common.ob.address.Address;
import com.tmb.common.model.legacy.rsl.common.ob.application.InstantApplication;
import com.tmb.common.model.legacy.rsl.common.ob.creditcard.InstantCreditCard;
import com.tmb.common.model.legacy.rsl.ob.individual.InstantIndividual;
import com.tmb.common.model.legacy.rsl.ws.instant.application.create.request.Body;
import com.tmb.common.model.legacy.rsl.ws.instant.application.create.request.Header;
import com.tmb.common.model.legacy.rsl.ws.instant.application.create.request.RequestInstantLoanCreateApplication;
import com.tmb.common.model.legacy.rsl.ws.instant.application.create.response.ResponseInstantLoanCreateApplication;
import com.tmb.common.model.legacy.rsl.ws.loan.submission.LoanSubmissionInstantLoanCreateApplicationServiceLocator;
import com.tmb.common.model.legacy.rsl.ws.loan.submission.LoanSubmissionInstantLoanCreateApplicationSoapBindingStub;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.xml.rpc.ServiceException;
import java.math.BigDecimal;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.List;

@Component
public class InstantLoanCreateApplicationService {


    @PostConstruct
    public void createLoan() throws RemoteException, ServiceException {
        System.err.println("calling createLoan...");
        ObjectMapper objectMapper = new ObjectMapper();

        LoanSubmissionInstantLoanCreateApplicationServiceLocator locator = new LoanSubmissionInstantLoanCreateApplicationServiceLocator();
        locator.setLoanSubmissionInstantLoanCreateApplicationEndpointAddress("http://10.209.27.99:9081/LoanSubmissionWS/services/LoanSubmissionInstantLoanCreateApplication");

        LoanSubmissionInstantLoanCreateApplicationSoapBindingStub stub = (LoanSubmissionInstantLoanCreateApplicationSoapBindingStub) locator.getLoanSubmissionInstantLoanCreateApplication();

        RequestInstantLoanCreateApplication request = new RequestInstantLoanCreateApplication();

        Body body = new Body();
        body.setTransactionType("INST");
        //Date date1 = new Date("yyyy-MM-ddTHH:mm:ss.SSSZ");
        List<InstantIndividual> InstantIndividualList = new ArrayList<>();
        InstantApplication instantApplicationReq = new InstantApplication();
        instantApplicationReq.setAppType("CC");
        instantApplicationReq.setAuthenCode("Access Pin");
        instantApplicationReq.setBookBranchCode("026");
        instantApplicationReq.setBranchCode("026");

        InstantIndividual individual = new InstantIndividual();
        //Date birthDt = new Date("1986-08-26T00:00:00.000Z");
        individual.setBirthDate(null);
        //  individual.setBusinessSubType(); is empty check with k.pump
        individual.setBusinessType("DK00");
        individual.setCifRelCode("M");
        fillAddress(individual);
        fillCreditcard(individual);
        //individual.setCreditCards();
        individual.setDiscloseCustInfoFlag("Y");
        individual.setEmail("40654@TMBBANKCOM");
        individual.setEmailStatementFlag("Y");
        individual.setEmploymentName("TMB BANK PUBLIC COMPANY LIMITED");
        individual.setEmploymentOccupation("04");
        individual.setEmploymentStatus("01");
        individual.setEmploymentTelephoneDirectNo("022991000");
        individual.setEmploymentTelephoneExtNo("1094");
        individual.setHostCifNo("00000018593706");
        individual.setIdNo1("1846622310794");
        individual.setIdType1("CI");
        individual.setIncomeBasicSalary(new BigDecimal("50000"));
        //  individual.setIncomeDeclared(); is empty check with k.pump
        individual.setIncomeType("8");
        individual.setIssuedDate(null);
        individual.setMobileNo("0953626563");
        individual.setNameLine1("ONEAPPTHREE");
        individual.setNameLine2("FLEXILOAN NA TEETEEBEE");
        individual.setNationality("TH");
        individual.setNcbConsentFlag("Y");
        individual.setProfessionalCode("13");
        individual.setRmOccupation("302");
        individual.setSourceFromCountry("TH");
        individual.setThaiName("à¸§à¸±à¸™à¹à¸­à¸žà¸ªà¸²à¸¡");
        individual.setThaiSurName("à¹€à¸Ÿà¸¥à¸à¸‹à¸µà¹ˆà¹‚à¸¥à¸™ à¸“ à¸—à¸µà¸—à¸µà¸šà¸µ");

        InstantIndividualList.add(individual);
        instantApplicationReq.setIndividuals(InstantIndividualList.toArray(new InstantIndividual[0]));
        body.setInstantApplication(instantApplicationReq);

        Header header = new Header();
        header.setChannel("MIB");
        header.setModule("3");
        header.setRequestID("aaaa");

        request.setBody(body);
        request.setHeader(header);


        try {
            System.out.println("request to  $$$$$   " + objectMapper.writeValueAsString(request));
            ResponseInstantLoanCreateApplication response = stub.createInstantLoanApplication(request);

            //response.getBody().getAppType();
            String responseStr = objectMapper.writeValueAsString(response.getHeader());
            System.out.println("response is  $$$$$   " + responseStr);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println(" Exception $$$$$ " + e.toString());
        }


    }


    public static void fillAddress(InstantIndividual individual) {
        List<Address> addrList = new ArrayList<>();
        Address addressObj = new Address();
        addressObj.setAddrTypCode("H");
        addressObj.setAddress("11/222");
        addressObj.setBuildingName("equinox");
        addrList.add(addressObj);
        individual.setAddresses(addrList.toArray(new Address[0]));

    }

    public static void fillCreditcard(InstantIndividual individual) {
        List<InstantCreditCard> addrList = new ArrayList<>();
        InstantCreditCard ccObj = new InstantCreditCard();
        ccObj.setCardInd("P");
        ccObj.setProductType("VM");
        ccObj.setCardBrand("1");
        ccObj.setCampaignCode("BB32");
        ccObj.setRequestCreditLimit(new BigDecimal("1500000"));
    }

}

