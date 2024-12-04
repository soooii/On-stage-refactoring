package com.team5.on_stage.link.entity;

import com.team5.on_stage.global.constants.BlockType;
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
    private Long prevLinkId;

    @Enumerated(EnumType.STRING)
    private BlockType blockType;

    private int padding;

    private String url;

    private boolean active = true;

    // 소프트 딜리트 여부
    private boolean isDeleted = false;

    private int subscribed;

    @Builder
    public Link(String username, Long prevLinkId, String title, BlockType blockType, int padding , String url) {
        this.username = username;
        this.prevLinkId = prevLinkId;
        this.title = title;
        this.blockType = blockType;
        this.padding = padding;
        this.url = url;
        this.isDeleted = false;
    }

    public void subscribe() {
        this.subscribed++;
    }

    // Todo: 예외처리
    public void unsubscribe() {
        if (this.subscribed > 0) {
            this.subscribed--;
        }
        else {
            throw new IllegalStateException("Subscribed cannot be minus");
        }
    }
}



