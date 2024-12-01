package com.team5.on_stage.concert.controller;


import com.team5.on_stage.concert.service.ConcertService;
import org.springframework.http.ResponseEntity;


import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/concert")
public class ConCertApiController {
    private final ConcertService concertService;
    @GetMapping("/info/{userId}")
    public void getConcertUpdatedDate(){

    }
    @PostMapping("/info/{userId}")
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
    //하루 주기 정도로 db 스케쥴링
    @DeleteMapping("/delete")
    public void deleteConcertInfo(){
    }
}
