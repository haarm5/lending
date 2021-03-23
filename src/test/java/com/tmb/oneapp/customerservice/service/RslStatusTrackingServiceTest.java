package com.tmb.oneapp.customerservice.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.tmb.common.model.TmbOneServiceResponse;
import com.tmb.oneapp.lendingservice.client.CommonServiceFeignClient;
import com.tmb.oneapp.lendingservice.client.RslStatusTrackingClient;
import com.tmb.oneapp.lendingservice.model.ProductConfig;
import com.tmb.oneapp.lendingservice.model.RslMessage;
import com.tmb.oneapp.lendingservice.model.RslStatusTrackingResponse;
import com.tmb.oneapp.lendingservice.repository.RslMessageRepository;
import com.tmb.oneapp.lendingservice.service.RslStatusTrackingService;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import org.mockito.Mockito;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@RunWith(JUnit4.class)
class RslStatusTrackingServiceTest {
    private final RslStatusTrackingClient rslStatusTrackingClient = Mockito.mock(RslStatusTrackingClient.class);
    private final CommonServiceFeignClient commonServiceFeignClient = Mockito.mock(CommonServiceFeignClient.class);
    private final RslMessageRepository rslMessageRepository = Mockito.mock(RslMessageRepository.class);

    private final RslStatusTrackingService rslStatusTrackingService = new RslStatusTrackingService(commonServiceFeignClient, rslMessageRepository);

    private final String realCitizenId = "1100400759800";
    private final String realMobileNo = "0811234567";
    private final String realCorrelationId = "32fbd3b2-3f97-4a89-ae39-b4f628fbc8da";

    private final ObjectMapper mapper = new ObjectMapper();

    @Test
    void getProductConfig() {
        TmbOneServiceResponse<List<ProductConfig>> mockProductConfig = new TmbOneServiceResponse<>();
        mockProductConfig.setData(new ArrayList<>());

        when(commonServiceFeignClient.getProductConfig(anyString())).thenReturn(new ResponseEntity<>(mockProductConfig, HttpStatus.OK));

        List<ProductConfig> rslResponse_actual = rslStatusTrackingService.fetchProductConfig(realCorrelationId);

        assertNotNull(rslResponse_actual);
    }

    @Test
    void fetchRslStatusTracking() {
        ResponseEntity<String> mockRslResponse = new ResponseEntity<>(getMockResult(), HttpStatus.OK);

        when(rslStatusTrackingClient.getRslStatusTracking(anyString())).thenReturn(mockRslResponse);

        String rslResponse_actual = rslStatusTrackingService.fetchRslStatusTracking(realCitizenId, realMobileNo, realCorrelationId);

        assertNotNull(rslResponse_actual);
    }

    @Test
    void fetchMessage() {
        when(rslMessageRepository.getMsg(anyString(), anyString(), anyString())).thenReturn(getMockResultRslMessage());

        List<RslStatusTrackingResponse> rslStatusTrackingResponseList_actual = rslStatusTrackingService.getRslStatusTracking(realCitizenId, realMobileNo, realCorrelationId);

        assertEquals(null, rslStatusTrackingResponseList_actual);
    }

    @Test
    void getRslStatusTracking() throws JsonProcessingException {
        ResponseEntity<String> mockRslResponse = new ResponseEntity<>(getMockResult(), HttpStatus.OK);

        when(rslStatusTrackingClient.getRslStatusTracking(anyString())).thenReturn(mockRslResponse);

        String mockResultProductConfig = getMockResultProductConfig();
        List<ProductConfig> productConfigResponses = mapper.readValue(mockResultProductConfig, new TypeReference<>() {
        });
        TmbOneServiceResponse<List<ProductConfig>> mockProductConfig = new TmbOneServiceResponse<>();
        mockProductConfig.setData(productConfigResponses);

        when(commonServiceFeignClient.getProductConfig(anyString())).thenReturn(new ResponseEntity<>(mockProductConfig, HttpStatus.OK));

        when(rslMessageRepository.getMsg(anyString(), anyString(), anyString())).thenReturn(getMockResultRslMessage());

        List<RslStatusTrackingResponse> rslStatusTrackingResponseList_actual = rslStatusTrackingService.getRslStatusTracking(realCitizenId, realMobileNo, realCorrelationId);
        assertNotNull(rslStatusTrackingResponseList_actual);
    }

