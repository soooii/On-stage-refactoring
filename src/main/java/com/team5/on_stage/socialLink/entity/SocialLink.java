package com.team5.on_stage.socialLink.entity;

import jakarta.persistence.*;

@Entity
@Table (name = "social-link")
public class SocialLink {
    @Id
    @GeneratedValue (strategy = GenerationType.IDENTITY)
    private Long id;

    private String instagram;

    private String youtube;

    private String x;

    private String spotify;

    // 추후 연관관계 매핑
    private Long userId;

}
