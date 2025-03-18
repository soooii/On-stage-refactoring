package com.team5.on_stage.article.mapper;

import com.team5.on_stage.article.dto.ArticleRequestDTO;
import com.team5.on_stage.article.dto.ArticleResponseDTO;
import com.team5.on_stage.article.entity.Article;
import com.team5.on_stage.article.entity.ArticleStatus;
import com.team5.on_stage.user.entity.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface ArticleMapper {

    // 기사 저장 시 쓰임
    @Mapping(target = "isDeleted", constant = "false")  // isDeleted 기본값으로 false 설정
    @Mapping(target = "user", source = "user")
    Article toEntityByUser(ArticleRequestDTO dto, User user);

    // Article 엔티티를 ArticleResponseDTO로 변환
    ArticleResponseDTO toDto(Article entity);
}