    @Test
    void getRslStatusTrackingDataNotFound() throws JsonProcessingException {
        String fakeCitizenId = "123";

        ResponseEntity<String> mockRslResponse = new ResponseEntity<>(getMockResultFail(), HttpStatus.OK);

        when(rslStatusTrackingClient.getRslStatusTracking(getRequestXML(fakeCitizenId, realMobileNo, realCorrelationId))).thenReturn(mockRslResponse);

        String mockResultProductConfig = getMockResultProductConfig();
        List<ProductConfig> productConfigResponses = mapper.readValue(mockResultProductConfig, new TypeReference<>() {
        });
        TmbOneServiceResponse<List<ProductConfig>> mockProductConfig = new TmbOneServiceResponse<>();
        mockProductConfig.setData(productConfigResponses);

        when(commonServiceFeignClient.getProductConfig(anyString())).thenReturn(new ResponseEntity<>(mockProductConfig, HttpStatus.OK));

        when(rslMessageRepository.getMsg(anyString(), anyString(), anyString())).thenReturn(getMockResultRslMessage());

        List<RslStatusTrackingResponse> rslStatusTrackingResponseList_actual = rslStatusTrackingService.getRslStatusTracking(fakeCitizenId, realMobileNo, realCorrelationId);
        assertNotNull(rslStatusTrackingResponseList_actual);
    }

    @Test
    void getRslStatusTrackingFail() throws JsonProcessingException {
        when(rslStatusTrackingClient.getRslStatusTracking(anyString())).thenReturn(new ResponseEntity<>(null, HttpStatus.OK));

        String mockResultProductConfig = getMockResultProductConfig();
        List<ProductConfig> productConfigResponses = mapper.readValue(mockResultProductConfig, new TypeReference<>() {
        });
        TmbOneServiceResponse<List<ProductConfig>> mockProductConfig = new TmbOneServiceResponse<>();
        mockProductConfig.setData(productConfigResponses);

        when(commonServiceFeignClient.getProductConfig(anyString())).thenReturn(new ResponseEntity<>(mockProductConfig, HttpStatus.OK));

        when(rslMessageRepository.getMsg(anyString(), anyString(), anyString())).thenReturn(getMockResultRslMessage());

        List<RslStatusTrackingResponse> rslStatusTrackingResponseList_actual = rslStatusTrackingService.getRslStatusTracking(null, realMobileNo, null);

        assertEquals(null, rslStatusTrackingResponseList_actual);
    }

    private String getRequestXML(String citizenId, String mobileNo, String correlationId) {
        String rslRequest = "<soapenv:Envelope xmlns:soapenv=\"http://schemas.xmlsoap.org/soap/envelope/\" xmlns:trac=\"http://tracking.ws.sml.integrosys.com\" xmlns:req=\"http://request.tracking.ws.sml.integrosys.com\">\n" +
                "   <soapenv:Header/>\n" +
                "   <soapenv:Body>\n" +
                "      <trac:searchAppStatusByID>\n" +
                "         <trac:req>\n" +
                "            <req:body>\n" +
                "               <req:citizenID>" + citizenId + "</req:citizenID>\n" +
                "               <req:mobileNo>" + mobileNo + "</req:mobileNo>\n" +
                "               <req:rmNo></req:rmNo>\n" +
                "            </req:body>\n" +
                "            <req:header>\n" +
                "               <req:channel>MIB</req:channel>\n" +
                "               <req:module>3</req:module>\n" +
                "               <req:requestID>" + correlationId + "</req:requestID>\n" +
                "            </req:header>\n" +
                "         </trac:req>\n" +
                "      </trac:searchAppStatusByID>\n" +
                "   </soapenv:Body>\n" +
                "</soapenv:Envelope>";

        return rslRequest;
    }

