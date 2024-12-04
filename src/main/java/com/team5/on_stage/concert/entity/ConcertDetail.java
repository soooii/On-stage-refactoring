package com.team5.on_stage.concert.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.LastModifiedDate;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "concert_detail")
public class ConcertDetail {

    @Id
    @Column(name = "mt20id", nullable = false, unique = true)
    private String mt20id; // PK로 변경

    @OneToOne
    @JoinColumn(name = "mt20id", referencedColumnName = "mt20id") // ConcertInfo와 연결
    private ConcertInfo concertInfo;

    @Column(name = "prfcast")
    private String prfcast;

    @Column(name = "prfcrew")
    private String prfcrew;

    @Column(name = "mt10id", nullable = false)
    private String mt10id;

    @OneToMany(mappedBy = "concertDetail", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Relate> relate;

    @OneToOne(mappedBy = "concertDetail", cascade = CascadeType.ALL, orphanRemoval = true)
    private ConcertPlace concertPlace;

    @LastModifiedDate
    @Column(name = "update_at")
    private LocalDateTime updatedAt;
}
