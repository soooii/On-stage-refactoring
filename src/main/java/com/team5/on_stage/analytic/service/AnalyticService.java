package com.team5.on_stage.analytic.service;

import com.querydsl.core.types.Projections;
import com.querydsl.jpa.JPAExpressions;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.team5.on_stage.analytic.constants.EventType;
import com.team5.on_stage.analytic.dto.AnalyticRequestDto;
import com.team5.on_stage.analytic.dto.AnalyticResponseDto;
import com.team5.on_stage.analytic.entity.Analytic;
import com.team5.on_stage.analytic.entity.LocationInfo;
import com.team5.on_stage.analytic.entity.QAnalytic;
import com.team5.on_stage.analytic.entity.QLocationInfo;
import com.team5.on_stage.analytic.repository.AnalyticRepository;
import com.team5.on_stage.analytic.repository.LocationRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDate;
import java.util.List;

@Service
public class AnalyticService {

    private final AnalyticRepository analyticRepository;
    private final LocationRepository locationRepository;
    private final JPAQueryFactory queryFactory; // QueryDSL을 위한 JPAQueryFactory

    @Autowired
    public AnalyticService(AnalyticRepository analyticRepository,
                           LocationRepository locationRepository,
                           JPAQueryFactory queryFactory) {
        this.analyticRepository = analyticRepository;
        this.locationRepository = locationRepository;
        this.queryFactory = queryFactory; // 주입
    }

    @Value("${api.geolocation.url}")
    private String API_URL;

    @Transactional
    public void logEvent(AnalyticRequestDto requestDto){
        LocationInfo locationInfo = getLocationByIp(requestDto.getIpAddress());

        Analytic analytic = Analytic.builder()
                .eventType(EventType.valueOf(requestDto.getEventType())) // Enum 변환
                .locationInfo(locationInfo)
                .date(LocalDate.now()) // 현재 시간 기록
                .link(requestDto.getLinkId())
                .user(requestDto.getUserId())
                .linkDetail(requestDto.getLinkDetailId())
                .build();
        Analytic savedAnalytic = analyticRepository.save(analytic);
    }

    @Transactional
    private LocationInfo getLocationByIp(String ipAddress) {
        RestTemplate restTemplate = new RestTemplate();
        LocationInfo locationInfo = restTemplate.getForObject(API_URL + ipAddress, LocationInfo.class);

        // 위치 정보가 없으면 새로 생성하여 DB에 저장
        if (locationInfo != null) {
            LocationInfo existingLocationInfo = locationRepository.findByIpAddress(ipAddress).orElse(null);
            if (existingLocationInfo == null) {
                // 새로운 위치 정보 저장
                locationInfo.setIpAddress(ipAddress); // IP 주소 저장
                return locationRepository.save(locationInfo);
            } else {
                return existingLocationInfo; // 기존 위치 정보 반환
            }
        }
        return null; // 위치 정보가 없는 경우
    }

    @Transactional
    public List<AnalyticResponseDto> getEventCountsByPageIdAndDateRange(Long pageId, LocalDate startDate, LocalDate endDate) {
        QAnalytic analytic = QAnalytic.analytic; // QueryDSL Q 클래스
        QLocationInfo locationInfo = QLocationInfo.locationInfo; // 위치 정보 Q 클래스

        // QueryDSL을 사용하여 이벤트 수 집계
        return queryFactory
                .select(Projections.constructor(AnalyticResponseDto.class, // DTO의 생성자
                        analytic.date, // 날짜
                        JPAExpressions.select(analytic.count()) // 페이지 조회수
                                .from(analytic)
                                .where(analytic.eventType.eq(EventType.PAGE_VIEW)
                                        .and(analytic.link.eq(pageId))),
                        JPAExpressions.select(analytic.count()) // 링크 클릭 수
                                .from(analytic)
                                .where(analytic.eventType.eq(EventType.LINK_CLICK)
                                        .and(analytic.link.eq(pageId))),
                        locationInfo.country, // 국가
                        locationInfo.region // 지역
                ))
                .from(analytic)
                .join(analytic.locationInfo, locationInfo)
                .where(analytic.date.between(startDate, endDate)
                        .and(analytic.link.eq(pageId)))
                .groupBy(analytic.date, locationInfo.country, locationInfo.region) // 그룹화에 국가와 지역 추가
                .fetch();
    }
}
