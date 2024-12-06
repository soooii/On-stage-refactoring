package com.team5.on_stage.analytic.dto;

import com.team5.on_stage.analytic.constants.SocialLinkType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class SocialLinkClickStatsDto {
    private SocialLinkType socialLinkType;
    private Long clickCount;
}
