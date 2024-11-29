package com.team5.on_stage.summary.repository;

import com.team5.on_stage.summary.dto.SummaryResponseDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface SummaryQueryDslRepository {
    void softDeleteByUserId(Long userId);
    //Page<SummaryResponseDTO> findByUserId(Long userId, Pageable pageable);
}
