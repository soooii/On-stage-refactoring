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
    NICKNAME_DUPLICATED(HttpStatus.CONFLICT, "USER-01", "중복된 이메일입니다."),
    USER_NOT_FOUND(HttpStatus.NOT_FOUND, "USER-02", "유저를 찾을 수 없습니다."),
    NOT_MODIFIED(HttpStatus.NOT_MODIFIED, "USER-03", "변경 사항이 없습니다."),
    LOGIN_FAILED(HttpStatus.INTERNAL_SERVER_ERROR, "USER-4", "로그인에 실패했습니다."),

    // Verify
    VERIFY_REQUEST_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "VERIFY-01", "인증 요청에 실패했습니다."),
    VERIFY_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "VERIFY-02", "인증 과정에서 문제가 발생했습니다."),
    VERIFY_CODE_UNMATCHED(HttpStatus.BAD_REQUEST, "VERIFY-03", "인증 코드가 일치하지 않습니다."),
    PHONENUMBER_UNMATCHED(HttpStatus.BAD_REQUEST, "VERIFY-04", "전화번호가 일치하지 않습니다."),
    BAD_REQUEST_VERIFY(HttpStatus.BAD_REQUEST, "VERIFY-05", "잘못된 요청입니다."),

    // JWT
    UNAUTHORIZED(HttpStatus.UNAUTHORIZED, "AUTH-01", "인증되지 않은 사용자입니다."),
    FORBIDDEN(HttpStatus.FORBIDDEN, "AUTH-02", "접근 권한이 없습니다."),
    INVALID_ACCESS_TOKEN(HttpStatus.UNAUTHORIZED, "AUTH-03", "유효한 토큰이 아닙니다."),
    INVALID_REFRESH_TOKEN(HttpStatus.UNAUTHORIZED, "AUTH-04", "유효한 토큰이 아닙니다."),
    ACCESS_TOKEN_EXPIRED(HttpStatus.UNAUTHORIZED, "AUTH-05", "토큰이 만료되었습니다."),
    REFRESH_TOKEN_EXPIRED(HttpStatus.UNAUTHORIZED, "AUTH-06", "토큰이 만료되었습니다."),
    INVALID_AUTH_HEADER(HttpStatus.UNAUTHORIZED, "AUTH-07", "유효한 인증 헤더가 아닙니다."),
    TYPE_NOT_MATCHED(HttpStatus.UNAUTHORIZED, "AUTH-08", "토큰 타입이 일치하지 않습니다."),
    BAD_REQUEST_TOKEN(HttpStatus.UNAUTHORIZED, "AUTH-09", "잘못된 요청입니다."),
    REFRESH_TOKEN_NOT_EXISTS(HttpStatus.UNAUTHORIZED, "AUTH-10", "토큰이 존재하지 않습니다."),
    FAILED_TO_REISSUE(HttpStatus.UNAUTHORIZED, "AUTH-11", "토큰 재발급에 실패했습니다."),

    // Link
    LINK_NOT_FOUND(HttpStatus.NOT_FOUND, "LINK-01","link not found"),
    LINK_DETAIL_NOT_FOUND(HttpStatus.NOT_FOUND, "LINK-02","link detail not found"),
    SOCIAL_LINK_NOT_FOUND(HttpStatus.NOT_FOUND, "LINK-03","social link not found"),
    THEME_NOT_FOUND(HttpStatus.NOT_FOUND, "LINK-04","theme not found"),

    // Subscribe
    SUBSCRIBED_NOT_FOUND(HttpStatus.NOT_FOUND, "SUBSCRIBE-01", "즐겨찾기 대상을 찾을 수 없습니다."),
    CANNOT_SUBSCRIBE_SELF(HttpStatus.BAD_REQUEST, "SUBSCRIBE-02", "잘못된 즐겨찾기 요청입니다."),
    SUBSCRIBE_CANNOT_BE_MINUS(HttpStatus.BAD_REQUEST, "SUBSCRIBE-03", "잘못된 즐겨찾기 요청입니다.");

    // ANALYSIS


    // CONCERT


    // ARTICLE



    private final HttpStatus httpStatus;
    private final String code;
    private final String message;
}
