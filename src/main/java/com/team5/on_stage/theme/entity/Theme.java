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

    private String preferColor;

    private String fontColor;

    private int borderRadius;

}
