package com.tmb.oneapp.lendingservice.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.tmb.common.exception.model.TMBCommonException;
import com.tmb.common.model.TmbOneServiceResponse;
import com.tmb.common.model.TmbStatus;
import com.tmb.common.model.creditcard.CreditCardDetail;
import com.tmb.common.model.creditcard.SilverlakeStatus;
import com.tmb.common.model.response.notification.NotificationResponse;
import com.tmb.oneapp.lendingservice.client.CreditCardClient;
import com.tmb.oneapp.lendingservice.client.NotificationServiceClient;
import com.tmb.oneapp.lendingservice.constant.ResponseCode;
import com.tmb.oneapp.lendingservice.model.creditcard.FetchCardResponse;
import com.tmb.oneapp.lendingservice.model.creditcard.ProductCodeData;
import com.tmb.oneapp.lendingservice.model.notification.ReportGeneratorNotificationWrapper;
import net.sf.jasperreports.engine.JRException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import org.mockito.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import javax.xml.rpc.ServiceException;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.doReturn;

@RunWith(JUnit4.class)
public class NotificationServiceTest {

    @InjectMocks
    private NotificationService notificationService;

    @Spy
    private NotificationServiceClient notificationServiceClient;
    @Mock
    private CreditCardClient creditCardClient;

    @BeforeEach
    void setUp() throws ServiceException, TMBCommonException, JsonProcessingException, JRException {
        MockitoAnnotations.initMocks(this);
        notificationService = new NotificationService(notificationServiceClient,
                creditCardClient);
    }

    @Test
    void sendNotifyEAppReportGenerator_Success() {
        Mockito.when(creditCardClient.getCreditCardDetails(any(), any())).thenReturn(ResponseEntity.status(HttpStatus.OK).body(mockFetchCardResponse()));
        Mockito.when(notificationServiceClient.sendMessage(any(), any())).thenReturn(mockEmailResponse());

        ReportGeneratorNotificationWrapper wrapper = new ReportGeneratorNotificationWrapper();
        wrapper.setEmail("test@gmail.com");
        notificationService.sendNotifyEAppReportGenerator("crmId", "accountId", "correlationId", wrapper);

        Mockito.verify(notificationServiceClient, Mockito.times(1)).sendMessage(any(), any());
    }

    private FetchCardResponse mockFetchCardResponse() {
        FetchCardResponse cardResponse = new FetchCardResponse();
        ProductCodeData productData = new ProductCodeData();
        productData.setProductNameEN("So Fast Credit Card");
        productData.setProductNameTH("โซฟาสต์");
        cardResponse.setProductCodeData(productData);
        SilverlakeStatus silverlake = new SilverlakeStatus();
        silverlake.setStatusCode(0);
        cardResponse.setStatus(silverlake);
        CreditCardDetail cardDetail = new CreditCardDetail();
        cardDetail.setProductId("VTOPBR");
        cardResponse.setCreditCard(cardDetail);

        return cardResponse;
    }

    private TmbOneServiceResponse<NotificationResponse> mockEmailResponse() {
        TmbOneServiceResponse<NotificationResponse> sendEmailResponse = new TmbOneServiceResponse<>();
        sendEmailResponse.setStatus(new TmbStatus(ResponseCode.SUCCESS.getCode(), "succcess", "notification-service"));
        return sendEmailResponse;
    }

}
