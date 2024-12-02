package com.team5.on_stage.summary.repository;

import com.team5.on_stage.summary.dto.SummaryResponseDTO;
import com.team5.on_stage.summary.entity.Summary;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface SummaryQueryDslRepository {
    void softDeleteByUserId(Long userId);
    List<Summary> getSummaryByUserId(Long userId, Pageable pageable);
    long countSummaryByUserId(Long userId);
}
