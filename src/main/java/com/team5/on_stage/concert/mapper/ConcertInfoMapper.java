package com.team5.on_stage.concert.mapper;

import com.team5.on_stage.concert.dto.ConcertInfoDto;
import com.team5.on_stage.concert.entity.ConcertInfo;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface ConcertInfoMapper {
    ConcertInfoMapper INSTANCE = Mappers.getMapper(ConcertInfoMapper.class);

    // Entity to DTO
    ConcertInfoDto toDto(ConcertInfo concertInfo);

    // DTO to Entity
    ConcertInfo toEntity(ConcertInfoDto concertInfoDto);
}
