package com.team5.on_stage.link.service;

import com.team5.on_stage.link.dto.LinkDTO;
import com.team5.on_stage.link.dto.LinkResponseDTO;
import com.team5.on_stage.link.entity.Link;
import com.team5.on_stage.link.mapper.LinkMapper;
import com.team5.on_stage.link.repository.LinkRepository;
import com.team5.on_stage.linkDetail.repository.LinkDetailRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class LinkService {
    private final LinkMapper linkMapper;
    private final LinkRepository linkRepository;
    private final LinkDetailRepository linkDetailRepository;

    // READ
    public LinkResponseDTO getLink(String username) {
        return linkMapper.toResponseDTO(username);
    }

    // CREATE
    public LinkDTO createLink(LinkDTO dto) {
        Link link = Link.builder()
                .username(dto.getUsername())
                .title(dto.getTitle())
                .prevLinkId(dto.getPrevLinkId())
                .blockType(dto.getBlockType())
                .padding(dto.getPadding())
                .url(dto.getUrl())
                .build();
        dto.setId(linkRepository.save(link).getId());
        return dto;
    }

    // UPDATE
    @Transactional
    public LinkDTO updateLink(LinkDTO dto) {
        // 쿼리 메서드를 사용 -> DB 접근 최소화
        linkRepository.updateLink(
                dto.getTitle(),
                dto.getPrevLinkId(),
                dto.isActive(),
                dto.getPadding(),
                dto.getUrl(),
                dto.getId()
        );
        return dto;
    }

    // DELETE
    @Transactional
    public void deleteLink(Long linkId) {
        linkDetailRepository.softDeleteByLinkId(linkId);
        linkRepository.softDeleteById(linkId);
    }
}
