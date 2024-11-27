package com.team5.on_stage.analytic.repository;


import com.team5.on_stage.analytic.dto.AnalyticResponseDto;
import com.team5.on_stage.analytic.entity.Analytic;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

public interface AnalyticRepository extends JpaRepository<Analytic, Long> {

    @Query("SELECT NEW com.team5.on_stage.analytic.dto.AnalyticResponseDto(" +
            "a.date, " +  // LocalDateTime을 반환
            "SUM(CASE WHEN a.eventType = 'PAGE_VIEW' THEN 1 ELSE 0 END), " +
            "SUM(CASE WHEN a.eventType = 'LINK_CLICK' THEN 1 ELSE 0 END), " +
            "l.country, l.region) " +
            "FROM Analytic a " +
            "JOIN a.locationInfo l " +
            "WHERE a.date BETWEEN :startDate AND :endDate " +
            "GROUP BY a.date, l.country, l.region")
    List<AnalyticResponseDto> countEventsByDateAndLocation(@Param("startDate") LocalDate startDate,
                                                           @Param("endDate") LocalDate endDate);
}
