package com.team5.on_stage.global.config.redis.dto;

import lombok.Getter;

import java.io.Serializable;

@Getter
public class SmsVerificationData implements Serializable {

    private final String username;

    private final String verificationCode;

    private final String phoneNumber;

    //private final long expiresAt;

    public SmsVerificationData(String username,
                               String verificationCode,
                               String phoneNumber) {

        this.username = username;
        this.verificationCode = verificationCode;
        this.phoneNumber = phoneNumber;
        //this.expiresAt = 1000 * 60 * 5;
    }
}
