package com.team5.on_stage.summary.mapper;

import com.team5.on_stage.article.dto.ArticleRequestDTO;
import com.team5.on_stage.summary.dto.SummaryRequestDTO;
import com.team5.on_stage.summary.dto.SummaryResponseDTO;
import com.team5.on_stage.summary.entity.Summary;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface SummaryMapper {
    Summary toEntity(SummaryRequestDTO dto);

    @Mapping(source = "id", target = "summaryId")
    SummaryResponseDTO toDTO(Summary entity);
}


