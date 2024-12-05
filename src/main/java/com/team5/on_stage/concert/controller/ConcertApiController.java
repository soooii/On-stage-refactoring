package com.team5.on_stage.concert.controller;


import com.team5.on_stage.concert.service.ConcertService;
import org.springframework.http.ResponseEntity;


import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/concert")
public class ConcertApiController {
    private final ConcertService concertService;
    // 필요 없나?
    @GetMapping("/info/{userId}")
    public void getConcertUpdatedDate(){

    }
    // 정보 집어넣는 post url => 배치일정 하루, batch 집어넣기
    @PostMapping("/info")
    public ResponseEntity<String> saveConcertList(){
        concertService.saveConcertList();
        return ResponseEntity.ok("ok");
    }
    @GetMapping("/list/{userId}")
    public void getConcertList(){
    }
    @GetMapping("/map/{userId}")
    public void getConcertMap(){
    }
    @PatchMapping("/info/{userId}")
    public void patchConcertList(){
    }
    //하루 주기 정도로 db 스케쥴링, 일정 끝난거 지워버리기
    @DeleteMapping("/delete")
    public void deleteConcertInfo(){
    }
}
