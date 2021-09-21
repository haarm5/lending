package com.tmb.oneapp.lendingservice.config;

import com.tmb.oneapp.lendingservice.service.JasperReportFillerService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class JasperReportConfig {

    @Bean
    public JasperReportFillerService reportFiller() {
        return new JasperReportFillerService();
    }

}
