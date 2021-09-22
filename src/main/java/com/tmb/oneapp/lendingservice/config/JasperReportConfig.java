package com.tmb.oneapp.lendingservice.config;

import com.tmb.oneapp.lendingservice.service.JasperReportService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class JasperReportConfig {

    @Bean
    public JasperReportService reportFiller() {
        return new JasperReportService();
    }

}
