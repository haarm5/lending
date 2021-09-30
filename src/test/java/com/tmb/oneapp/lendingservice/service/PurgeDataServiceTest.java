package com.tmb.oneapp.lendingservice.service;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import com.tmb.oneapp.lendingservice.client.FTPClient;

@RunWith(JUnit4.class)
public class PurgeDataServiceTest {

	@Mock
	FTPClient ftpClient;

	@InjectMocks
	PurgeDataService purgeDataService;

	@BeforeEach
	void setUp() {
		MockitoAnnotations.initMocks(this);
		purgeDataService = new PurgeDataService(ftpClient);
		purgeDataService.setPurgeAfterDay("10");
		purgeDataService.setPathLOC("");
		purgeDataService.setPathDocuments("");
	}

	@Test
	void purgeDataSuccess() {
		Mockito.when(ftpClient.purgeFileOlderThanNDays("", 10L)).thenReturn(true);
		boolean actualResult = purgeDataService.purgeData();
		Assertions.assertNotNull(actualResult);
		Assertions.assertEquals(true, actualResult);
		verify(ftpClient, times(2)).purgeFileOlderThanNDays("", 10L);
	}

	@Test
	void purgeDataFailed() {
		Mockito.when(ftpClient.purgeFileOlderThanNDays("", 10L)).thenReturn(false);
		boolean actualResult = purgeDataService.purgeData();
		Assertions.assertNotNull(actualResult);
		Assertions.assertEquals(false, actualResult);
		verify(ftpClient, times(1)).purgeFileOlderThanNDays("", 10L);
	}

}
