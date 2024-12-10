package com.team5.on_stage.analytic.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AnalyticRequestDto {
    private String ipAddress;   // 사용자 IP
    private String username;       // 사용자 이름
    private Long linkId;    // 링크 ID
    private String socialLinkType;  // 소셜 링크 ID
}
