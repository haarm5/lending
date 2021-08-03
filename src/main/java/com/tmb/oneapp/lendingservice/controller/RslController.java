package com.tmb.oneapp.lendingservice.controller;

import com.tmb.common.exception.model.TMBCommonException;
import com.tmb.common.logger.LogAround;
import com.tmb.common.model.TmbOneServiceResponse;
import com.tmb.common.model.TmbStatus;
import com.tmb.common.model.legacy.rsl.common.ob.facility.Facility;
import com.tmb.common.model.legacy.rsl.ws.application.response.ResponseApplication;
import com.tmb.common.model.legacy.rsl.ws.creditcard.response.ResponseCreditcard;
import com.tmb.common.model.legacy.rsl.ws.dropdown.response.ResponseDropdown;
import com.tmb.common.model.legacy.rsl.ws.facility.response.ResponseFacility;
import com.tmb.common.model.legacy.rsl.ws.individual.response.ResponseIndividual;
import com.tmb.common.model.legacy.rsl.ws.instant.calculate.uw.response.ResponseInstantLoanCalUW;
import com.tmb.common.model.legacy.rsl.ws.instant.eligible.customer.response.ResponseInstantLoanGetCustInfo;
import com.tmb.common.model.legacy.rsl.ws.instant.submit.response.ResponseInstantLoanSubmit;
import com.tmb.common.util.TMBUtils;
import com.tmb.oneapp.lendingservice.constant.LendingServiceConstant;
import com.tmb.oneapp.lendingservice.constant.ResponseCode;
import com.tmb.oneapp.lendingservice.model.rsl.*;
import com.tmb.oneapp.lendingservice.service.RslService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
@Api(tags = "RSL")
@RequestMapping("/rsl")
public class RslController {

    private final RslService rslService;

    @ApiOperation("Loan Submission Get Application Info")
    @PostMapping(value = "/LoanSubmissionGetApplicationInfo", produces = MediaType.APPLICATION_JSON_VALUE)
    @LogAround
    public ResponseEntity<TmbOneServiceResponse<ResponseApplication>> loanSubmissionGetApplicationInfo(
            @ApiParam(value = LendingServiceConstant.HEADER_CORRELATION_ID, defaultValue = "32fbd3b2-3f97-4a89-ar39-b4f628fbc8da", required = true)
            @Valid @RequestHeader(LendingServiceConstant.HEADER_CORRELATION_ID) String correlationId,
            @ApiParam(value = LendingServiceConstant.HEADER_X_CRMID, defaultValue = "001100000000000000000018593707", required = true)
            @Valid @RequestHeader(LendingServiceConstant.HEADER_X_CRMID) String crmId,
            @Valid @RequestBody LoanSubmissionGetApplicationInfoRequest request
    ) throws TMBCommonException {
        TmbOneServiceResponse<ResponseApplication> response = new TmbOneServiceResponse<>();

        try {
            ResponseApplication getApplicationInfoResponse = rslService.getLoanSubmissionApplicationInfo(request);
            response.setData(getApplicationInfoResponse);
            response.setStatus(new TmbStatus(ResponseCode.SUCCESS.getCode(),
                    ResponseCode.SUCCESS.getMessage(), ResponseCode.SUCCESS.getService(), ResponseCode.SUCCESS.getDesc()));

            return ResponseEntity.ok()
                    .headers(TMBUtils.getResponseHeaders())
                    .body(response);

        } catch (TMBCommonException e) {
            throw e;
        } catch (Exception e) {
            throw new TMBCommonException(ResponseCode.FAILED.getCode(), ResponseCode.FAILED.getMessage(), ResponseCode.FAILED.getService(), HttpStatus.INTERNAL_SERVER_ERROR, e);
        }
    }

