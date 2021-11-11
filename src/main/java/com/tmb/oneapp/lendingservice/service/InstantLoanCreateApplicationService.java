package com.tmb.oneapp.lendingservice.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.tmb.common.logger.TMBLogger;
import com.tmb.common.model.legacy.rsl.common.ob.address.Address;
import com.tmb.common.model.legacy.rsl.common.ob.application.InstantApplication;
import com.tmb.common.model.legacy.rsl.common.ob.creditcard.InstantCreditCard;
import com.tmb.common.model.legacy.rsl.common.ob.facility.InstantFacility;
import com.tmb.common.model.legacy.rsl.common.ob.individual.InstantIndividual;
import com.tmb.common.model.legacy.rsl.ws.instant.application.create.request.Body;
import com.tmb.common.model.legacy.rsl.ws.instant.application.create.request.Header;
import com.tmb.common.model.legacy.rsl.ws.instant.application.create.request.RequestInstantLoanCreateApplication;
import com.tmb.common.model.legacy.rsl.ws.instant.application.create.response.ResponseInstantLoanCreateApplication;
import com.tmb.common.model.loan.AddressInfo;
import com.tmb.common.model.loan.CreditCardLoanInfo;
import com.tmb.common.model.loan.CustomerInfo;
import com.tmb.common.model.loan.FlashCardOrC2GLoanInfo;
import com.tmb.common.model.loan.InstantLoanCreationRequest;
import com.tmb.oneapp.lendingservice.client.InstantLoanCreateApplicationClient;
import com.tmb.oneapp.lendingservice.client.SFTPClientImp;
import com.tmb.oneapp.lendingservice.client.SFTPEnotiClientImp;
import com.tmb.oneapp.lendingservice.constant.LendingServiceConstant;
import com.tmb.oneapp.lendingservice.model.SFTPStoreFileInfo;
import com.tmb.oneapp.lendingservice.model.ServiceError;
import com.tmb.oneapp.lendingservice.model.ServiceResponse;
import com.tmb.oneapp.lendingservice.model.ServiceResponseImp;
import com.tmb.oneapp.lendingservice.model.instantloancreation.*;
import com.tmb.oneapp.lendingservice.util.CommonServiceUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.xml.rpc.ServiceException;
import java.io.IOException;
import java.math.BigDecimal;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.rmi.RemoteException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.Collectors;

@Service
public class InstantLoanCreateApplicationService {
	private static final TMBLogger<InstantLoanCreateApplicationService> logger = new TMBLogger<>(
			InstantLoanCreateApplicationService.class);
	private final ObjectMapper mapper;
	private final InstantLoanCreateApplicationClient soapClient;
	private static final String BRANCH_CODE = "026";
	private static final String SALE_CHANNEL = "05";
	private final ImageGeneratorService imageGeneratorService;
	private String getMoreFlag = "";
	private final SFTPClientImp sftpClient;
	private final SFTPEnotiClientImp sftpEnotiClient;
	private static final String SEPARATOR = "/";

	@Value("${sftp.locations.consent-images}")
	private String sftpLocations;
	@Value("${sftp.e-noti.locations.consent-images}")
	private String sftpEnotiLocations;

	private ExecutorService executor = Executors.newSingleThreadExecutor();

	public InstantLoanCreateApplicationService(ObjectMapper mapper, InstantLoanCreateApplicationClient soapClient,
			ImageGeneratorService imageGeneratorService, SFTPClientImp sftpClient, SFTPEnotiClientImp sftpEnotiClient) {
		this.mapper = mapper;
		this.soapClient = soapClient;
		this.imageGeneratorService = imageGeneratorService;
		this.sftpClient = sftpClient;
		this.sftpEnotiClient = sftpEnotiClient;
	}

	public void setSftpLocations(String sftpLocations) {
		this.sftpLocations = sftpLocations;
	}

	public void setSftpEnotiLocations(String sftpEnotiLocations) {
		this.sftpEnotiLocations = sftpEnotiLocations;
	}

