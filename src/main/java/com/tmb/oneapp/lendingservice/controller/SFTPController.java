package com.tmb.oneapp.lendingservice.controller;

import com.tmb.common.exception.model.TMBCommonException;
import com.tmb.common.logger.LogAround;
import com.tmb.common.logger.TMBLogger;
import com.tmb.common.model.TmbOneServiceResponse;
import com.tmb.oneapp.lendingservice.client.SFTPClientImp;
import com.tmb.oneapp.lendingservice.constant.LendingServiceConstant;
import com.tmb.oneapp.lendingservice.constant.ResponseCode;
import com.tmb.oneapp.lendingservice.model.SFTPStoreFileInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;


@RequestMapping("/sftp")
@RestController

public class SFTPController {
    private static final TMBLogger<SFTPController> logger = new TMBLogger<>(SFTPController.class);
    @Autowired
    private SFTPClientImp sftpClientImp;



    /**
     * Get Flexi Loan Products
     *
     * @param reqHeaders - CRM-ID
     * @return
     * @throws TMBCommonException
     */

    @LogAround
    @GetMapping(value = "/upload")
    public ResponseEntity<TmbOneServiceResponse<Object>> upload(@RequestHeader Map<String, String> reqHeaders) throws TMBCommonException {
        String xCorrelationId = reqHeaders.get(LendingServiceConstant.HEADER_CORRELATION_ID);
        String crmId = reqHeaders.get(LendingServiceConstant.HEADER_X_CRMID);
        if (crmId == null) {
            logger.error("no x-crm-id");
            throw new TMBCommonException("0001", "failed", ResponseCode.BAD_REQUEST.getService(), HttpStatus.BAD_REQUEST, null);
        }
        logger.info("enter /sftp/upload X-Correlation-ID: {}", xCorrelationId);

        List<SFTPStoreFileInfo> req = new ArrayList<>();
        SFTPStoreFileInfo sftpStoreFileInfo = new SFTPStoreFileInfo();
        sftpStoreFileInfo.setRootPath("/u01/datafile/mib/mibshare/LetterConsent");
        sftpStoreFileInfo.setDstDir(crmId);
        sftpStoreFileInfo.setSrcFile(System.getProperty("user.dir") + "/fop/01_210618012001_abc_00110.JPG");
        req.add(sftpStoreFileInfo);
        sftpClientImp.storeFile(req);


        return ResponseEntity.ok().build();
    }

}
