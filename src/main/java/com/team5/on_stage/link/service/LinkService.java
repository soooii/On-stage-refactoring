package com.team5.on_stage.link.service;

import com.team5.on_stage.link.entity.Link;
import com.team5.on_stage.link.repository.LinkRepository;
import com.team5.on_stage.linkDetail.repository.LinkDetailRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class LinkService {

    private final LinkRepository linkRepository;
    private final LinkDetailRepository linkDetailRepository;


    public Link getLink(Long id){
        return linkRepository.findById(id).orElse(null);
    }
}
