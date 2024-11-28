package com.team5.on_stage.global.config.jwt;

public interface AuthConstants {

    // Domain
    String FRONT_DOMAIN = "http://localhost:3000";
    String BACK_DOMAIN = "http://localhost:8080";

    // Token Type
    String REFRESH_TOKEN = "refresh";
    String ACCESS_TOKEN = "access";

    // Access Token을 전달할 헤더
    String AUTH_HEADER = "Authorization";

    // Access Token의 타입
    String AUTH_TYPE = "Bearer ";

    // 각 Token의 유효 시간
    Long REFRESH_TOKEN_EXPIRED_MS = 86400000L;
    Long ACCESS_TOKEN_EXPIRED_MS = 86400000L;
}
