package com.tmb.oneapp.lendingservice.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.tmb.common.exception.model.TMBCommonException;
import com.tmb.common.logger.TMBLogger;
import com.tmb.common.model.legacy.rsl.common.ob.creditcard.CreditCard;
import com.tmb.common.model.legacy.rsl.common.ob.facility.Facility;
import com.tmb.common.model.legacy.rsl.common.ob.individual.Individual;
import com.tmb.common.model.legacy.rsl.ws.creditcard.response.ResponseCreditcard;
import com.tmb.common.model.legacy.rsl.ws.facility.response.ResponseFacility;
import com.tmb.common.model.legacy.rsl.ws.individual.response.ResponseIndividual;
import com.tmb.oneapp.lendingservice.client.*;
import com.tmb.oneapp.lendingservice.constant.ResponseCode;
import com.tmb.oneapp.lendingservice.model.loanonline.UpdateWorkingDetailRequest;
import com.tmb.oneapp.lendingservice.model.personal.Address;
import lombok.AllArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import javax.xml.rpc.ServiceException;
import java.rmi.RemoteException;
import java.util.Arrays;
import java.util.Objects;
import java.util.Optional;

@Service
@AllArgsConstructor
public class LoanOnlineSubmissionUpdateWorkingDetailService {
    private static final TMBLogger<LoanOnlineSubmissionUpdateWorkingDetailService> logger = new TMBLogger<>(LoanOnlineSubmissionUpdateWorkingDetailService.class);
    static final String MSG_000 = "MSG_000";

    private final LoanSubmissionGetCustomerInfoClient customerInfoClient;
    private final LoanSubmissionUpdateCustomerClient loanSubmissionUpdateCustomerClient;
    private final LoanSubmissionGetFacilityInfoClient loanSubmissionGetFacilityInfoClient;
    private final LoanSubmissionUpdateFacilityInfoClient loanSubmissionUpdateFacilityInfoClient;
    private final LoanSubmissionGetCreditcardInfoClient loanSubmissionGetCreditcardInfoClient;
    private final LoanSubmissionUpdateCreditCardClient loanSubmissionUpdateCreditCardClient;

    public com.tmb.common.model.legacy.rsl.ws.individual.update.response.ResponseIndividual updateWorkDetail(UpdateWorkingDetailRequest request) throws ServiceException, TMBCommonException, RemoteException, JsonProcessingException {
        String productCode = request.getProductCode();
        boolean isTypeCC = productCode.equals("VM") || productCode.equals("VC")
                || productCode.equals("VG") || productCode.equals("VP")
                || productCode.equals("VT") || productCode.equals("VJ")
                || productCode.equals("VH") || productCode.equals("VI")
                || productCode.equals("VB")
                || productCode.equals("MT") || productCode.equals("MS");

        com.tmb.common.model.legacy.rsl.ws.individual.update.response.ResponseIndividual result = updateIndividual(request, isTypeCC);
        if (!isTypeCC) {
            updateFacility(request.getCaId(), request.getMailingPreference());
        }
        return result;
    }

    public com.tmb.common.model.legacy.rsl.ws.individual.update.response.ResponseIndividual updateIndividual(UpdateWorkingDetailRequest request, boolean isTypeCC) throws ServiceException, TMBCommonException, RemoteException, JsonProcessingException {
        try {
            Individual individual = getCustomer(request.getCaId());
            prepareIndividual(individual, request);
            com.tmb.common.model.legacy.rsl.ws.individual.update.response.ResponseIndividual res = loanSubmissionUpdateCustomerClient.updateCustomerInfo(individual);
            if (res.getHeader().getResponseCode().equals(MSG_000)) {
                if (isTypeCC) {
                    updateCreditCard(prepareCreditCard(request.getCaId(), request.getMailingPreference()));
                }
                return res;
            }
            throw new TMBCommonException(res.getHeader().getResponseCode(),
                    res.getHeader().getResponseDescriptionEN(),
                    ResponseCode.FAILED.getService(), HttpStatus.NOT_FOUND, null);
        } catch (Exception e) {
            logger.error("working detail service => update customer soap error", e);
            throw e;
        }
    }


