package com.team5.on_stage.global.config.auth;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.oauth2.client.registration.ClientRegistrationRepository;
import org.springframework.security.oauth2.client.registration.InMemoryClientRegistrationRepository;

@RequiredArgsConstructor
@Configuration
public class CustomClientRegistrationRepo {

    private final SocialClientRegistration socialClientRegistration;

    // 2가지 방법
    // 1. 인메모리에 Registration 정보를 저장 -> 정보가 적으므로 채택
    // 2. JDBC로 DB에 정보를 저장
    @Bean
    public ClientRegistrationRepository ClientRegistrationRepository() {

        return new InMemoryClientRegistrationRepository(socialClientRegistration.naverClientRegistration(),
                                                        socialClientRegistration.googleClientRegistration());
    }

}
