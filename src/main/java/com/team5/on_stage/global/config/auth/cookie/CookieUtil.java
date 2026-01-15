package com.team5.on_stage.global.config.auth.cookie;

import org.springframework.http.ResponseCookie;

import static com.team5.on_stage.global.constants.AuthConstants.COOKIE_SAME_SITE;
import static com.team5.on_stage.global.constants.AuthConstants.COOKIE_SECURE;
import static com.team5.on_stage.global.constants.AuthConstants.DEPLOY_DOMAIN;

public class CookieUtil {

    public final static String COOKIE_DOMAIN = DEPLOY_DOMAIN; //"localhost";
    public final static String COOKIE_PATH = "/";
    public final static int COOKIE_MAX_AGE = 24 * 60 * 60;

    public static String createCookie(String key, String value, Boolean httpOnly) {
        ResponseCookie.ResponseCookieBuilder builder = ResponseCookie.from(key, value)
                .maxAge(COOKIE_MAX_AGE)
                .path(COOKIE_PATH)
                .secure(COOKIE_SECURE)
                .httpOnly(httpOnly)
                .sameSite(COOKIE_SAME_SITE);

        if (COOKIE_DOMAIN != null && !COOKIE_DOMAIN.isBlank()) {
            builder.domain(COOKIE_DOMAIN);
        }

        return builder.build().toString();
    }

    public static String deleteCookie(String key) {
        ResponseCookie.ResponseCookieBuilder builder = ResponseCookie.from(key, "")
                .maxAge(0)
                .path(COOKIE_PATH)
                .secure(COOKIE_SECURE)
                .httpOnly(true)
                .sameSite(COOKIE_SAME_SITE);

        if (COOKIE_DOMAIN != null && !COOKIE_DOMAIN.isBlank()) {
            builder.domain(COOKIE_DOMAIN);
        }

        return builder.build().toString();
    }
}
