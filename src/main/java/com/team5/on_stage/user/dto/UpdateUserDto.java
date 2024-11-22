package com.team5.on_stage.user.dto;

import lombok.Data;

@Data
public class UpdateUserDto {

    // Todo: 제약 추가
    private String description;
    private String nickname;
    private String password;
}
