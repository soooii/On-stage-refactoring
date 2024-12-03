package com.team5.on_stage.link.entity;

import com.team5.on_stage.global.constants.BlockType;
import com.team5.on_stage.global.constants.BorderType;
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

    private boolean active = true;

    // 소프트 딜리트 여부
    private boolean isDeleted = false;

    private int liked;

    @Builder
    public Link(String username, Long prevLinkId, String title, BlockType blockType, int padding) {
        this.username = username;
        this.prevLinkId = prevLinkId;
        this.title = title;
        this.blockType = blockType;
        this.padding = padding;
        this.isDeleted = false;
    }

    public void Like() {
        this.liked++;
    }

    // Todo: 예외처리
    public void unLike() {
        if (this.liked > 0) {
            this.liked--;
        }
        else {
            throw new IllegalStateException("Liked cannot be minus");
        }
    }
}



