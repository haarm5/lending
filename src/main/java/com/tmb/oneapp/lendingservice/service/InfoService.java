package com.tmb.oneapp.lendingservice.service;

import com.tmb.common.logger.TMBLogger;
import com.tmb.common.model.legacy.rsl.common.ob.dropdown.CommonCodeEntry;
import com.tmb.oneapp.lendingservice.LendingModuleCache;
import com.tmb.oneapp.lendingservice.model.ServiceResponse;
import com.tmb.oneapp.lendingservice.model.ServiceResponseImp;
import com.tmb.oneapp.lendingservice.model.info.MasterDataResponse;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Provides lending information services.
 */
@Service
public class InfoService {

    private final LendingModuleCache lendingModuleCache;
    private static final TMBLogger<InfoService> logger = new TMBLogger<>(InfoService.class);

    public InfoService(LendingModuleCache lendingModuleCache){
        this.lendingModuleCache = lendingModuleCache;
    }

    /**
     * Returns master data according to the codes.
     * @param masterDataKeys
     * @return
     */
    public ServiceResponse fetchMasterData(List<String> masterDataKeys){
        logger.info("request master data:"+String.join(",", masterDataKeys));
        MasterDataResponse response = new MasterDataResponse();
        Map<String, List<CommonCodeEntry>> masterData = new HashMap<>();
        response.setMasterData(masterData);
        masterDataKeys.forEach(s -> {
            List<CommonCodeEntry> masterDataItem  = lendingModuleCache.getListByCategoryCode(s);
            if(masterDataItem==null){
                logger.info("no master data in cache:"+s);
            }
            masterData.put(s, masterDataItem);
        });
        ServiceResponseImp serviceResponseImp = new ServiceResponseImp();
        serviceResponseImp.setData(response);
        return serviceResponseImp;
    }
}
