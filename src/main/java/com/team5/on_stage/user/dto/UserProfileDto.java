package com.team5.on_stage.user.dto;

import com.team5.on_stage.user.entity.Verified;
import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class UserProfileDto {

    private String username;

    private String nickname;

    private String description;

    private String profileImage;

    private Verified verified;
}
