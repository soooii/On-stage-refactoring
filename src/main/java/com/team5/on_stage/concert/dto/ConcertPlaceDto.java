package com.team5.on_stage.concert.dto;

import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ConcertPlaceDto {
    private Long id;
    private String mt10id;
    private String fcltynm;
    private String adres;
    private double latitude;
    private double longtitude;
}