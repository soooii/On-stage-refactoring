package com.team5.on_stage.theme.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name = "theme")
public class Theme {
    @Id
    @GeneratedValue (strategy = GenerationType.IDENTITY)
    private Long id;

    private Long userId;

    private String backgroundImage;

    private String buttonColor = "#ffffff";

    private String profileColor = "#ffffff";

    private String fontColor = "#000000";

    private String iconColor = "#ffffff";

    private int borderRadius;

}
