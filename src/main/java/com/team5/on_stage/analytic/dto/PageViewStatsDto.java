package com.team5.on_stage.analytic.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class PageViewStatsDto {
    private LocalDate date;
    private Long pageViewCount;
    private Long linkClickCount;
//    private Long subscriberCount; // 구독자 수
}