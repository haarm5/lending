package com.tmb.oneapp.lendingservice.controller;

import com.tmb.common.logger.LogAround;
import com.tmb.common.logger.TMBLogger;
import com.tmb.common.model.TmbOneServiceResponse;
import com.tmb.common.model.TmbStatus;
import com.tmb.common.util.TMBUtils;
import com.tmb.oneapp.lendingservice.constant.LendingServiceConstant;
import com.tmb.oneapp.lendingservice.constant.ResponseCode;
import com.tmb.oneapp.lendingservice.model.RslStatusTrackingResponse;
import com.tmb.oneapp.lendingservice.service.RslStatusTrackingService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;

/**
 * Controller class responsible for get customer RSL status tracking
 */
@RestController
@Api(tags = "RSL Status tracking")
public class RslStatusTrackingController {

    private static final TMBLogger<RslStatusTrackingController> logger = new TMBLogger<>(RslStatusTrackingController.class);
    private final RslStatusTrackingService rslStatusTrackingService;

    /**
     * Constructor
     *
     * @param rslStatusTrackingService rsl status tracking service
     */
    public RslStatusTrackingController(RslStatusTrackingService rslStatusTrackingService) {
    	super();
        this.rslStatusTrackingService = rslStatusTrackingService;
    }

    /**
     * method : To call rslStatusTrackingService service
     *
     * @param citizenId String
     * @param mobileNo String
     * @param correlationId String
     *
     * @return TmbOneServiceResponse<List<CaseStatusTrackingDetail>>
     */
    @ApiOperation(value = "Get RSL Status Tracking")
    @GetMapping(value = "/rsl/status", produces = MediaType.APPLICATION_JSON_VALUE)
    @LogAround
    public ResponseEntity<TmbOneServiceResponse<List<RslStatusTrackingResponse>>> getRslStatusTracking(
            @ApiParam(value = "Citizen-ID", defaultValue = "1100400759800", required = true) @RequestHeader(name = "Citizen-ID", required = false) String citizenId,
            @ApiParam(value = "Mobile-No", defaultValue = "0811234567") @RequestHeader(name = "Mobile-No", required = false) String mobileNo,
            @ApiParam(value = "Module", defaultValue = "2") @RequestHeader(name = "Module", required = false) String module,
            @ApiParam(value = "Correlation ID", defaultValue = "32fbd3b2-3f97-4a89-ar39-b4f628fbc8da", required = true) @Valid @RequestHeader(LendingServiceConstant.HEADER_CORRELATION_ID) String correlationId) {

        logger.info("Lending-service getRslStatusTracking method start Time : {} ", System.currentTimeMillis());

        TmbOneServiceResponse<List<RslStatusTrackingResponse>> rslStatusTrackingResponse = new TmbOneServiceResponse<>();

        try {
            List<RslStatusTrackingResponse> rslStatusTracking = rslStatusTrackingService.getRslStatusTracking(citizenId, mobileNo, module, correlationId);

            if (rslStatusTracking == null) {
                rslStatusTrackingResponse.setStatus(new TmbStatus(ResponseCode.DATA_NOT_FOUND.getCode(),
                        ResponseCode.DATA_NOT_FOUND.getMessage(), ResponseCode.DATA_NOT_FOUND.getService()));
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .headers(TMBUtils.getResponseHeaders())
                        .body(rslStatusTrackingResponse);
            } else {
                if(rslStatusTracking.isEmpty()) {
                    rslStatusTrackingResponse.setData(null);
                } else {
                    rslStatusTrackingResponse.setData(rslStatusTracking);
                }
                rslStatusTrackingResponse.setStatus(new TmbStatus(ResponseCode.SUCCESS.getCode(), ResponseCode.SUCCESS.getMessage(),
                        ResponseCode.SUCCESS.getService(), ResponseCode.SUCCESS.getDesc()));
                return ResponseEntity.status(HttpStatus.OK)
                        .headers(TMBUtils.getResponseHeaders())
                        .body(rslStatusTrackingResponse);
            }
        } catch (Exception e) {
            logger.error("Unable to getRslStatusTracking data : {} ", e);
            rslStatusTrackingResponse.setStatus(new TmbStatus(ResponseCode.FAILED.getCode(), ResponseCode.FAILED.getMessage(),
                            ResponseCode.FAILED.getService(), ResponseCode.FAILED.getDesc()));
        }

        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .headers(TMBUtils.getResponseHeaders())
                .body(rslStatusTrackingResponse);
    }
}
