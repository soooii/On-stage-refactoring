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
    @Column(name = "place_id", nullable = false, unique = true)
    private String mt10id; // PK로 변경

    @OneToOne
    @JoinColumn(name = "place_id", referencedColumnName = "place_id")
    private ConcertDetail concertDetail;

    @Column(name = "adres", nullable = false)
    private String adres;

    @Column(name = "place_nm", nullable = false)
    private String fcltynm;

    @Column(name = "latitude", nullable = false)
    private double latitude;

    @Column(name = "longtitude", nullable = false)
    private double longtitude; // 이름 수정 (오타 수정)
}