    @ApiOperation("Loan Submission Get Creditcard Info")
    @PostMapping(value = "/LoanSubmissionGetCreditcardInfo", produces = MediaType.APPLICATION_JSON_VALUE)
    @LogAround
    public ResponseEntity<TmbOneServiceResponse<ResponseCreditcard>> loanSubmissionGetCreditcardInfo(
            @ApiParam(value = LendingServiceConstant.HEADER_CORRELATION_ID, defaultValue = "32fbd3b2-3f97-4a89-ar39-b4f628fbc8da", required = true)
            @Valid @RequestHeader(LendingServiceConstant.HEADER_CORRELATION_ID) String correlationId,
            @ApiParam(value = LendingServiceConstant.HEADER_X_CRMID, defaultValue = "001100000000000000000018593707", required = true)
            @Valid @RequestHeader(LendingServiceConstant.HEADER_X_CRMID) String crmId,
            @Valid @RequestBody LoanSubmissionGetCreditcardInfoRequest request
    ) throws TMBCommonException {
        TmbOneServiceResponse<ResponseCreditcard> response = new TmbOneServiceResponse<>();

        try {
            ResponseCreditcard getCreditCardInfoResponse = rslService.getLoanSubmissionCreditCardInfo(request);
            response.setData(getCreditCardInfoResponse);
            response.setStatus(new TmbStatus(ResponseCode.SUCCESS.getCode(),
                    ResponseCode.SUCCESS.getMessage(), ResponseCode.SUCCESS.getService(), ResponseCode.SUCCESS.getDesc()));

            return ResponseEntity.ok()
                    .headers(TMBUtils.getResponseHeaders())
                    .body(response);

        } catch (TMBCommonException e) {
            throw e;
        } catch (Exception e) {
            throw new TMBCommonException(ResponseCode.FAILED.getCode(), ResponseCode.FAILED.getMessage(), ResponseCode.FAILED.getService(), HttpStatus.INTERNAL_SERVER_ERROR, e);
        }
    }

    @ApiOperation("Loan Submission Get Customer Info")
    @PostMapping(value = "/LoanSubmissionGetCustomerInfo", produces = MediaType.APPLICATION_JSON_VALUE)
    @LogAround
    public ResponseEntity<TmbOneServiceResponse<ResponseIndividual>> loanSubmissionGetCustomerInfo(
            @ApiParam(value = LendingServiceConstant.HEADER_CORRELATION_ID, defaultValue = "32fbd3b2-3f97-4a89-ar39-b4f628fbc8da", required = true)
            @Valid @RequestHeader(LendingServiceConstant.HEADER_CORRELATION_ID) String correlationId,
            @ApiParam(value = LendingServiceConstant.HEADER_X_CRMID, defaultValue = "001100000000000000000018593707", required = true)
            @Valid @RequestHeader(LendingServiceConstant.HEADER_X_CRMID) String crmId,
            @Valid @RequestBody LoanSubmissionGetCustomerInfoRequest request
    ) throws TMBCommonException {
        TmbOneServiceResponse<ResponseIndividual> response = new TmbOneServiceResponse<>();

        try {
            ResponseIndividual individualResponse = rslService.getLoanSubmissionCustomerInfo(request);
            response.setData(individualResponse);
            response.setStatus(new TmbStatus(ResponseCode.SUCCESS.getCode(),
                    ResponseCode.SUCCESS.getMessage(), ResponseCode.SUCCESS.getService(), ResponseCode.SUCCESS.getDesc()));

            return ResponseEntity.ok()
                    .headers(TMBUtils.getResponseHeaders())
                    .body(response);

        } catch (TMBCommonException e) {
            throw e;
        } catch (Exception e) {
            throw new TMBCommonException(ResponseCode.FAILED.getCode(), ResponseCode.FAILED.getMessage(), ResponseCode.FAILED.getService(), HttpStatus.INTERNAL_SERVER_ERROR, e);
        }
    }

