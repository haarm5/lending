package com.tmb.oneapp.lendingservice.controller;

import com.tmb.common.logger.LogAround;
import com.tmb.common.logger.TMBLogger;
import com.tmb.common.model.TmbOneServiceResponse;
import com.tmb.common.model.legacy.rsl.common.ob.facility.Facility;
import com.tmb.oneapp.lendingservice.constant.LendingServiceConstant;
import com.tmb.oneapp.lendingservice.model.loanonline.LoanSubmissionRequest;
import com.tmb.oneapp.lendingservice.service.LoanOnlineSubmissionGetLoanPaymentService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import javax.xml.rpc.ServiceException;
import java.rmi.RemoteException;
import java.time.Instant;

@RequiredArgsConstructor
@Api(tags = "Loan Submission Online")
@RestController
public class LoanSubmissionOnlineController {
    private static final TMBLogger<LoanSubmissionOnlineController> logger = new TMBLogger<>(LoanSubmissionOnlineController.class);
    private final LoanOnlineSubmissionGetLoanPaymentService loanOnlineSubmissionGetLoanPaymentService;

    @ApiOperation(value = "Create Instant Loan Application")
    @LogAround
    @PostMapping("/get-facility")
    public void getFacility(@Valid LoanSubmissionRequest request) throws ServiceException, RemoteException {

        HttpHeaders responseHeaders = new HttpHeaders();
        responseHeaders.set(LendingServiceConstant.HEADER_TIMESTAMP, String.valueOf(Instant.now().toEpochMilli()));
        TmbOneServiceResponse<Object> oneServiceResponse = new TmbOneServiceResponse<>();
        Facility[] facilities;

//        try {
//            facilities = loanOnlineSubmissionGetLoanPaymentService.getFacility(request.getCaId());
//            oneServiceResponse.setStatus(new TmbStatus(ResponseCode.SUCCESS.getCode(), ResponseCode.SUCCESS.getMessage(),
//                    ResponseCode.SUCCESS.getService(), ResponseCode.SUCCESS.getDesc()));
//            oneServiceResponse.setData(facilities);
//            return ResponseEntity.ok().body(oneServiceResponse);
//        }catch (Exception e) {
//            logger.error("get facility ::", e);
//            throw e;
//            oneServiceResponse.setStatus(new TmbStatus(ResponseCode.FAILED.getCode(), ResponseCode.FAILED.getMessage(),
//                    ResponseCode.FAILED.getService(),e.getMessage().substring(1,30)));
//            return ResponseEntity.badRequest().headers(responseHeaders).body(oneServiceResponse);
//        }
    }

}
