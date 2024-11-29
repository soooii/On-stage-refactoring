package com.team5.on_stage.summary.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import static com.team5.on_stage.summary.entity.QSummary.summary1;

@Repository
@RequiredArgsConstructor
public class SummaryQueryDslRepositoryImpl implements SummaryQueryDslRepository {

    private final JPAQueryFactory queryFactory;

    @Override
    @Transactional
    public void softDeleteByUserId(Long userId) {
        queryFactory
                .update(summary1)
                .set(summary1.isDeleted, true)
                .where(summary1.user.id.eq(userId))
                .execute();
    }

}
