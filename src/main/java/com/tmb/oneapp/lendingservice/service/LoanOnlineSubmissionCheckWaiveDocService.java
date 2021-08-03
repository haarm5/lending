package com.tmb.oneapp.lendingservice.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.tmb.common.exception.model.TMBCommonException;
import com.tmb.common.logger.TMBLogger;
import com.tmb.common.model.CustGeneralProfileResponse;
import com.tmb.common.model.TmbOneServiceResponse;
import com.tmb.common.model.legacy.rsl.ws.dropdown.response.ResponseDropdown;
import com.tmb.common.model.legacy.rsl.ws.incomemodel.response.ResponseIncomeModel;
import com.tmb.oneapp.lendingservice.client.CustomerServiceClient;
import com.tmb.oneapp.lendingservice.client.LoanSubmissionGetDropdownListClient;
import com.tmb.oneapp.lendingservice.client.LoanSubmissionGetIncomeModelInfoClient;
import com.tmb.oneapp.lendingservice.model.loanonline.IncomeInfo;
import lombok.AllArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import javax.xml.rpc.ServiceException;
import java.rmi.RemoteException;
import java.util.Arrays;
import java.util.Objects;

@Service
@AllArgsConstructor
public class LoanOnlineSubmissionCheckWaiveDocService {
    private static final TMBLogger<LoanOnlineSubmissionCheckWaiveDocService> logger = new TMBLogger<>(LoanOnlineSubmissionCheckWaiveDocService.class);
    private final LoanSubmissionGetIncomeModelInfoClient incomeModelInfoClient;
    private CustomerServiceClient customerServiceClient;
    private final LoanSubmissionGetDropdownListClient getDropdownListClient;


    public IncomeInfo getIncomeInfoByRmId(String rmId) throws ServiceException, RemoteException, TMBCommonException, JsonProcessingException {
        try {

            ResponseIncomeModel responseIncomeModel = incomeModelInfoClient.getIncomeInfo(StringUtils.right(rmId, 14));
            if (Objects.isNull(responseIncomeModel) || Objects.isNull(responseIncomeModel.getBody()) || Objects.isNull(responseIncomeModel.getBody().getIncomeModelAmt())) {
                return null;
            }

            IncomeInfo incomeInfo = new IncomeInfo();
            incomeInfo.setIncomeAmount(responseIncomeModel.getBody().getIncomeModelAmt());
            incomeInfo.setStatusWorking(getWorkingStatus(rmId));

            return incomeInfo;
        } catch (Exception e) {
            logger.error("getIncomeInfoByRmId error from soap:", e);
            throw e;
        }
    }

    private String getWorkingStatus(String rmId) throws ServiceException, RemoteException, TMBCommonException, JsonProcessingException {
        try {
            ResponseEntity<TmbOneServiceResponse<CustGeneralProfileResponse>> responseWorkingProfileInfo = customerServiceClient
                    .getCustomers(rmId);
            if (Objects.nonNull(responseWorkingProfileInfo)
                    && Objects.nonNull(responseWorkingProfileInfo.getBody())
                    && Objects.nonNull(responseWorkingProfileInfo.getBody().getData())) {
                return mapWorkingStatus(responseWorkingProfileInfo.getBody().getData().getOccupationCode());
            }
            return null;
        } catch (Exception e) {
            logger.error("getWorkingStatus error from soap:", e);
            throw e;
        }
    }

    private String mapWorkingStatus(String occupationCode) throws ServiceException, TMBCommonException, JsonProcessingException {
        try {
            ResponseDropdown getDropdownListResp = getDropdownListClient.getDropDownListByCode("RM_OCCUPATION");
            var list = getDropdownListResp.getBody().getCommonCodeEntries();
            var commonCodeEntry = Arrays.stream(list).filter(a -> a.getEntryCode().equals(occupationCode)).findFirst();
            if (commonCodeEntry.isPresent()) {
                var result = commonCodeEntry.get();
                if (result.getExtValue1().equals("01")) {
                    return "salary";
                } else if (result.getExtValue1().equals("02")) {
                    return "self_employed";
                }
            }
            return null;
        } catch (Exception e) {
            logger.error("mapWorkingStatus at getDropdownListClient error from soap:", e);
            throw e;
        }
    }
}
