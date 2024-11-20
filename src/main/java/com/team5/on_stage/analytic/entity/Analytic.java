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

    @Column(name = "device_info")
    private String deviceInfo;

    private LocalDateTime date;

    //TODO 추후에 연결
    private String page;
    private String user;
    private String link_detail;
}
