package com.team5.on_stage.socialLink.entity;

import com.team5.on_stage.link.entity.Link;
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

    private Long userId;

}
