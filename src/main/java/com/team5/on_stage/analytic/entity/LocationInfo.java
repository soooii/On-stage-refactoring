package com.team5.on_stage.analytic.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "location_info")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class LocationInfo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String ipAddress; // IP 주소
    private String country;    // 나라
    private String region;     // 지역
}