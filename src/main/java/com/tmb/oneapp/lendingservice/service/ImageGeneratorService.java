package com.tmb.oneapp.lendingservice.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.tmb.common.logger.TMBLogger;
import com.tmb.oneapp.lendingservice.client.FTPServerLOCClient;
import com.tmb.oneapp.lendingservice.constant.LendingServiceConstant;
import com.tmb.oneapp.lendingservice.model.instantloancreation.LOCRequest;
import com.tmb.oneapp.lendingservice.util.CommonServiceUtils;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.fop.apps.FOUserAgent;
import org.apache.fop.apps.Fop;
import org.apache.fop.apps.FopFactory;
import org.apache.xmlgraphics.util.MimeConstants;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.XML;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;
import org.xml.sax.SAXException;

import javax.imageio.*;
import javax.imageio.metadata.IIOInvalidTreeException;
import javax.imageio.metadata.IIOMetadata;
import javax.imageio.metadata.IIOMetadataNode;
import javax.imageio.stream.ImageOutputStream;
import javax.xml.XMLConstants;
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
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Iterator;

@Service
public class ImageGeneratorService {
    private static final TMBLogger<ImageGeneratorService> logger = new TMBLogger<>(ImageGeneratorService.class);
    private final ObjectMapper mapper;
    private final FTPServerLOCClient ftpServerLOCClient;

    public ImageGeneratorService(ObjectMapper mapper, FTPServerLOCClient ftpServerLOCClient) {
        this.mapper = mapper;
        this.ftpServerLOCClient = ftpServerLOCClient;
    }

    public String generateJPGFile(String jsonData, String xslTemplateName, String fileName) {

        String currentDir = System.getProperty("user.dir");
        logger.info("currentDir is {} : " + currentDir);

        fileName = fileName + ".png";
        String fullpathfilename = currentDir + File.separator + fileName;

        byte[] outputFilebytes = generateBytes(jsonData, xslTemplateName);

        try (OutputStream finalOutputStream = new FileOutputStream(new File(fullpathfilename))) {

            finalOutputStream.write(outputFilebytes);
            logger.info("In generateJPGFile file written to {} ", fullpathfilename);
            return convertPngToJPGWithDPI(currentDir, fullpathfilename, fileName);
        } catch (FileNotFoundException e) {
            logger.error("FileNotFoundException in {} : ", e.toString());
        } catch (IOException e) {
            logger.error("IOException in {} : ", e.toString());
        }
        return null;
    }

    public byte[] generateBytes(String request, String xslTemplateName) {
        logger.info("generateBytes Method Starts! ");
        byte[] outputFilebytes = null;

        try {
            xslTemplateName = StringUtils.removeEndIgnoreCase(xslTemplateName, ".xsl");
            xslTemplateName = xslTemplateName + ".xsl";

            // get xml from json string
            String xmlString = getXMLString(request);
            StreamSource dataSrc = new StreamSource(new java.io.StringReader(xmlString));
            outputFilebytes = createOutputFile(dataSrc, xslTemplateName, "fopcfg.xml");

        } catch (Exception e) {
            logger.error("Exception in generateBytes {} : ", e.toString());
        }
        logger.info("generateBytes Method End! ");
        return outputFilebytes;
    }

    public static String getXMLString(String jsonDataString) {
        String xmlString = "";
        try {
            xmlString = XML.toString(new JSONObject(jsonDataString), "customroot");
        } catch (JSONException e) {
            logger.info("JSONException in getXMLString {}", e.toString());
        }
        return xmlString;
    }


