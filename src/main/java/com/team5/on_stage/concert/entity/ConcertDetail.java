package com.team5.on_stage.concert.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import org.springframework.data.annotation.LastModifiedDate;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Getter
@Builder
@AllArgsConstructor
@Table(name = "concert_detail")
public class ConcertDetail{

    //1:1 연결
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JoinColumn(name = "mt20id")
    private ConcertInfo concertInfo;

    @Column(name="prfcast")
    private String prfcast;
    @Column(name="prfcrew")
    private String prfcrew;
    @Column(name="poster")
    private String poster;

    @Column(name="mt10id", nullable = false)
    private String mt10id;
    //TODO onetomany
    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Relate> relate;
    //TODO onetoone
    @OneToOne(mappedBy = "concertDetail")  // concert_detail => concertDetail로 수정
    private ConcertPlace concertPlace;

    //업데이트 날짜
    @LastModifiedDate
    @Column(name="update_at")
    private LocalDateTime updatedAt;

}
