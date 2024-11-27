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
    private Long userId;

    private String title;

    // LinkedList
    private Long prevLinkId;

    private boolean active = true;

    // 소프트 딜리트 여부
    private boolean isDeleted = false;


    @Builder
    public Link(Long userId, Long prevLinkId, String title) {
        this.userId = userId;
        this.prevLinkId = prevLinkId;
        this.title = title;
        this.isDeleted = false;
    }
}



