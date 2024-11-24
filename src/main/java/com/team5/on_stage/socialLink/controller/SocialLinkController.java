package com.team5.on_stage.socialLink.controller;

import com.team5.on_stage.socialLink.service.SocialLinkService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/social-link")
@RequiredArgsConstructor
public class SocialLinkController {
    private final SocialLinkService socialLinkService;
}