    @ApiOperation("Loan Submission Get Dropdown List")
    @PostMapping(value = "/LoanSubmissionGetDropdownList", produces = MediaType.APPLICATION_JSON_VALUE)
    @LogAround
    public ResponseEntity<TmbOneServiceResponse<ResponseDropdown>> loanSubmissionGetDropdownList(
            @ApiParam(value = LendingServiceConstant.HEADER_CORRELATION_ID, defaultValue = "32fbd3b2-3f97-4a89-ar39-b4f628fbc8da", required = true)
            @Valid @RequestHeader(LendingServiceConstant.HEADER_CORRELATION_ID) String correlationId,
            @ApiParam(value = LendingServiceConstant.HEADER_X_CRMID, defaultValue = "001100000000000000000018593707", required = true)
            @Valid @RequestHeader(LendingServiceConstant.HEADER_X_CRMID) String crmId,
            @Valid @RequestBody LoanSubmissionGetDropdownListRequest request
    ) throws TMBCommonException {
        TmbOneServiceResponse<ResponseDropdown> response = new TmbOneServiceResponse<>();

        try {
            ResponseDropdown getDropdownListResponse = rslService.getLoanSubmissionDropdownList(request);
            response.setData(getDropdownListResponse);
            response.setStatus(new TmbStatus(ResponseCode.SUCCESS.getCode(),
                    ResponseCode.SUCCESS.getMessage(), ResponseCode.SUCCESS.getService(), ResponseCode.SUCCESS.getDesc()));

            return ResponseEntity.ok()
                    .headers(TMBUtils.getResponseHeaders())
                    .body(response);

        } catch (TMBCommonException e) {
            throw e;
        } catch (Exception e) {
            throw new TMBCommonException(ResponseCode.FAILED.getCode(), ResponseCode.FAILED.getMessage(), ResponseCode.FAILED.getService(), HttpStatus.INTERNAL_SERVER_ERROR, e);
        }
    }

    @ApiOperation("Loan Submission Get Facility Info")
    @PostMapping(value = "/LoanSubmissionGetFacilityInfo", produces = MediaType.APPLICATION_JSON_VALUE)
    @LogAround
    public ResponseEntity<TmbOneServiceResponse<ResponseFacility>> loanSubmissionGetFacilityInfo(
            @ApiParam(value = LendingServiceConstant.HEADER_CORRELATION_ID, defaultValue = "32fbd3b2-3f97-4a89-ar39-b4f628fbc8da", required = true)
            @Valid @RequestHeader(LendingServiceConstant.HEADER_CORRELATION_ID) String correlationId,
            @ApiParam(value = LendingServiceConstant.HEADER_X_CRMID, defaultValue = "001100000000000000000018593707", required = true)
            @Valid @RequestHeader(LendingServiceConstant.HEADER_X_CRMID) String crmId,
            @Valid @RequestBody LoanSubmissionGetFacilityInfoRequest request
    ) throws TMBCommonException {
        TmbOneServiceResponse<ResponseFacility> response = new TmbOneServiceResponse<>();

        try {
            ResponseFacility getFacilityInfoResponse = rslService.getLoanSubmissionFacilityInfo(request);
            response.setData(getFacilityInfoResponse);
            response.setStatus(new TmbStatus(ResponseCode.SUCCESS.getCode(),
                    ResponseCode.SUCCESS.getMessage(), ResponseCode.SUCCESS.getService(), ResponseCode.SUCCESS.getDesc()));

            return ResponseEntity.ok()
                    .headers(TMBUtils.getResponseHeaders())
                    .body(response);

        } catch (TMBCommonException e) {
            throw e;
        } catch (Exception e) {
            throw new TMBCommonException(ResponseCode.FAILED.getCode(), ResponseCode.FAILED.getMessage(), ResponseCode.FAILED.getService(), HttpStatus.INTERNAL_SERVER_ERROR, e);
        }
    }

