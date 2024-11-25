package com.team5.on_stage.article.mapper;

import com.team5.on_stage.article.dto.ArticleRequestDTO;
import com.team5.on_stage.article.dto.ArticleResponseDTO;
import com.team5.on_stage.article.entity.Article;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2024-11-25T14:54:38+0900",
    comments = "version: 1.6.0, compiler: javac, environment: Java 17.0.12 (Amazon.com Inc.)"
)
@Component
public class ArticleMapperImpl implements ArticleMapper {

    @Override
    public Article toEntity(ArticleRequestDTO dto) {
        if ( dto == null ) {
            return null;
        }

        Article.ArticleBuilder article = Article.builder();

        article.title( dto.getTitle() );
        article.content( dto.getContent() );
        article.link( dto.getLink() );

        return article.build();
    }

    @Override
    public ArticleResponseDTO toDto(Article entity) {
        if ( entity == null ) {
            return null;
        }

        ArticleResponseDTO articleResponseDTO = new ArticleResponseDTO();

        return articleResponseDTO;
    }
}
