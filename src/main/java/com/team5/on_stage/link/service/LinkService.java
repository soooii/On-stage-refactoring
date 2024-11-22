package com.team5.on_stage.link.service;

import com.team5.on_stage.link.dto.LinkDTO;
import com.team5.on_stage.link.dto.LinkResponseDTO;
import com.team5.on_stage.link.repository.LinkRepository;
import com.team5.on_stage.linkDetail.service.LinkDetailService;
import com.team5.on_stage.socialLink.service.SocialLinkService;
import com.team5.on_stage.theme.service.ThemeService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class LinkService {

    private final LinkRepository linkRepository;
    private final LinkDetailService linkDetailService;
    private final SocialLinkService socialLinkService;
    private final ThemeService themeService;


    public LinkResponseDTO getLinkResponseDTO(Long userId){
        LinkResponseDTO linkResponseDTO = new LinkResponseDTO();
        //LinkDTO linkDTO = linkRepository.findAllByUserId(userId);
        linkResponseDTO.setLink(linkRepository.findAllByUserId(userId));
        linkResponseDTO.setSocialLink(socialLinkService.findByUserId(userId));
        linkResponseDTO.setTheme(themeService.findByUserId(userId));

        return linkResponseDTO;
    }
}
