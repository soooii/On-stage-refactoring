package com.team5.on_stage.link.dto;

import com.team5.on_stage.link.constants.Layout;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
public class LinkDTO {
    private Long id;

    private String thumbnail;

    private String title;

    private String description;

    private int priority;

    private Layout layout;

    private boolean active;

}
