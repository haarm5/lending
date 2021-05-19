package com.tmb.oneapp.lendingservice.service;

import com.tmb.common.model.legacy.rsl.common.ob.dropdown.CommonCodeEntry;
import com.tmb.oneapp.lendingservice.LendingModuleCache;
import com.tmb.oneapp.lendingservice.model.ServiceResponse;
import com.tmb.oneapp.lendingservice.model.info.MasterDataResponse;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@RunWith(JUnit4.class)
public class InfoServiceTest {

    @Mock
    LendingModuleCache lendingModuleCache;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    void fetchMasterDataSuccess() {
        List<CommonCodeEntry> master26 = new ArrayList<>();
        master26.add(new CommonCodeEntry());

        when(lendingModuleCache.getListByCategoryCode("26")).thenReturn(master26);
        List<CommonCodeEntry> master27 = new ArrayList<>();
        master27.add(new CommonCodeEntry());
        master27.add(new CommonCodeEntry());
        when(lendingModuleCache.getListByCategoryCode("27")).thenReturn(master27);
        InfoService infoService = new InfoService(lendingModuleCache);
        List<String> request = new ArrayList<>();
        request.add("26");
        request.add("27");
        ServiceResponse actualResult = infoService.fetchMasterData(request);
        Assertions.assertNotNull(actualResult);
        Assertions.assertNull(actualResult.getError());
        MasterDataResponse masterDataResponse = (MasterDataResponse) actualResult.getData();
        Assertions.assertNotNull(masterDataResponse);
        List<CommonCodeEntry> actualMaster26 = masterDataResponse.getMasterData().get("26");
        List<CommonCodeEntry> actualMaster27 = masterDataResponse.getMasterData().get("27");
        Assertions.assertNotNull(actualMaster26);
        Assertions.assertNotNull(actualMaster27);
        Assertions.assertEquals(1,actualMaster26.size());
        Assertions.assertEquals(2,actualMaster27.size());
        verify(lendingModuleCache, times(1)).getListByCategoryCode("26");
        verify(lendingModuleCache, times(1)).getListByCategoryCode("27");
    }
}
