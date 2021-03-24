package com.tmb.oneapp.lendingservice.service;

import com.tmb.common.logger.LogAround;
import com.tmb.common.logger.TMBLogger;
import com.tmb.common.model.TmbOneServiceResponse;
import com.tmb.oneapp.lendingservice.client.CommonServiceFeignClient;
import com.tmb.oneapp.lendingservice.client.RslStatusTrackingClient;
import com.tmb.oneapp.lendingservice.model.ProductConfig;
import com.tmb.oneapp.lendingservice.model.RslMessage;
import com.tmb.oneapp.lendingservice.model.RslStatusTrackingResponse;
import com.tmb.oneapp.lendingservice.repository.RslMessageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;

import java.io.StringReader;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * RslStatusTrackingService class will get Customer RSL Status from customer
 */
@Service
public class RslStatusTrackingService {
    private static final TMBLogger<RslStatusTrackingService> logger = new TMBLogger<>(RslStatusTrackingService.class);
    private final RslStatusTrackingClient rslStatusTrackingClient;
    private final CommonServiceFeignClient commonServiceFeignClient;
    private final RslMessageRepository rslMessageRepository;

    /**
     * Constructor
     */
    @Autowired
    public RslStatusTrackingService(RslStatusTrackingClient rslStatusTrackingClient,
                                    CommonServiceFeignClient commonServiceFeignClient,
                                    RslMessageRepository rslMessageRepository) {
        this.rslStatusTrackingClient = rslStatusTrackingClient;
        this.commonServiceFeignClient = commonServiceFeignClient;
        this.rslMessageRepository = rslMessageRepository;
    }

    /**
     * This method will fetch RSL status tracking.
     *
     * @param citizenId citizenId
     * @param mobileNo mobileNo
     * @param correlationId correlationId
     *
     * @return RslStatusTrackingResponse
     */
    public List<RslStatusTrackingResponse> getRslStatusTracking(String citizenId, String mobileNo, String correlationId) {
        try {
            String result = fetchRslStatusTracking(citizenId, mobileNo, correlationId);
            List<ProductConfig> productConfigList = fetchProductConfig(correlationId);

            if(!productConfigList.isEmpty()) {
                if (result.isEmpty()) {
                    return new ArrayList<>();
                } else if (!result.isEmpty()) {
                    List<RslStatusTrackingResponse> rslStatusTrackingResponseList = getRslStatusTrackingResponse(result);
                    return addImageUrlElement(rslStatusTrackingResponseList, productConfigList);
                }
            }
        } catch (Exception e) {
            logger.error("getRslStatusTracking method Error(Data Not Found) : {} ", e);
            return null;    //NOSONAR lightweight logging
        }

        return null;    //NOSONAR lightweight logging
    }