    private String getMockResult() {
        String result = "<?xml version=\"1.0\" encoding=\"UTF-8\"?><soapenv:Envelope xmlns:soapenv=\"http://schemas.xmlsoap.org/soap/envelope/\" xmlns:xsd=\"http://www.w3.org/2001/XMLSchema\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\"><soapenv:Body><searchAppStatusByIDResponse xmlns=\"http://tracking.ws.sml.integrosys.com\"><searchAppStatusByIDReturn><body><application><application><appRefNo>026CC64017644</appRefNo><appStatus>IDDFD</appStatus><appStatusDesc>Incomplete Document and Data at FIFI - from Doc Checker</appStatusDesc><appType>CC</appType><applicants><applicants><applicantType>P</applicantType><firstNameEN>PIRIYA</firstNameEN><firstNameTH>&#xE1E;&#xE34;&#xE23;&#xE34;&#xE22;&#xE30;</firstNameTH><products><products><approveCreditLimit>0.0</approveCreditLimit><bankAccountNumber xsi:nil=\"true\"/><cardName>P. CHOOPANAKITKOON</cardName><disburseBankCode xsi:nil=\"true\"/><disburseBankFullEN xsi:nil=\"true\"/><disburseBankFullTH xsi:nil=\"true\"/><disburseBankShort xsi:nil=\"true\"/><dsr>0.0</dsr><dti>0.0</dti><paymentMethod>1</paymentMethod><paymentMethodDescEN>By Direct Debit</paymentMethodDescEN><paymentMethodDescTH>&#xE2B;&#xE31;&#xE01;&#xE1A;&#xE31;&#xE0D;&#xE0A;&#xE35;&#xE40;&#xE07;&#xE34;&#xE19;&#xE1D;&#xE32;&#xE01;</paymentMethodDescTH><pricings xsi:nil=\"true\"/><productCode>MS</productCode><productDescEN>TMB So Chill</productDescEN><productDescTH>TMB So Chill</productDescTH><subProductCode xsi:nil=\"true\"/><subProductDescEN xsi:nil=\"true\"/><subProductDescTH xsi:nil=\"true\"/><tenure>0.0</tenure></products></products><seq>1</seq><surNameEN>CHOOPANAKITKOON</surNameEN><surNameTH>&#xE0A;&#xE39;&#xE1E;&#xE19;&#xE32;&#xE01;&#xE34;&#xE08;&#xE01;&#xE38;&#xE25;</surNameTH><titleEN xsi:nil=\"true\"/><titleTH>&#xE19;&#xE32;&#xE22;</titleTH></applicants></applicants><applicationDate>2021-01-24T14:14:32.000Z</applicationDate><balanceTransDate xsi:nil=\"true\"/><caId>2021012404529470</caId><currentNode>2</currentNode><instantAppliedStepFlag xsi:nil=\"true\"/><instantFlag>N</instantFlag><isApproved>N</isApproved><isIncompleteDocUpdated>N</isIncompleteDocUpdated><isRejected xsi:nil=\"true\"/><isSubmitted>Y</isSubmitted><lastUpdateDate>2021-01-26T09:53:55.000Z</lastUpdateDate><natureOfRequest>03</natureOfRequest><natureOfRequestDescEN>Apply - Income Reference</natureOfRequestDescEN><natureOfRequestDescTH>&#xE2A;&#xE21;&#xE31;&#xE04;&#xE23;&#xE1A;&#xE31;&#xE15;&#xE23;&#xE40;&#xE04;&#xE23;&#xE14;&#xE34;&#xE15; - &#xE40;&#xE01;&#xE13;&#xE11;&#xE4C;&#xE23;&#xE32;&#xE22;&#xE44;&#xE14;&#xE49;</natureOfRequestDescTH><roadMap><roadMap><defaultDescEn>TMB has received your application.</defaultDescEn><defaultDescTh>&#xE18;&#xE19;&#xE32;&#xE04;&#xE32;&#xE23;&#xE44;&#xE14;&#xE49;&#xE23;&#xE31;&#xE1A;&#xE43;&#xE1A;&#xE2A;&#xE21;&#xE31;&#xE04;&#xE23;&#xE08;&#xE32;&#xE01;&#xE17;&#xE48;&#xE32;&#xE19;&#xE40;&#xE23;&#xE35;&#xE22;&#xE1A;&#xE23;&#xE49;&#xE2D;&#xE22;&#xE41;&#xE25;&#xE49;&#xE27;</defaultDescTh><defaultPercentComplete xsi:nil=\"true\"/><groupNumber>1</groupNumber></roadMap><roadMap><defaultDescEn>Verifing Information and document in progress.</defaultDescEn><defaultDescTh>&#xE2D;&#xE22;&#xE39;&#xE48;&#xE23;&#xE30;&#xE2B;&#xE27;&#xE48;&#xE32;&#xE07;&#xE01;&#xE32;&#xE23;&#xE15;&#xE23;&#xE27;&#xE08;&#xE2A;&#xE2D;&#xE1A;&#xE02;&#xE49;&#xE2D;&#xE21;&#xE39;&#xE25;&#xE41;&#xE25;&#xE30;&#xE40;&#xE2D;&#xE01;&#xE2A;&#xE32;&#xE23;</defaultDescTh><defaultPercentComplete xsi:nil=\"true\"/><groupNumber>2</groupNumber></roadMap><roadMap><defaultDescEn>Approval process in progress.</defaultDescEn><defaultDescTh>&#xE2D;&#xE22;&#xE39;&#xE48;&#xE23;&#xE30;&#xE2B;&#xE27;&#xE48;&#xE32;&#xE07;&#xE01;&#xE32;&#xE23;&#xE1E;&#xE34;&#xE08;&#xE32;&#xE23;&#xE13;&#xE32;&#xE2D;&#xE19;&#xE38;&#xE21;&#xE31;&#xE15;&#xE34;&#xE2A;&#xE34;&#xE19;&#xE40;&#xE0A;&#xE37;&#xE48;&#xE2D;</defaultDescTh><defaultPercentComplete xsi:nil=\"true\"/><groupNumber>3</groupNumber></roadMap><roadMap><defaultDescEn>Your application has been approval.</defaultDescEn><defaultDescTh>&#xE17;&#xE48;&#xE32;&#xE19;&#xE44;&#xE14;&#xE49;&#xE23;&#xE31;&#xE1A;&#xE01;&#xE32;&#xE23;&#xE2D;&#xE19;&#xE38;&#xE21;&#xE31;&#xE15;&#xE34;&#xE2A;&#xE34;&#xE19;&#xE40;&#xE0A;&#xE37;&#xE48;&#xE2D;&#xE40;&#xE23;&#xE35;&#xE22;&#xE1A;&#xE23;&#xE49;&#xE2D;&#xE22;&#xE41;&#xE25;&#xE49;&#xE27;</defaultDescTh><defaultPercentComplete xsi:nil=\"true\"/><groupNumber>4</groupNumber></roadMap></roadMap><taskId>2021012635046285</taskId></application><application><appRefNo>026PL64034602</appRefNo><appStatus>IDDFD</appStatus><appStatusDesc>Incomplete Document and Data at FIFI - from Doc Checker</appStatusDesc><appType>PL</appType><applicants><applicants><applicantType>M</applicantType><firstNameEN>PIRIYA</firstNameEN><firstNameTH>&#xE1E;&#xE34;&#xE23;&#xE34;&#xE22;&#xE30;</firstNameTH><products><products><approveCreditLimit>0.0</approveCreditLimit><bankAccountNumber></bankAccountNumber><cardName xsi:nil=\"true\"/><disburseBankCode>011</disburseBankCode><disburseBankFullEN>TMB Bank Public Company Limited (TMB)</disburseBankFullEN><disburseBankFullTH>&#xE18;&#xE19;&#xE32;&#xE04;&#xE32;&#xE23; &#xE17;&#xE2B;&#xE32;&#xE23;&#xE44;&#xE17;&#xE22; &#xE08;&#xE33;&#xE01;&#xE31;&#xE14; (&#xE21;&#xE2B;&#xE32;&#xE0A;&#xE19;)</disburseBankFullTH><disburseBankShort>TMB</disburseBankShort><dsr>0.0</dsr><dti>0.0</dti><paymentMethod>1</paymentMethod><paymentMethodDescEN>By Direct Debit</paymentMethodDescEN><paymentMethodDescTH>&#xE2B;&#xE31;&#xE01;&#xE1A;&#xE31;&#xE0D;&#xE0A;&#xE35;&#xE40;&#xE07;&#xE34;&#xE19;&#xE1D;&#xE32;&#xE01;</paymentMethodDescTH><pricings/><productCode>RC</productCode><productDescEN>FLASH Card</productDescEN><productDescTH>&#xE1A;&#xE31;&#xE15;&#xE23;&#xE01;&#xE14;&#xE40;&#xE07;&#xE34;&#xE19;&#xE2A;&#xE14; &#xE41;&#xE1F;&#xE25;&#xE0A;</productDescTH><subProductCode>RC01</subProductCode><subProductDescEN>FLASH Card</subProductDescEN><subProductDescTH>&#xE1A;&#xE31;&#xE15;&#xE23;&#xE01;&#xE14;&#xE40;&#xE07;&#xE34;&#xE19;&#xE2A;&#xE14; &#xE41;&#xE1F;&#xE25;&#xE0A;</subProductDescTH><tenure>0.0</tenure></products></products><seq>1</seq><surNameEN>CHOOPANAKITKOON</surNameEN><surNameTH>&#xE0A;&#xE39;&#xE1E;&#xE19;&#xE32;&#xE01;&#xE34;&#xE08;&#xE01;&#xE38;&#xE25;</surNameTH><titleEN xsi:nil=\"true\"/><titleTH>&#xE19;&#xE32;&#xE22;</titleTH></applicants></applicants><applicationDate>2021-01-25T10:16:41.000Z</applicationDate><balanceTransDate xsi:nil=\"true\"/><caId>2021012504531599</caId><currentNode>2</currentNode><instantAppliedStepFlag xsi:nil=\"true\"/><instantFlag>N</instantFlag><isApproved>N</isApproved><isIncompleteDocUpdated>N</isIncompleteDocUpdated><isRejected xsi:nil=\"true\"/><isSubmitted>Y</isSubmitted><lastUpdateDate>2021-01-26T12:11:09.000Z</lastUpdateDate><natureOfRequest>11</natureOfRequest><natureOfRequestDescEN>Apply - Normal</natureOfRequestDescEN><natureOfRequestDescTH>&#xE2A;&#xE21;&#xE31;&#xE04;&#xE23;&#xE2A;&#xE34;&#xE19;&#xE40;&#xE0A;&#xE37;&#xE48;&#xE2D;&#xE1A;&#xE38;&#xE04;&#xE04;&#xE25;</natureOfRequestDescTH><roadMap><roadMap><defaultDescEn>TMB has received your application.</defaultDescEn><defaultDescTh>&#xE18;&#xE19;&#xE32;&#xE04;&#xE32;&#xE23;&#xE44;&#xE14;&#xE49;&#xE23;&#xE31;&#xE1A;&#xE43;&#xE1A;&#xE2A;&#xE21;&#xE31;&#xE04;&#xE23;&#xE08;&#xE32;&#xE01;&#xE17;&#xE48;&#xE32;&#xE19;&#xE40;&#xE23;&#xE35;&#xE22;&#xE1A;&#xE23;&#xE49;&#xE2D;&#xE22;&#xE41;&#xE25;&#xE49;&#xE27;</defaultDescTh><defaultPercentComplete xsi:nil=\"true\"/><groupNumber>1</groupNumber></roadMap><roadMap><defaultDescEn>Verifing Information and document in progress.</defaultDescEn><defaultDescTh>&#xE2D;&#xE22;&#xE39;&#xE48;&#xE23;&#xE30;&#xE2B;&#xE27;&#xE48;&#xE32;&#xE07;&#xE01;&#xE32;&#xE23;&#xE15;&#xE23;&#xE27;&#xE08;&#xE2A;&#xE2D;&#xE1A;&#xE02;&#xE49;&#xE2D;&#xE21;&#xE39;&#xE25;&#xE41;&#xE25;&#xE30;&#xE40;&#xE2D;&#xE01;&#xE2A;&#xE32;&#xE23;</defaultDescTh><defaultPercentComplete xsi:nil=\"true\"/><groupNumber>2</groupNumber></roadMap><roadMap><defaultDescEn>Approval process in progress.</defaultDescEn><defaultDescTh>&#xE2D;&#xE22;&#xE39;&#xE48;&#xE23;&#xE30;&#xE2B;&#xE27;&#xE48;&#xE32;&#xE07;&#xE01;&#xE32;&#xE23;&#xE1E;&#xE34;&#xE08;&#xE32;&#xE23;&#xE13;&#xE32;&#xE2D;&#xE19;&#xE38;&#xE21;&#xE31;&#xE15;&#xE34;&#xE2A;&#xE34;&#xE19;&#xE40;&#xE0A;&#xE37;&#xE48;&#xE2D;</defaultDescTh><defaultPercentComplete xsi:nil=\"true\"/><groupNumber>3</groupNumber></roadMap><roadMap><defaultDescEn>Your application has been approval.</defaultDescEn><defaultDescTh>&#xE17;&#xE48;&#xE32;&#xE19;&#xE44;&#xE14;&#xE49;&#xE23;&#xE31;&#xE1A;&#xE01;&#xE32;&#xE23;&#xE2D;&#xE19;&#xE38;&#xE21;&#xE31;&#xE15;&#xE34;&#xE2A;&#xE34;&#xE19;&#xE40;&#xE0A;&#xE37;&#xE48;&#xE2D;&#xE40;&#xE23;&#xE35;&#xE22;&#xE1A;&#xE23;&#xE49;&#xE2D;&#xE22;&#xE41;&#xE25;&#xE49;&#xE27;</defaultDescTh><defaultPercentComplete xsi:nil=\"true\"/><groupNumber>4</groupNumber></roadMap></roadMap><taskId>2021012635049935</taskId></application></application></body><header><channel>MIB</channel><module>3</module><requestID>b1eb325d-d112-44a4-92f7-7a9b4af83a14</requestID><responseCode>MSG_000</responseCode><responseDescriptionEN>Success</responseDescriptionEN><responseDescriptionTH xsi:nil=\"true\"/></header></searchAppStatusByIDReturn></searchAppStatusByIDResponse></soapenv:Body></soapenv:Envelope>";

        return result;
    }