	/**
	 * method to call InstantLoanCreateApplication service of legacy system for
	 * Credit cards, Flash cards, C2G
	 *
	 * @param request InstantLoanCreationRequest
	 * @return InstantLoanCreationResponse
	 */
	public ServiceResponseImp createInstantLoanApplication(String crmId, InstantLoanCreationRequest request) {

		RequestInstantLoanCreateApplication soapRequest = new RequestInstantLoanCreateApplication();
		ServiceResponseImp response = new ServiceResponseImp();
		try {

			logger.info(" Request from Client to InstantLoanCreateApplication is {} : "
					+ mapper.writeValueAsString(request));
			getMoreFlag = request.getGetMore();
			String appType = request.getAppType();
			// Address
			List<AddressInfo> addressInfoList = request.getAddresses();
			List<Address> soapAddressList = addressInfoList.stream().map(this::addressToSoapRequestAddress)
					.collect(Collectors.toList());
			InstantIndividual soapInstantIndividual = getInstantIndividualObject(request, request.getNcbConsentFlag());
			CustomerInfo customerInfo = request.getCustomerInfo();
			String customerFullName = soapInstantIndividual.getThaiName() + " "
					+ soapInstantIndividual.getThaiSurName();
			LOCRequest locRequest = new LOCRequest();
			locRequest.setNCBMobileNo(soapInstantIndividual.getMobileNo());
			locRequest.setNCBDateofbirth(customerInfo.getBirthDate());
			locRequest.setNcbid(customerInfo.getIdNo1());
			locRequest.setNCBCustName(customerFullName);
			locRequest.setCrmId(crmId);

			// Credit card Data
			if (request.getLoanType().equalsIgnoreCase("CC")) {
				logger.info("Calling Credit Cards");
				List<CreditCardLoanInfo> creditCardLoanInfo = request.getCreditCards();
				List<InstantCreditCard> soapCreditCardList = creditCardLoanInfo.stream()
						.map(this::creditCardInfoToSoapRequestCC).collect(Collectors.toList());
				soapInstantIndividual.setCreditCards(soapCreditCardList.toArray(new InstantCreditCard[0]));
				logger.info("soapCreditCardList size is  {} : ", String.valueOf(soapCreditCardList.size()));
			}
			soapInstantIndividual.setAddresses(soapAddressList.toArray(new Address[0]));
			soapInstantIndividual.setHostCifNo(crmId.substring(16));
			logger.info("soapAddressList size is  {} : ", String.valueOf(soapAddressList.size()));

			InstantApplication soapInstantApplication = new InstantApplication();
			// Flash Card or C2G
			if (!request.getLoanType().equalsIgnoreCase("CC")) {
				logger.info("Calling Flash Card or C2G");
				List<FlashCardOrC2GLoanInfo> facilitiesInfo = request.getFlashCardOrC2G();
				List<InstantFacility> soapInstantFacilityList = facilitiesInfo.stream()
						.map(facility -> facilitiesInfoToSoapRequestInstantFacility(facility, request.getLoanType()))
						.collect(Collectors.toList());
				soapInstantApplication.setFacilities(
						soapInstantFacilityList.toArray(new InstantFacility[soapInstantFacilityList.size()]));
			}

			soapInstantApplication.setIndividuals(new InstantIndividual[] { soapInstantIndividual });
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
			ResponseInstantLoanCreateApplication soapResponse = soapClient
					.callLoanSubmissionInstantLoanCreateApplication(soapRequest);

			response = (ServiceResponseImp) constructCreateLoanApplicationResponse(appType, soapResponse);
			if (response.getError() != null) {
				return response;
			}
			InstantLoanCreationResponse response2 = (InstantLoanCreationResponse) response.getData();
			locRequest.setNCBReferenceID(response2.getMemberRef());
			locRequest.setNCBDateTime(response2.getCreateDate());
			locRequest.setProductName(response2.getProductName());
			locRequest.setAppRefNo(response2.getAppRefNo());
			locRequest.setCreateDate(response2.getCreateDate());
			LOCRequest locRequest2 = new LOCRequest(locRequest);
			executor.execute(() -> constructRequestForLOCCompleteImage(locRequest2));
			return response;
		} catch (JsonProcessingException | ParseException | RemoteException | ServiceException e) {
			logger.error("Exception {} : ", e);
			response.setError(new ServiceError());
		}
		return response;
	}

