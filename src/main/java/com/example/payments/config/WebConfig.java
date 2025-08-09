package com.example.payments.config;

import com.example.payments.api.ApiKeyAuthFilter;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class WebConfig {
    @Bean
    public FilterRegistrationBean<ApiKeyAuthFilter> apiKeyFilterRegistration(ApiKeyAuthFilter filter) {
        FilterRegistrationBean<ApiKeyAuthFilter> registration = new FilterRegistrationBean<>();
        registration.setFilter(filter);
        registration.addUrlPatterns("/*");
        registration.setOrder(1);
        return registration;
    }
}


