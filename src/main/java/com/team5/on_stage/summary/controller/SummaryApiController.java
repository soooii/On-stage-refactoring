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

    @Operation(summary = "아티스트 뉴스 요약 저장", description = "특정 사용자의 아티스트 뉴스 요약 데이터를 저장합니다.")
    @Parameter(name = "username", description = "요약 데이터를 저장할 사용자의 username")
    @PostMapping("/{username}")
    public ResponseEntity<String> saveSummary(@PathVariable String username) {
        summaryService.saveSummary(username);
        return ResponseEntity.ok("뉴스 요약이 저장되었습니다.");
    }

    @Operation(summary = "아티스트 뉴스 요약 조회", description = "특정 사용자의 아티스트 뉴스 요약 데이터를 페이징 형식으로 조회합니다.")
    @Parameter(name = "username", description = "조회할 요약 데이터 사용자의 username")
    @GetMapping("/{username}")
    public ResponseEntity<Page<SummaryResponseDTO>> getSummary(
            @PathVariable String username,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "2") int size) {

        SummaryRequestDTO request = new SummaryRequestDTO(username, page, size);
        Page<SummaryResponseDTO> response = summaryService.getSummary(request);
        return ResponseEntity.ok(response);
    }

    @Operation(summary = "아티스트 뉴스 요약 삭제", description = "특정 사용자의 아티스트 뉴스 요약 데이터를 삭제합니다.")
    @Parameter(name = "username", description = "삭제할 요약 데이터 사용자의 username")
    @DeleteMapping("/{username}")
    public ResponseEntity<String> deleteSummary(@PathVariable String username){
        summaryService.deleteSummary(username);
        return ResponseEntity.ok("뉴스 요약 삭제가 완료되었습니다.");
    }



}