    public byte[] createOutputFile(StreamSource xmlDataStreamSource, String templateName, String fopConfigFileName) {

        String templatePath = "templates/" + templateName;
        String fopconfigFilePath = "templates/" + fopConfigFileName;


        Fop fop;
        byte[] outputBytes = null;

        try (ByteArrayOutputStream outputFileoutStream = new ByteArrayOutputStream()) {

            //File file = getFileFromResourceAsStream(templatePath,1);
            // fopConfigFile = getFileFromResourceAsStream(fopconfigFilePath,2);

            Resource resourceDirectory = new ClassPathResource("templates/");
            Resource resourceConfig = new ClassPathResource(fopconfigFilePath);

            Resource resourcetemplateNameFile = new ClassPathResource(templatePath);



            // create URL of the XSL template file


            FopFactory fopFactory = FopFactory.newInstance(new File("."));
            FOUserAgent foUserAgent = fopFactory.newFOUserAgent();




            logger.info("templatesFolderPath of resource is {} : " +resourceDirectory.getURI().getPath());
//            fopFactory.getFontManager().setFontBaseURL(resourceDirectory.getURI().getPath());
//            fopFactory.setUserConfig(resourceConfig.getURI().getPath());
//            fopFactory.setUserConfigBaseURI(resourceConfig.getURI());
//            foUserAgent.setBaseURL(fopFactory.getBaseURL());


            /**
             *   hard coded, as the fopcfg.xml value is not getting read properly. FOP frameword bug.
             */
            foUserAgent.setTargetResolution(150.00f);
            fop = fopFactory.newFop(MimeConstants.MIME_PNG, foUserAgent, outputFileoutStream);


            // creation of stream source from XSL input stream
            StreamSource templateFileTransformSource = new StreamSource(resourcetemplateNameFile.getInputStream());
            javax.xml.transform.TransformerFactory transformerFactory = TransformerFactory.newInstance();
            transformerFactory.setFeature(XMLConstants.FEATURE_SECURE_PROCESSING, true);

            Transformer xslTransformer = transformerFactory.newTransformer(templateFileTransformSource);

            Result transformationAsImageResult = new SAXResult(fop.getDefaultHandler());
            logger.info("starting FOP File transformation");


            xslTransformer.transform(xmlDataStreamSource, transformationAsImageResult);
            outputBytes = outputFileoutStream.toByteArray();

            logger.info("Completed FOP output transformation" + outputBytes.length);


        } catch (IOException | TransformerException | SAXException e) {
            logger.error("Exception in createOutputFile {} ", e.toString());
        }

        return outputBytes;

    }

    public static String convertPngToJPGWithDPI(String folderName, String fullPngFilename, String actualFileName) {

        logger.info("folderName " + folderName);
        logger.info("fullPngFilename " + fullPngFilename);
        logger.info("actualFileName " + actualFileName);
        try {

            File tempPngFile = new File(fullPngFilename);

            //read image file
            BufferedImage bufferedImage = ImageIO.read(tempPngFile);

            // create a blank, RGB, same width and height, and a white background
            BufferedImage newBufferedImage = new BufferedImage(bufferedImage.getWidth(),
                    bufferedImage.getHeight(), BufferedImage.TYPE_INT_RGB);
            newBufferedImage.createGraphics().drawImage(bufferedImage, 0, 0, Color.WHITE, null);
            bufferedImage.flush();

            String tempJPG = folderName + File.separator + actualFileName + "_temp.JPG";

            File tempJPGFile = new File(tempJPG);

            // write to jpeg file
            ImageIO.write(newBufferedImage, "jpg", tempJPGFile);
            newBufferedImage.flush();
            actualFileName = actualFileName.replace(".png", "");
            String actualJPG = folderName + File.separator + actualFileName + ".JPG"; //File.separator+
            File actualJPGFile = new File(actualJPG);

            // write to jpeg file
            ImageIO.write(newBufferedImage, "jpg", actualJPGFile);

            newBufferedImage.flush();

            saveGridImage(actualJPGFile, tempJPGFile);

            Path tempJPGFilePath = Paths.get(tempJPG);
            Files.delete(tempJPGFilePath);
            // Files.delete(Paths.get(fullPngFilename));

            logger.info("Location of LOC jpg image is {} ", actualJPG);
            return actualJPG;
        } catch (IOException e) {

            logger.error("IOException {} : ", e.toString());
        }
        return null;
    }

