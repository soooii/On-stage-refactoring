package com.team5.on_stage.socialLink.service;

import com.team5.on_stage.socialLink.dto.SocialLinkDTO;
import com.team5.on_stage.socialLink.repository.SocialLinkRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class SocialLinkService {
    private final SocialLinkRepository socialLinkRepository;

    public SocialLinkDTO findByLinkId(Long linkId) {
        return socialLinkRepository.findByLinkId(linkId);
    }

}
