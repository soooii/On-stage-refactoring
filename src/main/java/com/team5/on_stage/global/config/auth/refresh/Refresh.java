package com.team5.on_stage.global.config.auth.refresh;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
@Table(name = "refresh")
@Entity
public class Refresh {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // 사용자 식별
    @Column(nullable = false)
    private String username;

    @Lob
    @Column(nullable = false, columnDefinition = "BLOB")
    private String refresh;

    @Column(nullable = false)
    private String expiration;
}
