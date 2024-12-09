package com.team5.on_stage.global.config.redis.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Getter
@NoArgsConstructor
public class SmsVerificationData implements Serializable {

    private String username;

    private String verificationCode;

    private String phoneNumber;

    private String requestTime;

    @Builder
    public SmsVerificationData(String username,
                               String verificationCode,
                               String phoneNumber,
                               String requestTime) {

        this.username = username;
        this.verificationCode = verificationCode;
        this.phoneNumber = phoneNumber;
        this.requestTime = requestTime;
    }
}