    private Individual prepareIndividual(Individual individual, UpdateWorkingDetailRequest request) {

        individual.setEmploymentStatus(request.getEmploymentStatus());
        individual.setEmploymentOccupation(request.getEmploymentOccupation());
        individual.setRmOccupation(request.getRmOccupation());
        individual.setProfessionalCode(request.getProfessionalCode());
        individual.setEmploymentBizNature(request.getEmploymentBizNature());
        individual.setBusinessType(request.getBusinessType());
        individual.setBusinessSubType(request.getBusinessSubType());
        individual.setEmploymentName(request.getEmploymentName());
        individual.setEmploymentYear(request.getEmploymentYear());
        individual.setEmploymentMonth(request.getEmploymentMonth());
        individual.setContractEmployedFlag(request.getContractEmployedFlag());
        prepareAddress(individual, request.getAddress());
        individual.setEmploymentTelephoneNo(request.getTel());
        individual.setEmploymentTelephoneExtNo(request.getExTel());
        individual.setEmploymentTelephoneDirectNo(request.getTel());
        individual.setIncomeBasicSalary(request.getIncomeBasicSalary());
        individual.setInTotalIncome(request.getInTotalIncome());
        individual.setIncomeBankName(request.getIncomeBank());
        individual.setIncomeBankAccoutNumber(request.getIncomeBankAccountNumber());
        individual.setIncomeSharedHolderPercent(request.getIncomeSharedHolderPercent());
        individual.setIncometotalLastMthCreditAcct1(request.getIncomeTotalLastMthCreditAcct1());
        individual.setIncomeType(request.getIncomeType());
        individual.setSourceFromCountry(request.getSourceFromCountry());
        individual.setMailingPreference(request.getMailingPreference());
        individual.setEmailStatementFlag(request.getEmailStatementFlag());
        individual.setEmploymentInfoSavedFlag("Y");
        individual.setIncomeInfoSavedFlag("Y");
        return individual;
    }

    private CreditCard prepareCreditCard(Long caId, String preference) throws ServiceException, TMBCommonException, JsonProcessingException {
        CreditCard[] creditCards = getCreditCard(caId);
        if (Objects.nonNull(Objects.requireNonNull(creditCards)[0])) {
            creditCards[0].setMailPreference(preference);
            creditCards[0].setCardDeliveryAddress(preference);
            creditCards[0].setCreditcardSavedFlag("Y");
            return creditCards[0];
        }
        return null;
    }

    private String checkEmptyString(String string) {
        if (Objects.nonNull(string) && !string.isEmpty()) {
            return string;
        }
        return "";
    }

    private String mapBuildingName(Address address) {
        if (!checkEmptyString(address.getRoomNo()).isEmpty()) {
            return checkEmptyString(address.getBuildingName()) + " " + "ห้อง" + address.getRoomNo();
        }
        return checkEmptyString(address.getBuildingName());
    }

    private Individual prepareAddress(Individual individual, Address address) {
        com.tmb.common.model.legacy.rsl.common.ob.address.Address[] individualAddresses = individual.getAddresses();
        if (Objects.nonNull(individualAddresses)) {
            Optional<com.tmb.common.model.legacy.rsl.common.ob.address.Address> oldAddress = Arrays.stream(individualAddresses).filter(x -> x.getAddrTypCode().equals("O")).findFirst();
            var newAddress = new com.tmb.common.model.legacy.rsl.common.ob.address.Address();

            newAddress.setCifId(individual.getCifId());
            newAddress.setAddrTypCode("O");
            newAddress.setAddress(checkoutNullAddressMapping(address.getNo(), 25));
            newAddress.setBuildingName(checkoutNullAddressMapping(mapBuildingName(address), 100));
            newAddress.setFloor(checkoutNullAddressMapping(address.getFloor(), 3));
            newAddress.setStreetName(checkoutNullAddressMapping(address.getStreetName(), 100));
            newAddress.setRoad(checkoutNullAddressMapping(address.getRoad(), 25));
            newAddress.setMoo(checkoutNullAddressMapping(address.getMoo(), 20));
            newAddress.setTumbol(checkoutNullAddressMapping(address.getTumbol(), 20));
            newAddress.setAmphur(checkoutNullAddressMapping(address.getAmphur(), 30));
            newAddress.setProvince(checkoutNullAddressMapping(address.getProvince(), 100));
            newAddress.setPostalCode(checkoutNullAddressMapping(address.getPostalCode(), 20));
            newAddress.setCountry(checkoutNullAddressMapping(address.getCountry(), 40));

            if (oldAddress.isPresent()) {
                com.tmb.common.model.legacy.rsl.common.ob.address.Address workingAddress = oldAddress.get();
                setAddressToList(individual,workingAddress,newAddress);
            }
        }
        return individual;
    }

