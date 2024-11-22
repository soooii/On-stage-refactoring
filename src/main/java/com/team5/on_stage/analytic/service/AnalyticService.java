package com.team5.on_stage.analytic.service;

import com.team5.on_stage.analytic.constants.EventType;
import com.team5.on_stage.analytic.dto.AnalyticRequestDto;
import com.team5.on_stage.analytic.dto.AnalyticResponseDto;
import com.team5.on_stage.analytic.entity.Analytic;
import com.team5.on_stage.analytic.repository.AnalyticRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class AnalyticService {

    @Autowired
    private AnalyticRepository analyticRepository;
    public void logEvent(AnalyticRequestDto requestDto){
        Analytic analytic = Analytic.builder()
                .eventType(EventType.valueOf(requestDto.getEventType())) // Enum 변환
                .location(requestDto.getLocation())
                .date(LocalDateTime.now()) // 현재 시간 기록
                .link(requestDto.getLinkId())
                .user(requestDto.getUserId())
                .linkDetail(requestDto.getLinkDetailId())
                .build();
        Analytic savedAnalytic = analyticRepository.save(analytic);
    }

    public List<AnalyticResponseDto> getEventCountsByDate(LocalDateTime startDate, LocalDateTime endDate) {
        // 날짜 범위 내의 Analytic 엔티티를 가져옴
        List<Analytic> analytics = analyticRepository.findByDateBetween(startDate, endDate);

        // 날짜별로 페이지 조회수와 링크 클릭 수 집계
        Map<LocalDate, Long[]> eventCounts = new HashMap<>();

        for (Analytic analytic : analytics) {
            LocalDate date = analytic.getDate().toLocalDate();
            Long[] counts = eventCounts.getOrDefault(date, new Long[]{0L, 0L});

            // 이벤트 타입에 따라 카운트 증가
            if (analytic.getEventType() == EventType.PAGE_VIEW) {
                counts[0]++;
            } else if (analytic.getEventType() == EventType.LINK_CLICK) {
                counts[1]++;
            }

            eventCounts.put(date, counts);
        }

        // DTO 리스트로 변환
        return eventCounts.entrySet().stream()
                .map(entry -> new AnalyticResponseDto(entry.getKey(), entry.getValue()[0], entry.getValue()[1]))
                .collect(Collectors.toList());
    }
}
