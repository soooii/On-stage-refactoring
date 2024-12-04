package com.team5.on_stage.concert.dto;

import com.team5.on_stage.concert.entity.ConcertDetail;
import com.team5.on_stage.concert.entity.ConcertInfo;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.time.LocalDate;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
public class ConcertInfoDto {
    private String mt20id;
    private String prfnm;
    private LocalDate prfpdfrom;
    private LocalDate prfpdto;
    private String fcltynm;
    private String poster;
    private String area;
    private String prfstate;
    private ConcertDetail concertDetail; // You can also create a separate DTO for ConcertDetail if needed.
    private LocalDateTime updatedAt;

    @Builder
    public ConcertInfoDto(String mt20id, String prfnm, LocalDate prfpdfrom, LocalDate prfpdto, String fcltynm,
                          String poster, String area, String prfstate, ConcertDetail concertDetail, LocalDateTime updatedAt) {
        this.mt20id = mt20id;
        this.prfnm = prfnm;
        this.prfpdfrom = prfpdfrom;
        this.prfpdto = prfpdto;
        this.fcltynm = fcltynm;
        this.poster = poster;
        this.area = area;
        this.prfstate = prfstate;
        this.concertDetail = concertDetail;
        this.updatedAt = updatedAt;
    }

    // Method to convert Entity to DTO
    public static ConcertInfoDto fromEntity(ConcertInfo concertInfo) {
        return ConcertInfoDto.builder()
                .mt20id(concertInfo.getMt20id())
                .prfnm(concertInfo.getPrfnm())
                .prfpdfrom(concertInfo.getPrfpdfrom())
                .prfpdto(concertInfo.getPrfpdto())
                .fcltynm(concertInfo.getFcltynm())
                .poster(concertInfo.getPoster())
                .area(concertInfo.getArea())
                .prfstate(concertInfo.getPrfstate())
                .concertDetail(concertInfo.getConcertDetail()) // Convert to DTO if needed
                .updatedAt(concertInfo.getUpdatedAt())
                .build();
    }

    // Method to convert DTO to Entity
    public ConcertInfo toEntity() {
        return ConcertInfo.builder()
                .mt20id(this.mt20id)
                .prfnm(this.prfnm)
                .prfpdfrom(this.prfpdfrom)
                .prfpdto(this.prfpdto)
                .fcltynm(this.fcltynm)
                .poster(this.poster)
                .area(this.area)
                .prfstate(this.prfstate)
                .concertDetail(this.concertDetail) // Convert from DTO if needed
                .updatedAt(this.updatedAt)
                .build();
    }
}

