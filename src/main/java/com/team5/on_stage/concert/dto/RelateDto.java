package com.team5.on_stage.concert.dto;

import com.team5.on_stage.concert.entity.ConcertDetail;
import com.team5.on_stage.concert.entity.Relate;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class RelateDto {
    private String mt20id;
    private String relateNm;
    private String relateUrl;

    // RelateDto → Relate 엔티티 변환 메서드
    public Relate toEntity(ConcertDetail concertDetail) {
        return Relate.builder()
                .mt20id(mt20id)
                .relateNm(relateNm)
                .relateUrl(relateUrl)
                .build();
    }

    // Relate 엔티티 → RelateDto 변환 메서드
    public static RelateDto fromEntity(Relate relate) {
        return RelateDto.builder()
                .mt20id(relate.getMt20id())
                .relateNm(relate.getRelateNm())
                .relateUrl(relate.getRelateUrl())
                .build();
    }
}