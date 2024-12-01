package com.team5.on_stage.concert.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import org.springframework.data.annotation.LastModifiedDate;

import java.time.LocalDateTime;

@Entity
@Getter
@Builder
@AllArgsConstructor
@Table(name = "concert_detail")
public class ConcertDetail{

    //1:1 연결
    @Id
    @Column(name="mt20id", nullable = false)
    private String mt20id;

    @Column(name="prfcast")
    private String prfcast;
    @Column(name="prfcrew")
    private String prfcrew;
    @Column(name="poster")
    private String poster;
    @Column(name="mt10id", nullable = false)
    private String mt10id;
    //TODO onetomany
    private Relate relate;
    //TODO onetoone
    private ConcertPlace concertPlace;

    //업데이트 날짜
    @LastModifiedDate
    @Column(name="update_at")
    private LocalDateTime updatedAt;

}
