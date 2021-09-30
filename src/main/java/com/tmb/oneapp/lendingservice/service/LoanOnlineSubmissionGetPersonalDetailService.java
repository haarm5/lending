package com.tmb.oneapp.lendingservice.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.tmb.common.exception.model.TMBCommonException;
import com.tmb.common.logger.TMBLogger;
import com.tmb.common.model.CustGeneralProfileResponse;
import com.tmb.common.model.TmbOneServiceResponse;
import com.tmb.common.model.address.Province;
import com.tmb.common.model.legacy.rsl.common.ob.dropdown.CommonCodeEntry;
import com.tmb.common.model.legacy.rsl.common.ob.individual.Individual;
import com.tmb.common.model.legacy.rsl.ws.dropdown.response.ResponseDropdown;
import com.tmb.common.model.legacy.rsl.ws.individual.response.ResponseIndividual;
import com.tmb.oneapp.lendingservice.client.CommonServiceFeignClient;
import com.tmb.oneapp.lendingservice.client.CustomerServiceClient;
import com.tmb.oneapp.lendingservice.client.LoanSubmissionGetCustomerInfoClient;
import com.tmb.oneapp.lendingservice.client.LoanSubmissionGetDropdownListClient;
import com.tmb.oneapp.lendingservice.constant.AddressTypeCode;
import com.tmb.oneapp.lendingservice.constant.ResponseCode;
import com.tmb.oneapp.lendingservice.constant.RslResponseCode;
import com.tmb.oneapp.lendingservice.model.loanonline.CommonProvinceRequest;
import com.tmb.oneapp.lendingservice.model.personal.Address;
import com.tmb.oneapp.lendingservice.model.personal.DropDown;
import com.tmb.oneapp.lendingservice.model.personal.PersonalDetailResponse;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import javax.xml.rpc.ServiceException;
import java.rmi.RemoteException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class LoanOnlineSubmissionGetPersonalDetailService {
    private static final TMBLogger<LoanOnlineSubmissionGetPersonalDetailService> logger = new TMBLogger<>(LoanOnlineSubmissionGetPersonalDetailService.class);
    private final LoanSubmissionGetCustomerInfoClient customerInfoClient;
    private final CustomerServiceClient customerServiceClient;
    private final LoanSubmissionGetDropdownListClient dropdownListClient;
    private final CommonServiceFeignClient commonServiceFeignClient;
    static final String DROPDOWN_RESIDENT_TYPE = "RESIDENT_TYP";
    static final String DROPDOWN_SALUTATION_TYPE = "SALUTATION";
    static final String PATTERN_DATE = "yyyy-MM-dd";

    public PersonalDetailResponse getPersonalDetail(String crmId, Long caId) throws ServiceException, TMBCommonException, RemoteException, JsonProcessingException, ParseException {
        // rsl
        Individual individual = getCustomer(caId);
        // ec
        CustGeneralProfileResponse custGeneralProfileResponse = getCustomerEC(crmId);

        PersonalDetailResponse response = new PersonalDetailResponse();
        response.setExpiryDate(Objects.isNull(individual.getExpiryDate()) ? convertStringToCalender(custGeneralProfileResponse.getIdExpireDate()) : individual.getExpiryDate());
        response.setBirthDate(Objects.isNull(individual.getBirthDate()) ? convertStringToCalender(custGeneralProfileResponse.getIdBirthDate()) : individual.getBirthDate());
        response.setEmail(prepareData(individual.getEmail(), custGeneralProfileResponse.getEmailAddress()).toString());
        response.setNationality(prepareData(individual.getNationality(), custGeneralProfileResponse.getNationality()).toString());
        response.setThaiName(prepareData(individual.getThaiName(), custGeneralProfileResponse.getThaFname()).toString());
        response.setThaiSurname(prepareData(individual.getThaiSurName(), custGeneralProfileResponse.getThaLname()).toString());
        response.setEngName(prepareData(individual.getNameLine2(), custGeneralProfileResponse.getEngFname()).toString());
        response.setEngSurname(prepareData(individual.getNameLine1(), custGeneralProfileResponse.getEngLname()).toString());
        response.setMobileNo(prepareData(individual.getMobileNo(), custGeneralProfileResponse.getPhoneNoFull()).toString());
        response.setCitizenId(prepareData(individual.getIdNo1(), custGeneralProfileResponse.getCitizenId()).toString());
        response.setIdIssueCtry1(prepareData(individual.getIdIssueCtry1(), custGeneralProfileResponse.getNationality()).toString());
        response.setPrefix(prepareData(individual.getThaiSalutationCode(), custGeneralProfileResponse.getThaTname()).toString());
        response.setResidentStatus(" ");
        if (Objects.nonNull(individual.getResidentFlag()) && !individual.getResidentFlag().isEmpty()) {
            response.setResidentStatus(individual.getResidentFlag());
        }
        response.setAddress(mapAddress(individual, custGeneralProfileResponse));
        response.setResidentFlag(getResidents());
        response.setThaiSalutationCode(getThaiSalutationCodes());

        return response;
    }

    private Address mapAddress(Individual individual, CustGeneralProfileResponse custGeneralProfileResponse) {
        Address address = new Address();
        Optional<com.tmb.common.model.legacy.rsl.common.ob.address.Address> responseAddress = Arrays.stream(individual.getAddresses()).filter(addr -> AddressTypeCode.RESIDENT.getCode().equals(addr.getAddrTypCode())).findAny();
        if (responseAddress.isPresent()) {
            address.setAmphur(prepareData(responseAddress.get().getAmphur(), custGeneralProfileResponse.getCurrentAddrdistrictNameTh()).toString());
            address.setCountry(prepareData(responseAddress.get().getCountry(), custGeneralProfileResponse.getNationality()).toString());
            address.setFloor(prepareData(responseAddress.get().getFloor(), custGeneralProfileResponse.getCurrentAddrFloorNo()).toString());
            address.setMoo(prepareData(responseAddress.get().getMoo(), custGeneralProfileResponse.getCurrentAddrMoo()).toString());
            address.setNo(prepareData(responseAddress.get().getAddress(), custGeneralProfileResponse.getCurrentAddrHouseNo()).toString());
            address.setRoad(prepareData(responseAddress.get().getRoad(), custGeneralProfileResponse.getCurrentAddrStreet()).toString());
            address.setPostalCode(prepareData(responseAddress.get().getPostalCode(), custGeneralProfileResponse.getCurrentAddrZipcode()).toString());
            address.setStreetName(prepareData(responseAddress.get().getStreetName(), custGeneralProfileResponse.getCurrentAddrStreet()).toString());
            address.setTumbol(prepareData(responseAddress.get().getTumbol(), custGeneralProfileResponse.getCurrentAddrSubDistrictNameTh()).toString());
            address.setAddrTypCode(responseAddress.get().getAddrTypCode());

            String pasCode;
            if (responseAddress.get().getPostalCode().isEmpty() || responseAddress.get().getPostalCode() == null) {
                pasCode = custGeneralProfileResponse.getCurrentAddrZipcode();
            } else {
                pasCode = responseAddress.get().getPostalCode();
            }
            address.setProvince(getProvinceName(pasCode));

            String buildingName = prepareData(responseAddress.get().getBuildingName(), custGeneralProfileResponse.getCurrentAddrVillageOrbuilding()).toString();
            if (!buildingName.isEmpty() || !buildingName.isBlank()) {
                String[] roomNo = buildingName.split(" ");
                address.setRoomNo(roomNo[0]);
                if (roomNo.length > 1) {
                    address.setBuildingName(roomNo[1]);
                }
            }
        }
        return address;
    }


    public Individual getCustomer(Long caID) throws ServiceException, RemoteException, TMBCommonException, JsonProcessingException {
        try {
            ResponseIndividual response = customerInfoClient.searchCustomerInfoByCaID(caID);
            if (!response.getHeader().getResponseCode().equals(RslResponseCode.SUCCESS.getCode())
                    || Objects.isNull(response.getBody().getIndividuals()[0])) {
                throw new TMBCommonException(response.getHeader().getResponseCode(),
                        ResponseCode.FAILED.getMessage(), ResponseCode.FAILED.getService(), HttpStatus.INTERNAL_SERVER_ERROR, null);
            }
            return response.getBody().getIndividuals()[0];
        } catch (Exception e) {
            logger.error("get customer soap error", e);
            throw e;
        }
    }

    public CustGeneralProfileResponse getCustomerEC(String crmid) throws TMBCommonException {
        try {
            TmbOneServiceResponse<CustGeneralProfileResponse> response = customerServiceClient.getCustomers(crmid).getBody();
            if (Objects.nonNull(response)) {
                return response.getData();
            }
            throw new TMBCommonException(ResponseCode.FAILED.getCode(),
                    ResponseCode.FAILED.getMessage(),
                    ResponseCode.FAILED.getService(), HttpStatus.NOT_FOUND, null);
        } catch (Exception e) {
            logger.error("get CustomerEC  error", e);
            throw e;
        }
    }

    private CommonCodeEntry[] getDropdownList(String categoryCode) throws ServiceException, TMBCommonException, JsonProcessingException {
        ResponseDropdown getDropdownListResp = dropdownListClient.getDropDownListByCode(categoryCode);
        return getDropdownListResp.getBody().getCommonCodeEntries();
    }

    public List<DropDown> getResidents() throws ServiceException, TMBCommonException, JsonProcessingException {
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

    public List<DropDown> getThaiSalutationCodes() throws ServiceException, TMBCommonException, JsonProcessingException {
        CommonCodeEntry[] entries = getDropdownList(DROPDOWN_SALUTATION_TYPE);
        List<CommonCodeEntry> sortedList = Arrays.stream(entries)
                .sorted((entryId, entryCode) -> {
                    if (entryCode.getEntryCode().startsWith("G")) {
                        return entryCode.getEntryCode().compareTo("G");
                    }
                    return -1;
                })
                .collect(Collectors.toList());

        List<DropDown> thaiSalutationCodes = new ArrayList<>();
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

    public Calendar convertStringToCalender(String dateStr) throws ParseException {
        Calendar calendar = Calendar.getInstance();
        if (Objects.nonNull(dateStr) && !dateStr.isEmpty()) {
            Date expireDate = new SimpleDateFormat(PATTERN_DATE, Locale.ENGLISH).parse(dateStr);
            calendar.setTime(expireDate);
        }
        return calendar;
    }

    public static Object prepareData(Object individual, Object custGeneralProfileResponse) {
        if (Objects.nonNull(individual) && !individual.equals("")) {
            return individual;
        }
        if (custGeneralProfileResponse != null) {
            return custGeneralProfileResponse;
        }
        return "";
    }

    public boolean personalInfoSaved(Individual individual) {
        logger.info("PersonalInfoSavedFlag is : {}", individual.getPersonalInfoSavedFlag());
        return individual.getPersonalInfoSavedFlag().equals("Y");
    }

    private String getProvinceName(String postCode) {
        var req = new CommonProvinceRequest();
        req.setField("postcode");
        req.setSearch(postCode);
        ResponseEntity<TmbOneServiceResponse<List<Province>>> response = commonServiceFeignClient.getProvince(req);
        if (!response.getBody().getStatus().getCode().equals("0000")) {
            return null;
        }
        return response.getBody().getData().get(0).getProvinceNameTh();
    }
}
