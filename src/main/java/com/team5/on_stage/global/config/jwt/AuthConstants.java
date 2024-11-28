package com.team5.on_stage.global.config.jwt;

public interface AuthConstants {

    // Domain
    static String FRONT_DOMAIN = "http://localhost:3000";
    static String BACK_DOMAIN = "http://localhost:8080";

    // Token Type
    static String TYPE_REFRESH = "refresh";
    static String TYPE_ACCESS = "access";

    // Access Token을 전달할 헤더
    static String AUTH_HEADER = "Authorization";

    // Access Token의 타입
    static String AUTH_TYPE = "Bearer ";

    // 각 Token의 유효 시간
    static Long REFRESH_TOKEN_EXPIRED_MS = 86400000L;
    static Long ACCESS_TOKEN_EXPIRED_MS = 86400000L;
}
