package com.tmb.oneapp.customerservice.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.tmb.oneapp.lendingservice.client.RslStatusTrackingClient;
import com.tmb.oneapp.lendingservice.model.RslStatusTrackingResponse;
import com.tmb.oneapp.lendingservice.service.RslStatusTrackingService;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import org.mockito.Mockito;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@RunWith(JUnit4.class)
class RslStatusTrackingServiceTest {
    private final RslStatusTrackingClient rslStatusTrackingClient = Mockito.mock(RslStatusTrackingClient.class);

    private final RslStatusTrackingService rslStatusTrackingService = new RslStatusTrackingService();

    private final String realCitizenId = "1100400759800";
    private final String realMobileNo = "0811234567";
    private final String realCorrelationId = "32fbd3b2-3f97-4a89-ae39-b4f628fbc8da";

    @Test
    void fetchRslStatusTracking() {

        ResponseEntity<String> mockRslResponse = new ResponseEntity<>(getMockResult(), HttpStatus.OK);

        when(rslStatusTrackingClient.getRslStatusTracking(getRequestXML(realCitizenId, realMobileNo, realCorrelationId))).thenReturn(mockRslResponse);

        String rslResponse_actual = rslStatusTrackingService.fetchRslStatusTracking(realCitizenId, realMobileNo, realCorrelationId);

        assertNotNull(rslResponse_actual);
    }

