package com.tmb.oneapp.lendingservice.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import com.tmb.oneapp.lendingservice.client.SFTPClientImp;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

@RunWith(JUnit4.class)
public class PurgeDataServiceTest {

	@Mock
	SFTPClientImp sftpClient;

	@InjectMocks
	PurgeDataService purgeDataService;

	@BeforeEach
	void setUp() {
		MockitoAnnotations.initMocks(this);
		purgeDataService = new PurgeDataService(sftpClient);
		purgeDataService.setPurgeAfterDay("10");
		purgeDataService.setPathLOC("");
		purgeDataService.setPathDocuments("");
	}

	@Test
	void purgeDataSuccess() {
		Mockito.when(sftpClient.purgeFileOlderThanNDays("", 10L)).thenReturn(true);
		boolean actualResult = purgeDataService.purgeData();
		assertTrue(actualResult);
		verify(sftpClient, times(2)).purgeFileOlderThanNDays("", 10L);
	}

	@Test
	void purgeDataFailed() {
		Mockito.when(sftpClient.purgeFileOlderThanNDays("", 10L)).thenReturn(false);
		boolean actualResult = purgeDataService.purgeData();
		assertFalse(actualResult);
		verify(sftpClient, times(1)).purgeFileOlderThanNDays("", 10L);
	}

}
