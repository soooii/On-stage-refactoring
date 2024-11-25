package com.team5.on_stage.global.config.jwt;

import com.team5.on_stage.user.entity.Role;
import org.springframework.security.core.Authentication;

import java.util.Map;

public interface JwtProvider<T> {
    T convertAuthToken(String token);
    Authentication getAuthentication(T authToken);
    T createAccessToken(String sub, Role role, Map<String, Object> claims);
    T createRefreshToken(String sub, Role role, Map<String, Object> claims);
}

