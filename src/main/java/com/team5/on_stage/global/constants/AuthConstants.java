package com.team5.on_stage.global.constants;

import java.util.Arrays;

public class AuthConstants {

    private static final String ACTIVE_PROFILES = resolveActiveProfiles();
    private static final boolean IS_DEPLOY = isDeployProfile(ACTIVE_PROFILES);

    // 기본값은 Local, SPRING_PROFILES_ACTIVE=deploy 일 때만 배포 설정을 사용
    public static final boolean COOKIE_SECURE = IS_DEPLOY;
    public static final String COOKIE_SAME_SITE = IS_DEPLOY ? "None" : "Lax";

    public static final String DEPLOY_DOMAIN = IS_DEPLOY ? ".al-going.com" : "";
    public static final String DEPLOY_FRONT_DOMAIN = IS_DEPLOY ? "https://al-going.com" : "http://localhost:3000";
    public static final String DEPLOY_BACK_DOMAIN = IS_DEPLOY ? "https://api.al-going.com" : "http://localhost:8080";

    // Token Type
    public static final String TYPE_REFRESH = "refresh";
    public static final String TYPE_ACCESS = "access";

    // Access Token을 전달할 헤더
    public static final String AUTH_HEADER = "Authorization";

    // Access Token의 타입
    public static final String AUTH_TYPE = "Bearer ";

    // 각 Token의 유효 시간
    public static final Long REFRESH_TOKEN_EXPIRED_MS = 86400000L;
    public static final Long ACCESS_TOKEN_EXPIRED_MS = 86400000L;

    private static String resolveActiveProfiles() {
        String activeProfiles = System.getProperty("spring.profiles.active");
        if (activeProfiles == null || activeProfiles.isBlank()) {
            activeProfiles = System.getenv("SPRING_PROFILES_ACTIVE");
        }
        return activeProfiles == null ? "" : activeProfiles;
    }

    private static boolean isDeployProfile(String activeProfiles) {
        return Arrays.stream(activeProfiles.split(","))
                .map(String::trim)
                .anyMatch("deploy"::equalsIgnoreCase);
    }
}
