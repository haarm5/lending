package com.tmb.oneapp.lendingservice;

import java.util.HashMap;
import java.util.List;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.tmb.common.logger.TMBLogger;
import com.tmb.common.model.legacy.rsl.common.ob.dropdown.CommonCodeEntry;
import com.tmb.oneapp.lendingservice.constant.LoanCategory;
import com.tmb.oneapp.lendingservice.service.CodeEntriesService;

@Component
public class LendingModuleCache {

	private static final TMBLogger<LendingModuleCache> logger = new TMBLogger<>(LendingModuleCache.class);

	private CodeEntriesService codeEntriesService;
	private static HashMap<String, List<CommonCodeEntry>> cacheContainer = new HashMap();

	@Autowired
	public LendingModuleCache(CodeEntriesService codeEntriesService) {
		this.codeEntriesService = codeEntriesService;
	}

	@PostConstruct
	public void setupCaching() {

		for (LoanCategory loadCategory : LoanCategory.values()) {
			List<CommonCodeEntry> commonCodeEntry = codeEntriesService.loadEntry(loadCategory.getCode(), "MIB", "3",
					"725a9ec5-5de0-4b95-a51f-9774b559b459");
			cacheContainer.put(loadCategory.getCode(), commonCodeEntry);
		}

	}

	public List<CommonCodeEntry> getListByCategoryCode(String categoryCode) {
		return cacheContainer.get(categoryCode);
	}

}