    /**
     * Get rsl status tracking
     *
     * @param xml String of xml
     *
     * @return List<RslStatusTrackingResponse> list of RslStatusTrackingResponse model
     */
    public List<RslStatusTrackingResponse> getRslStatusTrackingResponse(String xml) { //NOSONAR lightweight logging
        List<RslStatusTrackingResponse> rslStatusTrackingResponseList = new ArrayList<>();

        try {
            logger.info("getRslStatusTrackingResponse start time : {}", System.currentTimeMillis());
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance(); //NOSONAR lightweight logging
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.parse(new InputSource(new StringReader(xml)));
            doc.getDocumentElement().normalize();

            String responseDescriptionEN = "";

            NodeList responseDescriptionENList = doc.getElementsByTagName("responseDescriptionEN");
            Node responseDescriptionENNode = responseDescriptionENList.item(0);
            if(responseDescriptionENNode.getNodeType() == Node.ELEMENT_NODE)
            {
                Element responseDescriptionENElement = (Element) responseDescriptionENNode;
                responseDescriptionEN = responseDescriptionENElement.getTextContent();
            }

            if(responseDescriptionEN.equals("Success")) {
                NodeList applicationsList = doc.getElementsByTagName("application");
                for (int i = 0; i < applicationsList.getLength(); i++) {
                    Node applicationNode = applicationsList.item(i);
                    if (applicationNode.getParentNode().getNodeName().equals("application")
                            && applicationNode.getNodeType() == Node.ELEMENT_NODE) {
                        RslStatusTrackingResponse rslStatusTrackingResponse = new RslStatusTrackingResponse();

                        Element applicationElement = (Element) applicationNode;

                        rslStatusTrackingResponse.setReferenceNo(applicationElement.getElementsByTagName("appRefNo")
                                .item(0)
                                .getTextContent());

                        //AppStatus
                        String appStatus = applicationElement.getElementsByTagName("appStatus")
                                .item(0)
                                .getTextContent();
                        rslStatusTrackingResponse.setStatus(getStatus(appStatus));

                        //CurrentNode
                        String currentNode = applicationElement.getElementsByTagName("currentNode")
                                .item(0)
                                .getTextContent();
                        rslStatusTrackingResponse.setCurrentNode(currentNode);

                        //AppType
                        String appType = applicationElement.getElementsByTagName("appType")
                                .item(0)
                                .getTextContent();
                        rslStatusTrackingResponse.setAppType(appType);

                        //Remark
                        RslMessage rslMessage = fetchMessage(appStatus, appType);
                        if(rslMessage == null) {
                            continue;
                        }

                        rslStatusTrackingResponse.setTopRemarkTh(rslMessage.getGroupMsgTh());
                        rslStatusTrackingResponse.setTopRemarkEn(rslMessage.getGroupMsgEn());
                        rslStatusTrackingResponse.setBottomRemarkTh(rslMessage.getLineDescTh());
                        rslStatusTrackingResponse.setBottomRemarkEn(rslMessage.getLineDescEn());

                        String applicationDate = applicationElement.getElementsByTagName("applicationDate")
                                .item(0)
                                .getTextContent();
                        if(!applicationDate.isEmpty()) {
                            applicationDate = applicationDate.substring(0, 19);
                        }
                        rslStatusTrackingResponse.setApplicationDate(applicationDate);
                        rslStatusTrackingResponse.setIsApproved(applicationElement.getElementsByTagName("isApproved")
                                .item(0)
                                .getTextContent());
                        rslStatusTrackingResponse.setIsRejected(applicationElement.getElementsByTagName("isRejected")
                                .item(0)
                                .getTextContent());

                        String lastUpdateDate = applicationElement.getElementsByTagName("lastUpdateDate")
                                .item(0)
                                .getTextContent();
                        if(!lastUpdateDate.isEmpty()) {
                            lastUpdateDate = lastUpdateDate.substring(0, 19);
                        }
                        rslStatusTrackingResponse.setLastUpdateDate(lastUpdateDate);

                        NodeList productsList = applicationElement.getElementsByTagName("products");
                        for (int j = 0; j < productsList.getLength(); j++) {
                            Node productNode = productsList.item(j);
                            if (productNode.getParentNode().getNodeName().equals("products")
                                    && productNode.getNodeType() == Node.ELEMENT_NODE) {
                                Element productElement = (Element) productNode;

                                rslStatusTrackingResponse.setProductCode(productElement.getElementsByTagName("productCode")
                                        .item(0)
                                        .getTextContent());
                                rslStatusTrackingResponse.setProductTypeTh(productElement.getElementsByTagName("productDescTH")
                                        .item(0)
                                        .getTextContent());
                                rslStatusTrackingResponse.setProductTypeEn(productElement.getElementsByTagName("productDescEN")
                                        .item(0)
                                        .getTextContent());
                            }
                        }

                        List<String> nodeTh = new ArrayList<>();
                        List<String> nodeEn = new ArrayList<>();

                        NodeList roadMapList = applicationElement.getElementsByTagName("roadMap");
                        for(int j=0; j<roadMapList.getLength(); j++) {
                            Node roadMapNode = roadMapList.item(j);
                            if(roadMapNode.getParentNode().getNodeName().equals("roadMap") && roadMapNode.getNodeType() == Node.ELEMENT_NODE) {
                                Element roadMapElement = (Element) roadMapNode;
                                String defaultDescEn = roadMapElement.getElementsByTagName("defaultDescEn")
                                        .item(0)
                                        .getTextContent();
                                String defaultDescTh = roadMapElement.getElementsByTagName("defaultDescTh")
                                        .item(0)
                                        .getTextContent();

                                nodeTh.add(defaultDescTh);
                                nodeEn.add(defaultDescEn);
                            }
                        }

                        rslStatusTrackingResponse.setNodeTextTh(nodeTh);
                        rslStatusTrackingResponse.setNodeTextEn(nodeEn);

                        rslStatusTrackingResponseList.add(rslStatusTrackingResponse);
                    }
                }
            }
            return rslStatusTrackingResponseList;
        } catch (Exception e) {
            logger.error("getRslStatusTrackingResponseFromStringXML method Error(Data Not Found) : {} ", e);
        }

        return null; //NOSONAR lightweight logging
    }

