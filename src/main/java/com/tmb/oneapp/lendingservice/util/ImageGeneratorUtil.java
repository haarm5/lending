package com.tmb.oneapp.lendingservice.util;

import com.tmb.common.logger.TMBLogger;
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
import org.xml.sax.SAXException;

import javax.imageio.*;
import javax.imageio.metadata.IIOInvalidTreeException;
import javax.imageio.metadata.IIOMetadata;
import javax.imageio.metadata.IIOMetadataNode;
import javax.imageio.stream.ImageOutputStream;
import javax.xml.transform.Result;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.sax.SAXResult;
import javax.xml.transform.stream.StreamSource;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.Iterator;


public class ImageGeneratorUtil {

    private static final TMBLogger<ImageGeneratorUtil> logger = new TMBLogger<>(ImageGeneratorUtil.class);

    public static String generateJPGFile(String jsonData, String xslTemplateName, String fileName){

        String currentDir = System.getProperty("user.dir");
        logger.info("currentDir is {} : "+currentDir);

        fileName = fileName + ".png";
        String fullpathfilename = currentDir + "/" +fileName;

        byte[] outputFilebytes = generateBytes(jsonData, xslTemplateName);

        try ( OutputStream finalOutputStream = new FileOutputStream(new File(fullpathfilename))) {

            finalOutputStream.write(outputFilebytes);
            logger.info("In generateJPGFile file written to {} ",fullpathfilename);
            return convertPngToJPGWithDPI(currentDir,fullpathfilename,fileName);
        } catch (FileNotFoundException e) {
            logger.error("FileNotFoundException in {} : ",e.toString());
        } catch (IOException e) {
            logger.error("IOException in {} : ",e.toString());
        }
        return null;
    }

