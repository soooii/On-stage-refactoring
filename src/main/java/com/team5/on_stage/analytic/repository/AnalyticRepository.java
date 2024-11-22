package com.team5.on_stage.analytic.repository;


import com.team5.on_stage.analytic.dto.AnalyticResponseDto;
import com.team5.on_stage.analytic.entity.Analytic;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;

public interface AnalyticRepository extends JpaRepository<Analytic, Long> {
    List<Analytic> findByDateBetween(LocalDateTime startDate, LocalDateTime endDate);
}
