package com.team5.on_stage.linkDetail.dto;

import com.team5.on_stage.link.constants.Platform;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter
@AllArgsConstructor
@NoArgsConstructor
public class LinkDetailDTO {
    private Long id;

    private Platform platform;

    private String url;

}
