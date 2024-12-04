package com.team5.on_stage.concert.dto;

import com.team5.on_stage.concert.entity.ConcertDetail;
import com.team5.on_stage.concert.entity.ConcertInfo;
import com.team5.on_stage.concert.entity.ConcertPlace;
import com.team5.on_stage.concert.entity.Relate;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ConcertDetailDto {
    private String mt20id;
    private String prfcast;
    private String prfcrew;
    private String mt10id;
    private List<Relate> relate;      // 필요하면 RelateDto로 변환 가능
    private ConcertPlace concertPlace; // 필요하면 ConcertPlaceDto로 변환 가능
    private LocalDateTime updatedAt;

    // toEntity 메서드
    public ConcertDetail toEntity() {
        return ConcertDetail.builder()
                .mt20id(mt20id)
                .prfcast(prfcast)
                .prfcrew(prfcrew)
                .mt10id(mt10id)
                .relate(relate) // 필요하면 RelateDto에서 변환
                .build();
    }

    // fromEntity 메서드
    public static ConcertDetailDto fromEntity(ConcertDetail concertDetail) {
        return ConcertDetailDto.builder()
                .mt20id(concertDetail.getMt20id())
                .prfcast(concertDetail.getPrfcast())
                .prfcrew(concertDetail.getPrfcrew())
                .mt10id(concertDetail.getMt10id())
                .relate(concertDetail.getRelate()) // 필요하면 Relate에서 DTO 변환
                .concertPlace(concertDetail.getConcertPlace()) // 필요하면 ConcertPlace에서 DTO 변환
                .updatedAt(concertDetail.getUpdatedAt())
                .build();
    }
}
