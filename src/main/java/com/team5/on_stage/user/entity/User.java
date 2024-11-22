package com.team5.on_stage.user.entity;


import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.hibernate.annotations.DynamicUpdate;

@DynamicUpdate
@NoArgsConstructor
@AllArgsConstructor
@Getter @Setter
@Builder
@Entity
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @NotNull
    @Column(name = "nickname", nullable = false)
    private String nickname;

    @Column(name = "description")
    private String description;

    // Todo: Enum으로 관리할지.
    @Column(name = "verified", nullable = false)
    private Boolean verified;

    @NotNull
    @Column(name = "email", nullable = false, unique = true)
    private String email;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "email_domain", nullable = false)
    private EmailDomain emailDomain;

    @Column(name = "image")
    private String image;


    @PrePersist
    public void setDefaultValue() {

        if (this.verified == null) {
            this.verified = false;
        }

        if (this.description == null) {
            this.description = "나를 소개하는 문장을 입력해주세요.";
        }
    }
}
