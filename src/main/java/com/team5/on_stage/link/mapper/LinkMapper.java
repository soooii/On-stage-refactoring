package com.team5.on_stage.link.mapper;

import com.team5.on_stage.global.constants.ErrorCode;
import com.team5.on_stage.global.exception.GlobalException;
import com.team5.on_stage.link.dto.LinkDTO;
import com.team5.on_stage.link.dto.LinkResponseDTO;
import com.team5.on_stage.link.entity.Link;
import com.team5.on_stage.link.repository.LinkRepository;
import com.team5.on_stage.linkDetail.service.LinkDetailService;
import com.team5.on_stage.socialLink.service.SocialLinkService;
import com.team5.on_stage.theme.service.ThemeService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class LinkMapper {
    private final LinkRepository linkRepository;
    private final LinkDetailService linkDetailService;
    private final SocialLinkService socialLinkService;
    private final ThemeService themeService;



    public LinkDTO toDTO(Link link) {
        return LinkDTO.builder()
                .id(link.getId())
                .username(link.getUsername())
                .title(link.getTitle())
                .prevLinkId(link.getPrevLinkId())
                .blockType(link.getBlockType())
                .padding(link.getPadding())
                .active(link.isActive())
                .details(linkDetailService.getDetail(link.getId()))
                .build();
    }

    public List<LinkDTO> toDTOList(List<Link> links) {
        return links.stream().map(this::toDTO).collect(Collectors.toList());
    }

    public Link toEntity(LinkDTO dto) {
        return Link.builder()
                .username(dto.getUsername())
                .title(dto.getTitle())
                .prevLinkId(dto.getPrevLinkId())
                .build();
    }

    public LinkResponseDTO toResponseDTO(String username) {
        List<Link> links = linkRepository.findByUsername(username)
                .orElseThrow(() -> new GlobalException(ErrorCode.LINK_NOT_FOUND));
        return LinkResponseDTO.builder()
                .link(toDTOList(links))
                .socialLink(socialLinkService.getSocial(username))
                .theme(themeService.getTheme(username))
                .build();
    }
}
