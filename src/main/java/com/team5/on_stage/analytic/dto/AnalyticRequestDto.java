package com.team5.on_stage.analytic.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AnalyticRequestDto {
    private String eventType;  // "PAGE_VIEW", "BUTTON_CLICK" 등
    private String location;   // 사용자 위치
    private Long linkId;       // 페이지 ID
    private Long userId;       // 사용자 ID
    private Long linkDetailId; // 링크 세부 정보
}
