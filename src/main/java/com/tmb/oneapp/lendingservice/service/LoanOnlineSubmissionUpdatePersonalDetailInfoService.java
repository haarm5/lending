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
import com.tmb.common.model.legacy.rsl.ws.individual.update.request.Body;
import com.tmb.common.model.legacy.rsl.ws.individual.update.request.RequestIndividual;
import com.tmb.common.model.legacy.rsl.ws.individual.update.response.ResponseIndividual;
import com.tmb.oneapp.lendingservice.client.CommonServiceFeignClient;
import com.tmb.oneapp.lendingservice.client.LoanSubmissionGetDropdownListClient;
import com.tmb.oneapp.lendingservice.client.LoanSubmissionUpdateCustomerClient;
import com.tmb.oneapp.lendingservice.constant.ResponseCode;
import com.tmb.oneapp.lendingservice.model.loanonline.CommonProvinceRequest;
import com.tmb.oneapp.lendingservice.model.personal.Address;
import com.tmb.oneapp.lendingservice.model.personal.DropDown;
import com.tmb.oneapp.lendingservice.model.personal.PersonalDetailResponse;
import com.tmb.oneapp.lendingservice.model.personal.PersonalDetailSaveInfoRequest;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import javax.xml.rpc.ServiceException;
import java.math.BigDecimal;
import java.rmi.RemoteException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

import static com.tmb.oneapp.lendingservice.service.LoanOnlineSubmissionGetPersonalDetailService.PATTERN_DATE;

@Service
@AllArgsConstructor
public class LoanOnlineSubmissionUpdatePersonalDetailInfoService {
    private static final TMBLogger<LoanOnlineSubmissionUpdatePersonalDetailInfoService> logger = new TMBLogger<>(LoanOnlineSubmissionUpdatePersonalDetailInfoService.class);
    private final LoanSubmissionUpdateCustomerClient updateCustomerClient;
    private final LoanSubmissionGetDropdownListClient dropdownListClient;
    private final LoanOnlineSubmissionGetPersonalDetailService loanOnlineSubmissionGetPersonalDetailService;
    private final CommonServiceFeignClient commonServiceFeignClient;
    static final String DROPDOWN_RESIDENT_TYPE = "RESIDENT_TYP";
    static final String DROPDOWN_SALUTATION_TYPE = "SALUTATION";
    static final String IDEN_PRESENT_BANK_03 = "03";
    static final String IDEN_PRESENT_BANK_02 = "02";
    static final String IDEN_PRESENT_BANK_01 = "01";
    static final String EXPIRE_DATE_DEFAULT = "9000-01-01";
    static final String RESIDENCE = "RESIDENCE";
    static final String YES = "Y";
    static final String NO = "N";

    public PersonalDetailResponse updateCustomerInfo(String crmId, PersonalDetailSaveInfoRequest request) throws ServiceException, TMBCommonException, JsonProcessingException, RemoteException, ParseException {

        Body body = new Body();
        body.setIndividual(prepareIndividual(crmId, request));
        RequestIndividual responseIndividual = new RequestIndividual();
        responseIndividual.setBody(body);
        return saveCustomer(request.getCaId(), responseIndividual.getBody().getIndividual(), request);
    }

