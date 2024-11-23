package com.team5.on_stage.linkDetail.controller;

import com.team5.on_stage.linkDetail.dto.LinkDetailDTO;
import com.team5.on_stage.linkDetail.entity.LinkDetail;
import com.team5.on_stage.linkDetail.service.LinkDetailService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/link-detail")
@RequiredArgsConstructor
public class LinkDetailController {
    private final LinkDetailService linkDetailService;

    @PostMapping("/{linkId}")
    public ResponseEntity<LinkDetailDTO> addLinkDetail(@RequestBody LinkDetailDTO linkDetail, @PathVariable Long linkId) {

        return ResponseEntity.status(HttpStatus.CREATED).body(linkDetailService.saveLinkDetail(linkDetail,linkId));
    }
}
