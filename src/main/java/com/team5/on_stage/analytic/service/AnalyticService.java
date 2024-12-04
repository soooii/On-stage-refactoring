package com.team5.on_stage.analytic.service;

import com.querydsl.core.types.Projections;
import com.querydsl.jpa.JPAExpressions;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.team5.on_stage.analytic.constants.EventType;
import com.team5.on_stage.analytic.constants.SocialLinkType;
import com.team5.on_stage.analytic.dto.AnalyticRequestDto;
import com.team5.on_stage.analytic.dto.AnalyticResponseDto;
import com.team5.on_stage.analytic.entity.Analytic;
import com.team5.on_stage.analytic.entity.LocationInfo;
import com.team5.on_stage.analytic.entity.QAnalytic;
import com.team5.on_stage.analytic.entity.QLocationInfo;
import com.team5.on_stage.analytic.repository.AnalyticRepository;
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
    private final JPAQueryFactory queryFactory; // QueryDSL을 위한 JPAQueryFactory

    @Autowired
    public AnalyticService(AnalyticRepository analyticRepository,
                           LocationRepository locationRepository,
                           UserRepository userRepository,
                           LinkRepository linkRepository,
                           LinkDetailRepository linkDetailRepository,
                           JPAQueryFactory queryFactory) {
        this.analyticRepository = analyticRepository;
        this.locationRepository = locationRepository;
        this.userRepository = userRepository;
        this.linkRepository = linkRepository;
        this.linkDetailRepository = linkDetailRepository;
        this.queryFactory = queryFactory; // 주입
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
    public List<AnalyticResponseDto> getEventCountsByPageIdAndDateRange(String userName, LocalDate startDate, LocalDate endDate) {
        QAnalytic analytic = QAnalytic.analytic; // QueryDSL Q 클래스
        QLocationInfo locationInfo = QLocationInfo.locationInfo; // 위치 정보 Q 클래스

        User user = userRepository.findByUsername(userName);

        // QueryDSL을 사용하여 이벤트 수 집계
        return queryFactory
                .select(Projections.constructor(AnalyticResponseDto.class, // DTO의 생성자
                        analytic.date, // 날짜
                        JPAExpressions.select(analytic.count()) // 페이지 조회수
                                .from(analytic)
                                .where(analytic.eventType.eq(EventType.PAGE_VIEW)
                                        .and(analytic.user.eq(user))),
                        JPAExpressions.select(analytic.count()) // 링크 클릭 수
                                .from(analytic)
                                .where(analytic.eventType.eq(EventType.LINK_CLICK)
                                        .and(analytic.user.eq(user))),
                        locationInfo.country, // 국가
                        locationInfo.region // 지역
                ))
                .from(analytic)
                .join(analytic.locationInfo, locationInfo)
                .where(analytic.date.between(startDate, endDate)
                        .and(analytic.user.eq(user)))
                .groupBy(analytic.date, locationInfo.country, locationInfo.region) // 그룹화에 국가와 지역 추가
                .fetch();
    }
}
