package com.team5.on_stage.global.config.path;

public class SecurityPath {
    public static final String[] COMMON_WHITELIST = {
            "/",
            "/swagger-ui/**",
            "/v3/api-docs/**",

            /* User */
            "/login",
            "/logout",
            "/api/auth/reissue",
    };
    public static final String[] ONLY_GET_WHITELIST = {
            /* User */
            "/{username}",
            /* Summary */
            "/api/summary/{username}",
            /* Analytics */
            "/api/analytics/dashboard",
            "/api/analytics/get-ip"
    };
    public static final String[] ONLY_POST_WHITELIST = {
            /* Summary */
            "/api/summary/{username}",
            /* Article */
            "/api/article/{username}",
            /* Analytics */
            "/api/analytics/page",
            "/api/analytics/link",
            "/api/analytics/socialLink"
    };
    public static final String[] ONLY_PATCH_WHITELIST = {

    };
    public static final String[] ONLY_PUT_WHITELIST = {

    };
    public static final String[] ONLY_DELETE_WHITELIST = {
            /* Summary */
            "/api/summary/{username}",
            /* Article */
            "/api/article/{username}"
    };
}
