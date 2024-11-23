package com.team5.on_stage.summary.mapper;

import com.team5.on_stage.article.dto.ArticleRequestDTO;
import com.team5.on_stage.summary.dto.SummaryResponseDTO;
import com.team5.on_stage.summary.entity.Summary;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface SummaryMapper {
    Summary toEntity(ArticleRequestDTO dto);
    SummaryResponseDTO toDTO(Summary entity);
}


