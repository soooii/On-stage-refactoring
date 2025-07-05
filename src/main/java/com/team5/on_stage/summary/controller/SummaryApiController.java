package com.team5.on_stage.summary.controller;

import java.util.Optional;

import com.team5.on_stage.global.config.redis.RedisService;
import com.team5.on_stage.summary.dto.SummaryRequestDTO;
import com.team5.on_stage.summary.dto.SummaryResponseDTO;
import com.team5.on_stage.summary.entity.Summary;
import com.team5.on_stage.summary.entity.SummaryStatus;
import com.team5.on_stage.summary.repository.SummaryRespository;
import com.team5.on_stage.summary.service.SummaryService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Tag(name = "Summary API", description = "사용자의 아티스트 뉴스 요약을 저장, 조회, 삭제하는 API")
@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/summary/")
public class SummaryApiController {
    private final SummaryService summaryService;
    private final SummaryRespository summaryRepository;
    private final RedisService redisService;

    @Operation(summary = "아티스트 뉴스 요약 조회", description = "특정 사용자의 아티스트 뉴스 요약 데이터를 페이징 형식으로 조회합니다.")
    @Parameter(name = "username", description = "조회할 요약 데이터 사용자의 username")
    @GetMapping("/{username}")
    public ResponseEntity<Page<SummaryResponseDTO>> getRecentSummary(
            @PathVariable String username,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "2") int size) {

        long start = System.currentTimeMillis();

        SummaryRequestDTO request = new SummaryRequestDTO(username, page, size);
        Page<SummaryResponseDTO> response = summaryService.getRecentSummary(request);
        //Page<SummaryResponseDTO> response = summaryService.getRecentSummaryWithoutCache(request);
        long end = System.currentTimeMillis();
        log.info("총 처리 시간 (ms): {}", end - start);

        return ResponseEntity.ok(response);
    }

    @GetMapping("/admin/{username}")
    public ResponseEntity<Page<SummaryResponseDTO>> getPendingSummary(
        @PathVariable String username,
        @RequestParam(defaultValue = "0") int page,
        @RequestParam(defaultValue = "2") int size) {

        SummaryRequestDTO request = new SummaryRequestDTO(username, page, size);
        Page<SummaryResponseDTO> response = summaryService.getPendingSummary(request);

        return ResponseEntity.ok(response);
    }

    // 승인
    @PatchMapping("/approve/{summaryId}")
    public ResponseEntity<Void> approveSummary(@PathVariable Long summaryId) {
        Optional<Summary> summaryOpt = summaryRepository.findById(summaryId);
        if (summaryOpt.isPresent()) {
            Summary summary = summaryOpt.get();
            summaryRepository.save(summary.updateStatus(SummaryStatus.APPROVED));

            // 캐시 무효화
            redisService.deleteSummaryCache(summary.getUser().getUsername());
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.notFound().build();
    }

    // 거절
    @PatchMapping("/reject/{summaryId}")
    public ResponseEntity<Void> rejectSummary(@PathVariable Long summaryId) {
        Optional<Summary> summaryOpt = summaryRepository.findById(summaryId);
        if (summaryOpt.isPresent()) {
            Summary summary = summaryOpt.get();
            summaryRepository.save(summary.updateStatus(SummaryStatus.REJECTED));

            // 캐시 무효화
            redisService.deleteSummaryCache(summary.getUser().getUsername());
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.notFound().build();
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