    private String getMockResultFail() {
        String result = "<?xml version=\"1.0\" encoding=\"UTF-8\"?><soapenv:Envelope xmlns:soapenv=\"http://schemas.xmlsoap.org/soap/envelope/\" xmlns:xsd=\"http://www.w3.org/2001/XMLSchema\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\"><soapenv:Body><searchAppStatusByIDResponse xmlns=\"http://tracking.ws.sml.integrosys.com\"><searchAppStatusByIDReturn><body><application xsi:nil=\"true\"/></body><header><channel>MIB</channel><module>3</module><requestID>b1eb325d-d112-44a4-92f7-7a9b4af83a14</requestID><responseCode>MSG_001</responseCode><responseDescriptionEN>Data Not Found</responseDescriptionEN><responseDescriptionTH>&#xE44;&#xE21;&#xE48;&#xE1E;&#xE1A;&#xE02;&#xE49;&#xE2D;&#xE21;&#xE39;&#xE25;&#xE02;&#xE2D;&#xE07;&#xE17;&#xE48;&#xE32;&#xE19; &#xE43;&#xE19;&#xE23;&#xE30;&#xE1A;&#xE1A;&#xE02;&#xE2D;&#xE07;&#xE18;&#xE19;&#xE32;&#xE04;&#xE32;&#xE23; &#xE42;&#xE1B;&#xE23;&#xE14;&#xE15;&#xE34;&#xE14;&#xE15;&#xE48;&#xE2D;&#xE40;&#xE08;&#xE49;&#xE32;&#xE2B;&#xE19;&#xE49;&#xE32;&#xE17;&#xE35;&#xE48;&#xE1C;&#xE39;&#xE49;&#xE19;&#xE33;&#xE40;&#xE2A;&#xE19;&#xE2D;&#xE1A;&#xE23;&#xE34;&#xE01;&#xE32;&#xE23;&#xE19;&#xE35;&#xE49;&#xE41;&#xE01;&#xE48;&#xE17;&#xE48;&#xE32;&#xE19;</responseDescriptionTH></header></searchAppStatusByIDReturn></searchAppStatusByIDResponse></soapenv:Body></soapenv:Envelope>";

        return result;
    }

