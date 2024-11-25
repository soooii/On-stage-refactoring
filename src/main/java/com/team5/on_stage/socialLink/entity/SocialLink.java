package com.team5.on_stage.socialLink.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table (name = "social_link")
@Getter
@Setter
public class SocialLink {
    @Id
    @GeneratedValue (strategy = GenerationType.IDENTITY)
    private Long id;

    private Long userId;

    private String instagram ="";

    private String youtube ="";

    private String x ="";

    private String spotify ="";

    private String github ="";

}
