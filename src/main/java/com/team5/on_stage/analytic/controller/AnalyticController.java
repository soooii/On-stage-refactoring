package com.team5.on_stage.analytic.controller;

import com.team5.on_stage.analytic.dto.AnalyticRequestDto;
import com.team5.on_stage.analytic.dto.AnalyticResponseDto;
import com.team5.on_stage.analytic.service.AnalyticService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/analytics")
public class AnalyticController {

    @Autowired
    private AnalyticService analyticService;

    @PostMapping("/event")
    public ResponseEntity<AnalyticResponseDto> logEvent(@RequestBody AnalyticRequestDto requestDto) {
        AnalyticResponseDto responseDto = analyticService.logEvent(requestDto);
        return new ResponseEntity<>(responseDto, HttpStatus.CREATED);
    }
}
