package com.team5.on_stage.global.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@RequiredArgsConstructor
@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

        http
                .cors((cors) -> cors.configurationSource(new CorsConfig()));

        http
        .csrf((auth) -> auth.disable())
        .formLogin((auth) -> auth.disable())
        .httpBasic((auth) -> auth.disable());

        http.authorizeHttpRequests((auth) -> auth
                .requestMatchers("/").permitAll()
                .requestMatchers("/api/**").permitAll()
                .requestMatchers("/swagger-ui/**", "/v3/api-docs/**").permitAll() /* Swagger */
                .anyRequest().permitAll());


        return http.build();
    }
}
