package com.team5.on_stage.analytic.controller;

import com.team5.on_stage.analytic.dto.*;
import com.team5.on_stage.analytic.service.AnalyticService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.concurrent.CompletableFuture;

@RestController
@RequestMapping("/analytics")
@Tag(name = "Analytics Controller", description = "APIs for tracking and retrieving analytics data")
public class AnalyticController {

    @Autowired
    private AnalyticService analyticService;

    @PostMapping("/page")
    @Operation(summary = "Record a page view event",
            description = "Logs analytics data for a page view")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Page event successfully recorded"),
            @ApiResponse(responseCode = "400", description = "Invalid request data")
    })
    public ResponseEntity<Void> pageEvent(
            @RequestBody
            @Parameter(description = "Page view event details", required = true)
            AnalyticRequestDto requestDto) {
        analyticService.pageEvent(requestDto);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @PostMapping("/link")
    @Operation(summary = "Record a link click event",
            description = "Logs analytics data for a link click")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Link event successfully recorded"),
            @ApiResponse(responseCode = "400", description = "Invalid request data")
    })
    public ResponseEntity<Void> linkEvent(
            @RequestBody
            @Parameter(description = "Link click event details", required = true)
            AnalyticRequestDto requestDto){
        analyticService.linkEvent(requestDto);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @PostMapping("/socialLink")
    @Operation(summary = "Record a social link click event",
            description = "Logs analytics data for a social link click")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Social link event successfully recorded"),
            @ApiResponse(responseCode = "400", description = "Invalid request data")
    })
    public ResponseEntity<Void> socialLinkEvent(
            @RequestBody
            @Parameter(description = "Social link click event details", required = true)
            AnalyticRequestDto requestDto){
        analyticService.socialLinkEvent(requestDto);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @GetMapping("/get-ip")
    @Operation(summary = "Get public IP address",
            description = "Retrieves the current public IP address")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Public IP successfully retrieved"),
            @ApiResponse(responseCode = "500", description = "Error retrieving IP address")
    })
    public String getIp(){
        return analyticService.getPublicIp();
    }

    @GetMapping("/dashboard")
    @Operation(summary = "Get combined analytics statistics",
            description = "Retrieves comprehensive analytics statistics for a user within a date range")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Analytics statistics successfully retrieved"),
            @ApiResponse(responseCode = "400", description = "Invalid input parameters")
    })
    public CompletableFuture<CombinedStatsDto> getCombinedStats(
            @Parameter(description = "Username to retrieve stats for", required = true)
            @RequestParam String userName,
            @Parameter(description = "Start date for statistics", required = true)
            @RequestParam LocalDate startDate,
            @Parameter(description = "End date for statistics", required = true)
            @RequestParam LocalDate endDate) {

        CompletableFuture<List<PageViewStatsDto>> pageViewStats = analyticService.getPageViewStats(userName, startDate, endDate);
        CompletableFuture<List<SocialLinkClickStatsDto>> socialLinkClickStats = analyticService.getSocialLinkClickStats(userName, startDate, endDate);
        CompletableFuture<List<LinkClickStatsDto>> linkClickStats = analyticService.getLinkClickStats(userName, startDate, endDate);
        CompletableFuture<List<LocationStatsDto>> locationStats = analyticService.getLocationStats(userName, startDate, endDate);

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
