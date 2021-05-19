package com.tmb.oneapp.lendingservice.model.info;

import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.tmb.common.model.legacy.rsl.common.ob.dropdown.CommonCodeEntry;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.Map;

@Getter
@Setter
@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
public class MasterDataResponse {
    private Map<String,List<CommonCodeEntry>> masterData;
}
