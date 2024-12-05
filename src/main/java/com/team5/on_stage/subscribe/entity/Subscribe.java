package com.team5.on_stage.subscribe.entity;

import com.team5.on_stage.link.entity.Link;
import com.team5.on_stage.user.entity.User;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "subscribe")
@Entity
public class Subscribe {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // 구독자
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "subscriber_username", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private User subscriber;

    // 구독 대상자
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "subscribed_username", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private User subscribed;

    @Column(name = "subscribed_at",nullable = false)
    private LocalDateTime subscribedAt;

    @PrePersist
    public void setDefaultValue() {
        this.subscribedAt = LocalDateTime.now();
    }

    @Builder
    public Subscribe(User subscriber, User subscribed) {
        this.subscriber = subscriber;
        this.subscribed = subscribed;
    }

}