    public static byte[] generateBytes(String request, String xslTemplateName) {
        logger.info("generatePDFBytes Method Starts! ");
        byte[] outputFilebytes = null;

        try {
            xslTemplateName = StringUtils.removeEndIgnoreCase(xslTemplateName, ".xsl");
            xslTemplateName =  xslTemplateName + ".xsl";

            // get xml from json string
            String xmlString = getXMLString(request);
            StreamSource dataSrc = new StreamSource(new java.io.StringReader(xmlString));
            outputFilebytes = createOutputFile(dataSrc, xslTemplateName, "fopcfg.xml");

        } catch (Exception e) {
            logger.error("Exception in generateBytes {} : ", e.toString());
        }
        logger.info("[generatePDFBytes] Method End! ");
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



    public static byte[] createOutputFile(StreamSource xmlDataStreamSource, String templateName, String fopConfigFileName) {

        ByteArrayOutputStream outputFileoutStream = null;

        String templatePath = "templates/" + templateName;
        String fopconfigFilePath = "templates/" + fopConfigFileName;
        File fopConfigFile = null;

        Fop fop;
        byte[] outputBytes = null;
        try {

            Resource resourceTemplate = new ClassPathResource(templatePath);
            File file = resourceTemplate.getFile();
            // create URL of the XSL template file
            URL templateUrl = file.toURI().toURL();
            logger.info("URL for template file is created {} : " + templateUrl);
            outputFileoutStream = new ByteArrayOutputStream();

            // creation of stream source from XSL input stream
            StreamSource templateFileTransformSource = new StreamSource(templateUrl.openStream());
            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer xslTransformer = transformerFactory.newTransformer(templateFileTransformSource);

            if (null != fopconfigFilePath) {
                Resource resourceFOP = new ClassPathResource(fopconfigFilePath);
                fopConfigFile = resourceFOP.getFile();
                FopFactory fopFactory = FopFactory.newInstance();
                FOUserAgent foUserAgent = fopFactory.newFOUserAgent();

               logger.info("FOP config File Path" + fopConfigFile.getAbsolutePath() + " does exist ? " + fopConfigFile.exists());
                if (fopConfigFile.exists()) {
                    String templatesFolderPath = fopConfigFile.getAbsolutePath().replace(fopConfigFileName,"");
                    logger.info("templatesFolderPath is {} : "+templatesFolderPath);
                    fopFactory.getFontManager().setFontBaseURL(templatesFolderPath);
                    fopFactory.setBaseURL(templatesFolderPath);
                    fopFactory.setUserConfig(fopConfigFile);
                    fopFactory.setUserConfigBaseURI(new URI("file://" + templatesFolderPath));
                    foUserAgent.setBaseURL(fopFactory.getBaseURL());
                }

                /**
                 *   hard coded as the fopcfg.xml value is not getting read properly. FOP frameword bug.
                 */
                foUserAgent.setTargetResolution(150.00f);
                fop = fopFactory.newFop(MimeConstants.MIME_PNG, foUserAgent, outputFileoutStream);

                Result transformationAsPdfResult = new SAXResult(fop.getDefaultHandler());
                logger.info("starting FOP File transformation");


                xslTransformer.transform(xmlDataStreamSource, transformationAsPdfResult);
                outputBytes = outputFileoutStream.toByteArray();


            }



            if (null != outputBytes) {
                logger.info("Completed FOP output transformation" + outputBytes.length);
            }

        } catch (IOException | TransformerException | SAXException | URISyntaxException e) {
            logger.error("Exception in createOutputFile {} ", e.toString());
        } finally {
            fopConfigFile = null;
            if (null != outputFileoutStream) {
                try {
                    outputFileoutStream.close();
                } catch (IOException e) {
                    outputFileoutStream = null;
                }
            }
        }

        return outputBytes;

    }

    public static String convertPngToJPGWithDPI(String folderName, String fullPngFilename, String actualFileName) {
        BufferedImage bufferedImage = null;
        File tempJPGFile = null;
        File actualJPGFile = null;

        logger.info("folderName "+folderName);
        logger.info("fullPngFilename "+fullPngFilename);
        logger.info("actualFileName "+actualFileName);
        try {

            File tempPngFile = new File(fullPngFilename);

            //read image file
            bufferedImage = ImageIO.read(tempPngFile);

            // create a blank, RGB, same width and height, and a white background
            BufferedImage newBufferedImage = new BufferedImage(bufferedImage.getWidth(),
                    bufferedImage.getHeight(), BufferedImage.TYPE_INT_RGB);
            newBufferedImage.createGraphics().drawImage(bufferedImage, 0, 0, Color.WHITE, null);
            bufferedImage.flush();

            String tempJPG = folderName+File.separator+actualFileName+"_temp.JPG";

            tempJPGFile = new File(tempJPG);

            // write to jpeg file
            ImageIO.write(newBufferedImage, "jpg", tempJPGFile);
            newBufferedImage.flush();
            actualFileName = actualFileName.replace(".png","");
            String actualJPG = folderName+ File.separator+ actualFileName+".JPG"; //File.separator+
            actualJPGFile = new File(actualJPG);

            // write to jpeg file
            ImageIO.write(newBufferedImage, "jpg", actualJPGFile);

            newBufferedImage.flush();

            saveGridImage(actualJPGFile, tempJPGFile);

            tempJPGFile.delete();
            tempPngFile.delete();

            logger.info("Location of LOC jpg image is {} ",actualJPG);
           return  actualJPG;
        } catch (IOException e) {

           logger.error("IOException {} : ",e.toString());
        }
        return null;
    }

    public static void saveGridImage(File output, File tempFile) throws IOException {
        output.delete();
        BufferedImage gridImage = null;
        final String formatName = "jpeg";

        for (Iterator<ImageWriter> iw = ImageIO.getImageWritersByFormatName(formatName); iw.hasNext();) {
            ImageWriter writer = iw.next();
            ImageWriteParam writeParam = writer.getDefaultWriteParam();
            ImageTypeSpecifier typeSpecifier = ImageTypeSpecifier.createFromBufferedImageType(BufferedImage.TYPE_INT_RGB);
            IIOMetadata metadata = writer.getDefaultImageMetadata(typeSpecifier, writeParam);
            if (metadata.isReadOnly() || !metadata.isStandardMetadataFormatSupported()) {
                continue;
            }

            setDPI(metadata);

            final ImageOutputStream stream = ImageIO.createImageOutputStream(output);
            try {
                writer.setOutput(stream);
                gridImage = ImageIO.read(tempFile);
                writer.write(metadata, new IIOImage(gridImage, null, metadata), writeParam);

            } finally {
                stream.close();
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

}
