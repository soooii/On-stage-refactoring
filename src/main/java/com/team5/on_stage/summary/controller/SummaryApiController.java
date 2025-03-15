package com.team5.on_stage.summary.controller;

import com.team5.on_stage.summary.dto.SummaryRequestDTO;
import com.team5.on_stage.summary.dto.SummaryResponseDTO;
import com.team5.on_stage.summary.service.SummaryService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Tag(name = "Summary API", description = "사용자의 아티스트 뉴스 요약을 저장, 조회, 삭제하는 API")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/summary/")
public class SummaryApiController {
    private final SummaryService summaryService;

    @Operation(summary = "아티스트 뉴스 요약 조회", description = "특정 사용자의 아티스트 뉴스 요약 데이터를 페이징 형식으로 조회합니다.")
    @Parameter(name = "username", description = "조회할 요약 데이터 사용자의 username")
    @GetMapping("/{username}")
    public ResponseEntity<Page<SummaryResponseDTO>> getRecentSummary(
            @PathVariable String username,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "2") int size) {

        SummaryRequestDTO request = new SummaryRequestDTO(username, page, size);
        Page<SummaryResponseDTO> response = summaryService.getRecentSummary(request);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/old/{username}")
    public ResponseEntity<Page<SummaryResponseDTO>> getOldSummary(
           @PathVariable String username,
           @RequestParam(defaultValue = "0") int page,
           @RequestParam(defaultValue = "2") int size){

        SummaryRequestDTO request = new SummaryRequestDTO(username, page, size);
        Page<SummaryResponseDTO> response = summaryService.getOldSummary(request);
        return ResponseEntity.ok(response);
    }

}