	/**
	 * method to call to construct InstantLoanCreateApplication response for Credit
	 * cards, Flasg crds, C2G
	 *
	 * @param soapResponse ResponseInstantLoanCreateApplication
	 * @return InstantLoanCreationResponse
	 */
	private ServiceResponse constructCreateLoanApplicationResponse(String appType,
			ResponseInstantLoanCreateApplication soapResponse) {
		ServiceResponseImp serviceResponseImp = new ServiceResponseImp();
		InstantLoanCreationResponse response = new InstantLoanCreationResponse();
		com.tmb.common.model.legacy.rsl.ws.instant.application.create.response.Body responseBody = soapResponse
				.getBody();
		String responseCode = soapResponse.getHeader().getResponseCode();
		if (StringUtils.isNotBlank(responseCode) && responseCode.equalsIgnoreCase("MSG_000")) {
			response.setRequestId(soapResponse.getHeader().getRequestID());
			response.setChannel(soapResponse.getHeader().getChannel());
			response.setModule(soapResponse.getHeader().getModule());
			response.setMemberRef(responseBody.getMemberref());
			response.setCreateDate(responseBody.getCreateDate());
			response.setCurrentWorkflow(responseBody.getCurrentWorkflow());
			response.setProductDescEN(responseBody.getProductDescEN());
			response.setCaId(String.valueOf(responseBody.getCaId()));
			response.setProductDescTH(responseBody.getProductDescTH());
			String productName = responseBody.getProductDescTH();
			productName = appType.equalsIgnoreCase("CC") ? productName + " (22)" : productName + " (05)";
			response.setAppRefNo(responseBody.getAppRefNo());
			response.setProductName(productName);
			serviceResponseImp.setData(response);
		} else {
			ServiceError error = new ServiceError();
			error.setErrorMessage(soapResponse.getHeader().getResponseDescriptionEN());
			error.setResponseCode(soapResponse.getHeader().getResponseCode());
			serviceResponseImp.setError(error);
		}
		return serviceResponseImp;
	}

	private void constructRequestForLOCCompleteImage(LOCRequest locRequest2) {

		logger.info("constructRequestForLOCCompleteImage Start");
		locRequest2.setConsentbyCustomer("Access PIN");
		String dob = locRequest2.getNCBDateofbirth();
		dob = dob.substring(0, 10);
		locRequest2.setNCBDateofbirth(getThaiDate(dob));

		String mobno = locRequest2.getNCBMobileNo();
		locRequest2.setNCBMobileNo(CommonServiceUtils.formatPhoneNumber(mobno));

		String customerId = locRequest2.getNcbid();
		locRequest2.setNcbid(CommonServiceUtils.formatCustomerId(customerId));

		locRequest2.setNCBDateTime(getDateAndTimeForLOC(locRequest2.getNCBDateTime()));

		try {
			String jpgFile = imageGeneratorService.generateLOCImage(locRequest2);
			String directoryPath = locRequest2.getCrmId() + SEPARATOR + locRequest2.getAppRefNo();
			String[] locationTokens = sftpLocations.split(",");
			List<SFTPStoreFileInfo> sftpStoreFileInfoList = setSFTPStoreFileInfo(locationTokens, jpgFile, directoryPath);
			String[] locationEnoti = sftpEnotiLocations.split(",");
			List<SFTPStoreFileInfo> sftpStoreEnotiFileInfoList = setSFTPStoreFileInfo(locationEnoti, jpgFile, directoryPath);
			sftpClient.storeFile(sftpStoreFileInfoList);
			sftpEnotiClient.storeFile(sftpStoreEnotiFileInfoList);
			Files.delete(Paths.get(jpgFile));
		} catch (IOException e) {
			logger.error("constructRequestForLOCCompleteImage got error:{}", e);
		}

		logger.info("constructRequestForLOCCompleteImage END");

	}

	private List<SFTPStoreFileInfo> setSFTPStoreFileInfo(String[] locations, String jpgFile, String directoryPath) {
		List<SFTPStoreFileInfo> sftpStoreFileInfoList = new ArrayList<>();

		SFTPStoreFileInfo sftpStoreFileInfo;
		for (int i = 0; i < locations.length; i++) {
			sftpStoreFileInfo = new SFTPStoreFileInfo();
			sftpStoreFileInfo.setSrcFile(jpgFile);
			sftpStoreFileInfo.setRootPath(locations[i]);
			if (i == 0)
				sftpStoreFileInfo.setDstDir(directoryPath);
			sftpStoreFileInfoList.add(sftpStoreFileInfo);
		}
		return sftpStoreFileInfoList;
	}

