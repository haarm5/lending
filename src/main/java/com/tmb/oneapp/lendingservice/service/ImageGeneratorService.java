package com.tmb.oneapp.lendingservice.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.tmb.common.logger.TMBLogger;
import com.tmb.oneapp.lendingservice.constant.LendingServiceConstant;
import com.tmb.oneapp.lendingservice.model.instantloancreation.LOCRequest;
import com.tmb.oneapp.lendingservice.util.CommonServiceUtils;
import org.apache.fop.apps.FOPException;
import org.apache.fop.apps.FOUserAgent;
import org.apache.fop.apps.Fop;
import org.apache.fop.apps.FopFactory;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.XML;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.imageio.ImageIO;
import javax.xml.transform.Result;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.sax.SAXResult;
import javax.xml.transform.stream.StreamSource;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.nio.file.Path;
import java.nio.file.Paths;

@Service
public class ImageGeneratorService {
    private static final TMBLogger<ImageGeneratorService> logger = new TMBLogger<>(ImageGeneratorService.class);
    @Autowired
    private ObjectMapper mapper;
    @Autowired
    private FopFactory fopFactory;
    @Autowired
    private TransformerFactory transformerFactory;

    private static final String MIME_PNG = "image/png";


    private String generateJPGFile(String jsonData, String fileName) throws IOException, FOPException, TransformerException {
        FOUserAgent userAgent = fopFactory.newFOUserAgent();// FOUserAgent can be used to set user configurable options
        File outputDir = new File("./images");
        outputDir.mkdir();
        File pngFile = new File(outputDir, fileName + ".png");

        String xmlString = getXMLString(jsonData);
        StreamSource data = new StreamSource(new java.io.StringReader(xmlString));
        try (OutputStream out = new FileOutputStream(pngFile);
             BufferedOutputStream buffOut = new BufferedOutputStream(out)) {

            Fop fop = fopFactory.newFop(MIME_PNG, userAgent, buffOut);
            Transformer transformer = transformerFactory.newTransformer(new StreamSource(new File("./fop/InstantLoanNCBConsentTH.xsl")));
            Result res = new SAXResult(fop.getDefaultHandler());
            transformer.transform(data, res);
        }
        return convertPngToJPG("./images/" + fileName + ".png");
    }

    private String getXMLString(String jsonDataString) {
        String xmlString = "";
        try {
            xmlString = XML.toString(new JSONObject(jsonDataString), "customroot");
        } catch (JSONException e) {
            logger.info("JSONException in getXMLString {}", e.toString());
        }
        return xmlString;
    }


    private String convertPngToJPG(String fullPathPngFilename) throws IOException {
        String jpgFullPathFileName = fullPathPngFilename.replace(".png", ".jpg");
        Path source = Paths.get(fullPathPngFilename);
        Path target = Paths.get(jpgFullPathFileName);

        BufferedImage originalImage = ImageIO.read(source.toFile());
        BufferedImage newBufferedImage = new BufferedImage(
                originalImage.getWidth(),
                originalImage.getHeight(),
                BufferedImage.TYPE_INT_RGB);
        // draw a white background and puts the originalImage on it.
        newBufferedImage.createGraphics()
                .drawImage(originalImage,
                        0,
                        0,
                        Color.WHITE,
                        null);
        ImageIO.write(newBufferedImage, "jpg", target.toFile());
        newBufferedImage.flush();
        return jpgFullPathFileName;
    }

    /**
     * private File getFileFromResource(String fileName) throws URISyntaxException{
     * <p>
     * ClassLoader classLoader = getClass().getClassLoader();
     * URL resource = classLoader.getResource(fileName);
     * <p>
     * if (resource == null) {
     * throw new IllegalArgumentException("file not found! " + fileName);
     * } else {
     * return new File(resource.toURI());
     * }
     * <p>
     * }
     **/

    public String generateLOCImage(LOCRequest request) throws JsonProcessingException {
        String dateStr = CommonServiceUtils.getDateAndTimeInYYYYMMDDHHMMSS();
        dateStr = dateStr.replaceAll("[/: ]", "");
        dateStr = dateStr.substring(2);
        String fileName = "01" + LendingServiceConstant.UNDER_SCORE + dateStr + LendingServiceConstant.UNDER_SCORE + request.getAppRefNo() + LendingServiceConstant.UNDER_SCORE + "00110";
        String jsonData = mapper.writeValueAsString(request);
        try {
            return generateJPGFile(jsonData, fileName);
        } catch (IOException | FOPException | TransformerException e) {
            logger.error("generate image got error:", e);
        }
        return null;
    }
}
