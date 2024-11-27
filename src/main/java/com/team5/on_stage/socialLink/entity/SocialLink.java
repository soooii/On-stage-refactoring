package com.team5.on_stage.socialLink.entity;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table (name = "social_link")
@Getter
@NoArgsConstructor
public class SocialLink {
    @Id
    @GeneratedValue (strategy = GenerationType.IDENTITY)
    private Long id;

    private String username;

    private String instagram;

    private String youtube;

    private String x;

    private String spotify;

    private String github;


    @Builder
    public SocialLink(String username) {
        this.username = username;
        this.instagram = "";
        this.youtube = "";
        this.x = "";
        this.spotify = "";
        this.github = "";
    }
}
