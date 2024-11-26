package com.team5.on_stage.link.service;

import com.team5.on_stage.global.constants.ErrorCode;
import com.team5.on_stage.global.exception.GlobalException;
import com.team5.on_stage.link.dto.LinkDTO;
import com.team5.on_stage.link.dto.LinkResponseDTO;
import com.team5.on_stage.link.entity.Link;
import com.team5.on_stage.link.repository.LinkRepository;
import com.team5.on_stage.linkDetail.dto.LinkDetailDTO;
import com.team5.on_stage.linkDetail.entity.LinkDetail;
import com.team5.on_stage.linkDetail.repository.LinkDetailRepository;
import com.team5.on_stage.linkDetail.service.LinkDetailService;
import com.team5.on_stage.socialLink.service.SocialLinkService;
import com.team5.on_stage.theme.service.ThemeService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class LinkService {

    private final LinkRepository linkRepository;
    private final LinkDetailService linkDetailService;
    private final LinkDetailRepository linkDetailRepository;
    private final SocialLinkService socialLinkService;
    private final ThemeService themeService;

    // ResponseDTO 조회
    public LinkResponseDTO getLink(Long userId) {
        LinkResponseDTO linkResponseDTO = new LinkResponseDTO();
        List<Link> link = linkRepository.findByUserId(userId)
                .orElseThrow(() -> new GlobalException(ErrorCode.LINK_NOT_FOUND));
        linkResponseDTO.setLink(convertLinksToLinkDTOList(link));
        linkResponseDTO.setSocialLink(socialLinkService.findByUserId(userId));
        linkResponseDTO.setTheme(themeService.findByUserId(userId));
        return linkResponseDTO;
    }

    // create link
    @Transactional
    public LinkDTO createLink(LinkDTO linkDTO) {
        Link link = new Link();
        link.setTitle(linkDTO.getTitle());
        link.setPrevLinkId(linkDTO.getPrevLinkId());
        link.setUserId(linkDTO.getUserId());
        Link savedLink = linkRepository.save(link);
        linkDTO.setId(savedLink.getId());

        List<LinkDetail> linkDetails = linkDTO.getDetails().stream()
                .map(detail -> {
                    LinkDetail linkDetail = new LinkDetail();
                    linkDetail.setLink(savedLink);
                    linkDetail.setPlatform(detail.getPlatform());
                    linkDetail.setUrl(detail.getUrl());
                    return linkDetail;
                })
                .collect(Collectors.toList());

        // 벌크 인서트 수행
        linkDetailRepository.saveAll(linkDetails);
        return linkDTO;
    }

    @Transactional
    public LinkDTO updateLinkDTO(LinkDTO linkDTO) {
        // 쿼리 메서드를 사용 -> DB 접근 최소화
        linkRepository.updateLink(
                linkDTO.getTitle(),
                linkDTO.getPrevLinkId(),
                linkDTO.isActive(),
                linkDTO.getId()
        );
        return linkDTO;
    }

    // delete Link
    @Transactional
    public void deleteLink(Long linkId) {
        linkDetailRepository.softDeleteByLinkId(linkId);
        linkRepository.softDeleteById(linkId);
    }

    // TODO 프로젝션 사용
    public List<LinkDTO> convertLinksToLinkDTOList(List<Link> links) {
        return links.stream()
                .map(link -> {
                    LinkDTO linkDTO = new LinkDTO();
                    linkDTO.setId(link.getId());
                    linkDTO.setUserId(link.getUserId());
                    linkDTO.setTitle(link.getTitle());
                    linkDTO.setPrevLinkId(link.getPrevLinkId());
                    linkDTO.setActive(link.isActive());
                    linkDTO.setDetails(linkDetailService.findByLinkId(link.getId()));
                    return linkDTO;
                })
                .collect(Collectors.toList());
    }
}
