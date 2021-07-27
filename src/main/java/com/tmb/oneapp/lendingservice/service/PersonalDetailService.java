package com.tmb.oneapp.lendingservice.service;

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
import com.tmb.oneapp.lendingservice.constant.ResponseCode;
import com.tmb.oneapp.lendingservice.model.personal.Address;
import com.tmb.oneapp.lendingservice.model.personal.PersonalDetailResponse;
import com.tmb.oneapp.lendingservice.model.personal.Resident;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import javax.xml.rpc.ServiceException;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.List;

@Service
@AllArgsConstructor
public class PersonalDetailService {
    private static final TMBLogger<PersonalDetailService> logger = new TMBLogger<>(PersonalDetailService.class);
    private final LoanSubmissionGetCustomerInfoClient customerInfoClient;
    private final CustomerServiceClient customerServiceClient;
    private final LoanSubmissionGetDropdownListClient dropdownListClient;
    static final String MSG_000 = "MSG_000";
    static final String DROPDOWN_RESIDENT_TYPE = "RESIDENT_TYP";

    public PersonalDetailResponse getPersonalDetail(String crmid, Long caId) throws ServiceException, TMBCommonException, RemoteException {
        PersonalDetailResponse response = new PersonalDetailResponse();
        Address address = new Address();
        Individual individual = getCustomer(caId);

        if (individual != null) {
            //rsl
            if (individual.getPersonalInfoSavedFlag().equals("Y")) {
                response.setEmail(individual.getEmail());
                response.setBirthDate(individual.getBirthDate().getTime().toString());
                response.setEngName(individual.getNameLine2());
                response.setEngSurName(individual.getNameLine1());
                response.setNationality(individual.getNationality());
                response.setExpiryDate(individual.getExpiryDate().getTime().toString());
                response.setMobileNo(individual.getMobileNo());
                response.setThaiName(individual.getThaiName());
                response.setThaiSurName(individual.getThaiSurName());
                response.setThaiSalutationCode(individual.getThaiSalutationCode());

                address.setAmphur(individual.getAddresses()[0].getAmphur());
                address.setCountry(individual.getAddresses()[0].getCountry());
                address.setFloor(individual.getAddresses()[0].getFloor());
                address.setBuildingName(individual.getAddresses()[0].getBuildingName());
                address.setMoo(individual.getAddresses()[0].getMoo());
                address.setNo(individual.getAddresses()[0].getAddress());
                address.setProvince(individual.getAddresses()[0].getProvince());
                address.setRoad(individual.getAddresses()[0].getRoad());
                address.setPostalCode(individual.getAddresses()[0].getPostalCode());
                address.setStreetName(individual.getAddresses()[0].getStreetName());
                address.setTumbol(individual.getAddresses()[0].getTumbol());

                response.setAddress(address);

            } else {
                //ec
                CustGeneralProfileResponse custGeneralProfileResponse = getCustomerEC(crmid);
                response.setEmail(custGeneralProfileResponse.getEmailAddress());
                response.setBirthDate(custGeneralProfileResponse.getIdBirthDate());
                response.setEngName(custGeneralProfileResponse.getEngFname());
                response.setEngSurName(custGeneralProfileResponse.getEngLname());
                response.setNationality(custGeneralProfileResponse.getNationality());
                response.setExpiryDate(custGeneralProfileResponse.getIdExpireDate());
                response.setMobileNo(custGeneralProfileResponse.getPhoneNoFull());
                response.setThaiName(custGeneralProfileResponse.getThaFname());
                response.setThaiSurName(custGeneralProfileResponse.getThaLname());
                response.setThaiSalutationCode(custGeneralProfileResponse.getCitizenId());

                address.setAmphur(custGeneralProfileResponse.getCurrentAddrdistrictNameTh());
                address.setCountry(custGeneralProfileResponse.getNationality());
                address.setFloor(custGeneralProfileResponse.getCurrentAddrFloorNo());
                address.setBuildingName(custGeneralProfileResponse.getCurrentAddrVillageOrbuilding());
                address.setMoo(custGeneralProfileResponse.getCurrentAddrMoo());
                address.setNo(custGeneralProfileResponse.getCurrentAddrHouseNo());
                address.setProvince(custGeneralProfileResponse.getCurrentAddrProvinceNameTh());
                address.setRoad(custGeneralProfileResponse.getCurrentAddrStreet());
                address.setPostalCode(custGeneralProfileResponse.getCurrentAddrZipcode());
                address.setStreetName(custGeneralProfileResponse.getCurrentAddrStreet());
                address.setTumbol(custGeneralProfileResponse.getCurrentAddrSubDistrictNameTh());

                response.setAddress(address);
            }
        }


        response.setResidentFlag(getResidents());

        return response;
    }


    private Individual getCustomer(Long caID) throws ServiceException, RemoteException, TMBCommonException {
        try {
            ResponseIndividual response = customerInfoClient.searchCustomerInfoByCaID(caID);
            if (response.getHeader().getResponseCode().equals(MSG_000)) {
                return response.getBody().getIndividuals() == null ? null : response.getBody().getIndividuals()[0];
            } else {
                throw new TMBCommonException(response.getHeader().getResponseCode(),
                        response.getHeader().getResponseDescriptionEN(),
                        ResponseCode.FAILED.getService(), HttpStatus.NOT_FOUND, null);
            }
        } catch (Exception e) {
            logger.error("get customer soap error", e);
            throw e;
        }
    }

    private CustGeneralProfileResponse getCustomerEC(String crmid) throws TMBCommonException {
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

    private CommonCodeEntry[] getDropdownList(String categoryCode) throws ServiceException, RemoteException {
        ResponseDropdown getDropdownListResp = dropdownListClient.getDropdownList(categoryCode);
        return getDropdownListResp.getBody().getCommonCodeEntries();
    }

    private List<Resident> getResidents() throws ServiceException, RemoteException {
        List<Resident> residents = new ArrayList<>();
        CommonCodeEntry[] entries = getDropdownList(DROPDOWN_RESIDENT_TYPE);
        for (CommonCodeEntry e : entries) {
            Resident resident = new Resident();
            resident.setEntryId(e.getEntryID());
            resident.setEntryCode(e.getEntryCode());
            resident.setEntryNameEng(e.getEntryName());
            resident.setEntryNameTh(e.getEntryName2());
            resident.setEntrySource(e.getEntrySource());
            residents.add(resident);
        }
        return residents;
    }
}
