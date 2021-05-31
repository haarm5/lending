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
import com.tmb.oneapp.lendingservice.client.FTPServerLOCClient;
import com.tmb.oneapp.lendingservice.client.InstantLoanCreateApplicationClient;
import com.tmb.oneapp.lendingservice.model.instantloancreation.*;
import com.tmb.oneapp.lendingservice.util.CommonServiceUtils;
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
import java.util.UUID;
import java.util.stream.Collectors;



@Service
public class InstantLoanCreateApplicationService {
    private static final TMBLogger<InstantLoanCreateApplicationService> logger = new TMBLogger<>(InstantLoanCreateApplicationService.class);
    private final ObjectMapper mapper;
    private final InstantLoanCreateApplicationClient soapClient;
    private final FTPServerLOCClient ftpServerLOCClient;
    private static final String BRANCH_CODE = "026";
    private static final String SALE_CHANNEL = "05";

    private String getMoreFlag = "";

    LOCRequest locRequest = new LOCRequest();
    public InstantLoanCreateApplicationService(ObjectMapper mapper, InstantLoanCreateApplicationClient soapClient, FTPServerLOCClient ftpServerLOCClient) {
        this.mapper = mapper;
        this.soapClient = soapClient;
        this.ftpServerLOCClient = ftpServerLOCClient;
    }


