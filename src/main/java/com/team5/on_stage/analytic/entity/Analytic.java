package com.team5.on_stage.analytic.entity;

import com.team5.on_stage.analytic.constants.EventType;
import com.team5.on_stage.analytic.constants.SocialLinkType;
import com.team5.on_stage.link.entity.Link;
import com.team5.on_stage.linkDetail.entity.LinkDetail;
import com.team5.on_stage.socialLink.entity.SocialLink;
import com.team5.on_stage.user.entity.User;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Entity
@Table(name = "analytic")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Builder
public class Analytic {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", updatable = false, nullable = false)
    private Long uuid;

    @Column(name = "event_type")
    @Enumerated(EnumType.STRING)
    private EventType eventType;

    @Column(name = "social_link")
    @Enumerated(EnumType.STRING)
    private SocialLinkType socialLinkType;

    @OneToOne
    @JoinColumn(name = "location_id") // 외래 키 설정
    private LocationInfo locationInfo; // 위치 정보 참조

    private LocalDate date;

    @ManyToOne
    @JoinColumn(name = "username")
    private User user;

    @ManyToOne
    @JoinColumn(name = "link_id")
    private Link link;

    @ManyToOne
    @JoinColumn(name = "linkDetail_id")
    private LinkDetail linkDetail;
}
