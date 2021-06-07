package com.tmb.oneapp.lendingservice.controller;

import org.apache.fop.apps.*;
import org.apache.fop.render.RendererFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ResourceLoaderAware;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.sax.SAXResult;
import javax.xml.transform.stream.StreamSource;
import java.io.IOException;
import java.io.InputStream;
import java.io.StringReader;

@RestController
public class ImageController implements ResourceLoaderAware {
    private ResourceLoader resourceLoader;
    @Autowired
    FopFactory fopFactory;

    @Autowired
    TransformerFactory transformerFactory;

    @PostMapping(value = "/pdf")
    public void getPdf(@RequestParam(defaultValue = "12345678", name = "code") String code,
                       final HttpServletResponse response) throws FOPException, IOException, TransformerException {



        FOUserAgent userAgent = fopFactory.newFOUserAgent();// FOUserAgent can be used to set user configurable options

        Fop fop = fopFactory.newFop(MimeConstants.MIME_PDF, userAgent, response.getOutputStream());


        // Start FOP processing
        Transformer transformer = transformerFactory.newTransformer(new StreamSource(getTestXsl()));
        transformer.transform(getSource(code),
                new SAXResult(fop.getDefaultHandler()));
    }
    private InputStream getTestXsl() throws IOException {
        Resource testFoFileResource = this.resourceLoader.getResource("classpath:fop/InstantLoanNCBConsentTH.xsl");
        return testFoFileResource.getInputStream();
    }
    private StreamSource getSource(final String codeValue) {
        String xmlObject = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n" +
                "<customroot>\n" +
                "    <NCBCustName>Phanlop Pengsupan</NCBCustName>\n" +
                "    <NCBID>1234567890</NCBID>\n" +
                "    <NCBDateofbirth>23 Apr 1985</NCBDateofbirth>\n" +
                "    <ProductName>Cash 2 Go</ProductName>\n" +
                "    <NCBReferenceID>abc12345</NCBReferenceID>\n" +
                "    <NCBDateTime>15:33:40</NCBDateTime>\n" +
                "    <ConsentbyCustomer>TEST</ConsentbyCustomer>\n" +
                "</customroot>";
        return new StreamSource(new StringReader(xmlObject));
    }




    @Override
    public void setResourceLoader(ResourceLoader resourceLoader) {
        this.resourceLoader = resourceLoader;
    }
}
