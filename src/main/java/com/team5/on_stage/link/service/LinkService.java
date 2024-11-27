package com.team5.on_stage.link.service;

import com.team5.on_stage.link.dto.LinkDTO;
import com.team5.on_stage.link.dto.LinkResponseDTO;
import com.team5.on_stage.link.entity.Link;
import com.team5.on_stage.link.mapper.LinkMapper;
import com.team5.on_stage.link.repository.LinkRepository;
import com.team5.on_stage.linkDetail.repository.LinkDetailRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class LinkService {
    private final LinkMapper linkMapper;
    private final LinkRepository linkRepository;
    private final LinkDetailRepository linkDetailRepository;

    // READ
    @Transactional(readOnly = true)
    public LinkResponseDTO getLink(Long userId) {
        return linkMapper.toResponseDTO(userId);
    }

    // CREATE
    public LinkDTO createLink(LinkDTO dto) {
        Link link = Link.builder()
                .userId(dto.getUserId())
                .title(dto.getTitle())
                .prevLinkId(dto.getPrevLinkId())
                .build();
        dto.setId(linkRepository.save(link).getId());
        return dto;
    }

    // UPDATE
    public LinkDTO updateLink(LinkDTO dto) {
        // 쿼리 메서드를 사용 -> DB 접근 최소화
        linkRepository.updateLink(
                dto.getTitle(),
                dto.getPrevLinkId(),
                dto.isActive(),
                dto.getId()
        );
        return dto;
    }

    // DELETE
    public void deleteLink(Long linkId) {
        linkDetailRepository.softDeleteByLinkId(linkId);
        linkRepository.softDeleteById(linkId);
    }
}
