package com.team5.on_stage.article.repository;

import com.team5.on_stage.article.entity.Article;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ArticleRepository extends JpaRepository<Article, Long>, ArticleQueryDslRepository {
    List<Article> findAllByUser_Username(String username);
}