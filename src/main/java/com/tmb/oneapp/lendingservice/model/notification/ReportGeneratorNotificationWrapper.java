package com.tmb.oneapp.lendingservice.model.notification;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class ReportGeneratorNotificationWrapper {
    private String customerNameTh;
    private String customerNameEn;
    private String productNameTh;
    private String productNameEn;
    private String appRefNo;
    private String email;

    private List<String> attachments;
}
