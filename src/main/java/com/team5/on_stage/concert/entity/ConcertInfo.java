package com.team5.on_stage.concert.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import java.time.LocalDate;
import org.springframework.data.annotation.LastModifiedDate;

import java.time.LocalDateTime;

// URL 가장 우선적인 id값
@Entity
@Getter
@Builder
@AllArgsConstructor
@Table(name = "concert_info")
public class ConcertInfo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name="mt20id", nullable = false, unique = true)
    private String mt20id;
//    //유저 id
//    @ManyToOne
//    @JoinColumn(name = "user_id", nullable = false)
//    private User user;

    @Column(name="prfnm", nullable = false)
    private String prfnm;
    @Column(name="prfpdfrom", nullable = false)
    private LocalDate prfpdfrom;
    @Column(name="prfpdto", nullable = false)
    private LocalDate prfpdto;
    //공연 위치
    @Column(name="fcltynm", nullable = false)
    private String fcltynm;
    @Column(name="poster")
    private String poster;
    //공연 지역
    @Column(name="area")
    private String area;
    @Column(name="prfstate")
    private String prfstate;
    //TODO ConcertDetail onetoone 연결
    @OneToOne(mappedBy = "concertInfo") // concert_info => concertInfo 변경
    private ConcertDetail concertDetail;

    //업데이트 날짜
    @LastModifiedDate
    @Column(name="update_at")
    private LocalDateTime updatedAt;

}