package com.team5.on_stage.linkDetail.service;

import com.team5.on_stage.linkDetail.dto.LinkDetailDTO;
import com.team5.on_stage.linkDetail.repository.LinkDetailRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class LinkDetailService {
    private final LinkDetailRepository linkDetailRepository;

    public List<LinkDetailDTO> getLinkDetailsByLinkId(Long linkId) {
        return linkDetailRepository.findLinkDetailsByLinkId(linkId);
    }
}
