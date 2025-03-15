package com.team5.on_stage.article.mapper;

import com.team5.on_stage.article.dto.ArticleRequestDTO;
import com.team5.on_stage.article.dto.ArticleResponseDTO;
import com.team5.on_stage.article.entity.Article;
import com.team5.on_stage.user.entity.User;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ArticleMapper {
    Article toEntity(ArticleRequestDTO dto);
    ArticleResponseDTO toDto(Article entity);

    default Article toEntityByUser(ArticleRequestDTO dto, User user) {
        Article article = toEntity(dto);
        article = Article.builder()
                .user(user)
                .title(dto.getTitle())
                .content(dto.getContent())
                .link(dto.getLink())
                .time(dto.getTime())
                .build();
        return article;
    }
}