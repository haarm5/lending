package com.tmb.oneapp.lendingservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@EnableFeignClients
@ComponentScan(basePackages = {"com.tmb.oneapp", "com.tmb.common"})
public class LendingServiceApplication {
    public static void main(String[] args) {
        SpringApplication.run(LendingServiceApplication.class);
    }
}

