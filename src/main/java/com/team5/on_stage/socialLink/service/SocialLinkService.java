package com.team5.on_stage.socialLink.service;

import com.team5.on_stage.global.constants.ErrorCode;
import com.team5.on_stage.global.exception.GlobalException;
import com.team5.on_stage.socialLink.dto.SocialLinkDTO;
import com.team5.on_stage.socialLink.entity.SocialLink;
import com.team5.on_stage.socialLink.repository.SocialLinkRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class SocialLinkService {
    private final SocialLinkRepository socialLinkRepository;

    public SocialLinkDTO findByUserId(Long userId) {
        return socialLinkRepository.findDTOByUserId(userId);
    }

    @Transactional
    public SocialLinkDTO updateSocialLink(SocialLinkDTO socialLinkDTO) {
        SocialLink target = socialLinkRepository.findByUserId(socialLinkDTO.getUserId())
                .orElseThrow(() -> new GlobalException(ErrorCode.SOCIAL_LINK_NOT_FOUND));
        target.setInstagram(socialLinkDTO.getInstagram());
        target.setYoutube(socialLinkDTO.getYoutube());
        target.setX(socialLinkDTO.getX());
        target.setSpotify(socialLinkDTO.getSpotify());
        target.setGithub(socialLinkDTO.getGithub());
        socialLinkRepository.save(target);
        return socialLinkDTO;
    }

}
