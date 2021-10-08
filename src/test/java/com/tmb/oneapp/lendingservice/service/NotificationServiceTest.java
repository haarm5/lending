package com.tmb.oneapp.lendingservice.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.tmb.common.exception.model.TMBCommonException;
import com.tmb.common.model.TmbOneServiceResponse;
import com.tmb.common.model.TmbStatus;
import com.tmb.common.model.response.notification.NotificationResponse;
import com.tmb.oneapp.lendingservice.client.NotificationServiceClient;
import com.tmb.oneapp.lendingservice.constant.ResponseCode;
import com.tmb.oneapp.lendingservice.model.notification.ReportGeneratorNotificationWrapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import org.mockito.InjectMocks;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.Spy;

import javax.xml.rpc.ServiceException;

import static org.mockito.ArgumentMatchers.any;

@RunWith(JUnit4.class)
public class NotificationServiceTest {

    @InjectMocks
    private NotificationService notificationService;

    @Spy
    private NotificationServiceClient notificationServiceClient;

    @BeforeEach
    void setUp() throws ServiceException, TMBCommonException, JsonProcessingException {
        MockitoAnnotations.initMocks(this);
        notificationService = new NotificationService(notificationServiceClient);
    }

    @Test
    void sendNotifyEAppReportGenerator_Success() throws JsonProcessingException {
        Mockito.when(notificationServiceClient.sendMessage(any(), any())).thenReturn(mockEmailResponse());

        ReportGeneratorNotificationWrapper wrapper = new ReportGeneratorNotificationWrapper();
        wrapper.setEmail("test@gmail.com");
        notificationService.sendNotifyEAppReportGenerator("crmId", "accountId", "correlationId", wrapper);

        Mockito.verify(notificationServiceClient, Mockito.times(1)).sendMessage(any(), any());
    }


    private TmbOneServiceResponse<NotificationResponse> mockEmailResponse() {
        TmbOneServiceResponse<NotificationResponse> sendEmailResponse = new TmbOneServiceResponse<>();
        sendEmailResponse.setStatus(new TmbStatus(ResponseCode.SUCCESS.getCode(), "succcess", "notification-service"));
        return sendEmailResponse;
    }

}
