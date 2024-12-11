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
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;

@Slf4j
@Service
public class AnalyticService {

    // IP 주소 응답을 위한 내부 클래스
    @Getter
    @Setter
    private static class IpResponse {
        private String ip;
    }
    private final String IPIFY_API_URL = "https://api.ipify.org?format=json";
    private static final String API_URL = "https://ipapi.co/{ip}/json/";
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

//    @Value("${api.geolocation.url}")
//    private String API_URL;

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

//    private LocationInfo getLocationByIp(String ipAddress) {
//        RestTemplate restTemplate = new RestTemplate();
//        LocationInfo locationInfo = restTemplate.getForObject(API_URL + ipAddress, LocationInfo.class);
//
//        // 위치 정보가 없으면 새로 생성하여 DB에 저장
//        if (locationInfo != null) {
//            LocationInfo existingLocationInfo = locationRepository.findByIpAddress(ipAddress).orElse(null);
//            if (existingLocationInfo == null) {
//                // 새로운 위치 정보 저장
//                locationInfo.setIpAddress(ipAddress); // IP 주소 저장
//                return locationRepository.save(locationInfo);
//            } else {
//                return existingLocationInfo; // 기존 위치 정보 반환
//            }
//        }
//        return null; // 위치 정보가 없는 경우
//    }
    public LocationInfo getLocationByIp(String ipAddress) {
        RestTemplate restTemplate = new RestTemplate();
        String url = API_URL.replace("{ip}", ipAddress);

        try {
            ResponseEntity<Map<String, Object>> response = restTemplate.exchange(
                    url,
                    HttpMethod.GET,
                    null,
                    new ParameterizedTypeReference<Map<String, Object>>() {}
            );

            if (response.getStatusCode() == HttpStatus.OK) {
                Map<String, Object> data = response.getBody();

                if (data != null) {
                    // LocationInfo 엔티티에 맞게 수정
                    LocationInfo locationInfo = LocationInfo.builder()
                            .ipAddress(ipAddress)
                            .country((String) data.get("country_name"))
                            .region((String) data.get("region"))
                            .build();

                    // 기존 위치 정보 확인 및 저장
                    return locationRepository.findByIpAddress(ipAddress)
                            .orElseGet(() -> locationRepository.save(locationInfo));
                }
            }

            log.warn("IP 위치 조회 실패. 상태 코드: {}", response.getStatusCode());
        } catch (RestClientException e) {
            log.error("IP 위치 조회 중 네트워크 오류: {}", e.getMessage(), e);
        } catch (Exception e) {
            log.error("예상치 못한 오류 발생: {}", e.getMessage(), e);
        }

        return null;
    }

    @Transactional
    public void linkEvent(AnalyticRequestDto requestDto){

        User user = userRepository.findByUsername(requestDto.getUsername());

        Link link = linkRepository.findById(requestDto.getLinkId())
                .orElseThrow(()-> new GlobalException(ErrorCode.LINK_NOT_FOUND));

        Analytic analytic = Analytic.builder()
                .eventType(EventType.valueOf("LINK_CLICK"))
                .link(link)
                .date(LocalDate.now())
                .user(user)
                .build();
        analyticRepository.save(analytic);
    }

    @Transactional
    public void socialLinkEvent(AnalyticRequestDto requestDto){

        User user = userRepository.findByUsername(requestDto.getUsername());

        Analytic analytic = Analytic.builder()
                .eventType(EventType.valueOf("SOCIAL_LINK_CLICK"))
                .socialLinkType(SocialLinkType.valueOf(requestDto.getSocialLinkType()))
                .date(LocalDate.now())
                .user(user)
                .build();

        analyticRepository.save(analytic);
    }

    public String getPublicIp() {
        RestTemplate restTemplate = new RestTemplate();
        IpResponse response = restTemplate.getForObject(IPIFY_API_URL, IpResponse.class);
        return response != null ? response.getIp() : null;
    }

    public CompletableFuture<List<PageViewStatsDto>> getPageViewStats(String userName, LocalDate startDate, LocalDate endDate) {
        return CompletableFuture.supplyAsync(() -> analyticRepositoryCustom.getPageViewStats(userName, startDate, endDate));
    }

    public CompletableFuture<List<SocialLinkClickStatsDto>> getSocialLinkClickStats(String userName, LocalDate startDate, LocalDate endDate) {
        return CompletableFuture.supplyAsync(() -> analyticRepositoryCustom.getSocialLinkClickStats(userName, startDate, endDate));
    }

    public CompletableFuture<List<LinkClickStatsDto>> getLinkClickStats(String userName, LocalDate startDate, LocalDate endDate) {
        return CompletableFuture.supplyAsync(() -> analyticRepositoryCustom.getLinkClickStats(userName, startDate, endDate));
    }

    public CompletableFuture<List<LocationStatsDto>> getLocationStats(String userName, LocalDate startDate, LocalDate endDate) {
        return CompletableFuture.supplyAsync(() -> analyticRepositoryCustom.getLocationStats(userName, startDate, endDate));
    }
}
