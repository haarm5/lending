package com.tmb.oneapp.lendingservice.controller;

import com.tmb.common.exception.model.TMBCommonException;
import com.tmb.common.logger.TMBLogger;
import com.tmb.common.model.TmbOneServiceResponse;
import com.tmb.common.model.TmbStatus;
import com.tmb.oneapp.lendingservice.constant.ResponseCode;
import com.tmb.oneapp.lendingservice.model.ServiceResponse;
import com.tmb.oneapp.lendingservice.model.loan.ProductRequest;
import com.tmb.oneapp.lendingservice.service.LoanService;
import io.swagger.annotations.Api;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 *
 */
@RequestMapping("/internal/loan")
@RestController
@Api(tags = "Loan Controller")
public class LoanController {
    private static final TMBLogger<LoanController> logger = new TMBLogger<>(LoanController.class);
    private final LoanService loanService;

    public LoanController(LoanService loanService) {
        this.loanService = loanService;
    }

    @PostMapping(value = "/products")
    public ResponseEntity<TmbOneServiceResponse<Object>> fetchProducts(@RequestHeader("X-Correlation-ID") String xCorrelationId, @RequestBody ProductRequest request) throws TMBCommonException {
        logger.info("enter /internal/loan/products X-Correlation-ID: {}",xCorrelationId);
        HttpHeaders responseHeaders = new HttpHeaders();
        TmbOneServiceResponse<Object> oneServiceResponse = new TmbOneServiceResponse<>();
        ServiceResponse serviceResponse = loanService.fetchProducts(request);
        if (serviceResponse.getError() == null) {
            oneServiceResponse.setStatus(new TmbStatus(ResponseCode.SUCCESS.getCode(), ResponseCode.SUCCESS.getMessage(),
                    ResponseCode.SUCCESS.getService(), ResponseCode.SUCCESS.getDesc()));
            oneServiceResponse.setData(serviceResponse.getData());
            return ResponseEntity.ok().headers(responseHeaders).body(oneServiceResponse);
        }
        throw new TMBCommonException("Service Error");
    }

}
