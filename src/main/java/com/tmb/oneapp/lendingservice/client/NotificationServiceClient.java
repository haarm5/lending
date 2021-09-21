package com.tmb.oneapp.lendingservice.client;

import com.tmb.common.model.TmbOneServiceResponse;
import com.tmb.common.model.request.notification.NotificationRequest;
import com.tmb.common.model.response.notification.NotificationResponse;
import com.tmb.oneapp.lendingservice.constant.LendingServiceConstant;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;

@FeignClient(name = "notification-service", url = "${notification-service.url}")
public interface NotificationServiceClient {

    @PostMapping(value = "${notification-service.e-noti.send-message.endpoint}")
    TmbOneServiceResponse<NotificationResponse> sendMessage(
            @RequestHeader(value = LendingServiceConstant.HEADER_CORRELATION_ID) final String xCorrelationId,
            NotificationRequest request);
}
