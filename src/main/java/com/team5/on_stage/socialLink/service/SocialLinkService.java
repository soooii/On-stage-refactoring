package com.team5.on_stage.socialLink.service;

import com.team5.on_stage.global.constants.ErrorCode;
import com.team5.on_stage.global.exception.GlobalException;
import com.team5.on_stage.socialLink.dto.SocialLinkDTO;
import com.team5.on_stage.socialLink.entity.SocialLink;
import com.team5.on_stage.socialLink.repository.SocialLinkRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class SocialLinkService {
    private final SocialLinkRepository socialLinkRepository;

    // READ
    public SocialLinkDTO getSocial(String username) {
        return socialLinkRepository.getSocial(username)
                .orElseThrow(() -> new GlobalException(ErrorCode.SOCIAL_LINK_NOT_FOUND));
    }

    // CREATE (user 생성 시점에 같이 생성)
    public void createSocial(String username){
        SocialLink socialLink = SocialLink.builder()
                .username(username)
                .build();
        socialLinkRepository.save(socialLink);
    }

    // UPDATE
    @Transactional
    public SocialLinkDTO updateSocial(SocialLinkDTO dto) {
        socialLinkRepository.updateSocial(
                dto.getUsername(),
                dto.getInstagram(),
                dto.getYoutube(),
                dto.getX(),
                dto.getSpotify(),
                dto.getGithub()
        );
        return dto;
    }

}
