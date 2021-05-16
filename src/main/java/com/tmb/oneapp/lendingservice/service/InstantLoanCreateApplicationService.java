package com.tmb.oneapp.lendingservice.service;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.tmb.common.logger.TMBLogger;
import com.tmb.common.model.legacy.rsl.common.ob.address.Address;
import com.tmb.common.model.legacy.rsl.common.ob.application.InstantApplication;
import com.tmb.common.model.legacy.rsl.common.ob.creditcard.InstantCreditCard;
import com.tmb.common.model.legacy.rsl.common.ob.facility.InstantFacility;
import com.tmb.common.model.legacy.rsl.ob.individual.InstantIndividual;
import com.tmb.common.model.legacy.rsl.ws.instant.application.create.request.Body;
import com.tmb.common.model.legacy.rsl.ws.instant.application.create.request.Header;
import com.tmb.common.model.legacy.rsl.ws.instant.application.create.request.RequestInstantLoanCreateApplication;
import com.tmb.common.model.legacy.rsl.ws.instant.application.create.response.ResponseInstantLoanCreateApplication;
import com.tmb.oneapp.lendingservice.client.InstantLoanCreateApplicationClient;
import com.tmb.oneapp.lendingservice.model.instantloancreation.*;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import javax.xml.rpc.ServiceException;
import java.math.BigDecimal;
import java.rmi.RemoteException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;

@Service
public class InstantLoanCreateApplicationService {
    private static final TMBLogger<InstantLoanCreateApplicationService> logger = new TMBLogger<>(InstantLoanCreateApplicationService.class);
    private final ObjectMapper mapper;
    private final InstantLoanCreateApplicationClient soapClient;

    public InstantLoanCreateApplicationService(ObjectMapper mapper, InstantLoanCreateApplicationClient soapClient) {
        this.mapper = mapper;
        this.soapClient = soapClient;
    }


      public InstantLoanCreationResponse createInstantLoanApplication(InstantLoanCreationRequest request){


        RequestInstantLoanCreateApplication soapRequest = new RequestInstantLoanCreateApplication();

        try {
            logger.info("In try ");
            logger.info("Client Request for  InstantLoanCreateApplication is {} : " +mapper.writeValueAsString(request));

            // Address
            List<AddressInfo> addressInfoList = request.getAddresses();
            List<Address> soapAddressList = addressInfoList.stream().map(this::addressToSoapRequestAddress).collect(Collectors.toList());
            InstantIndividual soapInstantIndividual = getInstantIndividualObject(request);
            if(request.getLoanType().equalsIgnoreCase("CC")){
                logger.info("Calling Credit Cards");
                // Credit card Data
                List<CreditCardLoanInfo> creditCardLoanInfo = request.getCreditCards();
                List<InstantCreditCard> soapCreditCardList = creditCardLoanInfo.stream().map(this::creditCardInfoToSoapRequestCC).collect(Collectors.toList());
                soapInstantIndividual.setCreditCards(soapCreditCardList.toArray(new InstantCreditCard[0]));
                logger.info("soapCreditCardList size is  {} : ",String.valueOf(soapCreditCardList.size()));
            }
            soapInstantIndividual.setAddresses(soapAddressList.toArray(new Address[0]));
            logger.info("soapAddressList size is  {} : ",String.valueOf(soapAddressList.size()));

            InstantApplication soapInstantApplication = new InstantApplication();
            // Flash Card or C2G
            if(!request.getLoanType().equalsIgnoreCase("CC")){
                logger.info("Calling Flash Card or C2G");
                List<FlashCardOrC2GLoanInfo> facilitiesInfo = request.getFlashCardOrC2G();
                List<InstantFacility> soapInstantFacilityList = facilitiesInfo.stream().map(this::facilitiesInfoToSoapRequestInstantFacility).collect(Collectors.toList());
                soapInstantApplication.setFacilities(soapInstantFacilityList.toArray(new InstantFacility[soapInstantFacilityList.size()]));
            }


            soapInstantApplication.setIndividuals(new InstantIndividual[]{soapInstantIndividual});
            soapInstantApplication.setNatureOfRequest(request.getNatureOfRequest());
            soapInstantApplication.setNcbConsentFlag(request.getNcbConsentFlag());
            soapInstantApplication.setSaleChannel(request.getSaleChannel());
            soapInstantApplication.setAppType(request.getAppType());
            soapInstantApplication.setAuthenCode(request.getAuthenCode());
            soapInstantApplication.setBookBranchCode(request.getBookBranchCode());
            soapInstantApplication.setBranchCode(request.getBranchCode());

            Body soapRequestBody = new Body();
            soapRequestBody.setInstantApplication(soapInstantApplication);
            soapRequestBody.setTransactionType(request.getTransactionType());

            Header soapRequestHeader = new Header();
            soapRequestHeader.setRequestID(request.getRequestID());
            soapRequestHeader.setModule(request.getModule());
            soapRequestHeader.setChannel(request.getChannel());

            soapRequest.setBody(soapRequestBody);
            soapRequest.setHeader(soapRequestHeader);
            ResponseInstantLoanCreateApplication soapResponse = soapClient.callLoanSubmissionInstantLoanCreateApplication(soapRequest);
            return constructCreateLoanApplicationResponse(soapResponse);
        } catch (JsonProcessingException | ParseException | RemoteException | ServiceException e) {
            logger.error("Exception {} : ",e.toString());
        }
        InstantLoanCreationResponse response = new InstantLoanCreationResponse();
        response.setError("exception");
        return response;
    }