    /**
     * method  to call InstantLoanCreateApplication service of legacy system for Credit cards, Flash cards, C2G
     *
     * @param request InstantLoanCreationRequest
     *
     * @return InstantLoanCreationResponse
     */
      public InstantLoanCreationResponse createInstantLoanApplication(InstantLoanCreationRequest request){


        RequestInstantLoanCreateApplication soapRequest = new RequestInstantLoanCreateApplication();

        try {

            logger.info(" Request from Client to InstantLoanCreateApplication is {} : " +mapper.writeValueAsString(request));
            getMoreFlag = request.getGetMore();
            // Address
            List<AddressInfo> addressInfoList = request.getAddresses();
            List<Address> soapAddressList = addressInfoList.stream().map(this::addressToSoapRequestAddress).collect(Collectors.toList());
            InstantIndividual soapInstantIndividual = getInstantIndividualObject(request);

            // Credit card Data
            if(request.getLoanType().equalsIgnoreCase("CC")){
                logger.info("Calling Credit Cards");
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
                List<InstantFacility> soapInstantFacilityList = facilitiesInfo.stream().map(facility -> facilitiesInfoToSoapRequestInstantFacility(facility,request.getLoanType())).collect(Collectors.toList());
                soapInstantApplication.setFacilities(soapInstantFacilityList.toArray(new InstantFacility[soapInstantFacilityList.size()]));
            }


            soapInstantApplication.setIndividuals(new InstantIndividual[]{soapInstantIndividual});
            soapInstantApplication.setNatureOfRequest(request.getNatureOfRequest());
            soapInstantApplication.setNcbConsentFlag(request.getNcbConsentFlag());
            soapInstantApplication.setAppType(request.getAppType());
            soapInstantApplication.setSaleChannel(SALE_CHANNEL);
            soapInstantApplication.setAuthenCode("Access Pin");
            soapInstantApplication.setBookBranchCode(BRANCH_CODE);
            soapInstantApplication.setBranchCode(BRANCH_CODE);

            Body soapRequestBody = new Body();
            soapRequestBody.setInstantApplication(soapInstantApplication);

            String transactionType = getMoreFlag.equalsIgnoreCase("Y") ? "MIB" : "INST";
            soapRequestBody.setTransactionType(transactionType);

            Header soapRequestHeader = new Header();
            String requestId = String.valueOf(UUID.randomUUID());
            soapRequestHeader.setRequestID(requestId);
            soapRequestHeader.setModule("3");
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


    /**
     * method  to call to construct InstantLoanCreateApplication response for Credit cards, Flasg crds, C2G
     *
     * @param soapResponse ResponseInstantLoanCreateApplication
     *
     * @return InstantLoanCreationResponse
     */
    private InstantLoanCreationResponse constructCreateLoanApplicationResponse(ResponseInstantLoanCreateApplication soapResponse){

        InstantLoanCreationResponse response = new InstantLoanCreationResponse();
        com.tmb.common.model.legacy.rsl.ws.instant.application.create.response.Body responseBody = soapResponse.getBody();
        String responseCode = soapResponse.getHeader().getResponseCode();
        if(responseCode.equalsIgnoreCase("MSG_000")){
            response.setRequestId(soapResponse.getHeader().getRequestID());
            response.setChannel(soapResponse.getHeader().getChannel());
            response.setModule(soapResponse.getHeader().getModule());
            /**  response.setCaId(soapResponse.getBody().getCaId());
           response.setAppRefNo(soapResponse.getBody().gera);
             */
            response.setMemberRef(responseBody.getMemberref());
            response.setCreateDate(responseBody.getCreateDate());
            response.setCurrentWorkflow(responseBody.getCurrentWorkflow());
            response.setProductDescEN(responseBody.getProductDescEN());

            String productName = responseBody.getProductDescTH();
            productName = responseBody.getAppType().equalsIgnoreCase("CC")? productName + " (22)" : productName +" (05)";
            locRequest.setNCBReferenceID(response.getMemberRef());
            locRequest.setNCBDateTime(response.getCreateDate());
            locRequest.setProductName(productName);
            locRequest.setAppRefNo(responseBody.getAppRefNo());
            constructRequestForLOCCompleteImage();
        }else{
            response.setError(responseCode);
        }

        return response;
    }


    private void constructRequestForLOCCompleteImage(){

        logger.info("constructRequestForLOCCompleteImage Start");
        locRequest.setConsentbyCustomer("Access PIN");
        String dob = locRequest.getNCBDateofbirth();
        dob = dob.substring(0,10);
        locRequest.setNCBDateofbirth(dob);

        String mobno = locRequest.getNCBMobileNo();
        locRequest.setNCBMobileNo(CommonServiceUtils.formatPhoneNumber(mobno));

        String customerId = locRequest.getNcbid();
        locRequest.setNcbid(CommonServiceUtils.formatCustomerId(customerId));

        try {
            ftpServerLOCClient.generateLOCImageAndUploadToFTP(locRequest);
        } catch (JsonProcessingException e) {
           logger.error("JsonProcessingException {} : ",e);
        }

        logger.info("constructRequestForLOCCompleteImage END");


    }
    /**
     * method to construct InstantIndividual Object request to legacy system
     *
     * @param request InstantLoanCreationRequest
     *
     * @return InstantIndividual
     */
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
        individual.setBusinessType(customerInfo.getBusinessType());
        individual.setCifRelCode("M");
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

        String employmentStatus = customerInfo.getEmploymentStatus();
        String incomeDeclared = "";
        String incomeBasicSalary = "";
        if(employmentStatus.equalsIgnoreCase("02")){
             incomeDeclared = getMoreFlag.equalsIgnoreCase("Y") ?  customerInfo.getMaxIncomeRange() : customerInfo.getIncomeDeclared();
        }

        if(employmentStatus.equalsIgnoreCase("01")){
            incomeBasicSalary = getMoreFlag.equalsIgnoreCase("Y") ?  customerInfo.getMaxIncomeRange() : customerInfo.getIncomeBasicSalary();
        }

        individual.setIncomeBasicSalary(convertStringToBigDecimal(incomeBasicSalary));
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

        String customerFullName= individual.getThaiName() + " " + individual.getThaiSurName();
        locRequest.setNCBMobileNo(customerInfo.getMobileNo());
        locRequest.setNCBDateofbirth(customerInfo.getBirthDate());
        locRequest.setNcbid(customerInfo.getIdNo1());
        locRequest.setNCBCustName(customerFullName);
        locRequest.setCrmId(customerInfo.getHostCifNo());
        return individual;
    }



    /**
     * method to construct address Object request to legacy system
     *
     * @param address AddressInfo
     *
     * @return Address
     */
    private Address addressToSoapRequestAddress(AddressInfo address){

        Address soapAddressObj = new Address();
        soapAddressObj.setAddrTypCode(address.getAddrTypCode());
        soapAddressObj.setAddress(address.getAddress());
        soapAddressObj.setBuildingName(address.getBuildingName());
        soapAddressObj.setStreetName(address.getStreetName());
        soapAddressObj.setPostalCode(address.getPostalCode());
        soapAddressObj.setCountry("TH");
        soapAddressObj.setTumbol(address.getTumbol());
        soapAddressObj.setRoad(address.getRoad());
        soapAddressObj.setMoo(address.getMoo());
        soapAddressObj.setAmphur(address.getAmphur());
        soapAddressObj.setProvince(address.getProvince());
        soapAddressObj.setFloor(address.getFloor());

        return  soapAddressObj;
    }


    /**
     * method to construct creditCard Info Object request to legacy system
     *
     * @param creditCardLoanInfo CreditCardLoanInfo
     *
     * @return InstantCreditCard
     */
    private InstantCreditCard creditCardInfoToSoapRequestCC(CreditCardLoanInfo creditCardLoanInfo){

        InstantCreditCard soapCreditCardLoanData = new InstantCreditCard();
        soapCreditCardLoanData.setCardInd("P");
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



    /**
     * method to construct FlashCardOrC2GLoan Info Object request to legacy system
     *
     * @param facilitiesInfo FlashCardOrC2GLoanInfo
     *
     * @return InstantFacility
     */
    private InstantFacility facilitiesInfoToSoapRequestInstantFacility(FlashCardOrC2GLoanInfo facilitiesInfo, String loanType){
        InstantFacility soapFacility = new InstantFacility();
        soapFacility.setFacilityCode(facilitiesInfo.getFacilityCode());
        soapFacility.setProductCode(facilitiesInfo.getProductCode());
        soapFacility.setCaCampaignCode(facilitiesInfo.getCaCampaignCode());
        soapFacility.setLimitApplied(convertStringToBigDecimal(facilitiesInfo.getLimitApplied()));
        soapFacility.setInterestRate(convertStringToBigDecimal(facilitiesInfo.getInterestRate()));
        soapFacility.setLoanWithOtherBank(facilitiesInfo.getLoanWithOtherBank());
        soapFacility.setConsiderLoanWithOtherBank(facilitiesInfo.getConsiderLoanWithOtherBank());
        soapFacility.setPaymentMethod(facilitiesInfo.getPaymentMethod());
        soapFacility.setPaymentAccountName(facilitiesInfo.getPaymentAccountName());
        soapFacility.setPaymentAccountNo(facilitiesInfo.getPaymentAccountNo());
        soapFacility.setMailingPreference(facilitiesInfo.getMailPreference());

        if(loanType.equalsIgnoreCase("F")){
            soapFacility.setCardDelivery(facilitiesInfo.getCardDelivery());
            soapFacility.setPayMethodCriteria(facilitiesInfo.getPaymentCriteria());

        }else{
            soapFacility.setMonthlyInstallment(convertStringToBigDecimal(facilitiesInfo.getMonthlyInstallment()));
            soapFacility.setTenure(convertStringToBigDecimal(facilitiesInfo.getTenure()));
            soapFacility.setFirstPaymentDueDate(facilitiesInfo.getFirstPaymentDueDate());
            soapFacility.setPaymentDueDate(facilitiesInfo.getFirstPaymentDueDate().substring(0,2));
            soapFacility.setDisburstBankName(facilitiesInfo.getDisburstBankName());
            soapFacility.setDisburstAccountName(facilitiesInfo.getDisburstAccountName());
            soapFacility.setDisburstAccountNo(facilitiesInfo.getDisburstAccountNo());
        }


        return soapFacility;
    }


    /**
     * method to create BigDecimal froma string
     *
     * @param data String
     *
     * @return BigDecimal
     */
    public BigDecimal convertStringToBigDecimal(String data){

        if(StringUtils.isNotBlank(data))
            return new BigDecimal(data);

        return null;
    }
}

