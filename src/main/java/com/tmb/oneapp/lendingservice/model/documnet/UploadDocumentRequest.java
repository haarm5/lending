package com.tmb.oneapp.lendingservice.model.documnet;

import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Getter
@Setter
public class UploadDocumentRequest {

    @NotEmpty
    private String caId;
    @NotEmpty
    private String docCode;
    @NotNull
    private MultipartFile file;

}
