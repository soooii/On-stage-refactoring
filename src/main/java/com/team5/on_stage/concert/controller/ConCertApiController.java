package com.team5.on_stage.concert.controller;


import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/concert")
public class ConCertApiController {
    @GetMapping("/info/{userId}")
    public void getConcertUpdatedDate(){

    }
    @PostMapping("/info/{userId}")
    public void saveConcertList(){

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
