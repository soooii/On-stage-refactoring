package com.team5.on_stage.user.entity;


import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;

@NoArgsConstructor
@AllArgsConstructor
@Getter @Setter
@Table(name = "user")
@Entity
public class User {

    // 소셜 로그인 도메인 + 소셜 로그인 ID 문자열
    @Id
    @NotNull
    @Column(name = "username", nullable = false, unique = true, updatable = false)
    private String username;

    // 서비스에서 사용할 이름
    @NotNull
    @Column(name = "nickname", nullable = false, unique = true)
    private String nickname;

    @Column(name = "description")
    private String description;

    @Column(name = "profile_image")
    private String profileImage;

    // 아티스트 인증 여부
    @Column(name = "verified", nullable = false)
    @Enumerated(EnumType.STRING)
    private Verified verified;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "role", nullable = false)
    private Role role;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "oauth2_domain", nullable = false, updatable = false)
    private OAuth2Domain OAuth2Domain;

    // 소셜 로그인 정보에서 가져온 이메일
    // Todo: 이메일이 없을 수도 있다. (ex: 깃허브)
    @NotNull
    @Email
    @Column(name = "email", nullable = false)
    private String email;

    // 소셜 로그인 정보에서 가져온 이름
    @NotNull
    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDate createdAt;

    @Column(name = "deactivated_at")
    private LocalDateTime deactivatedAt;


    private int subscribed;


    @Builder
    public User(String nickname,
                String username,
                String email,
                Role role,
                String name,
                OAuth2Domain OAuth2Domain) {

        this.nickname = nickname;
        this.username = username;
        this.email = email;
        this.role = role;
        this.OAuth2Domain = OAuth2Domain;
        this.name = name;
    }

    @PrePersist
    public void setDefaultValue() {

        this.role = Role.ROLE_USER;
        this.verified = Verified.UNVERIFIED;
        this.deactivatedAt = null;
        this.createdAt = LocalDate.now();
        this.description = "나를 소개하는 한마디";
        this.profileImage = "/img/imgbin_music-notes-.png";
    }

    public void updateOAuthUser(String name, String email) {

        this.name = name;
        this.email = email;
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
