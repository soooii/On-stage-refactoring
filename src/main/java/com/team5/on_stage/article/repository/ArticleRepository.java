package com.team5.on_stage.article.repository;

import com.team5.on_stage.article.entity.Article;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ArticleRepository extends JpaRepository<Article, Long> {
    List<Article> findAllByUserId(Long userId);
    void deleteAllByUserId(Long userId);
    //쿼리dsl 사용할 수 있는 인터페이스 articlequeryrepository
    //인터페이스 implements하는 클래스 구현체
    //구현체에서 update하는 쿼리 dsl
}