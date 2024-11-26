package com.team5.on_stage.global.config.auth.cookie;

import jakarta.servlet.http.Cookie;

public class CookieUtil {

    public final static String COOKIE_DOMAIN = "localhost";
    public final static String COOKIE_PATH = "/";
    public final static int COOKIE_MAX_AGE = 24 * 60 * 60;


    public static Cookie createCookie(String key, String value) {

        Cookie cookie = new Cookie(key, value);

        cookie.setMaxAge(COOKIE_MAX_AGE);
        cookie.setDomain(COOKIE_DOMAIN);
        cookie.setPath(COOKIE_PATH);
        cookie.setSecure(false);
        cookie.setHttpOnly(true);

        return cookie;
    }
}
