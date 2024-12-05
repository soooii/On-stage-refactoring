package com.team5.on_stage.analytic.repository;

import com.team5.on_stage.analytic.dto.LinkClickStatsDto;
import com.team5.on_stage.analytic.dto.SocialLinkClickStatsDto;
import com.team5.on_stage.analytic.dto.LocationStatsDto;
import com.team5.on_stage.analytic.dto.PageViewStatsDto;

import java.time.LocalDate;
import java.util.List;

public interface AnalyticRepositoryCustom {
    List<PageViewStatsDto> getPageViewStats(String userName, LocalDate startDate, LocalDate endDate);
    List<SocialLinkClickStatsDto> getSocialLinkClickStats(String userName, LocalDate startDate, LocalDate endDate);
    List<LocationStatsDto> getLocationStats(String userName, LocalDate startDate, LocalDate endDate);
    List<LinkClickStatsDto> getLinkClickStats(String userName, LocalDate startDate, LocalDate endDate);
}
