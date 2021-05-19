package com.tmb.oneapp.lendingservice.controller;

import com.tmb.common.exception.model.TMBCommonException;
import com.tmb.common.logger.LogAround;
import com.tmb.common.logger.TMBLogger;
import com.tmb.common.model.TmbOneServiceResponse;
import com.tmb.common.model.TmbStatus;
import com.tmb.oneapp.lendingservice.constant.ResponseCode;
import com.tmb.oneapp.lendingservice.model.ServiceResponse;
import com.tmb.oneapp.lendingservice.model.info.MasterDataRequest;
import com.tmb.oneapp.lendingservice.service.InfoService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * Provides Lending Information such as Master Data
 */
@RequestMapping("/info")
@RestController
@Api(tags = "Master Data Controller")
public class LendingInfoController {
    private static final TMBLogger<LendingInfoController> logger = new TMBLogger<>(LendingInfoController.class);
    private final InfoService infoService;

    public LendingInfoController(InfoService infoService) {
        this.infoService = infoService;
    }

    /**
     * Get Flexi Loan Products
     *
     * @param xCorrelationId
     * @param request        - Category Codes
     * @return
     * @throws TMBCommonException
     */
    @ApiOperation(value = "Get Master Data")
    @LogAround
    @PostMapping(value = "/master-data")
    public ResponseEntity<TmbOneServiceResponse<Object>> fetchMaster(@RequestHeader("X-Correlation-ID") String xCorrelationId, @RequestBody MasterDataRequest request) throws TMBCommonException {
        logger.info("enter /info/master-data X-Correlation-ID: {}", xCorrelationId);
        HttpHeaders responseHeaders = new HttpHeaders();
        TmbOneServiceResponse<Object> oneServiceResponse = new TmbOneServiceResponse<>();
        ServiceResponse serviceResponse = infoService.fetchMasterData(request.getCategoryCodes());
        if (serviceResponse.getError() == null) {
            oneServiceResponse.setStatus(new TmbStatus(ResponseCode.SUCCESS.getCode(), ResponseCode.SUCCESS.getMessage(),
                    ResponseCode.SUCCESS.getService(), ResponseCode.SUCCESS.getDesc()));
            oneServiceResponse.setData(serviceResponse.getData());
            return ResponseEntity.ok().headers(responseHeaders).body(oneServiceResponse);
        }
        throw new TMBCommonException("0001", serviceResponse.getError().getErrorMessage(), ResponseCode.BAD_REQUEST.getService(), HttpStatus.BAD_REQUEST, null);


    }


}
