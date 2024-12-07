package com.team5.on_stage.concert.dto;


import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class ConcertListResponse {
    private String concertId;
    private String concertName;
    private String startDate;
    private String endDate;
    private String placeName;
    private String posterUrl;
    private String area;
    private String concertState;

    private String performer;
    private String director;
    private String placeId;

    private PlaceInfo placeInfo;
    private List<RelateInfo> relateInfos;

    @Data
    @Builder
    public static class PlaceInfo {
        private String placeId;
        private String address;
        private String placeName;
        private double latitude;
        private double longitude;
    }

    @Data
    @Builder
    public static class RelateInfo {
        private String relateName;
        private String relateUrl;
    }
}
