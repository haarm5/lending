package com.tmb.oneapp.lendingservice.service;

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
import java.nio.file.Files;
import java.nio.file.NoSuchFileException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

@Service
public class ImageGeneratorService {
    private static final TMBLogger<ImageGeneratorService> logger = new TMBLogger<>(ImageGeneratorService.class);
    @Autowired
    private ObjectMapper mapper;
    @Autowired
    private FopFactory fopFactory;
    @Autowired
    private TransformerFactory transformerFactory;

    public ImageGeneratorService(ObjectMapper mapper, FopFactory fopFactory, TransformerFactory transformerFactory) {
        this.mapper = mapper;
        this.fopFactory = fopFactory;
        this.transformerFactory = transformerFactory;
    }

    private static final String MIME_PNG = "image/png";

    /**
     * generate consent image
     *
     * @param jsonData
     * @param fileName
     * @return
     * @throws IOException
     * @throws FOPException
     * @throws TransformerException
     */
    private String generateJPGFile(String jsonData, String fileName) throws IOException, FOPException, TransformerException {
        FOUserAgent userAgent = fopFactory.newFOUserAgent();// FOUserAgent can be used to set user configurable options
        String baseDir = System.getProperty("user.dir");
        userAgent.getRendererOptions().put(
                "target-bitmap-size", new Dimension(1200, 1650));
        File outputDir = new File(baseDir + File.separator + "images");
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
            logger.info("generated png consent image success:{}", pngFile.getAbsolutePath());
        }

        String jpgFIle = convertPngToJPG(baseDir + File.separator + "images" + File.separator + fileName + ".png");
        try{
            Files.delete(Paths.get(pngFile.getAbsolutePath()));
        }catch (NoSuchFileException e){
            logger.info("cannot delete png file: {}", e);
        }
        return jpgFIle;
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

    /**
     * convert png to jpt image
     *
     * @param fullPathPngFilename
     * @return
     * @throws IOException
     */
    private String convertPngToJPG(String fullPathPngFilename) throws IOException {
        String jpgFullPathFileName = fullPathPngFilename.replace(".png", ".JPG");
        Path source = Paths.get(fullPathPngFilename);
        Path target = Paths.get(jpgFullPathFileName);

        BufferedImage originalImage = ImageIO.read(source.toFile());
        BufferedImage newBufferedImage = new BufferedImage(
                originalImage.getWidth(),
                originalImage.getHeight(),
                BufferedImage.TYPE_INT_RGB);
        newBufferedImage.createGraphics()
                .drawImage(originalImage,
                        0,
                        0,
                        Color.WHITE,
                        null);
        ImageIO.write(newBufferedImage, "jpg", target.toFile());
        newBufferedImage.flush();
        logger.info("convert consent image png to jpg  success:{}", jpgFullPathFileName);
        return jpgFullPathFileName;
    }

    /**
     * Generate consent image
     *
     * @param request
     * @return
     */
    public String generateLOCImage(LOCRequest request) {
        try {
            Date dateObj = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss").parse(request.getCreateDate());
            String dateStr = CommonServiceUtils.getDateAndTimeInYYMMDDHHMMSS(dateObj);
            String fileName = "01" + LendingServiceConstant.UNDER_SCORE + dateStr + LendingServiceConstant.UNDER_SCORE + request.getAppRefNo() + LendingServiceConstant.UNDER_SCORE + "00110";
            String jsonData = mapper.writeValueAsString(request);
            return generateJPGFile(jsonData, fileName);
        } catch (ParseException | IOException | FOPException | TransformerException e) {
            logger.error("generate image got error:{}", e);
        }
        return null;
    }
}
