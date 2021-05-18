package com.tmb.oneapp.lendingservice.client;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;

@RunWith(JUnit4.class)
public class RslStatusTrackingClientTest {
	

	RslStatusTrackingClient trackingClient;
	
	@BeforeEach
	void setUp() {
		MockitoAnnotations.initMocks(this);
	}
	
	@Test
	public void statusTracking() {
		trackingClient = new RslStatusTrackingClient();
		ResponseEntity<String> response =  trackingClient.getRslStatusTracking(null);
		Assertions.assertNull(response);
	}

	
}
