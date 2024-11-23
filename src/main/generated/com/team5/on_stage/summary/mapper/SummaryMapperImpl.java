package com.team5.on_stage.summary.mapper;

import com.team5.on_stage.article.dto.ArticleRequestDTO;
import com.team5.on_stage.summary.dto.SummaryResponseDTO;
import com.team5.on_stage.summary.entity.Summary;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2024-11-23T17:44:03+0900",
    comments = "version: 1.6.0, compiler: javac, environment: Java 17.0.12 (Amazon.com Inc.)"
)
@Component
public class SummaryMapperImpl implements SummaryMapper {

    @Override
    public Summary toEntity(ArticleRequestDTO dto) {
        if ( dto == null ) {
            return null;
        }

        Summary.SummaryBuilder summary = Summary.builder();

        return summary.build();
    }

    @Override
    public SummaryResponseDTO toDTO(Summary entity) {
        if ( entity == null ) {
            return null;
        }

        SummaryResponseDTO summaryResponseDTO = new SummaryResponseDTO();

        return summaryResponseDTO;
    }
}
