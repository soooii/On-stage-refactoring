package com.team5.on_stage.concert.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "concert_place")
public class ConcertPlace {

    @Id
    @Column(name = "mt10id", nullable = false, unique = true)
    private String mt10id; // PK로 변경

    @OneToOne
    @JoinColumn(name = "mt10id", referencedColumnName = "mt10id")
    private ConcertDetail concertDetail;

    @Column(name = "adres", nullable = false)
    private String adres;

    @Column(name = "fcltynm", nullable = false)
    private String fcltynm;

    @Column(name = "latitude", nullable = false)
    private double latitude;

    @Column(name = "longtitude", nullable = false)
    private double longitude; // 이름 수정 (오타 수정)
}
