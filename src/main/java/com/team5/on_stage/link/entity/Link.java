package com.team5.on_stage.link.entity;

import com.team5.on_stage.global.constants.Layout;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@Table(name = "link")
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Link {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;

    // LinkedList
    private Long prevLinkId;

    private boolean active = true;

    private boolean isDeleted = false;

    // 추후 매핑
    private Long userId;
}



