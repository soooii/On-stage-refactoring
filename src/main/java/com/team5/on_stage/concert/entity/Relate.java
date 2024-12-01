package com.team5.on_stage.concert.entity;

import jakarta.persistence.*;

@Entity
public class Relate {
    //concertDetail 1:다 연결
    @Id
    @Column(name="mt20id", nullable = false)
    private String mt20id;
    
    @Column(name="relate_nm", nullable = false)
    private String relatenm;
    @Column(name="relate_url", nullable = false)
    private String relateUrl;
}
