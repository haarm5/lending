package com.tmb.oneapp.lendingservice.service;

import com.tmb.oneapp.lendingservice.LendingModuleCache;
import com.tmb.oneapp.lendingservice.constant.LoanCategory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@RunWith(JUnit4.class)
public class LendingModuleCacheTest {

    private LendingModuleCache lendingModuleCache;

    @Mock
    CodeEntriesService codeEntriesService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    void setupCachingSuccess() {
        when(codeEntriesService.loadEntry(any(), any(), any(), any())).thenReturn(new ArrayList<>());
        lendingModuleCache = new LendingModuleCache(codeEntriesService);
        lendingModuleCache.setupCaching();
        verify(codeEntriesService, times(LoanCategory.values().length)).loadEntry(any(), any(), any(), any());
    }
}