    /**
     * This method will add Image URL.
     *
     * @param rslStatusTrackingResponseList List<RslStatusTrackingResponse>
     * @param productConfigList List<ProductConfig>
     *
     * @return List<RslStatusTrackingResponse> List<RslStatusTrackingResponse>
     */
    public List<RslStatusTrackingResponse> addImageUrlElement(List<RslStatusTrackingResponse> rslStatusTrackingResponseList, List<ProductConfig> productConfigList) {
        try {
            List<RslStatusTrackingResponse> result = new ArrayList<>();

            rslStatusTrackingResponseList.forEach(rstr -> {
                String productCode = rstr.getProductCode();
                List<ProductConfig> productConfigListResult = productConfigList.stream().filter(productConfig -> productConfig.getRslProductCode().equals(productCode)).collect(Collectors.toList());
                String imageUrl = "";
                if(!productConfigListResult.isEmpty()) {
                    ProductConfig productConfig = productConfigListResult.get(0);
                    imageUrl = productConfig.getIconId();
                }

                RslStatusTrackingResponse rslStatusTrackingResponse = new RslStatusTrackingResponse();
                rslStatusTrackingResponse.setReferenceNo(rstr.getReferenceNo());
                rslStatusTrackingResponse.setStatus(rstr.getStatus());
                rslStatusTrackingResponse.setAppType(rstr.getAppType());
                rslStatusTrackingResponse.setCurrentNode(rstr.getCurrentNode());
                rslStatusTrackingResponse.setNodeTextTh(rstr.getNodeTextTh());
                rslStatusTrackingResponse.setNodeTextEn(rstr.getNodeTextEn());
                rslStatusTrackingResponse.setTopRemarkTh(rstr.getTopRemarkTh());
                rslStatusTrackingResponse.setTopRemarkEn(rstr.getTopRemarkEn());
                rslStatusTrackingResponse.setBottomRemarkTh(rstr.getBottomRemarkTh());
                rslStatusTrackingResponse.setBottomRemarkEn(rstr.getBottomRemarkEn());
                rslStatusTrackingResponse.setApplicationDate(rstr.getApplicationDate());
                rslStatusTrackingResponse.setIsApproved(rstr.getIsApproved());
                rslStatusTrackingResponse.setIsRejected(rstr.getIsRejected());
                rslStatusTrackingResponse.setLastUpdateDate(rstr.getLastUpdateDate());
                rslStatusTrackingResponse.setProductCode(rstr.getProductCode());
                rslStatusTrackingResponse.setProductTypeTh(rstr.getProductTypeTh());
                rslStatusTrackingResponse.setProductTypeEn(rstr.getProductTypeEn());
                rslStatusTrackingResponse.setImageUrl(imageUrl);

                result.add(rslStatusTrackingResponse);
            });

            return result;
        } catch (Exception e) {
            logger.error("addImageUrlElement method Error : {} ", e);
        }

        return null;    //NOSONAR lightweight logging
    }

    /**
     * This method will change statusCode to statusText
     *
     * @param statusCode String
     *
     * @return String String
     */
    public String getStatus(String statusCode) {
        List<String> completedCodeList = Arrays.asList(new String[]{"MNSTP","PDSTP","STPCP","SFSTP"});  //NOSONAR lightweight logging
        List<String> inProgressCodeList = Arrays.asList(new String[]{"1STAP","1STRJ","ABRCK","RVWDV","ADTCK","ADTET","ADCET","ADCAP","ATVRF","FAVRF","FDSVF","RQADD","PD2CR","RVWAA","RVWDA","RVWIA","RVWRA","AADAD","AVRFA","FDSAD","P2CRA","RVAAA","RVDAA","RVIAA","RVRAA","AGPRP","MGAGP","MGAGS","CRCPD","RGSRQ","RGSRF","DRAFT","ABRTD","PHDCR"}); //NOSONAR lightweight logging
        List<String> inCompleteCodeList = Arrays.asList(new String[]{"INAPD","INDVD","INDVB","INDAP","INDCV","INCST","IDOFD","IDAFD","IDDFD","IDFDE","IDFDC","IDMIB","IDFSO","IDFVR","IDOFV","IDDVR","NDFVR","IADFA","ICOFA"}); //NOSONAR lightweight logging
        List<String> rejectedCodeList = Arrays.asList(new String[]{"FNRJD","RJ1DC","FRAAD","FRAVR","CLNAC"}); //NOSONAR lightweight logging
        List<String> expiredCodeList = Arrays.asList(new String[]{"EXNCC","EXINA","EXINC","EXDAV","EXIDD","EXIDV","EXDMB",}); //NOSONAR lightweight logging
        List<String> customerCancelCodeList = Arrays.asList(new String[]{"CANAD","CANVF","CANFF","OFDCL"}); //NOSONAR lightweight logging

        if(completedCodeList.contains(statusCode)) {
            return "completed";
        } else if(inProgressCodeList.contains(statusCode)) {
            return "in_progress";
        } else if(inCompleteCodeList.contains(statusCode)) {
            return "incomplete";
        } else if(rejectedCodeList.contains(statusCode)) {
            return "rejected";
        } else if(expiredCodeList.contains(statusCode)) {
            return "expired";
        } else if(customerCancelCodeList.contains(statusCode)) {
            return "customer_cancel";
        }

        return "";
    }

