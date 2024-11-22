package com.team5.on_stage.link.dto;

import com.team5.on_stage.link.constants.Layout;
import com.team5.on_stage.linkDetail.dto.LinkDetailDTO;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
public class LinkDTO {
    private Long id;

    private String thumbnail;

    private String title;

    private int priority;

    private Layout layout;

    private boolean active;

    private List<LinkDetailDTO> details;

}
