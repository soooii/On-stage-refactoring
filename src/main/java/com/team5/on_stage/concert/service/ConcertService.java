package com.team5.on_stage.concert.service;

import com.team5.on_stage.concert.dto.ConcertDetailDto;
import com.team5.on_stage.concert.dto.ConcertInfoDto;
import com.team5.on_stage.concert.dto.ConcertListResponse;
import com.team5.on_stage.concert.dto.ConcertPlaceDto;
import com.team5.on_stage.concert.entity.ConcertDetail;
import com.team5.on_stage.concert.entity.ConcertInfo;
import com.team5.on_stage.concert.entity.ConcertPlace;
import com.team5.on_stage.concert.entity.Relate;
import com.team5.on_stage.concert.mapper.ConcertDetailMapper;
import com.team5.on_stage.concert.mapper.ConcertInfoMapper;
import com.team5.on_stage.concert.mapper.ConcertPlaceMapper;
import com.team5.on_stage.concert.repository.ConcertDetailRepository;
import com.team5.on_stage.concert.repository.ConcertInfoRepository;
import com.team5.on_stage.concert.repository.ConcertPlaceRepository;
import com.team5.on_stage.concert.repository.RelateRepository;
import com.team5.on_stage.user.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ConcertService {
    private final ConcertInfoRequestService concertInfoRequestService;
    private final ConcertDetailRequestService concertDetailRequestService;
    private final ConcertPlaceRequestService concertPlaceRequestService;

    private final ConcertInfoRepository concertInfoRepository;
    private final ConcertDetailRepository concertDetailRepository;
    private final ConcertPlaceRepository concertPlaceRepository;
    private final RelateRepository relateRepository;
    private final UserRepository userRepository;


    private final ConcertInfoMapper concertInfoMapper;
    private final ConcertDetailMapper concertDetailMapper;
    private final ConcertPlaceMapper concertPlaceMapper;


    //저장작업(레포와 연결), 벌크인서트 사용
    //C
    @Transactional
    public void saveConcertList() {
         /*
        User user = userRepository.findById(userId).orElseThrow(() -> new GlobalException(ErrorCode.USER_NOT_FOUND));
        String keyword = user.getNickname();
        */
        //TODO transaction save < 여러 개 인서트
        List<ConcertInfoDto> concertInfoDtos = concertInfoRequestService.concertInfoRequest();
        //bulk insert
        //TODO 만약 DB에 중복키가 존재한다면 패스
        List<ConcertInfo> concertInfos = concertInfoDtos.stream()
                .map(concertInfoMapper::toEntity)
                .filter(concertInfo -> !concertInfoRepository.existsByMt20id(concertInfo.getMt20id()))
                .collect(Collectors.toList());
        concertInfoRepository.saveAll(concertInfos);
        System.out.println(concertInfos);
        List<String> mt20ids = concertInfoDtos.stream()
                .map(dto -> dto.getMt20id())
                .collect(Collectors.toList());

        List<ConcertDetailDto> concertDetailDtos = concertDetailRequestService.concertDetailRequest(mt20ids);
        //bulk insert
        List<ConcertDetail> concertDetails = concertDetailDtos.stream()
                .map(concertDetailMapper::toEntity)
                .collect(Collectors.toList());
        concertInfoRepository.saveAll(concertInfos);
        System.out.println(concertInfos);
        List<String> mt10ids = concertDetailDtos.stream()
                .map(dto -> dto.getMt10id())
                .collect(Collectors.toList());
        concertDetailRepository.saveAll(concertDetails);
        System.out.println(concertDetails);
        List<ConcertPlaceDto> concertPlaceDtos = concertPlaceRequestService.concertPlaceRequest(mt10ids);
        //bulk insert
        List<ConcertPlace> concertPlaces = concertPlaceDtos.stream()
                .map(concertPlaceMapper::toEntity)
                .collect(Collectors.toList());
        concertPlaceRepository.saveAll(concertPlaces);
        System.out.println(concertPlaces);

    }
    public List<ConcertListResponse> getConcertList(String username){
        String nickname = userRepository.findByUsername(username).getNickname();
        // 1. ConcertInfo 검색
        List<ConcertInfo> infoList = concertInfoRepository.findByPrfnmContaining(nickname);

        // 2. ConcertDetail 검색
        List<ConcertDetail> detailList = concertDetailRepository.findByPrfcastContaining(nickname);

        // 3. 두 리스트의 공통된 ConcertInfo 필터링
        List<ConcertInfo> filteredInfoList = infoList.stream()
                .filter(info -> detailList.stream()
                        .anyMatch(detail -> detail.getConcertInfo().getMt20id().equals(info.getMt20id())))
                .collect(Collectors.toList());

        // 4. ConcertDetailResponse로 변환하여 반환
        //ConcertDetail은 연관 데이터(ConcertPlace, Relate)를 포함하고 있어 DTO 변환이 효율적
        return filteredInfoList.stream()
                .map(info -> {
                    ConcertDetail detail = detailList.stream()
                            .filter(d -> d.getConcertInfo().getMt20id().equals(info.getMt20id()))
                            .findFirst()
                            //TODO global exception 처리
                            .orElseThrow(() -> new IllegalArgumentException("Detail not found for concert: " + info.getMt20id()));

                    ConcertPlace place = detail.getConcertPlace();
                    List<Relate> relates = detail.getRelate();

                    return ConcertListResponse.builder()
                            .concertId(info.getMt20id())
                            .concertName(info.getPrfnm())
                            .startDate(info.getPrfpdfrom().toString())
                            .endDate(info.getPrfpdto().toString())
                            .placeName(info.getFcltynm())
                            .posterUrl(info.getPoster())
                            .area(info.getArea())
                            .concertState(info.getPrfstate())
                            .performer(detail.getPrfcast())
                            .director(detail.getPrfcrew())
                            .placeId(detail.getMt10id())
                            .placeInfo(ConcertListResponse.PlaceInfo.builder()
                                    .placeId(place.getMt10id())
                                    .address(place.getAdres())
                                    .placeName(place.getFcltynm())
                                    .latitude(place.getLatitude())
                                    .longitude(place.getLongtitude())
                                    .build())
                            .relateInfos(relates.stream()
                                    .map(relate -> ConcertListResponse.RelateInfo.builder()
                                            .relateName(relate.getRelateNm())
                                            .relateUrl(relate.getRelateUrl())
                                            .build())
                                    .collect(Collectors.toList()))
                            .build();
                })
                .collect(Collectors.toList());
    }
    public void getConcertMap(){
    }

    //U
    public void patchConcertList(){
    }

    //D
    public void deleteConcertInfo(){
    }
}