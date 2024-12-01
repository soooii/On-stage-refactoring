package com.team5.on_stage.concert.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import nonapi.io.github.classgraph.json.Id;

@Entity
@Getter
@Builder
@AllArgsConstructor
@Table(name = "concert_place")
public class ConcertPlace {
    @Id
    @Column(name="mt10id", nullable = false)
    private String mt10id;

    @Column(name="fcltynm", nullable = false)
    private String fcltynm;
    //TODO bigdecimal 계획
    // 위도
    @Column(name="latitude", nullable = false)
    private double latitude;
    // 경도
    @Column(name="longtitude")
    private double longtitude;
}
