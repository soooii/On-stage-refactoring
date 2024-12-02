package com.team5.on_stage.concert.service;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Service;
import org.w3c.dom.NodeList;

import java.io.IOException;

@Service
public class ConcertInfoRequestService {
    // api 호출한 리턴정보 받기
    //    @Value("${kopis.secret-key}") 컨트롤러 사용 불가
    private final String serviceKey= "0e9e1b8d0b1f494381162d27794deca7";

//    private static String getTagValue(String tag, Element){
//        NodeList nlList = eElement.getElementsByTagName();
//    }
    public NodeList ConcertInfoRequestService(){
        String searchKeyword = "NCT";
        //kopis api 이용 url
        String url = "http://www.kopis.or.kr/openApi/restful/pblprfr"
                +"?service="+serviceKey
                +"&stdate=20220101"
                +"&eddate=20241231"
                +"&cpage=1"
                +"&rows=10"
                +"&shprfnm="+searchKeyword;
        System.out.println(url);
        try{
            //TODO 객체로 받고 DTO List로 작성 후 bulk insert 시행
            Document concertInfo = Jsoup.connect(url).get();
            Elements concertInfoDbElements = concertInfo.select("db");
            for(Element db : concertInfoDbElements){
                db.select("mt20id").text();
                db.select("prfnm").text();
                db.select("prfpdfrom").text();
                db.select("prfpdto").text();
                db.select("fcltynm").text();
                db.select("poster").text();
                db.select("area").text();
                db.select("prfstate").text();
            }








//            System.out.println(concertInfo);
            Document concertDetail = Jsoup.connect(url).get();
            System.out.println(concertDetail);
        } catch (
                IOException e) {
            e.printStackTrace();
        }
        String urlDetail = "http://www.kopis.or.kr/openApi/restful/pblprfr/"+"PF132236"+"?service="+serviceKey;

        NodeList a = null;
        return a;
    }

}