    private Individual prepareIndividual(String crmId, PersonalDetailSaveInfoRequest request) throws ParseException, ServiceException, TMBCommonException, RemoteException, JsonProcessingException {
        //rsl
        Individual individual = loanOnlineSubmissionGetPersonalDetailService.getCustomer(request.getCaId());
        // ec
        CustGeneralProfileResponse ecResponse = loanOnlineSubmissionGetPersonalDetailService.getCustomerEC(crmId);

        prepareAddressTypeH(individual, request.getAddress());
        prepareAddressTypeR(individual, ecResponse);
        individual.setPersonalInfoSavedFlag(YES);
        individual.setNationality(request.getNationality());
        individual.setMobileNo(request.getMobileNo());
        individual.setThaiName(request.getThaiName());
        individual.setThaiSalutationCode(request.getThaiSalutationCode());
        individual.setThaiSurName(request.getThaiSurname());
        individual.setNameLine1(request.getEngSurname());
        individual.setNameLine2(request.getEngName());
        individual.setEmail(request.getEmail());
        individual.setIdIssueCtry1(request.getIdIssueCtry1());
        individual.setResidentType(request.getResidentFlag());
        individual.setExpiryDate(request.getExpiryDate());
        individual.setBirthDate(request.getBirthDate());
        individual.setAccounts(individual.getAccounts());
        individual.setWorkingAddrCopyFrom(RESIDENCE);
        individual.setIdenPresentToBank(prepareIdenPresentToBank(prepareData(individual.getCustomerType(), ecResponse.getCustomerType()).toString()));
        individual.setLifeTimeFlag(prepareLifeTimeFlag());
        individual.setCompanyType("4");
        individual.setIssuedDate(Objects.isNull(individual.getIssuedDate()) ? loanOnlineSubmissionGetPersonalDetailService.convertStringToCalender(ecResponse.getIdReleasedDate()) : individual.getIssuedDate()); // issuedDate

        List<BigDecimal> age = calculateAge(individual, ecResponse);
        individual.setAge(age.get(0));
        individual.setAgeMonth(age.get(1));

        individual.setSourceFromCountry(prepareData(individual.getSourceFromCountry(), ecResponse.getCountryOfIncome()).toString()); //country_of_income
        individual.setEducationLevel(prepareData(individual.getEducationLevel(), ecResponse.getEducationCode()).toString()); // education_code
        individual.setMaritalStatus(prepareData(individual.getMaritalStatus(), ecResponse.getMaritalStatus()).toString()); // marital_status
        individual.setGender(prepareData(individual.getGender(), ecResponse.getGender()).toString()); // gender
        individual.setCustomerType(prepareData(individual.getCustomerType(), ecResponse.getCustomerType()).toString()); //customer_type
        individual.setCustomerLevel(prepareCustomerLevel(prepareData(individual.getCustomerLevel(), ecResponse.getCustomerLevel()).toString())); // customer_level
        individual.setEmploymentYear("0");
        individual.setEmploymentMonth("0");
        individual.setIncomeType("2");
        individual.setIncomeBankName("011");

        return individual;
    }


    //response position0 = year ,position1 = month
    private List<BigDecimal> calculateAge(Individual individual, CustGeneralProfileResponse ecResponse) throws ParseException {
        Calendar year =  Objects.isNull(individual.getBirthDate()) ? loanOnlineSubmissionGetPersonalDetailService.convertStringToCalender(ecResponse.getIdBirthDate()) : individual.getBirthDate();
        Calendar currentYear = Calendar.getInstance();
        int year1 = year.get(Calendar.YEAR);
        int year2 = currentYear.get(Calendar.YEAR);
        int ageYear = year2 - year1;
        int month1 = year.get(Calendar.MONTH);
        int month2 = currentYear.get(Calendar.MONTH);
        int ageMonth = month2 - month1;

        List<BigDecimal> age = new ArrayList<>();
        age.add(BigDecimal.valueOf(ageYear));
        age.add(BigDecimal.valueOf(ageMonth));
        return age;
    }


    private String prepareIdenPresentToBank(String code) {
        if (Objects.nonNull(code) && !code.isEmpty()) {
            if (Integer.parseInt(code) == 920) {
                return IDEN_PRESENT_BANK_03;
            } else if (Integer.parseInt(code) <= 109 && Integer.parseInt(code) >= 101) {
                return IDEN_PRESENT_BANK_02;
            }
        }
        return IDEN_PRESENT_BANK_01;
    }

    private String prepareLifeTimeFlag() {
        Calendar cal = Calendar.getInstance();
        DateFormat sdf = new SimpleDateFormat(PATTERN_DATE);
        if (sdf.format(cal.getTime()).equals(EXPIRE_DATE_DEFAULT)) {
            return YES;
        }
        return NO;
    }

    private BigDecimal prepareCustomerLevel(String customerLevel) {
        if (customerLevel.length() < 2) {
            return BigDecimal.valueOf(Integer.parseInt(String.valueOf(customerLevel.charAt(0))));
        }
        return BigDecimal.valueOf(Integer.parseInt(String.valueOf(customerLevel.charAt(1))));
    }


    private PersonalDetailResponse saveCustomer(Long caId, Individual individual, PersonalDetailSaveInfoRequest request) throws ServiceException, TMBCommonException, JsonProcessingException, RemoteException {
        try {
            ResponseIndividual response = updateCustomerClient.updateCustomerInfo(individual);

            if (Objects.nonNull(response)) {
                return prepareResponse(caId, request);
            }
            throw new TMBCommonException(ResponseCode.FAILED.getCode(),
                    ResponseCode.FAILED.getDesc(),
                    ResponseCode.FAILED.getService(), HttpStatus.NOT_FOUND, null);

        } catch (Exception e) {
            logger.error("update customer soap error", e);
            throw e;
        }
    }

