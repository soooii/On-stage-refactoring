package com.team5.on_stage.analytic.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AnalyticResponseDto {
    private LocalDate date; // 날짜
    private Long pageViewCount; // 페이지 조회수
    private Long linkClickCount; // 링크 클릭 수
    private String country; // 나라
    private String region;  // 지역
}
