package com.team5.on_stage.concert.mapper;

import com.team5.on_stage.concert.dto.ConcertPlaceDto;
import com.team5.on_stage.concert.entity.ConcertPlace;
import org.mapstruct.*;
import java.util.List;

@Mapper(componentModel = "spring")
public interface ConcertPlaceMapper {

    // 엔티티 → DTO
    ConcertPlaceDto toDto(ConcertPlace concertPlace);

    // DTO → 엔티티
    ConcertPlace toEntity(ConcertPlaceDto concertPlaceDto);

    // 엔티티 리스트 → DTO 리스트
    List<ConcertPlaceDto> toDtoList(List<ConcertPlace> concertPlaces);

    // DTO 리스트 → 엔티티 리스트
    List<ConcertPlace> toEntityList(List<ConcertPlaceDto> concertPlaceDtos);
}