package com.team5.on_stage.link.dto;

import com.team5.on_stage.linkDetail.dto.LinkDetailDTO;
import lombok.*;
import java.util.List;

@NoArgsConstructor
@Getter
@Setter
public class LinkDTO {
    private Long id;

    private String username;

    private String title;

    private Long prevLinkId;

    private boolean active;

    private List<LinkDetailDTO> details;


    @Builder
    public LinkDTO(boolean active, Long id, String username, String title, Long prevLinkId, List<LinkDetailDTO> details) {
        this.active = active;
        this.id = id;
        this.username = username;
        this.title = title;
        this.prevLinkId = prevLinkId;
        this.details = details;
    }
}

