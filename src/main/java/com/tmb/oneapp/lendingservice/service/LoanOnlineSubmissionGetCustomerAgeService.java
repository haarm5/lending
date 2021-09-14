package com.tmb.oneapp.lendingservice.service;


import com.tmb.common.exception.model.TMBCommonException;
import com.tmb.common.logger.TMBLogger;
import com.tmb.common.model.CustGeneralProfileResponse;
import com.tmb.common.model.TmbOneServiceResponse;
import com.tmb.oneapp.lendingservice.client.CustomerServiceClient;
import com.tmb.oneapp.lendingservice.constant.ResponseCode;
import com.tmb.oneapp.lendingservice.model.rsl.LoanSubmissionGetCustomerAgeResponse;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.Objects;

@Service
@AllArgsConstructor
public class LoanOnlineSubmissionGetCustomerAgeService {
    private static final TMBLogger<LoanOnlineSubmissionGetCustomerAgeService> logger = new TMBLogger<>(LoanOnlineSubmissionGetCustomerAgeService.class);
    private final CustomerServiceClient customerServiceClient;


    public LoanSubmissionGetCustomerAgeResponse getAge(String crmId) throws TMBCommonException {
        CustGeneralProfileResponse customer = getCustomerEC(crmId);
        if (Objects.nonNull(customer.getIdBirthDate())) {
            LocalDate birtDate = LocalDate.parse(customer.getIdBirthDate());
            LocalDate currentDate = LocalDate.now();
            LoanSubmissionGetCustomerAgeResponse response = new LoanSubmissionGetCustomerAgeResponse();
            response.setAge(birtDate.until(currentDate, ChronoUnit.YEARS));
            response.setBirthDate(customer.getIdBirthDate());
            response.setExpireDate(customer.getIdExpireDate());
            response.setIdType(customer.getIdType());
            return response;
        }
        return null;
    }

    private CustGeneralProfileResponse getCustomerEC(String crmId) throws TMBCommonException {
        try {
            TmbOneServiceResponse<CustGeneralProfileResponse> response = customerServiceClient.getCustomers(crmId).getBody();
            if (Objects.nonNull(response) && response.getStatus().getCode().equals("0000")) {
                return response.getData();
            }
            throw new TMBCommonException(ResponseCode.FAILED.getCode(),
                    ResponseCode.FAILED.getMessage(),
                    ResponseCode.FAILED.getService(), HttpStatus.NOT_FOUND, null);
        } catch (Exception e) {
            logger.error("get customer age => get CustomerEC  error", e);
            throw e;
        }
    }
}
