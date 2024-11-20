package com.team5.on_stage.global.constants;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum ErrorCode {
    // GLOBAL
    INVALID_VALUE(HttpStatus.BAD_REQUEST, "GLOBAL-01","올바르지 않은 값"),

    // USER


    // Link
    LINK_NOT_FOUND(HttpStatus.NOT_FOUND, "LINK-01","link not found");

    // ANALYSIS


    // CONCERT


    // ARTICLE



    private final HttpStatus httpStatus;
    private final String code;
    private final String message;
}
