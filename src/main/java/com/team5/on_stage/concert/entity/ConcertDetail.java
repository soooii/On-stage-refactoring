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
    @Column(name = "concert_id", nullable = false, unique = true)
    private String mt20id; // PK로 변경

    @OneToOne
    @JoinColumn(name = "concert_id", referencedColumnName = "concert_id") // ConcertInfo와 연결
    private ConcertInfo concertInfo;

    @Column(name = "performer")
    private String prfcast;

    @Column(name = "director")
    private String prfcrew;

    @Column(name = "place_id", nullable = false, insertable = false, updatable = false) // 중복 매핑 방지
    private String mt10id;

    @OneToMany(mappedBy = "concertDetail", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Relate> relate;

    @OneToOne(fetch = FetchType.LAZY)  // Lazy Loading 추천
    @JoinColumn(name = "place_id")
    private ConcertPlace concertPlace;

    @LastModifiedDate
    @Column(name = "update_at")
    private LocalDateTime updatedAt;
}
