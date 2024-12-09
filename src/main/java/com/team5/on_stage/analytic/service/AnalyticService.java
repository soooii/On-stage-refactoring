package com.team5.on_stage.analytic.service;

import com.team5.on_stage.analytic.constants.EventType;
import com.team5.on_stage.analytic.constants.SocialLinkType;
import com.team5.on_stage.analytic.dto.*;
import com.team5.on_stage.analytic.entity.Analytic;
import com.team5.on_stage.analytic.entity.LocationInfo;
import com.team5.on_stage.analytic.repository.AnalyticRepository;
import com.team5.on_stage.analytic.repository.AnalyticRepositoryCustom;
import com.team5.on_stage.analytic.repository.LocationRepository;
import com.team5.on_stage.global.constants.ErrorCode;
import com.team5.on_stage.global.exception.GlobalException;
import com.team5.on_stage.link.entity.Link;
import com.team5.on_stage.link.repository.LinkRepository;
import com.team5.on_stage.linkDetail.entity.LinkDetail;
import com.team5.on_stage.linkDetail.repository.LinkDetailRepository;
import com.team5.on_stage.user.entity.User;
import com.team5.on_stage.user.repository.UserRepository;
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
    private final UserRepository userRepository;
    private final LinkRepository linkRepository;
    private final LinkDetailRepository linkDetailRepository;
    private final AnalyticRepositoryCustom analyticRepositoryCustom;
    @Autowired
    public AnalyticService(AnalyticRepository analyticRepository,
                           LocationRepository locationRepository,
                           UserRepository userRepository,
                           LinkRepository linkRepository,
                           LinkDetailRepository linkDetailRepository,
                           AnalyticRepositoryCustom analyticRepositoryCustom) {
        this.analyticRepository = analyticRepository;
        this.locationRepository = locationRepository;
        this.userRepository = userRepository;
        this.linkRepository = linkRepository;
        this.linkDetailRepository = linkDetailRepository;
        this.analyticRepositoryCustom = analyticRepositoryCustom;
    }

    @Value("${api.geolocation.url}")
    private String API_URL;

    @Transactional
    public void pageEvent(AnalyticRequestDto requestDto){

        LocationInfo locationInfo = getLocationByIp(requestDto.getIpAddress());

        User user = userRepository.findByUsername(requestDto.getUsername());

        Analytic analytic = Analytic.builder()
                .eventType(EventType.valueOf("PAGE_VIEW")) // Enum 변환
                .locationInfo(locationInfo)
                .date(LocalDate.now()) // 현재 시간 기록
                .user(user)
                .build();

        analyticRepository.save(analytic);
    }

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
    public void linkEvent(AnalyticRequestDto requestDto){

        LinkDetail linkDetail = linkDetailRepository.findById(requestDto.getLinkDetailId())
                .orElseThrow(() -> new GlobalException(ErrorCode.LINK_DETAIL_NOT_FOUND));

        User user = userRepository.findByUsername(requestDto.getUsername());

        Link link = linkRepository.findById(requestDto.getLinkId())
                .orElseThrow(()-> new GlobalException(ErrorCode.LINK_NOT_FOUND));

        Analytic analytic = Analytic.builder()
                .eventType(EventType.valueOf("LINK_CLICK"))
                .link(link)
                .linkDetail(linkDetail)
                .date(LocalDate.now())
                .user(user)
                .build();
        analyticRepository.save(analytic);
    }

    @Transactional
    public void socialLinkEvent(AnalyticRequestDto requestDto){

        User user = userRepository.findByUsername(requestDto.getUsername());

        Analytic analytic = Analytic.builder()
                .eventType(EventType.valueOf("LINK_CLICK"))
                .socialLinkType(SocialLinkType.valueOf(requestDto.getSocialLinkType()))
                .date(LocalDate.now())
                .user(user)
                .build();

        analyticRepository.save(analytic);
    }

    @Transactional
    public List<PageViewStatsDto> getPageViewStats(String userName, LocalDate startDate, LocalDate endDate) {
        return analyticRepositoryCustom.getPageViewStats(userName, startDate, endDate);
    }

    @Transactional
    public List<SocialLinkClickStatsDto> getSocialLinkClickStats(String userName, LocalDate startDate, LocalDate endDate) {
        return analyticRepositoryCustom.getSocialLinkClickStats(userName, startDate, endDate);
    }

    @Transactional
    public List<LinkClickStatsDto> getLickClickStats(String userName, LocalDate startDate, LocalDate endDate) {
        return analyticRepositoryCustom.getLinkClickStats(userName, startDate, endDate);
    }

    @Transactional
    public List<LocationStatsDto> getLocationStats(String userName, LocalDate startDate, LocalDate endDate) {
        return analyticRepositoryCustom.getLocationStats(userName, startDate, endDate);
    }
}
