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
    public ResponseEntity<LinkResponseDTO> getLink(@PathVariable Long userId) {
        return ResponseEntity.status(HttpStatus.OK).body(linkService.getLinkResponseDTO(1L)); // 수정예정
    }

    @PostMapping
    public ResponseEntity<LinkDTO> createLink(@RequestBody LinkDTO link) {
        return ResponseEntity.status(HttpStatus.OK).body(linkService.createLink(link));
    }

    @PutMapping
    public ResponseEntity<LinkDTO> updateLink(@RequestBody LinkDTO link) {
        return ResponseEntity.status(HttpStatus.OK).body(linkService.updateLinkDTO(link));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteLink(@PathVariable Long id) {
        linkService.deleteLink(id);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

}
