package com.team5.on_stage.concert.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import java.time.LocalDate;

import lombok.NoArgsConstructor;
import org.springframework.data.annotation.LastModifiedDate;

import java.time.LocalDateTime;

// URL 가장 우선적인 id값
@Entity
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "concert_info")
public class ConcertInfo {

    @Id
    @Column(name = "concert_id", nullable = false, unique = true)
    private String mt20id; // PK로 변경

    @Column(name = "concert_nm", nullable = false)
    private String prfnm;

    @Column(name = "startfrom", nullable = false)
    private LocalDate prfpdfrom;

    @Column(name = "endto", nullable = false)
    private LocalDate prfpdto;

    @Column(name = "place_name", nullable = false)
    private String fcltynm;

    @Column(name = "poster_url")
    private String poster;

    @Column(name = "area")
    private String area;

    @Column(name = "concert_state")
    private String prfstate;

    @OneToOne(mappedBy = "concertInfo", cascade = CascadeType.ALL, orphanRemoval = true)
    private ConcertDetail concertDetail;

    @LastModifiedDate
    @Column(name = "update_at")
    private LocalDateTime updatedAt;
}