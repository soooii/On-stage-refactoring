package com.team5.on_stage.socialLink.controller;

import com.team5.on_stage.socialLink.dto.SocialLinkDTO;
import com.team5.on_stage.socialLink.service.SocialLinkService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/social-link")
@RequiredArgsConstructor
public class SocialLinkController {
    private final SocialLinkService socialLinkService;

    @PutMapping
    public ResponseEntity<SocialLinkDTO> updateSocialLink(@RequestBody SocialLinkDTO socialLinkDTO) {
        return ResponseEntity.status(HttpStatus.OK).body(socialLinkService.updateSocial(socialLinkDTO));
    }
}