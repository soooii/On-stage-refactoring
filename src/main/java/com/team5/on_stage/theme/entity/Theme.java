package com.team5.on_stage.theme.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "theme")
public class Theme {
    @Id
    @GeneratedValue (strategy = GenerationType.IDENTITY)
    private Long id;

    private String backgroundImage;

    private String preferColor;

    private String fontColor;

    private int borderRadius;

    private Long userId;

}