    private String getMockResultProductConfig() {
        String result = "[{\"id\":\"6045f7d09ba0434890589e5b\",\"product_code\":\"MSCHIL\",\"product_description\":\"TMB So Chill credit card\",\"product_name_en\":\"So Chill Credit Card\",\"product_name_th\":\"โซ ชิลล์\",\"rsl_product_code\":\"MS\",\"account_type\":\"CCA\",\"icon_id\":\"/product/credit_card/cards_sochill.png\",\"sort_order\":\"20005\",\"account_summary_display\":\"1\",\"account_detail_view\":\"\",\"allow_transfer_from_account\":\"0\",\"transfer_own_ttbmap_code\":\"000\",\"transfer_other_ttbmap_code\":\"000\",\"allow_transfer_to_other_bank\":\"0\",\"allow_register_prompt_pay\":\"0\",\"allow_transfer_to_prompt_pay\":\"0\",\"allow_card_less_withdraw\":\"0\",\"allow_manage_debit_card\":\"0\",\"allow_point_redemption\":\"0\",\"allow_sogoood\":\"1\",\"allow_cashtransfer\":\"1\",\"allow_buy\":\"0\",\"allow_sell\":\"0\",\"allow_history\":\"0\",\"waive_fee_for_prompt_pay\":\"0\",\"waive_fee_for_prompt_pay_account\":\"0\",\"transfer_shortcut_flag\":\"0\",\"pay_bill_shortcut_flag\":\"0\",\"top_up_shortcut_flag\":\"0\",\"pay_my_cc_shortcut_flag\":\"1\",\"pay_my_loan_shortcut_flag\":\"0\"},{\"id\":\"6045f7d09ba0434890589e5f\",\"product_code\":\"VFPSTD\",\"product_description\":\"TMB FLASH Card\",\"product_name_en\":\"FLASH Card\",\"product_name_th\":\"บัตรกดเงินสด แฟลช\",\"rsl_product_code\":\"RC\",\"account_type\":\"CCA\",\"icon_id\":\"/product/credit_card/cards_flash.png\",\"sort_order\":\"30001\",\"account_summary_display\":\"1\",\"account_detail_view\":\"\",\"allow_transfer_from_account\":\"0\",\"transfer_own_ttbmap_code\":\"000\",\"transfer_other_ttbmap_code\":\"000\",\"allow_transfer_to_other_bank\":\"0\",\"allow_register_prompt_pay\":\"0\",\"allow_transfer_to_prompt_pay\":\"0\",\"allow_card_less_withdraw\":\"0\",\"allow_manage_debit_card\":\"0\",\"allow_point_redemption\":\"0\",\"allow_sogoood\":\"0\",\"allow_cashtransfer\":\"1\",\"allow_buy\":\"0\",\"allow_sell\":\"0\",\"allow_history\":\"0\",\"waive_fee_for_prompt_pay\":\"0\",\"waive_fee_for_prompt_pay_account\":\"0\",\"transfer_shortcut_flag\":\"0\",\"pay_bill_shortcut_flag\":\"0\",\"top_up_shortcut_flag\":\"0\",\"pay_my_cc_shortcut_flag\":\"1\",\"pay_my_loan_shortcut_flag\":\"0\"}]";

        return result;
    }

    private RslMessage getMockResultRslMessage() {
        return new RslMessage("apploan_status_code_Desc",
                "อยู่ระหว่างการตรวจสอบข้อมูลและเอกสาร",
                "Verifying Information and document in progress",
                "เอกสารที่ใช้ในการพิจารณาของท่านไม่ถูกต้อง หรือ ไม่ครบถ้วน จะมีเจ้าหน้าที่ธนาคารติดต่อกลับไปยังท่านอีกครั้ง",
                "Your document is incompleted/incorrect, our staff will call you shortly");
    }
}