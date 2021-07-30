package com.tmb.oneapp.lendingservice.service;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.tmb.common.exception.model.TMBCommonException;
import com.tmb.common.logger.TMBLogger;
import com.tmb.common.model.CustGeneralProfileResponse;
import com.tmb.common.model.TmbOneServiceResponse;
import com.tmb.common.model.legacy.rsl.common.ob.application.Application;
import com.tmb.common.model.legacy.rsl.common.ob.facility.Facility;
import com.tmb.common.model.legacy.rsl.common.ob.individual.Individual;
import com.tmb.common.model.legacy.rsl.ws.application.save.response.ResponseApplication;
import com.tmb.common.model.legacy.rsl.ws.incomemodel.response.ResponseIncomeModel;
import com.tmb.oneapp.lendingservice.client.CustomerServiceClient;
import com.tmb.oneapp.lendingservice.client.LoanSubmissionCreateApplicationClient;
import com.tmb.common.model.legacy.rsl.common.ob.creditcard.CreditCard;
import com.tmb.oneapp.lendingservice.client.LoanSubmissionGetIncomeModelInfoClient;
import com.tmb.oneapp.lendingservice.constant.ResponseCode;
import com.tmb.oneapp.lendingservice.model.loanonline.LoanSubmissionCreateApplicationReq;
import lombok.AllArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import javax.xml.rpc.ServiceException;
import java.rmi.RemoteException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Objects;

@Service
@AllArgsConstructor
public class LoanSubmissionCreateApplicationService {
    private static final TMBLogger<LoanSubmissionCreateApplicationService> logger = new TMBLogger<>(LoanSubmissionCreateApplicationService.class);
    private final LoanSubmissionCreateApplicationClient loanSubmissionCreateApplicationClient;
    private final LoanSubmissionGetIncomeModelInfoClient incomeModelInfoClient;
    private final CustomerServiceClient customerServiceClient;


    public ResponseApplication createApplication(LoanSubmissionCreateApplicationReq request, String rmId) throws Exception {

        try {
            var application = new Application();
            application = prepareData(application, request, rmId);
            ResponseApplication res = loanSubmissionCreateApplicationClient.createApplication(application);
            if (res.getHeader().getResponseCode().equals("MSG_000")) {
                return res;
            } else {
                throw new TMBCommonException(res.getHeader().getResponseCode(),
                        res.getHeader().getResponseDescriptionEN(),
                        ResponseCode.FAILED.getService(), HttpStatus.NOT_FOUND, null);
            }
        } catch (Exception e) {
            logger.error("create app soap error", e);
            throw e;
        }
    }


    private Application prepareData(Application application, LoanSubmissionCreateApplicationReq req, String rmId) throws Exception {
        mapIndividual(application, req, rmId);
        String productCode = req.getProductCode();
        boolean isTypeCC = productCode.equals("VM") || productCode.equals("VC")
                || productCode.equals("VG") || productCode.equals("VP")
                || productCode.equals("VT") || productCode.equals("MT")
                || productCode.equals("MS");
        if (isTypeCC) {
            application.setAppType("CC");
            application.setNatureOfRequest(waiveDocIsAlready(rmId) ? "04" : "03");
            mapDataTypeCC(application, req);
        } else {
            application.setAppType("PL");
            application.setNatureOfRequest(waiveDocIsAlready(rmId) ? "12" : "11");
            mapDataTypeNonCC(application, req);
        }
        application.setBranchCode("026");
        application.setBookBranchCode("026");
        application.setSaleChannel("05");
        application.setAuthenCode("Access Pin");
        application.setNcbConsentFlag("Y");
        return application;
    }

