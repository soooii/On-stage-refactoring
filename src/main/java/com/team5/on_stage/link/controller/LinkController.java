package com.team5.on_stage.link.controller;

import com.team5.on_stage.link.dto.LinkResponseDTO;
import com.team5.on_stage.link.service.LinkService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/link")
public class LinkController {
    private final LinkService linkService;

    @GetMapping
    public ResponseEntity<LinkResponseDTO> getLink(Long userId) {
        return ResponseEntity.status(HttpStatus.OK).body(linkService.getLinkResponseDTO(userId));
    }
}
