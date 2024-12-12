package com.team5.on_stage.concert.controller;


import com.team5.on_stage.concert.dto.ConcertListResponse;
import com.team5.on_stage.concert.service.ConcertService;
import com.team5.on_stage.global.config.jwt.TokenUsername;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;


import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/concert")
public class ConcertApiController {
    private final ConcertService concertService;

    // 정보 집어넣는 post url => 배치일정 하루, batch 집어넣기
    @PostMapping("/info")
    public ResponseEntity<String> saveConcertList(){
        concertService.saveConcertList();
        return ResponseEntity.ok("ok");
    }
    // 일반 페이지 path 통해서 콜
    //공유 url 끝 이름 nickname 검색 => 활동명
    // url 추출 linkcontext에서 nickname 받으면 됨
    // 매니지먼트 username 통해서 활동명 nickname
    @GetMapping("/list/{username}")
    public ResponseEntity<List<ConcertListResponse>> getConcertList(@PathVariable String username){
        //user네임 통해서 콘서트 이름 및 performer, director 검색sa
        return ResponseEntity.status(HttpStatus.OK).body(concertService.getConcertList(username));
    }
    // 관리페이지 정보 검토
    // api쪽 token값 검증
//    @GetMapping("/list")
//    public ResponseEntity<List<ConcertInfoDto>> getConcertListInProfile(@TokenUsername String username){
//        //user네임 통해서 콘서트 이름 및 performer, director 검색
//        //context 까서 nickname
//        concertService.getConcertList();
//    }

    //엄...?
    @PatchMapping("/info/{userId}")
    public void patchConcertList(){
    }
    //하루 주기 정도로 db 스케쥴링, 일정 끝난거 지워버리기
    @DeleteMapping("/delete")
    public void deleteConcertInfo(){
    }
}
