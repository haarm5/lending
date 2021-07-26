package com.tmb.oneapp.lendingservice.client;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.tmb.common.exception.model.TMBCommonException;
import com.tmb.common.logger.TMBLogger;
import com.tmb.common.model.legacy.rsl.common.ob.facility.Facility;
import com.tmb.common.model.legacy.rsl.ws.facility.update.request.Body;
import com.tmb.common.model.legacy.rsl.ws.facility.update.request.Header;
import com.tmb.common.model.legacy.rsl.ws.facility.update.request.RequestFacility;
import com.tmb.common.model.legacy.rsl.ws.facility.update.response.ResponseFacility;
import com.tmb.common.model.legacy.rsl.ws.loan.submission.LoanSubmissionUpdateFacilityServiceLocator;
import com.tmb.common.model.legacy.rsl.ws.loan.submission.LoanSubmissionUpdateFacilitySoapBindingStub;
import com.tmb.oneapp.lendingservice.constant.ResponseCode;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import javax.xml.rpc.ServiceException;
import java.math.BigDecimal;
import java.rmi.RemoteException;
import java.util.UUID;

@Service
public class LoanSubmissionUpdateFacilityInfoClient {

    private static final TMBLogger<LoanSubmissionUpdateFacilityInfoClient> logger = new TMBLogger<>(LoanSubmissionUpdateFacilityInfoClient.class);
    private final ObjectMapper mapper;

    @Value("${rsl.loan-submission-update-facility-info.url}")
    private String url;

     LoanSubmissionUpdateFacilityServiceLocator locator = new LoanSubmissionUpdateFacilityServiceLocator();

    private static final String CHANNEL = "MIB";
    private static final String MODULE = "3";

    public LoanSubmissionUpdateFacilityInfoClient(ObjectMapper mapper) {
        this.mapper = mapper;
    }

    public void setLocator(LoanSubmissionUpdateFacilityServiceLocator locator) {
        this.locator = locator;
    }


    public ResponseFacility updateFacilityInfo(Facility facility) throws ServiceException, JsonProcessingException, TMBCommonException {
        locator.setLoanSubmissionUpdateFacilityEndpointAddress(url);
        LoanSubmissionUpdateFacilitySoapBindingStub stub = (LoanSubmissionUpdateFacilitySoapBindingStub) locator.getLoanSubmissionUpdateFacility();
        logger.info("LoanSubmissionUpdateFacility Url: {}", url);

        RequestFacility request = new RequestFacility();
        request.setHeader(setHeader());
        request.setBody(setBody(facility));
        logger.info("LoanSubmissionUpdateFacility Request: {}", mapper.writeValueAsString(request));

        try {
            ResponseFacility response = stub.updateFacilityInfo(request);
            logger.info("LoanSubmissionUpdateFacility Response: {}", mapper.writeValueAsString(response));
            return response;
        } catch (RemoteException e) {
            throw new TMBCommonException(ResponseCode.RSL_CONNECTION_ERROR.getCode(), ResponseCode.RSL_CONNECTION_ERROR.getMessage(), ResponseCode.RSL_CONNECTION_ERROR.getService(), HttpStatus.INTERNAL_SERVER_ERROR, e);
        }
    }

    private Header setHeader() {
        Header header = new Header();
        header.setChannel(CHANNEL);
        header.setModule(MODULE);
        header.setRequestID(UUID.randomUUID().toString());
        return header;
    }

    private Body setBody(Facility facility) {
        Body body = new Body();
        facility.setTenure(BigDecimal.valueOf(facility.getFeature().getTenure()));
        body.setFacility(facility);
        return body;
    }

}
