package com.team5.on_stage.linkDetail.entity;

import com.team5.on_stage.global.constants.Platform;
import com.team5.on_stage.link.entity.Link;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name = "link_detail")
public class LinkDetail {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "link_id")
    private Link link;

    @Enumerated(EnumType.STRING)
    private Platform platform;

    private String url;

}