    private InstantLoanCreationResponse constructCreateLoanApplicationResponse(ResponseInstantLoanCreateApplication soapResponse){

        InstantLoanCreationResponse response = new InstantLoanCreationResponse();

        String responseCode = soapResponse.getHeader().getResponseCode();
        if(responseCode.equalsIgnoreCase("MSG_000")){
            response.setRequestId(soapResponse.getHeader().getRequestID());
            response.setChannel(soapResponse.getHeader().getChannel());
            response.setModule(soapResponse.getHeader().getModule());
            /**  response.setCaId(soapResponse.getBody().getCaId());
           response.setAppRefNo(soapResponse.getBody().gera);
             */
            response.setMemberRef(soapResponse.getBody().getMemberref());
            response.setCreateDate(soapResponse.getBody().getCreateDate());
            response.setCurrentWorkflow(soapResponse.getBody().getCurrentWorkflow());
        }else{
            response.setError(responseCode);
        }

        return response;
    }

    private InstantIndividual getInstantIndividualObject(InstantLoanCreationRequest request) throws ParseException {
        InstantIndividual individual = new InstantIndividual();
        CustomerInfo customerInfo = request.getCustomerInfo();
        Calendar calBirthDate = Calendar.getInstance();
        Calendar calIssueDate = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSS", Locale.ENGLISH);
        calBirthDate.setTime(sdf.parse(customerInfo.getBirthDate()));
        calIssueDate.setTime(sdf.parse(customerInfo.getIssuedDate()));
        individual.setBirthDate(calBirthDate);
        individual.setBusinessSubType(null);
        /** is empty check with k.pump customerInfo.getBusinessSubType()
         *
         */
        individual.setBusinessType(customerInfo.getBusinessType());
        individual.setCifRelCode(customerInfo.getCifRelCode());
        individual.setDiscloseCustInfoFlag(customerInfo.getDiscloseCustInfoFlag());
        individual.setEmail(customerInfo.getEmail());
        individual.setEmailStatementFlag(customerInfo.getEmailStatementFlag());
        individual.setEmploymentName(customerInfo.getEmploymentName());
        individual.setEmploymentOccupation(customerInfo.getEmploymentOccupation());
        individual.setEmploymentStatus(customerInfo.getEmploymentStatus());
        individual.setEmploymentTelephoneDirectNo(customerInfo.getEmploymentTelephoneDirectNo());
        individual.setEmploymentTelephoneExtNo(customerInfo.getEmploymentTelephoneExtNo());
        individual.setHostCifNo(customerInfo.getHostCifNo());
        individual.setIdNo1(customerInfo.getIdNo1());
        individual.setIdType1(customerInfo.getIdType1());
        String incomeBasicSalary = customerInfo.getEmploymentStatus().equalsIgnoreCase("01")? customerInfo.getIncomeBasicSalary() : "";
        individual.setIncomeBasicSalary(convertStringToBigDecimal(incomeBasicSalary));
        String incomeDeclared = customerInfo.getEmploymentStatus().equalsIgnoreCase("02")? customerInfo.getIncomeDeclared() : "";
        individual.setIncomeDeclared(convertStringToBigDecimal(incomeDeclared));
        individual.setIncomeType(customerInfo.getIncomeType());
        individual.setIssuedDate(calIssueDate);
        individual.setMobileNo(customerInfo.getMobileNo());
        individual.setNameLine1(customerInfo.getNameLine1());
        individual.setNameLine2(customerInfo.getNameLine2());
        individual.setNationality(customerInfo.getNationality());
        individual.setNcbConsentFlag(customerInfo.getNcbConsentFlag());
        individual.setProfessionalCode(customerInfo.getProfessionalCode());
        individual.setRmOccupation(customerInfo.getRmOccupation());
        individual.setSourceFromCountry(customerInfo.getSourceFromCountry());
        individual.setThaiName(customerInfo.getThaiName());
        individual.setThaiSurName(customerInfo.getThaiSurName());

        return individual;
    }