    private Application mapIndividual(Application application, LoanSubmissionCreateApplicationReq req, String rmId) throws TMBCommonException, ParseException {
        CustGeneralProfileResponse customer = getCustomerEC(rmId);
        Individual[] individuals = new Individual[1];
        individuals[0] = new Individual();
        individuals[0].setCifRelCode("M");
        individuals[0].setIdType1(customer.getIdType());
        individuals[0].setIdNo1(customer.getIdNo());
        individuals[0].setHostCifNo(StringUtils.right(rmId, 14));
        individuals[0].setIdIssueCtry1(customer.getNationality());
        individuals[0].setThaiSalutationCode("-");
        individuals[0].setThaiName(customer.getThaFname());
        individuals[0].setThaiSurName(customer.getThaLname());
        individuals[0].setMobileNo(customer.getPhoneNoFull());

        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
        Date date = sdf.parse(customer.getIdBirthDate());
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        individuals[0].setBirthDate(cal);

        individuals[0].setEmploymentStatus(req.getEmploymentStatus());
        individuals[0].setIncomeBasicSalary(req.getIncomeBasicSalary());
        individuals[0].setInTotalIncome(req.getInTotalIncome());
        individuals[0].setIncomeOtherIncome(req.getIncomeOtherIncome());
        individuals[0].setEmploymentFinalTotalIncome(req.getEmploymentFinalTotalIncome());
        application.setIndividuals(individuals);
        return application;
    }

    private Application mapDataTypeCC(Application application, LoanSubmissionCreateApplicationReq req) {
        CreditCard[] creditCards = new CreditCard[1];
        creditCards[0] = new CreditCard();
        creditCards[0].setCardInd(req.getCardInd());
        creditCards[0].setProductType(req.getProductType());
        creditCards[0].setCardBrand(req.getCardBrand());

        creditCards[0].setCampaignCode(req.getCampaignCode());

        creditCards[0].setPaymentMethod(req.getPaymentMethod());
        creditCards[0].setDebitAccountNo(req.getDebitAccountNo());
        creditCards[0].setDebitAccountName(req.getDebitAccountName());
        creditCards[0].setPaymentCriteria(req.getPaymentCriteria());
        application.getIndividuals()[0].setCreditCards(creditCards);
        return application;
    }

    private Application mapDataTypeNonCC(Application application, LoanSubmissionCreateApplicationReq req) {
        Facility[] facilities = new Facility[1];
        facilities[0] = new Facility();
        facilities[0].setFacilityCode("RC");
        facilities[0].setProductCode("RC01");
        if (req.getProductCode().contains("C2G")) {
            facilities[0].setFacilityCode("C2G");
            facilities[0].setProductCode("C2G01");
        }

        facilities[0].setCaCampaignCode(req.getCampaignCode());

        facilities[0].setLimitApplied(req.getLimitApplied());
        facilities[0].setTenure(req.getTenure());
        facilities[0].setDisburstBankName(req.getDisburstBankName());
        facilities[0].setDisburstAccountNo(req.getDisburstAccountNo());
        facilities[0].setDisburstAccountName(req.getDisburstAccountName());
        facilities[0].setPaymentMethod(req.getPaymentMethod());
        facilities[0].setPaymentAccountNo(req.getPaymentAccountNo());
        facilities[0].setPaymentAccountName(req.getPaymentAccountName());
        facilities[0].setPayMethodCriteria(req.getPayMethodCriteria());
        facilities[0].setLoanWithOtherBank(req.getLoanWithOtherBank());
        facilities[0].setConsiderLoanWithOtherBank(req.getConsiderLoanWithOtherBank());
        application.setFacilities(facilities);
        return application;
    }

    private boolean waiveDocIsAlready(String rmId) throws ServiceException, RemoteException, JsonProcessingException {

        try {
            ResponseIncomeModel responseIncomeModel = incomeModelInfoClient.getIncomeInfo(StringUtils.right(rmId, 14));
            if (responseIncomeModel.getHeader().getResponseCode().equals("MSG_000")) {
                return Objects.nonNull(responseIncomeModel.getBody()) && Objects.nonNull(responseIncomeModel.getBody().getIncomeModelAmt());
            } else {
                return false;
            }
        } catch (Exception e) {
            loging("create app  check waive doc soap error", e);
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
            loging("create app get CustomerEC soap error", e);
            throw e;
        }
    }

    private void loging(String error, Exception e) {
        logger.error(error, e);
    }
}
