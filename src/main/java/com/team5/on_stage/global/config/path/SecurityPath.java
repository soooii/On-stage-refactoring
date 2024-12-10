package com.team5.on_stage.global.config.path;

public class SecurityPath {
    public static final String[] ONLY_GET_WHITELIST = {
            "/",
            "/swagger-ui/**",
            "/v3/api-docs/**",

            /* Summary */
            "/api/summary/{username}",
    };
    public static final String[] ONLY_POST_WHITELIST = {
            "/",
            "/swagger-ui/**",
            "/v3/api-docs/**",

            /* Summary */
            "/api/summary/{username}",
            /* Article */
            "/api/article/{username}",
    };
    public static final String[] ONLY_PATCH_WHITELIST = {
            "/",
            "/swagger-ui/**",
            "/v3/api-docs/**"


    };
    public static final String[] ONLY_PUT_WHITELIST = {
            "/",
            "/swagger-ui/**",
            "/v3/api-docs/**"

    };
    public static final String[] ONLY_DELETE_WHITELIST = {
            "/",
            "/swagger-ui/**",
            "/v3/api-docs/**",

            /* Summary */
            "/api/summary/{username}",
            /* Article */
            "/api/article/{username}",

    };
}
