package com.team5.on_stage.subscribe;

import com.team5.on_stage.user.enums.Verified;
import lombok.Builder;

@Builder
public class SubscribedUserDto {

    private String nickname;

    private String profileImage;

    private Verified verified;

    private Integer subscribe;

    private Integer subscribed;
}
