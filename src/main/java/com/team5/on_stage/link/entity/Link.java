package com.team5.on_stage.link.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Table(name = "link")
@NoArgsConstructor
public class Link {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // 추후 매핑
    private String username;

    private String title;

    // LinkedList
    private String prevLinkId;

    private boolean active = true;

    // 소프트 딜리트 여부
    private boolean isDeleted = false;


    @Builder
    public Link(String username, String prevLinkId, String title) {
        this.username = username;
        this.prevLinkId = prevLinkId;
        this.title = title;
        this.isDeleted = false;
    }
}



