package com.tmb.oneapp.lendingservice.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.tmb.common.exception.model.TMBCommonException;
import com.tmb.common.logger.TMBLogger;
import com.tmb.common.model.legacy.rsl.common.ob.individual.Individual;
import com.tmb.common.model.legacy.rsl.ws.individual.update.request.Body;
import com.tmb.common.model.legacy.rsl.ws.individual.update.request.RequestIndividual;
import com.tmb.common.model.legacy.rsl.ws.individual.update.response.ResponseIndividual;
import com.tmb.oneapp.lendingservice.client.LoanSubmissionGetCustomerInfoClient;
import com.tmb.oneapp.lendingservice.client.LoanSubmissionUpdateCustomerClient;
import com.tmb.oneapp.lendingservice.constant.ResponseCode;
import com.tmb.oneapp.lendingservice.model.personal.Address;
import com.tmb.oneapp.lendingservice.model.personal.PersonalDetailSaveInfoRequest;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import javax.xml.rpc.ServiceException;
import java.rmi.RemoteException;
import java.util.Arrays;
import java.util.Objects;
import java.util.Optional;

@Service
@AllArgsConstructor
public class PersonalDetailSaveInfoService {
    private static final TMBLogger<PersonalDetailSaveInfoService> logger = new TMBLogger<>(PersonalDetailSaveInfoService.class);
    private final LoanSubmissionUpdateCustomerClient updateCustomerClient;
    private final LoanSubmissionGetCustomerInfoClient getCustomerInfoClient;
    static final String MSG_000 = "MSG_000";

    public ResponseIndividual updateCustomerInfo(PersonalDetailSaveInfoRequest request) throws ServiceException, RemoteException, TMBCommonException, JsonProcessingException {
        RequestIndividual responseIndividual = new RequestIndividual();

        Individual individual = getCustomerInfo(request.getCaId());

        Body body = new Body();

        if (individual != null) {
            individual.setAddresses(prepareAddress(individual,request.getAddress()).getAddresses());
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
        }

        return saveCustomer(responseIndividual.getBody().getIndividual());
    }

    private ResponseIndividual saveCustomer(Individual individual) throws ServiceException, TMBCommonException, JsonProcessingException {
        try {
            ResponseIndividual response = updateCustomerClient.updateCustomerInfo(individual);
            if (response.getHeader().getResponseCode().equals(MSG_000)) {
                return response;
            } else {
                throw new TMBCommonException(response.getHeader().getResponseCode(),
                        response.getHeader().getResponseDescriptionEN(), ResponseCode.FAILED.getService(), HttpStatus.NOT_FOUND, null);
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
            }
        } catch (Exception e) {
            logger.error("get customer info soap error", e);
            throw e;
        }

        return null;
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

}
