package com.team5.on_stage.analytic.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AnalyticResponseDto {
    private Long id;          // 로그 ID
    private String message;   // 성공 메시지
}
