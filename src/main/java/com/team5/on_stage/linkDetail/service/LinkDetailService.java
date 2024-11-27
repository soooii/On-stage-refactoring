package com.team5.on_stage.linkDetail.service;

import com.team5.on_stage.global.constants.ErrorCode;
import com.team5.on_stage.global.exception.GlobalException;
import com.team5.on_stage.link.entity.Link;
import com.team5.on_stage.link.repository.LinkRepository;
import com.team5.on_stage.linkDetail.dto.LinkDetailDTO;
import com.team5.on_stage.linkDetail.entity.LinkDetail;
import com.team5.on_stage.linkDetail.repository.LinkDetailRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class LinkDetailService {
    private final LinkDetailRepository linkDetailRepository;
    private final LinkRepository linkRepository;

    // READ
    @Transactional(readOnly = true)
    public List<LinkDetailDTO> getLinkDetail(Long linkId) {
        return linkDetailRepository.findByLinkId(linkId);
    }

    // CREATE
    public LinkDetailDTO createLinkDetail(LinkDetailDTO dto, Long linkId) {
        LinkDetail target = LinkDetail.builder()
                .link(findLink(linkId))
                .url(dto.getUrl())
                .platform(dto.getPlatform())
                .build();
        LinkDetail saved = linkDetailRepository.save(target);
        dto.setId(saved.getId());
        return dto;
    }

    // UPDATE
    public LinkDetailDTO updateLinkDetail(LinkDetailDTO dto) {
        linkDetailRepository.updateLinkDetail(
                dto.getPlatform(),
                dto.getUrl(),
                dto.getId()
        );
        return dto;
    }

    // DELETE
    public void deleteLinkDetail(Long id) {
        linkDetailRepository.softDeleteById(id);
    }

    private Link findLink(Long id) {
        return linkRepository.findById(id)
                .orElseThrow(() -> new GlobalException(ErrorCode.LINK_NOT_FOUND));
    }
}
