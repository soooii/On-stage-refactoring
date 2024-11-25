package com.team5.on_stage.summary.controller;

import com.team5.on_stage.summary.dto.SummaryResponseDTO;
import com.team5.on_stage.summary.entity.Summary;
import com.team5.on_stage.summary.service.SummaryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequiredArgsConstructor
@RequestMapping("/api/summary/")
public class SummaryApiController {
    private final SummaryService summaryService;

    @PostMapping("/{userId}")
    public ResponseEntity<String> saveSummary(@PathVariable Long userId) {
        summaryService.saveSummary(userId);
        return ResponseEntity.ok("뉴스 요약이 저장되었습니다.");
    }

    @GetMapping("/{userId}")
    public ResponseEntity<List<SummaryResponseDTO>> getSummary(@PathVariable Long userId) {
        List<SummaryResponseDTO> summaryResponseDTO = summaryService.getSummary(userId);
        return ResponseEntity.ok(summaryResponseDTO);
    }

    @DeleteMapping("/{userId}")
    public ResponseEntity<String> deleteSummary(@PathVariable Long userId){
        return ResponseEntity.ok("뉴스 요약 삭제가 완료되었습니다.");
    }



}