	/**
	 * method to construct InstantIndividual Object request to legacy system
	 *
	 * @param request InstantLoanCreationRequest
	 * @return InstantIndividual
	 */
	private InstantIndividual getInstantIndividualObject(InstantLoanCreationRequest request, String ncbConsent)
			throws ParseException {
		InstantIndividual individual = new InstantIndividual();
		CustomerInfo customerInfo = request.getCustomerInfo();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSS", Locale.ENGLISH);
		if (StringUtils.isNotBlank(customerInfo.getBirthDate())) {
			Calendar calBirthDate = Calendar.getInstance();
			String birthDate = customerInfo.getBirthDate() + "T00:00:00.000Z";
			calBirthDate.setTime(sdf.parse(birthDate));
			individual.setBirthDate(calBirthDate);
		}
		if (StringUtils.isNotBlank(customerInfo.getIssuedDate())) {
			String issueDate = customerInfo.getIssuedDate() + "T00:00:00.000Z";
			Calendar calIssueDate = Calendar.getInstance();
			calIssueDate.setTime(sdf.parse(issueDate));
			individual.setIssuedDate(calIssueDate);
		}
		individual.setBusinessSubType(customerInfo.getBusinessSubType());
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
		individual.setIdNo1(customerInfo.getIdNo1());
		individual.setIdType1(customerInfo.getIdType1());

		String employmentStatus = customerInfo.getEmploymentStatus();
		String incomeDeclared = "";
		String incomeBasicSalary = "";
		if (employmentStatus.equalsIgnoreCase("02")) {
			incomeDeclared = getMoreFlag.equalsIgnoreCase("Y") ? customerInfo.getMaxIncomeRange()
					: customerInfo.getIncomeDeclared();
		}

		if (employmentStatus.equalsIgnoreCase("01")) {
			incomeBasicSalary = getMoreFlag.equalsIgnoreCase("Y") ? customerInfo.getMaxIncomeRange()
					: customerInfo.getIncomeBasicSalary();
		}

		individual.setIncomeBasicSalary(convertStringToBigDecimal(incomeBasicSalary));
		individual.setIncomeDeclared(convertStringToBigDecimal(incomeDeclared));
		individual.setIncomeType(customerInfo.getIncomeType());
		individual.setMobileNo(customerInfo.getMobileNo());
		individual.setNameLine1(customerInfo.getNameLine1());
		individual.setNameLine2(customerInfo.getNameLine2());
		individual.setNationality(customerInfo.getNationality());
		individual.setNcbConsentFlag(ncbConsent);
		individual.setProfessionalCode(customerInfo.getProfessionalCode());
		individual.setRmOccupation(customerInfo.getRmOccupation());
		individual.setSourceFromCountry(customerInfo.getSourceFromCountry());
		individual.setThaiName(customerInfo.getThaiName());
		individual.setThaiSurName(customerInfo.getThaiSurName());
		return individual;
	}

	/**
	 * method to construct address Object request to legacy system
	 *
	 * @param address AddressInfo
	 * @return Address
	 */
	private Address addressToSoapRequestAddress(AddressInfo address) {

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

		return soapAddressObj;
	}

	/**
	 * method to construct creditCard Info Object request to legacy system
	 *
	 * @param creditCardLoanInfo CreditCardLoanInfo
	 * @return InstantCreditCard
	 */
	private InstantCreditCard creditCardInfoToSoapRequestCC(CreditCardLoanInfo creditCardLoanInfo) {

		InstantCreditCard soapCreditCardLoanData = new InstantCreditCard();
		soapCreditCardLoanData.setCardInd("P");
		soapCreditCardLoanData.setProductType(creditCardLoanInfo.getProductType());
		soapCreditCardLoanData.setPaymentCriteria(creditCardLoanInfo.getPaymentCriteria());
		soapCreditCardLoanData.setDebitAccountName(creditCardLoanInfo.getDebitAccountName());
		soapCreditCardLoanData.setDebitAccountNo(creditCardLoanInfo.getDebitAccountNo());

		String cardBrand = creditCardLoanInfo.getProductType().equalsIgnoreCase("MS") ? "1" : "0";
		soapCreditCardLoanData.setCardBrand(cardBrand);
		soapCreditCardLoanData.setCampaignCode(creditCardLoanInfo.getCampaignCode());
		soapCreditCardLoanData
				.setRequestCreditLimit(convertStringToBigDecimal(creditCardLoanInfo.getRequestCreditLimit()));
		soapCreditCardLoanData.setPaymentMethod(creditCardLoanInfo.getPaymentMethod());
		soapCreditCardLoanData.setMailPreference(creditCardLoanInfo.getMailPreference());
		soapCreditCardLoanData.setCardDeliveryAddress(creditCardLoanInfo.getCardDelivery());

		return soapCreditCardLoanData;
	}

