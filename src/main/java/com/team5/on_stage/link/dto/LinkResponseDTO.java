package com.team5.on_stage.link.dto;

import com.team5.on_stage.socialLink.dto.SocialLinkDTO;
import com.team5.on_stage.theme.dto.ThemeDTO;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter @Setter
public class LinkResponseDTO {

    private List<LinkDTO> link;

    private SocialLinkDTO socialLink;

    private ThemeDTO theme;
}
