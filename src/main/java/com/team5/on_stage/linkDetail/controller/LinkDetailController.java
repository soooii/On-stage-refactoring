package com.team5.on_stage.linkDetail.controller;

import com.team5.on_stage.linkDetail.dto.LinkDetailDTO;
import com.team5.on_stage.linkDetail.service.LinkDetailService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/link-detail")
@RequiredArgsConstructor
@Tag(name = "Link Detail Controller", description = "링크 디테일 컨트롤러")
public class LinkDetailController {
    private final LinkDetailService linkDetailService;

    @PostMapping("/{linkId}")
    @Operation(summary = "세부 링크 생성", description = "지정한 블록 컨테이너 내부에 세부 링크 생성")
    public ResponseEntity<LinkDetailDTO> createDetail(@RequestBody LinkDetailDTO linkDetail, @PathVariable Long linkId) {
        return ResponseEntity.status(HttpStatus.CREATED).body(linkDetailService.createDetail(linkDetail,linkId));
    }

    @PutMapping
    @Operation(summary = "세부 링크 수정", description = "세부링크를 수정")
    public ResponseEntity<LinkDetailDTO> updateDetail(@RequestBody LinkDetailDTO linkDetail) {
        return ResponseEntity.status(HttpStatus.OK).body(linkDetailService.updateDetail(linkDetail));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "세부 링크 삭제", description = "세부링크를 삭제 (논리삭제)")
    public ResponseEntity<Void> deleteDetail(@PathVariable Long id) {
        linkDetailService.deleteDetail(id);
        return ResponseEntity.status(HttpStatus.OK).build();
    }
}
