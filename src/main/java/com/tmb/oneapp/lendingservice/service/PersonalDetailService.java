package com.tmb.oneapp.lendingservice.service;

import com.tmb.common.exception.model.TMBCommonException;
import com.tmb.common.logger.TMBLogger;
import com.tmb.common.model.legacy.rsl.common.ob.individual.Individual;
import com.tmb.common.model.legacy.rsl.ws.individual.response.ResponseIndividual;
import com.tmb.oneapp.lendingservice.client.LoanSubmissionGetCustomerInfoClient;
import com.tmb.oneapp.lendingservice.client.LoanSubmissionInstantLoanCalUWClient;
import com.tmb.oneapp.lendingservice.constant.ResponseCode;
import com.tmb.oneapp.lendingservice.model.personal.Address;
import com.tmb.oneapp.lendingservice.model.personal.PersonalDetailResponse;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import javax.xml.rpc.ServiceException;
import java.rmi.RemoteException;

@Service
@AllArgsConstructor
public class PersonalDetailService {
    private static final TMBLogger<PersonalDetailService> logger = new TMBLogger<>(PersonalDetailService.class);
    private final LoanSubmissionGetCustomerInfoClient customerInfoClient;
    static final  String MSG_000 = "MSG_000";

    public PersonalDetailResponse getPersonalDetail(Long caId) throws ServiceException, TMBCommonException, RemoteException {
        PersonalDetailResponse response = new PersonalDetailResponse();
        Address address = new Address();
        Individual individual =  getCustomer(caId);
        if (individual.getPersonalInfoSavedFlag().equals("Y")) {
            response.setEmail(individual.getEmail());
            response.setBirthDate(individual.getBirthDate().getTime());
            response.setEngName(individual.getNameLine2());
            response.setEngSurName(individual.getNameLine1());
            response.setNationality(individual.getNationality());
            response.setExpiryDate(individual.getExpiryDate().getTime());
            response.setMobileNo(individual.getMobileNo());
            response.setThaiName(individual.getThaiName());
            response.setThaiSurName(individual.getThaiSurName());
            response.setThaiSalutationCode(individual.getThaiSalutationCode());
            response.setResidentFlag(individual.getResidentFlag());

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
            // GET EC
        }


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
        }catch (Exception e) {
            logger.error("get customer soap error",e);
            throw e;
        }
    }
}
