package com.tmb.oneapp.lendingservice.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.tmb.common.exception.model.TMBCommonException;
import com.tmb.common.logger.TMBLogger;
import com.tmb.common.model.legacy.rsl.common.ob.dropdown.CommonCodeEntry;
import com.tmb.common.model.legacy.rsl.common.ob.individual.Individual;
import com.tmb.common.model.legacy.rsl.ws.dropdown.response.ResponseDropdown;
import com.tmb.common.model.legacy.rsl.ws.individual.update.request.Body;
import com.tmb.common.model.legacy.rsl.ws.individual.update.request.RequestIndividual;
import com.tmb.common.model.legacy.rsl.ws.individual.update.response.ResponseIndividual;
import com.tmb.oneapp.lendingservice.client.LoanSubmissionGetCustomerInfoClient;
import com.tmb.oneapp.lendingservice.client.LoanSubmissionGetDropdownListClient;
import com.tmb.oneapp.lendingservice.client.LoanSubmissionUpdateCustomerClient;
import com.tmb.oneapp.lendingservice.constant.ResponseCode;
import com.tmb.oneapp.lendingservice.model.personal.*;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import javax.xml.rpc.ServiceException;
import java.rmi.RemoteException;
import java.util.*;

@Service
@AllArgsConstructor
public class PersonalDetailSaveInfoService {
    private static final TMBLogger<PersonalDetailSaveInfoService> logger = new TMBLogger<>(PersonalDetailSaveInfoService.class);
    private final LoanSubmissionUpdateCustomerClient updateCustomerClient;
    private final LoanSubmissionGetCustomerInfoClient getCustomerInfoClient;
    private final LoanSubmissionGetDropdownListClient dropdownListClient;
    static final String DROPDOWN_RESIDENT_TYPE = "RESIDENT_TYP";
    static final String DROPDOWN_SALUTATION_TYPE = "SALUTATION";

    public PersonalDetailResponse updateCustomerInfo(PersonalDetailSaveInfoRequest request) throws ServiceException, TMBCommonException, JsonProcessingException, RemoteException {
        RequestIndividual responseIndividual = new RequestIndividual();

        Individual individual = getCustomerInfo(request.getCaId());

        Body body = new Body();

        individual.setAddresses(prepareAddress(individual, request.getAddress()).getAddresses());
        individual.setPersonalInfoSavedFlag("Y");
        individual.setNationality(request.getNationality());
        individual.setMobileNo(request.getMobileNo());
        individual.setThaiName(request.getThaiName());
        individual.setThaiSalutationCode(request.getThaiSalutationCode());
        individual.setThaiSurName(request.getThaiSurname());
        individual.setNameLine1(request.getEngSurName());
        individual.setNameLine2(request.getEngName());
        individual.setEmail(request.getEmail());
        individual.setIdIssueCtry1(request.getIdIssueCtry1());
        individual.setResidentFlag(request.getResidentFlag());
        individual.setExpiryDate(request.getExpiryDate());
        individual.setBirthDate(request.getBirthDate());
        individual.setAccounts(individual.getAccounts());

        body.setIndividual(individual);
        responseIndividual.setBody(body);
        return saveCustomer(request.getCaId(), responseIndividual.getBody().getIndividual(), request);
    }

