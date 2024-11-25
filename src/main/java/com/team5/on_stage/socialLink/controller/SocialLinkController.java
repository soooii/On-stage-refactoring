package com.team5.on_stage.socialLink.controller;

import com.team5.on_stage.socialLink.dto.SocialLinkDTO;
import com.team5.on_stage.socialLink.service.SocialLinkService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/social-link")
@RequiredArgsConstructor
public class SocialLinkController {
    private final SocialLinkService socialLinkService;

    @PostMapping
    public ResponseEntity<SocialLinkDTO> createSocialLink(@RequestBody SocialLinkDTO socialLinkDTO) {
        return ResponseEntity.status(HttpStatus.OK).body(socialLinkService.createSocialLink(socialLinkDTO));
    }
}