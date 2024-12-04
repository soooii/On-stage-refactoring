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
@RequestMapping("/api/analytics")
public class AnalyticController {

    @Autowired
    private AnalyticService analyticService;

    @PostMapping("/page")
    public ResponseEntity<AnalyticResponseDto> pageEvent(@RequestBody AnalyticRequestDto requestDto) {
        analyticService.pageEvent(requestDto);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @PostMapping("/link")
    public ResponseEntity<AnalyticResponseDto> linkEvent(@RequestBody AnalyticRequestDto requestDto){
        analyticService.linkEvent(requestDto);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @PostMapping("/socialLink")
    public ResponseEntity<AnalyticResponseDto> socialLinkEvent(@RequestBody AnalyticRequestDto requestDto){
        analyticService.socialLinkEvent(requestDto);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @GetMapping("/dashboard/{userName}")
    public ResponseEntity<List<AnalyticResponseDto>> getEventCountsByUserName(
            @PathVariable String userName,
            @RequestParam LocalDate startDate,
            @RequestParam LocalDate endDate) {
        List<AnalyticResponseDto> analytics = analyticService.getEventCountsByPageIdAndDateRange(userName, startDate, endDate);
        return ResponseEntity.ok(analytics);
    }
}
