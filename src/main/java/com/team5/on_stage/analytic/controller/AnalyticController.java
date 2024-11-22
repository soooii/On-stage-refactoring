package com.team5.on_stage.analytic.controller;

import com.team5.on_stage.analytic.dto.AnalyticRequestDto;
import com.team5.on_stage.analytic.dto.AnalyticResponseDto;
import com.team5.on_stage.analytic.service.AnalyticService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/analytics")
public class AnalyticController {

    @Autowired
    private AnalyticService analyticService;

    @PostMapping("/event")
    public ResponseEntity<AnalyticResponseDto> logEvent(@RequestBody AnalyticRequestDto requestDto) {
        analyticService.logEvent(requestDto);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @GetMapping("/event/dashboard")
    public ResponseEntity<List<AnalyticResponseDto>> getEventCountsByDate(
            @RequestParam LocalDateTime startDate,
            @RequestParam LocalDateTime endDate) {
        List<AnalyticResponseDto> analytics = analyticService.getEventCountsByDate(startDate, endDate);
        return ResponseEntity.ok(analytics);
    }
}
