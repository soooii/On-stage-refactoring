package com.team5.on_stage.subscribe;

import com.team5.on_stage.user.entity.Verified;
import lombok.Builder;

@Builder
public class SubscribedUserDto {

    private String nickname;

    private String profileImage;

    private Verified verified;

    private int subscribed;
}