    private Individual setAddressToList(Individual individual,com.tmb.common.model.legacy.rsl.common.ob.address.Address workingAddress,
                                        com.tmb.common.model.legacy.rsl.common.ob.address.Address newAddress) {
        newAddress.setId(workingAddress.getId());
        for (int i = 0; i < individual.getAddresses().length; i++) {
            if (individual.getAddresses()[i].getAddrTypCode().equals("O")) {
                individual.getAddresses()[i] = newAddress;
                break;
            }
        }
        return individual;
    }


    private String checkoutNullAddressMapping(String value, int length) {
        if (Objects.nonNull(value)) {
            return StringUtils.left(value, length);
        }
        return "";
    }


    private void updateFacility(Long caId, String mailingPreference) throws ServiceException, TMBCommonException, JsonProcessingException {
        try {
            Facility facility = getFacility(caId);
            if (Objects.isNull(facility)) {
                return;
            }
            facility.setMailingPreference(mailingPreference);
            facility.setCardDelivery(mailingPreference);
            facility.setFacilitySavedFlag("Y");
            com.tmb.common.model.legacy.rsl.ws.facility.update.response.ResponseFacility response = loanSubmissionUpdateFacilityInfoClient.updateFacilityInfo(facility);
            if (!response.getHeader().getResponseCode().equals(MSG_000)) {
                throw new TMBCommonException(response.getHeader().getResponseCode(),
                        response.getHeader().getResponseDescriptionEN(),
                        ResponseCode.FAILED.getService(), HttpStatus.NOT_FOUND, null);
            }
        } catch (Exception e) {
            logger.error("working detail service => update facility soap error", e);
            throw e;
        }
    }


    private Facility getFacility(Long caId) throws ServiceException, TMBCommonException, JsonProcessingException {
        try {
            ResponseFacility response = loanSubmissionGetFacilityInfoClient.searchFacilityInfoByCaID(caId);
            if (response.getHeader().getResponseCode().equals(MSG_000)) {
                return Objects.isNull(response.getBody().getFacilities()) ? null : response.getBody().getFacilities()[0];
            }
            throw new TMBCommonException(response.getHeader().getResponseCode(),
                    response.getHeader().getResponseDescriptionEN(),
                    ResponseCode.FAILED.getService(), HttpStatus.NOT_FOUND, null);
        } catch (Exception e) {
            logger.error("working detail service => get facility soap error", e);
            throw e;
        }
    }


    private CreditCard[] getCreditCard(Long caId) throws ServiceException, TMBCommonException, JsonProcessingException {
        try {
            ResponseCreditcard response = loanSubmissionGetCreditcardInfoClient.searchCreditcardInfoByCaID(caId);
            if (response.getHeader().getResponseCode().equals(MSG_000)) {
                return Objects.isNull(response.getBody().getCreditCards()) ? null : response.getBody().getCreditCards();
            }
            throw new TMBCommonException(response.getHeader().getResponseCode(),
                    response.getHeader().getResponseDescriptionEN(),
                    ResponseCode.FAILED.getService(), HttpStatus.NOT_FOUND, null);
        } catch (Exception e) {
            logger.error("working detail service => get credit card soap error", e);
            throw e;
        }
    }

    private void updateCreditCard(CreditCard creditCard) throws ServiceException, TMBCommonException, JsonProcessingException {
        try {
            com.tmb.common.model.legacy.rsl.ws.creditcard.update.response.ResponseCreditcard response = loanSubmissionUpdateCreditCardClient.updateCreditCard(creditCard);
            if (!response.getHeader().getResponseCode().equals(MSG_000)) {
                throw new TMBCommonException(response.getHeader().getResponseCode(),
                        response.getHeader().getResponseDescriptionEN(),
                        ResponseCode.FAILED.getService(), HttpStatus.NOT_FOUND, null);
            }
        } catch (Exception e) {
            logger.error("working detail service => update credit card soap error", e);
            throw e;
        }
    }


    private Individual getCustomer(Long caId) throws ServiceException, RemoteException, TMBCommonException, JsonProcessingException {
        try {
            ResponseIndividual response = customerInfoClient.searchCustomerInfoByCaID(caId);
            if (response.getHeader().getResponseCode().equals(MSG_000)) {
                if (Objects.isNull(response.getBody().getIndividuals())) {
                    return null;
                }
                return response.getBody().getIndividuals()[0];
            }
            throw new TMBCommonException(response.getHeader().getResponseCode(),
                    response.getHeader().getResponseDescriptionEN(),
                    ResponseCode.FAILED.getService(), HttpStatus.NOT_FOUND, null);
        } catch (Exception e) {
            logger.error("working detail service => get customer soap error", e);
            throw e;
        }
    }
}