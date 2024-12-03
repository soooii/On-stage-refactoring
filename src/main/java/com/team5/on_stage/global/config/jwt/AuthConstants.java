package com.team5.on_stage.global.config.jwt;

public class AuthConstants {

    // Domain
    static public String FRONT_DOMAIN = "http://localhost:3000";
    static public String BACK_DOMAIN = "http://localhost:8080";

    // Token Type
    static public String TYPE_REFRESH = "refresh";
    static public String TYPE_ACCESS = "access";

    // Access Token을 전달할 헤더
    static public String AUTH_HEADER = "Authorization";

    // Access Token의 타입
    static public String AUTH_TYPE = "Bearer ";

    // 각 Token의 유효 시간
    static public Long REFRESH_TOKEN_EXPIRED_MS = 86400000L;
    static public Long ACCESS_TOKEN_EXPIRED_MS = 86400000L;
}
