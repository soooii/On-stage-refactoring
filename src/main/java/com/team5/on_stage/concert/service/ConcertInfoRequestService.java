package com.team5.on_stage.concert.service;

import com.team5.on_stage.concert.dto.ConcertInfoDto;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@Service
public class ConcertInfoRequestService {
    // api 호출한 리턴정보 받기
    //    @Value("${kopis.secret-key}") 컨트롤러 사용 불가
    private final String serviceKey= "0e9e1b8d0b1f494381162d27794deca7";
    // DateTimeFormatter 설정 (Jsoup의 문자열 형식에 맞게)
    private DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy.MM.dd");

    public List<ConcertInfoDto> concertInfoRequest(){
//        String searchKeyword = "NCT";
        //kopis api 이용 url
        String url = "http://www.kopis.or.kr/openApi/restful/pblprfr"
                +"?service="+serviceKey
                +"&stdate=20241201"
                +"&eddate=20241231"
                +"&cpage=1"
                +"&rows=100";
        //100건 제한, 조회 31일 제한 => 1일마다 갱신
//                +"&shprfnm="+searchKeyword;
        System.out.println(url);
        List<ConcertInfoDto> concertInfoDtos = new ArrayList<>();
        try{
            //TODO 객체로 받고 DTO List로 작성 후 bulk insert 시행
            Document concertInfo = Jsoup.connect(url).get();
            Elements concertInfoDbElements = concertInfo.select("db");
            for(Element db : concertInfoDbElements){
                String mt20id = db.select("mt20id").text();
                //DB 저장
                String prfnm = db.select("prfnm").text();
                // 날짜 필드 값 처리
                String prfpdfromText = db.select("prfpdfrom").text();
                LocalDate prfpdfrom = LocalDate.parse(prfpdfromText, formatter);
                String prfpdtoText = db.select("prfpdto").text();
                LocalDate prfpdto = LocalDate.parse(prfpdtoText, formatter);

                String  fcltynm = db.select("fcltynm").text();
                String  poster = db.select("poster").text();
                String area = db.select("area").text();
                String prfstate = db.select("prfstate").text();
                ConcertInfoDto concertInfoDto = new ConcertInfoDto();
                concertInfoDto.setMt20id(mt20id);
                concertInfoDto.setPrfnm(prfnm);
                concertInfoDto.setPrfpdfrom(prfpdfrom);
                concertInfoDto.setPrfpdto(prfpdto);
                concertInfoDto.setFcltynm(fcltynm);
                concertInfoDto.setPoster(poster);
                concertInfoDto.setArea(area);
                concertInfoDto.setPrfstate(prfstate);
                concertInfoDtos.add(concertInfoDto);
            }
            System.out.println(concertInfo);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return concertInfoDtos;
    }

}
