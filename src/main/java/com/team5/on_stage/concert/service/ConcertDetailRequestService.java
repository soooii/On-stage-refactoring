package com.team5.on_stage.concert.service;

import com.team5.on_stage.concert.dto.ConcertDetailDto;
import com.team5.on_stage.concert.dto.RelateDto;
import com.team5.on_stage.concert.entity.Relate;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ConcertDetailRequestService {
    // api 호출한 리턴정보 받기
    //    @Value("${kopis.secret-key}") 컨트롤러 사용 불가
    private final String serviceKey= "0e9e1b8d0b1f494381162d27794deca7";

    public List<ConcertDetailDto> concertDetailRequest(List<String> mt20ids){

        List<ConcertDetailDto> concertDetailDtos = new ArrayList<>();
        for (String mt20id : mt20ids){
            ConcertDetailDto concertDetailDto = new ConcertDetailDto();
            //kopis api 이용 url
            String url = "http://www.kopis.or.kr/openApi/restful/pblprfr/"
                    + mt20id
                    +"?service="+serviceKey;
            System.out.println(url);
            try{
                Document concertDetail = Jsoup.connect(url).get();
                String prfcast = concertDetail.select("prfcast").text();
                String prfcrew = concertDetail.select("prfcrew").text();
                String mt10id = concertDetail.select("mt10id").text();
                Elements relatesData = concertDetail.select("relates");
                List<RelateDto> relates = new ArrayList<>();
                for(Element relate : relatesData){
                    String relatenm = relate.select("relatenm").text();
                    String relateurl = relate.selectFirst("relateurl").text();
                    // RelateDto 객체 생성
                    RelateDto relateTemp = RelateDto.builder()
                            .mt20id(mt20id)
                            .relateNm(relatenm) // 필드 설정
                            .relateUrl(relateurl)
                            .build();
                    relates.add(relateTemp);
                }
                concertDetailDto.setMt20id(mt20id);
                concertDetailDto.setPrfcast(prfcast);
                concertDetailDto.setPrfcrew(prfcrew);
                concertDetailDto.setMt10id(mt10id);
                // RelateDto 리스트를 Relate 엔티티 리스트로 변환
                List<Relate> relateEntities = relates.stream()
                        .map(relateDto -> relateDto.toEntity(null)) // ConcertDetail 객체는 null로 설정 (필요시 설정 가능)
                        .collect(Collectors.toList());

                concertDetailDto.setRelate(relateEntities);
            } catch (IOException e) {
                e.printStackTrace();
            }
            concertDetailDtos.add(concertDetailDto);
        }
        return concertDetailDtos;
    }
}
