package com.tmb.oneapp.lendingservice;

import java.util.HashMap;
import java.util.List;
import java.util.UUID;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.tmb.common.logger.TMBLogger;
import com.tmb.common.model.legacy.rsl.common.ob.dropdown.CommonCodeEntry;
import com.tmb.oneapp.lendingservice.constant.LoanCategory;
import com.tmb.oneapp.lendingservice.service.CodeEntriesService;

@Component
public class LendingModuleCache {

	private static final TMBLogger<LendingModuleCache> logger = new TMBLogger<>(LendingModuleCache.class);

	@Value("${mf.channel}")
	private String channel;

	private CodeEntriesService codeEntriesService;
	private static HashMap<String, List<CommonCodeEntry>> cacheContainer = new HashMap();

	@Autowired
	public LendingModuleCache(CodeEntriesService codeEntriesService) {
		this.codeEntriesService = codeEntriesService;
	}

	@PostConstruct
	public void setupCaching() {

		for (LoanCategory loadCategory : LoanCategory.values()) {
			List<CommonCodeEntry> commonCodeEntry = codeEntriesService.loadEntry(loadCategory.getCode(), channel, "3",
					UUID.randomUUID().toString());
			cacheContainer.put(loadCategory.getCode(), commonCodeEntry);
		}

	}

	public List<CommonCodeEntry> getListByCategoryCode(String categoryCode) {
		return cacheContainer.get(categoryCode);
	}

}
