package com.team5.on_stage.user.entity;


import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.hibernate.annotations.DynamicUpdate;

import java.time.LocalDate;

@DynamicUpdate
@NoArgsConstructor
@AllArgsConstructor
@Getter @Setter
@Builder
@Table(name = "user")
@Entity
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    // 서비스에서 사용할 이름
    @NotNull
    @Column(name = "nickname", nullable = false)
    private String nickname;

    @Column(name = "description")
    private String description;

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

    // 소셜 로그인 도메인 + 소셜 로그인 ID 문자열
    @NotNull
    @Column(name = "username", nullable = false, unique = true)
    private String username;

    // 아티스트 인증 여부
    @Column(name = "verified", nullable = false)
    @Enumerated(EnumType.STRING)
    private Verified verified;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "oauth2_domain", nullable = false)
    private OAuth2Domain OAuth2Domain;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "role", nullable = false)
    private Role role;

    @Column(name = "image")
    private String image;

    @Column(name = "created_at", nullable = false)
    private LocalDate createdAt;


    @PrePersist
    public void setDefaultValue() {

        if (this.description == null) {
            this.description = "나를 소개하는 문장을 입력해주세요.";
        }

        if (this.image == null) {
            this.image = "";
        }

        this.createdAt = LocalDate.now();
    }

    public void updateOAuthUser(String name, String email) {

        this.name = name;
        this.email = email;
    }
}
