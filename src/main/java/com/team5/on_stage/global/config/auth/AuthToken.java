package com.team5.on_stage.global.config.auth;

public interface AuthToken<T> {
    String AUTHORITIES_TOKEN_KEY = "role";
    boolean validate();
    T getDate();
}

