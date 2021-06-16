package com.tmb.oneapp.lendingservice.controller;

import com.tmb.common.exception.model.TMBCommonException;
import com.tmb.common.logger.LogAround;
import com.tmb.common.logger.TMBLogger;
import com.tmb.common.model.TmbOneServiceResponse;
import com.tmb.common.model.TmbStatus;
import com.tmb.oneapp.lendingservice.constant.LendingServiceConstant;
import com.tmb.oneapp.lendingservice.constant.ResponseCode;
import com.tmb.oneapp.lendingservice.model.ServiceResponse;
import com.tmb.oneapp.lendingservice.model.loan.ProductRequest;
import com.tmb.oneapp.lendingservice.service.LoanService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * Provides loan services
 */
@RequestMapping("/loan")
@RestController
@Api(tags = "Loan Controller")
public class LoanController {
    private static final TMBLogger<LoanController> logger = new TMBLogger<>(LoanController.class);
    private final LoanService loanService;

    public LoanController(LoanService loanService) {
        this.loanService = loanService;
    }

    /**
     * Get Flexi Loan Products
     * @param reqHeaders - CRM-ID
     * @return
     * @throws TMBCommonException
     */
    @ApiOperation(value = "Get Flexi Loan Products")
    @LogAround
    @GetMapping(value = "/products")
    public ResponseEntity<TmbOneServiceResponse<Object>> fetchProducts(@RequestHeader Map<String, String> reqHeaders) throws TMBCommonException {
        String xCorrelationId = reqHeaders.get(LendingServiceConstant.HEADER_CORRELATION_ID);
        String crmId = reqHeaders.get(LendingServiceConstant.HEADER_X_CRMID);
        if(crmId==null){
            logger.error("no x-crm-id");
            throw new TMBCommonException("0001","failed",ResponseCode.BAD_REQUEST.getService(), HttpStatus.BAD_REQUEST,null);
        }
        logger.info("enter /loan/products X-Correlation-ID: {}",xCorrelationId);
        HttpHeaders responseHeaders = new HttpHeaders();
        TmbOneServiceResponse<Object> oneServiceResponse = new TmbOneServiceResponse<>();
        ProductRequest request = new ProductRequest();
        request.setCrmId(crmId);
        ServiceResponse serviceResponse = loanService.fetchProducts(request);
        if (serviceResponse.getError() == null) {
            oneServiceResponse.setStatus(new TmbStatus(ResponseCode.SUCCESS.getCode(), ResponseCode.SUCCESS.getMessage(),
                    ResponseCode.SUCCESS.getService(), ResponseCode.SUCCESS.getDesc()));
            oneServiceResponse.setData(serviceResponse.getData());
            return ResponseEntity.ok().headers(responseHeaders).body(oneServiceResponse);
        }
        throw new TMBCommonException("0001",serviceResponse.getError().getErrorMessage(),ResponseCode.BAD_REQUEST.getService(), HttpStatus.BAD_REQUEST,null);
    }

}
