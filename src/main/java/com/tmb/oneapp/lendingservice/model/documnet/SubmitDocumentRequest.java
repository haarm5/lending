package com.tmb.oneapp.lendingservice.model.documnet;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;
import java.util.List;

@Getter
@Setter
public class SubmitDocumentRequest {

    @NotEmpty
    private String caId;
    @NotEmpty
    private List<String> docCodes;

}
