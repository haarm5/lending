package com.tmb.oneapp.lendingservice.service;

import com.tmb.common.model.TmbOneServiceResponse;
import com.tmb.common.model.TmbStatus;
import com.tmb.common.model.legacy.rsl.common.ob.creditcard.InstantCreditCard;
import com.tmb.common.model.legacy.rsl.common.ob.facility.InstantFacility;
import com.tmb.common.model.legacy.rsl.ws.instant.eligible.product.response.Header;
import com.tmb.common.model.legacy.rsl.ws.instant.eligible.product.response.ResponseInstantLoanGetEligibleProduct;
import com.tmb.oneapp.lendingservice.constant.ResponseCode;
import com.tmb.oneapp.lendingservice.model.account.AccountSaving;
import com.tmb.oneapp.lendingservice.model.account.LoanAccount;
import com.tmb.oneapp.lendingservice.model.account.LoanSummary;
import com.tmb.oneapp.lendingservice.model.config.CommonProductConfig;
import com.tmb.oneapp.lendingservice.model.config.LendingModuleConfig;
import com.tmb.oneapp.lendingservice.model.creditcard.CreditCard;
import com.tmb.oneapp.lendingservice.model.creditcard.CreditCardResponse;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

public class LoanServiceUtils {

    private static HashMap<String, CommonProductConfig> productConfigHashMap = new HashMap<>();

    static {
        CommonProductConfig viProductConfig = new CommonProductConfig();
        viProductConfig.setRslCode("VI");
        viProductConfig.setProductNameEn("ttb reserve infinite");
        viProductConfig.setProductNameTh("ttb reserve infinite");
        viProductConfig.setContentLink("https://www-uat.tau2904.com/ttb-reserve/main/index.html?inapp=y&dl=n");

        CommonProductConfig vjProductConfig = new CommonProductConfig();
        vjProductConfig.setRslCode("VJ");
        vjProductConfig.setProductNameEn("ttb absolute");
        vjProductConfig.setProductNameTh("ttb absolute");
        vjProductConfig.setContentLink("VJ Content Link");

        productConfigHashMap.put("VI", viProductConfig);
        productConfigHashMap.put("VJ", vjProductConfig);
    }

    public static CommonProductConfig getProductConfig(String rslCode) {
        return productConfigHashMap.get(rslCode.toUpperCase());
    }

    public static TmbOneServiceResponse<List<LendingModuleConfig>> moduleLendingModuleConfig() {

        LendingModuleConfig mockLendingModuleConfig = new LendingModuleConfig();
        mockLendingModuleConfig.setFlexiOnly(new ArrayList<>(Arrays.asList("VI", "VB")));
        mockLendingModuleConfig.setApplyCreditCards(new ArrayList<>(productConfigHashMap.values()));
        mockLendingModuleConfig.setApplyPersonalLoans(new ArrayList<>());
        mockLendingModuleConfig.setIncompleteDocStatus(new ArrayList<>(Arrays.asList("IDOFD", "IDDFD")));
        TmbOneServiceResponse<List<LendingModuleConfig>> oneServiceResponse = new TmbOneServiceResponse<>();
        oneServiceResponse.setData(new ArrayList<>(Arrays.asList(mockLendingModuleConfig)));
        TmbStatus tmbStatus = new TmbStatus();
        tmbStatus.setCode(ResponseCode.SUCCESS.getCode());
        oneServiceResponse.setStatus(tmbStatus);
        return oneServiceResponse;
    }

    public static TmbOneServiceResponse<CreditCardResponse> mockOneAppCreditCardResponse(CreditCard... creditCards) {
        TmbOneServiceResponse<CreditCardResponse> mockCreditCardResponse = new TmbOneServiceResponse<>();
        TmbStatus tmbStatus = new TmbStatus();
        tmbStatus.setCode(ResponseCode.SUCCESS.getCode());
        mockCreditCardResponse.setStatus(tmbStatus);
        CreditCardResponse mockCreditCardData = new CreditCardResponse();
        mockCreditCardData.setCreditCards(Arrays.asList(creditCards));
        mockCreditCardData.setFlashCards(new ArrayList<>());
        mockCreditCardResponse.setData(mockCreditCardData);
        return mockCreditCardResponse;

    }

    public static ResponseInstantLoanGetEligibleProduct mockEligibleProductInstantFacility(InstantFacility... f) {
        ResponseInstantLoanGetEligibleProduct mockEligibleProductResponse = new ResponseInstantLoanGetEligibleProduct();
        com.tmb.common.model.legacy.rsl.ws.instant.eligible.product.response.Body mockBody = new com.tmb.common.model.legacy.rsl.ws.instant.eligible.product.response.Body();
        mockBody.setInstantFacility(f);
        mockEligibleProductResponse.setBody(mockBody);
        Header header = new Header();
        header.setResponseCode("MSG_000");
        header.setResponseDescriptionEN("Success");
        mockEligibleProductResponse.setHeader(header);
        return mockEligibleProductResponse;
    }

    public static ResponseInstantLoanGetEligibleProduct mockEligibleProductInstantCreditCard(InstantCreditCard... c) {
        ResponseInstantLoanGetEligibleProduct mockEligibleProductResponse = new ResponseInstantLoanGetEligibleProduct();
        com.tmb.common.model.legacy.rsl.ws.instant.eligible.product.response.Body mockBody = new com.tmb.common.model.legacy.rsl.ws.instant.eligible.product.response.Body();
        mockBody.setInstantCreditCard(c);
        mockEligibleProductResponse.setBody(mockBody);
        Header header = new Header();
        header.setResponseCode("MSG_000");
        header.setResponseDescriptionEN("Success");
        mockEligibleProductResponse.setHeader(header);
        return mockEligibleProductResponse;
    }


    public static TmbOneServiceResponse<LoanSummary> mockAccountLoanResponse(LoanAccount... loanAccounts) {
        TmbOneServiceResponse<LoanSummary> oneServiceResponse = new TmbOneServiceResponse<>();
        TmbStatus tmbStatus = new TmbStatus();
        tmbStatus.setCode(ResponseCode.SUCCESS.getCode());
        oneServiceResponse.setStatus(tmbStatus);
        LoanSummary loanSummary = new LoanSummary();
        loanSummary.setLoanAccounts(new ArrayList<>(Arrays.asList(loanAccounts)));
        oneServiceResponse.setData(loanSummary);
        return oneServiceResponse;
    }

    public static TmbOneServiceResponse<AccountSaving> mockLoanAccountAccountListResponse(LoanAccount... loanAccountSaving) {
        TmbOneServiceResponse<AccountSaving> oneServiceResponse = new TmbOneServiceResponse<>();
        TmbStatus tmbStatus = new TmbStatus();
        tmbStatus.setCode(ResponseCode.SUCCESS.getCode());
        oneServiceResponse.setStatus(tmbStatus);
        AccountSaving accountSaving = new AccountSaving();
        accountSaving.setLoanAccounts(new ArrayList<>(Arrays.asList(loanAccountSaving)));
        oneServiceResponse.setData(accountSaving);
        return oneServiceResponse;
    }

    public static TmbOneServiceResponse<CreditCardResponse> mockOneAppCreditCardErrorResponse() {
        TmbOneServiceResponse<CreditCardResponse> mockCreditCardResponse = new TmbOneServiceResponse<>();
        TmbStatus tmbStatus = new TmbStatus();
        tmbStatus.setCode(ResponseCode.FAILED.getCode());
        mockCreditCardResponse.setStatus(tmbStatus);
        return mockCreditCardResponse;

    }
}
