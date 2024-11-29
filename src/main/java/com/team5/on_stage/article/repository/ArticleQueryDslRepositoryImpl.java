package com.team5.on_stage.article.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import static com.team5.on_stage.article.entity.QArticle.article;


@Repository
@RequiredArgsConstructor
public class ArticleQueryDslRepositoryImpl implements ArticleQueryDslRepository {
    private final JPAQueryFactory queryFactory;
    @Override
    public void softDeleteByUserId(Long userId) {
            queryFactory.
                    update(article)
                    .where(article.user.id.eq(userId))
                    .execute();

    }
}
