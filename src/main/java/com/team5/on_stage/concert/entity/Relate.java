package com.team5.on_stage.concert.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Entity
@Getter
@Builder
@AllArgsConstructor
public class Relate {
    //concertDetail 1:다 연결
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name="mt20id", nullable = false)
    private ConcertDetail concertDetail;
    
    @Column(name="relate_nm", nullable = false)
    private String relatenm;
    @Column(name="relate_url", nullable = false)
    private String relateUrl;
}
