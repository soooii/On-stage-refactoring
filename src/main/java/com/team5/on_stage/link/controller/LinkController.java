package com.team5.on_stage.link.controller;

import com.team5.on_stage.link.dto.LinkDTO;
import com.team5.on_stage.link.dto.LinkResponseDTO;
import com.team5.on_stage.link.service.LinkService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/api/link")
public class LinkController {
    private final LinkService linkService;

    @GetMapping("/{userId}")
    public ResponseEntity<LinkResponseDTO> getLink() {
        return ResponseEntity.status(HttpStatus.OK).body(linkService.getLinkResponseDTO(1L)); // 수정예정
    }

    @PutMapping
    public ResponseEntity<LinkDTO> updateLink(@RequestBody LinkDTO link) {
        log.info("Updating link: {}", link);
        return ResponseEntity.status(HttpStatus.OK).body(linkService.updateLinkDTO(link));
    }
}