    private Address addressToSoapRequestAddress(AddressInfo address){

        Address soapAddressObj = new Address();
        soapAddressObj.setAddrTypCode(address.getAddrTypCode());
        soapAddressObj.setAddress(address.getAddress());
        soapAddressObj.setBuildingName(address.getBuildingName());
        soapAddressObj.setStreetName(address.getStreetName());
        soapAddressObj.setPostalCode(address.getPostalCode());
        soapAddressObj.setCountry(address.getCountry());
        soapAddressObj.setTumbol(address.getTumbol());
        soapAddressObj.setRoad(address.getRoad());
        soapAddressObj.setMoo(address.getMoo());
        soapAddressObj.setAmphur(address.getAmphur());
        soapAddressObj.setProvince(address.getProvince());
        soapAddressObj.setFloor(address.getFloor());

        return  soapAddressObj;
    }

    private InstantCreditCard creditCardInfoToSoapRequestCC(CreditCardLoanInfo creditCardLoanInfo){

        InstantCreditCard soapCreditCardLoanData = new InstantCreditCard();
        soapCreditCardLoanData.setCardInd(creditCardLoanInfo.getCardInd());
        soapCreditCardLoanData.setProductType(creditCardLoanInfo.getProductType());

        String cardBrand = creditCardLoanInfo.getProductType().equalsIgnoreCase("MS") ? "1" : "0";
        soapCreditCardLoanData.setCardBrand(cardBrand);
        soapCreditCardLoanData.setCampaignCode(creditCardLoanInfo.getCampaignCode());
        soapCreditCardLoanData.setRequestCreditLimit(convertStringToBigDecimal(creditCardLoanInfo.getRequestCreditLimit()));
        soapCreditCardLoanData.setPaymentMethod(creditCardLoanInfo.getPaymentMethod());
        soapCreditCardLoanData.setMailPreference(creditCardLoanInfo.getMailPreference());
        soapCreditCardLoanData.setCardDeliveryAddress(creditCardLoanInfo.getCardDelivery());

       /** soapCreditCardLoanData.setdebitAccountName
        soapCreditCardLoanData.setdebitAccountNo
        soapCreditCardLoanData.setpaymentCriteria
        */

        return  soapCreditCardLoanData;
    }




    private InstantFacility facilitiesInfoToSoapRequestInstantFacility(FlashCardOrC2GLoanInfo facilitiesInfo){
        InstantFacility soapFacility = new InstantFacility();
        soapFacility.setFacilityCode(facilitiesInfo.getFacilityCode());
        soapFacility.setProductCode(facilitiesInfo.getProductCode());
        soapFacility.setCaCampaignCode(facilitiesInfo.getCaCampaignCode());
        soapFacility.setLimitApplied(convertStringToBigDecimal(facilitiesInfo.getLimitApplied()));
        soapFacility.setInterestRate(convertStringToBigDecimal(facilitiesInfo.getInterestRate())); //interestRate k.pump
        soapFacility.setLoanWithOtherBank(facilitiesInfo.getLoanWithOtherBank());
        soapFacility.setConsiderLoanWithOtherBank(facilitiesInfo.getConsiderLoanWithOtherBank());
        soapFacility.setPaymentMethod(facilitiesInfo.getPaymentMethod());
        soapFacility.setPaymentAccountName(facilitiesInfo.getPaymentAccountName());
        soapFacility.setPaymentAccountNo(facilitiesInfo.getPaymentAccountNo());
        soapFacility.setMailingPreference(facilitiesInfo.getMailPreference());

        String loanType = "F";

        if(loanType.equalsIgnoreCase("F")){
            soapFacility.setCardDelivery(facilitiesInfo.getCardDelivery());
            soapFacility.setPayMethodCriteria(facilitiesInfo.getPaymentCriteria());

        }else{
            soapFacility.setMonthlyInstallment(convertStringToBigDecimal(facilitiesInfo.getMonthlyInstallment()));
            soapFacility.setTenure(convertStringToBigDecimal(facilitiesInfo.getTenure()));
            soapFacility.setPaymentDueDate(facilitiesInfo.getPaymentDueDate());
            soapFacility.setFirstPaymentDueDate(facilitiesInfo.getFirstPaymentDueDate());
            soapFacility.setDisburstBankName(facilitiesInfo.getDisburstBankName());
            soapFacility.setDisburstAccountName(facilitiesInfo.getDisburstAccountName());
            soapFacility.setDisburstAccountNo(facilitiesInfo.getDisburstAccountNo());
        }


        return soapFacility;
    }

    public BigDecimal convertStringToBigDecimal(String data){

        if(StringUtils.isNotBlank(data))
            return new BigDecimal(data);

        return null;
    }
}

