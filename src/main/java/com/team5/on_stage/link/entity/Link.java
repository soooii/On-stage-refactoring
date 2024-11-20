package com.team5.on_stage.link.entity;

import com.team5.on_stage.link.constants.Layout;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter @Setter
@Table(name = "link")
public class Link {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String image;

    private String title;

    private String description;

    private int priority;

    @Enumerated(EnumType.STRING)
    private Layout layout;

    private boolean active;

    // 추후 매핑
    private Long userId;
}
