package com.team5.on_stage.concert.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Entity
@Getter
@Builder
@AllArgsConstructor
@Table(name = "concert_place")
public class ConcertPlace {
    //1:1 연결
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JoinColumn(name = "mt10id")
    private ConcertDetail concertDetail;


    @Column(name="fcltynm", nullable = false)
    private String fcltynm;
    //TODO bigdecimal 계획
    // 위도
    @Column(name="latitude", nullable = false)
    private double latitude;
    // 경도
    @Column(name="longtitude", nullable = false)
    private double longtitude;
}
