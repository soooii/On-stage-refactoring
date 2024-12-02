package com.team5.on_stage.link.controller;

import com.team5.on_stage.global.config.jwt.JwtUtil;
import com.team5.on_stage.link.dto.LinkDTO;
import com.team5.on_stage.link.dto.LinkResponseDTO;
import com.team5.on_stage.link.service.LinkService;
import com.team5.on_stage.user.service.UserService;
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
    private final UserService userService;

    @GetMapping
    public ResponseEntity<LinkResponseDTO> getLink() {
        return ResponseEntity.status(HttpStatus.OK).body(linkService.getLink(userService.getUsername())); // 수정예정
    }

    @GetMapping("/{username}")
    public ResponseEntity<LinkResponseDTO> getLink(@PathVariable String username) {
        return ResponseEntity.status(HttpStatus.OK).body(linkService.getLink(username));
    }

    @PostMapping
    public ResponseEntity<LinkDTO> createLink(@RequestBody LinkDTO link) {
        return ResponseEntity.status(HttpStatus.CREATED).body(linkService.createLink(link));
    }

    @PutMapping
    public ResponseEntity<LinkDTO> updateLink(@RequestBody LinkDTO link) {
        return ResponseEntity.status(HttpStatus.OK).body(linkService.updateLink(link));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteLink(@PathVariable Long id) {
        linkService.deleteLink(id);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

}
