package com.team5.on_stage.analytic.service;

import com.team5.on_stage.analytic.constants.EventType;
import com.team5.on_stage.analytic.dto.AnalyticRequestDto;
import com.team5.on_stage.analytic.dto.AnalyticResponseDto;
import com.team5.on_stage.analytic.entity.Analytic;
import com.team5.on_stage.analytic.entity.LocationInfo;
import com.team5.on_stage.analytic.repository.AnalyticRepository;
import com.team5.on_stage.analytic.repository.LocationRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class AnalyticService {

    private final AnalyticRepository analyticRepository;
    private final LocationRepository locationRepository;

    public AnalyticService(AnalyticRepository analyticRepository, LocationRepository locationRepository) {
        this.analyticRepository = analyticRepository;
        this.locationRepository = locationRepository;
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
    public List<AnalyticResponseDto> getEventCountsByDateAndLocation(LocalDateTime startDate, LocalDateTime endDate) {
        return analyticRepository.countEventsByDateAndLocation(startDate.toLocalDate(), endDate.toLocalDate());
    }
}
