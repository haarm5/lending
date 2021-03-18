package com.tmb.oneapp.lendingservice.service;

import com.tmb.common.logger.LogAround;
import com.tmb.common.logger.TMBLogger;
import com.tmb.oneapp.lendingservice.client.RslStatusTrackingClient;
import com.tmb.oneapp.lendingservice.model.RslStatusTrackingResponse;
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
import java.util.List;

/**
 * RslStatusTrackingService class will get Customer RSL Status from customer
 */
@Service
public class RslStatusTrackingService {
    private static final TMBLogger<RslStatusTrackingService> logger = new TMBLogger<>(RslStatusTrackingService.class);

    /**
     * Constructor
     */
    @Autowired
    public RslStatusTrackingService() {
        //Empty Constructor
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

            return getRslStatusTrackingResponse(result);
        } catch (Exception e) {
            logger.error("getRslStatusTracking method Error(Data Not Found) : {} ", e);
            return new ArrayList<>();
        }
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
            ResponseEntity<String> response = new RslStatusTrackingClient().getRslStatusTracking(rslRequest);

            return response.getBody();
        } catch (Exception e) {
            logger.error("fetchRslStatusTracking method Error(Data Not Found) : {} ", e);
        }

        return null;
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

            NodeList applicationsList = doc.getElementsByTagName("application");
            for(int i=0; i<applicationsList.getLength(); i++) {
                Node applicationNode = applicationsList.item(i);
                if(applicationNode.getParentNode().getNodeName().equals("application") && applicationNode.getNodeType() == Node.ELEMENT_NODE)
                {
                    RslStatusTrackingResponse rslStatusTrackingResponse = new RslStatusTrackingResponse();

                    Element applicationElement = (Element) applicationNode;

                    rslStatusTrackingResponse.setReferenceNo(applicationElement.getElementsByTagName("appRefNo").item(0).getTextContent());
                    rslStatusTrackingResponse.setAppStatus(applicationElement.getElementsByTagName("appStatus").item(0).getTextContent());
                    rslStatusTrackingResponse.setCurrentNode(applicationElement.getElementsByTagName("currentNode").item(0).getTextContent());
                    rslStatusTrackingResponse.setApplicationDate(applicationElement.getElementsByTagName("applicationDate").item(0).getTextContent());
                    rslStatusTrackingResponse.setIsApproved(applicationElement.getElementsByTagName("isApproved").item(0).getTextContent());
                    rslStatusTrackingResponse.setIsRejected(applicationElement.getElementsByTagName("isRejected").item(0).getTextContent());
                    rslStatusTrackingResponse.setLastUpdateDate(applicationElement.getElementsByTagName("lastUpdateDate").item(0).getTextContent());

                    NodeList productsList = applicationElement.getElementsByTagName("products");
                    for(int j=0; j<productsList.getLength(); j++) {
                        Node productNode = productsList.item(j);
                        if(productNode.getParentNode().getNodeName().equals("products") && productNode.getNodeType() == Node.ELEMENT_NODE) {
                            Element productElement = (Element) productNode;

                            rslStatusTrackingResponse.setProductCode(productElement.getElementsByTagName("productCode").item(0).getTextContent());
                            rslStatusTrackingResponse.setProductTypeTh(productElement.getElementsByTagName("productDescTH").item(0).getTextContent());
                            rslStatusTrackingResponse.setProductTypeEn(productElement.getElementsByTagName("productDescEN").item(0).getTextContent());
                        }
                    }

                    List<String> nodeTextTh = new ArrayList<>();
                    List<String> nodeTextEn = new ArrayList<>();

                    NodeList roadMapList = applicationElement.getElementsByTagName("roadMap");
                    for(int j=0; j<roadMapList.getLength(); j++) {
                        Node roadMapNode = roadMapList.item(j);
                        if(roadMapNode.getParentNode().getNodeName().equals("roadMap") && roadMapNode.getNodeType() == Node.ELEMENT_NODE) {
                            Element roadMapElement = (Element) roadMapNode;

                            nodeTextTh.add(roadMapElement.getElementsByTagName("defaultDescTh").item(0).getTextContent());
                            nodeTextEn.add(roadMapElement.getElementsByTagName("defaultDescEn").item(0).getTextContent());
                        }
                    }

                    rslStatusTrackingResponse.setNodeTextTh(nodeTextTh);
                    rslStatusTrackingResponse.setNodeTextEn(nodeTextEn);

                    rslStatusTrackingResponseList.add(rslStatusTrackingResponse);
                }
            }

        } catch (Exception e) {
            logger.error("getRslStatusTrackingResponseFromStringXML method Error(Data Not Found) : {} ", e);
        }

        return rslStatusTrackingResponseList;
    }
}