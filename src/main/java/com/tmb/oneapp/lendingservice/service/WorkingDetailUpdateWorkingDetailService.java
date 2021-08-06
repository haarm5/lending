package com.tmb.oneapp.lendingservice.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.tmb.common.exception.model.TMBCommonException;
import com.tmb.common.logger.TMBLogger;
import com.tmb.common.model.legacy.rsl.common.ob.individual.Individual;
import com.tmb.common.model.legacy.rsl.ws.individual.response.ResponseIndividual;
import com.tmb.oneapp.lendingservice.client.LoanSubmissionGetCustomerInfoClient;
import com.tmb.oneapp.lendingservice.client.LoanSubmissionUpdateCustomerClient;
import com.tmb.oneapp.lendingservice.constant.ResponseCode;
import com.tmb.oneapp.lendingservice.model.loanonline.UpdateWorkingDetailRequest;
import com.tmb.oneapp.lendingservice.model.personal.Address;
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
public class WorkingDetailUpdateWorkingDetailService {
    private static final TMBLogger<WorkingDetailUpdateWorkingDetailService> logger = new TMBLogger<>(WorkingDetailUpdateWorkingDetailService.class);


    private final LoanSubmissionGetCustomerInfoClient customerInfoClient;
    private final LoanSubmissionUpdateCustomerClient loanSubmissionUpdateCustomerClient;

    public com.tmb.common.model.legacy.rsl.ws.individual.update.response.ResponseIndividual updateWorkDetail(UpdateWorkingDetailRequest request) throws ServiceException, TMBCommonException, RemoteException, JsonProcessingException {

        try {
            Individual individual = getCustomer(request.getCaId());
            prepareIndividual(individual, request);
            com.tmb.common.model.legacy.rsl.ws.individual.update.response.ResponseIndividual res = loanSubmissionUpdateCustomerClient.updateCustomerInfo(individual);
            if (res.getHeader().getResponseCode().equals("MSG_000")) {
                return res;
            } else {
                throw new TMBCommonException(res.getHeader().getResponseCode(),
                        res.getHeader().getResponseDescriptionEN(),
                        ResponseCode.FAILED.getService(), HttpStatus.NOT_FOUND, null);
            }
        } catch (Exception e) {
            logger.error("update customer soap error", e);
            throw e;
        }
    }


    private Individual prepareIndividual(Individual individual, UpdateWorkingDetailRequest request) {
        individual.setEmploymentStatus(request.getEmploymentStatus());
        individual.setEmploymentOccupation(request.getOccupation());
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
        individual.setIncomeDeclared(request.getIncomeDeclared());
        individual.setIncometotalLastMthCreditAcct1(request.getIncomeTotalLastMthCreditAcct1());
        individual.setIncomeType(request.getIncomeType());
        individual.setSourceFromCountry("TH");
        individual.setMailingPreference(request.getMailingPreference());
        individual.setEmailStatementFlag(request.getEmailStatementFlag());
        return individual;
    }

    private Individual prepareAddress(Individual individual, Address address) {
        com.tmb.common.model.legacy.rsl.common.ob.address.Address[] individualAddresses = individual.getAddresses();
        if (Objects.nonNull(individualAddresses)) {
            Optional<com.tmb.common.model.legacy.rsl.common.ob.address.Address> oldAddress = Arrays.stream(individualAddresses).filter(x -> x.getAddrTypCode().equals("O")).findFirst();

            var newAddress = new com.tmb.common.model.legacy.rsl.common.ob.address.Address();
            newAddress.setCifId(individual.getCifId());
            newAddress.setAddrTypCode("O");
            newAddress.setAddress(address.getNo());
            newAddress.setBuildingName(address.getBuildingName());
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
                    if (individual.getAddresses()[i].getAddrTypCode().equals("O")) {
                        individual.getAddresses()[i] = newAddress;
                        break;
                    }
                }
            } else {
                individualAddresses[individualAddresses.length] = newAddress;
            }
        }
        return individual;
    }


    private Individual getCustomer(Long caID) throws ServiceException, RemoteException, TMBCommonException, JsonProcessingException {
        try {
            ResponseIndividual response = customerInfoClient.searchCustomerInfoByCaID(caID);
            if (response.getHeader().getResponseCode().equals("MSG_000")) {
                return response.getBody().getIndividuals() == null ? null : response.getBody().getIndividuals()[0];
            } else {
                throw new TMBCommonException(response.getHeader().getResponseCode(),
                        response.getHeader().getResponseDescriptionEN(),
                        ResponseCode.FAILED.getService(), HttpStatus.NOT_FOUND, null);
            }
        } catch (Exception e) {
            logger.error("update customer then get customer soap error", e);
            throw e;
        }
    }
}
