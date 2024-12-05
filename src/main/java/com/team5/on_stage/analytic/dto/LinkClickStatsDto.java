package com.team5.on_stage.analytic.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class LinkClickStatsDto {
    private String linkTitle;
    private Long clickCount;
}
