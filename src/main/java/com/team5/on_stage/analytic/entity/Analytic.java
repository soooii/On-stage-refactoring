package com.team5.on_stage.analytic.entity;

import com.team5.on_stage.analytic.constants.EventType;
import jakarta.annotation.Nullable;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

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
    private Long id;

    @Column(name = "event_type")
    @Enumerated(EnumType.STRING)
    private EventType eventType;

    private String location;

    private LocalDateTime date;

    //TODO 추후에 연결
    private Long page;
    private Long user;
    private Long link_detail;
}
