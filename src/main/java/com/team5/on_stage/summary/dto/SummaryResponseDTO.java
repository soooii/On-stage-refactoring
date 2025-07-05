package com.team5.on_stage.summary.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class SummaryResponseDTO {
    private String title;
    private String summary;
    private LocalDateTime createdAt;
    private Long summaryId;
}
