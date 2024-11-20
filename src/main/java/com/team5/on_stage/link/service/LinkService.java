package com.team5.on_stage.link.service;

import com.team5.on_stage.link.dto.LinkResponseDTO;
import com.team5.on_stage.link.entity.Link;
import com.team5.on_stage.link.repository.LinkRepository;
import com.team5.on_stage.linkDetail.repository.LinkDetailRepository;
import com.team5.on_stage.linkDetail.service.LinkDetailService;
import com.team5.on_stage.socialLink.repository.SocialLinkRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class LinkService {

    private final LinkRepository linkRepository;
    private final LinkDetailService linkDetailService;
    private final
    private final


    public LinkResponseDTO getLink(Long id){


    }
}
