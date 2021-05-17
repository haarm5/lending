package com.tmb.oneapp.lendingservice;

import java.util.ArrayList;
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

/**
 * Provides beans to load and cache all master data from
 * LoanSubmissionGetDropdownListService
 */
@Component
public class LendingModuleCache {

	private static final TMBLogger<LendingModuleCache> logger = new TMBLogger<>(LendingModuleCache.class);

	private CodeEntriesService codeEntriesService;
	private static HashMap<String, List<CommonCodeEntry>> cacheContainer = new HashMap();

	@Value("${mf.channel}")
	private String channel;

	@Autowired
	public LendingModuleCache(CodeEntriesService codeEntriesService) {
		this.codeEntriesService = codeEntriesService;
	}

	@PostConstruct
	public void setupCaching() {
		try {
			for (LoanCategory loadCategory : LoanCategory.values()) {
				List<CommonCodeEntry> commonCodeEntry = codeEntriesService.loadEntry(loadCategory.getCode(), channel,
						"3", UUID.randomUUID().toString());
				if (LoanCategory.BUSINESS_TYPE.getCode().equals(loadCategory.getCode())) {
					List<CommonCodeEntry> fillterList = new ArrayList<CommonCodeEntry>();
					commonCodeEntry.forEach(e -> {
						if (!"03".equals(e.getEntryCode())) {
							fillterList.add(e);
						}
					});
					cacheContainer.put(loadCategory.getCode(), fillterList);
				} else {
					cacheContainer.put(loadCategory.getCode(), commonCodeEntry);
				}
			}
		} catch (Exception e) {
			logger.error(e.toString(), e);
		}

	}

	public List<CommonCodeEntry> getListByCategoryCode(String categoryCode) {
		return cacheContainer.get(categoryCode);
	}

}
