package com.tmb.oneapp.lendingservice.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.tmb.common.exception.model.TMBCommonException;
import com.tmb.common.logger.TMBLogger;
import com.tmb.common.model.legacy.rsl.ws.application.response.ResponseApplication;
import com.tmb.common.model.legacy.rsl.ws.individual.response.ResponseIndividual;
import com.tmb.common.model.legacy.rsl.ws.instant.eligible.product.response.ResponseInstantLoanGetEligibleProduct;
import com.tmb.common.model.legacy.rsl.ws.tracking.response.Header;
import com.tmb.common.model.legacy.rsl.ws.tracking.response.ResponseTracking;
import com.tmb.oneapp.lendingservice.constant.ResponseCode;
import org.springframework.http.HttpStatus;

/**
 * LoanServiceResponseParser provides functionality for parsing Soap response for lending services.
 */
public class LoanServiceResponseParser {
    private static final TMBLogger<LoanService> logger = new TMBLogger<>(LoanService.class);
    private static final String SUCCESS_CODE = "MSG_000";
    private static final String SUCCESS_DESC = "Success";

    private LoanServiceResponseParser(){}
    public static ResponseInstantLoanGetEligibleProduct parseEligibleProducts(ResponseInstantLoanGetEligibleProduct responseInstantLoanGetEligibleProduct) {

        com.tmb.common.model.legacy.rsl.ws.instant.eligible.product.response.Header header = responseInstantLoanGetEligibleProduct.getHeader();

        if (!SUCCESS_CODE.equalsIgnoreCase(header.getResponseCode()) || !SUCCESS_DESC.equalsIgnoreCase(header.getResponseDescriptionEN())) {
            logger.error("EligibleProducts got error:{}, {}", header.getResponseCode(), header.getResponseDescriptionEN());
            logError(responseInstantLoanGetEligibleProduct);
            return responseInstantLoanGetEligibleProduct;
        }
        return responseInstantLoanGetEligibleProduct;
    }

    /**
     * Parses LoanStatusTracking Response
     * @param responseTracking
     * @return
     * @throws TMBCommonException
     */
    public static ResponseTracking parseLoanStatusTracking(ResponseTracking responseTracking) throws TMBCommonException {
        Header header = responseTracking.getHeader();
        if (!SUCCESS_CODE.equalsIgnoreCase(header.getResponseCode()) || !SUCCESS_DESC.equalsIgnoreCase(header.getResponseDescriptionEN())) {
            logger.error("LoanStatusTracking got error:{}, {}", header.getResponseCode(), header.getResponseDescriptionEN());
            logError(responseTracking);
            throw new TMBCommonException(ResponseCode.FAILED.getCode(), ResponseCode.FAILED.getMessage(), ResponseCode.FAILED.getService(), HttpStatus.BAD_REQUEST, null);
        }
        return responseTracking;
    }

    /**
     * Parses ApplicationInfo response.
     * @param responseApplication
     * @return
     * @throws TMBCommonException
     */
    public static ResponseApplication parseApplicationInfo(ResponseApplication responseApplication) throws TMBCommonException {
        com.tmb.common.model.legacy.rsl.ws.application.response.Header header = responseApplication.getHeader();
        if (!SUCCESS_CODE.equalsIgnoreCase(header.getResponseCode()) || !SUCCESS_DESC.equalsIgnoreCase(header.getResponseDescriptionEN())) {
            logger.error("GetApplicationInfo got error:{}, {}", header.getResponseCode(), header.getResponseDescriptionEN());
            logError(responseApplication);
            throw new TMBCommonException(ResponseCode.FAILED.getCode(), ResponseCode.FAILED.getMessage(), ResponseCode.FAILED.getService(), HttpStatus.BAD_REQUEST, null);
        }
        return responseApplication;
    }

    /**
     * Parses CustomerInfo response.
     * @param responseIndividual
     * @return
     * @throws TMBCommonException
     */
    public static ResponseIndividual parseCustomerInfo(ResponseIndividual responseIndividual) throws TMBCommonException {
        com.tmb.common.model.legacy.rsl.ws.individual.response.Header header = responseIndividual.getHeader();
        if (!SUCCESS_CODE.equalsIgnoreCase(header.getResponseCode()) || !SUCCESS_DESC.equalsIgnoreCase(header.getResponseDescriptionEN())) {
            logger.error("GetCustomerInfo got error:{}, {}", header.getResponseCode(), header.getResponseDescriptionEN());
            logError(responseIndividual);
            throw new TMBCommonException(ResponseCode.FAILED.getCode(), ResponseCode.FAILED.getMessage(), ResponseCode.FAILED.getService(), HttpStatus.BAD_REQUEST, null);
        }
        return responseIndividual;
    }

    /**
     * Logs error from Soap services.
     * @param data
     */
    private static void  logError(Object data){
        try {
            logger.error("Soap error response:",new ObjectMapper().writerWithDefaultPrettyPrinter().writeValueAsString(data));
        } catch (JsonProcessingException e) {
            logger.error("Cannot write json");
        }
    }
}