    private PersonalDetailResponse prepareResponse(Long caId, PersonalDetailSaveInfoRequest request) throws ServiceException, TMBCommonException, RemoteException, JsonProcessingException {
        Individual responseIndividual = loanOnlineSubmissionGetPersonalDetailService.getCustomer(caId);
        PersonalDetailResponse response = new PersonalDetailResponse();
        response.setAddress(prepareResponseAddress(responseIndividual));
        response.setCitizenId(responseIndividual.getIdNo1());
        response.setThaiName(responseIndividual.getThaiName());
        response.setThaiSurname(responseIndividual.getThaiSurName());
        response.setNationality(responseIndividual.getNationality());
        response.setMobileNo(responseIndividual.getMobileNo());
        response.setEmail(responseIndividual.getEmail());
        response.setBirthDate(responseIndividual.getBirthDate());
        response.setEngName(responseIndividual.getNameLine2());
        response.setEngSurname(responseIndividual.getNameLine1());
        response.setExpiryDate(responseIndividual.getExpiryDate());
        response.setIdIssueCtry1(responseIndividual.getIdIssueCtry1());
        response.setPrefix(responseIndividual.getThaiSalutationCode());
        response.setResidentStatus(responseIndividual.getResidentFlag());
        if (Objects.nonNull(request.getThaiSalutationCode())) {
            response.setThaiSalutationCode(prepareDropDown(DROPDOWN_SALUTATION_TYPE, request.getThaiSalutationCode()));
        }
        response.setResidentFlag(prepareDropDown(DROPDOWN_RESIDENT_TYPE, request.getResidentFlag()));
        return response;
    }

    private Address prepareResponseAddress(Individual individual) {
        Optional<com.tmb.common.model.legacy.rsl.common.ob.address.Address> responseAddress = Arrays.stream(individual.getAddresses()).filter(x -> x.getAddrTypCode().equals("H")).findFirst();
        if (responseAddress.isPresent()) {
            com.tmb.common.model.legacy.rsl.common.ob.address.Address address = responseAddress.get();
            Address result = new Address();
            result.setId(address.getId());
            result.setNo(address.getAddress());
            result.setAmphur(address.getAmphur());
            result.setTumbol(address.getTumbol());
            result.setProvince(address.getProvince());
            result.setRoad(address.getRoad());
            result.setStreetName(address.getStreetName());
            result.setPostalCode(address.getPostalCode());
            result.setMoo(address.getMoo());
            result.setFloor(address.getFloor());
            result.setCountry(address.getCountry());
            if (!address.getBuildingName().isBlank() || !address.getBuildingName().isEmpty()) {
                String[] roomNo = address.getBuildingName().split("ห้อง");
                result.setBuildingName(roomNo[0]);
                if (roomNo.length > 1) {
                    result.setBuildingName(roomNo[0]);
                    result.setRoomNo("ห้อง" + roomNo[1]);
                }
            }
            return result;
        }
        return null;
    }


    private Individual prepareAddressTypeH(Individual individual, Address address) {
        var newAddress = new com.tmb.common.model.legacy.rsl.common.ob.address.Address();
        newAddress.setCifId(individual.getCifId());
        newAddress.setAddrTypCode("H");
        newAddress.setAddress(address.getNo());
        newAddress.setBuildingName(prepareBuildingName(address.getBuildingName(), address.getRoomNo()));
        newAddress.setFloor(address.getFloor());
        newAddress.setStreetName(address.getStreetName());
        newAddress.setRoad(address.getRoad());
        newAddress.setMoo(address.getMoo());
        newAddress.setTumbol(address.getTumbol());
        newAddress.setAmphur(address.getAmphur());
        newAddress.setProvince(address.getProvince());
        newAddress.setPostalCode(address.getPostalCode());
        newAddress.setCountry(address.getCountry());
        if (individual.getCountryOfRegAddr().equals(YES)) {
            individual.setCountryOfRegAddr(newAddress.getCountry());
        }

        setAddressByType(individual, newAddress);
        return individual;
    }


