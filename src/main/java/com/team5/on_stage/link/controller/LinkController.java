package com.team5.on_stage.link.controller;

import com.team5.on_stage.global.config.jwt.TokenUsername;
import com.team5.on_stage.link.dto.LinkDTO;
import com.team5.on_stage.link.dto.LinkResponseDTO;
import com.team5.on_stage.link.service.LinkService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/api/link")
@Tag(name = "Link Controller", description = "링크(블록) 컨트롤러")
public class LinkController {
    private final LinkService linkService;

    @Operation(summary = "블록 조회 (호스트)", description = "생성한 블록 컨테이너의 정보 조회")
    @GetMapping
    public ResponseEntity<LinkResponseDTO> getLink(@TokenUsername String username) {
        return ResponseEntity.status(HttpStatus.OK).body(linkService.getLink(username)); // 수정예정
    }

    @Operation(summary = "블록 조회 (방문자)", description = "호스트가 생성한 블록의 정보 조회")
    @GetMapping("/{username}")
    public ResponseEntity<LinkResponseDTO> getLinkForVisitors(@PathVariable String username) {
        return ResponseEntity.status(HttpStatus.OK).body(linkService.getLink(username));
    }

    @Operation(summary = "블록 생성", description = "블록을 생성")
    @PostMapping
    public ResponseEntity<LinkDTO> createLink(@RequestBody LinkDTO link) {
        return ResponseEntity.status(HttpStatus.CREATED).body(linkService.createLink(link));
    }

    @Operation(summary = "블록 수정", description = "블록을 수정")
    @PutMapping
    public ResponseEntity<LinkDTO> updateLink(@RequestBody LinkDTO link) {
        return ResponseEntity.status(HttpStatus.OK).body(linkService.updateLink(link));
    }

    @Operation(summary = "블록 삭제", description = "블록을 삭제 (논리삭제)")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteLink(@PathVariable Long id) {
        linkService.deleteLink(id);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

}
