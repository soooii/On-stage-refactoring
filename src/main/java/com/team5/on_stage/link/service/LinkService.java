package com.team5.on_stage.link.service;

import com.team5.on_stage.global.constants.ErrorCode;
import com.team5.on_stage.global.exception.GlobalException;
import com.team5.on_stage.link.dto.LinkDTO;
import com.team5.on_stage.link.dto.LinkResponseDTO;
import com.team5.on_stage.link.entity.Link;
import com.team5.on_stage.link.mapper.LinkMapper;
import com.team5.on_stage.link.repository.LinkRepository;
import com.team5.on_stage.linkDetail.repository.LinkDetailRepository;
import com.team5.on_stage.linkDetail.service.LinkDetailService;
import com.team5.on_stage.socialLink.service.SocialLinkService;
import com.team5.on_stage.theme.service.ThemeService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class LinkService {
    private final LinkMapper linkMapper;
    private final LinkRepository linkRepository;
    private final LinkDetailService linkDetailService;
    private final LinkDetailRepository linkDetailRepository;
    private final SocialLinkService socialLinkService;
    private final ThemeService themeService;

    // READ
    public LinkResponseDTO getLink(Long userId) {
        LinkResponseDTO linkResponseDTO = new LinkResponseDTO();
        List<Link> link = linkRepository.findByUserId(userId)
                .orElseThrow(() -> new GlobalException(ErrorCode.LINK_NOT_FOUND));
        linkResponseDTO.setLink(toDTOList(link));
        linkResponseDTO.setSocialLink(socialLinkService.findByUserId(userId));
        linkResponseDTO.setTheme(themeService.findByUserId(userId));
        return linkResponseDTO;
    }

    // CREATE
    @Transactional
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
    @Transactional
    public LinkDTO updateLink(LinkDTO linkDTO) {
        // 쿼리 메서드를 사용 -> DB 접근 최소화
        linkRepository.updateLink(
                linkDTO.getTitle(),
                linkDTO.getPrevLinkId(),
                linkDTO.isActive(),
                linkDTO.getId()
        );
        return linkDTO;
    }

    // DELETE
    @Transactional
    public void deleteLink(Long linkId) {
        linkDetailRepository.softDeleteByLinkId(linkId);
        linkRepository.softDeleteById(linkId);
    }

    public List<LinkDTO> toDTOList(List<Link> links) {
        return linkMapper.toDTOList(links);
    }
}
