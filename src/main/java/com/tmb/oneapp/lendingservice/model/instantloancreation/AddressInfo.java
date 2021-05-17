package com.tmb.oneapp.lendingservice.model.instantloancreation;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;
import org.apache.commons.lang3.StringUtils;

@Getter
@Setter
public class AddressInfo {

    @JsonIgnore
    public boolean isValid() {
        return !StringUtils.isEmpty(getAddrTypCode());
    }

    @JsonProperty("addrTypCode")
    private String addrTypCode;
    @JsonProperty("address")
    private String address;
    @JsonProperty("buildingName")
    private String buildingName;
    @JsonProperty("streetName")
    private String streetName;
    @JsonProperty("postalCode")
    private String postalCode;
    @JsonProperty("country")
    private String country;
    @JsonProperty("tumbol")
    private String tumbol;
    @JsonProperty("road")
    private String road;
    @JsonProperty("moo")
    private String moo;
    @JsonProperty("amphur")
    private String amphur;
    @JsonProperty("province")
    private String province;
    @JsonProperty("floor")
    private String floor;
}
