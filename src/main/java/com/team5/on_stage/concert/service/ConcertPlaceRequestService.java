package com.team5.on_stage.concert.service;

import com.team5.on_stage.concert.dto.ConcertPlaceDto;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Service
public class ConcertPlaceRequestService {
    // api 호출한 리턴정보 받기
    //    @Value("${kopis.secret-key}") 컨트롤러 사용 불가
    private final String serviceKey= "0e9e1b8d0b1f494381162d27794deca7";

    public List<ConcertPlaceDto> concertPlaceRequest(List<String> mt10ids){

        List<ConcertPlaceDto> concertPlaceDtos = new ArrayList<>();
        for (String mt10id : mt10ids){
            ConcertPlaceDto concertPlaceDto = new ConcertPlaceDto();
            //kopis api 이용 url
            String url = "http://www.kopis.or.kr/openApi/restful/prfplc/"
                    + mt10id
                    +"?service="+serviceKey;
            System.out.println(url);
            try{
                Document concertPlace = Jsoup.connect(url).get();
                // 아래 수정
                String fcltynm = concertPlace.select("fcltynm").text();
                String adres = concertPlace.select("adres").text();
                double latitude = Double.parseDouble(concertPlace.select("la").text());
                double longtitude = Double.parseDouble(concertPlace.select("lo").text());
                concertPlaceDto.setMt10id(mt10id);
                concertPlaceDto.setFcltynm(fcltynm);
                concertPlaceDto.setAdres(adres);
                concertPlaceDto.setLatitude(latitude);
                concertPlaceDto.setLongtitude(longtitude);
            } catch (IOException e) {
                e.printStackTrace();
            }
            concertPlaceDtos.add(concertPlaceDto);
        }
        return concertPlaceDtos;
    }
}
