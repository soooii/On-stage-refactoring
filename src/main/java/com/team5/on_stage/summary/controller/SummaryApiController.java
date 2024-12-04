package com.team5.on_stage.summary.controller;

import com.team5.on_stage.summary.dto.SummaryRequestDTO;
import com.team5.on_stage.summary.dto.SummaryResponseDTO;
import com.team5.on_stage.summary.entity.Summary;
import com.team5.on_stage.summary.service.SummaryService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequiredArgsConstructor
@RequestMapping("/api/summary/")
public class SummaryApiController {
    private final SummaryService summaryService;

    @PostMapping("/{username}")
    public ResponseEntity<String> saveSummary(@PathVariable String username) {
        summaryService.saveSummary(username);
        return ResponseEntity.ok("뉴스 요약이 저장되었습니다.");
    }

    //해당하는 페이지의 아티스트 뉴스 가져오기
    @GetMapping("/{username}")
    public ResponseEntity<Page<SummaryResponseDTO>> getSummary(
            @PathVariable String username,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "2") int size) {

        SummaryRequestDTO request = new SummaryRequestDTO(username, page, size);
        Page<SummaryResponseDTO> response = summaryService.getSummary(request);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{username}")
    public ResponseEntity<String> deleteSummary(@PathVariable String username){
        summaryService.deleteSummary(username);
        return ResponseEntity.ok("뉴스 요약 삭제가 완료되었습니다.");
    }



}
