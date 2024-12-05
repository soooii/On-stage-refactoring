package com.team5.on_stage.analytic.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class LocationStatsDto {
    private String country;
    private String region;
    private Long pageViewCount;
}
