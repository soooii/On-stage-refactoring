package com.team5.on_stage.analytic.service;

import com.team5.on_stage.analytic.constants.EventType;
import com.team5.on_stage.analytic.dto.AnalyticRequestDto;
import com.team5.on_stage.analytic.dto.AnalyticResponseDto;
import com.team5.on_stage.analytic.entity.Analytic;
import com.team5.on_stage.analytic.repository.AnalyticRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class AnalyticService {

    @Autowired
    private AnalyticRepository analyticRepository;
    public AnalyticResponseDto logEvent(AnalyticRequestDto requestDto){
        Analytic analytic = Analytic.builder()
                .eventType(EventType.valueOf(requestDto.getEventType())) // Enum 변환
                .location(requestDto.getLocation())
                .date(LocalDateTime.now()) // 현재 시간 기록
                .page(requestDto.getPageId())
                .user(requestDto.getUserId())
                .link_detail(requestDto.getLinkDetailId())
                .build();
        Analytic savedAnalytic = analyticRepository.save(analytic);

        return new AnalyticResponseDto(savedAnalytic.getId(), "Log event saved successfully.");
    }
}
