package com.team5.on_stage.linkDetail.controller;

import com.team5.on_stage.linkDetail.dto.LinkDetailDTO;
import com.team5.on_stage.linkDetail.service.LinkDetailService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/link-detail")
@RequiredArgsConstructor
public class LinkDetailController {
    private final LinkDetailService linkDetailService;

    @PostMapping("/{linkId}")
    public ResponseEntity<LinkDetailDTO> createDetail(@RequestBody LinkDetailDTO linkDetail, @PathVariable Long linkId) {
        return ResponseEntity.status(HttpStatus.CREATED).body(linkDetailService.createDetail(linkDetail,linkId));
    }

    @PutMapping
    public ResponseEntity<LinkDetailDTO> updateDetail(@RequestBody LinkDetailDTO linkDetail) {
        return ResponseEntity.status(HttpStatus.OK).body(linkDetailService.updateDetail(linkDetail));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteDetail(@PathVariable Long id) {
        linkDetailService.deleteDetail(id);
        return ResponseEntity.status(HttpStatus.OK).build();
    }
}
