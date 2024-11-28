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

    // READ
    public List<LinkDetailDTO> getDetail(Long linkId) {
        return linkDetailRepository.findByLinkId(linkId);
    }

    // CREATE
    @Transactional
    public LinkDetailDTO createDetail(LinkDetailDTO dto, Long linkId) {
        LinkDetail target = LinkDetail.builder()
                .link(linkRepository.findById(linkId)
                        .orElseThrow(() -> new GlobalException(ErrorCode.LINK_NOT_FOUND)))
                .url(dto.getUrl())
                .platform(dto.getPlatform())
                .build();
        LinkDetail saved = linkDetailRepository.save(target);
        dto.setId(saved.getId());
        return dto;
    }

    // UPDATE
    public LinkDetailDTO updateDetail(LinkDetailDTO dto) {
        linkDetailRepository.updateLinkDetail(
                dto.getPlatform(),
                dto.getUrl(),
                dto.getId()
        );
        return dto;
    }

    // DELETE
    public void deleteDetail(Long id) {
        linkDetailRepository.softDeleteById(id);
    }
}