	/**
	 * method to construct FlashCardOrC2GLoan Info Object request to legacy system
	 *
	 * @param facilitiesInfo FlashCardOrC2GLoanInfo
	 * @return InstantFacility
	 */
	private InstantFacility facilitiesInfoToSoapRequestInstantFacility(FlashCardOrC2GLoanInfo facilitiesInfo,
			String loanType) {
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
		soapFacility.setPayMethodCriteria(facilitiesInfo.getPaymentCriteria());

		if (loanType.equalsIgnoreCase("F")) {
			soapFacility.setCardDelivery(facilitiesInfo.getCardDelivery());
			soapFacility.setPayMethodCriteria(facilitiesInfo.getPaymentCriteria());

		} else {
			soapFacility.setMonthlyInstallment(convertStringToBigDecimal(facilitiesInfo.getMonthlyInstallment()));
			soapFacility.setTenure(convertStringToBigDecimal(facilitiesInfo.getTenure()));
			soapFacility.setFirstPaymentDueDate(facilitiesInfo.getFirstPaymentDueDate());
			String dueDate = StringUtils.isNotBlank(facilitiesInfo.getFirstPaymentDueDate())
					? facilitiesInfo.getFirstPaymentDueDate().substring(0, 2)
					: "";
			soapFacility.setPaymentDueDate(dueDate);
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
	 * @return BigDecimal
	 */
	public BigDecimal convertStringToBigDecimal(String data) {

		if (StringUtils.isNotBlank(data) && StringUtils.isNumeric(data))
			return new BigDecimal(data);

		return null;
	}

	private String getDateAndTimeForLOC(String dateAndTime) {

		if (StringUtils.isBlank(dateAndTime))
			return "";

		logger.info("dateAndTime is  {} :", dateAndTime);
		String[] dateAndTimeArry = dateAndTime.split("T");
		String dateEng = dateAndTimeArry[0];
		String curTime = dateAndTimeArry[1];
		return getThaiDate(dateEng) + LendingServiceConstant.SPACE + curTime.replace(".000Z", "");

	}

	private String getThaiDate(String dateEng) {
		if (StringUtils.isBlank(dateEng))
			return "";
		String[] dateArray = dateEng.split("-");
		String thaiYear = CommonServiceUtils.getThaiYear(dateArray[0]);
		String thaiMonth = CommonServiceUtils.getThaiMonth(dateArray[1]);
		StringBuilder thaiDate = new StringBuilder();
		thaiDate.append(dateArray[2]);
		thaiDate.append(LendingServiceConstant.SPACE);
		thaiDate.append(thaiMonth);
		thaiDate.append(LendingServiceConstant.SPACE);
		thaiDate.append(thaiYear);
		return thaiDate.toString();
	}

	/**
	 * Validate require field minimum
	 * 
	 * @param request
	 * @return
	 */
	public boolean isPassMandatoryInfomationRequest(InstantLoanCreationRequest request) {
		boolean isValid = false;
		String loanType = request.getLoanType();
		String appType = request.getAppType();
		if (loanType.equals("CC") && appType.equals("CC")) {
			isValid = request.getCreditCards() != null && !request.getCreditCards().isEmpty();
		}

		if ((loanType.equals("F") || loanType.equals("C2G")) && appType.equals("PL")) {
			isValid = request.getFlashCardOrC2G() != null && !request.getFlashCardOrC2G().isEmpty();
		}
		return isValid;
	}

}
