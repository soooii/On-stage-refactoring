package com.team5.on_stage.summary.repository;

import com.team5.on_stage.summary.entity.Summary;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SummaryRespository extends JpaRepository<Summary, Long> {
    Page<Summary> findSummariesByUserId(Long userId, Pageable pageable);
    void deleteSummariesByUserId(Long userId);
}
