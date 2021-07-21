package com.tmb.oneapp.lendingservice.service;

import com.tmb.common.logger.TMBLogger;
import com.tmb.common.model.legacy.rsl.common.ob.facility.Facility;
import com.tmb.common.model.legacy.rsl.ws.facility.response.ResponseFacility;
import com.tmb.oneapp.lendingservice.client.LoanSubmissionGetFacilityInfoClient;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import javax.xml.rpc.ServiceException;
import java.rmi.RemoteException;

@Service
@AllArgsConstructor
public class LoanOnlineSubmissionGetLoanPaymentService {
    private final LoanSubmissionGetFacilityInfoClient getFacilityInfoClient;
    private static final TMBLogger<LoanOnlineSubmissionGetLoanPaymentService> logger = new TMBLogger<>(LoanOnlineSubmissionGetLoanPaymentService.class);

    public Facility[] getFacility(Long caID) throws ServiceException, RemoteException {
        try {
            ResponseFacility getFacilityResp = getFacilityInfoClient.searchFacilityInfoByCaID(caID);
            return getFacilityResp.getBody().getFacilities();
        } catch (Exception exception) {
            logger.info("constructRequestForLOCCompleteImage Start");
            throw exception;
        }
    }
}
