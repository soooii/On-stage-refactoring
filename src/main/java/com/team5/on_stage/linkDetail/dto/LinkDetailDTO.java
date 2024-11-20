package com.team5.on_stage.linkDetail.dto;

import com.team5.on_stage.link.constants.Platform;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class LinkDetailDTO {

    private Platform platform;

    private String url;


    public LinkDetailDTO(Platform platform, String url) {
        this.platform = platform;
        this.url = url;
    }
}
