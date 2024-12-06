package com.team5.on_stage.article.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import static com.team5.on_stage.article.entity.QArticle.article;


@Repository
@RequiredArgsConstructor
public class ArticleQueryDslRepositoryImpl implements ArticleQueryDslRepository {
    private final JPAQueryFactory queryFactory;

    @Override
    @Transactional
    public void softDeleteByUsername(String username) {
            queryFactory
                    .update(article)
                    .set(article.isDeleted, true)
                    .where(article.user.username.eq(username))
                    .execute();

    }
}
