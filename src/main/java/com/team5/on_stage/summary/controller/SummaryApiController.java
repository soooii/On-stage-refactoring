package com.team5.on_stage.summary.controller;

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

    @PostMapping("/{userId}")
    public ResponseEntity<String> saveSummary(@PathVariable Long userId) {
        summaryService.saveSummary(userId);
        return ResponseEntity.ok("뉴스 요약이 저장되었습니다.");
    }

    /*
    @GetMapping("/{userId}")
    public ResponseEntity<List<SummaryResponseDTO>> getSummary(@PathVariable Long userId) {
        List<SummaryResponseDTO> summaryResponseDTO = summaryService.getSummary(userId);
        return ResponseEntity.ok(summaryResponseDTO);
    }*/

    //해당하는 페이지의 아티스트 뉴스 가져오기
    @GetMapping("/{userId}")
    public ResponseEntity<Page<SummaryResponseDTO>> getSummary(
            @PathVariable Long userId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "2") int size) {

        Page<SummaryResponseDTO> summaryResponseDTOPage = summaryService.getSummary(userId, page, size);

        return ResponseEntity.ok(summaryResponseDTOPage);
    }

    @DeleteMapping("/{userId}")
    public ResponseEntity<String> deleteSummary(@PathVariable Long userId){
        return ResponseEntity.ok("뉴스 요약 삭제가 완료되었습니다.");
    }



}
