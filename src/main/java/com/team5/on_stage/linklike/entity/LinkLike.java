package com.team5.on_stage.linklike.entity;

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
@Entity
public class LinkLike {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "link_id")
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Link link;

    @Column(name = "liked_at",nullable = false)
    private LocalDateTime likedAt;

    @PrePersist
    public void setDefaultValue() {
        this.likedAt = LocalDateTime.now();
    }

    @Builder
    public LinkLike(User user, Link link) {
        this.user = user;
        this.link = link;
    }

//    public void setLink(Link link) {
//        this.link = link;
//        link.getLinkLikes().add(this);
//    }
}
