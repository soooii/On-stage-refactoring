package com.team5.on_stage.global.config.path;

public class SecurityPath {
    public static final String[] COMMON_WHITELIST = {
            "/",
            "/swagger-ui/**",
            "/v3/api-docs/**",
            /* User */
            "/login/**",
            "/api/login/**",
            "/login/oauth2/authorization/*",
            "/login/oauth2/code/{registrationId}",
            "/logout",
            "/auth/reissue",
    };
    public static final String[] ONLY_GET_WHITELIST = {
            /* User */
            "/{username}",
            /* Summary */
            "/summary/{username}",
            /* Analytics */
            "/analytics/dashboard",
            "/analytics/get-ip"
    };
    public static final String[] ONLY_POST_WHITELIST = {
            /* Summary */
            "/summary/{username}",
            /* Article */
            "/article/{username}",
            /* Analytics */
            "/analytics/page",
            "/analytics/link",
            "/analytics/socialLink"
    };
    public static final String[] ONLY_PATCH_WHITELIST = {

    };
    public static final String[] ONLY_PUT_WHITELIST = {

    };
    public static final String[] ONLY_DELETE_WHITELIST = {
            /* Summary */
            "/summary/{username}",
            /* Article */
            "/article/{username}"
    };
}
