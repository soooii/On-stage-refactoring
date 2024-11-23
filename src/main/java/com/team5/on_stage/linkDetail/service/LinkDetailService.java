package com.team5.on_stage.linkDetail.service;

import com.team5.on_stage.global.constants.ErrorCode;
import com.team5.on_stage.global.exception.GlobalException;
import com.team5.on_stage.link.entity.Link;
import com.team5.on_stage.link.repository.LinkRepository;
import com.team5.on_stage.linkDetail.dto.LinkDetailDTO;
import com.team5.on_stage.linkDetail.entity.LinkDetail;
import com.team5.on_stage.linkDetail.repository.LinkDetailRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class LinkDetailService {
    private final LinkDetailRepository linkDetailRepository;
    private final LinkRepository linkRepository;

    public List<LinkDetailDTO> findByLinkId(Long linkId) {
        return linkDetailRepository.findLinkDetailsByLinkId(linkId);
    }

    @Transactional
    public LinkDetailDTO saveLinkDetail(LinkDetailDTO linkDetailDTO, Long linkId) {
        LinkDetail target = new LinkDetail();
        Link link = linkRepository.findById(linkId)
                .orElseThrow(() -> new GlobalException(ErrorCode.LINK_NOT_FOUND));
        target.setLink(link);
        target.setUrl(linkDetailDTO.getUrl());
        target.setPlatform(linkDetailDTO.getPlatform());
        LinkDetail save = linkDetailRepository.save(target);
        linkDetailDTO.setId(save.getId());
        return linkDetailDTO;
    }

    @Transactional
    public LinkDetailDTO updateLinkDetail(LinkDetailDTO linkDetailDTO, Long id) {
        LinkDetail target = linkDetailRepository.findById(id)
                .orElseThrow(() -> new GlobalException(ErrorCode.LINK_DETAIL_NOT_FOUND));
        target.setUrl(linkDetailDTO.getUrl());
        target.setPlatform(linkDetailDTO.getPlatform());
        LinkDetail save = linkDetailRepository.save(target);
        return linkDetailDTO;
    }
}
