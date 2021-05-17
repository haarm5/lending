package com.tmb.oneapp.lendingservice.controller;


import com.tmb.common.logger.LogAround;
import com.tmb.common.logger.TMBLogger;
import com.tmb.common.model.TmbOneServiceResponse;
import com.tmb.common.model.TmbStatus;
import com.tmb.oneapp.lendingservice.constant.LendingServiceConstant;
import com.tmb.oneapp.lendingservice.constant.ResponseCode;
import com.tmb.oneapp.lendingservice.model.instantloancreation.InstantLoanCreationRequest;
import com.tmb.oneapp.lendingservice.model.instantloancreation.InstantLoanCreationResponse;
import com.tmb.oneapp.lendingservice.service.InstantLoanCreateApplicationService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.StringUtils;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.time.Instant;

/**
 * Controller to call Instant Loan Creation
 *
 */
@Api(tags = "Loan Submission- Instant Loan Create Application")
@RestController
public class InstantLoanCreateApplicationController {
    private static final TMBLogger<InstantLoanCreateApplicationController> logger = new TMBLogger<>(InstantLoanCreateApplicationController.class);
    private final InstantLoanCreateApplicationService instantLoanCreateApplicationService;


    /**
     * Constructor
     *
     * @param instantLoanCreateApplicationService
     */
    public InstantLoanCreateApplicationController(InstantLoanCreateApplicationService instantLoanCreateApplicationService) {
        this.instantLoanCreateApplicationService = instantLoanCreateApplicationService;
    }

    /**
     * method  to call InstantLoanCreateApplication service for Credit cards, Flasg crds, C2G
     *
     * @param request InstantLoanCreationRequest
     *
     * @return TmbOneServiceResponse<InstantLoanCreationResponse>
     */

    @ApiOperation(value = "Create Instant Loan Application")
    @LogAround
    @PostMapping("/create-instant-loan-application")
    public ResponseEntity<TmbOneServiceResponse<InstantLoanCreationResponse>> createInstantLoanApplication(@Valid @RequestBody InstantLoanCreationRequest request){
        logger.info("Calling createInstantLoanApplication ");
        HttpHeaders responseHeaders = new HttpHeaders();
        responseHeaders.set(LendingServiceConstant.HEADER_TIMESTAMP, String.valueOf(Instant.now().toEpochMilli()));
        TmbOneServiceResponse<InstantLoanCreationResponse> oneServiceResponse = new TmbOneServiceResponse<>();


        boolean isRequestValid = false;


        /**
         * List<AddressInfo> addressInfoList = request.getAddresses();
        Stream<Boolean> isvalid =  addressInfoList.stream().map(address -> address.isValid()) ;
        */
        if(request.getCustomerInfo().isValid())
        {
            isRequestValid = request.getLoanType().equalsIgnoreCase("CC") ? request.getCreditCards().get(0).isValid() : request.getFlashCardOrC2G().get(0).isValid();

        }


        if(!isRequestValid){
            oneServiceResponse.setData(null);
            oneServiceResponse
                    .setStatus(new TmbStatus(ResponseCode.BAD_REQUEST.getCode(), ResponseCode.BAD_REQUEST.getMessage(),
                            ResponseCode.BAD_REQUEST.getService(), ResponseCode.BAD_REQUEST.getDesc()));
            return ResponseEntity.badRequest().headers(responseHeaders).body(oneServiceResponse);

        }

         InstantLoanCreationResponse response = instantLoanCreateApplicationService.createInstantLoanApplication(request);

          if(StringUtils.isNotBlank(response.getError())){
              oneServiceResponse.setData(null);
              oneServiceResponse
                      .setStatus(new TmbStatus(ResponseCode.FAILED.getCode(), ResponseCode.FAILED.getMessage(),
                              ResponseCode.FAILED.getService(), ResponseCode.FAILED.getDesc()));
              return ResponseEntity.badRequest().headers(responseHeaders).body(oneServiceResponse);

          }
            oneServiceResponse.setData(response);
            oneServiceResponse
                    .setStatus(new TmbStatus(ResponseCode.SUCCESS.getCode(), ResponseCode.SUCCESS.getMessage(),
                            ResponseCode.SUCCESS.getService(), ResponseCode.SUCCESS.getDesc()));
            return  ResponseEntity.ok().headers(responseHeaders).body(oneServiceResponse);

    }

}
