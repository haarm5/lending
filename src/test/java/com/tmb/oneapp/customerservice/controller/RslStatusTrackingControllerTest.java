package com.tmb.oneapp.customerservice.controller;

import com.tmb.common.model.TmbOneServiceResponse;
import com.tmb.oneapp.lendingservice.controller.RslStatusTrackingController;
import com.tmb.oneapp.lendingservice.model.RslStatusTrackingResponse;
import com.tmb.oneapp.lendingservice.service.RslStatusTrackingService;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import org.mockito.Mockito;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@RunWith(JUnit4.class)
class RslStatusTrackingControllerTest {

    private final RslStatusTrackingService rslStatusTrackingService = Mockito.mock(RslStatusTrackingService.class);

    private final RslStatusTrackingController rslStatusTrackingController = new RslStatusTrackingController(rslStatusTrackingService);

    private final String realCitizenId = "1100400759800";
    private final String realMobileNo = "0811234567";
    private final String realCorrelationId = "32fbd3b2-3f97-4a89-ae39-b4f628fbc8da";

    @Test
    void getCaseStatusTracking_success() {

        List<RslStatusTrackingResponse> rslStatusTrackingResponseList = getRealRslStatusTrackingResponseList();

        TmbOneServiceResponse<List<RslStatusTrackingResponse>> mockResponseRslStatusTrackingList = new TmbOneServiceResponse<>();
        mockResponseRslStatusTrackingList.setData(rslStatusTrackingResponseList);

        when(rslStatusTrackingService.getRslStatusTracking(anyString(), anyString(), anyString())).thenReturn(rslStatusTrackingResponseList);

        ResponseEntity<TmbOneServiceResponse<List<RslStatusTrackingResponse>>> response_actual = rslStatusTrackingController.getRslStatusTracking(realCitizenId, realMobileNo, realCorrelationId);

        assertEquals(200, response_actual.getStatusCodeValue());
        assertNotNull(response_actual.getBody());
    }

    @Test
    void getCaseStatusTracking_success_no_case() {
        String fakeCitizenId = "12345";

        List<RslStatusTrackingResponse> rslStatusTrackingResponseList = new ArrayList<>();

        when(rslStatusTrackingService.getRslStatusTracking(anyString(), anyString(), anyString())).thenReturn(rslStatusTrackingResponseList);

        ResponseEntity<TmbOneServiceResponse<List<RslStatusTrackingResponse>>> response_actual = rslStatusTrackingController.getRslStatusTracking(fakeCitizenId, realMobileNo, realCorrelationId);

        assertEquals(404, response_actual.getStatusCodeValue());
    }

    @Test
    void getCaseStatusTracking_fail() {
        String fakeCitizenId = "";
        String fakeCorrelationId = "";

        when(rslStatusTrackingService.getRslStatusTracking(anyString(), anyString(), anyString())).thenThrow(IllegalArgumentException.class);

        ResponseEntity<TmbOneServiceResponse<List<RslStatusTrackingResponse>>> response_actual = rslStatusTrackingController.getRslStatusTracking(fakeCitizenId, realMobileNo, fakeCorrelationId);

        assertNotNull(response_actual);
    }

    private List<RslStatusTrackingResponse> getRealRslStatusTrackingResponseList() {
        List<RslStatusTrackingResponse> rslStatusTrackingResponseList = new ArrayList<>();
//        First Data
        RslStatusTrackingResponse rslStatusTrackingResponse = new RslStatusTrackingResponse();
        rslStatusTrackingResponse.setReferenceNo("026CC64017644");
        rslStatusTrackingResponse.setAppStatus("IDDFD");
        rslStatusTrackingResponse.setCurrentNode("2");
        rslStatusTrackingResponse.setApplicationDate("2021-01-24T14:14:32.000Z");
        rslStatusTrackingResponse.setIsApproved("N");
        rslStatusTrackingResponse.setIsRejected("");
        rslStatusTrackingResponse.setLastUpdateDate("2021-01-26T09:53:55.000Z");
        rslStatusTrackingResponse.setProductCode("MS");
        rslStatusTrackingResponse.setProductTypeTh("TMB So Chill");
        rslStatusTrackingResponse.setProductTypeEn("TMB So Chill");
        rslStatusTrackingResponse.setNodeTextTh(getNodeTh());
        rslStatusTrackingResponse.setNodeTextEn(getNodeEn());
//        Second Data
        RslStatusTrackingResponse rslStatusTrackingResponse2 = new RslStatusTrackingResponse();
        rslStatusTrackingResponse2.setReferenceNo("026PL64034602");
        rslStatusTrackingResponse2.setAppStatus("IDDFD");
        rslStatusTrackingResponse2.setCurrentNode("2");
        rslStatusTrackingResponse2.setApplicationDate("2021-01-25T10:16:41.000Z");
        rslStatusTrackingResponse2.setIsApproved("N");
        rslStatusTrackingResponse2.setIsRejected("");
        rslStatusTrackingResponse2.setLastUpdateDate("2021-01-26T12:11:09.000Z");
        rslStatusTrackingResponse2.setProductCode("RC");
        rslStatusTrackingResponse2.setProductTypeTh("FLASH Card");
        rslStatusTrackingResponse2.setProductTypeEn("บัตรกดเงินสด แฟลช");
        rslStatusTrackingResponse2.setNodeTextTh(getNodeTh());
        rslStatusTrackingResponse2.setNodeTextEn(getNodeEn());

        rslStatusTrackingResponseList.add(rslStatusTrackingResponse2);

        return rslStatusTrackingResponseList;
    }

    private List<String> getNodeTh() {
        List<String> nodeTh = new ArrayList<>();
        nodeTh.add("ธนาคารได้รับใบสมัครจากท่านเรียบร้อยแล้ว");
        nodeTh.add("อยู่ระหว่างการตรวจสอบข้อมูลและเอกสาร");
        nodeTh.add("อยู่ระหว่างการพิจารณาอนุมัติสินเชื่อ");
        nodeTh.add("ท่านได้รับการอนุมัติสินเชื่อเรียบร้อยแล้ว");

        return nodeTh;
    }

    private List<String> getNodeEn() {
        List<String> nodeEn = new ArrayList<>();
        nodeEn.add("TMB has received your application.");
        nodeEn.add("Verifing Information and document in progress.");
        nodeEn.add("Approval process in progress.");
        nodeEn.add("Your application has been approval.");

        return nodeEn;
    }
}