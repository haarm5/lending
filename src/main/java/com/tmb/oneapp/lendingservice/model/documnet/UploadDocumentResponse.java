package com.tmb.oneapp.lendingservice.model.documnet;

import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
public class UploadDocumentResponse {

    private String pdfFileName;
    private String status;
    private String appRefNo;
    private String appType;
    private String productDescTh;

}
