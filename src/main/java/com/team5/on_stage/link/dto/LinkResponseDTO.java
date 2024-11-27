package com.team5.on_stage.link.dto;

import com.team5.on_stage.socialLink.dto.SocialLinkDTO;
import com.team5.on_stage.theme.dto.ThemeDTO;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class LinkResponseDTO {

    private List<LinkDTO> link;

    private SocialLinkDTO socialLink;

    private ThemeDTO theme;

    @Builder
    public LinkResponseDTO(List<LinkDTO> link, SocialLinkDTO socialLink, ThemeDTO theme) {
        this.link = link;
        this.socialLink = socialLink;
        this.theme = theme;
    }
}
