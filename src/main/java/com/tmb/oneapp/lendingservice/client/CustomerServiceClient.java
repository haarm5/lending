package com.tmb.oneapp.lendingservice.client;

import com.tmb.common.model.CustGeneralProfileResponse;
import com.tmb.common.model.TmbOneServiceResponse;
import com.tmb.oneapp.lendingservice.constant.LendingServiceConstant;
import com.tmb.oneapp.lendingservice.model.Customer;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;

@FeignClient(name = "customer-service", url = "${customers-service.url}")
public interface CustomerServiceClient {
    @GetMapping(value = "${customers-service.customer.details.endpoint}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    TmbOneServiceResponse<Customer> getCustomerDetails(@RequestHeader(value = LendingServiceConstant.HEADER_X_CRMID, required = true) final String crmID);

    @GetMapping(value = "${customers-service.customer.details.endpoint}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    TmbOneServiceResponse<Customer> getCustomerProfile(@RequestHeader(value = LendingServiceConstant.HEADER_X_CRMID, required = true) final String crmID);

    @GetMapping(value = "${customers-service.customer.details.endpoint}", produces = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<TmbOneServiceResponse<CustGeneralProfileResponse>> getCustomers(@RequestHeader(name = LendingServiceConstant.HEADER_X_CRMID) String crmId);

}
