package com.team5.on_stage.article.repository;

public interface ArticleQueryDslRepository {
    void softDeleteByUserId(Long userId);
}
