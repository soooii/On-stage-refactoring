package com.team5.on_stage.concert.mapper;

import com.team5.on_stage.concert.dto.RelateDto;
import com.team5.on_stage.concert.entity.Relate;
import org.mapstruct.*;
import java.util.List;

@Mapper(componentModel = "spring")
public interface RelateMapper {

    // 엔티티 → DTO
    RelateDto toDto(Relate relate);

    // DTO → 엔티티
    Relate toEntity(RelateDto relateDto);

    // 엔티티 리스트 → DTO 리스트
    List<RelateDto> toDtoList(List<Relate> relates);

    // DTO 리스트 → 엔티티 리스트
    List<Relate> toEntityList(List<RelateDto> relateDtos);
}

