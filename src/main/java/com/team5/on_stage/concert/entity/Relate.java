package com.team5.on_stage.concert.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "relate")
public class Relate {

    @Id
    @Column(name = "concert_id", nullable = false)
    private String mt20id; // ConcertDetail의 mt20id를 PK로 사용

    @ManyToOne
    @JoinColumn(name = "concert_id", referencedColumnName = "concert_id", insertable = false, updatable = false)
    private ConcertDetail concertDetail;

    @Column(name = "relate_nm", nullable = false)
    private String relateNm;

    @Column(name = "relate_url", nullable = false)
    private String relateUrl;
}