    private PersonalDetailResponse saveCustomer(Long caId, Individual individual, PersonalDetailSaveInfoRequest request) throws ServiceException, TMBCommonException, JsonProcessingException, RemoteException {
        try {
            ResponseIndividual response = updateCustomerClient.updateCustomerInfo(individual);
            PersonalDetailResponse detailResponse = new PersonalDetailResponse();
            Address address = new Address();

            if (response != null) {
                Individual responseIndividual = getCustomerInfo(caId);

                Optional<com.tmb.common.model.legacy.rsl.common.ob.address.Address> responseAddress = Arrays.stream(responseIndividual.getAddresses()).filter(x -> x.getAddrTypCode().equals("R")).findFirst();
                if (responseAddress.isPresent()) {
                    com.tmb.common.model.legacy.rsl.common.ob.address.Address personalAddress = responseAddress.get();
                    address.setId(personalAddress.getId());
                    address.setNo(personalAddress.getAddress());
                    address.setAmphur(personalAddress.getAmphur());
                    address.setTumbol(personalAddress.getTumbol());
                    address.setProvince(personalAddress.getProvince());
                    address.setRoad(personalAddress.getRoad());
                    address.setStreetName(personalAddress.getStreetName());
                    address.setPostalCode(personalAddress.getPostalCode());
                    address.setMoo(personalAddress.getMoo());
                    address.setFloor(personalAddress.getFloor());
                    address.setCountry(personalAddress.getCountry());
                    address.setBuildingName(personalAddress.getBuildingName());
                }

                detailResponse.setAddress(address);
                detailResponse.setCitizenId(responseIndividual.getIdNo1());
                detailResponse.setThaiName(responseIndividual.getThaiName());
                detailResponse.setThaiSurname(responseIndividual.getThaiSurName());
                detailResponse.setNationality(responseIndividual.getNationality());
                detailResponse.setMobileNo(responseIndividual.getMobileNo());
                detailResponse.setEmail(responseIndividual.getEmail());
                detailResponse.setBirthDate(responseIndividual.getBirthDate());
                detailResponse.setEngName(responseIndividual.getNameLine2());
                detailResponse.setEngSurname(responseIndividual.getNameLine1());
                detailResponse.setExpiryDate(responseIndividual.getExpiryDate());
                detailResponse.setIdIssueCtry1(responseIndividual.getIdIssueCtry1());
                detailResponse.setThaiSalutationCode(prepareDropDown(DROPDOWN_SALUTATION_TYPE,request.getThaiSalutationCode()));
                detailResponse.setResidentFlag(prepareDropDown(DROPDOWN_RESIDENT_TYPE,request.getResidentFlag()));


                return detailResponse;
            } else {
                throw new TMBCommonException(ResponseCode.FAILED.getCode(),
                        ResponseCode.FAILED.getDesc(),
                        ResponseCode.FAILED.getService(), HttpStatus.NOT_FOUND, null);
            }

        } catch (Exception e) {
            logger.error("update customer soap error", e);
            throw e;
        }
    }

    private Individual getCustomerInfo(Long caId) throws RemoteException, TMBCommonException, JsonProcessingException, ServiceException {
        try {
            Individual individual = getCustomerInfoClient.searchCustomerInfoByCaID(caId).getBody().getIndividuals()[0];
            if (individual != null) {
                return individual;
            } else {
                throw new TMBCommonException(ResponseCode.FAILED.getCode(),
                        ResponseCode.FAILED.getDesc(),
                        ResponseCode.FAILED.getService(), HttpStatus.NOT_FOUND, null);
            }
        } catch (Exception e) {
            logger.error("get customer info soap error", e);
            throw e;
        }

    }


    private Individual prepareAddress(Individual individual, Address address) {
        com.tmb.common.model.legacy.rsl.common.ob.address.Address[] individualAddresses = individual.getAddresses();
        if (Objects.nonNull(individualAddresses)) {
            Optional<com.tmb.common.model.legacy.rsl.common.ob.address.Address> oldAddress = Arrays.stream(individualAddresses).filter(x -> x.getAddrTypCode().equals("R")).findFirst();

            var newAddress = new com.tmb.common.model.legacy.rsl.common.ob.address.Address();
            newAddress.setCifId(individual.getCifId());
            newAddress.setAddrTypCode("R");
            newAddress.setAddress(address.getNo());
            newAddress.setBuildingName(address.getBuildingName() + " " + address.getRoomNo());
            newAddress.setFloor(address.getFloor());
            newAddress.setStreetName(address.getStreetName());
            newAddress.setRoad(address.getRoad());
            newAddress.setMoo(address.getMoo());
            newAddress.setTumbol(address.getTumbol());
            newAddress.setAmphur(address.getAmphur());
            newAddress.setProvince(address.getProvince());
            newAddress.setPostalCode(address.getPostalCode());
            newAddress.setCountry(address.getCountry());

            if (oldAddress.isPresent()) {
                com.tmb.common.model.legacy.rsl.common.ob.address.Address workingAddress = oldAddress.get();
                newAddress.setId(workingAddress.getId());
                for (int i = 0; i < individual.getAddresses().length; i++) {
                    if (individual.getAddresses()[i].getAddrTypCode().equals("R")) {
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
}
