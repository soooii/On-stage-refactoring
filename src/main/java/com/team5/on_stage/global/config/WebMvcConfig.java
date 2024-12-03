package com.team5.on_stage.global.config;

import com.team5.on_stage.global.config.jwt.TokenUsernameArgumentResolver;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.List;

@RequiredArgsConstructor
@Configuration
public class WebMvcConfig implements WebMvcConfigurer {

    private final TokenUsernameArgumentResolver tokenUsernameArgumentResolver;

    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> resolvers) {

        resolvers.add(tokenUsernameArgumentResolver);
    }
}