    @ApiOperation("Loan Submission Instant Loan CalUW")
    @PostMapping(value = "/LoanSubmissionInstantLoanCalUW", produces = MediaType.APPLICATION_JSON_VALUE)
    @LogAround
    public ResponseEntity<TmbOneServiceResponse<ResponseInstantLoanCalUW>> loanSubmissionInstantLoanCalUW(
            @ApiParam(value = LendingServiceConstant.HEADER_CORRELATION_ID, defaultValue = "32fbd3b2-3f97-4a89-ar39-b4f628fbc8da", required = true)
            @Valid @RequestHeader(LendingServiceConstant.HEADER_CORRELATION_ID) String correlationId,
            @ApiParam(value = LendingServiceConstant.HEADER_X_CRMID, defaultValue = "001100000000000000000018593707", required = true)
            @Valid @RequestHeader(LendingServiceConstant.HEADER_X_CRMID) String crmId,
            @Valid @RequestBody LoanSubmissionInstantLoanCalUWRequest request
    ) throws TMBCommonException {
        TmbOneServiceResponse<ResponseInstantLoanCalUW> response = new TmbOneServiceResponse<>();

        try {
            ResponseInstantLoanCalUW loanCalUWResponse = rslService.getLoanSubmissionInstantLoanCalUW(request);
            response.setData(loanCalUWResponse);
            response.setStatus(new TmbStatus(ResponseCode.SUCCESS.getCode(),
                    ResponseCode.SUCCESS.getMessage(), ResponseCode.SUCCESS.getService(), ResponseCode.SUCCESS.getDesc()));

            return ResponseEntity.ok()
                    .headers(TMBUtils.getResponseHeaders())
                    .body(response);

        } catch (TMBCommonException e) {
            throw e;
        } catch (Exception e) {
            throw new TMBCommonException(ResponseCode.FAILED.getCode(), ResponseCode.FAILED.getMessage(), ResponseCode.FAILED.getService(), HttpStatus.INTERNAL_SERVER_ERROR, e);
        }
    }

    @ApiOperation("Loan Submission Instant Loan Get Customer Info")
    @PostMapping(value = "/LoanSubmissionInstantLoanGetCustomerInfo", produces = MediaType.APPLICATION_JSON_VALUE)
    @LogAround

    public ResponseEntity<TmbOneServiceResponse<ResponseInstantLoanGetCustInfo>> loanSubmissionInstantLoanGetCustomerInfo(
            @ApiParam(value = LendingServiceConstant.HEADER_CORRELATION_ID, defaultValue = "32fbd3b2-3f97-4a89-ar39-b4f628fbc8da", required = true)
            @Valid @RequestHeader(LendingServiceConstant.HEADER_CORRELATION_ID) String correlationId,
            @ApiParam(value = LendingServiceConstant.HEADER_X_CRMID, defaultValue = "001100000000000000000018593707", required = true)
            @Valid @RequestHeader(LendingServiceConstant.HEADER_X_CRMID) String crmId
    ) throws TMBCommonException {
        TmbOneServiceResponse<ResponseInstantLoanGetCustInfo> response = new TmbOneServiceResponse<>();

        try {
            ResponseInstantLoanGetCustInfo getCustomerInfoResponse = rslService.getSubmissionInstantLoanCustomerInfo(crmId);
            response.setData(getCustomerInfoResponse);
            response.setStatus(new TmbStatus(ResponseCode.SUCCESS.getCode(),
                    ResponseCode.SUCCESS.getMessage(), ResponseCode.SUCCESS.getService(), ResponseCode.SUCCESS.getDesc()));

            return ResponseEntity.ok()
                    .headers(TMBUtils.getResponseHeaders())
                    .body(response);

        } catch (TMBCommonException e) {
            throw e;
        } catch (Exception e) {
            throw new TMBCommonException(ResponseCode.FAILED.getCode(), ResponseCode.FAILED.getMessage(), ResponseCode.FAILED.getService(), HttpStatus.INTERNAL_SERVER_ERROR, e);
        }
    }