    public static void saveGridImage(File output, File tempFile) throws IOException {

        Path outputFilePath = Paths.get(output.getAbsolutePath());
        Files.delete(outputFilePath);
        final String formatName = "jpeg";

        for (Iterator<ImageWriter> iw = ImageIO.getImageWritersByFormatName(formatName); iw.hasNext(); ) {
            ImageWriter writer = iw.next();
            ImageWriteParam writeParam = writer.getDefaultWriteParam();
            ImageTypeSpecifier typeSpecifier = ImageTypeSpecifier.createFromBufferedImageType(BufferedImage.TYPE_INT_RGB);
            IIOMetadata metadata = writer.getDefaultImageMetadata(typeSpecifier, writeParam);
            if (metadata.isReadOnly() || !metadata.isStandardMetadataFormatSupported()) {
                continue;
            }

            setDPI(metadata);
            try (ImageOutputStream stream = ImageIO.createImageOutputStream(output)) {
                writer.setOutput(stream);
                BufferedImage gridImage = ImageIO.read(tempFile);
                writer.write(metadata, new IIOImage(gridImage, null, metadata), writeParam);

            }
            break;
        }
    }

    private static void setDPI(IIOMetadata metadata) throws IIOInvalidTreeException {
        String dpi = "200";
        String metadataFormat = "javax_imageio_jpeg_image_1.0";
        IIOMetadataNode root = new IIOMetadataNode(metadataFormat);
        IIOMetadataNode jpegVariety = new IIOMetadataNode("JPEGvariety");
        IIOMetadataNode markerSequence = new IIOMetadataNode("markerSequence");

        IIOMetadataNode app0JFIF = new IIOMetadataNode("app0JFIF");
        app0JFIF.setAttribute("majorVersion", "1");
        app0JFIF.setAttribute("minorVersion", "2");
        app0JFIF.setAttribute("thumbWidth", "0");
        app0JFIF.setAttribute("thumbHeight", "0");
        app0JFIF.setAttribute("resUnits", "01"); // DENSITY_UNITS_PIXELS_PER_INCH = 01
        app0JFIF.setAttribute("Xdensity", dpi);
        app0JFIF.setAttribute("Ydensity", dpi);

        root.appendChild(jpegVariety);
        root.appendChild(markerSequence);
        jpegVariety.appendChild(app0JFIF);

        metadata.mergeTree(metadataFormat, root);
    }

    private File getFileFromResourceAsStream(String fileName, int fileType) throws IOException {

        ClassLoader classLoader = getClass().getClassLoader();
        InputStream resource = classLoader.getResourceAsStream(fileName);
        if (resource == null) {
            throw new IllegalArgumentException("file not found! " + fileName);
        } else {
            String currentDir = System.getProperty("user.dir");
            logger.info("currentDir is {} : " + currentDir);
            String fileNameTmp = currentDir + "/fopconfig.xml";
            if (fileType == 1) {
                fileNameTmp = currentDir + "/InstantLoanNCBConsentTH.xsl";
            }

            File targetFile = new File(fileNameTmp);
            FileUtils.copyInputStreamToFile(resource, targetFile);
            return targetFile;

        }

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

    public void generateLOCImageAndUploadToFTP(LOCRequest request) throws JsonProcessingException {

        logger.info("generateLOCImageAndUploadToFTP start");
        String dateStr = CommonServiceUtils.getDateAndTimeInYYYYMMDDHHMMSS();
        logger.info("dateStr {} : " + dateStr);
        dateStr = dateStr.replaceAll("[/: ]", "");
        dateStr = dateStr.substring(2);
        String fileName = "01" + LendingServiceConstant.UNDER_SCORE + dateStr + LendingServiceConstant.UNDER_SCORE + request.getAppRefNo() + LendingServiceConstant.UNDER_SCORE + "00110";
        String jsonData = mapper.writeValueAsString(request);
        String jpgSourcePath = generateJPGFile(jsonData, "InstantLoanNCBConsentTH", fileName);
        if (jpgSourcePath != null) {
            String jpgFileName = fileName + ".JPG";
            String directoryPath = request.getCrmId() + File.separator + request.getAppRefNo();
            ftpServerLOCClient.uploadFileToFTP(jpgSourcePath, jpgFileName, directoryPath);
        }

        logger.info("generateLOCImageAndUploadToFTP END");
    }
}
