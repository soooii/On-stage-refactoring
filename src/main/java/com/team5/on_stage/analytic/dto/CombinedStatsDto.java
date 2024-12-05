package com.team5.on_stage.analytic.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class CombinedStatsDto {
    private List<PageViewStatsDto> pageViewStats;
    private List<SocialLinkClickStatsDto> socialLinkClickStats;
    private List<LinkClickStatsDto> linkClickStats;
    private List<LocationStatsDto> locationStats;
}
