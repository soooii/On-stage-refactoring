package com.team5.on_stage.analytic.controller;

import com.team5.on_stage.analytic.dto.AnalyticRequestDto;
import com.team5.on_stage.analytic.dto.AnalyticResponseDto;
import com.team5.on_stage.analytic.service.AnalyticService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/analytics")
public class AnalyticController {

    @Autowired
    private AnalyticService analyticService;

    @PostMapping("/event")
    public ResponseEntity<AnalyticResponseDto> logEvent(@RequestBody AnalyticRequestDto requestDto) {
        analyticService.logEvent(requestDto);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @GetMapping("/dashboard/{pageId}")
    public ResponseEntity<List<AnalyticResponseDto>> getEventCountsByPageId(
            @PathVariable Long pageId,
            @RequestParam LocalDate startDate,
            @RequestParam LocalDate endDate) {
        List<AnalyticResponseDto> analytics = analyticService.getEventCountsByPageIdAndDateRange(pageId, startDate, endDate);
        return ResponseEntity.ok(analytics);
    }
}
