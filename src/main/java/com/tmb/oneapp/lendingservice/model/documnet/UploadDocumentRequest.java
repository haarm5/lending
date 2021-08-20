package com.tmb.oneapp.lendingservice.model.documnet;

import lombok.Getter;
import lombok.Setter;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.List;

@Getter
@Setter
public class UploadDocumentRequest {

    @NotNull
    private long caId;
    @NotNull
    private List<Document> documents;

    @Getter
    @Setter
    @Valid
    public static class Document {
        @NotEmpty
        private String docCode;
        @NotEmpty
        private String pdfFile;
    }

}
