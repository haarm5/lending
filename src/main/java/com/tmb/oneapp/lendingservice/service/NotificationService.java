package com.tmb.oneapp.lendingservice.service;

import com.tmb.common.logger.TMBLogger;
import com.tmb.common.model.TmbOneServiceResponse;
import com.tmb.common.model.request.notification.EmailChannel;
import com.tmb.common.model.request.notification.NotificationRecord;
import com.tmb.common.model.request.notification.NotificationRequest;
import com.tmb.common.model.request.notification.NotifyCommon;
import com.tmb.common.model.response.notification.NotificationResponse;
import com.tmb.common.util.NotificationUtil;
import com.tmb.oneapp.lendingservice.client.NotificationServiceClient;
import com.tmb.oneapp.lendingservice.constant.NotificationConstant;
import com.tmb.oneapp.lendingservice.constant.ResponseCode;
import com.tmb.oneapp.lendingservice.model.notification.ReportGeneratorNotificationWrapper;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class NotificationService {

    private static final TMBLogger<NotificationService> logger = new TMBLogger<>(NotificationService.class);

    @Value("${notification-service.e-noti.default.channel.th}")
    private String defaultChannelTh;
    @Value("${notification-service.e-noti.default.channel.en}")
    private String defaultChannelEn;
    @Value("${notification-service.e-noti.default.template.date}")
    private static final String HTML_DATE_FORMAT = "dd/MM/yyyy";
    @Value("${notification-service.e-noti.default.template.time}")
    private static final String HH_MM = "HH:mm";

    private final NotificationServiceClient notificationServiceClient;


    public NotificationService(NotificationServiceClient notificationServiceClient) {
        this.notificationServiceClient = notificationServiceClient;
    }

    public void sendNotifyEAppReportGenerator(String crmId, String productCode, String correlationId, ReportGeneratorNotificationWrapper wrapper) {
        NotifyCommon notifyCommon = NotificationUtil.generateNotifyCommon(correlationId, defaultChannelEn,
                defaultChannelTh, wrapper.getProductNameEn(), wrapper.getProductNameTh(), wrapper.getCustomerNameEn(), wrapper.getCustomerNameTh());
        notifyCommon.setCrmId(crmId);
        String tranDate = LocalDateTime.now().format(DateTimeFormatter.ofPattern(HTML_DATE_FORMAT));
        String tranTime = LocalDateTime.now().format(DateTimeFormatter.ofPattern(HH_MM));

        if (StringUtils.isNotBlank(wrapper.getEmail())) {
            NotificationRequest notificationRequest = new NotificationRequest();
            List<NotificationRecord> notificationRecords = new ArrayList<>();
            NotificationRecord record = new NotificationRecord();

            EmailChannel emailChannel = new EmailChannel();
            emailChannel.setEmailEndpoint(wrapper.getEmail());
            emailChannel.setEmailSearch(false);

            record.setEmail(emailChannel);

            Map<String, Object> params = new HashMap<>();
            params.put(NotificationConstant.TEMPLATE_KEY, NotificationConstant.APPLY_LOAN_SUBMISSION_VALUE);
            params.put(NotificationConstant.CUSTOMER_NAME_TH, notifyCommon.getCustFullNameTH());
            params.put(NotificationConstant.PRODUCT_NAME_TH, notifyCommon.getProductNameTH());
            params.put(NotificationConstant.PRODUCT_ID, productCode);
            params.put(NotificationConstant.TRAN_DATE, tranDate);
            params.put(NotificationConstant.TRAN_TIME, tranTime);
            params.put(NotificationConstant.CHANNEL_NAME_TH, notifyCommon.getChannelNameTh());

            record.setParams(params);
            record.setCrmId(notifyCommon.getCrmId());
            record.setLanguage(NotificationConstant.LOCALE_TH);

            record.setAttachments(wrapper.getAttachments());

            setRequestForEmail("oranuch901@gmail.com", record);

            notificationRecords.add(record);
            notificationRequest.setRecords(notificationRecords);

            TmbOneServiceResponse<NotificationResponse> sendEmailResponse = notificationServiceClient
                    .sendMessage(notifyCommon.getXCorrelationId(), notificationRequest);

            processResultLog(sendEmailResponse, notificationRequest);
        }
    }

    /**
     * set param for email
     *
     * @param record
     */
    private void setRequestForEmail(String email, NotificationRecord record) {
        // case email
        if (StringUtils.isNotBlank(email)) {
            EmailChannel emailChannel = new EmailChannel();
            emailChannel.setEmailEndpoint(email);
            emailChannel.setEmailSearch(false);

            record.setEmail(emailChannel);
        }
    }

    /**
     * Log response for e-notification system
     *
     * @param sendEmailResponse
     * @param notificationRequest
     */
    private void processResultLog(TmbOneServiceResponse<NotificationResponse> sendEmailResponse,
                                  NotificationRequest notificationRequest) {
        if (ResponseCode.SUCCESS.getCode().equals(sendEmailResponse.getStatus().getCode())) {
            logger.info("xCorrelationId:{} ,e-noti response sent email success", notificationRequest);
        } else {
            logger.error("xCorrelationId:{}, e-noti response sent email error:{}, {}", notificationRequest,
                    sendEmailResponse.getStatus().getCode(), sendEmailResponse.getStatus().getMessage());
        }
    }
}
