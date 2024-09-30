package com.github.pankajyogi.spring.taxibooking.agent.config;

import com.github.pankajyogi.spring.taxibooking.model.StringConstants;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Configuration
public class WebConfig {


    @Bean
    public RestTemplate restTemplate(@Value("${taxiId}")Long taxiId, RestTemplateBuilder builder) {
        return builder.defaultHeader(StringConstants.TAXI_ID_HEADER, String.valueOf(taxiId)).build();
    }
}
