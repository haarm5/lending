package com.tmb.oneapp.lendingservice.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.tmb.common.exception.model.TMBCommonException;
import com.tmb.common.logger.TMBLogger;
import com.tmb.common.model.CustGeneralProfileResponse;
import com.tmb.common.model.TmbOneServiceResponse;
import com.tmb.common.model.legacy.rsl.common.ob.dropdown.CommonCodeEntry;
import com.tmb.common.model.legacy.rsl.common.ob.individual.Individual;
import com.tmb.common.model.legacy.rsl.ws.dropdown.response.ResponseDropdown;
import com.tmb.common.model.legacy.rsl.ws.individual.response.ResponseIndividual;
import com.tmb.oneapp.lendingservice.client.CustomerServiceClient;
import com.tmb.oneapp.lendingservice.client.LoanSubmissionGetCustomerInfoClient;
import com.tmb.oneapp.lendingservice.client.LoanSubmissionGetDropdownListClient;
import com.tmb.oneapp.lendingservice.constant.AddressTypeCode;
import com.tmb.oneapp.lendingservice.constant.ResponseCode;
import com.tmb.oneapp.lendingservice.constant.RslResponseCode;
import com.tmb.oneapp.lendingservice.model.personal.Address;
import com.tmb.oneapp.lendingservice.model.personal.DropDown;
import com.tmb.oneapp.lendingservice.model.personal.PersonalDetailResponse;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import javax.xml.rpc.ServiceException;
import java.rmi.RemoteException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class PersonalDetailService {
    private static final TMBLogger<PersonalDetailService> logger = new TMBLogger<>(PersonalDetailService.class);
    private final LoanSubmissionGetCustomerInfoClient customerInfoClient;
    private final CustomerServiceClient customerServiceClient;
    private final LoanSubmissionGetDropdownListClient dropdownListClient;
    static final String DROPDOWN_RESIDENT_TYPE = "RESIDENT_TYP";
    static final String DROPDOWN_SALUTATION_TYPE = "SALUTATION";
    static final String PATTERN_DATE = "yyyy-MM-dd";

    public PersonalDetailResponse getPersonalDetail(String crmId, Long caId) throws ServiceException, TMBCommonException, RemoteException, JsonProcessingException, ParseException {
        PersonalDetailResponse response = new PersonalDetailResponse();
        Address address = new Address();
        // rsl
        Individual individual = getCustomer(caId);
        // ec
        CustGeneralProfileResponse custGeneralProfileResponse = getCustomerEC(crmId);

        response.setEmail(individual.getEmail() == null ? custGeneralProfileResponse.getEmailAddress() : individual.getEmail());
        response.setBirthDate(individual.getBirthDate() == null ? convertStringToCalender(custGeneralProfileResponse.getIdBirthDate()) : individual.getBirthDate());
        response.setEngName(individual.getNameLine2() == null ? custGeneralProfileResponse.getEngFname() : individual.getNameLine2());
        response.setEngSurname(individual.getNameLine1() == null ? custGeneralProfileResponse.getEngLname() : individual.getNameLine1());
        response.setNationality(individual.getNationality() == null ? custGeneralProfileResponse.getNationality() : individual.getNationality());
        response.setExpiryDate(individual.getExpiryDate() == null ? convertStringToCalender(custGeneralProfileResponse.getIdExpireDate()) : individual.getExpiryDate());
        response.setMobileNo(individual.getMobileNo() == null ? custGeneralProfileResponse.getPhoneNoFull() : individual.getMobileNo());
        response.setThaiName(individual.getThaiName() == null ? custGeneralProfileResponse.getThaFname() : individual.getThaiName());
        response.setThaiSurname(individual.getThaiSurName() == null ? custGeneralProfileResponse.getThaLname() : individual.getThaiSurName());
        response.setCitizenId(individual.getIdNo1() == null ? custGeneralProfileResponse.getCitizenId() : individual.getIdNo1());
        response.setPrefix(individual.getThaiSalutationCode());
        Optional<com.tmb.common.model.legacy.rsl.common.ob.address.Address> responseAddress = Arrays.stream(individual.getAddresses()).filter(addr -> AddressTypeCode.RESIDENT.getCode().equals(addr.getAddrTypCode())).findAny();

        if (responseAddress.isPresent()) {
            address.setAmphur(responseAddress.get().getAmphur() == null ? custGeneralProfileResponse.getCurrentAddrdistrictNameTh() : responseAddress.get().getAmphur());
            address.setCountry(responseAddress.get().getCountry() == null ? custGeneralProfileResponse.getNationality() : responseAddress.get().getCountry());
            address.setFloor(responseAddress.get().getFloor() == null ? custGeneralProfileResponse.getCurrentAddrFloorNo() : responseAddress.get().getFloor());
            address.setBuildingName(responseAddress.get().getBuildingName() == null ? custGeneralProfileResponse.getCurrentAddrVillageOrbuilding() : responseAddress.get().getBuildingName());
            address.setMoo(responseAddress.get().getMoo() == null ? custGeneralProfileResponse.getCurrentAddrMoo() : responseAddress.get().getMoo());
            address.setNo(responseAddress.get().getAddress() == null ? custGeneralProfileResponse.getCurrentAddrHouseNo() : responseAddress.get().getAddress());
            address.setProvince(responseAddress.get().getProvince() == null ? custGeneralProfileResponse.getCurrentAddrProvinceNameTh() : responseAddress.get().getProvince());
            address.setRoad(responseAddress.get().getRoad() == null ? custGeneralProfileResponse.getCurrentAddrStreet() : responseAddress.get().getRoad());
            address.setPostalCode(responseAddress.get().getPostalCode() == null ? custGeneralProfileResponse.getCurrentAddrZipcode() : responseAddress.get().getPostalCode());
            address.setStreetName(responseAddress.get().getStreetName() == null ? custGeneralProfileResponse.getCurrentAddrStreet() : responseAddress.get().getStreetName());
            address.setTumbol(responseAddress.get().getTumbol() == null ? custGeneralProfileResponse.getCurrentAddrSubDistrictNameTh() : responseAddress.get().getTumbol());
            address.setAddrTypCode(responseAddress.get().getAddrTypCode());
        }

        response.setAddress(address);
        response.setResidentFlag(getResidents());
        response.setThaiSalutationCode(getThaiSalutationCodes());

        return response;
    }


    public Individual getCustomer(Long caID) throws ServiceException, RemoteException, TMBCommonException, JsonProcessingException {
        try {
            ResponseIndividual response = customerInfoClient.searchCustomerInfoByCaID(caID);
            if (response.getHeader().getResponseCode().equals(RslResponseCode.SUCCESS.getCode())) {
                if (response.getBody().getIndividuals()[0] == null) {
                    throw new TMBCommonException(response.getHeader().getResponseCode(),
                            ResponseCode.FAILED.getMessage(), ResponseCode.FAILED.getService(), HttpStatus.INTERNAL_SERVER_ERROR, null);
                }
                return response.getBody().getIndividuals()[0];
            } else {
                String errorMessage = String.format("[%s] %s", response.getHeader().getResponseCode(), response.getHeader().getResponseDescriptionEN());
                throw new TMBCommonException(response.getHeader().getResponseCode(),
                        errorMessage, ResponseCode.FAILED.getService(), HttpStatus.INTERNAL_SERVER_ERROR, null);
            }
        } catch (Exception e) {
            logger.error("get customer soap error", e);
            throw e;
        }
    }

    public CustGeneralProfileResponse getCustomerEC(String crmid) throws TMBCommonException {
        try {
            TmbOneServiceResponse<CustGeneralProfileResponse> response = customerServiceClient.getCustomers(crmid).getBody();
            if (response != null) {
                return response.getData();
            } else {
                throw new TMBCommonException(ResponseCode.FAILED.getCode(),
                        ResponseCode.FAILED.getMessage(),
                        ResponseCode.FAILED.getService(), HttpStatus.NOT_FOUND, null);
            }
        } catch (Exception e) {
            logger.error("get CustomerEC  error", e);
            throw e;
        }
    }

    private CommonCodeEntry[] getDropdownList(String categoryCode) throws ServiceException, TMBCommonException, JsonProcessingException {
        ResponseDropdown getDropdownListResp = dropdownListClient.getDropDownListByCode(categoryCode);
        return getDropdownListResp.getBody().getCommonCodeEntries();
    }

    private List<DropDown> getResidents() throws ServiceException, TMBCommonException, JsonProcessingException {
        List<DropDown> residents = new ArrayList<>();
        CommonCodeEntry[] entries = getDropdownList(DROPDOWN_RESIDENT_TYPE);
        for (CommonCodeEntry e : entries) {
            DropDown resident = new DropDown();
            resident.setEntryId(e.getEntryID());
            resident.setEntryCode(e.getEntryCode());
            resident.setEntryNameEng(e.getEntryName());
            resident.setEntryNameTh(e.getEntryName2());
            resident.setEntrySource(e.getEntrySource());
            residents.add(resident);
        }
        return residents;
    }

    private List<DropDown> getThaiSalutationCodes() throws ServiceException, TMBCommonException, JsonProcessingException {
        List<DropDown> thaiSalutationCodes = new ArrayList<>();
        CommonCodeEntry[] entries = getDropdownList(DROPDOWN_SALUTATION_TYPE);
        List<CommonCodeEntry> sortedList = Arrays.stream(entries)
                .sorted((entryId, entryCode) -> {
                    if (entryCode.getEntryCode().startsWith("G")) {
                        return entryCode.getEntryCode().compareTo("G");
                    }
                    return -1;
                })
                .collect(Collectors.toList());


        for (CommonCodeEntry e : sortedList) {
            DropDown thaiSalutationCode = new DropDown();
            thaiSalutationCode.setEntryId(e.getEntryID());
            thaiSalutationCode.setEntryCode(e.getEntryCode());
            thaiSalutationCode.setEntryNameEng(e.getEntryName());
            thaiSalutationCode.setEntryNameTh(e.getEntryName2());
            thaiSalutationCode.setEntrySource(e.getEntrySource());
            thaiSalutationCodes.add(thaiSalutationCode);
        }

        return thaiSalutationCodes;
    }

    private Calendar convertStringToCalender(String dateStr) throws ParseException {
        Calendar calendar = Calendar.getInstance();
        if (dateStr != null && !dateStr.equals("")) {
            Date expireDate = new SimpleDateFormat(PATTERN_DATE).parse(dateStr);
            calendar.setTime(expireDate);
        }
        return calendar;
    }

    public boolean personalInfoSaved(Individual individual) {
        logger.info("PersonalInfoSavedFlag is : {}", individual.getPersonalInfoSavedFlag());
        return individual.getPersonalInfoSavedFlag().equals("Y");
    }
}