    /**
     * Fetch rsl status tracking from rsl service(TMB)
     *
     * @param citizenId citizen id of customer
     * @param mobileNo mobile number of customer
     * @param correlationId correlationId
     *
     * @return String result of rsl in String
     */
    @LogAround
    public String fetchRslStatusTracking(String citizenId, String mobileNo, String correlationId) {
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

        try {
            logger.info("fetchRslStatusTracking start time : {}", System.currentTimeMillis());
            ResponseEntity<String> response = rslStatusTrackingClient.getRslStatusTracking(rslRequest);

            return response.getBody();
        } catch (Exception e) {
            logger.error("fetchRslStatusTracking method Error(Data Not Found) : {} ", e);
        }

        return null;
    }

    /**
     * Method responsible for getting all product config info from common service
     *
     * @param correlationId correlate id of user action
     *
     * @return List<ProductConfig> List of ProductConfig model
     */
    @LogAround
    public List<ProductConfig> fetchProductConfig(String correlationId){
        try {
            logger.info("fetchProductConfig Calling Account Service start time : {}", System.currentTimeMillis());

            ResponseEntity<TmbOneServiceResponse<List<ProductConfig>>> response
                    = commonServiceFeignClient.getProductConfig(correlationId);

            if(response == null || response.getBody() == null || response.getBody().getData() == null) {    //NOSONAR lightweight logging
                return new ArrayList<>();
            } else {
                return response.getBody().getData();    //NOSONAR lightweight logging
            }
        } catch (Exception e) {
            logger.error("fetchProductConfig method Error(Bad Request) : {} ", e);
        }

        return new ArrayList<>();
    }

    /**
     * Method responsible for getting Line Message from MongoDB
     *
     * @param loanType type of loan from customer
     *
     * @return RslLineMessage RslLineMessage Model
     */
    @LogAround
    public RslMessage fetchMessage(String appStatus, String loanType){
        String appStatusWithLoanTypeNumber = appStatus + getTypeOfAppStatusNumber(loanType);
        String aggregateMatchCommand = "{$match:{'Desc_Detail." + appStatusWithLoanTypeNumber + ".status_code':\"" + appStatus + "\",'Desc_Detail." + appStatusWithLoanTypeNumber + ".loantype':\"" + loanType + "\"}}"; //NOSONAR lightweight logging
        String aggregateGroupCommand = "{$group:{_id:\"$_id\",\"group_msgth\":{\"$first\":\"$Desc_Detail." + appStatusWithLoanTypeNumber + ".group_msgth\"},\"group_msgen\":{\"$first\":\"$Desc_Detail." + appStatusWithLoanTypeNumber + ".group_msgen\"},\"line_descth\":{\"$first\":\"$Desc_Detail." + appStatusWithLoanTypeNumber + ".line_descth\"},\"line_descen\":{\"$first\":\"$Desc_Detail." + appStatusWithLoanTypeNumber + ".line_descen\"}}}"; //NOSONAR lightweight logging

        try {
            logger.info("fetchMessage Calling MongoDB start time : {}", System.currentTimeMillis());

            return rslMessageRepository.getMsg(appStatusWithLoanTypeNumber, appStatus, loanType);
        } catch (Exception e) {
            logger.error("fetchMessage method Error(Bad Request) : {} ", e);
        }

        return null;
    }

    /**
     * Method will change appStatus to typenumber.
     *
     * @param appStatus String
     *
     * @return String String
     */
    public String getTypeOfAppStatusNumber(String appStatus) {
        return (appStatus.equals("PL") || appStatus.equals("CC") || appStatus.equals("SP")) ? "1" : "2";
    }
}