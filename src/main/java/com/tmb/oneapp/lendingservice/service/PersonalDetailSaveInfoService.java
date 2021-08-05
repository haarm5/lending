package com.tmb.oneapp.lendingservice.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.tmb.common.exception.model.TMBCommonException;
import com.tmb.common.logger.TMBLogger;
import com.tmb.common.model.legacy.rsl.common.ob.address.Address;
import com.tmb.common.model.legacy.rsl.common.ob.individual.Individual;
import com.tmb.common.model.legacy.rsl.ws.individual.update.request.Body;
import com.tmb.common.model.legacy.rsl.ws.individual.update.request.RequestIndividual;
import com.tmb.common.model.legacy.rsl.ws.individual.update.response.ResponseIndividual;
import com.tmb.oneapp.lendingservice.client.LoanSubmissionGetCustomerInfoClient;
import com.tmb.oneapp.lendingservice.client.LoanSubmissionUpdateCustomerClient;
import com.tmb.oneapp.lendingservice.constant.ResponseCode;
import com.tmb.oneapp.lendingservice.model.personal.PersonalDetailSaveInfoRequest;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import javax.xml.rpc.ServiceException;
import java.rmi.RemoteException;

@Service
@AllArgsConstructor
public class PersonalDetailSaveInfoService {
    private static final TMBLogger<PersonalDetailSaveInfoService> logger = new TMBLogger<>(PersonalDetailSaveInfoService.class);
    private final LoanSubmissionUpdateCustomerClient updateCustomerClient;
    private final LoanSubmissionGetCustomerInfoClient getCustomerInfoClient;

    public ResponseIndividual updateCustomerInfo(PersonalDetailSaveInfoRequest request) throws ServiceException, RemoteException, TMBCommonException, JsonProcessingException {
        RequestIndividual responseIndividual = new RequestIndividual();

        Individual individual = getCustomerInfo(request.getCaId());

        Body body = new Body();
        Address address = new Address();

        address.setTumbol(request.getAddress().getTumbol());
        address.setRoad(request.getAddress().getRoad());
        address.setProvince(request.getAddress().getProvince());
        address.setStreetName(request.getAddress().getStreetName());
        address.setMoo(request.getAddress().getMoo());
        address.setId(request.getAddress().getId());
        address.setFloor(request.getAddress().getFloor());
        address.setCountry(request.getAddress().getCountry());
        address.setPostalCode(request.getAddress().getPostalCode());
        address.setAmphur(request.getAddress().getAmphur());
        address.setBuildingName(request.getAddress().getBuildingName());
        address.setAddrTypCode(request.getAddress().getAddrTypCode());
        address.setCifId(address.getCifId());
        Address[] addresss = {address};

        individual.setPersonalInfoSavedFlag("Y");
        individual.setNationality(request.getNationality());
        individual.setMobileNo(request.getMobileNo());
        individual.setThaiName(request.getThaiName());
        individual.setThaiSalutationCode(request.getThaiSalutationCode());
        individual.setThaiSurName(request.getThaiSurName());
        individual.setNameLine1(request.getEngSurName());
        individual.setNameLine2(request.getEngName());
        individual.setEmail(request.getEmail());
        individual.setIdIssueCtry1(request.getIdIssueCtry1());
        individual.setResidentFlag(request.getResidentFlag());
        individual.setExpiryDate(request.getExpiryDate());
        individual.setBirthDate(request.getBirthDate());
        individual.setAccounts(individual.getAccounts());
        individual.setAddresses(addresss);

        body.setIndividual(individual);
        responseIndividual.setBody(body);

        return saveCustomer(responseIndividual.getBody().getIndividual());
    }

    private ResponseIndividual saveCustomer(Individual individual) throws ServiceException, TMBCommonException, JsonProcessingException, RemoteException {
        try {
            ResponseIndividual response = updateCustomerClient.updateCustomerInfo(individual);
            if (response != null) {
                return response;
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

    private Individual getCustomerInfo(Long caId) throws ServiceException, RemoteException, TMBCommonException, JsonProcessingException {
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

}
