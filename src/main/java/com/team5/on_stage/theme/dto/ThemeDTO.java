package com.team5.on_stage.theme.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
public class ThemeDTO {
    private String backgroundImage;

    private String preferColor;

    private String fontColor;

    private int borderRadius;

    private Long linkId;
}