    private Individual prepareAddressTypeR(Individual individual, CustGeneralProfileResponse ec) {
        var newAddress = new com.tmb.common.model.legacy.rsl.common.ob.address.Address();
        newAddress.setCifId(individual.getCifId());
        newAddress.setAddrTypCode("R");
        newAddress.setAddress(ec.getCurrentAddrHouseNo());
        newAddress.setBuildingName(prepareBuildingName(ec.getCurrentAddrVillageOrbuilding(), ec.getCurrentAddrRoomNo()));
        newAddress.setFloor(ec.getCurrentAddrFloorNo());
        newAddress.setStreetName(ec.getCurrentAddrSoi());
        newAddress.setRoad(ec.getCurrentAddrStreet());
        newAddress.setMoo(ec.getCurrentAddrMoo());
        newAddress.setTumbol(ec.getCurrentAddrSubDistrictNameTh());
        newAddress.setAmphur(ec.getCurrentAddrdistrictNameTh());
        newAddress.setProvince(getProvince(ec.getCurrentAddrZipcode()));
        newAddress.setPostalCode(ec.getCurrentAddrZipcode());
        if (individual.getCountryOfRegAddr().equals(YES)) {
            individual.setCountryOfRegAddr(newAddress.getCountry());
        }

        setAddressByType(individual, newAddress);
        return individual;
    }


    private String prepareBuildingName(String buildingName, String roomNo) {
        if (Objects.nonNull(roomNo) && !roomNo.isEmpty()) {
            return prepareData(buildingName, "") + " "
                    + "ห้อง" + prepareData(roomNo, "");
        }
        return buildingName;
    }


    private Individual setAddressByType(Individual individual,
                                        com.tmb.common.model.legacy.rsl.common.ob.address.Address newAddress) {
        com.tmb.common.model.legacy.rsl.common.ob.address.Address[] individualAddresses = individual.getAddresses();
        if (Objects.nonNull(individualAddresses)) {
            Optional<com.tmb.common.model.legacy.rsl.common.ob.address.Address> oldAddress =
                    Arrays.stream(individualAddresses).filter(x -> x.getAddrTypCode().equals(newAddress.getAddrTypCode())).findFirst();
            if (oldAddress.isPresent()) {
                com.tmb.common.model.legacy.rsl.common.ob.address.Address address = oldAddress.get();
                newAddress.setId(address.getId());
                for (int i = 0; i < individual.getAddresses().length; i++) {
                    if (individual.getAddresses()[i].getAddrTypCode().equals(newAddress.getAddrTypCode())) {
                        individual.getAddresses()[i] = newAddress;
                        break;
                    }
                }
            }
        }
        return individual;
    }


    private CommonCodeEntry[] getDropdownList(String categoryCode) throws ServiceException, TMBCommonException, JsonProcessingException {
        ResponseDropdown getDropdownListResp = dropdownListClient.getDropDownListByCode(categoryCode);
        return getDropdownListResp.getBody().getCommonCodeEntries();
    }

    private List<DropDown> getDropDown(String type) throws ServiceException, TMBCommonException, JsonProcessingException {
        List<DropDown> dropDowns = new ArrayList<>();
        CommonCodeEntry[] entries = getDropdownList(type);
        for (CommonCodeEntry e : entries) {
            DropDown dropDown = new DropDown();
            dropDown.setEntryId(e.getEntryID());
            dropDown.setEntryCode(e.getEntryCode());
            dropDown.setEntryNameEng(e.getEntryName());
            dropDown.setEntryNameTh(e.getEntryName2());
            dropDown.setEntrySource(e.getEntrySource());
            dropDowns.add(dropDown);
        }
        return dropDowns;
    }

    private List<DropDown> prepareDropDown(String type, String flag) throws ServiceException, TMBCommonException, JsonProcessingException {
        List<DropDown> commonCodeEntry = getDropDown(type);
        List<DropDown> filterList = new ArrayList<>();
        commonCodeEntry.forEach(e -> {
            if (flag.equals(e.getEntryCode())) {
                filterList.add(e);
            }
        });

        return filterList;
    }

    public static Object prepareData(Object individual, Object custGeneralProfileResponse) {
        if (Objects.nonNull(individual) && !individual.toString().isEmpty()) {
            return individual;
        }
        return custGeneralProfileResponse;
    }


    private String getProvince(String postCode) {
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
