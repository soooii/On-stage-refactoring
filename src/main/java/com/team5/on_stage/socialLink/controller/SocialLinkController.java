package com.team5.on_stage.socialLink.controller;

import com.team5.on_stage.socialLink.dto.SocialLinkDTO;
import com.team5.on_stage.socialLink.service.SocialLinkService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/social-link")
@RequiredArgsConstructor
@Tag(name = "Social Link Controller", description = "소셜 링크 컨트롤러")
public class SocialLinkController {
    private final SocialLinkService socialLinkService;

    @PutMapping
    @Operation(summary = "소셜 링크 수정", description = "소셜 링크를 수정")
    public ResponseEntity<SocialLinkDTO> updateSocialLink(@RequestBody SocialLinkDTO socialLinkDTO) {
        return ResponseEntity.status(HttpStatus.OK).body(socialLinkService.updateSocial(socialLinkDTO));
    }
}