package com.team5.on_stage.article.repository;

import com.team5.on_stage.article.entity.Article;

import java.util.List;

public interface ArticleQueryDslRepository {
    void softDeleteByUsername(String username);
    List<Article> findByStatus(String status);
}