    @ApiOperation("Loan Submission Instant Loan Submit Application")
    @PostMapping(value = "/LoanSubmissionInstantLoanSubmitApplication", produces = MediaType.APPLICATION_JSON_VALUE)
    @LogAround
    public ResponseEntity<TmbOneServiceResponse<ResponseInstantLoanSubmit>> loanSubmissionInstantLoanSubmitApplication(
            @ApiParam(value = LendingServiceConstant.HEADER_CORRELATION_ID, defaultValue = "32fbd3b2-3f97-4a89-ar39-b4f628fbc8da", required = true)
            @Valid @RequestHeader(LendingServiceConstant.HEADER_CORRELATION_ID) String correlationId,
            @ApiParam(value = LendingServiceConstant.HEADER_X_CRMID, defaultValue = "001100000000000000000018593707", required = true)
            @Valid @RequestHeader(LendingServiceConstant.HEADER_X_CRMID) String crmId,
            @Valid @RequestBody LoanSubmissionInstantLoanSubmitApplicationRequest request
    ) throws TMBCommonException {
        TmbOneServiceResponse<ResponseInstantLoanSubmit> response = new TmbOneServiceResponse<>();

        try {
            ResponseInstantLoanSubmit submitApplicationResponse = rslService.submitInstantLoanApplication(request);
            response.setData(submitApplicationResponse);
            response.setStatus(new TmbStatus(ResponseCode.SUCCESS.getCode(),
                    ResponseCode.SUCCESS.getMessage(), ResponseCode.SUCCESS.getService(), ResponseCode.SUCCESS.getDesc()));

            return ResponseEntity.ok()
                    .headers(TMBUtils.getResponseHeaders())
                    .body(response);

        } catch (TMBCommonException e) {
            throw e;
        } catch (Exception e) {
            throw new TMBCommonException(ResponseCode.FAILED.getCode(), ResponseCode.FAILED.getMessage(), ResponseCode.FAILED.getService(), HttpStatus.INTERNAL_SERVER_ERROR, e);
        }
    }

    @ApiOperation("Loan Submission Update Facility")
    @PostMapping(value = "/LoanSubmissionUpdateFacility", produces = MediaType.APPLICATION_JSON_VALUE)
    @LogAround
    public ResponseEntity<TmbOneServiceResponse<com.tmb.common.model.legacy.rsl.ws.facility.update.response.ResponseFacility>> loanSubmissionUpdateFacility(
            @ApiParam(value = LendingServiceConstant.HEADER_CORRELATION_ID, defaultValue = "32fbd3b2-3f97-4a89-ar39-b4f628fbc8da", required = true)
            @Valid @RequestHeader(LendingServiceConstant.HEADER_CORRELATION_ID) String correlationId,
            @ApiParam(value = LendingServiceConstant.HEADER_X_CRMID, defaultValue = "001100000000000000000018593707", required = true)
            @Valid @RequestHeader(LendingServiceConstant.HEADER_X_CRMID) String crmId,
            @Valid @RequestBody Facility request
    ) throws TMBCommonException {
        TmbOneServiceResponse<com.tmb.common.model.legacy.rsl.ws.facility.update.response.ResponseFacility> response = new TmbOneServiceResponse<>();

        try {
            com.tmb.common.model.legacy.rsl.ws.facility.update.response.ResponseFacility updateFacilityResponse = rslService.updateFacilityInfo(request);
            response.setData(updateFacilityResponse);
            response.setStatus(new TmbStatus(ResponseCode.SUCCESS.getCode(),
                    ResponseCode.SUCCESS.getMessage(), ResponseCode.SUCCESS.getService(), ResponseCode.SUCCESS.getDesc()));

            return ResponseEntity.ok()
                    .headers(TMBUtils.getResponseHeaders())
                    .body(response);

        } catch (TMBCommonException e) {
            throw e;
        } catch (Exception e) {
            throw new TMBCommonException(ResponseCode.FAILED.getCode(), ResponseCode.FAILED.getMessage(), ResponseCode.FAILED.getService(), HttpStatus.INTERNAL_SERVER_ERROR, e);
        }
    }

}
