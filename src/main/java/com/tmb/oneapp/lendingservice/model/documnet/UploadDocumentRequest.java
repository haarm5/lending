package com.tmb.oneapp.lendingservice.model.documnet;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;

@Getter
@Setter
public class UploadDocumentRequest {
    @NotEmpty
    private String caId;
    @NotEmpty
    private String docCode;
    @NotEmpty
    private String file;
}
