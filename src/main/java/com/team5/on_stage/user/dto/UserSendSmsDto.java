package com.team5.on_stage.user.dto;

import jakarta.validation.constraints.Pattern;
import lombok.Getter;

@Getter
public class UserSendSmsDto {

    private String username;

    @Pattern(regexp = "(010)[0-9]{4}[0-9]{4}", message = "전화번호 형식이 올바르지 않습니다.")
    private String phoneNumber;

}
