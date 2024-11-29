package com.team5.on_stage.global.config.auth.cookie;

import jakarta.servlet.http.Cookie;

public class CookieUtil {

    public final static String COOKIE_DOMAIN = "localhost";
    public final static String COOKIE_PATH = "/";
    public final static int COOKIE_MAX_AGE = 24 * 60 * 60;


    public static Cookie createCookie(String key, String value, Boolean httpOnly) {

        Cookie cookie = new Cookie(key, value);

        cookie.setMaxAge(COOKIE_MAX_AGE);
        cookie.setDomain(COOKIE_DOMAIN);
        cookie.setPath(COOKIE_PATH);
        cookie.setSecure(true);
        cookie.setHttpOnly(httpOnly);

        return cookie;
    }

    public static Cookie deleteCookie(String key) {

        Cookie cookie = new Cookie(key, null);
        cookie.setMaxAge(0);
        cookie.setDomain(COOKIE_DOMAIN);
        cookie.setPath(COOKIE_PATH);
        cookie.setSecure(true);
        cookie.setHttpOnly(true);

        return cookie;
    }
}
