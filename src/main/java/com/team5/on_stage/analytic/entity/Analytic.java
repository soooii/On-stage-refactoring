package com.team5.on_stage.analytic.entity;

import com.team5.on_stage.analytic.constants.EventType;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Entity
@Table(name = "analytic")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Builder
public class Analytic {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", updatable = false)
    private Long uuid;

    @Column(name = "event_type")
    @Enumerated(EnumType.STRING)
    private EventType eventType;

    @OneToOne
    @JoinColumn(name = "location_id") // 외래 키 설정
    private LocationInfo locationInfo; // 위치 정보 참조

    private LocalDate date;

    //TODO 추후에 연결
    private Long link;
    private Long user;
    private Long linkDetail;
}
