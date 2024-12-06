package com.team5.on_stage.analytic.controller;

import com.team5.on_stage.analytic.dto.*;
import com.team5.on_stage.analytic.service.AnalyticService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.concurrent.CompletableFuture;

@RestController
@RequestMapping("/api/analytics")
public class AnalyticController {

    @Autowired
    private AnalyticService analyticService;

    @PostMapping("/page")
    public ResponseEntity<Void> pageEvent(@RequestBody AnalyticRequestDto requestDto) {
        analyticService.pageEvent(requestDto);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @PostMapping("/link")
    public ResponseEntity<Void> linkEvent(@RequestBody AnalyticRequestDto requestDto){
        analyticService.linkEvent(requestDto);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @PostMapping("/socialLink")
    public ResponseEntity<Void> socialLinkEvent(@RequestBody AnalyticRequestDto requestDto){
        analyticService.socialLinkEvent(requestDto);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @GetMapping("/dashboard/{userName}")
    public CompletableFuture<CombinedStatsDto> getCombinedStats(
            @PathVariable String userName,
            @RequestParam LocalDate startDate,
            @RequestParam LocalDate endDate){

        CompletableFuture<List<PageViewStatsDto>> pageViewStats =
                CompletableFuture.supplyAsync(() -> analyticService.getPageViewStats(userName, startDate, endDate));

        CompletableFuture<List<SocialLinkClickStatsDto>> socialLinkClickStats =
                CompletableFuture.supplyAsync(() -> analyticService.getSocialLinkClickStats(userName, startDate, endDate));

        CompletableFuture<List<LinkClickStatsDto>> linkClickStats =
                CompletableFuture.supplyAsync(() -> analyticService.getLickClickStats(userName, startDate, endDate));

        CompletableFuture<List<LocationStatsDto>> locationStats =
                CompletableFuture.supplyAsync(() -> analyticService.getLocationStats(userName, startDate, endDate));

        return CompletableFuture.allOf(pageViewStats, socialLinkClickStats, linkClickStats, locationStats)
                .thenApply(voidResult -> {
                    CombinedStatsDto combinedStats = new CombinedStatsDto();
                    combinedStats.setPageViewStats(pageViewStats.join());
                    combinedStats.setSocialLinkClickStats(socialLinkClickStats.join());
                    combinedStats.setLinkClickStats(linkClickStats.join());
                    combinedStats.setLocationStats(locationStats.join());
                    return combinedStats;
                });
    }
}
