package com.team5.on_stage.link.entity;

import com.team5.on_stage.global.constants.Layout;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name = "link")
public class Link {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String thumbnail = null;

    private String title;

    // LinkedList 자료구조 방식 사용할 예정
    private Long prevLinkId;

    @Enumerated(EnumType.STRING)
    private Layout layout = Layout.CLASSIC;

    private boolean active = true;

    private boolean isDeleted = false;

    // 추후 매핑
    private Long userId;
}
