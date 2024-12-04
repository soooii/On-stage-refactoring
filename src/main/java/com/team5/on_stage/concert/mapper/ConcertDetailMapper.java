package com.team5.on_stage.concert.mapper;

import com.team5.on_stage.concert.dto.ConcertDetailDto;
import com.team5.on_stage.concert.entity.ConcertDetail;
import org.mapstruct.*;
import java.util.List;

@Mapper(componentModel = "spring", uses = {RelateMapper.class, ConcertPlaceMapper.class})
public interface ConcertDetailMapper {

    // 엔티티 → DTO
    @Mapping(target = "relate", source = "relate") // Relate 매핑
    @Mapping(target = "concertPlace", source = "concertPlace") // ConcertPlace 매핑
    ConcertDetailDto toDto(ConcertDetail concertDetail);

    // DTO → 엔티티
    @Mapping(target = "relate", source = "relate") // Relate 매핑
    @Mapping(target = "concertPlace", source = "concertPlace") // ConcertPlace 매핑
    ConcertDetail toEntity(ConcertDetailDto concertDetailDto);

    // 엔티티 리스트 → DTO 리스트
    List<ConcertDetailDto> toDtoList(List<ConcertDetail> concertDetails);

    // DTO 리스트 → 엔티티 리스트
    List<ConcertDetail> toEntityList(List<ConcertDetailDto> concertDetailDtos);
}
