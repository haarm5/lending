package com.tmb.oneapp.lendingservice.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.oxm.jaxb.Jaxb2Marshaller;

import com.tmb.oneapp.lendingservice.SOAPConnector;

//@Configuration
public class SoapConfig {

//	@Bean
//	public Jaxb2Marshaller marshaller() {
//		Jaxb2Marshaller marshaller = new Jaxb2Marshaller();
////		marshaller.setContextPath("com.integrosys.sml.common.ob.dropdown");
//		marshaller.setContextPaths("com.integrosys.sml.common.ob.dropdown", "com.integrosys.sml.ws.common",
//				"com.integrosys.sml.ws.dropdown.request", "com.integrosys.sml.ws.dropdown.response",
//				"com.integrosys.sml.ws.loan.submission");
//		return marshaller;
//	}
//
//	@Bean
//	public SOAPConnector soapConnector(Jaxb2Marshaller marshaller) {
//		SOAPConnector client = new SOAPConnector();
//		client.setDefaultUri("http://10.209.27.99:9081/LoanSubmissionWS");
//		client.setMarshaller(marshaller);
//		client.setUnmarshaller(marshaller);
//		return client;
//	}

}
