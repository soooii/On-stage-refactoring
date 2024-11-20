package com.team5.on_stage.user.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class SignUpDto {

    @NotNull
    @Email
    private String email;

    @NotNull
    private String nickname;

    private String description;

    @NotNull
    private String password;
}
