package com.tmb.oneapp.lendingservice.model.instantloancreation;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class LOCRequest {

    @JsonProperty("NCBCustName")
    private String nCBCustName;
    @JsonProperty("NCBID")
    private String ncbid;
    @JsonProperty("NCBDateofbirth")
    private String nCBDateofbirth;
    @JsonProperty("NCBMobileNo")
    private String nCBMobileNo;
    @JsonProperty("ProductName")
    private String productName;
    @JsonProperty("NCBReferenceID")
    private String nCBReferenceID;
    @JsonProperty("NCBDateTime")
    private String nCBDateTime;
    @JsonProperty("ConsentbyCustomer")
    private String consentbyCustomer;
    @JsonProperty("appRefNo")
    private String appRefNo;

    @JsonProperty("crmId")
    private String crmId;

    public LOCRequest() {
    }

    public LOCRequest(LOCRequest locRequest) {
        setNCBMobileNo(locRequest.getNCBMobileNo());
        setNCBDateofbirth(locRequest.getNCBDateofbirth());
        setNcbid(locRequest.getNcbid());
        setNCBCustName(locRequest.getNCBCustName());
        setCrmId(locRequest.getCrmId());
        setNCBReferenceID(locRequest.getNCBReferenceID());
        setNCBDateTime(locRequest.getNCBDateTime());
        setProductName(locRequest.getProductName());
        setAppRefNo(locRequest.getAppRefNo());

    }
}