    @Test
    void getRslStatusTracking() throws JsonProcessingException {
        ResponseEntity<String> mockRslResponse = new ResponseEntity<>(getMockResult(), HttpStatus.OK);

        when(rslStatusTrackingClient.getRslStatusTracking(getRequestXML(realCitizenId, realMobileNo, realCorrelationId))).thenReturn(mockRslResponse);

        String rslResponse_actual = rslStatusTrackingService.fetchRslStatusTracking(realCitizenId, realMobileNo, realCorrelationId);

        List<RslStatusTrackingResponse> rslStatusTrackingResponseList_actual = rslStatusTrackingService.getRslStatusTracking(realCitizenId, realMobileNo, realCorrelationId);
        assertNotNull(rslStatusTrackingResponseList_actual);
    }

//    @Test
//    void getSalesforceDataFail() throws JsonProcessingException {
//        String crmId = "3513513515";
//
//        String mockSalesforceAuthenticationFeignClientResponse = "{\n" +
//                "    \"access_token\": \"00D9D0000000nTg!AQoAQGDoQikmGY.3fNuA57MP8oeFoR3ngmmIBtQiSS5.0rKGmjZaLX_pVgizC3AW4.ckFZNjwEB4omDzOeF9nfQPj.A8bQZS\",\n" +
//                "    \"instance_url\": \"https://tmbbank--uat.my.salesforce.com\",\n" +
//                "    \"id\": \"https://test.salesforce.com/id/00D9D0000000nTgUAI/0059000000TtBrGAAV\",\n" +
//                "    \"token_type\": \"Bearer\",\n" +
//                "    \"issued_at\": \"1615200539124\",\n" +
//                "    \"signature\": \"dJM6qy9WWUXzn/LSvOj5Q1hxCdMw80e9iF6mUMSfZtM=\"\n" +
//                "}";
//        ObjectMapper mapper = new ObjectMapper();
//        SalesforceAuthenticationResponseFormat salesforceAuthenticationResponseFormat = mapper.readValue(mockSalesforceAuthenticationFeignClientResponse, SalesforceAuthenticationResponseFormat.class);
//        ResponseEntity<SalesforceAuthenticationResponseFormat> mockResponseSalesforceAuthenticationResponseFormat = new ResponseEntity<SalesforceAuthenticationResponseFormat>(salesforceAuthenticationResponseFormat, HttpStatus.OK);
//        when(salesforceAuthenticationFeignClient.getAuthentication(any())).thenReturn(mockResponseSalesforceAuthenticationResponseFormat);
//
//        SalesforceEncryptedDataFormat salesforceEncryptedDataFormat = new SalesforceEncryptedDataFormat("e7b87c5268b2d56036dca5788222e41c",
//                "d165ec43a5beaf10ebfb0b7ee53c1909769e03b085d1a94786da9907b3c8a16a05e820164c8d1f1154e0d4d76d62ecc9a866df508798deb58cba03d5a77c7b28a6e8a938df8baf4206428b72ac3ad26ba20879ea726f6f36c2f73b090ed7934766a3df278b66a7f36299b4e82618c54c05c33f150ad74f427b28da35213652aa1dee82885ae81c1312eccf60cfd22601c502d8a9343e7ea332529cc0d9cc634058952f3b80b4f0a8d73baffa79dd1f832a3b380badd911eaf3d16d77d64558531f844cf12d2ec574d199b916e031217816a1ec463dee3738bff0a7aa8d3bb3ff0fa9bce017a5eecc9d49a7c7170954da207483c22013a4984ed139e81169a0e32eb025132ea301a1a2351edbe56f62d0774d6ae2ccc9d2f39247dedeb53ce5e3fa373ed8c22b83bedd34fe97530b44031a87fb9292dabc89ffc0606ed3c3ed24c28a2d531ece87092f6c2c99a636d5f9f5740f2243d085fb1b9168e146e51de0b75a1128c23bab26682715671b905c886132b06ddaba193d589e7f430821a5cd456d31e5821739cb95424b8eb912709979eadc6942a601968ea1af067fb7f0e456b4187a5bd2b0a67071d49554979b9225416ac61146cb879dc4c023390e8150521b2902d719ff675abfe5fe76609d0395410979619f09bb3d4e8c2044e41c99728c32b2943450dd99aae7381638685ba841bc1e57d503ac22bfc0395af7f8d83b1d10168e7978d59bfe7381f2710ea183e53258651e896b0536e3d3a99446b75678b2867c5c14f58db736d58b9da4e7a2ae076e435aeb90b815d92b0219f5311864379c38999f7dabddfa9b10ec5dca14d03f49a22a1fbe0a2ebd46c6ca153d9bb1049f282212d8161f597e05ebe1b4e4c889a3d36fc94271066b968663e843fa9383f9ebeb2996163bee85287aac97c04af75e4473dcb874b7ae50ba11607a2d5c5fc5569f2f6b58c1d0b1864d4c76f34085fdc5dbbb44997101489cfc76963feff0351da3ac5a5d7626d075b6b0767400d741999dc33fe237ed3746abc0ee5c69ff7b00229ddaf16448d1c4d1fa90f413f9b80c8b5dbdb30878756e3fa1bde83d18c3757fd43864cae602186b60447513daf98218937df648d1eabca04e9525c3f37627a2830d80fa6b30092cc56a20cf696653ccd437387f76c0673540978b64ecb2d003669d41cae7906d7a817c33664f4e03838618d7492f7f3ab16bfed1af6319404f64cea0a6ab6a39a95f1a7c756c12c69b7877af4fc170518d08028c33854f33a077f78cbe56c4ee3d9ab585cf40c612bde73a73d637517d9c78c135dc6d09ca71ae6990feadbfab13e92f23601b27b06233397973d827344a8959ac1b956486e9ccede1cef818c94822501f61e8650b5069cea4ecd1737e535df2193a260e3c50d94e1f3d81f506be8ece0a5d59e3952b006ba15409b00b7f99076ace3879cc9c56d5703cde6d45d756d0b6d7343a6bcea97b1a315205547944c83e0273f573bb26089f4d3d2cdbe777f8953f409d218259eb752b4f9441f550d61c9cc78d78aa94f2691c4667a6b7dd317802c298159ee3362bad7e37f85e4d400dce7a40a9664d230f176c89e89400da701ea0516d0e6990fd96921324f7f13a5e64e5e68e808191e3bc18453e576daf041f73811eed1750925627fc2eea8ec442e98e7bdc5ee8e820e21c43ccb98451f5119ce759c44c77560dac925740fb8bc1e1cc6a7f9b6de76611d17e1c0903107ca3cecbc021e13c6460b624d02c337a600a78c0663d30cee66baa30178ef44fed48be20a03146f03f8b2297349f23aaff2a46a1393ada2e7d01aa396b3302549a6a101c558a7a69149e3edef4ce65fb7a89313afe1de85da869de4f53b1a04e0fced6050f188c37779817220e8bf654820860b18a7b0467d0434f39c74507be13f566e1652c726294358eafd57675103e1c5636519436c8007dce3d3c9ccf8c22da3d383052ba2e94b4e4e07568f2ccb73a8aaec757343cbf9b17b7289de3a1f031f43cda9526e7966eca63923f5917ea159087e3b06b4fde3bb47aaf2d2458158143e1d75a549d706830d7e2390c4923e7dd41ea3b7d78f690e6e9d8b51b3ec1c507d602493735274e7e13700bce031872d4323103ba5732311b3d78d0c16461befdacb88971c16f814dcb6150cd4857a768ce3edfce494e4eceefc511f92a6d44330c99e72067a42f5094f09eade155e3fc46b0243eb9e83482f91ab0b29c9b52d71f7421aadfe3c6a89d691c209e0cb1e7e6211f8ed622e696bb9c94e75ddb61e267161cf314b8b1f1dcbf48dad654b2eb73edd931b1132c9f71169fb5e0123dea9ade31d2c6d04927764e926f8a1ceff9068e26b5860718620c428f5cf7d4f0020ee75cefcb2daf1d9e1a8f767bb97057308b3d1227815a99a1740191175f2707cbf2dd0e8eb8d44b47c78f78bcf926b309028e2596468f35c83d43b46fbfae52e2826e7a8314b06a7779f2a547e729abc72bc1462a38f32ae3836c16de600c0e5a452226096f3dfd6227066efba054056686f2bc7c59cb76f74ba8eae2c42b5167a88de2ccd9f219ee2fa43c27bc25edf8de10015faeb35bedc7cdca505d44c2a0da33eedcd12afe9e9bd67bd07a489839ae535cfedcefe32578c3c1e5824e87dd87936ce9b972c6cb3ab12e24098bdaeb545d447378a38129971ac9d1abc6323f1176e2af135312f9e04d204bd8fb3d355b54c5a1a71d4734d2f96b8358df42044379a99ef469c8bffbaedc39e8a3c60d623439dc692fcb201a08eedc4bd8308b10aafe68c01cfdea4babc704cfa814002552ee119b6f088453d174ac4da137a9d0aecd5d68148d84c7816dbf91e745a9d33217c95eace066ea4ed940418e6a1118d454947e3fe49fabc41d6469816511cf5482932bd6254397a15c640f9f956b3f15dbbe260f97c414fcc22edc120c2849b71a77d97254e3187a2cccef43e061f6782f52600b8a1d0cc7b428ed7e97358b09da1d48fe6063573737296a4180eac6850714bd9952f6929127c5f7023c8be6572930e5adc38b343b431366edaecce83a1079610fd4f51d28e5aca27419e0767c8141f11e06e4ba0f087762d14819bed40d83d4f01599ff67311e1ddf210e5584440a225c69f8c7e60b8eef1d4f4daaba24c8c6dca3d438f8572aa6c4cc6a77447c6f322b5dab46080b51dca36ce109c96431dab7395f2b48e444b1faf015ee0d9e676b746357c474e86c768968363e31694bfe433f32cd529266083b5c959e4e83cd17db76cf8d51f5b194f5a0d3ff2b476ff0d6500d7a2038fa24ff9e929309c6903d2de3c6731d99219f051eecbfe30514c75a04553490bbfecd995400731f1c460561626f4689921fe0da4f950b89b44e31c4519d28217e556ac56d69c2ee0a0714aecd264c8bbe609906ba8d3bcb180037d44855da18b79ea9a584268a5720c2c48426880001ba9da7a18c051d61c8bdfa600fb7332d99158074d36f8caba384f39e73b6314f78b6fd762f0d449e6d527eee6ad410b90c5d966f961f56d365fc1973853af842f55c66c25af31c926050f5a5e74f6a98543397206f4226d560336d17e2c5bb48c58ae0c9cb14108923a4553021363863b0381467a63c96e3f95ecb1b894e8984b1055339125cffcd4178c0b1bc0a039e70e2962d443cf653fb35f077208feb2c138e2d3bfd0635dea6633a78d3b5024c86ddca2eee029c43ad5dc0b57b3cabcc0e1ec3b9f92a525aa8a11a4c5ee0606169ff76760d91704a654262076d7103a7b724e6046083fab28ba2cba8bb2118599ad0");
//        ResponseEntity<SalesforceEncryptedDataFormat> mockResponseSalesforceEncryptedDataFormat = new ResponseEntity<SalesforceEncryptedDataFormat>(salesforceEncryptedDataFormat, HttpStatus.OK);
//        when(salesforceFeignClient.fetchData(any(), any(), any())).thenThrow(new IllegalArgumentException());
//
//        List<CaseStatusTrackingDetail> caseStatusTrackingList_actual = customerCaseStatusTrackingService.getCaseStatusTracking(crmId);
//        assertNotNull(caseStatusTrackingList_actual);
//    }

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
}