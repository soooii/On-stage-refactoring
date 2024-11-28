package com.team5.on_stage.global.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;


@Configuration
public class ChatGPTConfig {

    @Value("${ON-STAGE-CHATGPT}")
    private String gptSecretKey;
    @Bean
    public RestTemplate template() {
        RestTemplate restTemplate = new RestTemplate();
        restTemplate.getInterceptors().add((request, body, execution) -> {
            request.getHeaders().add("Authorization", "Bearer " + gptSecretKey);
            return execution.execute(request, body);
        });
        return restTemplate;
    }
}
